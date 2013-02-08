/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/c3pr/LICENSE.txt for details.
 ******************************************************************************/
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
