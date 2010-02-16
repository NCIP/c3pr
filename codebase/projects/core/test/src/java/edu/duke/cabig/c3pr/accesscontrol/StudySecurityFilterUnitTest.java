package edu.duke.cabig.c3pr.accesscontrol;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.acegisecurity.Authentication;
import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.userdetails.User;
import org.easymock.classextension.EasyMock;

import edu.duke.cabig.c3pr.AbstractTestCase;
import edu.duke.cabig.c3pr.constants.RoleTypes;
import edu.duke.cabig.c3pr.domain.ResearchStaff;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.repository.CSMUserRepository;
import edu.duke.cabig.c3pr.utils.StudyCreationHelper;

public class StudySecurityFilterUnitTest extends AbstractTestCase {
	
	private StudySecurityFilter studySecurityFilter;
	private CSMUserRepository csmUserRepository;
	List<RoleTypes> rolesToExclude;
	List<Study> studies;
	CollectionFilterer collectionFilterer;
	Authentication authentication;
	StudyCreationHelper studyCreationHelper;
	ResearchStaff researchStaff;
	Study study1;
	Study study2;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		studySecurityFilter = new StudySecurityFilter();
		csmUserRepository = registerMockFor(CSMUserRepository.class);
		studySecurityFilter.setCsmUserRepository(csmUserRepository);
		rolesToExclude = new ArrayList<RoleTypes>();
		studies = new ArrayList<Study>();
		collectionFilterer = new CollectionFilterer(studies);
		authentication = registerMockFor(Authentication.class);
		studyCreationHelper = new StudyCreationHelper();
		researchStaff = registerMockFor(ResearchStaff.class);
		studySecurityFilter.setRolesToExclude(rolesToExclude);
		study1 = registerMockFor(Study.class);
		studies.add(study1);
		study2 = registerMockFor(Study.class);
		studies.add(study2);
	}
	
	public void testFilterSuperAdmin(){
		expectSecurityUtilsIsSuperAdmin(true);
		replayMocks();
		Collection<Study> studiesRet = (Collection<Study>)studySecurityFilter.filter(authentication, "", collectionFilterer);
		assertEquals(2, studiesRet.size());
		verifyMocks();
	}
	
	public void testFilterSiteCoordinator(){
		rolesToExclude.add(RoleTypes.SITE_COORDINATOR);
		expectSecurityUtilsIsSuperAdmin(false);
		expectSecurityUtilsHasRole(RoleTypes.SITE_COORDINATOR);
		replayMocks();
		Collection<Study> studiesRet = (Collection<Study>)studySecurityFilter.filter(authentication, "", collectionFilterer);
		assertEquals(2, studiesRet.size());
		verifyMocks();
	}
	
	public void testFilterStudyCoordinator1(){
		rolesToExclude.add(RoleTypes.SITE_COORDINATOR);
		expectSecurityUtilsIsSuperAdmin(false);
		expectSecurityUtilsHasRole(RoleTypes.STUDY_COORDINATOR);
		expectSecurityUtilsGetUsername();
		EasyMock.expect(csmUserRepository.getUserByName("username")).andReturn(researchStaff);
		EasyMock.expect(study1.isAssignedAndActivePersonnel(researchStaff)).andReturn(true);
		EasyMock.expect(study2.isAssignedAndActivePersonnel(researchStaff)).andReturn(false);
		replayMocks();
		Collection<Study> studiesRet = (Collection<Study>)studySecurityFilter.filter(authentication, "", collectionFilterer);
		assertEquals(1, studiesRet.size());
		assertTrue(studiesRet.toArray()[0]==study1);
		assertFalse(studiesRet.toArray()[0]==study2);
		verifyMocks();
	}
	
	public void testFilterStudyCoordinator2(){
		rolesToExclude.add(RoleTypes.SITE_COORDINATOR);
		expectSecurityUtilsIsSuperAdmin(false);
		expectSecurityUtilsHasRole(RoleTypes.STUDY_COORDINATOR);
		expectSecurityUtilsGetUsername();
		EasyMock.expect(csmUserRepository.getUserByName("username")).andReturn(researchStaff);
		EasyMock.expect(study1.isAssignedAndActivePersonnel(researchStaff)).andReturn(false);
		EasyMock.expect(study2.isAssignedAndActivePersonnel(researchStaff)).andReturn(true);
		replayMocks();
		Collection<Study> studiesRet = (Collection<Study>)studySecurityFilter.filter(authentication, "", collectionFilterer);
		assertEquals(1, studiesRet.size());
		assertTrue(studiesRet.toArray()[0]==study2);
		assertFalse(studiesRet.toArray()[0]==study1);
		verifyMocks();
	}
	
	public void testFilterStudyCoordinator3(){
		rolesToExclude.add(RoleTypes.SITE_COORDINATOR);
		expectSecurityUtilsIsSuperAdmin(false);
		expectSecurityUtilsHasRole(RoleTypes.STUDY_COORDINATOR);
		expectSecurityUtilsGetUsername();
		EasyMock.expect(csmUserRepository.getUserByName("username")).andReturn(researchStaff);
		EasyMock.expect(study1.isAssignedAndActivePersonnel(researchStaff)).andReturn(true);
		EasyMock.expect(study2.isAssignedAndActivePersonnel(researchStaff)).andReturn(true);
		replayMocks();
		Collection<Study> studiesRet = (Collection<Study>)studySecurityFilter.filter(authentication, "", collectionFilterer);
		assertEquals(2, studiesRet.size());
		assertTrue(studiesRet.toArray()[0]==study1);
		assertTrue(studiesRet.toArray()[1]==study2);
		verifyMocks();
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
