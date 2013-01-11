/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.service.passwordpolicy.validators;

import java.sql.Timestamp;
import java.util.Calendar;

import org.acegisecurity.AuthenticationException;
import org.acegisecurity.CredentialsExpiredException;
import org.acegisecurity.DisabledException;
import org.acegisecurity.LockedException;

import edu.duke.cabig.c3pr.AbstractTestCase;
import edu.duke.cabig.c3pr.domain.LocalPersonUser;
import edu.duke.cabig.c3pr.domain.User;
import edu.duke.cabig.c3pr.domain.passwordpolicy.LoginPolicy;
import edu.duke.cabig.c3pr.domain.passwordpolicy.PasswordPolicy;

public class LoginPolicyValidatorTest extends AbstractTestCase {
	
	private LoginPolicyValidator loginPolicyValidator;
	private PasswordPolicy passwordPolicy;
	private LoginPolicy loginPolicy;
	private User user;
	private String userName;    
	private String password;
	
	protected void setUp() throws Exception {
		super.setUp();
		userName = "xyz";
		password = "Abcdef1!";
		user = new LocalPersonUser();
		user.setLoginId(userName);
		loginPolicyValidator = new LoginPolicyValidator();
		loginPolicy = new LoginPolicy();
		loginPolicy.setAllowedFailedLoginAttempts(3); // 3 attempts
		loginPolicy.setMaxPasswordAge(180800); // 2 days
		loginPolicy.setLockOutDuration(180); // 3 minutes
		passwordPolicy = new PasswordPolicy();
		passwordPolicy.setLoginPolicy(loginPolicy);
	}	
	
	public void testForFailedLoginAttempts_CheckingSuccess_1() {		
		user.setFailedLoginAttempts(1);
		replayMocks();
		try {
			boolean response = loginPolicyValidator.validateAllowedFailedLoginAttempts(loginPolicy, user);
			verifyMocks();
			assertTrue(response);
			
		} catch (DisabledException e) {
			fail("Not expecting ValidationException to be thrown");
		}
	}
	
	public void testForFailedLoginAttempts_CheckingFailure_3() {		
		user.setFailedLoginAttempts(3);
		replayMocks();		
		try {
			loginPolicyValidator.validateAllowedFailedLoginAttempts(loginPolicy, user);
			verifyMocks();
			fail("Testcase Failed: AllowedFailedLoginAttemps limit reached but exception was not thrown");
		} catch (DisabledException e) {
			/*Good*/
			assertEquals("Too many failed login attempts",e.getMessage().substring(0, 30));
		}
	}
	
	
	public void testForFailedLoginAttempts_CheckingFailure_4() {		
		user.setFailedLoginAttempts(4);
		replayMocks();
		try {
			loginPolicyValidator.validateAllowedFailedLoginAttempts(loginPolicy, user);
			verifyMocks();
			fail("Testcase Failed: AllowedFailedLoginAttemps limit reached but exception was not thrown");
		} catch (DisabledException e) {
			/*Good*/
			assertEquals("Too many failed login attempts",e.getMessage().substring(0, 30));
		}
	}	

