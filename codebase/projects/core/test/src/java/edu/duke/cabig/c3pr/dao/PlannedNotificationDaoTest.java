package edu.duke.cabig.c3pr.dao;

import java.util.Date;

import edu.duke.cabig.c3pr.constants.NotificationEventTypeEnum;
import edu.duke.cabig.c3pr.domain.PlannedNotification;
import edu.duke.cabig.c3pr.domain.RecipientScheduledNotification;
import edu.duke.cabig.c3pr.domain.RoleBasedRecipient;
import edu.duke.cabig.c3pr.domain.ScheduledNotification;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.UserBasedRecipient;
import edu.duke.cabig.c3pr.service.impl.SchedulerServiceImpl;
import edu.duke.cabig.c3pr.utils.ContextDaoTestCase;

/**
 * JUnit Tests for ArmDao
 * 
 * @author Priyatam
 * @testType unit
 */
public class PlannedNotificationDaoTest extends ContextDaoTestCase<PlannedNotificationDao> {

    /**
     * Test for loading a plannedNotification by Id
     * @throws Exception
     */
	 private StudyDao studyDao = (StudyDao) getApplicationContext().getBean("studyDao");
	
    public void testGetById() throws Exception {
        PlannedNotification plannedNotification = getDao().getById(1000);
        assertEquals("STUDY_STATUS_CHANGED_EVENT", plannedNotification.getEventName().toString());
    }

    /**
     * Test for Persisting PlannedNotfns
     * @throws Exception
     */
    public void testSavePlannedNotificationWithScheduledNotficationsAndRecipients() throws Exception {
    	Integer savedId;
        {
        	PlannedNotification plannedNotification = getDao().getById(1000);
        	addScheduledNotification(plannedNotification);
            
            this.getDao().merge(plannedNotification);

            savedId = plannedNotification.getId();
            assertNotNull("The saved notification didn't get an id", savedId);
        }

        interruptSession();
        {
        	PlannedNotification plannedNotification  = this.getDao().getById(savedId);
            assertNotNull("Could not reload organization with id " + savedId, plannedNotification);
            assertEquals("Wrong Event Name", NotificationEventTypeEnum.STUDY_STATUS_CHANGED_EVENT.getDisplayName(), plannedNotification.getEventName().getDisplayName());
            assertNotNull("Missing ScheduledNotfn", plannedNotification.getScheduledNotification());
            for(ScheduledNotification sn: plannedNotification.getScheduledNotification()){
            	assertNotNull("Missing RecipientScheduledNotification", sn.getRecipientScheduledNotification());
            	assertEquals("Incorrect message","message",sn.getMessage());
            	assertEquals("Incorrect title","title",sn.getTitle());
            	assertEquals("Incorrect num of reipients in rsn", 2, sn.getRecipientScheduledNotification().size());
            	for(RecipientScheduledNotification rsn: sn.getRecipientScheduledNotification()){
            		assertNotNull("Missing Recipient", rsn.getRecipient());
            	}
            }
        }
    }

    public void addScheduledNotification(PlannedNotification plannedNotification){
    	
    	ScheduledNotification scheduledNotification = new ScheduledNotification();
    	plannedNotification.getScheduledNotification().add(scheduledNotification);
    	scheduledNotification.setDateSent(new Date());
		scheduledNotification.setMessage(plannedNotification.getMessage());
		scheduledNotification.setTitle(plannedNotification.getTitle());
		
    	RecipientScheduledNotification rsn; 
    	for(RoleBasedRecipient rbr: plannedNotification.getRoleBasedRecipient()){
    		rsn = new RecipientScheduledNotification();
    		rsn.setRecipient(rbr);
    		rsn.setIsRead(Boolean.FALSE);
    		rsn.setScheduledNotification(scheduledNotification);
    		scheduledNotification.getRecipientScheduledNotification().add(rsn);
    	}
    	
    	for(UserBasedRecipient ubr: plannedNotification.getUserBasedRecipient()){
    		rsn = new RecipientScheduledNotification();
    		rsn.setRecipient(ubr);
    		rsn.setIsRead(Boolean.FALSE);
    		rsn.setScheduledNotification(scheduledNotification);
    		scheduledNotification.getRecipientScheduledNotification().add(rsn);
    	}
    	return;
    }

    public void testGetInitializedById(){
    	this.getDao().getInitializedPlannedNotificationById(1000);
    }

}


