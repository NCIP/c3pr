package edu.duke.cabig.c3pr.domain.repository;

import java.util.Date;
import java.util.List;

import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;
import edu.duke.cabig.c3pr.utils.IdentifierGenerator;

public interface StudySubjectRepository {
    
    public void assignC3DIdentifier(StudySubject studySubject, String c3dIdentifierValue);

    public void assignCoOrdinatingCenterIdentifier(StudySubject studySubject, String identifierValue);

    public boolean isEpochAccrualCeilingReached(int epochId);
    
    /**
     * @param studySubject
     * @return
     * save or updates the studySubject instance and returns a persisted instance.
     * Clients are advised to use the returned instance for persistence expectations
     * since the argument instance might or might not become the persisted instance  
     */
    public StudySubject save(StudySubject studySubject);
    
    public StudySubject doLocalRegistration(StudySubject studySubject) throws C3PRCodedException;
    
    public StudySubject importStudySubject(StudySubject deserialedStudySubject)
	throws C3PRCodedException;
    
    public List<StudySubject> findRegistrations(StudySubject exampleStudySubject);
    
    public StudySubject register(StudySubject studySubject);
    
    public StudySubject register(List<Identifier> studySubjectIdentifiers);
    
    public StudySubject enroll(List<Identifier> studySubjectIdentifiers) throws C3PRCodedException;
    
    public StudySubject enroll(StudySubject studySubject) throws C3PRCodedException;
    
    public StudySubject transferSubject(List<Identifier> studySubjectIdentifiers);
    
    public StudySubject transferSubject(StudySubject studySubject);
    
    public void takeSubjectOffStudy(List<Identifier> studySubjectIdentifiers,String offStudyReasonText,Date offStudyDate);
    
    public StudySubject create(StudySubject studySubject);
    
    public StudySubject reserve(StudySubject studySubject);
    
    public StudySubject reserve(List<Identifier> studySubjectIdentifiers);
    
    public StudySubject getUniqueStudySubjects(List<Identifier> studySubjectIdentifiers);
    
    public void setIdentifierGenerator(IdentifierGenerator identifierGenerator) ; 
    
}
