/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.domain;

import org.easymock.EasyMock;

import edu.duke.cabig.c3pr.AbstractTestCase;
import edu.duke.cabig.c3pr.constants.EndPointType;

/**
 * The Class OrganizationTest.
 */
public class OrganizationTest extends AbstractTestCase{
	
	/**
	 * Test compare to.
	 * 
	 */
	public void testCompareTo1() {
		
		Organization organization1 = new LocalHealthcareSite();
		organization1.setName("OrganizationA");

		Organization organization2 = new LocalHealthcareSite();
		organization2.setName("OrganizationB");
		
		assertEquals("These 2 organizations cannot be same",1, organization1.compareTo(organization2));
	}
	
	/**
	 * Test compare to.
	 * 
	 */
	public void testCompareTo2() {

		Organization organization2 = new LocalHealthcareSite();
		organization2.setName("OrganizationB");

		Organization organization3 = new LocalHealthcareSite();
		organization3.setName("OrganizationB");
		
		assertEquals("These 2 organization are same",0, organization2.compareTo(organization3));
	}
	
	/**
	 * Test equals1.
	 * 
	 */
	public void testEquals1() {
	        
		Organization organization1 = new LocalHealthcareSite();
		organization1.setName("OrganizationA");
		
		Organization organization2 = organization1;
		assertTrue("These two organizations are equal",organization1.equals(organization2));
		
	}
	
	/**
	 * Test equals2.
	 * 
	 */
	public void testEquals2() {
        
		Organization organization1 = new LocalHealthcareSite();
		organization1.setName("OrganizationA");
		
		Epoch organization3 = new Epoch(); // making sure testing for different objects  
		assertFalse("These two organizations are  not equal",organization1.equals(organization3));
	}
	
	/**
	 * Test equals3.
	 * 
	 */
	public void testEquals3() {
        
		Organization organization1 = new LocalHealthcareSite();
		organization1.setName("OrganizationA");
		
		Organization organization4 = new LocalHealthcareSite();
		assertFalse("These two organizations cannot be equal",organization4.equals(organization1));
	}
	
	/**
	 * Test equals4.
	 * 
	 */
	public void testEquals4() {
        
		Organization organization1 = new LocalHealthcareSite();
		organization1.setName("OrganizationA");
		
		Organization organization5 = new LocalHealthcareSite();
		organization5.setName("OrganizationA");
		assertTrue("These two organizations are equal",organization1.equals(organization5));
	}
	
	/**
	 * Test equals5.
	 * 
	 */
	public void testEquals5() {
        
		Organization organization1 = new LocalHealthcareSite();
		organization1.setName("OrganizationA");
		
		Organization organization6 = new LocalHealthcareSite();
		organization6.setName("OrganizationB");
		assertFalse("These two organizations cannot be equal",organization1.equals(organization6));
	}
	
	/**
	 * Test get trimmed name.
	 */
	public void testGetTrimmedName(){
		Organization organization = new LocalHealthcareSite();
		organization.setName("Duke Hematology/Oncology at Raleigh Community Hospital");
		assertEquals("Trimmed name should be Duke Hematology/Oncology","Duke Hematology/Oncology...", organization.getTrimmedName());
	}
	
    /**
     * Test get has endpoint property.
     */
    public void testGetHasEndpointProperty(){
    	Organization organization = new LocalHealthcareSite();
    	assertFalse("this organization doesnt have end point property", organization.getHasEndpointProperty());
    }
    
    /**
     * Test get has endpoint property.
     */
    public void testGetHasEndpointProperty1(){
    	EndPointConnectionProperty endPointProperty = registerMockFor(EndPointConnectionProperty.class);
    	Organization organization = new LocalHealthcareSite();
    	organization.setStudyEndPointProperty(endPointProperty);
    	assertFalse("this organization doesnt have end point property", organization.getHasEndpointProperty());
    }


    /**
     * Test get has endpoint property.
     */
    public void testGetHasEndpointProperty2(){
    	EndPointConnectionProperty endPointProperty = registerMockFor(EndPointConnectionProperty.class);
    	Organization organization = new LocalHealthcareSite();
    	organization.setRegistrationEndPointProperty(endPointProperty);
    	assertFalse("this organization doesnt have end point property", organization.getHasEndpointProperty());
    }
    
