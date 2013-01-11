/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.service.passwordpolicy.validators;

import org.springframework.beans.factory.annotation.Required;

import edu.duke.cabig.c3pr.domain.User;
import edu.duke.cabig.c3pr.domain.passwordpolicy.LoginPolicy;
import edu.duke.cabig.c3pr.domain.passwordpolicy.PasswordPolicy;
import edu.duke.cabig.c3pr.domain.repository.CSMUserRepository;

public class LoginPolicyValidator implements PasswordPolicyValidator {

    private CSMUserRepository csmUserRepository;

    public boolean validate(PasswordPolicy policy, User user, String password)
            throws ValidationException {
        LoginPolicy loginPolicy = policy.getLoginPolicy();

        return validateAllowedFailedLoginAttempts(loginPolicy, user)
                && validateLockOutDuration(loginPolicy, user)
                && validateMaxPasswordAge(loginPolicy, user);
    }

    private boolean validateAllowedFailedLoginAttempts(LoginPolicy policy, User user)
            throws ValidationException {
        if (csmUserRepository.getUserByName(user.getLoginId()).getFailedLoginAttempts() > policy
                .getAllowedFailedLoginAttempts()) {
            throw new ValidationException("Too many failed logins.");
        }
        return true;
    }

    private boolean validateLockOutDuration(LoginPolicy policy, User user)
            throws ValidationException {
        // TODO
        return true;
    }

    private boolean validateMaxPasswordAge(LoginPolicy policy, User user)
            throws ValidationException {
        if (csmUserRepository.getUserByName(user.getLoginId()).getPasswordAge() > policy
                .getMaxPasswordAge()) {
            throw new ValidationException("Password is too old.");
        }
        return true;
    }

    @Required
    public void setCsmUserRepository(final CSMUserRepository csmUserRepository) {
        this.csmUserRepository = csmUserRepository;
    }


}
