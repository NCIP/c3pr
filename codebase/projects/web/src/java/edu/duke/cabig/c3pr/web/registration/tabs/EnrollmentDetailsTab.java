package edu.duke.cabig.c3pr.web.registration.tabs;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import edu.duke.cabig.c3pr.constants.ICD9DiseaseSiteCodeDepth;
import edu.duke.cabig.c3pr.constants.RegistrationWorkFlowStatus;
import edu.duke.cabig.c3pr.dao.ICD9DiseaseSiteDao;
import edu.duke.cabig.c3pr.domain.ICD9DiseaseSite;
import edu.duke.cabig.c3pr.domain.StudyInvestigator;
import edu.duke.cabig.c3pr.domain.StudySiteStudyVersion;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.StudySubjectConsentVersion;
import edu.duke.cabig.c3pr.domain.StudyVersion;
import edu.duke.cabig.c3pr.utils.Lov;
import edu.duke.cabig.c3pr.utils.StringUtils;
import edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.AjaxableUtils;
import edu.duke.cabig.c3pr.web.registration.StudySubjectWrapper;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Jun 15, 2007 Time: 3:30:05 PM To change this template
 * use File | Settings | File Templates.
 */
public class EnrollmentDetailsTab extends RegistrationTab<StudySubjectWrapper> {
	
	 private ICD9DiseaseSiteDao icd9DiseaseSiteDao;

    public void setIcd9DiseaseSiteDao(ICD9DiseaseSiteDao icd9DiseaseSiteDao) {
		this.icd9DiseaseSiteDao = icd9DiseaseSiteDao;
	}

	public EnrollmentDetailsTab() {
        super("Enrollment Details", "Enrollment Details", "registration/reg_registration_details");
    }
    
    @Override
    public Map referenceData(HttpServletRequest request,
    		StudySubjectWrapper command) {
    	Map refdata=super.referenceData(request, command);
    	Map<String, List<Lov>> configMap = configurationProperty.getMap();
    	refdata.put("paymentMethods", configMap.get("paymentMethods"));
    	 refdata.put("diseaseSiteCategories", getDiseaseSiteCategories());
    	return refdata;
    }
    
    public List<ICD9DiseaseSite> getDiseaseSiteCategories(){
    	List<ICD9DiseaseSite> icd9DiseaseSites = new ArrayList<ICD9DiseaseSite>();
    	icd9DiseaseSites.addAll(icd9DiseaseSiteDao.getByLevel(ICD9DiseaseSiteCodeDepth.LEVEL1));
    	
    	return icd9DiseaseSites;
    }
    
    @Override
    public void postProcess(HttpServletRequest request, StudySubjectWrapper command, Errors errors) {
    	StudySubjectWrapper wrapper = (StudySubjectWrapper) command ;
    	StudySubject studySubject = wrapper.getStudySubject();
    	if(studySubject.getRegWorkflowStatus() != RegistrationWorkFlowStatus.ENROLLED && studySubject.getScheduledEpoch().getEpoch().getEnrollmentIndicator()){
    		studySubject.getScheduledEpoch().setStartDate(studySubject.getStartDate());
    	}
    	if(WebUtils.hasSubmitParameter(request, "updateStudyVersion") && request.getParameter("updateStudyVersion").equals("true")){
    		Date consentSignedDate = null;
    		try {
    			consentSignedDate = new SimpleDateFormat("MM/dd/yyyy").parse(request.getParameter("consentSignedDate"));
    			request.setAttribute("consentSignedDate", request.getParameter("consentSignedDate"));
			} catch (ParseException e) {
				throw new RuntimeException("Invalid Submit. Registration Date is invalid");
			}
    		studySubject.changeStudyVersion(consentSignedDate);
    		return;
    	}
    	
    	// set the scheduled epoch start date to registration start date for first time enrollment
    	
    	if(command.getStudySubject().getScheduledEpoch().getEpoch().getEnrollmentIndicator() &&
    			command.getStudySubject().getRegWorkflowStatus() != RegistrationWorkFlowStatus.ENROLLED){
    		command.getStudySubject().getScheduledEpoch().setStartDate(command.getStudySubject().getStartDate());
    	}
    		
        if(!StringUtils.isBlank(request.getParameter("treatingPhysicianInternal"))){
            for(StudyInvestigator studyInvestigator : studySubject.getStudySite().getStudyInvestigators()){
                if(studyInvestigator.getId()==Integer.parseInt(request.getParameter("treatingPhysicianInternal"))){
                	studySubject.setTreatingPhysician(studyInvestigator);
                    break;
                }
            }
        }

        if(command.getStudySubject().getDiseaseHistory() != null){
        	if(StringUtils.equals(command.getStudySubject().getDiseaseHistory().getOtherPrimaryDiseaseSiteCode(), "(Begin typing here)")){
        		command.getStudySubject().getDiseaseHistory().setOtherPrimaryDiseaseSiteCode("");
        	}
        }
    }
    
    @Override
    public void validate(StudySubjectWrapper command, Errors errors) {
    	if(command.getStudySubject().getRegWorkflowStatus() != RegistrationWorkFlowStatus.ENROLLED && 
    			command.getStudySubject().getScheduledEpoch().getEpoch().getEnrollmentIndicator()) {
	    	Date date = command.getStudySubject().getStartDate();
		    if(date !=null){
		    	for(StudySubjectConsentVersion studySubjectConsentVersion : command.getStudySubject().getStudySubjectStudyVersion().getStudySubjectConsentVersions()){
					if(date.before(studySubjectConsentVersion.getInformedConsentSignedDate())){
						errors.reject("studySubject.startDate", "Registration start date cannot be before the informed consent signed date");
					}
				}
	    	}
    	}
    }
    
    public ModelAndView validateRegistrationDate(HttpServletRequest request, Object command, Errors errors) {
    	Map<String, Object> map = new HashMap<String, Object>();
    	Date consentSignedDate = null;
    	try {
    		consentSignedDate = new SimpleDateFormat("MM/dd/yyyy").parse(request.getParameter("consentSignedDate"));
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
		map.put("cannotEnroll", "false");
		StudySiteStudyVersion studySiteStudyVersion = ((StudySubjectWrapper)command).getStudySubject().getStudySubjectStudyVersion().getStudySiteStudyVersion();
		if (!studySiteStudyVersion.getStudySite().canEnroll(studySiteStudyVersion.getStudyVersion() , consentSignedDate)){
			map.put("cannotEnroll", "true");
			StudyVersion studyVersion = studySiteStudyVersion.getStudySite().getStudyVersion(consentSignedDate);
			map.put("studyVersion", studyVersion);
		}else{
			map.put(AjaxableUtils.getFreeTextModelName(), "");
			return new ModelAndView("",map);
		}
		return new ModelAndView(AjaxableUtils.getAjaxViewName(request), map);
    }
    
}
