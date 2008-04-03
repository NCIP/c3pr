package edu.duke.cabig.c3pr.service;

import org.springframework.context.MessageSource;

import edu.duke.cabig.c3pr.dao.HealthcareSiteDao;
import edu.duke.cabig.c3pr.dao.ParticipantDao;
import edu.duke.cabig.c3pr.dao.StudyDao;
import edu.duke.cabig.c3pr.dao.StudySubjectDao;
import edu.duke.cabig.c3pr.domain.Arm;
import edu.duke.cabig.c3pr.domain.RandomizationType;
import edu.duke.cabig.c3pr.domain.RegistrationDataEntryStatus;
import edu.duke.cabig.c3pr.domain.RegistrationWorkFlowStatus;
import edu.duke.cabig.c3pr.domain.ScheduledEpochDataEntryStatus;
import edu.duke.cabig.c3pr.domain.ScheduledEpochWorkFlowStatus;
import edu.duke.cabig.c3pr.domain.ScheduledTreatmentEpoch;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;
import edu.duke.cabig.c3pr.utils.DaoTestCase;
import edu.duke.cabig.c3pr.utils.PersistedStudySubjectCreator;
import edu.duke.cabig.c3pr.utils.StudyCreationHelper;
import edu.duke.cabig.c3pr.xml.XmlMarshaller;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Jun 4, 2007 Time: 2:33:16 PM To change this template
 * use File | Settings | File Templates.
 */

public class StudySubjectXMLImporterServiceTestCase extends DaoTestCase {

    private StudySubjectService studySubjectService;

    private StudyCreationHelper studyCreationHelper = new StudyCreationHelper();

    private StudyDao dao = (StudyDao) getApplicationContext().getBean("studyDao");

    private HealthcareSiteDao healthcareSitedao = (HealthcareSiteDao) getApplicationContext()
                    .getBean("healthcareSiteDao");

    private ParticipantDao participantDao = (ParticipantDao) getApplicationContext().getBean(
                    "participantDao");

    private StudySubjectDao studySubjectDao = (StudySubjectDao) getApplicationContext().getBean(
                    "studySubjectDao");

    private StudySubjectXMLImporterService studySubjectXMLImporterService = (StudySubjectXMLImporterService) getApplicationContext()
                    .getBean("studySubjectXMLImporterService");

    private XmlMarshaller xmlUtility;

    private MessageSource c3prErrorMessages = (MessageSource) getApplicationContext().getBean(
                    "c3prErrorMessages");

    private final String identifierTypeValueStr = "Coordinating Center Identifier";
    
    private PersistedStudySubjectCreator persistedStudySubjectCreator;

    protected void setUp() throws Exception {
        super.setUp();
        studySubjectService = (StudySubjectService) getApplicationContext().getBean(
                        "studySubjectService");
        xmlUtility = new XmlMarshaller((String) getApplicationContext().getBean(
                        "ccts-registration-castorMapping"));
        persistedStudySubjectCreator=new PersistedStudySubjectCreator(getApplicationContext());
    }

