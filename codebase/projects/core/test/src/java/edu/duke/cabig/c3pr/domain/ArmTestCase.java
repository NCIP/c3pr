/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.domain;

import org.easymock.classextension.EasyMock;

import edu.duke.cabig.c3pr.AbstractTestCase;

// TODO: Auto-generated Javadoc
/**
 * The Class ArmTestCase.
 */
public class ArmTestCase extends AbstractTestCase{

	/**
	 * Test hash code.
	 */
	public void testHashCode(){
		Arm arm1= new Arm();
		Arm arm2= new Arm();
		arm1.setName("Test");
		arm2.setName("Test");
		assertTrue(arm1.hashCode()==arm2.hashCode());
	}

	/**
	 * Test equals for arms with same reference.
	 */
	public void testEqualsSameReference(){
		Arm arm1= new Arm();
		Arm arm2= arm1;
		arm1.setName("Test");
		assertTrue(arm1.equals(arm2));
	}

	/**
	 * Test equals for arms with different class types.
	 */
	public void testEqualsDifferentClassTypes(){
		Arm arm1= new Arm();
		Arm arm2= new ArmSubClass();
		arm1.setName("Test");
		arm2.setName("Test");
		assertFalse(arm1.equals(arm2));
	}

	/**
	 * Test equals with one arm having null name.
	 */
	public void testEqualsNullName(){
		Arm arm1= new Arm();
		Arm arm2= new Arm();
		arm2.setName("Test");
		assertFalse(arm1.equals(arm2));
	}

	/**
	 * Test equals for arms with different names.
	 */
	public void testEqualsDifferentName(){
		Arm arm1= new Arm();
		Arm arm2= new Arm();
		arm2.setName("Test");
		assertFalse(arm1.equals(arm2));
	}

	/**
	 * Test equals for arms with same name.
	 */
	public void testEqualsSameName(){
		Arm arm1= new Arm();
		Arm arm2= new Arm();
		arm2.setName("Test");
		arm1.setName("Test");
		assertTrue(arm1.equals(arm2));
	}

	/**
	 * The Class ArmSubClass.
	 */
	class ArmSubClass extends Arm{

	}

}
