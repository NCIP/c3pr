package edu.duke.cabig.c3pr.service;

import org.springframework.context.MessageSource;

import edu.duke.cabig.c3pr.constants.RandomizationType;
import edu.duke.cabig.c3pr.constants.RegistrationDataEntryStatus;
import edu.duke.cabig.c3pr.constants.RegistrationWorkFlowStatus;
import edu.duke.cabig.c3pr.constants.ScheduledEpochDataEntryStatus;
import edu.duke.cabig.c3pr.constants.ScheduledEpochWorkFlowStatus;
import edu.duke.cabig.c3pr.dao.StudySubjectDao;
import edu.duke.cabig.c3pr.domain.Arm;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.ScheduledEpoch;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;
import edu.duke.cabig.c3pr.utils.DaoTestCase;
import edu.duke.cabig.c3pr.utils.PersistedStudySubjectCreator;
import edu.duke.cabig.c3pr.xml.XmlMarshaller;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Jun 4, 2007 Time: 2:33:16 PM To change this template
 * use File | Settings | File Templates.
 */

public class StudySubjectXMLImporterServiceTestCase extends DaoTestCase {

    private StudySubjectDao studySubjectDao;

    private StudySubjectXMLImporterService studySubjectXMLImporterService;

    private XmlMarshaller xmlUtility;

    private MessageSource c3prErrorMessages;

    private PersistedStudySubjectCreator persistedStudySubjectCreator;

    protected void setUp() throws Exception {
        super.setUp();
        studySubjectDao = (StudySubjectDao) getApplicationContext().getBean("studySubjectDao");

        studySubjectXMLImporterService = (StudySubjectXMLImporterService) getApplicationContext()
                        .getBean("studySubjectXMLImporterService");

        c3prErrorMessages = (MessageSource) getApplicationContext().getBean("c3prErrorMessages");

        xmlUtility = new XmlMarshaller((String) getApplicationContext().getBean(
                        "c3pr-registration-xml-castor-mapping"));
        persistedStudySubjectCreator = new PersistedStudySubjectCreator(getApplicationContext());
    }

    /*
     * Test Cases for import registration Multi Site Trial Treatment Epoch Book Randomization
     */
    public void testImportRegistrationCase0() throws Exception {
        StudySubject studySubject = persistedStudySubjectCreator
                        .getMultiSiteRandomizedStudySubject(RandomizationType.BOOK, false);
        Participant participant = persistedStudySubjectCreator.createNewParticipant();
        studySubject.setStudySubjectDemographics(participant.createStudySubjectDemographics());
        studySubject.setStudySubjectDemographics(participant.createStudySubjectDemographics());
        persistedStudySubjectCreator.addMRNIdentifierToSubject(studySubject.getStudySubjectDemographics().getMasterSubject(),
                        studySubject.getStudySite().getHealthcareSite());
        persistedStudySubjectCreator.addScheduledNonEnrollingEpochFromStudyEpochs(studySubject);
        persistedStudySubjectCreator.completeRegistrationDataEntry(studySubject);
        persistedStudySubjectCreator.completeScheduledEpochDataEntry(studySubject);
        persistedStudySubjectCreator.forceAssignArm(studySubject);
        String xml = xmlUtility.toXML(studySubject);
        Integer savedId;
        {
            StudySubject saved = null;
            try {
                saved = studySubjectXMLImporterService.importStudySubject(xml);
            }
            catch (Exception e) {
                e.printStackTrace();
                fail("shouldnt have thrown exception.");
            }
            savedId = saved.getId();
            assertNotNull("The registration didn't get an id", savedId);
        }

        interruptSession();
        {
            StudySubject loaded = studySubjectDao.getById(savedId);
            assertNotNull("Could not reload registration with id " + savedId, loaded);
            assertEquals("Wrong number of scheduled epochs", 1, loaded.getScheduledEpochs().size());
            assertEquals("Wrong number of scheduled treatment epochs", 1, loaded
                            .getScheduledEpochs().size());
            assertEquals("Wrong number of scheduled epochs", 1, loaded
                            .getScheduledEpochs().size());
            ScheduledEpoch scheduledTreatmentEpoch = loaded.getScheduledEpoch();
            assertEquals("Wrong eligibility indicator", true, scheduledTreatmentEpoch
                            .getEligibilityIndicator().booleanValue());
            assertEquals("Wrong registration data entry status",
                            RegistrationDataEntryStatus.COMPLETE, loaded.getRegDataEntryStatus());
            assertEquals("Wrong epoch data entry status", ScheduledEpochDataEntryStatus.COMPLETE,
                            loaded.getScheduledEpoch().getScEpochDataEntryStatus());
            assertEquals("Wrong registration work flow status",
                            RegistrationWorkFlowStatus.ENROLLED, loaded.getRegWorkflowStatus());
            assertEquals("Wrong epoch work flow status", ScheduledEpochWorkFlowStatus.REGISTERED,
                            loaded.getScheduledEpoch().getScEpochWorkflowStatus());
        }
        interruptSession();

    }

