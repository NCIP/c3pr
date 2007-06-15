package edu.duke.cabig.c3pr.dao;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import edu.duke.cabig.c3pr.domain.DiseaseCategory;


/**
 * @author Priytam
 */
public class DiseaseCategoryDao extends GridIdentifiableDao<DiseaseCategory> {
    
    private static final List<String> SUBSTRING_MATCH_PROPERTIES = Arrays.asList("name");
    private static final List<String> EXACT_MATCH_PROPERTIES = Collections.emptyList();

	public Class<DiseaseCategory> domainClass() {
        return DiseaseCategory.class;
    }

	@SuppressWarnings("unchecked")
    public List<DiseaseCategory> getAll() {
        return getHibernateTemplate().find("from DiseaseCategory");
    }
    
    @SuppressWarnings("unchecked")
    public List<DiseaseCategory> getByParentId(Integer parentId) {
        return getHibernateTemplate().find("from DiseaseCategory dc where dc.parentCategory.id =?",
        		new Object[] { parentId });
    }
    
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
    
}
