package edu.duke.cabig.c3pr.service;

import edu.duke.cabig.c3pr.dao.HealthcareSiteDao;
import edu.duke.cabig.c3pr.dao.ParticipantDao;
import edu.duke.cabig.c3pr.dao.StudyDao;
import edu.duke.cabig.c3pr.dao.StudySubjectDao;
import edu.duke.cabig.c3pr.domain.*;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;
import edu.duke.cabig.c3pr.utils.DaoTestCase;
import edu.duke.cabig.c3pr.utils.StudyCreationHelper;
import edu.duke.cabig.c3pr.xml.XmlMarshaller;
import org.springframework.context.MessageSource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Jun 4, 2007
 * Time: 2:33:16 PM
 * To change this template use File | Settings | File Templates.
 */

public class StudySubjectXMLImporterServiceTestCase extends DaoTestCase {

    private StudySubjectService studySubjectService;
    private StudyCreationHelper studyCreationHelper = new StudyCreationHelper();
    private StudyDao dao = (StudyDao) getApplicationContext().getBean("studyDao");
    private HealthcareSiteDao healthcareSitedao = (HealthcareSiteDao) getApplicationContext().getBean("healthcareSiteDao");
    private ParticipantDao participantDao = (ParticipantDao) getApplicationContext().getBean("participantDao");
    private StudySubjectDao studySubjectDao = (StudySubjectDao) getApplicationContext().getBean("studySubjectDao");
    private StudySubjectXMLImporterService studySubjectXMLImporterService = (StudySubjectXMLImporterService) getApplicationContext().getBean("studySubjectXMLImporterService");
    private XmlMarshaller xmlUtility;
    private MessageSource c3prErrorMessages = (MessageSource) getApplicationContext().getBean("c3prErrorMessages");
    private final String identifierTypeValueStr = "Coordinating Center Identifier";

    protected void setUp() throws Exception {
        super.setUp();
        studySubjectService = (StudySubjectService) getApplicationContext().getBean("studySubjectService");
        xmlUtility = new XmlMarshaller((String) getApplicationContext().getBean("ccts-registration-castorMapping"));
    }