    /*
     * Test Cases for import registration Multi Site Trial Treatment Epoch Phone call Randomization
     * No Arm assigned
     */
    public void testImportRegistrationCase1() throws Exception {
        StudySubject studySubject = persistedStudySubjectCreator
                        .getMultiSiteRandomizedStudySubject(RandomizationType.PHONE_CALL, false);
        interruptSession();
        studySubject.setStudySubjectDemographics(persistedStudySubjectCreator.createNewParticipant().createStudySubjectDemographics());
        persistedStudySubjectCreator.addMRNIdentifierToSubject(studySubject.getStudySubjectDemographics().getMasterSubject(),
                        studySubject.getStudySite().getHealthcareSite());
        persistedStudySubjectCreator.addScheduledNonEnrollingEpochFromStudyEpochs(studySubject);
        persistedStudySubjectCreator.completeRegistrationDataEntry(studySubject);
        persistedStudySubjectCreator.buildCommandObject(studySubject);
        persistedStudySubjectCreator.bindEligibility(studySubject);
        persistedStudySubjectCreator.bindStratification(studySubject);
        String xml = xmlUtility.toXML(studySubject);
        try {
            studySubjectXMLImporterService.importStudySubject(xml);
        }
        catch (C3PRCodedException e) {
            e.printStackTrace();
            assertEquals(
                            "Exception Code unmatched",
                            getCode("C3PR.EXCEPTION.REGISTRATION.IMPORT.REQUIRED.ARM.NOTFOUND.CODE"),
                            e.getExceptionCode());
            return;
        }
        catch (Exception e) {
            e.printStackTrace();
            fail("Wrong Exception Type.");
            return;
        }
        fail("Should have thrown C3PR Coded Exception.");
    }

    /*
     * Test Cases for import registration Multi Site Trial Non Treatment Epoch, Non Reserving, Non
     * Registering Non Randomized
     */
    public void testImportRegistrationCase2() throws Exception {
        StudySubject studySubject = persistedStudySubjectCreator
                        .getMultiSiteNonRandomizedStudySubject(false, false, false);
        studySubject.setStudySubjectDemographics(persistedStudySubjectCreator.createNewParticipant().createStudySubjectDemographics());
        persistedStudySubjectCreator.addMRNIdentifierToSubject(studySubject.getStudySubjectDemographics().getMasterSubject(),
                        studySubject.getStudySite().getHealthcareSite());
        persistedStudySubjectCreator.addScheduledNonEnrollingEpochFromStudyEpochs(studySubject);
        persistedStudySubjectCreator.completeRegistrationDataEntry(studySubject);
        persistedStudySubjectCreator.completeScheduledEpochDataEntry(studySubject);
        String xml = xmlUtility.toXML(studySubject);
        Integer savedId;
        {
            StudySubject saved = null;
            try {
                saved = studySubjectXMLImporterService.importStudySubject(xml);
            }
            catch (Exception e) {
                e.printStackTrace();
                interruptSession();
                fail("shouldnt have thrown exception.");
            }
            finally {
                interruptSession();
            }
            savedId = saved.getId();
            assertNotNull("The registration didn't get an id", savedId);
        }
        interruptSession();
        {
            StudySubject loaded = studySubjectDao.getById(savedId);
            assertNotNull("Could not reload registration with id " + savedId, loaded);
            assertEquals("Wrong number of scheduled epochs", 1, loaded.getScheduledEpochs().size());
            assertEquals("Wrong number of scheduled epochs", 1, loaded
                            .getScheduledEpochs().size());
            assertEquals("Wrong number of scheduled non treatment epochs", 1, loaded
                            .getScheduledEpochs().size());
            assertEquals("Wrong registration data entry status",
                            RegistrationDataEntryStatus.COMPLETE, loaded.getRegDataEntryStatus());
            assertEquals("Wrong epoch data entry status", ScheduledEpochDataEntryStatus.COMPLETE,
                            loaded.getScheduledEpoch().getScEpochDataEntryStatus());
            assertEquals("Wrong epoch work flow status", ScheduledEpochWorkFlowStatus.REGISTERED,
                            loaded.getScheduledEpoch().getScEpochWorkflowStatus());
        }
        interruptSession();

    }

