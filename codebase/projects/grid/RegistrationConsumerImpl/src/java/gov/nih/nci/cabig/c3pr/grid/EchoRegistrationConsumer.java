/**
 *    Test Registration Consumer. Just logs the incoming message
 */
package gov.nih.nci.cabig.c3pr.grid;

import gov.nih.nci.cagrid.metadata.security.ServiceSecurityMetadata;
import gov.nih.nci.ccts.grid.Registration;
import gov.nih.nci.ccts.grid.common.RegistrationConsumer;
import gov.nih.nci.ccts.grid.stubs.types.InvalidRegistrationException;
import gov.nih.nci.ccts.grid.stubs.types.RegistrationConsumptionException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.rmi.RemoteException;


public class EchoRegistrationConsumer implements RegistrationConsumer {

    private static final Log logger = LogFactory.getLog(EchoRegistrationConsumer.class);



    public EchoRegistrationConsumer() {

    }

    /*
     * (non-Javadoc)
     * 
     * @see gov.nih.nci.cabig.ctms.common.RegistrationConsumer#createRegistration(gov.nih.nci.cabig.ctms.grid.RegistrationType)
     */


    public Registration register(Registration registration) throws InvalidRegistrationException, RegistrationConsumptionException, RemoteException {
        System.out.println("Registration received with Grid Id " + registration.getGridId());
        return registration;
    }

    public ServiceSecurityMetadata getServiceSecurityMetadata() throws RemoteException {
        throw new UnsupportedOperationException("Not implemented");
    }


}