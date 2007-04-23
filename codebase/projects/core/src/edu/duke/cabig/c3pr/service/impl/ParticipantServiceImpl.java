package edu.duke.cabig.c3pr.service.impl;

import java.util.List;

import org.apache.log4j.Logger;

import edu.duke.cabig.c3pr.dao.ParticipantDao;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.StudyParticipantAssignment;
import edu.duke.cabig.c3pr.esb.impl.MessageBroadcastServiceImpl;
import edu.duke.cabig.c3pr.service.ParticipantService;
import edu.duke.cabig.c3pr.utils.XMLUtils;

/**
 * @author Kulasekaran, Ramakrishna
 * @version 1.0
 *
 */
public class ParticipantServiceImpl implements ParticipantService {

	private static final Logger logger = Logger.getLogger(ParticipantServiceImpl.class);
	private String isBroadcastEnable="true";
	private MessageBroadcastServiceImpl messageBroadcaster;

	public MessageBroadcastServiceImpl getMessageBroadcaster() {
		return messageBroadcaster;
	}

	public void setMessageBroadcaster(
			MessageBroadcastServiceImpl messageBroadcaster) {
		this.messageBroadcaster = messageBroadcaster;
	}

	public String getIsBroadcastEnable() {
		return isBroadcastEnable;
	}

	public void setIsBroadcastEnable(String isBroadcastEnable) {
		this.isBroadcastEnable = isBroadcastEnable;
	}

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


	public void createRegistration(StudyParticipantAssignment studyParticipantAssignment) {
		participantDao.save(studyParticipantAssignment.getParticipant());
		studyParticipantAssignment.setStudyParticipantIdentifier(studyParticipantAssignment.getId()+ "");
		if(isBroadcastEnable.equalsIgnoreCase("true")){
			String xml = "";
			try {
				xml = XMLUtils.toXml(studyParticipantAssignment);
				if (logger.isDebugEnabled()) {
					logger.debug(" - XML for Registration"); //$NON-NLS-1$
				}
				if (logger.isDebugEnabled()) {
					logger.debug(" - " + xml); //$NON-NLS-1$
				}
				messageBroadcaster.initialize();
				messageBroadcaster.broadcast(xml);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("", e); //$NON-NLS-1$
			}
		}
		
	}
}
