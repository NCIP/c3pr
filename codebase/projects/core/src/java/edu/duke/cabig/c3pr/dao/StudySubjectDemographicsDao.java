/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

import edu.duke.cabig.c3pr.domain.Address;
import edu.duke.cabig.c3pr.domain.ContactMechanism;
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
    private static Log log = LogFactory.getLog(StudySubjectDemographicsDao.class); /** The participant dao. */
    private ParticipantDao participantDao;
    
    public ParticipantDao getParticipantDao() {
		return participantDao;
	}

	public void setParticipantDao(ParticipantDao participantDao) {
		this.participantDao = participantDao;
	}



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
     * @param studySubjectDemographics the studySubjectDemographics
     * 
     * @throws DataAccessException the data access exception
     */
    @Transactional(readOnly = false)
    public void initialize(StudySubjectDemographics studySubjectDemographics) throws DataAccessException {
    	participantDao.initialize(studySubjectDemographics.getMasterSubject());
    	getHibernateTemplate().initialize(studySubjectDemographics.getIdentifiers());
      
        for(OrganizationAssignedIdentifier identifier : studySubjectDemographics.getOrganizationAssignedIdentifiers()){
        	getHibernateTemplate().initialize(identifier.getHealthcareSite().getIdentifiersAssignedToOrganization());
        }
        getHibernateTemplate().initialize(studySubjectDemographics.getContactMechanisms());
        for(ContactMechanism contactMechanism : studySubjectDemographics.getContactMechanisms()){
        	getHibernateTemplate().initialize(contactMechanism.getContactMechanismUseAssociation());
        }
        getHibernateTemplate().initialize(studySubjectDemographics.getRaceCodeAssociations());
        getHibernateTemplate().initialize(studySubjectDemographics.getCustomFieldsInternal());
        getHibernateTemplate().initialize(studySubjectDemographics.getAddress().getAddressUseAssociation());
    }


    /* (non-Javadoc)
     * @see gov.nih.nci.cabig.ctms.dao.MutableDomainObjectDao#save(gov.nih.nci.cabig.ctms.domain.MutableDomainObject)
     */
    @Transactional(readOnly = false)
	public void save(StudySubjectDemographics studySubjectDemographics) {
		 getHibernateTemplate().saveOrUpdate(studySubjectDemographics);
	}
}
