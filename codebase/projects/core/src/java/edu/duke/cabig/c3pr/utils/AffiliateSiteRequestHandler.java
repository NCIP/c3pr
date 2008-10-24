package edu.duke.cabig.c3pr.utils;

import java.io.StringReader;

import edu.duke.cabig.c3pr.domain.WorkFlowStatusType;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.esb.MessageResponseHandler;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;
import edu.duke.cabig.c3pr.service.StudySubjectService;
import edu.duke.cabig.c3pr.xml.XmlMarshaller;
import gov.nih.nci.common.exception.XMLUtilityException;

public class AffiliateSiteRequestHandler extends AbstractRequestHandler<StudySubject>{

    private StudySubjectService studySubjectService;
    private XmlMarshaller xmlUtility;
    
    public void setXmlUtility(XmlMarshaller xmlUtility) {
        this.xmlUtility = xmlUtility;
    }
    public void setStudySubjectService(StudySubjectService studySubjectService) {
        this.studySubjectService = studySubjectService;
    }

    @Override
    public void handlerRequest(StudySubject messageObject) {
        studySubjectService.processAffliateSiteRegistrationRequest(messageObject);
    }
    
    @Override
    public StudySubject messageAsObject(String message) {
        try {
            return (StudySubject) xmlUtility.fromXML(new StringReader(message));
        }catch (XMLUtilityException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public WorkFlowStatusType multisiteWorkflowStatus() {
        // TODO Auto-generated method stub
        return WorkFlowStatusType.MESSAGE_RECIEVED;
    }
}
