package edu.duke.cabig.c3pr.dao;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.HealthcareSiteInvestigator;
import edu.duke.cabig.c3pr.domain.Investigator;
import edu.emory.mathcs.backport.java.util.Collections;
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
    private static final List<String> SUBSTRING_MATCH_PROPERTIES = Arrays.asList("investigator.firstName", "investigator.lastName");

    /** The Constant EXACT_MATCH_PROPERTIES. */
    private static final List<String> EXACT_MATCH_PROPERTIES = Collections.emptyList();

    /** The Constant EXTRA_PARAMS. */
    private static final List<Object> EXTRA_PARAMS = Collections.emptyList();
    
    /** The Constant SUBNAME_SUBEMAIL_MATCH_PROPERTIES. */
    private static final List<String> SUBNAME_SUBEMAIL_MATCH_PROPERTIES = Arrays.asList("investigator.firstName","investigator.lastName","investigator.contactMechanisms.value");
    

    /* (non-Javadoc)
     * @see edu.duke.cabig.c3pr.dao.C3PRBaseDao#domainClass()
     */
    public Class<HealthcareSiteInvestigator> domainClass() {
        return HealthcareSiteInvestigator.class;
    }

    /*
     * Returns all HealthcarSite objects (non-Javadoc)
     * 
     * @see edu.duke.cabig.c3pr.dao.HealthcareSiteDao#getAll()
     */
    /**
     * Gets the all.
     * 
     * @return the all
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
    public List<HealthcareSiteInvestigator> getBySubnames(String[] subnames, int healthcareSite) {
        return findBySubname(subnames, "o.healthcareSite.id = '" + healthcareSite + "'",
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
                                        .find(
                                                        "from HealthcareSiteInvestigator a where a.healthcareSite = ? and a.investigator = ?",
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
    public List<HealthcareSiteInvestigator> getBySubNameAndSubEmail(String[] subnames, String nciInstituteCode) {
        return findBySubname(subnames, 
        		"o.healthcareSite.nciInstituteCode = '"+ nciInstituteCode + "'",
                 EXTRA_PARAMS, SUBNAME_SUBEMAIL_MATCH_PROPERTIES, EXACT_MATCH_PROPERTIES);
    }
    
    
    
}