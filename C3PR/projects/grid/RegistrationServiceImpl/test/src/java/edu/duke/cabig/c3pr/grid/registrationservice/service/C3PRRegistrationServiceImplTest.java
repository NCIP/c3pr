package edu.duke.cabig.c3pr.grid.registrationservice.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import edu.duke.cabig.c3pr.constants.RegistrationWorkFlowStatus;
import edu.duke.cabig.c3pr.dao.ParticipantDao;
import edu.duke.cabig.c3pr.dao.StudySiteDao;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.grid.registrationservice.common.RegistrationServiceI;
import edu.duke.cabig.c3pr.grid.registrationservice.service.impl.C3PRRegistrationServiceImpl;
import edu.duke.cabig.c3pr.tools.Configuration;
import edu.duke.cabig.c3pr.utils.DaoTestCase;
import edu.duke.cabig.c3pr.utils.StudySubjectCreatorHelper;
import edu.duke.cabig.c3pr.utils.XMLUtils;
import edu.duke.cabig.c3pr.xml.XmlMarshaller;
import gov.nih.nci.cabig.ccts.domain.Message;
import gov.nih.nci.cabig.ctms.domain.AbstractMutableDomainObject;

public class C3PRRegistrationServiceImplTest extends DaoTestCase{

	private static ApplicationContext applicationContext = null;
	
	private StudySubjectCreatorHelper studySubjectCreatorHelper;
	
	private StudySiteDao studySiteDao;
	
	private ParticipantDao participantDao;
	
	private Configuration configuration;
	
	private List<AbstractMutableDomainObject> domainObjects= new ArrayList<AbstractMutableDomainObject>();
	
	private StudySubject studySubject;
	
	private XMLUtils xmlUtils;
	
	private RegistrationServiceI registrationGridService;
	
	
	@Override
	public ApplicationContext getApplicationContext() {
        if (applicationContext == null) {
            applicationContext = new ClassPathXmlApplicationContext(new String[] {"classpath*:applicationContext-grid-c3prRegistrationService-test.xml"});
        }
        return applicationContext;
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		studySubject= new StudySubject();
		studySubjectCreatorHelper= new StudySubjectCreatorHelper();
		studySiteDao= (StudySiteDao)getApplicationContext().getBean("studySiteDao");
		participantDao= (ParticipantDao)getApplicationContext().getBean("participantDao");
		configuration= (Configuration)getApplicationContext().getBean("configuration");
		xmlUtils=new XMLUtils((XmlMarshaller)getApplicationContext().getBean("registrationXmlUtility"));
		registrationGridService=(C3PRRegistrationServiceImpl)getApplicationContext().getBean("c3prRegistrationService");
		configuration.set(Configuration.LOCAL_NCI_INSTITUTE_CODE, "coord");
	}
	
	public void testEnroll(){
		StudySite studySite= studySiteDao.getById(2102);
		studySubject.setStudySite(studySite);
		Participant participant=studySubjectCreatorHelper.createNewParticipant();
		studySubjectCreatorHelper.addMRNIdentifierToSubject(participant, studySite.getHealthcareSite());
		studySubject.setParticipant(participant);
		studySubjectCreatorHelper.addEnrollmentDetails(studySubject);
		studySubjectCreatorHelper.addScheduledEnrollingEpochFromStudyEpochs(studySubject);
		studySubjectCreatorHelper.bindArm(studySubject);
		domainObjects.add(studySubject);
    	Message message=xmlUtils.buildMessageFromDomainObjects(domainObjects);
    	interruptSession();
    	try {
			Message retMessage=registrationGridService.enroll(message);
			List<StudySubject> objects = xmlUtils.getDomainObjectsFromList(StudySubject.class, xmlUtils.getArguments(retMessage));
	        if (objects.size() != 1) {
	            fail("Invaid return");
	        }
	        assertEquals("Wrong value", "1", objects.get(0).getCoOrdinatingCenterIdentifier().getValue());
	        assertEquals("Wrong name", "Arm A", objects.get(0).getScheduledEpoch().getScheduledArm().getArm().getName());
	        interruptSession();
	        studySite= studySiteDao.getById(2102);
	        studySubject= studySite.getStudySubjects().get(0);
	        assertEquals("Wrong status", RegistrationWorkFlowStatus.ENROLLED, studySubject.getRegWorkflowStatus());
		}catch (Exception e) {
			e.printStackTrace();
			fail("Shouldnt have failed");
		}
	}
	
}
