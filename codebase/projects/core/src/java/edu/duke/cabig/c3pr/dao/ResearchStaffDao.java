package edu.duke.cabig.c3pr.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

import edu.duke.cabig.c3pr.dao.query.ResearchStaffQuery;
import edu.duke.cabig.c3pr.domain.LocalResearchStaff;
import edu.duke.cabig.c3pr.domain.ResearchStaff;
import edu.duke.cabig.c3pr.service.impl.CoppaResearchStaffServiceImpl;

/**
 * Hibernate implementation of ResearchStaffDao.
 * 
 * @see edu.duke.cabig.c3pr.dao.ResearchStaffDao
 * @author Priyatam
 */
public class ResearchStaffDao extends GridIdentifiableDao<LocalResearchStaff> {

    /** The log. */
    private static Log log = LogFactory.getLog(InvestigatorDao.class);

    /** The Constant SUBSTRING_MATCH_PROPERTIES. */
    private static final List<String> SUBSTRING_MATCH_PROPERTIES = Arrays.asList("firstName","lastName");
    
    /** The Constant SUBNAME_SUBEMAIL_MATCH_PROPERTIES. */
    private static final List<String> SUBNAME_SUBEMAIL_MATCH_PROPERTIES = Arrays.asList("firstName","lastName","contactMechanisms.value");

    /** The Constant EXACT_MATCH_PROPERTIES. */
    private static final List<String> EXACT_MATCH_PROPERTIES = Collections.emptyList();

    /** The Constant EXTRA_PARAMS. */
    private static final List<Object> EXTRA_PARAMS = Collections.emptyList();

    /* (non-Javadoc)
     * @see edu.duke.cabig.c3pr.dao.C3PRBaseDao#domainClass()
     */
    @Override
    public Class<LocalResearchStaff> domainClass() {
        return LocalResearchStaff.class;
    }
    
    private CoppaResearchStaffServiceImpl coppaResearchStaffServiceImpl = new CoppaResearchStaffServiceImpl();

    /**
     * Gets  all ResearchStaff.
     * 
     * @return all ResearchStaff
     */
    public List<ResearchStaff> getAll() {
    	
        List<ResearchStaff> researchStaffList = new ArrayList<ResearchStaff>();
        researchStaffList.addAll(coppaResearchStaffServiceImpl.getAll());
        researchStaffList.addAll(getHibernateTemplate().find("from LocalResearchStaff"));
        return getUniqueResearchStaffPersonnel(researchStaffList);
    }

    public CoppaResearchStaffServiceImpl getCoppaResearchStaffServiceImpl() {
		return coppaResearchStaffServiceImpl;
	}

	public void setCoppaResearchStaffServiceImpl(
			CoppaResearchStaffServiceImpl coppaResearchStaffServiceImpl) {
		this.coppaResearchStaffServiceImpl = coppaResearchStaffServiceImpl;
	}

	/**
     * Gets the by subnames.
     * 
     * @param subnames the subnames
     * @param healthcareSite the healthcare site
     * 
     * @return the by subnames
     */
    public List<LocalResearchStaff> getBySubnames(String[] subnames, int healthcareSite) {
        return findBySubname(subnames, "o.healthcareSite.id = '" + healthcareSite + "'",
                        EXTRA_PARAMS, SUBSTRING_MATCH_PROPERTIES, EXACT_MATCH_PROPERTIES);
    }
    
    /**
     * Gets the by sub name and sub email.
     * 
     * @param subnames the subnames
     * @param nciInstituteCode the nci institute code
     * 
     * @return the by sub name and sub email
     */
    public List<LocalResearchStaff> getBySubNameAndSubEmail(String[] subnames, String nciInstituteCode) {
        return findBySubname(subnames, "o.healthcareSite.nciInstituteCode = '" + nciInstituteCode + "'",
                        EXTRA_PARAMS, SUBNAME_SUBEMAIL_MATCH_PROPERTIES, EXACT_MATCH_PROPERTIES);
    }

