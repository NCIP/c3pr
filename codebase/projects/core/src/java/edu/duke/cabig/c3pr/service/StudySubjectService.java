package edu.duke.cabig.c3pr.service;

import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.esb.MessageResponseHandler;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;

import java.util.List;

/**
 * @author Kulasekaran,Ramakrishna
 * @version 1.0
 */
public interface StudySubjectService extends CCTSWorkflowService, MultiSiteWorkflowService {

    /**
     * Search using a sample. Populate a Participant object
     * 
     * @param StudySubject
     *                object
     * @return List of Participant objects based on the sample participant object
     * @throws Runtime
     *                 exception
     */

    public void setHostedMode(boolean hostedMode);

    public StudySubject register(StudySubject studySubject);
    
    public void processAffliateSiteRegistrationRequest(StudySubject studySubject);
    
    public void processCoOrdinatingCenterResponse(StudySubject deserializedStudySubject);
    
    public boolean requiresExternalApprovalForRegistration(StudySubject studySubject);

    public List<StudySubject> searchByExample(StudySubject ss, int maxResults);
    
    public List<StudySubject> getIncompleteRegistrations(StudySubject registration, int maxResults);
}
