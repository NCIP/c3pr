package edu.duke.cabig.c3pr.service.impl;

import edu.duke.cabig.c3pr.dao.ParticipantDao;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.service.ParticipantService;

/**
 * @author Kulasekaran
 * @version 1.0
 *
 */
public class ParticipantServiceImpl implements ParticipantService {

	ParticipantDao participantDao;

	/**
	  Saves a Participant
	*/
	public void saveParticipant(Participant participant) throws Exception {
		participantDao.saveParticipant(participant);		
	}

	public ParticipantDao getParticipantDao() {
		return participantDao;
	}


	public void setParticipantDao(ParticipantDao participantDao) {
		this.participantDao = participantDao;
	}
}
