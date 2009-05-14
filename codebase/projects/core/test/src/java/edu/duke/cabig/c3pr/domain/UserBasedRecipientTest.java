package edu.duke.cabig.c3pr.domain;

import edu.duke.cabig.c3pr.AbstractTestCase;
import edu.duke.cabig.c3pr.constants.ContactMechanismType;

/**
 * The Class UserBasedRecipientTest.
 */
public class UserBasedRecipientTest extends AbstractTestCase{
	
	/** The Constant INV. */
	public static final String INV = "inv@example.com";
	
	/** The Constant STAFF. */
	public static final String STAFF = "staff@example.com";
	
	/**
	 * Test get email address for Investigator.
	 */
	public void testGetEmailAddressForInv(){
		
		UserBasedRecipient userBasedRecipient = getUserBasedRecipientForInv();
		String emails = userBasedRecipient.getEmailAddress();
		assertTrue(emails.equals(INV));
	}
	
	/**
	 * Test get email address for staff.
	 */
	public void testGetEmailAddressForStaff(){
		
		UserBasedRecipient userBasedRecipient = getUserBasedRecipientForStaff();
		String emails = userBasedRecipient.getEmailAddress();
		assertTrue(emails.equals(STAFF));
	}

	/**
	 * Gets the user based recipient for staff.
	 * 
	 * @return the user based recipient for staff
	 */
	private UserBasedRecipient getUserBasedRecipientForStaff() {
		ResearchStaff researchStaff = new LocalResearchStaff();
		ContactMechanism contactMechanism = new ContactMechanism();
		contactMechanism.setType(ContactMechanismType.EMAIL);
		contactMechanism.setValue(STAFF);
		researchStaff.getContactMechanisms().add(contactMechanism);
		
		
		UserBasedRecipient userBasedRecipient = new UserBasedRecipient();
		userBasedRecipient.setResearchStaff(researchStaff);
		
		return userBasedRecipient;
	}
	
	/**
	 * Gets the user based recipient for inv.
	 * 
	 * @return the user based recipient for inv
	 */
	private UserBasedRecipient getUserBasedRecipientForInv() {
		
		Investigator investigator = new LocalInvestigator();
		ContactMechanism contactMechanismInv = new ContactMechanism();
		contactMechanismInv.setType(ContactMechanismType.EMAIL);
		contactMechanismInv.setValue(INV);
		investigator.getContactMechanisms().add(contactMechanismInv);
		
		UserBasedRecipient userBasedRecipient = new UserBasedRecipient();
		userBasedRecipient.setInvestigator(investigator);
		
		return userBasedRecipient;
	}
}

