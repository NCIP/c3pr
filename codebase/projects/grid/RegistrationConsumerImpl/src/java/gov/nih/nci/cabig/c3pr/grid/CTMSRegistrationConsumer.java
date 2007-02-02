/**
 * 
 */
package gov.nih.nci.cabig.caaers.grid;

import java.rmi.RemoteException;

i
import gov.nih.nci.cabig.ctms.common.RegistrationConsumer;
import gov.nih.nci.cabig.ctms.grid.ParticipantType;
import gov.nih.nci.cabig.ctms.grid.RegistrationType;
import gov.nih.nci.cabig.ctms.stubs.types.InvalidRegistration;
import gov.nih.nci.cabig.ctms.stubs.types.RegistrationFailed;
import gov.nih.nci.cagrid.metadata.security.ServiceSecurityMetadata;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class CTMSSRegistrationConsumer implements RegistrationConsumer {

    private static final Log logger = LogFactory.getLog(CTMSRegistrationConsumer.class);

    private ApplicationContext ctx;

    public CaaersRegistrationConsumer() {
        this.ctx = new ClassPathXmlApplicationContext(
                        new String[] { "classpath*:gov/nih/nci/cabig/ctms/applicationContext-*.xml" });
    }

    /*
     * (non-Javadoc)
     * 
     * @see gov.nih.nci.cabig.ctms.common.RegistrationConsumer#createRegistration(gov.nih.nci.cabig.ctms.grid.RegistrationType)
     */
    public void register(RegistrationType registration) throws RemoteException, InvalidRegistration, RegistrationFailed {


        logger.debug("Received registration " + registration.getGridId());
    }

    public ServiceSecurityMetadata getServiceSecurityMetadata() throws RemoteException {
        throw new UnsupportedOperationException("Not implemented");
    }

}
