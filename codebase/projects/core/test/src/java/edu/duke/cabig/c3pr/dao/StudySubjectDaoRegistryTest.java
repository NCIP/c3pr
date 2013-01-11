/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.dao;

import static edu.duke.cabig.c3pr.C3PRUseCase.ADD_DISEASE_SUBJECT;
import static edu.duke.cabig.c3pr.C3PRUseCase.ASSIGN_ARM;
import static edu.duke.cabig.c3pr.C3PRUseCase.ASSIGN_EXISTING_PARTICIPANT;
import static edu.duke.cabig.c3pr.C3PRUseCase.ASSIGN_REGISTERED_PARTICIPANT;
import static edu.duke.cabig.c3pr.C3PRUseCase.CREATE_INCOMPLETE_REGISTERATION;
import static edu.duke.cabig.c3pr.C3PRUseCase.CREATE_LOCAL_REGISTERATION;
import static edu.duke.cabig.c3pr.C3PRUseCase.UPDATE_REGISTERATION_STATUS;

import java.util.ArrayList;
import java.util.List;

import com.semanticbits.querybuilder.AdvancedSearchCriteriaParameter;
import com.semanticbits.querybuilder.AdvancedSearchHelper;

import edu.duke.cabig.c3pr.C3PRUseCases;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.utils.DaoTestCase;

/**
 * JUnit Tests for ParticipantDao
 *
 * @author Priyatam
 * @testType unit
 */
@C3PRUseCases( { ADD_DISEASE_SUBJECT, ASSIGN_EXISTING_PARTICIPANT, ASSIGN_REGISTERED_PARTICIPANT,
        CREATE_INCOMPLETE_REGISTERATION, CREATE_LOCAL_REGISTERATION, ASSIGN_ARM,
        UPDATE_REGISTERATION_STATUS })
public class StudySubjectDaoRegistryTest extends DaoTestCase {
    private StudySubjectDao studySubjectDao;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        studySubjectDao = (StudySubjectDao) getApplicationContext().getBean("studySubjectDao");
    }
	
	public void testGetResultSetWithHQLForRegistryEffectiveDate() throws Exception {
		List<String> values = new ArrayList<String>();
		values.add("01/01/2000");

		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
				.buildAdvancedSearchCriteriaParameter(
						"edu.duke.cabig.c3pr.domain.StudySubjectRegistryStatus", "effectiveDate",
						values, "=");

		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
		criteriaParameters.add(advancedSearchCriteriaParameter1);
		
		List<StudySubject> registrations = studySubjectDao.search(criteriaParameters);
		assertEquals("1 registration not found", 1,  registrations.size());
	}
	
	public void testGetResultSetWithHQLForRegistryEffectiveDateIn() throws Exception {
		List<String> values = new ArrayList<String>();
		values.add("01/01/2000");
		values.add("01/11/2000");

		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
				.buildAdvancedSearchCriteriaParameter(
						"edu.duke.cabig.c3pr.domain.StudySubjectRegistryStatus", "effectiveDate",
						values, "in");

		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
		criteriaParameters.add(advancedSearchCriteriaParameter1);
		
		List<StudySubject> registrations = studySubjectDao.search(criteriaParameters);
		assertEquals("1 registration not found", 1,  registrations.size());
	}
	
	public void testGetResultSetWithHQLForRegistryEffectiveDateNotIn() throws Exception {
		List<String> values = new ArrayList<String>();
		values.add("01/01/2000");
		values.add("01/11/2000");

		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
				.buildAdvancedSearchCriteriaParameter(
						"edu.duke.cabig.c3pr.domain.StudySubjectRegistryStatus", "effectiveDate",
						values, "not in");

		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
		criteriaParameters.add(advancedSearchCriteriaParameter1);
		
		List<StudySubject> registrations = studySubjectDao.search(criteriaParameters);
		assertEquals("1 registration not found", 1,  registrations.size());
	}
	
	public void testGetResultSetWithHQLForRegistryEffectiveDateBetween() throws Exception {
		List<String> values = new ArrayList<String>();
		values.add("01/01/2000");
		values.add("01/11/2000");

		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
				.buildAdvancedSearchCriteriaParameter(
						"edu.duke.cabig.c3pr.domain.StudySubjectRegistryStatus", "effectiveDate",
						values, "between");

		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
		criteriaParameters.add(advancedSearchCriteriaParameter1);
		
		List<StudySubject> registrations = studySubjectDao.search(criteriaParameters);
		assertEquals("1 registration not found", 1,  registrations.size());
	}
	
	public void testGetResultSetWithHQLForRegistryStatus() throws Exception {
		List<String> values = new ArrayList<String>();
		values.add("Pre-Enrolled");

		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
				.buildAdvancedSearchCriteriaParameter(
						"edu.duke.cabig.c3pr.domain.RegistryStatus", "code",
						values, "=");

		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
		criteriaParameters.add(advancedSearchCriteriaParameter1);
		
		List<StudySubject> registrations = studySubjectDao.search(criteriaParameters);
		assertEquals("1 registration not found", 1,  registrations.size());
	}
	
	public void testGetResultSetWithHQLForRegistryStatusReasonCode() throws Exception {
		List<String> values = new ArrayList<String>();
       	values.add("FAILED INCLUSION");

		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
				.buildAdvancedSearchCriteriaParameter(
						"edu.duke.cabig.c3pr.domain.RegistryStatusReason", "code",
						values, "like");

		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
		criteriaParameters.add(advancedSearchCriteriaParameter1);
		
		List<StudySubject> registrations = studySubjectDao.search(criteriaParameters);
		assertEquals("1 registration not found", 1,  registrations.size());
	}
}
