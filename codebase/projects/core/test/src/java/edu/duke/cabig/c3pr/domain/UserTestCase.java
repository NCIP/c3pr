package edu.duke.cabig.c3pr.domain;

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
		
	}
	
	public void testGetPasswordAge(){
		
	}
	
	public void testAddPasswordToHistory(){
		
	}
	
	public void testGetLastFirst(){
		assertEquals("getLastFirst should evaluate to Scott, John","Scott, John",user.getLastFirst());
	}
	
	public void testGetFullName(){
		assertEquals("getLastFirst should evaluate to John Scott","John Scott",user.getFullName());
	}
}
