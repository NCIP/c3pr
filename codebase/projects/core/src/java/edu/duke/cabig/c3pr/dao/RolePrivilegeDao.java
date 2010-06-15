package edu.duke.cabig.c3pr.dao;

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
}
