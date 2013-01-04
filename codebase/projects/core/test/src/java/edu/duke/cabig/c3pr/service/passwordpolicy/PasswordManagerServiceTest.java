/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 *   Distributed under the OSI-approved BSD 3-Clause License.
 *   See https://github.com/NCIP/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.service.passwordpolicy;

import static org.easymock.EasyMock.expect;
import edu.duke.cabig.c3pr.AbstractTestCase;
import edu.duke.cabig.c3pr.dao.PasswordPolicyDao;
import edu.duke.cabig.c3pr.dao.UserDao;
import edu.duke.cabig.c3pr.domain.LocalPersonUser;
import edu.duke.cabig.c3pr.domain.User;
import edu.duke.cabig.c3pr.domain.passwordpolicy.CombinationPolicy;
import edu.duke.cabig.c3pr.domain.passwordpolicy.PasswordCreationPolicy;
import edu.duke.cabig.c3pr.domain.passwordpolicy.PasswordPolicy;
import edu.duke.cabig.c3pr.domain.repository.impl.CSMUserRepositoryImpl;
import edu.duke.cabig.c3pr.service.impl.passwordpolicy.PasswordManagerServiceImpl;
import edu.duke.cabig.c3pr.service.impl.passwordpolicy.PasswordPolicyServiceImpl;

public class PasswordManagerServiceTest extends AbstractTestCase {
	
    private PasswordManagerServiceImpl passwordManagerService;
    private PasswordPolicyServiceImpl passwordPolicyService;
    private PasswordPolicyDao passwordPolicyDao;
    private UserDao userDao;
    
	private PasswordCreationPolicy passwordCreationPolicy;    
	private PasswordPolicy passwordPolicy;   
	private CombinationPolicy combinationPolicy;

    private CSMUserRepositoryImpl csmUserRepositoryImpl;
    private User user;	
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        csmUserRepositoryImpl = registerMockFor(CSMUserRepositoryImpl.class);
        passwordPolicyDao = registerDaoMockFor(PasswordPolicyDao.class);
        userDao = registerDaoMockFor(UserDao.class);
        passwordManagerService = new PasswordManagerServiceImpl();
        passwordPolicyService = new PasswordPolicyServiceImpl();
        
        passwordPolicyService.setCsmUserRepository(csmUserRepositoryImpl);
        passwordPolicyService.setPasswordPolicyDao(passwordPolicyDao);
        passwordManagerService.setCsmUserRepository(csmUserRepositoryImpl);
        passwordManagerService.setPasswordPolicyService(passwordPolicyService);
        passwordManagerService.setUserDao(userDao);
        
		passwordPolicy = new PasswordPolicy();   
		combinationPolicy = new CombinationPolicy();
		passwordCreationPolicy = new PasswordCreationPolicy();				
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
    
    
    public void testAddToken() throws Exception {
    	
    	String userName = "user@domain";
        user = new LocalPersonUser();
        user.setLoginId(userName);
        
        expect(csmUserRepositoryImpl.getUserByName(userName)).andReturn(user).anyTimes();
		userDao.save(user);
		
		replayMocks();
		assertNull(user.getToken());
		passwordManagerService.addUserToken(userName);
		assertNotNull(user.getToken());
    }
    
    
    public void testSetPassword() throws Exception {
    	
    	gov.nih.nci.security.authorization.domainobjects.User csmUser = new gov.nih.nci.security.authorization.domainobjects.User();
    	String userName = "user@domain";
    	String password = "Password1!";
    	csmUser.setLoginName(userName);
        user = new LocalPersonUser();
        user.setLoginId(userName);
        
        expect(csmUserRepositoryImpl.getUserByName(userName)).andReturn(user).anyTimes();
		expect(csmUserRepositoryImpl.userHasPassword(user,password)).andReturn(false).anyTimes();
		expect(csmUserRepositoryImpl.userHadPassword(user,password)).andReturn(false).anyTimes();
		expect(csmUserRepositoryImpl.getUsernameById(userName)).andReturn(userName).anyTimes();
		expect(passwordPolicyDao.getById(1)).andReturn(passwordPolicy).anyTimes();
		csmUserRepositoryImpl.userChangePassword(user, password, 3);
		userDao.save(user);
		
		replayMocks();
		try{
			passwordManagerService.addUserToken(userName);
			passwordManagerService.setPassword(userName, password, user.getToken());
		}catch(Exception e){
			e.printStackTrace();
			fail("Not expecting any exception");
		}
		verifyMocks();
    }

}
