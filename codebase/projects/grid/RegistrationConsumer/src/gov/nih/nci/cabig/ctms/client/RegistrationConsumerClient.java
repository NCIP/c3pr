package gov.nih.nci.cabig.ctms.client;

import java.io.InputStream;
import java.rmi.RemoteException;

import javax.xml.namespace.QName;

import org.apache.axis.EngineConfiguration;
import org.apache.axis.client.AxisClient;
import org.apache.axis.client.Stub;
import org.apache.axis.configuration.FileProvider;
import org.apache.axis.message.addressing.EndpointReferenceType;
import org.apache.axis.types.URI.MalformedURIException;
import org.apache.axis.utils.ClassUtils;

import org.oasis.wsrf.properties.GetResourcePropertyResponse;

import org.globus.gsi.GlobusCredential;

import gov.nih.nci.cabig.ctms.stubs.RegistrationConsumerPortType;
import gov.nih.nci.cabig.ctms.stubs.service.RegistrationConsumerServiceAddressingLocator;
import gov.nih.nci.cabig.ctms.common.RegistrationConsumer;
import gov.nih.nci.cagrid.introduce.security.client.ServiceSecurityClient;
import gov.nih.nci.cagrid.common.Utils;

/**
 * This class is autogenerated, DO NOT EDIT GENERATED GRID SERVICE METHODS.
 *
 * This client is generated automatically by Introduce to provide a clean unwrapped API to the
 * service.
 *
 * On construction the class instance will contact the remote service and retrieve it's security
 * metadata description which it will use to configure the Stub specifically for each method call.
 *
 * @created by Introduce Toolkit version 1.0
 */
public class RegistrationConsumerClient extends ServiceSecurityClient implements RegistrationConsumer {
    protected RegistrationConsumerPortType portType;
    private Object portTypeMutex;

    public RegistrationConsumerClient(String url) throws MalformedURIException, RemoteException {
        this(url,null);
    }

    public RegistrationConsumerClient(String url, GlobusCredential proxy) throws MalformedURIException, RemoteException {
        super(url,proxy);
        initialize();
    }

    public RegistrationConsumerClient(EndpointReferenceType epr) throws MalformedURIException, RemoteException {
        this(epr,null);
    }

    public RegistrationConsumerClient(EndpointReferenceType epr, GlobusCredential proxy) throws MalformedURIException, RemoteException {
        super(epr,proxy);
        initialize();
    }

    private void initialize() throws RemoteException {
        this.portTypeMutex = new Object();
        this.portType = createPortType();
    }

    private RegistrationConsumerPortType createPortType() throws RemoteException {

        RegistrationConsumerServiceAddressingLocator locator = new RegistrationConsumerServiceAddressingLocator();
        // attempt to load our context sensitive wsdd file
        InputStream resourceAsStream = ClassUtils.getResourceAsStream(getClass(), "client-config.wsdd");
        if (resourceAsStream != null) {
            // we found it, so tell axis to configure an engine to use it
            EngineConfiguration engineConfig = new FileProvider(resourceAsStream);
            // set the engine of the locator
            locator.setEngine(new AxisClient(engineConfig));
        }
        RegistrationConsumerPortType port = null;
        try {
            port = locator.getRegistrationConsumerPortTypePort(getEndpointReference());
        } catch (Exception e) {
            throw new RemoteException("Unable to locate portType:" + e.getMessage(), e);
        }

        return port;
    }

    public GetResourcePropertyResponse getResourceProperty(QName resourcePropertyQName) throws RemoteException {
        return portType.getResourceProperty(resourcePropertyQName);
    }

    public static void usage(){
        System.out.println(RegistrationConsumerClient.class.getName() + " -url <service url>");
    }

    public static void main(String [] args) {
        System.out.println("Running the Grid Service Client");
        try{
            if(!(args.length < 4)){
                if(args[0].equals("-url")){
                    RegistrationConsumerClient client = new RegistrationConsumerClient(args[1]);
                    if(args[2].equals("-message")){

                        gov.nih.nci.cabig.ctms.grid.RegistrationType registrationMessage = (gov.nih.nci.cabig.ctms.grid.RegistrationType) Utils.deserializeDocument(args[3],gov.nih.nci.cabig.ctms.grid.RegistrationType.class);
                        System.out.println("Registering with gridID " + registrationMessage.getStudyGridId());
                        client.register(registrationMessage);
                    }
                }
                else {
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

    public void register(gov.nih.nci.cabig.ctms.grid.RegistrationType registration) throws RemoteException, gov.nih.nci.cabig.ctms.stubs.types.InvalidRegistration, gov.nih.nci.cabig.ctms.stubs.types.RegistrationFailed {
        synchronized(portTypeMutex){
            configureStubSecurity((Stub)portType,"register");
            gov.nih.nci.cabig.ctms.stubs.RegisterRequest params = new gov.nih.nci.cabig.ctms.stubs.RegisterRequest();
            gov.nih.nci.cabig.ctms.stubs.RegisterRequestRegistration registrationContainer = new gov.nih.nci.cabig.ctms.stubs.RegisterRequestRegistration();
            registrationContainer.setRegistration(registration);
            params.setRegistration(registrationContainer);
            gov.nih.nci.cabig.ctms.stubs.RegisterResponse boxedResult = portType.register(params);
        }
    }
    public gov.nih.nci.cagrid.metadata.security.ServiceSecurityMetadata getServiceSecurityMetadata() throws RemoteException {
        synchronized(portTypeMutex){
            configureStubSecurity((Stub)portType,"getServiceSecurityMetadata");
            gov.nih.nci.cagrid.introduce.security.stubs.GetServiceSecurityMetadataRequest params = new gov.nih.nci.cagrid.introduce.security.stubs.GetServiceSecurityMetadataRequest();
            gov.nih.nci.cagrid.introduce.security.stubs.GetServiceSecurityMetadataResponse boxedResult = portType.getServiceSecurityMetadata(params);
            return boxedResult.getServiceSecurityMetadata();
        }
    }

}
