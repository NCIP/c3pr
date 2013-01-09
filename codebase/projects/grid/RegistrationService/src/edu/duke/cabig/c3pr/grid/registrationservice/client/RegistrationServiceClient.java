/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.grid.registrationservice.client;

import java.io.InputStream;
import java.rmi.RemoteException;

import javax.xml.namespace.QName;

import org.apache.axis.EngineConfiguration;
import org.apache.axis.client.AxisClient;
import org.apache.axis.client.Stub;
import org.apache.axis.configuration.FileProvider;
import org.apache.axis.message.addressing.EndpointReferenceType;
import org.apache.axis.types.URI.MalformedURIException;

import org.oasis.wsrf.properties.GetResourcePropertyResponse;

import org.globus.gsi.GlobusCredential;

import edu.duke.cabig.c3pr.grid.registrationservice.stubs.RegistrationServicePortType;
import edu.duke.cabig.c3pr.grid.registrationservice.stubs.service.RegistrationServiceAddressingLocator;
import edu.duke.cabig.c3pr.grid.registrationservice.common.RegistrationServiceI;
import gov.nih.nci.cagrid.introduce.security.client.ServiceSecurityClient;

/**
 * This class is autogenerated, DO NOT EDIT GENERATED GRID SERVICE ACCESS METHODS.
 *
 * This client is generated automatically by Introduce to provide a clean unwrapped API to the
 * service.
 *
 * On construction the class instance will contact the remote service and retrieve it's security
 * metadata description which it will use to configure the Stub specifically for each method call.
 * 
 * @created by Introduce Toolkit version 1.2
 */
public class RegistrationServiceClient extends RegistrationServiceClientBase implements RegistrationServiceI {	

	public RegistrationServiceClient(String url) throws MalformedURIException, RemoteException {
		this(url,null);	
	}

	public RegistrationServiceClient(String url, GlobusCredential proxy) throws MalformedURIException, RemoteException {
	   	super(url,proxy);
	}
	
	public RegistrationServiceClient(EndpointReferenceType epr) throws MalformedURIException, RemoteException {
	   	this(epr,null);
	}
	
	public RegistrationServiceClient(EndpointReferenceType epr, GlobusCredential proxy) throws MalformedURIException, RemoteException {
	   	super(epr,proxy);
	}

	public static void usage(){
		System.out.println(RegistrationServiceClient.class.getName() + " -url <service url>");
	}
	
	public static void main(String [] args){
	    System.out.println("Running the Grid Service Client");
		try{
		if(!(args.length < 2)){
			if(args[0].equals("-url")){
			  RegistrationServiceClient client = new RegistrationServiceClient(args[1]);
			  // place client calls here if you want to use this main as a
			  // test....
			} else {
				usage();
				System.exit(1);
			}
		} else {
			usage();
			System.exit(1);
		}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

  public org.oasis.wsrf.properties.GetMultipleResourcePropertiesResponse getMultipleResourceProperties(org.oasis.wsrf.properties.GetMultipleResourceProperties_Element params) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"getMultipleResourceProperties");
    return portType.getMultipleResourceProperties(params);
    }
  }

  public org.oasis.wsrf.properties.GetResourcePropertyResponse getResourceProperty(javax.xml.namespace.QName params) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"getResourceProperty");
    return portType.getResourceProperty(params);
    }
  }

  public org.oasis.wsrf.properties.QueryResourcePropertiesResponse queryResourceProperties(org.oasis.wsrf.properties.QueryResourceProperties_Element params) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"queryResourceProperties");
    return portType.queryResourceProperties(params);
    }
  }

  public gov.nih.nci.cabig.ccts.domain.Message enroll(gov.nih.nci.cabig.ccts.domain.Message message) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"enroll");
    edu.duke.cabig.c3pr.grid.registrationservice.stubs.EnrollRequest params = new edu.duke.cabig.c3pr.grid.registrationservice.stubs.EnrollRequest();
    edu.duke.cabig.c3pr.grid.registrationservice.stubs.EnrollRequestMessage messageContainer = new edu.duke.cabig.c3pr.grid.registrationservice.stubs.EnrollRequestMessage();
    messageContainer.setMessage(message);
    params.setMessage(messageContainer);
    edu.duke.cabig.c3pr.grid.registrationservice.stubs.EnrollResponse boxedResult = portType.enroll(params);
    return boxedResult.getMessage();
    }
  }

  public gov.nih.nci.cabig.ccts.domain.Message transfer(gov.nih.nci.cabig.ccts.domain.Message message) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"transfer");
    edu.duke.cabig.c3pr.grid.registrationservice.stubs.TransferRequest params = new edu.duke.cabig.c3pr.grid.registrationservice.stubs.TransferRequest();
    edu.duke.cabig.c3pr.grid.registrationservice.stubs.TransferRequestMessage messageContainer = new edu.duke.cabig.c3pr.grid.registrationservice.stubs.TransferRequestMessage();
    messageContainer.setMessage(message);
    params.setMessage(messageContainer);
    edu.duke.cabig.c3pr.grid.registrationservice.stubs.TransferResponse boxedResult = portType.transfer(params);
    return boxedResult.getMessage();
    }
  }

  public void offStudy(gov.nih.nci.cabig.ccts.domain.Message message) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"offStudy");
    edu.duke.cabig.c3pr.grid.registrationservice.stubs.OffStudyRequest params = new edu.duke.cabig.c3pr.grid.registrationservice.stubs.OffStudyRequest();
    edu.duke.cabig.c3pr.grid.registrationservice.stubs.OffStudyRequestMessage messageContainer = new edu.duke.cabig.c3pr.grid.registrationservice.stubs.OffStudyRequestMessage();
    messageContainer.setMessage(message);
    params.setMessage(messageContainer);
    edu.duke.cabig.c3pr.grid.registrationservice.stubs.OffStudyResponse boxedResult = portType.offStudy(params);
    }
  }

  public gov.nih.nci.cabig.ccts.domain.Message getRegistrations(gov.nih.nci.cabig.ccts.domain.Message message) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"getRegistrations");
    edu.duke.cabig.c3pr.grid.registrationservice.stubs.GetRegistrationsRequest params = new edu.duke.cabig.c3pr.grid.registrationservice.stubs.GetRegistrationsRequest();
    edu.duke.cabig.c3pr.grid.registrationservice.stubs.GetRegistrationsRequestMessage messageContainer = new edu.duke.cabig.c3pr.grid.registrationservice.stubs.GetRegistrationsRequestMessage();
    messageContainer.setMessage(message);
    params.setMessage(messageContainer);
    edu.duke.cabig.c3pr.grid.registrationservice.stubs.GetRegistrationsResponse boxedResult = portType.getRegistrations(params);
    return boxedResult.getMessage();
    }
  }

}
