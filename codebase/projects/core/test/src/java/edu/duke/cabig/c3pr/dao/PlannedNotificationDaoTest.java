/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import edu.duke.cabig.c3pr.constants.NotificationEventTypeEnum;
import edu.duke.cabig.c3pr.domain.PlannedNotification;
import edu.duke.cabig.c3pr.domain.RecipientScheduledNotification;
import edu.duke.cabig.c3pr.domain.RoleBasedRecipient;
import edu.duke.cabig.c3pr.domain.ScheduledNotification;
import edu.duke.cabig.c3pr.domain.UserBasedRecipient;
import edu.duke.cabig.c3pr.utils.ContextDaoTestCase;

/**
 * JUnit Tests for PlannedNotificationDao
 * 
 * @author Vinay Gangoli
 * @testType integration
 */
public class PlannedNotificationDaoTest extends ContextDaoTestCase<PlannedNotificationDao> {

    /**
     * Test for loading a plannedNotification by Id
     * @throws Exception
     */
	 private ScheduledNotificationDao scheduledNotificationDao;
	 private SessionFactory sessionFactory;
	 
    @Override
    protected void setUp() throws Exception {
    	
    	super.setUp();
    	sessionFactory = (SessionFactory)getApplicationContext().getBean("sessionFactory");
    	scheduledNotificationDao = (ScheduledNotificationDao) getApplicationContext().getBean("scheduledNotificationDao");
    	
    }	 
	 
    public void testGetById() throws Exception {
        PlannedNotification plannedNotification = getDao().getById(1000);
        assertEquals("STUDY_STATUS_CHANGED_EVENT", plannedNotification.getEventName().toString());
    }

    public void testGetAll(){
    	List<PlannedNotification> plannedNotificationsList = getDao().getAll();
    	assertEquals(2, plannedNotificationsList.size());
    }
    
    /**
     * Test for Persisting PlannedNotfns
     * @throws Exception
     */
    public void testSavePlannedNotificationWithScheduledNotficationsAndRecipients() throws Exception {
    	Integer savedId;
        {
        	PlannedNotification plannedNotification = getDao().getById(999);
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
            assertNotNull("Missing ScheduledNotfn", plannedNotification.getScheduledNotifications());
            for(ScheduledNotification sn: plannedNotification.getScheduledNotifications()){
            	assertNotNull("Missing RecipientScheduledNotification", sn.getRecipientScheduledNotification());
           		assertEquals("Incorrect message","message",sn.getMessage());
           		assertEquals("Incorrect title","title",sn.getTitle());
//            	assertEquals("Incorrect num of recipients in rsn", 2, sn.getRecipientScheduledNotification().size());
//            	for(RecipientScheduledNotification rsn: sn.getRecipientScheduledNotification()){
//            		assertNotNull("Missing Recipient", rsn.getRecipient());
//            	}
            }
        }
    }
    
    /** This tests the sql query in getPlannedNotifications in NotificationInterceptor class.
     * Move this to the NotificationInterceptorTest class when its created.  
     * 
     *
     */
    public void testGetPlannedNotifications(){
        Session session = sessionFactory.openSession(sessionFactory.getCurrentSession().connection());
        List<String> nciCodeList = new ArrayList<String>();
        nciCodeList.add("du code");
        List<PlannedNotification> result = null;
        Query query =  session.createQuery("select p from PlannedNotification p, HealthcareSite o, IN (o.plannedNotificationsInternal ) AS pn," +
        		"IN (o.identifiersAssignedToOrganization) AS I where p.id = pn.id and I.primaryIndicator = '1' and " +
                "I.value in (:nciCodeList)").setParameterList("nciCodeList",nciCodeList);
        result = query.list();
        assertEquals(result.size(), 1);
    }
    
    public void testDeletePlannedNotificationWithScheduledNotficationsAndRecipients() throws Exception {
    	Integer snId = -1;
        {
        	PlannedNotification plannedNotification = getDao().getById(1000);
        	addScheduledNotification(plannedNotification);
            this.getDao().merge(plannedNotification);

            List<PlannedNotification> pnList = getDao().getAll(); 
            for(PlannedNotification pn: pnList){
            	if(pn.getId().intValue() == 1000){
            		snId = pn.getScheduledNotifications().get(0).getId();
            		pn.setRetiredIndicatorAsTrue();
            		this.getDao().saveOrUpdate(pn);
            	}
            }
        }
        interruptSession();
        {
        	PlannedNotification plannedNotification  = this.getDao().getById(1000);
            assertNull("Could not reload organization with id ",  plannedNotification);
            assertNotNull("Missing ScheduledNotfn", scheduledNotificationDao.getById(snId));
        }
    }

    public void addScheduledNotification(PlannedNotification plannedNotification){
    	
    	ScheduledNotification scheduledNotification = new ScheduledNotification();
    	plannedNotification.getScheduledNotifications().add(scheduledNotification);
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

}


