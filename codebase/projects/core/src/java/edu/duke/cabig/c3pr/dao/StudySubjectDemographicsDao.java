package edu.duke.cabig.c3pr.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

import edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.StudySubjectDemographics;
import gov.nih.nci.cabig.ctms.dao.MutableDomainObjectDao;

/**
 * The Class StudySubjectDemographicsDao.
 * 
 * @author Ramakrishna
 */
public class StudySubjectDemographicsDao extends GridIdentifiableDao<StudySubjectDemographics> implements
                MutableDomainObjectDao<StudySubjectDemographics> {

    /** The log. */
    private static Log log = LogFactory.getLog(StudySubjectDemographicsDao.class);

    /* (non-Javadoc)
     * @see edu.duke.cabig.c3pr.dao.C3PRBaseDao#domainClass()
     */
    public Class<StudySubjectDemographics> domainClass() {
        return StudySubjectDemographics.class;
    }

  

    /* (non-Javadoc)
     * @see gov.nih.nci.cabig.ctms.dao.AbstractDomainObjectDao#getById(int)
     */
    @Override
    public StudySubjectDemographics getById(int id) {
    	StudySubjectDemographics studySubjectDemographics = super.getById(id);
        initialize(studySubjectDemographics);
        return studySubjectDemographics;
    }

    /**
     * Initialize.
     * 
     * @param participant the participant
     * 
     * @throws DataAccessException the data access exception
     */
    @Transactional(readOnly = false)
    public void initialize(StudySubjectDemographics studySubjectDemographics) throws DataAccessException {
        getHibernateTemplate().initialize(studySubjectDemographics.getIdentifiers());
        for(OrganizationAssignedIdentifier identifier : studySubjectDemographics.getOrganizationAssignedIdentifiers()){
        	getHibernateTemplate().initialize(identifier.getHealthcareSite().getIdentifiersAssignedToOrganization());
        }
        getHibernateTemplate().initialize(studySubjectDemographics.getContactMechanisms());
        getHibernateTemplate().initialize(studySubjectDemographics.getRaceCodes());
        getHibernateTemplate().initialize(studySubjectDemographics.getCustomFieldsInternal());
    }


    /* (non-Javadoc)
     * @see gov.nih.nci.cabig.ctms.dao.MutableDomainObjectDao#save(gov.nih.nci.cabig.ctms.domain.MutableDomainObject)
     */
    @Transactional(readOnly = false)
	public void save(StudySubjectDemographics studySubjectDemographics) {
		 getHibernateTemplate().saveOrUpdate(studySubjectDemographics);
	}
}
