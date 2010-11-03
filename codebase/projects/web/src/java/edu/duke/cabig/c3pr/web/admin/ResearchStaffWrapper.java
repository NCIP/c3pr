package edu.duke.cabig.c3pr.web.admin;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections15.functors.InstantiateFactory;
import org.apache.commons.collections15.list.LazyList;

import edu.duke.cabig.c3pr.domain.ResearchStaff;

public class ResearchStaffWrapper {
	
	private ResearchStaff researchStaff ;
	private String userName ;
	private boolean hasAccessToAllSites ;
	private String preExistingUsersAssignedId;
	private String roleName;
	
	
	private List<HealthcareSiteRolesHolder> healthcareSiteRolesHolderList = LazyList.decorate(new ArrayList<HealthcareSiteRolesHolder>(), new InstantiateFactory<HealthcareSiteRolesHolder>(HealthcareSiteRolesHolder.class));
	
	public ResearchStaff getResearchStaff() {
		return researchStaff;
	}
	public void setResearchStaff(ResearchStaff researchStaff) {
		this.researchStaff = researchStaff;
	}

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setHasAccessToAllSites(boolean hasAccessToAllSites) {
		this.hasAccessToAllSites = hasAccessToAllSites;
	}
	public boolean getHasAccessToAllSites() {
		return hasAccessToAllSites;
	}
	public void setHealthcareSiteRolesHolderList(
			List<HealthcareSiteRolesHolder> healthcareSiteRolesHolderList) {
		this.healthcareSiteRolesHolderList = healthcareSiteRolesHolderList;
	}
	public List<HealthcareSiteRolesHolder> getHealthcareSiteRolesHolderList() {
		return healthcareSiteRolesHolderList;
	}
	
	public void addHealthcareSiteRolesHolder(HealthcareSiteRolesHolder healthcareSiteRolesHolder){
		getHealthcareSiteRolesHolderList().add(healthcareSiteRolesHolder);
	}
	public String getPreExistingUsersAssignedId() {
		return preExistingUsersAssignedId;
	}
	public void setPreExistingUsersAssignedId(String preExistingUsersAsignedId) {
		this.preExistingUsersAssignedId = preExistingUsersAsignedId;
	}
	
	/**This is used by the study_personel page to display every staff and his role.
	 * This is not a list but a string as every staff needs to be assigned to a study using one role at a time.
	 * 
	 * @return String
	 */
	public String getRoleName() {
		return roleName;
	}
	
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
}
