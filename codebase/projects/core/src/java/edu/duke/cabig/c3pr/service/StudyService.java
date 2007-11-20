package edu.duke.cabig.c3pr.service;

import java.util.Date;
import java.util.List;

import edu.duke.cabig.c3pr.domain.CoordinatingCenterStudyStatus;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.SiteStudyStatus;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyDataEntryStatus;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.exception.C3PRBaseException;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;

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
    public void save(Study study) throws C3PRCodedException;
    
    public StudyDataEntryStatus evaluateDataEntryStatus(Study study) throws Exception;
    
    public CoordinatingCenterStudyStatus evaluateCoordinatingCenterStudyStatus(Study study) throws Exception;
    
    public SiteStudyStatus evaluateSiteStudyStatus(StudySite studySite) throws Exception;
    
    public void setSiteStudyStatuses(Study study) throws Exception;
    
    public void setDataEntryStatus(Study study, boolean throwException) throws Exception;
    
    public Study setStatuses(Study study, boolean throwException) throws Exception;
    
    public Study setStatuses(Study study,CoordinatingCenterStudyStatus status) throws Exception;
    
    public Study setSiteStudyStatus(Study study,StudySite studySite, SiteStudyStatus status) throws Exception;
    
    public Study merge(Study study);
    
    public Study refresh(Study study);
    
    public Study reassociate(Study study);

    public List<Study> searchByCoOrdinatingCenterId(OrganizationAssignedIdentifier identifier)throws C3PRCodedException;

    /**
     * @param study
     * @param participant
     * @param site
     * @return StudySubject
     */
    public StudySubject assignParticipant(Study study, Participant participant,
                                                        HealthcareSite site, Date enrollmentDate);


}
