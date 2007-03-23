/**
 *    Test Registration Consumer. Just logs the incoming message
 */
package gov.nih.nci.cabig.c3pr.grid;

import edu.duke.cabig.c3pr.service.StudyService;
import gov.nih.nci.cabig.ctms.common.RegistrationConsumer;
import gov.nih.nci.cabig.ctms.grid.RegistrationType;
import gov.nih.nci.cabig.ctms.stubs.types.InvalidRegistration;
import gov.nih.nci.cabig.ctms.stubs.types.RegistrationFailed;
import gov.nih.nci.cagrid.metadata.security.ServiceSecurityMetadata;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.rmi.RemoteException;


public class EchoRegistrationConsumer implements RegistrationConsumer {

    private static final Log logger = LogFactory.getLog(EchoRegistrationConsumer.class);


    StudyService studyService;

    public EchoRegistrationConsumer() {

    }

    /*
     * (non-Javadoc)
     * 
     * @see gov.nih.nci.cabig.ctms.common.RegistrationConsumer#createRegistration(gov.nih.nci.cabig.ctms.grid.RegistrationType)
     */
    public void register(RegistrationType registration) throws RemoteException, InvalidRegistration, RegistrationFailed {

        System.out.println("Registration Received " + registration.getIdentifier());
        logger.info("Received registration " + registration.getIdentifier());
    }

    public ServiceSecurityMetadata getServiceSecurityMetadata() throws RemoteException {
        throw new UnsupportedOperationException("Not implemented");
    }


    //for spring injection. Not used
    public void setStudyService(StudyService studyService) {
        this.studyService = studyService;
    }
}
