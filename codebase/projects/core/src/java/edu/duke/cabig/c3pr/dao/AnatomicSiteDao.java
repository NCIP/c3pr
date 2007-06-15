package edu.duke.cabig.c3pr.dao;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import edu.duke.cabig.c3pr.domain.AnatomicSite;

/*
 * @author kulasekaran
 * 
 */
public class AnatomicSiteDao extends GridIdentifiableDao<AnatomicSite> {
	
	private static final List<String> SUBSTRING_MATCH_PROPERTIES
			= Arrays.asList("name");
	private static final List<String> EXACT_MATCH_PROPERTIES
			= Collections.emptyList();
	private static final List<Object> EXTRA_PARAMS
			= Collections.emptyList();
	
    @Override
	public Class<AnatomicSite> domainClass() {
        return AnatomicSite.class;
    } 
    
    public List<AnatomicSite> getBySubnames(String[] subnames) {
        return findBySubname(subnames,null,EXTRA_PARAMS,
            SUBSTRING_MATCH_PROPERTIES, EXACT_MATCH_PROPERTIES);
	}

    public List<AnatomicSite> getAll() {
        return getHibernateTemplate().find("from AnatomicSite");
    }
    
}

