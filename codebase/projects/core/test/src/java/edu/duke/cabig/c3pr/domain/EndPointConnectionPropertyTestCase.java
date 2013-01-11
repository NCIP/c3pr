/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.domain;

import edu.duke.cabig.c3pr.constants.EndPointType;
import junit.framework.TestCase;

public class EndPointConnectionPropertyTestCase extends TestCase {

	private EndPointConnectionProperty endPointConnectionProperty;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		endPointConnectionProperty= new EndPointConnectionProperty();
	}
	
	/**
	 * Test is end point defined.
	 * endpointType: null.
	 */
	public void testIsEndPointDefinedNullEndpointType(){
		assertFalse(endPointConnectionProperty.isEndPointDefined());
	}
	
	/**
	 * Test is end point defined.
	 * URL: null.
	 */
	public void testIsEndPointDefinedNullURL(){
		endPointConnectionProperty.setEndPointType(EndPointType.GRID);
		assertFalse(endPointConnectionProperty.isEndPointDefined());
	}
	
	/**
	 * Test is end point defined.
	 * URL: null.
	 */
	public void testIsEndPointDefinedEmptyURL(){
		endPointConnectionProperty.setEndPointType(EndPointType.GRID);
		endPointConnectionProperty.setUrl("");
		assertFalse(endPointConnectionProperty.isEndPointDefined());
	}
	
	/**
	 * Test is end point defined.
	 * URL: not null, not empty.
	 * endpointType: not null
	 */
	public void testIsEndPointDefined(){
		endPointConnectionProperty.setEndPointType(EndPointType.GRID);
		endPointConnectionProperty.setUrl("some");
		assertTrue(endPointConnectionProperty.isEndPointDefined());
	}
	
}
