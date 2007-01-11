package edu.duke.cabig.c3pr.domain.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import edu.duke.cabig.c3pr.domain.Epoch;

public class EpochValidator implements Validator {
	ArmValidator armValidator;
	
	public boolean supports(Class clazz) {
		return Epoch.class.isAssignableFrom(clazz);
	}

	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "required",
			"required field");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "required",
			"required field");
	}
	
	//TODO add arm validator logic
	
	public ArmValidator getArmValidator() {
		return armValidator;
	}

	public void setArmValidator(ArmValidator armValidator) {
		this.armValidator = armValidator;
	}		
}
