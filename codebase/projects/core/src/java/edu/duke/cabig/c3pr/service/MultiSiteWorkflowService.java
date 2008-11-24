package edu.duke.cabig.c3pr.service;

import java.util.ArrayList;
import java.util.List;

import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.WorkFlowStatusType;
import edu.duke.cabig.c3pr.domain.InteroperableAbstractMutableDeletableDomainObject;
import edu.duke.cabig.c3pr.domain.APIName;
import edu.duke.cabig.c3pr.domain.ServiceName;
import edu.duke.cabig.c3pr.domain.StudyOrganization;
import edu.duke.cabig.c3pr.domain.StudySubject;
import gov.nih.nci.cabig.ctms.domain.AbstractMutableDomainObject;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Dec 6, 2007 Time: 4:15:53 PM To change this template
 * use File | Settings | File Templates.
 */
public interface MultiSiteWorkflowService<C extends InteroperableAbstractMutableDeletableDomainObject> {

//    public void sendRegistrationRequest(StudySubject studySubject);
//    
//    public void sendRegistrationResponse(StudySubject studySubject);
//    
//    public CCTSWorkflowStatusType getMultiSiteWofkflowStatus(C cctsObject);
    
    public boolean canMultisiteBroadcast(StudyOrganization studyOrganization);
    
    public void handleAffiliateSiteBroadcast(String nciInstituteCode, Study study, APIName multisiteAPIName, List<? extends AbstractMutableDomainObject> domainObjects);
    
    public void handleAffiliateSitesBroadcast(Study study, APIName multisiteAPIName, List<? extends AbstractMutableDomainObject> domainObjects);
    
    public void handleCoordinatingCenterBroadcast(Study study, APIName multisiteAPIName, List<? extends AbstractMutableDomainObject> domainObjects);
    
    public <T extends AbstractMutableDomainObject> void handleMultiSiteBroadcast(List<StudyOrganization> studyOrganizations, ServiceName multisiteServiceName, APIName multisiteAPIName, List<T> domainObjects);
    
    public <T extends AbstractMutableDomainObject> void handleMultiSiteBroadcast(StudyOrganization studyOrganization, ServiceName multisiteServiceName, APIName multisiteAPIName, List<T> domainObjects);

}
