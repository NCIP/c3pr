package edu.duke.cabig.c3pr.dao;

import static edu.nwu.bioinformatics.commons.testing.CoreTestCase.assertContains;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.duke.cabig.c3pr.domain.Address;
import edu.duke.cabig.c3pr.domain.Arm;
import edu.duke.cabig.c3pr.domain.Epoch;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.HealthcareSiteInvestigator;
import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.Investigator;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyInvestigator;
import edu.duke.cabig.c3pr.domain.StudyParticipantAssignment;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.utils.DaoTestCase;

/**
 * JUnit Tests for StudyDao
 * @author Priyatam
 * @testType unit
 */
public class StudyDaoTest extends DaoTestCase {
    private StudyDao dao = (StudyDao) getApplicationContext().getBean("studyDao");
    private HealthcareSiteDao healthcareSitedao = (HealthcareSiteDao) getApplicationContext()
    	.getBean("healthcareSiteDao");
    private HealthcareSiteInvestigatorDao hcsidao = (HealthcareSiteInvestigatorDao) getApplicationContext()
		.getBean("healthcareSiteInvestigatorDao");
    private InvestigatorDao investigatorDao = (InvestigatorDao) getApplicationContext()
		.getBean("investigatorDao");


    /**
	 * Test for loading a Study by Id 
	 * @throws Exception
	 */
    public void testGetById() throws Exception {
     	Study study = dao.getById(1000);
        assertNotNull("Study 1000 not found", study);
        assertEquals("Wrong name", "precis_text", study.getPrecisText());
    }

    /**
	 * Test for loading all Studies 
	 * @throws Exception
	 */
    public void testGetAll() throws Exception {
        List<Study> actual = dao.getAll();
        assertEquals(3, actual.size());
        List<Integer> ids = collectIds(actual);
        assertContains("Wrong study found", ids, 1000);
        assertContains("Wrong study found", ids, 1001);
        assertContains("Wrong study found", ids, 1002);
    }

    /**
     * Test Saving of a Study with all associations present
     * @throws Exception
     */
    public void testSaveNewStudy() throws Exception {
        Integer savedId;
        {
            Study study = new Study();
            study.setPrecisText("New study");            
            study.setShortTitleText("ShortTitleText");
            study.setLongTitleText("LongTitleText");
            study.setPhaseCode("PhaseCode");
            study.setSponsorCode("SponsorCode");
            study.setStatus("Status");
            study.setTargetAccrualNumber(150);
            study.setType("Type");
            study.setMultiInstitutionIndicator(true);
            dao.save(study);
            savedId = study.getId();
            assertNotNull("The saved study didn't get an id", savedId);            
        }

        interruptSession();
        {
            Study loaded = dao.getById(savedId);
            assertNotNull("GridId not updated", loaded.getGridId());
            assertNotNull("Could not reload study with id " + savedId, loaded);
            assertEquals("Wrong name", "New study", loaded.getPrecisText());
        }
    }
    

    /**
     * Test Saving of a Study with all associations present
     * @throws Exception
     */
    public void testSaveNewStudyWithAssociations() throws Exception {
        Integer savedId;
        {
            Study study = new Study();
            study.setPrecisText("New study");            
            study.setShortTitleText("ShortTitleText");
            study.setLongTitleText("LongTitleText");
            study.setPhaseCode("PhaseCode");
            study.setSponsorCode("SponsorCode");
            study.setStatus("Status");
            study.setTargetAccrualNumber(150);
            study.setType("Type");
            study.setMultiInstitutionIndicator(new Boolean(true));
            
            createDefaultStudyWithDesign(study);
            
            dao.save(study);
            savedId = study.getId();
            assertNotNull("The saved study didn't get an id", savedId);            
        }

        interruptSession();
        {
            Study loaded = dao.getById(savedId);            
            assertNotNull("Could not reload study with id " + savedId, loaded);
            assertNotNull("GridId not updated", loaded.getGridId());            
            assertEquals("Wrong name", "New study", loaded.getPrecisText());
        }
    }
    
    /**
     * Test for retrieving all Epochs associated with this Study 
     * @throws Exception
     */
    public void testGetEpochs() throws Exception {
    	Study study = dao.getById(1000);
    	List<Epoch> epochs = study.getEpochs();
        assertEquals("Wrong number of Epochs", 2, epochs.size());
        List<Integer> ids = collectIds(epochs);

        assertContains("Missing expected Epoch", ids, 1000);
        assertContains("Missing expected Epoch", ids, 1001);        
    }

