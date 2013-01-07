package edu.duke.cabig.c3pr.service.passwordpolicy.validators;

import edu.duke.cabig.c3pr.domain.User;
import edu.duke.cabig.c3pr.domain.passwordpolicy.PasswordPolicy;

public interface PasswordPolicyValidator {
    public boolean validate(PasswordPolicy policy, User user, String password)
                    throws ValidationException;
}
