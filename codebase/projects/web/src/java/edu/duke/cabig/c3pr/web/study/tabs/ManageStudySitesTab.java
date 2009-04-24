package edu.duke.cabig.c3pr.web.study.tabs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;

import edu.duke.cabig.c3pr.domain.APIName;
import edu.duke.cabig.c3pr.domain.EndPoint;
import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyOrganization;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.exception.C3PRCodedRuntimeException;
import edu.duke.cabig.c3pr.exception.MultisiteException;
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
        EndPoint endPoint=null;
        if(StringUtils.isBlank(studySiteType)){
			studySite = study.getStudySite(nciInstituteCode);
		}else{
			studySite = study.getCompanionStudySite(nciInstituteCode);
		}

        APIName apiName=APIName.valueOf(request.getParameter("action"));
        if(apiName==APIName.CREATE_STUDY_DEFINITION){
        	endPoint=studyRepository.createStudyAtAffiliate(studyIdentifiers, nciInstituteCode);
        }else if(apiName==APIName.OPEN_STUDY){
        	endPoint=studyRepository.openStudyAtAffiliate(studyIdentifiers, nciInstituteCode);
        }else if(apiName==APIName.AMEND_STUDY){
        	studyRepository.amendStudyAtAffiliates(studyIdentifiers, study);
        }else if(apiName==APIName.CLOSE_STUDY_TO_ACCRUAL){
        	endPoint=studyRepository.closeStudyToAccrualAtAffiliate(studyIdentifiers, nciInstituteCode);
        }
//        else if(apiName==APIName.APPROVE_STUDY_SITE_FOR_ACTIVATION){
//            try {
//            	studySite = studyRepository.approveStudySiteForActivation(studyIdentifiers, studySite);
//            }
//            catch (MultisiteException e) {
//                e.printStackTrace();
//            }
//            catch (C3PRCodedRuntimeException e) {
//                request.setAttribute("actionError", e);
//            }
//        }
        else if(apiName==APIName.ACTIVATE_STUDY_SITE){
            try {
            	studySite = studyRepository.activateStudySite(studyIdentifiers, studySite);
            }
            catch (MultisiteException e) {
                e.printStackTrace();
            }
            catch (C3PRCodedRuntimeException e) {
                request.setAttribute("actionError", e);
            }
        }else if(apiName==APIName.CLOSE_STUDY_SITE_TO_ACCRUAL){
            try {
            	studySite = studyRepository.closeStudySiteToAccrual(studyIdentifiers, nciInstituteCode);
            }
            catch (MultisiteException e) {
                e.printStackTrace();
            }
            catch (C3PRCodedRuntimeException e) {
                request.setAttribute("actionError", e);
            }
        } 
        if(studySite == null){
        	studySite =  wrapper.getStudy().getStudySite(studySite.getHealthcareSite().getNciInstituteCode());
        }
        Map map=new HashMap();
        wrapper.setStudy(studyRepository.getUniqueStudy(study.getIdentifiers()));
        studyDao.initialize(wrapper.getStudy());
        //map.put("site",studySite);
        //using the nci code to load the fresh studysite from the study. Himanshu to review it.
        if(StringUtils.isBlank(studySiteType)){
			studySite = wrapper.getStudy().getStudySite(nciInstituteCode);
		}else{
			studySite = wrapper.getStudy().getCompanionStudySite(nciInstituteCode);
		}
        map.put("site",studySite);
        
        return new ModelAndView(AjaxableUtils.getAjaxViewName(request),map);
    }

	private StudySite getStudySite(Study study, String nciCode, String studySiteType) {
		if(StringUtils.isBlank(studySiteType)){
			return study.getStudySite(nciCode);
		}else{
			return study.getCompanionStudySite(nciCode);
		}
	}
	
}
