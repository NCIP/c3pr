/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.domain;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.axis.AxisFault;
import org.easymock.classextension.EasyMock;

import edu.duke.cabig.c3pr.AbstractTestCase;
import edu.duke.cabig.c3pr.constants.APIName;
import edu.duke.cabig.c3pr.constants.ServiceName;
import edu.duke.cabig.c3pr.constants.WorkFlowStatusType;
import edu.duke.cabig.c3pr.exception.C3PRCodedRuntimeException;

public class EndpointTest extends AbstractTestCase {

	private SomeMock someMock;
	
	@Override
	protected void setUp() throws Exception {
		someMock= registerMockFor(SomeMock.class);
	}
	
	public void testInvoke() throws Exception{
		someMock.someAPI();
		replayMocks();
		new EndpointSubclass("someAPI", new Class[]{}).invoke(null);
		verifyMocks();
	}
	
	public void testInvokeWithReturn() throws Exception{
		EasyMock.expect(someMock.someStringAPI("Test")).andReturn("ReturnTest");
		replayMocks();
		EndpointSubclass endpointSubclass= new EndpointSubclass("someStringAPI", new Class[]{String.class});
		endpointSubclass.invoke("Test");
		assertEquals("ReturnTest", endpointSubclass.getReturnValue());
		verifyMocks();
	}
	
	public void testInvokeException1()throws Exception{
		someMock.someExceptionAPI();
		EasyMock.expectLastCall().andThrow(new RuntimeException("200:some message"));
		EndpointSubclass endpointSubclass= new EndpointSubclass("someExceptionAPI", new Class[]{});
		endpointSubclass.setStudyOrganization(registerMockFor(StudyOrganization.class));
		HealthcareSite healthcareSite= registerMockFor(HealthcareSite.class);
		EasyMock.expect(endpointSubclass.getStudyOrganization().getHealthcareSite()).andReturn(healthcareSite);
		EasyMock.expect(healthcareSite.getName()).andReturn("Some Name");
		replayMocks();
		try {
			endpointSubclass.invoke(null);
			fail("Should have failed");
		} catch (C3PRCodedRuntimeException e) {
			e.printStackTrace();
			assertEquals(506+"", endpointSubclass.getErrors().get(0).getErrorCode());
			assertEquals(WorkFlowStatusType.MESSAGE_SEND_FAILED, endpointSubclass.getStatus());
		}
		verifyMocks();
	}
	
	public void testInvokeException2()throws NoSuchMethodException{
		EndpointSubclass endpointSubclass= new EndpointSubclass("someAPI", new Class[]{});
		endpointSubclass.setThrowReturnException(true);
		try {
			endpointSubclass.invoke(null);
			fail("Should have failed");
		} catch (RuntimeException e) {
			e.printStackTrace();
			assertEquals(-1+"", endpointSubclass.getErrors().get(0).getErrorCode());
			assertEquals(WorkFlowStatusType.MESSAGE_SEND_FAILED, endpointSubclass.getStatus());
		}catch (InvocationTargetException invocationTargetException){
			fail("Wrong Exception");
		}
	}
	
	public void testInvokeException3()throws Exception{
		AxisFault axisFault= new AxisFault();
		axisFault.detail=new ConnectException("Some Connection Exception");
		InvocationTargetException e1= new InvocationTargetException(axisFault);
		someMock.someExceptionAPI();
		EasyMock.expectLastCall().andThrow(axisFault);
		EndpointSubclass endpointSubclass= new EndpointSubclass("someExceptionAPI", new Class[]{});
		replayMocks();
		try {
			endpointSubclass.invoke(null);
			fail("Should have failed");
		} catch (C3PRCodedRuntimeException e2) {
			e2.printStackTrace();
			assertEquals("Wrong exception code",504, e2.getExceptionCode());
			assertEquals(WorkFlowStatusType.MESSAGE_SEND_FAILED, endpointSubclass.getStatus());
		}catch (InvocationTargetException e3) {
			e3.printStackTrace();
			fail("Should have thrown c3pr coded runtime exception");
		}
		verifyMocks();
	}
	
