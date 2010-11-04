package edu.duke.cabig.c3pr.domain;

import java.sql.Timestamp;
import java.util.Date;

import edu.duke.cabig.c3pr.AbstractTestCase;
import edu.duke.cabig.c3pr.utils.UserCreationHelper;

// TODO: Auto-generated Javadoc
/**
 * The Class UserTestCase.
 */
public class UserTestCase extends AbstractTestCase{
	
	/** The user. */
	private User user ;
	
	/** The user creation helper. */
	private UserCreationHelper userCreationHelper = new UserCreationHelper();
	
	/**
	 * Sets the user creation helper.
	 * 
	 * @param userCreationHelper the new user creation helper
	 */
	public void setUserCreationHelper(UserCreationHelper userCreationHelper) {
		this.userCreationHelper = userCreationHelper;
	}

	/* (non-Javadoc)
	 * @see edu.nwu.bioinformatics.commons.testing.CoreTestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		user = userCreationHelper.buildBasicResearchStaffUser();
	}
	
	/**
	 * Test reset token.
	 */
	public void testResetToken(){
		Timestamp timestamp = new Timestamp(200);
		user.setTokenTime(timestamp);
		user.resetToken();
		assertNotEquals("after reset token time, time stamp should be differnt then earlier", timestamp, user.getTokenTime());
	}
	
	/**
	 * Test get password age.
	 */
	public void testGetPasswordAge() {
		final double delta = 10;
		Timestamp timestamp = new Timestamp(new Date().getTime() - 1000);
		user.setPasswordLastSet(timestamp);
		assertEquals("password age is 1000 miliseconds+delta", 1000,
				user.getPasswordAge(), delta);
	}
	
	/**
	 * Test add password to history.
	 */
	public void testAddPasswordToHistory(){
		assertEquals("password history size is 0", 0, user.getPasswordHistory().size());
		user.addPasswordToHistory("testpasswd", 1)	;
		assertEquals("password history size is 1", 1, user.getPasswordHistory().size());
	}
	
	/**
	 * Test add password to history1.
	 */
	public void testAddPasswordToHistory1(){
		assertEquals("password history size is 0", 0, user.getPasswordHistory().size());
		user.addPasswordToHistory("testpasswd", 1)	;
		user.addPasswordToHistory("testpasswd2", 1)	;
		assertEquals("password history size is 1", 1, user.getPasswordHistory().size());
		assertEquals("last stored password should be testpasswd2", "testpasswd2", user.getPasswordHistory().get(user.getPasswordHistory().size() -1));
	}
	
	/**
	 * Test get last first.
	 */
	public void testGetLastFirst(){
		assertEquals("getLastFirst should evaluate to Scott, John","Scott, John",user.getLastFirst());
	}
	
	/**
	 * Test get full name.
	 */
	public void testGetFullName(){
		assertEquals("getLastFirst should evaluate to John Scott","John Scott",user.getFullName());
	}
	
}
