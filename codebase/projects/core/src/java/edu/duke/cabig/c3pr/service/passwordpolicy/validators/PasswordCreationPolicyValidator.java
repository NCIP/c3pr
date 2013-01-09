/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.service.passwordpolicy.validators;

import edu.duke.cabig.c3pr.domain.User;
import edu.duke.cabig.c3pr.domain.passwordpolicy.PasswordCreationPolicy;
import edu.duke.cabig.c3pr.domain.passwordpolicy.PasswordPolicy;
import edu.duke.cabig.c3pr.domain.repository.CSMUserRepository;

public class PasswordCreationPolicyValidator implements PasswordPolicyValidator {

    private CombinationValidator combinationValidator;
    private CSMUserRepository csmUserRepository;

    public PasswordCreationPolicyValidator() {
        combinationValidator = new CombinationValidator();
    }

    public boolean validate(PasswordPolicy policy, User user, String password)
            throws ValidationException {
        PasswordCreationPolicy passwordCreationPolicy = policy.getPasswordCreationPolicy();

        return validateMinPasswordAge(passwordCreationPolicy, user)
                && validatePasswordHistory(passwordCreationPolicy, user, password)
                && validateMinPasswordLength(passwordCreationPolicy, password)
                && combinationValidator.validate(policy, user, password);
    }

    private boolean validateMinPasswordAge(PasswordCreationPolicy policy, User user)
            throws ValidationException {
        if (user.getPasswordAge() < policy
                .getMinPasswordAge()) {
            throw new ValidationException("Password was changed too recently.");
        }
        return true;
    }

    private boolean validatePasswordHistory(PasswordCreationPolicy policy, User user, String password)
            throws ValidationException {
        if (csmUserRepository.userHasPassword(user, password)
                || csmUserRepository.userHadPassword(user, password)) {
            throw new ValidationException("Must choose a password that has not been used recently.");
        }
        return true;
    }

    private boolean validateMinPasswordLength(PasswordCreationPolicy policy, String password)
            throws ValidationException {
        if (password.length() >= policy.getMinPasswordLength()) return true;
        throw new ValidationException("The minimum length of password must be at least "
                + policy.getMinPasswordLength() + " characters");
    }

    public void setCsmUserRepository(final CSMUserRepository csmUserRepository) {
        this.csmUserRepository = csmUserRepository;
        this.combinationValidator.setCsmUserRepository(csmUserRepository);
    }
}
