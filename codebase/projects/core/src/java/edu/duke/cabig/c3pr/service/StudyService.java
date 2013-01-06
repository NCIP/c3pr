package edu.duke.cabig.c3pr.service;



/**
 * Interface for Services on Study related domain object
 * 
 * @author priyatam
 */
public interface StudyService extends CCTSWorkflowService{
    public String getLocalNCIInstituteCode();
    
    public boolean isStudyOrganizationLocal(String nciInstituteCode);
    
    //public <T extends AbstractMutableDomainObject> void handleMultiSiteBroadcast(List<StudyOrganization> studyOrganizations, ServiceName multisiteServiceName, APIName multisiteAPIName, List<T> domainObjects);
}
