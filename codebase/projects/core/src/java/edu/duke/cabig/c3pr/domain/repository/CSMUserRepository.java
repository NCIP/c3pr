/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.domain.repository;

import java.util.List;
import java.util.Set;

import edu.duke.cabig.c3pr.constants.C3PRUserGroupType;
import edu.duke.cabig.c3pr.domain.C3PRUser;
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
	
	public Set<gov.nih.nci.security.authorization.domainobjects.User> getCSMUsersByGroup(C3PRUserGroupType group);
	
	public gov.nih.nci.security.authorization.domainobjects.User getCSMUserByName(String userName);

	public List<gov.nih.nci.security.authorization.domainobjects.User> searchCSMUsers(
			String firstName, String lastName, String emailAddress,
			String loginName);
}

