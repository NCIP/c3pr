/*
 *  @author Kruttik,Ramakrishna
 */
package edu.duke.cabig.c3pr.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.transaction.annotation.Transactional;

import edu.duke.cabig.c3pr.domain.CompanionStudyAssociation;
import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.ScheduledEpoch;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.SystemAssignedIdentifier;
import edu.emory.mathcs.backport.java.util.Collections;
import gov.nih.nci.cabig.ctms.dao.MutableDomainObjectDao;

/**
 * The Class StudySubjectDao.
 */
public class StudySubjectDao extends GridIdentifiableDao<StudySubject> implements
                MutableDomainObjectDao<StudySubject> {

    /** The SUBSTRIN g_ matc h_ properties. */
    private List<String> SUBSTRING_MATCH_PROPERTIES = Arrays
                    .asList("studySite.study.shortTitleText");

    /** The EXAC t_ matc h_ properties. */
    private List<String> EXACT_MATCH_PROPERTIES = Collections.emptyList();

    /** The log. */
    private static Log log = LogFactory.getLog(StudySubjectDao.class);

    /** The study dao. */
    private StudyDao studyDao;
    
    /** The study site dao. */
    private StudySiteDao studySiteDao;
    
    /** The participant dao. */
    private ParticipantDao participantDao;
    
    /**
	 * Sets the study dao.
	 * 
	 * @param studyDao
	 *            the new study dao
	 */
    public void setStudyDao(StudyDao studyDao) {
        this.studyDao = studyDao;
    }

    /**
	 * Instantiates a new study subject dao.
	 */
    public StudySubjectDao() {
    }

    /* (non-Javadoc)
     * @see edu.duke.cabig.c3pr.dao.C3PRBaseDao#domainClass()
     */
    @Override
    public Class<StudySubject> domainClass() {
        return StudySubject.class;
    }

    /**
	 * Advanced search.
	 * 
	 * @param registration
	 *            the registration
	 * @param startDate
	 *            the start date
	 * @param endDate
	 *            the end date
	 * @param ccId
	 *            the cc id
	 * 
	 * @return list of matching registration objects based on the date present
	 *         in the sample object that is passsed in. Also takes the date
	 *         range(startDate, endDate) and gets all objects having their
	 *         informedConsentSignedDate between these two dates. list of
	 *         StudySubject objects.
	 */
    public List<StudySubject> advancedSearch(StudySubject registration, Date startDate,
                    Date endDate, String ccId) {

        Criteria registrationCriteria = getHibernateTemplate().getSessionFactory()
                        .getCurrentSession().createCriteria(StudySubject.class);

        Criteria studySiteCriteria = registrationCriteria.createCriteria("studySite");
        Criteria participantCriteria = registrationCriteria.createCriteria("participant");
        Criteria studyCriteria = studySiteCriteria.createCriteria("study");
        Criteria siteCriteria = studySiteCriteria.createCriteria("healthcareSite");
        Criteria identifiersCriteria = studyCriteria.createCriteria("identifiers");

        // Study Criteria
        if (registration.getStudySite().getStudy().getShortTitleText() != null
                        && !registration.getStudySite().getStudy().getShortTitleText().equals("")) {
            studyCriteria.add(Expression.ilike("shortTitleText", "%"
                            + registration.getStudySite().getStudy().getShortTitleText() + "%"));
        }

        // Site criteria
        if (registration.getStudySite().getHealthcareSite().getName() != null
                        && !registration.getStudySite().getHealthcareSite().getName().equals("")) {
            siteCriteria.add(Expression.ilike("name", "%"
                            + registration.getStudySite().getHealthcareSite().getName() + "%"));
        }
        if (registration.getStudySite().getHealthcareSite().getNciInstituteCode() != null
                        && !registration.getStudySite().getHealthcareSite().getNciInstituteCode()
                                        .equals("")) {
            siteCriteria.add(Expression.ilike("nciInstituteCode", "%"
                            + registration.getStudySite().getHealthcareSite().getNciInstituteCode()
                            + "%"));
        }

        // registration criteria
        if (startDate != null && endDate != null) {
            registrationCriteria.add(Expression.between("informedConsentSignedDate", startDate, endDate));
        }else if(startDate != null){
        	registrationCriteria.add(Expression.ge("informedConsentSignedDate", startDate));
        }else if(endDate != null){
        	registrationCriteria.add(Expression.le("informedConsentSignedDate", endDate));
        }

        // participant/subject criteria
        if (registration.getParticipant().getBirthDate() != null) {
            participantCriteria.add(Expression.eq("birthDate", registration.getParticipant()
                            .getBirthDate()));
        }
        if (registration.getParticipant().getRaceCode() != null
                        && !registration.getParticipant().getRaceCode().equals("")) {
            participantCriteria.add(Expression.ilike("raceCode", "%" + registration.getParticipant()
                            .getRaceCode() + "%" )  );
        }

        if (ccId != null && !ccId.equals("")) {
            identifiersCriteria.add(Expression.ilike("value", "%" + ccId + "%"));
        }
        registrationCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        registrationCriteria.addOrder(Order.asc("id"));
        return registrationCriteria.list();

    }

    /*
     * This is the advanced search method used for Study Reports generation.
     */
    /**
	 * Advanced study search.
	 * 
	 * @param studySubject
	 *            the study subject
	 * 
	 * @return the list< study subject>
	 */
    public List<StudySubject> advancedStudySearch(StudySubject studySubject) {
        Criteria studySubjectCriteria = getHibernateTemplate().getSessionFactory()
                        .getCurrentSession().createCriteria(StudySubject.class);

        Criteria studySiteCriteria = studySubjectCriteria.createCriteria("studySite");
        Criteria participantCriteria = studySubjectCriteria.createCriteria("participant");
        Criteria studyCriteria = studySiteCriteria.createCriteria("study");
        Criteria sIdentifiersCriteria = studyCriteria.createCriteria("identifiers");
        Criteria pIdentifiersCriteria = participantCriteria.createCriteria("identifiers");

        // Study Criteria
        if (studySubject.getStudySite().getStudy().getShortTitleText() != null
                        && !studySubject.getStudySite().getStudy().getShortTitleText().equals("")) {
            studyCriteria.add(Expression.ilike("shortTitleText", "%"
                            + studySubject.getStudySite().getStudy().getShortTitleText() + "%"));
        }
        if (studySubject.getStudySite().getStudy().getIdentifiers().size() > 0) {
            if (studySubject.getStudySite().getStudy().getIdentifiers().get(0).getValue() != null
                            && studySubject.getStudySite().getStudy().getIdentifiers().get(0)
                                            .getValue() != "") {
                sIdentifiersCriteria.add(Expression.ilike("value", "%"
                                + studySubject.getStudySite().getStudy().getIdentifiers().get(0)
                                                .getValue() + "%"));
            }
        }

        // participant/subject criteria
        if (studySubject.getParticipant().getFirstName() != null
                        && !studySubject.getParticipant().getFirstName().equals("")) {
            participantCriteria.add(Expression.ilike("firstName", "%"
                            + studySubject.getParticipant().getFirstName() + "%"));
        }
        if (studySubject.getParticipant().getLastName() != null
                        && !studySubject.getParticipant().getLastName().equals("")) {
            participantCriteria.add(Expression.ilike("lastName", "%"
                            + studySubject.getParticipant().getLastName() + "%"));
        }
        if (studySubject.getParticipant().getIdentifiers().size() > 0) {
            if (studySubject.getParticipant().getIdentifiers().get(0).getValue() != null
                            && !studySubject.getParticipant().getIdentifiers().get(0).getValue()
                                            .equals("")) {
                pIdentifiersCriteria.add(Expression.ilike("value", "%"
                                + studySubject.getParticipant().getIdentifiers().get(0).getValue()
                                + "%"));
            }
        }

        studySubjectCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return studySubjectCriteria.list();
    }

    /*
     * Used by searchRegistrationsController
     */
    /**
	 * Search by participant.
	 * 
	 * @param participant
	 *            the participant
	 * 
	 * @return the list< study subject>
	 */
    public List<StudySubject> searchByParticipant(Participant participant){
    	List<StudySubject> registrations = new ArrayList<StudySubject>();
    	
    	List<Participant> participantList = participantDao.searchByExample(participant);
        Set<Participant> participantSet = new TreeSet<Participant>();
        participantSet.addAll(participantList);
        List<Participant> uniqueParticipants = new ArrayList<Participant>();
        uniqueParticipants.addAll(participantSet);
        for (Participant partVar : uniqueParticipants) {
            registrations.addAll(partVar.getStudySubjects());
        }
        return registrations;
    }
    

    /*
     * Used by searchRegistrationsController
     */
	/**
	 * Search by participant id.
	 * 
	 * @param participantId
	 *            the participant id
	 * 
	 * @return the list< study subject>
	 */
    public List<StudySubject> searchByParticipantId(Integer participantId) {
		return participantDao.getById(participantId).getStudySubjects();
	}
    
	/*
     * Used by searchRegistrationsController
     */
	/**
	 * Search by study.
	 * 
	 * @param study
	 *            the study
	 * 
	 * @return the list< study subject>
	 */
	public List<StudySubject> searchByStudy(Study study) {
		List<StudySubject> registrations = new ArrayList<StudySubject>();
		List<Study> studies = studyDao.searchByExample(study, true);
        Set<Study> studySet = new TreeSet<Study>();
        List<Study> uniqueStudies = new ArrayList<Study>();
        studySet.addAll(studies);
        uniqueStudies.addAll(studySet);
        for (Study studyVar : uniqueStudies) {
            for (StudySite studySite : studyVar.getStudySites()) {
                for (StudySubject studySubject : studySite.getStudySubjects()) {
                    registrations.add(studySubject);
                }
            }
        }
        return registrations;
	}
	
	/*
     * Used by searchRegistrationsController
     */
	/**
	 * Search by study id.
	 * 
	 * @param studyId
	 *            the study id
	 * 
	 * @return the list< study subject>
	 */
	public List<StudySubject> searchByStudyId(Integer studyId) {
		List<StudySubject> registrations = new ArrayList<StudySubject>();
		Study study = studyDao.getById(studyId);
        for (StudySite studySite : study.getStudySites()) {
            for (StudySubject studySubject : studySite.getStudySubjects()) {
                registrations.add(studySubject);
            }
        }
        if(study.getCompanionIndicator()){
        	for(CompanionStudyAssociation companionStudyAssociation : study.getParentStudyAssociations()){
        		for(StudySite studySite : companionStudyAssociation.getStudySites()){
        			for (StudySubject studySubject : studySite.getStudySubjects()) {
                        registrations.add(studySubject);
                    }
        		}
        	}
        }
        return registrations;
	}
	
    /**
	 * *.
	 * 
	 * @param registration
	 *            the registration
	 * @param isWildCard
	 *            the is wild card
	 * @param maxResults
	 *            the max results
	 * 
	 * @return list of matching registration objects based on your sample
	 *         registration object
	 */
    public List<StudySubject> searchByExample(StudySubject registration, boolean isWildCard,  int maxResults) {
        List<StudySubject> result = new ArrayList<StudySubject>();

        Example example = Example.create(registration).excludeZeroes().ignoreCase();
        try {
            Criteria studySubjectCriteria = getSession().createCriteria(StudySubject.class);
            studySubjectCriteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

            if (isWildCard) {
                example.excludeProperty("doNotUse").enableLike(MatchMode.ANYWHERE);
                studySubjectCriteria.add(example);

                if (maxResults > 0) studySubjectCriteria.setMaxResults(maxResults);

                if (registration.getIdentifiers().size() > 1) {
                    studySubjectCriteria.createCriteria("identifiers").add(
                                    Restrictions.ilike("value", "%"
                                                    + registration.getIdentifiers().get(0)
                                                                    .getValue() + "%")).add(
                                    Restrictions.ilike("value", "%"
                                                    + registration.getIdentifiers().get(1)
                                                                    .getValue() + "%"));
                }
                else if (registration.getIdentifiers().size() > 0) {
                    studySubjectCriteria.createCriteria("identifiers").add(
                                    Restrictions.ilike("value", "%"
                                                    + registration.getIdentifiers().get(0)
                                                                    .getValue() + "%"));
                }
                result = studySubjectCriteria.list();
            }
            result = studySubjectCriteria.add(example).list();
        }
        catch (Exception e) {
            log.error(e.getMessage());
        }
        return result;
    }

    /**
	 * *.
	 * 
	 * @param registration
	 *            the registration
	 * 
	 * @return list of matching registration objects based on your sample
	 *         registration object
	 */
    public List<StudySubject> searchBySubjectAndStudySite(StudySubject registration) {

        Example example = Example.create(registration).excludeZeroes().ignoreCase();
        Criteria registrationCriteria = getHibernateTemplate().getSessionFactory()
                        .getCurrentSession().createCriteria(StudySubject.class);
        registrationCriteria.add(example);
        registrationCriteria.createCriteria("participant").add(
                        Restrictions.like("id", registration.getParticipant().getId()));
        registrationCriteria.createCriteria("studySite").add(
                        Restrictions.like("id", registration.getStudySite().getId()));
        return registrationCriteria.list();

    }

    /**
	 * Search by scheduled epoch.
	 * 
	 * @param scheduledEpoch
	 *            the scheduled epoch
	 * 
	 * @return the list< study subject>
	 */
    public List<StudySubject> searchByScheduledEpoch(ScheduledEpoch scheduledEpoch) {

        StudySubject registration = new StudySubject(true);
        Example example = Example.create(registration).excludeZeroes().ignoreCase();
        Criteria registrationCriteria = getHibernateTemplate().getSessionFactory()
                        .getCurrentSession().createCriteria(StudySubject.class);
        registrationCriteria.add(example);
        registrationCriteria.createCriteria("scheduledEpochs").createCriteria("epoch").add(
                        Restrictions.like("id", scheduledEpoch.getEpoch().getId()));
        return registrationCriteria.list();

    }

    /**
	 * Gets the all.
	 * 
	 * @return the all
	 * 
	 * @throws DataAccessException
	 *             the data access exception
	 */
    public List<StudySubject> getAll() throws DataAccessException {
        return getHibernateTemplate().find("from StudySubject");
    }

    /**
	 * Search by example.
	 * 
	 * @param registration
	 *            the registration
	 * @param maxResults
	 *            the max results
	 * 
	 * @return the list< study subject>
	 */
    public List<StudySubject> searchByExample(StudySubject registration,  int maxResults) {
        return searchByExample(registration, true, maxResults);
    }

    
    /**
	 * Gets the incomplete registrations.
	 * 
	 * @param registration
	 *            the registration
	 * @param maxResults
	 *            the max results
	 * 
	 * @return the incomplete registrations
	 */
    public List<StudySubject> getIncompleteRegistrations(int maxResults){
    	HibernateTemplate template = getHibernateTemplate() ;
    	if(maxResults > 0){
    		template.setMaxResults(maxResults);
    	}
    	List<StudySubject> studySubjects = (List<StudySubject>) template.find( "select ss from StudySubject ss join ss.scheduledEpochs se where  (se.scEpochWorkflowStatus = 'PENDING' OR se.scEpochWorkflowStatus = 'REGISTERED_BUT_NOT_RANDOMIZED')");
    	for(StudySubject studySubject : studySubjects){
    		initialize(studySubject);
    	}
    	return studySubjects ;
    }
    
    /**
	 * Initialize.
	 * 
	 * @param studySubject
	 *            the study subject
	 * 
	 * @throws DataAccessException
	 *             the data access exception
	 */
    @Transactional(readOnly = false)
    public void initialize(StudySubject studySubject) throws DataAccessException {
        studyDao.initialize(studySubject.getStudySite().getStudy());
        studySiteDao.initialize(studySubject.getStudySite());
        participantDao.initialize(studySubject.getParticipant());
        
        getHibernateTemplate().initialize(studySubject.getStudySite().getStudyInvestigatorsInternal());
        getHibernateTemplate().initialize(studySubject.getStudySite().getStudyPersonnelInternal());
        getHibernateTemplate().initialize(studySubject.getChildStudySubjects());
        getHibernateTemplate().initialize(studySubject.getConsentHistoryList());
        
        getHibernateTemplate().initialize(studySubject.getParticipant().getIdentifiers());
        getHibernateTemplate().initialize(studySubject.getParticipant().getRaceCodes());
        getHibernateTemplate().initialize(studySubject.getParticipant().getContactMechanisms());
        
        getHibernateTemplate().initialize(studySubject.getScheduledEpochs());
        getHibernateTemplate().initialize(studySubject.getIdentifiers());
        for(ScheduledEpoch scheduledEpoch: studySubject.getScheduledEpochs()){
            getHibernateTemplate().initialize(scheduledEpoch.getScheduledArmsInternal());
            getHibernateTemplate().initialize(scheduledEpoch.getSubjectEligibilityAnswersInternal());
            getHibernateTemplate().initialize(scheduledEpoch.getSubjectStratificationAnswersInternal());
        }
        
        for(StudySubject childStudySubject : studySubject.getChildStudySubjects()){
        	initialize(childStudySubject);
        }
    }
    
    /**
	 * Search by sys identifier.
	 * 
	 * @param id
	 *            the id
	 * 
	 * @return the list< study subject>
	 */
    @SuppressWarnings("unchecked")
    public List<StudySubject> searchBySysIdentifier(SystemAssignedIdentifier id) {
        return (List<StudySubject>) getHibernateTemplate()
                        .find(
                                        "select S from StudySubject S, SystemAssignedIdentifier I where I.systemName=?"
                                                        + " and I.value=? and I.type=? and I=any elements(S.identifiers)",
                                        new Object[] { id.getSystemName(),
                                                id.getValue(), id.getType() });
    }
    
    /**
	 * Search by org identifier.
	 * 
	 * @param id
	 *            the id
	 * 
	 * @return the list< study subject>
	 */
    @SuppressWarnings("unchecked")
    public List<StudySubject> searchByOrgIdentifier(OrganizationAssignedIdentifier id) {
        return (List<StudySubject>) getHibernateTemplate()
                        .find(
                                        "select S from StudySubject S, OrganizationAssignedIdentifier I where I.healthcareSite.nciInstituteCode=?"
                                                        + " and I.value=? and I.type=? and I=any elements(S.identifiers)",
                                        new Object[] { id.getHealthcareSite().getNciInstituteCode(),
                                                id.getValue(), id.getType() });
    }
    
    /**
	 * Gets study subjects by identifiers.
	 * 
	 * @param studySubjectIdentifiers
	 *            the study subject identifiers
	 * 
	 * @return the by identifiers
	 */
    public List<StudySubject> getByIdentifiers(List<Identifier> studySubjectIdentifiers) {
        List<StudySubject> studySubjects = new ArrayList<StudySubject>();
        for (Identifier identifier : studySubjectIdentifiers) {
            if (identifier instanceof SystemAssignedIdentifier){ 
            	studySubjects.addAll(searchBySysIdentifier((SystemAssignedIdentifier) identifier));
            }
            else if (identifier instanceof OrganizationAssignedIdentifier) {
            	studySubjects.addAll(searchByOrgIdentifier((OrganizationAssignedIdentifier) identifier));
            }
                            
        }
        Set<StudySubject> set = new LinkedHashSet<StudySubject>();
        set.addAll(studySubjects);
        return new ArrayList<StudySubject>(set);
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.cabig.ctms.dao.MutableDomainObjectDao#save(gov.nih.nci.cabig.ctms.domain.MutableDomainObject)
     */
    @Transactional(readOnly = false)
    public void save(StudySubject obj) {
        getHibernateTemplate().saveOrUpdate(obj);
    }

    /**
	 * Merge.
	 * 
	 * @param obj
	 *            the obj
	 * 
	 * @return the study subject
	 */
    @Transactional(readOnly = false)
    public StudySubject merge(StudySubject obj) {
        return (StudySubject) getHibernateTemplate().merge(obj);
    }

    /*public List<StudySubject> searchByExample(StudySubject ss) {
        return searchByExample(ss, false, 0);
    }*/

    /**
	 * Search by example.
	 * 
	 * @param ss
	 *            the ss
	 * @param isWildCard
	 *            the is wild card
	 * 
	 * @return the list< study subject>
	 */
    public List<StudySubject> searchByExample(StudySubject ss, boolean isWildCard) {
        return searchByExample(ss, isWildCard, 0);
    }

	/**
	 * Gets the participant dao.
	 * 
	 * @return the participant dao
	 */
	public ParticipantDao getParticipantDao() {
		return participantDao;
	}

	/**
	 * Sets the participant dao.
	 * 
	 * @param participantDao
	 *            the new participant dao
	 */
	public void setParticipantDao(ParticipantDao participantDao) {
		this.participantDao = participantDao;
	}
	
	/**
	 * Search by identifier.
	 * 
	 * @param id
	 *            the id
	 * 
	 * @return the list< study subject>
	 */
	@SuppressWarnings("unchecked")
    public List<StudySubject> searchByIdentifier(int id) {
        return (List<StudySubject>) getHibernateTemplate().find(
                                        "select SS from StudySubject SS, Identifier I where I.id=? and I=any elements(SS.identifiers)",
                                        new Object[] {id});
    }

	/**
	 * Gets the study site dao.
	 * 
	 * @return the study site dao
	 */
	public StudySiteDao getStudySiteDao() {
		return studySiteDao;
	}

	/**
	 * Sets the study site dao.
	 * 
	 * @param studySiteDao
	 *            the new study site dao
	 */
	public void setStudySiteDao(StudySiteDao studySiteDao) {
		this.studySiteDao = studySiteDao;
	}

}