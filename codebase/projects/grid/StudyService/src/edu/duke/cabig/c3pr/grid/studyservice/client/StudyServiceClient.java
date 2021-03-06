/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.grid.studyservice.client;

import java.io.File;
import java.io.FileInputStream;
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

import edu.duke.cabig.c3pr.grid.studyservice.stubs.StudyServicePortType;
import edu.duke.cabig.c3pr.grid.studyservice.stubs.service.StudyServiceAddressingLocator;
import edu.duke.cabig.c3pr.grid.studyservice.common.StudyServiceI;
import gov.nih.nci.cabig.ccts.domain.Message;
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
public class StudyServiceClient extends StudyServiceClientBase implements StudyServiceI {	

	public StudyServiceClient(String url) throws MalformedURIException, RemoteException {
		this(url,null);	
	}

	public StudyServiceClient(String url, GlobusCredential proxy) throws MalformedURIException, RemoteException {
	   	super(url,proxy);
	}
	
	public StudyServiceClient(EndpointReferenceType epr) throws MalformedURIException, RemoteException {
	   	this(epr,null);
	}
	
	public StudyServiceClient(EndpointReferenceType epr, GlobusCredential proxy) throws MalformedURIException, RemoteException {
	   	super(epr,proxy);
	}

	public static void usage(){
		System.out.println(StudyServiceClient.class.getName() + " -url <service url>");
	}
	
