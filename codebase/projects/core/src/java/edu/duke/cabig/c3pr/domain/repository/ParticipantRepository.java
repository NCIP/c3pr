package edu.duke.cabig.c3pr.domain.repository;

import java.util.List;

import edu.duke.cabig.c3pr.domain.Address;
import edu.duke.cabig.c3pr.domain.ContactMechanism;
import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.Participant;

public interface ParticipantRepository {
    public Participant getUniqueParticipant(List<Identifier> identifiers);
    
    public void save(Participant participant);
    
    public Participant merge(Participant participant);
    
    public List<Participant> searchByIdentifier(Identifier identifier);
    
    /**
     * This search does not include all associated entities of the {@link Participant}, such as {@link Address} or {@link ContactMechanism}.
     * Identifiers are included, if present.
     * @see #searchByFullExample(Participant)
     * @param participant
     * @return
     */
    public List<Participant> searchByExample(Participant participant);
    
    /**
     * This search takes {@link Address} and {@link ContactMechanism} into account, if set.
     * @param participant
     * @return
     */
    public List<Participant> searchByFullExample(Participant participant);
    
}
