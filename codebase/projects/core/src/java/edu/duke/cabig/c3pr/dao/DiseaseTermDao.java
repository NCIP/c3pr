package edu.duke.cabig.c3pr.dao;

import java.util.List;

import edu.duke.cabig.c3pr.domain.DiseaseTerm;

/**
 * @author Krikor Krumlian
 */
public class DiseaseTermDao extends GridIdentifiableDao<DiseaseTerm> {

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
}
