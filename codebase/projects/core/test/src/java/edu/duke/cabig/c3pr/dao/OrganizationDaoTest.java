package edu.duke.cabig.c3pr.dao;

import static edu.duke.cabig.c3pr.C3PRUseCase.CREATE_ORGANIZATION;

import java.util.Date;
import java.util.List;

import com.semanticbits.coppa.infrastructure.RemoteSession;
import com.semanticbits.coppa.infrastructure.hibernate.RemoteEntityInterceptor;

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
import edu.duke.cabig.c3pr.domain.InvestigatorGroup;
import edu.duke.cabig.c3pr.domain.LocalHealthcareSite;
import edu.duke.cabig.c3pr.domain.Organization;
import edu.duke.cabig.c3pr.domain.PlannedNotification;
import edu.duke.cabig.c3pr.domain.RecipientScheduledNotification;
import edu.duke.cabig.c3pr.domain.RemoteHealthcareSite;
import edu.duke.cabig.c3pr.domain.ResearchStaff;
import edu.duke.cabig.c3pr.domain.RoleBasedRecipient;
import edu.duke.cabig.c3pr.domain.ScheduledNotification;
import edu.duke.cabig.c3pr.domain.SiteInvestigatorGroupAffiliation;
import edu.duke.cabig.c3pr.domain.UserBasedRecipient;
import edu.duke.cabig.c3pr.exception.C3PRBaseException;
import edu.duke.cabig.c3pr.exception.C3PRBaseRuntimeException;
import edu.duke.cabig.c3pr.infrastructure.RemoteHealthcareSiteResolver;
import edu.duke.cabig.c3pr.tools.Configuration;
import edu.duke.cabig.c3pr.utils.DaoTestCase;
import gov.nih.nci.cabig.ctms.tools.configuration.ConfigurationProperty;


/**
 * Test for loading an Organization by Id
 * @throws Exception
 */
@C3PRUseCases( { CREATE_ORGANIZATION })
public class OrganizationDaoTest extends DaoTestCase {
	
	public static final String MESSAGE_BODY = "You get this message and your domain model is working";
	public static final String TITLE = "Vanguard";
	
    private ResearchStaffDao researchStaffDao;
    private PlannedNotificationDao plannedNotificationDao;
    private InvestigatorGroupDao investigatorGroupDao;
    private HealthcareSiteInvestigatorDao healthcareSiteInvestigatorDao;
    private HealthcareSiteDao healthcareSiteDao;
    private RemoteSession remoteSession;
    private RemoteEntityInterceptor remoteEntityInterceptor;
    private RemoteHealthcareSiteResolver remoteHealthcareSiteResolver;
    
    public OrganizationDaoTest() {
    	researchStaffDao = (ResearchStaffDao) getApplicationContext().getBean("researchStaffDao");
        plannedNotificationDao= (PlannedNotificationDao) getApplicationContext().getBean("plannedNotificationDao");
        investigatorGroupDao = (InvestigatorGroupDao) getApplicationContext().getBean("investigatorGroupDao");
        healthcareSiteInvestigatorDao = (HealthcareSiteInvestigatorDao) getApplicationContext().getBean("healthcareSiteInvestigatorDao");
        healthcareSiteDao = (HealthcareSiteDao) getApplicationContext().getBean("healthcareSiteDao");
        remoteSession = (RemoteSession) getApplicationContext().getBean("remoteSession");
        remoteEntityInterceptor = (RemoteEntityInterceptor) getApplicationContext().getBean("remoteEntityInterceptor");
        remoteHealthcareSiteResolver = (RemoteHealthcareSiteResolver)getApplicationContext().getBean("remoteHealthcareSiteResolver");
	}
    /**
     * Test get by id.
     * 
     * @throws Exception the exception
     */
    public void testGetById() throws Exception {
        HealthcareSite org = healthcareSiteDao.getById(1000);
        assertEquals("Duke Comprehensive Cancer Center", org.getName());
    }

    /**
     * Test get by nci identifier.
     * 
     * @throws Exception the exception
     */
    public void testGetByNciIdentifier() throws Exception {
        HealthcareSite hcs = healthcareSiteDao.getByNciInstituteCode("du code");
        assertEquals("Duke Comprehensive Cancer Center", hcs.getName());
    }
    
    
    /**
     * Test search by example with wild card true.
     * @throws C3PRBaseException 
     * @throws C3PRBaseRuntimeException 
     */
    public void testSearchByExampleWithNciCodeAndWildCardTrue(){
    	HealthcareSite org  = new LocalHealthcareSite();
    	org.setNciInstituteCode("du code");
    	List<HealthcareSite> hcsList = healthcareSiteDao.searchByExample(org, true);
    	assertNotNull(hcsList);
    	assertEquals(1, hcsList.size());
    }
    
