package edu.duke.cabig.c3pr.dao;

import java.sql.Timestamp;
import java.util.Date;

import edu.duke.cabig.c3pr.domain.C3PRUser;
import edu.duke.cabig.c3pr.domain.PersonUser;
import edu.duke.cabig.c3pr.exception.C3PRBaseRuntimeException;
import edu.duke.cabig.c3pr.utils.ContextDaoTestCase;
import gov.nih.nci.security.util.StringEncrypter;

/**
 * JUnit Tests for UserDao.
 * 
 * @author Priyatam
 * @testType integration
 */
public class UserDaoTest extends ContextDaoTestCase<UserDao> {
    
	PersonUserDao personUserDao;
	
	public UserDaoTest(){
		personUserDao = (PersonUserDao) getApplicationContext().getBean("personUserDao");
	}
	
    /**
     * Test domain class.
     */
	public void testDomainClass() {
		assertEquals("Wrong Domain Class", C3PRUser.class, getDao().domainClass());
	}
    
    
    /**
     * Test save.
     * 
     * @throws Exception the exception
     */
    public void testSave() throws Exception {
        PersonUser researchStaff = personUserDao.getById(1000);
        Timestamp timestamp = new Timestamp(new Date().getTime());
        researchStaff.setTokenTime(timestamp);
        researchStaff.setToken(encryptString(researchStaff.getSalt() + researchStaff.getTokenTime().toString()
				+ "random_string").replaceAll("\\W", "Q"));
		getDao().save((C3PRUser)researchStaff);
        
		interruptSession();
		
		PersonUser savedResearchStaff = personUserDao.getById(1000);
		assertEquals(savedResearchStaff.getTokenTime(), timestamp);
		assertNotNull(savedResearchStaff.getToken());
        	
    }

    /**
     * Encrypt string.
     * 
     * @param string the string
     * 
     * @return the string
     */
    private String encryptString(String string) {
		try {
			return new StringEncrypter().encrypt(string);
		} catch (StringEncrypter.EncryptionException e) {
			throw new C3PRBaseRuntimeException("not able to encrypt string");
		}
	}

}

