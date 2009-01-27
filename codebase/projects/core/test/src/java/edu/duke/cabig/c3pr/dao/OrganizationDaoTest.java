package edu.duke.cabig.c3pr.dao;

import static edu.duke.cabig.c3pr.C3PRUseCase.CREATE_ORGANIZATION;

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
import edu.duke.cabig.c3pr.domain.EndPointConnectionProperty;
import edu.duke.cabig.c3pr.domain.EndPointType;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.HealthcareSiteInvestigator;
import edu.duke.cabig.c3pr.domain.Investigator;
import edu.duke.cabig.c3pr.domain.InvestigatorGroup;
import edu.duke.cabig.c3pr.domain.Organization;
import edu.duke.cabig.c3pr.domain.PlannedNotification;
import edu.duke.cabig.c3pr.domain.RecipientScheduledNotification;
import edu.duke.cabig.c3pr.domain.ResearchStaff;
import edu.duke.cabig.c3pr.domain.RoleBasedRecipient;
import edu.duke.cabig.c3pr.domain.ScheduledNotification;
import edu.duke.cabig.c3pr.domain.SiteInvestigatorGroupAffiliation;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.UserBasedRecipient;
import edu.duke.cabig.c3pr.utils.ContextDaoTestCase;

/**
 * Test for loading an Organization by Id
 * @throws Exception
 */
@C3PRUseCases( { CREATE_ORGANIZATION })
public class OrganizationDaoTest extends ContextDaoTestCase<OrganizationDao> {
	
	public static final String MESSAGE_BODY = "You get this message and your domain model is working";
	public static final String TITLE = "Vanguard";
	
    private ResearchStaffDao researchStaffDao;
    private PlannedNotificationDao plannedNotificationDao;
    private InvestigatorGroupDao investigatorGroupDao;
    private HealthcareSiteInvestigatorDao healthcareSiteInvestigatorDao;
    private HealthcareSiteDao healthcareSiteDao;
    
    public OrganizationDaoTest() {
    	researchStaffDao = (ResearchStaffDao) getApplicationContext().getBean("researchStaffDao");
        plannedNotificationDao= (PlannedNotificationDao) getApplicationContext().getBean("plannedNotificationDao");
        investigatorGroupDao = (InvestigatorGroupDao) getApplicationContext().getBean("investigatorGroupDao");
        healthcareSiteInvestigatorDao = (HealthcareSiteInvestigatorDao) getApplicationContext().getBean("healthcareSiteInvestigatorDao");
        healthcareSiteDao = (HealthcareSiteDao) getApplicationContext().getBean("healthcareSiteDao");
	}
    /**
     * Test get by id.
     * 
     * @throws Exception the exception
     */
    public void testGetById() throws Exception {
        HealthcareSite org = getDao().getById(1000);
        assertEquals("Duke Comprehensive Cancer Center", org.getName());
    }

    /**
     * Test get by nci identifier.
     * 
     * @throws Exception the exception
     */
    public void testGetByNciIdentifier() throws Exception {
        List<HealthcareSite> orgList = getDao().getByNciIdentifier("du code");
        assertEquals("Duke Comprehensive Cancer Center", orgList.get(0).getName());
    }
    
    
    /**
     * Test search by example with wild card true.
     */
    public void testSearchByExampleWithWildCardTrue(){
    	//HealthcareSite org = getDao().getById(1000);
    	HealthcareSite org  = new HealthcareSite();
    	org.setNciInstituteCode("du code");
    	org.setName("Duke");
    	List<HealthcareSite> hcsList = getDao().searchByExample(org, true);
    	assertNotNull(hcsList);
    	assertEquals(1, hcsList.size());
    }
    
    
    /**
     * Test search by example with wild card false.
     */
    public void testSearchByExampleWithWildCardFalse(){
    	//HealthcareSite org = getDao().getById(1000);
    	HealthcareSite org  = new HealthcareSite();
    	org.setNciInstituteCode("du code");
    	org.setName("Duke Comprehensive Cancer Center");
    	List<HealthcareSite> hcsList = getDao().searchByExample(org, false);
    	assertNotNull(hcsList);
    	assertEquals(1, hcsList.size());
    }
    
    
    
