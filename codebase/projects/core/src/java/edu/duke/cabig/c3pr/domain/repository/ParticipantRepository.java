package edu.duke.cabig.c3pr.domain.repository;

import java.util.List;

import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.Participant;

public interface ParticipantRepository {
    public Participant getUniqueParticipant(List<Identifier> identifiers);
}
