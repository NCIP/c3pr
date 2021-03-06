/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.domain.repository;

import java.util.Date;

import edu.duke.cabig.c3pr.constants.CoordinatingCenterStudyStatus;
import edu.duke.cabig.c3pr.constants.SiteStudyStatus;
import edu.duke.cabig.c3pr.constants.StudyDataEntryStatus;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;
import edu.duke.cabig.c3pr.exception.C3PRCodedRuntimeException;
import edu.duke.cabig.c3pr.exception.C3PRInvalidDataEntryException;
import edu.duke.cabig.c3pr.tools.Configuration;
import edu.duke.cabig.c3pr.utils.StudyDaoTestCaseTemplate;

public class StudyRepositoryHostedTest extends StudyDaoTestCaseTemplate {

	private Study study;
	
    protected void setUp() throws Exception {
        super.setUp();
        Configuration configuration=(Configuration) applicationContext.getBean("configuration");
        configuration.set(Configuration.MULTISITE_ENABLE, "false");

    }

    public void testCreateStudyCompleteDataEntry() {
        // study = studySubjectCreatorHelper.getPersistedMultiSiteNonRandomizedWithArmStudySubject(
        // false).getStudySite().getStudy();
        study = studyCreationHelper.createBasicStudy();
        study = studyCreationHelper.addConsent(study, "consent 1");
        study = createDefaultStudyWithDesign(study);
        studyCreationHelper.addStudySiteAsCooordinatingCenter(study);
        studyDao.save(study);
        study = studyDao.getById(study.getId());
        study = studyRepository.createStudy(study);
        assertEquals("Wrong Data entry status",StudyDataEntryStatus.COMPLETE,study.getDataEntryStatus());
        assertEquals("Wrong Coordinating center status", study.getCoordinatingCenterStudyStatus(),
                        CoordinatingCenterStudyStatus.READY_TO_OPEN);
    }

    public void testCreateStudyInCompleteDataEntry() {
        study = studyCreationHelper.createBasicStudy();
        try {
            studyRepository.createStudy(study);
        }
        catch (C3PRInvalidDataEntryException e) {
        }
        catch (Exception e) {
            fail("Wrong Exception thrown");
        }
    }

    public void testOpenStudyPending() throws C3PRCodedException {
        Study study = studyCreationHelper.createBasicStudy();
        study = studyCreationHelper.addConsent(study, "consent 1");
        study = createDefaultStudyWithDesign(study);
        studyCreationHelper.addStudySiteAsCooordinatingCenter(study);
        studyDao.save(study);
        studyRepository.openStudy(study.getIdentifiers());
        interruptSession();
        study = studyDao.getById(study.getId());
        assertEquals("Wrong Coordinating center status", study.getCoordinatingCenterStudyStatus(),
                        CoordinatingCenterStudyStatus.OPEN);
    }

    public void testOpenStudyReadyToOpen() throws C3PRCodedException {
        study = getPersistedStudy();
        study = studyRepository.openStudy(study.getIdentifiers());
        interruptSession();
        study = studyDao.getById(study.getId());
        assertEquals("Wrong Coordinating center status", study.getCoordinatingCenterStudyStatus(),
                        CoordinatingCenterStudyStatus.OPEN);
    }

//    public void testApproveStudySiteForActivationPendingStudy() {
//        study= getPersistedStudy();
//        try {
//            studyRepository.approveStudySiteForActivation(study.getIdentifiers(), study.getStudySites().get(0).getHealthcareSite().getNciInstituteCode());
//        }
//        catch (C3PRCodedRuntimeException e) {
//            e.printStackTrace();
//            assertEquals("Wrong exception message", e.getExceptionCode(),
//                            400);
//            return;
//        }
//        catch (Exception e) {
//            fail("Wrong Exception thrown");
//            return;
//        }
//        fail("Should have thrown Exception");
//    }
//    
//    public void testApproveStudySiteForActivationInvaidNCICode() throws Exception{
//        study = getOpenedStudy();
//        try {
//            studyRepository.approveStudySiteForActivation(study.getIdentifiers(), "wrong");
//        }
//        catch (C3PRCodedRuntimeException e) {
//            e.printStackTrace();
//            assertEquals("Wrong exception message", e.getExceptionCode(),
//                            339);
//        }
//        catch (Exception e) {
//            fail("Wrong Exception thrown");
//        }
//    }
//    
//    public void testApproveStudySiteForActivationClosedStudySite() throws Exception{
//        study=getOpenedStudy();
//        study.getStudySites().get(0).setSiteStudyStatus(SiteStudyStatus.CLOSED_TO_ACCRUAL_AND_TREATMENT);
//        study.getStudySites().set(0, studySiteDao.merge(study.getStudySites().get(0)));
//        interruptSession();
//        try {
//            studyRepository.approveStudySiteForActivation(study.getIdentifiers(), study.getStudySites().get(0).getHealthcareSite().getNciInstituteCode());
//        }
//        catch (RuntimeException e) {
//            return;
//        }
//        catch (Exception e) {
//            fail("Wrong Exception thrown");
//            return;
//        }
//        fail("Should have thrown Exception");
//    }
//    
//    public void testApproveCoordinatingCenterStudySiteForActivation() throws Exception {
//        study=getOpenedStudy();
//        StudySite studySite=studyRepository.approveStudySiteForActivation(study.getIdentifiers(), study.getStudySites().get(0).getHealthcareSite().getNciInstituteCode());
//        assertEquals("Wrong SiteStudyStatus", SiteStudyStatus.APPROVED_FOR_ACTIVTION, studySite.getSiteStudyStatus() );
//    }
//
//    public void testApproveAffiliateStudySiteForActivation() throws Exception {
//        study=getOpenedStudy();
//        StudySite studySite=studyRepository.approveStudySiteForActivation(study.getIdentifiers(), study.getStudySites().get(0).getHealthcareSite().getNciInstituteCode());
//        assertEquals("Wrong SiteStudyStatus", SiteStudyStatus.APPROVED_FOR_ACTIVTION, studySite.getSiteStudyStatus() );
//    }

