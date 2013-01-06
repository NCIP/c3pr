package edu.duke.cabig.c3pr.domain;

import edu.duke.cabig.c3pr.AbstractTestCase;

public class AddressTestCase extends AbstractTestCase {
	private Address address;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		address = new Address(); 
	}
	
	public void testAddressString(){
		address.setCity("City");
		assertEquals("address should be City", "City", address.getAddressString());
	}
	
	public void testAddressString1(){
		address.setCity("City");
		address.setStateCode("State");
		assertEquals("address should be City, State", "City, State", address.getAddressString());
	}
	
	public void testAddressString2(){
		assertEquals("address should be empty string", "", address.getAddressString());
	}
}
