package edu.duke.cabig.c3pr.dao;

import static edu.duke.cabig.c3pr.C3PRUseCase.CREATE_ORGANIZATION;

import java.util.Date;
import java.util.List;

import com.semanticbits.coppa.infrastructure.RemoteSession;

import edu.duke.cabig.c3pr.C3PRUseCases;
import edu.duke.cabig.c3pr.constants.ContactMechanismType;
import edu.duke.cabig.c3pr.constants.DeliveryMechanismEnum;
import edu.duke.cabig.c3pr.constants.EmailNotificationDeliveryStatusEnum;
import edu.duke.cabig.c3pr.constants.EndPointType;
import edu.duke.cabig.c3pr.constants.NotificationEventTypeEnum;
import edu.duke.cabig.c3pr.constants.NotificationFrequencyEnum;
import edu.duke.cabig.c3pr.constants.OrganizationIdentifierTypeEnum;
import edu.duke.cabig.c3pr.domain.Address;
import edu.duke.cabig.c3pr.domain.ContactMechanism;
import edu.duke.cabig.c3pr.domain.ContactMechanismBasedRecipient;
import edu.duke.cabig.c3pr.domain.EndPointConnectionProperty;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.HealthcareSiteInvestigator;
import edu.duke.cabig.c3pr.domain.InvestigatorGroup;
import edu.duke.cabig.c3pr.domain.LocalHealthcareSite;
import edu.duke.cabig.c3pr.domain.Organization;
import edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.PlannedNotification;
import edu.duke.cabig.c3pr.domain.RecipientScheduledNotification;
import edu.duke.cabig.c3pr.domain.RemoteHealthcareSite;
import edu.duke.cabig.c3pr.domain.ResearchStaff;
import edu.duke.cabig.c3pr.domain.RoleBasedRecipient;
import edu.duke.cabig.c3pr.domain.ScheduledNotification;
import edu.duke.cabig.c3pr.domain.SiteInvestigatorGroupAffiliation;
import edu.duke.cabig.c3pr.domain.SystemAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.UserBasedRecipient;
import edu.duke.cabig.c3pr.exception.C3PRBaseException;
import edu.duke.cabig.c3pr.infrastructure.RemoteHealthcareSiteResolver;
import edu.duke.cabig.c3pr.tools.Configuration;
import edu.duke.cabig.c3pr.utils.DaoTestCase;
import gov.nih.nci.cabig.ctms.tools.configuration.ConfigurationProperty;


/**
 * Test for loading an Organization by Id.
 * 
 * @throws Exception  */
@C3PRUseCases( { CREATE_ORGANIZATION })
public class OrganizationDaoTest extends DaoTestCase {
	
	/** The Constant MESSAGE_BODY. */
	public static final String MESSAGE_BODY = "You get this message and your domain model is working";
	
	/** The Constant TITLE. */
	public static final String TITLE = "Vanguard";
	
    /** The research staff dao. */
    private ResearchStaffDao researchStaffDao;
    
    /** The planned notification dao. */
    private PlannedNotificationDao plannedNotificationDao;
    
    /** The investigator group dao. */
    private InvestigatorGroupDao investigatorGroupDao;
    
    /** The healthcare site investigator dao. */
    private HealthcareSiteInvestigatorDao healthcareSiteInvestigatorDao;
    
    /** The healthcare site dao. */
    private HealthcareSiteDao healthcareSiteDao;
    
    /** The organization dao. */
    private OrganizationDao organizationDao;
    
    /** The remote session. */
    private RemoteSession remoteSession;
    
    /** The remote healthcare site resolver. */
    private RemoteHealthcareSiteResolver remoteHealthcareSiteResolver;
    
    /**
     * Instantiates a new organization dao test.
     */
    public OrganizationDaoTest() {
    	researchStaffDao = (ResearchStaffDao) getApplicationContext().getBean("researchStaffDao");
        plannedNotificationDao= (PlannedNotificationDao) getApplicationContext().getBean("plannedNotificationDao");
        investigatorGroupDao = (InvestigatorGroupDao) getApplicationContext().getBean("investigatorGroupDao");
        healthcareSiteInvestigatorDao = (HealthcareSiteInvestigatorDao) getApplicationContext().getBean("healthcareSiteInvestigatorDao");
        healthcareSiteDao = (HealthcareSiteDao) getApplicationContext().getBean("healthcareSiteDao");
        organizationDao = (OrganizationDao) getApplicationContext().getBean("organizationDao");
        remoteSession = (RemoteSession) getApplicationContext().getBean("remoteSession");
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
        HealthcareSite hcs = healthcareSiteDao.getByPrimaryIdentifier("du code");
        assertEquals("Duke Comprehensive Cancer Center", hcs.getName());
    }
    
