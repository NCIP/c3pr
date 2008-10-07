package edu.duke.cabig.c3pr.dao;

import edu.duke.cabig.c3pr.constants.EmailNotificationDeliveryStatusEnum;
import edu.duke.cabig.c3pr.domain.RecipientScheduledNotification;
import edu.duke.cabig.c3pr.utils.ContextDaoTestCase;

/**
 * JUnit Tests for recipientScheduledNotificationDao
 * 
 * @author Vinay Gangoli
 * @testType unit
 */
public class RecipientScheduledNotificationDaoTest extends ContextDaoTestCase<RecipientScheduledNotificationDao> {

    /**
     * Test for loading a recipientScheduledNotification by Id
     * @throws Exception
     */
    public void testGetById() throws Exception {
    	RecipientScheduledNotification recipientScheduledNotification = getDao().getById(1000);
        assertEquals("Title", recipientScheduledNotification.getScheduledNotification().getTitle());
    }

    /**
     * Test for Persisting recipientScheduledNotification
     * @throws Exception
     */
    public void testSaveRecipientScheduledNotification() throws Exception {
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

}


