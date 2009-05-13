package edu.duke.cabig.c3pr.domain;

import java.sql.Timestamp;
import java.util.Date;

import edu.duke.cabig.c3pr.AbstractTestCase;
import edu.duke.cabig.c3pr.utils.UserCreationHelper;

public class UserTestCase extends AbstractTestCase{
	
	private User user ;
	private UserCreationHelper userCreationHelper = new UserCreationHelper();
	public void setUserCreationHelper(UserCreationHelper userCreationHelper) {
		this.userCreationHelper = userCreationHelper;
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		user = userCreationHelper.buildBasicResearchStaffUser();
	}
	
	public void testResetToken(){
		Timestamp timestamp = new Timestamp(200);
		user.setTokenTime(timestamp);
		user.resetToken();
		assertNotEquals("after reset token time, time stamp should be differnt then earlier", timestamp, user.getTokenTime());
	}
	
	public void testGetPasswordAge(){
        Timestamp timestamp = new Timestamp(new Date().getTime() - 1000);
		user.setPasswordLastSet(timestamp);
		System.out.println("######## : " + user.getPasswordAge());
		assertEquals("password age is 1000 miliseconds", 1000, user.getPasswordAge());
	}
	
	public void testAddPasswordToHistory(){
		assertEquals("password history size is 0", 0, user.getPasswordHistory().size());
		user.addPasswordToHistory("testpasswd", 1)	;
		assertEquals("password history size is 1", 1, user.getPasswordHistory().size());
	}
	
	public void testAddPasswordToHistory1(){
		assertEquals("password history size is 0", 0, user.getPasswordHistory().size());
		user.addPasswordToHistory("testpasswd", 1)	;
		user.addPasswordToHistory("testpasswd2", 1)	;
		assertEquals("password history size is 1", 1, user.getPasswordHistory().size());
		assertEquals("last stored password should be testpasswd2", "testpasswd2", user.getPasswordHistory().get(user.getPasswordHistory().size() -1));
	}
	
	public void testGetLastFirst(){
		assertEquals("getLastFirst should evaluate to Scott, John","Scott, John",user.getLastFirst());
	}
	
	public void testGetFullName(){
		assertEquals("getLastFirst should evaluate to John Scott","John Scott",user.getFullName());
	}
	
}
