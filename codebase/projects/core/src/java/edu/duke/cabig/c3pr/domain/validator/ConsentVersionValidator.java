/**
 * 
 */
package edu.duke.cabig.c3pr.domain.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import edu.duke.cabig.c3pr.domain.ConsentVersion;

/**
 * @author himanshu
 *
 */
public class ConsentVersionValidator implements Validator {

	public boolean supports(Class clazz) {
		 return ConsentVersion.class.isAssignableFrom(clazz);
	}

	public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "required", "required field");
	}


}
