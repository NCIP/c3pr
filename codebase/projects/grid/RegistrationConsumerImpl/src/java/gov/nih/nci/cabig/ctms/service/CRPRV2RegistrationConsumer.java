/**
 *
 */
package gov.nih.nci.cabig.ctms.service;

import edu.duke.cabig.c3pr.domain.*;
import edu.duke.cabig.c3pr.service.StudyService;
import gov.nih.nci.cabig.ctms.common.RegistrationConsumer;
import gov.nih.nci.cabig.ctms.grid.ParticipantType;
import gov.nih.nci.cabig.ctms.grid.RegistrationType;
import gov.nih.nci.cabig.ctms.grid.IdentifierType;
import gov.nih.nci.cabig.ctms.stubs.types.InvalidRegistration;
import gov.nih.nci.cabig.ctms.stubs.types.RegistrationFailed;
import gov.nih.nci.cagrid.metadata.security.ServiceSecurityMetadata;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.rmi.RemoteException;
import java.util.List;
import java.util.ArrayList;


public class CRPRV2RegistrationConsumer implements RegistrationConsumer {

    private static final Log logger = LogFactory.getLog(CRPRV2RegistrationConsumer.class);

    StudyService studyService;


    public CRPRV2RegistrationConsumer() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see gov.nih.nci.cabig.ctms.common.RegistrationConsumer#createRegistration(gov.nih.nci.cabig.ctms.grid.RegistrationType)
     */
    public void register(RegistrationType registration) throws RemoteException, InvalidRegistration, RegistrationFailed {


        Study study = new Study();
        study.setGridId(registration.getStudyGridId());

        HealthcareSite site = new HealthcareSite();
        site.setGridId(registration.getHealthCareSiteGridId());

        Participant participant = createParticipant(registration);


        StudyParticipantAssignment assignment = studyService.assignParticipant(study, participant, site,
                                                                               registration.getEnrollmentDate());
        logger.debug("Created assignment" + assignment);
    }


    private Participant createParticipant(RegistrationType registration) {
        ParticipantType partBean = registration.getParticipant();
        Participant participant = new Participant();

        participant.setGridId(partBean.getParticipantGridId());
        participant.setAdministrativeGenderCode(partBean.getAdministrativeGenderCode());
        participant.setBirthDate(partBean.getBirthDate());
        participant.setEthnicGroupCode(partBean.getEthnicGroupCode());
        participant.setFirstName(partBean.getFirstName());
        participant.setLastName(partBean.getLastName());
        participant.setRaceCode(partBean.getRaceCode());

        if (partBean.getIdentifier() != null) {
            List<Identifier> identifiersList =  new ArrayList<Identifier>();
            for (IdentifierType identifierType : partBean.getIdentifier()) {
                Identifier identifier = new Identifier();
                identifier.setSource(identifierType.getSource());
                identifier.setType(identifierType.getType());
                identifier.setValue(identifierType.getValue());

                identifiersList.add(identifier);
            }
            participant.setIdentifiers(identifiersList);
        }
        return participant;
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
