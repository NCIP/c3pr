/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.service.passwordpolicy.validators;

import static org.easymock.EasyMock.expect;

import org.easymock.EasyMock;

import edu.duke.cabig.c3pr.AbstractTestCase;
import edu.duke.cabig.c3pr.domain.LocalPersonUser;
import edu.duke.cabig.c3pr.domain.User;
import edu.duke.cabig.c3pr.domain.passwordpolicy.CombinationPolicy;
import edu.duke.cabig.c3pr.domain.passwordpolicy.PasswordCreationPolicy;
import edu.duke.cabig.c3pr.domain.passwordpolicy.PasswordPolicy;
import edu.duke.cabig.c3pr.domain.repository.CSMUserRepository;

public class PasswordCreationPolicyValidatorTest extends AbstractTestCase {
	
	private PasswordCreationPolicyValidator passwordCreationPolicyValidator;    
	private PasswordCreationPolicy passwordCreationPolicy;    
	private PasswordPolicy passwordPolicy;   
	private CombinationPolicy combinationPolicy;
	private String userName;    
	private String password;    
	private User user;
	private CSMUserRepository csmUserRepository;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		csmUserRepository = registerMockFor(CSMUserRepository.class);
		
		userName = "wxyz1234";        
		password = "Password1!";  
		user =  new LocalPersonUser();
		user.setLoginId(userName);
		passwordPolicy = new PasswordPolicy();   
		combinationPolicy = new CombinationPolicy();
		passwordCreationPolicy = new PasswordCreationPolicy();				
		passwordCreationPolicyValidator = new PasswordCreationPolicyValidator(); 
		passwordCreationPolicyValidator.setCsmUserRepository(csmUserRepository);
		passwordPolicy.setPasswordCreationPolicy(passwordCreationPolicy);
		passwordCreationPolicy.setMinPasswordLength(7);
		passwordCreationPolicy.setMinPasswordAge(180);
		passwordCreationPolicy.setPasswordHistorySize(3);
		passwordCreationPolicy.setCombinationPolicy(combinationPolicy);
		combinationPolicy.setBaseTenDigitRequired(true);
		combinationPolicy.setLowerCaseAlphabetRequired(true);
		combinationPolicy.setMaxSubstringLength(3);
		combinationPolicy.setNonAlphaNumericRequired(true);
		combinationPolicy.setUpperCaseAlphabetRequired(false);
	}
		
	
	/**
	 * 1. This testcase checks if the all the validations return "true" in a positive case.
	 */
	public void testAllValidations_Success() {
		try {
			expect(csmUserRepository.userHasPassword(user,password)).andReturn(false).anyTimes();
			expect(csmUserRepository.userHadPassword(user,password)).andReturn(false).anyTimes();
			expect(csmUserRepository.getUsernameById(userName)).andReturn(userName).anyTimes();
			replayMocks();
			boolean response = passwordCreationPolicyValidator.validate(passwordPolicy, user, password);
			verifyMocks();
			assertTrue(response);
			
		} catch (ValidationException e) {
			fail("No Exceptions should be thrown");
		}
	}	
	
	/**
	 * 2. This testcase checks if the validate method returns false if the user tries to set a password which is already used.
	 */
	public void testPasswordHistoryValidation_Failure() {
		
		try {
			user = registerMockFor(User.class);
			expect(user.getPasswordAge()).andReturn(new Long(190));
			expect(csmUserRepository.userHasPassword(user,password)).andReturn(false).anyTimes();
			expect(csmUserRepository.userHadPassword(user,password)).andReturn(true).anyTimes();
			replayMocks();
			boolean response;
			response = passwordCreationPolicyValidator.validate(passwordPolicy, user, password);
			assertFalse(response);
			verifyMocks();
			fail("Expecting ValidationException to be thrown");
		} catch (ValidationException e) {
			/*good*/
			assertEquals("Must choose a password that has not been used recently.", e.getMessage());
		}
	}
	
	/**
	 * 3. This method has various assertions to check the password creation validations on various passwords.
	 */
	public void testAllPasswordFormatValidations() {
		
		user = registerMockFor(User.class);
		//user.setLoginId(userName);
		expect(user.getLoginId()).andReturn(userName).anyTimes();
		expect(csmUserRepository.getUsernameById(userName)).andReturn(userName).anyTimes();
		expect(user.getPasswordAge()).andReturn(new Long(190)).anyTimes();
		expect(csmUserRepository.userHasPassword((User)EasyMock.anyObject(),(String)EasyMock.anyObject())).andReturn(false).anyTimes();
		expect(csmUserRepository.userHadPassword((User)EasyMock.anyObject(),(String)EasyMock.anyObject())).andReturn(false).anyTimes();
		replayMocks();		
		try {
			// Purpose of this assertion: To test for password length to be 7
			passwordCreationPolicyValidator.validate(passwordPolicy, user, "Pass1!");
			fail("Expecting ValidationException to be thrown");
		} catch (ValidationException e) {
			/*good*/
			assertEquals("The minimum length of password must be at least 7 characters", e.getMessage());
		} 
		try {
			 // Purpose of this assertion: To test for password to have a special character
			passwordCreationPolicyValidator.validate(passwordPolicy, user, "Password1");
			fail("Expecting ValidationException to be thrown");
		} catch (ValidationException e) {
			/*good*/
			assertEquals("The password should have at least one special charcter", e.getMessage());
		}
		try {
			// Purpose of this assertion: To test for password to have a numeric (0-9)
			passwordCreationPolicyValidator.validate(passwordPolicy, user, "Password!");
			fail("Expecting ValidationException to be thrown");
		} catch (ValidationException e) {
			/*good*/
			assertEquals("The password should have at least one numeral digit{0-9}", e.getMessage());
		} 
		try {
			 // Purpose of this assertion: To test for password to have a lower-case alphabet
			passwordCreationPolicyValidator.validate(passwordPolicy, user, "PASSWORD1!");
			fail("Expecting ValidationException to be thrown");
		} catch (ValidationException e) {
			/*good*/
			assertEquals("The password should have at least one lower case letter", e.getMessage());
		}
		try {
			 // Purpose of this assertion: To test for password not to have more than 3 characters from userName
			passwordCreationPolicyValidator.validate(passwordPolicy, user, "wxyzPass1!");
			fail("Expecting ValidationException to be thrown");
		} catch (ValidationException e) {
			/*good*/
			assertEquals("The password should not contain a substring of 4 letters from the username", e.getMessage());
		}
		verifyMocks(); 
	}
}
