package edu.duke.cabig.c3pr.domain.repository;

import java.util.List;
import java.util.Map;

import edu.duke.cabig.c3pr.constants.C3PRUserGroupType;
import edu.duke.cabig.c3pr.domain.C3PRUser;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.PersonUser;
import edu.duke.cabig.c3pr.exception.C3PRBaseException;
import gov.nih.nci.security.authorization.domainobjects.User;

public interface PersonUserRepository {
	
	public PersonUser merge(PersonUser staff) ;
	public void initialize(PersonUser staff);
	public void evict(PersonUser researchStaff);

	public PersonUser getByAssignedIdentifier(String assignedIdentifier);
	public PersonUser getByAssignedIdentifierFromLocal(String assignedIdentifier);
	public List<PersonUser> getRemoteResearchStaff(PersonUser staff);
	public gov.nih.nci.security.authorization.domainobjects.User getCSMUser(C3PRUser user) ;
	public gov.nih.nci.security.authorization.domainobjects.User getCSMUserByUserName(String userName);
	public List<C3PRUserGroupType> getGroups(User csmUser, HealthcareSite healthcareSite);
	public boolean isLoggedInUser(PersonUser staff);
	
	public PersonUser createOrModifyResearchStaffWithUserAndAssignRoles(PersonUser researchStaff, String username, 
			Map<HealthcareSite, List<C3PRUserGroupType>> associationMap, boolean hasAccessToAllSites) throws C3PRBaseException;
	public PersonUser createSuperUser(PersonUser researchStaff, String username, Map<HealthcareSite, List<C3PRUserGroupType>> associationMap) throws C3PRBaseException;
	public PersonUser createOrModifyUserWithoutResearchStaffAndAssignRoles(PersonUser researchStaff, String username, Map<HealthcareSite, List<C3PRUserGroupType>> associationMap,
			boolean hasAccessToAllSites) throws C3PRBaseException;
	public PersonUser createOrModifyResearchStaffWithoutUser(PersonUser researchStaff, Map<HealthcareSite, List<C3PRUserGroupType>> associationMap,
			boolean hasAccessToAllSites) throws C3PRBaseException;
	public boolean getHasAccessToAllSites(User csmUser);
	
}
