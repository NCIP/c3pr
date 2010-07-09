package edu.duke.cabig.c3pr.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.easymock.classextension.EasyMock;

import edu.duke.cabig.c3pr.AbstractTestCase;
import edu.duke.cabig.c3pr.constants.APIName;
import edu.duke.cabig.c3pr.constants.ServiceName;
import edu.duke.cabig.c3pr.constants.WorkFlowStatusType;

// TODO: Auto-generated Javadoc
/**
 * The Class InteroperableAbstractMutableDeletableDomainObjectTestCase.
 */
public class InteroperableMutableDeletableDomainObjectTestCase extends AbstractTestCase {

	/** The test object. */
	private InteroperableAbstractMutableDeletableDomainObject iAMDDObject;
	
	/** The endpoints local. */
	private List<EndPoint> endpointsLocal;
	
	/* (non-Javadoc)
	 * @see edu.nwu.bioinformatics.commons.testing.CoreTestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		iAMDDObject=new InteroperableAbstractMutableDeletableDomainObject()
		{
			@Override
			public List<EndPoint> getEndpoints() {
				// TODO Auto-generated method stub
				return endpointsLocal;
			}
		};
		endpointsLocal= new ArrayList<EndPoint>();
	}
	
	/**
	 * Test get import error string null string.
	 */
	public void testGetImportErrorStringNullString(){
		assertNull(iAMDDObject.getImportErrorString());
	}
	

	/**
	 * Test get import error, string size=200.
	 */
	public void testGetImportErrorStringSize200(){
		char[] someString=new char[200];
		Arrays.fill(someString, 'a');
		iAMDDObject.setImportErrorString(new String(someString));
		assertEquals(100, iAMDDObject.getImportErrorString().length());
	}
	
	/**
	 * Test get import error string.
	 */
	public void testGetImportErrorString(){
		iAMDDObject.setImportErrorString("Test");
		assertEquals("Test", iAMDDObject.getImportErrorString());
	}
	
	/**
	 * Test set import error string.
	 */
	public void testSetImportErrorString(){
		assertFalse(iAMDDObject.isImportErrorFlag());
		iAMDDObject.setImportErrorString("Test");
		assertTrue(iAMDDObject.isImportErrorFlag());
		assertEquals("Test", iAMDDObject.getImportErrorString());
	}
	
	/**
	 * Test get endpoint.
	 */
	public void testGetEndpoint(){
		EndPoint endPoint1= registerMockFor(EndPoint.class);
		EndPoint endPoint2= registerMockFor(EndPoint.class);
		EndPoint endPoint3= registerMockFor(EndPoint.class);
		endpointsLocal.add(endPoint1);
		endpointsLocal.add(endPoint2);
		endpointsLocal.add(endPoint3);
		iAMDDObject.setEndpoints(endpointsLocal);
		EasyMock.expect(endPoint1.getServiceName()).andReturn(ServiceName.REGISTRATION);
		EasyMock.expect(endPoint1.getApiName()).andReturn(APIName.CHANGE_EPOCH);
		EasyMock.expect(endPoint2.getServiceName()).andReturn(ServiceName.REGISTRATION);
		EasyMock.expect(endPoint2.getApiName()).andReturn(APIName.ENROLL_SUBJECT);
		EasyMock.expect(endPoint3.getServiceName()).andReturn(ServiceName.REGISTRATION);
		EasyMock.expect(endPoint3.getApiName()).andReturn(APIName.PUT_SUBJECT_OFF_STUDY);
		replayMocks();
		assertNotNull(iAMDDObject.getEndPoint(ServiceName.REGISTRATION, APIName.PUT_SUBJECT_OFF_STUDY));
		verifyMocks();
	}
	
