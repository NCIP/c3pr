package edu.duke.cabig.c3pr.domain;

import java.util.ArrayList;
import java.util.List;

import edu.duke.cabig.c3pr.AbstractTestCase;
import edu.duke.cabig.c3pr.constants.ContactMechanismType;

/**
 * The Class InvestigatorTest.
 */
public class InvestigatorTest extends AbstractTestCase{
	
	/* (non-Javadoc)
	 * @see edu.nwu.bioinformatics.commons.testing.CoreTestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}
	
	/**
	 * Test set external Investigators.
	 * 
	 * @throws Exception the exception
	 */
	public void testSetExternalInvestigators() throws Exception{
		
		Investigator Investigator = new LocalInvestigator();
		assertEquals("Unexpected external investigator(s) ",0,Investigator.getExternalInvestigators().size());
		
		List<Investigator> externalInvestigators = new ArrayList<Investigator>();
		externalInvestigators.add(new LocalInvestigator());
		
		Investigator.setExternalInvestigators(externalInvestigators);
		assertEquals("Wrong number of investigators",1,Investigator.getExternalInvestigators().size());
	}
	
	/**
	 * Test remove healthcareSte investigator.
	 * 
	 * @throws Exception the exception
	 */
	public void testRemoveHealthcareSiteInvestigator() throws Exception{
		Investigator investigator = new LocalInvestigator();
		assertEquals("Unexpected healthcareSite investigator(s)",0,investigator.getHealthcareSiteInvestigators().size());
		
		HealthcareSiteInvestigator hcsInv = new HealthcareSiteInvestigator();
		investigator.addHealthcareSiteInvestigator(hcsInv);
		
		assertEquals("Should have found 1 HealthcareSite Investigator",1,investigator.getHealthcareSiteInvestigators().size());
		
	}
	
	/**
	 * Test getLastFirst.
	 * 
	 * @throws Exception the exception
	 */
	public void testGetLastFirst() throws Exception{
		Investigator investigator = new LocalInvestigator();
		investigator.setFirstName("first");
		investigator.setLastName("last");
		
		assertEquals("Investigator name is incorrect or retrieved in wrong order ","last, first",investigator.getLastFirst());
	}
	
	/**
	 * Test compare to.
	 * 
	 * @throws Exception the exception
	 */
	public void testCompareTo() throws Exception{
		Investigator investigator1 = new LocalInvestigator();
		Investigator investigator2 = new LocalInvestigator();
		assertEquals("The two Investigators should be same",0,investigator1.compareTo(investigator2));
		
		ContactMechanism contactMechanism = new ContactMechanism();
		contactMechanism.setType(ContactMechanismType.EMAIL);
		contactMechanism.setValue("john.doe@gmail.com");
		investigator1.getContactMechanisms().add(contactMechanism);
		
		assertEquals("The two Investigators should be different",1,investigator1.compareTo(investigator2));
	}
	
	/**
	 * Test hash code.
	 * 
	 * @throws Exception the exception
	 */
	public void testHashCode() throws Exception{
		Investigator investigator1 = new LocalInvestigator();
		assertEquals("Wrong hash code",31,investigator1.hashCode());
		ContactMechanism contactMechanism = new ContactMechanism();
		contactMechanism.setType(ContactMechanismType.EMAIL);
		contactMechanism.setValue("john.doe@gmail.com");
		investigator1.getContactMechanisms().add(contactMechanism);
		assertEquals("Wrong hash code",31 + investigator1.getEmail().hashCode(),investigator1.hashCode());
	}
	
	/**
	 * Test equals1.
	 * 
	 * @throws Exception the exception
	 */
	public void testEquals1() throws Exception{
		Investigator investigator1 = new LocalInvestigator();
		Investigator investigator2 = new RemoteInvestigator();
		assertFalse("The two Investigators cannot be equal",investigator1.equals(investigator2));
		Investigator investigator3 = new LocalInvestigator();
		assertTrue("The two Investigators should be equal",investigator1.equals(investigator3));
	}
	
	/**
	 * Test equals2.
	 * 
	 * @throws Exception the exception
	 */
	public void testEquals2() throws Exception{
		Investigator investigator1 = new LocalInvestigator();
		Investigator investigator2 = new LocalInvestigator();
		ContactMechanism contactMechanism = new ContactMechanism();
		contactMechanism.setType(ContactMechanismType.EMAIL);
		contactMechanism.setValue("john.doe@gmail.com");
		investigator1.getContactMechanisms().add(contactMechanism);
		
		assertFalse("The two Investigators cannot be equal",investigator1.equals(investigator2));
		
		investigator2.getContactMechanisms().add(contactMechanism);
		assertTrue("The two Investigators should be equal",investigator1.equals(investigator2));
	}
	
	/**
	 * Test add external Investigator.
	 * 
	 * @throws Exception the exception
	 */
	public void testAddExternalInvestigator() throws Exception{
		
		Investigator investigator = new LocalInvestigator();
		assertEquals("Unexpected external Investigator(s)",0,investigator.getExternalInvestigators().size());
		
		investigator.addExternalInvestigator(new RemoteInvestigator());
		assertEquals("Wrong number of external Investigators",1,investigator.getExternalInvestigators().size());
		
	}
	

}
