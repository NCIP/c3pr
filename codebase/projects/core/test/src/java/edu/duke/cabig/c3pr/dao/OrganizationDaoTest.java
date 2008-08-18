package edu.duke.cabig.c3pr.dao;

import static edu.duke.cabig.c3pr.C3PRUseCase.CREATE_ORGANIZATION;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.duke.cabig.c3pr.C3PRUseCases;
import edu.duke.cabig.c3pr.constants.DeliveryMechanismEnum;
import edu.duke.cabig.c3pr.constants.EmailNotificationDeliveryStatusEnum;
import edu.duke.cabig.c3pr.constants.NotificationEventTypeEnum;
import edu.duke.cabig.c3pr.constants.NotificationFrequencyEnum;
import edu.duke.cabig.c3pr.domain.Address;
import edu.duke.cabig.c3pr.domain.ContactMechanism;
import edu.duke.cabig.c3pr.domain.ContactMechanismBasedRecipient;
import edu.duke.cabig.c3pr.domain.ContactMechanismType;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.PlannedNotification;
import edu.duke.cabig.c3pr.domain.RecipientScheduledNotification;
import edu.duke.cabig.c3pr.domain.ResearchStaff;
import edu.duke.cabig.c3pr.domain.RoleBasedRecipient;
import edu.duke.cabig.c3pr.domain.ScheduledNotification;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.UserBasedRecipient;
import edu.duke.cabig.c3pr.utils.ContextDaoTestCase;

@C3PRUseCases( { CREATE_ORGANIZATION })
public class OrganizationDaoTest extends ContextDaoTestCase<OrganizationDao> {
    /**
     * Test for loading an Organization by Id
     * @throws Exception
     */
	
	public static final String MESSAGE_BODY = "You get this message and your domain model is working";
	public static final String TITLE = "Vanguard";
	
    private ResearchStaffDao researchStaffDao = (ResearchStaffDao) getApplicationContext().getBean("researchStaffDao");
    private ScheduledNotificationDao scheduledNotificationDao= (ScheduledNotificationDao) getApplicationContext().getBean("scheduledNotificationDao");
    private PlannedNotificationDao plannedNotificationDao= (PlannedNotificationDao) getApplicationContext().getBean("plannedNotificationDao");
    private RecipientScheduledNotificationDao rsnDao= (RecipientScheduledNotificationDao) getApplicationContext().getBean("recipientScheduledNotificationDao");
    
    public void testGetById() throws Exception {
        HealthcareSite org = getDao().getById(1000);
        assertEquals("Duke Comprehensive Cancer Center", org.getName());
    }

    public void testSaveNewOrganization() {
        Integer savedId;
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
            healthcaresite.setNciInstituteCode("NCI northwestern");
            this.getDao().save(healthcaresite);

            savedId = healthcaresite.getId();
            assertNotNull("The saved organization didn't get an id", savedId);
        }

