package edu.duke.cabig.c3pr.domain.repository;

import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.exception.C3PRBaseException;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;

public interface StudySubjectRepository {
    
    public StudySubject createRegistration(StudySubject studySubject) throws C3PRCodedException;

    public StudySubject register(StudySubject studySubject) throws C3PRCodedException;

    public void assignC3DIdentifier(StudySubject studySubject, String c3dIdentifierValue);

    public void assignCoOrdinatingCenterIdentifier(String studySubjectGridId, String identifierValue);

    public boolean isEpochAccrualCeilingReached(int epochId);
    
    public StudySubject processAffliateSiteRegistrationRequest(StudySubject studySubject)
    throws C3PRCodedException;
    
    public StudySubject doRandomization(StudySubject studySubject) throws C3PRBaseException;
    
    /**
     * @param studySubject
     * @return
     * save or updates the studySubject instance and returns a persisted instance.
     * Clients are advised to use the returned instance for persistence expectations
     * since the argument instance might or might not become the persisted instance  
     */
    public StudySubject save(StudySubject studySubject);
    
    public void doLocalRegistration(StudySubject studySubject, boolean randomize, boolean affiliateSiteRequest) throws C3PRCodedException;
    
    public StudySubject importStudySubject(StudySubject deserialedStudySubject)
	throws C3PRCodedException;
}
