package edu.duke.cabig.c3pr.web.participant;

import edu.duke.cabig.c3pr.domain.Participant;

public class ParticipantWrapper {

	private Participant participant;

	public ParticipantWrapper() {
	}

	public ParticipantWrapper(Participant participant) {
		super();
		this.participant = participant;
	}

	public Participant getParticipant() {
		return participant;
	}

	public void setParticipant(Participant participant) {
		this.participant = participant;
	}
}
