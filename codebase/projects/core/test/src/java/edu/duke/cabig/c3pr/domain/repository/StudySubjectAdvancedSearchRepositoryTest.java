package edu.duke.cabig.c3pr.domain.repository;

import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import com.semanticbits.querybuilder.QueryBuilder;
import com.semanticbits.querybuilder.QueryBuilderDao;

import edu.duke.cabig.c3pr.utils.DaoTestCase;

public class StudySubjectAdvancedSearchRepositoryTest extends
		DaoTestCase {

	private QueryBuilderDao queryBuilderDao;
	private QueryBuilder queryBuilder ;

	public QueryBuilderDao getQueryBuilderDao() {
		return queryBuilderDao;
	}

	public void setQueryBuilderDao(QueryBuilderDao queryBuilderDao) {
		this.queryBuilderDao = queryBuilderDao;
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		queryBuilderDao = (QueryBuilderDao) getApplicationContext().getBean("queryBuilderDao");
		InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("sample-registration-advanced-search.xml");
		Unmarshaller unmarshaller;
		try {
			unmarshaller = JAXBContext.newInstance("com.semanticbits.querybuilder").createUnmarshaller();
			queryBuilder = (QueryBuilder) unmarshaller.unmarshal(inputStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void testGetResultSetWithHQLForWorkFlowStatus() throws Exception {
//		TargetObject targetObject = (TargetObject) queryBuilder.getTargetObject().get(0);
//
//		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper.buildAdvancedSearchCriteriaParameter(
//						"edu.duke.cabig.c3pr.domain.StudySubject", "regWorkflowStatus.code", "ENROLLED", "=");
//
//		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
//		criteriaParameters.add(advancedSearchCriteriaParameter1);
//		
//		String hql = QueryGenerator.generateHQL(targetObject, criteriaParameters, true);
//		
//		List<StudySubject> registrations = (List<StudySubject>)queryBuilderDao.search(hql);
//		assertEquals("2 registrations not found", 2,  registrations.size());
	}

//	public void testGetResultSetWithHQLForDataEntryStatus() throws Exception {
//		TargetObject targetObject = (TargetObject) queryBuilder.getTargetObject().get(0);
//
//		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
//				.buildAdvancedSearchCriteriaParameter(
//						"edu.duke.cabig.c3pr.domain.StudySubject", "regDataEntryStatus.code", "COMPLETE", "=");
//
//		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
//		criteriaParameters.add(advancedSearchCriteriaParameter1);
//		
//		String hql = QueryGenerator.generateHQL(targetObject, criteriaParameters, true);
//		
//		List<StudySubject> registrations = (List<StudySubject>)queryBuilderDao.search(hql);
//		assertEquals("1 registration not found", 1,  registrations.size());
//	}
//	
//	public void testGetResultSetWithHQLForPaymentMethod() throws Exception {
//		TargetObject targetObject = (TargetObject) queryBuilder.getTargetObject().get(0);
//
//		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
//				.buildAdvancedSearchCriteriaParameter( "edu.duke.cabig.c3pr.domain.StudySubject", "paymentMethod", "medicare", "like");
//
//		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
//		criteriaParameters.add(advancedSearchCriteriaParameter1);
//		
//		String hql = QueryGenerator.generateHQL(targetObject, criteriaParameters, true);
//		
//		List<StudySubject> registrations = (List<StudySubject>)queryBuilderDao.search(hql);
//		assertEquals("2 registrations not found", 2,  registrations.size());
//	}
//	
//	public void testGetResultSetWithHQLForRegistrationStartDate() throws Exception {
//		TargetObject targetObject = (TargetObject) queryBuilder.getTargetObject().get(0);
//
//		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
//				.buildAdvancedSearchCriteriaParameter( "edu.duke.cabig.c3pr.domain.StudySubject", "startDate", "01/01/2007", ">");
//
//		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
//		criteriaParameters.add(advancedSearchCriteriaParameter1);
//		
//		String hql = QueryGenerator.generateHQL(targetObject, criteriaParameters, true);
//		
//		List<StudySubject> registrations = (List<StudySubject>)queryBuilderDao.search(hql);
//		assertEquals("2 registrations not found", 2,  registrations.size());
//	}
//	
//	public void testGetResultSetWithHQLForIdentifierValue() throws Exception {
//		TargetObject targetObject = (TargetObject) queryBuilder.getTargetObject().get(0);
//
//		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
//				.buildAdvancedSearchCriteriaParameter( "edu.duke.cabig.c3pr.domain.Identifier", "registrationIdentifierCriteria", "value", "nci1%", "like");
//
//		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
//		criteriaParameters.add(advancedSearchCriteriaParameter1);
//		
//		String hql = QueryGenerator.generateHQL(targetObject, criteriaParameters, true);
//		
//		List<StudySubject> registrations = (List<StudySubject>)queryBuilderDao.search(hql);
//		assertEquals("1 registration not found", 1,  registrations.size());
//	}
//	
//	public void testGetResultSetWithHQLForIdentifierType() throws Exception {
//		TargetObject targetObject = (TargetObject) queryBuilder.getTargetObject().get(0);
//
//		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
//				.buildAdvancedSearchCriteriaParameter( "edu.duke.cabig.c3pr.domain.Identifier", "registrationIdentifierCriteria", "typeInternal", "local", "like");
//
//		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
//		criteriaParameters.add(advancedSearchCriteriaParameter1);
//		
//		String hql = QueryGenerator.generateHQL(targetObject, criteriaParameters, true);
//		
//		List<StudySubject> registrations = (List<StudySubject>)queryBuilderDao.search(hql);
//		assertEquals("2 registrations not found", 2,  registrations.size());
//	}
//	
//	public void testGetResultSetWithHQLForParticipantIdentifier() throws Exception {
//		TargetObject targetObject = (TargetObject) queryBuilder.getTargetObject().get(0);
//
//		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
//				.buildAdvancedSearchCriteriaParameter( "edu.duke.cabig.c3pr.domain.Identifier", "registrationParticipantIdentifierCriteria", "value", "mrn%", "like");
//
//		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
//		criteriaParameters.add(advancedSearchCriteriaParameter1);
//		
//		String hql = QueryGenerator.generateHQL(targetObject, criteriaParameters, true);
//		
//		List<StudySubject> registrations = (List<StudySubject>)queryBuilderDao.search(hql);
//		assertEquals("1 registration not found", 1,  registrations.size());
//	}
//	
//	public void testGetResultSetWithHQLForInvestigatorAssignedIdentifier() throws Exception {
//		TargetObject targetObject = (TargetObject) queryBuilder.getTargetObject().get(0);
//
//		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
//				.buildAdvancedSearchCriteriaParameter( "edu.duke.cabig.c3pr.domain.Investigator","registrationTreatingPhysicianCriteria" ,"assignedIdentifier", "x1", "=");
//
//		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
//		criteriaParameters.add(advancedSearchCriteriaParameter1);
//		
//		String hql = QueryGenerator.generateHQL(targetObject, criteriaParameters, true);
//		
//		List<StudySubject> registrations = (List<StudySubject>)queryBuilderDao.search(hql);
//		assertEquals("2 registrations not found", 2,  registrations.size());
//	}
//	
//	public void testGetResultSetWithHQLForInvestigatorFirstName() throws Exception {
//		TargetObject targetObject = (TargetObject) queryBuilder.getTargetObject().get(0);
//
//		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
//				.buildAdvancedSearchCriteriaParameter(
//						"edu.duke.cabig.c3pr.domain.Investigator", "registrationTreatingPhysicianCriteria", "firstName", "Bill%", "like");
//
//		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
//		criteriaParameters.add(advancedSearchCriteriaParameter1);
//		
//		String hql = QueryGenerator.generateHQL(targetObject, criteriaParameters, true);
//		
//		List<StudySubject> registrations = (List<StudySubject>)queryBuilderDao.search(hql);
//		assertEquals("2 registrations not found", 2,  registrations.size());
//	}
//	
//	public void testGetResultSetWithHQLForSubjectFirstName() throws Exception {
//		TargetObject targetObject = (TargetObject) queryBuilder.getTargetObject().get(0);
//
//		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
//				.buildAdvancedSearchCriteriaParameter(
//						"edu.duke.cabig.c3pr.domain.StudySubjectDemographics", "registrationParticipantCriteria", "firstName",
//						"Rudo%", "like");
//
//		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
//		criteriaParameters.add(advancedSearchCriteriaParameter1);
//		
//		String hql = QueryGenerator.generateHQL(targetObject, criteriaParameters, true);
//		
//		List<StudySubject> registrations = (List<StudySubject>)queryBuilderDao.search(hql);
//		assertEquals("1 registration not found", 1,  registrations.size());
//	}
//	
//	public void testGetResultSetWithHQLForParticipantZipCode() throws Exception {
//		TargetObject targetObject = (TargetObject) queryBuilder.getTargetObject().get(0);
//
//		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
//				.buildAdvancedSearchCriteriaParameter(
//						"edu.duke.cabig.c3pr.domain.Address", "registrationParticipantAddressCriteria", "postalCode",
//						"20171", "=");
//
//		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
//		criteriaParameters.add(advancedSearchCriteriaParameter1);
//		
//		String hql = QueryGenerator.generateHQL(targetObject, criteriaParameters, true);
//		
//		List<StudySubject> registrations = (List<StudySubject>)queryBuilderDao.search(hql);
//		assertEquals("3 registrations not found", 3,  registrations.size());
//	}
//	
//	public void testGetResultSetWithHQLForDiseaseSite() throws Exception {
//		TargetObject targetObject = (TargetObject) queryBuilder.getTargetObject().get(0);
//
//		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
//				.buildAdvancedSearchCriteriaParameter(
//						"edu.duke.cabig.c3pr.domain.ICD9DiseaseSite", "registrationICD9DiseaseSiteCriteria", "code",
//						"200", "=");
//
//		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
//		criteriaParameters.add(advancedSearchCriteriaParameter1);
//		
//		String hql = QueryGenerator.generateHQL(targetObject, criteriaParameters, true);
//		
//		List<StudySubject> registrations = (List<StudySubject>)queryBuilderDao.search(hql);
//		assertEquals("1 registration not found", 1,  registrations.size());
//	}
//	
//	public void testGetResultSetWithHQLForStudyDiseaseTerm() throws Exception {
//		TargetObject targetObject = (TargetObject) queryBuilder.getTargetObject().get(0);
//
//		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
//				.buildAdvancedSearchCriteriaParameter(
//						"edu.duke.cabig.c3pr.domain.DiseaseTerm", "registrationDiseaseTermCriteria", "term",
//						"%anal cancer%", "like");
//
//		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
//		criteriaParameters.add(advancedSearchCriteriaParameter1);
//		
//		String hql = QueryGenerator.generateHQL(targetObject, criteriaParameters, true);
//		
//		List<StudySubject> registrations = (List<StudySubject>)queryBuilderDao.search(hql);
//		assertEquals("1 registration not found", 1,  registrations.size());
//	}
//	
//	public void testGetResultSetWithHQLForStudyDiseaseCategoryName() throws Exception {
//		TargetObject targetObject = (TargetObject) queryBuilder.getTargetObject().get(0);
//
//		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
//				.buildAdvancedSearchCriteriaParameter(
//						"edu.duke.cabig.c3pr.domain.DiseaseCategory", "registrationDiseaseCategoryCriteria", "name",
//						"%Human Papillomavirus%", "like");
//
//		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
//		criteriaParameters.add(advancedSearchCriteriaParameter1);
//		
//		String hql = QueryGenerator.generateHQL(targetObject, criteriaParameters, true);
//		
//		List<StudySubject> registrations = (List<StudySubject>)queryBuilderDao.search(hql);
//		assertEquals("1 registration not found", 1,  registrations.size());
//	}
//	
//	public void testGetResultSetWithHQLForConsentDeliveryDate() throws Exception {
//		TargetObject targetObject = (TargetObject) queryBuilder.getTargetObject().get(0);
//
//		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
//				.buildAdvancedSearchCriteriaParameter(
//						"edu.duke.cabig.c3pr.domain.StudySubjectConsentVersion", "registrationConsentCriteria", "consentDeliveryDate",
//						"10/10/2010", "=");
//
//		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
//		criteriaParameters.add(advancedSearchCriteriaParameter1);
//		
//		String hql = QueryGenerator.generateHQL(targetObject, criteriaParameters, true);
//		
//		List<StudySubject> registrations = (List<StudySubject>)queryBuilderDao.search(hql);
//		assertEquals("2 registrations not found", 2,  registrations.size());
//	}
//	
//	public void testGetResultSetWithHQLForConsentPresenter() throws Exception {
//		TargetObject targetObject = (TargetObject) queryBuilder.getTargetObject().get(0);
//
//		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
//				.buildAdvancedSearchCriteriaParameter(
//						"edu.duke.cabig.c3pr.domain.StudySubjectConsentVersion", "registrationConsentCriteria", "consentPresenter",
//						"Himanshu", "=");
//
//		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
//		criteriaParameters.add(advancedSearchCriteriaParameter1);
//		
//		String hql = QueryGenerator.generateHQL(targetObject, criteriaParameters, true);
//		
//		List<StudySubject> registrations = (List<StudySubject>)queryBuilderDao.search(hql);
//		assertEquals("2 registrations not found", 2,  registrations.size());
//	}
//	
//	public void testGetResultSetWithHQLForInformedConsentSignedDate() throws Exception {
//		TargetObject targetObject = (TargetObject) queryBuilder.getTargetObject().get(0);
//
//		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
//				.buildAdvancedSearchCriteriaParameter(
//						"edu.duke.cabig.c3pr.domain.StudySubjectConsentVersion", "registrationConsentCriteria", "informedConsentSignedTimestamp",
//						"11/10/2010", ">");
//
//		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
//		criteriaParameters.add(advancedSearchCriteriaParameter1);
//		
//		String hql = QueryGenerator.generateHQL(targetObject, criteriaParameters, true);
//		
//		List<StudySubject> registrations = (List<StudySubject>)queryBuilderDao.search(hql);
//		assertEquals("2 registrations not found", 2,  registrations.size());
//	}
//	
//	public void testGetResultSetWithHQLForConsentMethod() throws Exception {
//		TargetObject targetObject = (TargetObject) queryBuilder.getTargetObject().get(0);
//
//		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
//				.buildAdvancedSearchCriteriaParameter(
//						"edu.duke.cabig.c3pr.domain.StudySubjectConsentVersion", "registrationConsentCriteria", "consentingMethod.code",
//						"WRITTEN", "=");
//
//		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
//		criteriaParameters.add(advancedSearchCriteriaParameter1);
//		
//		String hql = QueryGenerator.generateHQL(targetObject, criteriaParameters, true);
//		
//		List<StudySubject> registrations = (List<StudySubject>)queryBuilderDao.search(hql);
//		assertEquals("3 registrations not found", 3,  registrations.size());
//	}
//	
//	public void testGetResultSetWithHQLForEligibilityIndicator() throws Exception {
//		TargetObject targetObject = (TargetObject) queryBuilder.getTargetObject().get(0);
//
//		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
//				.buildAdvancedSearchCriteriaParameter(
//						"edu.duke.cabig.c3pr.domain.ScheduledEpoch", "registrationScheduledEpochCriteria", "eligibilityIndicator",
//						"true", "=");
//
//		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
//		criteriaParameters.add(advancedSearchCriteriaParameter1);
//		
//		String hql = QueryGenerator.generateHQL(targetObject, criteriaParameters, true);
//		
//		List<StudySubject> registrations = (List<StudySubject>)queryBuilderDao.search(hql);
//		assertEquals("4 registrations not found", 4,  registrations.size());
//	}
//	
//	public void testGetResultSetWithHQLForScheduledEpochDataEntryStatus() throws Exception {
//		TargetObject targetObject = (TargetObject) queryBuilder.getTargetObject().get(0);
//
//		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
//				.buildAdvancedSearchCriteriaParameter(
//						"edu.duke.cabig.c3pr.domain.ScheduledEpoch", "registrationScheduledEpochCriteria", "scEpochDataEntryStatus.code",
//						"COMPLETE", "=");
//
//		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
//		criteriaParameters.add(advancedSearchCriteriaParameter1);
//		
//		String hql = QueryGenerator.generateHQL(targetObject, criteriaParameters, true);
//		
//		List<StudySubject> registrations = (List<StudySubject>)queryBuilderDao.search(hql);
//		assertEquals("1 registration not found", 1,  registrations.size());
//	}
//	
//	public void testGetResultSetWithHQLForScheduledEpochWorkFlowStatus() throws Exception {
//		TargetObject targetObject = (TargetObject) queryBuilder.getTargetObject().get(0);
//
//		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
//				.buildAdvancedSearchCriteriaParameter(
//						"edu.duke.cabig.c3pr.domain.ScheduledEpoch", "registrationScheduledEpochCriteria", "scEpochWorkflowStatus.code",
//						"PENDING", "=");
//
//		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
//		criteriaParameters.add(advancedSearchCriteriaParameter1);
//		
//		String hql = QueryGenerator.generateHQL(targetObject, criteriaParameters, true);
//		
//		List<StudySubject> registrations = (List<StudySubject>)queryBuilderDao.search(hql);
//		assertEquals("2 registrations not found", 2,  registrations.size());
//	}
//	
//	public void testGetResultSetWithHQLForOffEpochReasonCode() throws Exception {
//		TargetObject targetObject = (TargetObject) queryBuilder.getTargetObject().get(0);
//
//		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
//				.buildAdvancedSearchCriteriaParameter(
//						"edu.duke.cabig.c3pr.domain.Reason", "registrationOffEpochReasonCriteria", "code",
//						"test reason", "like");
//
//		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
//		criteriaParameters.add(advancedSearchCriteriaParameter1);
//		
//		String hql = QueryGenerator.generateHQL(targetObject, criteriaParameters, true);
//		
//		List<StudySubject> registrations = (List<StudySubject>)queryBuilderDao.search(hql);
//		assertEquals("1 registration not found", 1,  registrations.size());
//	}
//	
//	public void testGetResultSetWithHQLForEpochType() throws Exception {
//		TargetObject targetObject = (TargetObject) queryBuilder.getTargetObject().get(0);
//
//		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
//				.buildAdvancedSearchCriteriaParameter(
//						"edu.duke.cabig.c3pr.domain.Epoch", "registrationEpochCriteria", "type.code",
//						"SCREENING", "=");
//
//		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
//		criteriaParameters.add(advancedSearchCriteriaParameter1);
//		
//		String hql = QueryGenerator.generateHQL(targetObject, criteriaParameters, true);
//		
//		List<StudySubject> registrations = (List<StudySubject>)queryBuilderDao.search(hql);
//		assertEquals("1 registration not found", 1,  registrations.size());
//	}
//	
//	public void testGetResultSetWithHQLForEpochName() throws Exception {
//		TargetObject targetObject = (TargetObject) queryBuilder.getTargetObject().get(0);
//
//		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
//				.buildAdvancedSearchCriteriaParameter(
//						"edu.duke.cabig.c3pr.domain.Epoch", "registrationEpochCriteria", "name",
//						"NonTreatment%", "like");
//
//		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
//		criteriaParameters.add(advancedSearchCriteriaParameter1);
//		
//		String hql = QueryGenerator.generateHQL(targetObject, criteriaParameters, true);
//		
//		List<StudySubject> registrations = (List<StudySubject>)queryBuilderDao.search(hql);
//		assertEquals("1 registration not found", 1,  registrations.size());
//	}
//	
//	public void testGetResultSetWithHQLForStudyShortTitle() throws Exception {
//		TargetObject targetObject = (TargetObject) queryBuilder.getTargetObject().get(0);
//
//		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
//				.buildAdvancedSearchCriteriaParameter(
//						"edu.duke.cabig.c3pr.domain.StudyVersion", "registrationStudyVersionCriteria", "shortTitleText",
//						"%title_text1", "like");
//
//		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
//		criteriaParameters.add(advancedSearchCriteriaParameter1);
//		
//		String hql = QueryGenerator.generateHQL(targetObject, criteriaParameters, true);
//		
//		List<StudySubject> registrations = (List<StudySubject>)queryBuilderDao.search(hql);
//		assertEquals("2 registrations not found", 2,  registrations.size());
//	}
//	
//	public void testGetResultSetWithHQLForStudyTherapeuticIntent() throws Exception {
//		TargetObject targetObject = (TargetObject) queryBuilder.getTargetObject().get(0);
//
//		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
//				.buildAdvancedSearchCriteriaParameter(
//						"edu.duke.cabig.c3pr.domain.Study", "registrationStudyCriteria", "therapeuticIntentIndicator",
//						"false", "=");
//
//		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
//		criteriaParameters.add(advancedSearchCriteriaParameter1);
//		
//		String hql = QueryGenerator.generateHQL(targetObject, criteriaParameters, true);
//		
//		List<StudySubject> registrations = (List<StudySubject>)queryBuilderDao.search(hql);
//		assertEquals("4 registrations not found", 4,  registrations.size());
//	}
//	
//	public void testGetResultSetWithHQLForStudyPhaseCode() throws Exception {
//		TargetObject targetObject = (TargetObject) queryBuilder.getTargetObject().get(0);
//
//		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
//				.buildAdvancedSearchCriteriaParameter(
//						"edu.duke.cabig.c3pr.domain.Study", "registrationStudyCriteria", "phaseCode",
//						"Ph", "=");
//
//		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
//		criteriaParameters.add(advancedSearchCriteriaParameter1);
//		
//		String hql = QueryGenerator.generateHQL(targetObject, criteriaParameters, true);
//		
//		List<StudySubject> registrations = (List<StudySubject>)queryBuilderDao.search(hql);
//		assertEquals("4 registrations not found", 4,  registrations.size());
//	}
//	
//	public void testGetResultSetWithHQLForStudyType() throws Exception {
//		TargetObject targetObject = (TargetObject) queryBuilder.getTargetObject().get(0);
//
//		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
//				.buildAdvancedSearchCriteriaParameter(
//						"edu.duke.cabig.c3pr.domain.Study", "registrationStudyCriteria", "type",
//						"Ty", "=");
//
//		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
//		criteriaParameters.add(advancedSearchCriteriaParameter1);
//		
//		String hql = QueryGenerator.generateHQL(targetObject, criteriaParameters, true);
//		
//		List<StudySubject> registrations = (List<StudySubject>)queryBuilderDao.search(hql);
//		assertEquals("5 registrations not found", 5,  registrations.size());
//	}
//	
//	public void testGetResultSetWithHQLForPersonnelFirstName() throws Exception {
//		TargetObject targetObject = (TargetObject) queryBuilder.getTargetObject().get(0);
//
//		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
//				.buildAdvancedSearchCriteriaParameter(
//						"edu.duke.cabig.c3pr.domain.ResearchStaff", "registrationStudyPersonnelCriteria", "firstName",
//						"Research Bill%", "like");
//
//		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
//		criteriaParameters.add(advancedSearchCriteriaParameter1);
//		
//		String hql = QueryGenerator.generateHQL(targetObject, criteriaParameters, true);
//		
//		List<StudySubject> registrations = (List<StudySubject>)queryBuilderDao.search(hql);
//		assertEquals("1 registrations not found", 1,  registrations.size());
//	}
//	
//	public void testGetResultSetWithHQLForPersonnelLastName() throws Exception {
//		TargetObject targetObject = (TargetObject) queryBuilder.getTargetObject().get(0);
//
//		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
//				.buildAdvancedSearchCriteriaParameter(
//						"edu.duke.cabig.c3pr.domain.ResearchStaff", "registrationStudyPersonnelCriteria", "lastName",
//						"Staff2%", "like");
//
//		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
//		criteriaParameters.add(advancedSearchCriteriaParameter1);
//		
//		String hql = QueryGenerator.generateHQL(targetObject, criteriaParameters, true);
//		
//		List<StudySubject> registrations = (List<StudySubject>)queryBuilderDao.search(hql);
//		assertEquals("2 registrations not found", 2,  registrations.size());
//	}
//	
//	public void testGetResultSetWithHQLForPersonnelAssignedIdentifier() throws Exception {
//		TargetObject targetObject = (TargetObject) queryBuilder.getTargetObject().get(0);
//
//		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
//				.buildAdvancedSearchCriteriaParameter(
//						"edu.duke.cabig.c3pr.domain.ResearchStaff", "registrationStudyPersonnelCriteria", "assignedIdentifier",
//						"x1", "=");
//
//		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
//		criteriaParameters.add(advancedSearchCriteriaParameter1);
//		
//		String hql = QueryGenerator.generateHQL(targetObject, criteriaParameters, true);
//		
//		List<StudySubject> registrations = (List<StudySubject>)queryBuilderDao.search(hql);
//		assertEquals("1 registrations not found", 1,  registrations.size());
//	}
	
	
}