    /**
     * Test get by nci identifier.
     * 
     * @throws Exception the exception
     */
    public void testGetByNciIdentifierFromLocal() throws Exception {
        HealthcareSite hcs = healthcareSiteDao.getByPrimaryIdentifierFromLocal("du code");
        assertEquals("Duke Comprehensive Cancer Center", hcs.getName());
    }
    
    
    /**
     * Test search by example with wild card true.
     * 
     * @throws C3PRBaseException      * @throws C3PRBaseRuntimeException
     */
    public void testSearchByExampleWithNciCodeAndWildCardTrue(){
    	HealthcareSite org  = new LocalHealthcareSite();
    	org.setCtepCode("du code");
    	org.getOrganizationAssignedIdentifiers().get(0).setPrimaryIndicator(true);
    	List<HealthcareSite> hcsList = healthcareSiteDao.searchByExample(org, true);
    	assertNotNull(hcsList);
    	assertEquals(1, hcsList.size());
    }
    
    /**
     * Test search by example with wild card true.
     * 
     * @throws C3PRBaseException      * @throws C3PRBaseRuntimeException
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
     * 
     * @throws C3PRBaseException      * @throws C3PRBaseRuntimeException
     */
    public void testSearchByExampleWithNameAndNciCodeAndWildCardTrue(){
    	HealthcareSite org  = new LocalHealthcareSite();
    	org.setName("Duke");
    	org.setCtepCode("du code");
    	List<HealthcareSite> hcsList = healthcareSiteDao.searchByExample(org, true);
    	assertNotNull(hcsList);
    	assertEquals(1, hcsList.size());
    }
    
    
    /**
     * Test search by example with wild card false.
     * 
     * @throws C3PRBaseException      * @throws C3PRBaseRuntimeException
     */
    public void testSearchByExampleWithWildCardFalse(){
    	//HealthcareSite org = healthcareSiteDao.getById(1000);
    	HealthcareSite org  = new LocalHealthcareSite();
    	org.setCtepCode("du code");
    	org.setName("Duke Comprehensive Cancer Center");
    	List<HealthcareSite> hcsList = healthcareSiteDao.searchByExample(org, false);
    	assertNotNull(hcsList);
    	assertEquals(1, hcsList.size());
    }
    
    
    
    /**
     * Test merge new organization.
     */
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
            healthcaresite.setCtepCode("NCI northwestern");
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
    
    
    /**
     * Test save new organization.
     */
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
            healthcaresite.setCtepCode("NCI northwestern");
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
    
    /**
     * Test save new organization with endpoint.
     */
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
            healthcaresite.setCtepCode("NCI northwestern");
            this.healthcareSiteDao.save(healthcaresite);
            
