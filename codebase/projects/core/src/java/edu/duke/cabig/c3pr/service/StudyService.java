package edu.duke.cabig.c3pr.service;

import edu.duke.cabig.c3pr.domain.*;
import edu.duke.cabig.c3pr.exception.C3PRBaseException;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;

import java.util.Date;
import java.util.List;

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

    public void broadcastStudyMessage(Study study) throws C3PRBaseException;

    public CCTSWorkflowStatusType getCCTSWofkflowStatus(Study study) throws Exception;

    public StudyDataEntryStatus evaluateDataEntryStatus(Study study) throws Exception;

    public CoordinatingCenterStudyStatus evaluateCoordinatingCenterStudyStatus(Study study) throws Exception;

    public SiteStudyStatus evaluateSiteStudyStatus(StudySite studySite) throws Exception;

    public void setSiteStudyStatuses(Study study) throws Exception;

    public void setDataEntryStatus(Study study, boolean throwException) throws Exception;

    public Study setStatuses(Study study, boolean throwException) throws Exception;

    public Study setStatuses(Study study, CoordinatingCenterStudyStatus status) throws Exception;

    public Study setSiteStudyStatus(Study study, StudySite studySite, SiteStudyStatus status) throws Exception;

    public Study merge(Study study);

    public Study refresh(Study study);

    public Study reassociate(Study study);

    public List<Study> searchByCoOrdinatingCenterId(OrganizationAssignedIdentifier identifier) throws C3PRCodedException;

    /**
     * @param study
     * @param participant
     * @param site
     * @return StudySubject
     */
    public StudySubject assignParticipant(Study study, Participant participant,
                                          HealthcareSite site, Date enrollmentDate);


}
