package edu.duke.cabig.c3pr.service;

import java.util.Date;

import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyParticipantAssignment;

/**
 * Interface for Services on Study related domain object
 *
 * @author priyatam
 */
public interface StudyService {

    /**
     * Saves a study object
     *
     * @param study the study object
     * @throws Exception runtime exception object
     */
    public void save(Study study) throws Exception;


    /**
     * @param study
     * @param participant
     * @param site
     * @return StudyParticipantAssignment
     */
    public StudyParticipantAssignment assignParticipant(Study study, Participant participant,
                                                        HealthcareSite site, Date enrollmentDate);


}
