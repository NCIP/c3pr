/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.dao;

import com.semanticbits.coppasimulator.util.CoppaPAObjectFactory;

import junit.framework.TestCase;

public class CoppaPAObjectFactoryTest extends TestCase {

	
	public void testGetStudyProtocol(){
		
		String responseXml = CoppaPAObjectFactory.getStudyProtocolSearchXML("evalu", null, null);
		assertNotNull(responseXml);
	}
}
