package edu.duke.cabig.c3pr.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import edu.duke.cabig.c3pr.domain.Participant;

/**
 * 
 * @author Kulasekaran,Ramakrishna
 * @version 1.0
 */

public interface ParticipantDao extends BaseDao{

	/**
	 * Add a new Participant to the data source 
	 * @param Participant
	 * @throws Exception
	 */
	public void saveParticipant(Participant participant) throws Exception;
	
	public List<Participant> getAll(); 
	 
	/**
	 * Searches based on an example object.	
	 * @param participant
	 * @return 
	 * @throws DataAccessException
	 */
	public List<Participant> searchByExample(Participant participant) throws DataAccessException;
	
}

