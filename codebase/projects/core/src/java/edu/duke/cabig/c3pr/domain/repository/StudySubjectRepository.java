package edu.duke.cabig.c3pr.domain.repository;

import java.util.Date;
import java.util.List;

import edu.duke.cabig.c3pr.domain.EligibilityCriteria;
import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.OffEpochReason;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.StudySubjectConsentVersion;
import edu.duke.cabig.c3pr.domain.SubjectEligibilityAnswer;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;
import edu.duke.cabig.c3pr.utils.IdentifierGenerator;

public interface StudySubjectRepository {
    
    public void assignC3DIdentifier(StudySubject studySubject, String c3dIdentifierValue);
    
    public void assignMedidataIdentifier(StudySubject studySubject, String c3dIdentifierValue);

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
    
    public StudySubject importStudySubject(StudySubject deserialedStudySubject)
	throws C3PRCodedException;
    
    public List<StudySubject> findRegistrations(StudySubject exampleStudySubject);
    
    public StudySubject register(StudySubject studySubject);
    
    public StudySubject register(Identifier studySubjectIdentifier);
    
    public StudySubject enroll(Identifier studySubjectIdentifier) throws C3PRCodedException;
    
    public StudySubject enroll(StudySubject studySubject) throws C3PRCodedException;
    
    public StudySubject transferSubject(Identifier studySubjectIdentifier);
    
    public StudySubject transferSubject(StudySubject studySubject);
    
    public StudySubject takeSubjectOffStudy(Identifier studySubjectIdentifier, List<OffEpochReason> offStudyReasons, Date offStudyDate);
    
    public StudySubject create(StudySubject studySubject);
    
    public StudySubject reserve(StudySubject studySubject);
    
    public StudySubject reserve(Identifier studySubjectIdentifier);
    
    public StudySubject getUniqueStudySubject(Identifier studySubjectIdentifier);
    
	public StudySubject invalidateRegistration(StudySubject studySubject);
	
	public StudySubject allowEligibilityWaiver(Identifier studySubjectIdentifier, List<EligibilityCriteria> eligibilityCrieteria,  String waivedByPersonnelAssignedIdentifier);
	
	public StudySubject waiveEligibility(Identifier studySubjectIdentifier, List<SubjectEligibilityAnswer> subjectEligibilityAnswers);
	
	public StudySubject failScreening(Identifier studySubjectIdentifier, List<OffEpochReason> offScreeningReasons, Date failScreeningDate);
    
	public StudySubject takeSubjectOffCurrentEpoch(Identifier studySubjectIdentifier, List<OffEpochReason> offEpochReasons, Date offEpochDate);
	
	public StudySubject reConsent(String studyVersionName, List<StudySubjectConsentVersion> studySubjectConsentVersionsHolder, Identifier studySubjectIdentifier);
	
	public StudySubject discontinueEnrollment(Identifier studySubjectIdentifier, List<OffEpochReason> discontinueEpochReasons, Date discontinueEpochDate);
}
