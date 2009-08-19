package edu.duke.cabig.c3pr.domain;

import edu.duke.cabig.c3pr.AbstractTestCase;
import edu.duke.cabig.c3pr.constants.ContactMechanismType;

// TODO: Auto-generated Javadoc
/**
 * The Class ContactMechanismTest.
 */
public class ContactMechanismTest extends AbstractTestCase{
	
	/**
	 * Test get value string.
	 * 
	 * @throws Exception the exception
	 */
	public void testGetValueString1() throws Exception{
		
		ContactMechanism phoneContact = new LocalContactMechanism();
		phoneContact.setType(ContactMechanismType.PHONE);
		phoneContact.setValue("-1-23-456--78-9-");
		
		assertEquals("Wrong format of phone number","123-456-789",phoneContact.getValueString());
	}
	
	/**
	 * Test get value string2.
	 * 
	 * @throws Exception the exception
	 */
	public void testGetValueString2() throws Exception{
		
		ContactMechanism emailContact = new LocalContactMechanism();
		emailContact.setType(ContactMechanismType.EMAIL);
		emailContact.setValue("  --alfred.1237(*&@yahOo.343.com&)");
		
		assertEquals("Wrong format of phone number","  --alfred.1237(*&@yahOo.343.com&)",emailContact.getValueString());
	}

}
