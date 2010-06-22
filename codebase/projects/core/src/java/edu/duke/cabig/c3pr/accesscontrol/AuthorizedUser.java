package edu.duke.cabig.c3pr.accesscontrol;

import edu.duke.cabig.c3pr.domain.RolePrivilege;
import gov.nih.nci.cabig.ctms.suite.authorization.ProvisioningSession;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
	
    public AuthorizedUser(String string, String string1, boolean b, boolean b1, boolean b2, boolean b3,
                    GrantedAuthority[] grantedAuthorities, ProvisioningSession provisioningSession, RolePrivilege rolePrivileges[]) throws IllegalArgumentException {
        super(string, string1, b, b1, b2, b3, grantedAuthorities);
        
        this.provisioningSession = provisioningSession;
        this.rolePrivileges = rolePrivileges;
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

 }
