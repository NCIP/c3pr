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
        refdata.put("participantAssignments", this.getParticipantDao().getById(participant.getId())
                        .getStudySubjects());
        participantDao.initializeStudySubjects(participant);
        return refdata;
    }

    public ParticipantDao getParticipantDao() {
        return participantDao;
    }

    public void setParticipantDao(ParticipantDao participantDao) {
        this.participantDao = participantDao;
    }

}
