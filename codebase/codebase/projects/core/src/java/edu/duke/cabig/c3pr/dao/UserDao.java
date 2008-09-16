package edu.duke.cabig.c3pr.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import edu.duke.cabig.c3pr.domain.User;
import gov.nih.nci.cabig.ctms.dao.MutableDomainObjectDao;

/**
 * This class implements the Data access related operations for the User domain object.
 * 
 */
public class UserDao extends GridIdentifiableDao<User> implements MutableDomainObjectDao<User> {

    /**
     * Get the Class representation of the domain object that this DAO is representing.
     * 
     * @return Class representation of the domain object that this DAO is representing.
     */
    @Override
    public Class<User> domainClass() {
        return User.class;
    }

    /**
     * Save or update the user in the db.
     * 
     * @param The
     *                user.
     */
    @Transactional(readOnly = false)
    public void save(final User user) {
        getHibernateTemplate().saveOrUpdate(user);
    }

    /**
     * Get the user who has specified email address.
     * 
     * @param emailAddress
     *                The email address of the user.
     * @return The user.
     */
    public User getByEmailAddress(String emailAddress) {
        List<User> results = getHibernateTemplate().find("from ResearchStaff rs where rs.contactMechanisms.type='EMAIL' and rs.contactMechanisms.value=?", emailAddress);
        return results.size() > 0 ? results.get(0) : null;
    }
}
