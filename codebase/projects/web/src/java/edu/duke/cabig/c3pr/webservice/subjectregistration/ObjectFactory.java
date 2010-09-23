
package edu.duke.cabig.c3pr.webservice.subjectregistration;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the edu.duke.cabig.c3pr.webservice.subjectregistration package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _InsufficientPrivilegesExceptionFault_QNAME = new QName("http://enterpriseservices.nci.nih.gov/SubjectRegistrationService", "InsufficientPrivilegesExceptionFault");
    private final static QName _SubjectAlreadyRegisteredExceptionFault_QNAME = new QName("http://enterpriseservices.nci.nih.gov/SubjectRegistrationService", "SubjectAlreadyRegisteredExceptionFault");
    private final static QName _InvalidQueryExceptionFault_QNAME = new QName("http://enterpriseservices.nci.nih.gov/SubjectRegistrationService", "InvalidQueryExceptionFault");
    private final static QName _NoSuchStudySubjectExceptionFault_QNAME = new QName("http://enterpriseservices.nci.nih.gov/SubjectRegistrationService", "NoSuchStudySubjectExceptionFault");
    private final static QName _SubjectRegistrationRejectedExceptionFault_QNAME = new QName("http://enterpriseservices.nci.nih.gov/SubjectRegistrationService", "SubjectRegistrationRejectedExceptionFault");
    private final static QName _InvalidSiteExceptionFault_QNAME = new QName("http://enterpriseservices.nci.nih.gov/SubjectRegistrationService", "InvalidSiteExceptionFault");
    private final static QName _InvalidStudySubjectDataExceptionFault_QNAME = new QName("http://enterpriseservices.nci.nih.gov/SubjectRegistrationService", "InvalidStudySubjectDataExceptionFault");
    private final static QName _SubjectRegistrationFault_QNAME = new QName("http://enterpriseservices.nci.nih.gov/SubjectRegistrationService", "SubjectRegistrationFault");
    private final static QName _InvalidStudyProtocolExceptionFault_QNAME = new QName("http://enterpriseservices.nci.nih.gov/SubjectRegistrationService", "InvalidStudyProtocolExceptionFault");
    private final static QName _NoSuchPatientExceptionFault_QNAME = new QName("http://enterpriseservices.nci.nih.gov/SubjectRegistrationService", "NoSuchPatientExceptionFault");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: edu.duke.cabig.c3pr.webservice.subjectregistration
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Document }
     * 
     */
    public Document createDocument() {
        return new Document();
    }

    /**
     * Create an instance of {@link AdvanceSearchCriterionParameter }
     * 
     */
    public AdvanceSearchCriterionParameter createAdvanceSearchCriterionParameter() {
        return new AdvanceSearchCriterionParameter();
    }

    /**
     * Create an instance of {@link OrganizationIdentifier }
     * 
     */
    public OrganizationIdentifier createOrganizationIdentifier() {
        return new OrganizationIdentifier();
    }

    /**
     * Create an instance of {@link StudySite }
     * 
     */
    public StudySite createStudySite() {
        return new StudySite();
    }

    /**
     * Create an instance of {@link DSETPerformedObservationResult }
     * 
     */
    public DSETPerformedObservationResult createDSETPerformedObservationResult() {
        return new DSETPerformedObservationResult();
    }

    /**
     * Create an instance of {@link TakeSubjectOffStudyResponse }
     * 
     */
    public TakeSubjectOffStudyResponse createTakeSubjectOffStudyResponse() {
        return new TakeSubjectOffStudyResponse();
    }

    /**
     * Create an instance of {@link DefinedStratificationCriterionPermissibleResult }
     * 
     */
    public DefinedStratificationCriterionPermissibleResult createDefinedStratificationCriterionPermissibleResult() {
        return new DefinedStratificationCriterionPermissibleResult();
    }

    /**
     * Create an instance of {@link Person }
     * 
     */
    public Person createPerson() {
        return new Person();
    }

    /**
     * Create an instance of {@link InvalidStudySubjectDataExceptionFault }
     * 
     */
    public InvalidStudySubjectDataExceptionFault createInvalidStudySubjectDataExceptionFault() {
        return new InvalidStudySubjectDataExceptionFault();
    }

    /**
     * Create an instance of {@link InsufficientPrivilegesExceptionFault }
     * 
     */
    public InsufficientPrivilegesExceptionFault createInsufficientPrivilegesExceptionFault() {
        return new InsufficientPrivilegesExceptionFault();
    }

    /**
     * Create an instance of {@link InitiateSubjectEnrollmentRequest }
     * 
     */
    public InitiateSubjectEnrollmentRequest createInitiateSubjectEnrollmentRequest() {
        return new InitiateSubjectEnrollmentRequest();
    }

    /**
     * Create an instance of {@link PerformedAdministrativeActivity }
     * 
     */
    public PerformedAdministrativeActivity createPerformedAdministrativeActivity() {
        return new PerformedAdministrativeActivity();
    }

    /**
     * Create an instance of {@link UpdateRegistrationRequest }
     * 
     */
    public UpdateRegistrationRequest createUpdateRegistrationRequest() {
        return new UpdateRegistrationRequest();
    }

    /**
     * Create an instance of {@link ReconsentStudySubjectRequest }
     * 
     */
    public ReconsentStudySubjectRequest createReconsentStudySubjectRequest() {
        return new ReconsentStudySubjectRequest();
    }

    /**
     * Create an instance of {@link Subject }
     * 
     */
    public Subject createSubject() {
        return new Subject();
    }

    /**
     * Create an instance of {@link SubjectConsentQuestionAnswer }
     * 
     */
    public SubjectConsentQuestionAnswer createSubjectConsentQuestionAnswer() {
        return new SubjectConsentQuestionAnswer();
    }

    /**
     * Create an instance of {@link QuerySubjectRegistrationRequest }
     * 
     */
    public QuerySubjectRegistrationRequest createQuerySubjectRegistrationRequest() {
        return new QuerySubjectRegistrationRequest();
    }

    /**
     * Create an instance of {@link SubjectRegistrationRejectedExceptionFault }
     * 
     */
    public SubjectRegistrationRejectedExceptionFault createSubjectRegistrationRejectedExceptionFault() {
        return new SubjectRegistrationRejectedExceptionFault();
    }

    /**
     * Create an instance of {@link DefinedSubjectActivityGroup }
     * 
     */
    public DefinedSubjectActivityGroup createDefinedSubjectActivityGroup() {
        return new DefinedSubjectActivityGroup();
    }

    /**
     * Create an instance of {@link PerformedActivity }
     * 
     */
    public PerformedActivity createPerformedActivity() {
        return new PerformedActivity();
    }

    /**
     * Create an instance of {@link GenerateSummary3ReportResponse }
     * 
     */
    public GenerateSummary3ReportResponse createGenerateSummary3ReportResponse() {
        return new GenerateSummary3ReportResponse();
    }

    /**
     * Create an instance of {@link TakeSubjectOffStudyRequest }
     * 
     */
    public TakeSubjectOffStudyRequest createTakeSubjectOffStudyRequest() {
        return new TakeSubjectOffStudyRequest();
    }

    /**
     * Create an instance of {@link FailSubjectScreeningRequest }
     * 
     */
    public FailSubjectScreeningRequest createFailSubjectScreeningRequest() {
        return new FailSubjectScreeningRequest();
    }

    /**
     * Create an instance of {@link GenerateSummary3ReportRequest }
     * 
     */
    public GenerateSummary3ReportRequest createGenerateSummary3ReportRequest() {
        return new GenerateSummary3ReportRequest();
    }

    /**
     * Create an instance of {@link RetrieveSubjectDemographyHistoryResponse }
     * 
     */
    public RetrieveSubjectDemographyHistoryResponse createRetrieveSubjectDemographyHistoryResponse() {
        return new RetrieveSubjectDemographyHistoryResponse();
    }

    /**
     * Create an instance of {@link Organization }
     * 
     */
    public Organization createOrganization() {
        return new Organization();
    }

    /**
     * Create an instance of {@link SubjectIdentifier }
     * 
     */
    public SubjectIdentifier createSubjectIdentifier() {
        return new SubjectIdentifier();
    }

    /**
     * Create an instance of {@link DefinedExcusionCriterion }
     * 
     */
    public DefinedExcusionCriterion createDefinedExcusionCriterion() {
        return new DefinedExcusionCriterion();
    }

    /**
     * Create an instance of {@link HealthcareProvider }
     * 
     */
    public HealthcareProvider createHealthcareProvider() {
        return new HealthcareProvider();
    }

    /**
     * Create an instance of {@link DSETAdvanceSearchCriterionParameter }
     * 
     */
    public DSETAdvanceSearchCriterionParameter createDSETAdvanceSearchCriterionParameter() {
        return new DSETAdvanceSearchCriterionParameter();
    }

    /**
     * Create an instance of {@link RetrieveAccrualDataRequest }
     * 
     */
    public RetrieveAccrualDataRequest createRetrieveAccrualDataRequest() {
        return new RetrieveAccrualDataRequest();
    }

    /**
     * Create an instance of {@link PerformedObservationResult }
     * 
     */
    public PerformedObservationResult createPerformedObservationResult() {
        return new PerformedObservationResult();
    }

    /**
     * Create an instance of {@link ResearchStaff }
     * 
     */
    public ResearchStaff createResearchStaff() {
        return new ResearchStaff();
    }

    /**
     * Create an instance of {@link DSETPerson }
     * 
     */
    public DSETPerson createDSETPerson() {
        return new DSETPerson();
    }

    /**
     * Create an instance of {@link SubjectRegistrationFault }
     * 
     */
    public SubjectRegistrationFault createSubjectRegistrationFault() {
        return new SubjectRegistrationFault();
    }

    /**
     * Create an instance of {@link QuerySubjectRegistrationResponse }
     * 
     */
    public QuerySubjectRegistrationResponse createQuerySubjectRegistrationResponse() {
        return new QuerySubjectRegistrationResponse();
    }

    /**
     * Create an instance of {@link StudySiteProtocolVersionRelationship }
     * 
     */
    public StudySiteProtocolVersionRelationship createStudySiteProtocolVersionRelationship() {
        return new StudySiteProtocolVersionRelationship();
    }

    /**
     * Create an instance of {@link RetrieveAccrualDataResponse }
     * 
     */
    public RetrieveAccrualDataResponse createRetrieveAccrualDataResponse() {
        return new RetrieveAccrualDataResponse();
    }

    /**
     * Create an instance of {@link StudyProtocolVersion }
     * 
     */
    public StudyProtocolVersion createStudyProtocolVersion() {
        return new StudyProtocolVersion();
    }

    /**
     * Create an instance of {@link FailSubjectScreeningResponse }
     * 
     */
    public FailSubjectScreeningResponse createFailSubjectScreeningResponse() {
        return new FailSubjectScreeningResponse();
    }

    /**
     * Create an instance of {@link RetrieveSubjectDemographyHistoryRequest }
     * 
     */
    public RetrieveSubjectDemographyHistoryRequest createRetrieveSubjectDemographyHistoryRequest() {
        return new RetrieveSubjectDemographyHistoryRequest();
    }

    /**
     * Create an instance of {@link BiologicEntityIdentifier }
     * 
     */
    public BiologicEntityIdentifier createBiologicEntityIdentifier() {
        return new BiologicEntityIdentifier();
    }

    /**
     * Create an instance of {@link SubjectAlreadyRegisteredExceptionFault }
     * 
     */
    public SubjectAlreadyRegisteredExceptionFault createSubjectAlreadyRegisteredExceptionFault() {
        return new SubjectAlreadyRegisteredExceptionFault();
    }

    /**
     * Create an instance of {@link InvalidSiteExceptionFault }
     * 
     */
    public InvalidSiteExceptionFault createInvalidSiteExceptionFault() {
        return new InvalidSiteExceptionFault();
    }

    /**
     * Create an instance of {@link StudyInvestigator }
     * 
     */
    public StudyInvestigator createStudyInvestigator() {
        return new StudyInvestigator();
    }

    /**
     * Create an instance of {@link ReconsentStudySubjectResponse }
     * 
     */
    public ReconsentStudySubjectResponse createReconsentStudySubjectResponse() {
        return new ReconsentStudySubjectResponse();
    }

    /**
     * Create an instance of {@link ConsentQuestion }
     * 
     */
    public ConsentQuestion createConsentQuestion() {
        return new ConsentQuestion();
    }

    /**
     * Create an instance of {@link DiscontinueEnrollmentResponse }
     * 
     */
    public DiscontinueEnrollmentResponse createDiscontinueEnrollmentResponse() {
        return new DiscontinueEnrollmentResponse();
    }

    /**
     * Create an instance of {@link DefinedObservation }
     * 
     */
    public DefinedObservation createDefinedObservation() {
        return new DefinedObservation();
    }

    /**
     * Create an instance of {@link DefinedStratificationCriterion }
     * 
     */
    public DefinedStratificationCriterion createDefinedStratificationCriterion() {
        return new DefinedStratificationCriterion();
    }

    /**
     * Create an instance of {@link InvalidStudyProtocolExceptionFault }
     * 
     */
    public InvalidStudyProtocolExceptionFault createInvalidStudyProtocolExceptionFault() {
        return new InvalidStudyProtocolExceptionFault();
    }

    /**
     * Create an instance of {@link DSETStudySubject }
     * 
     */
    public DSETStudySubject createDSETStudySubject() {
        return new DSETStudySubject();
    }

    /**
     * Create an instance of {@link StudyProtocolDocumentVersion }
     * 
     */
    public StudyProtocolDocumentVersion createStudyProtocolDocumentVersion() {
        return new StudyProtocolDocumentVersion();
    }

    /**
     * Create an instance of {@link Epoch }
     * 
     */
    public Epoch createEpoch() {
        return new Epoch();
    }

    /**
     * Create an instance of {@link Summary3Report }
     * 
     */
    public Summary3Report createSummary3Report() {
        return new Summary3Report();
    }

    /**
     * Create an instance of {@link ImportRegistrationsRequest }
     * 
     */
    public ImportRegistrationsRequest createImportRegistrationsRequest() {
        return new ImportRegistrationsRequest();
    }

    /**
     * Create an instance of {@link Activity }
     * 
     */
    public Activity createActivity() {
        return new Activity();
    }

    /**
     * Create an instance of {@link DocumentVersion }
     * 
     */
    public DocumentVersion createDocumentVersion() {
        return new DocumentVersion();
    }

    /**
     * Create an instance of {@link StudySubjectProtocolVersionRelationship }
     * 
     */
    public StudySubjectProtocolVersionRelationship createStudySubjectProtocolVersionRelationship() {
        return new StudySubjectProtocolVersionRelationship();
    }

    /**
     * Create an instance of {@link Consent }
     * 
     */
    public Consent createConsent() {
        return new Consent();
    }

    /**
     * Create an instance of {@link PerformedDiagnosis }
     * 
     */
    public PerformedDiagnosis createPerformedDiagnosis() {
        return new PerformedDiagnosis();
    }

    /**
     * Create an instance of {@link ScheduledEpoch }
     * 
     */
    public ScheduledEpoch createScheduledEpoch() {
        return new ScheduledEpoch();
    }

    /**
     * Create an instance of {@link DSETStudySubjectConsentVersion }
     * 
     */
    public DSETStudySubjectConsentVersion createDSETStudySubjectConsentVersion() {
        return new DSETStudySubjectConsentVersion();
    }

    /**
     * Create an instance of {@link ImportRegistrationsResponse }
     * 
     */
    public ImportRegistrationsResponse createImportRegistrationsResponse() {
        return new ImportRegistrationsResponse();
    }

    /**
     * Create an instance of {@link UpdateRegistrationResponse }
     * 
     */
    public UpdateRegistrationResponse createUpdateRegistrationResponse() {
        return new UpdateRegistrationResponse();
    }

    /**
     * Create an instance of {@link StudySubject }
     * 
     */
    public StudySubject createStudySubject() {
        return new StudySubject();
    }

    /**
     * Create an instance of {@link ChangeStudySubjectEpochAssignmentRequest }
     * 
     */
    public ChangeStudySubjectEpochAssignmentRequest createChangeStudySubjectEpochAssignmentRequest() {
        return new ChangeStudySubjectEpochAssignmentRequest();
    }

    /**
     * Create an instance of {@link DefinedEligibilityCriterion }
     * 
     */
    public DefinedEligibilityCriterion createDefinedEligibilityCriterion() {
        return new DefinedEligibilityCriterion();
    }

    /**
     * Create an instance of {@link Drug }
     * 
     */
    public Drug createDrug() {
        return new Drug();
    }

    /**
     * Create an instance of {@link ChangeStudySubjectEpochAssignmentResponse }
     * 
     */
    public ChangeStudySubjectEpochAssignmentResponse createChangeStudySubjectEpochAssignmentResponse() {
        return new ChangeStudySubjectEpochAssignmentResponse();
    }

    /**
     * Create an instance of {@link InitiateSubjectEnrollmentResponse }
     * 
     */
    public InitiateSubjectEnrollmentResponse createInitiateSubjectEnrollmentResponse() {
        return new InitiateSubjectEnrollmentResponse();
    }

    /**
     * Create an instance of {@link DefinedActivity }
     * 
     */
    public DefinedActivity createDefinedActivity() {
        return new DefinedActivity();
    }

    /**
     * Create an instance of {@link InvalidQueryExceptionFault }
     * 
     */
    public InvalidQueryExceptionFault createInvalidQueryExceptionFault() {
        return new InvalidQueryExceptionFault();
    }

    /**
     * Create an instance of {@link StudySubjectConsentVersion }
     * 
     */
    public StudySubjectConsentVersion createStudySubjectConsentVersion() {
        return new StudySubjectConsentVersion();
    }

    /**
     * Create an instance of {@link NoSuchPatientExceptionFault }
     * 
     */
    public NoSuchPatientExceptionFault createNoSuchPatientExceptionFault() {
        return new NoSuchPatientExceptionFault();
    }

    /**
     * Create an instance of {@link NoSuchStudySubjectExceptionFault }
     * 
     */
    public NoSuchStudySubjectExceptionFault createNoSuchStudySubjectExceptionFault() {
        return new NoSuchStudySubjectExceptionFault();
    }

    /**
     * Create an instance of {@link StudyCondition }
     * 
     */
    public StudyCondition createStudyCondition() {
        return new StudyCondition();
    }

    /**
     * Create an instance of {@link DiscontinueEnrollmentRequest }
     * 
     */
    public DiscontinueEnrollmentRequest createDiscontinueEnrollmentRequest() {
        return new DiscontinueEnrollmentRequest();
    }

    /**
     * Create an instance of {@link DefinedIncusionCriterion }
     * 
     */
    public DefinedIncusionCriterion createDefinedIncusionCriterion() {
        return new DefinedIncusionCriterion();
    }

    /**
     * Create an instance of {@link DefinedObservationResult }
     * 
     */
    public DefinedObservationResult createDefinedObservationResult() {
        return new DefinedObservationResult();
    }

    /**
     * Create an instance of {@link PerformedStudySubjectMilestone }
     * 
     */
    public PerformedStudySubjectMilestone createPerformedStudySubjectMilestone() {
        return new PerformedStudySubjectMilestone();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InsufficientPrivilegesExceptionFault }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://enterpriseservices.nci.nih.gov/SubjectRegistrationService", name = "InsufficientPrivilegesExceptionFault")
    public JAXBElement<InsufficientPrivilegesExceptionFault> createInsufficientPrivilegesExceptionFault(InsufficientPrivilegesExceptionFault value) {
        return new JAXBElement<InsufficientPrivilegesExceptionFault>(_InsufficientPrivilegesExceptionFault_QNAME, InsufficientPrivilegesExceptionFault.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SubjectAlreadyRegisteredExceptionFault }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://enterpriseservices.nci.nih.gov/SubjectRegistrationService", name = "SubjectAlreadyRegisteredExceptionFault")
    public JAXBElement<SubjectAlreadyRegisteredExceptionFault> createSubjectAlreadyRegisteredExceptionFault(SubjectAlreadyRegisteredExceptionFault value) {
        return new JAXBElement<SubjectAlreadyRegisteredExceptionFault>(_SubjectAlreadyRegisteredExceptionFault_QNAME, SubjectAlreadyRegisteredExceptionFault.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InvalidQueryExceptionFault }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://enterpriseservices.nci.nih.gov/SubjectRegistrationService", name = "InvalidQueryExceptionFault")
    public JAXBElement<InvalidQueryExceptionFault> createInvalidQueryExceptionFault(InvalidQueryExceptionFault value) {
        return new JAXBElement<InvalidQueryExceptionFault>(_InvalidQueryExceptionFault_QNAME, InvalidQueryExceptionFault.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NoSuchStudySubjectExceptionFault }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://enterpriseservices.nci.nih.gov/SubjectRegistrationService", name = "NoSuchStudySubjectExceptionFault")
    public JAXBElement<NoSuchStudySubjectExceptionFault> createNoSuchStudySubjectExceptionFault(NoSuchStudySubjectExceptionFault value) {
        return new JAXBElement<NoSuchStudySubjectExceptionFault>(_NoSuchStudySubjectExceptionFault_QNAME, NoSuchStudySubjectExceptionFault.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SubjectRegistrationRejectedExceptionFault }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://enterpriseservices.nci.nih.gov/SubjectRegistrationService", name = "SubjectRegistrationRejectedExceptionFault")
    public JAXBElement<SubjectRegistrationRejectedExceptionFault> createSubjectRegistrationRejectedExceptionFault(SubjectRegistrationRejectedExceptionFault value) {
        return new JAXBElement<SubjectRegistrationRejectedExceptionFault>(_SubjectRegistrationRejectedExceptionFault_QNAME, SubjectRegistrationRejectedExceptionFault.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InvalidSiteExceptionFault }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://enterpriseservices.nci.nih.gov/SubjectRegistrationService", name = "InvalidSiteExceptionFault")
    public JAXBElement<InvalidSiteExceptionFault> createInvalidSiteExceptionFault(InvalidSiteExceptionFault value) {
        return new JAXBElement<InvalidSiteExceptionFault>(_InvalidSiteExceptionFault_QNAME, InvalidSiteExceptionFault.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InvalidStudySubjectDataExceptionFault }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://enterpriseservices.nci.nih.gov/SubjectRegistrationService", name = "InvalidStudySubjectDataExceptionFault")
    public JAXBElement<InvalidStudySubjectDataExceptionFault> createInvalidStudySubjectDataExceptionFault(InvalidStudySubjectDataExceptionFault value) {
        return new JAXBElement<InvalidStudySubjectDataExceptionFault>(_InvalidStudySubjectDataExceptionFault_QNAME, InvalidStudySubjectDataExceptionFault.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SubjectRegistrationFault }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://enterpriseservices.nci.nih.gov/SubjectRegistrationService", name = "SubjectRegistrationFault")
    public JAXBElement<SubjectRegistrationFault> createSubjectRegistrationFault(SubjectRegistrationFault value) {
        return new JAXBElement<SubjectRegistrationFault>(_SubjectRegistrationFault_QNAME, SubjectRegistrationFault.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InvalidStudyProtocolExceptionFault }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://enterpriseservices.nci.nih.gov/SubjectRegistrationService", name = "InvalidStudyProtocolExceptionFault")
    public JAXBElement<InvalidStudyProtocolExceptionFault> createInvalidStudyProtocolExceptionFault(InvalidStudyProtocolExceptionFault value) {
        return new JAXBElement<InvalidStudyProtocolExceptionFault>(_InvalidStudyProtocolExceptionFault_QNAME, InvalidStudyProtocolExceptionFault.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NoSuchPatientExceptionFault }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://enterpriseservices.nci.nih.gov/SubjectRegistrationService", name = "NoSuchPatientExceptionFault")
    public JAXBElement<NoSuchPatientExceptionFault> createNoSuchPatientExceptionFault(NoSuchPatientExceptionFault value) {
        return new JAXBElement<NoSuchPatientExceptionFault>(_NoSuchPatientExceptionFault_QNAME, NoSuchPatientExceptionFault.class, null, value);
    }

}