    /**
     * Test for retrieving all Arms associated with this Studies' epochs 
     * @throws Exception
     */
    public void testGetArms() throws Exception {
        List<Arm> actual = dao.getArmsForStudy(1000);
       
        assertEquals("Wrong number of assigments", 4, actual.size());
        List<Integer> ids = collectIds(actual);

        assertContains("Missing expected Arm", ids, 1000);
        assertContains("Missing expected Arm", ids, 1001);
        assertContains("Missing expected Arm", ids, 1002);
        assertContains("Missing expected Arm", ids, 1003);      
    }
    
    /**
     * Test for Study Paticipant Assignments for a given Study 
     * @throws Exception
     */
    public void testGetStudyParticipantAssignmentsForStudy() throws Exception {
    	List<StudyParticipantAssignment> spa = dao.getStudyParticipantAssignmentsForStudy(34);
    	assertEquals(2, spa.size());
        List<Integer> ids = collectIds(spa);
        assertContains("Wrong study found", ids, 1000);          	    
    }
    /**
     * Test for searching Studies without wildcards 
     * @throws Exception
     */
    public void testSearchStudySimple()
    {    
    	  Study studySearchCriteria = new Study();
    	  studySearchCriteria.setShortTitleText("short_title_text");
          List<Study> results = dao.searchByExample(studySearchCriteria);
          assertEquals("Wrong number of Studies", 2, results.size());
          assertEquals("short_title_text", results.get(0).getShortTitleText());
          assertEquals("short_title_text", results.get(1).getShortTitleText());      
    }
    
    /**
     * Test for searching Studies using wildcards 
     * @throws Exception
     */   
    public void testSearchStudyByWildCards() throws Exception
    {    
    	  Study studySearchCriteria = new Study();
    	  studySearchCriteria.setShortTitleText("ti%e");
          List<Study> results = dao.searchByExample(studySearchCriteria, true);
          assertEquals("Wrong number of Studies", 3, results.size());          
    }
    
    private Study createDefaultStudyWithDesign(Study study)
	{
    	// Investigators
		Investigator inv = new Investigator();
		inv.setFirstName("Investigator first name");
		    	
    	investigatorDao.save(inv);
    	
    	 interruptSession();
        {
    	   System.out.println("id *****************"+inv.getId());
           Investigator loaded = investigatorDao.getById(inv.getId());
           assertNotNull("Could not reload study with id " + inv.getId(), loaded);
        }
    	
		study.addEpoch(Epoch.create("Screening"));
		study.addEpoch(Epoch.create("Treatment", "Arm A", "Arm B", "Arm C"));
		study.addEpoch(Epoch.create("Follow up"));
          		
		// healthcare site
		HealthcareSite healthcaresite = new HealthcareSite();
		Address address = new Address();
		address.setCity("Reston");
		address.setCountryCode("USA");
		address.setPostalCode("20191");
		address.setStateCode("VA");
		address.setStreetAddress("12359 Sunrise Valley Dr");
		healthcaresite.setAddress(address);	
		healthcaresite.setName("duke healthcare");	
		healthcaresite.setDescriptionText("duke healthcare");
		healthcaresite.setNciInstituteCode("Nci duke");
		
		healthcareSitedao.save(healthcaresite);
		System.out.println("hc site id ************"+healthcaresite.getId());
		
		//Study Site
		StudySite studySite = new StudySite();
		study.addStudySite(studySite);		
		studySite.setSite(healthcaresite); //
		studySite.setStartDate(new Date());
		studySite.setIrbApprovalDate(new Date());
		studySite.setRoleCode("role");
		studySite.setStatusCode("active");
					
		// HCSI
		HealthcareSiteInvestigator hcsi = new HealthcareSiteInvestigator();			
		inv.addHealthcareSiteInvestigator(hcsi);		
		healthcaresite.addHealthcareSiteInvestigator(hcsi);		
		hcsidao.save(hcsi);		
		System.out.println("hcsi id ************"+hcsi.getId());			
				
		// Study Investigator
		StudyInvestigator studyInvestigator = new StudyInvestigator();
		studyInvestigator.setRoleCode("role");
		studyInvestigator.setStartDate(new Date());		
		studySite.addStudyInvestigator(studyInvestigator);						
			
		hcsi.addStudyInvestigator(studyInvestigator);						
		
		//Identifiers
		List<Identifier> identifiers = new ArrayList<Identifier>();
		Identifier id = new Identifier();
		id.setPrimaryIndicator(true);
		id.setSource("nci");
		id.setValue("123456");
		id.setType("local");		
		identifiers.add(id);			
		study.setIdentifiers(identifiers);
		
		return study;
	}	
   
    private List<HealthcareSite> getHealthcareSites()
	{
    	return healthcareSitedao.getAll();  	
	}
       
}