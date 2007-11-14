package edu.duke.cabig.c3pr.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.context.MessageSource;

import edu.duke.cabig.c3pr.dao.HealthcareSiteDao;
import edu.duke.cabig.c3pr.dao.ParticipantDao;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.esb.impl.MessageBroadcastServiceImpl;
import edu.duke.cabig.c3pr.exception.C3PRBaseException;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;
import edu.duke.cabig.c3pr.exception.C3PRExceptionHelper;
import edu.duke.cabig.c3pr.service.ParticipantService;

/**
 * @author Kulasekaran, Ramakrishna
 * @version 1.0
 *
 */
public class ParticipantServiceImpl implements ParticipantService {

	private static final Logger logger = Logger.getLogger(ParticipantServiceImpl.class);
	private String isBroadcastEnable="true";
	private MessageBroadcastServiceImpl messageBroadcaster;
	private C3PRExceptionHelper exceptionHelper;
	private MessageSource c3prErrorMessages;
	private HealthcareSiteDao healthcareSiteDao;

	public void setHealthcareSiteDao(HealthcareSiteDao healthcareSiteDao) {
		this.healthcareSiteDao = healthcareSiteDao;
	}

	public void setC3prErrorMessages(MessageSource errorMessages) {
		c3prErrorMessages = errorMessages;
	}

	public void setExceptionHelper(C3PRExceptionHelper exceptionHelper) {
		this.exceptionHelper = exceptionHelper;
	}

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
	
	public List<Participant> searchByMRN(OrganizationAssignedIdentifier identifier)throws C3PRCodedException{
     	HealthcareSite healthcareSite = this.healthcareSiteDao.getByNciInstituteCode(identifier.getHealthcareSite().getNciInstituteCode());
		if (healthcareSite == null) {
			throw this.exceptionHelper.getException(getCode("C3PR.EXCEPTION.REGISTRATION.IMPORT.INVALID.HEALTHCARESITE_SUBJECT_IDENTIFIER.CODE")
                    ,new String[]{identifier.getHealthcareSite().getNciInstituteCode(), identifier.getType()});
		}
		identifier.setHealthcareSite(healthcareSite);
    	return participantDao.searchByOrgIdentifier(identifier);
    }
	private int getCode(String errortypeString){
		return Integer.parseInt(this.c3prErrorMessages.getMessage(errortypeString, null, null));
	}
}
