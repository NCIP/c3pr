package edu.duke.cabig.c3pr.domain.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import edu.duke.cabig.c3pr.domain.Participant;

public class ParticipantValidator implements Validator {

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

}
