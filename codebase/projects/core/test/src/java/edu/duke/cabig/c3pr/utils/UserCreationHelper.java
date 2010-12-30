package edu.duke.cabig.c3pr.utils;

import edu.duke.cabig.c3pr.domain.LocalPersonUser;
import edu.duke.cabig.c3pr.domain.User;

public class UserCreationHelper {
	
	public User buildBasicResearchStaffUser(){
		User user = new LocalPersonUser();
		user.setFirstName("John");
		user.setLastName("Scott");
		user.setEmail("john.scott@user.com");
		return user;
	}
	
}
