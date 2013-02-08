/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.domain;

import java.util.Date;

import edu.duke.cabig.c3pr.AbstractTestCase;
import edu.duke.cabig.c3pr.utils.DateUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class InvestigatorTest.
 */
public class InvestigatorGroupTest extends AbstractTestCase{
	
	/* (non-Javadoc)
	 * @see edu.nwu.bioinformatics.commons.testing.CoreTestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		super.setUp();
	}
	
	/**
	 * Test compare to.
	 * 
	 * @throws Exception the exception
	 */
	public void testCompareTo() throws Exception{
		InvestigatorGroup investigatorGroup11 = new InvestigatorGroup();
		InvestigatorGroup investigatorGroup2 = new InvestigatorGroup();
		assertEquals("The two Investigators should be same",0,investigatorGroup11.compareTo(investigatorGroup2));
		
		investigatorGroup11.setName("ABDOMINAL CANCER");
		assertEquals("The two Investigators should be different",1,investigatorGroup11.compareTo(investigatorGroup2));
		
		investigatorGroup2.setName("ABDOMINAL CANCER");
		assertEquals("The two Investigators should be same",0,investigatorGroup11.compareTo(investigatorGroup2));
	}
	
	/**
	 * Test hash code.
	 * 
	 * @throws Exception the exception
	 */
	public void testHashCode() throws Exception{
		InvestigatorGroup investigatorGroup = new InvestigatorGroup();
		assertEquals("Wrong hash code",31,investigatorGroup.hashCode());
		String name= "STEM CELL Group";
		investigatorGroup.setName(name);
		assertEquals("Wrong hash code",31 + name.hashCode(),investigatorGroup.hashCode());
	}
	
	/**
	 * Test equals1.
	 * 
	 * @throws Exception the exception
	 */
	public void testEquals1() throws Exception{
		InvestigatorGroup investigator1 = new InvestigatorGroup();
		InvestigatorGroup investigator2 = new InvestigatorGroup();
		assertTrue("The two investigator groups should be equal",investigator1.equals(investigator2));
		
		investigator2.setName("ONCOLOGY");
		assertFalse("The two investigator groups cannot be equal",investigator1.equals(investigator2));
	}
	
	/**
	 * Test equals2.
	 * 
	 * @throws Exception the exception
	 */
	public void testEquals2() throws Exception{
		InvestigatorGroup investigatorGroup1 = new InvestigatorGroup();
		InvestigatorGroup investigatorGroup2 = new InvestigatorGroup();
		
		investigatorGroup1.setName("ONCOLOGY");
		investigatorGroup2.setName("RADIATION THERAPY");
		assertFalse("The two investigator groups cannot be equal",investigatorGroup1.equals(investigatorGroup2));
		investigatorGroup2.setName("ONCOLOGY");
		
		assertTrue("The two Investigators should be equal",investigatorGroup1.equals(investigatorGroup2));
	}
	
	public void testGetStartDateStr() throws Exception{
		
		InvestigatorGroup investigatorGroup = new InvestigatorGroup();
		assertEquals("Wrong start date","",investigatorGroup.getStartDateStr());
		Date startDate = new Date();
		investigatorGroup.setStartDate(new Date());
		assertEquals("Wrong start date",DateUtil.formatDate(startDate, "MM/dd/yyyy"),investigatorGroup.getStartDateStr());
	}
	
	/**
	 * Test get end date str.
	 * 
	 * @throws Exception the exception
	 */
	public void testGetEndDateStr() throws Exception{
		
		InvestigatorGroup investigatorGroup = new InvestigatorGroup();
		assertEquals("Wrong end date","",investigatorGroup.getStartDateStr());
		Date endDate = new Date();
		investigatorGroup.setEndDate(new Date());
		assertEquals("Wrong end date",DateUtil.formatDate(endDate, "MM/dd/yyyy"),investigatorGroup.getEndDateStr());
	}
	
}
