/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.domain.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import edu.duke.cabig.c3pr.domain.StudySite;

public class StudySiteValidator implements Validator {

    public boolean supports(Class clazz) {
        return StudySite.class.isAssignableFrom(clazz);
    }

    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "irbApprovalDate", "required",
                        "required field");
        ValidationUtils.rejectIfEmpty(errors, "study.site", "required", "required field");
        ValidationUtils.rejectIfEmpty(errors, "study.startDate", "required", "required field");
        ValidationUtils.rejectIfEmpty(errors, "study.endDate", "required", "required field");
        ValidationUtils.rejectIfEmpty(errors, "study.irbApprovalDate", "required", "required field");
        ValidationUtils.rejectIfEmpty(errors, "study.statusCode", "required", "required field");
        ValidationUtils.rejectIfEmpty(errors, "study.roleCode", "required", "required field");
    }
}
