package edu.duke.cabig.c3pr.web.registration.tabs;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.Errors;

import edu.duke.cabig.c3pr.constants.ICD9DiseaseSiteCodeDepth;
import edu.duke.cabig.c3pr.constants.RegistrationWorkFlowStatus;
import edu.duke.cabig.c3pr.dao.ICD9DiseaseSiteDao;
import edu.duke.cabig.c3pr.domain.ICD9DiseaseSite;
import edu.duke.cabig.c3pr.domain.StudyInvestigator;
import edu.duke.cabig.c3pr.domain.StudySiteStudyVersion;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.StudySubjectConsentVersion;
import edu.duke.cabig.c3pr.utils.Lov;
import edu.duke.cabig.c3pr.utils.StringUtils;
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
        super("Registration / Enrollment Details", "Registration / Enrollment Details", "registration/reg_registration_details");
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
    	if(studySubject.getRegWorkflowStatus() != RegistrationWorkFlowStatus.ON_STUDY && studySubject.getScheduledEpoch().getEpoch().getEnrollmentIndicator()){
    		studySubject.getScheduledEpoch().setStartDate(studySubject.getStartDate());
    	}
    	
    	// set the scheduled epoch start date to registration start date for first time enrollment
    	if(command.getStudySubject().getScheduledEpoch().getEpoch().getEnrollmentIndicator() &&
    			command.getStudySubject().getRegWorkflowStatus() != RegistrationWorkFlowStatus.ON_STUDY){
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
        	if(StringUtils.equals(command.getStudySubject().getDiseaseHistory().getOtherPrimaryDiseaseSiteCode(), "(Begin typing here for suggestion)")){
        		command.getStudySubject().getDiseaseHistory().setOtherPrimaryDiseaseSiteCode("");
        	}
        }
        StudySiteStudyVersion studySiteStudyVersion = ((StudySubjectWrapper)command).getStudySubject().getStudySubjectStudyVersion().getStudySiteStudyVersion();
    }
    
    
    @Override
    public void validate(StudySubjectWrapper command, Errors errors) {
	    	Date date = command.getStudySubject().getStartDate();
	    	if(date !=null){
				if(date.after(new Date())){
		    		errors.reject("tempProperty", "Registration date cannot be a future date");
		    	}
	    	}
	    	for(StudySubjectConsentVersion studySubjectConsentVersion : command.getStudySubject().getStudySubjectStudyVersion().getStudySubjectConsentVersions()){
				if (studySubjectConsentVersion
						.getInformedConsentSignedDate() != null) {
					if(date !=null){
						if (date.before(studySubjectConsentVersion.getInformedConsentSignedDate())) {
							errors
									.reject("studySubject.startDate",
											"Registration date cannot be prior to informed consent signed date");
						}
					}
				}
			}
    	
    }
    
}
