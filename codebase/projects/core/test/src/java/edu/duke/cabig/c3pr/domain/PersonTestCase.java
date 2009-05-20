package edu.duke.cabig.c3pr.domain;

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
	
}
