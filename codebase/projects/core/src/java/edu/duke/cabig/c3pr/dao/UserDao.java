/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.dao;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import edu.duke.cabig.c3pr.domain.C3PRUser;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.PersonUser;
import edu.duke.cabig.c3pr.domain.User;
import gov.nih.nci.cabig.ctms.dao.MutableDomainObjectDao;

/**
 * This class implements the Data access related operations for the C3PRUser domain object.
 * 
 */
public class UserDao extends GridIdentifiableDao<C3PRUser> implements MutableDomainObjectDao<C3PRUser> {

    /**
     * Get the Class representation of the domain object that this DAO is representing.
     * 
     * @return Class representation of the domain object that this DAO is representing.
     */
    @Override
    public Class<C3PRUser> domainClass() {
        return C3PRUser.class;
    }

    /**
     * Save or update the user in the db.
     * 
     * @param The user.
     */
    @Transactional(readOnly = false)
    public void save(final C3PRUser user) {
        getHibernateTemplate().saveOrUpdate(user);
    }

    /**
     * Get the user who has specified csm user id.
     * 
     * @param loginId  The login id of the user.
     * @return The user.
     */
    @SuppressWarnings("unchecked")
	public User getByLoginId(long loginId) {
        List<User> results = getHibernateTemplate().find("from PersonUser as rs left join fetch rs.healthcareSites where rs.loginId=?", Long.toString(loginId));
        if(results.size() > 0 &&  ((PersonUser)results.get(0)).getHealthcareSites().size() > 0){
        	for(HealthcareSite hcs: ((PersonUser)results.get(0)).getHealthcareSites()){
            	getHibernateTemplate().initialize(hcs.getIdentifiersAssignedToOrganization());
        	}
        }
        
        return results.size() > 0 ? results.get(0) : null;
    }
    
    public static void addUserToken(User user){
		user.setTokenTime(new Timestamp(new Date().getTime()));
		user.setToken(user.generateRandomToken());
	}
}
