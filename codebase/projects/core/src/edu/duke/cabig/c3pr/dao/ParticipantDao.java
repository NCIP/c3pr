package edu.duke.cabig.c3pr.dao;

import edu.duke.cabig.c3pr.domain.Participant;

/**
 * 
 * @author Kulasekaran
 * @version 1.0
 */

public interface ParticipantDao extends BaseDao{

	/**
	 * Add a new Participant to the data source 
	 * @param Participant
	 * @throws Exception
	 */
	public void saveParticipant(Participant participant) throws Exception;
	
}

