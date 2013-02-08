/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import edu.duke.cabig.c3pr.constants.ConsentingMethod;
import edu.duke.cabig.c3pr.constants.CoordinatingCenterStudyStatus;
import edu.duke.cabig.c3pr.constants.OrganizationIdentifierTypeEnum;
import edu.duke.cabig.c3pr.constants.StudyDataEntryStatus;
import edu.duke.cabig.c3pr.dao.HealthcareSiteDao;
import edu.duke.cabig.c3pr.dao.ParticipantDao;
import edu.duke.cabig.c3pr.dao.ReasonDao;
import edu.duke.cabig.c3pr.dao.RegistryStatusDao;
import edu.duke.cabig.c3pr.dao.StudyDao;
import edu.duke.cabig.c3pr.dao.StudySubjectDao;
import edu.duke.cabig.c3pr.domain.Address;
import edu.duke.cabig.c3pr.domain.Consent;
import edu.duke.cabig.c3pr.domain.ConsentQuestion;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.LocalHealthcareSite;
import edu.duke.cabig.c3pr.domain.LocalStudy;
import edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.PermissibleStudySubjectRegistryStatus;
import edu.duke.cabig.c3pr.domain.RegistryStatus;
import edu.duke.cabig.c3pr.domain.RegistryStatusReason;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyCoordinatingCenter;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.StudySubjectConsentVersion;
import edu.duke.cabig.c3pr.utils.DaoTestCase;

/**
 * JUnit Tests for ParticipantDao
 *
 * @author Priyatam
 * @testType unit
 */
public class MayoSpecificRegistryUseCaseTest extends DaoTestCase {
	/** The dao. */
    private StudyDao studyDao;

    /** The healthcare sitedao. */
    private HealthcareSiteDao healthcareSitedao;

    private RegistryStatusDao registryStatusDao;
    
    private ReasonDao reasonDao;
    
    private ParticipantDao participantDao;

    private StudySubjectDao studySubjectDao;
    
    private StudySubject studySubject;
    private Study study;
    private Consent consent1;
    private Consent consent2;
    private RegistryStatusReason registryStatusReason1;
    private RegistryStatusReason registryStatusReason2;
    private RegistryStatusReason registryStatusReason3;
    private RegistryStatusReason registryStatusReason4;

