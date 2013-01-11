/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

import edu.duke.cabig.c3pr.domain.StudySite;

/**
 * Hibernate implementation of StudySiteDao
 *
 * @see edu.duke.cabig.c3pr.dao.StudySiteDao
 * @author Priyatam
 */
public class StudySiteDao extends GridIdentifiableDao<StudySite> {

    /* (non-Javadoc)
     * @see edu.duke.cabig.c3pr.dao.C3PRBaseDao#domainClass()
     */
    @Override
    public Class<StudySite> domainClass() {
        return StudySite.class;
    }
    
    private HealthcareSiteDao healthcareSiteDao;

    
    /**
     * Gets the StudySite by shortTitleText.
     * 
     * @param shortTitleText the shortTitleText
     * 
     * @return List of StudySite
     
    public List<StudySite> getStudySitesByShortTitle(String shortTitleText) {
    	List<StudySite> li = (List<StudySite>) getHibernateTemplate().find(
    			"select O from StudySite O, Study I, StudyVersion V where V.shortTitleText like ?"
					+ "and O.studyInternal.id=I.id and I.id=V.study.id", new Object[] {"%" + shortTitleText+ "%"});
    	return li;
    }*/
    
    
    /**
     * Reassociate.
     *
     * @param ss the ss
     */
    @Transactional(readOnly = false)
    public void reassociate(StudySite ss) {
        getHibernateTemplate().update(ss);
    }

    public void setHealthcareSiteDao(HealthcareSiteDao healthcareSiteDao) {
		this.healthcareSiteDao = healthcareSiteDao;
	}

	/**
     * Gets the by nci institute code.
     *
     * @param ctepCode the nci institute code
     *
     * @return the by nci institute code
     */
    @SuppressWarnings("unchecked")
	public List<StudySite> getBySitePrimaryIdentifier(String ctepCode) {
        return getHibernateTemplate().find(
           "Select s from StudySite s where " +
           "s.healthcareSite.identifiersAssignedToOrganization.value = ? and s.healthcareSite.identifiersAssignedToOrganization.primaryIndicator = '1'",
           new Object[] {ctepCode});
    }
    
    /**
     * Gets the by ctep code and short title.
     *
     * @param studyId the study id
     * @param sitePrimaryId the site primary id
     * @return the by site's Primary Identifier and short title
     */
    @SuppressWarnings("unchecked")
	public List<StudySite> getBySitePrimaryIdentifierAndStudyCoordinatingCenterIdentifier(String studyId, String sitePrimaryId) {
        return getHibernateTemplate().find(
           "Select s from StudySite s where s.studyInternal.identifiers.value = ? and s.studyInternal.identifiers.typeInternal = 'COORDINATING_CENTER_IDENTIFIER' and " +
           "s.healthcareSite.identifiersAssignedToOrganization.value = ? and s.healthcareSite.identifiersAssignedToOrganization.primaryIndicator = '1'",
           new Object[] {studyId, sitePrimaryId});
    }

    /**
     * Initialize.
     *
     * @param studySite the study site
     *
     * @throws DataAccessException the data access exception
     */
    @Transactional(readOnly = false)
    public void initialize(StudySite studySite) throws DataAccessException {
    	getHibernateTemplate().initialize(studySite.getStudySiteStudyVersions());
    	getHibernateTemplate().initialize(studySite.getSiteStatusHistoryInternal());
    	getHibernateTemplate().initialize(studySite.getStudyInternal());
    	healthcareSiteDao.initialize(studySite.getHealthcareSite());
    }

    /**
     * Merge.
     *
     * @param studySite the study site
     *
     * @return the study site
     */
    @Transactional(readOnly = false)
    public StudySite merge(StudySite studySite) {
        return (StudySite) getHibernateTemplate().merge(studySite);
    }
}
