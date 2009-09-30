package edu.duke.cabig.c3pr.domain.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.easymock.classextension.EasyMock;
import org.springframework.context.MessageSource;

import edu.duke.cabig.c3pr.AbstractTestCase;
import edu.duke.cabig.c3pr.constants.OrganizationIdentifierTypeEnum;
import edu.duke.cabig.c3pr.constants.RandomizationType;
import edu.duke.cabig.c3pr.constants.ScheduledEpochWorkFlowStatus;
import edu.duke.cabig.c3pr.dao.EpochDao;
import edu.duke.cabig.c3pr.dao.ParticipantDao;
import edu.duke.cabig.c3pr.dao.StratumGroupDao;
import edu.duke.cabig.c3pr.dao.StudySubjectDao;
import edu.duke.cabig.c3pr.domain.Arm;
import edu.duke.cabig.c3pr.domain.Epoch;
import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.ScheduledArm;
import edu.duke.cabig.c3pr.domain.ScheduledEpoch;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.SystemAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.factory.StudySubjectFactory;
import edu.duke.cabig.c3pr.domain.repository.impl.StudySubjectRepositoryImpl;
import edu.duke.cabig.c3pr.exception.C3PRCodedRuntimeException;
import edu.duke.cabig.c3pr.exception.C3PRExceptionHelper;
import edu.duke.cabig.c3pr.service.StudySubjectService;
import edu.duke.cabig.c3pr.utils.ApplicationTestCase;
import edu.duke.cabig.c3pr.utils.IdentifierGenerator;
import edu.duke.cabig.c3pr.utils.StudySubjectCreatorHelper;
import edu.duke.cabig.c3pr.utils.StudyTargetAccrualNotificationEmail;

public class StudySubjectRepositoryTestCase extends AbstractTestCase {
    private StudySubjectDao studySubjectDao;

    private EpochDao epochDao;

    private ParticipantDao participantDao;

    private StratumGroupDao stratumGroupDao;

    private C3PRExceptionHelper exceptionHelper;

    private MessageSource c3prErrorMessages;

    private StudySubjectFactory studySubjectFactory;

    private StudySubjectRepository studySubjectRepository;

    private StudySubject studySubject;

    private StudySubjectService studySubjectService;

    private StudySubjectCreatorHelper studySubjectCreatorHelper;

    private StudyTargetAccrualNotificationEmail notificationEmailer;

    private IdentifierGenerator identifierGenerator ;

