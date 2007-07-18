package edu.duke.cabig.c3pr.web.participant;

import java.util.Map;

import edu.duke.cabig.c3pr.dao.ParticipantDao;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.Study;

public class ParticipantSummaryTab extends ParticipantTab{
	 private ParticipantDao participantDao;
	
	public ParticipantSummaryTab() {
		super("Summary&Registrations", "Summary & Registrations", "participant/participant_summary_view");
	}
	
	@Override
    public Map<String, Object> referenceData(Participant participant) {
        Map<String, Object> refdata = super.referenceData(participant);
        refdata.put("participantAssignments", this.getParticipantDao().getById(participant.getId()).getStudySubjects());

        return refdata;
    }

	public ParticipantDao getParticipantDao() {
		return participantDao;
	}

	public void setParticipantDao(ParticipantDao participantDao) {
		this.participantDao = participantDao;
	}

}
