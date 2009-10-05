package edu.duke.cabig.c3pr.dao;

import com.semanticbits.coppasimulator.util.CoppaPAObjectFactory;

import junit.framework.TestCase;

public class CoppaPAObjectFactoryTest extends TestCase {

	
	public void testGetStudyProtocol(){
		
		String responseXml = CoppaPAObjectFactory.getStudyProtocolSearchXML("evalu", null, null);
		assertNotNull(responseXml);
	}
}