	public static void main(String [] args){
	    System.out.println("Running the Grid Service Client");
	    //String credentialFile="/Users/kruttikagarwal/proxyFileCCTSdemo1";
	    String credentialFile="/Users/kruttikagarwal/delegatedCCTSdemo1";
		try{
		if(!(args.length < 2)){
			if(args[0].equals("-url")){
			  StudyServiceClient client = new StudyServiceClient(args[1], new GlobusCredential(new FileInputStream(new File(credentialFile))));
			  Message message=new Message();
			  client.createStudyDefinition(message);
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

  public void activateStudySite(gov.nih.nci.cabig.ccts.domain.Message message) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"activateStudySite");
    edu.duke.cabig.c3pr.grid.studyservice.stubs.ActivateStudySiteRequest params = new edu.duke.cabig.c3pr.grid.studyservice.stubs.ActivateStudySiteRequest();
    edu.duke.cabig.c3pr.grid.studyservice.stubs.ActivateStudySiteRequestMessage messageContainer = new edu.duke.cabig.c3pr.grid.studyservice.stubs.ActivateStudySiteRequestMessage();
    messageContainer.setMessage(message);
    params.setMessage(messageContainer);
    edu.duke.cabig.c3pr.grid.studyservice.stubs.ActivateStudySiteResponse boxedResult = portType.activateStudySite(params);
    }
  }

  public void amendStudy(gov.nih.nci.cabig.ccts.domain.Message message) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"amendStudy");
    edu.duke.cabig.c3pr.grid.studyservice.stubs.AmendStudyRequest params = new edu.duke.cabig.c3pr.grid.studyservice.stubs.AmendStudyRequest();
    edu.duke.cabig.c3pr.grid.studyservice.stubs.AmendStudyRequestMessage messageContainer = new edu.duke.cabig.c3pr.grid.studyservice.stubs.AmendStudyRequestMessage();
    messageContainer.setMessage(message);
    params.setMessage(messageContainer);
    edu.duke.cabig.c3pr.grid.studyservice.stubs.AmendStudyResponse boxedResult = portType.amendStudy(params);
    }
  }

  public void closeStudyToAccrual(gov.nih.nci.cabig.ccts.domain.Message message) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"closeStudyToAccrual");
    edu.duke.cabig.c3pr.grid.studyservice.stubs.CloseStudyToAccrualRequest params = new edu.duke.cabig.c3pr.grid.studyservice.stubs.CloseStudyToAccrualRequest();
    edu.duke.cabig.c3pr.grid.studyservice.stubs.CloseStudyToAccrualRequestMessage messageContainer = new edu.duke.cabig.c3pr.grid.studyservice.stubs.CloseStudyToAccrualRequestMessage();
    messageContainer.setMessage(message);
    params.setMessage(messageContainer);
    edu.duke.cabig.c3pr.grid.studyservice.stubs.CloseStudyToAccrualResponse boxedResult = portType.closeStudyToAccrual(params);
    }
  }

  public void closeStudySiteToAccrual(gov.nih.nci.cabig.ccts.domain.Message message) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"closeStudySiteToAccrual");
    edu.duke.cabig.c3pr.grid.studyservice.stubs.CloseStudySiteToAccrualRequest params = new edu.duke.cabig.c3pr.grid.studyservice.stubs.CloseStudySiteToAccrualRequest();
    edu.duke.cabig.c3pr.grid.studyservice.stubs.CloseStudySiteToAccrualRequestMessage messageContainer = new edu.duke.cabig.c3pr.grid.studyservice.stubs.CloseStudySiteToAccrualRequestMessage();
    messageContainer.setMessage(message);
    params.setMessage(messageContainer);
    edu.duke.cabig.c3pr.grid.studyservice.stubs.CloseStudySiteToAccrualResponse boxedResult = portType.closeStudySiteToAccrual(params);
    }
  }

  public void createStudyDefinition(gov.nih.nci.cabig.ccts.domain.Message message) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"createStudyDefinition");
    edu.duke.cabig.c3pr.grid.studyservice.stubs.CreateStudyDefinitionRequest params = new edu.duke.cabig.c3pr.grid.studyservice.stubs.CreateStudyDefinitionRequest();
    edu.duke.cabig.c3pr.grid.studyservice.stubs.CreateStudyDefinitionRequestMessage messageContainer = new edu.duke.cabig.c3pr.grid.studyservice.stubs.CreateStudyDefinitionRequestMessage();
    messageContainer.setMessage(message);
    params.setMessage(messageContainer);
    edu.duke.cabig.c3pr.grid.studyservice.stubs.CreateStudyDefinitionResponse boxedResult = portType.createStudyDefinition(params);
    }
  }

  public void openStudy(gov.nih.nci.cabig.ccts.domain.Message message) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"openStudy");
    edu.duke.cabig.c3pr.grid.studyservice.stubs.OpenStudyRequest params = new edu.duke.cabig.c3pr.grid.studyservice.stubs.OpenStudyRequest();
    edu.duke.cabig.c3pr.grid.studyservice.stubs.OpenStudyRequestMessage messageContainer = new edu.duke.cabig.c3pr.grid.studyservice.stubs.OpenStudyRequestMessage();
    messageContainer.setMessage(message);
    params.setMessage(messageContainer);
    edu.duke.cabig.c3pr.grid.studyservice.stubs.OpenStudyResponse boxedResult = portType.openStudy(params);
    }
  }

  public void updateStudySiteProtocolVersion(gov.nih.nci.cabig.ccts.domain.Message message) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"updateStudySiteProtocolVersion");
    edu.duke.cabig.c3pr.grid.studyservice.stubs.UpdateStudySiteProtocolVersionRequest params = new edu.duke.cabig.c3pr.grid.studyservice.stubs.UpdateStudySiteProtocolVersionRequest();
    edu.duke.cabig.c3pr.grid.studyservice.stubs.UpdateStudySiteProtocolVersionRequestMessage messageContainer = new edu.duke.cabig.c3pr.grid.studyservice.stubs.UpdateStudySiteProtocolVersionRequestMessage();
    messageContainer.setMessage(message);
    params.setMessage(messageContainer);
    edu.duke.cabig.c3pr.grid.studyservice.stubs.UpdateStudySiteProtocolVersionResponse boxedResult = portType.updateStudySiteProtocolVersion(params);
    }
  }

  public void updateStudy(gov.nih.nci.cabig.ccts.domain.Message message) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"updateStudy");
    edu.duke.cabig.c3pr.grid.studyservice.stubs.UpdateStudyRequest params = new edu.duke.cabig.c3pr.grid.studyservice.stubs.UpdateStudyRequest();
    edu.duke.cabig.c3pr.grid.studyservice.stubs.UpdateStudyRequestMessage messageContainer = new edu.duke.cabig.c3pr.grid.studyservice.stubs.UpdateStudyRequestMessage();
    messageContainer.setMessage(message);
    params.setMessage(messageContainer);
    edu.duke.cabig.c3pr.grid.studyservice.stubs.UpdateStudyResponse boxedResult = portType.updateStudy(params);
    }
  }

  public gov.nih.nci.cabig.ccts.domain.Message getStudy(gov.nih.nci.cabig.ccts.domain.Message message) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"getStudy");
    edu.duke.cabig.c3pr.grid.studyservice.stubs.GetStudyRequest params = new edu.duke.cabig.c3pr.grid.studyservice.stubs.GetStudyRequest();
    edu.duke.cabig.c3pr.grid.studyservice.stubs.GetStudyRequestMessage messageContainer = new edu.duke.cabig.c3pr.grid.studyservice.stubs.GetStudyRequestMessage();
    messageContainer.setMessage(message);
    params.setMessage(messageContainer);
    edu.duke.cabig.c3pr.grid.studyservice.stubs.GetStudyResponse boxedResult = portType.getStudy(params);
    return boxedResult.getMessage();
    }
  }

  public void closeStudyToAccrualAndTreatment(gov.nih.nci.cabig.ccts.domain.Message message) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"closeStudyToAccrualAndTreatment");
    edu.duke.cabig.c3pr.grid.studyservice.stubs.CloseStudyToAccrualAndTreatmentRequest params = new edu.duke.cabig.c3pr.grid.studyservice.stubs.CloseStudyToAccrualAndTreatmentRequest();
    edu.duke.cabig.c3pr.grid.studyservice.stubs.CloseStudyToAccrualAndTreatmentRequestMessage messageContainer = new edu.duke.cabig.c3pr.grid.studyservice.stubs.CloseStudyToAccrualAndTreatmentRequestMessage();
    messageContainer.setMessage(message);
    params.setMessage(messageContainer);
    edu.duke.cabig.c3pr.grid.studyservice.stubs.CloseStudyToAccrualAndTreatmentResponse boxedResult = portType.closeStudyToAccrualAndTreatment(params);
    }
  }

  public void temporarilyCloseStudyToAccrual(gov.nih.nci.cabig.ccts.domain.Message message) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"temporarilyCloseStudyToAccrual");
    edu.duke.cabig.c3pr.grid.studyservice.stubs.TemporarilyCloseStudyToAccrualRequest params = new edu.duke.cabig.c3pr.grid.studyservice.stubs.TemporarilyCloseStudyToAccrualRequest();
    edu.duke.cabig.c3pr.grid.studyservice.stubs.TemporarilyCloseStudyToAccrualRequestMessage messageContainer = new edu.duke.cabig.c3pr.grid.studyservice.stubs.TemporarilyCloseStudyToAccrualRequestMessage();
    messageContainer.setMessage(message);
    params.setMessage(messageContainer);
    edu.duke.cabig.c3pr.grid.studyservice.stubs.TemporarilyCloseStudyToAccrualResponse boxedResult = portType.temporarilyCloseStudyToAccrual(params);
    }
  }

  public void temporarilyCloseStudyToAccrualAndTreatment(gov.nih.nci.cabig.ccts.domain.Message message) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"temporarilyCloseStudyToAccrualAndTreatment");
    edu.duke.cabig.c3pr.grid.studyservice.stubs.TemporarilyCloseStudyToAccrualAndTreatmentRequest params = new edu.duke.cabig.c3pr.grid.studyservice.stubs.TemporarilyCloseStudyToAccrualAndTreatmentRequest();
    edu.duke.cabig.c3pr.grid.studyservice.stubs.TemporarilyCloseStudyToAccrualAndTreatmentRequestMessage messageContainer = new edu.duke.cabig.c3pr.grid.studyservice.stubs.TemporarilyCloseStudyToAccrualAndTreatmentRequestMessage();
    messageContainer.setMessage(message);
    params.setMessage(messageContainer);
    edu.duke.cabig.c3pr.grid.studyservice.stubs.TemporarilyCloseStudyToAccrualAndTreatmentResponse boxedResult = portType.temporarilyCloseStudyToAccrualAndTreatment(params);
    }
  }

  public void closeStudySiteToAccrualAndTreatment(gov.nih.nci.cabig.ccts.domain.Message message) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"closeStudySiteToAccrualAndTreatment");
    edu.duke.cabig.c3pr.grid.studyservice.stubs.CloseStudySiteToAccrualAndTreatmentRequest params = new edu.duke.cabig.c3pr.grid.studyservice.stubs.CloseStudySiteToAccrualAndTreatmentRequest();
    edu.duke.cabig.c3pr.grid.studyservice.stubs.CloseStudySiteToAccrualAndTreatmentRequestMessage messageContainer = new edu.duke.cabig.c3pr.grid.studyservice.stubs.CloseStudySiteToAccrualAndTreatmentRequestMessage();
    messageContainer.setMessage(message);
    params.setMessage(messageContainer);
    edu.duke.cabig.c3pr.grid.studyservice.stubs.CloseStudySiteToAccrualAndTreatmentResponse boxedResult = portType.closeStudySiteToAccrualAndTreatment(params);
    }
  }

  public void temporarilyCloseStudySiteToAccrualAndTreatment(gov.nih.nci.cabig.ccts.domain.Message message) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"temporarilyCloseStudySiteToAccrualAndTreatment");
    edu.duke.cabig.c3pr.grid.studyservice.stubs.TemporarilyCloseStudySiteToAccrualAndTreatmentRequest params = new edu.duke.cabig.c3pr.grid.studyservice.stubs.TemporarilyCloseStudySiteToAccrualAndTreatmentRequest();
    edu.duke.cabig.c3pr.grid.studyservice.stubs.TemporarilyCloseStudySiteToAccrualAndTreatmentRequestMessage messageContainer = new edu.duke.cabig.c3pr.grid.studyservice.stubs.TemporarilyCloseStudySiteToAccrualAndTreatmentRequestMessage();
    messageContainer.setMessage(message);
    params.setMessage(messageContainer);
    edu.duke.cabig.c3pr.grid.studyservice.stubs.TemporarilyCloseStudySiteToAccrualAndTreatmentResponse boxedResult = portType.temporarilyCloseStudySiteToAccrualAndTreatment(params);
    }
  }

  public void temporarilyCloseStudySiteToAccrual(gov.nih.nci.cabig.ccts.domain.Message message) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"temporarilyCloseStudySiteToAccrual");
    edu.duke.cabig.c3pr.grid.studyservice.stubs.TemporarilyCloseStudySiteToAccrualRequest params = new edu.duke.cabig.c3pr.grid.studyservice.stubs.TemporarilyCloseStudySiteToAccrualRequest();
    edu.duke.cabig.c3pr.grid.studyservice.stubs.TemporarilyCloseStudySiteToAccrualRequestMessage messageContainer = new edu.duke.cabig.c3pr.grid.studyservice.stubs.TemporarilyCloseStudySiteToAccrualRequestMessage();
    messageContainer.setMessage(message);
    params.setMessage(messageContainer);
    edu.duke.cabig.c3pr.grid.studyservice.stubs.TemporarilyCloseStudySiteToAccrualResponse boxedResult = portType.temporarilyCloseStudySiteToAccrual(params);
    }
  }

  public void createAndOpenStudy(gov.nih.nci.cabig.ccts.domain.Message message) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"createAndOpenStudy");
    edu.duke.cabig.c3pr.grid.studyservice.stubs.CreateAndOpenStudyRequest params = new edu.duke.cabig.c3pr.grid.studyservice.stubs.CreateAndOpenStudyRequest();
    edu.duke.cabig.c3pr.grid.studyservice.stubs.CreateAndOpenStudyRequestMessage messageContainer = new edu.duke.cabig.c3pr.grid.studyservice.stubs.CreateAndOpenStudyRequestMessage();
    messageContainer.setMessage(message);
    params.setMessage(messageContainer);
    edu.duke.cabig.c3pr.grid.studyservice.stubs.CreateAndOpenStudyResponse boxedResult = portType.createAndOpenStudy(params);
    }
  }

}
