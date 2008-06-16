package edu.duke.cabig.c3pr.domain.repository;

import edu.duke.cabig.c3pr.domain.ResearchStaff;
import edu.duke.cabig.c3pr.domain.User;
import edu.duke.cabig.c3pr.exception.C3PRBaseRuntimeException;

/**
 * This service interface is used to manage user access to the c3pr UI.
 */
public interface CSMUserRepository {
	/**
	 * Create the csm users and groups for newly created research staff or updates csm users/groups
	 * if research staff already exists.
	 *
	 * @param researchStaff the research staff for which csm users and groups to be created or updated
	 * @throws C3PRBaseRuntimeException if CSM user can not be created.
	 */
	void createOrUpdateCSMUserAndGroupsForResearchStaff(
			ResearchStaff researchStaff, String changeURL) ;

	public User getUserByName(String userName);

	public void userChangePassword(String userName, String password, int maxHistorySize);

	public boolean userHasPassword(String userName, String password);

	public boolean userHadPassword(String userName, String password);

	public String userCreateToken(String userName);

	public void sendUserEmail(String userName, String subject, String text) ;
}

