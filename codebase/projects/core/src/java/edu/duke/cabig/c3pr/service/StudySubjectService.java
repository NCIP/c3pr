package edu.duke.cabig.c3pr.service;

import edu.duke.cabig.c3pr.domain.RegistrationDataEntryStatus;
import edu.duke.cabig.c3pr.domain.ScheduledEpochDataEntryStatus;
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

    public void manageRegWorkFlow(StudySubject studySubject) throws C3PRCodedException;

    public void manageSchEpochWorkFlow(StudySubject studySubject) throws C3PRCodedException;

    public void setHostedMode(boolean hostedMode);

    public void sendRegistrationRequest(StudySubject studySubject) throws C3PRCodedException;

    public void manageSchEpochWorkFlow(StudySubject studySubject, boolean triggerMultisite,
                    boolean randomize, boolean affiliateSiteRequest) throws C3PRCodedException;  
    
    public void register(StudySubject studySubject);
}
