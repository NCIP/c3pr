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
import edu.duke.cabig.c3pr.domain.RaceCode;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.SystemAssignedIdentifier;
import edu.duke.cabig.c3pr.tools.Configuration;
import edu.duke.cabig.c3pr.utils.DaoTestCase;

/**
 * JUnit Tests for ParticipantDao
 * 
 * @author Priyatam
 * @testType unit
 */
@C3PRUseCases( { CREATE_PARTICIPANT, UPDATE_SUBJECT, VERIFY_SUBJECT, SEARCH_SUBJECT })
public class ParticipantDaoTest extends DaoTestCase {
    private ParticipantDao dao = (ParticipantDao) getApplicationContext().getBean("participantDao");

    private HealthcareSiteDao healthcareSiteDao = (HealthcareSiteDao) getApplicationContext()
                    .getBean("healthcareSiteDao");
    private OrganizationDao organizationDao = (OrganizationDao) getApplicationContext().getBean("organizationDao");

    /**
     * Test for loading a Participant by Id
     * 
     * @throws Exception
     */
    public void testGetById() throws Exception {
        Participant participant = dao.getById(1000);
        assertNotNull("Participant 1 not found", participant);
        assertEquals("Wrong last name", "Clooney", participant.getLastName());
    }

    /**
     * Test for loading all Participants
     * 
     * @throws Exception
     */
    public void testGetAll() throws Exception {
        List<Participant> actual = dao.getAll();
        assertEquals(4, actual.size());
        List<Integer> ids = collectIds(actual);
        assertContains("Wrong Participant found", ids, 1000);
        assertContains("Wrong Participant found", ids, 1001);
        assertContains("Wrong Participant found", ids, 1002);
    }

    /**
     * Test for retrieving all Participant Assignments associated with this Participant
     * 
     * @throws Exception
     */
    public void testGetStudySubjects() throws Exception {
        Participant participant = dao.getById(1000);
        List<StudySubject> studyPartIds = participant.getStudySubjects();
        assertEquals("Wrong number of Study Participant Identifiers", 2, studyPartIds.size());
        List<Integer> ids = collectIds(studyPartIds);

        assertContains("Missing expected Study Participant Identifier", ids, 1000);
        assertContains("Missing expected Study Participant Identifier", ids, 1001);
    }

    /**
     * Test for searching Participants without a wildcard
     * 
     * @throws Exception
     */
    public void testSearchParticipantSimple() throws Exception {
        Participant searchCriteria = new Participant();
        searchCriteria.setLastName("Clooney");
        List<Participant> results = dao.searchByExample(searchCriteria);
        assertEquals("Wrong number of Participants", 1, results.size());
    }

    /**
     * Test for searching Participants using wildcards
     * 
     * @throws Exception
     */
    public void testSearchParticipantSimpleByWildCards() throws Exception {
        Participant searchCriteria = new Participant();
        searchCriteria.setLastName("Clo%ey");
        List<Participant> results = dao.searchByExample(searchCriteria, true);
        assertEquals("Wrong number of Participants", 1, results.size());
    }

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

        dao.save(participant);

        interruptSession();

        Participant savedParticipant = dao.getById(participant.getId());
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

        dao.save(participant);

        interruptSession();

        Participant savedParticipant = dao.getById(participant.getId());
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

        dao.save(participant);

        interruptSession();

        Participant savedParticipant = dao.getById(participant.getId());
        assertEquals("Identifier Value", savedParticipant.getOrganizationAssignedIdentifiers().get(
                        0).getValue());

        interruptSession();
        OrganizationAssignedIdentifier organizationAssignedIdentifier = new OrganizationAssignedIdentifier();
        organizationAssignedIdentifier.setHealthcareSite(healthcareSiteDao.getById(1001));
        organizationAssignedIdentifier.setValue("Identifier Value");
        organizationAssignedIdentifier.setType("MRN");
        List<Participant> pList = dao.searchByOrgIdentifier(organizationAssignedIdentifier);
        assertEquals("wrong size of list", 1, pList.size());
    }

    /**
     * Test for Creating Participant with organization assigned identifier
     * 
     * @throws Exception
     */

    public void testCreateParticipantWithOrganizationAssignedIdentifier() throws Exception {
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

        dao.save(participant);

        interruptSession();

        Participant savedParticipant = dao.getById(participant.getId());
        assertEquals("Identifier Value", savedParticipant.getOrganizationAssignedIdentifiers().get(
                        0).getValue());
        assertEquals("duke healthcare", savedParticipant
                        .getOrganizationAssignedIdentifiers().get(0).getHealthcareSite().getName());
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
        
        List<Participant> participants = dao.searchBySystemAssignedIdentifier(systemAssignedIdentifier);
        assertEquals("Expected to get 1 participant",1, participants.size());
        
        SystemAssignedIdentifier systemAssignedIdentifier1 = new SystemAssignedIdentifier();
        systemAssignedIdentifier1.setSystemName("nci");
        systemAssignedIdentifier1.setType("local");
        systemAssignedIdentifier1.setValue("nci");
        
        List<Participant> participants1 = dao.searchBySystemAssignedIdentifier(systemAssignedIdentifier1);
        assertEquals("Expected to get 1 participant",1, participants1.size());
        
        SystemAssignedIdentifier systemAssignedIdentifier2 = new SystemAssignedIdentifier();
        systemAssignedIdentifier2.setSystemName("nci123");
        systemAssignedIdentifier2.setType("local");
        systemAssignedIdentifier2.setValue("nci");
        
        List<Participant> participants2 = dao.searchBySystemAssignedIdentifier(systemAssignedIdentifier2);
        assertEquals("Expected to get 0 participant",0, participants2.size());
    }

}