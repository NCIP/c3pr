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
	
	public void testIsValidPhoneWithExtValid2(){
		String phone = "111 222 3333 ext 123";
		assertTrue(StringUtils.isValidPhone(phone));
	}
	
	public void testIsValidPhoneInValid1(){
		String phone = "111222-3333";
		assertFalse(StringUtils.isValidPhone(phone));
	}
	
	public void testIsValidPhoneInValid2(){
		String phone = "11-1-222-3333";
		assertFalse(StringUtils.isValidPhone(phone));
	}
	
	
	public void testIsValidPhoneWithExtInValid1(){
		String phone = "111-222-3333  ext-12";
		assertFalse(StringUtils.isValidPhone(phone));
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