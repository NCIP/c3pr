package edu.duke.cabig.c3pr.domain;

import edu.duke.cabig.c3pr.AbstractTestCase;

/**
 * The Class PlannedNotificationTest.
 */
public class PlannedNotificationTest extends AbstractTestCase{
	
	
	/* (non-Javadoc)
	 * @see edu.nwu.bioinformatics.commons.testing.CoreTestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}
	
	
	/**
	 * Test get roles.
	 * 
	 * @throws Exception the exception
	 */
	public void testGetRoles() throws Exception {
		PlannedNotification plannedNotification = new PlannedNotification();
		addRecipients(plannedNotification);
		String roles = plannedNotification.getRoles();
		assertTrue(roles.contains("Admin"));
	}

	
	/**
	 * Test get email addresses.
	 * 
	 * @throws Exception the exception
	 */
	public void testGetEmailAddresses() throws Exception {
		PlannedNotification plannedNotification = new PlannedNotification();
		addRecipients(plannedNotification);
		String emails = plannedNotification.getEmailAddresses();
		assertTrue(emails.contains("unknown"));
		assertTrue(emails.contains("contact"));
	}
	
	
	/**
	 * Adds the recipients. Utility method used by the tests.
	 * Just adds roleBasedRecipients and ContactMechanismBasedRecipients to the notification.
	 * 
	 * @param plannedNotification the planned notification
	 */
	private void addRecipients(PlannedNotification plannedNotification){

		RoleBasedRecipient roleBasedRecipient = new RoleBasedRecipient();
		roleBasedRecipient.setRole("Admin");

		UserBasedRecipient userBasedRecipient = new UserBasedRecipient();
		userBasedRecipient.setEmailAddress("unknown@example.com");

		ContactMechanism nonUserContactMechanism = new ContactMechanism();
		nonUserContactMechanism.setType(ContactMechanismType.EMAIL);
		nonUserContactMechanism.setValue("contact@example.com");

		ContactMechanismBasedRecipient contactMechanismBasedRecipient = new ContactMechanismBasedRecipient();
		contactMechanismBasedRecipient.getContactMechanisms().add(nonUserContactMechanism);
		
		plannedNotification.getContactMechanismBasedRecipient().add(contactMechanismBasedRecipient);
		plannedNotification.getRoleBasedRecipient().add(roleBasedRecipient);
		plannedNotification.getUserBasedRecipient().add(userBasedRecipient);
		
		return;
	}
	
	
}

