package edu.duke.cabig.c3pr.accesscontrol;

import java.util.ArrayList;
import java.util.List;

import org.acegisecurity.AccessDeniedException;
import org.acegisecurity.Authentication;

import edu.duke.cabig.c3pr.constants.RoleTypes;
import edu.duke.cabig.c3pr.dao.StudyDao;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.repository.CSMUserRepository;
import edu.duke.cabig.c3pr.utils.DaoTestCase;
import edu.duke.cabig.c3pr.utils.SecurityContextTestUtils;

/**
 * JUnit Tests for StudyDao.
 *
 * @author Kruttik Aggarwal
 * @testType unit
 */

public class StudySecurityFilterTest extends DaoTestCase {

	private CSMUserRepository csmUserRepository;
	
	private List<RoleTypes> rolesToExclude = new ArrayList<RoleTypes>();
	
	private StudySecurityFilter studySecurityFilter;
	
	private StudyDao studyDao;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		csmUserRepository = (CSMUserRepository) getApplicationContext().getBean("csmUserRepository");
		rolesToExclude.add(RoleTypes.SITE_COORDINATOR);
		studySecurityFilter = new StudySecurityFilter();
		studySecurityFilter.setCsmUserRepository(csmUserRepository);
		studySecurityFilter.setRolesToExclude(rolesToExclude);
		studyDao = (StudyDao) getApplicationContext().getBean("studyDao");
	}
    
	public void testFilterSuperAdmin(){
		Authentication authentication = SecurityContextTestUtils.setUser("admin",RoleTypes.C3PR_ADMIN);
		List<Study> studies = studyDao.getAll();
		
		List<Study> filteredStudies = (List<Study>)studySecurityFilter.filter(authentication, "", new CollectionFilterer(studies));
		assertEquals(3, filteredStudies.size());
	}
	
	public void testFilterSiteCoordinator(){
		Authentication authentication = SecurityContextTestUtils.setUser("site_coordinator",RoleTypes.SITE_COORDINATOR);
		List<Study> studies = studyDao.getAll();
		
		List<Study> filteredStudies = (List<Study>)studySecurityFilter.filter(authentication, "", new CollectionFilterer(studies));
		assertEquals(3, filteredStudies.size());
	}
	
	public void testFilterStudyCoordinator1(){
		Authentication authentication = SecurityContextTestUtils.setUser("hcs_study_co1",RoleTypes.STUDY_COORDINATOR);
		List<Study> studies = studyDao.getAll();
		
		List<Study> filteredStudies = (List<Study>)studySecurityFilter.filter(authentication, "", new CollectionFilterer(studies));
		assertEquals(2, filteredStudies.size());
		assertEquals(1000, filteredStudies.get(0).getId().intValue());
		assertEquals(1002, filteredStudies.get(1).getId().intValue());
		
		Study study = studyDao.getById(1000);
		assertNotNull(studySecurityFilter.filter(authentication, "", new AbstractMutableDomainObjectFilterer(study)));
		
		study = studyDao.getById(1001);
		try {
			studySecurityFilter.filter(authentication, "", new AbstractMutableDomainObjectFilterer(study));
			fail("Should have thrown exception");
		} catch (AccessDeniedException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			fail("Wrong Exception.");
		}
		
		study = studyDao.getById(1002);
		assertNotNull(studySecurityFilter.filter(authentication, "", new AbstractMutableDomainObjectFilterer(study)));
	}
	
	public void testFilterStudyCoordinator2(){
		Authentication authentication = SecurityContextTestUtils.setUser("hcs_study_co2",RoleTypes.STUDY_COORDINATOR);
		List<Study> studies = studyDao.getAll();
		
		List<Study> filteredStudies = (List<Study>)studySecurityFilter.filter(authentication, "", new CollectionFilterer(studies));
		assertEquals(2, filteredStudies.size());
		assertEquals(1002, filteredStudies.get(0).getId().intValue());
		assertEquals(1001, filteredStudies.get(1).getId().intValue());
		
		Study study = studyDao.getById(1001);
		assertNotNull(studySecurityFilter.filter(authentication, "", new AbstractMutableDomainObjectFilterer(study)));
		
		study = studyDao.getById(1000);
		try {
			studySecurityFilter.filter(authentication, "", new AbstractMutableDomainObjectFilterer(study));
			fail("Should have thrown exception");
		} catch (AccessDeniedException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			fail("Wrong Exception.");
		}
		
		study = studyDao.getById(1002);
		assertNotNull(studySecurityFilter.filter(authentication, "", new AbstractMutableDomainObjectFilterer(study)));
	}
	
	public void testFilterStudyCoordinator3(){
		Authentication authentication = SecurityContextTestUtils.setUser("hcs_study_co3",RoleTypes.STUDY_COORDINATOR);
		List<Study> studies = studyDao.getAll();
		
		List<Study> filteredStudies = (List<Study>)studySecurityFilter.filter(authentication, "", new CollectionFilterer(studies));
		assertEquals(0, filteredStudies.size());
		
		Study study = studyDao.getById(1000);
		try {
			studySecurityFilter.filter(authentication, "", new AbstractMutableDomainObjectFilterer(study));
			fail("Should have thrown exception");
		} catch (AccessDeniedException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			fail("Wrong Exception.");
		}
		
		study = studyDao.getById(1001);
		try {
			studySecurityFilter.filter(authentication, "", new AbstractMutableDomainObjectFilterer(study));
			fail("Should have thrown exception");
		} catch (AccessDeniedException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			fail("Wrong Exception.");
		}
		
		study = studyDao.getById(1002);
		try {
			studySecurityFilter.filter(authentication, "", new AbstractMutableDomainObjectFilterer(study));
			fail("Should have thrown exception");
		} catch (AccessDeniedException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			fail("Wrong Exception.");
		}
	}
	
	public void testFilterRegistrar1(){
		Authentication authentication = SecurityContextTestUtils.setUser("hcs_registrar1",RoleTypes.REGISTRAR);
		List<Study> studies = studyDao.getAll();
		
		List<Study> filteredStudies = (List<Study>)studySecurityFilter.filter(authentication, "", new CollectionFilterer(studies));
		assertEquals(2, filteredStudies.size());
		assertEquals(1002, filteredStudies.get(0).getId().intValue());
		assertEquals(1001, filteredStudies.get(1).getId().intValue());
		
		Study study = studyDao.getById(1001);
		assertNotNull(studySecurityFilter.filter(authentication, "", new AbstractMutableDomainObjectFilterer(study)));
		
		study = studyDao.getById(1000);
		try {
			studySecurityFilter.filter(authentication, "", new AbstractMutableDomainObjectFilterer(study));
			fail("Should have thrown exception");
		} catch (AccessDeniedException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			fail("Wrong Exception.");
		}
		
		study = studyDao.getById(1002);
		assertNotNull(studySecurityFilter.filter(authentication, "", new AbstractMutableDomainObjectFilterer(study)));
	}
	
	public void testFilterRegistrar2(){
		Authentication authentication = SecurityContextTestUtils.setUser("hcs_registrar2",RoleTypes.REGISTRAR);
		List<Study> studies = studyDao.getAll();
		
		List<Study> filteredStudies = (List<Study>)studySecurityFilter.filter(authentication, "", new CollectionFilterer(studies));
		assertEquals(2, filteredStudies.size());
		assertEquals(1000, filteredStudies.get(0).getId().intValue());
		assertEquals(1001, filteredStudies.get(1).getId().intValue());
		
		Study study = studyDao.getById(1000);
		assertNotNull(studySecurityFilter.filter(authentication, "", new AbstractMutableDomainObjectFilterer(study)));
		
		study = studyDao.getById(1002);
		try {
			studySecurityFilter.filter(authentication, "", new AbstractMutableDomainObjectFilterer(study));
			fail("Should have thrown exception");
		} catch (AccessDeniedException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			fail("Wrong Exception.");
		}
		
		study = studyDao.getById(1001);
		assertNotNull(studySecurityFilter.filter(authentication, "", new AbstractMutableDomainObjectFilterer(study)));
	}
	
	public void testFilterRegistrar3(){
		Authentication authentication = SecurityContextTestUtils.setUser("hcs_registrar3",RoleTypes.REGISTRAR);
		List<Study> studies = studyDao.getAll();
		
		List<Study> filteredStudies = (List<Study>)studySecurityFilter.filter(authentication, "", new CollectionFilterer(studies));
		assertEquals(0, filteredStudies.size());
		
		Study study = studyDao.getById(1000);
		try {
			studySecurityFilter.filter(authentication, "", new AbstractMutableDomainObjectFilterer(study));
			fail("Should have thrown exception");
		} catch (AccessDeniedException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			fail("Wrong Exception.");
		}
		
		study = studyDao.getById(1001);
		try {
			studySecurityFilter.filter(authentication, "", new AbstractMutableDomainObjectFilterer(study));
			fail("Should have thrown exception");
		} catch (AccessDeniedException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			fail("Wrong Exception.");
		}
		
		study = studyDao.getById(1002);
		try {
			studySecurityFilter.filter(authentication, "", new AbstractMutableDomainObjectFilterer(study));
			fail("Should have thrown exception");
		} catch (AccessDeniedException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			fail("Wrong Exception.");
		}
	}
}
