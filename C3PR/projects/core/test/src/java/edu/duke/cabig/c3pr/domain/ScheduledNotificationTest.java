package edu.duke.cabig.c3pr.domain;

import edu.duke.cabig.c3pr.AbstractTestCase;

/**
 * The Class ScheduledNotificationTest.
 */
public class ScheduledNotificationTest extends AbstractTestCase{
	
	/** The Constant MESSAGE_BODY. */
	public static final String MESSAGE_BODY = "This is the message body!";
	
	/**
	 * Test get html based message used in report generation.
	 * 
	 * @throws Exception the exception
	 */
	public void testGetHtmlMessage() throws Exception {
		ScheduledNotification scheduledNotification = new ScheduledNotification();
		
		StringBuffer message = new StringBuffer();
		message.append("<html><body>");
		message.append(MESSAGE_BODY);
		message.append("</body></html>");
		
		scheduledNotification.setMessage(message.toString());
		
		assertEquals(MESSAGE_BODY, scheduledNotification.getHtmlMessage());
	}

	/**
	 * Test get html based message used in report generation.
	 * 
	 * @throws Exception the exception
	 */
	public void testGetHtmlMessageForNull() throws Exception {
		ScheduledNotification scheduledNotification = new ScheduledNotification();
		
		scheduledNotification.setMessage(null);
		assertEquals("", scheduledNotification.getHtmlMessage());
	}
	
	/**
	 * Test get html based message used in report generation.
	 * 
	 * @throws Exception the exception
	 */
	public void testGetHtmlMessageForText() throws Exception {
ScheduledNotification scheduledNotification = new ScheduledNotification();
		
		StringBuffer message = new StringBuffer();
		message.append(MESSAGE_BODY);
		
		scheduledNotification.setMessage(message.toString());
		
		assertEquals(MESSAGE_BODY, scheduledNotification.getHtmlMessage());
	}
}

