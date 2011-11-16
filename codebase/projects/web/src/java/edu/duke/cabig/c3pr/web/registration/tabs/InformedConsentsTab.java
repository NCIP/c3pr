package edu.duke.cabig.c3pr.web.registration.tabs;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.Errors;
import org.springframework.web.util.WebUtils;

import edu.duke.cabig.c3pr.constants.ConsentRequired;
import edu.duke.cabig.c3pr.constants.RegistrationWorkFlowStatus;
import edu.duke.cabig.c3pr.domain.StudySiteStudyVersion;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.StudySubjectConsentVersion;
import edu.duke.cabig.c3pr.domain.StudyVersion;
import edu.duke.cabig.c3pr.exception.C3PRCodedRuntimeException;
import edu.duke.cabig.c3pr.utils.Lov;
import edu.duke.cabig.c3pr.web.registration.StudySubjectWrapper;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Jun 15, 2007 Time: 3:30:05 PM To change this template
 * use File | Settings | File Templates.
 */
public class InformedConsentsTab extends RegistrationTab<StudySubjectWrapper> {
	

	public InformedConsentsTab() {
        super("Informed Consents", "Informed Consents", "registration/reg_registration_informed_consent");
    }
    
    @Override
    public Map referenceData(HttpServletRequest request,
    		StudySubjectWrapper command) {
    	Map refdata=super.referenceData(request, command);
    	addConfigMapToRefdata(refdata, "yesNo");
    	Map<String, List<Lov>> configMap = configurationProperty.getMap();
    	return refdata;
    }
    
    @Override
    public void postProcess(HttpServletRequest request, StudySubjectWrapper command, Errors errors) {
    	 
    	StudySubjectWrapper wrapper = (StudySubjectWrapper) command ;
    	StudySubject studySubject = wrapper.getStudySubject();
    	 if(request.getSession().getAttribute("studyVersion") !=null ){
           	request.getSession().removeAttribute("studyVersion");
           }
       if(request.getSession().getAttribute("canEnroll") !=null ){
       	request.getSession().removeAttribute("canEnroll");
       }
    	if(studySubject.getRegWorkflowStatus() != RegistrationWorkFlowStatus.ON_STUDY && studySubject.getScheduledEpoch().getEpoch().getEnrollmentIndicator()){
    		studySubject.getScheduledEpoch().setStartDate(studySubject.getStartDate());
    	}
    	if(WebUtils.hasSubmitParameter(request, "updateStudyVersion") && request.getParameter("updateStudyVersion").equals("true")){
    		Date consentSignedDate = null;
    		try {
    			consentSignedDate = new SimpleDateFormat("MM/dd/yyyy").parse(request.getParameter("consentSignedDate"));
    			request.setAttribute("consentSignedDate", request.getParameter("consentSignedDate"));
			} catch (ParseException e) {
				throw new RuntimeException("Invalid Submit. Consent signed Date is invalid");
			}
			try{
				studySubject.changeStudyVersion(consentSignedDate);
			} catch(C3PRCodedRuntimeException ex){
				if(ex.getExceptionCode()==101){
					errors.reject("tempProperty","Unable to find an epoch with name " + studySubject.getScheduledEpoch().getEpoch().getName()+" in the study version");
				}
			}
    		getRegistrationControllerUtils().buildCommandObject(studySubject);
    		return;
    	}
    	
    	// set the scheduled epoch start date to registration start date for first time enrollment
    	if(command.getStudySubject().getScheduledEpoch().getEpoch().getEnrollmentIndicator() &&
    			command.getStudySubject().getRegWorkflowStatus() != RegistrationWorkFlowStatus.ON_STUDY){
    		command.getStudySubject().getScheduledEpoch().setStartDate(command.getStudySubject().getStartDate());
    	}
    		
        
        StudySiteStudyVersion studySiteStudyVersion = ((StudySubjectWrapper)command).getStudySubject().getStudySubjectStudyVersion().getStudySiteStudyVersion();
        
        for(StudySubjectConsentVersion studySubjectConsentVersion : command.getStudySubject().getStudySubjectStudyVersion().getStudySubjectConsentVersions()){
			if(studySubjectConsentVersion.getInformedConsentSignedDateStr()!=null && studySubjectConsentVersion.getInformedConsentSignedDateStr() != ""){
				if (!studySiteStudyVersion.getStudySite().canEnroll(studySiteStudyVersion.getStudyVersion() , studySubjectConsentVersion.getInformedConsentSignedDate())){
					request.getSession().setAttribute("canEnroll",false);
					StudyVersion studyVersion = studySiteStudyVersion.getStudySite().getActiveStudyVersion(studySubjectConsentVersion.getInformedConsentSignedDate());
					request.getSession().setAttribute("studyVersion",studyVersion);
					errors.reject("tempProperty","Consent:" +studySubjectConsentVersion.getConsent().getName()+ " signed date does not correspond to the selected study version");
					break;
				}
			}
		}
    }
    
    @Override
    public void validate(StudySubjectWrapper command, Errors errors) {
    	boolean consentedAtLeastOne = false;
	    	for(StudySubjectConsentVersion studySubjectConsentVersion : command.getStudySubject().getStudySubjectStudyVersion().
	    			getStudySubjectConsentVersions()){
				if (studySubjectConsentVersion
						.getInformedConsentSignedDate() != null) {
					if(studySubjectConsentVersion.getInformedConsentSignedDate().after(new Date())){
						errors.reject("tempProperty",  "Consent:" +studySubjectConsentVersion.getConsent().getName()+ " signed date cannot be a future date");
					}
					if(studySubjectConsentVersion.getConsentDeliveryDate()!=null && studySubjectConsentVersion.
							getInformedConsentSignedDate().before(studySubjectConsentVersion.getConsentDeliveryDate())){
						errors.reject("tempProperty",  "Consent:" +studySubjectConsentVersion.getConsent().getName()+ " signed date cannot be prior to the delivery date");
					}
				}
				if(studySubjectConsentVersion.getInformedConsentSignedDate() != null){
					consentedAtLeastOne = true;
				}
				
				if (studySubjectConsentVersion.getConsent().getMandatoryIndicator() && 
						studySubjectConsentVersion.getInformedConsentSignedDate() == null) {
						errors.reject("tempProperty", "Consent:" +studySubjectConsentVersion.getConsent().getName()+  " is mandatory");
				}
			}
	    	
	    	if(command.getStudySubject().getStudySite().getStudy().getConsentRequired() == ConsentRequired.ONE){
	    		if(!consentedAtLeastOne){
	    			errors.reject("tempProperty", "At least one consent should be signed");
	    		}
	    	}
    }
    
}
