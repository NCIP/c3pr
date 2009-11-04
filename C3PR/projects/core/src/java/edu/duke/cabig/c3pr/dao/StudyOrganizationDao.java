package edu.duke.cabig.c3pr.dao;

import edu.duke.cabig.c3pr.domain.StudyOrganization;

/**
 * Hibernate implementation of StudySiteDao
 * 
 * @see StudyOrganizationDao
 * @author Priyatam
 */
public class StudyOrganizationDao extends GridIdentifiableDao<StudyOrganization> {

    @Override
    public Class<StudyOrganization> domainClass() {
        return StudyOrganization.class;
    }
    
    

}
