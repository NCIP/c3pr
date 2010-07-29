package edu.duke.cabig.c3pr.domain.repository.impl;

import java.util.List;

import org.springframework.context.MessageSource;
import org.springframework.transaction.annotation.Transactional;

import edu.duke.cabig.c3pr.dao.ParticipantDao;
import edu.duke.cabig.c3pr.dao.StudySubjectDemographicsDao;
import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.repository.ParticipantRepository;
import edu.duke.cabig.c3pr.exception.C3PRExceptionHelper;
import edu.duke.cabig.c3pr.utils.IdentifierGenerator;

@Transactional
public class ParticipantRepositoryImpl implements ParticipantRepository {
	
	public ParticipantDao participantDao ;
	private C3PRExceptionHelper exceptionHelper;
	private MessageSource c3prErrorMessages;

	private StudySubjectDemographicsDao studySubjectDemographicsDao;
	
	public void setStudySubjectDemographicsDao(
			StudySubjectDemographicsDao studySubjectDemographicsDao) {
		this.studySubjectDemographicsDao = studySubjectDemographicsDao;
	}
	
	private IdentifierGenerator identifierGenerator;
	
	public void setIdentifierGenerator(IdentifierGenerator identifierGenerator) {
		this.identifierGenerator = identifierGenerator;
	}
	
	
	public Participant getUniqueParticipant(List<Identifier> identifiers) {
		List<Participant> participants = participantDao.getByIdentifiers(identifiers);
        if (participants.size() == 0) {
            throw this.exceptionHelper.getRuntimeException(getCode("C3PR.EXCEPTION.PARTICIPANT.NOT_FOUND_GIVEN_IDENTIFIERS.CODE"));
        }
        else if (participants.size() > 1) {
            throw this.exceptionHelper.getRuntimeException(getCode("C3PR.EXCEPTION.PARTICIPANT.MULTIPLE_PARTICIPANT_FOUND.CODE"));
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

	public Participant merge(Participant participant) {
		if(participant.getC3PRSystemSubjectIdentifier()==null){
			participant.addIdentifier(identifierGenerator.generateSystemAssignedIdentifier(participant));
		}
		participant=  participantDao.merge(participant);
		participantDao.initialize(participant);
		return participant;
	}

	public void save(Participant participant) {
		if(participant.getC3PRSystemSubjectIdentifier()==null){
			participant.addIdentifier(identifierGenerator.generateSystemAssignedIdentifier(participant));
		}
		participantDao.save(participant);
	}

	public List<Participant> searchByIdentifier(Identifier identifier) {
		return participantDao.searchByIdentifier(identifier, Participant.class);
	}


	/* (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.domain.repository.ParticipantRepository#searchByExample(edu.duke.cabig.c3pr.domain.Participant)
	 */
	public List<Participant> searchByExample(Participant participant) {
		return participantDao.searchByExample(participant);
	}

}
