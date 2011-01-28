package edu.duke.cabig.c3pr.accesscontrol;

import java.util.List;

import org.acegisecurity.AccessDeniedException;
import org.acegisecurity.Authentication;

import edu.duke.cabig.c3pr.dao.StudySubjectDao;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.utils.AuthenticationProviderDaoTestCase;

public class StudySubjectSecurityFilterTest extends AuthenticationProviderDaoTestCase {
	
	private StudySubjectSecurityFilter studySubjectSecurityFilter;
	private StudySubjectDao studySubjectDao;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		studySubjectSecurityFilter = new StudySubjectSecurityFilter();
		studySubjectDao = (StudySubjectDao) getApplicationContext().getBean("studySubjectDao");
	}
	
	@SuppressWarnings("unchecked")
	public void testFilterStudyCreator(){
		Authentication authentication = setUserInSecurityContext("study_creator");
		List<StudySubject> studySubjects = studySubjectDao.getAll();
		
		List<StudySubject> filteredStudySubjects = (List<StudySubject>)studySubjectSecurityFilter.filter(authentication, "", new CollectionFilterer(studySubjects));
		assertEquals(0, filteredStudySubjects.size());
	}
	
	@SuppressWarnings("unchecked")
	public void testFilterStudyTeamAdministrator(){
		Authentication authentication = setUserInSecurityContext("study_team_administrator");
		List<StudySubject> studySubjects = studySubjectDao.getAll();
		
		List<StudySubject> filteredStudies = (List<StudySubject>)studySubjectSecurityFilter.filter(authentication, "", new CollectionFilterer(studySubjects));
		assertEquals(0, filteredStudies.size());
	}
	
	@SuppressWarnings("unchecked")
	public void testFilterStudySiteParticipationAdministrator(){
		Authentication authentication = setUserInSecurityContext("study_site_participation_administrator");
		List<StudySubject> studySubjects = studySubjectDao.getAll();
		
		List<StudySubject> filteredStudies = (List<StudySubject>)studySubjectSecurityFilter.filter(authentication, "", new CollectionFilterer(studySubjects));
		assertEquals(0, filteredStudies.size());
	}
	
	@SuppressWarnings("unchecked")
	public void testFilterSystemAdministrator(){
		Authentication authentication = setUserInSecurityContext("system_administrator");
		List<StudySubject> studySubjects = studySubjectDao.getAll();
		
		List<StudySubject> filteredStudies = (List<StudySubject>)studySubjectSecurityFilter.filter(authentication, "", new CollectionFilterer(studySubjects));
		assertEquals(0, filteredStudies.size());
	}
	
	//	registrar-allStudy has access to all studies at both duke and wake
	public void testFilterStudyCreatorNonCollection(){
		Authentication authentication = setUserInSecurityContext("registrar-allStudy");
		StudySubject studySubject = studySubjectDao.getById(1000);
		assertNotNull(studySubjectSecurityFilter.filter(authentication, "", new AbstractMutableDomainObjectFilterer(studySubject)));
		
		studySubject = studySubjectDao.getById(1001);
		try{
			studySubjectSecurityFilter.filter(authentication, "", new AbstractMutableDomainObjectFilterer(studySubject));
			fail("Should throw accessDeniedException");
		} catch(Exception ade){
			assertTrue("wrong exception thrown", ade instanceof AccessDeniedException);
		}
		
		
		studySubject = studySubjectDao.getById(1004);
		assertNotNull(studySubjectSecurityFilter.filter(authentication, "", new AbstractMutableDomainObjectFilterer(studySubject)));
	}
	
	//	registrar has access to duke and studies 1000 and 1001
	@SuppressWarnings("unchecked")
	public void testFilterRegistrar(){
		Authentication authentication = setUserInSecurityContext("registrar");
		List<StudySubject> studies = studySubjectDao.getAll();
		
		List<StudySubject> filteredStudies = (List<StudySubject>)studySubjectSecurityFilter.filter(authentication, "", new CollectionFilterer(studies));
		assertEquals(2, filteredStudies.size());
		assertTrue(containsElementWithId(filteredStudies, 1000));
		assertTrue(containsElementWithId(filteredStudies, 1002));
		assertFalse(containsElementWithId(filteredStudies, 1003));
	}

	// the reg qa mgr has access to duke site only
	@SuppressWarnings("unchecked")
	public void testFilterRegistrarAllStudy(){
		Authentication authentication = setUserInSecurityContext("registration_qa_manager");
		List<StudySubject> studies = studySubjectDao.getAll();
		
		List<StudySubject> filteredStudies = (List<StudySubject>)studySubjectSecurityFilter.filter(authentication, "", new CollectionFilterer(studies));
		assertEquals(2, filteredStudies.size());
		assertTrue(containsElementWithId(filteredStudies, 1000));
		assertTrue(containsElementWithId(filteredStudies, 1002));
		assertFalse(containsElementWithId(filteredStudies, 1001));
	}
	
	//	registrar has access to duke and studies 1000 and 1001
	public void testFilterRegistrarNonCollection(){
		Authentication authentication = setUserInSecurityContext("registrar");
		StudySubject study = studySubjectDao.getById(1000);
		assertNotNull(studySubjectSecurityFilter.filter(authentication, "", new AbstractMutableDomainObjectFilterer(study)));
		
		study = studySubjectDao.getById(1004);
		try{
			studySubjectSecurityFilter.filter(authentication, "", new AbstractMutableDomainObjectFilterer(study));
			fail("Should throw accessDeniedException");
		} catch(Exception ade){
			assertTrue(ade instanceof AccessDeniedException);
		}
	}

	
}
