package edu.duke.cabig.c3pr.service.passwordpolicy.validators;

import edu.duke.cabig.c3pr.domain.passwordpolicy.PasswordPolicy;
import edu.duke.cabig.c3pr.service.passwordpolicy.Credential;

public interface PasswordPolicyValidator {
    public boolean validate(PasswordPolicy policy, Credential credential)
                    throws ValidationException;
}
