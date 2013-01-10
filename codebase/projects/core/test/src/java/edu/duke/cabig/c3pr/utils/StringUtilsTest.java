/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.utils;

import edu.duke.cabig.c3pr.AbstractTestCase;

public class StringUtilsTest extends AbstractTestCase {

	public void testIsValidPhoneValid1(){
		String phone = "111-222-3333";
		assertTrue(StringUtils.isValidPhone(phone));
	}
	
	public void testIsValidPhoneValid2(){
		String phone = "1-111-222-3333";
		assertTrue(StringUtils.isValidPhone(phone));
	}
	
	public void testIsValidPhoneValid3(){
		String phone = "1 111 222 3333";
		assertTrue(StringUtils.isValidPhone(phone));
	}
	
	public void testIsValidPhoneValid4(){
		String phone = "1112223333";
		assertTrue(StringUtils.isValidPhone(phone));
	}
	
	public void testIsValidPhoneWithExtValid(){
		String phone = "111-222-3333-ext-123";
		assertTrue(StringUtils.isValidPhone(phone));
	}
	
	public void testIsValidPhoneWithExtValid1(){
		String phone = "111-222-3333 ext-123";
		assertTrue(StringUtils.isValidPhone(phone));
	}
	
	public void testIsValidPhoneWithExtValid2(){
		String phone = "111 222 3333 ext 123";
		assertTrue(StringUtils.isValidPhone(phone));
	}
	
	public void testIsValidPhoneValid5(){
		String phone = "111222-3333";
		assertTrue(StringUtils.isValidPhone(phone));
	}
	
	public void testIsValidPhoneInValid2(){
		String phone = "11-1-222-3333";
		assertFalse(StringUtils.isValidPhone(phone));
	}
	
	
	public void testIsValidPhoneWithExtValid4(){
		String phone = "111-222-3333  ext-12";
		assertTrue(StringUtils.isValidPhone(phone));
	}
	
	public void testIsValidPhoneWithExtValid3(){
		String phone = "(301)-251-1161-x-194";
		assertTrue(StringUtils.isValidPhone(phone));
	}
	
	public void testIsValidPhoneWithExtInValid2(){
		String phone = "111-222-3333-ext-";
		assertFalse(StringUtils.isValidPhone(phone));
	}
	
	public void testIsValidPhoneInValid3(){
		String phone = "222-3333";
		assertFalse(StringUtils.isValidPhone(phone));
	}
	
}
