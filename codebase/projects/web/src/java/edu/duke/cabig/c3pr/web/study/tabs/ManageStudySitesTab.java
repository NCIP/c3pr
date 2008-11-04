package edu.duke.cabig.c3pr.web.study.tabs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;

import edu.duke.cabig.c3pr.domain.APIName;
import edu.duke.cabig.c3pr.domain.EndPoint;
import edu.duke.cabig.c3pr.domain.Error;
import edu.duke.cabig.c3pr.domain.ServiceName;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.WorkFlowStatusType;
import edu.duke.cabig.c3pr.domain.validator.StudyValidator;
import edu.duke.cabig.c3pr.tools.Configuration;
import edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.AjaxableUtils;
import edu.duke.cabig.c3pr.web.study.StudyWrapper;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Jun 15, 2007 Time: 1:39:34 PM To change this template
 * use File | Settings | File Templates.
 */
public class ManageStudySitesTab extends StudyTab {

    private StudyValidator studyValidator;

    protected Configuration configuration;

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public ManageStudySitesTab() {
        super("Manage Sites", "Manage Sites", "study/study_manage_studysites");
    }

    @Override
    public Map<String, Object> referenceData(HttpServletRequest request, StudyWrapper wrapper) {
        Map<String, Object> refdata = super.referenceData(wrapper);
        boolean isAdmin = isAdmin();
        refdata.put("localNCICode", this.configuration.get(Configuration.LOCAL_NCI_INSTITUTE_CODE));
        refdata.put("canMultisiteBroadcast", studyService.canMultisiteBroadcast(wrapper.getStudy()));
        return refdata;
    }

    public ModelAndView changeStatus(HttpServletRequest request, Object obj,
                    Errors errors) {
        StudyWrapper wrapper=((StudyWrapper)obj);
        Study study=((StudyWrapper)wrapper).getStudy();
        String nciCode=request.getParameter("nciCode");
        StudySite studySite=study.getStudySite(nciCode);
        Boolean isRetry=new Boolean(request.getParameter("isRetry"));
        APIName apiName=APIName.valueOf(request.getParameter("action"));
        List domainObjects=new ArrayList();
        if(apiName==APIName.OPEN_STUDY || apiName==APIName.CLOSE_STUDY || apiName==APIName.AMEND_STUDY){
            studyService.handleMultiSiteBroadcast(studySite, ServiceName.STUDY, apiName, study.getIdentifiers());
            EndPoint endPoint=studySite.getLastAttemptedEndpoint();
            if(endPoint.getStatus()==WorkFlowStatusType.MESSAGE_SEND_FAILED ){
                Error error=endPoint.getLastAttemptError();
                if(error!=null && error.getErrorCode().equals("337")){
                    domainObjects.add(study);
                    studyService.handleMultiSiteBroadcast(studySite, ServiceName.STUDY, APIName.CREATE_STUDY, domainObjects);
                    endPoint=studySite.getLastAttemptedEndpoint();
                    if(endPoint.getStatus()==WorkFlowStatusType.MESSAGE_SEND_CONFIRMED ){
                        studyService.handleMultiSiteBroadcast(studySite, ServiceName.STUDY, apiName, study.getIdentifiers());
                    }
                }
            }
        }else if(apiName==APIName.CREATE_STUDY){
            domainObjects.add(study);
            studyService.handleMultiSiteBroadcast(studySite, ServiceName.STUDY, apiName, domainObjects);
        }else if(apiName==APIName.APPROVE_STUDY_SITE_FOR_ACTIVATION && isRetry){
            domainObjects.addAll(study.getIdentifiers());
            domainObjects.add(studySite.getHealthcareSite());
            studyService.handleMultiSiteBroadcast(studySite, ServiceName.STUDY, apiName, study.getIdentifiers());
        }else if(apiName==APIName.APPROVE_STUDY_SITE_FOR_ACTIVATION){
            studySite=studyRepository.approveStudySiteForActivation(study.getIdentifiers(), nciCode);
        }else if(apiName==APIName.ACTIVATE_STUDY_SITE && isRetry){
            domainObjects.addAll(study.getIdentifiers());
            domainObjects.add(studySite.getHealthcareSite());
            studyService.handleMultiSiteBroadcast(studySite, ServiceName.STUDY, apiName, study.getIdentifiers());
        }else if(apiName==APIName.ACTIVATE_STUDY_SITE){
            studySite=studyRepository.activateStudySite(study.getIdentifiers(), nciCode);
        }else if(apiName==APIName.CLOSE_STUDY_SITE && isRetry){
            domainObjects.addAll(study.getIdentifiers());
            domainObjects.add(studySite.getHealthcareSite());
            studyService.handleMultiSiteBroadcast(studySite, ServiceName.STUDY, apiName, study.getIdentifiers());
        }else if(apiName==APIName.CLOSE_STUDY_SITE){
            studySite=studyRepository.closeStudySite(study.getIdentifiers(), nciCode);
        }
        Map map=new HashMap();
        map.put("site", studySite);
        return new ModelAndView(AjaxableUtils.getAjaxViewName(request),map);
    }
}