            savedId = healthcaresite.getId();
            assertNotNull("The saved organization didn't get an id", savedId);
        }

        interruptSession();
        {
            HealthcareSite loaded = this.healthcareSiteDao.getById(savedId);
            //loaded.setStudyEndPointProperty(new EndPointConnectionProperty(true, EndPointType.GRID));
            EndPointConnectionProperty endPointConnectionProperty= new EndPointConnectionProperty(EndPointType.GRID);
            endPointConnectionProperty.setIsAuthenticationRequired(true);
            loaded.setStudyEndPointProperty(endPointConnectionProperty);
            loaded.getStudyEndPointProperty().setUrl("http://");
            this.healthcareSiteDao.save(loaded);
        }
        interruptSession();
        {
            HealthcareSite loaded = this.healthcareSiteDao.getById(savedId);
            assertNotNull("Shouldnt be null", loaded.getStudyEndPointProperty());
        }
    }

    /**
     * Test save new organization with identifiers.
     */
    public void testSaveNewOrganizationWithIdentifiers() {
        Integer savedId;
        {
            HealthcareSite healthcaresite = new LocalHealthcareSite();
            HealthcareSite duke = this.healthcareSiteDao.getById(1000);
            
            Address address = new Address();
            address.setCity("Chicago");
            address.setCountryCode("USA");
            address.setPostalCode("83929");
            address.setStateCode("IL");
            address.setStreetAddress("123 Lake Shore Dr");

            healthcaresite.setAddress(address);
            healthcaresite.setName("Northwestern Memorial Hospital");
            healthcaresite.setDescriptionText("NU healthcare");
            
            OrganizationAssignedIdentifier oai_ctep = new OrganizationAssignedIdentifier();
            oai_ctep.setType(OrganizationIdentifierTypeEnum.CTEP);
            oai_ctep.setValue("ctep value 1");
            oai_ctep.setPrimaryIndicator(true);
            //set the assigning organization in the OAI.
            oai_ctep.setHealthcareSite(duke);
            healthcaresite.getOrganizationAssignedIdentifiers().add(oai_ctep);
            
            
            OrganizationAssignedIdentifier oai = new OrganizationAssignedIdentifier();
            oai.setType(OrganizationIdentifierTypeEnum.AI);
            oai.setValue("AOI-1");
            //set the assigning organization in the OAI.
            oai.setHealthcareSite(duke);
            healthcaresite.getOrganizationAssignedIdentifiers().add(oai);
            
            
            SystemAssignedIdentifier sai = new SystemAssignedIdentifier();
            sai.setType("Assigned Organization Identifier");
            sai.setValue("AOI-2");
            healthcaresite.getSystemAssignedIdentifiers().add(sai);
            
            this.healthcareSiteDao.save(healthcaresite);
            
            savedId = healthcaresite.getId();
            assertNotNull("The saved organization didn't get an id", savedId);
        }
        interruptSession();
        {
        	HealthcareSite loaded = this.healthcareSiteDao.getById(savedId);
        	
            assertEquals(3, loaded.getIdentifiersAssignedToOrganization().size(), 3);
           	assertEquals(2, loaded.getOrganizationAssignedIdentifiers().size());
           	assertEquals("ctep value 1", loaded.getCtepCode());
           	assertEquals("AOI-2", loaded.getSystemAssignedIdentifiers().get(0).getValue());
           	assertEquals(1000, loaded.getOrganizationAssignedIdentifiers().get(0).getHealthcareSite().getId().intValue());
        }        
    }
    
    /**
     * Test get by id for investigator groups.
     * 
     * @throws Exception the exception
     */
    public void testGetByIdForInvestigatorGroups() throws Exception {
        HealthcareSite org = healthcareSiteDao.getById(1000);
        assertEquals("Expected 2 investigator groups", 2, org.getInvestigatorGroups().size());
    }

    /**
     * Test add investigator group to healthcare site.
     * 
     * @throws Exception the exception
     */
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
    
    /**
     * Test save notification with message details and recepients.
     * 
     * @throws Exception the exception
     */
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
    
    /**
     * Builds the notification with recepients and messsage details.
     * 
     * @param org the org
     * 
     * @return the planned notification
     */
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
    
    
    /**
     * Test save scheduled notification.
     */
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
    
    /**
     * Adds the scheduled notification.
     * 
     * @param plannedNotification the planned notification
     * @param composedMessage the composed message
     * 
     * @return the scheduled notification
     */
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
    
    /**
     * Test get all.
     * 
     * @throws Exception the exception
     */
    public void testGetAll() throws Exception{
    	ConfigurationProperty coppaEnable = Configuration.COPPA_ENABLE;
    	assertNotNull("Missing the property 'COPPA_ENABLE'",coppaEnable);
    	if(Boolean.parseBoolean(coppaEnable.getDefault().toString())){
    		assertEquals("wrong number of organizations",5,healthcareSiteDao.getAll().size());
    	} else {
    		assertEquals("wrong number of organizations",3,healthcareSiteDao.getAll().size());
    	}
    }
    
    /**
     * Test for clear.
     * 
     * @throws Exception the exception
     */
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
    
    /**
     * Test get by subname.
     * 
     * @throws Exception the exception
     */
    public void testGetBySubname() throws Exception {
        List<HealthcareSite> actual = healthcareSiteDao.getBySubnames(new String[] { "Du" });
        assertEquals("Wrong number of matches", 1, actual.size());
        assertEquals("Wrong match", 1000, (int) actual.get(0).getId());
    }
    
    /**
     * Test get by subname for nci code.
     * 
     * @throws Exception the exception
     */
    public void testGetBySubnameForNciCode() throws Exception {
        List<HealthcareSite> actual = healthcareSiteDao.getBySubnames(new String[] { "code" });
        assertEquals("Wrong number of matches", 2, actual.size());
        assertEquals("Wrong match", 1000, (int) actual.get(0).getId());
    }
    
    /**
     * Test domain class.
     * 
     * @throws Exception the exception
     */
  public void testDomainClass() throws Exception{
    	assertEquals("Wrong domain class",HealthcareSite.class, healthcareSiteDao.domainClass());
    }
    
    /**
     * Test remote session load on coppa enabled.
     * 
     * @throws Exception the exception
     */
    public void testRemoteSessionLoadOnCoppaEnabled() throws Exception {
    	ConfigurationProperty coppaEnable = Configuration.COPPA_ENABLE;
    	assertNotNull("Missing the property 'COPPA_ENABLE'",coppaEnable);
    	if(Boolean.parseBoolean(coppaEnable.getDefault().toString())){
    		Object object = remoteSession.load(RemoteHealthcareSite.class,"CP-RM-TST-ID2" );
    		assertNotNull("Remote object cannot be null as coppa services are enabled", object);
    	}
    	
    }
    
    /**
     * Test remote session find on coppa enabled.
     * 
     * @throws Exception the exception
     */
    public void testRemoteSessionFindOnCoppaEnabled() throws Exception {
    	ConfigurationProperty coppaEnable = Configuration.COPPA_ENABLE;
    	assertNotNull("Missing the property 'COPPA_ENABLE'",coppaEnable);
    	if(Boolean.parseBoolean(coppaEnable.getDefault().toString())){
	    	RemoteHealthcareSite remoteHealthcareSite = (RemoteHealthcareSite) healthcareSiteDao.getById(1002);
	    	Object object = remoteSession.find(remoteHealthcareSite);
	    	assertNotNull("Remote object cannot be null as coppa services are enabled", object);
    	}
    	
    }
	
	/**
	 * Test get remote entity by unique id.
	 * 
	 * @throws Exception the exception
	 */
	public void testGetRemoteEntityByUniqueId() throws Exception{
		ConfigurationProperty coppaEnable = Configuration.COPPA_ENABLE;
		Organization organization = (RemoteHealthcareSite) remoteSession.load(RemoteHealthcareSite.class,"CP-RM-TST-ID");
		if(Boolean.parseBoolean(coppaEnable.getDefault().toString())){
			assertEquals(organization.getName(), "Nairobi Hospital");
			organization = (Organization) remoteHealthcareSiteResolver
					.getRemoteEntityByUniqueId("CP-RM-TST-ID2");
			assertEquals(organization.getName().trim(),
					"Montreal Childrens Hospital Remote 2".trim());
			organization = (Organization) remoteHealthcareSiteResolver
					.getRemoteEntityByUniqueId("CP-RM-TST-ID3");
			assertEquals(organization.getName().trim(),
					"Sydney Cancer Centre Remote 3".trim());
		}		
	}
	
	/**
	 * Test find.
	 * 
	 * @throws Exception the exception
	 */
	public void testFind() throws Exception{
		ConfigurationProperty coppaEnable = Configuration.COPPA_ENABLE;
    	if(Boolean.parseBoolean(coppaEnable.getDefault().toString())){
			Organization remoteOrgExample = new RemoteHealthcareSite();
			List<Object> organizations = remoteHealthcareSiteResolver.find(remoteOrgExample);
			assertEquals(3,organizations.size());
			//check if return object is of type RemoteOrganization
			Organization obj = (RemoteHealthcareSite)organizations.get(0);	
    	}
		
	}
	
	/**
	 * Test get remote organizations.
	 * 
	 * @throws Exception the exception
	 */
	public void testGetRemoteOrganizations() throws Exception{
		ConfigurationProperty coppaEnable = Configuration.COPPA_ENABLE;
    	assertNotNull("Missing the property 'COPPA_ENABLE'",coppaEnable);
    	if(Boolean.parseBoolean(coppaEnable.getDefault().toString())){
			HealthcareSite healthcareSite = new RemoteHealthcareSite();
			// nci institute code is being set to some code as the mock service returns 3 organizations by default.
			// this should be later changed to correct nci code once intergration with actual COPPA services is done
			healthcareSite.setCtepCode("Some Code");
			List<?> organizations = healthcareSiteDao.getRemoteOrganizations(healthcareSite);
			assertEquals(3,organizations.size());
			//check if return object is of type RemoteOrganization
			Organization obj = (RemoteHealthcareSite)organizations.get(0);	
    	}
		
	}

}
