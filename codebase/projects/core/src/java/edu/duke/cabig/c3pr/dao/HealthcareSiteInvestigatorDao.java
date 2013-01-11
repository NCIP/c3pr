/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.dao;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.HealthcareSiteInvestigator;
import edu.duke.cabig.c3pr.domain.Investigator;
import edu.nwu.bioinformatics.commons.CollectionUtils;

/**
 * The Class HealthcareSiteInvestigatorDao.
 * 
 * @author Priyatam
 */
public class HealthcareSiteInvestigatorDao extends GridIdentifiableDao<HealthcareSiteInvestigator> {

    /** The log. */
    private static Log log = LogFactory.getLog(HealthcareSiteInvestigatorDao.class);

    /** The Constant SUBSTRING_MATCH_PROPERTIES. */
    private static final List<String> SUBSTRING_MATCH_PROPERTIES = Arrays.asList("investigator.firstName", "investigator.lastName", "investigator.assignedIdentifier");

    /** The Constant EXACT_MATCH_PROPERTIES. */
    private static final List<String> EXACT_MATCH_PROPERTIES = Collections.emptyList();

    /** The Constant EXTRA_PARAMS. */
    private static final List<Object> EXTRA_PARAMS = Collections.emptyList();
    
    /* (non-Javadoc)
     * @see edu.duke.cabig.c3pr.dao.C3PRBaseDao#domainClass()
     */
    public Class<HealthcareSiteInvestigator> domainClass() {
        return HealthcareSiteInvestigator.class;
    }

    /**
     * Gets all HealthcareSiteInvestigators.
     * 
     * @return the HealthcareSiteInvestigators
     */
    public List<HealthcareSiteInvestigator> getAll() {
        return getHibernateTemplate().find("from HealthcareSiteInvestigator");
    }

    /**
     * Gets the by subnames.
     * 
     * @param subnames the subnames
     * @param healthcareSite the healthcare site
     * 
     * @return the by subnames
     */
    public List<HealthcareSiteInvestigator> getBySubnames(String[] subnames, int healthcareSiteId) {
    	
//    	RemoteInvestigator remoteInvestigator = new RemoteInvestigator();
//    	HealthcareSiteInvestigator hcsi = new HealthcareSiteInvestigator();
//    	
//    	HealthcareSite healthcareSite = healthcareSiteDao.getById(healthcareSiteId);
//    	HealthcareSite healthcareSiteWithNciId = new LocalHealthcareSite();
//    	healthcareSiteWithNciId.setCtepCode(healthcareSite.getCtepCode());
//    	hcsi.setHealthcareSite(healthcareSiteWithNciId);
//    	remoteInvestigator.getHealthcareSiteInvestigators().add(hcsi);
//    	investigatorDao.getRemoteInvestigatorsAndUpdateDatabase(remoteInvestigator);
    	
    	return findBySubname(subnames, null, "o.healthcareSite.id = '" + healthcareSiteId + "'",
                        EXTRA_PARAMS, SUBSTRING_MATCH_PROPERTIES, EXACT_MATCH_PROPERTIES);
    }

    /**
     * Gets the site investigator.
     * 
     * @param site the site
     * @param investigator the investigator
     * 
     * @return the site investigator
     */
    @SuppressWarnings("unchecked")
    public HealthcareSiteInvestigator getSiteInvestigator(HealthcareSite site,
                    Investigator investigator) {
        return CollectionUtils
                	.firstElement((List<HealthcareSiteInvestigator>) getHibernateTemplate()
                       .find("from HealthcareSiteInvestigator a where a.healthcareSite = ? and a.investigator = ?",
                                            new Object[] { site, investigator }));
    }
    
    /**
     * Gets the by sub name and sub email.
     * 
     * @param subnames the subnames
     * @param nciInstituteCode the nci institute code
     * 
     * @return the by sub name and sub email
     */
    public List<HealthcareSiteInvestigator> getBySubNameAndSubEmail(String[] subnames) {
    	String queryTemplate = "LOWER(o.investigator.firstName) LIKE ? or LOWER(o.investigator.lastName) LIKE ? or LOWER(cm.value) LIKE ?";
    	return getHibernateTemplate().find("select distinct o from edu.duke.cabig.c3pr.domain.HealthcareSiteInvestigator o ," +
    			"IN (o.healthcareSite.identifiersAssignedToOrganization) AS I, ContactMechanism cm " +
    			"where  I.primaryIndicator = '1' and cm = any elements(o.investigator.contactMechanisms) " +
    			"and " + buildSubNameQuery(queryTemplate, subnames.length), buildSubNames(subnames, 3));
    }

    /**
     * Update database with remote content.
     * 
     * @param healthcareSiteInvestigator the healthcare site investigator
     * 
     * @return the healthcare site investigator
     */
    public HealthcareSiteInvestigator updateDatabaseWithRemoteContent(HealthcareSiteInvestigator healthcareSiteInvestigator){
    	HealthcareSiteInvestigator savedHealthcareSiteInvestigator = getSiteInvestigator(healthcareSiteInvestigator.getHealthcareSite(), healthcareSiteInvestigator.getInvestigator());
    	if(savedHealthcareSiteInvestigator == null){
    		log.debug("Saving the HealthcareSiteInvestigator.");
    		save(healthcareSiteInvestigator);
    		return healthcareSiteInvestigator;
    	} else {
    		log.debug("This HealthcareSiteInvestigator already exists in the database.");
    		return savedHealthcareSiteInvestigator;
    	}
    }
    
    /*
	 * Saves a domain object
	 */
	public void save(HealthcareSiteInvestigator healthcareSiteInvestigator) {
		getHibernateTemplate().saveOrUpdate(healthcareSiteInvestigator);
		getHibernateTemplate().flush();
	}


}
