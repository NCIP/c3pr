package edu.duke.cabig.c3pr.dao;

import static edu.duke.cabig.c3pr.C3PRUseCase.ADD_DISEASE_SUBJECT;
import static edu.duke.cabig.c3pr.C3PRUseCase.ASSIGN_ARM;
import static edu.duke.cabig.c3pr.C3PRUseCase.ASSIGN_EXISTING_PARTICIPANT;
import static edu.duke.cabig.c3pr.C3PRUseCase.ASSIGN_REGISTERED_PARTICIPANT;
import static edu.duke.cabig.c3pr.C3PRUseCase.CREATE_INCOMPLETE_REGISTERATION;
import static edu.duke.cabig.c3pr.C3PRUseCase.CREATE_LOCAL_REGISTERATION;
import static edu.duke.cabig.c3pr.C3PRUseCase.UPDATE_REGISTERATION_STATUS;
import edu.duke.cabig.c3pr.domain.*;
import static edu.nwu.bioinformatics.commons.testing.CoreTestCase.assertContains;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import edu.duke.cabig.c3pr.C3PRUseCases;
import edu.duke.cabig.c3pr.constants.OrganizationIdentifierTypeEnum;
import edu.duke.cabig.c3pr.constants.RegistrationDataEntryStatus;
import edu.duke.cabig.c3pr.constants.RegistrationWorkFlowStatus;
import edu.duke.cabig.c3pr.constants.ScheduledEpochDataEntryStatus;
import edu.duke.cabig.c3pr.constants.ScheduledEpochWorkFlowStatus;
import edu.duke.cabig.c3pr.utils.DaoTestCase;
import edu.duke.cabig.c3pr.utils.IdentifierGenerator;
import edu.duke.cabig.c3pr.xml.XmlMarshaller;
import gov.nih.nci.common.exception.XMLUtilityException;

/**
 * JUnit Tests for ParticipantDao
 *
 * @author Priyatam
 * @testType unit
 */
@C3PRUseCases( { ADD_DISEASE_SUBJECT, ASSIGN_EXISTING_PARTICIPANT, ASSIGN_REGISTERED_PARTICIPANT,
        CREATE_INCOMPLETE_REGISTERATION, CREATE_LOCAL_REGISTERATION, ASSIGN_ARM,
        UPDATE_REGISTERATION_STATUS })
public class StudySubjectDaoTest extends DaoTestCase {
    private ParticipantDao dao;

    private StudySiteDao studySiteDao;

    private EpochDao epochDao;

    private StudyDao studyDao;

    private ICD9DiseaseSiteDao icd9DiseaseSiteDao;

    private StudySubjectDao studySubjectDao;

    private ScheduledEpochDao scheduledEpochDao;

	private HealthcareSiteDao healthcareSiteDao;

	private StudySiteStudyVersionDao studySiteStudyVersionDao;

    private StudySubjectStudyVersionDao studySubjectStudyVersionDao;

    private XmlMarshaller xmlUtility;

    private XmlMarshaller xmlUtilityStudy;

