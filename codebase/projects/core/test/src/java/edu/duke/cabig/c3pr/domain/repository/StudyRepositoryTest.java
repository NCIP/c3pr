package edu.duke.cabig.c3pr.domain.repository;

import java.util.ArrayList;

import org.easymock.classextension.EasyMock;

import edu.duke.cabig.c3pr.AbstractTestCase;
import edu.duke.cabig.c3pr.dao.HealthcareSiteDao;
import edu.duke.cabig.c3pr.dao.HealthcareSiteInvestigatorDao;
import edu.duke.cabig.c3pr.dao.InvestigatorDao;
import edu.duke.cabig.c3pr.dao.StudyDao;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.HealthcareSiteInvestigator;
import edu.duke.cabig.c3pr.domain.Investigator;
import edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyInvestigator;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.repository.impl.StudyRepositoryImpl;
import edu.duke.cabig.c3pr.utils.StudyCreationHelper;

public class StudyRepositoryTest extends AbstractTestCase {
	
	private StudyRepositoryImpl studyRepositoryImpl;
    private StudyDao studyDao;
    private HealthcareSiteDao healthcareSiteDao;
    private InvestigatorDao investigatorDao;
    private HealthcareSiteInvestigatorDao healthcareSiteInvestigatorDao;
    private StudyCreationHelper studyCreationHelper; 

    protected void setUp() throws Exception {
    	studyRepositoryImpl = new StudyRepositoryImpl();
    	studyDao = registerDaoMockFor(StudyDao.class);
    	healthcareSiteDao = registerDaoMockFor(HealthcareSiteDao.class);
    	investigatorDao = registerDaoMockFor(InvestigatorDao.class);
    	healthcareSiteInvestigatorDao = registerMockFor(HealthcareSiteInvestigatorDao.class);
    	studyCreationHelper = new StudyCreationHelper();
    	
    	studyRepositoryImpl.setStudyDao(studyDao);
    	studyRepositoryImpl.setHealthcareSiteDao(healthcareSiteDao);
    	studyRepositoryImpl.setInvestigatorDao(investigatorDao);
    	studyRepositoryImpl.setHealthcareInvestigatorDao(healthcareSiteInvestigatorDao);
    }

    public void testBuildAndSave() {
    	//building the study
    	Study study = studyCreationHelper.getLocalNonRandomizedTratmentWithArmStudy();
    	
    	StudySite organization = new StudySite();
    	HealthcareSite healthcareSite = buildHealthcareSite();
    	
    	Investigator investigator = buildInvestigator();
    	HealthcareSiteInvestigator healthcareSiteInvestigator =  buildHealthcareSiteInvestigator(investigator, healthcareSite);    	
    	StudyInvestigator sInv = buildStudyInvestigator(healthcareSiteInvestigator);
    	ArrayList <StudyInvestigator>sInvList = new ArrayList<StudyInvestigator>();
    	sInvList.add(sInv);
    	
    	organization.setHealthcareSite(healthcareSite);
    	organization.setStudyInvestigators(sInvList);    	
    	study.getStudyOrganizations().add(organization);
    	
    	OrganizationAssignedIdentifier organizationAssignedIdentifier = new OrganizationAssignedIdentifier();
    	organizationAssignedIdentifier.setHealthcareSite(healthcareSite);
    	organizationAssignedIdentifier.setValue("oai-001");
    	study.getOrganizationAssignedIdentifiers().add(organizationAssignedIdentifier);
    	
    	//list of mocks
        EasyMock.expect(healthcareSiteDao.getByNciInstituteCode("hcs-001")).andReturn(healthcareSite);
        EasyMock.expect(investigatorDao.getByNciInstituteCode("inv-001")).andReturn(investigator);
        EasyMock.expect(healthcareSiteInvestigatorDao.getSiteInvestigator(healthcareSite, investigator)).andReturn(healthcareSiteInvestigator);
        EasyMock.expect(healthcareSiteDao.getByNciInstituteCode("hcs-001")).andReturn(healthcareSite); 
        studyDao.save(study);
        replayMocks();
        
        try{
        	studyRepositoryImpl.buildAndSave(study);
        } catch(Exception e){
        	assertFalse("C3PRCodedException thrown", false);
        }
        verifyMocks();
    }
    
    public StudyInvestigator buildStudyInvestigator(HealthcareSiteInvestigator healthcareSiteInvestigator){
    	StudyInvestigator sInv = new StudyInvestigator();
    	sInv.setHealthcareSiteInvestigator(healthcareSiteInvestigator);
    	return sInv;
    }    
    
    public HealthcareSite buildHealthcareSite(){
    	HealthcareSite hcs = new HealthcareSite();
    	hcs.setNciInstituteCode("hcs-001");
    	return hcs;
    }
    
	public Investigator buildInvestigator(){
		Investigator investigator = new Investigator();
		investigator.setFirstName("Frank");
		investigator.setLastName("Hardy");
		investigator.setNciIdentifier("inv-001");
		return investigator;
	}
	
	public HealthcareSiteInvestigator buildHealthcareSiteInvestigator(Investigator inv, HealthcareSite hcs){
		HealthcareSiteInvestigator hcsInv = new HealthcareSiteInvestigator();
		hcsInv.setInvestigator(inv);
		hcsInv.setHealthcareSite(hcs);
		return hcsInv;
	}
       
  
	public StudyRepositoryImpl getStudyRepositoryImpl() {
		return studyRepositoryImpl;
	}

	public void setStudyRepositoryImpl(StudyRepositoryImpl studyRepositoryImpl) {
		this.studyRepositoryImpl = studyRepositoryImpl;
	}

	public StudyCreationHelper getStudyCreationHelper() {
		return studyCreationHelper;
	}

	public void setStudyCreationHelper(StudyCreationHelper studyCreationHelper) {
		this.studyCreationHelper = studyCreationHelper;
	}

	public HealthcareSiteDao getHealthcareSiteDao() {
		return healthcareSiteDao;
	}

	public void setHealthcareSiteDao(HealthcareSiteDao healthcareSiteDao) {
		this.healthcareSiteDao = healthcareSiteDao;
	}

	public HealthcareSiteInvestigatorDao getHealthcareSiteInvestigatorDao() {
		return healthcareSiteInvestigatorDao;
	}

	public void setHealthcareSiteInvestigatorDao(
			HealthcareSiteInvestigatorDao healthcareSiteInvestigatorDao) {
		this.healthcareSiteInvestigatorDao = healthcareSiteInvestigatorDao;
	}

	public InvestigatorDao getInvestigatorDao() {
		return investigatorDao;
	}

	public void setInvestigatoeDao(InvestigatorDao investigatorDao) {
		this.investigatorDao = investigatorDao;
	}

	public StudyDao getStudyDao() {
		return studyDao;
	}

	public void setStudyDao(StudyDao studyDao) {
		this.studyDao = studyDao;
	}

	public void setInvestigatorDao(InvestigatorDao investigatorDao) {
		this.investigatorDao = investigatorDao;
	}


}
