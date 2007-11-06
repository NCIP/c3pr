package edu.duke.cabig.c3pr.domain.validator;

import java.util.List;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import edu.duke.cabig.c3pr.dao.ParticipantDao;
import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.Participant;

public class ParticipantValidator implements Validator {
	
	private ParticipantDao participantDao;

	public boolean supports(Class clazz) {
		return Participant.class.equals(clazz);
	}

	public void validate(Object arg0, Errors errors) {
		validateParticipantDetails(arg0, errors);
		validateParticipantAddress(arg0, errors);
	}

	public void validateParticipantDetails(Object arg0, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "firstName", "required",
				"required field");
		ValidationUtils.rejectIfEmpty(errors, "lastName", "required",
				"required field");
		ValidationUtils.rejectIfEmpty(errors, "birthDate", "required",
				"required field");
	}

	public void validateParticipantAddress(Object arg0, Errors errors) {

		ValidationUtils.rejectIfEmpty(errors, "address.streetAddress",
				"required", "required field");
		ValidationUtils.rejectIfEmpty(errors, "address.city", "required",
				"required field");
		ValidationUtils.rejectIfEmpty(errors, "address.stateCode", "required",
				"required field");
		ValidationUtils.rejectIfEmpty(errors, "address.countryCode",
				"required", "required field");

	}
	
	public void validateParticipantMRN(Object target, Errors errors) {
		
		Participant participant = (Participant) target;
		OrganizationAssignedIdentifier mrn = participant.getMRN();
		
		
		if ((mrn!=null)&&(mrn.getHealthcareSite()!=null)){
			List<OrganizationAssignedIdentifier> participantsWithMRN = participantDao.getSubjectIdentifiersWithMRN(mrn.getValue(),mrn.getHealthcareSite());
			if (participantsWithMRN.size() > 0){
				if ((participant.getId()==null)||(participantsWithMRN.size()>1)){
				errors.reject("tempProperty","Participant with this MRN already exists for the same Organization");
				}
			}
		}
		
	}

	public ParticipantDao getParticipantDao() {
		return participantDao;
	}

	public void setParticipantDao(ParticipantDao participantDao) {
		this.participantDao = participantDao;
	}

}
