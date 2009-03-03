package edu.duke.cabig.c3pr.dao;

import static edu.duke.cabig.c3pr.C3PRUseCase.CREATE_PARTICIPANT;
import static edu.duke.cabig.c3pr.C3PRUseCase.SEARCH_SUBJECT;
import static edu.duke.cabig.c3pr.C3PRUseCase.UPDATE_SUBJECT;
import static edu.duke.cabig.c3pr.C3PRUseCase.VERIFY_SUBJECT;
import static edu.nwu.bioinformatics.commons.testing.CoreTestCase.assertContains;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.duke.cabig.c3pr.C3PRUseCases;
import edu.duke.cabig.c3pr.domain.Address;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.SystemAssignedIdentifier;
import edu.duke.cabig.c3pr.utils.ContextDaoTestCase;

/**
 * JUnit Tests for ParticipantDao
 * 
 * @author Priyatam
 * @testType Integration
 */
@C3PRUseCases( { CREATE_PARTICIPANT, UPDATE_SUBJECT, VERIFY_SUBJECT, SEARCH_SUBJECT })
public class ParticipantDaoTest extends ContextDaoTestCase<ParticipantDao> {
    private ParticipantDao participantDao;

    private HealthcareSiteDao healthcareSiteDao;
    private OrganizationDao organizationDao;

    public ParticipantDaoTest() {
    	participantDao = (ParticipantDao) getApplicationContext().getBean("participantDao");
    	healthcareSiteDao = (HealthcareSiteDao) getApplicationContext().getBean("healthcareSiteDao");
    	organizationDao = (OrganizationDao) getApplicationContext().getBean("organizationDao");
	}
    
    /**
	 * Test domain class.
	 */
	public void testDomainClass() {
		assertEquals("Wrong Domain Class", Participant.class, getDao().domainClass());
	}
    
    /**
     * Test for loading a Participant by Id
     * 
     * @throws Exception
     */
    public void testGetById() throws Exception {
        Participant participant = participantDao.getById(1000);
        assertNotNull("Participant 1 not found", participant);
        assertEquals("Wrong last name", "Clooney", participant.getLastName());
    }
    
    /**
     * Test for loading a Participant by Id and ensuring its identifiers are initialized
     * 
     * @throws Exception
     */
    public void testGetByIdWithIdentifiers() throws Exception {
        Participant participant = participantDao.getById(1000, true);
        assertNotNull("Participant 1 not found", participant);
        assertEquals("Wrong last name", "Clooney", participant.getLastName());
        try{
        	participant.getIdentifiers().get(0);
        }catch(Exception e){
        	fail("Identifiers were not initialized");
        }
    }

    /**
     * Test for loading all Participants
     * 
     * @throws Exception
     */
    public void testGetAll() throws Exception {
        List<Participant> actual = participantDao.getAll();
        assertEquals(4, actual.size());
        List<Integer> ids = collectIds(actual);
        assertContains("Wrong Participant found", ids, 1000);
        assertContains("Wrong Participant found", ids, 1001);
        assertContains("Wrong Participant found", ids, 1002);
    }

    
    /**
     * Test get subject identifiers with mrn.
     */
    public void testGetSubjectIdentifiersWithMRN(){
    	HealthcareSite healthcareSite = healthcareSiteDao.getById(1000);
    	
    	List<OrganizationAssignedIdentifier> oaiList = getDao().getSubjectIdentifiersWithMRN("mrn_value", healthcareSite);
    	assertEquals(1, oaiList.size());
    	assertEquals(oaiList.get(0).getValue(), "mrn_value");
    	assertEquals(oaiList.get(0).getHealthcareSite(), healthcareSite);
    }
    
    
    /**
     * Test for retrieving all Participant Assignments associated with this Participant
     * 
     * @throws Exception
     */
    public void testGetStudySubjects() throws Exception {
        Participant participant = participantDao.getById(1000);
        List<StudySubject> studyPartIds = participant.getStudySubjects();
        assertEquals("Wrong number of Study Participant Identifiers", 2, studyPartIds.size());
        List<Integer> ids = collectIds(studyPartIds);

        assertContains("Missing expected Study Participant Identifier", ids, 1000);
        assertContains("Missing expected Study Participant Identifier", ids, 1001);
    }

   
    /**
     * Test search by example for participant without wildcard.
     * 
     * @throws Exception the exception
     */
    public void testSearchByExampleForParticipantWithoutWildcard() throws Exception {
        Participant searchCriteria = new Participant();
        searchCriteria.setLastName("Douglas");
        List<Participant> results = participantDao.searchByExample(searchCriteria, false);
        assertEquals("Wrong number of Participants", 1, results.size());
    }

   
    /**
     * Test search by example for participant with wildcard.
     * 
     * @throws Exception the exception
     */
    public void testSearchByExampleForParticipantWithWildcard() throws Exception {
        Participant searchCriteria = new Participant();
        searchCriteria.setLastName("Clo%ey");
        List<Participant> results = participantDao.searchByExample(searchCriteria);
        assertEquals("Wrong number of Participants", 1, results.size());
    }
    
