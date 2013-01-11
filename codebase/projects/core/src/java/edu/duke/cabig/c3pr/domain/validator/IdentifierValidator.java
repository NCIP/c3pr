/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.domain.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import edu.duke.cabig.c3pr.constants.OrganizationIdentifierTypeEnum;
import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier;

public class IdentifierValidator implements Validator {

    public boolean supports(Class clazz) {
        return Identifier.class.isAssignableFrom(clazz);
    }

    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "source", "required", "required field");
        ValidationUtils.rejectIfEmpty(errors, "type", "required", "required field");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "value", "required", "required field");

        if (target instanceof OrganizationAssignedIdentifier) {
            OrganizationAssignedIdentifier id = (OrganizationAssignedIdentifier) target;
            if (id.getType().equals(OrganizationIdentifierTypeEnum.PROTOCOL_AUTHORITY_IDENTIFIER)) 
            	for (OrganizationAssignedIdentifier loadedId : id
                            .getHealthcareSite().getIdentifiers()) {
                if (loadedId.getValue().equals(id.getValue())) {
                    errors.rejectValue("value", "Duplicate Value for Identifier");
                }
            }
        }
    }
}
