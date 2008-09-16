package edu.duke.cabig.c3pr.service;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import edu.duke.cabig.c3pr.domain.CCTSAbstractMutableDeletableDomainObject;
import edu.duke.cabig.c3pr.domain.CCTSWorkflowStatusType;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Dec 6, 2007 Time: 4:15:53 PM To change this template
 * use File | Settings | File Templates.
 */
public interface MultiSiteWorkflowService<C extends CCTSAbstractMutableDeletableDomainObject> {

    public void sendRegistrationRequest(StudySubject studySubject);
    
    public void sendRegistrationResponse(StudySubject studySubject);
    
    public CCTSWorkflowStatusType getMultiSiteWofkflowStatus(C cctsObject);

}
