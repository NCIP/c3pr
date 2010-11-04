/*
 *  @author Kruttik,Ramakrishna
 */
package edu.duke.cabig.c3pr.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

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
import org.springframework.transaction.annotation.Transactional;

import com.semanticbits.querybuilder.AdvancedSearchCriteriaParameter;
import com.semanticbits.querybuilder.QueryBuilder;
import com.semanticbits.querybuilder.QueryBuilderDao;
import com.semanticbits.querybuilder.QueryGenerator;
import com.semanticbits.querybuilder.TargetObject;

import edu.duke.cabig.c3pr.constants.RegistrationWorkFlowStatus;
import edu.duke.cabig.c3pr.domain.CompanionStudyAssociation;
import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.ScheduledEpoch;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.StudySubjectStudyVersion;
import edu.duke.cabig.c3pr.domain.SystemAssignedIdentifier;
import edu.duke.cabig.c3pr.exception.C3PRBaseRuntimeException;
import edu.duke.cabig.c3pr.utils.AccrualCountComparator;
import edu.duke.cabig.c3pr.utils.StringUtils;
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
    
    private QueryBuilderDao queryBuilderDao;

    /** The study site dao. */
    private StudySiteDao studySiteDao;

    /** The participant dao. */
    private ParticipantDao participantDao;
    
    /** The studySubjectDemographics dao. */
    private StudySubjectDemographicsDao studySubjectDemographicsDao;

    public void setStudySubjectDemographicsDao(
			StudySubjectDemographicsDao studySubjectDemographicsDao) {
		this.studySubjectDemographicsDao = studySubjectDemographicsDao;
	}

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
	 * Reporting.
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

        Criteria studySubjectStudyVersionCriteria = registrationCriteria.createCriteria("studySubjectStudyVersions");
        Criteria studySubjectConsentVersionCriteria = studySubjectStudyVersionCriteria.createCriteria("studySubjectConsentVersionsInternal");
        Criteria studySiteCriteria = studySubjectStudyVersionCriteria.createCriteria("studySiteStudyVersion").createCriteria("studySite");
        Criteria studySubjectDemographicsCriteria = registrationCriteria.createCriteria("studySubjectDemographics");
        Criteria participantCriteria = studySubjectDemographicsCriteria.createCriteria("masterSubject");
        Criteria studyCriteria = studySiteCriteria.createCriteria("studyInternal");
        Criteria studyVersionCriteria = studyCriteria.createCriteria("studyVersionsInternal");
        Criteria siteCriteria = studySiteCriteria.createCriteria("healthcareSite");
        Criteria identifiersAssignedToOrganizationCriteria = siteCriteria.createCriteria("identifiersAssignedToOrganization");
        Criteria identifiersCriteria = studyCriteria.createCriteria("identifiers");

        // Study Criteria
        if (registration.getStudySite().getStudy().getShortTitleText() != null
                        && !registration.getStudySite().getStudy().getShortTitleText().equals("")) {
        	studyVersionCriteria.add(Expression.ilike("shortTitleText", "%"
                            + registration.getStudySite().getStudy().getShortTitleText() + "%"));
        }

        // Site criteria
        if (registration.getStudySite().getHealthcareSite().getName() != null
                        && !registration.getStudySite().getHealthcareSite().getName().equals("")) {
            siteCriteria.add(Expression.ilike("name", "%"
                            + registration.getStudySite().getHealthcareSite().getName() + "%"));
        }
        if (registration.getStudySite().getHealthcareSite().getPrimaryIdentifier() != null
                        && !registration.getStudySite().getHealthcareSite().getPrimaryIdentifier()
                                        .equals("")) {
        	identifiersAssignedToOrganizationCriteria.add(Expression.ilike("value", "%"
                            + registration.getStudySite().getHealthcareSite().getPrimaryIdentifier()
                            + "%"));
        }

        // registration consent criteria
        if (startDate != null && endDate != null) {
        	studySubjectConsentVersionCriteria.add(Expression.between("informedConsentSignedTimestamp", startDate, endDate));
        }else if(startDate != null){
        	studySubjectConsentVersionCriteria.add(Expression.ge("informedConsentSignedTimestamp", startDate));
        }else if(endDate != null){
        	studySubjectConsentVersionCriteria.add(Expression.le("informedConsentSignedTimestamp", endDate));
        }
        

        // participant/subject criteria
        if (registration.getStudySubjectDemographics().getMasterSubject().getBirthDate() != null) {
            participantCriteria.add(Expression.eq("birthDate", registration.getStudySubjectDemographics().getMasterSubject()
                            .getBirthDate()));
        }
