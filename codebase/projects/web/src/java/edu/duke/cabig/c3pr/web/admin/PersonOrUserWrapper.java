package edu.duke.cabig.c3pr.web.admin;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections15.functors.InstantiateFactory;
import org.apache.commons.collections15.list.LazyList;
import org.apache.commons.lang.StringUtils;

import edu.duke.cabig.c3pr.domain.PersonUser;
import edu.duke.cabig.c3pr.utils.RoleBasedHealthcareSitesAndStudiesDTO;

public class PersonOrUserWrapper {
	
	private PersonUser personUser;
	private String userName;
	private String preExistingUsersAssignedId;
	private String roleName;
	private boolean createAsStaff = true;
	private boolean createAsUser = true;
	private String userStatus;
	
	/** List of newly added organization primaryIdentifiers for the staff. These are then assigned to the personUser.healthcareSites in the controller
	    note that unlike RoleBasedHealthcareSitesAndStudiesDTO.sites(list of CSM orgs) this list does not at any point contain the pre-existing organizations.
	    this is because staff orgs cannot be deleted and just need to be displayed as labels on the UI.*/
	private List<String> staffOrganizationPrimaryIdentifiers = new ArrayList<String>();
	
	/** The selected organization for display. the auto-completer maps to this field. But not used by the controller*/
	private String selectedOrganizationForDisplay;

	/** The sitePrimaryIdentifier. Used as a temp to hold the auto-completer value before being assigned to the personUser.healthcareSites for persistence. */
	private String primaryIdentifier;
	
	
	private List<RoleBasedHealthcareSitesAndStudiesDTO> healthcareSiteRolesHolderList = LazyList.decorate(new ArrayList<RoleBasedHealthcareSitesAndStudiesDTO>(), new InstantiateFactory<RoleBasedHealthcareSitesAndStudiesDTO>(RoleBasedHealthcareSitesAndStudiesDTO.class));
	
	public PersonUser getPersonUser() {
		return personUser;
	}
	public void setPersonUser(PersonUser personUser) {
		this.personUser = personUser;
	}

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setHealthcareSiteRolesHolderList(
			List<RoleBasedHealthcareSitesAndStudiesDTO> healthcareSiteRolesHolderList) {
		this.healthcareSiteRolesHolderList = healthcareSiteRolesHolderList;
	}
	
	public List<RoleBasedHealthcareSitesAndStudiesDTO> getHealthcareSiteRolesHolderList() {
		return healthcareSiteRolesHolderList;
	}
	
	public void addHealthcareSiteRolesHolder(RoleBasedHealthcareSitesAndStudiesDTO healthcareSiteRolesHolder){
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
	
	/**
	 * Checks if is staff.
	 *
	 * @return true, if assigned Id is present.
	 */
	public boolean getIsStaff(){
		if(StringUtils.isBlank(this.personUser.getAssignedIdentifier())){
			return false;
		}
		return true;
	}
	
	/**
	 * Checks if is user.
	 *
	 * @return true, if loginId is present.
	 */
	public boolean getIsUser(){
		if(StringUtils.isBlank(this.personUser.getLoginId())){
			return false;
		}
		return true;
	}
	
	public boolean getCreateAsStaff() {
		return createAsStaff;
	}
	public void setCreateAsStaff(boolean createAsStaff) {
		this.createAsStaff = createAsStaff;
	}
	public boolean getCreateAsUser() {
		return createAsUser;
	}
	public void setCreateAsUser(boolean createAsUser) {
		this.createAsUser = createAsUser;
	}
	public List<String> getStaffOrganizationPrimaryIdentifiers() {
		return staffOrganizationPrimaryIdentifiers;
	}
	public void setStaffOrganizationPrimaryIdentifiers(
			List<String> staffOrganizationPrimaryIdentifier) {
		this.staffOrganizationPrimaryIdentifiers = staffOrganizationPrimaryIdentifier;
	}
	public String getSelectedOrganizationForDisplay() {
		return selectedOrganizationForDisplay;
	}
	public void setSelectedOrganizationForDisplay(
			String selectedOrganizationForDisplay) {
		this.selectedOrganizationForDisplay = selectedOrganizationForDisplay;
	}
	public String getPrimaryIdentifier() {
		return primaryIdentifier;
	}
	public void setPrimaryIdentifier(String primaryIdentifier) {
		this.primaryIdentifier = primaryIdentifier;
	}
	
	/**
	 * Gets the user status based on the endDate mentioned in the csm_user table.
	 *
	 * @return the user status
	 */
	public String getUserStatus() {
		return userStatus;
	}
	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}
	
}