	/**
	 * Test get endpoint not found.
	 */
	public void testGetEndpointNotFound(){
		EndPoint endPoint1= registerMockFor(EndPoint.class);
		EndPoint endPoint2= registerMockFor(EndPoint.class);
		endpointsLocal.add(endPoint1);
		endpointsLocal.add(endPoint2);
		iAMDDObject.setEndpoints(endpointsLocal);
		EasyMock.expect(endPoint1.getServiceName()).andReturn(ServiceName.REGISTRATION);
		EasyMock.expect(endPoint1.getApiName()).andReturn(APIName.CHANGE_EPOCH);
		EasyMock.expect(endPoint2.getServiceName()).andReturn(ServiceName.REGISTRATION);
		EasyMock.expect(endPoint2.getApiName()).andReturn(APIName.ENROLL_SUBJECT);
		replayMocks();
		assertNull(iAMDDObject.getEndPoint(ServiceName.REGISTRATION, APIName.PUT_SUBJECT_OFF_STUDY));
		verifyMocks();
	}
	
	/**
	 * Test get last attempted endpoint.
	 */
	public void testGetLastAttemptedEndpoint(){
		EndPoint endPoint1= new GridEndPoint();
		EndPoint endPoint2= new GridEndPoint();
		Date date=new Date();
		endPoint1.setAttemptDate(date);
		endPoint2.setAttemptDate(new GregorianCalendar(2001,2,1).getTime());
		endpointsLocal.add(endPoint1);
		endpointsLocal.add(endPoint2);
		assertEquals(date,iAMDDObject.getLastAttemptedEndpoint().getAttemptDate());
	}
	
	/**
	 * Test get last attempted endpoint empty list.
	 */
	public void testGetLastAttemptedEndpointEmptyList(){
		assertNull(iAMDDObject.getLastAttemptedEndpoint());
	}
	
	/**
	 * Test get recent errors.
	 */
	public void testGetRecentErrors(){
		EndPoint endPoint1= new GridEndPoint();
		endPoint1.setErrors(new ArrayList<Error>());
		EndPoint endPoint2= new GridEndPoint();
		endPoint2.setErrors(new ArrayList<Error>());
		Date date1=new GregorianCalendar(2001,2,1).getTime();
		Date date2=new GregorianCalendar(2002,2,1).getTime();
		Date date3=new GregorianCalendar(2004,2,1).getTime();
		Date date4=new Date();
		Error error= new Error();
		error.setErrorDate(date1);
		endPoint1.getErrors().add(error);
		error= new Error();
		error.setErrorDate(date4);
		endPoint1.getErrors().add(error);
		error= new Error();
		error.setErrorDate(date2);
		endPoint2.getErrors().add(error);
		error= new Error();
		error.setErrorDate(date3);
		endPoint2.getErrors().add(error);
		endpointsLocal.add(endPoint1);
		endpointsLocal.add(endPoint2);
		List<Error> errors= iAMDDObject.getRecentErrors();
		assertNotNull(errors);
		assertEquals(date1, errors.get(0).getErrorDate());
		assertEquals(date2, errors.get(1).getErrorDate());
		assertEquals(date3, errors.get(2).getErrorDate());
		assertEquals(date4, errors.get(3).getErrorDate());
	}

	/**
	 * Test get multisite workflow status null last attemped endpoint.
	 */
	public void testGetMultisiteWorkflowStatusNullLastAttempedEndpoint(){
		assertNull(iAMDDObject.getMultisiteWorkflowStatus());
	}
	
	/**
	 * Test get multisite workflow status.
	 */
	public void testGetMultisiteWorkflowStatus(){
		EndPoint endPoint1= registerMockFor(EndPoint.class);
		endpointsLocal.add(endPoint1);
		EasyMock.expect(endPoint1.getStatus()).andReturn(WorkFlowStatusType.MESSAGE_RECIEVED);
		replayMocks();
		assertEquals(WorkFlowStatusType.MESSAGE_RECIEVED, iAMDDObject.getMultisiteWorkflowStatus());
		verifyMocks();
	}
}
