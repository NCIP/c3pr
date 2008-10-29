package edu.duke.cabig.c3pr.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyOrganization;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.StudySubject;

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

    /*
     * Returns all StudySite objects (non-Javadoc)
     * 
     * @see edu.duke.cabig.c3pr.dao.StudySite#getAll()
     */
    public List<StudyOrganization> getAll() {
        return getHibernateTemplate().find("from StudyOrganization");
    }

    @Transactional(readOnly = false)
    public void reassociate(StudyOrganization ss) {
        getHibernateTemplate().update(ss);
    }

    @Transactional(readOnly = false)
    public StudyOrganization merge(StudyOrganization studyOrganization) {
        return (StudyOrganization) getHibernateTemplate().merge(studyOrganization);
    }
}
