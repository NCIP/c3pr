/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.dao;

import static edu.duke.cabig.c3pr.C3PRUseCase.CREATE_PARTICIPANT;
import static edu.duke.cabig.c3pr.C3PRUseCase.SEARCH_SUBJECT;
import static edu.duke.cabig.c3pr.C3PRUseCase.UPDATE_SUBJECT;
import static edu.duke.cabig.c3pr.C3PRUseCase.VERIFY_SUBJECT;
import static edu.nwu.bioinformatics.commons.testing.CoreTestCase.assertContains;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.semanticbits.querybuilder.AdvancedSearchCriteriaParameter;
import com.semanticbits.querybuilder.AdvancedSearchHelper;

import edu.duke.cabig.c3pr.C3PRUseCases;
import edu.duke.cabig.c3pr.constants.AddressUse;
import edu.duke.cabig.c3pr.constants.ContactMechanismUse;
import edu.duke.cabig.c3pr.constants.FamilialRelationshipName;
import edu.duke.cabig.c3pr.constants.OrganizationIdentifierTypeEnum;
import edu.duke.cabig.c3pr.constants.RaceCodeEnum;
import edu.duke.cabig.c3pr.constants.RelationshipCategory;
import edu.duke.cabig.c3pr.domain.Address;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.LocalHealthcareSite;
import edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.RaceCodeAssociation;
import edu.duke.cabig.c3pr.domain.Relationship;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.StudySubjectDemographics;
import edu.duke.cabig.c3pr.domain.SystemAssignedIdentifier;
import edu.duke.cabig.c3pr.utils.CommonUtils;
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
	private final String householdIdType = "HOUSEHOLD_IDENTIFIER";

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
						"20191", "=");

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
		assertEquals("3 participants not found", 3,  subjects.size());
	}
	
	public void testGetResultSetWithHQLForIdentifierSystemName() throws Exception {
		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
				.buildAdvancedSearchCriteriaParameter(
						"edu.duke.cabig.c3pr.domain.SystemAssignedIdentifier", "systemName", "nci", "like");

		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
		criteriaParameters.add(advancedSearchCriteriaParameter1);
		
		List<Participant> subjects = participantDao.search(criteriaParameters);
		assertEquals("Unexpected number participants", 5,  subjects.size());
	}
	
	public void testGetResultSetWithHQLForIdentifierByAGivenOrganization() throws Exception {
		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
				.buildAdvancedSearchCriteriaParameter(
						"edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier", "healthcareSite.id", "1000", "=");
		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter2 = AdvancedSearchHelper
		.buildAdvancedSearchCriteriaParameter(
				"edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier", "value", "mrn_value", "like");
		
		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter3 = AdvancedSearchHelper
		.buildAdvancedSearchCriteriaParameter(
				"edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier", "typeInternal", "MRN", "like");

		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
		criteriaParameters.add(advancedSearchCriteriaParameter1);
		criteriaParameters.add(advancedSearchCriteriaParameter2);
		criteriaParameters.add(advancedSearchCriteriaParameter3);
		
		List<Participant> subjects = participantDao.search(criteriaParameters);
		assertEquals("Wrong number of participants found", 1,  subjects.size());
	}
	
	public void testGetResultSetWithHQLForWildSearch() throws Exception {
		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
		List<Participant> subjects = participantDao.search(criteriaParameters);
		assertEquals("11 participants not found", 11,  subjects.size());
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
				"edu.duke.cabig.c3pr.domain.Address",  "postalCode", "20191", "=");
		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter7 = AdvancedSearchHelper
		.buildAdvancedSearchCriteriaParameter(
				"edu.duke.cabig.c3pr.domain.Address", "stateCode", "VA", "like");
		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter8 = AdvancedSearchHelper
		.buildAdvancedSearchCriteriaParameter(
				"edu.duke.cabig.c3pr.domain.Address",  "countryCode", "USA", "like");
		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter9 = AdvancedSearchHelper
		.buildAdvancedSearchCriteriaParameter(
				"edu.duke.cabig.c3pr.domain.Address", "city", "Reston", "like");
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

	/**
     * Test for Creating Participant with basic details, address, contact mechanisms and healthcareSite
     *
     * @throws Exception
     */

    public void testCreateParticipantwithAddressAndContactMechanisms() throws Exception {
        Participant participant = new Participant();
        participant.setLastName("Doe");
        participant.setFirstName("John");
        participant.setAdministrativeGenderCode("Male");
        participant.setBirthDate(new Date());
        
        RaceCodeAssociation  raceCodeAssociation = new RaceCodeAssociation();
        raceCodeAssociation.setRaceCode(RaceCodeEnum.Unknown);
        participant.addRaceCodeAssociation(raceCodeAssociation);

        participant.getAddress().setCity("Charlotte");
        participant.getAddress().setCountryCode("USA");
        participant.getAddress().setStateCode("NC");
        participant.getAddress().setStreetAddress("350 Glen Dale Avenue");
        participant.getAddress().setAddressUses(Arrays.asList(AddressUse.ABC, AddressUse.H));
        
        participant.setEmail("john.doe@john.com", Arrays.asList(ContactMechanismUse.AS, ContactMechanismUse.H));
        participant.setFax("111-222-3333", Arrays.asList(ContactMechanismUse.BAD, ContactMechanismUse.HP));
        participant.setPhone("333-444-5555", Arrays.asList(ContactMechanismUse.MC, ContactMechanismUse.PG));
        participant.setOther("some_other_contact", Arrays.asList(ContactMechanismUse.PUB));
        
        HealthcareSite hcs = healthcareSiteDao.getById(1000);
        List<HealthcareSite> hcsList = new ArrayList<HealthcareSite>();
        hcsList.add(hcs);
        participant.setHealthcareSites(hcsList);
        participantDao.save(participant);

        interruptSession();

        Participant savedParticipant = participantDao.getById(participant.getId());
        assertEquals(1000,savedParticipant.getHealthcareSites().get(0).getId().intValue());
        assertEquals("Doe", savedParticipant.getLastName());
        assertEquals("NC", savedParticipant.getAddress().getStateCode());
        assertEquals(Arrays.asList(AddressUse.ABC, AddressUse.H), savedParticipant.getAddress().getAddressUses());
        assertEquals(Arrays.asList(ContactMechanismUse.AS, ContactMechanismUse.H), savedParticipant.getEmailContactMechanism().getContactUses());
        assertEquals(Arrays.asList(ContactMechanismUse.BAD, ContactMechanismUse.HP), savedParticipant.getFaxContactMechanism().getContactUses());
        assertEquals(Arrays.asList(ContactMechanismUse.MC, ContactMechanismUse.PG), savedParticipant.getPhoneContactMechanism().getContactUses());
        assertEquals(Arrays.asList(ContactMechanismUse.PUB), savedParticipant.getOtherContactMechanism().getContactUses());
    }
    
    public void testCreateParticipantRelationship() throws Exception{
    	Participant participant1 = participantDao.getById(1000);
    	Participant participant2 = participantDao.getById(1001);
    	
    	Relationship rel = new Relationship();
    	rel.setPrimaryParticipant(participant1);
    	rel.setSecondaryParticipant(participant2);
    	rel.setCategory(RelationshipCategory.FAMILIAL);
    	rel.setName(FamilialRelationshipName.FATHER);
    	participant1.addRelatedTo(rel);
    	
    	participantDao.save(participant1);
    	
    	interruptSession();
    	
    	Participant relatedParticipant = participantDao.getById(1000);
    	Participant relatedSecondaryParticipant = participantDao.getById(1001);
    	assertEquals("Wrong number of participant relations",1,relatedParticipant.getRelatedTo().size());
    	assertEquals("Wrong secondary participant",participant2.getId(),relatedParticipant.getRelatedTo().get(0).getSecondaryParticipant().getId());
    	assertEquals("Wrong primary participant",participant1.getId(),relatedParticipant.getRelatedTo().get(0).getPrimaryParticipant().getId());
    }
    
    
    public void testCreateParticipantRelationshipWithLazylist() throws Exception{
    	Participant participant1 = participantDao.getById(1000);
    	Participant participant2 = participantDao.getById(1001);
    	
    	participant1.getRelatedTo().get(0).setPrimaryParticipant(participant1);
    	participant1.getRelatedTo().get(0).setSecondaryParticipant(participant2);
    	participant1.getRelatedTo().get(0).setCategory(RelationshipCategory.FAMILIAL);
    	participant1.getRelatedTo().get(0).setName(FamilialRelationshipName.FATHER);
    	participant1.addRelatedTo(participant1.getRelatedTo().get(0));
    	
    	participantDao.save(participant1);
    	
    	interruptSession();
    	
    	Participant relatedParticipant = participantDao.getById(1000);
    	Participant relatedSecondaryParticipant = participantDao.getById(1001);
    	assertEquals("Wrong number of participant relations",1,relatedParticipant.getRelatedTo().size());
    	assertEquals("Wrong secondary participant",participant2.getId(),relatedParticipant.getRelatedTo().get(0).getSecondaryParticipant().getId());
    	assertEquals("Wrong primary participant",participant1.getId(),relatedParticipant.getRelatedTo().get(0).getPrimaryParticipant().getId());
    }
    
    public void testSaveAndRetrieveParticipantWithAddressDateRanges() throws Exception{
    	
    	Participant loadedParticipant = participantDao.getById(1000);
    	assertEquals("Unexpected address",0,loadedParticipant.getAddresses().size());
    	
    	// add first address
    	
    	Address address = new Address();
    	address.setStreetAddress("1900 Smith Barney Avenue");
    	Calendar startCal = Calendar.getInstance();
    	startCal.set(2001, 01, 18);
    	
    	Calendar endCal = Calendar.getInstance();
    	endCal.set(2005, 03, 11);
    	    	
    	address.setStartDate(startCal.getTime());
    	address.setEndDate(endCal.getTime());
    	loadedParticipant.getAddresses().add(address);
    	
    	
    	// add second address
    	
    	Address address1 = new Address();
    	address1.setStreetAddress("123 whaley street");
    	address1.setCity("Sun City");
    	address1.setCountryCode("USA");
    	address1.setPostalCode("12078");
    	
    	startCal = Calendar.getInstance();
    	startCal.set(2005, 03, 12);
    	
    	endCal = Calendar.getInstance();
    	endCal.set(2007, 05, 21);
    	    	
    	address1.setStartDate(startCal.getTime());
    	address1.setEndDate(endCal.getTime());
    	loadedParticipant.getAddresses().add(address1);
    	
    	// add third address
    	
    	Address address2 = new Address();
    	
    	address2.setStreetAddress("13921 main street");
    	address2.setCity("Columbia");
    	address2.setStateCode("OK");
    	address2.setCountryCode("USA");
    	address2.setPostalCode("91209");
    	
    	startCal = Calendar.getInstance();
    	startCal.set(2007, 05, 22);
    	
    	endCal = Calendar.getInstance();
    	endCal.set(2009, 1, 02);
    	    	
    	address2.setStartDate(startCal.getTime());
    	address2.setEndDate(endCal.getTime());
    	
    	loadedParticipant.getAddresses().add(address2);
    	
    	participantDao.save(loadedParticipant);
    	
    	interruptSession();
    	
    	Participant updatedParticipant = participantDao.getById(1000);
    	assertEquals("Wrong number of addresses", 3,updatedParticipant.getAddresses().size());
    	assertEquals("Wrong street name",address2.getStreetAddress(),((Address)updatedParticipant.getAddresses().toArray()[0]).getStreetAddress());
    	assertEquals("Wrong start date", CommonUtils.getDateString(address2.getStartDate()),CommonUtils.getDateString(((Address)updatedParticipant.getAddresses().toArray()[0]).getStartDate()));
    	assertEquals("Wrong street name",address1.getStreetAddress(),((Address)updatedParticipant.getAddresses().toArray()[1]).getStreetAddress());
    	assertEquals("Wrong start date", CommonUtils.getDateString(address1.getStartDate()),CommonUtils.getDateString(((Address)updatedParticipant.getAddresses().toArray()[1]).getStartDate()));
    }
    
    public void testSaveHouseholdIdentfierWithDateRanges() throws Exception{
    	Participant participant1 = participantDao.getById(1000);
    	
    	SystemAssignedIdentifier householdId = new SystemAssignedIdentifier();
    	householdId.setSystemName("UAMS System");
    
    	householdId.setType(householdIdType);
    	householdId.setValue("1");
    	Calendar startCal = Calendar.getInstance();
    	startCal.set(2006, 11, 03);
    	Calendar endCal = Calendar.getInstance();
    	endCal.set(2009, 07, 11);
    	
    	householdId.setStartDate(startCal.getTime());
    	householdId.setEndDate(endCal.getTime());
    	int noOfIdentifiers = participant1.getIdentifiers().size();
    	
    	
    	participant1.addIdentifier(householdId);
    	    	
    	participantDao.save(participant1);
    	
    	interruptSession();
    	
    	Participant updatedParticipant = participantDao.getById(1000);
    	assertEquals("Household identifier not added",noOfIdentifiers + 1, updatedParticipant.getIdentifiers().size());
    	assertEquals("Wrong household id type",householdIdType,updatedParticipant.getIdentifiers().get(noOfIdentifiers).getTypeInternal());
    	assertEquals("Wrong household id type",CommonUtils.getDateString(startCal.getTime()),CommonUtils.getDateString(updatedParticipant.getIdentifiers().get(noOfIdentifiers).getStartDate()));
    	assertEquals("Wrong household id type",CommonUtils.getDateString(endCal.getTime()),CommonUtils.getDateString(updatedParticipant.getIdentifiers().get(noOfIdentifiers).getEndDate()));
    	
    }
    
    public void testGetIsMinor() throws Exception{
    	Participant participant = participantDao.getById(1000);
    	Date birthDate = participant.getBirthDate();
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(birthDate);
    	
    	assertTrue(participant.getIsMinor());
    	assertEquals("Wrong birth year",2000,cal.get(Calendar.YEAR));
    	
    	cal.add(Calendar.YEAR, -7);
    	birthDate.setTime(cal.getTimeInMillis());
    	assertFalse(participant.getIsMinor());
    }
    
    public void testGetBySubnamesAndIdentifier() throws Exception{
    	String[] subnames = new String[]{"SU"};
    	List<Participant> participants = participantDao.getBySubnames(subnames);
    	assertEquals("Wrong number of participants",2,participants.size());
    	
    }
    
    public void testSaveParticipantWithMultipleAddressesAndRetire() throws Exception{
    	Participant participant = participantDao.getById(1000);
    	assertEquals("Unexpected addresses",0,participant.getAddresses().size());
    	
    	Address address1 = new Address();
    	
    	Calendar cal = Calendar.getInstance();
    	cal.set(2007, 07, 11);
    	Date addressStartDate = cal.getTime(); 
    	
    	address1.setStartDate(addressStartDate);
    	participant.getAddresses().add(address1);
    	participantDao.save(participant);
    	
    	interruptSession();
    	
    	Participant updatedParticipant1 = participantDao.getById(1000);
    	assertEquals("Participant address not saved",1,updatedParticipant1.getAddresses().size());
    	
    	
    	Address address2 = new Address();
    	
    	cal.set(2009, 07, 11);
    	addressStartDate = cal.getTime();
    	address2.setStartDate(addressStartDate);
    	String streetAddress= "Street address 2";
    	address2.setStreetAddress(streetAddress);
    	
    	updatedParticipant1.getAddresses().add(address2);
    	participantDao.save(updatedParticipant1);
    	
    	interruptSession();
    	
    	Participant updatedParticipant2 = participantDao.getById(1000);
    	assertEquals("Participant address not saved",2,updatedParticipant2.getAddresses().size());
    	
    	Iterator<Address> addressIterator = updatedParticipant2.getAddresses().iterator();
    	Address latestAddress = (Address)addressIterator.next();
    	
    	assertEquals("Participant street address not correct",streetAddress,latestAddress.getStreetAddress());
    	
    	latestAddress.setRetiredIndicatorAsTrue();
    	
    	participantDao.save(updatedParticipant2);
    	
    	interruptSession();
    	
    	Participant updatedParticipant3 = participantDao.getById(1000);
    	assertEquals("Participant address not retired",1,updatedParticipant3.getAddresses().size());
    }
    
    
}
