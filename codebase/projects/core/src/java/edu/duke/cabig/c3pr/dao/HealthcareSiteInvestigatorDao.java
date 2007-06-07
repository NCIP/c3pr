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
 * @author Priyatam
 */
public class HealthcareSiteInvestigatorDao extends AbstractBaseDao<HealthcareSiteInvestigator> {
	
	private static Log log = LogFactory.getLog(HealthcareSiteInvestigatorDao.class);
	
	private static final List<String> SUBSTRING_MATCH_PROPERTIES
		= Arrays.asList("investigator.firstName", "investigator.lastName");
	private static final List<String> EXACT_MATCH_PROPERTIES
		= Collections.emptyList();
	private static final List<Object> EXTRA_PARAMS
		= Collections.emptyList();
	
	public Class<HealthcareSiteInvestigator> domainClass() {
	        return HealthcareSiteInvestigator.class;
	 }
	
	/*
	 * Returns all HealthcarSite objects
	 * (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.dao.HealthcareSiteDao#getAll()
	 */
	 public List<HealthcareSiteInvestigator> getAll() {
		 return getHibernateTemplate().find("from HealthcareSiteInvestigator");
	 }

	 public List<HealthcareSiteInvestigator> getBySubnames(String[] subnames, int healthcareSite) {
	        return findBySubname(subnames,"o.healthcareSite.id = '"+healthcareSite+"'",EXTRA_PARAMS,
	            SUBSTRING_MATCH_PROPERTIES, EXACT_MATCH_PROPERTIES);
	 }	 			
	 
	 @SuppressWarnings("unchecked")
	 public HealthcareSiteInvestigator getSiteInvestigator(HealthcareSiteInvestigator site,
    		Investigator investigator) {
        return CollectionUtils.firstElement(
            (List<HealthcareSiteInvestigator>) getHibernateTemplate().find(
                "from HealthcareSiteInvestigator a where a.healthcareSite = ? and a.investigator = ?",
                new Object[] { site, investigator })
        );
	 } 
}