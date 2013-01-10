/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.web.registration.tabs;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.Errors;

import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.StudySubjectConsentVersion;
import edu.duke.cabig.c3pr.domain.repository.StudySubjectRepository;
import edu.duke.cabig.c3pr.web.registration.StudySubjectWrapper;

public class ReConsentTab extends RegistrationTab<StudySubjectWrapper> {

	
	public ReConsentTab() {
        super("Details", "Details", "registration/reg_re_consent");
    }
	
	private StudySubjectRepository studySubjectRepository;
	
	public void setStudySubjectRepository(
			StudySubjectRepository studySubjectRepository) {
		this.studySubjectRepository = studySubjectRepository;
	}

	@Override
	public Map referenceData(HttpServletRequest request, StudySubjectWrapper command) {
		return super.referenceData();
	}
	
	@Override
	public void validate(StudySubjectWrapper command, Errors errors) {
    	for(StudySubjectConsentVersion studySubjectConsentVersion : command.getReConsentingStudySubjectConsentVersions()){
			if (studySubjectConsentVersion
					.getInformedConsentSignedDate() != null) {
				if(studySubjectConsentVersion.getInformedConsentSignedDate().after(new Date())){
					errors.reject("tempProperty",  "Consent:" +studySubjectConsentVersion.getConsent().getName()+ " signed date cannot be a future date");
				}
				if(studySubjectConsentVersion.getConsentDeliveryDate()!=null && studySubjectConsentVersion.
						getInformedConsentSignedDate().before(studySubjectConsentVersion.getConsentDeliveryDate())){
					errors.reject("tempProperty",  "Consent:" +studySubjectConsentVersion.getConsent().getName()+ " signed date cannot be prior to the delivery date");
				}
				if (!command.getStudySubject().getStudySite().canEnroll(command.getReConsentingVersion() , studySubjectConsentVersion.getInformedConsentSignedDate())){
					errors.reject("tempProperty","Consent:" +studySubjectConsentVersion.getConsent().getName()+ " signed date does not correspond to the selected study version");
					break;
				}
			}
			
			if (studySubjectConsentVersion.getConsent().getMandatoryIndicator() && 
					studySubjectConsentVersion.getInformedConsentSignedDate() == null) {
					errors.reject("tempProperty", "Consent:" +studySubjectConsentVersion.getConsent().getName()+  " is mandatory");
			}
		}
	}


	@Override
	public void postProcess(HttpServletRequest request, StudySubjectWrapper command, Errors errors) {
		if(!errors.hasErrors()){
			List<StudySubjectConsentVersion> consentedStudySubjectConsentVersions = new ArrayList<StudySubjectConsentVersion>();
			for(StudySubjectConsentVersion studySubjectConsentVersion : command.getReConsentingStudySubjectConsentVersions()){
				if (studySubjectConsentVersion
						.getInformedConsentSignedDate() != null) {
					consentedStudySubjectConsentVersions.add(studySubjectConsentVersion);
				}
			}
			
			StudySubject studySubject = studySubjectRepository.reConsent(command.getReConsentingVersion().getName(),
					consentedStudySubjectConsentVersions, command.getStudySubject().getSystemAssignedIdentifiers().
					get(0));
			command.setStudySubject(studySubject);
		}
	}

	
}