    /**
     * Test search by example with wild card true.
     * @throws C3PRBaseException 
     * @throws C3PRBaseRuntimeException 
     */
    public void testSearchByExampleWithNameAndWildCardTrue(){
    	HealthcareSite org  = new LocalHealthcareSite();
    	org.setName("Duke");
    	List<HealthcareSite> hcsList = healthcareSiteDao.searchByExample(org, true);
    	assertNotNull(hcsList);
    	assertEquals(1, hcsList.size());
    }
    
    /**
     * Test search by example with wild card true.
     * @throws C3PRBaseException 
     * @throws C3PRBaseRuntimeException 
     */
    public void testSearchByExampleWithNameAndNciCodeAndWildCardTrue(){
    	HealthcareSite org  = new LocalHealthcareSite();
    	org.setName("Duke");
    	org.setNciInstituteCode("du code");
    	List<HealthcareSite> hcsList = healthcareSiteDao.searchByExample(org, true);
    	assertNotNull(hcsList);
    	assertEquals(1, hcsList.size());
    }
    
    
    /**
     * Test search by example with wild card false.
     * @throws C3PRBaseException 
     * @throws C3PRBaseRuntimeException 
     */
    public void testSearchByExampleWithWildCardFalse(){
    	//HealthcareSite org = healthcareSiteDao.getById(1000);
    	HealthcareSite org  = new LocalHealthcareSite();
    	org.setNciInstituteCode("du code");
    	org.setName("Duke Comprehensive Cancer Center");
    	List<HealthcareSite> hcsList = healthcareSiteDao.searchByExample(org, false);
    	assertNotNull(hcsList);
    	assertEquals(1, hcsList.size());
    }
    
    
    
    public void testMergeNewOrganization() {
        Integer savedId;
        {
        	HealthcareSite healthcaresite = healthcareSiteDao.getById(1000);

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
            Organization org = this.healthcareSiteDao.merge(healthcaresite);

            savedId = org.getId();
            assertNotNull("The saved organization didn't get an id", savedId);
        }

        interruptSession();
        {
            HealthcareSite loaded = this.healthcareSiteDao.getById(savedId);
            assertNotNull("Could not reload organization with id " + savedId, loaded);
            assertEquals("Wrong name", "Northwestern Memorial Hospital", loaded.getName());
            assertEquals("Wrong city", "Chicago", loaded.getAddress().getCity());
        }
    }
    
    
    public void testSaveNewOrganization() {
        Integer savedId;
        {
            HealthcareSite healthcaresite = new LocalHealthcareSite();

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
            this.healthcareSiteDao.save(healthcaresite);

            savedId = healthcaresite.getId();
            assertNotNull("The saved organization didn't get an id", savedId);
        }

        interruptSession();
        {
            HealthcareSite loaded = this.healthcareSiteDao.getById(savedId);
            assertNotNull("Could not reload organization with id " + savedId, loaded);
            assertEquals("Wrong name", "Northwestern Memorial Hospital", loaded.getName());
            assertEquals("Wrong city", "Chicago", loaded.getAddress().getCity());
        }
    }
    
    public void testSaveNewOrganizationWithEndpoint() {
        Integer savedId;
        {
            HealthcareSite healthcaresite = new LocalHealthcareSite();

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
            this.healthcareSiteDao.save(healthcaresite);
            
            savedId = healthcaresite.getId();
            assertNotNull("The saved organization didn't get an id", savedId);
        }

        interruptSession();
        {
            HealthcareSite loaded = this.healthcareSiteDao.getById(savedId);
            loaded.setStudyEndPointProperty(new EndPointConnectionProperty(true, EndPointType.GRID));
            loaded.getStudyEndPointProperty().setUrl("http://");
            this.healthcareSiteDao.save(loaded);
        }
        interruptSession();
        {
            HealthcareSite loaded = this.healthcareSiteDao.getById(savedId);
            assertNotNull("Shouldnt be null", loaded.getStudyEndPointProperty());
        }
    }

    public void testGetByIdForInvestigatorGroups() throws Exception {
        HealthcareSite org = healthcareSiteDao.getById(1000);
        assertEquals("Expected 2 investigator groups", 2, org.getInvestigatorGroups().size());
    }

