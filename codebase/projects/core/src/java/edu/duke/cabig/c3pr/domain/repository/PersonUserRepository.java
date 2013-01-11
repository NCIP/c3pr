/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.domain.repository;

import java.util.List;

import edu.duke.cabig.c3pr.constants.C3PRUserGroupType;
import edu.duke.cabig.c3pr.domain.PersonUser;
import edu.duke.cabig.c3pr.exception.C3PRBaseException;
import edu.duke.cabig.c3pr.utils.RoleBasedHealthcareSitesAndStudiesDTO;
import gov.nih.nci.cabig.ctms.suite.authorization.ProvisioningSession;
import gov.nih.nci.security.authorization.domainobjects.User;

public interface PersonUserRepository {
	
	public PersonUser merge(PersonUser staff) ;
	public void initialize(PersonUser staff);
	public void evict(PersonUser researchStaff);

	public PersonUser getByAssignedIdentifier(String assignedIdentifier);
	public PersonUser getByAssignedIdentifierFromLocal(String assignedIdentifier);
	public List<PersonUser> getRemoteResearchStaff(PersonUser staff);
	public gov.nih.nci.security.authorization.domainobjects.User getCSMUser(PersonUser staff) ;
	public gov.nih.nci.security.authorization.domainobjects.User getCSMUserByUserName(String userName);
	public List<C3PRUserGroupType> getGroupsForUser(User csmUser);
	public List<String> getOrganizationIdsForUser(User csmUser, C3PRUserGroupType c3prUserGroupType);
	public List<String> getStudyIdsForUser(User csmUser, C3PRUserGroupType c3prUserGroupType);
	public List<String> getOrganizationIdsForUser(ProvisioningSession provisioningSession , C3PRUserGroupType c3prUserGroupType);
	public List<String> getStudyIdsForUser(ProvisioningSession provisioningSession , C3PRUserGroupType c3prUserGroupType);
	
	public boolean isLoggedInUser(PersonUser staff);
	
	public PersonUser createOrModifyResearchStaffWithUserAndAssignRoles(PersonUser researchStaff, String username, List<RoleBasedHealthcareSitesAndStudiesDTO> listAssociation) throws C3PRBaseException;
	public PersonUser createSuperUser(PersonUser researchStaff, String username, List<RoleBasedHealthcareSitesAndStudiesDTO> listAssociation) throws C3PRBaseException;
	public PersonUser createOrModifyUserWithoutResearchStaffAndAssignRoles(PersonUser researchStaff, String username, List<RoleBasedHealthcareSitesAndStudiesDTO> listAssociation) throws C3PRBaseException;
	public PersonUser createOrModifyResearchStaffWithoutUser(PersonUser researchStaff, List<RoleBasedHealthcareSitesAndStudiesDTO> listAssociation) throws C3PRBaseException;
	public boolean getHasAccessToAllSites(User csmUser, C3PRUserGroupType group);
	public boolean getHasAccessToAllStudies(User csmUser, C3PRUserGroupType group);
	
	public boolean getHasAccessToAllSites(ProvisioningSession provisioningSession, C3PRUserGroupType group);
	public boolean getHasAccessToAllStudies(ProvisioningSession provisioningSession, C3PRUserGroupType group);
	
	
}