    /*
     * Test Cases for import registration Local Trial Treatment Epoch Book Randomization
     */
    public void testImportRegistrationCase3() throws Exception {
        StudySubject studySubject = persistedStudySubjectCreator.getLocalRandomizedStudySubject(
                        RandomizationType.BOOK, false);
        studySubject.setStudySubjectDemographics(persistedStudySubjectCreator.createNewParticipant().createStudySubjectDemographics());
        persistedStudySubjectCreator.addMRNIdentifierToSubject(studySubject.getStudySubjectDemographics().getMasterSubject(),
                        studySubject.getStudySite().getHealthcareSite());
        persistedStudySubjectCreator.addScheduledNonEnrollingEpochFromStudyEpochs(studySubject);
        persistedStudySubjectCreator.completeRegistrationDataEntry(studySubject);
        persistedStudySubjectCreator.completeScheduledEpochDataEntry(studySubject);
        persistedStudySubjectCreator.forceAssignArm(studySubject);
        String xml = xmlUtility.toXML(studySubject);
        Integer savedId;
        {
            StudySubject saved = null;
            try {
                saved = studySubjectXMLImporterService.importStudySubject(xml);
            }
            catch (Exception e) {
                e.printStackTrace();
                fail("shouldnt have thrown exception.");
            }
            finally {
                interruptSession();
            }
            savedId = saved.getId();
            assertNotNull("The registration didn't get an id", savedId);
        }
        interruptSession();
        {
            StudySubject loaded = studySubjectDao.getById(savedId);
            assertNotNull("Could not reload registration with id " + savedId, loaded);
            assertEquals("Wrong number of scheduled epochs", 1, loaded.getScheduledEpochs().size());
            assertEquals("Wrong number of scheduled treatment epochs", 1, loaded
                            .getScheduledEpochs().size());
            assertEquals("Wrong number of scheduled epochs", 1, loaded
                            .getScheduledEpochs().size());
            ScheduledEpoch scheduledTreatmentEpoch = loaded.getScheduledEpoch();
            assertEquals("Wrong eligibility indicator", true, scheduledTreatmentEpoch
                            .getEligibilityIndicator().booleanValue());
            assertEquals("Wrong registration data entry status",
                            RegistrationDataEntryStatus.COMPLETE, loaded.getRegDataEntryStatus());
            assertEquals("Wrong epoch data entry status", ScheduledEpochDataEntryStatus.COMPLETE,
                            loaded.getScheduledEpoch().getScEpochDataEntryStatus());
            assertEquals("Wrong registration work flow status",
                            RegistrationWorkFlowStatus.ENROLLED, loaded.getRegWorkflowStatus());
            assertEquals("Wrong epoch work flow status", ScheduledEpochWorkFlowStatus.REGISTERED,
                            loaded.getScheduledEpoch().getScEpochWorkflowStatus());
        }
        interruptSession();
    }