    public void testAddInvestigatorGroupToHealthcareSite() throws Exception {
        Integer savedId;
    	{
            HealthcareSite org = healthcareSiteDao.getById(1000);
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
            healthcareSiteDao.save(org);
            savedId = investigatorGroup.getId();
            //org.getInvestigatorGroups().get(1).setName("Physicians Group");
        }
        
        interruptSession();
        HealthcareSite loadedOrg = healthcareSiteDao.getById(1000);
        assertEquals("Expected 3 investigator groups", 3, loadedOrg.getInvestigatorGroups().size());
        InvestigatorGroup investigatorGroup = investigatorGroupDao.getById(savedId);
        assertEquals("inv_grp", investigatorGroup.getName());
        assertEquals("InvestigatorGroupDescription", investigatorGroup.getDescriptionText());
    }
    
    public void testSaveNotificationWithMessageDetailsAndRecepients() throws Exception {
    	{
    		HealthcareSite org = healthcareSiteDao.getById(1000);
			org.getPlannedNotifications().add(buildNotificationWithRecepientsAndMesssageDetails(org));    		
			this.healthcareSiteDao.save(org);
    	}
    	interruptSession();
    	
    	HealthcareSite loadedOrg = healthcareSiteDao.getById(1000);
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
    		HealthcareSite org = healthcareSiteDao.getById(1000);
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
    	assertEquals("wrong number of organizations",5,healthcareSiteDao.getAll().size());
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
    
  /*  public void testGetBySubnameForNciCode() throws Exception {
        List<HealthcareSite> actual = healthcareSiteDao.getBySubnames(new String[] { "code" });
        assertEquals("Wrong number of matches", 2, actual.size());
        assertEquals("Wrong match", 1000, (int) actual.get(0).getId());
    }*/
    
    public void testDomainClass() throws Exception{
    	assertEquals("Wrong domain class",HealthcareSite.class, healthcareSiteDao.domainClass());
    }
    
    public void testRemoteSessionLoadOnCoppaEnabled() throws Exception {
    	ConfigurationProperty coppaEnable = Configuration.COPPA_ENABLE;
    	assertNotNull("Missing the property 'COPPA_ENABLE'",coppaEnable);
        assertTrue("COPPA_ENABLE should have been true", Boolean.parseBoolean(coppaEnable.getDefault().toString()));
    	Object object = remoteSession.load(RemoteHealthcareSite.class,"CP-RM-TST-ID2" );
    	assertNotNull("Remote object cannot be null as coppa services are enabled", object);
    	
    }
    
    public void testRemoteSessionFindOnCoppaEnabled() throws Exception {
    	ConfigurationProperty coppaEnable = Configuration.COPPA_ENABLE;
    	assertNotNull("Missing the property 'COPPA_ENABLE'",coppaEnable);
        assertTrue("COPPA_ENABLE should have been true", Boolean.parseBoolean(coppaEnable.getDefault().toString()));
        RemoteHealthcareSite remoteHealthcareSite = (RemoteHealthcareSite) healthcareSiteDao.getById(1002);
    	Object object = remoteSession.find(remoteHealthcareSite);
    	assertNotNull("Remote object cannot be null as coppa services are enabled", object);
    	
    }
	
	public void testGetRemoteEntityByUniqueId() {
		Organization organization = (RemoteHealthcareSite) remoteHealthcareSiteResolver.getRemoteEntityByUniqueId("CP-RM-TST-ID");
		assertEquals(organization.getName(),"Nairobi Hospital");
		organization = (Organization) remoteHealthcareSiteResolver.getRemoteEntityByUniqueId("CP-RM-TST-ID2");
		assertEquals(organization.getName().trim(),"Montreal Childrens Hospital Remote 2".trim());
		organization = (Organization) remoteHealthcareSiteResolver.getRemoteEntityByUniqueId("CP-RM-TST-ID3");
		assertEquals(organization.getName().trim(),"Sydney Cancer Centre Remote 3".trim());		
	}
	
	public void testFind() {
		Organization remoteOrgExample = new RemoteHealthcareSite();
		List<Object> organizations = remoteHealthcareSiteResolver.find(remoteOrgExample);
		assertEquals(organizations.size(),3);
		//check if return object is of type RemoteOrganization
		Organization obj = (RemoteHealthcareSite)organizations.get(0);		
		
	}

}
