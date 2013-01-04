/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 *   Distributed under the OSI-approved BSD 3-Clause License.
 *   See https://github.com/NCIP/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.accesscontrol;

import java.util.List;

import org.acegisecurity.AccessDeniedException;
import org.acegisecurity.Authentication;

import edu.duke.cabig.c3pr.dao.StudyDao;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.utils.AuthenticationProviderDaoTestCase;

/**
 * Tests for StudySecurityFilter.
 *
 * @author Vinay Gangoli
 * @testType Integration
 */

public class StudySecurityFilterTest extends AuthenticationProviderDaoTestCase {

	private StudySecurityFilter studySecurityFilter;
	private StudyDao studyDao;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		studySecurityFilter = new StudySecurityFilter();
		studyDao = (StudyDao) getApplicationContext().getBean("studyDao");
	}
	
    
	@SuppressWarnings("unchecked")
	public void testFilterStudyCreator(){
		Authentication authentication = setUserInSecurityContext("study_creator");
		List<Study> studies = studyDao.getAll();
		
		List<Study> filteredStudies = (List<Study>)studySecurityFilter.filter(authentication, "", new CollectionFilterer(studies));
		assertEquals(3, filteredStudies.size());
	}
	
	@SuppressWarnings("unchecked")
	public void testFilterSupplementalStudyInformationManager(){
		Authentication authentication = setUserInSecurityContext("supplemental_study_information_manager");
		List<Study> studies = studyDao.getAll();
		
		List<Study> filteredStudies = (List<Study>)studySecurityFilter.filter(authentication, "", new CollectionFilterer(studies));
		assertEquals(3, filteredStudies.size());
	}
	
	@SuppressWarnings("unchecked")
	public void testFilterStudyTeamAdministrator(){
		Authentication authentication = setUserInSecurityContext("study_team_administrator");
		List<Study> studies = studyDao.getAll();
		
		List<Study> filteredStudies = (List<Study>)studySecurityFilter.filter(authentication, "", new CollectionFilterer(studies));
		assertEquals(2, filteredStudies.size());
	}
	
	@SuppressWarnings("unchecked")
	public void testFilterStudySiteParticipationAdministrator(){
		Authentication authentication = setUserInSecurityContext("study_site_participation_administrator");
		List<Study> studies = studyDao.getAll();
		
		List<Study> filteredStudies = (List<Study>)studySecurityFilter.filter(authentication, "", new CollectionFilterer(studies));
		assertEquals(1, filteredStudies.size());
	}
	
	@SuppressWarnings("unchecked")
	public void testFilterSystemAdministrator(){
		Authentication authentication = setUserInSecurityContext("system_administrator");
		List<Study> studies = studyDao.getAll();
		
		List<Study> filteredStudies = (List<Study>)studySecurityFilter.filter(authentication, "", new CollectionFilterer(studies));
		assertEquals(0, filteredStudies.size());
	}
	
	public void testFilterStudyCreatorNonCollection(){
		Authentication authentication = setUserInSecurityContext("study_creator");
		Study study = studyDao.getById(1000);
		assertNotNull(studySecurityFilter.filter(authentication, "", new AbstractMutableDomainObjectFilterer(study)));
		
		study = studyDao.getById(1001);
		assertNotNull(studySecurityFilter.filter(authentication, "", new AbstractMutableDomainObjectFilterer(study)));
		
		study = studyDao.getById(1002);
		assertNotNull(studySecurityFilter.filter(authentication, "", new AbstractMutableDomainObjectFilterer(study)));
	}
	
	@SuppressWarnings("unchecked")
	public void testFilterRegistrar(){
		Authentication authentication = setUserInSecurityContext("registrar");
		List<Study> studies = studyDao.getAll();
		
		List<Study> filteredStudies = (List<Study>)studySecurityFilter.filter(authentication, "", new CollectionFilterer(studies));
		assertEquals(2, filteredStudies.size());
		assertTrue(containsElementWithId(filteredStudies, 1000));
		assertTrue(containsElementWithId(filteredStudies, 1001));
		assertFalse(containsElementWithId(filteredStudies, 1002));
	}

	@SuppressWarnings("unchecked")
	public void testFilterRegistrarAllStudy(){
		Authentication authentication = setUserInSecurityContext("registrar-allStudy");
		List<Study> studies = studyDao.getAll();
		
		List<Study> filteredStudies = (List<Study>)studySecurityFilter.filter(authentication, "", new CollectionFilterer(studies));
		assertEquals(3, filteredStudies.size());
		assertTrue(containsElementWithId(filteredStudies, 1000));
		assertTrue(containsElementWithId(filteredStudies, 1001));
		assertTrue(containsElementWithId(filteredStudies, 1002));
	}
	
	public void testFilterRegistrarNonCollection(){
		Authentication authentication = setUserInSecurityContext("registrar");
		Study study = studyDao.getById(1000);
		assertNotNull(studySecurityFilter.filter(authentication, "", new AbstractMutableDomainObjectFilterer(study)));
		
		study = studyDao.getById(1002);
		try{
			studySecurityFilter.filter(authentication, "", new AbstractMutableDomainObjectFilterer(study));
			fail("Should throw accessDeniedException");
		} catch(Exception ade){
			assertTrue(ade instanceof AccessDeniedException);
		}
		
	}
	

}
