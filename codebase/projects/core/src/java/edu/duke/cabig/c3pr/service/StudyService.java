package edu.duke.cabig.c3pr.service;

import edu.duke.cabig.c3pr.domain.*;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;

import java.util.Date;
import java.util.List;

/**
 * Interface for Services on Study related domain object
 *
 * @author priyatam
 */
public interface StudyService extends CCTSWorkflowService {

    /**
     * Saves a study object
     *
     * @param study the study object
     * @throws Exception runtime exception object
     */
    public void save(Study study) throws C3PRCodedException;

    public StudyDataEntryStatus evaluateDataEntryStatus(Study study) throws C3PRCodedException;

    public CoordinatingCenterStudyStatus evaluateCoordinatingCenterStudyStatus(Study study) throws C3PRCodedException;

    public SiteStudyStatus evaluateSiteStudyStatus(StudySite studySite) throws C3PRCodedException;

    public void setSiteStudyStatuses(Study study) throws C3PRCodedException;

    public void setDataEntryStatus(Study study, boolean throwException) throws C3PRCodedException;

    public Study setStatuses(Study study, boolean throwException) throws C3PRCodedException;

    public Study setStatuses(Study study, CoordinatingCenterStudyStatus status) throws C3PRCodedException;

    public Study setSiteStudyStatus(Study study, StudySite studySite, SiteStudyStatus status) throws C3PRCodedException;

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
