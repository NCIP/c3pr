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
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Query;
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
import edu.duke.cabig.c3pr.constants.SortOrder;
import edu.duke.cabig.c3pr.domain.Address;
import edu.duke.cabig.c3pr.domain.CompanionStudyAssociation;
import edu.duke.cabig.c3pr.domain.Consent;
import edu.duke.cabig.c3pr.domain.ConsentQuestion;
import edu.duke.cabig.c3pr.domain.ContactMechanism;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.PermissibleStudySubjectRegistryStatus;
import edu.duke.cabig.c3pr.domain.ScheduledEpoch;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.StudySiteStudyVersion;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.StudySubjectConsentVersion;
import edu.duke.cabig.c3pr.domain.StudySubjectDemographics;
import edu.duke.cabig.c3pr.domain.StudySubjectRegistryStatus;
import edu.duke.cabig.c3pr.domain.StudySubjectStudyVersion;
import edu.duke.cabig.c3pr.domain.SubjectRegistryKeygenerator;
import edu.duke.cabig.c3pr.domain.SystemAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.factory.ObjectFactory;
import edu.duke.cabig.c3pr.domain.factory.ObjectFactoryOracle;
import edu.duke.cabig.c3pr.exception.C3PRBaseRuntimeException;
import edu.duke.cabig.c3pr.utils.AccrualCountComparator;
import edu.duke.cabig.c3pr.utils.AdvancedSearchCriteriaParameterUtil;
import edu.duke.cabig.c3pr.utils.DateUtil;
import edu.duke.cabig.c3pr.utils.SortParameter;
import edu.duke.cabig.c3pr.utils.StringUtils;
import edu.duke.cabig.c3pr.utils.StudySubjectDaoHelper;
import edu.emory.mathcs.backport.java.util.Collections;
import gov.nih.nci.cabig.ctms.dao.MutableDomainObjectDao;

/**
 * The Class StudySubjectDao.
 */
