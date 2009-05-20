package edu.duke.cabig.c3pr.domain;

import java.util.ArrayList;
import java.util.List;

import edu.duke.cabig.c3pr.AbstractTestCase;

// TODO: Auto-generated Javadoc
/**
 * The Class PersonTestCase.
 */
public class PersonTestCase extends AbstractTestCase {
	
	/**
	 * The Class PersonSubClass.
	 */
	class PersonSubClass extends Person{
		
	}
	
	/** The person. */
	private Person person ;
	
	/* (non-Javadoc)
	 * @see edu.nwu.bioinformatics.commons.testing.CoreTestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		person = new PersonSubClass(); 
	}
	
	/**
	 * Test get email as string.
	 */
	public void testGetEmailAsString(){
		assertNull("Email should come as null", person.getEmailAsString());
	}

	/**
	 * Test get email as string1.
	 */
	public void testGetEmailAsString1(){
		person.setEmail("test@best.com");
		assertNotNull("Email should not be null", person.getEmailAsString());
		assertEquals("Email id should be test@best.com", "test@best.com", person.getEmailAsString());
	}
	
	/**
	 * Test get email as string2.
	 */
	public void testGetEmailAsString2(){
		person.setFax("1234567890");
		assertNull("Email should be null", person.getEmailAsString());
	}
	
	/**
	 * Test get phone as string.
	 */
	public void testGetPhoneAsString(){
		assertNull("Phone should come as null", person.getPhoneAsString());
	}

	/**
	 * Test get phone as string1.
	 */
	public void testGetPhoneAsString1(){
		person.setPhone("1234567890");
		assertNotNull("Phone should not be null", person.getPhoneAsString());
		assertEquals("Phone number should be 1234567890", "1234567890", person.getPhoneAsString());
	}
	
	/**
	 * Test get phone as string2.
	 */
	public void testGetPhoneAsString2(){
		person.setFax("1234567890");
		assertNull("Phone should be null", person.getPhoneAsString());
	}
	
	/**
	 * Test get fax as string.
	 */
	public void testGetFaxAsString(){
		assertNull("Fax should come as null", person.getFaxAsString());
	}

	/**
	 * Test get fax as string1.
	 */
	public void testGetFaxAsString1(){
		person.setFax("1234567890");
		assertNotNull("Fax should not be null", person.getFaxAsString());
		assertEquals("Fax number should be 1234567890", "1234567890", person.getFaxAsString());
	}
	
	/**
	 * Test get fax as string2.
	 */
	public void testGetFaxAsString2(){
		person.setPhone("1234567890");
		assertNull("Fax should be null", person.getFaxAsString());
	}
	
	/**
	 * Test get contact mechanism.
	 */
	public void testGetContactMechanism(){
		ContactMechanism contactMechanism = registerMockFor(ContactMechanism.class);
		List<ContactMechanism> list = new ArrayList<ContactMechanism>();
		list.add(contactMechanism);
		person.setContactMechanisms(list);
		assertEquals("1 contact mechnism found",1, person.getContactMechanisms().size());
	}
	
	/**
	 * Test get full name.
	 */
	public void testGetFullName(){
		person.setFirstName("First");
		person.setMiddleName("Middle");
		person.setLastName("Last");
		assertEquals("Full name is First Middle Last","First Middle Last", person.getFullName());
	}
	
	/**
	 * Test equals.
	 */
	public void testEquals(){
		assertTrue("comparing person object to itself", person.equals(person));
	}
	
	/**
	 * Test equals1.
	 */
	public void testEquals1(){
		User user = new LocalResearchStaff();
		assertFalse("comparing person object to user", person.equals(user));
	}
	
	/**
	 * Test equals2.
	 */
	public void testEquals2(){
		Person newPerson = new PersonSubClass();
		assertTrue("comparing person object to another person", person.equals(newPerson));
	}
	
	/**
	 * Test equals3.
	 */
	public void testEquals3(){
		Person newPerson = new PersonSubClass();
		newPerson.setFirstName("Name");
		assertFalse("comparing person object to another person", person.equals(newPerson));
	}
	
	/**
	 * Test equals4.
	 */
	public void testEquals4(){
		Person newPerson = new PersonSubClass();
		newPerson.setMiddleName("Name");
		assertFalse("comparing person object to another person", person.equals(newPerson));
	}
	

	/**
	 * Test equals5.
	 */
	public void testEquals5(){
		Person newPerson = new PersonSubClass();
		newPerson.setLastName("Name");
		assertFalse("comparing person object to another person", person.equals(newPerson));
	}
	
	/**
	 * Test equals6.
	 */
	public void testEquals6(){
		Person newPerson = new PersonSubClass();
		newPerson.setLastName("Name");
		person.setLastName("Name");
		assertTrue("comparing person object to another person", person.equals(newPerson));
	}
	

	/**
	 * Test equals7.
	 */
	public void testEquals7(){
		Person newPerson = new PersonSubClass();
		newPerson.setFirstName("Name");
		person.setFirstName("Name");
		assertTrue("comparing person object to another person", person.equals(newPerson));
	}
	

	/**
	 * Test equals9.
	 */
	public void testEquals9(){
		Person newPerson = new PersonSubClass();
		newPerson.setMiddleName("Name");
		person.setMiddleName("Name");
		assertTrue("comparing person object to another person", person.equals(newPerson));
	}
	

	/**
	 * Test equals8.
	 */
	public void testEquals8(){
		Person newPerson = new PersonSubClass();
		newPerson.setMaidenName("Name");
		person.setMaidenName("Name");
		assertTrue("comparing person object to another person", person.equals(newPerson));
	}
	
	/**
	 * Test equals10.
	 */
	public void testEquals10(){
		Person newPerson = new PersonSubClass();
		newPerson.setMaidenName("Name");
		assertFalse("comparing person object to another person", person.equals(newPerson));
	}

	/**
	 * Test equals11.
	 */
	public void testEquals11(){
		Person newPerson = new PersonSubClass();
		person.setMaidenName("Name");
		person.setFirstName("First");
		person.setMiddleName("Middle");
		person.setLastName("Last");
		assertFalse("comparing person object to another person", person.equals(newPerson));
	}
	
	/**
	 * Test equals12.
	 */
	public void testEquals12(){
		Person newPerson = new PersonSubClass();
		newPerson.setFirstName("First");
		
		person.setMaidenName("Name");
		person.setFirstName("First");
		person.setMiddleName("Middle");
		person.setLastName("Last");
		assertFalse("comparing person object to another person", person.equals(newPerson));
	}

	/**
	 * Test equals13.
	 */
	public void testEquals13(){
		Person newPerson = new PersonSubClass();
		newPerson.setFirstName("First");
		newPerson.setLastName("Last");
		
		person.setMaidenName("Name");
		person.setFirstName("First");
		person.setMiddleName("Middle");
		person.setLastName("Last");
		assertFalse("comparing person object to another person", person.equals(newPerson));
	}


	/**
	 * Test equals14.
	 */
	public void testEquals14(){
		Person newPerson = new PersonSubClass();
		newPerson.setFirstName("First");
		newPerson.setMiddleName("Middle");
		newPerson.setLastName("Last");
		
		person.setMaidenName("Name");
		person.setFirstName("First");
		person.setMiddleName("Middle");
		person.setLastName("Last");
		assertFalse("comparing person object to another person", person.equals(newPerson));
	}

	
	
}