    private Logger log = Logger.getLogger(StudySubjectRepositoryTestCase.class.getName());

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        studySubjectDao=registerDaoMockFor(StudySubjectDao.class);
        epochDao=registerDaoMockFor(EpochDao.class);
        stratumGroupDao=registerDaoMockFor(StratumGroupDao.class);
        participantDao=registerDaoMockFor(ParticipantDao.class);
        studySubjectFactory=registerMockFor(StudySubjectFactory.class);
        studySubjectService=registerMockFor(StudySubjectService.class);
        notificationEmailer=registerMockFor(StudyTargetAccrualNotificationEmail.class);
        exceptionHelper=registerMockFor(C3PRExceptionHelper.class);
        c3prErrorMessages=registerMockFor(MessageSource.class);
        StudySubjectRepositoryImpl studySubjectRepositoryImpl=new StudySubjectRepositoryImpl();
        studySubjectRepositoryImpl.setC3prErrorMessages(c3prErrorMessages);
        studySubjectRepositoryImpl.setEpochDao(epochDao);
        studySubjectRepositoryImpl.setExceptionHelper(exceptionHelper);
        studySubjectRepositoryImpl.setStratumGroupDao(stratumGroupDao);
        studySubjectRepositoryImpl.setStudySubjectDao(studySubjectDao);
        studySubjectRepositoryImpl.setStudySubjectFactory(studySubjectFactory);
        studySubjectRepositoryImpl.setParticipantDao(participantDao);
        studySubjectRepositoryImpl.setStudySubjectService(studySubjectService);
        studySubjectRepositoryImpl.setNotificationEmailer(notificationEmailer);
        studySubjectRepository=studySubjectRepositoryImpl;
        studySubject=new StudySubject();
        studySubjectCreatorHelper=new StudySubjectCreatorHelper();
        studySubject.setParticipant(studySubjectCreatorHelper.createNewParticipant());
        identifierGenerator = (IdentifierGenerator) ApplicationTestCase.getDeployedCoreApplicationContext().getBean("identifierGenerator");
    }

    public ScheduledEpoch buildScheduledEpoch(){
    	ScheduledEpoch scheduledTreatmentEpoch = new ScheduledEpoch();
    	ScheduledArm scheduledArm = new ScheduledArm();
    	Epoch treatmentEpoch = new Epoch();
    	treatmentEpoch.setName("Treatmen Epoch 1");
    	Arm arm = new Arm();
    	arm.setId(001);
    	treatmentEpoch.addArm(arm);
    	scheduledArm.setArm(arm);

    	scheduledTreatmentEpoch.getScheduledArms().add(scheduledArm);
    	scheduledTreatmentEpoch.setEpoch(treatmentEpoch);
    	scheduledTreatmentEpoch.setEligibilityIndicator(true);

    	return scheduledTreatmentEpoch;
    }

    public Participant buildParticipant(){
    	Participant participant = new Participant();
    	participant.setFirstName("Johnny");
    	participant.setLastName("Cash");
    	participant.setBirthDate(new Date());
    	participant.setAdministrativeGenderCode("Male");
    	return participant;
    }

	public StudySubjectCreatorHelper getStudySubjectCreatorHelper() {
		return studySubjectCreatorHelper;
	}

	public void setStudySubjectCreatorHelper(
			StudySubjectCreatorHelper studySubjectCreatorHelper) {
		this.studySubjectCreatorHelper = studySubjectCreatorHelper;
	}

	  public void testCreate() throws Exception{
	        ScheduledEpoch scheduledEpochFirst = new ScheduledEpoch();
	        scheduledEpochFirst.setEpoch(studySubjectCreatorHelper.createTestTreatmentEpoch(true));
	        studySubjectRepository.setIdentifierGenerator(identifierGenerator);
	        studySubject.setStudySite(studySubjectCreatorHelper.getLocalNonRandomizedTreatmentWithArmStudySite(true));
	        studySubject.addScheduledEpoch(scheduledEpochFirst);
	        studySubjectCreatorHelper.buildCommandObject(studySubject);
	        studySubjectCreatorHelper.bindEligibility(studySubject);
	        studySubjectCreatorHelper.bindStratification(studySubject);
	        studySubject.getStudySubjectStudyVersion().getStudySubjectConsentVersions().get(0).setInformedConsentSignedDate(new Date());
	        studySubject.setId(1);
	        OrganizationAssignedIdentifier orgIdentifier = new OrganizationAssignedIdentifier();
	        orgIdentifier.setHealthcareSite(studySubject.getStudySite().getHealthcareSite());
	        orgIdentifier.setValue("MRN Value");
	        orgIdentifier.setType(OrganizationIdentifierTypeEnum.MRN);
	        studySubject.getParticipant().addIdentifier(orgIdentifier);
	        EasyMock.expect(studySubjectDao.merge(studySubject)).andReturn(studySubject);
	        EasyMock.expect(studySubjectDao.searchBySubjectAndStudySite((StudySubject)EasyMock.anyObject())).andReturn(new ArrayList<StudySubject>());
	        replayMocks();
	        studySubjectRepository.create(studySubject);
	        assertSame("The study subject should have only 1 system assigned Identifier",1, studySubject.getSystemAssignedIdentifiers().size());
	        verifyMocks();
	    }
	  public void testCreateWithC3PRSystemIdentifier() throws Exception{
	        ScheduledEpoch scheduledEpochFirst = new ScheduledEpoch();
	        scheduledEpochFirst.setEpoch(studySubjectCreatorHelper.createTestTreatmentEpoch(true));
	        studySubject.setStudySite(studySubjectCreatorHelper.getLocalNonRandomizedTreatmentWithArmStudySite(true));
	        studySubject.addScheduledEpoch(scheduledEpochFirst);
	        studySubjectCreatorHelper.buildCommandObject(studySubject);
	        studySubjectCreatorHelper.bindEligibility(studySubject);
	        studySubjectCreatorHelper.bindStratification(studySubject);
	        studySubject.getStudySubjectStudyVersion().getStudySubjectConsentVersions().get(0).setInformedConsentSignedDate(new Date());
	        studySubject.setId(1);
	        SystemAssignedIdentifier sysIdentifier = new SystemAssignedIdentifier();
			sysIdentifier.setSystemName("C3PR");
			sysIdentifier.setType("Study Subject Identifier1");
			sysIdentifier.setValue(studySubject.getStudySite().getStudy().getCoordinatingCenterAssignedIdentifier().getValue() + "_" +studySubject.getParticipant().getPrimaryIdentifierValue());
			studySubject.addIdentifier(sysIdentifier);
	        EasyMock.expect(studySubjectDao.merge(studySubject)).andReturn(studySubject);
	        EasyMock.expect(studySubjectDao.searchBySubjectAndStudySite((StudySubject)EasyMock.anyObject())).andReturn(new ArrayList<StudySubject>());
	        replayMocks();
	        studySubjectRepository.create(studySubject);
	        assertSame("The study subject should have only 1 system assigned Identifier",1, studySubject.getSystemAssignedIdentifiers().size());
	        verifyMocks();
	    }

	  public void testEnrollOnNonRandomizedEpochWithoutArm() throws Exception{
	        ScheduledEpoch scheduledEpochFirst = new ScheduledEpoch();
	        studySubjectRepository.setIdentifierGenerator(identifierGenerator);
	        scheduledEpochFirst.setEpoch(studySubjectCreatorHelper.createTestTreatmentEpochWithoutArm(false));
	        studySubject.setStudySite(studySubjectCreatorHelper.getLocalNonRandomizedTreatmentWithArmStudySite(true));
	        studySubject.addScheduledEpoch(scheduledEpochFirst);
	        studySubjectCreatorHelper.buildCommandObject(studySubject);
	        studySubjectCreatorHelper.bindEligibility(studySubject);
	        studySubjectCreatorHelper.bindStratification(studySubject);
	        studySubject.getStudySubjectStudyVersion().getStudySubjectConsentVersions().get(0).setInformedConsentSignedDate(new Date());
	        studySubject.setId(1);
	        studySubject.getStudySite().getStudy().setId(1);
	        
	        studySubjectDao.save(studySubject);
	        EasyMock.expect(studySubjectDao.getById(studySubject.getId())).andReturn(studySubject);
	        studySubjectDao.initialize(studySubject);
	        studySubjectDao.initialize(studySubject);
	        EasyMock.expect(studySubjectDao.searchBySubjectAndStudySite((StudySubject)EasyMock.anyObject())).andReturn(new ArrayList<StudySubject>());
//	        EasyMock.expect(identifierGenerator.generateOrganizationAssignedIdentifier(studySubject)).andReturn(new OrganizationAssignedIdentifier());

	        studySubjectService.broadcastMessage(studySubject);
	        notificationEmailer.sendEmail(studySubject);

	        replayMocks();
	        studySubjectRepository.enroll(studySubject);
	       
	        verifyMocks();
	    }
	  public void testEnrollOnNonRandomizedEpochWithArm() throws Exception{
	        ScheduledEpoch scheduledEpochFirst = new ScheduledEpoch();
	        studySubjectRepository.setIdentifierGenerator(identifierGenerator);
	        scheduledEpochFirst.setEpoch(studySubjectCreatorHelper.createTestTreatmentEpoch(false));
	        ScheduledArm scheduledArm = new ScheduledArm();
	        scheduledArm.setArm(studySubjectCreatorHelper.createTestTreatmentEpoch(false).getArms().get(0));
	        scheduledEpochFirst.addScheduledArm(scheduledArm);
	        studySubject.setStudySite(studySubjectCreatorHelper.getLocalNonRandomizedTreatmentWithArmStudySite(true));
	        studySubject.addScheduledEpoch(scheduledEpochFirst);
	        studySubjectCreatorHelper.buildCommandObject(studySubject);
	        studySubjectCreatorHelper.bindEligibility(studySubject);
	        studySubjectCreatorHelper.bindStratification(studySubject);
	        studySubject.getStudySubjectStudyVersion().getStudySubjectConsentVersions().get(0).setInformedConsentSignedDate(new Date());
	        studySubject.setId(1);
	        studySubject.getStudySite().getStudy().setId(1);
	        studySubjectDao.save(studySubject);
	        EasyMock.expect(studySubjectDao.getById(studySubject.getId())).andReturn(studySubject);
	        studySubjectDao.initialize(studySubject);
	        studySubjectDao.initialize(studySubject);
	        EasyMock.expect(studySubjectDao.searchBySubjectAndStudySite((StudySubject)EasyMock.anyObject())).andReturn(new ArrayList<StudySubject>());

	        studySubjectService.broadcastMessage(studySubject);
	        notificationEmailer.sendEmail(studySubject);
	        replayMocks();
	        studySubjectRepository.enroll(studySubject);
	        verifyMocks();
	    }

	  public void testEnrollOnPhoneCallRandomizedEpoch() throws Exception{
	        ScheduledEpoch scheduledEpochFirst = new ScheduledEpoch();
	        scheduledEpochFirst.setEpoch(studySubjectCreatorHelper.createTestTreatmentEpoch(true));
	        ScheduledArm scheduledArm = new ScheduledArm();
	        scheduledArm.setArm(studySubjectCreatorHelper.createTestTreatmentEpoch(false).getArms().get(0));
	        scheduledEpochFirst.addScheduledArm(scheduledArm);
	        studySubject.setStudySite(studySubjectCreatorHelper.getLocalRandomizedStudySite(RandomizationType.PHONE_CALL,true));
	        studySubject.addScheduledEpoch(scheduledEpochFirst);
	        studySubjectCreatorHelper.buildCommandObject(studySubject);
	        studySubjectCreatorHelper.bindEligibility(studySubject);
	        studySubjectCreatorHelper.bindStratification(studySubject);
	        studySubject.getStudySubjectStudyVersion().getStudySubjectConsentVersions().get(0).setInformedConsentSignedDate(new Date());
	        studySubject.setId(1);
	        studySubject.getStudySite().getStudy().setId(1);
	        studySubject.getStudySite().getStudy().setId(1);
	        studySubjectDao.save(studySubject);
	        EasyMock.expect(studySubjectDao.getById(studySubject.getId())).andReturn(studySubject);
	        studySubjectDao.initialize(studySubject);
	        studySubjectDao.initialize(studySubject);
	        EasyMock.expect(studySubjectDao.searchBySubjectAndStudySite((StudySubject)EasyMock.anyObject())).andReturn(new ArrayList<StudySubject>());

	        studySubjectService.broadcastMessage(studySubject);
	        notificationEmailer.sendEmail(studySubject);
	        studySubjectRepository.setIdentifierGenerator(identifierGenerator);
	        replayMocks();
	        studySubjectRepository.enroll(studySubject);
	        verifyMocks();
	    }
	  public void testEnrollOnBookRandomizedEpoch() throws Exception{
	        studySubject.setStudySite(studySubjectCreatorHelper.getLocalRandomizedStudySite(RandomizationType.BOOK,true));
	        studySubjectCreatorHelper.addScheduledEnrollingEpochFromStudyEpochs(studySubject);
	        studySubjectCreatorHelper.buildCommandObject(studySubject);
	        studySubjectCreatorHelper.bindEligibility(studySubject);
	        studySubjectCreatorHelper.bindStratification(studySubject);
	        studySubject.getStudySubjectStudyVersion().getStudySubjectConsentVersions().get(0).setInformedConsentSignedDate(new Date());
	        studySubject.setId(1);
	        studySubject.getStudySite().getStudy().setId(1);
	        studySubjectDao.save(studySubject);
	        EasyMock.expect(studySubjectDao.getById(studySubject.getId())).andReturn(studySubject);
	        studySubjectDao.initialize(studySubject);
	        studySubjectDao.initialize(studySubject);
	        EasyMock.expect(epochDao.merge(studySubject.getScheduledEpoch().getEpoch())).andReturn(studySubject.getScheduledEpoch().getEpoch());
	        EasyMock.expect(studySubjectDao.searchBySubjectAndStudySite((StudySubject)EasyMock.anyObject())).andReturn(new ArrayList<StudySubject>());

	        studySubjectService.broadcastMessage(studySubject);
	        notificationEmailer.sendEmail(studySubject);
	        studySubjectRepository.setIdentifierGenerator(identifierGenerator);
	        replayMocks();
	        studySubjectRepository.enroll(studySubject);
	        verifyMocks();
	    }
	  public void testTransferEpoch() throws Exception{
	        studySubject.setStudySite(studySubjectCreatorHelper.getLocalRandomizedStudySite(RandomizationType.BOOK,true));
	        studySubjectCreatorHelper.addScheduledEnrollingEpochFromStudyEpochs(studySubject);
	        studySubjectCreatorHelper.buildCommandObject(studySubject);
	        studySubjectCreatorHelper.bindEligibility(studySubject);
	        studySubjectCreatorHelper.bindStratification(studySubject);
	        studySubject.getStudySubjectStudyVersion().getStudySubjectConsentVersions().get(0).setInformedConsentSignedDate(new Date());
	        studySubject.setId(1);
	        studySubject.getStudySite().getStudy().setId(1);
	        EasyMock.expect(c3prErrorMessages.getMessage("C3PR.EXCEPTION.REGISTRATION.NOT_FOUND_GIVEN_IDENTIFIERS.CODE",null,null)).andReturn("1");
	        EasyMock.expect(exceptionHelper.getRuntimeException(1)).andReturn(new C3PRCodedRuntimeException(1,"Cannot find a registration with the given identifier(s)"));
	        studySubjectDao.save(studySubject);
	        EasyMock.expect(studySubjectDao.getById(studySubject.getId())).andReturn(studySubject);
	        studySubjectDao.initialize(studySubject);
	        studySubjectDao.initialize(studySubject);
	        EasyMock.expect(studySubjectDao.searchBySubjectAndStudySite((StudySubject)EasyMock.anyObject())).andReturn(new ArrayList<StudySubject>());
	        EasyMock.expect(studySubjectDao.getByIdentifiers((List<Identifier>)EasyMock.anyObject())).andReturn(new ArrayList<StudySubject>());
	        EasyMock.expect(epochDao.merge(studySubject.getScheduledEpoch().getEpoch())).andReturn(studySubject.getScheduledEpoch().getEpoch());
	        studySubjectService.broadcastMessage(studySubject);
	        notificationEmailer.sendEmail(studySubject);
	        studySubjectRepository.setIdentifierGenerator(identifierGenerator);
	        replayMocks();
	        studySubject = studySubjectRepository.enroll(studySubject);
	        try{
	        	studySubjectRepository.transferSubject(studySubject.getIdentifiers());
	        	}
	        catch(Exception ex){
	        	 log.error("studySubjectRepositoryImpl.getUniqueStudySubjects() threw exception");
	        	}
	        verifyMocks();
	    }

	  public void testTransferEpoch1() throws Exception{
	        studySubject.setStudySite(studySubjectCreatorHelper.getLocalRandomizedStudySite(RandomizationType.BOOK,true));
	        studySubjectCreatorHelper.addScheduledEnrollingEpochFromStudyEpochs(studySubject);
	        studySubjectCreatorHelper.buildCommandObject(studySubject);
	        studySubjectCreatorHelper.bindEligibility(studySubject);
	        studySubjectCreatorHelper.bindStratification(studySubject);
	        studySubject.getStudySubjectStudyVersion().getStudySubjectConsentVersions().get(0).setInformedConsentSignedDate(new Date());
	        studySubject.setId(1);
	        studySubject.getStudySite().getStudy().setId(1);
	       // studySubjectDao.merge(studySubject);
	        EasyMock.expect(studySubjectDao.merge(studySubject)).andReturn(studySubject).times(1);
	        studySubjectDao.save(studySubject);
	        EasyMock.expect(studySubjectDao.getById(studySubject.getId())).andReturn(studySubject);
	        studySubjectDao.initialize(studySubject);
	        studySubjectDao.initialize(studySubject);
	        EasyMock.expect(studySubjectDao.searchBySubjectAndStudySite((StudySubject)EasyMock.anyObject())).andReturn(new ArrayList<StudySubject>());
	        List<StudySubject> studySubjects = new ArrayList<StudySubject>();
	        studySubjects.add(studySubject);
	        EasyMock.expect(studySubjectDao.getByIdentifiers((List<Identifier>)EasyMock.anyObject())).andReturn(studySubjects);
	        EasyMock.expect(epochDao.merge(studySubject.getScheduledEpoch().getEpoch())).andReturn(studySubject.getScheduledEpoch().getEpoch()).times(2);
	        studySubjectService.broadcastMessage(studySubject);
	        notificationEmailer.sendEmail(studySubject);
	        studySubjectRepository.setIdentifierGenerator(identifierGenerator);
	        replayMocks();
	        studySubject = studySubjectRepository.enroll(studySubject);
	        try{
	        	studySubjectRepository.transferSubject(studySubject.getIdentifiers());
	        	}
	        catch(Exception ex){
	        	 log.error("StudySubject.transfer() threw exception");
	        	}
	        verifyMocks();
	    }

	  public void testTransferEpoch2() throws Exception{
	        studySubject.setStudySite(studySubjectCreatorHelper.getLocalRandomizedStudySiteWith2EnrollingEpochs(RandomizationType.BOOK,true));
	        studySubjectCreatorHelper.addScheduledEnrollingEpochFromStudyEpochs(studySubject);
	        studySubjectCreatorHelper.buildCommandObject(studySubject);
	        studySubjectCreatorHelper.bindEligibility(studySubject);
	        studySubjectCreatorHelper.bindStratification(studySubject);
	        studySubject.getStudySubjectStudyVersion().getStudySubjectConsentVersions().get(0).setInformedConsentSignedDate(new Date());
	        studySubject.setId(1);
	        studySubject.getStudySite().getStudy().setId(1);
	       // studySubjectDao.merge(studySubject);
	        EasyMock.expect(studySubjectDao.merge(studySubject)).andReturn(studySubject).times(1);
	        studySubjectDao.save(studySubject);
	        EasyMock.expect(studySubjectDao.getById(studySubject.getId())).andReturn(studySubject);
	        studySubjectDao.initialize(studySubject);
	        studySubjectDao.initialize(studySubject);
	        EasyMock.expect(studySubjectDao.searchBySubjectAndStudySite((StudySubject)EasyMock.anyObject())).andReturn(new ArrayList<StudySubject>());
	        List<StudySubject> studySubjects = new ArrayList<StudySubject>();
	        studySubjects.add(studySubject);
	        EasyMock.expect(studySubjectDao.getByIdentifiers((List<Identifier>)EasyMock.anyObject())).andReturn(studySubjects);
	        EasyMock.expect(epochDao.merge(studySubject.getScheduledEpoch().getEpoch())).andReturn(studySubject.getScheduledEpoch().getEpoch());
	        studySubjectService.broadcastMessage(studySubject);
	        notificationEmailer.sendEmail(studySubject);
	        studySubjectRepository.setIdentifierGenerator(identifierGenerator);
	        replayMocks();
	        studySubject = studySubjectRepository.enroll(studySubject);
	        studySubjectCreatorHelper.addScheduled2ndNonRandomizedEnrollingEpochFromStudyEpochs(studySubject);
	        studySubjectCreatorHelper.buildCommandObject(studySubject);
	        studySubjectCreatorHelper.bindEligibility(studySubject);
	        studySubjectCreatorHelper.bindStratification(studySubject);
	        studySubjectRepository.transferSubject(studySubject.getIdentifiers());
	        assertSame("The subject should have been successfully transferred",ScheduledEpochWorkFlowStatus.REGISTERED,studySubject.getScheduledEpochs().get(1).getScEpochWorkflowStatus());
	        verifyMocks();
	    }

	public StudySubjectService getStudySubjectService() {
		return studySubjectService;
	}

	public void setStudySubjectService(StudySubjectService studySubjectService) {
		this.studySubjectService = studySubjectService;
	}

	public StudyTargetAccrualNotificationEmail getNotificationEmailer() {
		return notificationEmailer;
	}

	public void setNotificationEmailer(
			StudyTargetAccrualNotificationEmail notificationEmailer) {
		this.notificationEmailer = notificationEmailer;
	}
}
