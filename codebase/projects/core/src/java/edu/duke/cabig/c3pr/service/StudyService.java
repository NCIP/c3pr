package edu.duke.cabig.c3pr.service;

import java.util.List;

import edu.duke.cabig.c3pr.domain.CoordinatingCenterStudyStatus;
import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.ServiceName;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyOrganization;
import edu.duke.cabig.c3pr.domain.StudySite;


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
    
    public void createStudyAtAffiliates(List<Identifier> studyIdentifiers);
    
    public void createStudyAtAffiliate(List<Identifier> studyIdentifiers, String nciInstituteCode);

    public void openStudyAtAffiliates(List<Identifier> studyIdentifiers);

    public void openStudyAtAffiliate(List<Identifier> studyIdentifiers, String nciInstituteCode);
    
    public void amendStudyAtAffiliates(List<Identifier> studyIdentifiers, Study amendedStudyDetails);

    public void updateAffliateProtocolVersion(List<Identifier> studyIdentifiers,
                    String nciInstituteCode, String version);

    public void closeStudyAtAffiliates(List<Identifier> studyIdentifiers);
    
    public void closeStudyAtAffiliate(List<Identifier> studyIdentifiers, String nciInstituteCode);
    
    public void updateStudyStatusAtAffiliates(List<Identifier> studyIdentifiers,
                    CoordinatingCenterStudyStatus status);
}
