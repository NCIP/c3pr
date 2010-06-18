package edu.duke.cabig.c3pr.domain.repository;

import java.util.List;
import java.util.Map;

import edu.duke.cabig.c3pr.constants.C3PRUserGroupType;
import edu.duke.cabig.c3pr.domain.C3PRUser;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.ResearchStaff;
import edu.duke.cabig.c3pr.exception.C3PRBaseException;
import gov.nih.nci.security.authorization.domainobjects.User;

public interface ResearchStaffRepository {
	
	public ResearchStaff merge(ResearchStaff staff) ;
	public void initialize(ResearchStaff staff);
	public void evict(ResearchStaff researchStaff);

	public ResearchStaff getByAssignedIdentifier(String assignedIdentifier);
	public ResearchStaff getByAssignedIdentifierFromLocal(String assignedIdentifier);
	public List<ResearchStaff> getRemoteResearchStaff(ResearchStaff staff);
	public gov.nih.nci.security.authorization.domainobjects.User getCSMUser(C3PRUser user) ;

	public List<C3PRUserGroupType> getGroups(User csmUser, HealthcareSite healthcareSite);
	public boolean isLoggedInUser(ResearchStaff staff);
	public ResearchStaff createOrModifyResearchStaff(ResearchStaff staff, boolean createCsmUser, String username, Map<HealthcareSite, List<C3PRUserGroupType>> associationMap) throws C3PRBaseException ;
	public ResearchStaff createOrModifyResearchStaff(ResearchStaff staff, boolean createCsmUser, String username, Map<HealthcareSite, List<C3PRUserGroupType>> associationMap, boolean hasAccessToAllSites) throws C3PRBaseException ;
	public ResearchStaff createOrModifyResearchStaff(ResearchStaff staff, Map<HealthcareSite, List<C3PRUserGroupType>> associationMap) throws C3PRBaseException ;
}
