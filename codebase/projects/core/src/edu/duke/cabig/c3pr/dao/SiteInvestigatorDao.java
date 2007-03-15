package edu.duke.cabig.c3pr.dao;

import edu.duke.cabig.c3pr.domain.HealthcareSiteInvestigator;
import edu.duke.cabig.c3pr.domain.Investigator;
import edu.nwu.bioinformatics.commons.CollectionUtils;

import java.util.List;

/**
 * @author Priyatam
 */
public class SiteInvestigatorDao extends AbstractBaseDao<HealthcareSiteInvestigator> {
    
	@Override
    public Class<HealthcareSiteInvestigator> domainClass() {
        return HealthcareSiteInvestigator.class;
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
