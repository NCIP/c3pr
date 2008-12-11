package edu.duke.cabig.c3pr.domain.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Transient;

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
import edu.duke.cabig.c3pr.domain.CoordinatingCenterStudyStatus;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.HealthcareSiteInvestigator;
import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.Investigator;
import edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.SiteStudyStatus;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyDataEntryStatus;
import edu.duke.cabig.c3pr.domain.StudyInvestigator;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.repository.impl.StudyRepositoryImpl;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;
import edu.duke.cabig.c3pr.exception.C3PRCodedRuntimeException;
import edu.duke.cabig.c3pr.exception.C3PRExceptionHelper;
import edu.duke.cabig.c3pr.service.StudyService;
import edu.duke.cabig.c3pr.utils.StudyCreationHelper;
import gov.nih.nci.cabig.ctms.domain.AbstractMutableDomainObject;

public class StudyRepositoryUnitTest extends AbstractTestCase {

    private StudyDao studyDao;

    private StudySiteDao studySiteDao;

    private StudyRepository studyRepository;

    private StudyService studyService;

    private Study study;

    StudySite studySite;

    List<Study> list;

    List<Identifier> ids;
    
    private StudyCreationHelper studyCreationHelper; 
    
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
        ((StudyRepositoryImpl) studyRepository).setStudyDao(studyDao);
        ((StudyRepositoryImpl) studyRepository).setStudySiteDao(studySiteDao);
        ((StudyRepositoryImpl) studyRepository).setStudyService(studyService);
        study=registerMockFor(Study.class);
        studySite = registerMockFor(StudySite.class);
        list = new ArrayList<Study>();
        list.add(study);
        ids = new ArrayList<Identifier>();
        healthcareSiteDao = registerDaoMockFor(HealthcareSiteDao.class);
        investigatorDao = registerDaoMockFor(InvestigatorDao.class);
        healthcareSiteInvestigatorDao = registerMockFor(HealthcareSiteInvestigatorDao.class);
        ((StudyRepositoryImpl) studyRepository).setHealthcareSiteDao(healthcareSiteDao);
        healthcareSite=new HealthcareSite();
    }

    public void testCreateStudyCompleteDataEntryHosted() {
 //       study.updateDataEntryStatus();
 //       EasyMock.expect(study.getDataEntryStatus()).andReturn(StudyDataEntryStatus.COMPLETE);
 //       study.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.READY_TO_OPEN);
        EasyMock.expect(studyDao.merge(study)).andReturn(study);
        EasyMock.expect(study.isMultisite()).andReturn(false);
        study.readyToOpen();
        replayMocks();
        studyRepository.createStudy(study);
        verifyMocks();
    }

    public void testCreateStudyCompleteDataEntryCoCenter() {
     //   study.updateDataEntryStatus();
    	study.readyToOpen();
     //   EasyMock.expect(study.getDataEntryStatus()).andReturn(StudyDataEntryStatus.COMPLETE);
     //   study.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.READY_TO_OPEN);
        EasyMock.expect(studyDao.merge(study)).andReturn(study);
        EasyMock.expect(study.isMultisite()).andReturn(true);
        EasyMock.expect(studyService.getLocalNCIInstituteCode()).andReturn("CC");
    //    EasyMock.expect(study.isCoOrdinatingCenter("CC")).andReturn(true);
        List<AbstractMutableDomainObject> domainObjects = new ArrayList<AbstractMutableDomainObject>();
        domainObjects.add(study);
        studyRepository.handleAffiliateSitesBroadcast(study, APIName.CREATE_STUDY,
                        domainObjects);
        replayMocks();
        studyRepository.createStudy(study);
        verifyMocks();
    }

    public void testCreateStudyIncompleteDataEntry1() {
        study.readyToOpen();
        EasyMock.expect(studyDao.merge(study)).andReturn(study);
        
    //    EasyMock.expect(study.getDataEntryStatus()).andReturn(StudyDataEntryStatus.INCOMPLETE);
        replayMocks();
        try {
            studyRepository.createStudy(study);
        }
        catch (C3PRCodedRuntimeException e) {
            assertEquals("Wrong exception message", e.getExceptionCode(),
                            336);
        }
        catch (Exception e) {
            fail("Wrong Exception thrown");
        }
        verifyMocks();
    }
    
    public void testCreateStudyIncompleteDataEntry2() {
        study.readyToOpen();
        EasyMock.expectLastCall().andThrow(this.c3PRExceptionHelper.getRuntimeException(
                        getCode("C3PR.EXCEPTION.STUDY.DATAENTRY.MISSING.STUDY_SITE.CODE")));
        replayMocks();
        try {
            studyRepository.createStudy(study);
        }
        catch (C3PRCodedRuntimeException e) {
            assertEquals("Wrong exception message", e.getExceptionCode(),
                            301);
        }
        catch (Exception e) {
            fail("Wrong Exception thrown");
        }
        verifyMocks();
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

    public void testOpenStudyPendingCoCenter() throws C3PRCodedException {
        EasyMock.expect(studyDao.getByIdentifiers(ids)).andReturn(list).times(2);
        EasyMock.expect(study.getCoordinatingCenterStudyStatus()).andReturn(CoordinatingCenterStudyStatus.PENDING);
        study.updateDataEntryStatus();
        EasyMock.expect(study.getDataEntryStatus()).andReturn(StudyDataEntryStatus.COMPLETE);
        study.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.READY_TO_OPEN);
        EasyMock.expect(studyDao.merge(study)).andReturn(study).times(2);
        EasyMock.expect(study.isMultisite()).andReturn(true).times(2);
        EasyMock.expect(studyService.getLocalNCIInstituteCode()).andReturn("CC").times(2);
        EasyMock.expect(study.isCoOrdinatingCenter("CC")).andReturn(true).times(2);
        List<AbstractMutableDomainObject> domainObjects = new ArrayList<AbstractMutableDomainObject>();
        domainObjects.add(study);
        studyRepository.handleAffiliateSitesBroadcast(study, APIName.CREATE_STUDY,
                        domainObjects);
        study.open();
        studyRepository.handleAffiliateSitesBroadcast(study, APIName.OPEN_STUDY, ids);
        replayMocks();
        studyRepository.openStudy(ids);
        verifyMocks();
    }
    
    public void testOpenStudyMultisite() throws C3PRCodedException {
        list.clear();
        list.add(0, study);
        study.open();
        EasyMock.expect(study.getCoordinatingCenterStudyStatus()).andReturn(CoordinatingCenterStudyStatus.READY_TO_OPEN);
        EasyMock.expect(studyDao.getByIdentifiers(ids)).andReturn(list);
        EasyMock.expect(studyDao.merge(study)).andReturn(study);
        EasyMock.expect(study.isMultisite()).andReturn(true);
        EasyMock.expect(studyService.getLocalNCIInstituteCode()).andReturn("Test");
        EasyMock.expect(study.isCoOrdinatingCenter("Test")).andReturn(true);
        studyRepository.handleAffiliateSitesBroadcast(study, APIName.OPEN_STUDY, ids);
        replayMocks();
        studyRepository.openStudy(ids);
        verifyMocks();
    }
    
    public void testOpenClosedStudy() throws C3PRCodedException {
        EasyMock.expect(studyDao.getByIdentifiers(ids)).andReturn(list);
        study.open();
        EasyMock.expectLastCall().andThrow(this.c3PRExceptionHelper.getRuntimeException(
                        getCode("C3PR.EXCEPTION.STUDY.STATUS_CANNOT_SET_TO_ACTIVE.CODE")));
        EasyMock.expect(study.getCoordinatingCenterStudyStatus()).andReturn(CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL);
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

    public void testApproveStudySiteForActivationPendingStudy() {
        EasyMock.expect(studyDao.getByIdentifiers(ids)).andReturn(list);
        EasyMock.expect(study.getStudySite("wrong")).andReturn(studySite);
        studySite.approveForActivation();
        EasyMock.expectLastCall().andThrow(this.c3PRExceptionHelper.getRuntimeException(
                        getCode("C3PR.EXCEPTION.STUDYSITE.STATUS_STUDY_NOT_OPEN.CODE")));
        replayMocks();
        try {
            studyRepository.approveStudySiteForActivation(ids, "wrong");
        }
        catch (C3PRCodedRuntimeException e) {
            e.printStackTrace();
            assertEquals("Wrong exception message", e.getExceptionCode(),
                            400);
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
    
    public void testApproveStudySiteForActivationClosedStudySite() {
        EasyMock.expect(studyDao.getByIdentifiers(ids)).andReturn(list);
        EasyMock.expect(study.getStudySite("wrong")).andReturn(studySite);
        studySite.approveForActivation();
        EasyMock.expectLastCall().andThrow(this.c3PRExceptionHelper.getRuntimeException(
                        getCode("C3PR.EXCEPTION.STUDYSITE.STATUS_CANNOT_SET_STATUS.CODE")));
        replayMocks();
        try {
            studyRepository.approveStudySiteForActivation(ids, "wrong");
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
    
    public void testApproveStudySiteForActivationInvaidNCICode() {
        EasyMock.expect(studyDao.getByIdentifiers(ids)).andReturn(list);
        EasyMock.expect(study.getStudySite("wrong")).andReturn(studySite);
        studySite.approveForActivation();
        EasyMock.expectLastCall().andThrow(this.c3PRExceptionHelper.getRuntimeException(
                        getCode("C3PR.EXCEPTION.STUDY.STUDYSITE_NOT_FOUND_INVALID_NCICODE.CODE"), new String[]{"wrong"}));
        replayMocks();
        try {
            studyRepository.approveStudySiteForActivation(ids, "wrong");
        }
        catch (C3PRCodedRuntimeException e) {
            e.printStackTrace();
            assertEquals("Wrong exception message", e.getExceptionCode(),
                            339);
        }
        catch (Exception e) {
            fail("Wrong Exception thrown");
        }
        verifyMocks();
    }

    public void testApproveCoordinatingCenterStudySiteForActivation() throws C3PRCodedException {
        EasyMock.expect(studyDao.getByIdentifiers(ids)).andReturn(list);
        EasyMock.expect(study.getStudySite("CC")).andReturn(studySite);
        studySite.approveForActivation();
        EasyMock.expect(studySiteDao.merge(studySite)).andReturn(studySite);
        EasyMock.expect(study.isMultisite()).andReturn(true);
        EasyMock.expect(studyService.isStudyOrganizationLocal("CC")).andReturn(true);
        replayMocks();
        studyRepository.approveStudySiteForActivation(ids, "CC");
        verifyMocks();
    }

    public void testApproveAffiliateStudySiteForActivation() throws C3PRCodedException {
        EasyMock.expect(studyDao.getByIdentifiers(ids)).andReturn(list);
        EasyMock.expect(study.getStudySite("Test")).andReturn(studySite).times(2);
        studySite.approveForActivation();
        EasyMock.expect(studySiteDao.merge(studySite)).andReturn(studySite);
        EasyMock.expect(study.isMultisite()).andReturn(true);
        EasyMock.expect(studyService.getLocalNCIInstituteCode()).andReturn("CC");
        EasyMock.expect(studyService.isStudyOrganizationLocal("Test")).andReturn(false);
        EasyMock.expect(study.isCoOrdinatingCenter("CC")).andReturn(true);
        EasyMock.expect(studySite.getHealthcareSite()).andReturn(healthcareSite);
        List<AbstractMutableDomainObject> domainObjects = new ArrayList<AbstractMutableDomainObject>();
        domainObjects.addAll(ids);
        domainObjects.add(healthcareSite);
        studyRepository.handleAffiliateSiteBroadcast("Test", study,
                        APIName.APPROVE_STUDY_SITE_FOR_ACTIVATION, domainObjects);
        replayMocks();
        studyRepository.approveStudySiteForActivation(ids, "Test");
        verifyMocks();
    }

    public void testActivateStudySitePendingStudy() {
        EasyMock.expect(studyDao.getByIdentifiers(ids)).andReturn(list);
        EasyMock.expect(study.getStudySite("Test")).andReturn(studySite);
        EasyMock.expect(study.isMultisite()).andReturn(true);
        EasyMock.expect(studyService.getLocalNCIInstituteCode()).andReturn("Test");
        EasyMock.expect(study.isCoOrdinatingCenter("Test")).andReturn(false);
        EasyMock.expect(studySite.getSiteStudyStatus()).andReturn(SiteStudyStatus.APPROVED_FOR_ACTIVTION);
        studySite.activate();
        EasyMock.expectLastCall().andThrow(this.c3PRExceptionHelper.getRuntimeException(
                        getCode("C3PR.EXCEPTION.STUDYSITE.STATUS_STUDY_NOT_OPEN.CODE")));
        replayMocks();
        try {
            studyRepository.activateStudySite(ids, "Test");
        }
        
        catch (C3PRCodedRuntimeException e) {
            e.printStackTrace();
            assertEquals("Wrong exception message", e.getExceptionCode(),
                            400);
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
        EasyMock.expect(studyDao.getByIdentifiers(ids)).andReturn(list);
        EasyMock.expect(study.getStudySite("Test")).andReturn(studySite);
        EasyMock.expect(study.isMultisite()).andReturn(false);
        studySite.activate();
        EasyMock.expectLastCall().andThrow(this.c3PRExceptionHelper.getRuntimeException(
                        getCode("C3PR.EXCEPTION.STUDYSITE.STATUS_CANNOT_SET_STATUS.CODE")));
        replayMocks();
        try {
            studyRepository.activateStudySite(ids, "Test");
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
    
    public void testActivateAffiliateStudySitePendingStudySite() {
        EasyMock.expect(studyDao.getByIdentifiers(ids)).andReturn(list);
        EasyMock.expect(study.getStudySite("Test")).andReturn(studySite);
        EasyMock.expect(study.isMultisite()).andReturn(true);
        EasyMock.expect(studyService.getLocalNCIInstituteCode()).andReturn("Test");
        EasyMock.expect(study.isCoOrdinatingCenter("Test")).andReturn(false);
        EasyMock.expect(studySite.getSiteStudyStatus()).andReturn(SiteStudyStatus.PENDING);
        replayMocks();
        try {
            studyRepository.activateStudySite(ids, "Test");
        }
        catch (C3PRCodedRuntimeException e) {
            e.printStackTrace();
            assertEquals("Wrong exception message", e.getExceptionCode(),
                            402);
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
        EasyMock.expect(studyDao.getByIdentifiers(ids)).andReturn(list);
        EasyMock.expect(study.getStudySite("CC")).andReturn(studySite);
        EasyMock.expect(study.isMultisite()).andReturn(true).times(2);
        EasyMock.expect(study.isCoOrdinatingCenter("CC")).andReturn(true).times(2);
        EasyMock.expect(studyService.getLocalNCIInstituteCode()).andReturn("CC").times(2);
        studySite.activate();
        EasyMock.expect(studySiteDao.merge(studySite)).andReturn(studySite);
        replayMocks();
        studyRepository.activateStudySite(ids, "CC");
        verifyMocks();
    }

    public void testActivateAffiliateStudySite() throws C3PRCodedException {
        EasyMock.expect(studyDao.getByIdentifiers(ids)).andReturn(list);
        EasyMock.expect(study.getStudySite("Test")).andReturn(studySite).times(2);
        EasyMock.expect(study.isMultisite()).andReturn(true).times(2);
        EasyMock.expect(study.isCoOrdinatingCenter("Test")).andReturn(false).times(2);
        EasyMock.expect(studyService.getLocalNCIInstituteCode()).andReturn("Test").times(2);
        EasyMock.expect(studySite.getSiteStudyStatus()).andReturn(SiteStudyStatus.APPROVED_FOR_ACTIVTION);
        studySite.activate();
        EasyMock.expect(studySiteDao.merge(studySite)).andReturn(studySite);
        EasyMock.expect(studySite.getHealthcareSite()).andReturn(healthcareSite);
        List<AbstractMutableDomainObject> domainObjects = new ArrayList<AbstractMutableDomainObject>();
        domainObjects.addAll(ids);
        domainObjects.add(healthcareSite);
        studyRepository.handleCoordinatingCenterBroadcast(study, APIName.ACTIVATE_STUDY_SITE,
                        domainObjects);
        replayMocks();
        studyRepository.activateStudySite(ids, "Test");
        verifyMocks();
    }
    
    public void testCloseStudyHosted() throws C3PRCodedException {
        study.closeToAccrual();
        EasyMock.expect(studyDao.getByIdentifiers(ids)).andReturn(list);
        EasyMock.expect(studyDao.merge(study)).andReturn(study);
        EasyMock.expect(study.isMultisite()).andReturn(false);
        replayMocks();
        studyRepository.closeStudy(ids);
        verifyMocks();
    }

    public void testCloseAffiliateStudySite() throws C3PRCodedException {
        EasyMock.expect(studyDao.getByIdentifiers(ids)).andReturn(list);
        EasyMock.expect(study.getStudySite("Test")).andReturn(studySite).times(2);
        studySite.closeToAccrual();
        EasyMock.expect(studySiteDao.merge(studySite)).andReturn(studySite);
        EasyMock.expect(study.isMultisite()).andReturn(true);
        EasyMock.expect(studyService.getLocalNCIInstituteCode()).andReturn("CC");
        EasyMock.expect(studyService.isStudyOrganizationLocal("Test")).andReturn(false);
        EasyMock.expect(study.isCoOrdinatingCenter("CC")).andReturn(true);
        EasyMock.expect(studySite.getHealthcareSite()).andReturn(healthcareSite);
        List<AbstractMutableDomainObject> domainObjects = new ArrayList<AbstractMutableDomainObject>();
        domainObjects.addAll(ids);
        domainObjects.add(healthcareSite);
        studyRepository.handleAffiliateSiteBroadcast("Test", study, APIName.CLOSE_STUDY_SITE,
                        domainObjects);
        replayMocks();
        studyRepository.closeStudySite(ids, "Test");
        verifyMocks();
    }

    public void testBuildAndSave() {
        // building the study
        Study study = studyCreationHelper.getLocalNonRandomizedTratmentWithArmStudy();

        StudySite organization = new StudySite();
        HealthcareSite healthcareSite = buildHealthcareSite();

        Investigator investigator = buildInvestigator();
        List<Investigator> investigators = new ArrayList<Investigator>();
        investigators.add(investigator);
        HealthcareSiteInvestigator healthcareSiteInvestigator = buildHealthcareSiteInvestigator(
                        investigator, healthcareSite);
        StudyInvestigator sInv = buildStudyInvestigator(healthcareSiteInvestigator);
        ArrayList<StudyInvestigator> sInvList = new ArrayList<StudyInvestigator>();
        sInvList.add(sInv);

        organization.setHealthcareSite(healthcareSite);
        organization.setStudyInvestigators(sInvList);
        study.getStudyOrganizations().add(organization);

        OrganizationAssignedIdentifier organizationAssignedIdentifier = new OrganizationAssignedIdentifier();
        organizationAssignedIdentifier.setHealthcareSite(healthcareSite);
        organizationAssignedIdentifier.setValue("oai-001");
        study.getOrganizationAssignedIdentifiers().add(organizationAssignedIdentifier);

        // list of mocks
        EasyMock.expect(healthcareSiteDao.getByNciInstituteCode("hcs-001")).andReturn(
                        healthcareSite);
        EasyMock.expect(investigatorDao.getInvestigatorsByNciInstituteCode("inv-001")).andReturn(
                        investigators);
        EasyMock.expect(
                        healthcareSiteInvestigatorDao.getSiteInvestigator(healthcareSite,
                                        investigator)).andReturn(healthcareSiteInvestigator);
        EasyMock.expect(healthcareSiteDao.getByNciInstituteCode("hcs-001")).andReturn(
                        healthcareSite);
        studyDao.save(study);
        replayMocks();

        try {
            studyRepository.buildAndSave(study);
        }
        catch (Exception e) {
            assertFalse("C3PRCodedException thrown", false);
        }
        verifyMocks();
    }

    public StudyInvestigator buildStudyInvestigator(
                    HealthcareSiteInvestigator healthcareSiteInvestigator) {
        StudyInvestigator sInv = new StudyInvestigator();
        sInv.setHealthcareSiteInvestigator(healthcareSiteInvestigator);
        return sInv;
    }

    public HealthcareSite buildHealthcareSite() {
        HealthcareSite hcs = new HealthcareSite();
        hcs.setNciInstituteCode("hcs-001");
        return hcs;
    }

    public Investigator buildInvestigator() {
        Investigator investigator = new Investigator();
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
    @Transient
    public int getCode(String errortypeString) {
        return Integer.parseInt(this.c3prErrorMessages.getMessage(errortypeString, null, null));
    }
}
