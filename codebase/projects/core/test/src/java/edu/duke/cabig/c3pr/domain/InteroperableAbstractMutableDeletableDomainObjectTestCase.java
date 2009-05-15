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

public class InteroperableAbstractMutableDeletableDomainObjectTestCase extends AbstractTestCase {

	private InteroperableAbstractMutableDeletableDomainObject iAMDDObject;
	private List<EndPoint> endpointsLocal;
	
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
	
	public void testGetImportErrorStringNullString(){
		assertNull(iAMDDObject.getImportErrorString());
	}
	

	public void testGetImportErrorStringSize200(){
		char[] someString=new char[200];
		Arrays.fill(someString, 'a');
		iAMDDObject.setImportErrorString(new String(someString));
		assertEquals(100, iAMDDObject.getImportErrorString().length());
	}
	
	public void testGetImportErrorString(){
		iAMDDObject.setImportErrorString("Test");
		assertEquals("Test", iAMDDObject.getImportErrorString());
	}
	
	public void testSetImportErrorString(){
		assertFalse(iAMDDObject.isImportErrorFlag());
		iAMDDObject.setImportErrorString("Test");
		assertTrue(iAMDDObject.isImportErrorFlag());
		assertEquals("Test", iAMDDObject.getImportErrorString());
	}
	
	public void testGetEndpoint(){
		EndPoint endPoint1= registerMockFor(EndPoint.class);
		EndPoint endPoint2= registerMockFor(EndPoint.class);
		EndPoint endPoint3= registerMockFor(EndPoint.class);
		endpointsLocal.add(endPoint1);
		endpointsLocal.add(endPoint2);
		endpointsLocal.add(endPoint3);
		iAMDDObject.setEndpoints(endpointsLocal);
		EasyMock.expect(endPoint1.getServiceName()).andReturn(ServiceName.REGISTRATION);
		EasyMock.expect(endPoint2.getServiceName()).andReturn(ServiceName.STUDY);
		EasyMock.expect(endPoint2.getApiName()).andReturn(APIName.ACTIVATE_STUDY_SITE);
		EasyMock.expect(endPoint3.getServiceName()).andReturn(ServiceName.STUDY);
		EasyMock.expect(endPoint3.getApiName()).andReturn(APIName.CREATE_STUDY_DEFINITION);
		replayMocks();
		assertNotNull(iAMDDObject.getEndPoint(ServiceName.STUDY, APIName.CREATE_STUDY_DEFINITION));
		verifyMocks();
	}
	
	public void testGetEndpointNotFound(){
		EndPoint endPoint1= registerMockFor(EndPoint.class);
		EndPoint endPoint2= registerMockFor(EndPoint.class);
		endpointsLocal.add(endPoint1);
		endpointsLocal.add(endPoint2);
		iAMDDObject.setEndpoints(endpointsLocal);
		EasyMock.expect(endPoint1.getServiceName()).andReturn(ServiceName.REGISTRATION);
		EasyMock.expect(endPoint2.getServiceName()).andReturn(ServiceName.STUDY);
		EasyMock.expect(endPoint2.getApiName()).andReturn(APIName.ACTIVATE_STUDY_SITE);
		replayMocks();
		assertNull(iAMDDObject.getEndPoint(ServiceName.STUDY, APIName.CREATE_STUDY_DEFINITION));
		verifyMocks();
	}
	
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
	
	public void testGetLastAttemptedEndpointEmptyList(){
		assertNull(iAMDDObject.getLastAttemptedEndpoint());
	}
	
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

	public void testGetMultisiteWorkflowStatusNullLastAttempedEndpoint(){
		assertNull(iAMDDObject.getMultisiteWorkflowStatus());
	}
	
	public void testGetMultisiteWorkflowStatus(){
		EndPoint endPoint1= registerMockFor(EndPoint.class);
		endpointsLocal.add(endPoint1);
		EasyMock.expect(endPoint1.getStatus()).andReturn(WorkFlowStatusType.MESSAGE_RECIEVED);
		replayMocks();
		assertEquals(WorkFlowStatusType.MESSAGE_RECIEVED, iAMDDObject.getMultisiteWorkflowStatus());
		verifyMocks();
	}
}