    /**
     * Search by example.
     * 
     * @param staff the staff
     * @param isWildCard the is wild card
     * 
     * @return the list< research staff>
     */
    public List<LocalResearchStaff> searchByExample(LocalResearchStaff staff, boolean isWildCard) {
        List<LocalResearchStaff> result = new ArrayList<LocalResearchStaff>();

        Example example = Example.create(staff).excludeZeroes().ignoreCase();
        example.excludeProperty("salt");
        example.excludeProperty("passwordLastSet");
        try {
            Criteria criteria = getSession().createCriteria(LocalResearchStaff.class);
            criteria.addOrder(Order.asc("nciIdentifier"));
            criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

            if (isWildCard) {
                example.enableLike(MatchMode.ANYWHERE);
                criteria.add(example);
                if (staff.getHealthcareSite() != null) {
                    criteria.createCriteria("healthcareSite").add(
                               Restrictions.ilike("name", "%" + staff.getHealthcareSite().getName() + "%"));
                }
                result = criteria.list();
            }
            else {
                result = criteria.add(example).list();
            }
        }
        catch (Exception e) {
        	e.printStackTrace();
            log.error(e.getMessage());
        }
        return result;
    }

    
    /**
     * Search research staff.
     * 
     * @param query the query
     * 
     * @return the list< research staff>
     */
    @SuppressWarnings( { "unchecked" })
    public List<LocalResearchStaff> searchResearchStaff(final ResearchStaffQuery query) {
        String queryString = query.getQueryString();
        log.debug("::: " + queryString.toString());
        return (List<LocalResearchStaff>) getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(final Session session) throws HibernateException,
                            SQLException {
                org.hibernate.Query hiberanteQuery = session.createQuery(query.getQueryString());
                Map<String, Object> queryParameterMap = query.getParameterMap();
                for (String key : queryParameterMap.keySet()) {
                    Object value = queryParameterMap.get(key);
                    hiberanteQuery.setParameter(key, value);

                }
                return hiberanteQuery.list();
            }

        });
    }
    
    
    /**
     * Gets the by nci identifier.
     * 
     * @param nciIdentifier the nci identifier
     * 
     * @return the by nci identifier
     */
    public LocalResearchStaff getByNciIdentifier(String nciIdentifier) {
    	LocalResearchStaff result = null;
        LocalResearchStaff staff = new LocalResearchStaff();
        staff.setNciIdentifier(nciIdentifier);

        try {
            result = searchByExample(staff, false).get(0);
        }
        catch (Exception e) {
            log.debug("User with nciIdentifier " + nciIdentifier
                            + " does not exist. Returning null");
        }
        return result;
    }
    
    
    /**
     * Gets the by email address. Created for the notifications use case.
     * 
     * @param emailAddress the email address
     * 
     * @return the ResearchStaff List
     */
    public List<ResearchStaff> getByEmailAddress(String emailAddress) {
    	 List<ResearchStaff> researchStaffList = new ArrayList<ResearchStaff>();
         researchStaffList.addAll(getHibernateTemplate().find("from LocalResearchStaff rs where rs.contactMechanisms.value = '" +emailAddress+ "'"));
         researchStaffList.addAll(getHibernateTemplate().find("from RemoteResearchStaff rs where rs.contactMechanisms.value = '" +emailAddress+ "'"));
         return researchStaffList;
    }
    
    public List<ResearchStaff>  getUniqueResearchStaffPersonnel(List<ResearchStaff> researchStaffList){
    	Set<ResearchStaff> filteredResearchSet = new HashSet<ResearchStaff>();
    	filteredResearchSet.addAll(researchStaffList);
    	List<ResearchStaff> uniqueResearchStaff = new ArrayList<ResearchStaff>();
    	uniqueResearchStaff.addAll(filteredResearchSet);
    	return uniqueResearchStaff;
    }
    
}