public class StudySubjectDao extends GridIdentifiableDao<StudySubject>
		implements MutableDomainObjectDao<StudySubject> {

	/** The SUBSTRIN g_ match_ properties. */
	private List<String> SUBSTRING_MATCH_PROPERTIES = Arrays
			.asList("studySite.study.shortTitleText");

	/** The EXAC t_ match_ properties. */
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
	
	private static final String ROW_NUM_PRE_STRING = "select * from (";
	private static final String ROW_NUM_POST_STRING = ") results where rn =1";
	
	public void setStudySubjectDemographicsDao(
			StudySubjectDemographicsDao studySubjectDemographicsDao) {
		this.studySubjectDemographicsDao = studySubjectDemographicsDao;
	}
	
	private ObjectFactory objectFactory;

	/**
	 * Sets the study dao.
	 * 
	 * @param studyDao
	 *            the new study dao
	 */
	public void setStudyDao(StudyDao studyDao) {
		this.studyDao = studyDao;
	}

	public void setObjectFactory(ObjectFactory objectFactory) {
		this.objectFactory = objectFactory;
	}

	/**
	 * Instantiates a new study subject dao.
	 */
	public StudySubjectDao() {
	}

	/*
	 * (non-Javadoc)
	 * 
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
	public List<StudySubject> advancedSearch(StudySubject registration,
			Date startDate, Date endDate, String ccId) {

		Criteria registrationCriteria = getHibernateTemplate()
				.getSessionFactory().getCurrentSession()
				.createCriteria(StudySubject.class);

		Criteria studySubjectStudyVersionCriteria = registrationCriteria
				.createCriteria("studySubjectStudyVersions");
		Criteria studySubjectConsentVersionCriteria = studySubjectStudyVersionCriteria
				.createCriteria("studySubjectConsentVersionsInternal");
		Criteria studySiteCriteria = studySubjectStudyVersionCriteria
				.createCriteria("studySiteStudyVersion").createCriteria(
						"studySite");
		Criteria studySubjectDemographicsCriteria = registrationCriteria
				.createCriteria("studySubjectDemographics");
		Criteria participantCriteria = studySubjectDemographicsCriteria
				.createCriteria("masterSubject");
		Criteria studyCriteria = studySiteCriteria
				.createCriteria("studyInternal");
		Criteria studyVersionCriteria = studyCriteria
				.createCriteria("studyVersionsInternal");
		Criteria siteCriteria = studySiteCriteria
				.createCriteria("healthcareSite");
		Criteria identifiersAssignedToOrganizationCriteria = siteCriteria
				.createCriteria("identifiersAssignedToOrganization");
		Criteria identifiersCriteria = studyCriteria
				.createCriteria("identifiers");

		// Study Criteria
		if (registration.getStudySite().getStudy().getShortTitleText() != null
				&& !registration.getStudySite().getStudy().getShortTitleText()
						.equals("")) {
			studyVersionCriteria.add(Expression.ilike("shortTitleText", "%"
					+ registration.getStudySite().getStudy()
							.getShortTitleText() + "%"));
		}

		// Site criteria
		if (registration.getStudySite().getHealthcareSite().getName() != null
				&& !registration.getStudySite().getHealthcareSite().getName()
						.equals("")) {
			siteCriteria.add(Expression.ilike("name", "%"
					+ registration.getStudySite().getHealthcareSite().getName()
					+ "%"));
		}
		if (registration.getStudySite().getHealthcareSite()
				.getPrimaryIdentifier() != null
				&& !registration.getStudySite().getHealthcareSite()
						.getPrimaryIdentifier().equals("")) {
			identifiersAssignedToOrganizationCriteria.add(Expression.ilike(
					"value", "%"
							+ registration.getStudySite().getHealthcareSite()
									.getPrimaryIdentifier() + "%"));
		}

		// registration consent criteria
		if (startDate != null && endDate != null) {
			studySubjectConsentVersionCriteria.add(Expression.between(
					"informedConsentSignedTimestamp", startDate, endDate));
		} else if (startDate != null) {
			studySubjectConsentVersionCriteria.add(Expression.ge(
					"informedConsentSignedTimestamp", startDate));
		} else if (endDate != null) {
			studySubjectConsentVersionCriteria.add(Expression.le(
					"informedConsentSignedTimestamp", endDate));
		}

		// participant/subject criteria
		if (registration.getStudySubjectDemographics().getMasterSubject()
				.getBirthDate() != null) {
			participantCriteria.add(Expression.eq("birthDate", registration
					.getStudySubjectDemographics().getMasterSubject()
					.getBirthDate()));
		}
		// if
		// (registration.getStudySubjectDemographics().getMasterSubject().getRaceCode()
		// != null
		// &&
		// !registration.getStudySubjectDemographics().getMasterSubject().getRaceCode().equals(""))
		// {
		// participantCriteria.add(Expression.ilike("raceCode", "%" +
		// registration.getStudySubjectDemographics().getMasterSubject()
		// .getRaceCode() + "%" ) );
		// }

		if (ccId != null && !ccId.equals("")) {
			identifiersCriteria
					.add(Expression.ilike("value", "%" + ccId + "%"));
		}
		registrationCriteria
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
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

		Study study = studySubject.getStudySite().getStudy();
		boolean addStudySelectClause = true;
		String studyClause = "join ssub.studySubjectStudyVersions ssbsv "
				+ "join ssbsv.studySiteStudyVersion ssisv "
				+ "join ssisv.studyVersion sv ";
		if (!StringUtils.getBlankIfNull(study.getShortTitleText()).equals("")) {
			selectClause += studyClause;
			whereClause += "lower(sv.shortTitleText) like ? ";
			params.add("%" + study.getShortTitleText().toLowerCase() + "%");
			addStudySelectClause = false;
		}
		if (study.getIdentifiers().size() > 0) {
			if (addStudySelectClause)
				selectClause += studyClause;
			selectClause += "join sv.study study join study.identifiers sid ";
			whereClause += "and lower(sid.value) like ? ";
			params.add("%"
					+ study.getIdentifiers().get(0).getValue().toLowerCase()
					+ "%");
		}

		Participant participant = studySubject.getStudySubjectDemographics()
				.getMasterSubject();
		boolean addParticipantSelectClause = true;
		String participantClause = "join ssub.studySubjectDemographics.masterSubject prt ";
		if (!StringUtils.getBlankIfNull(participant.getFirstName()).equals("")) {
			selectClause += participantClause;
			whereClause += "and lower(prt.firstName) like ? ";
			params.add("%" + participant.getFirstName().toLowerCase() + "%");
			addParticipantSelectClause = false;
		}

		if (!StringUtils.getBlankIfNull(participant.getLastName()).equals("")) {
			if (addParticipantSelectClause)
				selectClause += participantClause;
			whereClause += "and lower(prt.lastName) like ? ";
			params.add("%" + participant.getLastName().toLowerCase() + "%");
			addParticipantSelectClause = false;
		}

		if (participant.getIdentifiers().size() > 0) {
			if (addParticipantSelectClause)
				selectClause += participantClause;
			selectClause += "join prt.identifiers pid ";
			whereClause += "and lower(pid.value) like ?";
			params.add("%"
					+ participant.getIdentifiers().get(0).getValue()
							.toLowerCase() + "%");
		}
		whereClause = "where "
				+ (whereClause.startsWith("and") ? whereClause.replaceFirst(
						"and", "") : whereClause);
		String advanceQuery = selectClause + whereClause;

		return (List<StudySubject>) getHibernateTemplate().find(advanceQuery,
				params.toArray());

		// Criteria studySubjectCriteria =
		// getHibernateTemplate().getSessionFactory()
		// .getCurrentSession().createCriteria(StudySubject.class);
		//
		// Criteria studySiteCriteria =
		// studySubjectCriteria.createCriteria("studySite");
		// Criteria participantCriteria =
		// studySubjectCriteria.createCriteria("participant");
		// Criteria studyCriteria = studySiteCriteria.createCriteria("study");
		// Criteria studyVersionCriteria =
		// studyCriteria.createCriteria("studyVersionsInternal");
		// Criteria sIdentifiersCriteria =
		// studyCriteria.createCriteria("identifiers");
		// Criteria pIdentifiersCriteria =
		// participantCriteria.createCriteria("identifiers");
		//
		// // Study Criteria
		// if (studySubject.getStudySite().getStudy().getShortTitleText() !=
		// null
		// &&
		// !studySubject.getStudySite().getStudy().getShortTitleText().equals(""))
		// {
		// studyVersionCriteria.add(Expression.ilike("shortTitleText", "%"
		// + studySubject.getStudySite().getStudy().getShortTitleText() + "%"));
		// }
		// if (studySubject.getStudySite().getStudy().getIdentifiers().size() >
		// 0) {
		// if
		// (studySubject.getStudySite().getStudy().getIdentifiers().get(0).getValue()
		// != null
		// && studySubject.getStudySite().getStudy().getIdentifiers().get(0)
		// .getValue() != "") {
		// sIdentifiersCriteria.add(Expression.ilike("value", "%"
		// + studySubject.getStudySite().getStudy().getIdentifiers().get(0)
		// .getValue() + "%"));
		// }
		// }
		//
		// // participant/subject criteria
		// if (studySubject.getParticipant().getFirstName() != null
		// && !studySubject.getParticipant().getFirstName().equals("")) {
		// participantCriteria.add(Expression.ilike("firstName", "%"
		// + studySubject.getParticipant().getFirstName() + "%"));
		// }
		// if (studySubject.getParticipant().getLastName() != null
		// && !studySubject.getParticipant().getLastName().equals("")) {
		// participantCriteria.add(Expression.ilike("lastName", "%"
		// + studySubject.getParticipant().getLastName() + "%"));
		// }
		// if (studySubject.getParticipant().getIdentifiers().size() > 0) {
		// if (studySubject.getParticipant().getIdentifiers().get(0).getValue()
		// != null
		// && !studySubject.getParticipant().getIdentifiers().get(0).getValue()
		// .equals("")) {
		// pIdentifiersCriteria.add(Expression.ilike("value", "%"
		// + studySubject.getParticipant().getIdentifiers().get(0).getValue()
		// + "%"));
		// }
		// }
		//
		// studySubjectCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		// return studySubjectCriteria.list();
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
		if (study.getCompanionIndicator()) {
			for (CompanionStudyAssociation companionStudyAssociation : study
					.getParentStudyAssociations()) {
				for (StudySite studySite : companionStudyAssociation
						.getStudySites()) {
					for (StudySubject studySubject : studySite
							.getStudySubjects()) {
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
	public List<StudySubject> searchByExample(StudySubject registration,
			boolean isWildCard, int maxResults) {
		List<StudySubject> result = new ArrayList<StudySubject>();

		Example example = Example.create(registration).excludeZeroes()
				.ignoreCase();
		try {
			Criteria studySubjectCriteria = getSession().createCriteria(
					StudySubject.class);
			studySubjectCriteria
					.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

			if (isWildCard) {
				example.excludeProperty("doNotUse").enableLike(
						MatchMode.ANYWHERE);
				studySubjectCriteria.add(example);

				if (maxResults > 0)
					studySubjectCriteria.setMaxResults(maxResults);

				if (registration.getIdentifiers().size() > 1) {
					studySubjectCriteria
							.createCriteria("identifiers")
							.add(Restrictions.ilike("value", "%"
									+ registration.getIdentifiers().get(0)
											.getValue() + "%"))
							.add(Restrictions.ilike("value", "%"
									+ registration.getIdentifiers().get(1)
											.getValue() + "%"));
				} else if (registration.getIdentifiers().size() > 0) {
					studySubjectCriteria.createCriteria("identifiers").add(
							Restrictions.ilike("value", "%"
									+ registration.getIdentifiers().get(0)
											.getValue() + "%"));
				}
				result = studySubjectCriteria.list();
			}
			result = studySubjectCriteria.add(example).list();
		} catch (Exception e) {
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
	public List<StudySubject> searchBySubjectAndStudySite(
			StudySubject registration) {

		Example example = Example.create(registration).excludeZeroes()
				.ignoreCase();
		Criteria registrationCriteria = getHibernateTemplate()
				.getSessionFactory().getCurrentSession()
				.createCriteria(StudySubject.class);
		registrationCriteria.add(example);
		Criteria studySubjectDemographicsCriteria = registrationCriteria
				.createCriteria("studySubjectDemographics");
		studySubjectDemographicsCriteria.createCriteria("masterSubject").add(
				Restrictions.eq("id", registration
						.getStudySubjectDemographics().getMasterSubject()
						.getId()));
		registrationCriteria
				.createCriteria("studySubjectStudyVersions")
				.createCriteria("studySiteStudyVersion")
				.createCriteria("studySite")
				.add(Restrictions.eq("id", registration.getStudySite().getId()));
		return registrationCriteria.list();

	}

	@SuppressWarnings(value = "unchecked")
	public List<StudySubject> searchBySubjectAndStudyIdentifiers(
			Identifier subjectPrimaryIdentifier,
			Identifier coordinatingCenterAssignedIdentifier) {

		List<Identifier> subjectIdentifiers = Arrays
				.asList(subjectPrimaryIdentifier);
		List<Participant> subjects = participantDao
				.getByIdentifiers(subjectIdentifiers);
		if (subjects.size() == 0) {
			return new ArrayList<StudySubject>();
		} else if (subjects.size() > 1) {
			throw new C3PRBaseRuntimeException(
					"Found more than 1 subject with the same identifier");
		}

		List<Identifier> studyIdentifiers = Arrays
				.asList(coordinatingCenterAssignedIdentifier);
		List<Study> studies = studyDao.getByIdentifiers(studyIdentifiers);

		if (studies.size() == 0) {
			return new ArrayList<StudySubject>();
		} else if (studies.size() > 1) {
			throw new C3PRBaseRuntimeException(
					"Found more than 1 study with the same coordinating center identifier");
		}

		return (List<StudySubject>) getHibernateTemplate()
				.find("select distinct ss from StudySubject ss,StudySubjectStudyVersion sssv where ss.studySubjectDemographics.masterSubject.id = ? "
						+ "and sssv.studySiteStudyVersion.studyVersion.study.id=? and sssv = any elements (ss.studySubjectStudyVersions) ",
						new Object[] { subjects.get(0).getId(),
								studies.get(0).getId() });

	}

	/**
	 * Search by scheduled epoch.
	 * 
	 * @param scheduledEpoch
	 *            the scheduled epoch
	 * 
	 * @return the list< study subject>
	 */
	public List<StudySubject> searchByScheduledEpoch(
			ScheduledEpoch scheduledEpoch) {

		StudySubject registration = new StudySubject(true);
		Example example = Example.create(registration).excludeZeroes()
				.ignoreCase();
		Criteria registrationCriteria = getHibernateTemplate()
				.getSessionFactory().getCurrentSession()
				.createCriteria(StudySubject.class);
		registrationCriteria.add(example);

		registrationCriteria.createCriteria("studySubjectStudyVersions")
				.createCriteria("scheduledEpochs").createCriteria("epoch")
				.add(Restrictions.eq("id", scheduledEpoch.getEpoch().getId()));
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
		List<StudySubject> studySubjects = getHibernateTemplate().find(
				"from StudySubject");
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
	public List<StudySubject> searchByExample(StudySubject registration,
			int maxResults) {
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
	public List<StudySubject> getIncompleteRegistrations() {
		List<StudySubject> studySubjects = (List<StudySubject>) getHibernateTemplate()
				.find("select distinct ss from StudySubject ss join ss.studySubjectStudyVersions sssv join sssv.scheduledEpochs se where  (se.scEpochWorkflowStatus = 'PENDING_ON_EPOCH' OR se.scEpochWorkflowStatus = 'PENDING_RANDOMIZATION_ON_EPOCH')");
		return studySubjects;
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
	public void initialize(StudySubject studySubject)
			throws DataAccessException {
		studyDao.initialize(studySubject.getStudySite().getStudy());
		studySiteDao.initialize(studySubject.getStudySite());
		participantDao.initialize(studySubject.getStudySubjectDemographics()
				.getMasterSubject());
		if (studySubject.getStudySubjectDemographics().getValid()) {
			studySubjectDemographicsDao.initialize(studySubject
					.getStudySubjectDemographics());
		}
		getHibernateTemplate().initialize(
				studySubject.getStudySite().getStudyInvestigatorsInternal());
		getHibernateTemplate().initialize(
				studySubject.getStudySite().getStudyPersonnelInternal());
		getHibernateTemplate().initialize(
				studySubject.getStudySite().getStudySiteStudyVersions());
		getHibernateTemplate().initialize(studySubject.getChildStudySubjects());
		getHibernateTemplate().initialize(
				studySubject.getStudySubjectStudyVersions());

		for (StudySubjectStudyVersion studySubjectStudyVersion : studySubject
				.getStudySubjectStudyVersions()) {
			getHibernateTemplate().initialize(
					studySubjectStudyVersion
							.getStudySubjectConsentVersionsInternal());
		}

		participantDao.initialize(studySubject.getStudySubjectDemographics()
				.getMasterSubject());

		getHibernateTemplate().initialize(studySubject.getScheduledEpochs());
		getHibernateTemplate().initialize(studySubject.getIdentifiers());
		for (ScheduledEpoch scheduledEpoch : studySubject.getScheduledEpochs()) {
			getHibernateTemplate().initialize(
					scheduledEpoch.getScheduledArmsInternal());
			getHibernateTemplate().initialize(
					scheduledEpoch.getSubjectEligibilityAnswersInternal());
			getHibernateTemplate().initialize(
					scheduledEpoch.getSubjectStratificationAnswersInternal());
		}

		for (StudySubject childStudySubject : studySubject
				.getChildStudySubjects()) {
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
				.find("select S from StudySubject S, SystemAssignedIdentifier I where I.systemName=?"
						+ " and I.value=? and I.typeInternal=? and I=any elements(S.identifiers)",
						new Object[] { id.getSystemName(), id.getValue(),
								id.getType() });
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
	public List<StudySubject> searchByOrgIdentifier(
			OrganizationAssignedIdentifier id) {
		return (List<StudySubject>) getHibernateTemplate()
				.find("select S from StudySubject S, OrganizationAssignedIdentifier I where "
						+ "I.value=? and I.typeInternal=? and I=any elements(S.identifiers)",
						new Object[] { id.getValue(), id.getTypeInternal() });
	}

	/**
	 * Gets study subjects by identifiers.
	 * 
	 * @param studySubjectIdentifiers
	 *            the study subject identifiers
	 * 
	 * @return the by identifiers
	 */
	public List<StudySubject> getByIdentifiers(
			List<Identifier> studySubjectIdentifiers) {
		List<StudySubject> studySubjects = new ArrayList<StudySubject>();
		for (Identifier identifier : studySubjectIdentifiers) {
			if (identifier instanceof SystemAssignedIdentifier) {
				studySubjects
						.addAll(searchBySysIdentifier((SystemAssignedIdentifier) identifier));
			} else if (identifier instanceof OrganizationAssignedIdentifier) {
				studySubjects
						.addAll(searchByOrgIdentifier((OrganizationAssignedIdentifier) identifier));
			}

		}
		Set<StudySubject> set = new LinkedHashSet<StudySubject>();
		set.addAll(studySubjects);
		return new ArrayList<StudySubject>(set);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.nih.nci.cabig.ctms.dao.MutableDomainObjectDao#save(gov.nih.nci.cabig
	 * .ctms.domain.MutableDomainObject)
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

	/*
	 * public List<StudySubject> searchByExample(StudySubject ss) { return
	 * searchByExample(ss, false, 0); }
	 */

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
	public List<StudySubject> searchByExample(StudySubject ss,
			boolean isWildCard) {
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
		return (List<StudySubject>) getHibernateTemplate()
				.find("select SS from StudySubject SS, Identifier I where I.id=? and I=any elements(SS.identifiers)",
						new Object[] { id });
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

	public List<Study> getMostEnrolledStudies(Date startDate, Date endDate) {
		List<Study> listStudies = new ArrayList<Study>();

		List<StudySubject> studySubjects = getHibernateTemplate()
				.find("select ss from StudySubject ss where ss.regWorkflowStatus=? and ss.startDate between ? and ? order by ss.id desc",
						new Object[] { RegistrationWorkFlowStatus.ON_STUDY,
								startDate, endDate });
		for (StudySubject ss : studySubjects) {
			Study s = ss.getStudySite().getStudy();
			listStudies.add(s);
		}

		Set<Study> setStudy = new HashSet<Study>();
		setStudy.addAll(listStudies);

		for (Study study : setStudy) {
			study.setAccrualCount(Collections.frequency(listStudies, study));
		}

		List<Study> studies = new ArrayList<Study>();

		studies.addAll(setStudy);
		Collections.sort(studies, new AccrualCountComparator());

		return studies;
	}

	public List<StudySubject> search(
			List<AdvancedSearchCriteriaParameter> searchParameters) {
		String hql = generateHQL(searchParameters);
		return search(hql);
	}

	public List<StudySubject> invokeCustomHQLSearch(
			List<AdvancedSearchCriteriaParameter> searchParameters, final Integer min_rows, final Integer max_rows,List<SortParameter> sortParameters) {
		SortParameter sortParameter = null;
		if(sortParameters != null && sortParameters.size() > 0) {
			sortParameter = sortParameters.get(0);
		}
		Integer inputScenario = SubjectRegistryKeygenerator.queryMap
				.get(SubjectRegistryKeygenerator.generateKey(searchParameters));
		if(inputScenario == null){
			return search(searchParameters);
		}
		switch (inputScenario) {
		case 1:
			return retrieveStudySubjectsByStudyIdentifierValue(searchParameters
					.get(0).getValues().get(0), min_rows, max_rows, sortParameter);
		case 2:
			return retrieveStudySubjectsByStudySubjectIdentifierValue(searchParameters
					.get(0).getValues().get(0), min_rows, max_rows, sortParameter);
		case 3:
			return retrieveStudySubjectsByStudySubjectConsentDocumentId(searchParameters
					.get(0).getValues().get(0), min_rows, max_rows, sortParameter);
		case 4:
			return retrieveStudySubjectsBySubjectIdentifierValue(searchParameters
					.get(0).getValues().get(0), min_rows, max_rows, sortParameter);
		case 6:
			return retrieveStudySubjectsByStudyIdentifierValueAndSubjectIdentifierValue(
					AdvancedSearchCriteriaParameterUtil
							.extractStudyIdentifierValue(searchParameters),
					AdvancedSearchCriteriaParameterUtil
							.extractSubjectIdentifierValue(searchParameters), min_rows, max_rows, sortParameter);
		case 7:
			return retrieveStudySubjectsByStudyIdentifierValueAndType(
					AdvancedSearchCriteriaParameterUtil
							.extractStudyIdentifierValue(searchParameters),
					AdvancedSearchCriteriaParameterUtil
							.extractStudyIdentifierType(searchParameters), min_rows, max_rows, sortParameter);
		case 8:
			return retrieveStudySubjectsByStudySubjectIdentifierValueAndType(
					AdvancedSearchCriteriaParameterUtil
							.extractStudySubjectIdentifierValue(searchParameters),
					AdvancedSearchCriteriaParameterUtil
							.extractStudySubjectIdentifierType(searchParameters), min_rows, max_rows, sortParameter);
		case 9:
			return retrieveStudySubjectsBySubjectIdentifierValueAndType(
					AdvancedSearchCriteriaParameterUtil
							.extractSubjectIdentifierValue(searchParameters),
					AdvancedSearchCriteriaParameterUtil
							.extractSubjectIdentifierType(searchParameters), min_rows, max_rows, sortParameter);
		case 10:
			return retrieveStudySubjectsByStudyIdentifierAndRegistryStatusCodeAndLastName(
					AdvancedSearchCriteriaParameterUtil
							.extractStudyIdentifierValue(searchParameters),
					AdvancedSearchCriteriaParameterUtil
							.extractRegistryStatusCode(searchParameters),
					AdvancedSearchCriteriaParameterUtil
							.extractSubjectLastName(searchParameters), min_rows, max_rows, sortParameter);
		case 11:
			return retrieveStudySubjectsByStudyIdentifierAndRegistryStatusCodeAndLastNameAndRegistryStatusEffeciveDate(
					AdvancedSearchCriteriaParameterUtil
							.extractStudyIdentifierValue(searchParameters),
					AdvancedSearchCriteriaParameterUtil
							.extractNonMatchingRegistryStatusCode(searchParameters),
					AdvancedSearchCriteriaParameterUtil
							.extractSubjectLastName(searchParameters),
					AdvancedSearchCriteriaParameterUtil
							.extractSubjectRegistryEffectiveDate(searchParameters), min_rows, max_rows, sortParameter);
		case 12:
			return retrieveStudySubjectsByStudyIdentifierValueAndStudySubjectIdentifierValue(
					AdvancedSearchCriteriaParameterUtil
							.extractStudyIdentifierValue(searchParameters),
					AdvancedSearchCriteriaParameterUtil
							.extractStudySubjectIdentifierValue(searchParameters), min_rows, max_rows, sortParameter);
		case 13:
			return retrieveStudySubjectsByNullableStudyIdentifierAndStudySubjectConsentDocumentId(
					AdvancedSearchCriteriaParameterUtil
							.extractStudyIdentifierValue(searchParameters),
					AdvancedSearchCriteriaParameterUtil
							.extractSubjectDocumentId(searchParameters), min_rows, max_rows, sortParameter);
		case 14:
			return retrieveStudySubjectsByNullableStudyIdentifierValueAndRegistryStatusCode(
					AdvancedSearchCriteriaParameterUtil
							.extractStudyIdentifierValue(searchParameters),
					AdvancedSearchCriteriaParameterUtil
							.extractRegistryStatusCode(searchParameters), min_rows, max_rows, sortParameter);
		default:
			return search(searchParameters);
		}
	}

	public List<StudySubject> search(
			List<AdvancedSearchCriteriaParameter> searchParameters,
			String fileLocation) {
		String hql = generateHQL(searchParameters, fileLocation);
		return search(hql);
	}

	public String generateHQL(
			List<AdvancedSearchCriteriaParameter> searchParameters) {
		InputStream inputStream = Thread.currentThread()
				.getContextClassLoader()
				.getResourceAsStream("registration-advanced-search.xml");
		Unmarshaller unmarshaller;
		QueryBuilder queryBuilder = new QueryBuilder();
		try {
			unmarshaller = JAXBContext.newInstance(
					"com.semanticbits.querybuilder").createUnmarshaller();
			queryBuilder = (QueryBuilder) unmarshaller.unmarshal(inputStream);
		} catch (JAXBException e) {
			e.printStackTrace();
		}

		TargetObject targetObject = (TargetObject) queryBuilder
				.getTargetObject().get(0);
		try {
			return QueryGenerator.generateHQL(targetObject, searchParameters,
					true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public String generateHQL(
			List<AdvancedSearchCriteriaParameter> searchParameters,
			String fileLocation) {
		File file = new File(fileLocation);
		InputStream inputStream;
		QueryBuilder queryBuilder = new QueryBuilder();
		try {
			inputStream = new FileInputStream(file);
			Unmarshaller unmarshaller = JAXBContext.newInstance(
					"com.semanticbits.querybuilder").createUnmarshaller();
			queryBuilder = (QueryBuilder) unmarshaller.unmarshal(inputStream);
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		TargetObject targetObject = (TargetObject) queryBuilder
				.getTargetObject().get(0);
		try {
			return QueryGenerator.generateHQL(targetObject, searchParameters,
					true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	@SuppressWarnings(value="unchecked")
	public List<StudySubject> search(String hql) {
		return (List<StudySubject>) queryBuilderDao.search(hql);
	}

	public void setQueryBuilderDao(QueryBuilderDao queryBuilderDao) {
		this.queryBuilderDao = queryBuilderDao;
	}

	public QueryBuilderDao getQueryBuilderDao() {
		return queryBuilderDao;
	}

	@SuppressWarnings(value="unchecked")
	public StudySubject getByCorrespondenceId(int id) {
		List<StudySubject> studySubjects = (List<StudySubject>) getHibernateTemplate()
				.find("select ss from StudySubject ss, Correspondence c where c = any elements(ss.correspondencesInternal) and c.id = ?",
						new Object[] { id });
		return (studySubjects.size() > 0 ? studySubjects.get(0) : null);
	}

	// Input case 1: studyIdentifier value, = (exact search on study identifier)
	@SuppressWarnings(value="unchecked")
	public List<StudySubject> retrieveStudySubjectsByStudyIdentifierValue(
			String studyIdentifierValue, final Integer min_rows, final Integer max_rows, SortParameter sortParameter) {
		List<Object> studySubjectObjects = new ArrayList<Object>();
		StringBuilder sb = new StringBuilder(ROW_NUM_PRE_STRING);
		String baseQuery = "SELECT ss.id as study_subject_id,  ss.payment_method,  ss.reg_data_entry_status,  ssdmgphcs.id,  ssdmgphcs.first_name,  " +
				"ssdmgphcs.last_name, ssdmgphcs.middle_name,  ssdmgphcs.name_prefix,  ssdmgphcs.name_suffix, ssdmgphcs.administrative_gender_code," +
				" ssdmgphcs.ethnic_group_code, ssdmgphcs.birth_date,  ssdmgphcs.marital_status_code, ssdmgphcs.valid,row_number() over(partition by ss.id order by ss.id) rn " +
				" FROM study_subjects ss, stu_sub_demographics ssdmgphcs, study_subject_versions ssubv,  study_site_versions ssitv,  study_versions sv,  studies s, " +
				" identifiers stu_ide WHERE ss.id = ssubv.spa_id AND ssubv.study_site_ver_id = ssitv.id AND ssitv.stu_version_id = sv.id AND sv.study_id = " +
				"s.id AND s.id =stu_ide.stu_id AND stu_ide.value = (:value) AND ssdmgphcs.id = ss.stu_sub_dmgphcs_id";
		sb.append(baseQuery);
		
		if(sortParameter != null) {
			if(sortParameter.equals(SortParameter.studyShortTitle)){
				sb.append(" order by sv.short_title_text");
			} else if(sortParameter.equals(SortParameter.studyIdentifierType)){
				sb.append(" order by stu_ide.type");
			} else if(sortParameter.equals(SortParameter.studyIdentifierValue)){
				sb.append(" order by stu_ide.value");
			} else if(sortParameter.equals(SortParameter.subjectLastName)){
				sb.append(" order by ssdmgphcs.last_name");
			}
			
			if(sortParameter.equals(SortParameter.subjectIdentifierType) || sortParameter.equals(SortParameter.subjectIdentifierValue)){
				baseQuery = "SELECT ss.id as study_subject_id,  ss.payment_method,  ss.reg_data_entry_status,  ssdmgphcs.id,  ssdmgphcs.first_name,  " +
						"ssdmgphcs.last_name, ssdmgphcs.middle_name,  ssdmgphcs.name_prefix,  ssdmgphcs.name_suffix, ssdmgphcs.administrative_gender_code," +
						" ssdmgphcs.ethnic_group_code, ssdmgphcs.birth_date,  ssdmgphcs.marital_status_code,  ssdmgphcs.valid,row_number() over(partition by ss.id order " +
						"by ss.id) rn FROM study_subjects ss, stu_sub_demographics ssdmgphcs, study_subject_versions ssubv,  study_site_versions ssitv,  study_versions sv, " +
						"studies s, identifiers stu_ide, identifiers sub_ide WHERE ss.id = ssubv.spa_id AND ssubv.study_site_ver_id = ssitv.id AND ssitv.stu_version_id = sv.id " +
						"AND sv.study_id = s.id AND s.id =stu_ide.stu_id AND ssdmgphcs.id = sub_ide.stu_sub_dmgphcs_id AND stu_ide.value = (:value) AND " +
						"ssdmgphcs.id = ss.stu_sub_dmgphcs_id";
				sb =  new StringBuilder(ROW_NUM_PRE_STRING);
				sb.append(baseQuery);
				if(sortParameter.equals(SortParameter.subjectIdentifierType)){
					sb.append(" order by sub_ide.type");
				} else if(sortParameter.equals(SortParameter.subjectIdentifierValue)){
					sb.append(" order by sub_ide.value");
				}
			}
			
			if(sortParameter.equals(SortParameter.studySubjectIdentifierType) || sortParameter.equals(SortParameter.studySubjectIdentifierValue)){
				baseQuery = "SELECT ss.id as study_subject_id,  ss.payment_method,  ss.reg_data_entry_status,  ssdmgphcs.id,  ssdmgphcs.first_name,  " +
						"ssdmgphcs.last_name, ssdmgphcs.middle_name,  ssdmgphcs.name_prefix,  ssdmgphcs.name_suffix, ssdmgphcs.administrative_gender_code," +
						" ssdmgphcs.ethnic_group_code, ssdmgphcs.birth_date,  ssdmgphcs.marital_status_code,  ssdmgphcs.valid,row_number() over(partition by ss.id order " +
						"by ss.id) rn FROM study_subjects ss, stu_sub_demographics ssdmgphcs, study_subject_versions ssubv,  study_site_versions ssitv,  study_versions sv, " +
						"studies s, identifiers stu_ide, identifiers stu_sub_ide WHERE ss.id = ssubv.spa_id AND ssubv.study_site_ver_id = ssitv.id AND ssitv.stu_version_id = sv.id" +
						" AND sv.study_id = s.id AND s.id =stu_ide.stu_id AND ss.id = stu_sub_ide.spa_id AND stu_ide.value = (:value) AND ssdmgphcs.id = ss.stu_sub_dmgphcs_id";
				sb =  new StringBuilder(ROW_NUM_PRE_STRING);
				sb.append(baseQuery);
				if(sortParameter.equals(SortParameter.studySubjectIdentifierType)){
					sb.append(" order by stu_sub_ide.type");
				} else if(sortParameter.equals(SortParameter.studySubjectIdentifierValue)){
					sb.append(" order by stu_sub_ide.value");
				}
			}
			
			String[] stringArray = sb.toString().split("order");
			if(sb.toString().contains("order by") && (stringArray.length > 2) && sortParameter.getSortOrder() == SortOrder.DESCENDING){
				sb.append(" desc ");
			}
		}
		
		sb.append(ROW_NUM_POST_STRING);
		studySubjectObjects = StudySubjectDaoHelper.buildPaginationQuery(sb.toString(), min_rows, max_rows, getHibernateTemplate(), 
				this.objectFactory instanceof ObjectFactoryOracle ? true:false).setParameter("value", studyIdentifierValue).list();
        return buildStudySubjects(studySubjectObjects);
	}

	//(s, "ss", StudySubject.class)
	// Input case 2: studySubject Identifier value, = (exact search on study
	// subject identifier)
	@SuppressWarnings(value="unchecked")
	public List<StudySubject> retrieveStudySubjectsByStudySubjectIdentifierValue(
			String studySubjectIdentifierValue, final Integer min_rows, final Integer max_rows, SortParameter sortParameter) {
		List<Object> studySubjectObjects = new ArrayList<Object>();
		StringBuilder sb = new StringBuilder(ROW_NUM_PRE_STRING);
		String baseQuery = "SELECT ss.id as study_subject_id,  ss.payment_method,  ss.reg_data_entry_status,  ssdmgphcs.id,  ssdmgphcs.first_name," +
				"	ssdmgphcs.last_name, ssdmgphcs.middle_name,  ssdmgphcs.name_prefix,  ssdmgphcs.name_suffix, ssdmgphcs.administrative_gender_code," +
				" ssdmgphcs.ethnic_group_code, ssdmgphcs.birth_date,  ssdmgphcs.marital_status_code,  ssdmgphcs.valid,row_number() over(partition by ss.id order by ss.id) " +
				"rn FROM study_subjects ss, stu_sub_demographics ssdmgphcs, identifiers stu_sub_ide WHERE ss.id =stu_sub_ide.spa_id AND stu_sub_ide.value = (:value) AND " +
				"ssdmgphcs.id = ss.stu_sub_dmgphcs_id";
		sb.append(baseQuery);
		if(sortParameter != null ) {
			if(sortParameter.equals(SortParameter.studySubjectIdentifierType)){
				sb.append(" order by stu_sub_ide.type");
			} else if(sortParameter.equals(SortParameter.studySubjectIdentifierValue)){
				sb.append(" order by stu_sub_ide.value");
			} else if(sortParameter.equals(SortParameter.subjectLastName)){
				sb.append(" order by ssdmgphcs.last_name");
			}
			
			if(sortParameter.equals(SortParameter.subjectIdentifierType) || sortParameter.equals(SortParameter.subjectIdentifierValue)){
				baseQuery = "SELECT ss.id as study_subject_id,  ss.payment_method,  ss.reg_data_entry_status,  ssdmgphcs.id,  ssdmgphcs.first_name," +
						"	ssdmgphcs.last_name, ssdmgphcs.middle_name,  ssdmgphcs.name_prefix,  ssdmgphcs.name_suffix, ssdmgphcs.administrative_gender_code," +
						" ssdmgphcs.ethnic_group_code, ssdmgphcs.birth_date,  ssdmgphcs.marital_status_code,  ssdmgphcs.valid,row_number() over(partition by ss.id " +
						"order by ss.id) rn FROM study_subjects ss, stu_sub_demographics ssdmgphcs, identifiers stu_sub_ide, identifiers sub_ide WHERE ss.id =stu_sub_ide.spa_id " +
						"AND sub_ide.stu_sub_dmgphcs_id = ssdmgphcs.id AND stu_sub_ide.value = (:value) AND ssdmgphcs.id = ss.stu_sub_dmgphcs_id";
				sb =  new StringBuilder(ROW_NUM_PRE_STRING);
				sb.append(baseQuery);
				if(sortParameter.equals(SortParameter.subjectIdentifierType)){
					sb.append(" order by sub_ide.type");
				} else if(sortParameter.equals(SortParameter.subjectIdentifierValue)){
					sb.append(" order by sub_ide.value");
				}
			}
			
			if(sortParameter.equals(SortParameter.studyIdentifierType) || sortParameter.equals(SortParameter.studyIdentifierValue)
					|| sortParameter.equals(SortParameter.studyShortTitle)){
				baseQuery = "SELECT ss.id as study_subject_id,  ss.payment_method,  ss.reg_data_entry_status,  ssdmgphcs.id,  ssdmgphcs.first_name," +
						"	ssdmgphcs.last_name, ssdmgphcs.middle_name,  ssdmgphcs.name_prefix,  ssdmgphcs.name_suffix, ssdmgphcs.administrative_gender_code," +
						" ssdmgphcs.ethnic_group_code, ssdmgphcs.birth_date,  ssdmgphcs.marital_status_code,  ssdmgphcs.valid,row_number() over(partition by ss.id order " +
						"by ss.id) rn FROM study_subjects ss, stu_sub_demographics ssdmgphcs, study_subject_versions ssubv,  study_site_versions ssitv,  study_versions sv, " +
						"studies s, identifiers stu_sub_ide, identifiers stu_ide WHERE ss.id = ssubv.spa_id AND ssubv.study_site_ver_id = ssitv.id AND ssitv.stu_version_id = sv.id " +
					"AND sv.study_id = s.id AND ss.id =stu_sub_ide.spa_id AND stu_ide.stu_id = s.id AND stu_sub_ide.value = (:value) AND ssdmgphcs.id = ss.stu_sub_dmgphcs_id";
				sb =  new StringBuilder(ROW_NUM_PRE_STRING);
				sb.append(baseQuery);
				if(sortParameter.equals(SortParameter.studyIdentifierType)){
					sb.append(" order by stu_ide.type");
				} else if(sortParameter.equals(SortParameter.studyIdentifierValue)){
					sb.append(" order by stu_ide.value");
				} else if(sortParameter.equals(SortParameter.studyShortTitle)){
					sb.append(" order by sv.short_title_text");
				}
			}
			String[] stringArray = sb.toString().split("order");
			if(sb.toString().contains("order by") && (stringArray.length > 2) && sortParameter.getSortOrder() == SortOrder.DESCENDING){
				sb.append(" desc ");
			}
		}
		
		sb.append(ROW_NUM_POST_STRING);
		studySubjectObjects =StudySubjectDaoHelper.buildPaginationQuery(sb.toString(), min_rows, max_rows, getHibernateTemplate(), 
				this.objectFactory instanceof ObjectFactoryOracle ? true:false).setParameter("value", studySubjectIdentifierValue).list();
		return buildStudySubjects(studySubjectObjects);
	}

	// Input case 3: studySubjectConsentVersion documentId, =
	@SuppressWarnings(value="unchecked")
	public List<StudySubject> retrieveStudySubjectsByStudySubjectConsentDocumentId(
			String documentId, final Integer min_rows, final Integer max_rows, SortParameter sortParameter) {
		List<Object> studySubjectObjects = new ArrayList<Object>();
		StringBuilder sb = new StringBuilder(ROW_NUM_PRE_STRING);
		
		String baseQuery = "SELECT ss.id as study_subject_id,ss.payment_method,ss.reg_data_entry_status, ssdmgphcs.id, ssdmgphcs.first_name,ssdmgphcs.last_name, " +
				"ssdmgphcs.middle_name, ssdmgphcs.name_prefix, ssdmgphcs.name_suffix, ssdmgphcs.administrative_gender_code, ssdmgphcs.ethnic_group_code,ssdmgphcs.birth_date, " +
				" ssdmgphcs.marital_status_code,  ssdmgphcs.valid, row_number() over(partition by ss.id order by ss.id) rn FROM study_subjects ss, stu_sub_demographics ssdmgphcs," +
				" study_subject_versions ssubv, study_subject_consents sscv WHERE ss.id = ssubv.spa_id AND ssubv.id = sscv.study_subject_ver_id AND sscv.document_id =" +
				" (:documentId) AND ssdmgphcs.id = ss.stu_sub_dmgphcs_id";
		sb.append(baseQuery);
		if(sortParameter != null ) {
			if(sortParameter.equals(SortParameter.subjectLastName)){
				sb.append(" order by ssdmgphcs.last_name");
			}
		
			if(sortParameter.equals(SortParameter.studyIdentifierType) || sortParameter.equals(SortParameter.studyIdentifierValue)
					 || sortParameter.equals(SortParameter.studyShortTitle)){
				
				baseQuery = "SELECT ss.id as study_subject_id,ss.payment_method,ss.reg_data_entry_status, ssdmgphcs.id, ssdmgphcs.first_name,ssdmgphcs.last_name, " +
						"ssdmgphcs.middle_name, ssdmgphcs.name_prefix, ssdmgphcs.name_suffix, ssdmgphcs.administrative_gender_code, ssdmgphcs.ethnic_group_code," +
						"ssdmgphcs.birth_date, ssdmgphcs.marital_status_code, ssdmgphcs.valid,row_number() over(partition by ss.id order by ss.id) rn FROM study_subjects ss, " +
						"stu_sub_demographics ssdmgphcs, study_subject_versions ssubv, study_subject_consents sscv, study_site_versions ssitv,  study_versions sv, studies s, " +
						"identifiers stu_ide WHERE ss.id = ssubv.spa_id AND ssubv.id = sscv.study_subject_ver_id AND ssubv.study_site_ver_id = ssitv.id AND " +
						"ssitv.stu_version_id = sv.id AND sv.study_id = s.id AND s.id = stu_ide.stu_id AND sscv.document_id = (:documentId) AND ssdmgphcs.id = ss.stu_sub_dmgphcs_id";
				sb =  new StringBuilder(ROW_NUM_PRE_STRING);
				sb.append(baseQuery);
				if(sortParameter.equals(SortParameter.studyIdentifierType)){
					sb.append(" order by stu_ide.type");
				} else if(sortParameter.equals(SortParameter.studyIdentifierValue)){
					sb.append(" order by stu_ide.value");
				} else if(sortParameter.equals(SortParameter.studyShortTitle)){
					sb.append(" order by sv.short_title_text");
				}
			}
			
			if(sortParameter.equals(SortParameter.subjectIdentifierType) || sortParameter.equals(SortParameter.subjectIdentifierValue)){
				
				baseQuery = "SELECT ss.id as study_subject_id,ss.payment_method,ss.reg_data_entry_status, ssdmgphcs.id, ssdmgphcs.first_name,ssdmgphcs.last_name, " +
						"ssdmgphcs.middle_name, ssdmgphcs.name_prefix, ssdmgphcs.name_suffix, ssdmgphcs.administrative_gender_code, ssdmgphcs.ethnic_group_code," +
						"ssdmgphcs.birth_date, ssdmgphcs.marital_status_code,  ssdmgphcs.valid,row_number() over(partition by ss.id order by ss.id) rn FROM study_subjects ss, " +
						"stu_sub_demographics ssdmgphcs, study_subject_versions ssubv, study_subject_consents sscv, study_site_versions ssitv,  study_versions sv,  studies s, " +
						"identifiers sub_ide WHERE ss.id = ssubv.spa_id AND ssubv.id = sscv.study_subject_ver_id AND ssubv.study_site_ver_id = ssitv.id AND " +
						"ssitv.stu_version_id = sv.id AND sv.study_id = s.id AND ssdmgphcs.id = sub_ide.stu_sub_dmgphcs_id AND sscv.document_id = (:documentId) AND " +
						"ssdmgphcs.id = ss.stu_sub_dmgphcs_id";
				sb =  new StringBuilder(ROW_NUM_PRE_STRING);
				sb.append(baseQuery);
				if(sortParameter.equals(SortParameter.subjectIdentifierType)){
					sb.append(" order by sub_ide.type");
				} else if(sortParameter.equals(SortParameter.subjectIdentifierValue)){
					sb.append(" order by sub_ide.value");
				} 
			}
			
			if(sortParameter.equals(SortParameter.studySubjectIdentifierType) || sortParameter.equals(SortParameter.studySubjectIdentifierValue)){
				
				baseQuery = "SELECT ss.id as study_subject_id,ss.payment_method,ss.reg_data_entry_status, ssdmgphcs.id, ssdmgphcs.first_name,ssdmgphcs.last_name, " +
						"ssdmgphcs.middle_name, ssdmgphcs.name_prefix, ssdmgphcs.name_suffix, ssdmgphcs.administrative_gender_code, ssdmgphcs.ethnic_group_code," +
						"ssdmgphcs.birth_date, ssdmgphcs.marital_status_code,  ssdmgphcs.valid,row_number() over(partition by ss.id order by ss.id) rn FROM study_subjects ss," +
						" stu_sub_demographics ssdmgphcs, study_subject_versions ssubv, study_subject_consents sscv, study_site_versions ssitv,  study_versions sv, studies s," +
						" identifiers stu_sub_ide WHERE ss.id = ssubv.spa_id AND ssubv.id = sscv.study_subject_ver_id AND ssubv.study_site_ver_id = ssitv.id AND " +
						"ssitv.stu_version_id = sv.id AND sv.study_id = s.id AND ss.id = stu_sub_ide.spa_id AND sscv.document_id = (:documentId) AND " +
						"ssdmgphcs.id = ss.stu_sub_dmgphcs_id";
				sb =  new StringBuilder(ROW_NUM_PRE_STRING);
				sb.append(baseQuery);
				if(sortParameter.equals(SortParameter.studySubjectIdentifierType)){
					sb.append(" order by stu_sub_ide.type");
				} else if(sortParameter.equals(SortParameter.studySubjectIdentifierValue)){
					sb.append(" order by stu_sub_ide.value");
				} 
			}
			String[] stringArray = sb.toString().split("order");
			if(sb.toString().contains("order by") && (stringArray.length > 2) && sortParameter.getSortOrder() == SortOrder.DESCENDING){
				sb.append(" desc ");
			}
		}
		
		sb.append(ROW_NUM_POST_STRING);
        studySubjectObjects =StudySubjectDaoHelper.buildPaginationQuery(sb.toString(), min_rows, max_rows, getHibernateTemplate(), 
    		  this.objectFactory instanceof ObjectFactoryOracle ? true:false).setParameter("documentId", documentId).list();
       
        return buildStudySubjects(studySubjectObjects);
	}

	// Input case 4: subjectIdentifier value, =
	@SuppressWarnings(value="unchecked")
	public List<StudySubject> retrieveStudySubjectsBySubjectIdentifierValue(
			String subjectIdentifierValue, final Integer min_rows, final Integer max_rows, SortParameter sortParameter) {
		List<Object> studySubjectObjects = new ArrayList<Object>();
		StringBuilder sb = new StringBuilder(ROW_NUM_PRE_STRING);
		String baseQuery = "SELECT ss.id as study_subject_id,  ss.payment_method,  ss.reg_data_entry_status,  ssdmgphcs.id,  ssdmgphcs.first_name,  " +
				"ssdmgphcs.last_name, ssdmgphcs.middle_name,  ssdmgphcs.name_prefix,  ssdmgphcs.name_suffix, ssdmgphcs.administrative_gender_code," +
				" ssdmgphcs.ethnic_group_code, ssdmgphcs.birth_date,  ssdmgphcs.marital_status_code,  ssdmgphcs.valid,row_number() over(partition by ss.id order by ss.id) rn " +
				" FROM study_subjects ss, stu_sub_demographics ssdmgphcs, identifiers sub_ide WHERE ssdmgphcs.id =sub_ide.stu_sub_dmgphcs_id AND sub_ide.value = (:value) AND " +
				"ssdmgphcs.id = ss.stu_sub_dmgphcs_id";
		sb.append(baseQuery);
		if(sortParameter != null ) {
			if(sortParameter.equals(SortParameter.subjectLastName)){
				sb.append(" order by ssdmgphcs.last_name");
			} else if(sortParameter.equals(SortParameter.subjectIdentifierType)){
				sb.append(" order by sub_ide.type");
			} else if(sortParameter.equals(SortParameter.subjectIdentifierValue)){
				sb.append(" order by sub_ide.value");
			}
		}
		
		if(sortParameter != null ) {		
			if(sortParameter.equals(SortParameter.studySubjectIdentifierType) || sortParameter.equals(SortParameter.studySubjectIdentifierValue)){
				
				baseQuery = "SELECT ss.id as study_subject_id,  ss.payment_method,  ss.reg_data_entry_status,  ssdmgphcs.id,  ssdmgphcs.first_name,  " +
						"ssdmgphcs.last_name, ssdmgphcs.middle_name,  ssdmgphcs.name_prefix,  ssdmgphcs.name_suffix, ssdmgphcs.administrative_gender_code," +
						" ssdmgphcs.ethnic_group_code, ssdmgphcs.birth_date,  ssdmgphcs.marital_status_code,  ssdmgphcs.valid,row_number() over(partition by ss.id order" +
						" by ss.id) rn FROM study_subjects ss, stu_sub_demographics ssdmgphcs, identifiers sub_ide, identifiers stu_sub_ide WHERE " +
						"ssdmgphcs.id =sub_ide.stu_sub_dmgphcs_id AND ss.id = stu_sub_ide.spa_id AND sub_ide.value = (:value) AND ssdmgphcs.id = ss.stu_sub_dmgphcs_id";
				sb =  new StringBuilder(ROW_NUM_PRE_STRING);
				sb.append(baseQuery);
				if(sortParameter.equals(SortParameter.studySubjectIdentifierType)){
					sb.append(" order by stu_sub_ide.type");
				} else if(sortParameter.equals(SortParameter.studySubjectIdentifierValue)){
					sb.append(" order by stu_sub_ide.value");
				} 
			}
			
			if(sortParameter.equals(SortParameter.studyIdentifierType) || sortParameter.equals(SortParameter.studyIdentifierValue) 
					|| sortParameter.equals(SortParameter.studyShortTitle)){
				
				baseQuery = "SELECT ss.id as study_subject_id,  ss.payment_method,  ss.reg_data_entry_status,  ssdmgphcs.id,  ssdmgphcs.first_name,  " +
						"ssdmgphcs.last_name, ssdmgphcs.middle_name,  ssdmgphcs.name_prefix,  ssdmgphcs.name_suffix, ssdmgphcs.administrative_gender_code," +
						" ssdmgphcs.ethnic_group_code, ssdmgphcs.birth_date,  ssdmgphcs.marital_status_code,  ssdmgphcs.valid,row_number() over(partition by ss.id " +
						"order by ss.id) rn FROM study_subjects ss, stu_sub_demographics ssdmgphcs, study_subject_versions ssubv,  study_site_versions ssitv, study_versions sv, " +
						"studies s, identifiers sub_ide, identifiers stu_ide WHERE ssdmgphcs.id =sub_ide.stu_sub_dmgphcs_id AND ss.id = ssubv.spa_id AND " +
						"ssubv.study_site_ver_id = ssitv.id AND ssitv.stu_version_id = sv.id AND sv.study_id = s.id AND " +
						"s.id = stu_ide.stu_id AND sub_ide.value = (:value) AND ssdmgphcs.id = ss.stu_sub_dmgphcs_id";
				sb =  new StringBuilder(ROW_NUM_PRE_STRING);
				sb.append(baseQuery);
				if(sortParameter.equals(SortParameter.studyIdentifierType)){
					sb.append(" order by stu_ide.type");
				} else if(sortParameter.equals(SortParameter.studyIdentifierValue)){
					sb.append(" order by stu_ide.value");
				} else if(sortParameter.equals(SortParameter.studyShortTitle)){
					sb.append(" order by sv.short_title_text");
				}
			}
			String[] stringArray = sb.toString().split("order");
			if(sb.toString().contains("order by") && (stringArray.length > 2) && sortParameter.getSortOrder() == SortOrder.DESCENDING){
				sb.append(" desc ");
			}
		}
		
		sb.append(ROW_NUM_POST_STRING);
		studySubjectObjects =StudySubjectDaoHelper.buildPaginationQuery(sb.toString(), min_rows, max_rows, getHibernateTemplate(), 
				this.objectFactory instanceof ObjectFactoryOracle ? true:false).setParameter("value",subjectIdentifierValue).list();
		return buildStudySubjects(studySubjectObjects);
	}

	// Input case 5: studyIdentifier value, = ; 2. Status code, = (exact search
	// on study identifier and exact search on status code (Registry Status))
	@SuppressWarnings(value="unchecked")
	public List<StudySubject> retrieveStudySubjectsByStudyIdentifierValueAndRegistryStatusCode(
			String studyIdentifierValue, String registryStatusCode, final Integer min_rows, final Integer max_rows, SortParameter sortParameter) {
		List<Object> studySubjectObjects = new ArrayList<Object>();
		StringBuilder sb = new StringBuilder(ROW_NUM_PRE_STRING);
		String baseQuery = "SELECT ss.id as study_subject_id,  ss.payment_method,  ss.reg_data_entry_status,  ssdmgphcs.id,  ssdmgphcs.first_name," +
				" ssdmgphcs.last_name, ssdmgphcs.middle_name,  ssdmgphcs.name_prefix,  ssdmgphcs.name_suffix, ssdmgphcs.administrative_gender_code," +
				" ssdmgphcs.ethnic_group_code, ssdmgphcs.birth_date,  ssdmgphcs.marital_status_code,  ssdmgphcs.valid,row_number() over(partition by ss.id order by ss.id)" +
				" rn FROM study_subjects ss, stu_sub_demographics ssdmgphcs, study_subject_versions ssubv,  study_site_versions ssitv, study_versions sv, studies s, " +
				"identifiers stu_ide, stu_sub_reg_statuses ssrs, permissible_reg_stats prs, registry_statuses rs WHERE ss.id = ssubv.spa_id AND " +
				"ssubv.study_site_ver_id = ssitv.id AND ssitv.stu_version_id = sv.id AND sv.study_id = s.id AND ssrs.stu_sub_id = ss.id AND ssrs.per_reg_st_id = prs.id " +
				"AND prs.registry_st_id = rs.id AND s.id = stu_ide.stu_id AND stu_ide.value = (:value) AND ssdmgphcs.id = ss.stu_sub_dmgphcs_id AND rs.code =(:code)";
		sb.append(baseQuery);
		if(sortParameter != null ) {
			if(sortParameter.equals(SortParameter.studyShortTitle)){
				sb.append(" order by sv.short_title_text");
			} else if(sortParameter.equals(SortParameter.studyIdentifierType)){
				sb.append(" order by stu_ide.type");
			} else if(sortParameter.equals(SortParameter.studyIdentifierValue)){
				sb.append(" order by stu_ide.value");
			} else if(sortParameter.equals(SortParameter.subjectLastName)){
				sb.append(" order by ssdmgphcs.last_name");
			}
			
			if(sortParameter.equals(SortParameter.studySubjectIdentifierType) || sortParameter.equals(SortParameter.studySubjectIdentifierValue)){
				baseQuery = "SELECT ss.id as study_subject_id,  ss.payment_method,  ss.reg_data_entry_status,  ssdmgphcs.id,  ssdmgphcs.first_name," +
						" ssdmgphcs.last_name, ssdmgphcs.middle_name,  ssdmgphcs.name_prefix,  ssdmgphcs.name_suffix, ssdmgphcs.administrative_gender_code," +
						" ssdmgphcs.ethnic_group_code, ssdmgphcs.birth_date,  ssdmgphcs.marital_status_code,  ssdmgphcs.valid,row_number() over(partition by ss.id order " +
						"by ss.id) rn FROM study_subjects ss, stu_sub_demographics ssdmgphcs, study_subject_versions ssubv,  study_site_versions ssitv,  study_versions sv, " +
						"studies s, identifiers stu_ide, identifiers stu_sub_ide, stu_sub_reg_statuses ssrs, permissible_reg_stats prs, registry_statuses rs WHERE " +
						"ss.id = ssubv.spa_id AND ssubv.study_site_ver_id = ssitv.id AND ssitv.stu_version_id = sv.id AND sv.study_id = s.id AND ssrs.stu_sub_id = ss.id AND " +
						"ssrs.per_reg_st_id = prs.id AND prs.registry_st_id = rs.id AND s.id = stu_ide.stu_id AND ss.id = stu_sub_ide.spa_id AND stu_ide.value = (:value) AND" +
						" ssdmgphcs.id = ss.stu_sub_dmgphcs_id AND rs.code =(:code)";
				sb =  new StringBuilder(ROW_NUM_PRE_STRING);
				sb.append(baseQuery);
				if(sortParameter.equals(SortParameter.studySubjectIdentifierType)){
					sb.append(" order by stu_sub_ide.type");
				} else if(sortParameter.equals(SortParameter.studySubjectIdentifierValue)){
					sb.append(" order by stu_sub_ide.value");
				} 
			}
			
			if(sortParameter.equals(SortParameter.subjectIdentifierType) || sortParameter.equals(SortParameter.subjectIdentifierValue)){
				baseQuery = "SELECT ss.id as study_subject_id,  ss.payment_method,  ss.reg_data_entry_status,  ssdmgphcs.id,  ssdmgphcs.first_name," +
						" ssdmgphcs.last_name, ssdmgphcs.middle_name,  ssdmgphcs.name_prefix,  ssdmgphcs.name_suffix, ssdmgphcs.administrative_gender_code," +
						" ssdmgphcs.ethnic_group_code, ssdmgphcs.birth_date,  ssdmgphcs.marital_status_code,  ssdmgphcs.valid,row_number() over(partition by ss.id order " +
						"by ss.id) rn FROM study_subjects ss, stu_sub_demographics ssdmgphcs, study_subject_versions ssubv,  study_site_versions ssitv,  study_versions sv, " +
						"studies s, identifiers stu_ide, identifiers sub_ide, stu_sub_reg_statuses ssrs, permissible_reg_stats prs, registry_statuses rs WHERE ss.id = ssubv.spa_id " +
						"AND ssubv.study_site_ver_id = ssitv.id AND ssitv.stu_version_id = sv.id AND sv.study_id = s.id AND ssrs.stu_sub_id = ss.id AND ssrs.per_reg_st_id = prs.id " +
						"AND prs.registry_st_id = rs.id AND s.id = stu_ide.stu_id AND ssdmgphcs.id = sub_ide.stu_sub_dmgphcs_id AND stu_ide.value = (:value) AND ssdmgphcs.id = ss.stu_sub_dmgphcs_id AND rs.code =(:code)";
				sb =  new StringBuilder(ROW_NUM_PRE_STRING);
				sb.append(baseQuery);
				if(sortParameter.equals(SortParameter.subjectIdentifierType)){
					sb.append(" order by sub_ide.type");
				} else if(sortParameter.equals(SortParameter.subjectIdentifierValue)){
					sb.append(" order by sub_ide.value");
				} 
			}
			String[] stringArray = sb.toString().split("order");
			if(sb.toString().contains("order by") && (stringArray.length > 2) && sortParameter.getSortOrder() == SortOrder.DESCENDING){
				sb.append(" desc ");
			}
		}
		
		sb.append(ROW_NUM_POST_STRING);
		studySubjectObjects =StudySubjectDaoHelper.buildPaginationQuery(sb.toString(), min_rows, max_rows, getHibernateTemplate(), 
				this.objectFactory instanceof ObjectFactoryOracle ? true:false).setParameter("value", studyIdentifierValue).
				setParameter("code",registryStatusCode).list();
		return buildStudySubjects(studySubjectObjects);
	}

	// Input case 6: studyIdentifier value, =; 2. subjectIdentifier value, =
	@SuppressWarnings(value="unchecked")
	public List<StudySubject> retrieveStudySubjectsByStudyIdentifierValueAndSubjectIdentifierValue(
			String studyIdentifierValue, String subjectIdentifierValue, final Integer min_rows, final Integer max_rows, SortParameter sortParameter) {
		List<Object> studySubjectObjects = new ArrayList<Object>();
		StringBuilder sb = new StringBuilder(ROW_NUM_PRE_STRING);
		String baseQuery = "SELECT ss.id as study_subject_id,  ss.payment_method,  ss.reg_data_entry_status,  ssdmgphcs.id,  ssdmgphcs.first_name,  " +
				"ssdmgphcs.last_name, ssdmgphcs.middle_name,  ssdmgphcs.name_prefix,  ssdmgphcs.name_suffix, ssdmgphcs.administrative_gender_code," +
				" ssdmgphcs.ethnic_group_code, ssdmgphcs.birth_date,  ssdmgphcs.marital_status_code,  ssdmgphcs.valid, sv.short_title_text, stu_ide.type," +
				" stu_ide.value,row_number() over(partition by ss.id order by ss.id) rn FROM study_subjects ss, " +
				" stu_sub_demographics ssdmgphcs, study_subject_versions ssubv,  study_site_versions ssitv,  study_versions sv,  studies s, " +
				" identifiers stu_ide, identifiers sub_ide WHERE ss.id = ssubv.spa_id AND ssubv.study_site_ver_id = ssitv.id AND ssitv.stu_version_id = sv.id AND " +
				"sv.study_id = s.id AND s.id =stu_ide.stu_id AND stu_ide.value = (:stuIdenValue) AND ssdmgphcs.id =sub_ide.stu_sub_dmgphcs_id AND sub_ide.value = " +
				"(:subIdenValue) AND ssdmgphcs.id = ss.stu_sub_dmgphcs_id";
		sb.append(baseQuery);
		if(sortParameter != null ) {
			if(sortParameter.equals(SortParameter.studyShortTitle)){
				sb.append(" order by sv.short_title_text");
			} else if(sortParameter.equals(SortParameter.studyIdentifierType)){
				sb.append(" order by stu_ide.type");
			} else if(sortParameter.equals(SortParameter.studyIdentifierValue)){
				sb.append(" order by stu_ide.value");
			} else if(sortParameter.equals(SortParameter.subjectLastName)){
				sb.append(" order by ssdmgphcs.last_name");
			} else if(sortParameter.equals(SortParameter.subjectIdentifierType)){
				sb.append(" order by sub_ide.type");
			} else if(sortParameter.equals(SortParameter.subjectIdentifierValue)){
				sb.append(" order by sub_ide.value");
			} else if(sortParameter.equals(SortParameter.studySubjectIdentifierType) || sortParameter.equals(SortParameter.studySubjectIdentifierValue)){
				baseQuery = "SELECT ss.id as study_subject_id,  ss.payment_method,  ss.reg_data_entry_status,  ssdmgphcs.id,  ssdmgphcs.first_name,  " +
						"ssdmgphcs.last_name, ssdmgphcs.middle_name,  ssdmgphcs.name_prefix,  ssdmgphcs.name_suffix, ssdmgphcs.administrative_gender_code," +
						" ssdmgphcs.ethnic_group_code, ssdmgphcs.birth_date,  ssdmgphcs.marital_status_code,  ssdmgphcs.valid,row_number() over(partition by ss.id " +
						"order by ss.id) rn FROM study_subjects ss, stu_sub_demographics ssdmgphcs, study_subject_versions ssubv,  study_site_versions ssitv, study_versions sv," +
						" studies s, identifiers stu_ide, identifiers sub_ide, identifiers stu_sub_ide WHERE ss.id = ssubv.spa_id AND ssubv.study_site_ver_id = ssitv.id AND " +
						"ssitv.stu_version_id = sv.id AND sv.study_id = s.id AND s.id =stu_ide.stu_id AND stu_ide.value = (:stuIdenValue) AND ssdmgphcs.id = " +
						"sub_ide.stu_sub_dmgphcs_id AND ss.id =stu_sub_ide.spa_id AND sub_ide.value = (:subIdenValue) AND ssdmgphcs.id = ss.stu_sub_dmgphcs_id";
				sb =  new StringBuilder(ROW_NUM_PRE_STRING);
				sb.append(baseQuery);
				if(sortParameter.equals(SortParameter.studySubjectIdentifierType)){
					sb.append(" order by stu_sub_ide.type");
				} else if(sortParameter.equals(SortParameter.studySubjectIdentifierValue)){
					sb.append(" order by stu_sub_ide.value");
				} 
			}
			String[] stringArray = sb.toString().split("order");
			if(sb.toString().contains("order by") && (stringArray.length > 2) && sortParameter.getSortOrder() == SortOrder.DESCENDING){
				sb.append(" desc ");
			}
		}
			
		sb.append(ROW_NUM_POST_STRING);
		studySubjectObjects =StudySubjectDaoHelper.buildPaginationQuery(sb.toString(), min_rows, max_rows, getHibernateTemplate(), 
				this.objectFactory instanceof ObjectFactoryOracle ? true:false).setParameter("stuIdenValue", studyIdentifierValue).
				setParameter("subIdenValue",subjectIdentifierValue).list();
		return buildStudySubjects(studySubjectObjects);
	}

	// Input case 7: 1. studyIdentifier value, =; 2.
	// study-typeInternal-identifier, =
	@SuppressWarnings(value="unchecked")
	public List<StudySubject> retrieveStudySubjectsByStudyIdentifierValueAndType(
			String studyIdentifierValue, String studyIdentifierType, final Integer min_rows, final Integer max_rows, SortParameter sortParameter) {
		List<Object> studySubjectObjects = new ArrayList<Object>();
		StringBuilder sb = new StringBuilder(ROW_NUM_PRE_STRING);
		String baseQuery = "SELECT ss.id as study_subject_id,  ss.payment_method,  ss.reg_data_entry_status,  ssdmgphcs.id,  ssdmgphcs.first_name,  " +
				"ssdmgphcs.last_name, ssdmgphcs.middle_name,  ssdmgphcs.name_prefix,  ssdmgphcs.name_suffix, ssdmgphcs.administrative_gender_code," +
				" ssdmgphcs.ethnic_group_code, ssdmgphcs.birth_date,  ssdmgphcs.marital_status_code,  ssdmgphcs.valid,row_number() over(partition by ss.id order by ss.id)" +
				" rn FROM study_subjects ss, stu_sub_demographics ssdmgphcs, study_subject_versions ssubv,  study_site_versions ssitv,  study_versions sv,  studies s, " +
				" identifiers stu_ide WHERE ss.id = ssubv.spa_id AND ssubv.study_site_ver_id = ssitv.id AND ssitv.stu_version_id = sv.id AND sv.study_id = " +
				"s.id AND s.id =stu_ide.stu_id AND stu_ide.value = (:value) AND stu_ide.type = (:type) AND ssdmgphcs.id = ss.stu_sub_dmgphcs_id";
		sb.append(baseQuery);
		if(sortParameter != null ) {
			if(sortParameter.equals(SortParameter.studyShortTitle)){
				sb.append(" order by sv.short_title_text");
			} else if(sortParameter.equals(SortParameter.studyIdentifierType)){
				sb.append(" order by stu_ide.type");
			} else if(sortParameter.equals(SortParameter.studyIdentifierValue)){
				sb.append(" order by stu_ide.value");
			} else if(sortParameter.equals(SortParameter.subjectLastName)){
				sb.append(" order by ssdmgphcs.last_name");
			}
			
			if(sortParameter.equals(SortParameter.subjectIdentifierType) || sortParameter.equals(SortParameter.subjectIdentifierValue)){
				baseQuery = "SELECT ss.id as study_subject_id,  ss.payment_method,  ss.reg_data_entry_status,  ssdmgphcs.id,  ssdmgphcs.first_name,  " +
						"ssdmgphcs.last_name, ssdmgphcs.middle_name,  ssdmgphcs.name_prefix,  ssdmgphcs.name_suffix, ssdmgphcs.administrative_gender_code," +
						" ssdmgphcs.ethnic_group_code, ssdmgphcs.birth_date,  ssdmgphcs.marital_status_code,  ssdmgphcs.valid,row_number() over(partition by ss.id order" +
						" by ss.id) rn FROM study_subjects ss, stu_sub_demographics ssdmgphcs, study_subject_versions ssubv, study_site_versions ssitv, study_versions sv, " +
						"studies s, identifiers stu_ide, identifiers sub_ide WHERE ss.id = ssubv.spa_id AND ssubv.study_site_ver_id = ssitv.id AND ssitv.stu_version_id = sv.id " +
						"AND sv.study_id = s.id AND s.id =stu_ide.stu_id AND ssdmgphcs.id = sub_ide.stu_sub_dmgphcs_id AND stu_ide.value = (:value) AND stu_ide.type = (:type) " +
						"AND ssdmgphcs.id = ss.stu_sub_dmgphcs_id";
				sb =  new StringBuilder(ROW_NUM_PRE_STRING);
				sb.append(baseQuery);
				if(sortParameter.equals(SortParameter.subjectIdentifierType)){
					sb.append(" order by sub_ide.type");
				} else if(sortParameter.equals(SortParameter.subjectIdentifierValue)){
					sb.append(" order by sub_ide.value");
				}
			}
			
			if(sortParameter.equals(SortParameter.studySubjectIdentifierType) || sortParameter.equals(SortParameter.studySubjectIdentifierValue)){
				baseQuery = "SELECT ss.id as study_subject_id,  ss.payment_method,  ss.reg_data_entry_status,  ssdmgphcs.id,  ssdmgphcs.first_name,  " +
						"ssdmgphcs.last_name, ssdmgphcs.middle_name,  ssdmgphcs.name_prefix,  ssdmgphcs.name_suffix, ssdmgphcs.administrative_gender_code," +
						" ssdmgphcs.ethnic_group_code, ssdmgphcs.birth_date,  ssdmgphcs.marital_status_code,  ssdmgphcs.valid,row_number() over(partition by ss.id order " +
						"by ss.id) rn FROM study_subjects ss, stu_sub_demographics ssdmgphcs, study_subject_versions ssubv, study_site_versions ssitv, study_versions sv, " +
						"studies s, identifiers stu_ide, identifiers stu_sub_ide WHERE ss.id = ssubv.spa_id AND ssubv.study_site_ver_id = ssitv.id AND " +
						"ssitv.stu_version_id = sv.id AND sv.study_id = s.id AND s.id =stu_ide.stu_id AND ss.id = stu_sub_ide.spa_id AND stu_ide.value = (:value) AND " +
						"stu_ide.type = (:type) AND ssdmgphcs.id = ss.stu_sub_dmgphcs_id";
				sb =  new StringBuilder(ROW_NUM_PRE_STRING);
				sb.append(baseQuery);
				if(sortParameter.equals(SortParameter.studySubjectIdentifierType)){
					sb.append(" order by stu_sub_ide.type");
				} else if(sortParameter.equals(SortParameter.studySubjectIdentifierValue)){
					sb.append(" order by stu_sub_ide.value");
				}
			}
			String[] stringArray = sb.toString().split("order");
			if(sb.toString().contains("order by") && (stringArray.length > 2) && sortParameter.getSortOrder() == SortOrder.DESCENDING){
				sb.append(" desc ");
			}
		}
			
		sb.append(ROW_NUM_POST_STRING);
		studySubjectObjects =StudySubjectDaoHelper.buildPaginationQuery(sb.toString(), min_rows, max_rows, getHibernateTemplate(), 
				this.objectFactory instanceof ObjectFactoryOracle ? true:false).setParameter("value", studyIdentifierValue).
				setParameter("type",studyIdentifierType).list();
		return buildStudySubjects(studySubjectObjects);
	}

	// Input case 8: studySubjectIdentifier value, =; 2.
	// studySubject-typeInternal-identifier,=
	@SuppressWarnings(value="unchecked")
	public List<StudySubject> retrieveStudySubjectsByStudySubjectIdentifierValueAndType(
			String studySubjectIdentifierValue,
			String studySubjectIdentifierType, final Integer min_rows, final Integer max_rows, SortParameter sortParameter) {
		List<Object> studySubjectObjects = new ArrayList<Object>();
		StringBuilder sb = new StringBuilder(ROW_NUM_PRE_STRING);
		String baseQuery = "SELECT ss.id as study_subject_id,  ss.payment_method,  ss.reg_data_entry_status,  ssdmgphcs.id,  ssdmgphcs.first_name," +
				"	ssdmgphcs.last_name, ssdmgphcs.middle_name,  ssdmgphcs.name_prefix,  ssdmgphcs.name_suffix, ssdmgphcs.administrative_gender_code," +
				" ssdmgphcs.ethnic_group_code, ssdmgphcs.birth_date,  ssdmgphcs.marital_status_code,  ssdmgphcs.valid,row_number() over(partition by ss.id order by ss.id)" +
				" rn FROM study_subjects ss, stu_sub_demographics ssdmgphcs, identifiers stu_sub_ide WHERE ss.id =stu_sub_ide.spa_id AND stu_sub_ide.value = (:value) " +
				"AND stu_sub_ide.type = (:type) AND ssdmgphcs.id = ss.stu_sub_dmgphcs_id";
		sb.append(baseQuery);
		if(sortParameter != null ) {
			if(sortParameter.equals(SortParameter.studySubjectIdentifierType)){
				sb.append(" order by stu_sub_ide.type");
			} else if(sortParameter.equals(SortParameter.studySubjectIdentifierValue)){
				sb.append(" order by stu_sub_ide.value");
			} else if(sortParameter.equals(SortParameter.subjectLastName)){
				sb.append(" order by ssdmgphcs.last_name");
			}
			
			if(sortParameter.equals(SortParameter.subjectIdentifierType) || sortParameter.equals(SortParameter.subjectIdentifierValue)){
				baseQuery = "SELECT ss.id as study_subject_id,  ss.payment_method,  ss.reg_data_entry_status,  ssdmgphcs.id,  ssdmgphcs.first_name," +
						"	ssdmgphcs.last_name, ssdmgphcs.middle_name,  ssdmgphcs.name_prefix,  ssdmgphcs.name_suffix, ssdmgphcs.administrative_gender_code," +
						" ssdmgphcs.ethnic_group_code, ssdmgphcs.birth_date,  ssdmgphcs.marital_status_code,  ssdmgphcs.valid,row_number() over(partition by ss.id " +
						"order by ss.id) rn FROM study_subjects ss, stu_sub_demographics ssdmgphcs, identifiers stu_sub_ide, identifiers sub_ide WHERE ss.id =stu_sub_ide.spa_id " +
						"AND sub_ide.stu_sub_dmgphcs_id = ssdmgphcs.id AND stu_sub_ide.value = (:value) AND stu_sub_ide.type = (:type) AND ssdmgphcs.id = ss.stu_sub_dmgphcs_id";
				sb =  new StringBuilder(ROW_NUM_PRE_STRING);
				sb.append(baseQuery);
				if(sortParameter.equals(SortParameter.subjectIdentifierType)){
					sb.append(" order by sub_ide.type");
				} else if(sortParameter.equals(SortParameter.subjectIdentifierValue)){
					sb.append(" order by sub_ide.value");
				}
			}
			
			if(sortParameter.equals(SortParameter.studyIdentifierType) || sortParameter.equals(SortParameter.studyIdentifierValue)
					|| sortParameter.equals(SortParameter.studyShortTitle)){
				baseQuery = "SELECT ss.id as study_subject_id,  ss.payment_method,  ss.reg_data_entry_status,  ssdmgphcs.id,  ssdmgphcs.first_name," +
						"	ssdmgphcs.last_name, ssdmgphcs.middle_name,  ssdmgphcs.name_prefix,  ssdmgphcs.name_suffix, ssdmgphcs.administrative_gender_code," +
						" ssdmgphcs.ethnic_group_code, ssdmgphcs.birth_date,  ssdmgphcs.marital_status_code, ssdmgphcs.valid,row_number() over(partition by ss.id order" +
						" by ss.id) rn FROM study_subjects ss, stu_sub_demographics ssdmgphcs, study_subject_versions ssubv, study_site_versions ssitv, study_versions sv, " +
						"studies s, identifiers stu_sub_ide, identifiers stu_ide WHERE ss.id = ssubv.spa_id AND ssubv.study_site_ver_id = ssitv.id AND ssitv.stu_version_id = sv.id " +
					"AND sv.study_id = s.id AND ss.id =stu_sub_ide.spa_id AND stu_ide.stu_id = s.id AND stu_sub_ide.value = (:value) AND stu_sub_ide.type = (:type) " +
					"AND ssdmgphcs.id = ss.stu_sub_dmgphcs_id";
				sb =  new StringBuilder(ROW_NUM_PRE_STRING);;
				sb.append(baseQuery);
				if(sortParameter.equals(SortParameter.studyIdentifierType)){
					sb.append(" order by stu_ide.type");
				} else if(sortParameter.equals(SortParameter.studyIdentifierValue)){
					sb.append(" order by stu_ide.value");
				} else if(sortParameter.equals(SortParameter.studyShortTitle)){
					sb.append(" order by sv.short_title_text");
				}
			}
			String[] stringArray = sb.toString().split("order");
			if(sb.toString().contains("order by") && (stringArray.length > 2) && sortParameter.getSortOrder() == SortOrder.DESCENDING){
				sb.append(" desc ");
			}
		}
		
		sb.append(ROW_NUM_POST_STRING);
		studySubjectObjects =StudySubjectDaoHelper.buildPaginationQuery(sb.toString(), min_rows, max_rows, getHibernateTemplate(), 
				this.objectFactory instanceof ObjectFactoryOracle ? true:false).setParameter("value", studySubjectIdentifierValue).
				setParameter("type",studySubjectIdentifierType).list();
		return buildStudySubjects(studySubjectObjects);
	}

	// Input case 9: subjectIdentifier value, = ; 2.
	// subject-typeInternal-identifier,=
	@SuppressWarnings(value="unchecked")
	public List<StudySubject> retrieveStudySubjectsBySubjectIdentifierValueAndType(
			String subjectIdentifierValue, String subjectIdentifierType, final Integer min_rows, final Integer max_rows, SortParameter sortParameter) {
		List<Object> studySubjectObjects = new ArrayList<Object>();
		StringBuilder sb = new StringBuilder(ROW_NUM_PRE_STRING);
		String baseQuery = "SELECT ss.id as study_subject_id,  ss.payment_method,  ss.reg_data_entry_status,  ssdmgphcs.id,  ssdmgphcs.first_name,  " +
				"ssdmgphcs.last_name, ssdmgphcs.middle_name,  ssdmgphcs.name_prefix,  ssdmgphcs.name_suffix, ssdmgphcs.administrative_gender_code," +
				" ssdmgphcs.ethnic_group_code, ssdmgphcs.birth_date,  ssdmgphcs.marital_status_code,  ssdmgphcs.valid,row_number() over(partition by ss.id " +
				"order by ss.id) rn FROM study_subjects ss, stu_sub_demographics ssdmgphcs, identifiers sub_ide WHERE ssdmgphcs.id =sub_ide.stu_sub_dmgphcs_id " +
				"AND sub_ide.value = (:value) AND sub_ide.type = (:type) AND ssdmgphcs.id = ss.stu_sub_dmgphcs_id";
		sb.append(baseQuery);
		if(sortParameter != null ) {
			if(sortParameter.equals(SortParameter.subjectLastName)){
				sb.append(" order by ssdmgphcs.last_name");
			} else if(sortParameter.equals(SortParameter.subjectIdentifierType)){
				sb.append(" order by sub_ide.type");
			} else if(sortParameter.equals(SortParameter.subjectIdentifierValue)){
				sb.append(" order by sub_ide.value");
			}
		}
		
		if(sortParameter != null ) {
			if(sortParameter.equals(SortParameter.studySubjectIdentifierType) || sortParameter.equals(SortParameter.studySubjectIdentifierValue)){
				
				baseQuery = "SELECT ss.id as study_subject_id,  ss.payment_method,  ss.reg_data_entry_status,  ssdmgphcs.id,  ssdmgphcs.first_name,  " +
						"ssdmgphcs.last_name, ssdmgphcs.middle_name,  ssdmgphcs.name_prefix,  ssdmgphcs.name_suffix, ssdmgphcs.administrative_gender_code," +
						" ssdmgphcs.ethnic_group_code, ssdmgphcs.birth_date,  ssdmgphcs.marital_status_code,  ssdmgphcs.valid,row_number() over(partition by ss.id " +
						"order by ss.id) rn FROM study_subjects ss, stu_sub_demographics ssdmgphcs, identifiers sub_ide, identifiers stu_sub_ide WHERE " +
						"ssdmgphcs.id =sub_ide.stu_sub_dmgphcs_id AND ss.id = stu_sub_ide.spa_id AND sub_ide.value = (:value)  AND sub_ide.type = (:type) AND " +
						"ssdmgphcs.id = ss.stu_sub_dmgphcs_id";
				sb =  new StringBuilder(ROW_NUM_PRE_STRING);
				sb.append(baseQuery);
				if(sortParameter.equals(SortParameter.studySubjectIdentifierType)){
					sb.append(" order by stu_sub_ide.type");
				} else if(sortParameter.equals(SortParameter.studySubjectIdentifierValue)){
					sb.append(" order by stu_sub_ide.value");
				} 
			}
			
			if(sortParameter.equals(SortParameter.studyIdentifierType) || sortParameter.equals(SortParameter.studyIdentifierValue) 
					|| sortParameter.equals(SortParameter.studyShortTitle)){
				
				baseQuery = "SELECT ss.id as study_subject_id,  ss.payment_method,  ss.reg_data_entry_status,  ssdmgphcs.id,  ssdmgphcs.first_name,  " +
						"ssdmgphcs.last_name, ssdmgphcs.middle_name,  ssdmgphcs.name_prefix,  ssdmgphcs.name_suffix, ssdmgphcs.administrative_gender_code," +
						" ssdmgphcs.ethnic_group_code, ssdmgphcs.birth_date,  ssdmgphcs.marital_status_code, ssdmgphcs.valid,row_number() over(partition by" +
						" ss.id order by ss.id) rn FROM study_subjects ss, stu_sub_demographics ssdmgphcs, study_subject_versions ssubv,  study_site_versions ssitv," +
						" study_versions sv,  studies s, identifiers sub_ide, identifiers stu_ide WHERE ssdmgphcs.id =sub_ide.stu_sub_dmgphcs_id AND ss.id = ssubv.spa_id AND " +
						"ssubv.study_site_ver_id = ssitv.id AND ssitv.stu_version_id = sv.id AND sv.study_id = s.id AND " +
						"s.id = stu_ide.stu_id AND sub_ide.value = (:value)  AND sub_ide.type = (:type) AND ssdmgphcs.id = ss.stu_sub_dmgphcs_id";
				sb =  new StringBuilder(ROW_NUM_PRE_STRING);
				sb.append(baseQuery);
				if(sortParameter.equals(SortParameter.studyIdentifierType)){
					sb.append(" order by stu_ide.type");
				} else if(sortParameter.equals(SortParameter.studyIdentifierValue)){
					sb.append(" order by stu_ide.value");
				} else if(sortParameter.equals(SortParameter.studyShortTitle)){
					sb.append(" order by sv.short_title_text");
				}
			}
			String[] stringArray = sb.toString().split("order");
			if(sb.toString().contains("order by") && (stringArray.length > 2) && sortParameter.getSortOrder() == SortOrder.DESCENDING){
				sb.append(" desc ");
			}
		}
		
		sb.append(ROW_NUM_POST_STRING);
		studySubjectObjects =StudySubjectDaoHelper.buildPaginationQuery(sb.toString(), min_rows, max_rows, getHibernateTemplate(),
				this.objectFactory instanceof ObjectFactoryOracle ? true:false).setParameter("value", subjectIdentifierValue).
				setParameter("type",subjectIdentifierType).list();
		return buildStudySubjects(studySubjectObjects);
	}

	// Input case 10: studyIdentifier value, =; 2. statusCode, =; 3. lastName,  !=
	@SuppressWarnings(value="unchecked")
	public List<StudySubject> retrieveStudySubjectsByStudyIdentifierAndRegistryStatusCodeAndLastName(
			String studyIdentifierValue, String registryStatusCode,
			String lastName, final Integer min_rows, final Integer max_rows, SortParameter sortParameter) {
		List<Object> studySubjectObjects = new ArrayList<Object>();
		StringBuilder sb = new StringBuilder(ROW_NUM_PRE_STRING);
		String baseQuery = "SELECT ss.id as study_subject_id,  ss.payment_method,  ss.reg_data_entry_status,  ssdmgphcs.id,  ssdmgphcs.first_name," +
				" ssdmgphcs.last_name, ssdmgphcs.middle_name,  ssdmgphcs.name_prefix,  ssdmgphcs.name_suffix, ssdmgphcs.administrative_gender_code," +
				" ssdmgphcs.ethnic_group_code, ssdmgphcs.birth_date,  ssdmgphcs.marital_status_code,  ssdmgphcs.valid,row_number() over(partition by ss.id " +
				"order by ss.id) rn FROM study_subjects ss, stu_sub_demographics ssdmgphcs, study_subject_versions ssubv,  study_site_versions ssitv,  study_versions sv, " +
				" studies s, identifiers stu_ide, stu_sub_reg_statuses ssrs, permissible_reg_stats prs, registry_statuses rs WHERE ss.id = ssubv.spa_id AND " +
				"ssubv.study_site_ver_id = ssitv.id AND ssitv.stu_version_id = sv.id AND sv.study_id = s.id AND ssrs.stu_sub_id = ss.id AND ssrs.per_reg_st_id = prs.id " +
				"AND prs.registry_st_id = rs.id AND s.id =stu_ide.stu_id AND stu_ide.value = (:value) AND ssdmgphcs.id = ss.stu_sub_dmgphcs_id AND rs.code =(:code) " +
				"AND ssdmgphcs.last_name != (:lastName)";
		
		sb.append(baseQuery);
		if(sortParameter != null ) {
			if(sortParameter.equals(SortParameter.studyShortTitle)){
				sb.append(" order by sv.short_title_text");
			} else if(sortParameter.equals(SortParameter.studyIdentifierType)){
				sb.append(" order by stu_ide.type");
			} else if(sortParameter.equals(SortParameter.studyIdentifierValue)){
				sb.append(" order by stu_ide.value");
			} else if(sortParameter.equals(SortParameter.subjectLastName)){
				sb.append(" order by ssdmgphcs.last_name");
			}
			
			if(sortParameter.equals(SortParameter.subjectIdentifierType) || sortParameter.equals(SortParameter.subjectIdentifierValue)){
				baseQuery = "SELECT ss.id as study_subject_id,  ss.payment_method,  ss.reg_data_entry_status,  ssdmgphcs.id,  ssdmgphcs.first_name," +
						" ssdmgphcs.last_name, ssdmgphcs.middle_name,  ssdmgphcs.name_prefix,  ssdmgphcs.name_suffix, ssdmgphcs.administrative_gender_code," +
						" ssdmgphcs.ethnic_group_code, ssdmgphcs.birth_date,  ssdmgphcs.marital_status_code,  ssdmgphcs.valid,row_number() over(partition by ss.id" +
						" order by ss.id) rn FROM study_subjects ss, stu_sub_demographics ssdmgphcs, study_subject_versions ssubv,  study_site_versions ssitv, " +
						"study_versions sv,  studies s, identifiers stu_ide, identifiers sub_ide, stu_sub_reg_statuses ssrs, permissible_reg_stats prs, registry_statuses rs" +
						" WHERE ss.id = ssubv.spa_id AND ssubv.study_site_ver_id = ssitv.id AND ssitv.stu_version_id = sv.id AND sv.study_id = s.id AND ssrs.stu_sub_id = ss.id " +
						"AND ssrs.per_reg_st_id = prs.id AND prs.registry_st_id = rs.id AND s.id =stu_ide.stu_id AND ssdmgphcs.id = sub_ide.stu_sub_dmgphcs_id AND " +
						"stu_ide.value = (:value) AND ssdmgphcs.id = ss.stu_sub_dmgphcs_id AND rs.code =(:code) AND ssdmgphcs.last_name != (:lastName)";
				sb =  new StringBuilder(ROW_NUM_PRE_STRING);
				sb.append(baseQuery);
				if(sortParameter.equals(SortParameter.subjectIdentifierType)){
					sb.append(" order by sub_ide.type");
				} else if(sortParameter.equals(SortParameter.subjectIdentifierValue)){
					sb.append(" order by sub_ide.value");
				}
			}
			
			if(sortParameter.equals(SortParameter.studySubjectIdentifierType) || sortParameter.equals(SortParameter.studySubjectIdentifierValue)){
				baseQuery = "SELECT ss.id as study_subject_id,  ss.payment_method,  ss.reg_data_entry_status,  ssdmgphcs.id,  ssdmgphcs.first_name," +
						" ssdmgphcs.last_name, ssdmgphcs.middle_name,  ssdmgphcs.name_prefix,  ssdmgphcs.name_suffix, ssdmgphcs.administrative_gender_code," +
						" ssdmgphcs.ethnic_group_code, ssdmgphcs.birth_date,  ssdmgphcs.marital_status_code,  ssdmgphcs.valid,row_number() over(partition by ss.id " +
						"order by ss.id) rn FROM study_subjects ss, stu_sub_demographics ssdmgphcs, study_subject_versions ssubv,  study_site_versions ssitv, " +
						"study_versions sv,  studies s, identifiers stu_ide, identifiers stu_sub_ide, stu_sub_reg_statuses ssrs, permissible_reg_stats prs, " +
						"registry_statuses rs WHERE ss.id = ssubv.spa_id AND ssubv.study_site_ver_id = ssitv.id AND ssitv.stu_version_id = sv.id AND sv.study_id = s.id" +
						" AND ssrs.stu_sub_id = ss.id AND ssrs.per_reg_st_id = prs.id AND prs.registry_st_id = rs.id AND s.id =stu_ide.stu_id AND ss.id = stu_sub_ide.spa_id" +
						" AND stu_ide.value = (:value) AND ssdmgphcs.id = ss.stu_sub_dmgphcs_id AND rs.code =(:code) AND ssdmgphcs.last_name != (:lastName)";
				sb =  new StringBuilder(ROW_NUM_PRE_STRING);
				sb.append(baseQuery);
				if(sortParameter.equals(SortParameter.studySubjectIdentifierType)){
					sb.append(" order by stu_sub_ide.type");
				} else if(sortParameter.equals(SortParameter.studySubjectIdentifierValue)){
					sb.append(" order by stu_sub_ide.value");
				}
			}
			String[] stringArray = sb.toString().split("order");
			if(sb.toString().contains("order by") && (stringArray.length > 2) && sortParameter.getSortOrder() == SortOrder.DESCENDING){
				sb.append(" desc ");
			}
		}
		
		sb.append(ROW_NUM_POST_STRING);
		studySubjectObjects =StudySubjectDaoHelper.buildPaginationQuery(sb.toString(), min_rows, max_rows, getHibernateTemplate(), 
				this.objectFactory instanceof ObjectFactoryOracle ? true:false).setParameter("value", studyIdentifierValue).
				setParameter("code",registryStatusCode).setParameter("lastName",lastName).list();
		return buildStudySubjects(studySubjectObjects);
	}

	// Input case 11: studyIdentifier value, =; 2. statusCode, !=; 3. lastName,
	// !=; 4. effective-StudySubjectRegistryStatus-Date, >
	@SuppressWarnings(value="unchecked")
	public List<StudySubject> retrieveStudySubjectsByStudyIdentifierAndRegistryStatusCodeAndLastNameAndRegistryStatusEffeciveDate(
			String studyIdentifierValue, String statusCode, String lastName,
			Date registryStatusEffectiveDate, final Integer min_rows, final Integer max_rows, SortParameter sortParameter) {
		List<Object> studySubjectObjects = new ArrayList<Object>();
		StringBuilder sb = new StringBuilder(ROW_NUM_PRE_STRING);
		String baseQuery = "SELECT ss.id as study_subject_id,  ss.payment_method,  ss.reg_data_entry_status,  ssdmgphcs.id,  ssdmgphcs.first_name," +
				" ssdmgphcs.last_name, ssdmgphcs.middle_name,  ssdmgphcs.name_prefix,  ssdmgphcs.name_suffix, ssdmgphcs.administrative_gender_code," +
				" ssdmgphcs.ethnic_group_code, ssdmgphcs.birth_date,  ssdmgphcs.marital_status_code,  ssdmgphcs.valid,row_number() over(partition by ss.id order" +
				" by ss.id) rn FROM study_subjects ss, stu_sub_demographics ssdmgphcs, study_subject_versions ssubv,  study_site_versions ssitv,  study_versions sv, " +
				"studies s, identifiers stu_ide, stu_sub_reg_statuses ssrs, permissible_reg_stats prs, registry_statuses rs WHERE ss.id = ssubv.spa_id AND " +
				"ssubv.study_site_ver_id = ssitv.id AND ssitv.stu_version_id = sv.id AND sv.study_id = s.id AND ssrs.stu_sub_id = ss.id AND ssrs.per_reg_st_id = prs.id" +
				" AND prs.registry_st_id = rs.id AND s.id =stu_ide.stu_id AND stu_ide.value = (:value) AND ssdmgphcs.id = ss.stu_sub_dmgphcs_id AND rs.code != (:code) AND " +
				"ssdmgphcs.last_name != (:lastName) AND ssrs.effective_date > to_date( (:dateString),'mm/dd/yyyy')";
		
		sb.append(baseQuery);
		if(sortParameter != null ) {
			if(sortParameter.equals(SortParameter.studyShortTitle)){
				sb.append(" order by sv.short_title_text");
			} else if(sortParameter.equals(SortParameter.studyIdentifierType)){
				sb.append(" order by stu_ide.type");
			} else if(sortParameter.equals(SortParameter.studyIdentifierValue)){
				sb.append(" order by stu_ide.value");
			} else if(sortParameter.equals(SortParameter.subjectLastName)){
				sb.append(" order by ssdmgphcs.last_name");
			}
			
			if(sortParameter.equals(SortParameter.subjectIdentifierType) || sortParameter.equals(SortParameter.subjectIdentifierValue)){
				baseQuery =  "SELECT ss.id as study_subject_id,  ss.payment_method,  ss.reg_data_entry_status,  ssdmgphcs.id,  ssdmgphcs.first_name," +
						" ssdmgphcs.last_name, ssdmgphcs.middle_name,  ssdmgphcs.name_prefix,  ssdmgphcs.name_suffix, ssdmgphcs.administrative_gender_code," +
						" ssdmgphcs.ethnic_group_code, ssdmgphcs.birth_date,  ssdmgphcs.marital_status_code,  ssdmgphcs.valid,row_number() over(partition by ss.id order by ss.id) rn FROM study_subjects ss, " +
						"stu_sub_demographics ssdmgphcs, study_subject_versions ssubv,  study_site_versions ssitv,  study_versions sv,  studies s, identifiers stu_ide," +
						" identifiers sub_ide, stu_sub_reg_statuses ssrs, permissible_reg_stats prs, registry_statuses rs WHERE ss.id = ssubv.spa_id AND ssubv.study_site_ver_id = ssitv.id AND" +
						" ssitv.stu_version_id = sv.id AND sv.study_id = s.id AND ssrs.stu_sub_id = ss.id AND ssrs.per_reg_st_id = prs.id AND prs.registry_st_id = rs.id AND " +
						"s.id =stu_ide.stu_id AND ssdmgphcs.id =sub_ide.stu_sub_dmgphcs_id AND stu_ide.value = (:value) AND ssdmgphcs.id = ss.stu_sub_dmgphcs_id AND rs.code != (:code) AND ssdmgphcs.last_name != (:lastName)  AND " +
						"ssrs.effective_date > to_date( (:dateString),'mm/dd/yyyy')";
				sb =  new StringBuilder(ROW_NUM_PRE_STRING);
				sb.append(baseQuery);
				if(sortParameter.equals(SortParameter.subjectIdentifierType)){
					sb.append(" order by sub_ide.type");
				} else if(sortParameter.equals(SortParameter.subjectIdentifierValue)){
					sb.append(" order by sub_ide.value");
				}
			}
			
			if(sortParameter.equals(SortParameter.studySubjectIdentifierType) || sortParameter.equals(SortParameter.studySubjectIdentifierValue)){
				baseQuery = "SELECT ss.id as study_subject_id,  ss.payment_method,  ss.reg_data_entry_status,  ssdmgphcs.id,  ssdmgphcs.first_name," +
						" ssdmgphcs.last_name, ssdmgphcs.middle_name,  ssdmgphcs.name_prefix,  ssdmgphcs.name_suffix, ssdmgphcs.administrative_gender_code," +
						" ssdmgphcs.ethnic_group_code, ssdmgphcs.birth_date,  ssdmgphcs.marital_status_code,  ssdmgphcs.valid,row_number() over(partition by ss.id order " +
						"by ss.id) rn FROM study_subjects ss, stu_sub_demographics ssdmgphcs, study_subject_versions ssubv,  study_site_versions ssitv,  study_versions sv, " +
						" studies s, identifiers stu_ide,identifiers stu_sub_ide, stu_sub_reg_statuses ssrs, permissible_reg_stats prs, registry_statuses rs " +
						"WHERE ss.id = ssubv.spa_id AND ssubv.study_site_ver_id = ssitv.id AND ssitv.stu_version_id = sv.id AND sv.study_id = s.id AND ssrs.stu_sub_id = ss.id " +
						"AND ssrs.per_reg_st_id = prs.id AND prs.registry_st_id = rs.id AND s.id =stu_ide.stu_id AND ss.id =stu_sub_ide.spa_id AND stu_ide.value = (:value) AND " +
						"ssdmgphcs.id = ss.stu_sub_dmgphcs_id AND rs.code != (:code) AND ssdmgphcs.last_name != (:lastName)  AND ssrs.effective_date > to_date( (:dateString),'mm/dd/yyyy')";
				sb =  new StringBuilder(ROW_NUM_PRE_STRING);
				sb.append(baseQuery);
				if(sortParameter.equals(SortParameter.studySubjectIdentifierType)){
					sb.append(" order by stu_sub_ide.type");
				} else if(sortParameter.equals(SortParameter.studySubjectIdentifierValue)){
					sb.append(" order by stu_sub_ide.value");
				}
			}
			String[] stringArray = sb.toString().split("order");
			if(sb.toString().contains("order by") && (stringArray.length > 2) && sortParameter.getSortOrder() == SortOrder.DESCENDING){
				sb.append(" desc ");
			}
		}
		
		sb.append(ROW_NUM_POST_STRING);
		studySubjectObjects =StudySubjectDaoHelper.buildPaginationQuery(sb.toString(), min_rows, max_rows, getHibernateTemplate(), 
				this.objectFactory instanceof ObjectFactoryOracle ? true:false).setParameter("value", studyIdentifierValue).
				setParameter("code",statusCode).setParameter("lastName",lastName).setParameter("lastName",lastName).
				setParameter("dateString",DateUtil.formatDate(registryStatusEffectiveDate, "MM/dd/yyyy")).list();
		return buildStudySubjects(studySubjectObjects);
	}

	// Input case 12: .studyIdentifier value, =; 2. studySubjectIdentifier value, =
	@SuppressWarnings(value="unchecked")
	public List<StudySubject> retrieveStudySubjectsByStudyIdentifierValueAndStudySubjectIdentifierValue(
			String studyIdentifierValue, String studySubjectIdentifierValue, final Integer min_rows, final Integer max_rows, SortParameter sortParameter) {
		List<Object> studySubjectObjects = new ArrayList<Object>();
		StringBuilder sb = new StringBuilder(ROW_NUM_PRE_STRING);
		String baseQuery = "SELECT ss.id as study_subject_id,  ss.payment_method,  ss.reg_data_entry_status,  ssdmgphcs.id,  ssdmgphcs.first_name,  " +
				"ssdmgphcs.last_name, ssdmgphcs.middle_name,  ssdmgphcs.name_prefix,  ssdmgphcs.name_suffix, ssdmgphcs.administrative_gender_code," +
				" ssdmgphcs.ethnic_group_code, ssdmgphcs.birth_date,  ssdmgphcs.marital_status_code,  ssdmgphcs.valid, sv.short_title_text," +
				" stu_ide.type, stu_ide.value,row_number() over(partition by ss.id order by ss.id) rn FROM study_subjects ss, " +
				" stu_sub_demographics ssdmgphcs, study_subject_versions ssubv,  study_site_versions ssitv,  study_versions sv,  studies s, " +
				" identifiers stu_ide, identifiers stu_sub_ide WHERE ss.id = ssubv.spa_id AND ssubv.study_site_ver_id = ssitv.id AND ssitv.stu_version_id = sv.id AND " +
				"sv.study_id = s.id AND s.id =stu_ide.stu_id AND stu_ide.value = (:stuIdenValue) AND ss.id =stu_sub_ide.spa_id AND stu_sub_ide.value = " +
				"(:regIdenValue) AND ssdmgphcs.id = ss.stu_sub_dmgphcs_id";
		sb.append(baseQuery);
		if(sortParameter != null ) {
			if(sortParameter.equals(SortParameter.studyShortTitle)){
				sb.append(" order by sv.short_title_text");
			} else if(sortParameter.equals(SortParameter.studyIdentifierType)){
				sb.append(" order by stu_ide.type");
			} else if(sortParameter.equals(SortParameter.studyIdentifierValue)){
				sb.append(" order by stu_ide.value");
			} else if(sortParameter.equals(SortParameter.subjectLastName)){
				sb.append(" order by ssdmgphcs.last_name");
			} else if(sortParameter.equals(SortParameter.studySubjectIdentifierType)){
				sb.append(" order by stu_sub_ide.type");
			} else if(sortParameter.equals(SortParameter.studySubjectIdentifierValue)){
				sb.append(" order by stu_sub_ide.value");
			}
			
			if(sortParameter.equals(SortParameter.subjectIdentifierType) || sortParameter.equals(SortParameter.subjectIdentifierValue)){
				baseQuery =  "SELECT ss.id as study_subject_id,  ss.payment_method,  ss.reg_data_entry_status,  ssdmgphcs.id,  ssdmgphcs.first_name,  " +
						"ssdmgphcs.last_name, ssdmgphcs.middle_name,  ssdmgphcs.name_prefix,  ssdmgphcs.name_suffix, ssdmgphcs.administrative_gender_code," +
						" ssdmgphcs.ethnic_group_code, ssdmgphcs.birth_date,  ssdmgphcs.marital_status_code,  ssdmgphcs.valid,row_number() over(partition by ss.id order by ss.id) rn FROM study_subjects ss, " +
						" stu_sub_demographics ssdmgphcs, study_subject_versions ssubv,  study_site_versions ssitv,  study_versions sv,  studies s, " +
						" identifiers stu_ide, identifiers stu_sub_ide, identifiers sub_ide WHERE ss.id = ssubv.spa_id AND ssubv.study_site_ver_id = ssitv.id AND ssitv.stu_version_id = sv.id AND " +
						"sv.study_id = s.id AND s.id =stu_ide.stu_id AND ssdmgphcs.id =sub_ide.stu_sub_dmgphcs_id AND stu_ide.value = (:stuIdenValue) AND ss.id =stu_sub_ide.spa_id AND stu_sub_ide.value = " +
						"(:regIdenValue) AND ssdmgphcs.id = ss.stu_sub_dmgphcs_id";
				sb =  new StringBuilder(ROW_NUM_PRE_STRING);
				sb.append(baseQuery);
				if(sortParameter.equals(SortParameter.subjectIdentifierType)){
					sb.append(" order by sub_ide.type");
				} else if(sortParameter.equals(SortParameter.subjectIdentifierValue)){
					sb.append(" order by sub_ide.value");
				}
			}
			String[] stringArray = sb.toString().split("order");
			if(sb.toString().contains("order by") && (stringArray.length > 2) && sortParameter.getSortOrder() == SortOrder.DESCENDING){
				sb.append(" desc ");
			}
		}
		
		sb.append(ROW_NUM_POST_STRING);
		studySubjectObjects =StudySubjectDaoHelper.buildPaginationQuery(sb.toString(), min_rows, max_rows, getHibernateTemplate(), 
				this.objectFactory instanceof ObjectFactoryOracle ? true:false).setParameter("stuIdenValue", studyIdentifierValue).
				setParameter("regIdenValue",studySubjectIdentifierValue).list();
		return buildStudySubjects(studySubjectObjects);
	}

	// Input case 13: studyIdentifier value, =; 2. consent documentId,= (exact
	// search on study identifier and on document Id) ) here studyIdentifier
	// value might be null sometimes
	@SuppressWarnings(value="unchecked")
	public List<StudySubject> retrieveStudySubjectsByNullableStudyIdentifierAndStudySubjectConsentDocumentId(
			String studyIdentifier, String documentId, final Integer min_rows, final Integer max_rows, SortParameter sortParameter) {
		List<Object> studySubjectObjects = new ArrayList<Object>();
		StringBuilder sb = new StringBuilder(ROW_NUM_PRE_STRING);
		if (studyIdentifier == null) {
			String baseQuery = "SELECT ss.id as study_subject_id,ss.payment_method,ss.reg_data_entry_status, ssdmgphcs.id, ssdmgphcs.first_name,ssdmgphcs.last_name, " +
					"ssdmgphcs.middle_name, ssdmgphcs.name_prefix, ssdmgphcs.name_suffix, ssdmgphcs.administrative_gender_code, ssdmgphcs.ethnic_group_code,ssdmgphcs.birth_date, " +
					" ssdmgphcs.marital_status_code,  ssdmgphcs.valid,row_number() over(partition by ss.id order by ss.id) rn FROM study_subjects ss, stu_sub_demographics " +
					"ssdmgphcs, study_subject_versions ssubv, study_subject_consents sscv WHERE ss.id = ssubv.spa_id AND ssubv.id = sscv.study_subject_ver_id AND " +
					"sscv.document_id = (:documentId) AND ssdmgphcs.id = ss.stu_sub_dmgphcs_id";
			
			sb.append(baseQuery);
			if(sortParameter != null ) {
				 if(sortParameter.equals(SortParameter.subjectLastName)){
					sb.append(" order by ssdmgphcs.last_name");
				}
				
				if(sortParameter.equals(SortParameter.studyIdentifierType) || sortParameter.equals(SortParameter.studyIdentifierValue) 
						|| sortParameter.equals(SortParameter.studyShortTitle)){
					baseQuery = "SELECT ss.id as study_subject_id,ss.payment_method,ss.reg_data_entry_status, ssdmgphcs.id, ssdmgphcs.first_name,ssdmgphcs.last_name, " +
							"ssdmgphcs.middle_name, ssdmgphcs.name_prefix, ssdmgphcs.name_suffix, ssdmgphcs.administrative_gender_code, ssdmgphcs.ethnic_group_code,ssdmgphcs.birth_date, " +
							" ssdmgphcs.marital_status_code,  ssdmgphcs.valid,row_number() over(partition by ss.id order by ss.id) rn FROM study_subjects ss, stu_sub_demographics ssdmgphcs, study_subject_versions ssubv, " +
							"study_subject_consents sscv,  study_site_versions ssitv,  study_versions sv,  studies s, identifiers stu_ide WHERE ss.id = ssubv.spa_id " +
							"AND ssubv.id = sscv.study_subject_ver_id AND ssubv.study_site_ver_id = ssitv.id AND ssitv.stu_version_id = sv.id AND " +
							"sv.study_id = s.id AND s.id =stu_ide.stu_id AND sscv.document_id = (:documentId) AND ssdmgphcs.id = ss.stu_sub_dmgphcs_id";
					sb =  new StringBuilder(ROW_NUM_PRE_STRING);
					sb.append(baseQuery);
	
					if(sortParameter.equals(SortParameter.studyShortTitle)){
						sb.append(" order by sv.short_title_text");
					} else if(sortParameter.equals(SortParameter.studyIdentifierType)){
						sb.append(" order by stu_ide.type");
					} else if(sortParameter.equals(SortParameter.studyIdentifierValue)){
						sb.append(" order by stu_ide.value");
					} 
				}
				
				if(sortParameter.equals(SortParameter.subjectIdentifierType) || sortParameter.equals(SortParameter.subjectIdentifierValue)){
					baseQuery = "SELECT ss.id as study_subject_id,ss.payment_method,ss.reg_data_entry_status, ssdmgphcs.id, ssdmgphcs.first_name,ssdmgphcs.last_name, " +
							"ssdmgphcs.middle_name, ssdmgphcs.name_prefix, ssdmgphcs.name_suffix, ssdmgphcs.administrative_gender_code, ssdmgphcs.ethnic_group_code," +
							"ssdmgphcs.birth_date, ssdmgphcs.marital_status_code,  ssdmgphcs.valid,row_number() over(partition by ss.id order by ss.id) rn FROM study_subjects ss," +
							" stu_sub_demographics ssdmgphcs, study_subject_versions ssubv, study_subject_consents sscv,  study_site_versions ssitv,  study_versions sv," +
							" studies s, identifiers sub_ide WHERE ss.id = ssubv.spa_id AND ssubv.id = sscv.study_subject_ver_id AND ssubv.study_site_ver_id = ssitv.id " +
							"AND ssitv.stu_version_id = sv.id AND sv.study_id = s.id AND ssdmgphcs.id =sub_ide.stu_sub_dmgphcs_id AND sscv.document_id = (:documentId) AND " +
							"ssdmgphcs.id = ss.stu_sub_dmgphcs_id";
					sb =  new StringBuilder(ROW_NUM_PRE_STRING);
					sb.append(baseQuery);
					if(sortParameter.equals(SortParameter.subjectIdentifierType)){
						sb.append(" order by sub_ide.type");
					} else if(sortParameter.equals(SortParameter.subjectIdentifierValue)){
						sb.append(" order by sub_ide.value");
					}
				}
				
				if(sortParameter.equals(SortParameter.studySubjectIdentifierType) || sortParameter.equals(SortParameter.studySubjectIdentifierValue)){
					baseQuery = "SELECT ss.id as study_subject_id,ss.payment_method,ss.reg_data_entry_status, ssdmgphcs.id, ssdmgphcs.first_name,ssdmgphcs.last_name, " +
							"ssdmgphcs.middle_name, ssdmgphcs.name_prefix, ssdmgphcs.name_suffix, ssdmgphcs.administrative_gender_code, ssdmgphcs.ethnic_group_code," +
							"ssdmgphcs.birth_date, ssdmgphcs.marital_status_code, ssdmgphcs.valid,row_number() over(partition by ss.id order by ss.id) rn FROM study_subjects ss," +
							" stu_sub_demographics ssdmgphcs, study_subject_versions ssubv, study_subject_consents sscv,  study_site_versions ssitv,  study_versions sv, " +
							"studies s, identifiers stu_sub_ide WHERE ss.id = ssubv.spa_id AND ssubv.id = sscv.study_subject_ver_id AND ssubv.study_site_ver_id = ssitv.id " +
							"AND ssitv.stu_version_id = sv.id AND sv.study_id = s.id AND ss.id =stu_sub_ide.spa_id AND sscv.document_id = (:documentId) AND " +
							"ssdmgphcs.id = ss.stu_sub_dmgphcs_id";
					sb =  new StringBuilder(ROW_NUM_PRE_STRING);
					sb.append(baseQuery);
					if(sortParameter.equals(SortParameter.studySubjectIdentifierType)){
						sb.append(" order by stu_sub_ide.type");
					} else if(sortParameter.equals(SortParameter.studySubjectIdentifierValue)){
						sb.append(" order by stu_sub_ide.value");
					}
				}
				String[] stringArray = sb.toString().split("order");
				if(sb.toString().contains("order by") && (stringArray.length > 2) && sortParameter.getSortOrder() == SortOrder.DESCENDING){
					sb.append(" desc ");
				}
			}
			
		 sb.append(ROW_NUM_POST_STRING);
	     studySubjectObjects =StudySubjectDaoHelper.buildPaginationQuery(sb.toString(), min_rows, max_rows, getHibernateTemplate(), 
	    		 this.objectFactory instanceof ObjectFactoryOracle ? true:false).setParameter("documentId", documentId).list();
	       
		} else {
			String baseQuery = "SELECT ss.id as study_subject_id,  ss.payment_method,  ss.reg_data_entry_status,  ssdmgphcs.id,  ssdmgphcs.first_name,  " +
					"ssdmgphcs.last_name, ssdmgphcs.middle_name,  ssdmgphcs.name_prefix,  ssdmgphcs.name_suffix, ssdmgphcs.administrative_gender_code," +
					" ssdmgphcs.ethnic_group_code, ssdmgphcs.birth_date, ssdmgphcs.marital_status_code, ssdmgphcs.valid,row_number() over(partition by ss.id order by ss.id) " +
					"rn FROM study_subjects ss, stu_sub_demographics ssdmgphcs, study_subject_versions ssubv,  study_site_versions ssitv,  study_versions sv,  studies s, " +
					" identifiers stu_ide, study_subject_consents sscv WHERE ss.id = ssubv.spa_id AND ssubv.study_site_ver_id = ssitv.id AND " +
					"ssitv.stu_version_id = sv.id AND sv.study_id = s.id AND   ssubv.id = sscv.study_subject_ver_id AND sscv.document_id = (:documentId) " +
					"  AND s.id =stu_ide.stu_id AND stu_ide.value = (:value) AND ssdmgphcs.id = ss.stu_sub_dmgphcs_id";
			sb.append(baseQuery);
			if(sortParameter != null ) {
				 if(sortParameter.equals(SortParameter.subjectLastName)){
					sb.append(" order by ssdmgphcs.last_name");
				} else if(sortParameter.equals(SortParameter.studyShortTitle)){
					sb.append(" order by sv.short_title_text");
				} else if(sortParameter.equals(SortParameter.studyIdentifierType)){
					sb.append(" order by stu_ide.type");
				} else if(sortParameter.equals(SortParameter.studyIdentifierValue)){
					sb.append(" order by stu_ide.value");
				} 
				 
				 if(sortParameter.equals(SortParameter.subjectIdentifierType) || sortParameter.equals(SortParameter.subjectIdentifierValue)){
						baseQuery = "SELECT ss.id as study_subject_id,  ss.payment_method,  ss.reg_data_entry_status,  ssdmgphcs.id,  ssdmgphcs.first_name,  " +
								"ssdmgphcs.last_name, ssdmgphcs.middle_name,  ssdmgphcs.name_prefix,  ssdmgphcs.name_suffix, ssdmgphcs.administrative_gender_code," +
								" ssdmgphcs.ethnic_group_code, ssdmgphcs.birth_date,  ssdmgphcs.marital_status_code,  ssdmgphcs.valid,row_number() over(partition by ss.id " +
								"order by ss.id) rn FROM study_subjects ss, stu_sub_demographics ssdmgphcs, study_subject_versions ssubv,  study_site_versions ssitv, " +
								"study_versions sv,  studies s, identifiers stu_ide, identifiers sub_ide, study_subject_consents sscv WHERE ss.id = ssubv.spa_id AND " +
								"ssubv.study_site_ver_id = ssitv.id AND ssitv.stu_version_id = sv.id AND sv.study_id = s.id AND   ssubv.id = sscv.study_subject_ver_id AND " +
								"sscv.document_id = (:documentId) AND s.id =stu_ide.stu_id AND ssdmgphcs.id =sub_ide.stu_sub_dmgphcs_id AND stu_ide.value = (:value) AND " +
								"ssdmgphcs.id = ss.stu_sub_dmgphcs_id";
						sb =  new StringBuilder(ROW_NUM_PRE_STRING);
						sb.append(baseQuery);
						if(sortParameter.equals(SortParameter.subjectIdentifierType)){
							sb.append(" order by sub_ide.type");
						} else if(sortParameter.equals(SortParameter.subjectIdentifierValue)){
							sb.append(" order by sub_ide.value");
						}
					}
					
					if(sortParameter.equals(SortParameter.studySubjectIdentifierType) || sortParameter.equals(SortParameter.studySubjectIdentifierValue)){
						baseQuery = "SELECT ss.id as study_subject_id,  ss.payment_method,  ss.reg_data_entry_status,  ssdmgphcs.id,  ssdmgphcs.first_name,  " +
								"ssdmgphcs.last_name, ssdmgphcs.middle_name,  ssdmgphcs.name_prefix,  ssdmgphcs.name_suffix, ssdmgphcs.administrative_gender_code," +
								" ssdmgphcs.ethnic_group_code, ssdmgphcs.birth_date,  ssdmgphcs.marital_status_code,  ssdmgphcs.valid,row_number() over(partition by ss.id " +
								"order by ss.id) rn FROM study_subjects ss, stu_sub_demographics ssdmgphcs, study_subject_versions ssubv,  study_site_versions ssitv,  " +
								"study_versions sv,  studies s, identifiers stu_ide, identifiers stu_sub_ide, study_subject_consents sscv WHERE ss.id = ssubv.spa_id AND " +
								"ssubv.study_site_ver_id = ssitv.id AND ssitv.stu_version_id = sv.id AND sv.study_id = s.id AND   ssubv.id = sscv.study_subject_ver_id AND " +
								"sscv.document_id = (:documentId) AND s.id =stu_ide.stu_id AND ss.id =stu_sub_ide.spa_id AND stu_ide.value = (:value) AND " +
								"ssdmgphcs.id = ss.stu_sub_dmgphcs_id";
						sb =  new StringBuilder(ROW_NUM_PRE_STRING);
						sb.append(baseQuery);
						if(sortParameter.equals(SortParameter.studySubjectIdentifierType)){
							sb.append(" order by stu_sub_ide.type");
						} else if(sortParameter.equals(SortParameter.studySubjectIdentifierValue)){
							sb.append(" order by stu_sub_ide.value");
						}
					}
					String[] stringArray = sb.toString().split("order");
					if(sb.toString().contains("order by") && (stringArray.length > 2) && sortParameter.getSortOrder() == SortOrder.DESCENDING){
						sb.append(" desc ");
					}
			}
			
			sb.append(ROW_NUM_POST_STRING);
	        studySubjectObjects =StudySubjectDaoHelper.buildPaginationQuery(sb.toString(), min_rows, max_rows, getHibernateTemplate(), 
	        		this.objectFactory instanceof ObjectFactoryOracle ? true:false).setParameter("value", studyIdentifier).
	        		setParameter("documentId", documentId).list();
		}
		return buildStudySubjects(studySubjectObjects);
	}

	// Input case 14: 1.studyIdentifier value, = ; 2. RegistryStatus status, =
	// here studyIdentifier value might be null sometimes
	@SuppressWarnings(value="unchecked")
	public List<StudySubject> retrieveStudySubjectsByNullableStudyIdentifierValueAndRegistryStatusCode(
			String studyIdentifier, String registryStatusCode, final Integer min_rows, final Integer max_rows, SortParameter sortParameter) {
		List<Object> studySubjectObjects = new ArrayList<Object>();
		StringBuilder sb = new StringBuilder(ROW_NUM_PRE_STRING);
		if (studyIdentifier == null) {
			String baseQuery = "SELECT ss.id as study_subject_id,  ss.payment_method,  ss.reg_data_entry_status,  ssdmgphcs.id,  ssdmgphcs.first_name," +
					" ssdmgphcs.last_name, ssdmgphcs.middle_name,  ssdmgphcs.name_prefix,  ssdmgphcs.name_suffix, ssdmgphcs.administrative_gender_code," +
					" ssdmgphcs.ethnic_group_code, ssdmgphcs.birth_date,  ssdmgphcs.marital_status_code,  ssdmgphcs.valid,row_number() over(partition by ss.id order by " +
					"ss.id) rn  FROM study_subjects ss, stu_sub_demographics ssdmgphcs, study_subject_versions ssubv, stu_sub_reg_statuses ssrs, permissible_reg_stats prs," +
					" registry_statuses rs WHERE ss.id = ssubv.spa_id AND ssrs.stu_sub_id = ss.id AND ssrs.per_reg_st_id = prs.id AND prs.registry_st_id = rs.id AND " +
					"ssdmgphcs.id = ss.stu_sub_dmgphcs_id AND rs.code =(:code)";
			
			sb.append(baseQuery);
			if(sortParameter != null ) {
				 if(sortParameter.equals(SortParameter.subjectLastName)){
					sb.append(" order by ssdmgphcs.last_name");
				}
				
				if(sortParameter.equals(SortParameter.studyIdentifierType) || sortParameter.equals(SortParameter.studyIdentifierValue) 
						|| sortParameter.equals(SortParameter.studyShortTitle)){
					baseQuery = "SELECT ss.id as study_subject_id,  ss.payment_method,  ss.reg_data_entry_status,  ssdmgphcs.id,  ssdmgphcs.first_name," +
							" ssdmgphcs.last_name, ssdmgphcs.middle_name,  ssdmgphcs.name_prefix, ssdmgphcs.name_suffix, ssdmgphcs.administrative_gender_code," +
							" ssdmgphcs.ethnic_group_code, ssdmgphcs.birth_date, ssdmgphcs.marital_status_code, ssdmgphcs.valid,row_number() over(partition by ss.id " +
							"order by ss.id) rn FROM study_subjects ss, stu_sub_demographics ssdmgphcs, study_subject_versions ssubv, study_site_versions ssitv, " +
							"study_versions sv,  studies s, identifiers stu_ide, stu_sub_reg_statuses ssrs, permissible_reg_stats prs, registry_statuses rs WHERE " +
							"ss.id = ssubv.spa_id AND ssubv.study_site_ver_id = ssitv.id AND ssitv.stu_version_id = sv.id AND sv.study_id = s.id AND ssrs.stu_sub_id = ss.id " +
							"AND ssrs.per_reg_st_id = prs.id AND prs.registry_st_id = rs.id AND s.id =stu_ide.stu_id AND ssdmgphcs.id = ss.stu_sub_dmgphcs_id AND rs.code =(:code)";
					sb =  new StringBuilder(ROW_NUM_PRE_STRING);
					sb.append(baseQuery);
	
					if(sortParameter.equals(SortParameter.studyShortTitle)){
						sb.append(" order by sv.short_title_text");
					} else if(sortParameter.equals(SortParameter.studyIdentifierType)){
						sb.append(" order by stu_ide.type");
					} else if(sortParameter.equals(SortParameter.studyIdentifierValue)){
						sb.append(" order by stu_ide.value");
					} 
				}
				
				if(sortParameter.equals(SortParameter.subjectIdentifierType) || sortParameter.equals(SortParameter.subjectIdentifierValue)){
					baseQuery = "SELECT ss.id as study_subject_id,  ss.payment_method,  ss.reg_data_entry_status,  ssdmgphcs.id,  ssdmgphcs.first_name," +
							" ssdmgphcs.last_name, ssdmgphcs.middle_name,  ssdmgphcs.name_prefix,  ssdmgphcs.name_suffix, ssdmgphcs.administrative_gender_code," +
							" ssdmgphcs.ethnic_group_code, ssdmgphcs.birth_date,  ssdmgphcs.marital_status_code,  ssdmgphcs.valid,row_number() over(partition by ss.id order " +
							"by ss.id) rn FROM study_subjects ss, stu_sub_demographics ssdmgphcs, identifiers sub_ide, study_subject_versions ssubv, stu_sub_reg_statuses ssrs," +
							" permissible_reg_stats prs, registry_statuses rs WHERE ss.id = ssubv.spa_id AND ssrs.stu_sub_id = ss.id AND ssrs.per_reg_st_id = prs.id AND " +
							"prs.registry_st_id = rs.id AND ssdmgphcs.id = ss.stu_sub_dmgphcs_id AND ssdmgphcs.id =sub_ide.stu_sub_dmgphcs_id AND rs.code =(:code)";
					sb =  new StringBuilder(ROW_NUM_PRE_STRING);
					sb.append(baseQuery);
					if(sortParameter.equals(SortParameter.subjectIdentifierType)){
						sb.append(" order by sub_ide.type");
					} else if(sortParameter.equals(SortParameter.subjectIdentifierValue)){
						sb.append(" order by sub_ide.value");
					}
				}
				
				if(sortParameter.equals(SortParameter.studySubjectIdentifierType) || sortParameter.equals(SortParameter.studySubjectIdentifierValue)){
					baseQuery = "SELECT ss.id as study_subject_id,  ss.payment_method,  ss.reg_data_entry_status,  ssdmgphcs.id,  ssdmgphcs.first_name," +
							" ssdmgphcs.last_name, ssdmgphcs.middle_name,  ssdmgphcs.name_prefix,  ssdmgphcs.name_suffix, ssdmgphcs.administrative_gender_code," +
							" ssdmgphcs.ethnic_group_code, ssdmgphcs.birth_date,  ssdmgphcs.marital_status_code, ssdmgphcs.valid,row_number() over(partition by ss.id order " +
							"by ss.id) rn FROM study_subjects ss, stu_sub_demographics ssdmgphcs, identifiers stu_sub_ide, study_subject_versions ssubv, " +
							"stu_sub_reg_statuses ssrs, permissible_reg_stats prs, registry_statuses rs WHERE ss.id = ssubv.spa_id AND ssrs.stu_sub_id = ss.id AND" +
							" ssrs.per_reg_st_id = prs.id AND prs.registry_st_id = rs.id AND ssdmgphcs.id = ss.stu_sub_dmgphcs_id AND ss.id =stu_sub_ide.spa_id AND " +
							"rs.code =(:code)";
					sb =  new StringBuilder(ROW_NUM_PRE_STRING);
					sb.append(baseQuery);
					if(sortParameter.equals(SortParameter.studySubjectIdentifierType)){
						sb.append(" order by stu_sub_ide.type");
					} else if(sortParameter.equals(SortParameter.studySubjectIdentifierValue)){
						sb.append(" order by stu_sub_ide.value");
					}
				}
				String[] stringArray = sb.toString().split("order");
				if(sb.toString().contains("order by") && (stringArray.length > 2) && sortParameter.getSortOrder() == SortOrder.DESCENDING){
					sb.append(" desc ");
				}
			}
			sb.append(ROW_NUM_POST_STRING);
			studySubjectObjects =StudySubjectDaoHelper.buildPaginationQuery(sb.toString(), min_rows, max_rows, getHibernateTemplate(), 
					this.objectFactory instanceof ObjectFactoryOracle ? true:false).setParameter("code", registryStatusCode).
					setParameter("code",registryStatusCode).list();
		} else {
			
			return retrieveStudySubjectsByStudyIdentifierValueAndRegistryStatusCode(studyIdentifier,registryStatusCode, min_rows,  max_rows, sortParameter);

		}
		return buildStudySubjects(studySubjectObjects);
	}
	
	private List<StudySubject> buildStudySubjects(List<Object> studySubjectObjects){
		List<StudySubject> studySubjects = new ArrayList<StudySubject>();
        studySubjects = objectFactory.createStudySubjectsFromResultSet(studySubjectObjects);
        List<StudySubjectDemographics> studySubjectDemographics = StudySubjectDaoHelper.getStudySubjectDemographics(studySubjects);
        loadDemographicsContactMechanisms(studySubjectDemographics);
        loadDemographicsContactMechanismsUses(studySubjectDemographics);
		loadDemographicsIdentifiers(studySubjectDemographics);
		loadDemographicsIdentifierOrganizationIdentifiers(studySubjectDemographics);
		loadDemographicsAddresses(studySubjectDemographics);
		loadDemographicsAddressUses(studySubjectDemographics);
		loadDemographicsRaceCodes(studySubjectDemographics);
		
		loadStudySubjectStudyVersions(studySubjects);
		
		List<StudySubjectStudyVersion> studySubjectStudyVersions = StudySubjectDaoHelper.getStudySubjectStudyVersions(studySubjects);
		
		loadStudySubjectConsentVersions(studySubjects);
		loadStudyConsents(StudySubjectDaoHelper.getStudySubjectStudyVersions(studySubjects));
		loadStudySubjectConsentQuestionAnswers(studySubjectStudyVersions);
		loadStudyConsentQuestions(StudySubjectDaoHelper.getStudyConsentQuestions(studySubjectStudyVersions));
		loadStudyVersions(StudySubjectDaoHelper.getStudySubjectStudyVersions(studySubjects));
		loadStudyIdentifiers(StudySubjectDaoHelper.getStudySubjectStudyVersions(studySubjects));
		loadStudyIdentifierOrganizationIdentifiers(studySubjects);
		loadStudySiteHealthcareSiteIdentifiers(studySubjectStudyVersions);
		loadStudySubjectIdentifiers(studySubjects);
		loadStudySubjectIdentifierOrganizationIdentifiers(studySubjects);
		
		loadStudySubjectRegistryStatus(studySubjects);
		loadStudySubjectRegistryReasons(studySubjects);
		loadStudySubjectPermissibleRegistryStatusReason(studySubjects);
		
		
        return studySubjects;
	}
	
	
	public void loadStudySubjectStudyVersions(List<StudySubject> studySubjects){
		List<Integer> ids = new ArrayList<Integer>();
		Map<Integer,StudySubject> studySubjectsMap =  new HashMap<Integer,StudySubject>();
		studySubjectsMap = StudySubjectDaoHelper.constructStudySubjectsHashMap(studySubjects);
		ids.addAll(studySubjectsMap.keySet());
		if(!ids.isEmpty()) {
			String baseQuery = "select sssv.spa_id, sssv.id, sssv.STUDY_SITE_VER_ID from STUDY_SUBJECT_VERSIONS sssv where sssv.spa_id in ";
			Query query = StudySubjectDaoHelper.handleOracle1000LimitForInStatement(ids, baseQuery, getHibernateTemplate());
	        List<Object> studySubjectVersionsObjects =query.list();
	        objectFactory.buildStudySubjectVersions(studySubjectVersionsObjects,studySubjectsMap);
		}
	}
	
	public void loadStudySiteHealthcareSiteIdentifiers(List<StudySubjectStudyVersion> studySubjectStudyVersions){
		List<Integer> ids = new ArrayList<Integer>();
		Map<Integer,StudySiteStudyVersion> studySiteVersionsMap =  new HashMap<Integer,StudySiteStudyVersion>();
		studySiteVersionsMap = StudySubjectDaoHelper.constructStudySiteVersionsHashMap(studySubjectStudyVersions);
		ids.addAll(studySiteVersionsMap.keySet());
		if(!ids.isEmpty()) {
			String baseQuery = "SELECT DISTINCT sssv.id as sssv_id, so.id as so_id, so.hcs_id as so_org_id, id.type,id.value,id.primary_indicator from " +
					"IDENTIFIERS id inner join organizations orgs on id.org_id = orgs.id inner join study_organizations so on " +
					"so.hcs_id = orgs.id inner join study_site_versions sssv on sssv.sto_id = so.id where id.dtype like 'OAI' and sssv.id in ";
			Query query = StudySubjectDaoHelper.handleOracle1000LimitForInStatement(ids, baseQuery, getHibernateTemplate());
	        List<Object> studySiteHealthcareSiteIdentifierObjects =query.list();
	        objectFactory.buildStudySiteHealthcareSiteIdentifiers(studySiteHealthcareSiteIdentifierObjects,studySiteVersionsMap);
		}
	}
	
	public void loadStudyVersions(List<StudySubjectStudyVersion> studySubjectStudyVersions){
		List<Integer> ids = new ArrayList<Integer>();
		Map<Integer,StudySiteStudyVersion> studySiteStudyVersionsMap =  new HashMap<Integer,StudySiteStudyVersion>();
		studySiteStudyVersionsMap = StudySubjectDaoHelper.constructStudySiteVersionsHashMap(studySubjectStudyVersions);
		ids.addAll(studySiteStudyVersionsMap.keySet());
		if(!ids.isEmpty()) {
			String baseQuery = "select DISTINCT sssv.id as sssv_id, sv.id, sv.short_title_text, sv.long_title_text, sv.description_text,sv.study_id from STUDY_SITE_VERSIONS sssv " +
					"inner join STUDY_VERSIONS sv on sssv.stu_version_id = sv.id where sssv.id in ";
			Query query = StudySubjectDaoHelper.handleOracle1000LimitForInStatement(ids, baseQuery, getHibernateTemplate());
	        List<Object> studyVersionObjects =query.list();
	        objectFactory.buildStudyVersions(studyVersionObjects,studySiteStudyVersionsMap);
		}
	}
	
	public void loadStudyIdentifiers(List<StudySubjectStudyVersion> studySubjectStudyVersions){
		List<Integer> ids = new ArrayList<Integer>();
		Map<Integer,Study> studiesMap =  new HashMap<Integer,Study>();
		studiesMap = StudySubjectDaoHelper.constructStudiesHashMap(studySubjectStudyVersions);
		ids.addAll(studiesMap.keySet());
		if(!ids.isEmpty()) {
			String baseQuery = "SELECT id.stu_id, id.dtype, id.type,id.system_name,id.primary_indicator,id.value,id.hcs_id from IDENTIFIERS id where id.stu_id in ";
			Query query = StudySubjectDaoHelper.handleOracle1000LimitForInStatement(ids, baseQuery, getHibernateTemplate());
	        List<Object> identifierObjects =query.list();
	        objectFactory.buildStudyIdentifiers(identifierObjects,studiesMap);
		}
	}
	
	
	public void loadStudySubjectConsentVersions(List<StudySubject> studySubjects){
		List<Integer> ids = new ArrayList<Integer>();
		Map<Integer,StudySubjectStudyVersion> studySubjectStudyVersionsMap =  new HashMap<Integer,StudySubjectStudyVersion>();
		studySubjectStudyVersionsMap = StudySubjectDaoHelper.constructStudySubjectStudyVersionsHashMap(studySubjects);
		ids.addAll(studySubjectStudyVersionsMap.keySet());
		if(!ids.isEmpty()) {
			String baseQuery = "select ssc.study_subject_ver_id, ssc.id, ssc.informed_consent_signed_tstamp, ssc.consent_id, ssc.consenting_method, ssc.consent_presenter, ssc.consent_delivery_date,ssc.document_id, ssc.consent_declined_date from STUDY_SUBJECT_CONSENTS ssc where ssc.study_subject_ver_id in ";
			Query query = StudySubjectDaoHelper.handleOracle1000LimitForInStatement(ids, baseQuery, getHibernateTemplate());
	        List<Object> studySubjectConsentObjects =query.list();
	        objectFactory.buildStudySubjectConsentVersions(studySubjectConsentObjects,studySubjectStudyVersionsMap);
		}
	}
	
	public void loadStudyConsents(List<StudySubjectStudyVersion> studySubjectStudyVersions){
		List<Integer> ids = new ArrayList<Integer>();
		Map<Integer,Consent> consentsMap =  new HashMap<Integer,Consent>();
		consentsMap = StudySubjectDaoHelper.constructStudyConsentsHashMap(studySubjectStudyVersions);
		ids.addAll(consentsMap.keySet());
		if(!ids.isEmpty()) {
			String baseQuery = "select c.id, c.name, c.version_id, c.mandatory_indicator, c.description_text from CONSENTS c where c.id in ";
			Query query = StudySubjectDaoHelper.handleOracle1000LimitForInStatement(ids, baseQuery, getHibernateTemplate());
	        List<Object> consentObjects =query.list();
	        objectFactory.buildStudyConsents(consentObjects,consentsMap);
		}
	}
	
	public void loadStudySubjectConsentQuestionAnswers(List<StudySubjectStudyVersion> studySubjectStudyVersions){
		List<Integer> ids = new ArrayList<Integer>();
		Map<Integer,StudySubjectConsentVersion> studySubjectStudyVersionsMap =  new HashMap<Integer,StudySubjectConsentVersion>();
		studySubjectStudyVersionsMap = StudySubjectDaoHelper.constructStudySubjectConsentVersionsHashMap(studySubjectStudyVersions);
		ids.addAll(studySubjectStudyVersionsMap.keySet());
		if(!ids.isEmpty()) {
			String baseQuery = "select scqa.stu_sub_con_ver_id, scqa.id,scqa.agreement_indicator,scqa.con_que_id from sub_con_que_ans scqa where scqa.stu_sub_con_ver_id in ";
			Query query = StudySubjectDaoHelper.handleOracle1000LimitForInStatement(ids, baseQuery, getHibernateTemplate());
	        List<Object> consentQuestionAnswerObjects =query.list();
	        objectFactory.buildStudyConsentQuestionAnswers(consentQuestionAnswerObjects,studySubjectStudyVersionsMap);
		}
	}
	
	public void loadStudyConsentQuestions(List<ConsentQuestion> consentQuestions){
		List<Integer> ids = new ArrayList<Integer>();
		Map<Integer,ConsentQuestion> consentQuestionsHashMap =  new HashMap<Integer,ConsentQuestion>();
		consentQuestionsHashMap = StudySubjectDaoHelper.constructStudyConsentQuestionsHashMap(consentQuestions);
		ids.addAll(consentQuestionsHashMap.keySet());
		if(!ids.isEmpty()) {
			String baseQuery = "select cq.id, cq.code from consent_questions cq where cq.id in ";
			Query query = StudySubjectDaoHelper.handleOracle1000LimitForInStatement(ids, baseQuery, getHibernateTemplate());
	        List<Object> consentQuestionObjects =query.list();
	        objectFactory.buildStudyConsentQuestions(consentQuestionObjects,consentQuestionsHashMap);
		}
	}
	
	public void loadStudySubjectIdentifiers(List<StudySubject> studySubjects){
		List<Integer> ids = new ArrayList<Integer>();
		Map<Integer,StudySubject> studySubjectsMap =  new HashMap<Integer,StudySubject>();
		studySubjectsMap = StudySubjectDaoHelper.constructStudySubjectsHashMap(studySubjects);
		ids.addAll(studySubjectsMap.keySet());
		if(!ids.isEmpty()) {
			String baseQuery = "SELECT id.spa_id, id.dtype, id.type,id.system_name,id.primary_indicator,id.value,id.hcs_id from IDENTIFIERS id where id.spa_id in ";
			Query query = StudySubjectDaoHelper.handleOracle1000LimitForInStatement(ids, baseQuery, getHibernateTemplate());
	        List<Object> identifierObjects =query.list();
	        objectFactory.buildStudySubjectIdentifiers(identifierObjects,studySubjectsMap);
		}
	}
	
	public void loadDemographicsContactMechanisms(List<StudySubjectDemographics> studySubjectDemographics){
		List<Integer> ids = new ArrayList<Integer>();
		Map<Integer,StudySubjectDemographics> demographicsMap =  new HashMap<Integer,StudySubjectDemographics>();
		demographicsMap = StudySubjectDaoHelper.constructDempgraphicsHashMap(studySubjectDemographics);
		ids.addAll(demographicsMap.keySet());
		if(!ids.isEmpty()) {
			String baseQuery = "select cm.STU_SUB_DMGPHCS_ID, cm.type, cm.value, cm.id from CONTACT_MECHANISMS cm where cm.STU_SUB_DMGPHCS_ID in ";
			Query query = StudySubjectDaoHelper.handleOracle1000LimitForInStatement(ids, baseQuery, getHibernateTemplate());
	        List<Object> contactMechanismObjects =query.list();
	        objectFactory.buildContactMechanisms(contactMechanismObjects,demographicsMap);
		}
	}
	
	public void loadDemographicsContactMechanismsUses(List<StudySubjectDemographics> studySubjectDemographics){
		List<Integer> ids = new ArrayList<Integer>();
		Map<Integer,ContactMechanism> contactMechanismsMap =  new HashMap<Integer,ContactMechanism>();
		contactMechanismsMap = StudySubjectDaoHelper.constructContactMechanismsHashMap(studySubjectDemographics);
		ids.addAll(contactMechanismsMap.keySet());
		if(!ids.isEmpty()) {
			String baseQuery = "select cm.cntct_id, cm.use from contact_use_assocns cm where cm.cntct_id in ";
			Query query = StudySubjectDaoHelper.handleOracle1000LimitForInStatement(ids, baseQuery, getHibernateTemplate());
	        List<Object> contactMechanismObjects =query.list();
	        objectFactory.buildContactMechanismUseAssociations(contactMechanismObjects,contactMechanismsMap);
		}
	}
	
	public void loadDemographicsAddresses(List<StudySubjectDemographics> studySubjectDemographics){
		List<Integer> ids = new ArrayList<Integer>();
		Map<Integer,StudySubjectDemographics> demographicsMap =  new HashMap<Integer,StudySubjectDemographics>();
		demographicsMap = StudySubjectDaoHelper.constructDempgraphicsHashMap(studySubjectDemographics);
		ids.addAll(demographicsMap.keySet());
		if(!ids.isEmpty()) {
			String baseQuery = "select ad.STU_SUB_DEMOGRAPHICS_ID, ad.street_address, ad.city, ad.state_code, ad.country_code,ad.postal_code, ad.id from ADDRESSES ad " +
					"where ad.STU_SUB_DEMOGRAPHICS_ID in ";
			Query query = StudySubjectDaoHelper.handleOracle1000LimitForInStatement(ids, baseQuery, getHibernateTemplate());
	        List<Object> addressObjects =query.list();
	        objectFactory.buildAddresses(addressObjects,demographicsMap);
		}
	}
	
	public void loadDemographicsAddressUses(List<StudySubjectDemographics> studySubjectDemographics){
		List<Integer> ids = new ArrayList<Integer>();
		Map<Integer,Address> addressMap =  new HashMap<Integer,Address>();
		addressMap = StudySubjectDaoHelper.constructAddressesHashMap(studySubjectDemographics);
		ids.addAll(addressMap.keySet());
		if(!ids.isEmpty()) {
			String baseQuery = "select aduassocns.add_ID, aduassocns.use from address_use_assocns aduassocns where aduassocns.add_ID in ";
			Query query = StudySubjectDaoHelper.handleOracle1000LimitForInStatement(ids, baseQuery, getHibernateTemplate());
	        List<Object> addressObjects =query.list();
	        objectFactory.buildAddressUseAssociations(addressObjects,addressMap);
		}
	}
	
	public void loadDemographicsIdentifiers(List<StudySubjectDemographics> studySubjectDemographics){
		List<Integer> ids = new ArrayList<Integer>();
		Map<Integer,StudySubjectDemographics> demographicsMap =  new HashMap<Integer,StudySubjectDemographics>();
		demographicsMap = StudySubjectDaoHelper.constructDempgraphicsHashMap(studySubjectDemographics);
		ids.addAll(demographicsMap.keySet());
		if(!ids.isEmpty()) {
			String baseQuery = "SELECT id.STU_SUB_DMGPHCS_ID, id.dtype, id.type,id.system_name,id.primary_indicator,id.value,id.hcs_id from IDENTIFIERS id where id.STU_SUB_DMGPHCS_ID in ";
			Query query = StudySubjectDaoHelper.handleOracle1000LimitForInStatement(ids, baseQuery, getHibernateTemplate());
	        List<Object> identifierObjects =query.list();
	        objectFactory.buildDemographicsIdentifiers(identifierObjects,demographicsMap);
		}
	}
	
	public void loadDemographicsRaceCodes(List<StudySubjectDemographics> studySubjectDemographics){
		List<Integer> ids = new ArrayList<Integer>();
		Map<Integer,StudySubjectDemographics> demographicsMap =  new HashMap<Integer,StudySubjectDemographics>();
		demographicsMap = StudySubjectDaoHelper.constructDempgraphicsHashMap(studySubjectDemographics);
		ids.addAll(demographicsMap.keySet());
		if(!ids.isEmpty()) {
			String baseQuery = "select rc.stu_sub_dmgphcs_id, rc.race_code from race_code_assocn rc where rc.stu_sub_dmgphcs_id in ";
			Query query = StudySubjectDaoHelper.handleOracle1000LimitForInStatement(ids, baseQuery, getHibernateTemplate());
	        List<Object> identifierObjects =query.list();
	        objectFactory.buildRaceCodes(identifierObjects,demographicsMap);
		}
	}
	
	public void loadDemographicsIdentifierOrganizationIdentifiers(List<StudySubjectDemographics> studySubjectDemographics){
		List<Integer> ids = new ArrayList<Integer>();
		Map<Integer,HealthcareSite> healthcareSitesMap =  new HashMap<Integer,HealthcareSite>();
		healthcareSitesMap = StudySubjectDaoHelper.constructDemographicsIdentifierHealthcareSiteHashMap(studySubjectDemographics);
		ids.addAll(healthcareSitesMap.keySet());
		if(!ids.isEmpty()) {
			String baseQuery = "SELECT id.ORG_ID, id.dtype, id.type,id.primary_indicator,id.value from IDENTIFIERS id where id.ORG_ID in ";
			Query query = StudySubjectDaoHelper.handleOracle1000LimitForInStatement(ids, baseQuery, getHibernateTemplate());
	        List<Object> identifierObjects =query.list();
	        objectFactory.buildOrganizationIdentifiers(identifierObjects,healthcareSitesMap);
		}
	}
	
	public void loadStudySubjectIdentifierOrganizationIdentifiers(List<StudySubject> studySubjects){
		List<Integer> ids = new ArrayList<Integer>();
		Map<Integer,HealthcareSite> healthcareSitesMap =  new HashMap<Integer,HealthcareSite>();
		healthcareSitesMap = StudySubjectDaoHelper.constructStudySubjectIdentifierHealthcareSiteHashMap(studySubjects);
		ids.addAll(healthcareSitesMap.keySet());
		if(!ids.isEmpty()) {
			String baseQuery = "SELECT id.ORG_ID, id.dtype, id.type,id.primary_indicator,id.value from IDENTIFIERS id where id.ORG_ID in ";
			Query query = StudySubjectDaoHelper.handleOracle1000LimitForInStatement(ids, baseQuery, getHibernateTemplate());
	        List<Object> identifierObjects =query.list();
	        objectFactory.buildOrganizationIdentifiers(identifierObjects,healthcareSitesMap);
		}
	}
	
	public void loadStudySubjectPermissibleRegistryStatusReason(List<StudySubject> studySubjects){
		List<Integer> ids = new ArrayList<Integer>();
		Map<Integer,PermissibleStudySubjectRegistryStatus> permissibleRegistryStatusMap =  new HashMap<Integer,PermissibleStudySubjectRegistryStatus>();
		permissibleRegistryStatusMap = StudySubjectDaoHelper.constructPermissibleRegistryStatusHashMap(studySubjects);
		ids.addAll(permissibleRegistryStatusMap.keySet());
		if(!ids.isEmpty()) {
			String baseQuery = "select distinct prs.id,rs.id, rs.code from PERMISSIBLE_REG_STATS prs inner join REGISTRY_STATUSES rs on" +
					" rs.id = prs.registry_st_id where prs.id in ";
			Query query = StudySubjectDaoHelper.handleOracle1000LimitForInStatement(ids, baseQuery, getHibernateTemplate());
	        List<Object> permissbleStatusObjects =query.list();
	        objectFactory.buildPermissibleRegistryStatusObjects(permissbleStatusObjects,permissibleRegistryStatusMap);
		}
	}
	
	public void loadStudySubjectRegistryStatus(List<StudySubject> studySubjects){
		List<Integer> ids = new ArrayList<Integer>();
		Map<Integer,StudySubject> studySubjectsMap =  new HashMap<Integer,StudySubject>();
		studySubjectsMap = StudySubjectDaoHelper.constructStudySubjectsHashMap(studySubjects);
		ids.addAll(studySubjectsMap.keySet());
		if(!ids.isEmpty()) {
			String baseQuery = "select ssrs.stu_sub_id,  ssrs.id, ssrs.comment_text, ssrs.effective_date, ssrs.per_reg_st_id from STU_SUB_REG_STATUSES ssrs " +
					"where ssrs.stu_sub_id in ";
			Query query = StudySubjectDaoHelper.handleOracle1000LimitForInStatement(ids, baseQuery, getHibernateTemplate());
	        List<Object> registryStatusObjects =query.list();
	        objectFactory.buildRegistryStatusObjects(registryStatusObjects,studySubjectsMap);
		}
	}
	
	
	public void loadStudySubjectRegistryReasons(List<StudySubject> studySubjects){
		List<Integer> ids = new ArrayList<Integer>();
		Map<Integer,StudySubjectRegistryStatus> studySubjectRegistryStatusMap =  new HashMap<Integer,StudySubjectRegistryStatus>();
		studySubjectRegistryStatusMap = StudySubjectDaoHelper.constructRegistryStatusHashMap(studySubjects);
		ids.addAll(studySubjectRegistryStatusMap.keySet());
		if(!ids.isEmpty()) {
			String baseQuery = "select distinct r.id, r.code, ssrs.id as sssrs_id from reasons r inner join REGISTRY_REASONS_ASSN rra " +
					"on r.id = rra.reason_id inner join STU_SUB_REG_STATUSES ssrs on rra.stu_sub_reg_st_id = ssrs.id where ssrs.id in ";
			Query query = StudySubjectDaoHelper.handleOracle1000LimitForInStatement(ids, baseQuery, getHibernateTemplate());
	        List<Object> registryReasonObjects =query.list();
	        objectFactory.buildRegistryReasonObjects(registryReasonObjects,studySubjectRegistryStatusMap);
		}
	}
	
	
	public void loadStudyIdentifierOrganizationIdentifiers(List<StudySubject> studySubjects){
		List<Integer> ids = new ArrayList<Integer>();
		List<StudySubjectStudyVersion> studySubjectStudyVersions = StudySubjectDaoHelper.getStudySubjectStudyVersions(studySubjects);
		Map<Integer,HealthcareSite> healthcareSitesMap =  new HashMap<Integer,HealthcareSite>();
		healthcareSitesMap = StudySubjectDaoHelper.constructStudyIdentifierHealthcareSiteHashMap(studySubjectStudyVersions);
		ids.addAll(healthcareSitesMap.keySet());
		if(!ids.isEmpty()) {
			String baseQuery = "SELECT id.ORG_ID, id.dtype, id.type,id.primary_indicator,id.value from IDENTIFIERS id where id.ORG_ID in ";
			Query query = StudySubjectDaoHelper.handleOracle1000LimitForInStatement(ids, baseQuery, getHibernateTemplate());
	        List<Object> identifierObjects =query.list();
	        objectFactory.buildOrganizationIdentifiers(identifierObjects,healthcareSitesMap);
		}
	}
		
}