        interruptSession();
        {
            HealthcareSite loaded = this.getDao().getById(savedId);
            assertNotNull("Could not reload organization with id " + savedId, loaded);
            assertEquals("Wrong name", "Northwestern Memorial Hospital", loaded.getName());
            assertEquals("Wrong city", "Chicago", loaded.getAddress().getCity());
        }
    }

    public void testNumberOfInvestigatorGroups() throws Exception {
        HealthcareSite org = getDao().getById(1000);
        assertEquals("Expected 2 investigator groups", 2, org.getInvestigatorGroups().size());
    }

    public void testAddInvestigatorGroupToHealthcareSite() throws Exception {
        {
            HealthcareSite org = getDao().getById(1000);
            org.getInvestigatorGroups().get(1).setName("Physicians Group");
        }
        interruptSession();
        HealthcareSite loadedOrg = getDao().getById(1000);

        assertEquals("Expected 3 investigator groups", 2, loadedOrg.getInvestigatorGroups().size());
    }
    
    public void testSaveNotificationWithMessageDetailsAndRecepients() throws Exception {
    	{
    		HealthcareSite org = getDao().getById(1000);
			org.getPlannedNotifications().add(buildNotificationWithRecepientsAndMesssageDetails());    		
			this.getDao().save(org);
    	}
    	interruptSession();
    	
    	HealthcareSite loadedOrg = getDao().getById(1000);
    	assertNotNull("Could not reload notifications", loadedOrg.getPlannedNotifications());
        assertEquals("Notification list not the right size", 1, loadedOrg.getPlannedNotifications().size());
        
        assertEquals("Wrong Frequency", NotificationFrequencyEnum.IMMEDIATE, loadedOrg.getPlannedNotifications().get(0).getFrequency());
        assertEquals("Wrong title", OrganizationDaoTest.TITLE, loadedOrg.getPlannedNotifications().get(0).getTitle());
        assertEquals("Wrong message", OrganizationDaoTest.MESSAGE_BODY, loadedOrg.getPlannedNotifications().get(0).getMessage());

        assertEquals("Wrong Event Name", NotificationEventTypeEnum.STUDY_STATUS_CHANGED_EVENT, loadedOrg.getPlannedNotifications().get(0).getEventName());
        assertEquals("Wrong Delivery Mechanism", DeliveryMechanismEnum.EMAIL, loadedOrg.getPlannedNotifications().get(0).getDeliveryMechanism());
        
        assertEquals("Wrong Role", "admin", loadedOrg.getPlannedNotifications().get(0).getRoleBasedRecipient().get(0).getRole());
        assertEquals("Wrong User", "Research Bill", loadedOrg.getPlannedNotifications().get(0).getUserBasedRecipient().get(0).getResearchStaff().getFirstName());
        assertEquals("Wrong contact","vinay.gangoli@semanticbits.com", loadedOrg.getPlannedNotifications().get(0).getContactMechanismBasedRecipient().get(0).getContactMechanism().get(0).getValue());
    }
    
    public PlannedNotification buildNotificationWithRecepientsAndMesssageDetails(){
    	PlannedNotification plannedNotification = new PlannedNotification();
    	
    	plannedNotification.setDeliveryMechanism(DeliveryMechanismEnum.EMAIL);
    	plannedNotification.setEventName(NotificationEventTypeEnum.STUDY_STATUS_CHANGED_EVENT);
    	plannedNotification.setFrequency(NotificationFrequencyEnum.IMMEDIATE);
    	plannedNotification.setMessage(OrganizationDaoTest.MESSAGE_BODY);
    	plannedNotification.setTitle(OrganizationDaoTest.TITLE);
    	plannedNotification.setThreshold(10);

    	UserBasedRecipient ubr = new UserBasedRecipient();
    	ResearchStaff researchStaff = researchStaffDao.getById(1000);
        ubr.setResearchStaff(researchStaff);
        plannedNotification.getUserBasedRecipient().add(ubr);
        
        RoleBasedRecipient rbr = new RoleBasedRecipient();
        rbr.setRole("admin");
        plannedNotification.getRoleBasedRecipient().add(rbr);
        
        ContactMechanism cm = new ContactMechanism();
        cm.setType(ContactMechanismType.EMAIL);
        cm.setValue("vinay.gangoli@semanticbits.com");
        ArrayList <ContactMechanism> cmList  = new ArrayList<ContactMechanism>();
        cmList.add(cm);
        
        ContactMechanismBasedRecipient cmbr = new ContactMechanismBasedRecipient();
        cmbr.setContactMechanism(cmList);
        plannedNotification.getContactMechanismBasedRecipient().add(cmbr);

    	
    	return plannedNotification;
    }
    
    
    public void testSaveScheduledNotification(){
    
    	{
    		PlannedNotification plannedNotification = buildNotificationWithRecepientsAndMesssageDetails();
    		ScheduledNotification scheduledNotification = addScheduledNotification(plannedNotification, "composedMessage");
    		//plannedNotificationDao.getHibernateTemplate().merge(plannedNotification);
    		plannedNotificationDao.getHibernateTemplate().saveOrUpdate(plannedNotification);//merge(plannedNotification);
    	}
    	interruptSession();
	
    	List<RecipientScheduledNotification> rsn = rsnDao.getAll();
    	assertNotNull(rsn);
    	
    }
    
    public ScheduledNotification addScheduledNotification(PlannedNotification plannedNotification, String composedMessage){
    	
    	ScheduledNotification scheduledNotification = new ScheduledNotification();
    	plannedNotification.getScheduledNotification().add(scheduledNotification);
    	scheduledNotification.setDateSent(new Date());
		scheduledNotification.setMessage(composedMessage);
		scheduledNotification.setTitle(plannedNotification.getTitle());
    	RecipientScheduledNotification rsn; 
    	for(RoleBasedRecipient rbr: plannedNotification.getRoleBasedRecipient()){
    		rsn = new RecipientScheduledNotification();
    		rsn.setRecipient(rbr);
    		rsn.setIsRead(Boolean.FALSE);
    		rsn.setScheduledNotification(scheduledNotification);
    		rsn.setDeliveryStatus(EmailNotificationDeliveryStatusEnum.PENDING);
    		scheduledNotification.getRecipientScheduledNotification().add(rsn);
    	}
    	
    	for(UserBasedRecipient ubr: plannedNotification.getUserBasedRecipient()){
    		rsn = new RecipientScheduledNotification();
    		rsn.setRecipient(ubr);
    		rsn.setIsRead(Boolean.FALSE);
    		rsn.setScheduledNotification(scheduledNotification);
    		rsn.setDeliveryStatus(EmailNotificationDeliveryStatusEnum.PENDING);
    		scheduledNotification.getRecipientScheduledNotification().add(rsn);
    	}
    	return scheduledNotification;
    }

}
