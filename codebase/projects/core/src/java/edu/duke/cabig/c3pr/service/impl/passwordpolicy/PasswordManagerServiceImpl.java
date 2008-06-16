package edu.duke.cabig.c3pr.service.impl.passwordpolicy;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.beans.factory.annotation.Required;

import edu.duke.cabig.c3pr.domain.User;
import edu.duke.cabig.c3pr.domain.repository.CSMUserRepository;
import edu.duke.cabig.c3pr.exception.C3PRBaseException;
import edu.duke.cabig.c3pr.service.passwordpolicy.Credential;
import edu.duke.cabig.c3pr.service.passwordpolicy.PasswordManagerService;
import edu.duke.cabig.c3pr.service.passwordpolicy.PasswordPolicyService;

/**
 * @author Jared Flatow
 */
public class PasswordManagerServiceImpl implements PasswordManagerService {

    private PasswordPolicyService passwordPolicyService;

    private CSMUserRepository csmUserRepository;

    public String requestToken(String userName) throws C3PRBaseException {
        return csmUserRepository.userCreateToken(userName);
    }

    public void setPassword(String userName, String password, String token)
            throws C3PRBaseException {
        //validateToken(userName, token);
        validateAndSetPassword(userName, password);
    }

    private boolean validateToken(String userName, String token) throws C3PRBaseException {
        User user = csmUserRepository.getUserByName(userName);
        if (user.getTokenTime().after(new Timestamp(new Date().getTime()
                        - passwordPolicyService.getPasswordPolicy()
                        .getTokenTimeout()))
                && token.equals(user.getToken())) return true;
        throw new C3PRBaseException("Invalid token.");
    }

    private boolean validateAndSetPassword(String userName, String password)
            throws C3PRBaseException {
        passwordPolicyService.validatePasswordAgainstCreationPolicy(new Credential(userName,
                password));
        csmUserRepository.userChangePassword(userName, password, passwordPolicyService
                .getPasswordPolicy().getPasswordCreationPolicy().getPasswordHistorySize());
        return true;
    }

    @Required
    public void setPasswordPolicyService(PasswordPolicyService passwordPolicyService) {
        this.passwordPolicyService = passwordPolicyService;
    }

    @Required
    public void setCsmUserRepository(final CSMUserRepository csmUserRepository) {
        this.csmUserRepository = csmUserRepository;
    }
}
