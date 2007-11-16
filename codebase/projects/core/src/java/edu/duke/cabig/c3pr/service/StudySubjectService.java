package edu.duke.cabig.c3pr.service;

import edu.duke.cabig.c3pr.domain.RegistrationDataEntryStatus;
import edu.duke.cabig.c3pr.domain.ScheduledEpochDataEntryStatus;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;

/**
 * @author Kulasekaran,Ramakrishna
 * @version 1.0
 */
public interface StudySubjectService {	  
	  
	  /**
		 * Search using a sample. Populate a Participant object
		 * @param  Participant object
		 * @return List of Participant objects based on the sample participant object
		 * @throws Runtime exception 
		 */
	  public StudySubject createRegistration (StudySubject studySubject)throws C3PRCodedException;
	  
	  public StudySubject registerSubject(StudySubject studySubject) throws C3PRCodedException;
	  
	  public RegistrationDataEntryStatus evaluateRegistrationDataEntryStatus(StudySubject studySubject);
	  
	  public ScheduledEpochDataEntryStatus evaluateScheduledEpochDataEntryStatus(StudySubject studySubject);
	  
	  public boolean canRandomize(StudySubject studySubject);
	  
	  public void manageRegWorkFlow(StudySubject studySubject)throws C3PRCodedException;
	  
	  public void manageSchEpochWorkFlow(StudySubject studySubject)throws C3PRCodedException;
	  
	  public boolean isRegisterable(StudySubject studySubject);
	  
	  public void setHostedMode(boolean hostedMode);
	  
	  public void assignC3DIdentifier(StudySubject studySubject, String c3dIdentifierValue);
	  
	  public void assignCoOrdinatingCenterIdentifier(String studySubjectGridId, String identifierValue);
	  
	  public boolean isEpochAccrualCeilingReached(int epochId);
	  
	  public boolean requiresCoordinatingCenterApproval(StudySubject studySubject);
	  
	  public StudySubject processAffliateSiteRegistrationRequest(StudySubject studySubject) throws Exception;
	  
	  public void sendRegistrationRequest(StudySubject studySubject) throws C3PRCodedException;
	  
	  public void sendRegistrationEvent(StudySubject studySubject) throws C3PRCodedException;
	  
	  public StudySubject buildStudySubject(StudySubject deserializedStudySubject) throws C3PRCodedException;
}

