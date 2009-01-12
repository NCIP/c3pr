package edu.duke.cabig.c3pr.dao;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import edu.duke.cabig.c3pr.domain.AnatomicSite;

// TODO: Auto-generated Javadoc
/*
 * @author kulasekaran
 * 
 */
/**
 * The Class AnatomicSiteDao.
 */
public class AnatomicSiteDao extends GridIdentifiableDao<AnatomicSite> {

    /** The Constant SUBSTRING_MATCH_PROPERTIES. */
    private static final List<String> SUBSTRING_MATCH_PROPERTIES = Arrays.asList("name");

    /** The Constant EXACT_MATCH_PROPERTIES. */
    private static final List<String> EXACT_MATCH_PROPERTIES = Collections.emptyList();

    /** The Constant EXTRA_PARAMS. */
    private static final List<Object> EXTRA_PARAMS = Collections.emptyList();

    /* (non-Javadoc)
     * @see edu.duke.cabig.c3pr.dao.C3PRBaseDao#domainClass()
     */
    @Override
    public Class<AnatomicSite> domainClass() {
        return AnatomicSite.class;
    }

    /**
     * Gets the anatomic sites by subnames.
     * 
     * @param subnames the subnames
     * 
     * @return the by subnames
     */
    public List<AnatomicSite> getBySubnames(String[] subnames) {
        return findBySubname(subnames, null, EXTRA_PARAMS, SUBSTRING_MATCH_PROPERTIES,
                        EXACT_MATCH_PROPERTIES);
    }
}
