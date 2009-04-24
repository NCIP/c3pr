package edu.duke.cabig.c3pr.grid.studyservice.service;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import edu.duke.cabig.c3pr.domain.CoordinatingCenterStudyStatus;
import edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.SiteStudyStatus;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.SystemAssignedIdentifier;
import edu.duke.cabig.c3pr.grid.studyservice.common.StudyServiceI;
import edu.duke.cabig.c3pr.tools.Configuration;
import edu.duke.cabig.c3pr.utils.StudyDaoTestCaseTemplate;
import edu.duke.cabig.c3pr.utils.XMLUtils;
import edu.duke.cabig.c3pr.xml.XmlMarshaller;
import gov.nih.nci.cabig.ccts.domain.Message;
import gov.nih.nci.cabig.ctms.domain.AbstractMutableDomainObject;

public class C3PRStudyServiceImplTest extends StudyDaoTestCaseTemplate{

	private StudyServiceI studyGridService;
	
	private XMLUtils xmlUtils;
	
	private Study study;
	
	List<AbstractMutableDomainObject> domainObjects= new ArrayList<AbstractMutableDomainObject>();
	
	private static ApplicationContext applicationContext = null;
	
	private Configuration configuration= null;
	
	@Override
	public ApplicationContext getApplicationContext() {
        if (applicationContext == null) {
            applicationContext = new ClassPathXmlApplicationContext(new String[] {"classpath*:applicationContext-grid-c3prStudyService-test.xml"});
        }
        return applicationContext;
	}
	
    @Override
    protected void setUp() throws Exception {
    	super.setUp();
    	studyGridService= (StudyServiceI)getApplicationContext().getBean("gridStudyService");
    	xmlUtils=new XMLUtils((XmlMarshaller)getApplicationContext().getBean("studyXmlUtility"));
    	configuration=(Configuration)getApplicationContext().getBean("configuration");
    	configuration.set(Configuration.MULTISITE_ENABLE, "true");
    	configuration.set(Configuration.LOCAL_NCI_INSTITUTE_CODE, "CRB");
    }