	public void testInvokeException4()throws Exception{
		someMock.someExceptionAPI();
		EasyMock.expectLastCall().andThrow(new RuntimeException("org.globus.wsrf.impl.security.authorization.exceptions.AuthorizationException: \"/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=SmokeTest\" is not authorized to use operation: {http://studyservice.grid.c3pr.cabig.duke.edu/StudyService}createAndOpenStudy on this service"));
		EndpointSubclass endpointSubclass= new EndpointSubclass("someExceptionAPI", new Class[]{});
		endpointSubclass.setApiName(APIName.ENROLL_SUBJECT);
		endpointSubclass.setStudyOrganization(registerMockFor(StudyOrganization.class));
		HealthcareSite healthcareSite= registerMockFor(HealthcareSite.class);
		EasyMock.expect(endpointSubclass.getStudyOrganization().getHealthcareSite()).andReturn(healthcareSite);
		EasyMock.expect(healthcareSite.getName()).andReturn("Some Name");
		replayMocks();
		try {
			endpointSubclass.invoke(null);
			fail("Should have failed");
		} catch (C3PRCodedRuntimeException e2) {
			e2.printStackTrace();
			assertEquals("Wrong exception code",505, e2.getExceptionCode());
			assertEquals(WorkFlowStatusType.MESSAGE_SEND_FAILED, endpointSubclass.getStatus());
		}catch (InvocationTargetException e3) {
			e3.printStackTrace();
			fail("Should have thrown c3pr coded runtime exception");
		}
		verifyMocks();
	}
	
	public void testGetXMLUtilsNullServiceName(){
		EndpointSubclass endpointSubclass= new EndpointSubclass();
		assertNull(endpointSubclass.getXMLUtils());
	}
	
	public void testGetXMLUtils(){
		EndpointSubclass endpointSubclass= new EndpointSubclass();
		endpointSubclass.setServiceName(ServiceName.REGISTRATION);
		assertNotNull(endpointSubclass.getXMLUtils());
	}
	
	public void testGetXMLMarshallerNullServiceName(){
		EndpointSubclass endpointSubclass= new EndpointSubclass();
		assertNull(endpointSubclass.getXMLMarshaller());
	}
	
	public void testGetXMLMarshaller(){
		EndpointSubclass endpointSubclass= new EndpointSubclass();
		endpointSubclass.setServiceName(ServiceName.REGISTRATION);
		assertNotNull(endpointSubclass.getXMLMarshaller());
	}
	
	public void testCompareTo(){
		EndpointSubclass endpointSubclass1= new EndpointSubclass();
		endpointSubclass1.setAttemptDate(new GregorianCalendar(2001,1,1).getTime());
		EndpointSubclass endpointSubclass2= new EndpointSubclass();
		endpointSubclass2.setAttemptDate(new Date());
		assertEquals(-1, endpointSubclass1.compareTo(endpointSubclass2));
	}
	
	public void testGetLastAttemptError(){
		EndpointSubclass endpointSubclass= new EndpointSubclass();
		endpointSubclass.setErrors(new ArrayList<Error>());
		Date date1=new Date();
		Date date2=new GregorianCalendar(2001,2,1).getTime();
		Error error= new Error();
		error.setErrorDate(date1);
		endpointSubclass.getErrors().add(error);
		error= new Error();
		error.setErrorDate(date2);
		endpointSubclass.getErrors().add(error);
		assertEquals(date1, endpointSubclass.getLastAttemptError().getErrorDate());
	}
	
	interface SomeMock{
		public void someAPI();
		public String someStringAPI(String s);
		public void someExceptionAPI() throws AxisFault;
	}
	
	class EndpointSubclass extends EndPoint{

		private String apiName;
		private Class[] classArray;
		private boolean throwReturnException=false;
		
		public EndpointSubclass() {
		}
		
		public EndpointSubclass(String apiName, Class[] classArray) {
			this.apiName= apiName;
			this.classArray= classArray;
		}
		public void setThrowReturnException(boolean flag){
			this.throwReturnException= flag;
		}
		@Override
		public Method getAPI() {
			try {
				return someMock.getClass().getMethod(apiName, classArray);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		@Override
		public Object[] getArguments(Object argument) {
			if(throwReturnException){
				throw new RuntimeException("");
			}
			return argument==null?new Object[]{}:new Object[]{argument};
		}

		@Override
		public Object getService() {
			return someMock;
		}

		@Override
		public Object processReturnType(Object returnValue) {
			return returnValue;
		}
		
	}
	
}