    /**
     * Test search by example with identifier.
     */
    public void testSearchByExampleWithIdentifier() {
    	Participant participant = new Participant();
    	SystemAssignedIdentifier systemAssignedIdentifier = new SystemAssignedIdentifier();
    	systemAssignedIdentifier.setType("local");
    	systemAssignedIdentifier.setValue("nci");
    	
    	participant.getIdentifiers().add(systemAssignedIdentifier);
    	
    	List<Participant> participantList = getDao().searchByExample(participant, true);
    	
    	assertEquals(1, participantList.size());
    	assertEquals("Rudolph", participantList.get(0).getFirstName());
	}
    
    /**
     * Test search by identifier.
     */
    public void testSearchByIdentifier() {
    	List<Participant> participantList = getDao().searchByIdentifier(1001);
    	assertEquals(1, participantList.size());
    	assertEquals("Rudolph", participantList.get(0).getFirstName());
	}

    
    /**
     * Test search by subnames by first name.
     */
    public void testSearchBySubnamesByFirstName() {
		String[] namesArray = new String[] {"Rudo"};
    	List<Participant> participantList = getDao().getBySubnames(namesArray, 6);
    	
    	assertEquals(1, participantList.size());
    	assertEquals("Rudolph", participantList.get(0).getFirstName());
	}
    
    /**
     * Test search by subnames by last name.
     */
    public void testSearchBySubnamesByLastName() {
		String[] namesArray = new String[] {"mez"};
    	List<Participant> participantList = getDao().getBySubnames(namesArray, 0);
    	
    	assertEquals(1, participantList.size());
    	assertEquals("Andrez", participantList.get(0).getFirstName());
	}
    
    /**
     * Test search by subnames by identifier.
     
    public void testSearchBySubnamesByIdentifier() {
		String[] idsArray = new String[] {"mrn"};
    	List<Participant> participantList = getDao().getBySubnames(idsArray, 1);
    	
    	assertEquals(1, participantList.size());
    	assertEquals("Rudolph", participantList.get(0).getFirstName());
	}*/
    
    /**
     * Test for Creating Participant with basic details and address and healthcareSite
     * 
     * @throws Exception
     */

    public void testCreateParticipant() throws Exception {
        Participant participant = new Participant();
        participant.setLastName("Lewis");
        participant.setFirstName("Carrol");
        participant.setAdministrativeGenderCode("Male");
        participant.setRaceCode("Unknown");
        Date birthDate = new Date();
        participant.setBirthDate(birthDate);
        participant.getAddress().setCity("Charlotte");
        participant.getAddress().setCountryCode("USA");
        participant.getAddress().setStateCode("NC");
        participant.getAddress().setStreetAddress("350 Glen Dale Avenue");
        HealthcareSite hcs = organizationDao.getById(1000);
        List<HealthcareSite> hcsList = new ArrayList<HealthcareSite>();
        hcsList.add(hcs);
        participant.setHealthcareSites(hcsList);
        Address add = new Address();

        participantDao.save(participant);

        interruptSession();

        Participant savedParticipant = participantDao.getById(participant.getId());
        assertEquals(1000,savedParticipant.getHealthcareSites().get(0).getId().intValue());
        assertEquals("Lewis", savedParticipant.getLastName());
        assertEquals("NC", savedParticipant.getAddress().getStateCode());
    }

