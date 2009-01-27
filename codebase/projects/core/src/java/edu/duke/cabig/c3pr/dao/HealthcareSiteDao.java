package edu.duke.cabig.c3pr.dao;

import java.util.Arrays;
import java.util.List;

import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.emory.mathcs.backport.java.util.Collections;
import edu.nwu.bioinformatics.commons.CollectionUtils;

/**
 * The Class HealthcareSiteDao.
 * 
 * @author Ramakrishna
 * @author kherm
 * @version 1.0
 */
public class HealthcareSiteDao extends GridIdentifiableDao<HealthcareSite> {

    /** The SUBSTRING_ match_ properties. */
    private List<String> SUBSTRING_MATCH_PROPERTIES = Arrays.asList("name", "nciInstituteCode");

    /** The EXACT_ match_ properties. */
    private List<String> EXACT_MATCH_PROPERTIES = Collections.emptyList();

    /* (non-Javadoc)
     * @see edu.duke.cabig.c3pr.dao.C3PRBaseDao#domainClass()
     */
    public Class<HealthcareSite> domainClass() {
        return HealthcareSite.class;
    }

    /*
     * Returns all HealthcarSite objects (non-Javadoc)
     * 
     * @see edu.duke.cabig.c3pr.dao.HealthcareSiteDao#getAll()
     */
    /**
     * Gets the all.
     * 
     * @return HealthcareSite
     */
    public List<HealthcareSite> getAll() {
        return getHibernateTemplate().find("from HealthcareSite");
    }
    
    /**
     * Clear.
     */
    public void clear() {
        getHibernateTemplate().clear();
    }

    /**
     * Gets by subnames.
     * 
     * @param subnames the subnames
     * 
     * @return the subnames
     */
    public List<HealthcareSite> getBySubnames(String[] subnames) {

        return findBySubname(subnames, SUBSTRING_MATCH_PROPERTIES, EXACT_MATCH_PROPERTIES);
    }

    /**
     * Gets by nci institute code.
     * 
     * @param nciInstituteCode the nci institute code
     * 
     * @return the HealthcareSite
     */
    public HealthcareSite getByNciInstituteCode(String nciInstituteCode) {
        return CollectionUtils.firstElement((List<HealthcareSite>) getHibernateTemplate().find(
                        "from HealthcareSite h where h.nciInstituteCode = ?", nciInstituteCode));
    }

}
