package edu.duke.cabig.c3pr.domain;

import java.util.ArrayList;
import java.util.List;

import edu.duke.cabig.c3pr.AbstractTestCase;
import edu.duke.cabig.c3pr.constants.ContactMechanismType;

// TODO: Auto-generated Javadoc
/**
 * The Class ResearchStaffTest.
 */
public class ResearchStaffTest extends AbstractTestCase{
	
	/* (non-Javadoc)
	 * @see edu.nwu.bioinformatics.commons.testing.CoreTestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		super.setUp();
	}
	
	/**
	 * Test set external ResearchStaff.
	 * 
	 * @throws Exception the exception
	 */
	public void testSetExternalResearchStaffs() throws Exception{
		
		ResearchStaff ResearchStaff = new LocalResearchStaff();
		assertEquals("Unexpected external research staff",0,ResearchStaff.getExternalResearchStaff().size());
		
		List<ResearchStaff> externalResearchStaffs = new ArrayList<ResearchStaff>();
		externalResearchStaffs.add(new LocalResearchStaff());
		
		ResearchStaff.setExternalResearchStaff(externalResearchStaffs);
		assertEquals("Wrong number of research staff",1,ResearchStaff.getExternalResearchStaff().size());
	}
	
	/**
	 * Test getLastFirst.
	 * 
	 * @throws Exception the exception
	 */
	public void testGetLastFirst() throws Exception{
		ResearchStaff researchStaff = new LocalResearchStaff();
		researchStaff.setFirstName("first");
		researchStaff.setLastName("last");
		
		assertEquals("ResearchStaff name is incorrect or retrieved in wrong order ","last, first",researchStaff.getLastFirst());
	}
	
	/**
	 * Test compare to.
	 * 
	 * @throws Exception the exception
	 */
	public void testCompareTo() throws Exception{
		ResearchStaff researchStaff1 = new LocalResearchStaff();
		ResearchStaff researchStaff2 = new LocalResearchStaff();
		assertEquals("The two research staff personnel should be same",0,researchStaff1.compareTo(researchStaff2));
		
		researchStaff1.setAssignedIdentifier("testAssignedId");
		assertEquals("The two research staff personnel should be different",1,researchStaff1.compareTo(researchStaff2));
	}
	
	/**
	 * Test hash code.
	 * 
	 * @throws Exception the exception
	 */
	public void testHashCode() throws Exception{
		ResearchStaff researchStaff1 = new LocalResearchStaff();
		assertEquals("Wrong hash code",31,researchStaff1.hashCode());
		ContactMechanism contactMechanism = new ContactMechanism();
		contactMechanism.setType(ContactMechanismType.EMAIL);
		contactMechanism.setValue("john.doe@gmail.com");
		researchStaff1.getContactMechanisms().add(contactMechanism);
		assertEquals("Wrong hash code",31 + researchStaff1.getEmail().hashCode(),researchStaff1.hashCode());
	}
	
	/**
	 * Test equals1.
	 * 
	 * @throws Exception the exception
	 */
	public void testEquals1() throws Exception{
		ResearchStaff researchStaff1 = new LocalResearchStaff();
		ResearchStaff researchStaff2 = new RemoteResearchStaff();
		assertFalse("The two research staff cannot be equal",researchStaff1.equals(researchStaff2));
		ResearchStaff researchStaff3 = new LocalResearchStaff();
		assertTrue("The two research staff should be equal",researchStaff1.equals(researchStaff3));
	}
	
	/**
	 * Test equals2.
	 * 
	 * @throws Exception the exception
	 */
	public void testEquals2() throws Exception{
		ResearchStaff researchStaff1 = new LocalResearchStaff();
		ResearchStaff researchStaff2 = new LocalResearchStaff();
		
		researchStaff1.setAssignedIdentifier("testAssignedId");
		
		assertFalse("The two ResearchStaffs cannot be equal",researchStaff1.equals(researchStaff2));
		
		researchStaff2.setAssignedIdentifier("testAssignedId");
		assertTrue("The two ResearchStaffs should be equal",researchStaff1.equals(researchStaff2));
	}
	
	/**
	 * Test add external ResearchStaff.
	 * 
	 * @throws Exception the exception
	 */
	public void testAddExternalResearchStaff() throws Exception{
		
		ResearchStaff researchStaff = new LocalResearchStaff();
		assertEquals("Unexpected external research staff",0,researchStaff.getExternalResearchStaff().size());
		
		researchStaff.addExternalResearchStaff(new RemoteResearchStaff());
		assertEquals("Wrong number of external research staff",1,researchStaff.getExternalResearchStaff().size());
		
	}
	
	/**
	 * Test add study personnel.
	 * 
	 * @throws Exception the exception
	 */
	public void testAddStudyPersonnel() throws Exception{
		
		ResearchStaff researchStaff = new LocalResearchStaff();
		StudyPersonnel studyPersonnel = new StudyPersonnel();
		studyPersonnel.setRoleCode("Study Coordinator");
		assertEquals("Unexpected study personnel",0,researchStaff.getStudyPersonnels().size());
		
		researchStaff.addStudyPersonnel(studyPersonnel);
		assertEquals("Wrong number of study personnel",1,researchStaff.getStudyPersonnels().size());
		assertEquals("Wrong study personnel",1,researchStaff.getStudyPersonnels().size());
		
	}
	
	

}
