/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.utils;

import java.util.ArrayList;
import java.util.List;

import edu.duke.cabig.c3pr.constants.C3PRUserGroupType;

/**
 * The Class RoleBasedHealthcareSitesAndStudiesHolder. Maps every role with its sites and studies.
 */
public class RoleBasedHealthcareSitesAndStudiesDTO {
	
	/** Indicates if the role has been checked. */
	private boolean checked = false;
	
	/** The group/role of the user. */
	private C3PRUserGroupType group;
	
	/** The sites associated to the user(think CSM) added from the UI. Initially pre-loaded from CSM and then modified from the UI before provisioning. 
	 * 	unlike PersonOrUSerWrapper.staffOrganizationPrimaryIdentifiers(list of staff orgs) this list contains the pre-existing organizations.
	    this is because CSM orgs can be deleted and need to be displayed with the delete icon and delete functionality.*/
	private List<String> sites  = new ArrayList<String>();
	
	/** The studies associated to the user(think CSM) added from the UI. Initially pre-loaded from CSM and then modified from the UI before provisioning.*/
	private List<String> studies = new ArrayList<String>();
	
	/** determines if specified group has all site access. */
	private boolean hasAllSiteAccess = false;
	
	/** determines if specified group has all study access. */
	private boolean hasAllStudyAccess = false;
	
	//used for displaying the added sites and studies on the UI
	private String primaryIdentifier;
	private String selectedSiteForDisplay;
	
	private String studyId;
	private String selectedStudyForDisplay;
	
	public RoleBasedHealthcareSitesAndStudiesDTO(){
	}
	
	public RoleBasedHealthcareSitesAndStudiesDTO(C3PRUserGroupType group){
		this.group = group;
	}
	
	public C3PRUserGroupType getGroup() {
		return group;
	}
	public void setGroup(C3PRUserGroupType group) {
		this.group = group;
	}
	public List<String> getStudies() {
		return studies;
	}
	public void setStudies(List<String> studies) {
		this.studies = studies;
	}

	public boolean getHasAllSiteAccess() {
		return hasAllSiteAccess;
	}

	public void setHasAllSiteAccess(boolean hasAllSiteAccess) {
		this.hasAllSiteAccess = hasAllSiteAccess;
	}

	public boolean getHasAllStudyAccess() {
		return hasAllStudyAccess;
	}

	public void setHasAllStudyAccess(boolean hasAllStudyAccess) {
		this.hasAllStudyAccess = hasAllStudyAccess;
	}

	public boolean getChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getPrimaryIdentifier() {
		return primaryIdentifier;
	}

	public void setPrimaryIdentifier(String primaryIdentifier) {
		this.primaryIdentifier = primaryIdentifier;
	}

	public String getSelectedSiteForDisplay() {
		return selectedSiteForDisplay;
	}

	public void setSelectedSiteForDisplay(String selectedSiteForDisplay) {
		this.selectedSiteForDisplay = selectedSiteForDisplay;
	}

	public String getSelectedStudyForDisplay() {
		return selectedStudyForDisplay;
	}

	public void setSelectedStudyForDisplay(String selectedStudyForDisplay) {
		this.selectedStudyForDisplay = selectedStudyForDisplay;
	}

	public String getStudyId() {
		return studyId;
	}

	public void setStudyId(String studyId) {
		this.studyId = studyId;
	}

	public List<String> getSites() {
		return sites;
	}

	public void setSites(List<String> sites) {
		this.sites = sites;
	}
		
}