    /**
     * Test for Creating Participant with system assigned identifier
     * 
     * @throws Exception
     */

    public void testCreateParticipantWithSystemAssignedIdentifier() throws Exception {
        Participant participant = new Participant();
        participant.setLastName("Ben");
        participant.setFirstName("Afflek");
        participant.setAdministrativeGenderCode("Male");
        participant.setRaceCode("Unknown");
        Date birthDate = new Date();
        participant.setBirthDate(birthDate);
        SystemAssignedIdentifier systemIdentifier = new SystemAssignedIdentifier();
        systemIdentifier.setSystemName("localSystem");
        systemIdentifier.setValue("SysGenID");
        systemIdentifier.setType("MRN");
        participant.addIdentifier(systemIdentifier);

        participantDao.save(participant);

        interruptSession();

        Participant savedParticipant = participantDao.getById(participant.getId());
        assertEquals("SysGenID", savedParticipant.getSystemAssignedIdentifiers().get(0).getValue());
        assertEquals("localSystem", savedParticipant.getSystemAssignedIdentifiers().get(0)
                        .getSystemName());
    }

    public void testSearchParticipantWithOrganizationAssignedIdentifier() throws Exception {
        Participant participant = new Participant();
        participant.setLastName("Barry");
        participant.setFirstName("Bonds");
        participant.setAdministrativeGenderCode("Male");
        participant.setRaceCode("Unknown");
        Date birthDate = new Date();
        participant.setBirthDate(birthDate);
        OrganizationAssignedIdentifier orgIdentifier = new OrganizationAssignedIdentifier();
        orgIdentifier.setHealthcareSite(healthcareSiteDao.getById(1001));
        orgIdentifier.setValue("Identifier Value");
        orgIdentifier.setType("MRN");
        participant.addIdentifier(orgIdentifier);

        participantDao.save(participant);

        interruptSession();

        Participant savedParticipant = participantDao.getById(participant.getId());
        assertEquals("Identifier Value", savedParticipant.getOrganizationAssignedIdentifiers().get(
                        0).getValue());

        interruptSession();
        OrganizationAssignedIdentifier organizationAssignedIdentifier = new OrganizationAssignedIdentifier();
        organizationAssignedIdentifier.setHealthcareSite(healthcareSiteDao.getById(1001));
        organizationAssignedIdentifier.setValue("Identifier Value");
        organizationAssignedIdentifier.setType("MRN");
        List<Participant> pList = participantDao.searchByOrgIdentifier(organizationAssignedIdentifier);
        assertEquals("wrong size of list", 1, pList.size());
    }

    /**
     * Test for Creating Participant with organization assigned identifier
     * 
     * @throws Exception
     */

