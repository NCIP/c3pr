package edu.duke.cabig.c3pr.dao;

import java.util.Arrays;
import java.util.List;

import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.emory.mathcs.backport.java.util.Collections;
import edu.nwu.bioinformatics.commons.CollectionUtils;

/**
 * @author Ramakrishna
 * @author kherm
 * @version 1.0
 */
public class HealthcareSiteDao extends GridIdentifiableDao<HealthcareSite> {

    private List<String> SUBSTRING_MATCH_PROPERTIES = Arrays.asList("name");

    private List<String> EXACT_MATCH_PROPERTIES = Collections.emptyList();

    public Class<HealthcareSite> domainClass() {
        return HealthcareSite.class;
    }

    /*
      * Returns all HealthcarSite objects
      * (non-Javadoc)
      * @see edu.duke.cabig.c3pr.dao.HealthcareSiteDao#getAll()
      */
    public List<HealthcareSite> getAll() {
        return getHibernateTemplate().find("from HealthcareSite");
    }

    public List<HealthcareSite> getBySubnames(String[] subnames) {

        SUBSTRING_MATCH_PROPERTIES = Arrays.asList("name");

        return findBySubname(subnames, SUBSTRING_MATCH_PROPERTIES,
                EXACT_MATCH_PROPERTIES);
    }

    public HealthcareSite getByNciInstituteCode(String nciInstituteCode){
        return CollectionUtils.firstElement((List<HealthcareSite>) getHibernateTemplate().
                find("from HealthcareSite h where h.nciInstituteCode = ?", nciInstituteCode)
        );
    }


}