    /* Test Cases for import registration
      * Multi Site Trial
      * Treatment Epoch
      * Book Randomization
      */
    public void testImportRegistrationCase0() throws Exception {
        StudySubject studySubject = new StudySubject();
        Participant participant = addMRNIdentifier(addMRNIdentifier(participantDao.getById(1000)));
        Integer prtId = null;
        {
            participantDao.save(participant);
            prtId = participant.getId();
        }
        interruptSession();
        studySubject.setStudySite(getMultiSiteRandomizedStudy(RandomizationType.BOOK, false).getStudySites().get(0));
        participant = participantDao.getById(prtId);
        studySubject.setParticipant(participant);
        addScheduledEpoch(studySubject, true);
        buildCommandObject(studySubject);
        addEnrollmentDetails(studySubject);
        bindEligibility(studySubject);
        bindStratification(studySubject);
        assignArm(studySubject);
        String xml = xmlUtility.toXML(studySubject);
        Integer savedId;
        {
            StudySubject saved = null;
            try {
                saved = studySubjectXMLImporterService.importStudySubject(xml);
            } catch (Exception e) {
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
            assertEquals("Wrong number of scheduled treatment epochs", 1, loaded.getScheduledTreatmentEpochs().size());
            assertEquals("Wrong number of scheduled non treatment epochs", 0, loaded.getScheduledNonTreatmentEpochs().size());
            assertEquals("getIfTreatmentScheduledEpoch return is inconsistent", true, loaded.getIfTreatmentScheduledEpoch());
            ScheduledTreatmentEpoch scheduledTreatmentEpoch = (ScheduledTreatmentEpoch) loaded.getScheduledEpoch();
            assertEquals("Wrong eligibility indicator", true, scheduledTreatmentEpoch.getEligibilityIndicator().booleanValue());
            assertEquals("Wrong registration data entry status", RegistrationDataEntryStatus.COMPLETE, loaded.getRegDataEntryStatus());
            assertEquals("Wrong epoch data entry status", ScheduledEpochDataEntryStatus.COMPLETE, loaded.getScheduledEpoch().getScEpochDataEntryStatus());
            assertEquals("Wrong registration work flow status", RegistrationWorkFlowStatus.REGISTERED, loaded.getRegWorkflowStatus());
            assertEquals("Wrong epoch work flow status", ScheduledEpochWorkFlowStatus.APPROVED, loaded.getScheduledEpoch().getScEpochWorkflowStatus());
        }
        interruptSession();

    }

    /* Test Cases for import registration
      * Multi Site Trial
      * Treatment Epoch
      * Phone call Randomization
      * No Arm assigned
      */
    public void testImportRegistrationCase1() throws Exception {
        StudySubject studySubject = new StudySubject();
        Participant participant = addMRNIdentifier(addMRNIdentifier(participantDao.getById(1000)));
        Integer prtId = null;
        {
            participantDao.save(participant);
            prtId = participant.getId();
        }
        interruptSession();
        studySubject.setStudySite(getMultiSiteRandomizedStudy(RandomizationType.PHONE_CALL, false).getStudySites().get(0));
        addScheduledEpoch(studySubject, true);
        buildCommandObject(studySubject);
        addEnrollmentDetails(studySubject);
        bindEligibility(studySubject);
        bindStratification(studySubject);
        participant = participantDao.getById(prtId);
        studySubject.setParticipant(participant);
        String xml = xmlUtility.toXML(studySubject);
        try {
            studySubjectXMLImporterService.importStudySubject(xml);
        } catch (C3PRCodedException e) {
            e.printStackTrace();
            interruptSession();
            assertEquals("Exception Code unmatched", getCode("C3PR.EXCEPTION.REGISTRATION.IMPORT.REQUIRED.ARM.NOTFOUND.CODE"), e.getExceptionCode());
            return;
        } catch (Exception e) {
            e.printStackTrace();
            interruptSession();
            fail("Wrong Exception Type.");
            return;
        }
        fail("Should have thrown C3PR Coded Exception.");
        interruptSession();

    }

    /* Test Cases for import registration
      * Multi Site Trial
      * Non Treatment Epoch, Non Reserving, Non Registering
      * Non Randomized
      */
    public void testImportRegistrationCase2() throws Exception {
        interruptSession();
        StudySubject studySubject = new StudySubject();
        Participant participant = addMRNIdentifier(addMRNIdentifier(participantDao.getById(1000)));
        Integer prtId = null;
        {
            participantDao.save(participant);
            prtId = participant.getId();
        }
        interruptSession();
        studySubject.setStudySite(getMultiSiteNonRandomizedStudy(false, false, false).getStudySites().get(0));
        participant = participantDao.getById(prtId);
        studySubject.setParticipant(participant);
        addEnrollmentDetails(studySubject);
        addScheduledEpoch(studySubject, false);
        String xml = xmlUtility.toXML(studySubject);
        Integer savedId;
        {
            StudySubject saved = null;
            try {
                saved = studySubjectXMLImporterService.importStudySubject(xml);
            } catch (Exception e) {
                e.printStackTrace();
                interruptSession();
                fail("shouldnt have thrown exception.");
            } finally {
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
            assertEquals("Wrong number of scheduled treatment epochs", 0, loaded.getScheduledTreatmentEpochs().size());
            assertEquals("Wrong number of scheduled non treatment epochs", 1, loaded.getScheduledNonTreatmentEpochs().size());
            assertEquals("getIfTreatmentScheduledEpoch return is inconsistent", false, loaded.getIfTreatmentScheduledEpoch());
            assertEquals("Wrong registration data entry status", RegistrationDataEntryStatus.COMPLETE, loaded.getRegDataEntryStatus());
            assertEquals("Wrong epoch data entry status", ScheduledEpochDataEntryStatus.COMPLETE, loaded.getScheduledEpoch().getScEpochDataEntryStatus());
            assertEquals("Wrong registration work flow status", RegistrationWorkFlowStatus.UNREGISTERED, loaded.getRegWorkflowStatus());
            assertEquals("Wrong epoch work flow status", ScheduledEpochWorkFlowStatus.APPROVED, loaded.getScheduledEpoch().getScEpochWorkflowStatus());
        }
        interruptSession();

    }

    /* Test Cases for import registration
      * Local Trial
      * Treatment Epoch
      * Book Randomization
      */
    public void testImportRegistrationCase3() throws Exception {
        StudySubject studySubject = new StudySubject();
        Participant participant = addMRNIdentifier(addMRNIdentifier(participantDao.getById(1000)));
        Integer prtId = null;
        {
            participantDao.save(participant);
            prtId = participant.getId();
        }
        interruptSession();
        studySubject.setStudySite(getLocalRandomizedStudy(RandomizationType.BOOK, false).getStudySites().get(0));
        addScheduledEpoch(studySubject, true);
        buildCommandObject(studySubject);
        addEnrollmentDetails(studySubject);
        bindEligibility(studySubject);
        bindStratification(studySubject);
        assignArm(studySubject);
        participant = participantDao.getById(prtId);
        studySubject.setParticipant(participant);
        String xml = xmlUtility.toXML(studySubject);
        Integer savedId;
        {
            StudySubject saved = null;
            try {
                saved = studySubjectXMLImporterService.importStudySubject(xml);
            } catch (Exception e) {
                e.printStackTrace();
                fail("shouldnt have thrown exception.");
            } finally {
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
            assertEquals("Wrong number of scheduled treatment epochs", 1, loaded.getScheduledTreatmentEpochs().size());
            assertEquals("Wrong number of scheduled non treatment epochs", 0, loaded.getScheduledNonTreatmentEpochs().size());
            assertEquals("getIfTreatmentScheduledEpoch return is inconsistent", true, loaded.getIfTreatmentScheduledEpoch());
            ScheduledTreatmentEpoch scheduledTreatmentEpoch = (ScheduledTreatmentEpoch) loaded.getScheduledEpoch();
            assertEquals("Wrong eligibility indicator", true, scheduledTreatmentEpoch.getEligibilityIndicator().booleanValue());
            assertEquals("Wrong registration data entry status", RegistrationDataEntryStatus.COMPLETE, loaded.getRegDataEntryStatus());
            assertEquals("Wrong epoch data entry status", ScheduledEpochDataEntryStatus.COMPLETE, loaded.getScheduledEpoch().getScEpochDataEntryStatus());
            assertEquals("Wrong registration work flow status", RegistrationWorkFlowStatus.REGISTERED, loaded.getRegWorkflowStatus());
            assertEquals("Wrong epoch work flow status", ScheduledEpochWorkFlowStatus.APPROVED, loaded.getScheduledEpoch().getScEpochWorkflowStatus());
        }
        interruptSession();
    }

    /* Test Cases for import registration
	 * Local Trial
	 * Non Randomized Treatment Epoch
	 * Wrong Arm Name
	 */
    public void testImportRegistrationCase4() throws Exception {
        StudySubject studySubject = new StudySubject();
        Participant participant = addMRNIdentifier(addMRNIdentifier(participantDao.getById(1000)));
        Integer prtId = null;
        {
            participantDao.save(participant);
            prtId = participant.getId();
        }
        interruptSession();
        studySubject.setStudySite(getLocalNonRandomizedWithArmStudy(false).getStudySites().get(0));
        addScheduledEpoch(studySubject, true);
        buildCommandObject(studySubject);
        addEnrollmentDetails(studySubject);
        assignInvalidArm(studySubject);
        bindEligibility(studySubject);
        bindStratification(studySubject);
        participant = participantDao.getById(prtId);
        studySubject.setParticipant(participant);
        String xml = xmlUtility.toXML(studySubject);
        try {
            studySubjectXMLImporterService.importStudySubject(xml);
        } catch (C3PRCodedException e) {
            e.printStackTrace();
            assertEquals("Exception Code unmatched", getCode("C3PR.EXCEPTION.REGISTRATION.NOTFOUND.ARM_NAME.CODE"), e.getExceptionCode());
            return;
        } catch (Exception e) {
            e.printStackTrace();
            fail("Wrong Exception Type.");
        } finally {
            interruptSession();
        }
        fail("Should have thrown C3PR Coded Exception.");
        interruptSession();
    }

    /* Test Cases for import registration
      * Local Trial
      * NonTreatment Epoch Reserving
      */
    public void testImportRegistrationCase5() throws Exception {
        StudySubject studySubject = new StudySubject();
        Participant participant = addMRNIdentifier(addMRNIdentifier(participantDao.getById(1000)));
        Integer prtId = null;
        {
            participantDao.save(participant);
            prtId = participant.getId();
        }
        interruptSession();
        studySubject.setStudySite(getLocalNonRandomizedStudy(true, false, false).getStudySites().get(0));
        addScheduledEpoch(studySubject, false);
        addEnrollmentDetails(studySubject);
        buildCommandObject(studySubject);
        participant = participantDao.getById(prtId);
        studySubject.setParticipant(participant);
        String xml = xmlUtility.toXML(studySubject);
        Integer savedId;
        {
            StudySubject saved = null;
            try {
                saved = studySubjectXMLImporterService.importStudySubject(xml);
            } catch (Exception e) {
                e.printStackTrace();
                fail("shouldnt have thrown exception.");
            } finally {
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
            assertEquals("Wrong number of scheduled treatment epochs", 0, loaded.getScheduledTreatmentEpochs().size());
            assertEquals("Wrong number of scheduled non treatment epochs", 1, loaded.getScheduledNonTreatmentEpochs().size());
            assertEquals("getIfTreatmentScheduledEpoch return is inconsistent", false, loaded.getIfTreatmentScheduledEpoch());
            assertEquals("Wrong registration data entry status", RegistrationDataEntryStatus.COMPLETE, loaded.getRegDataEntryStatus());
            assertEquals("Wrong epoch data entry status", ScheduledEpochDataEntryStatus.COMPLETE, loaded.getScheduledEpoch().getScEpochDataEntryStatus());
            assertEquals("Wrong registration work flow status", RegistrationWorkFlowStatus.RESERVED, loaded.getRegWorkflowStatus());
            assertEquals("Wrong epoch work flow status", ScheduledEpochWorkFlowStatus.APPROVED, loaded.getScheduledEpoch().getScEpochWorkflowStatus());
        }
        interruptSession();

    }

    /* Test Cases for import registration
      * Local Trial
      * NonTreatment Epoch Enrolling
      * Non Randomized
      */
    public void testImportRegistrationCase6() throws Exception {
        StudySubject studySubject = new StudySubject();
        Participant participant = addMRNIdentifier(addMRNIdentifier(participantDao.getById(1000)));
        Integer prtId = null;
        {
            participantDao.save(participant);
            prtId = participant.getId();
        }
        interruptSession();
        studySubject.setStudySite(getLocalNonRandomizedStudy(false, true, false).getStudySites().get(0));
        Integer savedId;
        addScheduledEpoch(studySubject, false);
        addEnrollmentDetails(studySubject);
        buildCommandObject(studySubject);
        participant = participantDao.getById(prtId);
        studySubject.setParticipant(participant);
        String xml = xmlUtility.toXML(studySubject);
        {
            StudySubject saved = null;
            try {
                saved = studySubjectXMLImporterService.importStudySubject(xml);
            } catch (Exception e) {
                e.printStackTrace();
                fail("shouldnt have thrown exception.");
            } finally {
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
            assertEquals("Wrong number of scheduled treatment epochs", 0, loaded.getScheduledTreatmentEpochs().size());
            assertEquals("Wrong number of scheduled non treatment epochs", 1, loaded.getScheduledNonTreatmentEpochs().size());
            assertEquals("getIfTreatmentScheduledEpoch return is inconsistent", false, loaded.getIfTreatmentScheduledEpoch());
            assertEquals("Wrong registration data entry status", RegistrationDataEntryStatus.COMPLETE, loaded.getRegDataEntryStatus());
            assertEquals("Wrong epoch data entry status", ScheduledEpochDataEntryStatus.COMPLETE, loaded.getScheduledEpoch().getScEpochDataEntryStatus());
            assertEquals("Wrong registration work flow status", RegistrationWorkFlowStatus.REGISTERED, loaded.getRegWorkflowStatus());
            assertEquals("Wrong epoch work flow status", ScheduledEpochWorkFlowStatus.APPROVED, loaded.getScheduledEpoch().getScEpochWorkflowStatus());
        }
        interruptSession();

    }

    /* Test Cases for import registration
      * Local Trial
      * NonTreatment Epoch Non Enrolling, Non Reserving
      */
    public void testImportRegistrationCase7() throws Exception {
        StudySubject studySubject = new StudySubject();
        Participant participant = addMRNIdentifier(addMRNIdentifier(participantDao.getById(1000)));
        Integer prtId = null;
        {
            participantDao.save(participant);
            prtId = participant.getId();
        }
        interruptSession();
        studySubject.setStudySite(getLocalNonRandomizedStudy(false, false, false).getStudySites().get(0));
        Integer savedId;
        addScheduledEpoch(studySubject, false);
        addEnrollmentDetails(studySubject);
        buildCommandObject(studySubject);
        participant = participantDao.getById(prtId);
        studySubject.setParticipant(participant);
        String xml = xmlUtility.toXML(studySubject);
        {
            StudySubject saved = null;
            try {
                saved = studySubjectXMLImporterService.importStudySubject(xml);
            } catch (Exception e) {
                e.printStackTrace();
                fail("shouldnt have thrown exception.");
            } finally {
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
            assertEquals("Wrong number of scheduled treatment epochs", 0, loaded.getScheduledTreatmentEpochs().size());
            assertEquals("Wrong number of scheduled non treatment epochs", 1, loaded.getScheduledNonTreatmentEpochs().size());
            assertEquals("getIfTreatmentScheduledEpoch return is inconsistent", false, loaded.getIfTreatmentScheduledEpoch());
            assertEquals("Wrong registration data entry status", RegistrationDataEntryStatus.COMPLETE, loaded.getRegDataEntryStatus());
            assertEquals("Wrong epoch data entry status", ScheduledEpochDataEntryStatus.COMPLETE, loaded.getScheduledEpoch().getScEpochDataEntryStatus());
            assertEquals("Wrong registration work flow status", RegistrationWorkFlowStatus.UNREGISTERED, loaded.getRegWorkflowStatus());
            assertEquals("Wrong epoch work flow status", ScheduledEpochWorkFlowStatus.APPROVED, loaded.getScheduledEpoch().getScEpochWorkflowStatus());
        }
        interruptSession();

    }

    /* Test Cases for import registration
      * Multi Site Trial
      * Treatment Epoch
      * Book Randomization
      * Study Site is Co Ordinating Center
      */
    public void testImportRegistrationCase8() throws Exception {
        StudySubject studySubject = new StudySubject();
        Participant participant = addMRNIdentifier(addMRNIdentifier(participantDao.getById(1000)));
        Integer prtId = null;
        {
            participantDao.save(participant);
            prtId = participant.getId();
        }
        interruptSession();
        studySubject.setStudySite(getMultiSiteRandomizedStudy(RandomizationType.BOOK, true).getStudySites().get(0));
        addScheduledEpoch(studySubject, true);
        buildCommandObject(studySubject);
        addEnrollmentDetails(studySubject);
        bindEligibility(studySubject);
        bindStratification(studySubject);
        assignArm(studySubject);
        participant = participantDao.getById(prtId);
        studySubject.setParticipant(participant);
        String xml = xmlUtility.toXML(studySubject);
        Integer savedId;
        {
            StudySubject saved = null;
            try {
                saved = studySubjectXMLImporterService.importStudySubject(xml);
            } catch (Exception e) {
                e.printStackTrace();
                fail("shouldnt have thrown exception.");
            } finally {
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
            assertEquals("Wrong number of scheduled treatment epochs", 1, loaded.getScheduledTreatmentEpochs().size());
            assertEquals("Wrong number of scheduled non treatment epochs", 0, loaded.getScheduledNonTreatmentEpochs().size());
            assertEquals("getIfTreatmentScheduledEpoch return is inconsistent", true, loaded.getIfTreatmentScheduledEpoch());
            ScheduledTreatmentEpoch scheduledTreatmentEpoch = (ScheduledTreatmentEpoch) loaded.getScheduledEpoch();
            assertEquals("Wrong eligibility indicator", true, scheduledTreatmentEpoch.getEligibilityIndicator().booleanValue());
            assertEquals("Wrong registration data entry status", RegistrationDataEntryStatus.COMPLETE, loaded.getRegDataEntryStatus());
            assertEquals("Wrong epoch data entry status", ScheduledEpochDataEntryStatus.COMPLETE, loaded.getScheduledEpoch().getScEpochDataEntryStatus());
            assertEquals("Wrong registration work flow status", RegistrationWorkFlowStatus.REGISTERED, loaded.getRegWorkflowStatus());
            assertEquals("Wrong epoch work flow status", ScheduledEpochWorkFlowStatus.APPROVED, loaded.getScheduledEpoch().getScEpochWorkflowStatus());
        }
        interruptSession();

    }

    /* Test Cases for import registration
      * Multi Site Trial
      * New Subject
      * Treatment Epoch
      * Book Randomization
      */
    public void testImportRegistrationCase9() throws Exception {
        StudySubject studySubject = new StudySubject();
        Participant participant = createNewParticipant();
        participant = addMRNIdentifier(addMRNIdentifier(participant));
        studySubject.setStudySite(getMultiSiteRandomizedStudy(RandomizationType.BOOK, false).getStudySites().get(0));

        studySubject.setParticipant(participant);
        addScheduledEpoch(studySubject, true);
        buildCommandObject(studySubject);
        addEnrollmentDetails(studySubject);
        bindEligibility(studySubject);
        bindStratification(studySubject);
        assignArm(studySubject);
        String xml = xmlUtility.toXML(studySubject);
        Integer savedId;
        {
            StudySubject saved = null;
            try {
                saved = studySubjectXMLImporterService.importStudySubject(xml);
            } catch (Exception e) {
                e.printStackTrace();
                fail("shouldnt have thrown exception.");
            } finally {
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
            assertEquals("Wrong number of scheduled treatment epochs", 1, loaded.getScheduledTreatmentEpochs().size());
            assertEquals("Wrong number of scheduled non treatment epochs", 0, loaded.getScheduledNonTreatmentEpochs().size());
            assertEquals("getIfTreatmentScheduledEpoch return is inconsistent", true, loaded.getIfTreatmentScheduledEpoch());
            ScheduledTreatmentEpoch scheduledTreatmentEpoch = (ScheduledTreatmentEpoch) loaded.getScheduledEpoch();
            assertEquals("Wrong eligibility indicator", true, scheduledTreatmentEpoch.getEligibilityIndicator().booleanValue());
            assertEquals("Wrong registration data entry status", RegistrationDataEntryStatus.COMPLETE, loaded.getRegDataEntryStatus());
            assertEquals("Wrong epoch data entry status", ScheduledEpochDataEntryStatus.COMPLETE, loaded.getScheduledEpoch().getScEpochDataEntryStatus());
            assertEquals("Wrong registration work flow status", RegistrationWorkFlowStatus.REGISTERED, loaded.getRegWorkflowStatus());
            assertEquals("Wrong epoch work flow status", ScheduledEpochWorkFlowStatus.APPROVED, loaded.getScheduledEpoch().getScEpochWorkflowStatus());
        }
        interruptSession();

    }

    private Study getMultiSiteRandomizedStudy(RandomizationType randomizationType, boolean makeStudysiteCoCenter) throws Exception {
        Study study = studyCreationHelper.getMultiSiteRandomizedStudy(randomizationType);
        return addStudySiteCoCenterAndSave(study, makeStudysiteCoCenter);
    }

    private Study getMultiSiteNonRandomizedStudy(Boolean reserving, Boolean enrolling, boolean makeStudysiteCoCenter) {
        Study study = studyCreationHelper.getMultiSiteNonRandomizedStudy(reserving, enrolling);
        return addStudySiteCoCenterAndSave(study, makeStudysiteCoCenter);
    }

    private Study getMultiSiteNonRandomizedWithArmStudy(boolean makeStudysiteCoCenter) {
        Study study = studyCreationHelper.getMultiSiteNonRandomizedWithArmStudy();
        return addStudySiteCoCenterAndSave(study, makeStudysiteCoCenter);
    }

    private Study getLocalRandomizedStudy(RandomizationType randomizationType, boolean makeStudysiteCoCenter) throws Exception {
        Study study = studyCreationHelper.getLocalRandomizedStudy(randomizationType);
        return addStudySiteCoCenterAndSave(study, makeStudysiteCoCenter);
    }

    private Study getLocalNonRandomizedStudy(Boolean reserving, Boolean enrolling, boolean makeStudysiteCoCenter) {
        Study study = studyCreationHelper.getLocalNonRandomizedStudy(reserving, enrolling);
        return addStudySiteCoCenterAndSave(study, makeStudysiteCoCenter);
    }

    private Study getLocalNonRandomizedWithArmStudy(boolean makeStudysiteCoCenter) {
        Study study = studyCreationHelper.getLocalNonRandomizedWithArmStudy();
        return addStudySiteCoCenterAndSave(study, makeStudysiteCoCenter);
    }

    private void buildCommandObject(StudySubject studySubject) {
        if (studySubject.getIfTreatmentScheduledEpoch()) {
            ScheduledTreatmentEpoch scheduledTreatmentEpoch = (ScheduledTreatmentEpoch) studySubject.getScheduledEpoch();
            List criterias = scheduledTreatmentEpoch.getTreatmentEpoch().getInclusionEligibilityCriteria();
            for (int i = 0; i < criterias.size(); i++) {
                SubjectEligibilityAnswer subjectEligibilityAnswer = new SubjectEligibilityAnswer();
                subjectEligibilityAnswer.setEligibilityCriteria((EligibilityCriteria) criterias.get(i));
                scheduledTreatmentEpoch.addSubjectEligibilityAnswers(subjectEligibilityAnswer);
            }
            criterias = scheduledTreatmentEpoch.getTreatmentEpoch().getExclusionEligibilityCriteria();
            for (int i = 0; i < criterias.size(); i++) {
                SubjectEligibilityAnswer subjectEligibilityAnswer = new SubjectEligibilityAnswer();
                subjectEligibilityAnswer.setEligibilityCriteria((EligibilityCriteria) criterias.get(i));
                scheduledTreatmentEpoch.addSubjectEligibilityAnswers(subjectEligibilityAnswer);
            }
            List<StratificationCriterion> stratifications = scheduledTreatmentEpoch.getTreatmentEpoch().getStratificationCriteria();
            for (StratificationCriterion stratificationCriterion : stratifications) {
                stratificationCriterion.getPermissibleAnswers().size();
                SubjectStratificationAnswer subjectStratificationAnswer = new SubjectStratificationAnswer();
                subjectStratificationAnswer.setStratificationCriterion(stratificationCriterion);
                scheduledTreatmentEpoch.addSubjectStratificationAnswers(subjectStratificationAnswer);
            }
        }
    }

    private void bindEligibility(Object command) {
        StudySubject studySubject = (StudySubject) command;
        List<SubjectEligibilityAnswer> subList = ((ScheduledTreatmentEpoch) studySubject.getScheduledEpoch()).getSubjectEligibilityAnswers();
        for (SubjectEligibilityAnswer subjectEligibilityAnswer : subList) {
            if (subjectEligibilityAnswer.getEligibilityCriteria() instanceof InclusionEligibilityCriteria) {
                subjectEligibilityAnswer.setAnswerText("yes");
            } else {
                subjectEligibilityAnswer.setAnswerText("no");
            }
        }
        ((ScheduledTreatmentEpoch) studySubject.getScheduledEpoch()).setEligibilityIndicator(evaluateEligibilityIndicator(studySubject));
    }

    private void bindStratification(Object command) {
        StudySubject studySubject = (StudySubject) command;
        List<SubjectStratificationAnswer> subList1 = ((ScheduledTreatmentEpoch) studySubject.getScheduledEpoch()).getSubjectStratificationAnswers();
        for (SubjectStratificationAnswer subjectStratificationAnswer : subList1) {
            subjectStratificationAnswer.setStratificationCriterionAnswer(subjectStratificationAnswer.getStratificationCriterion().getPermissibleAnswers().get(0));
        }
    }

    private void bindStratificationInvalid(Object command) {
        StudySubject studySubject = (StudySubject) command;
        List<SubjectStratificationAnswer> subList1 = ((ScheduledTreatmentEpoch) studySubject.getScheduledEpoch()).getSubjectStratificationAnswers();
        for (SubjectStratificationAnswer subjectStratificationAnswer : subList1) {
            subjectStratificationAnswer.setStratificationCriterionAnswer(subjectStratificationAnswer.getStratificationCriterion().getPermissibleAnswers().get(subjectStratificationAnswer.getStratificationCriterion().getPermissibleAnswers().size() - 1));
        }
    }

    private void assignArm(StudySubject studySubject) {
        ScheduledTreatmentEpoch scheduledEpoch = ((ScheduledTreatmentEpoch) studySubject.getScheduledEpoch());
        scheduledEpoch.addScheduledArm(new ScheduledArm());
        ScheduledArm scheduledArm = scheduledEpoch.getScheduledArm();
        scheduledArm.setArm(((TreatmentEpoch) scheduledEpoch.getTreatmentEpoch()).getArms().get(0));
    }

    private void assignInvalidArm(StudySubject studySubject) {
        ScheduledTreatmentEpoch scheduledEpoch = ((ScheduledTreatmentEpoch) studySubject.getScheduledEpoch());
        scheduledEpoch.addScheduledArm(new ScheduledArm());
        ScheduledArm scheduledArm = scheduledEpoch.getScheduledArm();
        scheduledArm.setArm(new Arm());
        scheduledArm.getArm().setName("Invalid Name");
    }

    private boolean evaluateEligibilityIndicator(StudySubject studySubject) {
        boolean flag = true;
        List<SubjectEligibilityAnswer> answers = ((ScheduledTreatmentEpoch) studySubject.getScheduledEpoch()).getInclusionEligibilityAnswers();
        for (SubjectEligibilityAnswer subjectEligibilityAnswer : answers) {
            String answerText = subjectEligibilityAnswer.getAnswerText();
            if (answerText == null || answerText.equalsIgnoreCase("") || (!answerText.equalsIgnoreCase("Yes") && !answerText.equalsIgnoreCase("NA"))) {
                flag = false;
                break;
            }
        }
        if (flag) {
            answers = ((ScheduledTreatmentEpoch) studySubject.getScheduledEpoch()).getExclusionEligibilityAnswers();
            for (SubjectEligibilityAnswer subjectEligibilityAnswer : answers) {
                String answerText = subjectEligibilityAnswer.getAnswerText();
                if (answerText == null || answerText.equalsIgnoreCase("") || (!answerText.equalsIgnoreCase("No") && !answerText.equalsIgnoreCase("NA"))) {
                    flag = false;
                    break;
                }
            }
        }
        return flag;
    }

    private Epoch createTestTreatmentEpoch(boolean randomized) {
        TreatmentEpoch epoch = new TreatmentEpoch();
        epoch.addEligibilityCriterion(new InclusionEligibilityCriteria());
        epoch.addEligibilityCriterion(new ExclusionEligibilityCriteria());
        StratificationCriterion stratificationCriterion = new StratificationCriterion();
        stratificationCriterion.addPermissibleAnswer(new StratificationCriterionPermissibleAnswer());
        epoch.addStratificationCriterion(stratificationCriterion);
        epoch.addArm(new Arm());
        epoch.setRandomizedIndicator(randomized);
        if (randomized) {
            epoch.setRandomization(new PhoneCallRandomization());
        }
        return epoch;
    }

    protected void addScheduledEpoch(StudySubject studySubject, boolean isTreatment) {
        ScheduledEpoch scheduledEpoch = isTreatment ? new ScheduledTreatmentEpoch() : new ScheduledNonTreatmentEpoch();
        scheduledEpoch.setEpoch(studySubject.getStudySite().getStudy().getEpochs().get(0));
        studySubject.addScheduledEpoch(scheduledEpoch);
    }

    private void addEnrollmentDetails(StudySubject studySubject) {
        studySubject.setInformedConsentSignedDate(new Date());
        studySubject.setInformedConsentVersion("1.0");
        studySubject.setOtherTreatingPhysician("Other T P");
    }

    private Study addStudySiteCoCenterAndSave(Study study, boolean makeStudysiteCoCenter) {
        StudySite studySite = new StudySite();
        studySite.setHealthcareSite(healthcareSitedao.getById(1000));
        studySite.setStudy(study);
        studySite.setSiteStudyStatus(SiteStudyStatus.ACTIVE);
        study.getStudySites().add(studySite);
        Integer id;
        {
            HealthcareSite healthcaresite = new HealthcareSite();
            Address address = new Address();
            address.setCity("Chicago");
            address.setCountryCode("USA");
            address.setPostalCode("83929");
            address.setStateCode("IL");
            address.setStreetAddress("123 Lake Shore Dr");

            healthcaresite.setAddress(address);
            healthcaresite.setName("Northwestern Memorial Hospital");
            healthcaresite.setDescriptionText("NU healthcare");
            if (makeStudysiteCoCenter)
                healthcaresite.setNciInstituteCode(studySite.getHealthcareSite().getNciInstituteCode());
            else
                healthcaresite.setNciInstituteCode("NCI northwestern");
            healthcareSitedao.save(healthcaresite);
            id = healthcaresite.getId();
        }
        interruptSession();
        assertNotNull("The saved organization didn't get an id", id);
        HealthcareSite healthcareSite = healthcareSitedao.getById(id);
        StudyCoordinatingCenter stC = study.getStudyCoordinatingCenters().get(0);
        stC.setStudy(study);
        stC.setHealthcareSite(healthcareSite);
        OrganizationAssignedIdentifier identifier = study.getOrganizationAssignedIdentifiers().get(0);
        identifier.setHealthcareSite(makeStudysiteCoCenter ? studySite.getHealthcareSite() : healthcareSite);
        identifier.setType(this.identifierTypeValueStr);
        identifier.setValue("Some Test Value");
        identifier.setPrimaryIndicator(true);
        Integer savedId;
        {
            study = dao.merge(study);

            savedId = study.getId();
        }
        interruptSession();
        {
            Study loaded = dao.getById(savedId);
            return loaded;
        }
    }

    /**
     * @param filePath the name of the file to open. Not sure if it can accept URLs or just filenames. Path handling could be better, and buffer sizes are hardcoded
     */
    private String readFileAsString(String filePath) throws java.io.IOException {
        String fileData = "";
        File f = new File(filePath);
        System.out.println(f.getAbsolutePath());
        BufferedReader reader = new BufferedReader(
                new FileReader(filePath));
        String line;
        while ((line = reader.readLine()) != null) {
            fileData += line;
        }
        reader.close();
        return fileData;
    }

    private int getCode(String errortypeString) {
        return Integer.parseInt(this.c3prErrorMessages.getMessage(errortypeString, null, null));
    }

    private Participant addMRNIdentifier(Participant participant) {
        OrganizationAssignedIdentifier organizationAssignedIdentifier = participant.getOrganizationAssignedIdentifiers().get(0);
        organizationAssignedIdentifier.setType("MRN");
        organizationAssignedIdentifier.setHealthcareSite(healthcareSitedao.getById(1100));
        organizationAssignedIdentifier.setValue("MRN-temp");
        return participant;
    }

    private Participant createNewParticipant() {
        Participant participant = new Participant();
        participant.setFirstName("test first name");
        participant.setLastName("test last name");
        participant.setBirthDate(new Date());
        participant.setAdministrativeGenderCode("M");
        return participant;
    }
}
