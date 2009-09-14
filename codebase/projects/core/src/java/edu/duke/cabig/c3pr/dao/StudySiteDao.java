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

    /**
     * Reassociate.
     *
     * @param ss the ss
     */
    @Transactional(readOnly = false)
    public void reassociate(StudySite ss) {
        getHibernateTemplate().update(ss);
    }

    /**
     * Gets the by nci institute code.
     *
     * @param ctepCode the nci institute code
     *
     * @return the by nci institute code
     */
    public List<StudySite> getByCtepCode(String ctepCode) {
        return getHibernateTemplate().find(
           "Select s from StudySite s where " +
           "s.healthcareSite.identifiersAssignedToOrganization.value = ? and s.healthcareSite.identifiersAssignedToOrganization.primaryIndicator = 'TRUE')",
           new Object[] {ctepCode});
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
    	getHibernateTemplate().initialize(studySite.getStudySubjects());
    	getHibernateTemplate().initialize(studySite.getStudySiteStudyVersions());
    	getHibernateTemplate().initialize(studySite.getSiteStatusHistory());
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
