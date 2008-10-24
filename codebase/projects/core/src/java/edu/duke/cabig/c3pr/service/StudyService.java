package edu.duke.cabig.c3pr.service;

import edu.duke.cabig.c3pr.domain.ServiceName;
import edu.duke.cabig.c3pr.domain.Study;


/**
 * Interface for Services on Study related domain object
 * 
 * @author priyatam
 */
public interface StudyService extends MultiSiteWorkflowService, CCTSWorkflowService{
    public boolean canMultisiteBroadcast(Study study);
    
    public String getLocalNCIInstituteCode();
    
    public ServiceName getMultisiteServiceName();
    
    public boolean isStudyOrganizationLocal(String nciInstituteCode);
    
}
