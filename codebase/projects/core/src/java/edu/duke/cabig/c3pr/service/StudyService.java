package edu.duke.cabig.c3pr.service;

import edu.duke.cabig.c3pr.domain.CoordinatingCenterStudyStatus;
import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;

import java.util.List;
import java.util.Date;


/**
 * Interface for Services on Study related domain object
 * 
 * @author priyatam
 */
public interface StudyService extends CCTSWorkflowService {
    public List<Study> searchByExample(Study study, int maxResults);
    
    public int countAcrrualsByDate(Study study, Date startDate, Date endDate);
    
    public List<Study> searchByExample(Study study, boolean isWildCard, int maxResults, String order, String orderBy);
    
    public void createStudy(Study study);
    
    public void openStudy(List<Identifier> studyIdentifiers)throws C3PRCodedException;
    
    public void approveStudySiteForActivation(List<Identifier> studyIdentifiers, String nciInstituteCode)throws C3PRCodedException;
    
    public void activateStudySite(List<Identifier> studyIdentifiers, String nciInstituteCode)throws C3PRCodedException;

    public void amendStudy(List<Identifier> studyIdentifiers, Study amendedStudyDetails)throws C3PRCodedException;
    
    public void updateStudySiteProtocolVersion(List<Identifier> studyIdentifiers, String nciInstituteCode, String version);

    public void closeStudy(List<Identifier> studyIdentifiers)throws C3PRCodedException;
    
    public void updateStudyStatus(List<Identifier> studyIdentifiers, CoordinatingCenterStudyStatus status) throws C3PRCodedException;
    
    public void closeStudySite(List<Identifier> studyIdentifiers, String nciInstituteCode) throws C3PRCodedException;
    
    public void closeStudySites(List<Identifier> studyIdentifiers) throws C3PRCodedException;
}
