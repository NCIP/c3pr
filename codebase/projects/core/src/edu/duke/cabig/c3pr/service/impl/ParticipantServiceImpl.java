package edu.duke.cabig.c3pr.service.impl;

import java.util.List;

import edu.duke.cabig.c3pr.dao.ParticipantDao;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.service.ParticipantService;

/**
 * @author Kulasekaran, Ramakrishna
 * @version 1.0
 *
 */
public class ParticipantServiceImpl implements ParticipantService {

	ParticipantDao participantDao;
	
	public ParticipantDao getParticipantDao() {
		return participantDao;
	}


	public void setParticipantDao(ParticipantDao participantDao) {
		this.participantDao = participantDao;
	}
	
	/**
	 * Search using a sample. Populate a Participant object
	 * @param  Participant object
	 * @return List of Participant objects based on the sample participant object
	 * @throws Runtime exception 
	 */
	public List<Participant> search(Participant participant) throws Exception {		
		return participantDao.searchByExample(participant, true);
	}
}
