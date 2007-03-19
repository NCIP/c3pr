/package edu.duke.cabig.c3pr.api;

import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyParticipantAssignment;

/**
 * @author Priyatam
 *
 */
public interface StudyService {

    /**
     * Assigns a Participant to a Study at a particular Site.
     * The Study and Site must already exist and be associated.
     * 
     * @param study
     * @param participant
     * @param site
     * @return StudyParticipantAssignment for the Participant
     */
    StudyParticipantAssignment assignParticipant(Study study, Participant participant, HealthcareSite site, 
    	String registrationGridId);

}
