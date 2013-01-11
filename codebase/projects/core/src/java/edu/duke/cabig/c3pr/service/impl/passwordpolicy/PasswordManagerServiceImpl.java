/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.service.impl.passwordpolicy;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.beans.factory.annotation.Required;

import edu.duke.cabig.c3pr.dao.UserDao;
import edu.duke.cabig.c3pr.domain.User;
import edu.duke.cabig.c3pr.domain.repository.CSMUserRepository;
import edu.duke.cabig.c3pr.exception.C3PRBaseException;
import edu.duke.cabig.c3pr.exception.C3PRBaseRuntimeException;
import edu.duke.cabig.c3pr.service.passwordpolicy.PasswordManagerService;
import edu.duke.cabig.c3pr.service.passwordpolicy.PasswordPolicyService;

/**
 * @author Jared Flatow
 */
public class PasswordManagerServiceImpl implements PasswordManagerService {

	private PasswordPolicyService passwordPolicyService;

    private CSMUserRepository csmUserRepository;
    
    private UserDao userDao;
    
    public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void addUserToken(String userName) {
    	User user = csmUserRepository.getUserByName(userName);
    	UserDao.addUserToken(user);
    	userDao.save(user);
    }

    public void setPassword(String userName, String password, String token) throws C3PRBaseException {
    	User user = csmUserRepository.getUserByName(userName);
    	
    	if(user == null){
    		throw new C3PRBaseRuntimeException("User with login Id :" + userName + " unknowon");
    	}
        validateToken(user, token);
        validateAndSetPassword(user, password);
    }

    private boolean validateToken(User user, String token) {
        if (user.getTokenTime().after(
                new Timestamp(new Date().getTime()
                        - passwordPolicyService.getPasswordPolicy()
                        .getTokenTimeout()))
                && token.equals(user.getToken())) return true;
        throw new C3PRBaseRuntimeException("Invalid token.");
    }

    private void validateAndSetPassword(User user, String password) throws C3PRBaseException{
		passwordPolicyService.validatePasswordAgainstCreationPolicy(user, password);
        csmUserRepository.userChangePassword(user, password, passwordPolicyService
                .getPasswordPolicy().getPasswordCreationPolicy().getPasswordHistorySize());
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