    /*
     * Test Cases for import registration Multi Site Trial Treatment Epoch Book Randomization
     */
    public void testImportRegistrationCase0() throws Exception {
        StudySubject studySubject = persistedStudySubjectCreator.getMultiSiteRandomizedStudySubject(RandomizationType.BOOK, false);
        studySubject.setParticipant(persistedStudySubjectCreator.createNewParticipant());
        persistedStudySubjectCreator.addMRNIdentifierToSubject(studySubject.getParticipant(), studySubject.getStudySite().getHealthcareSite());
        persistedStudySubjectCreator.addScheduledEpochFromStudyEpochs(studySubject);
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
                            .getScheduledTreatmentEpochs().size());
            assertEquals("Wrong number of scheduled non treatment epochs", 0, loaded
                            .getScheduledNonTreatmentEpochs().size());
            assertEquals("getIfTreatmentScheduledEpoch return is inconsistent", true, loaded
                            .getIfTreatmentScheduledEpoch());
            ScheduledTreatmentEpoch scheduledTreatmentEpoch = (ScheduledTreatmentEpoch) loaded
                            .getScheduledEpoch();
            assertEquals("Wrong eligibility indicator", true, scheduledTreatmentEpoch
                            .getEligibilityIndicator().booleanValue());
            assertEquals("Wrong registration data entry status",
                            RegistrationDataEntryStatus.COMPLETE, loaded.getRegDataEntryStatus());
            assertEquals("Wrong epoch data entry status", ScheduledEpochDataEntryStatus.COMPLETE,
                            loaded.getScheduledEpoch().getScEpochDataEntryStatus());
            assertEquals("Wrong registration work flow status",
                            RegistrationWorkFlowStatus.REGISTERED, loaded.getRegWorkflowStatus());
            assertEquals("Wrong epoch work flow status", ScheduledEpochWorkFlowStatus.APPROVED,
                            loaded.getScheduledEpoch().getScEpochWorkflowStatus());
        }
        interruptSession();

    }

    /*
     * Test Cases for import registration Multi Site Trial Treatment Epoch Phone call Randomization
     * No Arm assigned
     */
    public void testImportRegistrationCase1() throws Exception {
        StudySubject studySubject = persistedStudySubjectCreator.getMultiSiteRandomizedStudySubject(RandomizationType.PHONE_CALL, false);
        studySubject.setParticipant(persistedStudySubjectCreator.createNewParticipant());
        persistedStudySubjectCreator.addMRNIdentifierToSubject(studySubject.getParticipant(), studySubject.getStudySite().getHealthcareSite());
        persistedStudySubjectCreator.addScheduledEpochFromStudyEpochs(studySubject);
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
            interruptSession();
            assertEquals(
                            "Exception Code unmatched",
                            getCode("C3PR.EXCEPTION.REGISTRATION.IMPORT.REQUIRED.ARM.NOTFOUND.CODE"),
                            e.getExceptionCode());
            return;
        }
        catch (Exception e) {
            e.printStackTrace();
            interruptSession();
            fail("Wrong Exception Type.");
            return;
        }
        fail("Should have thrown C3PR Coded Exception.");
        interruptSession();

    }

    /*
     * Test Cases for import registration Multi Site Trial Non Treatment Epoch, Non Reserving, Non
     * Registering Non Randomized
     */
    public void testImportRegistrationCase2() throws Exception {
        StudySubject studySubject = persistedStudySubjectCreator.getMultiSiteNonRandomizedStudySubject(false, false, false);
        studySubject.setParticipant(persistedStudySubjectCreator.createNewParticipant());
        persistedStudySubjectCreator.addMRNIdentifierToSubject(studySubject.getParticipant(), studySubject.getStudySite().getHealthcareSite());
        persistedStudySubjectCreator.addScheduledEpochFromStudyEpochs(studySubject);
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
            assertEquals("Wrong number of scheduled treatment epochs", 0, loaded
                            .getScheduledTreatmentEpochs().size());
            assertEquals("Wrong number of scheduled non treatment epochs", 1, loaded
                            .getScheduledNonTreatmentEpochs().size());
            assertEquals("getIfTreatmentScheduledEpoch return is inconsistent", false, loaded
                            .getIfTreatmentScheduledEpoch());
            assertEquals("Wrong registration data entry status",
                            RegistrationDataEntryStatus.COMPLETE, loaded.getRegDataEntryStatus());
            assertEquals("Wrong epoch data entry status", ScheduledEpochDataEntryStatus.COMPLETE,
                            loaded.getScheduledEpoch().getScEpochDataEntryStatus());
            assertEquals("Wrong registration work flow status",
                            RegistrationWorkFlowStatus.UNREGISTERED, loaded.getRegWorkflowStatus());
            assertEquals("Wrong epoch work flow status", ScheduledEpochWorkFlowStatus.APPROVED,
                            loaded.getScheduledEpoch().getScEpochWorkflowStatus());
        }
        interruptSession();

    }

    /*
     * Test Cases for import registration Local Trial Treatment Epoch Book Randomization
     */
    public void testImportRegistrationCase3() throws Exception {
        StudySubject studySubject = persistedStudySubjectCreator.getLocalRandomizedStudySubject(RandomizationType.BOOK, false);
        studySubject.setParticipant(persistedStudySubjectCreator.createNewParticipant());
        persistedStudySubjectCreator.addMRNIdentifierToSubject(studySubject.getParticipant(), studySubject.getStudySite().getHealthcareSite());
        persistedStudySubjectCreator.addScheduledEpochFromStudyEpochs(studySubject);
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
                            .getScheduledTreatmentEpochs().size());
            assertEquals("Wrong number of scheduled non treatment epochs", 0, loaded
                            .getScheduledNonTreatmentEpochs().size());
            assertEquals("getIfTreatmentScheduledEpoch return is inconsistent", true, loaded
                            .getIfTreatmentScheduledEpoch());
            ScheduledTreatmentEpoch scheduledTreatmentEpoch = (ScheduledTreatmentEpoch) loaded
                            .getScheduledEpoch();
            assertEquals("Wrong eligibility indicator", true, scheduledTreatmentEpoch
                            .getEligibilityIndicator().booleanValue());
            assertEquals("Wrong registration data entry status",
                            RegistrationDataEntryStatus.COMPLETE, loaded.getRegDataEntryStatus());
            assertEquals("Wrong epoch data entry status", ScheduledEpochDataEntryStatus.COMPLETE,
                            loaded.getScheduledEpoch().getScEpochDataEntryStatus());
            assertEquals("Wrong registration work flow status",
                            RegistrationWorkFlowStatus.REGISTERED, loaded.getRegWorkflowStatus());
            assertEquals("Wrong epoch work flow status", ScheduledEpochWorkFlowStatus.APPROVED,
                            loaded.getScheduledEpoch().getScEpochWorkflowStatus());
        }
        interruptSession();
    }

    /*
     * Test Cases for import registration Local Trial Non Randomized Treatment Epoch Wrong Arm Name
     */
    public void testImportRegistrationCase4() throws Exception {
        StudySubject studySubject = persistedStudySubjectCreator.getLocalNonRandomizedTrestmentWithArmStudySubject(false);
        studySubject.setParticipant(persistedStudySubjectCreator.createNewParticipant());
        persistedStudySubjectCreator.addMRNIdentifierToSubject(studySubject.getParticipant(), studySubject.getStudySite().getHealthcareSite());
        persistedStudySubjectCreator.addScheduledEpochFromStudyEpochs(studySubject);
        persistedStudySubjectCreator.completeRegistrationDataEntry(studySubject);
        persistedStudySubjectCreator.completeScheduledEpochDataEntry(studySubject);
        Arm arm=new Arm();
        arm.setName("Not matching");
        ((ScheduledTreatmentEpoch)studySubject.getScheduledEpoch()).getScheduledArm().setArm(arm);
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
        finally {
            interruptSession();
        }
        fail("Should have thrown C3PR Coded Exception.");
        interruptSession();
    }

    /*
     * Test Cases for import registration Local Trial NonTreatment Epoch Reserving
     */
    public void testImportRegistrationCase5() throws Exception {
        StudySubject studySubject = persistedStudySubjectCreator.getLocalNonRandomizedStudySubject(true, false, false);
        studySubject.setParticipant(persistedStudySubjectCreator.createNewParticipant());
        persistedStudySubjectCreator.addMRNIdentifierToSubject(studySubject.getParticipant(), studySubject.getStudySite().getHealthcareSite());
        persistedStudySubjectCreator.addScheduledEpochFromStudyEpochs(studySubject);
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
            assertEquals("Wrong number of scheduled treatment epochs", 0, loaded
                            .getScheduledTreatmentEpochs().size());
            assertEquals("Wrong number of scheduled non treatment epochs", 1, loaded
                            .getScheduledNonTreatmentEpochs().size());
            assertEquals("getIfTreatmentScheduledEpoch return is inconsistent", false, loaded
                            .getIfTreatmentScheduledEpoch());
            assertEquals("Wrong registration data entry status",
                            RegistrationDataEntryStatus.COMPLETE, loaded.getRegDataEntryStatus());
            assertEquals("Wrong epoch data entry status", ScheduledEpochDataEntryStatus.COMPLETE,
                            loaded.getScheduledEpoch().getScEpochDataEntryStatus());
            assertEquals("Wrong registration work flow status",
                            RegistrationWorkFlowStatus.RESERVED, loaded.getRegWorkflowStatus());
            assertEquals("Wrong epoch work flow status", ScheduledEpochWorkFlowStatus.APPROVED,
                            loaded.getScheduledEpoch().getScEpochWorkflowStatus());
        }
        interruptSession();

    }

    /*
     * Test Cases for import registration Local Trial NonTreatment Epoch Enrolling Non Randomized
     */
    public void testImportRegistrationCase6() throws Exception {
        StudySubject studySubject = persistedStudySubjectCreator.getLocalNonRandomizedStudySubject(false, true, false);
        studySubject.setParticipant(persistedStudySubjectCreator.createNewParticipant());
        persistedStudySubjectCreator.addMRNIdentifierToSubject(studySubject.getParticipant(), studySubject.getStudySite().getHealthcareSite());
        persistedStudySubjectCreator.addScheduledEpochFromStudyEpochs(studySubject);
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
            assertEquals("Wrong number of scheduled treatment epochs", 0, loaded
                            .getScheduledTreatmentEpochs().size());
            assertEquals("Wrong number of scheduled non treatment epochs", 1, loaded
                            .getScheduledNonTreatmentEpochs().size());
            assertEquals("getIfTreatmentScheduledEpoch return is inconsistent", false, loaded
                            .getIfTreatmentScheduledEpoch());
            assertEquals("Wrong registration data entry status",
                            RegistrationDataEntryStatus.COMPLETE, loaded.getRegDataEntryStatus());
            assertEquals("Wrong epoch data entry status", ScheduledEpochDataEntryStatus.COMPLETE,
                            loaded.getScheduledEpoch().getScEpochDataEntryStatus());
            assertEquals("Wrong registration work flow status",
                            RegistrationWorkFlowStatus.REGISTERED, loaded.getRegWorkflowStatus());
            assertEquals("Wrong epoch work flow status", ScheduledEpochWorkFlowStatus.APPROVED,
                            loaded.getScheduledEpoch().getScEpochWorkflowStatus());
        }
        interruptSession();

    }

    /*
     * Test Cases for import registration Local Trial NonTreatment Epoch Non Enrolling, Non
     * Reserving
     */
    public void testImportRegistrationCase7() throws Exception {
        StudySubject studySubject = persistedStudySubjectCreator.getLocalNonRandomizedStudySubject(false, false, false);
        studySubject.setParticipant(persistedStudySubjectCreator.createNewParticipant());
        persistedStudySubjectCreator.addMRNIdentifierToSubject(studySubject.getParticipant(), studySubject.getStudySite().getHealthcareSite());
        persistedStudySubjectCreator.addScheduledEpochFromStudyEpochs(studySubject);
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
            assertEquals("Wrong number of scheduled treatment epochs", 0, loaded
                            .getScheduledTreatmentEpochs().size());
            assertEquals("Wrong number of scheduled non treatment epochs", 1, loaded
                            .getScheduledNonTreatmentEpochs().size());
            assertEquals("getIfTreatmentScheduledEpoch return is inconsistent", false, loaded
                            .getIfTreatmentScheduledEpoch());
            assertEquals("Wrong registration data entry status",
                            RegistrationDataEntryStatus.COMPLETE, loaded.getRegDataEntryStatus());
            assertEquals("Wrong epoch data entry status", ScheduledEpochDataEntryStatus.COMPLETE,
                            loaded.getScheduledEpoch().getScEpochDataEntryStatus());
            assertEquals("Wrong registration work flow status",
                            RegistrationWorkFlowStatus.UNREGISTERED, loaded.getRegWorkflowStatus());
            assertEquals("Wrong epoch work flow status", ScheduledEpochWorkFlowStatus.APPROVED,
                            loaded.getScheduledEpoch().getScEpochWorkflowStatus());
        }
        interruptSession();

    }

    /*
     * Test Cases for import registration Multi Site Trial Treatment Epoch Book Randomization Study
     * Site is Co Ordinating Center
     */
    public void testImportRegistrationCase8() throws Exception {
        StudySubject studySubject = persistedStudySubjectCreator.getMultiSiteRandomizedStudySubject(RandomizationType.BOOK, true);
        studySubject.setParticipant(persistedStudySubjectCreator.createNewParticipant());
        persistedStudySubjectCreator.addMRNIdentifierToSubject(studySubject.getParticipant(), studySubject.getStudySite().getHealthcareSite());
        persistedStudySubjectCreator.addScheduledEpochFromStudyEpochs(studySubject);
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
                            .getScheduledTreatmentEpochs().size());
            assertEquals("Wrong number of scheduled non treatment epochs", 0, loaded
                            .getScheduledNonTreatmentEpochs().size());
            assertEquals("getIfTreatmentScheduledEpoch return is inconsistent", true, loaded
                            .getIfTreatmentScheduledEpoch());
            ScheduledTreatmentEpoch scheduledTreatmentEpoch = (ScheduledTreatmentEpoch) loaded
                            .getScheduledEpoch();
            assertEquals("Wrong eligibility indicator", true, scheduledTreatmentEpoch
                            .getEligibilityIndicator().booleanValue());
            assertEquals("Wrong registration data entry status",
                            RegistrationDataEntryStatus.COMPLETE, loaded.getRegDataEntryStatus());
            assertEquals("Wrong epoch data entry status", ScheduledEpochDataEntryStatus.COMPLETE,
                            loaded.getScheduledEpoch().getScEpochDataEntryStatus());
            assertEquals("Wrong registration work flow status",
                            RegistrationWorkFlowStatus.REGISTERED, loaded.getRegWorkflowStatus());
            assertEquals("Wrong epoch work flow status", ScheduledEpochWorkFlowStatus.APPROVED,
                            loaded.getScheduledEpoch().getScEpochWorkflowStatus());
        }
        interruptSession();

    }

    private int getCode(String errortypeString) {
        return Integer.parseInt(this.c3prErrorMessages.getMessage(errortypeString, null, null));
    }
}
