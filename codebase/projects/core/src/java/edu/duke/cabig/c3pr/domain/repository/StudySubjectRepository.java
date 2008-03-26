package edu.duke.cabig.c3pr.domain.repository;

import edu.duke.cabig.c3pr.domain.RegistrationDataEntryStatus;
import edu.duke.cabig.c3pr.domain.ScheduledEpochDataEntryStatus;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.exception.C3PRBaseException;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;

public interface StudySubjectRepository {
    public StudySubject createRegistration(StudySubject studySubject) throws C3PRCodedException;

    public StudySubject registerSubject(StudySubject studySubject) throws C3PRCodedException;

    public void assignC3DIdentifier(StudySubject studySubject, String c3dIdentifierValue);

    public void assignCoOrdinatingCenterIdentifier(String studySubjectGridId, String identifierValue);

    public boolean isEpochAccrualCeilingReached(int epochId);
    
    public StudySubject processAffliateSiteRegistrationRequest(StudySubject studySubject)
    throws C3PRCodedException;
    
    public StudySubject doRandomization(StudySubject studySubject) throws C3PRBaseException;
}
