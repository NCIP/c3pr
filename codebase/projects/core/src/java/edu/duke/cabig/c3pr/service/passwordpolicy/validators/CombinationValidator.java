/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.service.passwordpolicy.validators;

import edu.duke.cabig.c3pr.domain.User;
import edu.duke.cabig.c3pr.domain.passwordpolicy.CombinationPolicy;
import edu.duke.cabig.c3pr.domain.passwordpolicy.PasswordPolicy;
import edu.duke.cabig.c3pr.domain.repository.CSMUserRepository;

public class CombinationValidator implements PasswordPolicyValidator {
	
	private CSMUserRepository csmUserRepository;
	
	public void setCsmUserRepository(CSMUserRepository csmUserRepository) {
		this.csmUserRepository = csmUserRepository;
	}
	
	public boolean validate(PasswordPolicy policy, User user, String password)
                    throws ValidationException {
        CombinationPolicy combinationPolicy = policy.getPasswordCreationPolicy()
                        .getCombinationPolicy();

        return validateLowerCaseAlphabet(combinationPolicy, password)
                        && validateUpperCaseAlphabet(combinationPolicy, password)
                        && validateNonAlphaNumeric(combinationPolicy, password)
                        && validateBaseTenDigit(combinationPolicy, password)
                        && validateMaxSubstringLength(combinationPolicy, csmUserRepository.getUsernameById(user.getLoginId()), password);
    }

    private boolean validateLowerCaseAlphabet(CombinationPolicy policy, String password)
                    throws ValidationException {
        if (policy.isLowerCaseAlphabetRequired()
                        && !password.matches(".*[\\p{javaLowerCase}].*")) {
            throw new ValidationException("The password should have at least one lower case letter");
        }
        return true;
    }

    private boolean validateUpperCaseAlphabet(CombinationPolicy policy, String password)
                    throws ValidationException {
        if (policy.isUpperCaseAlphabetRequired()
                        && !password.matches(".*[\\p{javaUpperCase}].*")) {
            throw new ValidationException("The password should have at least one upper case letter");
        }
        return true;
    }

    private boolean validateNonAlphaNumeric(CombinationPolicy policy, String password)
                    throws ValidationException {
        if (policy.isNonAlphaNumericRequired() && password.matches("[\\p{Alnum}]+")) {
            throw new ValidationException("The password should have at least one special charcter");
        }
        return true;
    }

    private boolean validateBaseTenDigit(CombinationPolicy policy, String password)
                    throws ValidationException {
        if (policy.isBaseTenDigitRequired()
                        && !password.matches(".*[\\p{Digit}].*")) {
            throw new ValidationException(
                            "The password should have at least one numeral digit{0-9}");
        }
        return true;
    }

    private boolean validateMaxSubstringLength(CombinationPolicy policy, String userName, String password)
                    throws ValidationException {
        int substringLength = policy.getMaxSubstringLength() + 1;
        for (int i = 0; i < userName.length() - substringLength; i++) {
            try {
                if (password.contains(userName.substring(i, i + substringLength))) {
                    throw new ValidationException("The password should not contain a substring of "
                                    + substringLength + " letters from the username");
                }
            } catch (IndexOutOfBoundsException e) {
                return true;
            }
        }
        return true;
    }
}
