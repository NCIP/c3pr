package edu.duke.cabig.c3pr.web.participant;

import java.util.Map;

import edu.duke.cabig.c3pr.dao.ParticipantDao;
import edu.duke.cabig.c3pr.domain.Participant;

public class ParticipantSubmitTab extends ParticipantTab {
    private ParticipantDao participantDao;

    public ParticipantSubmitTab() {
        super("Review and Submit", "Review and Submit", "participant/participant_submit");
    }

    @Override
    public Map<String, Object> referenceData(Participant participant) {
        Map<String, Object> refdata = super.referenceData(participant);

        return refdata;
    }

    public ParticipantDao getParticipantDao() {
        return participantDao;
    }

    public void setParticipantDao(ParticipantDao participantDao) {
        this.participantDao = participantDao;
    }

}
