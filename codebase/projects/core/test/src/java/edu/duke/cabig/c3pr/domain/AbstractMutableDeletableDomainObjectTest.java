package edu.duke.cabig.c3pr.domain;

import org.hibernate.validator.AssertFalse;

import edu.duke.cabig.c3pr.AbstractTestCase;

public class AbstractMutableDeletableDomainObjectTest extends AbstractTestCase{

	class TestAbstractMutableDeletableDomainObject extends AbstractMutableDeletableDomainObject{
		
	}
	/*
	 * this method test equals method
	 * this will check equality, id other obj is null
	 */
	public void testEquals(){
		AbstractMutableDeletableDomainObject  obj1 = new AbstractMutableDeletableDomainObject();
		assertFalse("objects are not same because comparing with null",obj1.equals(null));
	}
	
	/*
	 * this method test equals method
	 * this will check equality based on retired indicator.
	 */
	public void testEquals1(){
		AbstractMutableDeletableDomainObject  obj1 = new AbstractMutableDeletableDomainObject();
		AbstractMutableDeletableDomainObject  obj2 = new AbstractMutableDeletableDomainObject();
		
		obj2.setRetiredIndicatorAsTrue();
		assertFalse("objects are not same because obj1 has null retired indicator while obj2 has true",obj1.equals(obj2));
		
	}


	/*
	 * this method test equals method
	 * this will check equality based on retired indicator.
	 */
	public void testEquals2(){
		AbstractMutableDeletableDomainObject  obj1 = new AbstractMutableDeletableDomainObject();
		AbstractMutableDeletableDomainObject  obj2 = new AbstractMutableDeletableDomainObject();
		obj1.setRetiredIndicatorAsFalse();
		obj2.setRetiredIndicatorAsTrue();
		assertFalse("objects are not same because obj1 has false retired indicator while obj2 has true",obj1.equals(obj2));
		
	}
	

	/*
	 * this method test equals method
	 * this will check equality based on retired indicator.
	 */
	public void testEquals3(){
		AbstractMutableDeletableDomainObject  obj1 = new AbstractMutableDeletableDomainObject();
		AbstractMutableDeletableDomainObject  obj2 = new AbstractMutableDeletableDomainObject();
		obj1.setRetiredIndicatorAsTrue();
		obj2.setRetiredIndicatorAsTrue();
		assertTrue("objects are same because retired indictaor is true for both objects",obj1.equals(obj2));
		
	}



}




