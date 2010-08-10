package edu.duke.cabig.c3pr.domain.repository;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import com.semanticbits.querybuilder.AdvancedSearchCriteriaParameter;
import com.semanticbits.querybuilder.AdvancedSearchHelper;
import com.semanticbits.querybuilder.QueryBuilder;
import com.semanticbits.querybuilder.QueryBuilderDao;
import com.semanticbits.querybuilder.QueryGenerator;
import com.semanticbits.querybuilder.TargetObject;

import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.StudySubject;
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
	
	// Test cases for registration specific criteria
	
	public void testGetResultSetWithHQLForWorkFlowStatus() throws Exception {
		TargetObject targetObject = (TargetObject) queryBuilder.getTargetObject().get(0);

		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper.buildAdvancedSearchCriteriaParameter(
						"edu.duke.cabig.c3pr.domain.StudySubject", "edu.duke.cabig.c3pr.domain.StudySubject", "regWorkflowStatus.code",
						"ENROLLED", "=");

		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
		criteriaParameters.add(advancedSearchCriteriaParameter1);
		
		String hql = QueryGenerator.generateHQL(targetObject, criteriaParameters, true);
		
		List<StudySubject> registrations = (List<StudySubject>)queryBuilderDao.search(hql);
		assertEquals("2 registrations not found", 2,  registrations.size());
	}

	public void testGetResultSetWithHQLForDataEntryStatus() throws Exception {
		TargetObject targetObject = (TargetObject) queryBuilder.getTargetObject().get(0);

		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
				.buildAdvancedSearchCriteriaParameter(
						"edu.duke.cabig.c3pr.domain.StudySubject", "edu.duke.cabig.c3pr.domain.StudySubject", "regDataEntryStatus.code",
						"COMPLETE", "=");

		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
		criteriaParameters.add(advancedSearchCriteriaParameter1);
		
		String hql = QueryGenerator.generateHQL(targetObject, criteriaParameters, true);
		
		List<StudySubject> registrations = (List<StudySubject>)queryBuilderDao.search(hql);
		assertEquals("1 registration not found", 1,  registrations.size());
	}
	
	public void testGetResultSetWithHQLForPaymentMethod() throws Exception {
		TargetObject targetObject = (TargetObject) queryBuilder.getTargetObject().get(0);

		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
				.buildAdvancedSearchCriteriaParameter(
						"edu.duke.cabig.c3pr.domain.StudySubject", "edu.duke.cabig.c3pr.domain.StudySubject", "paymentMethod",
						"medicare", "like");

		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
		criteriaParameters.add(advancedSearchCriteriaParameter1);
		
		String hql = QueryGenerator.generateHQL(targetObject, criteriaParameters, true);
		
		List<StudySubject> registrations = (List<StudySubject>)queryBuilderDao.search(hql);
		assertEquals("2 registrations not found", 2,  registrations.size());
	}
	
	public void testGetResultSetWithHQLForRegistrationStartDate() throws Exception {
		TargetObject targetObject = (TargetObject) queryBuilder.getTargetObject().get(0);

		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
				.buildAdvancedSearchCriteriaParameter(
						"edu.duke.cabig.c3pr.domain.StudySubject", "edu.duke.cabig.c3pr.domain.StudySubject", "startDate",
						"01/01/2007", ">");

		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
		criteriaParameters.add(advancedSearchCriteriaParameter1);
		
		String hql = QueryGenerator.generateHQL(targetObject, criteriaParameters, true);
		
		List<StudySubject> registrations = (List<StudySubject>)queryBuilderDao.search(hql);
		assertEquals("2 registrations not found", 2,  registrations.size());
	}
	
	public void testGetResultSetWithHQLForIdentifier() throws Exception {
		TargetObject targetObject = (TargetObject) queryBuilder.getTargetObject().get(0);

		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
				.buildAdvancedSearchCriteriaParameter(
						"edu.duke.cabig.c3pr.domain.Identifier", "edu.duke.cabig.c3pr.domain.Identifier", "value",
						"nci1%", "like");

		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
		criteriaParameters.add(advancedSearchCriteriaParameter1);
		
		String hql = QueryGenerator.generateHQL(targetObject, criteriaParameters, true);
		
		List<StudySubject> registrations = (List<StudySubject>)queryBuilderDao.search(hql);
		assertEquals("1 registration not found", 1,  registrations.size());
	}
	
	public void testGetResultSetWithHQLForInvestigatorAssignedIdentifier() throws Exception {
		TargetObject targetObject = (TargetObject) queryBuilder.getTargetObject().get(0);

		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
				.buildAdvancedSearchCriteriaParameter(
						"edu.duke.cabig.c3pr.domain.Investigator", "edu.duke.cabig.c3pr.domain.Investigator", "assignedIdentifier",
						"x1", "=");

		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
		criteriaParameters.add(advancedSearchCriteriaParameter1);
		
		String hql = QueryGenerator.generateHQL(targetObject, criteriaParameters, true);
		
		List<StudySubject> registrations = (List<StudySubject>)queryBuilderDao.search(hql);
		assertEquals("2 registrations not found", 2,  registrations.size());
	}
	
	public void testGetResultSetWithHQLForSubjectFirstName() throws Exception {
		TargetObject targetObject = (TargetObject) queryBuilder.getTargetObject().get(0);

		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
				.buildAdvancedSearchCriteriaParameter(
						"edu.duke.cabig.c3pr.domain.StudySubjectDemographics", "edu.duke.cabig.c3pr.domain.StudySubjectDemographics", "firstName",
						"Rudo%", "like");

		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
		criteriaParameters.add(advancedSearchCriteriaParameter1);
		
		String hql = QueryGenerator.generateHQL(targetObject, criteriaParameters, true);
		
		List<StudySubject> registrations = (List<StudySubject>)queryBuilderDao.search(hql);
		assertEquals("1 registration not found", 1,  registrations.size());
	}
	
	public void testGetResultSetWithHQLForParticipantZipCode() throws Exception {
		TargetObject targetObject = (TargetObject) queryBuilder.getTargetObject().get(0);

		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
				.buildAdvancedSearchCriteriaParameter(
						"edu.duke.cabig.c3pr.domain.Address", "edu.duke.cabig.c3pr.domain.Address", "postalCode",
						"20171", "=");

		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
		criteriaParameters.add(advancedSearchCriteriaParameter1);
		
		String hql = QueryGenerator.generateHQL(targetObject, criteriaParameters, true);
		
		List<StudySubject> registrations = (List<StudySubject>)queryBuilderDao.search(hql);
		assertEquals("3 registrations not found", 3,  registrations.size());
	}
	
	public void testGetResultSetWithHQLForDiseaseSite() throws Exception {
		TargetObject targetObject = (TargetObject) queryBuilder.getTargetObject().get(0);

		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
				.buildAdvancedSearchCriteriaParameter(
						"edu.duke.cabig.c3pr.domain.ICD9DiseaseSite", "edu.duke.cabig.c3pr.domain.ICD9DiseaseSite", "code",
						"200", "=");

		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
		criteriaParameters.add(advancedSearchCriteriaParameter1);
		
		String hql = QueryGenerator.generateHQL(targetObject, criteriaParameters, true);
		
		List<StudySubject> registrations = (List<StudySubject>)queryBuilderDao.search(hql);
		assertEquals("1 registration not found", 1,  registrations.size());
	}
	
	public void testGetResultSetWithHQLForStudyDiseaseTerm() throws Exception {
		TargetObject targetObject = (TargetObject) queryBuilder.getTargetObject().get(0);

		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
				.buildAdvancedSearchCriteriaParameter(
						"edu.duke.cabig.c3pr.domain.DiseaseTerm", "edu.duke.cabig.c3pr.domain.DiseaseTerm", "term",
						"%anal cancer%", "like");

		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
		criteriaParameters.add(advancedSearchCriteriaParameter1);
		
		String hql = QueryGenerator.generateHQL(targetObject, criteriaParameters, true);
		
		List<StudySubject> registrations = (List<StudySubject>)queryBuilderDao.search(hql);
		assertEquals("1 registration not found", 1,  registrations.size());
	}
	
	public void testGetResultSetWithHQLForStudyDiseaseCategoryName() throws Exception {
		TargetObject targetObject = (TargetObject) queryBuilder.getTargetObject().get(0);

		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
				.buildAdvancedSearchCriteriaParameter(
						"edu.duke.cabig.c3pr.domain.DiseaseCategory", "edu.duke.cabig.c3pr.domain.DiseaseCategory", "name",
						"%Human Papillomavirus%", "like");

		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
		criteriaParameters.add(advancedSearchCriteriaParameter1);
		
		String hql = QueryGenerator.generateHQL(targetObject, criteriaParameters, true);
		
		List<StudySubject> registrations = (List<StudySubject>)queryBuilderDao.search(hql);
		assertEquals("1 registration not found", 1,  registrations.size());
	}
	
}
