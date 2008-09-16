package edu.duke.cabig.c3pr.service.passwordpolicy.validators;

import org.springframework.beans.factory.annotation.Required;

import edu.duke.cabig.c3pr.domain.passwordpolicy.LoginPolicy;
import edu.duke.cabig.c3pr.domain.passwordpolicy.PasswordPolicy;
import edu.duke.cabig.c3pr.domain.repository.CSMUserRepository;
import edu.duke.cabig.c3pr.service.passwordpolicy.Credential;

public class LoginPolicyValidator implements PasswordPolicyValidator {

    private CSMUserRepository csmUserRepository;

    public boolean validate(PasswordPolicy policy, Credential credential)
            throws ValidationException {
        LoginPolicy loginPolicy = policy.getLoginPolicy();

        return validateAllowedFailedLoginAttempts(loginPolicy, credential)
                && validateLockOutDuration(loginPolicy, credential)
                && validateMaxPasswordAge(loginPolicy, credential);
    }

    private boolean validateAllowedFailedLoginAttempts(LoginPolicy policy, Credential credential)
            throws ValidationException {
        if (csmUserRepository.getUserByName(credential.getUserName()).getFailedLoginAttempts() > policy
                .getAllowedFailedLoginAttempts()) {
            throw new ValidationException("Too many failed logins.");
        }
        return true;
    }

    private boolean validateLockOutDuration(LoginPolicy policy, Credential credential)
            throws ValidationException {
        // TODO
        return true;
    }

    private boolean validateMaxPasswordAge(LoginPolicy policy, Credential credential)
            throws ValidationException {
        if (csmUserRepository.getUserByName(credential.getUserName()).getPasswordAge() > policy
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
