package edu.duke.cabig.c3pr.utils;

import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.SystemAssignedIdentifier;
import edu.duke.cabig.c3pr.esb.CaXchangeMessageResponseHandler;
import edu.duke.cabig.c3pr.service.StudySubjectService;
import edu.duke.cabig.c3pr.xml.XmlMarshaller;
import gov.nih.nci.caxchange.MessagePayload;
import gov.nih.nci.caxchange.Response;
import gov.nih.nci.caxchange.TargetResponseMessage;
import gov.nih.nci.common.exception.XMLUtilityException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;

import java.io.StringReader;

/**
 * Handles c3d patient position
 * <p/>
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Nov 27, 2007
 * Time: 10:11:56 AM
 * To change this template use File | Settings | File Templates.
 */
public class C3DPatientPositionResponseHandler implements CaXchangeMessageResponseHandler {

    public static final String C3D_SERVICE_IDENTIFIER = "C3D";

    Logger log = Logger.getLogger(C3DPatientPositionResponseHandler.class);
    private XmlMarshaller marshaller;
    private StudySubjectService subjectService;


    public void handleMessageResponse(String string, Response response) {
        log.debug("Looking for c3d identifier");

        for (TargetResponseMessage tResponse : response.getTargetResponse()) {

            if (tResponse.getTargetServiceIdentifier().indexOf(C3D_SERVICE_IDENTIFIER) > -1) {
                log.debug("Found c3d response. Processing...");
                MessagePayload payload = tResponse.getTargetBusinessMessage();
                StudySubject c3dSubject;
                try {
                    c3dSubject = (StudySubject) marshaller.fromXML(new StringReader(payload.toString()));
                    for (SystemAssignedIdentifier sId : c3dSubject.getSystemAssignedIdentifiers()) {
                        if (sId.getSystemName().toUpperCase().indexOf(C3D_SERVICE_IDENTIFIER) > -1) {
                            log.debug("Found c3d identifier.processing");
                            subjectService.assignC3DIdentifier(c3dSubject, sId.getValue());
                        }
                    }
                    ;

                } catch (XMLUtilityException e) {
                    log.error("Could not deserialize c3d response." + e.getMessage());
                }
                log.debug("Assigned c3d patient position sucessfully");
            }

        }
    }

    public XmlMarshaller getMarshaller() {
        return marshaller;
    }

    @Required
    public void setMarshaller(XmlMarshaller marshaller) {
        this.marshaller = marshaller;
    }

    public StudySubjectService getSubjectService() {
        return subjectService;
    }

    @Required
    public void setSubjectService(StudySubjectService subjectService) {
        this.subjectService = subjectService;
    }

}