    public void testCreateStudyInvalidArguments(){
    	configuration.set(Configuration.LOCAL_NCI_INSTITUTE_CODE, "site");
    	SystemAssignedIdentifier systemAssignedIdentifier=new SystemAssignedIdentifier();
    	systemAssignedIdentifier.setPrimaryIndicator(true);
    	systemAssignedIdentifier.setSystemName("test");
    	systemAssignedIdentifier.setType("test");
    	systemAssignedIdentifier.setValue("test");
    	domainObjects.add(systemAssignedIdentifier);
    	Message message=xmlUtils.buildMessageFromDomainObjects(domainObjects);
    	try {
			studyGridService.createStudyDefinition(message);
			fail("Should have failed");
		} catch (RemoteException e) {
			e.printStackTrace();
			assertEquals("Wrong Message", "Illegal Argument(s). Make sure there is exactly one study defination in the message.", e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			fail("Should have thrown RemoteException");
		}
    }
    
    public void testCreateStudyCompleteDataEntry(){
    	configuration.set(Configuration.LOCAL_NCI_INSTITUTE_CODE, "site");
    	buildUnsavedStudyMessage();
    	domainObjects.add(study);
    	Message message=xmlUtils.buildMessageFromDomainObjects(domainObjects);
    	try {
			studyGridService.createStudyDefinition(message);
			Study openedStudy=studyRepository.getUniqueStudy(study.getIdentifiers());;
			assertEquals("Wrong short title", study.getShortTitleText(), openedStudy.getShortTitleText());
			assertEquals("Wrong Identifier value", study.getCoordinatingCenterAssignedIdentifier().getValue(), openedStudy.getCoordinatingCenterAssignedIdentifier().getValue());
			assertEquals("Wrong Status", CoordinatingCenterStudyStatus.READY_TO_OPEN, openedStudy.getCoordinatingCenterStudyStatus());
		} catch (Exception e) {
			e.printStackTrace();
			fail("Shouldnt have failed");
		}
    }
    
    public void testCreateAndOpenStudyInvalidArguments(){
    	configuration.set(Configuration.LOCAL_NCI_INSTITUTE_CODE, "site");
    	SystemAssignedIdentifier systemAssignedIdentifier=new SystemAssignedIdentifier();
    	systemAssignedIdentifier.setPrimaryIndicator(true);
    	systemAssignedIdentifier.setSystemName("test");
    	systemAssignedIdentifier.setType("test");
    	systemAssignedIdentifier.setValue("test");
    	domainObjects.add(systemAssignedIdentifier);
    	Message message=xmlUtils.buildMessageFromDomainObjects(domainObjects);
    	try {
			studyGridService.createAndOpenStudy(message);
			fail("Should have failed");
		} catch (RemoteException e) {
			e.printStackTrace();
			assertEquals("Wrong Message", "Illegal Argument(s). Make sure there is exactly one study defination in the message.", e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			fail("Should have thrown RemoteException");
		}
    }
    
    public void testCreateAndOpenStudyCompleteDataEntry(){
    	configuration.set(Configuration.LOCAL_NCI_INSTITUTE_CODE, "site");
    	buildUnsavedStudyMessage();
    	domainObjects.add(study);
    	Message message=xmlUtils.buildMessageFromDomainObjects(domainObjects);
    	try {
			studyGridService.createAndOpenStudy(message);
			Study openedStudy=studyRepository.getUniqueStudy(study.getIdentifiers());;
			assertEquals("Wrong short title", study.getShortTitleText(), openedStudy.getShortTitleText());
			assertEquals("Wrong Identifier value", study.getCoordinatingCenterAssignedIdentifier().getValue(), openedStudy.getCoordinatingCenterAssignedIdentifier().getValue());
			assertEquals("Wrong Status", CoordinatingCenterStudyStatus.OPEN, openedStudy.getCoordinatingCenterStudyStatus());
		} catch (Exception e) {
			e.printStackTrace();
			fail("Shouldnt have failed");
		}
    }
    
    public void testOpenStudyInvalidArguments(){
    	configuration.set(Configuration.LOCAL_NCI_INSTITUTE_CODE, "site");
    	buildUnsavedStudyMessage();
    	domainObjects.add(study);
    	Message message=xmlUtils.buildMessageFromDomainObjects(domainObjects);
    	try {
			studyGridService.openStudy(message);
			fail("Should have failed");
		} catch (RemoteException e) {
			e.printStackTrace();
			assertEquals("Wrong Message", "Illegal Argument(s). Make sure there is atleast one identifier in the message.", e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			fail("Should have thrown RemoteException");
		}
    }
    
    public void testOpenStudyCompleteDataEntry(){
    	configuration.set(Configuration.LOCAL_NCI_INSTITUTE_CODE, "site");
    	createMultisiteStudy(false, false);
    	domainObjects.add(study.getCoordinatingCenterAssignedIdentifier());
    	Message message=xmlUtils.buildMessageFromDomainObjects(domainObjects);
    	try {
			studyGridService.openStudy(message);
			Study openedStudy=studyRepository.getUniqueStudy(study.getIdentifiers());;
			assertEquals("Wrong short title", study.getShortTitleText(), openedStudy.getShortTitleText());
			assertEquals("Wrong Identifier value", study.getCoordinatingCenterAssignedIdentifier().getValue(), openedStudy.getCoordinatingCenterAssignedIdentifier().getValue());
			assertEquals("Wrong Status", CoordinatingCenterStudyStatus.OPEN, openedStudy.getCoordinatingCenterStudyStatus());
		} catch (Exception e) {
			e.printStackTrace();
			fail("Shouldnt have failed");
		}
    }
    
    public void testActivateStudySite(){
    	configuration.set(Configuration.LOCAL_NCI_INSTITUTE_CODE, "Nci duke");
    	createMultisiteStudy(true, false);
    	domainObjects.addAll(study.getIdentifiers());
    	domainObjects.add(study.getStudySite("site"));
    	Message message=xmlUtils.buildMessageFromDomainObjects(domainObjects);
    	try {
			studyGridService.activateStudySite(message);
			Study openedStudy=studyRepository.getUniqueStudy(study.getIdentifiers());
			assertEquals("Wrong short title", study.getShortTitleText(), openedStudy.getShortTitleText());
			assertEquals("Wrong Identifier value", study.getCoordinatingCenterAssignedIdentifier().getValue(), openedStudy.getCoordinatingCenterAssignedIdentifier().getValue());
			assertEquals("Wrong Status", CoordinatingCenterStudyStatus.OPEN, openedStudy.getCoordinatingCenterStudyStatus());
			assertEquals("Wrong Status", SiteStudyStatus.ACTIVE, openedStudy.getStudySite("site").getSiteStudyStatus());
		} catch (Exception e) {
			e.printStackTrace();
			fail("Shouldnt have failed");
		}
    }
    
    public void testCloseStudySiteToAccrual(){
    	configuration.set(Configuration.LOCAL_NCI_INSTITUTE_CODE, "site");
    	createMultisiteStudy(true, true);
    	domainObjects.addAll(study.getIdentifiers());
    	domainObjects.add(study.getStudySite("site").getHealthcareSite());
    	Message message=xmlUtils.buildMessageFromDomainObjects(domainObjects);
    	try {
			studyGridService.closeStudySiteToAccrual(message);
			Study openedStudy=studyRepository.getUniqueStudy(study.getIdentifiers());
			assertEquals("Wrong short title", study.getShortTitleText(), openedStudy.getShortTitleText());
			assertEquals("Wrong Identifier value", study.getCoordinatingCenterAssignedIdentifier().getValue(), openedStudy.getCoordinatingCenterAssignedIdentifier().getValue());
			assertEquals("Wrong Status", CoordinatingCenterStudyStatus.OPEN, openedStudy.getCoordinatingCenterStudyStatus());
			assertEquals("Wrong Status", SiteStudyStatus.CLOSED_TO_ACCRUAL, openedStudy.getStudySite("site").getSiteStudyStatus());
		} catch (Exception e) {
			e.printStackTrace();
			fail("Shouldnt have failed");
		}
    }
    
    public void testCloseStudySiteToAccrualAndTreatment(){
    	configuration.set(Configuration.LOCAL_NCI_INSTITUTE_CODE, "site");
    	createMultisiteStudy(true, true);
    	domainObjects.addAll(study.getIdentifiers());
    	domainObjects.add(study.getStudySite("site").getHealthcareSite());
    	Message message=xmlUtils.buildMessageFromDomainObjects(domainObjects);
    	try {
			studyGridService.closeStudySiteToAccrualAndTreatment(message);
			Study openedStudy=studyRepository.getUniqueStudy(study.getIdentifiers());
			assertEquals("Wrong short title", study.getShortTitleText(), openedStudy.getShortTitleText());
			assertEquals("Wrong Identifier value", study.getCoordinatingCenterAssignedIdentifier().getValue(), openedStudy.getCoordinatingCenterAssignedIdentifier().getValue());
			assertEquals("Wrong Status", CoordinatingCenterStudyStatus.OPEN, openedStudy.getCoordinatingCenterStudyStatus());
			assertEquals("Wrong Status", SiteStudyStatus.CLOSED_TO_ACCRUAL_AND_TREATMENT, openedStudy.getStudySite("site").getSiteStudyStatus());
		} catch (Exception e) {
			e.printStackTrace();
			fail("Shouldnt have failed");
		}
    }
    
    public void testTemporarilyCloseStudySiteToAccrual(){
    	configuration.set(Configuration.LOCAL_NCI_INSTITUTE_CODE, "site");
    	createMultisiteStudy(true, true);
    	domainObjects.addAll(study.getIdentifiers());
    	domainObjects.add(study.getStudySite("site").getHealthcareSite());
    	Message message=xmlUtils.buildMessageFromDomainObjects(domainObjects);
    	try {
			studyGridService.temporarilyCloseStudySiteToAccrual(message);
			Study openedStudy=studyRepository.getUniqueStudy(study.getIdentifiers());
			assertEquals("Wrong short title", study.getShortTitleText(), openedStudy.getShortTitleText());
			assertEquals("Wrong Identifier value", study.getCoordinatingCenterAssignedIdentifier().getValue(), openedStudy.getCoordinatingCenterAssignedIdentifier().getValue());
			assertEquals("Wrong Status", CoordinatingCenterStudyStatus.OPEN, openedStudy.getCoordinatingCenterStudyStatus());
			assertEquals("Wrong Status", SiteStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL, openedStudy.getStudySite("site").getSiteStudyStatus());
		} catch (Exception e) {
			e.printStackTrace();
			fail("Shouldnt have failed");
		}
    }
    
    public void testTemporarilyCloseStudySiteToAccrualAndTreatment(){
    	configuration.set(Configuration.LOCAL_NCI_INSTITUTE_CODE, "site");
    	createMultisiteStudy(true, true);
    	domainObjects.addAll(study.getIdentifiers());
    	domainObjects.add(study.getStudySite("site").getHealthcareSite());
    	Message message=xmlUtils.buildMessageFromDomainObjects(domainObjects);
    	try {
			studyGridService.temporarilyCloseStudySiteToAccrualAndTreatment(message);
			Study openedStudy=studyRepository.getUniqueStudy(study.getIdentifiers());
			assertEquals("Wrong short title", study.getShortTitleText(), openedStudy.getShortTitleText());
			assertEquals("Wrong Identifier value", study.getCoordinatingCenterAssignedIdentifier().getValue(), openedStudy.getCoordinatingCenterAssignedIdentifier().getValue());
			assertEquals("Wrong Status", CoordinatingCenterStudyStatus.OPEN, openedStudy.getCoordinatingCenterStudyStatus());
			assertEquals("Wrong Status", SiteStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_TREATMENT, openedStudy.getStudySite("site").getSiteStudyStatus());
		} catch (Exception e) {
			e.printStackTrace();
			fail("Shouldnt have failed");
		}
    }
    
    public void testCloseStudyToAccrual(){
    	configuration.set(Configuration.LOCAL_NCI_INSTITUTE_CODE, "site");
    	createMultisiteStudy(true, false);
    	domainObjects.addAll(study.getIdentifiers());
    	Message message=xmlUtils.buildMessageFromDomainObjects(domainObjects);
    	try {
			studyGridService.closeStudyToAccrual(message);
			Study openedStudy=studyRepository.getUniqueStudy(study.getIdentifiers());
			assertEquals("Wrong short title", study.getShortTitleText(), openedStudy.getShortTitleText());
			assertEquals("Wrong Identifier value", study.getCoordinatingCenterAssignedIdentifier().getValue(), openedStudy.getCoordinatingCenterAssignedIdentifier().getValue());
			assertEquals("Wrong Status", CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL, openedStudy.getCoordinatingCenterStudyStatus());
		} catch (Exception e) {
			e.printStackTrace();
			fail("Shouldnt have failed");
		}
    }
    
    public void testCloseStudyToAccrualAndTreatment(){
    	configuration.set(Configuration.LOCAL_NCI_INSTITUTE_CODE, "site");
    	createMultisiteStudy(true, false);
    	domainObjects.addAll(study.getIdentifiers());
    	Message message=xmlUtils.buildMessageFromDomainObjects(domainObjects);
    	try {
			studyGridService.closeStudyToAccrualAndTreatment(message);
			Study openedStudy=studyRepository.getUniqueStudy(study.getIdentifiers());
			assertEquals("Wrong short title", study.getShortTitleText(), openedStudy.getShortTitleText());
			assertEquals("Wrong Identifier value", study.getCoordinatingCenterAssignedIdentifier().getValue(), openedStudy.getCoordinatingCenterAssignedIdentifier().getValue());
			assertEquals("Wrong Status", CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL_AND_TREATMENT, openedStudy.getCoordinatingCenterStudyStatus());
		} catch (Exception e) {
			e.printStackTrace();
			fail("Shouldnt have failed");
		}
    }
    
    public void testTemporarilyCloseStudyToAccrual(){
    	configuration.set(Configuration.LOCAL_NCI_INSTITUTE_CODE, "site");
    	createMultisiteStudy(true, false);
    	domainObjects.addAll(study.getIdentifiers());
    	Message message=xmlUtils.buildMessageFromDomainObjects(domainObjects);
    	try {
			studyGridService.temporarilyCloseStudyToAccrual(message);
			Study openedStudy=studyRepository.getUniqueStudy(study.getIdentifiers());
			assertEquals("Wrong short title", study.getShortTitleText(), openedStudy.getShortTitleText());
			assertEquals("Wrong Identifier value", study.getCoordinatingCenterAssignedIdentifier().getValue(), openedStudy.getCoordinatingCenterAssignedIdentifier().getValue());
			assertEquals("Wrong Status", CoordinatingCenterStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL, openedStudy.getCoordinatingCenterStudyStatus());
		} catch (Exception e) {
			e.printStackTrace();
			fail("Shouldnt have failed");
		}
    }
    
    public void testTemporarilyCloseStudyToAccrualAndTreatment(){
    	configuration.set(Configuration.LOCAL_NCI_INSTITUTE_CODE, "site");
    	createMultisiteStudy(true, false);
    	domainObjects.addAll(study.getIdentifiers());
    	Message message=xmlUtils.buildMessageFromDomainObjects(domainObjects);
    	try {
			studyGridService.temporarilyCloseStudyToAccrualAndTreatment(message);
			Study openedStudy=studyRepository.getUniqueStudy(study.getIdentifiers());
			assertEquals("Wrong short title", study.getShortTitleText(), openedStudy.getShortTitleText());
			assertEquals("Wrong Identifier value", study.getCoordinatingCenterAssignedIdentifier().getValue(), openedStudy.getCoordinatingCenterAssignedIdentifier().getValue());
			assertEquals("Wrong Status", CoordinatingCenterStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_TREATMENT, openedStudy.getCoordinatingCenterStudyStatus());
		} catch (Exception e) {
			e.printStackTrace();
			fail("Shouldnt have failed");
		}
    }
    
    public void testAmendStudy(){
    	try {
			studyGridService.amendStudy(new Message());
			fail("Should have failed");
		} catch (RemoteException e) {
			e.printStackTrace();
			assertEquals("Wrong Exception", "Not yet implemented", e.getMessage());
		}
    }
    
    public void testUpdateStudySiteProtocolVersion(){
    	try {
			studyGridService.updateStudySiteProtocolVersion(new Message());
			fail("Should have failed");
		} catch (RemoteException e) {
			e.printStackTrace();
			assertEquals("Wrong Exception", "Not yet implemented", e.getMessage());
		}
    }
    
    public void testGetStudy(){
    	try {
			studyGridService.getStudy(new Message());
			fail("Should have failed");
		} catch (RemoteException e) {
			e.printStackTrace();
			assertEquals("Wrong Exception", "Not yet implemented", e.getMessage());
		}
    }
    
    public void testUpdateStudy(){
    	try {
			studyGridService.updateStudy(new Message());
			fail("Should have failed");
		} catch (RemoteException e) {
			e.printStackTrace();
			assertEquals("Wrong Exception", "Not yet implemented", e.getMessage());
		}
    }
    
    private void buildUnsavedStudyMessage(){
    	buildMultisiteStudy();
        interruptSession();
        study.setShortTitleText("test-short"+new Random().nextInt(1000));
    	study.getIdentifiers().clear();
        OrganizationAssignedIdentifier organizationAssignedIdentifier= study.getOrganizationAssignedIdentifiers().get(0);
        organizationAssignedIdentifier.setHealthcareSite(study.getStudySites().get(0).getHealthcareSite());
        organizationAssignedIdentifier.setType("Coordinating Center Identifier");
        organizationAssignedIdentifier.setValue("test-id"+new Random().nextInt(1000));
    }
    
    private void createMultisiteStudy(boolean open, boolean activateSite){
    	buildMultisiteStudy();
    	study.setCoordinatingCenterStudyStatusInternal(CoordinatingCenterStudyStatus.READY_TO_OPEN);
        OrganizationAssignedIdentifier organizationAssignedIdentifier= study.getOrganizationAssignedIdentifiers().get(0);
        organizationAssignedIdentifier.setHealthcareSite(study.getStudySites().get(0).getHealthcareSite());
        organizationAssignedIdentifier.setType("Coordinating Center Identifier");
        organizationAssignedIdentifier.setValue("test-id"+new Random().nextInt(1000));
        if(open){
        	study.setCoordinatingCenterStudyStatusInternal(CoordinatingCenterStudyStatus.OPEN);
        	study.getStudySites().get(0).setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.OPEN);
        	study.getStudySites().get(1).setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.OPEN);
        	if(activateSite){
        		study.getStudySites().get(0).setSiteStudyStatus(SiteStudyStatus.ACTIVE);
        		study.getStudySites().get(1).setSiteStudyStatus(SiteStudyStatus.ACTIVE);
        	}
        }
        study= studyDao.merge(study);
        interruptSession();
    }
    
    private void buildMultisiteStudy(){
    	study = studyCreationHelper.createBasicStudy();
        study = createDefaultStudyWithDesign(study);
        studyCreationHelper.addStudySiteAsCooordinatingCenter(study);
        addNewStudySite(study, "site");
        study.getStudyCoordinatingCenter().setHostedMode(false);
        study.getStudySites().get(0).setHostedMode(false);
        study.getStudySites().get(1).setHostedMode(false);
    }
}
