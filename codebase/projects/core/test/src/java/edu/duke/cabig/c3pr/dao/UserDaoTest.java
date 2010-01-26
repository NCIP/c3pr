package edu.duke.cabig.c3pr.dao;

import java.sql.Timestamp;
import java.util.Date;

import edu.duke.cabig.c3pr.domain.ResearchStaff;
import edu.duke.cabig.c3pr.domain.User;
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
    
	ResearchStaffDao researchStaffDao;
	
	public UserDaoTest(){
		researchStaffDao = (ResearchStaffDao) getApplicationContext().getBean("researchStaffDao");
	}
	
    /**
     * Test domain class.
     */
	public void testDomainClass() {
		assertEquals("Wrong Domain Class", User.class, getDao().domainClass());
	}
    
    
    /**
     * Test save.
     * 
     * @throws Exception the exception
     */
    public void testSave() throws Exception {
        ResearchStaff researchStaff = researchStaffDao.getById(1000);
        Timestamp timestamp = new Timestamp(new Date().getTime());
        researchStaff.setTokenTime(timestamp);
        researchStaff.setToken(encryptString(researchStaff.getSalt() + researchStaff.getTokenTime().toString()
				+ "random_string").replaceAll("\\W", "Q"));
		getDao().save((User)researchStaff);
        
		interruptSession();
		
		ResearchStaff savedResearchStaff = researchStaffDao.getById(1000);
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

