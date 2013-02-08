/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.domain;

import java.util.Date;

import edu.duke.cabig.c3pr.AbstractTestCase;
import edu.duke.cabig.c3pr.utils.DateUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class InvestigatorTest.
 */
public class SiteInvestigatorGroupAffiliationTest extends AbstractTestCase{
	
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
		SiteInvestigatorGroupAffiliation investigatorGroup1Affiliation1 = new SiteInvestigatorGroupAffiliation();
		SiteInvestigatorGroupAffiliation investigatorGroupAffiliation2 = new SiteInvestigatorGroupAffiliation();
		assertEquals("The two affiliations should be same",0,investigatorGroup1Affiliation1.compareTo(investigatorGroupAffiliation2));
		
		InvestigatorGroup invGroup1 = new InvestigatorGroup();
		investigatorGroup1Affiliation1.setInvestigatorGroup(invGroup1);
		assertEquals("The two affiliations should be different",1,investigatorGroup1Affiliation1.compareTo(investigatorGroupAffiliation2));
		
		investigatorGroupAffiliation2.setInvestigatorGroup(invGroup1);
		assertEquals("The two affiliations should be same",0,investigatorGroup1Affiliation1.compareTo(investigatorGroupAffiliation2));
	}
	
	/**
	 * Test hash code.
	 * 
	 * @throws Exception the exception
	 */
	public void testHashCode() throws Exception{
		int prime = 31;
		SiteInvestigatorGroupAffiliation siteInvestigatorGroupAffiliation = new SiteInvestigatorGroupAffiliation();
		assertEquals("Wrong hash code",prime*prime,siteInvestigatorGroupAffiliation.hashCode());
		InvestigatorGroup invGroup= new InvestigatorGroup();
		siteInvestigatorGroupAffiliation.setInvestigatorGroup(invGroup);
		assertEquals("Wrong hash code",prime*prime + invGroup.hashCode(),siteInvestigatorGroupAffiliation.hashCode());
	}
	
	/**
	 * Test equals1.
	 * 
	 * @throws Exception the exception
	 */
	public void testEquals1() throws Exception{
		SiteInvestigatorGroupAffiliation investigatorAffiliation1 = new SiteInvestigatorGroupAffiliation();
		SiteInvestigatorGroupAffiliation investigatorAffiliation2 = new SiteInvestigatorGroupAffiliation();
		assertTrue("The two investigator groups should be equal",investigatorAffiliation1.equals(investigatorAffiliation2));
		
		HealthcareSiteInvestigator hcsInv = new HealthcareSiteInvestigator();
		investigatorAffiliation2.setHealthcareSiteInvestigator(hcsInv);
		assertFalse("The two investigator groups cannot be equal",investigatorAffiliation1.equals(investigatorAffiliation2));
		
		investigatorAffiliation1.setHealthcareSiteInvestigator(hcsInv);
		assertTrue("The two investigator groups cannot be equal",investigatorAffiliation1.equals(investigatorAffiliation2));
	}
	
	
	/**
	 * Test get start date str.
	 * 
	 * @throws Exception the exception
	 */
	public void testGetStartDateStr() throws Exception{
		
		SiteInvestigatorGroupAffiliation siteInvestigatorGroupAffiliation = new SiteInvestigatorGroupAffiliation();
		assertEquals("Wrong start date","",siteInvestigatorGroupAffiliation.getStartDateStr());
		Date startDate = new Date();
		siteInvestigatorGroupAffiliation.setStartDate(new Date());
		assertEquals("Wrong start date",DateUtil.formatDate(startDate, "MM/dd/yyyy"),siteInvestigatorGroupAffiliation.getStartDateStr());
	}
	
	/**
	 * Test get end date str.
	 * 
	 * @throws Exception the exception
	 */
	public void testGetEndDateStr() throws Exception{
		
		SiteInvestigatorGroupAffiliation siteInvestigatorGroupAffiliation = new SiteInvestigatorGroupAffiliation();
		assertEquals("Wrong end date","",siteInvestigatorGroupAffiliation.getStartDateStr());
		Date endDate = new Date();
		siteInvestigatorGroupAffiliation.setEndDate(new Date());
		assertEquals("Wrong end date",DateUtil.formatDate(endDate, "MM/dd/yyyy"),siteInvestigatorGroupAffiliation.getEndDateStr());
	}
	
}
