package edu.duke.cabig.c3pr.utils;

import java.io.StringReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.axis.message.MessageElement;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;
import org.w3c.dom.Text;

import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.SystemAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.repository.StudySubjectRepository;
import edu.duke.cabig.c3pr.xml.XmlMarshaller;
import gov.nih.nci.caxchange.MessagePayload;
import gov.nih.nci.caxchange.Response;
import gov.nih.nci.caxchange.TargetResponseMessage;
import gov.nih.nci.common.exception.XMLUtilityException;

/**
 * Handles c3d patient position <p/> Created by IntelliJ IDEA. User: kherm Date: Nov 27, 2007 Time:
 * 10:11:56 AM To change this template use File | Settings | File Templates.
 */
public class C3DPatientPositionResponseHandler extends CaXchangeMessageResponseHandlerImpl {

    public static final String C3D_SERVICE_IDENTIFIER = "C3D";

    public static final String REGISTRATION_MESSAGE_ELEMENT_NAME = "registration";

    Logger log = Logger.getLogger(C3DPatientPositionResponseHandler.class);

    private XmlMarshaller marshaller;

    private StudySubjectRepository studySubjectRepository;

    public void processResponse(String objectId, Response response) {
        log.debug("Will look for c3d identifier in response message");

        for (TargetResponseMessage tResponse : response.getTargetResponse()) {

            if (tResponse.getTargetServiceIdentifier().indexOf(C3D_SERVICE_IDENTIFIER) > -1) {
                log.debug("Found c3d response. Processing...");
                MessagePayload payload = tResponse.getTargetBusinessMessage();

                MessageElement[] elems = payload.get_any();
                for (MessageElement elem : elems) {
                    if (elem.getTagName().indexOf(REGISTRATION_MESSAGE_ELEMENT_NAME) != -1) {

                        log.debug("Found Registration element in c3d response. Processing....");

                        StudySubject c3dSubject= new StudySubject();
                        c3dSubject.setGridId(objectId);
                        try {
                        	studySubjectRepository.assignC3DIdentifier(c3dSubject, findC3DIdentifier(elem));
                            return;
                        }
                        catch (RuntimeException e) {
                            log.error(e);
                            return;
                        }
                    }
                }
                log.debug("No registration element found in C3D response.");
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
    
    private String findC3DIdentifier(MessageElement messageElement){
		for (MessageElement me1 : getMessageElements(messageElement)){
			if(me1.getTagName().indexOf("identifier") != -1){
				String systemName="";
				String value="";
				for (MessageElement me2 : getMessageElements(me1)) {
					if(me2.getTagName().indexOf("value") != -1){
						value=me2.getValue();
					}else if(me2.getTagName().indexOf("systemName") != -1){
						systemName=me2.getValue();
					}
				}
				if(systemName.equalsIgnoreCase(C3D_SERVICE_IDENTIFIER)){
					if(StringUtils.getBlankIfNull(value).equals("")){
						throw new RuntimeException("Found C3D identifier in message but the value is blank------"+messageElement.toString()+"--------------------");
					}
					return value;
				}
			}
		}
		throw new RuntimeException("Cannot find C3D identifier in message------"+messageElement.toString()+"--------------------");
    }
    
    private List<MessageElement> getMessageElements(MessageElement messageElement){
    	Iterator it = messageElement.getChildElements();
    	List<MessageElement> messageList = new ArrayList<MessageElement>();
		while (it.hasNext()){
			Object obj = it.next();
			if (obj instanceof MessageElement) {
				messageList.add((MessageElement)obj);
			}
		}
		return messageList;
    }
}
