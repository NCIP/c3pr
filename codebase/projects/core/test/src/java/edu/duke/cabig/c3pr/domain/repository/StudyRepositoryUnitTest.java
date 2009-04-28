package edu.duke.cabig.c3pr.domain.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.easymock.classextension.EasyMock;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;

import edu.duke.cabig.c3pr.AbstractTestCase;
import edu.duke.cabig.c3pr.dao.HealthcareSiteDao;
import edu.duke.cabig.c3pr.dao.HealthcareSiteInvestigatorDao;
import edu.duke.cabig.c3pr.dao.InvestigatorDao;
import edu.duke.cabig.c3pr.dao.StudyDao;
import edu.duke.cabig.c3pr.dao.StudySiteDao;
import edu.duke.cabig.c3pr.domain.APIName;
import edu.duke.cabig.c3pr.domain.Address;
import edu.duke.cabig.c3pr.domain.CoordinatingCenterStudyStatus;
import edu.duke.cabig.c3pr.domain.DiseaseCategory;
import edu.duke.cabig.c3pr.domain.DiseaseTerm;
import edu.duke.cabig.c3pr.domain.EndPoint;
import edu.duke.cabig.c3pr.domain.Epoch;
import edu.duke.cabig.c3pr.domain.GridEndPoint;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.HealthcareSiteInvestigator;
import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.Investigator;
import edu.duke.cabig.c3pr.domain.LocalHealthcareSite;
import edu.duke.cabig.c3pr.domain.LocalInvestigator;
import edu.duke.cabig.c3pr.domain.ServiceName;
import edu.duke.cabig.c3pr.domain.SiteStudyStatus;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyCoordinatingCenter;
import edu.duke.cabig.c3pr.domain.StudyDataEntryStatus;
import edu.duke.cabig.c3pr.domain.StudyDisease;
import edu.duke.cabig.c3pr.domain.StudyInvestigator;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.SystemAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.WorkFlowStatusType;
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

    private StudyService studyService;

    private Study study;

    StudySite studySite;

    List<Study> list;
    
    List<Identifier> ids;
    
    private EndPoint endPoint=new GridEndPoint();
    
    private StudyCreationHelper studyCreationHelper= new StudyCreationHelper(); 
    
    private HealthcareSiteDao healthcareSiteDao;
    private InvestigatorDao investigatorDao;
    private HealthcareSiteInvestigatorDao healthcareSiteInvestigatorDao;
    private C3PRExceptionHelper c3PRExceptionHelper;
    private MessageSource c3prErrorMessages;
    private HealthcareSite healthcareSite;

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
        studyService = registerMockFor(StudyService.class);
        healthcareSiteDao = registerDaoMockFor(HealthcareSiteDao.class);
        ((StudyRepositoryImpl) studyRepository).setHealthcareSiteDao(healthcareSiteDao);
        ((StudyRepositoryImpl) studyRepository).setStudyDao(studyDao);
        ((StudyRepositoryImpl) studyRepository).setStudySiteDao(studySiteDao);
        ((StudyRepositoryImpl) studyRepository).setStudyService(studyService);
        ((StudyRepositoryImpl) studyRepository).setC3prErrorMessages(c3prErrorMessages);
        ((StudyRepositoryImpl) studyRepository).setC3PRExceptionHelper(c3PRExceptionHelper);
        study = studyCreationHelper.createBasicStudy();
        study = createDefaultStudyWithDesign(study);
        studySite = study.getStudySites().get(0);
        list = new ArrayList<Study>();
        list.add(study);
        ids = study.getIdentifiers();
        healthcareSiteInvestigatorDao = registerMockFor(HealthcareSiteInvestigatorDao.class);
        investigatorDao = registerDaoMockFor(InvestigatorDao.class);
        endPoint.setStatus(WorkFlowStatusType.MESSAGE_SEND_CONFIRMED);
    }

    public void testCreateStudyCompleteDataEntryHosted() {
        EasyMock.expect(studyDao.merge(study)).andReturn(study);
        EasyMock.expect(studyService.isMultisiteEnable()).andReturn(false);
        replayMocks();
        studyRepository.createStudy(study);
        verifyMocks();
    }

    public void testCreateStudyCompleteDataEntryCoCenter() {
    	EasyMock.expect(studyService.isMultisiteEnable()).andReturn(true);
    	EasyMock.expect(studyService.getLocalNCIInstituteCode()).andReturn("Duke").times(2);
    	EasyMock.expect(studyDao.getByIdentifiers(ids)).andReturn(list).times(2);
    	EasyMock.expect(studyService.canMultisiteBroadcast(studySite)).andReturn(true).times(1);
    	EasyMock.expect(studyService.handleMultiSiteBroadcast(EasyMock.isA(StudySite.class), EasyMock.isA(ServiceName.class), EasyMock.isA(APIName.class),EasyMock.isA(List.class))).andReturn(new GridEndPoint()).times(1);
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
        list.add(new Study());
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

    public void testOpenStudyMultisite() throws C3PRCodedException {
        EasyMock.expect(studyDao.getByIdentifiers(ids)).andReturn(list).times(6);
        EasyMock.expect(studyDao.merge(study)).andReturn(study).times(2);
        EasyMock.expect(studyService.isMultisiteEnable()).andReturn(true).times(2);
        EasyMock.expect(studyService.getLocalNCIInstituteCode()).andReturn("Duke").times(4);
        EasyMock.expect(studyService.canMultisiteBroadcast(studySite)).andReturn(true).times(2);
        EasyMock.expect(studyService.handleMultiSiteBroadcast(EasyMock.isA(StudySite.class), EasyMock.isA(ServiceName.class), EasyMock.isA(APIName.class),EasyMock.isA(List.class))).andReturn(new GridEndPoint()).times(2);
        replayMocks();
        studyRepository.openStudy(ids);
        verifyMocks();
    }
    
    public void testOpenClosedStudy() throws C3PRCodedException {
    	study.setCoordinatingCenterStudyStatusInternal(CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL);
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
            studyRepository.activateStudySite(ids, "Duke");
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
    	study.setCoordinatingCenterStudyStatusInternal(CoordinatingCenterStudyStatus.OPEN);
    	studySite.setSiteStudyStatus(SiteStudyStatus.CLOSED_TO_ACCRUAL);
        EasyMock.expect(studyDao.getByIdentifiers(ids)).andReturn(list).times(2);
        replayMocks();
        try {
            studyRepository.activateStudySite(ids, "Duke");
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
    	studySite.setHostedMode(false);
    	study.setCoordinatingCenterStudyStatusInternal(CoordinatingCenterStudyStatus.OPEN);
        EasyMock.expect(studyDao.getByIdentifiers(ids)).andReturn(list).times(2);
        EasyMock.expect(studySiteDao.merge(studySite)).andReturn(studySite);
        //EasyMock.expect(studyService.getLocalNCIInstituteCode()).andReturn("Duke");
        replayMocks();
        studyRepository.activateStudySite(ids, "Duke");
        verifyMocks();
    }

    public void testActivateAffiliateStudySite() throws C3PRCodedException {
    	studySite.setHostedMode(false);
    	study.getStudyCoordinatingCenter().setHostedMode(false);
    	study.setCoordinatingCenterStudyStatusInternal(CoordinatingCenterStudyStatus.OPEN);
    	//studySite.setSiteStudyStatus(SiteStudyStatus.APPROVED_FOR_ACTIVTION);
        EasyMock.expect(studyDao.getByIdentifiers(ids)).andReturn(list).times(2);
        EasyMock.expect(studyService.getLocalNCIInstituteCode()).andReturn("Test");
        EasyMock.expect(studySiteDao.merge(studySite)).andReturn(studySite);
        EasyMock.expect(studyService.handleMultiSiteBroadcast(EasyMock.isA(StudyCoordinatingCenter.class), EasyMock.isA(ServiceName.class), EasyMock.isA(APIName.class),EasyMock.isA(List.class))).andReturn(endPoint);
        replayMocks();
        studyRepository.activateStudySite(ids, "Duke");
        verifyMocks();
    }
    
    public void testCloseStudyHosted() throws C3PRCodedException {
    	study.setCoordinatingCenterStudyStatusInternal(CoordinatingCenterStudyStatus.OPEN);
        EasyMock.expect(studyDao.getByIdentifiers(ids)).andReturn(list);
        EasyMock.expect(studyService.isMultisiteEnable()).andReturn(false);
        EasyMock.expect(studyDao.merge(study)).andReturn(study);
        replayMocks();
        studyRepository.closeStudyToAccrual(ids);
        verifyMocks();
    }

    public void testCloseAffiliateStudySite() throws C3PRCodedException {
    	studySite.setHostedMode(false);
        EasyMock.expect(studyDao.getByIdentifiers(ids)).andReturn(list);
        EasyMock.expect(studySiteDao.merge(studySite)).andReturn(studySite);
        EasyMock.expect(studyService.getLocalNCIInstituteCode()).andReturn("Duke");
        EasyMock.expect(studyService.isStudyOrganizationLocal("Duke")).andReturn(false);
        EasyMock.expect(studyService.handleMultiSiteBroadcast(EasyMock.isA(StudySite.class), EasyMock.isA(ServiceName.class), EasyMock.isA(APIName.class),EasyMock.isA(List.class))).andReturn(endPoint);
        replayMocks();
        studyRepository.closeStudySiteToAccrual(ids, "Duke");
        verifyMocks();
    }

//    public void testBuildAndSave() {
//        // building the study
//        Study study = studyCreationHelper.getLocalNonRandomizedTratmentWithArmStudy();
//
//        StudySite organization = new StudySite();
//        HealthcareSite healthcareSite = buildHealthcareSite();
//
//        Investigator investigator = buildInvestigator();
//        List<Investigator> investigators = new ArrayList<Investigator>();
//        investigators.add(investigator);
//        HealthcareSiteInvestigator healthcareSiteInvestigator = buildHealthcareSiteInvestigator(
//                        investigator, healthcareSite);
//        StudyInvestigator sInv = buildStudyInvestigator(healthcareSiteInvestigator);
//        ArrayList<StudyInvestigator> sInvList = new ArrayList<StudyInvestigator>();
//        sInvList.add(sInv);
//
//        organization.setHealthcareSite(healthcareSite);
//        organization.setStudyInvestigators(sInvList);
//        study.getStudyOrganizations().add(organization);
//
//        OrganizationAssignedIdentifier organizationAssignedIdentifier = new OrganizationAssignedIdentifier();
//        organizationAssignedIdentifier.setHealthcareSite(healthcareSite);
//        organizationAssignedIdentifier.setValue("oai-001");
//        study.getOrganizationAssignedIdentifiers().add(organizationAssignedIdentifier);
//
//        // list of mocks
//        EasyMock.expect(healthcareSiteDao.getByNciInstituteCode("hcs-001")).andReturn(
//                        healthcareSite);
//        EasyMock.expect(investigatorDao.getInvestigatorsByNciInstituteCode("inv-001")).andReturn(
//                        investigators);
//        EasyMock.expect(
//                        healthcareSiteInvestigatorDao.getSiteInvestigator(healthcareSite,
//                                        investigator)).andReturn(healthcareSiteInvestigator);
//        EasyMock.expect(healthcareSiteDao.getByNciInstituteCode("hcs-001")).andReturn(
//                        healthcareSite);
//        studyDao.save(study);
//        replayMocks();
//
//        try {
//            studyRepository.buildAndSave(study);
//        }
//        catch (Exception e) {
//            assertFalse("C3PRCodedException thrown", false);
//        }
//        verifyMocks();
//    }

    public StudyInvestigator buildStudyInvestigator(
                    HealthcareSiteInvestigator healthcareSiteInvestigator) {
        StudyInvestigator sInv = new StudyInvestigator();
        sInv.setHealthcareSiteInvestigator(healthcareSiteInvestigator);
        return sInv;
    }

    public HealthcareSite buildHealthcareSite() {
        HealthcareSite hcs = new LocalHealthcareSite();
        hcs.setNciInstituteCode("hcs-001");
        return hcs;
    }

    public Investigator buildInvestigator() {
        Investigator investigator = new LocalInvestigator();
        investigator.setFirstName("Frank");
        investigator.setLastName("Hardy");
        investigator.setNciIdentifier("inv-001");
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
        healthcaresite.setNciInstituteCode("Duke");

        // HCSI
        HealthcareSiteInvestigator hcsiSave = new HealthcareSiteInvestigator();
        invSave.addHealthcareSiteInvestigator(hcsiSave);
        hcsiSave.setHealthcareSite(healthcaresite);

        DiseaseTerm term1 = new DiseaseTerm();
        term1.setTerm("AIDS-related anal cancer");
        term1.setCtepTerm("AIDS-related anal cancer");
        term1.setMedraCode(1033333);
        term1.setDiseaseCategory(disCatSaved);
        DiseaseTerm term2 = new DiseaseTerm();
        term2.setTerm("AIDS-related cervical cancer");
        term2.setCtepTerm("AIDS-related cervical cancer");
        term2.setMedraCode(10322);
        term2.setDiseaseCategory(disCatSaved);

        DiseaseCategory disCat = disCatSaved;
        Investigator inv = invSave;

        study.addEpoch(Epoch.createEpoch("Screening"));
        study.addEpoch(Epoch.createEpochWithArms("Treatment", "Arm A", "Arm B", "Arm C"));
        study.addEpoch(Epoch.createEpoch("Follow up"));
        study.getEpochs().get(1).setEnrollmentIndicator(true);

        // Study Site
        StudySite studySite=study.getStudySites().get(0);
        studySite.setHealthcareSite(healthcaresite); //
        studySite.setStartDate(new Date());
        studySite.setIrbApprovalDate(new Date());
        studySite.setRoleCode("role");
        
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

        // Diseases
        System.out.println("disease disCat id ************" + disCat.getId());

        StudyDisease studyDisease = new StudyDisease();
        studyDisease.setDiseaseTerm(term1);
        studyDisease.setDiseaseTerm(term2);
        studyDisease.setStudy(study);

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
        healthcaresite.setNciInstituteCode("Nci-Cood");
        studyCoordinatingCenter.setHealthcareSite(healthcaresite);
        studyCoordinatingCenter.setStudy(study);
    }
}
