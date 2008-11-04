package edu.duke.cabig.c3pr.service;

import edu.duke.cabig.c3pr.domain.ServiceName;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyOrganization;


/**
 * Interface for Services on Study related domain object
 * 
 * @author priyatam
 */
public interface StudyService extends MultiSiteWorkflowService, CCTSWorkflowService{
    public boolean canMultisiteBroadcast(StudyOrganization studyOrganization);
    
    public String getLocalNCIInstituteCode();
    
    public ServiceName getMultisiteServiceName();
    
    public boolean isStudyOrganizationLocal(String nciInstituteCode);
    
}
