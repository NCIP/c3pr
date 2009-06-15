package edu.duke.cabig.c3pr.domain.repository;

import java.util.Date;
import java.util.List;

import edu.duke.cabig.c3pr.constants.APIName;
import edu.duke.cabig.c3pr.constants.CoordinatingCenterStudyStatus;
import edu.duke.cabig.c3pr.domain.EndPoint;
import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;
import edu.duke.cabig.c3pr.exception.StudyValidationException;
import gov.nih.nci.cabig.ctms.domain.AbstractMutableDomainObject;

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

    public int countAcrrualsByDate(Study study, Date startDate, Date endDate);

    public List<Study> searchByExample(Study study, boolean isWildCard, int maxResults,
                    String order, String orderBy);

    public Study createStudy(Study study);
    
    public Study createStudy(List<Identifier> studyIdentifiers);
    
    public Study createAndOpenStudy(Study study);

    public Study openStudy(List<Identifier> studyIdentifiers);

//    public StudySite approveStudySiteForActivation(List<Identifier> studyIdentifiers,
//                    String nciInstituteCode);

    public StudySite activateStudySite(List<Identifier> studyIdentifiers, String nciInstituteCode);
    
//    public StudySite approveStudySiteForActivation(List<Identifier> studyIdentifiers,
//            StudySite studySite);

    public StudySite activateStudySite(List<Identifier> studyIdentifiers, StudySite studySite);

    public Study amendStudy(List<Identifier> studyIdentifiers, Study amendedStudyDetails);

    public StudySite updateStudySiteProtocolVersion(List<Identifier> studyIdentifiers,
                    String nciInstituteCode, String version);

    public Study closeStudyToAccrual(List<Identifier> studyIdentifiers);
    
    public Study closeStudyToAccrualAndTreatment(List<Identifier> studyIdentifiers);
    
    public Study temporarilyCloseStudy(List<Identifier> studyIdentifiers);
    
    public Study temporarilyCloseStudyToAccrualAndTreatment(List<Identifier> studyIdentifiers);
    
    public Study updateStudyStatus(List<Identifier> studyIdentifiers,
                    CoordinatingCenterStudyStatus status);

    public StudySite closeStudySiteToAccrual(List<Identifier> studyIdentifiers, String nciInstituteCode);
    
    public StudySite closeStudySiteToAccrualAndTreatment(List<Identifier> studyIdentifiers, String nciInstituteCode);
    
    public StudySite temporarilyCloseStudySiteToAccrual(List<Identifier> studyIdentifiers, String nciInstituteCode);
    
    public StudySite temporarilyCloseStudySiteToAccrualAndTreatment(List<Identifier> studyIdentifiers, String nciInstituteCode);
    
    public List<StudySite> closeStudySites(List<Identifier> studyIdentifiers);
    
    public void createStudyAtAffiliates(List<Identifier> studyIdentifiers);
    
    public EndPoint createStudyAtAffiliate(List<Identifier> studyIdentifiers, String nciInstituteCode);
    
    public void createAndOpenStudyAtAffiliates(List<Identifier> studyIdentifiers);
    
    public EndPoint createAndOpenStudyAtAffiliate(List<Identifier> studyIdentifiers, String nciInstituteCode);

    public void openStudyAtAffiliates(List<Identifier> studyIdentifiers);

    public EndPoint openStudyAtAffiliate(List<Identifier> studyIdentifiers, String nciInstituteCode);
    
    public void amendStudyAtAffiliates(List<Identifier> studyIdentifiers, Study amendedStudyDetails);

    public void updateAffliateProtocolVersion(List<Identifier> studyIdentifiers,
                    String nciInstituteCode, String version);

    public void closeStudyToAccrualAtAffiliates(List<Identifier> studyIdentifiers);
    
    public EndPoint closeStudyToAccrualAtAffiliate(List<Identifier> studyIdentifiers, String nciInstituteCode);
    
    public void closeStudyToAccrualAndTreatmentAtAffiliates(List<Identifier> studyIdentifiers);
    
    public EndPoint closeStudyToAccrualAndTreatmentAtAffiliate(List<Identifier> studyIdentifiers, String nciInstituteCode);
    
    public void temporarilyCloseStudyToAccrualAtAffiliates(List<Identifier> studyIdentifiers);
    
    public EndPoint temporarilyCloseStudyToAccrualAtAffiliate(List<Identifier> studyIdentifiers, String nciInstituteCode);
    
    public void temporarilyCloseStudyToAccrualAndTreatmentAtAffiliates(List<Identifier> studyIdentifiers);
    
    public EndPoint temporarilyCloseStudyToAccrualAndTreatmentAtAffiliate(List<Identifier> studyIdentifiers, String nciInstituteCode);
    
    public void updateStudyStatusAtAffiliates(List<Identifier> studyIdentifiers,
                    CoordinatingCenterStudyStatus status);
    
    public EndPoint handleAffiliateSiteBroadcast(String nciInstituteCode, Study study, APIName multisiteAPIName, List<? extends AbstractMutableDomainObject> domainObjects);
    
    public void handleAffiliateSitesBroadcast(Study study, APIName multisiteAPIName, List<? extends AbstractMutableDomainObject> domainObjects);
    
    public EndPoint handleCoordinatingCenterBroadcast(Study study, APIName multisiteAPIName, List<? extends AbstractMutableDomainObject> domainObjects);

    public Study getUniqueStudy(List<Identifier> studyIdentifiers);

}
