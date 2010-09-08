package edu.duke.cabig.c3pr.dao;

import static edu.duke.cabig.c3pr.C3PRUseCase.CREATE_PARTICIPANT;
import static edu.duke.cabig.c3pr.C3PRUseCase.SEARCH_SUBJECT;
import static edu.duke.cabig.c3pr.C3PRUseCase.UPDATE_SUBJECT;
import static edu.duke.cabig.c3pr.C3PRUseCase.VERIFY_SUBJECT;
import static edu.nwu.bioinformatics.commons.testing.CoreTestCase.assertContains;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.semanticbits.querybuilder.AdvancedSearchCriteriaParameter;
import com.semanticbits.querybuilder.AdvancedSearchHelper;
import com.semanticbits.querybuilder.QueryGenerator;
import com.semanticbits.querybuilder.TargetObject;

import edu.duke.cabig.c3pr.C3PRUseCases;
import edu.duke.cabig.c3pr.constants.OrganizationIdentifierTypeEnum;
import edu.duke.cabig.c3pr.constants.RaceCodeEnum;
import edu.duke.cabig.c3pr.domain.Address;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.LocalHealthcareSite;
import edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.RaceCodeAssociation;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.StudySubjectDemographics;
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

    public ParticipantDaoTest() {
    	participantDao = (ParticipantDao) getApplicationContext().getBean("participantDao");
    	healthcareSiteDao = (HealthcareSiteDao) getApplicationContext().getBean("healthcareSiteDao");
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
     * Test for loading all Participants
     *
     * @throws Exception
     */
    public void testGetAll() throws Exception {
        List<Participant> actual = participantDao.getAll();
        assertEquals(11, actual.size());
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
        assertEquals("Wrong number of Participants", 2, results.size());
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

    	assertEquals(2, participantList.size());
    	assertEquals("Rudolph", participantList.get(0).getFirstName());
	}

    /**
     * Test search by subnames by last name.
     */
    public void testSearchBySubnamesByLastName() {
		String[] namesArray = new String[] {"mez"};
    	List<Participant> participantList = getDao().getBySubnames(namesArray, 0);

    	assertEquals(1, participantList.size());
    	assertEquals("Aldrez", participantList.get(0).getFirstName());
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
        
        RaceCodeAssociation  raceCodeAssociation = new RaceCodeAssociation();
        raceCodeAssociation.setRaceCode(RaceCodeEnum.Unknown);
        
        participant.addRaceCodeAssociation(raceCodeAssociation);
        
        Date birthDate = new Date();
        participant.setBirthDate(birthDate);
        participant.getAddress().setCity("Charlotte");
        participant.getAddress().setCountryCode("USA");
        participant.getAddress().setStateCode("NC");
        participant.getAddress().setStreetAddress("350 Glen Dale Avenue");
        HealthcareSite hcs = healthcareSiteDao.getById(1000);
        List<HealthcareSite> hcsList = new ArrayList<HealthcareSite>();
        hcsList.add(hcs);
        participant.setHealthcareSites(hcsList);
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
        RaceCodeAssociation  raceCodeAssociation = new RaceCodeAssociation();
        raceCodeAssociation.setRaceCode(RaceCodeEnum.Unknown);
        
        participant.addRaceCodeAssociation(raceCodeAssociation);
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
        RaceCodeAssociation  raceCodeAssociation = new RaceCodeAssociation();
        raceCodeAssociation.setRaceCode(RaceCodeEnum.Unknown);
        
        participant.addRaceCodeAssociation(raceCodeAssociation);
        Date birthDate = new Date();
        participant.setBirthDate(birthDate);
        OrganizationAssignedIdentifier orgIdentifier = new OrganizationAssignedIdentifier();
        orgIdentifier.setHealthcareSite(healthcareSiteDao.getById(1001));
        orgIdentifier.setValue("Identifier Value");
        orgIdentifier.setType(OrganizationIdentifierTypeEnum.MRN);
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
        organizationAssignedIdentifier.setType(OrganizationIdentifierTypeEnum.MRN);
        List<Participant> pList = participantDao.searchByOrgIdentifier(organizationAssignedIdentifier);
        assertEquals("wrong size of list", 1, pList.size());
        assertEquals(pList.get(0).getFirstName(), "Bonds");
        
        interruptSession();
        HealthcareSite healthcareSite = new LocalHealthcareSite();
        healthcareSite.setCtepCode("code", true);
        organizationAssignedIdentifier.setHealthcareSite(healthcareSite);
        List<Participant> prtList = participantDao.searchByOrgIdentifier(organizationAssignedIdentifier);
        assertEquals("wrong size of list", 1, prtList.size());
        assertEquals(pList.get(0).getFirstName(), "Bonds");
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
	    
	    RaceCodeAssociation  raceCodeAssociation = new RaceCodeAssociation();
        raceCodeAssociation.setRaceCode(RaceCodeEnum.Asian);
        
        participant.addRaceCodeAssociation(raceCodeAssociation);
         
	    participant.setAdministrativeGenderCode("Male");
	    Date birthDate = new Date();
	    participant.setBirthDate(birthDate);
	    OrganizationAssignedIdentifier orgAssignedIdentifier = new OrganizationAssignedIdentifier();
	    HealthcareSite healthcareSite = healthcareSiteDao.getById(1001);
	    orgAssignedIdentifier.setHealthcareSite(healthcareSite);
	    orgAssignedIdentifier.setValue("Identifier Value");
	    orgAssignedIdentifier.setType(OrganizationIdentifierTypeEnum.MRN);
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
			participant.getRaceCodeAssociations().size();
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
    
    public void testSynchronizeWithLatestStudySubjectDemographics1() throws Exception {
    	Participant participant = new Participant();
    	
    	StudySubjectDemographics studySubjectDemographics = participant.createStudySubjectDemographics();
    	
    	OrganizationAssignedIdentifier orgIdentifier = new OrganizationAssignedIdentifier();
    	orgIdentifier.setHealthcareSite(new LocalHealthcareSite());
    	orgIdentifier.setType(OrganizationIdentifierTypeEnum.C3PR);
    	orgIdentifier.setValue("identifier1");
    	
    	participant.addIdentifier(orgIdentifier);
    	
    	assertEquals("Unexpected identifier(s)",0,studySubjectDemographics.getIdentifiers().size());
    	assertEquals("Expected 1 identifier",1,participant.getIdentifiers().size());
    	
    	participant.synchronizeWithStudySubjectDemographics(studySubjectDemographics);
    	
    	assertEquals("Unexpected identifier(s)",0,participant.getIdentifiers().size());
    	
    }
    
    public void testSynchronizeWithLatestStudySubjectDemographicsIdentifiers() throws Exception {
    	Participant participant = new Participant();
    	
    	StudySubjectDemographics studySubjectDemographics = participant.createStudySubjectDemographics();
    	
    	OrganizationAssignedIdentifier orgIdentifier = new OrganizationAssignedIdentifier();
    	
    	LocalHealthcareSite localHealthcareSite = new LocalHealthcareSite();
    	localHealthcareSite.setName("NCCTG");
    	orgIdentifier.setHealthcareSite(localHealthcareSite);
    	orgIdentifier.setType(OrganizationIdentifierTypeEnum.C3PR);
    	orgIdentifier.setValue("identifier1");
    	
    	studySubjectDemographics.addIdentifier(orgIdentifier);
    	
    	assertEquals("Unexpected identifier(s)", 0, participant.getIdentifiers().size());
    	assertEquals("Expected 1 identifier", 1, studySubjectDemographics.getIdentifiers().size());
    	
    	participant.synchronizeWithStudySubjectDemographics(studySubjectDemographics);
    	
    	assertEquals("Expected 1 identifier",1, participant.getIdentifiers().size());
    	assertEquals("Name of healthcare site assigning the identifier is different", "NCCTG",
    			((OrganizationAssignedIdentifier)participant.getIdentifiers().get(0)).getHealthcareSite().getName());
    	assertEquals("Type of identifier is different", OrganizationIdentifierTypeEnum.C3PR, 
    			((OrganizationAssignedIdentifier)participant.getIdentifiers().get(0)).getType());
    	assertEquals("Value of identifier is different", "identifier1",
    			((OrganizationAssignedIdentifier)participant.getIdentifiers().get(0)).getValue());
    	
    }
    
    public void testSynchronizeWithLatestStudySubjectDemographicsContactMechanisms() throws Exception {
    	Participant participant = new Participant();
    	
    	StudySubjectDemographics studySubjectDemographics = participant.createStudySubjectDemographics();
    	studySubjectDemographics.setEmail("mail1@duke.edu");
    	
    	assertNull("Unexpected email",participant.getEmail());
    	participant.synchronizeWithStudySubjectDemographics(studySubjectDemographics);
    	
    	assertEquals("Expected email","mail1@duke.edu", participant.getEmail());
    	
    }
    
    public void testSynchronizeWithLatestStudySubjectDemographicsBasicDetails() throws Exception {
    	Participant participant = new Participant();
    	
    	StudySubjectDemographics studySubjectDemographics = participant.createStudySubjectDemographics();
    	
    	studySubjectDemographics.setFirstName("John");
    	studySubjectDemographics.setLastName("Doe");
    	studySubjectDemographics.setMiddleName("JD");
    	studySubjectDemographics.setMaidenName("Gerber");
    	studySubjectDemographics.setEthnicGroupCode("Non Hispanic");
    	studySubjectDemographics.setAdministrativeGenderCode("Not Reported");
    	
    	 RaceCodeAssociation  raceCodeAssociation = new RaceCodeAssociation();
         raceCodeAssociation.setRaceCode(RaceCodeEnum.Unknown);
         participant.addRaceCodeAssociation(raceCodeAssociation);
         
         RaceCodeAssociation  raceCodeAssociation1 = new RaceCodeAssociation();
         raceCodeAssociation1.setRaceCode(RaceCodeEnum.Not_Reported);
         participant.addRaceCodeAssociation(raceCodeAssociation1);
           
    	Date date = new Date("03/11/1890");
    	studySubjectDemographics.setBirthDate(date);
    	Address address = new Address();
    	address.setCountryCode("USA");
    	address.setCity("Allen Town");
    	address.setStreetAddress("650 mountain drive");
    	address.setPostalCode("12321");
    	address.setStateCode("Montana");
    	
    	studySubjectDemographics.setAddress(address);
    	
    	    	
    	assertNull("Unexpected state code in participant address",participant.getAddress().getStateCode());
    	assertNull("Unexpected last name",participant.getLastName());
    	
    	participant.synchronizeWithStudySubjectDemographics(studySubjectDemographics);
    	
    	//FIXME : HIMANSHU
//   	 RaceCodeAssociation  raceCodeAssociation = new RaceCodeAssociation();
//     raceCodeAssociation.setRaceCode(RaceCodeEnum.Unknown);
//     participant.addRaceCodeAssociation(raceCodeAssociation);
//     
//     RaceCodeAssociation  raceCodeAssociation1 = new RaceCodeAssociation();
//     raceCodeAssociation1.setRaceCode(RaceCodeEnum.Not_Reported);
//     participant.addRaceCodeAssociation(raceCodeAssociation1);
    	
    	assertEquals("Wrong first name","John",participant.getFirstName());
    	assertEquals("Wrong last name","Doe",participant.getLastName());
    	assertEquals("Wrong middle name","JD",participant.getMiddleName());
    	assertEquals("Wrong maiden name","Gerber",participant.getMaidenName());
    	assertEquals("Wrong gener","Not Reported",participant.getAdministrativeGenderCode());
    	assertEquals("Wrong birth date","03/11/1890",participant.getBirthDateStr());
    	assertEquals("Wrong ethnicity","Non Hispanic",participant.getEthnicGroupCode());
    	assertEquals("Wrong city code","Allen Town",participant.getAddress().getCity());
    	assertEquals("Wrong country code","USA",participant.getAddress().getCountryCode());
    	assertEquals("Wrong state code","Montana",participant.getAddress().getStateCode());
    	assertEquals("Wrong street address","650 mountain drive",participant.getAddress().getStreetAddress());
    	assertEquals("Wrong postal code","12321",participant.getAddress().getPostalCode());
    	
    }
    
    public void testCreateStudySubjectDemographicsBasicDetails() throws Exception {
    	Participant participant = new Participant();
    	
    	participant.setFirstName("John");
    	participant.setLastName("Doe");
    	participant.setMiddleName("JD");
    	participant.setMaidenName("Gerber");
    	participant.setEthnicGroupCode("Non Hispanic");
    	participant.setAdministrativeGenderCode("Not Reported");
    	
   	 RaceCodeAssociation  raceCodeAssociation = new RaceCodeAssociation();
     raceCodeAssociation.setRaceCode(RaceCodeEnum.Unknown);
     participant.addRaceCodeAssociation(raceCodeAssociation);
     
     RaceCodeAssociation  raceCodeAssociation1 = new RaceCodeAssociation();
     raceCodeAssociation1.setRaceCode(RaceCodeEnum.Not_Reported);
     participant.addRaceCodeAssociation(raceCodeAssociation1);
		
    	
    	Date date = new Date("03/11/1890");
    	participant.setBirthDate(date);
    	Address address = new Address();
    	address.setCountryCode("USA");
    	address.setCity("Allen Town");
    	address.setStreetAddress("650 mountain drive");
    	address.setPostalCode("12321");
    	address.setStateCode("Montana");
    	
    	participant.setAddress(address);
    	
    	StudySubjectDemographics studySubjectDemographics = participant.createStudySubjectDemographics();
    	
    	//FIXME : HIMANSHU
//    	List<RaceCode> raceCodes = new ArrayList<RaceCode>();
//    	raceCodes.add(raceCodeDao.getById(6));
//    	raceCodes.add(raceCodeDao.getById(7));
    	
    	
    	assertEquals("Wrong first name","John",studySubjectDemographics.getFirstName());
    	assertEquals("Wrong last name","Doe",studySubjectDemographics.getLastName());
    	assertEquals("Wrong middle name","JD",studySubjectDemographics.getMiddleName());
    	assertEquals("Wrong maiden name","Gerber",studySubjectDemographics.getMaidenName());
    	assertEquals("Wrong gener","Not Reported",studySubjectDemographics.getAdministrativeGenderCode());
    	assertEquals("Wrong birth date","03/11/1890",studySubjectDemographics.getBirthDateStr());
    	assertEquals("Wrong ethnicity","Non Hispanic",studySubjectDemographics.getEthnicGroupCode());
//    	assertEquals("Wrong race code",raceCodes.size(), participant.getRaceCodes().size());
//    	assertTrue("Wrong race code", participant.getRaceCodes().contains(raceCodes.get(0)));
//    	assertTrue("Wrong race code", participant.getRaceCodes().contains(raceCodes.get(1)));
    	assertEquals("Wrong city code","Allen Town",studySubjectDemographics.getAddress().getCity());
    	assertEquals("Wrong country code","USA",studySubjectDemographics.getAddress().getCountryCode());
    	assertEquals("Wrong state code","Montana",studySubjectDemographics.getAddress().getStateCode());
    	assertEquals("Wrong street address","650 mountain drive",studySubjectDemographics.getAddress().getStreetAddress());
    	assertEquals("Wrong postal code","12321",studySubjectDemographics.getAddress().getPostalCode());
    }
    
    public void testRaceCodeAssocnSave() throws Exception {
    	Participant participant = new Participant();
    	
    	participant.setFirstName("John");
    	participant.setLastName("Doe");
    	participant.setMiddleName("JD");
    	participant.setMaidenName("Gerber");
    	participant.setEthnicGroupCode("Non Hispanic");
    	participant.setAdministrativeGenderCode("Not Reported");
    	
	   	 RaceCodeAssociation  raceCodeAssociation = new RaceCodeAssociation();
	     raceCodeAssociation.setRaceCode(RaceCodeEnum.Asian);
	     participant.addRaceCodeAssociation(raceCodeAssociation);
	     participant = participantDao.merge(participant);
	     assertEquals("1 association",1, participant.getRaceCodeAssociations().size());
	     
	     participant.removeRaceCodeAssociation(raceCodeAssociation);
	     
	     RaceCodeAssociation  raceCodeAssociation1 = new RaceCodeAssociation();
	     raceCodeAssociation1.setRaceCode(RaceCodeEnum.Unknown);
	     participant.addRaceCodeAssociation(raceCodeAssociation1);
	     
	     participant = participantDao.merge(participant);
	     assertEquals("1 association", 1, participant.getRaceCodeAssociations().size());
    }
    
    public void testGetResultSetWithHQLForFirstName() throws Exception {
		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper.buildAdvancedSearchCriteriaParameter(
						"edu.duke.cabig.c3pr.domain.Participant",  "firstName", "%re%", "like");

		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
		criteriaParameters.add(advancedSearchCriteriaParameter1);
		
		List<Participant> subjects = participantDao.search(criteriaParameters);
		assertEquals("2 participants not found", 2,  subjects.size());
	}
	
	public void testGetResultSetWithHQLForRaceCode() throws Exception {
		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
				.buildAdvancedSearchCriteriaParameter(
						"edu.duke.cabig.c3pr.domain.RaceCodeAssociation",  "raceCode.code",
						"Asian", "=");

		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
		criteriaParameters.add(advancedSearchCriteriaParameter1);
		
		List<Participant> subjects = participantDao.search(criteriaParameters);
		assertEquals("3 participants not found", 3,  subjects.size());
	}
	
	public void testGetResultSetWithHQLForMultipleRaceCodeNotIn() throws Exception {
		List<String> values = new ArrayList<String>();
		values.add("White");
		values.add("Asian");
		
		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
				.buildAdvancedSearchCriteriaParameter(
						"edu.duke.cabig.c3pr.domain.RaceCodeAssociation",  "raceCode.code",
						values, "not in");

		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
		criteriaParameters.add(advancedSearchCriteriaParameter1);
		
		List<Participant> subjects = participantDao.search(criteriaParameters);
		assertEquals("0 participants not found", 0,  subjects.size());
	}
	
	public void testGetResultSetWithHQLForMultipleRaceCodeIn() throws Exception {
		List<String> values = new ArrayList<String>();
		values.add("White");
		values.add("Asian");
		
		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
				.buildAdvancedSearchCriteriaParameter(
						"edu.duke.cabig.c3pr.domain.RaceCodeAssociation",  "raceCode.code",
						values, "in");

		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
		criteriaParameters.add(advancedSearchCriteriaParameter1);
		
		List<Participant> subjects = participantDao.search(criteriaParameters);
		assertEquals("11 participants not found", 11,  subjects.size());
	}
	
	
	public void testGetResultSetWithHQLForZipcode() throws Exception {
		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
				.buildAdvancedSearchCriteriaParameter(
						"edu.duke.cabig.c3pr.domain.Address",  "postalCode",
						"20171", "=");

		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
		criteriaParameters.add(advancedSearchCriteriaParameter1);
		
		List<Participant> subjects = participantDao.search(criteriaParameters);
		assertEquals("2 participants not found", 2,  subjects.size());
	}
	
	public void testGetResultSetWithHQLForIdentifier() throws Exception {
		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
				.buildAdvancedSearchCriteriaParameter(
						"edu.duke.cabig.c3pr.domain.Identifier", "value", "sub%", "like");

		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
		criteriaParameters.add(advancedSearchCriteriaParameter1);
		
		List<Participant> subjects = participantDao.search(criteriaParameters);
		assertEquals("2 participants not found", 3,  subjects.size());
	}
	
	public void testGetResultSetWithAllSearchCriterion() throws Exception {

		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
		.buildAdvancedSearchCriteriaParameter(
				"edu.duke.cabig.c3pr.domain.Participant",  "firstName", "Ru%", "like");
		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter2 = AdvancedSearchHelper
		.buildAdvancedSearchCriteriaParameter( "edu.duke.cabig.c3pr.domain.Participant",  "lastName", "%oo%", "like");
		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter3 = AdvancedSearchHelper
		.buildAdvancedSearchCriteriaParameter(
				"edu.duke.cabig.c3pr.domain.Participant",  "administrativeGenderCode", "Male", "=");
		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter4 = AdvancedSearchHelper
		.buildAdvancedSearchCriteriaParameter(
				"edu.duke.cabig.c3pr.domain.Participant",  "ethnicGroupCode", "Unknown", "=");
		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter5 = AdvancedSearchHelper
		.buildAdvancedSearchCriteriaParameter(
				"edu.duke.cabig.c3pr.domain.RaceCodeAssociation", "raceCode.code", "White", "=");
		
		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter6 = AdvancedSearchHelper
		.buildAdvancedSearchCriteriaParameter(
				"edu.duke.cabig.c3pr.domain.Address",  "postalCode", "20171", "=");
		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter7 = AdvancedSearchHelper
		.buildAdvancedSearchCriteriaParameter(
				"edu.duke.cabig.c3pr.domain.Address", "stateCode", "VA", "like");
		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter8 = AdvancedSearchHelper
		.buildAdvancedSearchCriteriaParameter(
				"edu.duke.cabig.c3pr.domain.Address",  "countryCode", "USA", "like");
		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter9 = AdvancedSearchHelper
		.buildAdvancedSearchCriteriaParameter(
				"edu.duke.cabig.c3pr.domain.Address", "city", "Herndon", "like");
		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter10 = AdvancedSearchHelper
		.buildAdvancedSearchCriteriaParameter(
				"edu.duke.cabig.c3pr.domain.Identifier", "value", "sub%", "like");
		
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
		
		List<Participant> subjects = participantDao.search(criteriaParameters);
		assertEquals("Only one subject expected", 1, subjects.size());
		assertEquals("First name should be Ru", "Ru", subjects.get(0).getFirstName());
		assertEquals("Last name should be Cloon", "Cloon", subjects.get(0).getLastName());
	}

}