/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.domain;

import edu.duke.cabig.c3pr.AbstractTestCase;

/**
 * The Class PhoneCallRandomizationTest.
 */
public class PhoneCallRandomizationTest extends AbstractTestCase{
	
	/** The Constant NUM_WITH_HYPHENS. */
	public static final String NUM_WITH_HYPHENS = "111-111-1111";
	
	/** The Constant NUM_WITHOUT_HYPHENS. */
	public static final String NUM_WITHOUT_HYPHENS = "1111111111";
	
	
	/**
	 * Test get phone number string without hyphens.
	 * 
	 * @throws Exception the exception
	 */
	public void testGetPhoneNumberStringWithoutHyphens() throws Exception {
			PhoneCallRandomization phoneCallRandomization = new PhoneCallRandomization();
			phoneCallRandomization.setPhoneNumber(NUM_WITHOUT_HYPHENS);
	
			String retValue = phoneCallRandomization.getPhoneNumberString();
			assertTrue(retValue.equals(NUM_WITH_HYPHENS));
	}

	/**
	 * Test get phone number string with hyphens.
	 * 
	 * @throws Exception the exception
	 */
	public void testGetPhoneNumberStringWithHyphens() throws Exception {
			PhoneCallRandomization phoneCallRandomization = new PhoneCallRandomization();
			phoneCallRandomization.setPhoneNumber(NUM_WITH_HYPHENS);
	
			String retValue = phoneCallRandomization.getPhoneNumberString();
			assertTrue(retValue.equals(NUM_WITH_HYPHENS));
	}
	
	/**
	 * Test get phone number string with Blank.
	 * 
	 * @throws Exception the exception
	 */
	public void testGetPhoneNumberStringWithBlank() throws Exception {
			PhoneCallRandomization phoneCallRandomization = new PhoneCallRandomization();
			phoneCallRandomization.setPhoneNumber("");
	
			String retValue = phoneCallRandomization.getPhoneNumberString();
			assertTrue(retValue.equals(""));
	}
}

