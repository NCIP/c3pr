package edu.duke.cabig.c3pr.dao;

import static edu.duke.cabig.c3pr.C3PRUseCase.CREATE_PARTICIPANT;
import static edu.duke.cabig.c3pr.C3PRUseCase.SEARCH_SUBJECT;
import static edu.duke.cabig.c3pr.C3PRUseCase.UPDATE_SUBJECT;
import static edu.duke.cabig.c3pr.C3PRUseCase.VERIFY_SUBJECT;
import static edu.nwu.bioinformatics.commons.testing.CoreTestCase.assertContains;

import java.util.Date;
import java.util.List;

import edu.duke.cabig.c3pr.C3PRUseCases;
import edu.duke.cabig.c3pr.domain.Address;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.SystemAssignedIdentifier;
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
     * Test for Creating Participant with basic details and address
     * 
     * @throws Exception
     */

    public void testCreateParticipant() throws Exception {
        Participant participant = new Participant();
        participant.setLastName("Lewis");
        participant.setFirstName("Carrol");
        participant.setAdministrativeGenderCode("Male");
        Date birthDate = new Date();
        participant.setBirthDate(birthDate);
        participant.getAddress().setCity("Charlotte");
        participant.getAddress().setCountryCode("USA");
        participant.getAddress().setStateCode("NC");
        participant.getAddress().setStreetAddress("350 Glen Dale Avenue");
        Address add = new Address();

        dao.save(participant);

        interruptSession();

        Participant savedParticipant = dao.getById(participant.getId());
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
        participant.setAdministrativeGenderCode("Male");
        Date birthDate = new Date();
        participant.setBirthDate(birthDate);
        OrganizationAssignedIdentifier systemIdentifier = new OrganizationAssignedIdentifier();
        HealthcareSite healthcareSite = new HealthcareSite();
        healthcareSite.setName("Local HealthcareSite Name");
        systemIdentifier.setHealthcareSite(healthcareSite);
        systemIdentifier.setValue("Identifier Value");
        systemIdentifier.setType("MRN");
        participant.addIdentifier(systemIdentifier);

        dao.save(participant);

        interruptSession();

        Participant savedParticipant = dao.getById(participant.getId());
        assertEquals("Identifier Value", savedParticipant.getOrganizationAssignedIdentifiers().get(
                        0).getValue());
        assertEquals("Local HealthcareSite Name", savedParticipant
                        .getOrganizationAssignedIdentifiers().get(0).getHealthcareSite().getName());
    }

    /**
     * Test for Retrieving Participant with organization assigned identifier
     * 
     * @throws Exception
     */

}