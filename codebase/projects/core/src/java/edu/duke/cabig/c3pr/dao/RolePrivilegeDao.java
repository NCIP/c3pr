/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.dao;

import java.util.ArrayList;
import java.util.List;

import edu.duke.cabig.c3pr.domain.RolePrivilege;

/**
 * Hibernate implementation of RolePrivilegeDao
 * 
 * @see edu.duke.cabig.c3pr.dao.RolePrivilegeDao
 * @author Vinay Gangoli
 */
public class RolePrivilegeDao extends GridIdentifiableDao<RolePrivilege> {

    @Override
    public Class<RolePrivilege> domainClass() {
        return RolePrivilege.class;
    }

    public List<RolePrivilege> getAccessibleRoles(String objectId, String checkPrivilege){
    	 return (List<RolePrivilege>) getHibernateTemplate()
         .find("from RolePrivilege R where R.objectId=? and R.privilege=?",
                 new Object[] {objectId, checkPrivilege});
    }
    
    /**
     * Checks if entered combination is valid role privilege.
     *
     * @param objectId the object id
     * @param checkPrivilege the check privilege
     * @param roleName the role name
     * @return true, if is valid role privilege
     */
    public boolean isValidRolePrivilege(String objectId, String checkPrivilege, String roleName){
    	return getHibernateTemplate()
        .find("from RolePrivilege R where R.objectId=? and R.privilege=? and R.roleName=?",
                new Object[] {objectId, checkPrivilege, roleName}).size() > 0;
    }
    

    public List<RolePrivilege> getAllPrivilegesForRole(String roleName){
    	 return (List<RolePrivilege>) getHibernateTemplate()
         .find("from RolePrivilege R where R.roleName=?",
                 new Object[] {roleName});
    }
}
