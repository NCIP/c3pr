package edu.duke.cabig.c3pr.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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

import com.semanticbits.coppa.infrastructure.RemoteSession;

import edu.duke.cabig.c3pr.dao.query.ResearchStaffQuery;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.LocalResearchStaff;
import edu.duke.cabig.c3pr.domain.RemoteResearchStaff;
import edu.duke.cabig.c3pr.domain.ResearchStaff;

/**
 * Hibernate implementation of ResearchStaffDao.
 * 
 * @see edu.duke.cabig.c3pr.dao.ResearchStaffDao
 * @author Priyatam
 */
public class ResearchStaffDao extends GridIdentifiableDao<ResearchStaff> {

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
    
    private RemoteSession remoteSession;

    /* (non-Javadoc)
     * @see edu.duke.cabig.c3pr.dao.C3PRBaseDao#domainClass()
     */
    @Override
    public Class<ResearchStaff> domainClass() {
        return ResearchStaff.class;
    }

    /**
     * Gets  all ResearchStaff.
     * 
     * @return all ResearchStaff
     */
    public List<ResearchStaff> getAll() {
        return getHibernateTemplate().find("from ResearchStaff");
    }

    /**
     * Gets the by subnames.
     * 
     * @param subnames the subnames
     * @param healthcareSite the healthcare site
     * 
     * @return the by subnames
     */
    public List<ResearchStaff> getBySubnames(String[] subnames, int healthcareSite) {
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
    public List<ResearchStaff> getBySubNameAndSubEmail(String[] subnames, String nciInstituteCode) {
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
    public List<ResearchStaff> searchByExample(ResearchStaff staff, boolean isWildCard) {
        List<ResearchStaff> result = new ArrayList<ResearchStaff>();

        Example example = Example.create(staff).excludeZeroes().ignoreCase();
        example.excludeProperty("salt");
        example.excludeProperty("passwordLastSet");
        try {
            Criteria criteria = getSession().createCriteria(ResearchStaff.class);
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
    public List<ResearchStaff> searchResearchStaff(final ResearchStaffQuery query) {
        String queryString = query.getQueryString();
        log.debug("::: " + queryString.toString());
        return (List<ResearchStaff>) getHibernateTemplate().execute(new HibernateCallback() {

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
     * Gets the by nci identifier. Looks for local and remote
     * 
     * @param nciIdentifier the nci identifier
     * 
     * @return the by nci identifier
     */
    public ResearchStaff getByNciIdentifier(String nciIdentifier) {
        ResearchStaff result = null;

        try {
            result = (ResearchStaff)(getHibernateTemplate().find("from ResearchStaff rs where rs.nciIdentifier = '" +nciIdentifier+ "'").get(0));
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
        return getHibernateTemplate().find("from ResearchStaff rs where rs.contactMechanisms.value = '" +emailAddress+ "'");
    }
    
    /**
     * Gets the by unique identifier. Created for the remote research staff use case.
     * 
     * @param emailAddress the email address
     * 
     * @return the ResearchStaff List
     */
    public List<ResearchStaff> getByUniqueIdentifier(String emailAddress) {
    	List<ResearchStaff> researchStaffList = new ArrayList<ResearchStaff>();
    	researchStaffList.addAll(getHibernateTemplate().find("from LocalResearchStaff rs where rs.contactMechanisms.value = '" +emailAddress+ "'"));
    	researchStaffList.addAll(getHibernateTemplate().find("from RemoteResearchStaff rs where rs.uniqueIdentifier = '" +emailAddress+ "'"));
        return researchStaffList;
    }
    
    /**
     * Gets the research staff by organization nci institute code from the database and the remote datastore.
     * 
     * @param nciInstituteCode the nci institute code
     * 
     * @return the research staff by organization nci institute code
     */
    public List<ResearchStaff> getResearchStaffByOrganizationNCIInstituteCode(HealthcareSite healthcareSite) {
    	//First get the remote content
    	List<RemoteResearchStaff> remoteResearchStaffList =  getFromResolverUsingOrganization(healthcareSite);
    	//update the database with the remote content
    	updateDatabaseWithRemoteContent(remoteResearchStaffList);
    	
    	//run a query against the updated database to get all research staff
    	List<ResearchStaff> completeResearchStaffListFromDatabase =  
    		getHibernateTemplate().find("from ResearchStaff rs where rs.healthcareSite.nciInstituteCode = '" +healthcareSite.getNciInstituteCode()+ "'");
    	return completeResearchStaffListFromDatabase;
    }
    
    /**
     * Gets the research staff by organization nci institute code from the resolver.
     * 
     * @param nciInstituteCode the nci institute code
     * 
     * @return the research staff by organization nci institute code
     */
    public List<RemoteResearchStaff> getFromResolverUsingOrganization(HealthcareSite healthcareSite){
    	
    	RemoteResearchStaff remoteResearchStaff = new RemoteResearchStaff();
    	remoteResearchStaff.setHealthcareSite(healthcareSite);
    	
    	List<Object> objectList = remoteSession.find(remoteResearchStaff);
    	List<RemoteResearchStaff> researchStaffList = new ArrayList<RemoteResearchStaff>();
    	
    	RemoteResearchStaff tempRemoteResearchStaff;
    	for(Object object: objectList){
    		tempRemoteResearchStaff = (RemoteResearchStaff)object;
    		tempRemoteResearchStaff.setHealthcareSite(healthcareSite);
    		researchStaffList.add(tempRemoteResearchStaff);
    	}
    	return researchStaffList;
    }
    

    /**
     * Update database with remote content.
     * 
     * @param remoteResearchStaffList the remote research staff list
     */
    public void updateDatabaseWithRemoteContent(List<RemoteResearchStaff> remoteResearchStaffList){
    	
    	for (RemoteResearchStaff remoteResearchStaff: remoteResearchStaffList) {
    		List<ResearchStaff> researchStaffFromDatabase = getByUniqueIdentifier(remoteResearchStaff.getUniqueIdentifier());
   			if(researchStaffFromDatabase.size() > 0){
   				//this guy exists....copy latest remote data into the existing object...which is done by the interceptor
   			} else{
   				//this guy doesnt exist
   				getHibernateTemplate().save(remoteResearchStaff);
   			}
    	}
    }
    
    
	public void setRemoteSession(RemoteSession remoteSession) {
		this.remoteSession = remoteSession;
	}


}
