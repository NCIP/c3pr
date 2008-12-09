package edu.duke.cabig.c3pr.web.study.tabs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;

import edu.duke.cabig.c3pr.domain.APIName;
import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyOrganization;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.exception.C3PRCodedRuntimeException;
import edu.duke.cabig.c3pr.tools.Configuration;
import edu.duke.cabig.c3pr.utils.StringUtils;
import edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.AjaxableUtils;
import edu.duke.cabig.c3pr.web.study.StudyWrapper;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Jun 15, 2007 Time: 1:39:34 PM To change this template
 * use File | Settings | File Templates.
 */
public class ManageStudySitesTab extends StudyTab {

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
        return refdata;
    }
    
    public ModelAndView showEndpointMessage(HttpServletRequest request, Object obj, Errors errors) {
        StudyWrapper wrapper=((StudyWrapper)obj);
        Study study=((StudyWrapper)wrapper).getStudy();
        String nciCode=request.getParameter("nciCode");
        String localNciCode=request.getParameter("localNciCode");
        StudyOrganization studyOrganization=study.getStudyOrganization(nciCode);
        Map map=new HashMap();
        map.put("site", studyOrganization);
        map.put("localSite", study.getStudyOrganization(localNciCode));
        return new ModelAndView(AjaxableUtils.getAjaxViewName(request),map);
    }

    public ModelAndView changeStatus(HttpServletRequest request, Object obj,
                    Errors errors) {
        StudyWrapper wrapper = (StudyWrapper)obj ;
        Study study = wrapper.getStudy();
        
        String nciInstituteCode = request.getParameter("nciCode");
        String studySiteType = request.getParameter("studySiteType"); ;
        List<Identifier> studyIdentifiers = study.getIdentifiers();
        StudySite studySite ;
        if(StringUtils.isBlank(studySiteType)){
			studySite = study.getStudySite(nciInstituteCode);
		}else{
			studySite = study.getCompanionStudySite(nciInstituteCode);
		}

        APIName apiName=APIName.valueOf(request.getParameter("action"));
        if(apiName==APIName.CREATE_STUDY){
            studyService.createStudyAtAffiliate(studyIdentifiers, nciInstituteCode);
        }else if(apiName==APIName.OPEN_STUDY){
            studyService.openStudyAtAffiliate(studyIdentifiers, nciInstituteCode);
        }else if(apiName==APIName.AMEND_STUDY){
            studyService.amendStudyAtAffiliates(studyIdentifiers, study);
        }else if(apiName==APIName.CLOSE_STUDY){
            studyService.closeStudyAtAffiliate(studyIdentifiers, nciInstituteCode);
        }else if(apiName==APIName.APPROVE_STUDY_SITE_FOR_ACTIVATION){
            try {
            	studySite = studyRepository.approveStudySiteForActivation(studyIdentifiers, studySite);
            }
            catch (C3PRCodedRuntimeException e) {
                e.printStackTrace();
            }
        }else if(apiName==APIName.ACTIVATE_STUDY_SITE){
            try {
            	studySite = studyRepository.activateStudySite(studyIdentifiers, studySite);
            }
            catch (C3PRCodedRuntimeException e) {
                e.printStackTrace();
            }
        }else if(apiName==APIName.CLOSE_STUDY_SITE){
            try {
            	studySite = studyRepository.closeStudySite(studyIdentifiers, nciInstituteCode);
            }
            catch (C3PRCodedRuntimeException e) {
                e.printStackTrace();
            }
        } 
        Map map=new HashMap();
        study = studyRepository.getUniqueStudy(study.getIdentifiers());
        wrapper.setStudy(study);
        studyDao.initialize(study);
        map.put("site", studySite);
        return new ModelAndView(AjaxableUtils.getAjaxViewName(request),map);
    }

}