    /*
     * Test Cases for import registration Local Trial Non Randomized Treatment Epoch Wrong Arm Name
     */
    public void testImportRegistrationCase4() throws Exception {
        StudySubject studySubject = persistedStudySubjectCreator
                        .getLocalNonRandomizedTrestmentWithArmStudySubject(false);
        interruptSession();
        studySubject.setStudySubjectDemographics(persistedStudySubjectCreator.createNewParticipant().createStudySubjectDemographics());
        persistedStudySubjectCreator.addMRNIdentifierToSubject(studySubject.getStudySubjectDemographics().getMasterSubject(),
                        studySubject.getStudySite().getHealthcareSite());
        persistedStudySubjectCreator.addScheduledNonEnrollingEpochFromStudyEpochs(studySubject);
        persistedStudySubjectCreator.completeRegistrationDataEntry(studySubject);
        persistedStudySubjectCreator.completeScheduledEpochDataEntry(studySubject);
        Arm arm = new Arm();
        arm.setName("Not matching");
        (studySubject.getScheduledEpoch()).getScheduledArm().setArm(arm);
        String xml = xmlUtility.toXML(studySubject);
        try {
            studySubjectXMLImporterService.importStudySubject(xml);
        }
        catch (C3PRCodedException e) {
            e.printStackTrace();
            assertEquals("Exception Code unmatched",
                            getCode("C3PR.EXCEPTION.REGISTRATION.NOTFOUND.ARM_NAME.CODE"), e
                                            .getExceptionCode());
            return;
        }
        catch (Exception e) {
            e.printStackTrace();
            fail("Wrong Exception Type.");
        }
        fail("Should have thrown C3PR Coded Exception.");
    }

    /*
     * Test Cases for import registration Local Trial NonTreatment Epoch Reserving
     */
    public void testImportRegistrationCase5() throws Exception {
        StudySubject studySubject = persistedStudySubjectCreator.getLocalNonRandomizedStudySubject(
                        true, false, false);
        studySubject.setStudySubjectDemographics(persistedStudySubjectCreator.createNewParticipant().createStudySubjectDemographics());
        persistedStudySubjectCreator.addMRNIdentifierToSubject(studySubject.getStudySubjectDemographics().getMasterSubject(),
                        studySubject.getStudySite().getHealthcareSite());
        persistedStudySubjectCreator.addScheduledNonEnrollingEpochFromStudyEpochs(studySubject);
        persistedStudySubjectCreator.completeRegistrationDataEntry(studySubject);
        persistedStudySubjectCreator.completeScheduledEpochDataEntry(studySubject);
        String xml = xmlUtility.toXML(studySubject);
        Integer savedId;
        {
            StudySubject saved = null;
            try {
                saved = studySubjectXMLImporterService.importStudySubject(xml);
            }
            catch (Exception e) {
                e.printStackTrace();
                fail("shouldnt have thrown exception.");
            }
            finally {
                interruptSession();
            }
            savedId = saved.getId();
            assertNotNull("The registration didn't get an id", savedId);
        }
        interruptSession();
        {
            StudySubject loaded = studySubjectDao.getById(savedId);
            assertNotNull("Could not reload registration with id " + savedId, loaded);
            assertEquals("Wrong number of scheduled epochs", 1, loaded.getScheduledEpochs().size());
            assertEquals("Wrong number of scheduled epochs", 1, loaded
                            .getScheduledEpochs().size());
            assertEquals("Wrong number of scheduled non treatment epochs", 1, loaded
                            .getScheduledEpochs().size());
            assertEquals("Wrong registration data entry status",
                            RegistrationDataEntryStatus.COMPLETE, loaded.getRegDataEntryStatus());
            assertEquals("Wrong epoch data entry status", ScheduledEpochDataEntryStatus.COMPLETE,
                            loaded.getScheduledEpoch().getScEpochDataEntryStatus());
            assertEquals("Wrong registration work flow status",
                            RegistrationWorkFlowStatus.RESERVED, loaded.getRegWorkflowStatus());
            assertEquals("Wrong epoch work flow status", ScheduledEpochWorkFlowStatus.REGISTERED,
                            loaded.getScheduledEpoch().getScEpochWorkflowStatus());
        }
        interruptSession();

    }