//        if (registration.getStudySubjectDemographics().getMasterSubject().getRaceCode() != null
//                        && !registration.getStudySubjectDemographics().getMasterSubject().getRaceCode().equals("")) {
//            participantCriteria.add(Expression.ilike("raceCode", "%" + registration.getStudySubjectDemographics().getMasterSubject()
//                            .getRaceCode() + "%" )  );
//        }

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
    	String selectClause = "select ssub from StudySubject ssub ";

    	String whereClause = "";

    	List<Object> params = new ArrayList<Object>();

    	Study study= studySubject.getStudySite().getStudy();
    	boolean addStudySelectClause = true;
    	String studyClause="join ssub.studySubjectStudyVersions ssbsv " +
        			"join ssbsv.studySiteStudyVersion ssisv " +
        			"join ssisv.studyVersion sv ";
        if (!StringUtils.getBlankIfNull(study.getShortTitleText()).equals("")) {
        	selectClause += studyClause;
        	whereClause += "lower(sv.shortTitleText) like ? ";
        	params.add("%" + study.getShortTitleText().toLowerCase() + "%");
        	addStudySelectClause = false;
        }
        if (study.getIdentifiers().size() > 0){
        	if(addStudySelectClause)
        		selectClause += studyClause;
        	selectClause += "join sv.study study join study.identifiers sid ";
        	whereClause += "and lower(sid.value) like ? ";
        	params.add("%" + study.getIdentifiers().get(0).getValue().toLowerCase() + "%");
        }

        Participant participant= studySubject.getStudySubjectDemographics().getMasterSubject();
        boolean addParticipantSelectClause = true;
        String participantClause = "join ssub.studySubjectDemographics.masterSubject prt ";
        if(!StringUtils.getBlankIfNull(participant.getFirstName()).equals("")){
        	selectClause += participantClause;
        	whereClause += "and lower(prt.firstName) like ? ";
        	params.add("%" + participant.getFirstName().toLowerCase() + "%");
        	addParticipantSelectClause = false;
        }

        if(!StringUtils.getBlankIfNull(participant.getLastName()).equals("")){
        	if(addParticipantSelectClause)
        		selectClause += participantClause;
        	whereClause += "and lower(prt.lastName) like ? ";
        	params.add("%" + participant.getLastName().toLowerCase() + "%");
        	addParticipantSelectClause = false;
        }

        if (participant.getIdentifiers().size() > 0){
        	if(addParticipantSelectClause)
        		selectClause += participantClause;
        	selectClause += "join prt.identifiers pid ";
        	whereClause += "and lower(pid.value) like ?";
        	params.add("%" + participant.getIdentifiers().get(0).getValue().toLowerCase() + "%");
        }
        whereClause = "where " + (whereClause.startsWith("and")?whereClause.replaceFirst("and", ""):whereClause);
        String advanceQuery = selectClause + whereClause;

        return (List<StudySubject>)getHibernateTemplate().find(advanceQuery, params.toArray());

