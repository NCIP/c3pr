package edu.duke.cabig.c3pr.service;

import edu.duke.cabig.c3pr.domain.RegistrationDataEntryStatus;
import edu.duke.cabig.c3pr.domain.ScheduledEpochDataEntryStatus;
import edu.duke.cabig.c3pr.domain.StudySubject;

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
	  public StudySubject createRegistration (StudySubject studySubject)throws Exception;
	  
	  public StudySubject registerSubject(StudySubject studySubject) throws Exception;
	  
	  public RegistrationDataEntryStatus evaluateRegistrationDataEntryStatus(StudySubject studySubject);
	  
	  public ScheduledEpochDataEntryStatus evaluateScheduledEpochDataEntryStatus(StudySubject studySubject);
	  
	  public boolean canRandomize(StudySubject studySubject);
	  
	  public void manageRegWorkFlow(StudySubject studySubject)throws Exception;
	  
	  public void manageSchEpochWorkFlow(StudySubject studySubject)throws Exception;
	  
	  public boolean isRegisterable(StudySubject studySubject);
	  
	  public void setHostedMode(boolean hostedMode);
}

