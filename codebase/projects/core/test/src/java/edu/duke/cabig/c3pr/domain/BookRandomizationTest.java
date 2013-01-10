/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.domain;

import edu.duke.cabig.c3pr.AbstractTestCase;

/**
 * The Class BookRandomizationTest.
 */
public class BookRandomizationTest extends AbstractTestCase{
	
	
	/**
	 * Test set retired indicator as true.
	 */
	public void testSetRetiredIndicatorAsTrue(){
		
		BookRandomization bookRandomization = new BookRandomization();
		BookRandomizationEntry bookRandomizationEntry = new BookRandomizationEntry();

		bookRandomization.getBookRandomizationEntry().add(bookRandomizationEntry);
		
		bookRandomization.setRetiredIndicatorAsTrue();
		assertEquals(bookRandomization.getBookRandomizationEntry().get(0).getRetiredIndicator(), "true");
	}
	
}

