/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.service.passwordpolicy.validators;

import org.acegisecurity.AuthenticationException;
import org.acegisecurity.CredentialsExpiredException;
import org.acegisecurity.DisabledException;
import org.acegisecurity.LockedException;

import edu.duke.cabig.c3pr.domain.User;
import edu.duke.cabig.c3pr.domain.passwordpolicy.LoginPolicy;
import edu.duke.cabig.c3pr.domain.passwordpolicy.PasswordPolicy;

public class LoginPolicyValidator implements PasswordPolicyValidator {

    public boolean validate(PasswordPolicy policy, User user, String password)
            throws AuthenticationException {
        LoginPolicy loginPolicy = policy.getLoginPolicy();

        return validateAllowedFailedLoginAttempts(loginPolicy, user)
                && validateLockOutDuration(loginPolicy, user)
                && validateMaxPasswordAge(loginPolicy, user);
    }

    protected boolean validateAllowedFailedLoginAttempts(LoginPolicy policy, User user)
            throws DisabledException {
        if (user.getFailedLoginAttempts() >= policy.getAllowedFailedLoginAttempts()-1) {
            throw new DisabledException("Too many failed login attempts resulted in account lock out for "+policy.getLockOutDuration()+" seconds.");
        }
        return true;
    }

    protected boolean validateLockOutDuration(LoginPolicy policy, User user)
            throws LockedException {
    	if(user.getSecondsPastLastFailedLoginAttempt() == -1) 
    		return true;
    	else if(policy.getLockOutDuration() > user.getSecondsPastLastFailedLoginAttempt() && user.getFailedLoginAttempts()==-1) {
    		long timeLeft = policy.getLockOutDuration() - user.getSecondsPastLastFailedLoginAttempt();
    		throw new LockedException("Account is locked out for "+timeLeft+" more second(s).");
    		}
    	return true;
    }

    protected boolean validateMaxPasswordAge(LoginPolicy policy, User user)
            throws CredentialsExpiredException {
        if (user.getPasswordAge() > policy.getMaxPasswordAge()) {
            throw new CredentialsExpiredException("Password is too old.");
        }
        return true;
    }
}
