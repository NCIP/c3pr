/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.domain;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import org.apache.axis.message.MessageElement;
import org.easymock.classextension.EasyMock;
import org.globus.gsi.GlobusCredential;

import edu.duke.cabig.c3pr.AbstractTestCase;
import edu.duke.cabig.c3pr.constants.APIName;
import edu.duke.cabig.c3pr.constants.ServiceName;
import edu.duke.cabig.c3pr.grid.registrationservice.client.RegistrationServiceClient;
import edu.duke.cabig.c3pr.utils.XMLUtils;
import gov.nih.nci.cabig.ccts.domain.Message;
import gov.nih.nci.cabig.ctms.domain.AbstractMutableDomainObject;

// TODO: Auto-generated Javadoc
/**
 * The Class GridEndPointTestCase.
 */
public class GridEndPointTestCase extends AbstractTestCase {

	/** The service name. */
	private ServiceName serviceName;
	
	/** The api name. */
	private APIName apiName;
	
	/** The end point connection property. */
	private EndPointConnectionProperty endPointConnectionProperty;
	
	/** The grid end point. */
	private GridEndPoint gridEndPoint;
	
	/* (non-Javadoc)
	 * @see edu.nwu.bioinformatics.commons.testing.CoreTestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		endPointConnectionProperty= registerMockFor(EndPointConnectionProperty.class);
		gridEndPoint= new GridEndPoint();
		gridEndPoint.setEndPointProperty(endPointConnectionProperty);
		gridEndPoint.setGlobusCredential(registerMockFor(GlobusCredential.class));
	}
	
	/**
	 * Test get service for registration.
	 */
	public void testGetServiceRegistration(){
		serviceName= ServiceName.REGISTRATION;
		gridEndPoint.setServiceName(serviceName);
		EasyMock.expect(endPointConnectionProperty.getIsAuthenticationRequired()).andReturn(true);
		EasyMock.expect(endPointConnectionProperty.getUrl()).andReturn("http://www.semanticbits.com");
		replayMocks();
		RegistrationServiceClient someService= (RegistrationServiceClient)gridEndPoint.getService();
		assertNotNull(someService);
		verifyMocks();
	}
	
	/**
	 * Test get service registration, no authentication.
	 */
	public void testGetServiceRegistrationNoAuthentication(){
		serviceName= ServiceName.REGISTRATION;
		gridEndPoint.setServiceName(serviceName);
		EasyMock.expect(endPointConnectionProperty.getIsAuthenticationRequired()).andReturn(false);
		EasyMock.expect(endPointConnectionProperty.getUrl()).andReturn("http://www.semanticbits.com");
		replayMocks();
		RegistrationServiceClient someService= (RegistrationServiceClient)gridEndPoint.getService();
		assertNotNull(someService);
		verifyMocks();
	}
	
	/**
	 * Test get api name.
	 */
	public void testGetAPIName(){
		serviceName= ServiceName.REGISTRATION;
		apiName=APIName.ENROLL_SUBJECT;
		gridEndPoint.setServiceName(serviceName);
		gridEndPoint.setApiName(apiName);
		EasyMock.expect(endPointConnectionProperty.getIsAuthenticationRequired()).andReturn(false);
		EasyMock.expect(endPointConnectionProperty.getUrl()).andReturn("http://www.semanticbits.com");
		replayMocks();
		assertNotNull(gridEndPoint.getAPI());
		verifyMocks();
	}
	
	/**
	 * Test get arguments with null argument.
	 */
	public void testGetArgumentsNullArgument(){
		assertNotNull(gridEndPoint.getArguments(null));
		assertNull(((Message)(gridEndPoint.getArguments(null)[0])).get_any());
	}
	
	/**
	 * Test get arguments.
	 * 
	 */
	public void testGetArguments() throws Exception{
		gridEndPoint= registerMockFor(GridEndPoint.class, GridEndPoint.class.getMethod("getXMLUtils", new Class[]{}));
		Message message= new Message();
		message.set_any(new MessageElement[]{});
		XMLUtils xmlUtils= registerMockFor(XMLUtils.class);
		List<AbstractMutableDomainObject> list=new ArrayList<AbstractMutableDomainObject>();
		EasyMock.expect(gridEndPoint.getXMLUtils()).andReturn(xmlUtils);
		EasyMock.expect(xmlUtils.buildMessageFromDomainObjects(list)).andReturn(message);
		replayMocks();
		Object[] objects=gridEndPoint.getArguments(list);
		assertNotNull(objects);
		assertNotNull(((Message)(objects[0])).get_any());
		verifyMocks();
	}
	
	/**
	 * Test process return type, null argument.
	 */
	public void testProcessReturnTypeNullArgument(){
		assertNull(gridEndPoint.processReturnType(null));
	}
	
	/**
	 * Test process return type, unknown argument type.
	 */
	public void testProcessReturnTypeUnknownTypeArgument(){
		assertNull(gridEndPoint.processReturnType(new String()));
	}
	
	/**
	 * Test process return type.
	 * 
	 * @throws Exception the exception
	 */
	public void testProcessReturnType() throws Exception{
		gridEndPoint= registerMockFor(GridEndPoint.class, GridEndPoint.class.getMethod("getXMLUtils", new Class[]{}));
		Message message= new Message();
		message.set_any(new MessageElement[]{});
		XMLUtils xmlUtils= registerMockFor(XMLUtils.class);
		List<AbstractMutableDomainObject> list=new ArrayList<AbstractMutableDomainObject>();
		EasyMock.expect(gridEndPoint.getXMLUtils()).andReturn(xmlUtils);
		EasyMock.expect(xmlUtils.getArguments(message)).andReturn(list);
		replayMocks();
		List objects=(List)gridEndPoint.processReturnType(message);
		assertNotNull(objects);
		verifyMocks();
	}
	
	/**
	 * Test process return type, exception.
	 * 
	 * @throws Exception the exception
	 */
	public void testProcessReturnTypeException() throws Exception{
		gridEndPoint= registerMockFor(GridEndPoint.class, GridEndPoint.class.getMethod("getXMLUtils", new Class[]{}));
		gridEndPoint.setErrors(new ArrayList<Error>());
		Message message= new Message();
		message.set_any(new MessageElement[]{});
		XMLUtils xmlUtils= registerMockFor(XMLUtils.class);
		EasyMock.expect(gridEndPoint.getXMLUtils()).andReturn(xmlUtils);
		EasyMock.expect(xmlUtils.getArguments(message)).andThrow(new RemoteException(""));
		replayMocks();
		assertNull(gridEndPoint.processReturnType(message));
		assertEquals(-1+"", gridEndPoint.getErrors().get(0).getErrorCode());
		verifyMocks();
	}
}
