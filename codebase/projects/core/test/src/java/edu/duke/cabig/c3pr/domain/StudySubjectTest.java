package edu.duke.cabig.c3pr.domain;

import java.util.Date;

import edu.duke.cabig.c3pr.AbstractTestCase;
import edu.duke.cabig.c3pr.utils.StudySubjectCreatorHelper;

public class StudySubjectTest extends AbstractTestCase {
    
    private StudySubjectCreatorHelper studySubjectCreatorHelper=new StudySubjectCreatorHelper();
    /**
     * Registration Data Entry Status test blank Study Subject
     */
    public void testEvaluateRegistrationDataEntryStatusInComplete() throws Exception {
        StudySubject studySubject = new StudySubject();
        assertEquals("Wrong Registration Data Entry Status",
                        RegistrationDataEntryStatus.INCOMPLETE, studySubject
                                        .evaluateRegistrationDataEntryStatus());
    }

    /**
     * Registration Data Entry Status test InformedConsent Date Filled InformedConsent Version
     * Filled
     */
    public void testEvaluateRegistrationDataEntryStatusComplete() {
        StudySubject studySubject = new StudySubject();
        studySubject.setInformedConsentSignedDate(new Date());
        studySubject.setInformedConsentVersion("1.0");
        assertEquals("Wrong Registration Data Entry Status", RegistrationDataEntryStatus.COMPLETE,
                        studySubject.evaluateRegistrationDataEntryStatus());
    }

    /**
     * Epoch Data Entry Status test Randomized Treatment Epoch Blank Scheduled Epoch
     */
    public void testEvaluateEpochDataEntryStatusInComplete1() throws Exception {
        StudySubject studySubject = new StudySubject();
        ScheduledEpoch scheduledEpochFirst = new ScheduledTreatmentEpoch();
        scheduledEpochFirst.setEpoch(studySubjectCreatorHelper.createTestTreatmentEpoch(true));
        studySubject.addScheduledEpoch(scheduledEpochFirst);
        studySubjectCreatorHelper.buildCommandObject(studySubject);
        assertEquals("Wrong Epoch Data Entry Status", ScheduledEpochDataEntryStatus.INCOMPLETE,
                        studySubject.evaluateScheduledEpochDataEntryStatus());
    }

    /**
     * Epoch Data Entry Status test Randomized Treatment Epoch Eligibility Done
     */
    public void testEvaluateEpochDataEntryStatusInComplete2() throws Exception {
        StudySubject studySubject = new StudySubject();
        ScheduledEpoch scheduledEpochFirst = new ScheduledTreatmentEpoch();
        scheduledEpochFirst.setEpoch(studySubjectCreatorHelper.createTestTreatmentEpoch(true));
        ((TreatmentEpoch) scheduledEpochFirst.getEpoch()).getArms().size();
        studySubject.addScheduledEpoch(scheduledEpochFirst);
        studySubjectCreatorHelper.buildCommandObject(studySubject);
        studySubjectCreatorHelper.bindEligibility(studySubject);
        assertEquals("Wrong Epoch Data Entry Status", ScheduledEpochDataEntryStatus.INCOMPLETE,
                        studySubject.evaluateScheduledEpochDataEntryStatus());
    }

    /**
     * Epoch Data Entry Status test Non Randomized Treatment Epoch with Arms Eligibility Done
     * Stratification Done Arm not assigned
     */
    public void testEvaluateEpochDataEntryStatusInComplete3() throws Exception {
        StudySubject studySubject = new StudySubject();
        ScheduledEpoch scheduledEpochFirst = new ScheduledTreatmentEpoch();
        scheduledEpochFirst.setEpoch(studySubjectCreatorHelper.createTestTreatmentEpoch(false));
        studySubject.addScheduledEpoch(scheduledEpochFirst);
        studySubjectCreatorHelper.buildCommandObject(studySubject);
        studySubjectCreatorHelper.bindEligibility(studySubject);
        studySubjectCreatorHelper.bindStratification(studySubject);
        assertEquals("Wrong Epoch Data Entry Status", ScheduledEpochDataEntryStatus.INCOMPLETE,
                        studySubject.evaluateScheduledEpochDataEntryStatus());
    }

    /**
     * Epoch Data Entry Status test Randomized Treatment Epoch Eligibility Done Stratification Done
     */
    public void testEvaluateEpochDataEntryStatusComplete1() throws Exception {
        StudySubject studySubject = new StudySubject();
        ScheduledEpoch scheduledEpochFirst = new ScheduledTreatmentEpoch();
        scheduledEpochFirst.setEpoch(studySubjectCreatorHelper.createTestTreatmentEpoch(true));
        studySubject.addScheduledEpoch(scheduledEpochFirst);
        studySubjectCreatorHelper.buildCommandObject(studySubject);
        studySubjectCreatorHelper.bindEligibility(studySubject);
        studySubjectCreatorHelper.bindStratification(studySubject);
        assertEquals("Wrong Epoch Data Entry Status", ScheduledEpochDataEntryStatus.COMPLETE,
                        studySubject.evaluateScheduledEpochDataEntryStatus());
    }

