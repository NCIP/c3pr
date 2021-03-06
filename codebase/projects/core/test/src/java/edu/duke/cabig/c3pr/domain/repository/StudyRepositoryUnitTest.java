/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.domain.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.easymock.classextension.EasyMock;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;

import edu.duke.cabig.c3pr.AbstractTestCase;
import edu.duke.cabig.c3pr.constants.CoordinatingCenterStudyStatus;
import edu.duke.cabig.c3pr.constants.SiteStudyStatus;
import edu.duke.cabig.c3pr.constants.StudyDataEntryStatus;
import edu.duke.cabig.c3pr.constants.WorkFlowStatusType;
import edu.duke.cabig.c3pr.dao.HealthcareSiteDao;
import edu.duke.cabig.c3pr.dao.StudyDao;
import edu.duke.cabig.c3pr.dao.StudySiteDao;
import edu.duke.cabig.c3pr.domain.Address;
import edu.duke.cabig.c3pr.domain.DiseaseCategory;
import edu.duke.cabig.c3pr.domain.DiseaseTerm;
import edu.duke.cabig.c3pr.domain.EndPoint;
import edu.duke.cabig.c3pr.domain.GridEndPoint;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.HealthcareSiteInvestigator;
import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.Investigator;
import edu.duke.cabig.c3pr.domain.LocalHealthcareSite;
import edu.duke.cabig.c3pr.domain.LocalInvestigator;
import edu.duke.cabig.c3pr.domain.LocalStudy;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyCoordinatingCenter;
import edu.duke.cabig.c3pr.domain.StudyDisease;
import edu.duke.cabig.c3pr.domain.StudyInvestigator;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.StudySiteStudyVersion;
import edu.duke.cabig.c3pr.domain.StudyVersion;
import edu.duke.cabig.c3pr.domain.SystemAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.repository.impl.StudyRepositoryImpl;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;
import edu.duke.cabig.c3pr.exception.C3PRCodedRuntimeException;
import edu.duke.cabig.c3pr.exception.C3PRExceptionHelper;
import edu.duke.cabig.c3pr.service.StudyService;
import edu.duke.cabig.c3pr.utils.StudyCreationHelper;

public class StudyRepositoryUnitTest extends AbstractTestCase {

    private StudyDao studyDao;

    private StudySiteDao studySiteDao;

    private StudyRepository studyRepository;

    private Study study;

    StudySite studySite;

    List<Study> list;

    List<Identifier> ids;

    private EndPoint endPoint=new GridEndPoint();

    private StudyCreationHelper studyCreationHelper= new StudyCreationHelper();

    private HealthcareSiteDao healthcareSiteDao;
    private C3PRExceptionHelper c3PRExceptionHelper;
    private MessageSource c3prErrorMessages;

    public StudyRepositoryUnitTest() {
        ResourceBundleMessageSource resourceBundleMessageSource = new ResourceBundleMessageSource();
        resourceBundleMessageSource.setBasename("error_messages_multisite");
        ResourceBundleMessageSource resourceBundleMessageSource1 = new ResourceBundleMessageSource();
        resourceBundleMessageSource1.setBasename("error_messages_c3pr");
        resourceBundleMessageSource1.setParentMessageSource(resourceBundleMessageSource);
        this.c3prErrorMessages = resourceBundleMessageSource1;
        this.c3PRExceptionHelper = new C3PRExceptionHelper(c3prErrorMessages);
        studyDao = registerMockFor(StudyDao.class);
        studySiteDao = registerMockFor(StudySiteDao.class);
        studyRepository = new StudyRepositoryImpl();
        healthcareSiteDao = registerDaoMockFor(HealthcareSiteDao.class);
        ((StudyRepositoryImpl) studyRepository).setHealthcareSiteDao(healthcareSiteDao);
        ((StudyRepositoryImpl) studyRepository).setStudyDao(studyDao);
        ((StudyRepositoryImpl) studyRepository).setStudySiteDao(studySiteDao);
        ((StudyRepositoryImpl) studyRepository).setC3prErrorMessages(c3prErrorMessages);
        ((StudyRepositoryImpl) studyRepository).setC3PRExceptionHelper(c3PRExceptionHelper);
        study = studyCreationHelper.createBasicStudy();
        study = createDefaultStudyWithDesign(study);
        study = studyCreationHelper.addConsent(study, "consent 1");
        studySite = study.getStudySites().get(0);
        list = new ArrayList<Study>();
        list.add(study);
        ids = study.getIdentifiers();
        endPoint.setStatus(WorkFlowStatusType.MESSAGE_SEND_CONFIRMED);
    }

