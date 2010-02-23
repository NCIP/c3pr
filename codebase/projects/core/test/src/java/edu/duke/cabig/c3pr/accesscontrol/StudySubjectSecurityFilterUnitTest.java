package edu.duke.cabig.c3pr.accesscontrol;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.acegisecurity.AccessDeniedException;
import org.acegisecurity.Authentication;
import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.userdetails.User;
import org.easymock.classextension.EasyMock;

import edu.duke.cabig.c3pr.AbstractTestCase;
import edu.duke.cabig.c3pr.constants.RoleTypes;
import edu.duke.cabig.c3pr.domain.ResearchStaff;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.repository.CSMUserRepository;

public class StudySubjectSecurityFilterUnitTest extends AbstractTestCase {
	
	private StudySubjectSecurityFilter studySubjectSecurityFilter;
	private CSMUserRepository csmUserRepository;
	private List<RoleTypes> rolesToExclude;
	private List<StudySubject> studySubjects;
	private CollectionFilterer collectionFilterer;
	private Authentication authentication;
	private ResearchStaff researchStaff;
	private StudySubject studySubject1;
	private StudySubject studySubject2;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		studySubjectSecurityFilter = new StudySubjectSecurityFilter();
		csmUserRepository = registerMockFor(CSMUserRepository.class);
		studySubjectSecurityFilter.setCsmUserRepository(csmUserRepository);
		rolesToExclude = new ArrayList<RoleTypes>();
		studySubjects = new ArrayList<StudySubject>();
		collectionFilterer = new CollectionFilterer(studySubjects);
		authentication = registerMockFor(Authentication.class);
		researchStaff = registerMockFor(ResearchStaff.class);
		studySubjectSecurityFilter.setRolesToExclude(rolesToExclude);
		studySubject1 = registerMockFor(StudySubject.class);
		studySubjects.add(studySubject1);
		studySubject2 = registerMockFor(StudySubject.class);
		studySubjects.add(studySubject2);
	}
	
	public void testFilterSuperAdmin(){
		expectSecurityUtilsIsSuperAdmin(true);
		replayMocks();
		Collection<Study> studiesRet = (Collection<Study>)studySubjectSecurityFilter.filter(authentication, "", collectionFilterer);
		assertEquals(2, studiesRet.size());
		verifyMocks();
	}
	
	public void testFilterSiteCoordinator(){
		rolesToExclude.add(RoleTypes.SITE_COORDINATOR);
		expectSecurityUtilsIsSuperAdmin(false);
		expectSecurityUtilsHasRole(RoleTypes.SITE_COORDINATOR);
		replayMocks();
		Collection<Study> studiesRet = (Collection<Study>)studySubjectSecurityFilter.filter(authentication, "", collectionFilterer);
		assertEquals(2, studiesRet.size());
		verifyMocks();
	}
	
	public void testFilterStudyCoordinator1(){
		rolesToExclude.add(RoleTypes.SITE_COORDINATOR);
		expectSecurityUtilsIsSuperAdmin(false);
		expectSecurityUtilsHasRole(RoleTypes.STUDY_COORDINATOR);
		expectSecurityUtilsGetUsername();
		EasyMock.expect(csmUserRepository.getUserByName("username")).andReturn(researchStaff);
		EasyMock.expect(studySubject1.isAssignedAndActivePersonnel(researchStaff)).andReturn(true);
		EasyMock.expect(studySubject2.isAssignedAndActivePersonnel(researchStaff)).andReturn(false);
		replayMocks();
		Collection<Study> studiesRet = (Collection<Study>)studySubjectSecurityFilter.filter(authentication, "", collectionFilterer);
		assertEquals(1, studiesRet.size());
		assertTrue(studiesRet.toArray()[0]==studySubject1);
		assertFalse(studiesRet.toArray()[0]==studySubject2);
		verifyMocks();
	}
	
	public void testFilterStudyCoordinator2(){
		rolesToExclude.add(RoleTypes.SITE_COORDINATOR);
		expectSecurityUtilsIsSuperAdmin(false);
		expectSecurityUtilsHasRole(RoleTypes.STUDY_COORDINATOR);
		expectSecurityUtilsGetUsername();
		EasyMock.expect(csmUserRepository.getUserByName("username")).andReturn(researchStaff);
		EasyMock.expect(studySubject1.isAssignedAndActivePersonnel(researchStaff)).andReturn(false);
		EasyMock.expect(studySubject2.isAssignedAndActivePersonnel(researchStaff)).andReturn(true);
		replayMocks();
		Collection<Study> studiesRet = (Collection<Study>)studySubjectSecurityFilter.filter(authentication, "", collectionFilterer);
		assertEquals(1, studiesRet.size());
		assertTrue(studiesRet.toArray()[0]==studySubject2);
		assertFalse(studiesRet.toArray()[0]==studySubject1);
		verifyMocks();
	}
	
	public void testFilterStudyCoordinator3(){
		rolesToExclude.add(RoleTypes.SITE_COORDINATOR);
		expectSecurityUtilsIsSuperAdmin(false);
		expectSecurityUtilsHasRole(RoleTypes.STUDY_COORDINATOR);
		expectSecurityUtilsGetUsername();
		EasyMock.expect(csmUserRepository.getUserByName("username")).andReturn(researchStaff);
		EasyMock.expect(studySubject1.isAssignedAndActivePersonnel(researchStaff)).andReturn(true);
		EasyMock.expect(studySubject2.isAssignedAndActivePersonnel(researchStaff)).andReturn(true);
		replayMocks();
		Collection<Study> studiesRet = (Collection<Study>)studySubjectSecurityFilter.filter(authentication, "", collectionFilterer);
		assertEquals(2, studiesRet.size());
		assertTrue(studiesRet.toArray()[0]==studySubject1);
		assertTrue(studiesRet.toArray()[1]==studySubject2);
		verifyMocks();
	}
	
	public void testFilterStudyCoordinator4(){
		rolesToExclude.add(RoleTypes.SITE_COORDINATOR);
		expectSecurityUtilsIsSuperAdmin(false);
		expectSecurityUtilsHasRole(RoleTypes.STUDY_COORDINATOR);
		expectSecurityUtilsGetUsername();
		EasyMock.expect(csmUserRepository.getUserByName("username")).andReturn(researchStaff);
		EasyMock.expect(studySubject1.isAssignedAndActivePersonnel(researchStaff)).andReturn(true);
		replayMocks();
		StudySubject studySubject = (StudySubject)studySubjectSecurityFilter.filter(authentication, "", new AbstractMutableDomainObjectFilterer(studySubject1));
		assertTrue(studySubject==studySubject1);
		verifyMocks();
	}
	
	public void testFilterStudyCoordinator5(){
		rolesToExclude.add(RoleTypes.SITE_COORDINATOR);
		expectSecurityUtilsIsSuperAdmin(false);
		expectSecurityUtilsHasRole(RoleTypes.STUDY_COORDINATOR);
		expectSecurityUtilsGetUsername();
		EasyMock.expect(csmUserRepository.getUserByName("username")).andReturn(researchStaff);
		EasyMock.expect(studySubject2.isAssignedAndActivePersonnel(researchStaff)).andReturn(false);
		replayMocks();
		try {
			studySubjectSecurityFilter.filter(authentication, "", new AbstractMutableDomainObjectFilterer(studySubject2));
			fail("Should have thrown exception");
		} catch (AccessDeniedException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			fail("Wrong Exception.");
		}finally{
			verifyMocks();
		}
	}
	
	private void expectSecurityUtilsIsSuperAdmin(boolean isAdmin){
		GrantedAuthority[] grantedAuthorities = getGrantedAuthorities(3);
		EasyMock.expect(authentication.getAuthorities()).andReturn(grantedAuthorities);
		EasyMock.expect(grantedAuthorities[0].getAuthority()).andReturn(RoleTypes.REGISTRAR.getCode());
		EasyMock.expect(grantedAuthorities[1].getAuthority()).andReturn(RoleTypes.STUDY_COORDINATOR.getCode());
		EasyMock.expect(grantedAuthorities[2].getAuthority()).andReturn(isAdmin?RoleTypes.C3PR_ADMIN.getCode():RoleTypes.SITE_COORDINATOR.getCode());
	}
	
	private void expectSecurityUtilsHasRole(RoleTypes roleTypes){
		GrantedAuthority[] grantedAuthorities = getGrantedAuthorities(1);
		EasyMock.expect(authentication.getAuthorities()).andReturn(grantedAuthorities);
		EasyMock.expect(grantedAuthorities[0].getAuthority()).andReturn(roleTypes.getCode());
	}
	
	private void expectSecurityUtilsGetUsername(){
		User user = registerMockFor(User.class);
		EasyMock.expect(authentication.getPrincipal()).andReturn(user);
		EasyMock.expect(user.getUsername()).andReturn("username");
	}

	private GrantedAuthority[] getGrantedAuthorities(int size){
		GrantedAuthority[] grantedAuthorities = new GrantedAuthority[size];
		for(int i=0 ; i<size ; i++){
			grantedAuthorities[i] = registerMockFor(GrantedAuthority.class);
		}
		return grantedAuthorities;
	}
	
}
