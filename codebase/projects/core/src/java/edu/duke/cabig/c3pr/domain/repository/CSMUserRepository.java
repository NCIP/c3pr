package edu.duke.cabig.c3pr.domain.repository;

import edu.duke.cabig.c3pr.domain.User;

/**
 * This service interface is used to manage user access to the c3pr UI.
 */
public interface CSMUserRepository {

	public User getUserByName(String userName);
	
	public void userChangePassword(User user, String password, int maxHistorySize);

	public boolean userHasPassword(User user, String password);

	public boolean userHadPassword(User user, String password);

	public void sendUserEmail(String userName, String subject, String text) ;
	
	public String getUsernameById(String loginId);
}

