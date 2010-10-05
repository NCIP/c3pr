package edu.duke.cabig.c3pr.dao;

import static edu.duke.cabig.c3pr.C3PRUseCase.ADD_DISEASE_SUBJECT;
import static edu.duke.cabig.c3pr.C3PRUseCase.ASSIGN_ARM;
import static edu.duke.cabig.c3pr.C3PRUseCase.ASSIGN_EXISTING_PARTICIPANT;
import static edu.duke.cabig.c3pr.C3PRUseCase.ASSIGN_REGISTERED_PARTICIPANT;
import static edu.duke.cabig.c3pr.C3PRUseCase.CREATE_INCOMPLETE_REGISTERATION;
import static edu.duke.cabig.c3pr.C3PRUseCase.CREATE_LOCAL_REGISTERATION;
import static edu.duke.cabig.c3pr.C3PRUseCase.UPDATE_REGISTERATION_STATUS;
import static edu.nwu.bioinformatics.commons.testing.CoreTestCase.assertContains;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import com.semanticbits.querybuilder.AdvancedSearchCriteriaParameter;
import com.semanticbits.querybuilder.AdvancedSearchHelper;

import edu.duke.cabig.c3pr.C3PRUseCases;
import edu.duke.cabig.c3pr.constants.OrganizationIdentifierTypeEnum;
import edu.duke.cabig.c3pr.constants.RaceCodeEnum;
import edu.duke.cabig.c3pr.constants.RegistrationDataEntryStatus;
import edu.duke.cabig.c3pr.constants.RegistrationWorkFlowStatus;
import edu.duke.cabig.c3pr.constants.ScheduledEpochDataEntryStatus;
import edu.duke.cabig.c3pr.constants.ScheduledEpochWorkFlowStatus;
import edu.duke.cabig.c3pr.domain.Consent;
import edu.duke.cabig.c3pr.domain.ConsentQuestion;
import edu.duke.cabig.c3pr.domain.EligibilityCriteria;
import edu.duke.cabig.c3pr.domain.Epoch;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.InclusionEligibilityCriteria;
import edu.duke.cabig.c3pr.domain.LocalStudy;
import edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.RaceCodeAssociation;
import edu.duke.cabig.c3pr.domain.ScheduledArm;
import edu.duke.cabig.c3pr.domain.ScheduledEpoch;
import edu.duke.cabig.c3pr.domain.StratificationCriterion;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.StudySiteStudyVersion;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.StudySubjectConsentVersion;
import edu.duke.cabig.c3pr.domain.StudySubjectDemographics;
import edu.duke.cabig.c3pr.domain.StudySubjectStudyVersion;
import edu.duke.cabig.c3pr.domain.SubjectConsentQuestionAnswer;
import edu.duke.cabig.c3pr.domain.SubjectEligibilityAnswer;
import edu.duke.cabig.c3pr.domain.SubjectStratificationAnswer;
import edu.duke.cabig.c3pr.domain.SystemAssignedIdentifier;
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
            //FIXME : HIMANSHU
            outputStream.println("Race: " + participant.getRaceCodeAssociations());
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
            studySubject.setStudySubjectDemographics(participant.createStudySubjectDemographics());

            ssList = studySubjectDao.advancedStudySearch(studySubject);
            assertEquals(5, ssList.size());
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
            studySubject.setStudySubjectDemographics(participant.createStudySubjectDemographics());

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
            studySubject.setStudySubjectDemographics(participant.createStudySubjectDemographics());

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
            studySubject.setStudySubjectDemographics(participant.createStudySubjectDemographics());

            ssList = studySubjectDao.advancedStudySearch(studySubject);
            assertEquals(5, ssList.size());
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
            studySubject.setStudySubjectDemographics(participant.createStudySubjectDemographics());

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
            studySubject.setStudySubjectDemographics(participant.createStudySubjectDemographics());

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
            studySubject.setStudySubjectDemographics(participant.createStudySubjectDemographics());

            ssList = studySubjectDao.advancedStudySearch(studySubject);
            assertEquals(2, ssList.size());
        }
        interruptSession();
        {
            Study study = new LocalStudy();


            Participant participant = new Participant();

            participant.setLastName("Gomez");

            StudySite studySite = new StudySite();
            study.addStudySite(studySite);

            StudySubject studySubject = new StudySubject();
            studySubject.setStudySite(studySite);
            studySubject.setStudySubjectDemographics(participant.createStudySubjectDemographics());

            ssList = studySubjectDao.advancedStudySearch(studySubject);
            assertEquals(3, ssList.size());
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
            studySubject.setStudySubjectDemographics(participant.createStudySubjectDemographics());

            ssList = studySubjectDao.advancedStudySearch(studySubject);
            assertEquals(3, ssList.size());
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
            id2.setType(OrganizationIdentifierTypeEnum.MRN);
            id2.setValue("mrn");
            participant.addIdentifier(id2);


            StudySite studySite = new StudySite();
            study.addStudySite(studySite);

            StudySubject studySubject = new StudySubject();
            studySubject.setStudySite(studySite);
            studySubject.setStudySubjectDemographics(participant.createStudySubjectDemographics());

            ssList = studySubjectDao.advancedStudySearch(studySubject);
            assertEquals(2, ssList.size());
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
            
            // build and add study subject consent versions
            buildAndAddStudySubjectConsentVersions(command);

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
        assertEquals("Wrong last name", "Clooney", studySubject.getStudySubjectDemographics().getLastName());
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
        assertContains("Wrong Study Subject found", ids, 1004);
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
            StudySubjectDemographics studySubjectDemographics = participant.createStudySubjectDemographics();
            studySubject.setStudySubjectDemographics(studySubjectDemographics);
            StudySite studySite = studySubjectDao.getStudySiteDao().getById(1001);
            studySubject.setStudySubjectDemographics(participant.createStudySubjectDemographics());
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
        Participant participant = dao.getById(1010);
        studySubject.setStudySubjectDemographics(participant.createStudySubjectDemographics());
        StudySubjectDemographics studySubjectDemographics = participant.createStudySubjectDemographics();
    	studySubject.setStudySubjectDemographics(studySubjectDemographics);
        studySubject.setStudySite(studySiteDao.getById(1010));
        return studySubject;
    }
    
    private Object buildAndAddStudySubjectConsentVersions(Object command){
    	StudySite studySite = ((StudySubject) command).getStudySite();
    	for (Consent consent :studySite.getStudySiteStudyVersion().getStudyVersion().getConsents()){
    		StudySubjectConsentVersion studySubjectConsentVersion = new StudySubjectConsentVersion();
    		studySubjectConsentVersion.setConsent(consent);
    		for(ConsentQuestion question:consent.getQuestions()){
    			SubjectConsentQuestionAnswer subjectConsentQuestionAnswer = new SubjectConsentQuestionAnswer();
    			subjectConsentQuestionAnswer.setConsentQuestion(question);
    			studySubjectConsentVersion.addSubjectConsentAnswer(subjectConsentQuestionAnswer);
    		}
    		((StudySubject) command).getStudySubjectStudyVersion().addStudySubjectConsentVersion(studySubjectConsentVersion);
    	}
    	
    	return command;
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
        if (studySubject.getScheduledEpoch().getScEpochWorkflowStatus() == ScheduledEpochWorkFlowStatus.PENDING_ON_EPOCH) {
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
                    scheduledEpoch.setScEpochWorkflowStatus(ScheduledEpochWorkFlowStatus.PENDING_ON_EPOCH);
                }
                catch (Exception e) {
                    e.printStackTrace();
                    scheduledEpoch
                                    .setScEpochWorkflowStatus(ScheduledEpochWorkFlowStatus.PENDING_ON_EPOCH);
                }
            }
            else {
                if (studySubject.getScheduledEpoch().getRequiresArm()
                                && (studySubject.getScheduledEpoch())
                                                .getScheduledArm() == null) {
                    scheduledEpoch
                                    .setScEpochWorkflowStatus(ScheduledEpochWorkFlowStatus.PENDING_ON_EPOCH);
                }
                else {
                    // logic for accrual ceiling check
                    scheduledEpoch.setScEpochWorkflowStatus(ScheduledEpochWorkFlowStatus.ON_EPOCH);
                }
            }
        }
        else {
            scheduledEpoch.setScEpochWorkflowStatus(ScheduledEpochWorkFlowStatus.PENDING_ON_EPOCH);
        }
    }

    private void manageRegWorkFlowIfUnReg(StudySubject studySubject) {
        ScheduledEpoch scheduledEpoch = studySubject.getScheduledEpoch();
//        if (scheduledEpoch.getScEpochWorkflowStatus() == ScheduledEpochWorkFlowStatus.DISAPPROVED) {
//            studySubject.setRegWorkflowStatus(RegistrationWorkFlowStatus.DISAPPROVED);
//        }
        if (scheduledEpoch.getScEpochWorkflowStatus() == ScheduledEpochWorkFlowStatus.PENDING_ON_EPOCH) {
            studySubject.setRegWorkflowStatus(RegistrationWorkFlowStatus.PENDING);
        }
        else if (scheduledEpoch.getScEpochWorkflowStatus() == ScheduledEpochWorkFlowStatus.ON_EPOCH) {
            if (scheduledEpoch.isReserving()) {
                studySubject.setRegWorkflowStatus(RegistrationWorkFlowStatus.RESERVED);
            }
            else {
                studySubject.setRegWorkflowStatus(RegistrationWorkFlowStatus.ON_STUDY);
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
            
            // build and add study subject consent versions
            buildAndAddStudySubjectConsentVersions(command);

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
    	assertEquals("Wrong number or study subjects retrieved",2,studySubjects.size());
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
    	studySubject.setStudySubjectDemographics(subject.createStudySubjectDemographics());
    	List<StudySubject> studySubjects = new ArrayList<StudySubject>();
    	studySubjects = studySubjectDao.searchBySubjectAndStudySite(studySubject);
    	assertEquals("Wrong number or study subjects retrieved",1,studySubjects.size());
    }
    
    public void testSearchBySubjectAndStudy() throws Exception{
    	Participant subject = dao.getById(1000);
    	Study study = studyDao.getById(1000);
    	
    	List<StudySubject> studySubjects = new ArrayList<StudySubject>();
    	studySubjects = studySubjectDao.searchBySubjectAndStudyIdentifiers(subject.getIdentifiers().get(0),study.getIdentifiers().get(0));
    	assertEquals("Wrong number or study subjects retrieved",2,studySubjects.size());
    	
    	StudySite studySite1 = studySiteDao.getById(1000);
    	StudySubject studySubject1 = new StudySubject(true);
    	studySubject1.setStudySite(studySite1);
    	studySubject1.setStudySubjectDemographics(subject.createStudySubjectDemographics());
    	List<StudySubject> studySubjects1 = new ArrayList<StudySubject>();
    	studySubjects1 = studySubjectDao.searchBySubjectAndStudySite(studySubject1);
    	assertEquals("Wrong number or study subjects retrieved",1,studySubjects1.size());
    	
    	StudySite studySite2 = studySiteDao.getById(1001);
    	StudySubject studySubject2 = new StudySubject(true);
    	studySubject2.setStudySite(studySite2);
    	studySubject2.setStudySubjectDemographics(subject.createStudySubjectDemographics());
    	List<StudySubject> studySubjects2 = new ArrayList<StudySubject>();
    	studySubjects2 = studySubjectDao.searchBySubjectAndStudySite(studySubject2);
    	assertEquals("Wrong number or study subjects retrieved",1,studySubjects2.size());
    }

    public void testSearchByExample() throws Exception{
    	SystemAssignedIdentifier sysIdentifier = new SystemAssignedIdentifier();
    	sysIdentifier.setValue("nci");
    	StudySubject studySubject = new StudySubject(true);
    	studySubject.addIdentifier(sysIdentifier);
    	List<StudySubject> studySubjects = new ArrayList<StudySubject>();
    	studySubjects = studySubjectDao.searchByExample(studySubject, true, 10);
    	assertEquals("Wrong number or study subjects retrieved",2,studySubjects.size());
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
	   	 RaceCodeAssociation  raceCodeAssociation = new RaceCodeAssociation();
	     raceCodeAssociation.setRaceCode(RaceCodeEnum.White);
	     participant.addRaceCodeAssociation(raceCodeAssociation);
     
     
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
    	studySubject.setStudySubjectDemographics(participant.createStudySubjectDemographics());
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
    	studySubject.setStudySubjectDemographics(participant.createStudySubjectDemographics());
    	List<StudySubject> studySubjects = new ArrayList<StudySubject>();

    	studySubjects = studySubjectDao.advancedStudySearch(studySubject);
    	assertEquals("Wrong number or study subjects retrieved",2,studySubjects.size());
    }

    public void testIncompleteRegistrations() throws Exception{
    	 List<StudySubject> studySubjects = studySubjectDao.getIncompleteRegistrations();
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
    	assertEquals("Wrong number or study subjects retrieved",2,studySubjects.size());
    }

    public void testSearchByExampleWithMaxResults() throws Exception{
    	SystemAssignedIdentifier sysIdentifier = new SystemAssignedIdentifier();
    	sysIdentifier.setValue("nci");
    	StudySubject studySubject = new StudySubject(true);
    	studySubject.addIdentifier(sysIdentifier);
    	List<StudySubject> studySubjects = new ArrayList<StudySubject>();
    	studySubjects = studySubjectDao.searchByExample(studySubject, 15);
    	assertEquals("Wrong number or study subjects retrieved",2,studySubjects.size());
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
        studySubject.setStudySubjectDemographics(participant.createStudySubjectDemographics());
        StudySubjectDemographics studySubjectDemographics = participant.createStudySubjectDemographics();
    	studySubject.setStudySubjectDemographics(studySubjectDemographics);
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
    	studySubject.setStudySubjectDemographics(participant.createStudySubjectDemographics());
    	studySubject.getStudySubjectStudyVersion();
    	StudySubjectDemographics studySubjectDemographics = new StudySubjectDemographics();
    	studySubjectDemographics = participant.createStudySubjectDemographics();
    	studySubject.setStudySubjectDemographics(studySubjectDemographics);
    	studySubjectDao.save(studySubject);
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
    	 Date startDate = simpleDateFormat.parse("06/01/2010");
    	 Date endDate = simpleDateFormat.parse("08/18/2010");
    	 
    	 assertEquals("Only 1 record should be present",1,studySubjectDao.getMostEnrolledStudies(startDate, endDate).size());
     }
     
     public void testSaveStudySubjectWithStudySubjectDemographics() throws Exception{
    	 StudySubject studySubject = new StudySubject();
    	 
    	 Participant participant1 = dao.getById(1000);
    	 StudySubjectDemographics studySubjectDemographics = participant1.createStudySubjectDemographics();
    	 StudySubjectStudyVersion studySubjectStudyVersion= new StudySubjectStudyVersion();
    	 studySubjectStudyVersion.setStudySiteStudyVersion(studySiteStudyVersionDao.getById(1000));
    	 studySubject.clearAllAndAddStudySubjectStudyVersion(studySubjectStudyVersion);
    	 
    	 studySubject.setStudySubjectDemographics(studySubjectDemographics);
    	 studySubject.setStudySubjectDemographics(participant1.createStudySubjectDemographics());
    	 studySubjectDao.save(studySubject);
    	 int savedId = studySubject.getId();
    	 
    	 interruptSession();
    	 
    	 StudySubjectDemographics savedStudySubjectDemographics = studySubjectDao.getById(savedId).getStudySubjectDemographics();
    	 
    	 assertNotNull("Study subject demographics not saved", savedStudySubjectDemographics.getId());
    	 
     }
     
     public void testSearchByIdentifiers1() throws Exception{
    	OrganizationAssignedIdentifier orgIdentifier = new OrganizationAssignedIdentifier();
    	HealthcareSite hcs = healthcareSiteDao.getById(1003);
    	orgIdentifier.setHealthcareSite(hcs);
    	orgIdentifier.setType(OrganizationIdentifierTypeEnum.CTEP);
    	orgIdentifier.setValue("");
    	
    	List<StudySubject> registrations = studySubjectDao.searchByIdentifier(orgIdentifier, StudySubject.class);
    	assertEquals("Wrong number of registrations ",0, registrations.size());
    	 
    	 orgIdentifier.setValue("nci1");
    	 
    	 List<StudySubject> registrationsNew = studySubjectDao.searchByIdentifier(orgIdentifier, StudySubject.class);
    	 assertEquals("Wrong number of registrations ",1, registrationsNew.size());
    	 
     }
     
     public void testSearchByIdentifiers2() throws Exception{
     	SystemAssignedIdentifier sysIdentifier = new SystemAssignedIdentifier();
     	sysIdentifier.setSystemName("nci1");
     	sysIdentifier.setType("local");
     	sysIdentifier.setValue("nci");
     	
     	List<StudySubject> registrations = studySubjectDao.searchByIdentifier(sysIdentifier, StudySubject.class);
     	assertEquals("Wrong number of registrations ",0, registrations.size());
     	 
     	sysIdentifier.setSystemName("nci");
     	 
     	 List<StudySubject> registrationsNew = studySubjectDao.searchByIdentifier(sysIdentifier,StudySubject.class);
     	 assertEquals("Wrong number of registrations ",1, registrationsNew.size());
     	 
      }
     
     public void testSaveStudySubjectConsentVersionWithSubjectConsentAnswers() throws Exception{
    	 StudySubject studySubject = studySubjectDao.getById(1000);
    	 assertEquals("Expected study subject consents",1,studySubject.getStudySubjectStudyVersion()
    			 .getStudySubjectConsentVersions().size());
    	 
    	 StudySubjectConsentVersion studySubjectConsentVersion = new StudySubjectConsentVersion();
    	 
    	 Consent consent = studySubject.getStudySubjectStudyVersion().getStudySiteStudyVersion().getStudyVersion().getConsents().get(0);
    	 ConsentQuestion consentQuestion = consent.getQuestions().get(0);
    	 
    	 studySubjectConsentVersion.setConsent(consent);
    	 studySubjectConsentVersion.setInformedConsentSignedDate(new Date());
    	 
    	 Time time = new Time(22, 11, 11);
    	 studySubjectConsentVersion.setInformedConsentSignedTime(time);
    	 
    	 SubjectConsentQuestionAnswer subjectConsentQuestionAnswer = new SubjectConsentQuestionAnswer();
    	 subjectConsentQuestionAnswer.setConsentQuestion(consentQuestion);
    	 subjectConsentQuestionAnswer.setAgreementIndicator(true);
    	 
    	 studySubjectConsentVersion.addSubjectConsentAnswer(subjectConsentQuestionAnswer);
    	 studySubject.getStudySubjectStudyVersion().addStudySubjectConsentVersion(studySubjectConsentVersion);
    	 
    	 studySubjectDao.save(studySubject);
    	 
    	 StudySubject reloadedStudySubject = studySubjectDao.getById(1000);
    	 
    	 assertEquals("Wrong number of study subject consents",2,reloadedStudySubject.getStudySubjectStudyVersion()
    			 .getStudySubjectConsentVersions().size());
    	 
    	 assertTrue("Wrong subject consent answer",reloadedStudySubject.getStudySubjectStudyVersion()
    			 .getStudySubjectConsentVersions().get(1).getSubjectConsentAnswers().get(0).getAgreementIndicator());
    	 assertEquals("Picked wrong study consent question","Are you willing to participate in tissue collection",
    			 reloadedStudySubject.getStudySubjectStudyVersion().getStudySubjectConsentVersions().get(1)
    			 .getSubjectConsentAnswers().get(0).getConsentQuestion().getText());
    	
     }
     
     // TEST CASES FOR ADVANCED QUERY
     
     // Test cases for registration attributes
     public void testGetResultSetWithHQLForWorkFlowStatus() throws Exception {
    	List<String> values = new ArrayList<String>();
    	values.add("ON_STUDY");
    	
 		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper.buildAdvancedSearchCriteriaParameter("edu.duke.cabig.c3pr.domain.StudySubject", "regWorkflowStatus.code", values, "=");
 
 		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
 		criteriaParameters.add(advancedSearchCriteriaParameter1);
 		
 		List<StudySubject> registrations = studySubjectDao.search(criteriaParameters);
 		
 		assertEquals("2 registrations not found", 2,  registrations.size());
 	}
     
     public void testGetResultSetWithHQLForDataEntryStatus() throws Exception {
    	List<String> values = new ArrayList<String>();
     	values.add("COMPLETE");
 
 		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
 				.buildAdvancedSearchCriteriaParameter(
 						"edu.duke.cabig.c3pr.domain.StudySubject", "regDataEntryStatus.code", values, "=");
 
 		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
 		criteriaParameters.add(advancedSearchCriteriaParameter1);
 		
 		List<StudySubject> registrations = studySubjectDao.search(criteriaParameters);
 		assertEquals("1 registration not found", 1,  registrations.size());
 	}
     
     public void testGetResultSetWithHQLForPaymentMethod() throws Exception {
    	 List<String> values = new ArrayList<String>();
        	values.add("medicare");
  
 
 		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
 				.buildAdvancedSearchCriteriaParameter( "edu.duke.cabig.c3pr.domain.StudySubject", "paymentMethod", values, "=");
 
 		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
 		criteriaParameters.add(advancedSearchCriteriaParameter1);
 		
 		List<StudySubject> registrations = studySubjectDao.search(criteriaParameters);
 		assertEquals("2 registrations not found", 2,  registrations.size());
 	}
     
     public void testGetResultSetWithHQLForRegistrationStartDate() throws Exception {
    	List<String> values = new ArrayList<String>();
       	values.add("01/01/2007");
 		
 		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
 				.buildAdvancedSearchCriteriaParameter( "edu.duke.cabig.c3pr.domain.StudySubject", "startDate", values, ">");
 
 		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
 		criteriaParameters.add(advancedSearchCriteriaParameter1);
 		
 		List<StudySubject> registrations = studySubjectDao.search(criteriaParameters);
 		assertEquals("2 registrations not found", 2,  registrations.size());
 	}
     
     public void testGetResultSetWithHQLForRegistrationMultipleStartDate() throws Exception {
     	List<String> values = new ArrayList<String>();
        	values.add("01/01/2000");
        	values.add("05/01/2007");
        	values.add("09/01/2003");
  		
  		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
  				.buildAdvancedSearchCriteriaParameter( "edu.duke.cabig.c3pr.domain.StudySubject", "startDate", values, "in");
  
  		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
  		criteriaParameters.add(advancedSearchCriteriaParameter1);
  		
  		List<StudySubject> registrations = studySubjectDao.search(criteriaParameters);
  		assertEquals("3 registrations not found", 3,  registrations.size());
  	}
     
     public void testGetResultSetWithHQLForRegistrationMultipleStartDateNotIn() throws Exception {
      	List<String> values = new ArrayList<String>();
         	values.add("01/01/2000");
         	values.add("05/01/2007");
         	values.add("09/01/2003");
   		
   		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
   				.buildAdvancedSearchCriteriaParameter( "edu.duke.cabig.c3pr.domain.StudySubject", "startDate", values, "not in");
   
   		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
   		criteriaParameters.add(advancedSearchCriteriaParameter1);
   		
   		List<StudySubject> registrations = studySubjectDao.search(criteriaParameters);
   		assertEquals("2 registrations not found", 2,  registrations.size());
   	}
     
     public void testGetResultSetWithHQLForRegistrationStartDateRange() throws Exception {
      	List<String> values = new ArrayList<String>();
         	values.add("01/01/2000");
         	values.add("05/01/2007");
   		
   		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
   				.buildAdvancedSearchCriteriaParameter( "edu.duke.cabig.c3pr.domain.StudySubject", "startDate", values, "between");
   
   		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
   		criteriaParameters.add(advancedSearchCriteriaParameter1);
   		
   		List<StudySubject> registrations = studySubjectDao.search(criteriaParameters);
   		assertEquals("4 registrations not found", 4,  registrations.size());
   	}
     
    //test case for search by registration identifier 
 	public void testGetResultSetWithHQLForIdentifierValue() throws Exception {
 		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
 				.buildAdvancedSearchCriteriaParameter( "edu.duke.cabig.c3pr.domain.Identifier", "StudySubject","value", "nci1%", "like");
 
 		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
 		criteriaParameters.add(advancedSearchCriteriaParameter1);
 		
 		List<StudySubject> registrations = studySubjectDao.search(criteriaParameters);
 		assertEquals("1 registration not found", 1,  registrations.size());
 	}
 	
 	public void testGetResultSetWithHQLForIdentifierValueNegative() throws Exception {
 		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
 				.buildAdvancedSearchCriteriaParameter( "edu.duke.cabig.c3pr.domain.Identifier","StudySubject" ,"value", "nci5", "=");
 
 		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
 		criteriaParameters.add(advancedSearchCriteriaParameter1);
 		
 		List<StudySubject> registrations = studySubjectDao.search(criteriaParameters);
 		assertEquals("0 registration not found", 0,  registrations.size());
 	}
	
	public void testGetResultSetWithHQLForIdentifierType() throws Exception {
		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
				.buildAdvancedSearchCriteriaParameter( "edu.duke.cabig.c3pr.domain.Identifier","StudySubject" ,"typeInternal", "local", "like");

		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
		criteriaParameters.add(advancedSearchCriteriaParameter1);
		
		List<StudySubject> registrations = studySubjectDao.search(criteriaParameters);
		assertEquals("2 registrations not found", 2,  registrations.size());
	}
	
	//test case for search by participant identifier 
 	public void testGetResultSetWithHQLForParticipantIdentifierValue() throws Exception {
 		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
 				.buildAdvancedSearchCriteriaParameter( "edu.duke.cabig.c3pr.domain.Identifier", "Subject",
 						"value", "mrn%", "like");
 
 		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
 		criteriaParameters.add(advancedSearchCriteriaParameter1);
 		
 		List<StudySubject> registrations = studySubjectDao.search(criteriaParameters);
 		assertEquals("1 registration not found", 1,  registrations.size());
 	}
	
 	//test case for search by participant race code 
 	public void testGetResultSetWithHQLForParticipantSingleRaceCode() throws Exception {
 		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
 				.buildAdvancedSearchCriteriaParameter( "edu.duke.cabig.c3pr.domain.RaceCodeAssociation", "raceCode.code", "White", "=");
 
 		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
 		criteriaParameters.add(advancedSearchCriteriaParameter1);
 		
 		List<StudySubject> registrations = studySubjectDao.search(criteriaParameters);
 		assertEquals("2 registration not found", 2,  registrations.size());
 	}
 	
 	public void testGetResultSetWithHQLForParticipantMultipleRaceCode() throws Exception {
	List<String> values = new ArrayList<String>();
   	values.add("White");
   	values.add("Native_Hawaiian_or_Pacific_Islander");
 		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
 				.buildAdvancedSearchCriteriaParameter( "edu.duke.cabig.c3pr.domain.RaceCodeAssociation", "raceCode.code", values, "in");
 
 		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
 		criteriaParameters.add(advancedSearchCriteriaParameter1);
 		
 		List<StudySubject> registrations = studySubjectDao.search(criteriaParameters);
 		assertEquals("5 registration not found", 5,  registrations.size());
 	}
 	
	public void testGetResultSetWithHQLForParticipantIdentifierType() throws Exception {
		List<String> values = new ArrayList<String>();
       	values.add("mrn");
 
		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
				.buildAdvancedSearchCriteriaParameter( "edu.duke.cabig.c3pr.domain.Identifier", "Subject",
						"typeInternal", values, "like");

		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
		criteriaParameters.add(advancedSearchCriteriaParameter1);
		
		List<StudySubject> registrations = studySubjectDao.search(criteriaParameters);
		assertEquals("2 registrations not found", 2,  registrations.size());
	}
	
	// test case for study coordinating center assigned identifier
	
	public void testGetResultSetWithHQLForStudyIdentifierCTEPAndValue() throws Exception {
		List<String> values = new ArrayList<String>();
       	values.add("CTEP");
 
		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
				.buildAdvancedSearchCriteriaParameter( "edu.duke.cabig.c3pr.domain.Identifier", "Study",
						"typeInternal", values, "like");
		
		List<String> values2 = new ArrayList<String>();
       	values2.add("nci2");
 
		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter2 = AdvancedSearchHelper
				.buildAdvancedSearchCriteriaParameter( "edu.duke.cabig.c3pr.domain.Identifier", "Study",
						"value", values2, "like");

		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
		criteriaParameters.add(advancedSearchCriteriaParameter1);
		criteriaParameters.add(advancedSearchCriteriaParameter2);
		
		List<StudySubject> registrations = studySubjectDao.search(criteriaParameters);
		assertEquals("1 registrations not found", 1,  registrations.size());
	}
	
	public void testGetResultSetWithHQLForCoordinatingCenterAssuignedIdentifier() throws Exception {
		List<String> values = new ArrayList<String>();
       	values.add("COORDINATING_CENTER_IDENTIFIER");
 
		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
				.buildAdvancedSearchCriteriaParameter( "edu.duke.cabig.c3pr.domain.Identifier", "Study",
						"typeInternal", values, "like");
		
		List<String> values2 = new ArrayList<String>();
       	values2.add("himanshu");
 
		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter2 = AdvancedSearchHelper
				.buildAdvancedSearchCriteriaParameter( "edu.duke.cabig.c3pr.domain.Identifier", "Study",
						"value", values2, "like");

		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
		criteriaParameters.add(advancedSearchCriteriaParameter1);
		criteriaParameters.add(advancedSearchCriteriaParameter2);
		
		List<StudySubject> registrations = studySubjectDao.search(criteriaParameters);
		assertEquals("1 registrations not found", 1,  registrations.size());
	}

	// not supported as of now
	public void testGetResultSetWithHQLForCoordinatingCenterAssignedAndSubjectIdentifier() throws Exception {
		List<String> values = new ArrayList<String>();
       	values.add("COORDINATING_CENTER_IDENTIFIER");
 
		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
				.buildAdvancedSearchCriteriaParameter( "edu.duke.cabig.c3pr.domain.Identifier", "Study",
						"typeInternal", values, "like");
		
		List<String> values2 = new ArrayList<String>();
       	values2.add("himanshu");
       	
       	AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter2 = AdvancedSearchHelper
		.buildAdvancedSearchCriteriaParameter( "edu.duke.cabig.c3pr.domain.Identifier", "Study",
				"value", values2, "like");
       	
       	List<String> values3 = new ArrayList<String>();
       	values3.add("MRN");
 
 		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter3 = AdvancedSearchHelper
 				.buildAdvancedSearchCriteriaParameter( "edu.duke.cabig.c3pr.domain.Identifier", "Subject",
 						"typeInternal", values3, "like");
 		
 		List<String> values4 = new ArrayList<String>();
       	values4.add("participant");
 
 		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter4 = AdvancedSearchHelper
 				.buildAdvancedSearchCriteriaParameter( "edu.duke.cabig.c3pr.domain.Identifier", "Subject",
 						"value", values4, "like");
 
		

		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
		criteriaParameters.add(advancedSearchCriteriaParameter1);
		criteriaParameters.add(advancedSearchCriteriaParameter2);
		criteriaParameters.add(advancedSearchCriteriaParameter3);
		criteriaParameters.add(advancedSearchCriteriaParameter4);
		
		List<StudySubject> registrations = studySubjectDao.search(criteriaParameters);
		assertEquals("1 registrations not found", 1,  registrations.size());
	}
	
	
	public void testGetResultSetWithHQLForCoordinatingCenterAssignedAndSubjectIdentifierNegative() throws Exception {
		List<String> values = new ArrayList<String>();
       	values.add("COORDINATING_CENTER_IDENTIFIER");
 
		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
				.buildAdvancedSearchCriteriaParameter( "edu.duke.cabig.c3pr.domain.Identifier", "Study",
						"typeInternal", values, "like");
		
		List<String> values2 = new ArrayList<String>();
       	values2.add("himanshu");
       	
       	AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter2 = AdvancedSearchHelper
		.buildAdvancedSearchCriteriaParameter( "edu.duke.cabig.c3pr.domain.Identifier", "Study",
				"value", values2, "like");
       	
       	List<String> values3 = new ArrayList<String>();
       	values3.add("MRN");
 
 		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter3 = AdvancedSearchHelper
 				.buildAdvancedSearchCriteriaParameter( "edu.duke.cabig.c3pr.domain.Identifier", "Subject",
 						"typeInternal", values3, "like");
 		
 		List<String> values4 = new ArrayList<String>();
       	values4.add("participant-not");
 
 		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter4 = AdvancedSearchHelper
 				.buildAdvancedSearchCriteriaParameter( "edu.duke.cabig.c3pr.domain.Identifier", "Subject",
 						"value", values4, "like");
 
		

		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
		criteriaParameters.add(advancedSearchCriteriaParameter1);
		criteriaParameters.add(advancedSearchCriteriaParameter2);
		criteriaParameters.add(advancedSearchCriteriaParameter3);
		criteriaParameters.add(advancedSearchCriteriaParameter4);
		
		List<StudySubject> registrations = studySubjectDao.search(criteriaParameters);
		assertEquals("0 registrations not found",0,  registrations.size());
	}
	
	
	// test case for treating physician
	public void testGetResultSetWithHQLForInvestigatorAssignedIdentifier() throws Exception {
		List<String> values = new ArrayList<String>();
       	values.add("x1");

		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
				.buildAdvancedSearchCriteriaParameter( "edu.duke.cabig.c3pr.domain.Investigator","assignedIdentifier", values, "=");

		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
		criteriaParameters.add(advancedSearchCriteriaParameter1);
		
		List<StudySubject> registrations = studySubjectDao.search(criteriaParameters);
		assertEquals("2 registrations not found", 2,  registrations.size());
	}
	
	public void testGetResultSetWithHQLForInvestigatorFirstName() throws Exception {
		List<String> values = new ArrayList<String>();
       	values.add("Bill%");

		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
				.buildAdvancedSearchCriteriaParameter(
						"edu.duke.cabig.c3pr.domain.Investigator",  "firstName", values, "like");

		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
		criteriaParameters.add(advancedSearchCriteriaParameter1);
		
		List<StudySubject> registrations = studySubjectDao.search(criteriaParameters);
		assertEquals("2 registrations not found", 2,  registrations.size());
	}
	
	// test case for subject
	public void testGetResultSetWithHQLForSubjectFirstName() throws Exception {
		List<String> values = new ArrayList<String>();
       	values.add("Rudo%");

		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
				.buildAdvancedSearchCriteriaParameter(
						"edu.duke.cabig.c3pr.domain.StudySubjectDemographics", "firstName",
						values, "like");

		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
		criteriaParameters.add(advancedSearchCriteriaParameter1);
		
		List<StudySubject> registrations = studySubjectDao.search(criteriaParameters);
		assertEquals("1 registration not found", 1,  registrations.size());
	}
	
	//test case for subject address
	public void testGetResultSetWithHQLForParticipantZipCode() throws Exception {
		List<String> values = new ArrayList<String>();
       	values.add("20171");

		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
				.buildAdvancedSearchCriteriaParameter(
						"edu.duke.cabig.c3pr.domain.Address", "postalCode",
						values, "=");

		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
		criteriaParameters.add(advancedSearchCriteriaParameter1);
		
		List<StudySubject> registrations = studySubjectDao.search(criteriaParameters);
		assertEquals("3 registrations not found", 3,  registrations.size());
	}
	
	//test case for disease site
	public void testGetResultSetWithHQLForDiseaseSite() throws Exception {
		List<String> values = new ArrayList<String>();
       	values.add("200");

		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
				.buildAdvancedSearchCriteriaParameter(
						"edu.duke.cabig.c3pr.domain.ICD9DiseaseSite",  "code",
						values, "=");

		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
		criteriaParameters.add(advancedSearchCriteriaParameter1);
		
		List<StudySubject> registrations = studySubjectDao.search(criteriaParameters);
		assertEquals("1 registration not found", 1,  registrations.size());
	}
	
	//test cases for disease term
	public void testGetResultSetWithHQLForStudyDiseaseTerm() throws Exception {
		List<String> values = new ArrayList<String>();
       	values.add("%anal cancer%");

		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
				.buildAdvancedSearchCriteriaParameter(
						"edu.duke.cabig.c3pr.domain.DiseaseTerm",  "term",
						values, "like");

		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
		criteriaParameters.add(advancedSearchCriteriaParameter1);
		
		List<StudySubject> registrations = studySubjectDao.search(criteriaParameters);
		assertEquals("1 registration not found", 1,  registrations.size());
	}
	
	//test vase for disease category name
	public void testGetResultSetWithHQLForStudyDiseaseCategoryName() throws Exception {
		List<String> values = new ArrayList<String>();
       	values.add("%Human Papillomavirus%");

		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
				.buildAdvancedSearchCriteriaParameter(
						"edu.duke.cabig.c3pr.domain.DiseaseCategory",  "name",
						values, "like");

		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
		criteriaParameters.add(advancedSearchCriteriaParameter1);
		
		List<StudySubject> registrations = studySubjectDao.search(criteriaParameters);
		assertEquals("1 registration not found", 1,  registrations.size());
	}
	
	public void testGetResultSetWithHQLForConsentDeliveryDate() throws Exception {
		List<String> values = new ArrayList<String>();
       	values.add("10/10/2010");

		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
				.buildAdvancedSearchCriteriaParameter(
						"edu.duke.cabig.c3pr.domain.StudySubjectConsentVersion",  "consentDeliveryDate",
						values, "=");

		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
		criteriaParameters.add(advancedSearchCriteriaParameter1);
		
		List<StudySubject> registrations = studySubjectDao.search(criteriaParameters);
		assertEquals("2 registrations not found", 2,  registrations.size());
	}
	
	public void testGetResultSetWithHQLForConsentPresenter() throws Exception {
		List<String> values = new ArrayList<String>();
       	values.add("Himanshu");
		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
				.buildAdvancedSearchCriteriaParameter(
						"edu.duke.cabig.c3pr.domain.StudySubjectConsentVersion",  "consentPresenter",
						values, "=");

		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
		criteriaParameters.add(advancedSearchCriteriaParameter1);
		
		List<StudySubject> registrations = studySubjectDao.search(criteriaParameters);
		assertEquals("2 registrations not found", 2,  registrations.size());
	}
	
	public void testGetResultSetWithHQLForInformedConsentSignedDate() throws Exception {
		List<String> values = new ArrayList<String>();
       	values.add("11/10/2010");
		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
				.buildAdvancedSearchCriteriaParameter(
						"edu.duke.cabig.c3pr.domain.StudySubjectConsentVersion",  "informedConsentSignedTimestamp",
						values, ">");

		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
		criteriaParameters.add(advancedSearchCriteriaParameter1);
		
		List<StudySubject> registrations = studySubjectDao.search(criteriaParameters);
		assertEquals("2 registrations not found", 2,  registrations.size());
	}
	
	public void testGetResultSetWithHQLForConsentMethod() throws Exception {
		List<String> values = new ArrayList<String>();
       	values.add("WRITTEN");

		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
				.buildAdvancedSearchCriteriaParameter(
						"edu.duke.cabig.c3pr.domain.StudySubjectConsentVersion",  "consentingMethod.code",
						values, "=");

		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
		criteriaParameters.add(advancedSearchCriteriaParameter1);
		
		List<StudySubject> registrations = studySubjectDao.search(criteriaParameters);
		assertEquals("3 registrations not found", 3,  registrations.size());
	}
	
	public void testGetResultSetWithHQLForEligibilityIndicator() throws Exception {
		List<String> values = new ArrayList<String>();
       	values.add("true");

		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
				.buildAdvancedSearchCriteriaParameter(
						"edu.duke.cabig.c3pr.domain.ScheduledEpoch",  "eligibilityIndicator",
						values, "=");

		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
		criteriaParameters.add(advancedSearchCriteriaParameter1);
		
		List<StudySubject> registrations = studySubjectDao.search(criteriaParameters);
		assertEquals("2 registrations not found", 2,  registrations.size());
	}
	
	public void testGetResultSetWithHQLForScheduledEpochDataEntryStatus() throws Exception {
		List<String> values = new ArrayList<String>();
       	values.add("COMPLETE");

		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
				.buildAdvancedSearchCriteriaParameter(
						"edu.duke.cabig.c3pr.domain.ScheduledEpoch",  "scEpochDataEntryStatus.code",
						values, "=");

		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
		criteriaParameters.add(advancedSearchCriteriaParameter1);
		
		List<StudySubject> registrations = studySubjectDao.search(criteriaParameters);
		assertEquals("1 registration not found", 1,  registrations.size());
	}
	
	public void testGetResultSetWithHQLForScheduledEpochWorkFlowStatus() throws Exception {
		List<String> values = new ArrayList<String>();
       	values.add("PENDING_ON_EPOCH");

		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
				.buildAdvancedSearchCriteriaParameter(
						"edu.duke.cabig.c3pr.domain.ScheduledEpoch",  "scEpochWorkflowStatus.code",
						values, "=");

		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
		criteriaParameters.add(advancedSearchCriteriaParameter1);
		
		List<StudySubject> registrations = studySubjectDao.search(criteriaParameters);
		assertEquals("2 registrations not found", 2,  registrations.size());
	}
	
	public void testGetResultSetWithHQLForOffEpochReasonCode() throws Exception {
		List<String> values = new ArrayList<String>();
       	values.add("test reason");

		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
				.buildAdvancedSearchCriteriaParameter(
						"edu.duke.cabig.c3pr.domain.Reason", "OffEpochReason", "code",
						values, "like");

		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
		criteriaParameters.add(advancedSearchCriteriaParameter1);
		
		List<StudySubject> registrations = studySubjectDao.search(criteriaParameters);
		assertEquals("1 registration not found", 1,  registrations.size());
	}
	
	public void testGetResultSetWithHQLForEpochType() throws Exception {
		List<String> values = new ArrayList<String>();
       	values.add("SCREENING");

		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
				.buildAdvancedSearchCriteriaParameter(
						"edu.duke.cabig.c3pr.domain.Epoch",  "type.code",
						values, "=");

		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
		criteriaParameters.add(advancedSearchCriteriaParameter1);
		
		List<StudySubject> registrations = studySubjectDao.search(criteriaParameters);
		assertEquals("1 registration not found", 1,  registrations.size());
	}
	
	public void testGetResultSetWithHQLForEpochName() throws Exception {
		List<String> values = new ArrayList<String>();
       	values.add("NonTreatment%");

		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
				.buildAdvancedSearchCriteriaParameter(
						"edu.duke.cabig.c3pr.domain.Epoch",  "name",
						values, "like");

		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
		criteriaParameters.add(advancedSearchCriteriaParameter1);
		
		List<StudySubject> registrations = studySubjectDao.search(criteriaParameters);
		assertEquals("1 registration not found", 1,  registrations.size());
	}
	
	public void testGetResultSetWithHQLForStudyShortTitle() throws Exception {
		List<String> values = new ArrayList<String>();
       	values.add("short_title_text1");

		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
				.buildAdvancedSearchCriteriaParameter(
						"edu.duke.cabig.c3pr.domain.StudyVersion",  "shortTitleText",
						values, "like");

		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
		criteriaParameters.add(advancedSearchCriteriaParameter1);
		
		List<StudySubject> registrations = studySubjectDao.search(criteriaParameters);
		assertEquals("2 registrations not found", 2,  registrations.size());
	}
	
	public void testGetResultSetWithHQLForStudyTherapeuticIntent() throws Exception {
		List<String> values = new ArrayList<String>();
       	values.add("false");

       	AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
				.buildAdvancedSearchCriteriaParameter(
						"edu.duke.cabig.c3pr.domain.Study",  "therapeuticIntentIndicator",
						values, "=");

		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
		criteriaParameters.add(advancedSearchCriteriaParameter1);
		
		List<StudySubject> registrations = studySubjectDao.search(criteriaParameters);
		assertEquals("4 registrations not found", 4,  registrations.size());
	}
	
	public void testGetResultSetWithHQLForStudyPhaseCode() throws Exception {
		List<String> values = new ArrayList<String>();
       	values.add("Ph");

		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
				.buildAdvancedSearchCriteriaParameter(
						"edu.duke.cabig.c3pr.domain.Study",  "phaseCode",
						values, "=");

		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
		criteriaParameters.add(advancedSearchCriteriaParameter1);
		
		List<StudySubject> registrations = studySubjectDao.search(criteriaParameters);
		assertEquals("4 registrations not found", 4,  registrations.size());
	}
	
	public void testGetResultSetWithHQLForStudyType() throws Exception {
		List<String> values = new ArrayList<String>();
       	values.add("Ty");

		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
				.buildAdvancedSearchCriteriaParameter(
						"edu.duke.cabig.c3pr.domain.Study",  "type",
						values, "=");

		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
		criteriaParameters.add(advancedSearchCriteriaParameter1);
		
		List<StudySubject> registrations = studySubjectDao.search(criteriaParameters);
		assertEquals("5 registrations not found", 5,  registrations.size());
	}
	
	public void testGetResultSetWithHQLForPersonnelFirstName() throws Exception {
		List<String> values = new ArrayList<String>();
       	values.add("Research Bill%");

		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
				.buildAdvancedSearchCriteriaParameter(
						"edu.duke.cabig.c3pr.domain.ResearchStaff",  "firstName",
						values, "like");

		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
		criteriaParameters.add(advancedSearchCriteriaParameter1);
		
		List<StudySubject> registrations = studySubjectDao.search(criteriaParameters);
		assertEquals("1 registrations not found", 1,  registrations.size());
	}
	
	public void testGetResultSetWithHQLForPersonnelLastName() throws Exception {
		List<String> values = new ArrayList<String>();
       	values.add("Staff2%");

		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
				.buildAdvancedSearchCriteriaParameter(
						"edu.duke.cabig.c3pr.domain.ResearchStaff",  "lastName",
						values, "like");

		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
		criteriaParameters.add(advancedSearchCriteriaParameter1);
		
		List<StudySubject> registrations = studySubjectDao.search(criteriaParameters);
		assertEquals("1 registrations not found", 1,  registrations.size());
	}
	
	public void testGetResultSetWithHQLForPersonnelAssignedIdentifier() throws Exception {
		List<String> values = new ArrayList<String>();
       	values.add("x1");

		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
				.buildAdvancedSearchCriteriaParameter(
						"edu.duke.cabig.c3pr.domain.ResearchStaff",  "assignedIdentifier",
						values, "=");

		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
		criteriaParameters.add(advancedSearchCriteriaParameter1);
		
		List<StudySubject> registrations = studySubjectDao.search(criteriaParameters);
		assertEquals("1 registrations not found", 1,  registrations.size());
	}
     
	public void testGetResultSetWithHQLAllRecord() throws Exception {
		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
				.buildAdvancedSearchCriteriaParameter(
						"edu.duke.cabig.c3pr.domain.StudySubject",  "paymentMethod",
						"%", "like");

		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
		criteriaParameters.add(advancedSearchCriteriaParameter1);
		
		List<StudySubject> registrations = studySubjectDao.search(criteriaParameters);
		assertEquals("5 registrations not found", 5,  registrations.size());
	}
	
}