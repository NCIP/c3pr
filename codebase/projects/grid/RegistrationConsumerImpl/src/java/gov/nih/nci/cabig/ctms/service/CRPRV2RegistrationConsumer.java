/**
 *
 */
package gov.nih.nci.cabig.ctms.service;

import edu.duke.cabig.c3pr.domain.*;
import edu.duke.cabig.c3pr.utils.XmlMarshaller;
import edu.duke.cabig.c3pr.service.StudyService;
import gov.nih.nci.cabig.ctms.common.RegistrationConsumer;
import gov.nih.nci.cabig.ctms.grid.RegistrationType;
import gov.nih.nci.cabig.ctms.grid.ParticipantType;
import gov.nih.nci.cabig.ctms.stubs.types.InvalidRegistration;
import gov.nih.nci.cabig.ctms.stubs.types.RegistrationFailed;
import gov.nih.nci.cagrid.common.Utils;
import gov.nih.nci.cagrid.metadata.security.ServiceSecurityMetadata;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.globus.wsrf.encoding.ObjectSerializer;

import java.io.StringReader;
import java.io.StringWriter;
import java.rmi.RemoteException;


public  class CRPRV2RegistrationConsumer implements RegistrationConsumer {

    private static final Log logger = LogFactory.getLog(CRPRV2RegistrationConsumer.class);

    StudyService studyService;


    /*
     * (non-Javadoc)
     * 
     * @see gov.nih.nci.cabig.ctms.common.RegistrationConsumer#createRegistration(gov.nih.nci.cabig.ctms.grid.RegistrationType)
     */
    public void register(RegistrationType registration) throws RemoteException, InvalidRegistration, RegistrationFailed {
        /**
         * If we were using castor to deserialize

         try {
         StringWriter sw = new StringWriter();
         ObjectSerializer.serialize(sw,registration,new javax.xml.namespace.QName("http://semanticbits.com/registration.xsd","registration"));

         System.out.println(sw.toString());

         XmlMarshaller marshall = new XmlMarshaller();
         StudyParticipantAssignment c3prv2Registration = (StudyParticipantAssignment) marshall.fromXML(new StringReader(sw.toString()));

         System.out.println(c3prv2Registration.getStudyParticipantIdentifier());
         } catch (Exception e) {
         throw new RemoteException(e.getMessage());
         }
         */

        Study study = new Study();
        study.setGridId(registration.getStudyGridId());

        HealthcareSite site = new HealthcareSite();
        site.setGridId(registration.getHealthCareSiteGridId());

        Participant participant = new Participant();
        ParticipantType partBean = registration.getParticipant();
        participant.setGridId(partBean.getParticipantGridId());
        participant.setAdministrativeGenderCode(partBean.getAdministrativeGenderCode());
        participant.setBirthDate(partBean.getBirthDate());
        participant.setEthnicGroupCode(partBean.getEthnicGroupCode());
        participant.setFirstName(partBean.getFirstName());
        participant.setLastName(partBean.getLastName());
        participant.setRaceCode(partBean.getRaceCode());



        StudyParticipantAssignment assignment = studyService.assignParticipant(study, participant, site);
        logger.debug("Created assignment " + assignment.getGridId());
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
