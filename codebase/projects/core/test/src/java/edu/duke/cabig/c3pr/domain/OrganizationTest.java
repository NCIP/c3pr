package edu.duke.cabig.c3pr.domain;

import edu.duke.cabig.c3pr.AbstractTestCase;

public class OrganizationTest extends AbstractTestCase{
	
public void testCompareTo() throws Exception{
		
		Organization organization1 = new LocalHealthcareSite();
		organization1.setName("OrganizationA");

		Organization organization2 = new LocalHealthcareSite();
		organization2.setName("OrganizationB");

		Organization organization3 = new LocalHealthcareSite();
		organization3.setName("OrganizationB");
		
		assertEquals("These 2 organizations cannot be same",1, organization1.compareTo(organization2));
		assertEquals("These 2 organization are same",0, organization2.compareTo(organization3));
	}
	
	public void testEquals() throws Exception{
	        
		Organization organization1 = new LocalHealthcareSite();
		organization1.setName("OrganizationA");
		
		Organization organization2 = organization1;
		assertTrue("These two organizations are equal",organization1.equals(organization2));
		
		Epoch organization3 = new Epoch(); // making sure testing for different objects  
		assertFalse("These two organizations are  not equal",organization1.equals(organization3));
		
		Organization organization4 = new LocalHealthcareSite();
		assertFalse("These two organizations cannot be equal",organization4.equals(organization1));
		
		Organization organization5 = new LocalHealthcareSite();
		organization5.setName("OrganizationA");
		assertTrue("These two organizations are equal",organization1.equals(organization5));
		
		Organization organization6 = new LocalHealthcareSite();
		organization6.setName("OrganizationB");
		assertFalse("These two organizations cannot be equal",organization1.equals(organization6));
	}
	
	public void testGetTrimmedName(){
		Organization organization = new LocalHealthcareSite();
		organization.setName("Duke Hematology/Oncology at Raleigh Community Hospital");
		assertEquals("Trimmed name should be Duke Hematology/Oncology","Duke Hematology/Oncology...", organization.getTrimmedName());
	}
	
    public void testGetEndPointAuthenticationRequired(){
//        return this.getHasEndpointProperty() && this.studyEndPointProperty.getIsAuthenticationRequired() && this.registrationEndPointProperty.getIsAuthenticationRequired();
    }
    
    public void testSetEndPointAuthenticationRequired() {
//        this.studyEndPointProperty.setIsAuthenticationRequired(endPointAuthenticationRequired);
//        this.registrationEndPointProperty.setIsAuthenticationRequired(endPointAuthenticationRequired);
    }
    
    public void testGetHasEndpointProperty(){
    	Organization organization = new LocalHealthcareSite();
    	assertFalse("this organization doesnt have end point property", organization.getHasEndpointProperty());
    	
    	
    	//        return studyEndPointProperty!=null && registrationEndPointProperty!=null;
    }
    
    public void testInitializeEndPointProperties(){
//        registrationEndPointProperty=new EndPointConnectionProperty(endPointType);
//        studyEndPointProperty=new EndPointConnectionProperty(endPointType);
    }

}
