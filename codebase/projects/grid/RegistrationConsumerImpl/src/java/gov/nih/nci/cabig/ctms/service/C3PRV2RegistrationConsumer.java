/**
 *
 */
package gov.nih.nci.cabig.ctms.service;

import edu.duke.cabig.c3pr.service.StudyService;
import gov.nih.nci.cagrid.metadata.security.ServiceSecurityMetadata;
import gov.nih.nci.ccts.grid.Registration;
import gov.nih.nci.ccts.grid.common.RegistrationConsumer;
import gov.nih.nci.ccts.grid.stubs.types.InvalidRegistrationException;
import gov.nih.nci.ccts.grid.stubs.types.RegistrationConsumptionException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.rmi.RemoteException;


public class C3PRV2RegistrationConsumer implements RegistrationConsumer {

    private static final Log logger = LogFactory.getLog(C3PRV2RegistrationConsumer.class);

    StudyService studyService;


    public C3PRV2RegistrationConsumer() {
    }


    public Registration register(Registration registration) throws RemoteException, InvalidRegistrationException, RegistrationConsumptionException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public ServiceSecurityMetadata getServiceSecurityMetadata() throws RemoteException {
        throw new UnsupportedOperationException("Not implemented");
    }


    public StudyService getStudyService() {
        return studyService;
    }

    public void setStudyService(StudyService studyService) {
        this.studyService = studyService;
    }
}
