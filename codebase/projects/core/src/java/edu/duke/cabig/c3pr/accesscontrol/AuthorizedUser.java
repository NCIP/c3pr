package edu.duke.cabig.c3pr.accesscontrol;

import java.util.ArrayList;
import java.util.List;

import edu.duke.cabig.c3pr.constants.C3PRUserGroupType;
import edu.duke.cabig.c3pr.dao.RolePrivilegeDao;
import edu.duke.cabig.c3pr.domain.RolePrivilege;
import edu.duke.cabig.c3pr.utils.SecurityUtils;
import gov.nih.nci.cabig.ctms.suite.authorization.ProvisioningSession;

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
                    GrantedAuthority[] grantedAuthorities, ProvisioningSession provisioningSession, RolePrivilegeDao rolePrivilegeDao) throws IllegalArgumentException {
        super(string, string1, b, b1, b2, b3, grantedAuthorities);
        
        this.provisioningSession = provisioningSession;
        this.rolePrivileges = getAllRolePrivileges(grantedAuthorities, rolePrivilegeDao);
    }

	private RolePrivilege[] getAllRolePrivileges(GrantedAuthority[] grantedAuthorities, RolePrivilegeDao rolePrivilegeDao) {
		List<C3PRUserGroupType> c3prUserGroupTypes = SecurityUtils.getC3PRUserRoleTypes(grantedAuthorities);
		List<RolePrivilege> rolePrivilegeList = new ArrayList<RolePrivilege>();
		String roleName;
		for(C3PRUserGroupType group: c3prUserGroupTypes){
			roleName = group.getCode();
			rolePrivilegeList.addAll(rolePrivilegeDao.getAllPrivilegesForRole(roleName));
		}
		return rolePrivilegeList.toArray(new RolePrivilege[rolePrivilegeList.size()]);
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

 }