    public void testActivateStudySitePendingStudy() {
        Study study = studyCreationHelper.createBasicStudy();
        study = createDefaultStudyWithDesign(study);
        studyCreationHelper.addStudySiteAsCooordinatingCenter(study);
        studyDao.save(study);
        int id = study.getId(); 
        interruptSession();
        study = studyDao.getById(id);
        try {
            studyRepository.activateStudySite(study.getIdentifiers(), study.getStudySites().get(0).getHealthcareSite().getCtepCode(), new Date());
        }
        catch (C3PRCodedRuntimeException e) {
            e.printStackTrace();
            assertEquals("Wrong exception message", e.getExceptionCode(),
                            323);
            return;
        }
        catch (Exception e) {
            fail("Wrong Exception thrown");
            return;
        }
        fail("Should have thrown Exception");
    }
    
    public void testActivateStudySiteClosedStudySite() {
        study=getPersistedStudy();
        study.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.OPEN);
        study.getStudySites().get(0).handleStudySiteStatusChange(new Date(), SiteStudyStatus.CLOSED_TO_ACCRUAL_AND_TREATMENT);
        study.getStudySites().set(0, studySiteDao.merge(study.getStudySites().get(0)));
        interruptSession();
        try {
            studyRepository.activateStudySite(study.getIdentifiers(), study.getStudySites().get(0).getHealthcareSite().getCtepCode(), new Date());
        }
        catch (RuntimeException e) {
            return;
        }
        catch (Exception e) {
            fail("Wrong Exception thrown");
            return;
        }
        fail("Should have thrown Exception");
    }
    
    public void testActivatePendingStudySite() throws Exception {
        study=getOpenedStudy();
        addNewCooordinatingCenter(study);
        studyDao.merge(study);
        interruptSession();
        StudySite studySite=studyRepository.activateStudySite(study.getIdentifiers(), study.getStudySites().get(0).getHealthcareSite().getCtepCode(), new Date());
        assertEquals("Wrong SiteStudyStatus", SiteStudyStatus.ACTIVE, studySite.getSiteStudyStatus() );
    }

    public void testActivateApprovedStudySite() throws Exception {
        study=getOpenedStudy();
        addNewCooordinatingCenter(study);
        //study.getStudySites().get(0).setSiteStudyStatus(SiteStudyStatus.APPROVED_FOR_ACTIVTION);
        study.getStudySites().set(0, studySiteDao.merge(study.getStudySites().get(0)));
        studyDao.merge(study);
        interruptSession();
        StudySite studySite=studyRepository.activateStudySite(study.getIdentifiers(), study.getStudySites().get(0).getHealthcareSite().getCtepCode(), new Date());
        assertEquals("Wrong SiteStudyStatus", SiteStudyStatus.ACTIVE, studySite.getSiteStudyStatus() );
    }
    
    public void testCloseStudyLocal() throws Exception {
        study=getOpenedStudy();
        studyRepository.closeStudyToAccrual(study.getIdentifiers());
        assertEquals("Wrong Coordinating center status", study.getCoordinatingCenterStudyStatus(),
                        CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL);
    }

    public void testCloseAffiliateStudySite() throws C3PRCodedException {
        study=getPersistedStudy();
        study.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.OPEN);
        study.getStudySites().get(0).activate(new Date());
        study.getStudySites().set(0, studySiteDao.merge(study.getStudySites().get(0)));
        int id = study.getId();
        interruptSession();
        study=studyDao.getById(id);
        StudySite studySite=studyRepository.closeStudySiteToAccrual(study.getIdentifiers(), study.getStudySites().get(0).getHealthcareSite().getCtepCode(), new Date());
        assertEquals("Wrong SiteStudyStatus", SiteStudyStatus.CLOSED_TO_ACCRUAL, studySite.getSiteStudyStatus() );
    }
}