//    	Criteria studySubjectCriteria = getHibernateTemplate().getSessionFactory()
//                        .getCurrentSession().createCriteria(StudySubject.class);
//
//        Criteria studySiteCriteria = studySubjectCriteria.createCriteria("studySite");
//        Criteria participantCriteria = studySubjectCriteria.createCriteria("participant");
//        Criteria studyCriteria = studySiteCriteria.createCriteria("study");
//        Criteria studyVersionCriteria = studyCriteria.createCriteria("studyVersionsInternal");
//        Criteria sIdentifiersCriteria = studyCriteria.createCriteria("identifiers");
//        Criteria pIdentifiersCriteria = participantCriteria.createCriteria("identifiers");
//
//        // Study Criteria
//        if (studySubject.getStudySite().getStudy().getShortTitleText() != null
//                        && !studySubject.getStudySite().getStudy().getShortTitleText().equals("")) {
//        	studyVersionCriteria.add(Expression.ilike("shortTitleText", "%"
//                            + studySubject.getStudySite().getStudy().getShortTitleText() + "%"));
//        }
//        if (studySubject.getStudySite().getStudy().getIdentifiers().size() > 0) {
//            if (studySubject.getStudySite().getStudy().getIdentifiers().get(0).getValue() != null
//                            && studySubject.getStudySite().getStudy().getIdentifiers().get(0)
//                                            .getValue() != "") {
//                sIdentifiersCriteria.add(Expression.ilike("value", "%"
//                                + studySubject.getStudySite().getStudy().getIdentifiers().get(0)
//                                                .getValue() + "%"));
//            }
//        }
//
//        // participant/subject criteria
//        if (studySubject.getParticipant().getFirstName() != null
//                        && !studySubject.getParticipant().getFirstName().equals("")) {
//            participantCriteria.add(Expression.ilike("firstName", "%"
//                            + studySubject.getParticipant().getFirstName() + "%"));
//        }
//        if (studySubject.getParticipant().getLastName() != null
//                        && !studySubject.getParticipant().getLastName().equals("")) {
//            participantCriteria.add(Expression.ilike("lastName", "%"
//                            + studySubject.getParticipant().getLastName() + "%"));
//        }
//        if (studySubject.getParticipant().getIdentifiers().size() > 0) {
//            if (studySubject.getParticipant().getIdentifiers().get(0).getValue() != null
//                            && !studySubject.getParticipant().getIdentifiers().get(0).getValue()
//                                            .equals("")) {
//                pIdentifiersCriteria.add(Expression.ilike("value", "%"
//                                + studySubject.getParticipant().getIdentifiers().get(0).getValue()
//                                + "%"));
//            }
//        }
//
//        studySubjectCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//        return studySubjectCriteria.list();
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
        Criteria studySubjectDemographicsCriteria = registrationCriteria.createCriteria("studySubjectDemographics");
        studySubjectDemographicsCriteria.createCriteria("masterSubject").add(
                        Restrictions.eq("id", registration.getStudySubjectDemographics().getMasterSubject().getId()));
        registrationCriteria.createCriteria("studySubjectStudyVersions").createCriteria("studySiteStudyVersion").createCriteria("studySite").add(
                        Restrictions.eq("id", registration.getStudySite().getId()));
        return registrationCriteria.list();

    }
     
    @SuppressWarnings(value="unchecked")
    public List<StudySubject> searchBySubjectAndStudyIdentifiers(Identifier subjectPrimaryIdentifier,Identifier coordinatingCenterAssignedIdentifier) {
    	
    	List<Identifier> subjectIdentifiers = Arrays.asList(subjectPrimaryIdentifier);
    	List<Participant> subjects = participantDao.getByIdentifiers(subjectIdentifiers);
    	if(subjects.size() == 0){
    		return new ArrayList<StudySubject>();
    	}
    	else if(subjects.size()> 1){
    		throw new C3PRBaseRuntimeException("Found more than 1 subject with the same identifier");
    	}
    	
    	List<Identifier> studyIdentifiers = Arrays.asList(coordinatingCenterAssignedIdentifier);
    	List<Study> studies = studyDao.getByIdentifiers(studyIdentifiers);
    	
    	if(studies.size() == 0){
    		return new ArrayList<StudySubject>();
    	}
    	else if(studies.size()> 1){
    		throw new C3PRBaseRuntimeException("Found more than 1 study with the same coordinating center identifier");
    	}

        return (List<StudySubject>)getHibernateTemplate().find("select distinct ss from StudySubject ss,StudySubjectStudyVersion sssv where ss.studySubjectDemographics.masterSubject.id = ? " +
        		"and sssv.studySiteStudyVersion.studyVersion.study.id=? and sssv = any elements (ss.studySubjectStudyVersions) ",new Object[]{subjects.get(0).getId(),studies.get(0).getId()});

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

       registrationCriteria.createCriteria("studySubjectStudyVersions").createCriteria("scheduledEpochs").createCriteria("epoch").add(
                        Restrictions.eq("id", scheduledEpoch.getEpoch().getId()));
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
    @SuppressWarnings("unchecked")
	public List<StudySubject> getAll() throws DataAccessException {
    	int storedMaxResults = getHibernateTemplate().getMaxResults();
    	getHibernateTemplate().setMaxResults(0);
        List<StudySubject> studySubjects =  getHibernateTemplate().find("from StudySubject");
        getHibernateTemplate().setMaxResults(storedMaxResults);
        
        return studySubjects;
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
    public List<StudySubject> getIncompleteRegistrations(){
    	List<StudySubject> studySubjects = (List<StudySubject>) getHibernateTemplate().find( "select distinct ss from StudySubject ss join ss.studySubjectStudyVersions sssv join sssv.scheduledEpochs se where  (se.scEpochWorkflowStatus = 'PENDING_ON_EPOCH' OR se.scEpochWorkflowStatus = 'PENDING_RANDOMIZATION_ON_EPOCH')");
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
        participantDao.initialize(studySubject.getStudySubjectDemographics().getMasterSubject());
        if(studySubject.getStudySubjectDemographics().getValid()){
        	studySubjectDemographicsDao.initialize(studySubject.getStudySubjectDemographics());
        }
        getHibernateTemplate().initialize(studySubject.getStudySite().getStudyInvestigatorsInternal());
        getHibernateTemplate().initialize(studySubject.getStudySite().getStudyPersonnelInternal());
        getHibernateTemplate().initialize(studySubject.getStudySite().getStudySiteStudyVersions());
        getHibernateTemplate().initialize(studySubject.getChildStudySubjects());
        getHibernateTemplate().initialize(studySubject.getStudySubjectStudyVersions());
        
        for(StudySubjectStudyVersion studySubjectStudyVersion : studySubject.getStudySubjectStudyVersions()){
        	getHibernateTemplate().initialize(studySubjectStudyVersion.getStudySubjectConsentVersionsInternal());
        }

        participantDao.initialize(studySubject.getStudySubjectDemographics().getMasterSubject());

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
	 * @param id  the id
	 *
	 * @return the list< study subject>
	 */
    @SuppressWarnings("unchecked")
    public List<StudySubject> searchBySysIdentifier(SystemAssignedIdentifier id) {
        return (List<StudySubject>) getHibernateTemplate()
                    .find("select S from StudySubject S, SystemAssignedIdentifier I where I.systemName=?"
                        + " and I.value=? and I.typeInternal=? and I=any elements(S.identifiers)",
                        new Object[] { id.getSystemName(), id.getValue(), id.getType()});
    }

    /**
	 * Search by org identifier.
	 *
	 * @param id   the id
	 *
	 * @return the list< study subject>
	 */
    @SuppressWarnings("unchecked")
    public List<StudySubject> searchByOrgIdentifier(OrganizationAssignedIdentifier id) {
        return (List<StudySubject>) getHibernateTemplate()
                .find("select S from StudySubject S, OrganizationAssignedIdentifier I where " +
                	  "I.value=? and I.typeInternal=? and I=any elements(S.identifiers)",
                      new Object[]{id.getValue(), id.getTypeInternal()});
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
	
	public List<Study> getMostEnrolledStudies(Date startDate, Date endDate){
    	List<Study> listStudies = new ArrayList<Study>();
		
		List<StudySubject> studySubjects =  getHibernateTemplate().find("select ss from StudySubject ss where ss.regWorkflowStatus=? and ss.startDate between ? and ? order by ss.id desc", new Object[]{RegistrationWorkFlowStatus.ON_STUDY, startDate, endDate});
    	for(StudySubject ss : studySubjects){
    		Study s = ss.getStudySite().getStudy();
    		listStudies.add(s);
    	}
    	
    	Set<Study> setStudy = new HashSet<Study>();
		setStudy.addAll(listStudies);
    	
    	
    	for(Study study : setStudy){
    		study.setAccrualCount(Collections.frequency(listStudies, study));
    	}

    	List<Study> studies = new ArrayList<Study>();
    	
    	studies.addAll(setStudy);
    	Collections.sort(studies, new AccrualCountComparator());

    	return studies; 
    }
	
	public List<StudySubject> search(List<AdvancedSearchCriteriaParameter> searchParameters){
		String hql = generateHQL(searchParameters);
		return search(hql);
	}
	
	public List<StudySubject> search(List<AdvancedSearchCriteriaParameter> searchParameters, String fileLocation){
		String hql = generateHQL(searchParameters, fileLocation);
		return search(hql);
	}
	
	public String generateHQL(List<AdvancedSearchCriteriaParameter> searchParameters){
		InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("registration-advanced-search.xml");
		Unmarshaller unmarshaller;
		QueryBuilder queryBuilder = new QueryBuilder();
		try {
			unmarshaller = JAXBContext.newInstance("com.semanticbits.querybuilder").createUnmarshaller();
			queryBuilder = (QueryBuilder) unmarshaller.unmarshal(inputStream);
		} catch (JAXBException e) {
			e.printStackTrace();
		}

		TargetObject targetObject = (TargetObject) queryBuilder.getTargetObject().get(0);
		try {
			return QueryGenerator.generateHQL(targetObject, searchParameters, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public String generateHQL(List<AdvancedSearchCriteriaParameter> searchParameters, String fileLocation){
		File file = new File(fileLocation);
		InputStream inputStream ;
		QueryBuilder queryBuilder = new QueryBuilder();
		try {
			inputStream = new FileInputStream(file);
			Unmarshaller unmarshaller = JAXBContext.newInstance("com.semanticbits.querybuilder").createUnmarshaller();
			queryBuilder = (QueryBuilder) unmarshaller.unmarshal(inputStream);
		}catch (JAXBException e) {
			e.printStackTrace();
		}catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		TargetObject targetObject = (TargetObject) queryBuilder.getTargetObject().get(0);
		try {
			return QueryGenerator.generateHQL(targetObject, searchParameters, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public List<StudySubject> search(String hql){
		return (List<StudySubject>)queryBuilderDao.search(hql);
	}

	public void setQueryBuilderDao(QueryBuilderDao queryBuilderDao) {
		this.queryBuilderDao = queryBuilderDao;
	}

	public QueryBuilderDao getQueryBuilderDao() {
		return queryBuilderDao;
	}
	
}