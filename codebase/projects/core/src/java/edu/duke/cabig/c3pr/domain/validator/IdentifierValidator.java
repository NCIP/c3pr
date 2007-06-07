package edu.duke.cabig.c3pr.domain.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import edu.duke.cabig.c3pr.domain.Identifier;

public class IdentifierValidator implements Validator{

	public boolean supports(Class clazz) {
		return Identifier.class.isAssignableFrom(clazz);
	}
	
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "source", "required",	"required field");
		ValidationUtils.rejectIfEmpty(errors, "type", "required", "required field");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "value", "required", "required field");
	}	
}
