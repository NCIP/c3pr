package edu.duke.cabig.c3pr.domain.repository.impl;

import java.util.List;

import org.springframework.context.MessageSource;
import org.springframework.transaction.annotation.Transactional;

import edu.duke.cabig.c3pr.dao.ParticipantDao;
import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.repository.ParticipantRepository;
import edu.duke.cabig.c3pr.exception.C3PRExceptionHelper;

@Transactional
public class ParticipantRepositoryImpl implements ParticipantRepository {
	
	public ParticipantDao participantDao ;
	private C3PRExceptionHelper exceptionHelper;
	private MessageSource c3prErrorMessages;
	
	public Participant getUniqueParticipant(List<Identifier> identifiers) {
		List<Participant> participants = participantDao.getByIdentifiers(identifiers);
        if (participants.size() == 0) {
            throw this.exceptionHelper.getRuntimeException(getCode("C3PR.EXCEPTION.REGISTRATION.NOT_FOUND_GIVEN_IDENTIFIERS.CODE"));
        }
        else if (participants.size() > 1) {
            throw this.exceptionHelper.getRuntimeException(getCode("C3PR.EXCEPTION.REGISTRATION.MULTIPLE_STUDYSUBJECTS_FOUND.CODE"));
        }
        return participants.get(0);
	}

	public ParticipantDao getParticipantDao() {
		return participantDao;
	}

	public void setParticipantDao(ParticipantDao participantDao) {
		this.participantDao = participantDao;
	}

	public C3PRExceptionHelper getExceptionHelper() {
		return exceptionHelper;
	}

	public void setExceptionHelper(C3PRExceptionHelper exceptionHelper) {
		this.exceptionHelper = exceptionHelper;
	}
	
	private int getCode(String errortypeString) {
        return Integer.parseInt(this.c3prErrorMessages.getMessage(errortypeString, null, null));
    }

	public MessageSource getC3prErrorMessages() {
		return c3prErrorMessages;
	}

	public void setC3prErrorMessages(MessageSource errorMessages) {
		c3prErrorMessages = errorMessages;
	}
    
}