    public void testMergeNewOrganization() {
        Integer savedId;
        {
        	HealthcareSite healthcaresite = getDao().getById(1000);

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
            Organization org = this.getDao().merge(healthcaresite);

            savedId = org.getId();
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
    
    public void testSaveNewOrganizationWithEndpoint() {
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
            loaded.setStudyEndPointProperty(new EndPointConnectionProperty(true, EndPointType.GRID));
            loaded.getStudyEndPointProperty().setUrl("http://");
            this.getDao().save(loaded);
        }
        interruptSession();
        {
            HealthcareSite loaded = this.getDao().getById(savedId);
            assertNotNull("Shouldnt be null", loaded.getStudyEndPointProperty());
        }
    }

    public void testGetByIdForInvestigatorGroups() throws Exception {
        HealthcareSite org = getDao().getById(1000);
        assertEquals("Expected 2 investigator groups", 2, org.getInvestigatorGroups().size());
    }

    public void testAddInvestigatorGroupToHealthcareSite() throws Exception {
        Integer savedId;
    	{
            HealthcareSite org = getDao().getById(1000);
            InvestigatorGroup investigatorGroup = new InvestigatorGroup();
            investigatorGroup.setHealthcareSite(org);
            investigatorGroup.setDescriptionText("InvestigatorGroupDescription");
            investigatorGroup.setName("inv_grp");
            
           
            HealthcareSiteInvestigator healthcareSiteInvestigator = healthcareSiteInvestigatorDao.getById(1001);
            
            SiteInvestigatorGroupAffiliation siga = new SiteInvestigatorGroupAffiliation();
            siga.setHealthcareSiteInvestigator(healthcareSiteInvestigator);
            siga.setInvestigatorGroup(investigatorGroup);
            
            investigatorGroup.getSiteInvestigatorGroupAffiliations().add(siga);
            org.getInvestigatorGroups().add(investigatorGroup);
            //investigatorGroupDao.save(investigatorGroup);
            getDao().save(org);
            savedId = investigatorGroup.getId();
            //org.getInvestigatorGroups().get(1).setName("Physicians Group");
        }
        
        interruptSession();
        HealthcareSite loadedOrg = getDao().getById(1000);
        assertEquals("Expected 3 investigator groups", 3, loadedOrg.getInvestigatorGroups().size());
        InvestigatorGroup investigatorGroup = investigatorGroupDao.getById(savedId);
        assertEquals("inv_grp", investigatorGroup.getName());
        assertEquals("InvestigatorGroupDescription", investigatorGroup.getDescriptionText());
    }
    
    public void testSaveNotificationWithMessageDetailsAndRecepients() throws Exception {
    	{
    		HealthcareSite org = getDao().getById(1000);
			org.getPlannedNotifications().add(buildNotificationWithRecepientsAndMesssageDetails(org));    		
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
        assertEquals("Wrong contact","vinay.gangoli@semanticbits.com", loadedOrg.getPlannedNotifications().get(0).getContactMechanismBasedRecipient().get(0).getContactMechanisms().get(0).getValue());
    }
    
    public PlannedNotification buildNotificationWithRecepientsAndMesssageDetails(HealthcareSite org){
    	PlannedNotification plannedNotification = new PlannedNotification();
    	
    	plannedNotification.setHealthcareSite(org);
    	plannedNotification.setDeliveryMechanism(DeliveryMechanismEnum.EMAIL);
    	plannedNotification.setEventName(NotificationEventTypeEnum.STUDY_STATUS_CHANGED_EVENT);
    	plannedNotification.setFrequency(NotificationFrequencyEnum.IMMEDIATE);
    	plannedNotification.setMessage(OrganizationDaoTest.MESSAGE_BODY);
    	plannedNotification.setTitle(OrganizationDaoTest.TITLE);
    	plannedNotification.setStudyThreshold(10);

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
        
        ContactMechanismBasedRecipient cmbr = new ContactMechanismBasedRecipient();
        cmbr.getContactMechanisms().add(cm);
        plannedNotification.getContactMechanismBasedRecipient().add(cmbr);

    	
    	return plannedNotification;
    }
    
    
    public void testSaveScheduledNotification(){
    	int savedId;
    	{
    		HealthcareSite org = getDao().getById(1000);
    		PlannedNotification plannedNotification = buildNotificationWithRecepientsAndMesssageDetails(org);
    		addScheduledNotification(plannedNotification, "composedMessage");
    		plannedNotificationDao.saveOrUpdate(plannedNotification);//merge(plannedNotification);
    		savedId = plannedNotification.getId();
    	}
    	interruptSession();
	
    	PlannedNotification plannedNotification = plannedNotificationDao.getById(savedId);
    	assertNotNull(plannedNotification);
    	assertEquals(plannedNotification.getScheduledNotifications().get(0).getMessage(), "composedMessage");
    	for(RecipientScheduledNotification rsn : plannedNotification.getScheduledNotifications().get(0).getRecipientScheduledNotification()){
    		assertNotNull(rsn.getRecipient());
    		if(rsn.getRecipient() instanceof RoleBasedRecipient){
    			assertEquals(((RoleBasedRecipient)rsn.getRecipient()).getRole(), "admin");
    		}
    		if(rsn.getRecipient() instanceof UserBasedRecipient){
    			assertEquals(((UserBasedRecipient)rsn.getRecipient()).getResearchStaff().getFirstName(), "Research Bill");
    		}
    	}
    }
    
    public ScheduledNotification addScheduledNotification(PlannedNotification plannedNotification, String composedMessage){
    	
    	ScheduledNotification scheduledNotification = new ScheduledNotification();
    	plannedNotification.getScheduledNotifications().add(scheduledNotification);
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
    
    public void testGetAll() throws Exception{
    	assertEquals("wrong number of organizations",2,healthcareSiteDao.getAll().size());
    }
    
    public void testForClear() throws Exception{
    	HealthcareSite organization = healthcareSiteDao.getById(1000);
    	assertNotNull("organization is null",organization);
    	assertEquals("Duke Comprehensive Cancer Center",organization.getName());
    	healthcareSiteDao.clear();
    	try{
    	organization.getHealthcareSiteInvestigators().get(0);
    	fail("Should throw lazy initialization");
    	}catch(org.hibernate.LazyInitializationException ex){
    		
    	}
    }
    
    public void testGetBySubname() throws Exception {
        List<HealthcareSite> actual = healthcareSiteDao.getBySubnames(new String[] { "Du" });
        assertEquals("Wrong number of matches", 1, actual.size());
        assertEquals("Wrong match", 1000, (int) actual.get(0).getId());
    }
    
    public void testGetBySubnameForNciCode() throws Exception {
        List<HealthcareSite> actual = healthcareSiteDao.getBySubnames(new String[] { "code" });
        assertEquals("Wrong number of matches", 2, actual.size());
       // assertEquals("Wrong match", 1000, (int) actual.get(0).getId());
    }
    
    public void testDomainClass() throws Exception{
    	assertEquals("Wrong domain class",HealthcareSite.class, healthcareSiteDao.domainClass());
    }

}