    public void testCreateStudyCompleteDataEntryHosted() {
        EasyMock.expect(studyDao.merge(study)).andReturn(study);
        replayMocks();
        studyRepository.createStudy(study);
        verifyMocks();
    }
    
    public void testCreateStudyIncompleteDataEntry1() {
        study.readyToOpen();
        try {
            studyRepository.createStudy(study);
        }
        catch (C3PRCodedRuntimeException e) {
            assertEquals("Wrong exception message", 319, e.getExceptionCode());
        }
        catch (Exception e) {
            fail("Wrong Exception thrown");
        }
    }

    public void testOpenStudyNoStudyFound() {
        EasyMock.expect(studyDao.getByIdentifiers(ids)).andReturn(new ArrayList<Study>());
        replayMocks();
        try {
            studyRepository.openStudy(ids);
        }
        catch (C3PRCodedRuntimeException e) {
            e.printStackTrace();
            assertEquals("Wrong exception message", e.getExceptionCode(),
                            337);
        }
        catch (Exception e) {
            e.printStackTrace();
            fail("Wrong Exception thrown");
        }
        verifyMocks();
    }

    public void testOpenStudyMultipleStudiesFound() {
        list.add(new LocalStudy());
        EasyMock.expect(studyDao.getByIdentifiers(ids)).andReturn(list);
        replayMocks();
        try {
            studyRepository.openStudy(ids);
        }
        catch (C3PRCodedRuntimeException e) {
            e.printStackTrace();
            assertEquals("Wrong exception message", e.getExceptionCode(),
                            338);
        }
        catch (Exception e) {
            fail("Wrong Exception thrown");
        }
        verifyMocks();
    }
    
    //TODO 
    // check the logic and method calls for this test case.
    public void testOpenStudyMultisite() throws C3PRCodedException {
        EasyMock.expect(studyDao.getByIdentifiers(ids)).andReturn(list).times(2);
        EasyMock.expect(studyDao.merge(study)).andReturn(study).times(2);
        replayMocks();
        studyRepository.openStudy(ids);
        verifyMocks();
    }

    public void testOpenClosedStudy() throws C3PRCodedException {
    	study.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL);
        EasyMock.expect(studyDao.getByIdentifiers(ids)).andReturn(list);
        replayMocks();
        try {
            studyRepository.openStudy(ids);
        }
        catch (C3PRCodedRuntimeException e) {
            e.printStackTrace();
            assertEquals("Wrong exception message", e.getExceptionCode(),
                            319);
            return;
        }finally{
            verifyMocks();
        }
        fail("Should have thrown Exception");
    }

