package edu.duke.cabig.c3pr.dao.passwordpolicy;

import edu.duke.cabig.c3pr.dao.GridIdentifiableDao;
import edu.duke.cabig.c3pr.domain.passwordpolicy.PasswordPolicy;
import gov.nih.nci.cabig.ctms.dao.MutableDomainObjectDao;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public class PasswordPolicyDao extends GridIdentifiableDao<PasswordPolicy> implements
                MutableDomainObjectDao<PasswordPolicy> {

    /**
     * Get the Class representation of the domain object that this DAO is representing.
     * 
     * @return Class representation of the domain object that this DAO is representing.
     */
    @Override
    public Class<PasswordPolicy> domainClass() {
        return PasswordPolicy.class;
    }

    /**
     * Save or update the password policy in the db.
     * 
     * @param The
     *                password policy.
     */
    @Transactional(readOnly = false)
    public void save(final PasswordPolicy passwordPolicy) {
        getHibernateTemplate().saveOrUpdate(passwordPolicy);
    }
}
