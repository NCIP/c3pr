package edu.duke.cabig.c3pr.dao;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import edu.duke.cabig.c3pr.domain.DiseaseCategory;

/**
 * The Class DiseaseCategoryDao.
 * 
 * @author Priytam
 */
public class DiseaseCategoryDao extends GridIdentifiableDao<DiseaseCategory> {

    /** The Constant SUBSTRING_MATCH_PROPERTIES. */
    private static final List<String> SUBSTRING_MATCH_PROPERTIES = Arrays.asList("name");

    /** The Constant EXACT_MATCH_PROPERTIES. */
    private static final List<String> EXACT_MATCH_PROPERTIES = Collections.emptyList();

    /* (non-Javadoc)
     * @see edu.duke.cabig.c3pr.dao.C3PRBaseDao#domainClass()
     */
    public Class<DiseaseCategory> domainClass() {
        return DiseaseCategory.class;
    }

    /**
     * Gets the all.
     * 
     * @return the all
     */
    @SuppressWarnings("unchecked")
    public List<DiseaseCategory> getAll() {
        return getHibernateTemplate().find("from DiseaseCategory");
    }

    /**
     * Gets the by parent id.
     * 
     * @param parentId the parent id
     * 
     * @return the by parent id
     */
    @SuppressWarnings("unchecked")
    public List<DiseaseCategory> getByParentId(Integer parentId) {
        return getHibernateTemplate().find("from DiseaseCategory dc where dc.parentCategory.id =?",
                        new Object[] { parentId });
    }

    /**
     * Gets disease categories by subname.
     * 
     * @param subnames the subnames
     * @param diseaseCategoryId the disease category id
     * 
     * @return disease categories by subname
     */
    public List<DiseaseCategory> getBySubname(String[] subnames, Integer diseaseCategoryId) {
        List<Object> extraParams = new LinkedList<Object>();
        StringBuilder extraConds = new StringBuilder("");
        if (diseaseCategoryId != null) {
            extraConds.append(" o.parentCategory.id = ?");
            extraParams.add(diseaseCategoryId);
        }
        else {
            extraConds.append(" o.parentCategory is null");
        }
        return findBySubname(subnames, extraConds.toString(), extraParams,
                        SUBSTRING_MATCH_PROPERTIES, EXACT_MATCH_PROPERTIES);
    }
    
    /**
     * Initialize.
     * 
     * @param diseaseCategory the disease category
     */
    public void initialize(DiseaseCategory diseaseCategory){
    	getHibernateTemplate().initialize(diseaseCategory.getTerms());
    	getHibernateTemplate().initialize(diseaseCategory.getChildCategories());
    }
}