    /**
     * Test get has endpoint property.
     */
    public void testGetHasEndpointProperty3(){
    	EndPointConnectionProperty studyEndPointProperty = registerMockFor(EndPointConnectionProperty.class);
    	EndPointConnectionProperty regEndPointProperty = registerMockFor(EndPointConnectionProperty.class);
    	Organization organization = new LocalHealthcareSite();
    	organization.setStudyEndPointProperty(studyEndPointProperty);
    	organization.setRegistrationEndPointProperty(regEndPointProperty);
    	assertTrue("this organization has end point property", organization.getHasEndpointProperty());
    }

	
    /**
     * Test get end point authentication required.
     */
    public void testGetEndPointAuthenticationRequired(){
    	EndPointConnectionProperty studyEndPointProperty = registerMockFor(EndPointConnectionProperty.class);
    	EndPointConnectionProperty regEndPointProperty = registerMockFor(EndPointConnectionProperty.class);
    	
    	Organization organization = new LocalHealthcareSite();
    	organization.setStudyEndPointProperty(studyEndPointProperty);
    	organization.setRegistrationEndPointProperty(regEndPointProperty);
    	
    	EasyMock.expect(studyEndPointProperty.getIsAuthenticationRequired()).andReturn(true);
    	EasyMock.expect(regEndPointProperty.getIsAuthenticationRequired()).andReturn(false);

    	replayMocks();
    	
    	assertFalse("this organization end point doesn't require authentication", organization.getEndPointAuthenticationRequired());
    	
    	verifyMocks();
    }

    /**
     * Test get end point authentication required.
     */
    public void testGetEndPointAuthenticationRequired1(){
    	EndPointConnectionProperty studyEndPointProperty = registerMockFor(EndPointConnectionProperty.class);
    	EndPointConnectionProperty regEndPointProperty = registerMockFor(EndPointConnectionProperty.class);
    	
    	Organization organization = new LocalHealthcareSite();
    	organization.setStudyEndPointProperty(studyEndPointProperty);
    	organization.setRegistrationEndPointProperty(regEndPointProperty);
    	
    	EasyMock.expect(studyEndPointProperty.getIsAuthenticationRequired()).andReturn(true);
    	EasyMock.expect(regEndPointProperty.getIsAuthenticationRequired()).andReturn(true);

    	replayMocks();
    	
    	assertTrue("this organization end point require authentication", organization.getEndPointAuthenticationRequired());
    	
    	verifyMocks();
    }

    /**
     * Test set end point authentication required.
     * this test case seems unnecessary.
     */
    public void testSetEndPointAuthenticationRequired() {
    	EndPointConnectionProperty studyEndPointProperty = registerMockFor(EndPointConnectionProperty.class);
    	EndPointConnectionProperty regEndPointProperty = registerMockFor(EndPointConnectionProperty.class);
    	studyEndPointProperty.setIsAuthenticationRequired(true);
    	regEndPointProperty.setIsAuthenticationRequired(true);
    	Organization organization = new LocalHealthcareSite();
    	organization.setStudyEndPointProperty(studyEndPointProperty);
    	replayMocks();
    	organization.setRegistrationEndPointProperty(regEndPointProperty);
    	organization.setEndPointAuthenticationRequired(true);
    	verifyMocks();
    }
    
    /**
     * Test set end point authentication required.
     * this test case seems unnecessary.
     */
    public void testSetEndPointAuthenticationRequired1() {
    	EndPointConnectionProperty studyEndPointProperty = registerMockFor(EndPointConnectionProperty.class);
    	EndPointConnectionProperty regEndPointProperty = registerMockFor(EndPointConnectionProperty.class);
    	studyEndPointProperty.setIsAuthenticationRequired(false);
    	regEndPointProperty.setIsAuthenticationRequired(false);
    	Organization organization = new LocalHealthcareSite();
    	organization.setStudyEndPointProperty(studyEndPointProperty);
    	organization.setRegistrationEndPointProperty(regEndPointProperty);
    	replayMocks();
    	organization.setEndPointAuthenticationRequired(false);
    	verifyMocks();
    }

    /**
     * Test initialize end point properties.
     */
    public void testInitializeEndPointProperties(){
    		EndPointConnectionProperty studyEndPointProperty = registerMockFor(EndPointConnectionProperty.class);
        	EndPointConnectionProperty regEndPointProperty = registerMockFor(EndPointConnectionProperty.class);
        	
        	Organization organization = new LocalHealthcareSite();
        	replayMocks();
        	organization.initializeEndPointProperties(EndPointType.GRID);
        	assertNotNull("studyEndPointProperty should not be null", organization.getStudyEndPointProperty());
        	assertNotNull("regEndPointProperty should not be null", organization.getRegistrationEndPointProperty());
        	verifyMocks();
    }

}