    /*
     * Test Cases for import registration Local Trial NonTreatment Epoch Enrolling Non Randomized
     */
    public void testImportRegistrationCase6() throws Exception {
        StudySubject studySubject = persistedStudySubjectCreator.getLocalNonRandomizedStudySubject(
                        false, true, false);
        studySubject.setStudySubjectDemographics(persistedStudySubjectCreator.createNewParticipant().createStudySubjectDemographics());
        persistedStudySubjectCreator.addMRNIdentifierToSubject(studySubject.getStudySubjectDemographics().getMasterSubject(),
                        studySubject.getStudySite().getHealthcareSite());
        persistedStudySubjectCreator.addScheduledNonEnrollingEpochFromStudyEpochs(studySubject);
        persistedStudySubjectCreator.completeRegistrationDataEntry(studySubject);
        persistedStudySubjectCreator.completeScheduledEpochDataEntry(studySubject);
        Integer savedId;
        String xml = xmlUtility.toXML(studySubject);
        {
            StudySubject saved = null;
            try {
                saved = studySubjectXMLImporterService.importStudySubject(xml);
            }
            catch (Exception e) {
                e.printStackTrace();
                fail("shouldnt have thrown exception.");
            }
            finally {
                interruptSession();
            }
            savedId = saved.getId();
            assertNotNull("The registration didn't get an id", savedId);
        }
        interruptSession();
        {
            StudySubject loaded = studySubjectDao.getById(savedId);
            assertNotNull("Could not reload registration with id " + savedId, loaded);
            assertEquals("Wrong number of scheduled epochs", 1, loaded.getScheduledEpochs().size());
            assertEquals("Wrong number of scheduled epochs", 1, loaded
                            .getScheduledEpochs().size());
            assertEquals("Wrong number of scheduled non treatment epochs", 1, loaded
                            .getScheduledEpochs().size());
            assertEquals("Wrong registration data entry status",
                            RegistrationDataEntryStatus.COMPLETE, loaded.getRegDataEntryStatus());
            assertEquals("Wrong epoch data entry status", ScheduledEpochDataEntryStatus.COMPLETE,
                            loaded.getScheduledEpoch().getScEpochDataEntryStatus());
            assertEquals("Wrong registration work flow status",
                            RegistrationWorkFlowStatus.ENROLLED, loaded.getRegWorkflowStatus());
            assertEquals("Wrong epoch work flow status", ScheduledEpochWorkFlowStatus.REGISTERED,
                            loaded.getScheduledEpoch().getScEpochWorkflowStatus());
        }
        interruptSession();

    }

    /*
     * Test Cases for import registration Local Trial NonTreatment Epoch Non Enrolling, Non
     * Reserving
     */
    public void testImportRegistrationCase7() throws Exception {
        StudySubject studySubject = persistedStudySubjectCreator.getLocalNonRandomizedStudySubject(
                        false, false, false);
        studySubject.setStudySubjectDemographics(persistedStudySubjectCreator.createNewParticipant().createStudySubjectDemographics());
        persistedStudySubjectCreator.addMRNIdentifierToSubject(studySubject.getStudySubjectDemographics().getMasterSubject(),
                        studySubject.getStudySite().getHealthcareSite());
        persistedStudySubjectCreator.addScheduledNonEnrollingEpochFromStudyEpochs(studySubject);
        persistedStudySubjectCreator.completeRegistrationDataEntry(studySubject);
        persistedStudySubjectCreator.completeScheduledEpochDataEntry(studySubject);
        Integer savedId;
        String xml = xmlUtility.toXML(studySubject);
        {
            StudySubject saved = null;
            try {
                saved = studySubjectXMLImporterService.importStudySubject(xml);
            }
            catch (Exception e) {
                e.printStackTrace();
                fail("shouldnt have thrown exception.");
            }
            finally {
                interruptSession();
            }
            savedId = saved.getId();
            assertNotNull("The registration didn't get an id", savedId);
        }
        interruptSession();
        {
            StudySubject loaded = studySubjectDao.getById(savedId);
            assertNotNull("Could not reload registration with id " + savedId, loaded);
            assertEquals("Wrong number of scheduled epochs", 1, loaded.getScheduledEpochs().size());
            assertEquals("Wrong number of scheduled epochs", 1, loaded
                            .getScheduledEpochs().size());
            assertEquals("Wrong number of scheduled non treatment epochs", 1, loaded
                            .getScheduledEpochs().size());
            assertEquals("Wrong registration data entry status",
                            RegistrationDataEntryStatus.COMPLETE, loaded.getRegDataEntryStatus());
            assertEquals("Wrong epoch data entry status", ScheduledEpochDataEntryStatus.COMPLETE,
                            loaded.getScheduledEpoch().getScEpochDataEntryStatus());
            assertEquals("Wrong epoch work flow status", ScheduledEpochWorkFlowStatus.REGISTERED,
                            loaded.getScheduledEpoch().getScEpochWorkflowStatus());
        }
        interruptSession();

    }

