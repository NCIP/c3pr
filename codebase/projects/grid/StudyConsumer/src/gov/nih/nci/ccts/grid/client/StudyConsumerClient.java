package gov.nih.nci.ccts.grid.client;

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

import gov.nih.nci.ccts.grid.stubs.StudyConsumerPortType;
import gov.nih.nci.ccts.grid.stubs.service.StudyConsumerServiceAddressingLocator;
import gov.nih.nci.ccts.grid.common.StudyConsumerI;
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
 * @created by Introduce Toolkit version 1.1
 */
public class StudyConsumerClient extends ServiceSecurityClient implements StudyConsumerI {	
	protected StudyConsumerPortType portType;
	private Object portTypeMutex;

	public StudyConsumerClient(String url) throws MalformedURIException, RemoteException {
		this(url,null);	
	}

	public StudyConsumerClient(String url, GlobusCredential proxy) throws MalformedURIException, RemoteException {
	   	super(url,proxy);
	   	initialize();
	}
	
	public StudyConsumerClient(EndpointReferenceType epr) throws MalformedURIException, RemoteException {
	   	this(epr,null);
	}
	
	public StudyConsumerClient(EndpointReferenceType epr, GlobusCredential proxy) throws MalformedURIException, RemoteException {
	   	super(epr,proxy);
		initialize();
	}
	
	private void initialize() throws RemoteException {
	    this.portTypeMutex = new Object();
		this.portType = createPortType();
	}

	private StudyConsumerPortType createPortType() throws RemoteException {

		StudyConsumerServiceAddressingLocator locator = new StudyConsumerServiceAddressingLocator();
		// attempt to load our context sensitive wsdd file
		InputStream resourceAsStream = getClass().getResourceAsStream("client-config.wsdd");
		if (resourceAsStream != null) {
			// we found it, so tell axis to configure an engine to use it
			EngineConfiguration engineConfig = new FileProvider(resourceAsStream);
			// set the engine of the locator
			locator.setEngine(new AxisClient(engineConfig));
		}
		StudyConsumerPortType port = null;
		try {
			port = locator.getStudyConsumerPortTypePort(getEndpointReference());
		} catch (Exception e) {
			throw new RemoteException("Unable to locate portType:" + e.getMessage(), e);
		}

		return port;
	}
	
	public GetResourcePropertyResponse getResourceProperty(QName resourcePropertyQName) throws RemoteException {
		return portType.getResourceProperty(resourcePropertyQName);
	}

	public static void usage(){
		System.out.println(StudyConsumerClient.class.getName() + " -url <service url>");
	}
	
	public static void main(String [] args){
	    System.out.println("Running the Grid Service Client");
		try{
		if(!(args.length < 2)){
			if(args[0].equals("-url")){
			  StudyConsumerClient client = new StudyConsumerClient(args[1]);
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

  public void createStudy(gov.nih.nci.ccts.grid.Study study) throws RemoteException, gov.nih.nci.ccts.grid.stubs.types.InvalidStudyException, gov.nih.nci.ccts.grid.stubs.types.StudyCreationException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"createStudy");
    gov.nih.nci.ccts.grid.stubs.CreateStudyRequest params = new gov.nih.nci.ccts.grid.stubs.CreateStudyRequest();
    gov.nih.nci.ccts.grid.stubs.CreateStudyRequestStudy studyContainer = new gov.nih.nci.ccts.grid.stubs.CreateStudyRequestStudy();
    studyContainer.setStudy(study);
    params.setStudy(studyContainer);
    gov.nih.nci.ccts.grid.stubs.CreateStudyResponse boxedResult = portType.createStudy(params);
    }
  }

  public void commit(gov.nih.nci.ccts.grid.Study study) throws RemoteException, gov.nih.nci.ccts.grid.stubs.types.InvalidStudyException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"commit");
    gov.nih.nci.ccts.grid.stubs.CommitRequest params = new gov.nih.nci.ccts.grid.stubs.CommitRequest();
    gov.nih.nci.ccts.grid.stubs.CommitRequestStudy studyContainer = new gov.nih.nci.ccts.grid.stubs.CommitRequestStudy();
    studyContainer.setStudy(study);
    params.setStudy(studyContainer);
    gov.nih.nci.ccts.grid.stubs.CommitResponse boxedResult = portType.commit(params);
    }
  }

  public void rollback(gov.nih.nci.ccts.grid.Study study) throws RemoteException, gov.nih.nci.ccts.grid.stubs.types.InvalidStudyException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"rollback");
    gov.nih.nci.ccts.grid.stubs.RollbackRequest params = new gov.nih.nci.ccts.grid.stubs.RollbackRequest();
    gov.nih.nci.ccts.grid.stubs.RollbackRequestStudy studyContainer = new gov.nih.nci.ccts.grid.stubs.RollbackRequestStudy();
    studyContainer.setStudy(study);
    params.setStudy(studyContainer);
    gov.nih.nci.ccts.grid.stubs.RollbackResponse boxedResult = portType.rollback(params);
    }
  }

}
