/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.accesscontrol;

import edu.duke.cabig.c3pr.constants.UserPrivilegeType;
import edu.duke.cabig.c3pr.domain.PersonUser;
import edu.duke.cabig.c3pr.domain.RolePrivilege;
import gov.nih.nci.cabig.ctms.suite.authorization.ProvisioningSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.userdetails.User;


/**
 * The Class AuthorizedUser.
 * This class populates the protectionGroupRoleContext from CSM so that the "filterers" can use it instead 
 * of going to CSM for every search and filter operation.
 * 
 * 
 * @author Vinay G
 */
public class AuthorizedUser extends User {

	private ProvisioningSession provisioningSession;
	
	private RolePrivilege rolePrivileges[];
	
	private Map<String, List<String>> roleBasedOrganizationsMap = new HashMap<String, List<String>>();
	
	private PersonUser personUser;
	
    public AuthorizedUser(String string, String string1, boolean b, boolean b1, boolean b2, boolean b3, GrantedAuthority[] grantedAuthorities, 
                    ProvisioningSession provisioningSession, RolePrivilege rolePrivileges[], PersonUser personUser, Map<String, List<String>> roleBasedOrganizationsMap) 
    				throws IllegalArgumentException {
        super(string, string1, b, b1, b2, b3, grantedAuthorities);
        
        this.personUser = personUser;
        this.provisioningSession = provisioningSession;
        this.rolePrivileges = rolePrivileges;
        this.roleBasedOrganizationsMap = roleBasedOrganizationsMap;
    }


    public ProvisioningSession getProvisioningSession() {
		return provisioningSession;
	}

	public void setProvisioningSession(ProvisioningSession provisioningSession) {
		this.provisioningSession = provisioningSession;
	}

	public RolePrivilege[] getRolePrivileges() {
		return rolePrivileges;
	}
	
	public List<UserPrivilege> getUserPrivileges(){
		Set<UserPrivilege> privileges = new HashSet<UserPrivilege>();
		for(RolePrivilege rolePrivilege : rolePrivileges){
			privileges.add(new UserPrivilege(rolePrivilege.getObjectId(),rolePrivilege.getPrivilege()));
		}
		return new ArrayList(privileges);
	}

	public List<RolePrivilege> getRolePrivileges(UserPrivilegeType userPrivilegeType){
		String checkObjectId = userPrivilegeType.getCode().split(":")[0];
		String checkPrivilege = userPrivilegeType.getCode().split(":")[1];
		List<RolePrivilege> filteredRolePrivileges = new ArrayList<RolePrivilege>();
		for(RolePrivilege rolePrivilege : rolePrivileges){
			if(rolePrivilege.getObjectId().equals(checkObjectId) &&
					rolePrivilege.getPrivilege().equals(checkPrivilege)){
				filteredRolePrivileges.add(rolePrivilege);
			}
		}
		return filteredRolePrivileges;
	}


	public PersonUser getPersonUser() {
		return personUser;
	}


	public Map<String, List<String>> getRoleBasedOrganizationsMap() {
		return roleBasedOrganizationsMap;
	}


	public void setRoleBasedOrganizationsMap(
			Map<String, List<String>> roleBasedOrganizationsMap) {
		this.roleBasedOrganizationsMap = roleBasedOrganizationsMap;
	}

 }
