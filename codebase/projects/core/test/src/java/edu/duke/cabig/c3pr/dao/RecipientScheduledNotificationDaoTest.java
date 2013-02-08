/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.dao;

import edu.duke.cabig.c3pr.constants.EmailNotificationDeliveryStatusEnum;
import edu.duke.cabig.c3pr.domain.RecipientScheduledNotification;
import edu.duke.cabig.c3pr.domain.RoleBasedRecipient;
import edu.duke.cabig.c3pr.domain.StudyInvestigator;
import edu.duke.cabig.c3pr.domain.StudyPersonnel;
import edu.duke.cabig.c3pr.utils.ContextDaoTestCase;

/**
 * JUnit Tests for recipientScheduledNotificationDao
 * 
 * @author Vinay Gangoli
 * @testType Integration
 */
public class RecipientScheduledNotificationDaoTest extends ContextDaoTestCase<RecipientScheduledNotificationDao> {
    /**
     * Test for loading a recipientScheduledNotification by Id and ensure the lists which are consequently accessed
     * are initialized.
     * @throws Exception
     */
    public void testGetById() throws Exception {
    	RecipientScheduledNotification recipientScheduledNotification = getDao().getById(1000);
        assertEquals("Title", recipientScheduledNotification.getScheduledNotification().getTitle());
        try{
	        for(StudyPersonnel sp: recipientScheduledNotification.getScheduledNotification().getStudyOrganization().getStudyPersonnel()){
	        	assertNotNull("ResearchStaff is null", sp.getPersonUser());
	        }
	        for(StudyInvestigator si: recipientScheduledNotification.getScheduledNotification().getStudyOrganization().getStudyInvestigators()){
	        	assertNotNull("Investigator is null", si.getHealthcareSiteInvestigator().getInvestigator());
	        }
        } catch(NullPointerException npe){
        	fail("Null pointer is thrown due to lazy initialization.");
        }
    }


    /**
     * Test for Persisting recipientScheduledNotification
     * @throws Exception
     */
    public void testSaveOrUpdateRecipientScheduledNotification() throws Exception {
    	Integer savedId;
        {
        	RecipientScheduledNotification recipientScheduledNotification = getDao().getById(1000);
        	recipientScheduledNotification.setDeliveryStatus(EmailNotificationDeliveryStatusEnum.COMPLETE);
        	recipientScheduledNotification.setIsRead(Boolean.TRUE);
        	
            getDao().saveOrUpdate(recipientScheduledNotification);

            savedId = recipientScheduledNotification.getId();
            assertNotNull("The saved notification didn't get an id", savedId);
        }
        interruptSession();
        {
        	RecipientScheduledNotification recipientScheduledNotification  = getDao().getById(savedId);
            assertNotNull("Could not reload recipientScheduledNotification with id " + savedId, recipientScheduledNotification);
            assertEquals(EmailNotificationDeliveryStatusEnum.COMPLETE, recipientScheduledNotification.getDeliveryStatus());
        }
    }
    
    
    /**
     * Test save or update recipient scheduled notification without scheduled notification.
     * Ensures that the constraint ViolationException is thrown.
     * 
     * @throws Exception the exception
     */
    public void testSaveOrUpdateRecipientScheduledNotificationWithoutScheduledNotification() throws Exception {
    	RecipientScheduledNotification recipientScheduledNotification = new RecipientScheduledNotification();
    	recipientScheduledNotification.setDeliveryStatus(EmailNotificationDeliveryStatusEnum.COMPLETE);
    	recipientScheduledNotification.setIsRead(Boolean.TRUE);
//    	recipientScheduledNotification.setRecipient(getRoleBasedRecipient());
    	recipientScheduledNotification.setScheduledNotification(null);
        try{
    		getDao().saveOrUpdate(recipientScheduledNotification);
    		fail("Should have thrown Hibernate Exception");
        } catch(Exception e){
        	assertTrue(e.getCause().getMessage().contains("ScheduledNotification"));
        }
    }
    
    
    private RoleBasedRecipient getRoleBasedRecipient(){
    	RoleBasedRecipient roleBasedRecipient = new RoleBasedRecipient();
    	roleBasedRecipient.setRole("Registrar");
    	return roleBasedRecipient;
    }
    
    
}


