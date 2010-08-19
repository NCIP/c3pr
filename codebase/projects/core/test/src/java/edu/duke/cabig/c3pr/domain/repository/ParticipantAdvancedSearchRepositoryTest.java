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
import edu.duke.cabig.c3pr.utils.DaoTestCase;

public class ParticipantAdvancedSearchRepositoryTest extends
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
		InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("sample-participant-advanced-search.xml");
		Unmarshaller unmarshaller;
		try {
			unmarshaller = JAXBContext.newInstance("com.semanticbits.querybuilder").createUnmarshaller();
			queryBuilder = (QueryBuilder) unmarshaller.unmarshal(inputStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void testGetResultSetWithHQLForFirstName() throws Exception {
		TargetObject targetObject = (TargetObject) queryBuilder.getTargetObject().get(0);

		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
				.buildAdvancedSearchCriteriaParameter(
						"edu.duke.cabig.c3pr.domain.Participant",  "firstName",
						"%re%", "like");

		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
		criteriaParameters.add(advancedSearchCriteriaParameter1);
		
		String hql = QueryGenerator.generateHQL(targetObject, criteriaParameters, true);
		
		List<Participant> subjects = (List<Participant>)queryBuilderDao.search(hql);
		assertEquals("2 participants not found", 2,  subjects.size());
	}
	
	public void testGetResultSetWithHQLForRaceCode() throws Exception {
		TargetObject targetObject = (TargetObject) queryBuilder.getTargetObject().get(0);

		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
				.buildAdvancedSearchCriteriaParameter(
						"edu.duke.cabig.c3pr.domain.Participant",  "raceCode",
						"Asian", "like");

		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
		criteriaParameters.add(advancedSearchCriteriaParameter1);
		
		String hql = QueryGenerator.generateHQL(targetObject, criteriaParameters, true);
		
		List<Participant> subjects = (List<Participant>)queryBuilderDao.search(hql);
		assertEquals("3 participants not found", 3,  subjects.size());
	}
	
	public void testGetResultSetWithHQLForMultipleRaceCodeNotIn() throws Exception {
		List<String> values = new ArrayList<String>();
		values.add("White");
		values.add("Asian");
		
		TargetObject targetObject = (TargetObject) queryBuilder.getTargetObject().get(0);

		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
				.buildAdvancedSearchCriteriaParameter(
						"edu.duke.cabig.c3pr.domain.Participant",  "raceCode",
						values, "not in");

		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
		criteriaParameters.add(advancedSearchCriteriaParameter1);
		
		String hql = QueryGenerator.generateHQL(targetObject, criteriaParameters, true);
		
		List<Participant> subjects = (List<Participant>)queryBuilderDao.search(hql);
		assertEquals("0 participants not found", 0,  subjects.size());
	}
	
	public void testGetResultSetWithHQLForMultipleRaceCodeIn() throws Exception {
		List<String> values = new ArrayList<String>();
		values.add("White");
		values.add("Asian");
		
		TargetObject targetObject = (TargetObject) queryBuilder.getTargetObject().get(0);

		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
				.buildAdvancedSearchCriteriaParameter(
						"edu.duke.cabig.c3pr.domain.Participant",  "raceCode",
						values, "in");

		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
		criteriaParameters.add(advancedSearchCriteriaParameter1);
		
		String hql = QueryGenerator.generateHQL(targetObject, criteriaParameters, true);
		
		List<Participant> subjects = (List<Participant>)queryBuilderDao.search(hql);
		assertEquals("8 participants not found", 8,  subjects.size());
	}
	
	
	public void testGetResultSetWithHQLForZipcode() throws Exception {
		TargetObject targetObject = (TargetObject) queryBuilder.getTargetObject().get(0);

		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
				.buildAdvancedSearchCriteriaParameter(
						"edu.duke.cabig.c3pr.domain.Address", "participantAddressCriteria", "postalCode",
						"20171", "=");

		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
		criteriaParameters.add(advancedSearchCriteriaParameter1);
		
		String hql = QueryGenerator.generateHQL(targetObject, criteriaParameters, true);
		
		List<Participant> subjects = (List<Participant>)queryBuilderDao.search(hql);
		assertEquals("4 participants not found", 4,  subjects.size());
	}
	
	public void testGetResultSetWithHQLForIdentifier() throws Exception {
		TargetObject targetObject = (TargetObject) queryBuilder.getTargetObject().get(0);

		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
				.buildAdvancedSearchCriteriaParameter(
						"edu.duke.cabig.c3pr.domain.Identifier", "participantIdentifierCriteria", "value",
						"sub%", "like");

		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
		criteriaParameters.add(advancedSearchCriteriaParameter1);
		
		String hql = QueryGenerator.generateHQL(targetObject, criteriaParameters, true);
		
		List<Participant> subjects = (List<Participant>)queryBuilderDao.search(hql);
		assertEquals("2 participants not found", 3,  subjects.size());
	}
	
	public void testGetResultSetWithAllSearchCriterion() throws Exception {
		TargetObject targetObject = (TargetObject) queryBuilder.getTargetObject().get(0);

		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
		.buildAdvancedSearchCriteriaParameter(
				"edu.duke.cabig.c3pr.domain.Participant",  "firstName",
				"ru%", "like");
		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter2 = AdvancedSearchHelper
		.buildAdvancedSearchCriteriaParameter(
				"edu.duke.cabig.c3pr.domain.Participant",  "lastName",
				"%oo%", "like");
		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter3 = AdvancedSearchHelper
		.buildAdvancedSearchCriteriaParameter(
				"edu.duke.cabig.c3pr.domain.Participant",  "administrativeGenderCode",
				"Male", "=");
		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter4 = AdvancedSearchHelper
		.buildAdvancedSearchCriteriaParameter(
				"edu.duke.cabig.c3pr.domain.Participant",  "ethnicGroupCode",
				"Unknown", "=");
		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter5 = AdvancedSearchHelper
		.buildAdvancedSearchCriteriaParameter(
				"edu.duke.cabig.c3pr.domain.Participant", "raceCode",
				"White", "like");
		
		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter6 = AdvancedSearchHelper
		.buildAdvancedSearchCriteriaParameter(
				"edu.duke.cabig.c3pr.domain.Address", "participantAddressCriteria", "postalCode",
				"20171", "=");
		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter7 = AdvancedSearchHelper
		.buildAdvancedSearchCriteriaParameter(
				"edu.duke.cabig.c3pr.domain.Address", "participantAddressCriteria", "stateCode",
				"VA", "like");
		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter8 = AdvancedSearchHelper
		.buildAdvancedSearchCriteriaParameter(
				"edu.duke.cabig.c3pr.domain.Address", "participantAddressCriteria", "countryCode",
				"USA", "like");
		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter9 = AdvancedSearchHelper
		.buildAdvancedSearchCriteriaParameter(
				"edu.duke.cabig.c3pr.domain.Address", "participantAddressCriteria", "city",
				"Herndon", "like");
		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter10 = AdvancedSearchHelper
		.buildAdvancedSearchCriteriaParameter(
				"edu.duke.cabig.c3pr.domain.Identifier", "participantIdentifierCriteria", "value",
				"sub%", "like");
		
		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
		criteriaParameters.add(advancedSearchCriteriaParameter1);
		criteriaParameters.add(advancedSearchCriteriaParameter2);
		criteriaParameters.add(advancedSearchCriteriaParameter3);
		criteriaParameters.add(advancedSearchCriteriaParameter4);
		criteriaParameters.add(advancedSearchCriteriaParameter5);
		criteriaParameters.add(advancedSearchCriteriaParameter6);
		criteriaParameters.add(advancedSearchCriteriaParameter7);
		criteriaParameters.add(advancedSearchCriteriaParameter8);
		criteriaParameters.add(advancedSearchCriteriaParameter9);
		criteriaParameters.add(advancedSearchCriteriaParameter10);
		
		String hql = QueryGenerator.generateHQL(targetObject, criteriaParameters, true);
		
		System.out.println("######################################################");
		System.out.println("Generated HQL is : " + hql);
		System.out.println("######################################################");
		
		List<Participant> subjects = (List<Participant>)queryBuilderDao.search(hql);
		assertEquals("Only one subject expected", 1, subjects.size());
		assertEquals("First name should be Rudolph", "Rudolph", subjects.get(0).getFirstName());
		assertEquals("Last name should be Clooney", "Clooney", subjects.get(0).getLastName());
	}
}