    /**
     * Epoch Data Entry Status test Non Randomized Treatment Epoch Eligibility Done Stratification
     * Done Assigned Arm
     */
    public void testEvaluateEpochDataEntryStatusComplete2() throws Exception {
        StudySubject studySubject = new StudySubject();
        ScheduledEpoch scheduledEpochFirst = new ScheduledTreatmentEpoch();
        ScheduledArm scheduledArm = new ScheduledArm();
        scheduledArm.setArm(new Arm());
        ((ScheduledTreatmentEpoch) scheduledEpochFirst).addScheduledArm(scheduledArm);
        scheduledEpochFirst.setEpoch(studySubjectCreatorHelper.createTestTreatmentEpoch(false));
        studySubject.addScheduledEpoch(scheduledEpochFirst);
        studySubjectCreatorHelper.buildCommandObject(studySubject);
        studySubjectCreatorHelper.bindEligibility(studySubject);
        studySubjectCreatorHelper.bindStratification(studySubject);
        assertEquals("Wrong Epoch Data Entry Status", ScheduledEpochDataEntryStatus.COMPLETE,
                        studySubject.evaluateScheduledEpochDataEntryStatus());
    }

    /**
     * Epoch Data Entry Status test Non Treatment Epoch
     */
    public void testEvaluateEpochDataEntryStatusComplete3() throws Exception {
        StudySubject studySubject = new StudySubject();
        studySubject.addScheduledEpoch(new ScheduledNonTreatmentEpoch());
        studySubjectCreatorHelper.buildCommandObject(studySubject);
        assertEquals("Wrong Epoch Data Entry Status", ScheduledEpochDataEntryStatus.COMPLETE,
                        studySubject.evaluateScheduledEpochDataEntryStatus());
    }
    
    public void testIsRegisterableTrue(){
        StudySubject studySubject = new StudySubject();
        studySubject.setRegDataEntryStatus(RegistrationDataEntryStatus.COMPLETE);
        ScheduledNonTreatmentEpoch sc=new ScheduledNonTreatmentEpoch();
        NonTreatmentEpoch nt=new NonTreatmentEpoch();
        nt.setEnrollmentIndicator(true);
        sc.setEpoch(nt);
        studySubject.addScheduledEpoch(sc);
        studySubject.getScheduledEpoch().setScEpochDataEntryStatus(ScheduledEpochDataEntryStatus.COMPLETE);
        
        assertEquals("Wrong isRegisterable return", true,
                        studySubject.isRegisterable());
    }
    
    public void testIsRegisterableFalse(){
        StudySubject studySubject = new StudySubject();
        studySubject.setRegDataEntryStatus(RegistrationDataEntryStatus.COMPLETE);
        ScheduledNonTreatmentEpoch sc=new ScheduledNonTreatmentEpoch();
        sc.setEpoch(new NonTreatmentEpoch());
        studySubject.addScheduledEpoch(sc);
        studySubject.getScheduledEpoch().setScEpochDataEntryStatus(ScheduledEpochDataEntryStatus.COMPLETE);
        assertEquals("Wrong isRegisterable return", false,
                        studySubject.isRegisterable());
    }
    
    public void testRequiresCoordinatingCenterApprovalTrue(){
        StudySubject studySubject = new StudySubject();
        studySubject.setRegDataEntryStatus(RegistrationDataEntryStatus.COMPLETE);
        ScheduledNonTreatmentEpoch sc=new ScheduledNonTreatmentEpoch();
        NonTreatmentEpoch nt=new NonTreatmentEpoch();
        nt.setEnrollmentIndicator(true);
        sc.setEpoch(nt);
        studySubject.addScheduledEpoch(sc);
        studySubject.getScheduledEpoch().setScEpochDataEntryStatus(ScheduledEpochDataEntryStatus.COMPLETE);
        studySubject.setStudySite(new StudySite());
        studySubject.getStudySite().setStudy(new Study());
        studySubject.getStudySite().getStudy().setMultiInstitutionIndicator(true);
        assertEquals("Wrong requiresCoordinatingCenterApproval return", true,
                        studySubject.requiresCoordinatingCenterApproval());
    }
    
    public void testRequiresCoordinatingCenterApprovalFalse0(){
        StudySubject studySubject = new StudySubject();
        studySubject.setRegDataEntryStatus(RegistrationDataEntryStatus.COMPLETE);
        ScheduledNonTreatmentEpoch sc=new ScheduledNonTreatmentEpoch();
        NonTreatmentEpoch nt=new NonTreatmentEpoch();
        nt.setEnrollmentIndicator(false);
        sc.setEpoch(nt);
        studySubject.addScheduledEpoch(sc);
        studySubject.getScheduledEpoch().setScEpochDataEntryStatus(ScheduledEpochDataEntryStatus.COMPLETE);
        studySubject.setStudySite(new StudySite());
        studySubject.getStudySite().setStudy(new Study());
        studySubject.getStudySite().getStudy().setMultiInstitutionIndicator(true);
        assertEquals("Wrong requiresCoordinatingCenterApproval return", false,
                        studySubject.requiresCoordinatingCenterApproval());
    }
    
    public void testRequiresCoordinatingCenterApprovalFalse1(){
        StudySubject studySubject = new StudySubject();
        studySubject.setRegDataEntryStatus(RegistrationDataEntryStatus.COMPLETE);
        ScheduledNonTreatmentEpoch sc=new ScheduledNonTreatmentEpoch();
        NonTreatmentEpoch nt=new NonTreatmentEpoch();
        nt.setEnrollmentIndicator(true);
        sc.setEpoch(nt);
        studySubject.addScheduledEpoch(sc);
        studySubject.getScheduledEpoch().setScEpochDataEntryStatus(ScheduledEpochDataEntryStatus.COMPLETE);
        studySubject.setStudySite(new StudySite());
        studySubject.getStudySite().setStudy(new Study());
        studySubject.getStudySite().getStudy().setMultiInstitutionIndicator(false);
        assertEquals("Wrong requiresCoordinatingCenterApproval return", false,
                        studySubject.requiresCoordinatingCenterApproval());
    }
}