	public void testForMaxPasswordAge_CheckingSuccess1() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 0);// last set today
		user.setPasswordLastSet(new Timestamp(cal.getTime().getTime()));
		replayMocks();
		try {
			boolean response = loginPolicyValidator.validateMaxPasswordAge(loginPolicy, user); 
			verifyMocks();
			assertTrue(response);
		} catch (CredentialsExpiredException e) {
			fail("Not expecting ValidationException to be thrown");
		}
	}	
	
	public void testForMaxPasswordAge_CheckingSuccess2() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -2);// last set 2 days ago
		user.setPasswordLastSet(new Timestamp(cal.getTime().getTime()));
		replayMocks();
		try {
			boolean response = loginPolicyValidator.validateMaxPasswordAge(loginPolicy, user); 
			verifyMocks();
			assertTrue(response);
		} catch (CredentialsExpiredException e) {
			fail("Not expecting ValidationException to be thrown");
		}		
	}
	
	public void testForMaxPasswordAge_CheckingFailure() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -3);//  last set 3 days ago
		user.setPasswordLastSet(new Timestamp(cal.getTime().getTime()));// last set 3days ago
		replayMocks();
		try {
			loginPolicyValidator.validateMaxPasswordAge(loginPolicy, user);
			verifyMocks();
			fail("Testcase Failed: MaxPasswordAge limit reached but exception was not thrown.");
		} catch (CredentialsExpiredException e) {
			/*Good*/
			assertEquals("Password is too old.",e.getMessage());
		}
	}
	
	public void testForLockOutDuration_CheckingSuccess() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, -4); // last attempt made 4 minute ago
		user.setLastFailedLoginAttemptTime(cal.getTime());
		replayMocks();
		try {
			boolean response = loginPolicyValidator.validateLockOutDuration(loginPolicy, user);
			verifyMocks();
			assertTrue(response);
		} catch (LockedException e) {
			fail("Not expecting ValidationException to be thrown");
		}
	}
	
	public void testForLockOutDuration_CheckingFailure1() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, -1); // last attempt made 1 minute ago
		user.setFailedLoginAttempts(-1);
		user.setLastFailedLoginAttemptTime(cal.getTime());
		replayMocks();
		try {
			loginPolicyValidator.validateLockOutDuration(loginPolicy, user);
			fail("Testcase Failed: LockOutDuration limit has not been reached but exception was not thrown.");
		} catch (LockedException e) {
			/*Good*/
			assertEquals("Account is locked out",e.getMessage().subSequence(0, 21));
		}
	}	
	
	public void testValidateMethod_CheckingSuccess() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, -6);  // last attempt made 4 minute ago
		user.setLastFailedLoginAttemptTime(cal.getTime());
		user.setFailedLoginAttempts(1); // 1 failed attempt
		Calendar cal1 = Calendar.getInstance();
		cal1.add(Calendar.DATE, -1);// last set 1 day ago
		user.setPasswordLastSet(new Timestamp(cal1.getTime().getTime()));
		replayMocks();
		try {
			boolean response = loginPolicyValidator.validate(passwordPolicy,user,password);
			assertTrue(response);
		}catch (AuthenticationException e) {
			fail("Not expecting ValidationException to be thrown");
		}
	}	
	
	public void testValidateMethod_CheckingFailure1() {
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, -4);  // last attempt made 4 minute ago
		user.setLastFailedLoginAttemptTime(cal.getTime());
		user.setFailedLoginAttempts(1); // 1 failed attempt
		Calendar cal1 = Calendar.getInstance();
		cal1.add(Calendar.DATE, -3);// last set 3 days ago
		user.setPasswordLastSet(new Timestamp(cal1.getTime().getTime()));
		replayMocks();
		try {
			loginPolicyValidator.validate(passwordPolicy, user, password);
			fail("Testcase Failed: Password is too old but exception was not thrown.");
		} catch (AuthenticationException e) {
			/*Good*/
			assertEquals("Password is too old.",e.getMessage());
		}
	}	
	
	public void testValidateMethod_CheckingFailure2() {
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, -4);  // last attempt made 4 minute ago
		user.setLastFailedLoginAttemptTime(cal.getTime());
		user.setFailedLoginAttempts(4); // 4 failed attempts
		Calendar cal1 = Calendar.getInstance();
		cal1.add(Calendar.DATE, -1);// last set 1 day ago
		user.setPasswordLastSet(new Timestamp(cal1.getTime().getTime()));
		replayMocks();
		try {
			loginPolicyValidator.validate(passwordPolicy, user, password);
			fail("Testcase Failed: Too many failed logins but exception was not thrown.");
		} catch (AuthenticationException e) {
			/*Good*/
			assertEquals("Too many failed login attempts",e.getMessage().substring(0, 30));
		}
	}
}
