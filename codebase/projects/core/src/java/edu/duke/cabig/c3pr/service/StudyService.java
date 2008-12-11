package edu.duke.cabig.c3pr.service;

import java.util.List;

import edu.duke.cabig.c3pr.domain.APIName;
import edu.duke.cabig.c3pr.domain.CoordinatingCenterStudyStatus;
import edu.duke.cabig.c3pr.domain.EndPoint;
import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.ServiceName;
import edu.duke.cabig.c3pr.domain.Study;
import gov.nih.nci.cabig.ctms.domain.AbstractMutableDomainObject;


/**
 * Interface for Services on Study related domain object
 * 
 * @author priyatam
 */
public interface StudyService extends MultiSiteWorkflowService, CCTSWorkflowService{
    public String getLocalNCIInstituteCode();
    
    public boolean isStudyOrganizationLocal(String nciInstituteCode);
    
    //public <T extends AbstractMutableDomainObject> void handleMultiSiteBroadcast(List<StudyOrganization> studyOrganizations, ServiceName multisiteServiceName, APIName multisiteAPIName, List<T> domainObjects);
}
