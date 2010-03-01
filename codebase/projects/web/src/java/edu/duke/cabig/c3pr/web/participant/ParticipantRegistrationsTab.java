package edu.duke.cabig.c3pr.web.participant;

import java.util.Map;

import edu.duke.cabig.c3pr.dao.ParticipantDao;
import edu.duke.cabig.c3pr.domain.Participant;

public class ParticipantRegistrationsTab extends ParticipantTab {
    private ParticipantDao participantDao;

    public ParticipantRegistrationsTab() {
        super("Registrations", "Registrations", "participant/participant_registrations");
    }

    @Override
    public Map<String, Object> referenceData(Participant participant) {
        Map<String, Object> refdata = super.referenceData(participant);
        participant = participantDao.getById(participant.getId());
        participantDao.initializeStudySubjects(participant);
        refdata.put("participantAssignments",participant.getStudySubjects());
        return refdata;
    }

    public void setParticipantDao(ParticipantDao participantDao) {
        this.participantDao = participantDao;
    }

}
