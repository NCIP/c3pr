package edu.duke.cabig.c3pr.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.springframework.transaction.annotation.Transactional;

import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.LocalHealthcareSite;
import edu.duke.cabig.c3pr.domain.Organization;
import gov.nih.nci.cabig.ctms.dao.MutableDomainObjectDao;
import gov.nih.nci.cabig.ctms.domain.DomainObject;

/**
 * The Class OrganizationDao.
 * 
 * @author Vinay Gangoli
 * @version 1.0
 */
public class OrganizationDao extends GridIdentifiableDao<HealthcareSite> implements
                MutableDomainObjectDao<HealthcareSite> {

    private static Log log = LogFactory.getLog(StudyDao.class);

    public Class<HealthcareSite> domainClass() {
        return HealthcareSite.class;
    }

    /**
     * Search by example.
     * 
     * @param hcs the hcs
     * @param isWildCard the is wild card
     * 
     * @return the list< healthcare site>
     */
    public List<HealthcareSite> searchByExample(HealthcareSite hcs, boolean isWildCard) {
        List<HealthcareSite> result = new ArrayList<HealthcareSite>();
        Example example = Example.create(hcs).excludeZeroes().ignoreCase();
        try {
            Criteria orgCriteria = getSession().createCriteria(Organization.class);
            orgCriteria.addOrder(Order.asc("name"));
            orgCriteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
            orgCriteria.setMaxResults(50);
            if (isWildCard) {
                example.enableLike(MatchMode.ANYWHERE);
            }
            example.excludeProperty("studyEndPointProperty");
            example.excludeProperty("registrationEndPointProperty");
            result = orgCriteria.add(example).list();
        }
        catch (Exception e) {
        	log.error(e.getMessage());
        }
        return result;
    }

    /**
     * Gets the by nci identifier.
     * 
     * @param nciIdentifier the nci identifier
     * 
     * @return the by nci identifier
     */
    public List<HealthcareSite> getByNciIdentifier(String nciIdentifier) {

        List<HealthcareSite> result = new ArrayList<HealthcareSite>();
        HealthcareSite hcs = new LocalHealthcareSite();
        hcs.setNciInstituteCode(nciIdentifier);
        Example example = Example.create(hcs).excludeZeroes().ignoreCase();
        try {
            Criteria orgCriteria = getSession().createCriteria(Organization.class);
            result = orgCriteria.add(example).list();
        }
        catch (Exception e) {
        	log.error(e.getMessage());
        }
        return result;
    }
    

    /* Saves a domain object
     * @see gov.nih.nci.cabig.ctms.dao.MutableDomainObjectDao#save(gov.nih.nci.cabig.ctms.domain.MutableDomainObject)
     */
    @Transactional(readOnly = false)
    public void save(HealthcareSite obj) {
        getHibernateTemplate().saveOrUpdate(obj);
    }
    
	/* Saves a domain object
     * @see edu.duke.cabig.c3pr.dao.C3PRBaseDao#merge(gov.nih.nci.cabig.ctms.domain.DomainObject)
     */
    @Transactional(readOnly = false)
	public Organization merge(DomainObject domainObject) {
		return (Organization)getHibernateTemplate().merge(domainObject);
	}

}