    /* (non-Javadoc)
     * @see edu.duke.cabig.c3pr.utils.DaoTestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        studyDao = (StudyDao) getApplicationContext().getBean("studyDao");
        healthcareSitedao = (HealthcareSiteDao) getApplicationContext()
        	.getBean("healthcareSiteDao");
        registryStatusDao = (RegistryStatusDao) getApplicationContext()
    	.getBean("registryStatusDao");
        reasonDao = (ReasonDao) getApplicationContext()
    	.getBean("reasonDao");
        participantDao = (ParticipantDao) getApplicationContext().getBean("participantDao");
        studySubjectDao = (StudySubjectDao) getApplicationContext().getBean("studySubjectDao");
    }
    
    private Study createStudyForRegistry() {
        study = new LocalStudy();
        study.setPrecisText("New study");
        study.setShortTitleText("ShortTitleText");
        study.setLongTitleText("LongTitleText");
        study.setPhaseCode("PhaseCode");
        study.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.OPEN);
        study.setDataEntryStatus(StudyDataEntryStatus.COMPLETE);
        study.setTargetAccrualNumber(150);
        study.setType("Type");
        study.setMultiInstitutionIndicator(Boolean.TRUE);
        study.setOriginalIndicator(true);
        
        //add consents
        consent1 = study.getStudyVersion().getConsents().get(0);
        consent1.setConsentingMethods(Arrays.asList(new ConsentingMethod[]{ConsentingMethod.VERBAL, ConsentingMethod.WRITTEN}));
        consent1.setName("general1");
        consent1.setVersion(1);
        consent1.addQuestion(new ConsentQuestion("code1","question1"));
        consent1.addQuestion(new ConsentQuestion("code2","question2"));
        consent2 = study.getStudyVersion().getConsents().get(1);
        consent2.setConsentingMethods(Arrays.asList(new ConsentingMethod[]{ConsentingMethod.VERBAL, ConsentingMethod.WRITTEN}));
        consent2.setName("general2");
        consent2.setVersion(1);
        
        //add mayo as cocenter and study site
        HealthcareSite healthcaresite = new LocalHealthcareSite();
        Address address = new Address();
        address.setCity("Reston");
        address.setCountryCode("USA");
        address.setPostalCode("20191");
        address.setStateCode("VA");
        address.setStreetAddress("12359 Sunrise Valley Dr");
        healthcaresite.setAddress(address);
        healthcaresite.setName("MAYO");
        healthcaresite.setDescriptionText("MAYO");
        healthcaresite.setCtepCode("MN026");
        healthcareSitedao.save(healthcaresite);
        // Cocenter
        StudyCoordinatingCenter studyCoCenter = new StudyCoordinatingCenter();
        study.addStudyOrganization(studyCoCenter);
        studyCoCenter.setHealthcareSite(healthcaresite); 
     // Study Site
        StudySite studySite = new StudySite();
        study.addStudySite(studySite);
        studySite.setHealthcareSite(healthcaresite); 
        studySite.setIrbApprovalDate(new Date());
    
     // Identifiers
        OrganizationAssignedIdentifier id = new OrganizationAssignedIdentifier();
        id.setPrimaryIndicator(true);
        id.setHealthcareSite(healthcaresite);
        id.setValue("MAYO-1");
        id.setType(OrganizationIdentifierTypeEnum.COORDINATING_CENTER_IDENTIFIER);
        study.getIdentifiers().add(id);
        
        study.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.OPEN);
        study.setDataEntryStatus(StudyDataEntryStatus.COMPLETE);

        PermissibleStudySubjectRegistryStatus permissibleStudySubjectRegistryStatus1 = study.getPermissibleStudySubjectRegistryStatuses().get(0);
        permissibleStudySubjectRegistryStatus1.setRegistryStatus(registryStatusDao.getRegistryStatusByCode("Pre-Enrolled"));
        PermissibleStudySubjectRegistryStatus permissibleStudySubjectRegistryStatus2 = study.getPermissibleStudySubjectRegistryStatuses().get(1);
        permissibleStudySubjectRegistryStatus2.setRegistryStatus(registryStatusDao.getRegistryStatusByCode("Enrolled"));
        PermissibleStudySubjectRegistryStatus permissibleStudySubjectRegistryStatus3 = study.getPermissibleStudySubjectRegistryStatuses().get(2);
        permissibleStudySubjectRegistryStatus3.setRegistryStatus(registryStatusDao.getRegistryStatusByCode("Screen Failed"));
        registryStatusReason1 = new RegistryStatusReason("lab1","lab out of range1", reasonDao.getReasonByCode("FAILED INCLUSION"), false);
        registryStatusReason2 = new RegistryStatusReason("lab2","lab out of range2", reasonDao.getReasonByCode("FAILED EXCLUSION"), false);
        permissibleStudySubjectRegistryStatus3.setSecondaryReasons(Arrays.asList(new RegistryStatusReason[]{registryStatusReason1, registryStatusReason2}));
        PermissibleStudySubjectRegistryStatus permissibleStudySubjectRegistryStatus4 = study.getPermissibleStudySubjectRegistryStatuses().get(3);
        permissibleStudySubjectRegistryStatus4.setRegistryStatus(registryStatusDao.getRegistryStatusByCode("Accrued"));
        PermissibleStudySubjectRegistryStatus permissibleStudySubjectRegistryStatus5 = study.getPermissibleStudySubjectRegistryStatuses().get(4);
        permissibleStudySubjectRegistryStatus5.setRegistryStatus(registryStatusDao.getRegistryStatusByCode("Withdrawn"));
        registryStatusReason3 = new RegistryStatusReason("distance","distance", reasonDao.getReasonByCode("UNWILLING"), false);
        registryStatusReason4 = new RegistryStatusReason("schedule","schedule", reasonDao.getReasonByCode("UNWILLING"), false);
        permissibleStudySubjectRegistryStatus5.setSecondaryReasons(Arrays.asList(new RegistryStatusReason[]{registryStatusReason3, registryStatusReason4}));
        PermissibleStudySubjectRegistryStatus permissibleStudySubjectRegistryStatus6 = study.getPermissibleStudySubjectRegistryStatuses().get(5);
        permissibleStudySubjectRegistryStatus6.setRegistryStatus(registryStatusDao.getRegistryStatusByCode("Active Intervention"));
        PermissibleStudySubjectRegistryStatus permissibleStudySubjectRegistryStatus7 = study.getPermissibleStudySubjectRegistryStatuses().get(6);
        permissibleStudySubjectRegistryStatus7.setRegistryStatus(registryStatusDao.getRegistryStatusByCode("Long-Term Followup"));
        PermissibleStudySubjectRegistryStatus permissibleStudySubjectRegistryStatus8 = study.getPermissibleStudySubjectRegistryStatuses().get(7);            
        permissibleStudySubjectRegistryStatus8.setRegistryStatus(registryStatusDao.getRegistryStatusByCode("Observation"));
        PermissibleStudySubjectRegistryStatus permissibleStudySubjectRegistryStatus9 = study.getPermissibleStudySubjectRegistryStatuses().get(8);
        permissibleStudySubjectRegistryStatus9.setRegistryStatus(registryStatusDao.getRegistryStatusByCode("Declined Consent"));
        PermissibleStudySubjectRegistryStatus permissibleStudySubjectRegistryStatus10 = study.getPermissibleStudySubjectRegistryStatuses().get(9);
        permissibleStudySubjectRegistryStatus10.setRegistryStatus(registryStatusDao.getRegistryStatusByCode("Completed"));
        
        studyDao.save(study);
        return study;
    }
    
    private void createStudySubject(){
    	studySubject = new StudySubject();
        Participant participant = participantDao.getById(1001);
        studySubject.setStudySubjectDemographics(participant.createStudySubjectDemographics());
        Study study = createStudyForRegistry();
        studySubject.setStudySite(study.getStudySites().get(0));
    }
    
    public void testScreenFailed(){
    	createStudySubject();
    	
    	// Pre-Enrolled
    	//add consent delivery date
    	StudySubjectConsentVersion studySubjectConsentVersion = studySubject.getStudySubjectStudyVersion().getStudySubjectConsentVersions().get(0);
    	studySubjectConsentVersion.setConsent(consent1);
    	studySubjectConsentVersion.setConsentDeliveryDate(new GregorianCalendar(2006, 02,20).getTime());
    	studySubjectConsentVersion.setConsentPresenter("me");
    	studySubjectDao.save(studySubject);
    	
    	interruptSession();
    	
    	studySubject = studySubjectDao.getById(studySubject.getId());
    	studySubject.updateRegistryStatus("Pre-Enrolled", new GregorianCalendar(2006, 01, 31).getTime(), "some comment", null);
    	studySubject = studySubjectDao.merge(studySubject);
    	
    	interruptSession();
    	
    	studySubject = studySubjectDao.getById(studySubject.getId());
    	
    	assertEquals("Pre-Enrolled", studySubject.getStudySubjectRegistryStatus().getPermissibleStudySubjectRegistryStatus().getRegistryStatus().getCode());
    	assertEquals("some comment", studySubject.getStudySubjectRegistryStatus().getCommentText());
    	assertEquals(1, studySubject.getLatestConsents().size());
    	assertEquals("general1", studySubject.getLatestConsents().get(0).getConsent().getName());
    	assertEquals(new GregorianCalendar(2006, 02,20).getTime(), studySubject.getLatestConsents().get(0).getConsentDeliveryDate());
    	assertEquals("me", studySubject.getLatestConsents().get(0).getConsentPresenter());
    	assertEquals(new GregorianCalendar(2006, 01,31).getTime(), studySubject.getStudySubjectRegistryStatus().getEffectiveDate());
    	
    	//Enrolled
    	interruptSession();
    	studySubject = studySubjectDao.getById(studySubject.getId());
    	//add consent signed
    	studySubjectConsentVersion = studySubject.getStudySubjectStudyVersion().getStudySubjectConsentVersions().get(0);
    	studySubjectConsentVersion.setInformedConsentSignedDate(new GregorianCalendar(2006, 03,20).getTime());
    	studySubjectDao.save(studySubject);
    	
    	interruptSession();
    	
    	studySubject = studySubjectDao.getById(studySubject.getId());
    	studySubject.updateRegistryStatus("Enrolled", new GregorianCalendar(2006, 03,20).getTime(), null, null);
    	studySubject = studySubjectDao.merge(studySubject);
    	
    	interruptSession();
    	
    	studySubject = studySubjectDao.getById(studySubject.getId());
    	
    	assertEquals("Enrolled", studySubject.getStudySubjectRegistryStatus().getPermissibleStudySubjectRegistryStatus().getRegistryStatus().getCode());
    	assertEquals(new GregorianCalendar(2006, 03,20).getTime(), studySubject.getStudySubjectRegistryStatus().getEffectiveDate());
    	assertEquals(2, studySubject.getStudySubjectRegistryStatusHistory().size());
    	assertEquals("Pre-Enrolled", studySubject.getStudySubjectRegistryStatusHistory().get(1).getPermissibleStudySubjectRegistryStatus().getRegistryStatus().getCode());
    	assertEquals(new GregorianCalendar(2006, 01,31).getTime(), studySubject.getStudySubjectRegistryStatusHistory().get(1).getEffectiveDate());
    	assertEquals(new GregorianCalendar(2006, 03,20).getTime(), studySubject.getLatestConsents().get(0).getInformedConsentSignedDate());
    	
    	//Screen Failed
    	interruptSession();
    	studySubject = studySubjectDao.getById(studySubject.getId());
    	
    	//build screen failure reasons
    	List<RegistryStatusReason> reasons = new ArrayList<RegistryStatusReason>();
    	RegistryStatus registryStatus = registryStatusDao.getRegistryStatusByCode("Screen Failed");
    	reasons.add(registryStatus.getPrimaryReason("FAILED INCLUSION"));
    	reasons.add(registryStatus.getPrimaryReason("FAILED EXCLUSION"));
    	reasons.add(registryStatusReason1);
    	reasons.add(registryStatusReason2);
    	
    	studySubject.updateRegistryStatus("Screen Failed", new GregorianCalendar(2006, 05,12).getTime(), null, reasons);
    	studySubject = studySubjectDao.merge(studySubject);
    	
    	interruptSession();
    	
    	studySubject = studySubjectDao.getById(studySubject.getId());
    	
    	assertEquals("Screen Failed", studySubject.getStudySubjectRegistryStatus().getPermissibleStudySubjectRegistryStatus().getRegistryStatus().getCode());
    	assertEquals(new GregorianCalendar(2006, 05,12).getTime(), studySubject.getStudySubjectRegistryStatus().getEffectiveDate());
    	assertEquals(3, studySubject.getStudySubjectRegistryStatusHistory().size());
    	assertEquals("Enrolled", studySubject.getStudySubjectRegistryStatusHistory().get(1).getPermissibleStudySubjectRegistryStatus().getRegistryStatus().getCode());
    	assertEquals("Pre-Enrolled", studySubject.getStudySubjectRegistryStatusHistory().get(2).getPermissibleStudySubjectRegistryStatus().getRegistryStatus().getCode());
    	assertEquals(4, studySubject.getStudySubjectRegistryStatus().getReasons().size());
    }
    
    public void testCompleted(){
    	createStudySubject();
    	
    	// Pre-Enrolled
    	//add consent delivery date
    	StudySubjectConsentVersion studySubjectConsentVersion = studySubject.getStudySubjectStudyVersion().getStudySubjectConsentVersions().get(0);
    	studySubjectConsentVersion.setConsent(consent1);
    	studySubjectConsentVersion.setConsentDeliveryDate(new GregorianCalendar(2006, 02,20).getTime());
    	studySubjectConsentVersion.setConsentPresenter("me");
    	studySubjectDao.save(studySubject);
    	
    	interruptSession();
    	
    	studySubject = studySubjectDao.getById(studySubject.getId());
    	studySubject.updateRegistryStatus("Pre-Enrolled", new GregorianCalendar(2006, 01, 31).getTime(), null, null);
    	studySubject = studySubjectDao.merge(studySubject);
    	
    	interruptSession();
    	
    	studySubject = studySubjectDao.getById(studySubject.getId());
    	
    	assertEquals("Pre-Enrolled", studySubject.getStudySubjectRegistryStatus().getPermissibleStudySubjectRegistryStatus().getRegistryStatus().getCode());
    	assertEquals(1, studySubject.getLatestConsents().size());
    	assertEquals("general1", studySubject.getLatestConsents().get(0).getConsent().getName());
    	assertEquals(new GregorianCalendar(2006, 02,20).getTime(), studySubject.getLatestConsents().get(0).getConsentDeliveryDate());
    	assertEquals("me", studySubject.getLatestConsents().get(0).getConsentPresenter());
    	assertEquals(new GregorianCalendar(2006, 01,31).getTime(), studySubject.getStudySubjectRegistryStatus().getEffectiveDate());
    	
    	//Enrolled
    	interruptSession();
    	studySubject = studySubjectDao.getById(studySubject.getId());
    	//add consent signed
    	studySubjectConsentVersion = studySubject.getStudySubjectStudyVersion().getStudySubjectConsentVersions().get(0);
    	studySubjectConsentVersion.setInformedConsentSignedDate(new GregorianCalendar(2006, 03,20).getTime());
    	studySubjectDao.save(studySubject);
    	
    	interruptSession();
    	
    	studySubject = studySubjectDao.getById(studySubject.getId());
    	studySubject.updateRegistryStatus("Enrolled", new GregorianCalendar(2006, 03,20).getTime(), null, null);
    	studySubject = studySubjectDao.merge(studySubject);
    	
    	interruptSession();
    	
    	studySubject = studySubjectDao.getById(studySubject.getId());
    	
    	assertEquals("Enrolled", studySubject.getStudySubjectRegistryStatus().getPermissibleStudySubjectRegistryStatus().getRegistryStatus().getCode());
    	assertEquals(new GregorianCalendar(2006, 03,20).getTime(), studySubject.getStudySubjectRegistryStatus().getEffectiveDate());
    	assertEquals(2, studySubject.getStudySubjectRegistryStatusHistory().size());
    	assertEquals("Pre-Enrolled", studySubject.getStudySubjectRegistryStatusHistory().get(1).getPermissibleStudySubjectRegistryStatus().getRegistryStatus().getCode());
    	assertEquals(new GregorianCalendar(2006, 01,31).getTime(), studySubject.getStudySubjectRegistryStatusHistory().get(1).getEffectiveDate());
    	assertEquals(new GregorianCalendar(2006, 03,20).getTime(), studySubject.getLatestConsents().get(0).getInformedConsentSignedDate());
    	
    	//Accrued
    	interruptSession();
    	studySubject = studySubjectDao.getById(studySubject.getId());
    	studySubject.updateRegistryStatus("Accrued", new GregorianCalendar(2006, 04,26).getTime(), null, null);
    	studySubject = studySubjectDao.merge(studySubject);
    	
    	interruptSession();
    	
    	studySubject = studySubjectDao.getById(studySubject.getId());
    	
    	assertEquals("Accrued", studySubject.getStudySubjectRegistryStatus().getPermissibleStudySubjectRegistryStatus().getRegistryStatus().getCode());
    	assertEquals(new GregorianCalendar(2006, 04,26).getTime(), studySubject.getStudySubjectRegistryStatus().getEffectiveDate());
    	assertEquals(3, studySubject.getStudySubjectRegistryStatusHistory().size());
    	assertEquals("Enrolled", studySubject.getStudySubjectRegistryStatusHistory().get(1).getPermissibleStudySubjectRegistryStatus().getRegistryStatus().getCode());
    	assertEquals("Pre-Enrolled", studySubject.getStudySubjectRegistryStatusHistory().get(2).getPermissibleStudySubjectRegistryStatus().getRegistryStatus().getCode());
    	
    	// Active Intervention
    	//add new consent delivery date
    	studySubjectConsentVersion = studySubject.getStudySubjectStudyVersion().getStudySubjectConsentVersions().get(1);
    	studySubjectConsentVersion.setConsent(consent2);
    	studySubjectConsentVersion.setConsentDeliveryDate(new GregorianCalendar(2006, 05,01).getTime());
    	studySubjectConsentVersion.setConsentPresenter("me");
    	studySubjectConsentVersion.setInformedConsentSignedDate(new GregorianCalendar(2006, 05,10).getTime());
    	studySubjectDao.save(studySubject);
    	
    	interruptSession();
    	
    	studySubject = studySubjectDao.getById(studySubject.getId());
    	studySubject.updateRegistryStatus("Active Intervention", new GregorianCalendar(2006, 05,10).getTime(), null, null);
    	studySubject = studySubjectDao.merge(studySubject);
    	
    	interruptSession();
    	
    	studySubject = studySubjectDao.getById(studySubject.getId());
    	
    	assertEquals("Active Intervention", studySubject.getStudySubjectRegistryStatus().getPermissibleStudySubjectRegistryStatus().getRegistryStatus().getCode());
    	assertEquals(new GregorianCalendar(2006, 05,10).getTime(), studySubject.getStudySubjectRegistryStatus().getEffectiveDate());
    	assertEquals(4, studySubject.getStudySubjectRegistryStatusHistory().size());
    	assertEquals("Accrued", studySubject.getStudySubjectRegistryStatusHistory().get(1).getPermissibleStudySubjectRegistryStatus().getRegistryStatus().getCode());
    	assertEquals("Enrolled", studySubject.getStudySubjectRegistryStatusHistory().get(2).getPermissibleStudySubjectRegistryStatus().getRegistryStatus().getCode());
    	assertEquals("Pre-Enrolled", studySubject.getStudySubjectRegistryStatusHistory().get(3).getPermissibleStudySubjectRegistryStatus().getRegistryStatus().getCode());
    	assertEquals(2, studySubject.getLatestConsents().size());
    	
    	//Observation
    	interruptSession();
    	studySubject = studySubjectDao.getById(studySubject.getId());
    	studySubject.updateRegistryStatus("Observation", new GregorianCalendar(2007, 01,05).getTime(), null, null);
    	studySubject = studySubjectDao.merge(studySubject);
    	
    	interruptSession();
    	
    	studySubject = studySubjectDao.getById(studySubject.getId());
    	
    	assertEquals("Observation", studySubject.getStudySubjectRegistryStatus().getPermissibleStudySubjectRegistryStatus().getRegistryStatus().getCode());
    	assertEquals(new GregorianCalendar(2007, 01,05).getTime(), studySubject.getStudySubjectRegistryStatus().getEffectiveDate());
    	assertEquals(5, studySubject.getStudySubjectRegistryStatusHistory().size());
    	assertEquals("Active Intervention", studySubject.getStudySubjectRegistryStatusHistory().get(1).getPermissibleStudySubjectRegistryStatus().getRegistryStatus().getCode());
    	assertEquals("Accrued", studySubject.getStudySubjectRegistryStatusHistory().get(2).getPermissibleStudySubjectRegistryStatus().getRegistryStatus().getCode());
    	assertEquals("Enrolled", studySubject.getStudySubjectRegistryStatusHistory().get(3).getPermissibleStudySubjectRegistryStatus().getRegistryStatus().getCode());
    	assertEquals("Pre-Enrolled", studySubject.getStudySubjectRegistryStatusHistory().get(4).getPermissibleStudySubjectRegistryStatus().getRegistryStatus().getCode());
    	
    	//Long-Term Followup
    	interruptSession();
    	studySubject = studySubjectDao.getById(studySubject.getId());
    	studySubject.updateRegistryStatus("Long-Term Followup", new GregorianCalendar(2009, 03,13).getTime(), null, null);
    	studySubject = studySubjectDao.merge(studySubject);
    	
    	interruptSession();
    	
    	studySubject = studySubjectDao.getById(studySubject.getId());
    	
    	assertEquals("Long-Term Followup", studySubject.getStudySubjectRegistryStatus().getPermissibleStudySubjectRegistryStatus().getRegistryStatus().getCode());
    	assertEquals(new GregorianCalendar(2009, 03,13).getTime(), studySubject.getStudySubjectRegistryStatus().getEffectiveDate());
    	assertEquals(6, studySubject.getStudySubjectRegistryStatusHistory().size());
    	assertEquals("Observation", studySubject.getStudySubjectRegistryStatusHistory().get(1).getPermissibleStudySubjectRegistryStatus().getRegistryStatus().getCode());
    	assertEquals("Active Intervention", studySubject.getStudySubjectRegistryStatusHistory().get(2).getPermissibleStudySubjectRegistryStatus().getRegistryStatus().getCode());
    	assertEquals("Accrued", studySubject.getStudySubjectRegistryStatusHistory().get(3).getPermissibleStudySubjectRegistryStatus().getRegistryStatus().getCode());
    	assertEquals("Enrolled", studySubject.getStudySubjectRegistryStatusHistory().get(4).getPermissibleStudySubjectRegistryStatus().getRegistryStatus().getCode());
    	assertEquals("Pre-Enrolled", studySubject.getStudySubjectRegistryStatusHistory().get(5).getPermissibleStudySubjectRegistryStatus().getRegistryStatus().getCode());
    	
    	//Completed
    	interruptSession();
    	studySubject = studySubjectDao.getById(studySubject.getId());
    	
    	//build screen failure reasons
    	List<RegistryStatusReason> reasons = new ArrayList<RegistryStatusReason>();
    	RegistryStatus registryStatus = registryStatusDao.getRegistryStatusByCode("Completed");
    	reasons.add(registryStatus.getPrimaryReason("DIED"));
    	
    	studySubject.updateRegistryStatus("Completed", new GregorianCalendar(2010, 07,22).getTime(), null, reasons);
    	studySubject = studySubjectDao.merge(studySubject);
    	
    	interruptSession();
    	
    	studySubject = studySubjectDao.getById(studySubject.getId());
    	
    	assertEquals("Completed", studySubject.getStudySubjectRegistryStatus().getPermissibleStudySubjectRegistryStatus().getRegistryStatus().getCode());
    	assertEquals(new GregorianCalendar(2010, 07,22).getTime(), studySubject.getStudySubjectRegistryStatus().getEffectiveDate());
    	assertEquals(7, studySubject.getStudySubjectRegistryStatusHistory().size());
    	assertEquals("Long-Term Followup", studySubject.getStudySubjectRegistryStatusHistory().get(1).getPermissibleStudySubjectRegistryStatus().getRegistryStatus().getCode());
    	assertEquals("Observation", studySubject.getStudySubjectRegistryStatusHistory().get(2).getPermissibleStudySubjectRegistryStatus().getRegistryStatus().getCode());
    	assertEquals("Active Intervention", studySubject.getStudySubjectRegistryStatusHistory().get(3).getPermissibleStudySubjectRegistryStatus().getRegistryStatus().getCode());
    	assertEquals("Accrued", studySubject.getStudySubjectRegistryStatusHistory().get(4).getPermissibleStudySubjectRegistryStatus().getRegistryStatus().getCode());
    	assertEquals("Enrolled", studySubject.getStudySubjectRegistryStatusHistory().get(5).getPermissibleStudySubjectRegistryStatus().getRegistryStatus().getCode());
    	assertEquals("Pre-Enrolled", studySubject.getStudySubjectRegistryStatusHistory().get(6).getPermissibleStudySubjectRegistryStatus().getRegistryStatus().getCode());
    	assertEquals(1, studySubject.getStudySubjectRegistryStatus().getReasons().size());
    }
    
    public void testWithdrawn(){
    	createStudySubject();
    	
    	// Pre-Enrolled
    	//add consent delivery date
    	StudySubjectConsentVersion studySubjectConsentVersion = studySubject.getStudySubjectStudyVersion().getStudySubjectConsentVersions().get(0);
    	studySubjectConsentVersion.setConsent(consent1);
    	studySubjectConsentVersion.setConsentDeliveryDate(new GregorianCalendar(2006, 02,20).getTime());
    	studySubjectConsentVersion.setConsentPresenter("me");
    	studySubjectDao.save(studySubject);
    	
    	interruptSession();
    	
    	studySubject = studySubjectDao.getById(studySubject.getId());
    	studySubject.updateRegistryStatus("Pre-Enrolled", new GregorianCalendar(2006, 01, 31).getTime(), null, null);
    	studySubject = studySubjectDao.merge(studySubject);
    	
    	interruptSession();
    	
    	studySubject = studySubjectDao.getById(studySubject.getId());
    	
    	assertEquals("Pre-Enrolled", studySubject.getStudySubjectRegistryStatus().getPermissibleStudySubjectRegistryStatus().getRegistryStatus().getCode());
    	assertEquals(1, studySubject.getLatestConsents().size());
    	assertEquals("general1", studySubject.getLatestConsents().get(0).getConsent().getName());
    	assertEquals(new GregorianCalendar(2006, 02,20).getTime(), studySubject.getLatestConsents().get(0).getConsentDeliveryDate());
    	assertEquals("me", studySubject.getLatestConsents().get(0).getConsentPresenter());
    	assertEquals(new GregorianCalendar(2006, 01,31).getTime(), studySubject.getStudySubjectRegistryStatus().getEffectiveDate());
    	
    	//Enrolled
    	interruptSession();
    	studySubject = studySubjectDao.getById(studySubject.getId());
    	//add consent signed
    	studySubjectConsentVersion = studySubject.getStudySubjectStudyVersion().getStudySubjectConsentVersions().get(0);
    	studySubjectConsentVersion.setInformedConsentSignedDate(new GregorianCalendar(2006, 03,20).getTime());
    	studySubjectDao.save(studySubject);
    	
    	interruptSession();
    	
    	studySubject = studySubjectDao.getById(studySubject.getId());
    	studySubject.updateRegistryStatus("Enrolled", new GregorianCalendar(2006, 03,20).getTime(), null, null);
    	studySubject = studySubjectDao.merge(studySubject);
    	
    	interruptSession();
    	
    	studySubject = studySubjectDao.getById(studySubject.getId());
    	
    	assertEquals("Enrolled", studySubject.getStudySubjectRegistryStatus().getPermissibleStudySubjectRegistryStatus().getRegistryStatus().getCode());
    	assertEquals(new GregorianCalendar(2006, 03,20).getTime(), studySubject.getStudySubjectRegistryStatus().getEffectiveDate());
    	assertEquals(2, studySubject.getStudySubjectRegistryStatusHistory().size());
    	assertEquals("Pre-Enrolled", studySubject.getStudySubjectRegistryStatusHistory().get(1).getPermissibleStudySubjectRegistryStatus().getRegistryStatus().getCode());
    	assertEquals(new GregorianCalendar(2006, 01,31).getTime(), studySubject.getStudySubjectRegistryStatusHistory().get(1).getEffectiveDate());
    	assertEquals(new GregorianCalendar(2006, 03,20).getTime(), studySubject.getLatestConsents().get(0).getInformedConsentSignedDate());
    	
    	//Accrued
    	interruptSession();
    	studySubject = studySubjectDao.getById(studySubject.getId());
    	studySubject.updateRegistryStatus("Accrued", new GregorianCalendar(2006, 04,26).getTime(), null, null);
    	studySubject = studySubjectDao.merge(studySubject);
    	
    	interruptSession();
    	
    	studySubject = studySubjectDao.getById(studySubject.getId());
    	
    	assertEquals("Accrued", studySubject.getStudySubjectRegistryStatus().getPermissibleStudySubjectRegistryStatus().getRegistryStatus().getCode());
    	assertEquals(new GregorianCalendar(2006, 04,26).getTime(), studySubject.getStudySubjectRegistryStatus().getEffectiveDate());
    	assertEquals(3, studySubject.getStudySubjectRegistryStatusHistory().size());
    	assertEquals("Enrolled", studySubject.getStudySubjectRegistryStatusHistory().get(1).getPermissibleStudySubjectRegistryStatus().getRegistryStatus().getCode());
    	assertEquals("Pre-Enrolled", studySubject.getStudySubjectRegistryStatusHistory().get(2).getPermissibleStudySubjectRegistryStatus().getRegistryStatus().getCode());
    	
    	// Active Intervention
    	//add new consent delivery date
    	studySubjectConsentVersion = studySubject.getStudySubjectStudyVersion().getStudySubjectConsentVersions().get(1);
    	studySubjectConsentVersion.setConsent(consent2);
    	studySubjectConsentVersion.setConsentDeliveryDate(new GregorianCalendar(2006, 05,01).getTime());
    	studySubjectConsentVersion.setConsentPresenter("me");
    	studySubjectConsentVersion.setInformedConsentSignedDate(new GregorianCalendar(2006, 05,10).getTime());
    	studySubjectDao.save(studySubject);
    	
    	interruptSession();
    	
    	studySubject = studySubjectDao.getById(studySubject.getId());
    	studySubject.updateRegistryStatus("Active Intervention", new GregorianCalendar(2006, 05,10).getTime(), null, null);
    	studySubject = studySubjectDao.merge(studySubject);
    	
    	interruptSession();
    	
    	studySubject = studySubjectDao.getById(studySubject.getId());
    	
    	assertEquals("Active Intervention", studySubject.getStudySubjectRegistryStatus().getPermissibleStudySubjectRegistryStatus().getRegistryStatus().getCode());
    	assertEquals(new GregorianCalendar(2006, 05,10).getTime(), studySubject.getStudySubjectRegistryStatus().getEffectiveDate());
    	assertEquals(4, studySubject.getStudySubjectRegistryStatusHistory().size());
    	assertEquals("Accrued", studySubject.getStudySubjectRegistryStatusHistory().get(1).getPermissibleStudySubjectRegistryStatus().getRegistryStatus().getCode());
    	assertEquals("Enrolled", studySubject.getStudySubjectRegistryStatusHistory().get(2).getPermissibleStudySubjectRegistryStatus().getRegistryStatus().getCode());
    	assertEquals("Pre-Enrolled", studySubject.getStudySubjectRegistryStatusHistory().get(3).getPermissibleStudySubjectRegistryStatus().getRegistryStatus().getCode());
    	assertEquals(2, studySubject.getLatestConsents().size());
    	
    	//Observation
    	interruptSession();
    	studySubject = studySubjectDao.getById(studySubject.getId());
    	studySubject.updateRegistryStatus("Observation", new GregorianCalendar(2007, 01,05).getTime(), null, null);
    	studySubject = studySubjectDao.merge(studySubject);
    	
    	interruptSession();
    	
    	studySubject = studySubjectDao.getById(studySubject.getId());
    	
    	assertEquals("Observation", studySubject.getStudySubjectRegistryStatus().getPermissibleStudySubjectRegistryStatus().getRegistryStatus().getCode());
    	assertEquals(new GregorianCalendar(2007, 01,05).getTime(), studySubject.getStudySubjectRegistryStatus().getEffectiveDate());
    	assertEquals(5, studySubject.getStudySubjectRegistryStatusHistory().size());
    	assertEquals("Active Intervention", studySubject.getStudySubjectRegistryStatusHistory().get(1).getPermissibleStudySubjectRegistryStatus().getRegistryStatus().getCode());
    	assertEquals("Accrued", studySubject.getStudySubjectRegistryStatusHistory().get(2).getPermissibleStudySubjectRegistryStatus().getRegistryStatus().getCode());
    	assertEquals("Enrolled", studySubject.getStudySubjectRegistryStatusHistory().get(3).getPermissibleStudySubjectRegistryStatus().getRegistryStatus().getCode());
    	assertEquals("Pre-Enrolled", studySubject.getStudySubjectRegistryStatusHistory().get(4).getPermissibleStudySubjectRegistryStatus().getRegistryStatus().getCode());
    	
    	//Long-Term Followup
    	interruptSession();
    	studySubject = studySubjectDao.getById(studySubject.getId());
    	studySubject.updateRegistryStatus("Long-Term Followup", new GregorianCalendar(2009, 03,13).getTime(), null, null);
    	studySubject = studySubjectDao.merge(studySubject);
    	
    	interruptSession();
    	
    	studySubject = studySubjectDao.getById(studySubject.getId());
    	
    	assertEquals("Long-Term Followup", studySubject.getStudySubjectRegistryStatus().getPermissibleStudySubjectRegistryStatus().getRegistryStatus().getCode());
    	assertEquals(new GregorianCalendar(2009, 03,13).getTime(), studySubject.getStudySubjectRegistryStatus().getEffectiveDate());
    	assertEquals(6, studySubject.getStudySubjectRegistryStatusHistory().size());
    	assertEquals("Observation", studySubject.getStudySubjectRegistryStatusHistory().get(1).getPermissibleStudySubjectRegistryStatus().getRegistryStatus().getCode());
    	assertEquals("Active Intervention", studySubject.getStudySubjectRegistryStatusHistory().get(2).getPermissibleStudySubjectRegistryStatus().getRegistryStatus().getCode());
    	assertEquals("Accrued", studySubject.getStudySubjectRegistryStatusHistory().get(3).getPermissibleStudySubjectRegistryStatus().getRegistryStatus().getCode());
    	assertEquals("Enrolled", studySubject.getStudySubjectRegistryStatusHistory().get(4).getPermissibleStudySubjectRegistryStatus().getRegistryStatus().getCode());
    	assertEquals("Pre-Enrolled", studySubject.getStudySubjectRegistryStatusHistory().get(5).getPermissibleStudySubjectRegistryStatus().getRegistryStatus().getCode());
    	
    	//Withdrawn
    	interruptSession();
    	studySubject = studySubjectDao.getById(studySubject.getId());
    	
    	//build screen failure reasons
    	List<RegistryStatusReason> reasons = new ArrayList<RegistryStatusReason>();
    	RegistryStatus registryStatus = registryStatusDao.getRegistryStatusByCode("Withdrawn");
    	reasons.add(registryStatus.getPrimaryReason("UNWILLING"));
    	reasons.add(registryStatusReason3);
    	reasons.add(registryStatusReason4);
    	
    	studySubject.updateRegistryStatus("Withdrawn", new GregorianCalendar(2010, 07,22).getTime(), null, reasons);
    	studySubject = studySubjectDao.merge(studySubject);
    	
    	interruptSession();
    	
    	studySubject = studySubjectDao.getById(studySubject.getId());
    	
    	assertEquals("Withdrawn", studySubject.getStudySubjectRegistryStatus().getPermissibleStudySubjectRegistryStatus().getRegistryStatus().getCode());
    	assertEquals(new GregorianCalendar(2010, 07,22).getTime(), studySubject.getStudySubjectRegistryStatus().getEffectiveDate());
    	assertEquals(7, studySubject.getStudySubjectRegistryStatusHistory().size());
    	assertEquals("Long-Term Followup", studySubject.getStudySubjectRegistryStatusHistory().get(1).getPermissibleStudySubjectRegistryStatus().getRegistryStatus().getCode());
    	assertEquals("Observation", studySubject.getStudySubjectRegistryStatusHistory().get(2).getPermissibleStudySubjectRegistryStatus().getRegistryStatus().getCode());
    	assertEquals("Active Intervention", studySubject.getStudySubjectRegistryStatusHistory().get(3).getPermissibleStudySubjectRegistryStatus().getRegistryStatus().getCode());
    	assertEquals("Accrued", studySubject.getStudySubjectRegistryStatusHistory().get(4).getPermissibleStudySubjectRegistryStatus().getRegistryStatus().getCode());
    	assertEquals("Enrolled", studySubject.getStudySubjectRegistryStatusHistory().get(5).getPermissibleStudySubjectRegistryStatus().getRegistryStatus().getCode());
    	assertEquals("Pre-Enrolled", studySubject.getStudySubjectRegistryStatusHistory().get(6).getPermissibleStudySubjectRegistryStatus().getRegistryStatus().getCode());
    	assertEquals(3, studySubject.getStudySubjectRegistryStatus().getReasons().size());
    }
    
    public void testDeclinedConsent(){
    	createStudySubject();
    	
    	// Pre-Enrolled
    	//add consent delivery date
    	StudySubjectConsentVersion studySubjectConsentVersion = studySubject.getStudySubjectStudyVersion().getStudySubjectConsentVersions().get(0);
    	studySubjectConsentVersion.setConsent(consent1);
    	studySubjectConsentVersion.setConsentDeliveryDate(new GregorianCalendar(2006, 02,20).getTime());
    	studySubjectConsentVersion.setConsentPresenter("me");
    	studySubjectDao.save(studySubject);
    	
    	interruptSession();
    	
    	studySubject = studySubjectDao.getById(studySubject.getId());
    	studySubject.updateRegistryStatus("Pre-Enrolled", new GregorianCalendar(2006, 01, 31).getTime(), null, null);
    	studySubject = studySubjectDao.merge(studySubject);
    	
    	interruptSession();
    	
    	studySubject = studySubjectDao.getById(studySubject.getId());
    	
    	assertEquals("Pre-Enrolled", studySubject.getStudySubjectRegistryStatus().getPermissibleStudySubjectRegistryStatus().getRegistryStatus().getCode());
    	assertEquals(1, studySubject.getLatestConsents().size());
    	assertEquals("general1", studySubject.getLatestConsents().get(0).getConsent().getName());
    	assertEquals(new GregorianCalendar(2006, 02,20).getTime(), studySubject.getLatestConsents().get(0).getConsentDeliveryDate());
    	assertEquals("me", studySubject.getLatestConsents().get(0).getConsentPresenter());
    	assertEquals(new GregorianCalendar(2006, 01,31).getTime(), studySubject.getStudySubjectRegistryStatus().getEffectiveDate());
    	
    	//Enrolled
    	interruptSession();
    	studySubject = studySubjectDao.getById(studySubject.getId());
    	//add consent signed
    	studySubjectConsentVersion = studySubject.getStudySubjectStudyVersion().getStudySubjectConsentVersions().get(0);
    	studySubjectConsentVersion.setInformedConsentSignedDate(new GregorianCalendar(2006, 03,20).getTime());
    	studySubjectDao.save(studySubject);
    	
    	interruptSession();
    	
    	studySubject = studySubjectDao.getById(studySubject.getId());
    	studySubject.updateRegistryStatus("Enrolled", new GregorianCalendar(2006, 03,20).getTime(), null, null);
    	studySubject = studySubjectDao.merge(studySubject);
    	
    	interruptSession();
    	
    	studySubject = studySubjectDao.getById(studySubject.getId());
    	
    	assertEquals("Enrolled", studySubject.getStudySubjectRegistryStatus().getPermissibleStudySubjectRegistryStatus().getRegistryStatus().getCode());
    	assertEquals(new GregorianCalendar(2006, 03,20).getTime(), studySubject.getStudySubjectRegistryStatus().getEffectiveDate());
    	assertEquals(2, studySubject.getStudySubjectRegistryStatusHistory().size());
    	assertEquals("Pre-Enrolled", studySubject.getStudySubjectRegistryStatusHistory().get(1).getPermissibleStudySubjectRegistryStatus().getRegistryStatus().getCode());
    	assertEquals(new GregorianCalendar(2006, 01,31).getTime(), studySubject.getStudySubjectRegistryStatusHistory().get(1).getEffectiveDate());
    	assertEquals(new GregorianCalendar(2006, 03,20).getTime(), studySubject.getLatestConsents().get(0).getInformedConsentSignedDate());
    	
    	//Accrued
    	interruptSession();
    	studySubject = studySubjectDao.getById(studySubject.getId());
    	studySubject.updateRegistryStatus("Accrued", new GregorianCalendar(2006, 04,26).getTime(), null, null);
    	studySubject = studySubjectDao.merge(studySubject);
    	
    	interruptSession();
    	
    	studySubject = studySubjectDao.getById(studySubject.getId());
    	
    	assertEquals("Accrued", studySubject.getStudySubjectRegistryStatus().getPermissibleStudySubjectRegistryStatus().getRegistryStatus().getCode());
    	assertEquals(new GregorianCalendar(2006, 04,26).getTime(), studySubject.getStudySubjectRegistryStatus().getEffectiveDate());
    	assertEquals(3, studySubject.getStudySubjectRegistryStatusHistory().size());
    	assertEquals("Enrolled", studySubject.getStudySubjectRegistryStatusHistory().get(1).getPermissibleStudySubjectRegistryStatus().getRegistryStatus().getCode());
    	assertEquals("Pre-Enrolled", studySubject.getStudySubjectRegistryStatusHistory().get(2).getPermissibleStudySubjectRegistryStatus().getRegistryStatus().getCode());
    	
    	// Declined Consent
    	//add new consent delivery date
    	studySubjectConsentVersion = studySubject.getStudySubjectStudyVersion().getStudySubjectConsentVersions().get(1);
    	studySubjectConsentVersion.setConsent(consent2);
    	studySubjectConsentVersion.setConsentDeliveryDate(new GregorianCalendar(2006, 05,01).getTime());
    	studySubjectConsentVersion.setConsentPresenter("me");
    	studySubjectDao.save(studySubject);
    	
    	interruptSession();
    	
    	studySubject = studySubjectDao.getById(studySubject.getId());
    	studySubject.updateRegistryStatus("Declined Consent", new GregorianCalendar(2006, 05,10).getTime(), null, null);
    	studySubject = studySubjectDao.merge(studySubject);
    	
    	interruptSession();
    	
    	studySubject = studySubjectDao.getById(studySubject.getId());
    	
    	assertEquals("Declined Consent", studySubject.getStudySubjectRegistryStatus().getPermissibleStudySubjectRegistryStatus().getRegistryStatus().getCode());
    	assertEquals(new GregorianCalendar(2006, 05,10).getTime(), studySubject.getStudySubjectRegistryStatus().getEffectiveDate());
    	assertEquals(4, studySubject.getStudySubjectRegistryStatusHistory().size());
    	assertEquals("Accrued", studySubject.getStudySubjectRegistryStatusHistory().get(1).getPermissibleStudySubjectRegistryStatus().getRegistryStatus().getCode());
    	assertEquals("Enrolled", studySubject.getStudySubjectRegistryStatusHistory().get(2).getPermissibleStudySubjectRegistryStatus().getRegistryStatus().getCode());
    	assertEquals("Pre-Enrolled", studySubject.getStudySubjectRegistryStatusHistory().get(3).getPermissibleStudySubjectRegistryStatus().getRegistryStatus().getCode());
    	assertEquals(2, studySubject.getLatestConsents().size());
    	
    }
}
