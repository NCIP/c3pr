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
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import edu.duke.cabig.c3pr.C3PRUseCases;
import edu.duke.cabig.c3pr.domain.EligibilityCriteria;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.IdentifierGenerator;
import edu.duke.cabig.c3pr.domain.InclusionEligibilityCriteria;
import edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.RegistrationDataEntryStatus;
import edu.duke.cabig.c3pr.domain.RegistrationWorkFlowStatus;
import edu.duke.cabig.c3pr.domain.ScheduledArm;
import edu.duke.cabig.c3pr.domain.ScheduledEpoch;
import edu.duke.cabig.c3pr.domain.ScheduledEpochDataEntryStatus;
import edu.duke.cabig.c3pr.domain.ScheduledEpochWorkFlowStatus;
import edu.duke.cabig.c3pr.domain.StratificationCriterion;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.SubjectEligibilityAnswer;
import edu.duke.cabig.c3pr.domain.SubjectStratificationAnswer;
import edu.duke.cabig.c3pr.domain.SystemAssignedIdentifier;
import edu.duke.cabig.c3pr.utils.DaoTestCase;
import edu.duke.cabig.c3pr.utils.StringUtils;
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

    private AnatomicSiteDao anatomicSiteDao;

    private StudySubjectDao studySubjectDao;

    private ScheduledEpochDao scheduledEpochDao;

    private HealthcareSiteDao healthcareSiteDao;

    private XmlMarshaller xmlUtility;

    private XmlMarshaller xmlUtilityStudy;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        dao = (ParticipantDao) getApplicationContext().getBean("participantDao");
        studySiteDao = (StudySiteDao) getApplicationContext().getBean("studySiteDao");
        epochDao = (EpochDao) getApplicationContext().getBean("epochDao");
        anatomicSiteDao = (AnatomicSiteDao) getApplicationContext().getBean("anatomicSiteDao");
        studySubjectDao = (StudySubjectDao) getApplicationContext().getBean("studySubjectDao");
        scheduledEpochDao = (ScheduledEpochDao) getApplicationContext()
                        .getBean("scheduledEpochDao");
        studyDao = (StudyDao) getApplicationContext().getBean("studyDao");
        healthcareSiteDao = (HealthcareSiteDao) getApplicationContext()
                        .getBean("healthcareSiteDao");
        xmlUtility = new XmlMarshaller((String) getApplicationContext().getBean(
                        "c3pr-registration-xml-castor-mapping"));
        xmlUtilityStudy = new XmlMarshaller((String) getApplicationContext().getBean(
                        "c3pr-study-xml-castorMapping"));
    }

    /*
     * 
     */
    public void testForReport() {
        // Fetch the subject with firstName Rudolph
        List<Participant> participantList = dao.getByFirstName("Rudolph");
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
    public void testAdvancedStudySearch() {
        List<StudySubject> ssList;
        {
            Study study = new Study();
            study.setShortTitleText("");

            SystemAssignedIdentifier id = new SystemAssignedIdentifier();
            id.setValue(null);
            study.addIdentifier(id);

            Participant participant = new Participant();
            id = new SystemAssignedIdentifier();
            id.setValue(null);
            participant.addIdentifier(id);

            participant.setFirstName("Alfred");
            participant.setLastName("");

            StudySite studySite = new StudySite();
            studySite.setStudy(study);
            studySite.setHealthcareSite(new HealthcareSite());

            StudySubject studySubject = new StudySubject();
            studySubject.setStudySite(studySite);
            studySubject.setParticipant(participant);

            ssList = studySubjectDao.advancedStudySearch(studySubject);
        }
        interruptSession();
    }

    public void testCurrentScheduledEpoch() throws Exception {
        StudySubject studySubject = new StudySubject();
        ScheduledEpoch scheduledEpoch1 = new ScheduledEpoch();
        scheduledEpoch1.setStartDate((new GregorianCalendar(1990, 1, 2)).getTime());
        ScheduledEpoch scheduledEpoch2 = new ScheduledEpoch();
        studySubject.addScheduledEpoch(scheduledEpoch1);
        studySubject.addScheduledEpoch(scheduledEpoch2);
        assertEquals("current epoch is wrong", studySubject.getCurrentScheduledEpoch(),
                        scheduledEpoch2);
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
            id.setType("Test");
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
        assertEquals(2, actual.size());
        List<Integer> ids = collectIds(actual);
        assertContains("Wrong Study Subject found", ids, 1000);
        assertContains("Wrong Study Subject found", ids, 1001);
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
    
    public void testGetStudySubjectsByIdentifiers() throws Exception {
    /*	
    	SystemAssignedIdentifier sysIdentifier = new SystemAssignedIdentifier();
    	sysIdentifier.setSystemName("nci");
    	sysIdentifier.setType("local");
    	sysIdentifier.setValue("grid");
    	List<Identifier> studySubjectSysIdentifiers = new ArrayList<Identifier>();
    	studySubjectSysIdentifiers.add(sysIdentifier);
    	
        List<StudySubject> studyPartsBySys = studySubjectDao.getByIdentifiers(studySubjectSysIdentifiers);
        assertSame("Wrong number of Study Participants", 1, studyPartsBySys.size());*/
        
    	StudySubject studySubject = studySubjectDao.getById(1000);
    	
        OrganizationAssignedIdentifier orgIdentifier = new OrganizationAssignedIdentifier();
        orgIdentifier.setHealthcareSite(studySubject.getStudySite().getHealthcareSite());
        orgIdentifier.setType("Coordinating Center Identifier");
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
            StudySite studySite = studySiteDao.getById(1001);
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
            studySubject.setInformedConsentSignedDate(new Date());
            studySubject.setInformedConsentVersion("1.0");
            studySubject.setTreatingPhysician(studySubject.getStudySite()
                            .getStudyInvestigatorsInternal().get(0));
            studySubject.getDiseaseHistory().setAnatomicSite(anatomicSiteDao.getById(1000));
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
        studySubject.setInformedConsentSignedDate(new Date());
        studySubject.setInformedConsentVersion("1.0");
        studySubject.setTreatingPhysician(studySubject.getStudySite()
                        .getStudyInvestigatorsInternal().get(0));
        return studySubject;
    }

    private Object bindEnrollmentDetailsOtherTreatingPhysician(Object command) {
        StudySubject studySubject = (StudySubject) command;
        studySubject.setInformedConsentSignedDate(new Date());
        studySubject.setInformedConsentVersion("1.0");
        studySubject.setOtherTreatingPhysician("other treating physician");
        return studySubject;
    }

    private Object bindDiseaseDetails(Object command) {
        StudySubject studySubject = (StudySubject) command;
        studySubject.getDiseaseHistory().setAnatomicSite(anatomicSiteDao.getById(1000));
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
        studySubject.setRegDataEntryStatus(evaluateRegistrationDataEntryStatus(studySubject));
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
            if (command.getParticipant() != null) dao.reassociate(command.getParticipant());
            if (command.getStudySite() != null) {
                studySiteDao.reassociate(command.getStudySite());
                studyDao.reassociate(command.getStudySite().getStudy());
            }
        }
        return command;
    }

    public RegistrationDataEntryStatus evaluateRegistrationDataEntryStatus(StudySubject studySubject) {
        if (studySubject.getInformedConsentSignedDateStr().equals("")) return RegistrationDataEntryStatus.INCOMPLETE;
        if (StringUtils.getBlankIfNull(studySubject.getInformedConsentVersion()).equals("")) return RegistrationDataEntryStatus.INCOMPLETE;
        return RegistrationDataEntryStatus.COMPLETE;
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
            assertEquals("Wrong registration status", "COMPLETE", loaded.getRegDataEntryStatus()
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
       orgIdentifier.setType("Coordinating Center Identifier");
       orgIdentifier.setValue("CoordinatingCenterIdValue");
       Study study = studySubject.getStudySite().getStudy();
       study.addIdentifier(orgIdentifier);
       studyDao.save(study);
       
       int numberOfIdentifiers = studySubject.getIdentifiers().size();
       
       studySubject.addIdentifier(IdentifierGenerator
					.generateOrganizationAssignedIdentifier(studySubject));
       studySubjectDao.save(studySubject);
       StudySubject updatedStudySubject = studySubjectDao.getById(1000);
       assertEquals("Wrong number of study subject identifiers retrieved", numberOfIdentifiers+1, updatedStudySubject.getIdentifiers().size());
    }
}