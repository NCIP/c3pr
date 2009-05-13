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

