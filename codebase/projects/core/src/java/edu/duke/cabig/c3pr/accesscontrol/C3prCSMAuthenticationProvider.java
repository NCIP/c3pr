/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 *   Distributed under the OSI-approved BSD 3-Clause License.
 *   See https://github.com/NCIP/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.accesscontrol;

import java.util.Date;

import org.acegisecurity.AccountExpiredException;
import org.acegisecurity.AuthenticationException;
import org.acegisecurity.BadCredentialsException;
import org.acegisecurity.CredentialsExpiredException;
import org.acegisecurity.DisabledException;
import org.acegisecurity.LockedException;
import org.acegisecurity.providers.UsernamePasswordAuthenticationToken;
import org.acegisecurity.userdetails.UserDetails;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import edu.duke.cabig.c3pr.dao.PersonUserDao;
import edu.duke.cabig.c3pr.dao.UserDao;
import edu.duke.cabig.c3pr.domain.PersonUser;
import edu.duke.cabig.c3pr.domain.User;
import edu.duke.cabig.c3pr.domain.passwordpolicy.PasswordPolicy;
import edu.duke.cabig.c3pr.service.passwordpolicy.PasswordPolicyService;
import edu.duke.cabig.c3pr.service.passwordpolicy.validators.LoginPolicyValidator;
import gov.nih.nci.cabig.ctms.acegi.csm.authentication.CSMAuthenticationProvider;
import gov.nih.nci.security.UserProvisioningManager;

public class C3prCSMAuthenticationProvider extends CSMAuthenticationProvider{
	
	private static final Log logger = LogFactory.getLog(C3prCSMAuthenticationProvider.class);	
	private PasswordPolicyService passwordPolicyService;
	private PersonUserDao personUserDao;
	private UserDao userDao;
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	private UserProvisioningManager userProvisioningManager;
	
	/**
	 * This method will do Login policy validations and authentication checks.
	 */
	@Override
    @Transactional
	protected void additionalAuthenticationChecks(UserDetails user, 
												UsernamePasswordAuthenticationToken token) throws AccountExpiredException{
		
		PasswordPolicy passwordPolicy = passwordPolicyService.getPasswordPolicy();
		LoginPolicyValidator loginPolicyValidator = new LoginPolicyValidator();
		logger.debug((new StringBuilder()).append("Authenticating ").append(user.getUsername()).append("...").toString());
		if(!user.isAccountNonExpired()){
			throw new AccountExpiredException((new StringBuilder()).append("Error authenticating : User is InActive").toString());
		}
		
		gov.nih.nci.security.authorization.domainobjects.User csmUser = userProvisioningManager.getUser(user.getUsername());
		User personUser = userDao.getByLoginId(csmUser.getUserId());
		
		try {
			if(personUser != null){
				if(personUser.getSecondsPastLastFailedLoginAttempt() > passwordPolicy.getLoginPolicy().getLockOutDuration()) {
					if(passwordPolicy.getLoginPolicy().getAllowedLoginTime() <= personUser.getSecondsPastLastFailedLoginAttempt()) {
						personUser.setFailedLoginAttempts(0);
						personUser.setLastFailedLoginAttemptTime(null);
					}
				}
				loginPolicyValidator.validate(passwordPolicy, personUser, null);
				if(personUser.getFailedLoginAttempts()==-1)	personUser.setFailedLoginAttempts(0);
				if(passwordPolicy.getLoginPolicy().getAllowedLoginTime() <= personUser.getSecondsPastLastFailedLoginAttempt())	personUser.setFailedLoginAttempts(0);	
			}
			
			super.additionalAuthenticationChecks(user, token);
			
			if(personUser!=null){
				// If the user passes the checks and validations, then do the following
				personUser.setFailedLoginAttempts(0);
				personUser.setLastFailedLoginAttemptTime(null);
			}
		}catch (DisabledException attemptsEx) {
			// This exception is thrown when too many failed login attempts occur.
			personUser.setLastFailedLoginAttemptTime(new Date());
			personUser.setFailedLoginAttempts(-1);
			throw attemptsEx;
		}catch (LockedException lockEx) {
			// This exception is thrown when user tries to login while the account is locked.
			throw lockEx;
		}catch (CredentialsExpiredException oldEx) {
			// This exception is thrown when the password is too old.
			personUser.setFailedLoginAttempts(0);
			personUser.setLastFailedLoginAttemptTime(null);
			throw oldEx;
		}catch (AuthenticationException authEx) {
			// This exception is thrown when invalid credentials are used to login.
			if(personUser!=null) {
				personUser.setFailedLoginAttempts(personUser.getFailedLoginAttempts()+1);
				if(personUser.getFailedLoginAttempts()==1)	personUser.setLastFailedLoginAttemptTime(new Date());
			}
			throw new BadCredentialsException("Invalid login credentials");
		}finally {
			// save the user properties.
			if(personUser!=null) {
				personUserDao.saveOrUpdatePersonUser((PersonUser)personUser);
			}
		}  
	}
	
	public PasswordPolicyService getPasswordPolicyService() {
		return passwordPolicyService;
	}

	public void setPasswordPolicyService(PasswordPolicyService passwordPolicyService) {
		this.passwordPolicyService = passwordPolicyService;
	}

	public void setPersonUserDao(PersonUserDao personUserDao) {
		this.personUserDao = personUserDao;
	}

	public void setUserProvisioningManager(
			UserProvisioningManager userProvisioningManager) {
		this.userProvisioningManager = userProvisioningManager;
	}
	
	
}
