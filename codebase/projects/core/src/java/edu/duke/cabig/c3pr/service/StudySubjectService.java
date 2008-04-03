package edu.duke.cabig.c3pr.service;

import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;

/**
 * @author Kulasekaran,Ramakrishna
 * @version 1.0
 */
public interface StudySubjectService extends CCTSWorkflowService {

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

    public void sendRegistrationRequest(StudySubject studySubject) throws C3PRCodedException;

    public StudySubject register(StudySubject studySubject);
    
    public StudySubject processAffliateSiteRegistrationRequest(StudySubject studySubject)
    throws C3PRCodedException;
}
