/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 *   Distributed under the OSI-approved BSD 3-Clause License.
 *   See https://github.com/NCIP/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.dao;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import edu.duke.cabig.c3pr.domain.DiseaseTerm;

/**
 * @author Krikor Krumlian
 */
public class DiseaseTermDao extends GridIdentifiableDao<DiseaseTerm> {
	
	private static final List<String> SUBSTRING_MATCH_PROPERTIES = Arrays.asList("ctepTerm");
    private static final List<String> EXACT_MATCH_PROPERTIES = Collections.emptyList();

    public Class<DiseaseTerm> domainClass() {
        return DiseaseTerm.class;
    }

    /**
     * Gets by category id.
     * 
     * @param parentId the parent id
     * 
     * @return DiseaseTerm
     */
    @SuppressWarnings("unchecked")
    public List<DiseaseTerm> getByCategoryId(Integer parentId) {
        return getHibernateTemplate().find("from DiseaseTerm dt where dt.category.id =?",
                        new Object[] { parentId });
    }

    /*
     * Primarily created for Generating test reports.
     */
    /**
     * Gets by ctepTerm .
     * 
     * @param parentId the parent id
     * 
     * @return DiseaseTerm
     */
    @SuppressWarnings("unchecked")
    public List<DiseaseTerm> getByCtepTerm(String ctepTerm) {
        return getHibernateTemplate().find("from DiseaseTerm dt where dt.ctepTerm =?", ctepTerm);
    }
    
    public List<DiseaseTerm> getBySubnames(String[] subnames) {
        return findBySubname(subnames, SUBSTRING_MATCH_PROPERTIES, EXACT_MATCH_PROPERTIES);
    }
}
