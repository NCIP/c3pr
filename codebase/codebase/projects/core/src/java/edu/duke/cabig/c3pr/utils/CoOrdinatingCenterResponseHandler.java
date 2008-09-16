package edu.duke.cabig.c3pr.utils;

import java.io.StringReader;

import edu.duke.cabig.c3pr.domain.CCTSWorkflowStatusType;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.esb.MessageResponseHandler;
import edu.duke.cabig.c3pr.service.StudySubjectService;
import edu.duke.cabig.c3pr.xml.XmlMarshaller;
import gov.nih.nci.common.exception.XMLUtilityException;

public class CoOrdinatingCenterResponseHandler extends AbstractRequestHandler<StudySubject>{

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
        studySubjectService.processCoOrdinatingCenterResponse(messageObject);
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
    public CCTSWorkflowStatusType multisiteWorkflowStatus() {
        // TODO Auto-generated method stub
        return CCTSWorkflowStatusType.MESSAGE_RESPONSE_RECIEVED;
    }
}
