package edu.duke.cabig.c3pr.service;

import java.util.List;

import edu.duke.cabig.c3pr.domain.Participant;

/**
 * @author Kulasekaran,Ramakrishna
 * @version 1.0
 */
public interface ParticipantService {	  
	  
	  /**
		 * Search using a sample. Populate a Participant object
		 * @param  Participant object
		 * @return List of Participant objects based on the sample participant object
		 * @throws Runtime exception 
		 */
	  public List <Participant> search (Participant participant) throws Exception;
}

