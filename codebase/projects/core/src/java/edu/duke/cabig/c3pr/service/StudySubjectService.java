package edu.duke.cabig.c3pr.service;

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
	  public StudySubject createRegistration (StudySubject studySubject);
}