    private IdentifierGenerator identifierGenerator ;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        dao = (ParticipantDao) getApplicationContext().getBean("participantDao");
        studySiteDao = (StudySiteDao) getApplicationContext().getBean("studySiteDao");
        epochDao = (EpochDao) getApplicationContext().getBean("epochDao");
        icd9DiseaseSiteDao = (ICD9DiseaseSiteDao) getApplicationContext().getBean("icd9DiseaseSiteDao");
        studySubjectDao = (StudySubjectDao) getApplicationContext().getBean("studySubjectDao");
        studySubjectDao.setStudySiteDao(studySiteDao);
        studySubjectDao.setParticipantDao(dao);
        studySiteStudyVersionDao = (StudySiteStudyVersionDao) getApplicationContext().getBean("studySiteStudyVersionDao");
        studySubjectStudyVersionDao = (StudySubjectStudyVersionDao) getApplicationContext().getBean("studySubjectStudyVersionDao");
        scheduledEpochDao = (ScheduledEpochDao) getApplicationContext()
                        .getBean("scheduledEpochDao");
        studyDao = (StudyDao) getApplicationContext().getBean("studyDao");
        healthcareSiteDao = (HealthcareSiteDao) getApplicationContext()
                        .getBean("healthcareSiteDao");
        xmlUtility = new XmlMarshaller((String) getApplicationContext().getBean(
                        "c3pr-registration-xml-castor-mapping"));
        xmlUtilityStudy = new XmlMarshaller((String) getApplicationContext().getBean(
                        "c3pr-study-xml-castorMapping"));
        identifierGenerator = (IdentifierGenerator) getApplicationContext().getBean("identifierGenerator");
    }
    
    public void testDeleteStudySubjectStudyVersion() throws Exception {
        StudySubject loadedStudySubject = studySubjectDao.getById(1000);
        ScheduledEpoch scheduledEpoch =  scheduledEpochDao.getById(1000);
        Epoch epoch = epochDao.getById(1000);
        assertEquals("2 epochs should be equal",epoch,scheduledEpoch.getEpoch());
        loadedStudySubject.getStudySubjectStudyVersion().getScheduledEpochs().remove(scheduledEpoch);
        interruptSession();
        epoch =  epochDao.getById(1000);
        assertNotNull("Epoch cannot be deleted by scheduled epoch",epoch);
        assertNotNull("Eligibility criteria cannot be deleted by scheduled epoch",epoch.getEligibilityCriteria());
        assertEquals("Wrong number of eligibility criteria. Some may have been deleted by scheduled epoch",3,epoch.getEligibilityCriteria().size());
    }

    /*
     *
     */
    public void testForReport() {
        // Fetch the subject with firstName Rudolph
        List<Participant> participantList = studySubjectDao.getParticipantDao().getByFirstName("Rudolph");
        Participant participant = participantList.get(0);
        try {
            String outputFileName = "TestReport.txt";

            // Create FileReader Object
            FileWriter outputFileReader = new FileWriter(outputFileName);
            PrintWriter outputStream = new PrintWriter(outputFileReader);
            outputStream
                            .println("+---------- Auto generated report based on data retrieved from the c3pr database. ----------+");
            outputStream.println("");
            outputStream.println("");
            outputStream.println("--- Retrieving the participant ---");
            outputStream.println("Name: " + participant.getFullName());
            outputStream.println("Gender: " + participant.getAdministrativeGenderCode());
            outputStream.println("Race: " + participant.getRaceCode());
            outputStream.println("Address: " + participant.getAddress().getStreetAddress() + " "
                            + participant.getAddress().getCity() + " "
                            + participant.getAddress().getStateCode() + " "
                            + participant.getAddress().getCountryCode());
            outputStream.println("");
            outputStream.println("");
            outputStream
                            .println("--- Retrieving the Registration details for the selected participant. ---");
            outputStream.println("--- Participant has " + participant.getStudySubjects().size()
                            + " Registration(s). ---");
            outputStream.println("");

            StudySubject ss = null;
            for (int i = 0; i < participant.getStudySubjects().size(); i++) {
                // fetch all registrations for Rudolph.
                ss = studySubjectDao.getById(participant.getStudySubjects().get(i).getId());
                outputStream.println("Registration: " + i);
                try {
                    String xml = xmlUtility.toXML(ss);
                    String newXml = new XMLOutputter(Format.getPrettyFormat())
                                    .outputString(new SAXBuilder().build(new StringReader(xml)));
                    outputStream.println(newXml);
                }
                catch (XMLUtilityException xue) {
                    log.error(xue.getMessage());
                }
                catch (JDOMException je) {
                    log.error(je.getMessage());
                }
            }
            outputStream.close();
        }
        catch (IOException e) {
            System.out.println("IOException:");
            e.printStackTrace();
        }
    }

    /*
     * Test for the advanced search for studies(reporting use case).
     */
    public void testAdvancedStudySearch1() {
        List<StudySubject> ssList;
        {
            Study study = new LocalStudy();
            study.setShortTitleText("%short%");

            Participant participant = new Participant();

            StudySite studySite = new StudySite();
            study.addStudySite(studySite);

            StudySubject studySubject = new StudySubject();
            studySubject.setStudySite(studySite);
            studySubject.setParticipant(participant);

            ssList = studySubjectDao.advancedStudySearch(studySubject);
            assertEquals(4, ssList.size());
        }
        interruptSession();
        {
            Study study = new LocalStudy();
            study.setShortTitleText("%short%2");

            Participant participant = new Participant();

            StudySite studySite = new StudySite();
            study.addStudySite(studySite);

            StudySubject studySubject = new StudySubject();
            studySubject.setStudySite(studySite);
            studySubject.setParticipant(participant);

            ssList = studySubjectDao.advancedStudySearch(studySubject);
            assertEquals(1, ssList.size());
        }
        interruptSession();

        {
            Study study = new LocalStudy();
            study.setShortTitleText("%short%1");

            Participant participant = new Participant();

            StudySite studySite = new StudySite();
            study.addStudySite(studySite);

            StudySubject studySubject = new StudySubject();
            studySubject.setStudySite(studySite);
            studySubject.setParticipant(participant);

            ssList = studySubjectDao.advancedStudySearch(studySubject);
            assertEquals(2, ssList.size());
        }
        interruptSession();
    }

    /*
     * Test for the advanced search for studies(reporting use case).
     */
    public void testAdvancedStudySearch2() {
        List<StudySubject> ssList;
        {
            Study study = new LocalStudy();

            SystemAssignedIdentifier id = new SystemAssignedIdentifier();
            id.setValue("nci%");
            study.addIdentifier(id);

            Participant participant = new Participant();

            StudySite studySite = new StudySite();
            study.addStudySite(studySite);

            StudySubject studySubject = new StudySubject();
            studySubject.setStudySite(studySite);
            studySubject.setParticipant(participant);

            ssList = studySubjectDao.advancedStudySearch(studySubject);
            assertEquals(4, ssList.size());
        }
        interruptSession();
        {
            Study study = new LocalStudy();

            SystemAssignedIdentifier id = new SystemAssignedIdentifier();
            id.setValue("nci1");
            study.addIdentifier(id);

            Participant participant = new Participant();

            StudySite studySite = new StudySite();
            study.addStudySite(studySite);

            StudySubject studySubject = new StudySubject();
            studySubject.setStudySite(studySite);
            studySubject.setParticipant(participant);

            ssList = studySubjectDao.advancedStudySearch(studySubject);
            assertEquals(2, ssList.size());
        }
        interruptSession();
        {
            Study study = new LocalStudy();

            OrganizationAssignedIdentifier id = new OrganizationAssignedIdentifier();
            id.setValue("nci2");
            study.addIdentifier(id);

            Participant participant = new Participant();

            StudySite studySite = new StudySite();
            study.addStudySite(studySite);

            StudySubject studySubject = new StudySubject();
            studySubject.setStudySite(studySite);
            studySubject.setParticipant(participant);

            ssList = studySubjectDao.advancedStudySearch(studySubject);
            assertEquals(1, ssList.size());
        }
        interruptSession();
    }

    /*
     * Test for the advanced search for participant(reporting use case).
     */
    public void testAdvancedParticipantSearch1() {
        List<StudySubject> ssList;
        {
            Study study = new LocalStudy();


            Participant participant = new Participant();

            participant.setFirstName("Rudolph");

            StudySite studySite = new StudySite();
            study.addStudySite(studySite);

            StudySubject studySubject = new StudySubject();
            studySubject.setStudySite(studySite);
            studySubject.setParticipant(participant);

            ssList = studySubjectDao.advancedStudySearch(studySubject);
            assertEquals(1, ssList.size());
        }
        interruptSession();
        {
            Study study = new LocalStudy();


            Participant participant = new Participant();

            participant.setLastName("Douglas");

            StudySite studySite = new StudySite();
            study.addStudySite(studySite);

            StudySubject studySubject = new StudySubject();
            studySubject.setStudySite(studySite);
            studySubject.setParticipant(participant);

            ssList = studySubjectDao.advancedStudySearch(studySubject);
            assertEquals(1, ssList.size());
        }
        interruptSession();
        {
            Study study = new LocalStudy();


            Participant participant = new Participant();

            participant.setFirstName("A%");
            participant.setLastName("%o%");

            StudySite studySite = new StudySite();
            study.addStudySite(studySite);

            StudySubject studySubject = new StudySubject();
            studySubject.setStudySite(studySite);
            studySubject.setParticipant(participant);

            ssList = studySubjectDao.advancedStudySearch(studySubject);
            assertEquals(4, ssList.size());
        }
        interruptSession();
    }

    /*
     * Test for the advanced search for participant(reporting use case).
     */
    public void testAdvancedPaticipantSearch2() {
        List<StudySubject> ssList;
        {
            Study study = new LocalStudy();


            Participant participant = new Participant();
            OrganizationAssignedIdentifier id2 = new OrganizationAssignedIdentifier();
            id2.setValue("mrn");
            participant.addIdentifier(id2);


            StudySite studySite = new StudySite();
            study.addStudySite(studySite);

            StudySubject studySubject = new StudySubject();
            studySubject.setStudySite(studySite);
            studySubject.setParticipant(participant);

            ssList = studySubjectDao.advancedStudySearch(studySubject);
            assertEquals(1, ssList.size());
        }
        interruptSession();
    }

    public void testCurrentScheduledEpoch() throws Exception {
        StudySubject studySubject = new StudySubject();
        
        Epoch epoch1 = new Epoch();
        epoch1.setEpochOrder(1);
        
        ScheduledEpoch scheduledEpoch1 = new ScheduledEpoch();
        scheduledEpoch1.setStartDate((new GregorianCalendar(1990, 1, 2)).getTime());
        scheduledEpoch1.setEpoch(epoch1);
        studySubject.addScheduledEpoch(scheduledEpoch1);
        
        
        Epoch epoch2 = new Epoch();
        epoch2.setEpochOrder(2);
        ScheduledEpoch scheduledEpoch2 = new ScheduledEpoch();
        scheduledEpoch2.setEpoch(epoch2);
        
        studySubject.addScheduledEpoch(scheduledEpoch2);
        assertEquals("current epoch is wrong", studySubject.getScheduledEpoch(),
                        scheduledEpoch2);
    }
    
    
    public void testGetScheduledEpoch() throws Exception {
        StudySubject studySubject = studySubjectDao.getById(1000);
        ScheduledEpoch scheduledEpoch = scheduledEpochDao.getById(1001);
        assertEquals("current epoch is wrong", studySubject.getScheduledEpoch(),
        		scheduledEpoch);
    }

    /**
     * Test Saving of a basic Study Subject
     *
     * @throws Exception
     */
    public void testSaveBasicRegistrationWeb() throws Exception {
        Integer savedId;
        {
            // formbackingobject
            Object command = formBackingObject();

            // binding process
            Object afterBind = bind(command);

            // select study & subject
            Object onBindFormObject = bindSelectSubjectStudy(afterBind);
            interruptSession();

            StudySubject studySubject1 = (StudySubject) onBindFormObject;
            OrganizationAssignedIdentifier id = studySubject1.getOrganizationAssignedIdentifiers()
                            .get(0);
            id.setHealthcareSite(healthcareSiteDao.getById(1002));
            id.setType(OrganizationIdentifierTypeEnum.MRN);
            id.setValue("Test");
            Object saved = reviewAndSave(currentFormObject(onBindFormObject));

            StudySubject studySubject = (StudySubject) saved;

            savedId = studySubject.getId().intValue();
            assertNotNull("The registration didn't get an id", savedId);
        }

        interruptSession();
        {
            StudySubject loaded = studySubjectDao.getById(savedId);
            assertNotNull("Could not reload registration with id " + savedId, loaded);
            assertEquals("Wrong number of scheduled epochs", 1, loaded.getScheduledEpochs().size());
            ScheduledEpoch scheduledEpoch = loaded
                            .getScheduledEpoch();
            assertEquals("Wrong eligibility indicator", false, scheduledEpoch
                            .getEligibilityIndicator().booleanValue());
            assertEquals("Wrong number of subject eligibility answers", 3, scheduledEpoch
                            .getSubjectEligibilityAnswers().size());
            assertEquals("Wrong number of subject inclusion eligibility answers", 2,
            		scheduledEpoch.getInclusionEligibilityAnswers().size());
            assertEquals("Wrong number of subject exclusion eligibility answers", 1,
            		scheduledEpoch.getExclusionEligibilityAnswers().size());
            assertEquals("Wrong number of subject stratification answers", 1,
            		scheduledEpoch.getSubjectStratificationAnswers().size());
            assertEquals("Wrong number of identifier", 1, loaded.getIdentifiers().size());
            assertEquals("Wrong registration status", "INCOMPLETE", loaded.getRegDataEntryStatus()
                            .getName());
            assertEquals("Wrong epoch status", "INCOMPLETE", loaded.getScheduledEpoch()
                            .getScEpochDataEntryStatus().getName());
        }
        interruptSession();
    }

    /**
     * Test for loading a Study Subject by Id
     *
     * @throws Exception
     */
    public void testGetById() throws Exception {
        StudySubject studySubject = studySubjectDao.getById(1000);
        assertNotNull("Study Subject 1 not found");
        assertEquals("Wrong last name", "Clooney", studySubject.getParticipant().getLastName());
    }

    /**
     * Test for loading all Study Subjects
     *
     * @throws Exception
     */
    public void testGetAll() throws Exception {
        List<StudySubject> actual = studySubjectDao.getAll();
        assertEquals(5, actual.size());
        List<Integer> ids = collectIds(actual);
        assertContains("Wrong Study Subject found", ids, 1000);
        assertContains("Wrong Study Subject found", ids, 1001);
        assertContains("Wrong Study Subject found", ids, 1002);
    }

    /**
     * Test for retrieving all Participant Assignments associated with this Participant
     *
     * @throws Exception
     */
    public void testGetStudySubjects() throws Exception {
        Participant participant = dao.getById(1000);
        List<StudySubject> studyPartIds = participant.getStudySubjects();
        assertEquals("Wrong number of Study Participant Identifiers", 1, studyPartIds.size());
        List<Integer> ids = collectIds(studyPartIds);

        assertContains("Missing expected Study Participant Identifier", ids, 1000);
    }

    public void testGetStudySubjectsByIdentifiers() throws Exception {
    	SystemAssignedIdentifier sysIdentifier = new SystemAssignedIdentifier();
    	sysIdentifier.setSystemName("nci");
    	sysIdentifier.setType("local");
    	sysIdentifier.setValue("grid");
    	List<Identifier> studySubjectSysIdentifiers = new ArrayList<Identifier>();
    	studySubjectSysIdentifiers.add(sysIdentifier);

        List<StudySubject> studyPartsBySys = studySubjectDao.getByIdentifiers(studySubjectSysIdentifiers);
        assertSame("Wrong number of Study Participants", 1, studyPartsBySys.size());

    	StudySubject studySubject = studySubjectDao.getById(1000);

        OrganizationAssignedIdentifier orgIdentifier = new OrganizationAssignedIdentifier();
        orgIdentifier.setHealthcareSite(studySubject.getStudySite().getHealthcareSite());
        orgIdentifier.setType(OrganizationIdentifierTypeEnum.COORDINATING_CENTER_IDENTIFIER);
        orgIdentifier.setValue("nci1");

    	studySubject.addIdentifier(orgIdentifier);
    	studySubjectDao.save(studySubject);

    	List<Identifier> iList = new ArrayList<Identifier>();
    	iList.add((Identifier) orgIdentifier);

    	List<StudySubject> studyPartsByOrg = studySubjectDao.getByIdentifiers(iList);
        assertSame("Wrong number of Study Participants", 1, studyPartsByOrg.size());

    }

    public void testCreateRegistrationWithAllAssociations() throws Exception {
        Integer savedId;
        {
            StudySubject studySubject = new StudySubject();
            Participant participant = dao.getById(1001);
            StudySite studySite = studySubjectDao.getStudySiteDao().getById(1001);
            studySubject.setParticipant(participant);
            studySubject.setStudySite(studySite);
            ScheduledEpoch scheduledEpochFirst = new ScheduledEpoch();
            scheduledEpochFirst.setEpoch(epochDao.getById(1000));
            studySubject.addScheduledEpoch(scheduledEpochFirst);
            ScheduledEpoch scheduledTreatmentEpoch = studySubject
                            .getScheduledEpoch();
            List criterias = scheduledTreatmentEpoch.getEpoch()
                            .getInclusionEligibilityCriteria();
            for (int i = 0; i < criterias.size(); i++) {
                SubjectEligibilityAnswer subjectEligibilityAnswer = new SubjectEligibilityAnswer();
                subjectEligibilityAnswer.setEligibilityCriteria((EligibilityCriteria) criterias
                                .get(i));
                scheduledTreatmentEpoch.addSubjectEligibilityAnswers(subjectEligibilityAnswer);
            }
            criterias = scheduledTreatmentEpoch.getEpoch()
                            .getExclusionEligibilityCriteria();
            for (int i = 0; i < criterias.size(); i++) {
                SubjectEligibilityAnswer subjectEligibilityAnswer = new SubjectEligibilityAnswer();
                subjectEligibilityAnswer.setEligibilityCriteria((EligibilityCriteria) criterias
                                .get(i));
                scheduledTreatmentEpoch.addSubjectEligibilityAnswers(subjectEligibilityAnswer);
            }
            List<StratificationCriterion> stratifications = scheduledTreatmentEpoch
                            .getEpoch().getStratificationCriteria();
            for (StratificationCriterion stratificationCriterion : stratifications) {
                stratificationCriterion.getPermissibleAnswers().size();
                SubjectStratificationAnswer subjectStratificationAnswer = new SubjectStratificationAnswer();
                subjectStratificationAnswer.setStratificationCriterion(stratificationCriterion);
                scheduledTreatmentEpoch
                                .addSubjectStratificationAnswers(subjectStratificationAnswer);
            }
            studySubject.setTreatingPhysician(studySubject.getStudySite()
                            .getStudyInvestigatorsInternal().get(0));
            studySubject.getDiseaseHistory().setIcd9DiseaseSite(icd9DiseaseSiteDao.getById(1000));
            studySubject.getDiseaseHistory().setOtherPrimaryDiseaseCode(
                            "Other Primary Disease Code");
            List<SubjectEligibilityAnswer> subList = (studySubject
                            .getScheduledEpoch()).getSubjectEligibilityAnswers();
            for (SubjectEligibilityAnswer subjectEligibilityAnswer : subList) {
                if (subjectEligibilityAnswer.getEligibilityCriteria() instanceof InclusionEligibilityCriteria) {
                    subjectEligibilityAnswer.setAnswerText("yes");
                }
                else {
                    subjectEligibilityAnswer.setAnswerText("no");
                }
            }
            (studySubject.getScheduledEpoch())
                            .setEligibilityIndicator(evaluateEligibilityIndicator(studySubject));

            List<SubjectStratificationAnswer> subList1 = (studySubject
                            .getScheduledEpoch()).getSubjectStratificationAnswers();
            for (SubjectStratificationAnswer subjectStratificationAnswer : subList1) {
                subjectStratificationAnswer
                                .setStratificationCriterionAnswer(subjectStratificationAnswer
                                                .getStratificationCriterion()
                                                .getPermissibleAnswers().get(0));
            }
            ScheduledEpoch scheduledEpoch = (studySubject
                            .getScheduledEpoch());
            scheduledEpoch.addScheduledArm(new ScheduledArm());
            ScheduledArm scheduledArm = scheduledEpoch.getScheduledArm();
            scheduledArm.setArm((scheduledEpoch.getEpoch()).getArms()
                            .get(0));
            studySubject.setRegDataEntryStatus(RegistrationDataEntryStatus.COMPLETE);

            studySubject = studySubjectDao.merge(studySubject);

            savedId = studySubject.getId().intValue();
            assertNotNull("The registration didn't get an id", savedId);
        }

        interruptSession();
        {
            StudySubject loaded = studySubjectDao.getById(savedId);
            assertNotNull("Could not reload registration with id " + savedId, loaded);
            assertEquals("Wrong number of scheduled epochs", 1, loaded.getScheduledEpochs().size());
            assertEquals("Wrong number of scheduled treatment epochs", 1, loaded
                            .getScheduledEpochs().size());
            ScheduledEpoch scheduledTreatmentEpoch = loaded
                            .getScheduledEpoch();
            assertEquals("Wrong eligibility indicator", true, scheduledTreatmentEpoch
                            .getEligibilityIndicator().booleanValue());
            assertEquals("Wrong number of subject eligibility answers", 3, scheduledTreatmentEpoch
                            .getSubjectEligibilityAnswers().size());
            assertEquals("Wrong number of subject inclusion eligibility answers", 2,
                            scheduledTreatmentEpoch.getInclusionEligibilityAnswers().size());
            assertEquals("Wrong number of subject exclusion eligibility answers", 1,
                            scheduledTreatmentEpoch.getExclusionEligibilityAnswers().size());
            assertEquals("Wrong number of subject stratification answers", 1,
                            scheduledTreatmentEpoch.getSubjectStratificationAnswers().size());
        }
    }

    private Object formBackingObject() {
        StudySubject studySubject = new StudySubject();
        return studySubject;
    }

    private Object bind(Object command) {
        StudySubject studySubject = (StudySubject) command;
        studySubject.setParticipant(dao.getById(1010));
        studySubject.setStudySite(studySiteDao.getById(1010));
        return studySubject;
    }

    protected StudySubject bindSelectSubjectStudy(Object command) {
        StudySubject studySubject = (StudySubject) command;
        ScheduledEpoch scheduledEpochFirst = new ScheduledEpoch();
        scheduledEpochFirst.setEpoch(epochDao.getById(1000));
        int a = (scheduledEpochFirst.getEpoch()).getArms().size();
        studySubject.addScheduledEpoch(scheduledEpochFirst);
        return buildCommandObject(studySubject);
    }

    protected StudySubject bindNewScheduledEpoch(Object command) {
        StudySubject studySubject = (StudySubject) command;
        ScheduledEpoch scheduledEpochFirst = new ScheduledEpoch();
        scheduledEpochFirst.setEpoch(epochDao.getById(1002));
        int a = (scheduledEpochFirst.getEpoch()).getArms().size();
        studySubject.addScheduledEpoch(scheduledEpochFirst);
        return buildCommandObject(studySubject);
    }

    private StudySubject buildCommandObject(StudySubject studySubject) {
            ScheduledEpoch scheduledTreatmentEpoch = studySubject
                            .getScheduledEpoch();
            List criterias = scheduledTreatmentEpoch.getEpoch()
                            .getInclusionEligibilityCriteria();
            for (int i = 0; i < criterias.size(); i++) {
                SubjectEligibilityAnswer subjectEligibilityAnswer = new SubjectEligibilityAnswer();
                subjectEligibilityAnswer.setEligibilityCriteria((EligibilityCriteria) criterias
                                .get(i));
                scheduledTreatmentEpoch.addSubjectEligibilityAnswers(subjectEligibilityAnswer);
            }
            criterias = scheduledTreatmentEpoch.getEpoch()
                            .getExclusionEligibilityCriteria();
            for (int i = 0; i < criterias.size(); i++) {
                SubjectEligibilityAnswer subjectEligibilityAnswer = new SubjectEligibilityAnswer();
                subjectEligibilityAnswer.setEligibilityCriteria((EligibilityCriteria) criterias
                                .get(i));
                scheduledTreatmentEpoch.addSubjectEligibilityAnswers(subjectEligibilityAnswer);
            }
            List<StratificationCriterion> stratifications = scheduledTreatmentEpoch
                            .getEpoch().getStratificationCriteria();
            for (StratificationCriterion stratificationCriterion : stratifications) {
                stratificationCriterion.getPermissibleAnswers().size();
                SubjectStratificationAnswer subjectStratificationAnswer = new SubjectStratificationAnswer();
                subjectStratificationAnswer.setStratificationCriterion(stratificationCriterion);
                scheduledTreatmentEpoch
                                .addSubjectStratificationAnswers(subjectStratificationAnswer);
            }
        return studySubject;
    }

    private Object bindEnrollmentDetails(Object command) {
        StudySubject studySubject = (StudySubject) command;
        studySubject.setTreatingPhysician(studySubject.getStudySite()
                        .getStudyInvestigatorsInternal().get(0));
        return studySubject;
    }

    private Object bindEnrollmentDetailsOtherTreatingPhysician(Object command) {
        StudySubject studySubject = (StudySubject) command;
        studySubject.setOtherTreatingPhysician("other treating physician");
        return studySubject;
    }

    private Object bindDiseaseDetails(Object command) {
        StudySubject studySubject = (StudySubject) command;
        studySubject.getDiseaseHistory().setIcd9DiseaseSite(icd9DiseaseSiteDao.getById(1000));
        studySubject.getDiseaseHistory().setOtherPrimaryDiseaseCode("Other Primary Disease Code");
        return studySubject;
    }

    private Object bindEligibility(Object command) {
        StudySubject studySubject = (StudySubject) command;
        List<SubjectEligibilityAnswer> subList = (studySubject
                        .getScheduledEpoch()).getSubjectEligibilityAnswers();
        for (SubjectEligibilityAnswer subjectEligibilityAnswer : subList) {
            if (subjectEligibilityAnswer.getEligibilityCriteria() instanceof InclusionEligibilityCriteria) {
                subjectEligibilityAnswer.setAnswerText("yes");
            }
            else {
                subjectEligibilityAnswer.setAnswerText("no");
            }
        }
        (studySubject.getScheduledEpoch())
                        .setEligibilityIndicator(evaluateEligibilityIndicator(studySubject));
        return studySubject;
    }

    private Object bindStratification(Object command) {
        StudySubject studySubject = (StudySubject) command;
        List<SubjectStratificationAnswer> subList1 = (studySubject
                        .getScheduledEpoch()).getSubjectStratificationAnswers();
        for (SubjectStratificationAnswer subjectStratificationAnswer : subList1) {
            subjectStratificationAnswer
                            .setStratificationCriterionAnswer(subjectStratificationAnswer
                                            .getStratificationCriterion().getPermissibleAnswers()
                                            .get(0));
        }
        return studySubject;
    }

    private Object bindRandomization(Object command) {
        StudySubject studySubject = (StudySubject) command;
        ScheduledEpoch scheduledEpoch = (studySubject
                        .getScheduledEpoch());
        scheduledEpoch.addScheduledArm(new ScheduledArm());
        ScheduledArm scheduledArm = scheduledEpoch.getScheduledArm();
        scheduledArm.setArm((scheduledEpoch.getEpoch()).getArms().get(0));
        return studySubject;
    }

    private Object reviewAndSave(Object command) {
        StudySubject studySubject = (StudySubject) command;
        return createRegistration(studySubject);
    }

    private boolean evaluateEligibilityIndicator(StudySubject studySubject) {
        boolean flag = true;
        List<SubjectEligibilityAnswer> answers = (studySubject
                        .getScheduledEpoch()).getInclusionEligibilityAnswers();
        for (SubjectEligibilityAnswer subjectEligibilityAnswer : answers) {
            String answerText = subjectEligibilityAnswer.getAnswerText();
            if (answerText == null
                            || answerText.equalsIgnoreCase("")
                            || (!answerText.equalsIgnoreCase("Yes") && !answerText
                                            .equalsIgnoreCase("NA"))) {
                flag = false;
                break;
            }
        }
        if (flag) {
            answers = (studySubject.getScheduledEpoch())
                            .getExclusionEligibilityAnswers();
            for (SubjectEligibilityAnswer subjectEligibilityAnswer : answers) {
                String answerText = subjectEligibilityAnswer.getAnswerText();
                if (answerText == null
                                || answerText.equalsIgnoreCase("")
                                || (!answerText.equalsIgnoreCase("No") && !answerText
                                                .equalsIgnoreCase("NA"))) {
                    flag = false;
                    break;
                }
            }
        }
        return flag;
    }

    private Object createRegistration(StudySubject studySubject) {
        studySubject.setRegDataEntryStatus(studySubject.evaluateRegistrationDataEntryStatus());
        studySubject.getScheduledEpoch().setScEpochDataEntryStatus(
                        evaluateScheduledEpochDataEntryStatus(studySubject));
        ScheduledEpoch current = studySubject.getScheduledEpoch();
        if (current instanceof ScheduledEpoch) {
            ScheduledEpoch scheduledTreatmentEpoch = current;
            if (scheduledTreatmentEpoch.getScheduledArm() != null
                            && scheduledTreatmentEpoch.getScheduledArm().getArm() == null) {
                scheduledTreatmentEpoch.removeScheduledArm();
            }
        }
        // evaluate status
        if (studySubject.getScheduledEpoch().getScEpochWorkflowStatus() == ScheduledEpochWorkFlowStatus.PENDING) {
            manageSchEpochWorkFlowStatusIfUnApp(studySubject);
        }
//        if (studySubject.getRegWorkflowStatus() == RegistrationWorkFlowStatus.UNREGISTERED) {
//            manageRegWorkFlowIfUnReg(studySubject);
//        }
        studySubject = studySubjectDao.merge(studySubject);
        return studySubject;
    }

    private static boolean evaluateStratificationIndicator(
                    ScheduledEpoch scheduledTreatmentEpoch) {
        List<SubjectStratificationAnswer> answers = scheduledTreatmentEpoch
                        .getSubjectStratificationAnswers();
        for (SubjectStratificationAnswer subjectStratificationAnswer : answers) {
            if (subjectStratificationAnswer.getStratificationCriterionAnswer() == null) {
                return false;
            }
        }
        return true;
    }

    private StudySubject currentFormObject(Object sessionFormObject) throws Exception {
        StudySubject command = (StudySubject) sessionFormObject;
        if (sessionFormObject != null) {
            if (command.getId() != null) {
                return studySubjectDao.merge(command);
            }
            if (command.getStudySite() != null) {
                studySiteDao.reassociate(command.getStudySite());
                studyDao.reassociate(command.getStudySite().getStudy());
            }
        }
        return command;
    }

    public ScheduledEpochDataEntryStatus evaluateScheduledEpochDataEntryStatus(
                    StudySubject studySubject) {
        ScheduledEpoch scheduledEpoch = studySubject
                        .getScheduledEpoch();
        if (!evaluateStratificationIndicator(scheduledEpoch)) {
            return ScheduledEpochDataEntryStatus.INCOMPLETE;
        }
        if (!scheduledEpoch.getEligibilityIndicator()) {
            return ScheduledEpochDataEntryStatus.INCOMPLETE;
        }
        return ScheduledEpochDataEntryStatus.COMPLETE;
    }

    private void manageSchEpochWorkFlowStatusIfUnApp(StudySubject studySubject) {
        ScheduledEpoch scheduledEpoch = studySubject.getScheduledEpoch();
        if (scheduledEpoch.getScEpochDataEntryStatus() == ScheduledEpochDataEntryStatus.COMPLETE
                        && studySubject.getRegDataEntryStatus() == RegistrationDataEntryStatus.COMPLETE) {
            if (studySubject.getStudySite().getStudy().getMultiInstitutionIndicator()) {
                // broadcase message to co-ordinating center
                try {
                    scheduledEpoch.setScEpochWorkflowStatus(ScheduledEpochWorkFlowStatus.PENDING);
                }
                catch (Exception e) {
                    e.printStackTrace();
                    scheduledEpoch
                                    .setScEpochWorkflowStatus(ScheduledEpochWorkFlowStatus.PENDING);
                }
            }
            else {
                if (studySubject.getScheduledEpoch().getRequiresArm()
                                && (studySubject.getScheduledEpoch())
                                                .getScheduledArm() == null) {
                    scheduledEpoch
                                    .setScEpochWorkflowStatus(ScheduledEpochWorkFlowStatus.PENDING);
                }
                else {
                    // logic for accrual ceiling check
                    scheduledEpoch.setScEpochWorkflowStatus(ScheduledEpochWorkFlowStatus.REGISTERED);
                }
            }
        }
        else {
            scheduledEpoch.setScEpochWorkflowStatus(ScheduledEpochWorkFlowStatus.PENDING);
        }
    }

    private void manageRegWorkFlowIfUnReg(StudySubject studySubject) {
        ScheduledEpoch scheduledEpoch = studySubject.getScheduledEpoch();
//        if (scheduledEpoch.getScEpochWorkflowStatus() == ScheduledEpochWorkFlowStatus.DISAPPROVED) {
//            studySubject.setRegWorkflowStatus(RegistrationWorkFlowStatus.DISAPPROVED);
//        }
        if (scheduledEpoch.getScEpochWorkflowStatus() == ScheduledEpochWorkFlowStatus.PENDING) {
            studySubject.setRegWorkflowStatus(RegistrationWorkFlowStatus.PENDING);
        }
        else if (scheduledEpoch.getScEpochWorkflowStatus() == ScheduledEpochWorkFlowStatus.REGISTERED) {
            if (scheduledEpoch.isReserving()) {
                studySubject.setRegWorkflowStatus(RegistrationWorkFlowStatus.RESERVED);
            }
            else {
                studySubject.setRegWorkflowStatus(RegistrationWorkFlowStatus.ENROLLED);
            }
        }
    }

    /**
     * Test Saving of a basic Study Subject
     *
     * @throws Exception
     */
    public void testCreateRegistrationOtherTreatingPhysician() throws Exception {
        Integer savedId;
        {
            // formbackingobject
            Object command = formBackingObject();

            // binding process
            Object afterBind = bind(command);

            // select study & subject
            Object onBindFormObject = bindSelectSubjectStudy(afterBind);
            interruptSession();
            currentFormObject(onBindFormObject);

            Object onEnrollmentBindObject = bindEnrollmentDetailsOtherTreatingPhysician(afterBind);
            interruptSession();
            currentFormObject(onEnrollmentBindObject);

            Object saved = reviewAndSave(onEnrollmentBindObject);

            StudySubject studySubject = (StudySubject) saved;

            savedId = studySubject.getId().intValue();
            assertNotNull("The registration didn't get an id", savedId);
        }

        interruptSession();
        {
            StudySubject loaded = studySubjectDao.getById(savedId);
            assertNotNull("Could not reload registration with id " + savedId, loaded);
            assertEquals("Treating Physician not null", null, loaded.getTreatingPhysician());
            assertEquals("Other Treating Physician wrong", "other treating physician", loaded
                            .getOtherTreatingPhysician());
            assertEquals("Wrong number of scheduled epochs", 1, loaded.getScheduledEpochs().size());
            ScheduledEpoch scheduledEpoch = loaded
                            .getScheduledEpoch();
            assertEquals("Wrong eligibility indicator", false, scheduledEpoch
                            .getEligibilityIndicator().booleanValue());
            assertEquals("Wrong number of subject eligibility answers", 3, scheduledEpoch
                            .getSubjectEligibilityAnswers().size());
            assertEquals("Wrong number of subject inclusion eligibility answers", 2,
            		scheduledEpoch.getInclusionEligibilityAnswers().size());
            assertEquals("Wrong number of subject exclusion eligibility answers", 1,
            		scheduledEpoch.getExclusionEligibilityAnswers().size());
            assertEquals("Wrong number of subject stratification answers", 1,
            		scheduledEpoch.getSubjectStratificationAnswers().size());
            assertEquals("Wrong registration status", "INCOMPLETE", loaded.getRegDataEntryStatus()
                            .getName());
        }
        interruptSession();
    }

    public void testScheduledEpochSearchByExample() throws Exception {
        ScheduledEpoch scheduledEpoch = new ScheduledEpoch(true);
        scheduledEpoch.setEpoch(epochDao.getById(1005));
        List<StudySubject> list = studySubjectDao.searchByScheduledEpoch(scheduledEpoch);
        assertEquals("Wrong number of study subjects", 1, list.size());
    }

    public void testSaveStudySubjectWithOrganizationAssignedIdentifier() throws Exception {
       StudySubject studySubject = studySubjectDao.getById(1000);
       OrganizationAssignedIdentifier orgIdentifier = new OrganizationAssignedIdentifier();
       orgIdentifier.setHealthcareSite(studySubject.getStudySite().getHealthcareSite());
       orgIdentifier.setType(OrganizationIdentifierTypeEnum.COORDINATING_CENTER_IDENTIFIER);
       orgIdentifier.setValue("CoordinatingCenterIdValue");
       Study study = studySubject.getStudySite().getStudy();
       study.addIdentifier(orgIdentifier);
       studyDao.save(study);

       int numberOfIdentifiers = studySubject.getIdentifiers().size();

       studySubject.addIdentifier(identifierGenerator
					.generateOrganizationAssignedIdentifier(studySubject));
       studySubjectDao.save(studySubject);
       StudySubject updatedStudySubject = studySubjectDao.getById(1000);
       assertEquals("Wrong number of study subject identifiers retrieved", numberOfIdentifiers+1, updatedStudySubject.getIdentifiers().size());
    }

    public void testSearchByStudyId() throws Exception{
    	List<StudySubject> studySubjects = new ArrayList<StudySubject>();
    	studySubjects = studySubjectDao.searchByStudyId(1000);
    	List<StudySubject> studySubjects1 = new ArrayList<StudySubject>();
    	studySubjects1 = studySubjectDao.searchByStudyId(1002);
    	assertEquals("Wrong number or study subjects retrieved",3,studySubjects.size());
    	assertEquals("Wrong number or study subjects retrieved",0,studySubjects1.size());
    }

    public void testSearchBySubjectId() throws Exception{
    	List<StudySubject> studySubjects = new ArrayList<StudySubject>();
    	studySubjects = studySubjectDao.searchByParticipantId(1000);
    	assertEquals("Wrong number or study subjects retrieved",1,studySubjects.size());
    	studySubjects = studySubjectDao.searchByParticipantId(1002);
    	assertEquals("Wrong number or study subjects retrieved",3,studySubjects.size());
    	studySubjects = studySubjectDao.searchByParticipantId(1010);
    	assertEquals("Wrong number or study subjects retrieved",0,studySubjects.size());
    }

    public void testSearchByScheduledEpoch() throws Exception{
    	ScheduledEpoch scheduledEpoch = scheduledEpochDao.getById(1001);
    	List<StudySubject> studySubjects = new ArrayList<StudySubject>();
    	studySubjects = studySubjectDao.searchByScheduledEpoch(scheduledEpoch);
    	assertEquals("Wrong number or study subjects retrieved",1,studySubjects.size());
    }

    public void testSearchBySubjectAndStudySite() throws Exception{
    	Participant subject = dao.getById(1000);
    	StudySite studySite = studySiteDao.getById(1000);
    	StudySubject studySubject = new StudySubject(true);
    	studySubject.setStudySite(studySite);
    	studySubject.setParticipant(subject);
    	List<StudySubject> studySubjects = new ArrayList<StudySubject>();
    	studySubjects = studySubjectDao.searchBySubjectAndStudySite(studySubject);
    	assertEquals("Wrong number or study subjects retrieved",1,studySubjects.size());
    }

    public void testSearchByExample() throws Exception{
    	SystemAssignedIdentifier sysIdentifier = new SystemAssignedIdentifier();
    	sysIdentifier.setValue("nci");
    	StudySubject studySubject = new StudySubject(true);
    	studySubject.addIdentifier(sysIdentifier);
    	List<StudySubject> studySubjects = new ArrayList<StudySubject>();
    	studySubjects = studySubjectDao.searchByExample(studySubject, true, 10);
    	assertEquals("Wrong number or study subjects retrieved",1,studySubjects.size());
    }

    public void testForLazyInitialization() throws Exception{
    	StudySubject studySubject = studySubjectDao.getById(1000);
    	super.interruptSession();
    	assertNotNull("Study Subject cannot be null",studySubject);
    	try{
    		studySubject.getSystemAssignedIdentifiers().get(0).getType();
    		fail("Test should not have reached this line");
    	}catch(org.hibernate.LazyInitializationException ex){

    	}
    }

    public void testInitialize() throws Exception{
    	StudySubject studySubject = studySubjectDao.getById(1000);
    	studySubjectDao.initialize(studySubject);
    	super.interruptSession();
    	assertNotNull("Study Subject cannot be null",studySubject);
    	try{
    		studySubject.getSystemAssignedIdentifiers().get(0).getType();
    	}catch(org.hibernate.LazyInitializationException ex){
    		fail("Should not have thrown lazy initialization error as the object is initialized");
    	}
    }

    public void testAdvancedSearch() throws Exception{
    	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");

    	Study study = new LocalStudy(true);
        SystemAssignedIdentifier id = new SystemAssignedIdentifier();
        id.setValue(null);
        study.addIdentifier(id);
        study.setShortTitleText(" ");

    	Participant participant = new Participant();
    	participant.setRaceCode("White");
    	participant.setBirthDate(simpleDateFormat.parse("01/01/2000"));
		id = new SystemAssignedIdentifier();
	    id.setValue(null);
	    participant.addIdentifier(id);

    	StudySubject studySubject = new StudySubject(true);
    	StudySite studySite = new StudySite();
    	HealthcareSite site = healthcareSiteDao.getById(1000);
    	studySite.setHealthcareSite(site);
    	study.addStudySite(studySite);
    	studySubject.setStudySite(studySite);
    	studySubject.setParticipant(participant);
    	List<StudySubject> studySubjects = new ArrayList<StudySubject>();

    	studySubjects = studySubjectDao.advancedSearch(studySubject,simpleDateFormat.parse("01/01/1999"),simpleDateFormat.parse("01/01/2001"),"nci");
    	assertEquals("Wrong number or study subjects retrieved",0,studySubjects.size());
    }

    public void testAdvancedStudySearchAfterInitializationWithObjectsFromDatabase() throws Exception{
    	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
    	StudySubject studySubject = new StudySubject(true);

    	Participant participant = dao.getById(1000);
    	StudySite studySite = studySiteDao.getById(1000);
    	studySubject.setStudySite(studySite);
    	studySubject.setParticipant(participant);
    	List<StudySubject> studySubjects = new ArrayList<StudySubject>();

    	studySubjects = studySubjectDao.advancedStudySearch(studySubject);
    	assertEquals("Wrong number or study subjects retrieved",1,studySubjects.size());
    }

    public void testIncompleteRegistrations() throws Exception{
    	 List<StudySubject> studySubjects = studySubjectDao.getIncompleteRegistrations( 15);
    	assertEquals("Wrong number or study subjects retrieved",2,studySubjects.size());
    }

    public void testSearchByIdentifier() throws Exception{

    	StudySubject studySubject = studySubjectDao.getById(1000);
    	List<StudySubject> studySubjects = new ArrayList<StudySubject>();

    	studySubjects = studySubjectDao.searchByIdentifier(studySubject.getSystemAssignedIdentifiers().get(0).getId());
    	assertEquals("Wrong number or study subjects retrieved",1,studySubjects.size());
    }

    public void testSearchByExampleWithWildCard() throws Exception{
    	SystemAssignedIdentifier sysIdentifier = new SystemAssignedIdentifier();
    	sysIdentifier.setValue("nci");
    	StudySubject studySubject = new StudySubject(true);
    	studySubject.addIdentifier(sysIdentifier);
    	List<StudySubject> studySubjects = new ArrayList<StudySubject>();
    	studySubjects = studySubjectDao.searchByExample(studySubject, true);
    	assertEquals("Wrong number or study subjects retrieved",1,studySubjects.size());
    }

    public void testSearchByExampleWithMaxResults() throws Exception{
    	SystemAssignedIdentifier sysIdentifier = new SystemAssignedIdentifier();
    	sysIdentifier.setValue("nci");
    	StudySubject studySubject = new StudySubject(true);
    	studySubject.addIdentifier(sysIdentifier);
    	List<StudySubject> studySubjects = new ArrayList<StudySubject>();
    	studySubjects = studySubjectDao.searchByExample(studySubject, 15);
    	assertEquals("Wrong number or study subjects retrieved",1,studySubjects.size());
    }

    public void testDomainClass() throws Exception{
    	assertEquals("Wrong domain class",StudySubject.class, studySubjectDao.domainClass());
    }

    public void testStartDateNotNullable() throws Exception{
    	StudySubject studySubject = new StudySubject();
    	studySubject.getStudySubjectStudyVersion().getStudySubjectConsentVersions().get(0).setInformedConsentSignedDate(new Date());
    	StudySite studySite = studySiteDao.getById(1000);
        Participant participant = dao.getById(1000);
        studySubject.setStudySite(studySite);
        studySubject.setParticipant(participant);
        // Have to explicitly set start date to null because it may be initialized
        studySubject.setStartDate(null);

        try{
        	studySubjectDao.save(studySubject);
        	fail("Should have failed due to not null constraint on start date");
        }catch(Exception ex){

        }
    }

    public void testSaveStudySubjectAndStudyWithSameIdentifiers() throws Exception{
    	SystemAssignedIdentifier sysIdentifier = new SystemAssignedIdentifier();
    	sysIdentifier.setType("type");
    	sysIdentifier.setValue("nci");
    	sysIdentifier.setSystemName("system_name");

    	SystemAssignedIdentifier sysIdentifier1 =new SystemAssignedIdentifier();
    	sysIdentifier1.setType("type");
    	sysIdentifier1.setValue("nci");
    	sysIdentifier1.setSystemName("system_name");

    	StudySubject studySubject = studySubjectDao.getById(1000);
    	Study study = studyDao.getById(1000);

    	studySubject.addIdentifier(sysIdentifier);
    	studySubjectDao.save(studySubject);
    	StudySubject loadedStudySubject = studySubjectDao.getById(1000);

    	study.addIdentifier(sysIdentifier1);
    	studyDao.save(study);
    	Study loadedStudy = studyDao.getById(1000);

    	assertEquals("Wrong number of study identifiers retrieved",2,loadedStudy.getIdentifiers().size());
    	assertEquals("Wrong number of study subject identifiers retrieved",2,loadedStudySubject.getIdentifiers().size());
    }

    public void testSaveStudySubjectWithStudySubjectVersionAndStudySubjectConsent() throws Exception{
    	StudySubject studySubject = new StudySubject();
    	StudySubjectConsentVersion studySubjectConsentVersion = new StudySubjectConsentVersion();
    	studySubjectConsentVersion.setConsent(new Consent());
    	StudySiteStudyVersion studySiteStudyVersion = studySiteStudyVersionDao.getById(1000);
    	studySubject.getStudySubjectStudyVersion().setStudySiteStudyVersion(studySiteStudyVersion);
    	Participant participant = dao.getById(1000);
    	studySubject.setParticipant(participant);
    	studySubject.getStudySubjectStudyVersion();
    	studySubjectDao.save(studySubject);
    	System.out.println();
    }

     public void testDeleteStudySiteStudyVersion() throws Exception {
        StudySiteStudyVersion loadedStudySiteStudyVersion = studySiteStudyVersionDao.getById(1011);
         assertNotNull("study site study version cannot be null",loadedStudySiteStudyVersion);
         assertNotNull("study version cannot be null",loadedStudySiteStudyVersion.getStudyVersion());
         ScheduledEpoch scheduledEpoch =  studySubjectStudyVersionDao.getById(1003).getScheduledEpochs().get(0);
         assertEquals("Wrong epoch",1000,scheduledEpoch.getEpoch().getId().intValue());
         assertNotNull("scheduled epoch cannot be null",scheduledEpoch);
         studySubjectStudyVersionDao.remove(studySubjectStudyVersionDao.getById(1003));
         interruptSession();
         StudySubjectStudyVersion studySubjectStudyVersion = studySubjectStudyVersionDao.getById(1003);
         assertNull("Should have been deleted",studySubjectStudyVersion);
         assertNotNull("Should not have been deleted",loadedStudySiteStudyVersion.getStudyVersion());

         scheduledEpoch = scheduledEpochDao.getById(1003);
         assertNull("Should have been deleted",scheduledEpoch);
         loadedStudySiteStudyVersion = studySiteStudyVersionDao.getById(1011);
          assertNotNull("Should not have been deleted",loadedStudySiteStudyVersion.getStudyVersion());
    }
     
     public void testGetMostEnrolledStudies() throws Exception {
    	 SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");

    	 Date startDate = simpleDateFormat.parse("01/08/2010");
    	 Date endDate = simpleDateFormat.parse("01/18/2010");
    	 
    	 assertEquals("Only 1 record should be present",1,studySubjectDao.getMostEnrolledStudies(5, startDate, endDate).size());
     }

}