    /*
     * Test Cases for import registration Multi Site Trial Treatment Epoch Book Randomization Study
     * Site is Co Ordinating Center
     */
    public void testImportRegistrationCase8() throws Exception {
        StudySubject studySubject = persistedStudySubjectCreator
                        .getMultiSiteRandomizedStudySubject(RandomizationType.BOOK, true);
        studySubject.setStudySubjectDemographics(persistedStudySubjectCreator.createNewParticipant().createStudySubjectDemographics());
        persistedStudySubjectCreator.addMRNIdentifierToSubject(studySubject.getStudySubjectDemographics().getMasterSubject(),
                        studySubject.getStudySite().getHealthcareSite());
        persistedStudySubjectCreator.addScheduledNonEnrollingEpochFromStudyEpochs(studySubject);
        persistedStudySubjectCreator.completeRegistrationDataEntry(studySubject);
        persistedStudySubjectCreator.completeScheduledEpochDataEntry(studySubject);
        persistedStudySubjectCreator.forceAssignArm(studySubject);
        String xml = xmlUtility.toXML(studySubject);
        Integer savedId;
        {
            StudySubject saved = null;
            try {
                saved = studySubjectXMLImporterService.importStudySubject(xml);
            }
            catch (Exception e) {
                e.printStackTrace();
                fail("shouldnt have thrown exception.");
            }
            finally {
                interruptSession();
            }
            savedId = saved.getId();
            assertNotNull("The registration didn't get an id", savedId);
        }
        interruptSession();
        {
            StudySubject loaded = studySubjectDao.getById(savedId);
            assertNotNull("Could not reload registration with id " + savedId, loaded);
            assertEquals("Wrong number of scheduled epochs", 1, loaded.getScheduledEpochs().size());
            assertEquals("Wrong number of scheduled treatment epochs", 1, loaded
                            .getScheduledEpochs().size());
            assertEquals("Wrong number of scheduled epochs", 1, loaded
                            .getScheduledEpochs().size());
            ScheduledEpoch scheduledTreatmentEpoch = loaded.getScheduledEpoch();
            assertEquals("Wrong eligibility indicator", true, scheduledTreatmentEpoch
                            .getEligibilityIndicator().booleanValue());
            assertEquals("Wrong registration data entry status",
                            RegistrationDataEntryStatus.COMPLETE, loaded.getRegDataEntryStatus());
            assertEquals("Wrong epoch data entry status", ScheduledEpochDataEntryStatus.COMPLETE,
                            loaded.getScheduledEpoch().getScEpochDataEntryStatus());
            assertEquals("Wrong registration work flow status",
                            RegistrationWorkFlowStatus.ENROLLED, loaded.getRegWorkflowStatus());
            assertEquals("Wrong epoch work flow status", ScheduledEpochWorkFlowStatus.REGISTERED,
                            loaded.getScheduledEpoch().getScEpochWorkflowStatus());
        }
        interruptSession();

    }

    private int getCode(String errortypeString) {
        return Integer.parseInt(this.c3prErrorMessages.getMessage(errortypeString, null, null));
    }
}