//    public void testApproveStudySiteForActivationPendingStudy() {
//        EasyMock.expect(studyDao.getByIdentifiers(ids)).andReturn(list).times(2);
//        replayMocks();
//        try {
//            studyRepository.approveStudySiteForActivation(ids, "Duke");
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
//        }finally{
//            verifyMocks();
//        }
//        fail("Should have thrown Exception");
//    }
//
//    public void testApproveStudySiteForActivationClosedStudySite() {
//    	study.setCoordinatingCenterStudyStatusInternal(CoordinatingCenterStudyStatus.OPEN);
//    	studySite.setSiteStudyStatus(SiteStudyStatus.CLOSED_TO_ACCRUAL);
//        EasyMock.expect(studyDao.getByIdentifiers(ids)).andReturn(list).times(2);
//        replayMocks();
//        try {
//            studyRepository.approveStudySiteForActivation(ids, "Duke");
//        }
//        catch (C3PRCodedRuntimeException e) {
//            e.printStackTrace();
//            assertEquals("Wrong exception message", e.getExceptionCode(),
//                            401);
//            return;
//        }
//        catch (Exception e) {
//            fail("Wrong Exception thrown");
//            return;
//        }finally{
//            verifyMocks();
//        }
//        fail("Should have thrown Exception");
//    }
//
//    public void testApproveStudySiteForActivationInvaidNCICode() {
//        EasyMock.expect(studyDao.getByIdentifiers(ids)).andReturn(list);
//        replayMocks();
//        try {
//            studyRepository.approveStudySiteForActivation(ids, "wrong");
//        }
//        catch (C3PRCodedRuntimeException e) {
//            e.printStackTrace();
//            assertEquals("Wrong exception message", e.getExceptionCode(),
//                            339);
//        }
//        catch (Exception e) {
//            fail("Wrong Exception thrown");
//        }
//        verifyMocks();
//    }
//
//    public void testApproveAffiliateStudySiteForActivation() throws C3PRCodedException {
//    	study.setCoordinatingCenterStudyStatusInternal(CoordinatingCenterStudyStatus.OPEN);
//    	studySite.setHostedMode(false);
//        EasyMock.expect(studyDao.getByIdentifiers(ids)).andReturn(list).times(2);
//        EasyMock.expect(studySiteDao.merge(studySite)).andReturn(studySite);
//        EasyMock.expect(studyService.getLocalNCIInstituteCode()).andReturn("Duke");
//        EasyMock.expect(studyService.isStudyOrganizationLocal("Duke")).andReturn(false);
//        EasyMock.expect(studyService.handleMultiSiteBroadcast(EasyMock.isA(StudySite.class), EasyMock.isA(ServiceName.class), EasyMock.isA(APIName.class),EasyMock.isA(List.class))).andReturn(endPoint);
//        replayMocks();
//        studyRepository.approveStudySiteForActivation(ids, "Duke");
//        verifyMocks();
//    }

    public void testActivateStudySitePendingStudy() {
        EasyMock.expect(studyDao.getByIdentifiers(ids)).andReturn(list).times(2);
        replayMocks();
        try {
            studyRepository.activateStudySite(ids, "Duke", new Date());
        }

        catch (C3PRCodedRuntimeException e) {
            e.printStackTrace();
            assertEquals("Wrong exception message", 323 ,e.getExceptionCode());
            return;
        }
        catch (Exception e) {
            fail("Wrong Exception thrown");
            return;
        }finally{
            verifyMocks();
        }
        fail("Should have thrown Exception");
    }

    public void testActivateStudySiteClosedStudySite() {
    	study.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.OPEN);
    	studySite.handleStudySiteStatusChange(new Date(),SiteStudyStatus.CLOSED_TO_ACCRUAL);
        EasyMock.expect(studyDao.getByIdentifiers(ids)).andReturn(list).times(2);
        replayMocks();
        try {
            studyRepository.activateStudySite(ids, "Duke", new Date());
        }
        catch (C3PRCodedRuntimeException e) {
            e.printStackTrace();
            assertEquals("Wrong exception message", e.getExceptionCode(),
                            401);
            return;
        }
        catch (Exception e) {
            fail("Wrong Exception thrown");
            return;
        }finally{
            verifyMocks();
        }
        fail("Should have thrown Exception");
    }

    public void testActivateCoordinatingCenterStudySite() throws C3PRCodedException {
    	StudySiteStudyVersion studySiteStudyVersion = registerMockFor(StudySiteStudyVersion.class);
    	StudyVersion studyVersion = registerMockFor(StudyVersion.class);
    	studySite.setHostedMode(false);
//    	EasyMock.expect(studySite.getStudySiteStudyVersion()).andReturn(studySiteStudyVersion);
//    	EasyMock.expect(studySite.getIrbApprovalDate()).andReturn(new Date());
//    	EasyMock.expect(studySiteStudyVersion.getStudyVersion()).andReturn(studyVersion);
//    	EasyMock.expect(studyVersion.getVersionDate()).andReturn(new Date());
    	study.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.OPEN);
        EasyMock.expect(studyDao.getByIdentifiers(ids)).andReturn(list).times(2);
        EasyMock.expect(studySiteDao.merge(studySite)).andReturn(studySite);
        //EasyMock.expect(studyService.getLocalNCIInstituteCode()).andReturn("Duke");
        replayMocks();
        studyRepository.activateStudySite(ids, "Duke", new Date());
        verifyMocks();
    }

    public void testActivateAffiliateStudySite() throws C3PRCodedException {
    	studySite.setHostedMode(false);
    	study.getStudyCoordinatingCenter().setHostedMode(false);
    	study.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.OPEN);
        EasyMock.expect(studyDao.getByIdentifiers(ids)).andReturn(list).times(2);
        EasyMock.expect(studySiteDao.merge(studySite)).andReturn(studySite);
        replayMocks();
        studyRepository.activateStudySite(ids, "Duke", new Date());
        verifyMocks();
    }

    public void testCloseStudyHosted() throws C3PRCodedException {
    	study.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.OPEN);
        EasyMock.expect(studyDao.getByIdentifiers(ids)).andReturn(list);
        EasyMock.expect(studyDao.merge(study)).andReturn(study);
        replayMocks();
        studyRepository.closeStudyToAccrual(ids);
        verifyMocks();
    }

    public void testCloseAffiliateStudySite() throws C3PRCodedException {
    	studySite.setHostedMode(false);
    	studySite.handleStudySiteStatusChange(new Date(), SiteStudyStatus.ACTIVE);
        EasyMock.expect(studyDao.getByIdentifiers(ids)).andReturn(list);
        EasyMock.expect(studySiteDao.merge(studySite)).andReturn(studySite);
        replayMocks();
        studyRepository.closeStudySiteToAccrual(ids, "Duke", new Date());
        verifyMocks();
    }

    public StudyInvestigator buildStudyInvestigator(
                    HealthcareSiteInvestigator healthcareSiteInvestigator) {
        StudyInvestigator sInv = new StudyInvestigator();
        sInv.setHealthcareSiteInvestigator(healthcareSiteInvestigator);
        return sInv;
    }

    public HealthcareSite buildHealthcareSite() {
        HealthcareSite hcs = new LocalHealthcareSite();
        hcs.setCtepCode("hcs-001");
        return hcs;
    }

    public Investigator buildInvestigator() {
        Investigator investigator = new LocalInvestigator();
        investigator.setFirstName("Frank");
        investigator.setLastName("Hardy");
        investigator.setAssignedIdentifier("inv-001");
        return investigator;
    }

    public HealthcareSiteInvestigator buildHealthcareSiteInvestigator(Investigator inv,
                    HealthcareSite hcs) {
        HealthcareSiteInvestigator hcsInv = new HealthcareSiteInvestigator();
        hcsInv.setInvestigator(inv);
        hcsInv.setHealthcareSite(hcs);
        return hcsInv;
    }

    public int getCode(String errortypeString) {
        return Integer.parseInt(this.c3prErrorMessages.getMessage(errortypeString, null, null));
    }

    private Study createDefaultStudyWithDesign(Study study) {
        study.setStratificationIndicator(false);
        DiseaseCategory disCatSaved = new DiseaseCategory();
        disCatSaved.setName("AIDS-related Human Papillomavirus");
        // Investigators
        Investigator invSave = new LocalInvestigator();
        invSave.setFirstName("Investigator first name");

        // healthcare site
        HealthcareSite healthcaresite = new LocalHealthcareSite();
        Address address = new Address();
        address.setCity("Reston");
        address.setCountryCode("USA");
        address.setPostalCode("20191");
        address.setStateCode("VA");
        address.setStreetAddress("12359 Sunrise Valley Dr");
        healthcaresite.setAddress(address);
        healthcaresite.setName("duke healthcare");
        healthcaresite.setDescriptionText("duke healthcare");
        healthcaresite.setCtepCode("Duke");
        healthcaresite.getOrganizationAssignedIdentifiers().get(0).setPrimaryIndicator(true);

        // HCSI
        HealthcareSiteInvestigator hcsiSave = new HealthcareSiteInvestigator();
        invSave.addHealthcareSiteInvestigator(hcsiSave);
        hcsiSave.setHealthcareSite(healthcaresite);

        DiseaseTerm term1 = new DiseaseTerm();
        term1.setTerm("AIDS-related anal cancer");
        term1.setCtepTerm("AIDS-related anal cancer");
        term1.setMedraCode(1033333);
        term1.setCategory(disCatSaved);
        DiseaseTerm term2 = new DiseaseTerm();
        term2.setTerm("AIDS-related cervical cancer");
        term2.setCtepTerm("AIDS-related cervical cancer");
        term2.setMedraCode(10322);
        term2.setCategory(disCatSaved);

        DiseaseCategory disCat = disCatSaved;
        Investigator inv = invSave;

        study.addEpoch(studyCreationHelper.createEpoch("Screening"));
        study.addEpoch(studyCreationHelper.createEpochWithArms("Treatment", "Arm A", "Arm B", "Arm C"));
        study.addEpoch(studyCreationHelper.createEpoch("Follow up"));
        study.getEpochs().get(1).setEnrollmentIndicator(true);

        // Study Site
        StudySite studySite= new StudySite();
        studySite.setHealthcareSite(healthcaresite); 
        studySite.setTargetAccrualNumber(1000);
        
        StudySiteStudyVersion studySiteStudyVersion = new StudySiteStudyVersion();
        StudyVersion studyVersion = null;
        if(study.getStudyVersion() == null){
        	studyVersion = new StudyVersion();
        }else{
        	studyVersion = study.getStudyVersion();
        }
        studySiteStudyVersion.setStudyVersion(studyVersion);
        studySiteStudyVersion.setStartDate(new Date());
        study.addStudyVersion(studyVersion);
        
        studySite.addStudySiteStudyVersion(studySiteStudyVersion);
        study.addStudySite(studySite);

        studySite.setIrbApprovalDate(new Date());

        StudyCoordinatingCenter studyCoordinatingCenter = study.getStudyCoordinatingCenters()
        .get(0);
		studyCoordinatingCenter.setHealthcareSite(healthcaresite);
		studyCoordinatingCenter.setStudy(study);
        study.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.PENDING);
        study.setDataEntryStatus(StudyDataEntryStatus.COMPLETE);

        // Study Investigator
        StudyInvestigator studyInvestigator = studySite.getStudyInvestigators().get(
                        studySite.getStudyInvestigators().size());
        studyInvestigator.setRoleCode("role");
        studyInvestigator.setStartDate(new Date());

        HealthcareSiteInvestigator hcsi = hcsiSave;
        hcsi.addStudyInvestigator(studyInvestigator);

        // Identifiers
        List<SystemAssignedIdentifier> identifiers = new ArrayList<SystemAssignedIdentifier>();
        SystemAssignedIdentifier id = new SystemAssignedIdentifier();
        id.setPrimaryIndicator(true);
        id.setSystemName("nci");
        id.setValue("123456");
        id.setType("local");
        identifiers.add(id);
        study.getIdentifiers().addAll(identifiers);

        StudyDisease studyDisease = new StudyDisease();
        studyDisease.setDiseaseTerm(term1);
        studyDisease.setDiseaseTerm(term2);
        study.addStudyDisease(studyDisease);

        return study;
    }

    public void addNewCooordinatingCenter(Study study) {
        StudyCoordinatingCenter studyCoordinatingCenter = study.getStudyCoordinatingCenters()
                        .get(0);
        HealthcareSite healthcaresite = new LocalHealthcareSite();
        Address address = new Address();
        address.setCity("Reston");
        address.setCountryCode("USA");
        address.setPostalCode("20191");
        address.setStateCode("VA");
        address.setStreetAddress("12359 Sunrise Valley Dr");
        healthcaresite.setAddress(address);
        healthcaresite.setName("duke healthcare");
        healthcaresite.setDescriptionText("duke healthcare");
        healthcaresite.setCtepCode("Nci-Cood");
        studyCoordinatingCenter.setHealthcareSite(healthcaresite);
        studyCoordinatingCenter.setStudy(study);
    }
}
