package edu.duke.cabig.c3pr.domain;

import java.util.ArrayList;
import java.util.List;

import edu.duke.cabig.c3pr.AbstractTestCase;

public class PersonTestCase extends AbstractTestCase {
	
	class PersonSubClass extends Person{
		
	}
	
	private Person person ;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		person = new PersonSubClass(); 
	}
	
	public void testGetEmailAsString(){
		assertNull("Email should come as null", person.getEmailAsString());
	}

	public void testGetEmailAsString1(){
		person.setEmail("test@best.com");
		assertNotNull("Email should not be null", person.getEmailAsString());
		assertEquals("Email id should be test@best.com", "test@best.com", person.getEmailAsString());
	}
	
	public void testGetEmailAsString2(){
		person.setFax("1234567890");
		assertNull("Email should be null", person.getEmailAsString());
	}
	
	public void testGetPhoneAsString(){
		assertNull("Phone should come as null", person.getPhoneAsString());
	}

	public void testGetPhoneAsString1(){
		person.setPhone("1234567890");
		assertNotNull("Phone should not be null", person.getPhoneAsString());
		assertEquals("Phone number should be 1234567890", "1234567890", person.getPhoneAsString());
	}
	
	public void testGetPhoneAsString2(){
		person.setFax("1234567890");
		assertNull("Phone should be null", person.getPhoneAsString());
	}
	
	public void testGetFaxAsString(){
		assertNull("Fax should come as null", person.getFaxAsString());
	}

	public void testGetFaxAsString1(){
		person.setFax("1234567890");
		assertNotNull("Fax should not be null", person.getFaxAsString());
		assertEquals("Fax number should be 1234567890", "1234567890", person.getFaxAsString());
	}
	
	public void testGetFaxAsString2(){
		person.setPhone("1234567890");
		assertNull("Fax should be null", person.getFaxAsString());
	}
	
	public void testGetContactMechanism(){
		ContactMechanism contactMechanism = registerMockFor(ContactMechanism.class);
		List<ContactMechanism> list = new ArrayList<ContactMechanism>();
		list.add(contactMechanism);
		person.setContactMechanisms(list);
		assertEquals("1 contact mechnism found",1, person.getContactMechanisms().size());
	}
	
	public void testGetFullName(){
		person.setFirstName("First");
		person.setMiddleName("Middle");
		person.setLastName("Last");
		assertEquals("Full name is First Middle Last","First Middle Last", person.getFullName());
	}
	
	public void testEquals(){
		assertTrue("comparing person object to itself", person.equals(person));
	}
	
	public void testEquals1(){
		User user = new LocalResearchStaff();
		assertFalse("comparing person object to user", person.equals(user));
	}
	
	public void testEquals2(){
		Person newPerson = new PersonSubClass();
		assertTrue("comparing person object to another person", person.equals(newPerson));
	}
	
	public void testEquals3(){
		Person newPerson = new PersonSubClass();
		newPerson.setFirstName("Name");
		assertFalse("comparing person object to another person", person.equals(newPerson));
	}
	
	public void testEquals4(){
		Person newPerson = new PersonSubClass();
		newPerson.setMiddleName("Name");
		assertFalse("comparing person object to another person", person.equals(newPerson));
	}
	

	public void testEquals5(){
		Person newPerson = new PersonSubClass();
		newPerson.setLastName("Name");
		assertFalse("comparing person object to another person", person.equals(newPerson));
	}
	
	public void testEquals6(){
		Person newPerson = new PersonSubClass();
		newPerson.setLastName("Name");
		person.setLastName("Name");
		assertTrue("comparing person object to another person", person.equals(newPerson));
	}
	

	public void testEquals7(){
		Person newPerson = new PersonSubClass();
		newPerson.setFirstName("Name");
		person.setFirstName("Name");
		assertTrue("comparing person object to another person", person.equals(newPerson));
	}
	

	public void testEquals9(){
		Person newPerson = new PersonSubClass();
		newPerson.setMiddleName("Name");
		person.setMiddleName("Name");
		assertTrue("comparing person object to another person", person.equals(newPerson));
	}
	

	public void testEquals8(){
		Person newPerson = new PersonSubClass();
		newPerson.setMaidenName("Name");
		person.setMaidenName("Name");
		assertTrue("comparing person object to another person", person.equals(newPerson));
	}
	
	public void testEquals10(){
		Person newPerson = new PersonSubClass();
		newPerson.setMaidenName("Name");
		assertFalse("comparing person object to another person", person.equals(newPerson));
	}
	
	
	
}