    public void testCreateParticipantWithOrganizationAssignedIdentifier() throws Exception {
        Participant participant = buildParticipantWithOrganizationAssignedIdentifier();
        participantDao.save(participant);
        interruptSession();

        Participant savedParticipant = participantDao.getById(participant.getId());
        assertEquals("Identifier Value", savedParticipant.getOrganizationAssignedIdentifiers().get(
                        0).getValue());
        assertEquals("duke healthcare", savedParticipant
                        .getOrganizationAssignedIdentifiers().get(0).getHealthcareSite().getName());
    }
    
    
    public Participant buildParticipantWithOrganizationAssignedIdentifier() {
		
	    Participant participant = new Participant();
	    participant.setLastName("Barry");
	    participant.setFirstName("Bonds");
	    participant.setRaceCode("Asian");
	    participant.setAdministrativeGenderCode("Male");
	    Date birthDate = new Date();
	    participant.setBirthDate(birthDate);
	    OrganizationAssignedIdentifier orgAssignedIdentifier = new OrganizationAssignedIdentifier();
	    HealthcareSite healthcareSite = healthcareSiteDao.getById(1001);
	    orgAssignedIdentifier.setHealthcareSite(healthcareSite);
	    orgAssignedIdentifier.setValue("Identifier Value");
	    orgAssignedIdentifier.setType("MRN");
	    participant.addIdentifier(orgAssignedIdentifier);
	    
	    return participant;
    }
    /**
     * Test for Retrieving Participant with organization assigned identifier
     * 
     * @throws Exception
     */
    
    /**
     * Test for Search Participant with system assigned identifier
     * 
     * @throws Exception
     */

    public void testSearchParticipantWithSystemAssignedIdentifier() throws Exception {
        SystemAssignedIdentifier systemAssignedIdentifier = new SystemAssignedIdentifier();
        systemAssignedIdentifier.setSystemName("nci");
        systemAssignedIdentifier.setType("local");
        systemAssignedIdentifier.setValue("grid");
        
        List<Participant> participants = participantDao.searchBySystemAssignedIdentifier(systemAssignedIdentifier);
        assertEquals("Expected to get 1 participant",1, participants.size());
        
        SystemAssignedIdentifier systemAssignedIdentifier1 = new SystemAssignedIdentifier();
        systemAssignedIdentifier1.setSystemName("nci");
        systemAssignedIdentifier1.setType("local");
        systemAssignedIdentifier1.setValue("nci");
        
        List<Participant> participants1 = participantDao.searchBySystemAssignedIdentifier(systemAssignedIdentifier1);
        assertEquals("Expected to get 2 participants",2, participants1.size());
        
        SystemAssignedIdentifier systemAssignedIdentifier2 = new SystemAssignedIdentifier();
        systemAssignedIdentifier2.setSystemName("nci123");
        systemAssignedIdentifier2.setType("local");
        systemAssignedIdentifier2.setValue("nci");
        
        List<Participant> participants2 = participantDao.searchBySystemAssignedIdentifier(systemAssignedIdentifier2);
        assertEquals("Expected to get 0 participant",0, participants2.size());
    }
    
    
    /**
     * Test initialize and ensure lazylists are initialized.
     */
    public void testInitialize() {
		Participant participant = getDao().getById(1000);
		getDao().initialize(participant);
		try{
			participant.getIdentifiers().get(0);
			participant.getContactMechanisms().size();
			participant.getRaceCodes().size();
		} catch(Exception e){
			fail();
		}
	}
    
    /**
     * Test initialize and ensure studySubjects are initalized.
     */
    public void testInitializeStudySubjects() {
		Participant participant = getDao().getById(1000);
		getDao().initializeStudySubjects(participant);
		try{
			participant.getStudySubjects().get(0);
		} catch(Exception e){
			fail();
		}
	}
    
    /**
     * Test merge.
     */
    public void testMerge() {
    	Participant participant = buildParticipantWithOrganizationAssignedIdentifier();
    	participant = getDao().merge(participant);
    	int savedId = participant.getId();
    	interruptSession();

        Participant savedParticipant = participantDao.getById(savedId);
        assertEquals("Identifier Value", savedParticipant.getOrganizationAssignedIdentifiers().get(
                        0).getValue());
        assertEquals("duke healthcare", savedParticipant
                        .getOrganizationAssignedIdentifiers().get(0).getHealthcareSite().getName());
	}
    
    public void testReassociate() {
    	Participant participant = getDao().getById(1000);
    	participant.setMaidenName("mani");
    	getDao().reassociate(participant);
    	interruptSession();

        Participant savedParticipant = participantDao.getById(participant.getId());
        assertEquals("mani", savedParticipant.getMaidenName());
	}

}