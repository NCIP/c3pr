package edu.duke.cabig.c3pr.utils;

import java.io.StringReader;

import org.apache.axis.message.MessageElement;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;

import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.SystemAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.repository.StudySubjectRepository;
import edu.duke.cabig.c3pr.esb.CaXchangeMessageResponseHandler;
import edu.duke.cabig.c3pr.xml.XmlMarshaller;
import gov.nih.nci.caxchange.MessagePayload;
import gov.nih.nci.caxchange.Response;
import gov.nih.nci.caxchange.TargetResponseMessage;
import gov.nih.nci.common.exception.XMLUtilityException;

/**
 * Handles c3d patient position <p/> Created by IntelliJ IDEA. User: kherm Date: Nov 27, 2007 Time:
 * 10:11:56 AM To change this template use File | Settings | File Templates.
 */
public class C3DPatientPositionResponseHandler implements CaXchangeMessageResponseHandler {

    public static final String C3D_SERVICE_IDENTIFIER = "C3D";

    public static final String REGISTRATION_MESSAGE_ELEMENT_NAME = "registration";

    Logger log = Logger.getLogger(C3DPatientPositionResponseHandler.class);

    private XmlMarshaller marshaller;

    private StudySubjectRepository studySubjectRepository;

    public void handleMessageResponse(String string, Response response) {
        log.debug("Will look for c3d identifier in response message");

        for (TargetResponseMessage tResponse : response.getTargetResponse()) {

            if (tResponse.getTargetServiceIdentifier().indexOf(C3D_SERVICE_IDENTIFIER) > -1) {
                log.debug("Found c3d response. Processing...");
                MessagePayload payload = tResponse.getTargetBusinessMessage();

                MessageElement[] elems = payload.get_any();
                for (MessageElement elem : elems) {
                    if (REGISTRATION_MESSAGE_ELEMENT_NAME.equalsIgnoreCase(elem.getName())) {

                        log.debug("Found Registration element in c3d response. Processing....");

                        StudySubject c3dSubject;
                        try {
                            c3dSubject = (StudySubject) marshaller.fromXML(new StringReader(elem
                                            .toString()));
                            for (SystemAssignedIdentifier sId : c3dSubject
                                            .getSystemAssignedIdentifiers()) {
                                if (sId.getSystemName().toUpperCase().indexOf(
                                                C3D_SERVICE_IDENTIFIER) > -1) {
                                    log.debug("Found c3d identifier.processing");
                                    try {
                                        studySubjectRepository.assignC3DIdentifier(c3dSubject, sId
                                                        .getValue());
                                    }
                                    catch (Exception e) {
                                        log.error("Could not assign identifier." + e.getMessage());
                                    }
                                }
                            }
                            ;

                        }
                        catch (XMLUtilityException e) {
                            log.error("Could not deserialize c3d response." + e.getMessage());
                        }
                    }
                }

            }
        }
    }

    @Required
    public XmlMarshaller getMarshaller() {
        return marshaller;
    }

    public void setMarshaller(XmlMarshaller marshaller) {
        this.marshaller = marshaller;
    }

    public void setStudySubjectRepositoryNew(StudySubjectRepository studySubjectRepository) {
        this.studySubjectRepository = studySubjectRepository;
    }

}
