package edu.duke.cabig.c3pr.domain.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import edu.duke.cabig.c3pr.domain.StudySite;

public class StudySiteValidator implements Validator{

	public boolean supports(Class clazz) {
		return StudySite.class.isAssignableFrom(clazz);
	}

	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "irbApprovalDate", "required",
			"required field");
		ValidationUtils.rejectIfEmpty(errors, "site", "required", "required field");
		ValidationUtils.rejectIfEmpty(errors, "startDate", "required", "required field");
		ValidationUtils.rejectIfEmpty(errors, "endDate", "required", "required field");
		ValidationUtils.rejectIfEmpty(errors, "irbApprovalDate", "required", "required field");		
		ValidationUtils.rejectIfEmpty(errors, "statusCode", "required", "required field");
		ValidationUtils.rejectIfEmpty(errors, "roleCode", "required", "required field");		
	}	
}
