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
	
	public void testGetFullNameForStaff(){
		
		UserBasedRecipient userBasedRecipient = getUserBasedRecipientForStaff();
		String name = userBasedRecipient.getFullName();
		assertTrue(name.equals("John Doe"));
	}

	public void testGetFullNameForInv(){
		
		UserBasedRecipient userBasedRecipient = getUserBasedRecipientForInv();
		String name = userBasedRecipient.getFullName();
		assertTrue(name.equals("Jane Doe"));
	}
	
	/**
	 * Gets the user based recipient for staff.
	 * 
	 * @return the user based recipient for staff
	 */
	private UserBasedRecipient getUserBasedRecipientForStaff() {
		PersonUser researchStaff = new LocalPersonUser();
//		ContactMechanism contactMechanism = new LocalContactMechanism();
//		contactMechanism.setType(ContactMechanismType.EMAIL);
//		contactMechanism.setValue(STAFF);
//		researchStaff.getContactMechanisms().add(contactMechanism);
		researchStaff.setEmail(STAFF);
		
		researchStaff.setFirstName("John");
		researchStaff.setLastName("Doe");
		researchStaff.setMiddleName("middle");
		
		UserBasedRecipient userBasedRecipient = new UserBasedRecipient();
		userBasedRecipient.setPersonUser(researchStaff);
		
		return userBasedRecipient;
	}
	
	/**
	 * Gets the user based recipient for inv.
	 * 
	 * @return the user based recipient for inv
	 */
	private UserBasedRecipient getUserBasedRecipientForInv() {
		
		Investigator investigator = new LocalInvestigator();
//		ContactMechanism contactMechanismInv = new LocalContactMechanism();
//		contactMechanismInv.setType(ContactMechanismType.EMAIL);
//		contactMechanismInv.setValue(INV);
//		investigator.getContactMechanisms().add(contactMechanismInv);
		investigator.setEmail(INV);

		investigator.setFirstName("Jane");
		investigator.setLastName("Doe");
		investigator.setMiddleName("middle");
		
		UserBasedRecipient userBasedRecipient = new UserBasedRecipient();
		userBasedRecipient.setInvestigator(investigator);
		
		return userBasedRecipient;
	}
}

