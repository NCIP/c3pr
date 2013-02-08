/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.web.registration.tabs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.Errors;

import edu.duke.cabig.c3pr.constants.ICD9DiseaseSiteCodeDepth;
import edu.duke.cabig.c3pr.constants.RegistrationWorkFlowStatus;
import edu.duke.cabig.c3pr.dao.ICD9DiseaseSiteDao;
import edu.duke.cabig.c3pr.domain.ICD9DiseaseSite;
import edu.duke.cabig.c3pr.domain.ScheduledEpoch;
import edu.duke.cabig.c3pr.domain.StudyInvestigator;
import edu.duke.cabig.c3pr.domain.StudySiteStudyVersion;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.StudySubjectConsentVersion;
import edu.duke.cabig.c3pr.domain.StudyVersion;
import edu.duke.cabig.c3pr.utils.DateUtil;
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
    		Date epochStartDate = command.getStudySubject().getScheduledEpoch().getStartDate();
    		if(epochStartDate!=null){
    			// validate only non embedded companion registrations. 
    			//TODO validate embedded companion registrations based on the parent study
    			if (!command.getStudySubject().getStudySite().getStudy().getIsEmbeddedCompanionStudy()){
    				StudySiteStudyVersion studySiteStudyVersion = ((StudySubjectWrapper)command).getStudySubject().getStudySubjectStudyVersion().getStudySiteStudyVersion();
    				StudyVersion studyVersion = studySiteStudyVersion.getStudySite().getActiveStudyVersion(epochStartDate);
    				if(studyVersion== null || !studySiteStudyVersion.getStudyVersion().equals(studyVersion)){
        				errors.reject("tempProperty", "Scheduled epoch start date does not correspond to the selected study version");
        			}
    			}
    			
    		}
    		
    		List<Date> offEpochDates = new ArrayList<Date>();
    		for(ScheduledEpoch schEpoch:command.getStudySubject().getScheduledEpochs()){
    			if(schEpoch.getOffEpochDate()!=null){
    				offEpochDates.add(schEpoch.getOffEpochDate());
    			}
    		}
    		
    		if(!offEpochDates.isEmpty()){
    			Collections.sort(offEpochDates);   		
        		Collections.reverse(offEpochDates);
        		if(epochStartDate!=null && epochStartDate.before(offEpochDates.get(0))){
        			errors.reject("tempProperty", "Scheduled epoch start date cannot be prior to " + DateUtil.formatDate(offEpochDates.get(0), "MM/dd/yyyy") + ","
        				+ " which is off epoch date of previous epoch");
        		}
    		}
    		
	    	Date registrationStartDate = command.getStudySubject().getStartDate();
	    	if(registrationStartDate !=null){
				if(registrationStartDate.after(new Date())){
		    		errors.reject("tempProperty", "Registration date cannot be a future date");
		    	}
	    	}
	    	for(StudySubjectConsentVersion studySubjectConsentVersion : command.getStudySubject().getStudySubjectStudyVersion().getStudySubjectConsentVersions()){
				if (studySubjectConsentVersion
						.getInformedConsentSignedDate() != null) {
					if(registrationStartDate !=null){
						if (registrationStartDate.before(studySubjectConsentVersion.getInformedConsentSignedDate())) {
							errors
									.reject("studySubject.startDate",
											"Registration date cannot be prior to informed consent signed date " + studySubjectConsentVersion.getInformedConsentSignedDateStr());
						}
					}
				}
			}
    	
    }
    
}
