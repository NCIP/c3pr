package edu.duke.cabig.c3pr.service;

import java.util.Date;

import edu.duke.cabig.c3pr.domain.CoordinatingCenterStudyStatus;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.SiteStudyStatus;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyDataEntryStatus;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.StudySubject;

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
    
    public StudyDataEntryStatus evaluateDataEntryStatus(Study study) throws Exception;
    
    public CoordinatingCenterStudyStatus evaluateCoordinatingCenterStudyStatus(Study study) throws Exception;
    
    public SiteStudyStatus evaluateSiteStudyStatus(StudySite studySite) throws Exception;
    
    
    public Study merge(Study study);


    /**
     * @param study
     * @param participant
     * @param site
     * @return StudySubject
     */
    public StudySubject assignParticipant(Study study, Participant participant,
                                                        HealthcareSite site, Date enrollmentDate);


}
