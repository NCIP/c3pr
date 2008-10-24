package edu.duke.cabig.c3pr.domain.repository;

import java.util.Date;
import java.util.List;

import edu.duke.cabig.c3pr.domain.CoordinatingCenterStudyStatus;
import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;
import edu.duke.cabig.c3pr.exception.StudyValidationException;

public interface StudyRepository {

    public void buildAndSave(Study study) throws C3PRCodedException;

    public void validate(Study study) throws StudyValidationException, C3PRCodedException;

    public List<Study> searchByCoOrdinatingCenterId(OrganizationAssignedIdentifier identifier)
                    throws C3PRCodedException;

    public void save(Study study) throws C3PRCodedException;

    public Study merge(Study study);

    public void refresh(Study study);

    public void reassociate(Study study);

    public void clear();

    public List<Study> searchByExample(Study study, int maxResults);

    public int countAcrrualsByDate(Study study, Date startDate, Date endDate);

    public List<Study> searchByExample(Study study, boolean isWildCard, int maxResults,
                    String order, String orderBy);

    public Study createStudy(Study study);
    
    public Study createStudy(List<Identifier> studyIdentifiers);

    public Study openStudy(List<Identifier> studyIdentifiers);

    public StudySite approveStudySiteForActivation(List<Identifier> studyIdentifiers,
                    String nciInstituteCode);

    public StudySite activateStudySite(List<Identifier> studyIdentifiers, String nciInstituteCode);

    public Study amendStudy(List<Identifier> studyIdentifiers, Study amendedStudyDetails);

    public StudySite updateStudySiteProtocolVersion(List<Identifier> studyIdentifiers,
                    String nciInstituteCode, String version);

    public Study closeStudy(List<Identifier> studyIdentifiers);
    
    public Study closeStudyToAccrualAndTreatment(List<Identifier> studyIdentifiers);
    
    public Study temporarilyCloseStudy(List<Identifier> studyIdentifiers);
    
    public Study temporarilyCloseStudyToAccrualAndTreatment(List<Identifier> studyIdentifiers);
    
    public Study updateStudyStatus(List<Identifier> studyIdentifiers,
                    CoordinatingCenterStudyStatus status);

    public StudySite closeStudySite(List<Identifier> studyIdentifiers, String nciInstituteCode);
    
    public StudySite closeStudySiteToAccrualAndTreatment(List<Identifier> studyIdentifiers, String nciInstituteCode);
    
    public StudySite temporarilyCloseStudySite(List<Identifier> studyIdentifiers, String nciInstituteCode);
    
    public StudySite temporarilyCloseStudySiteToAccrualAndTreatment(List<Identifier> studyIdentifiers, String nciInstituteCode);
    

    public List<StudySite> closeStudySites(List<Identifier> studyIdentifiers);

    public Study getUniqueStudy(List<Identifier> studyIdentifiers);

}
