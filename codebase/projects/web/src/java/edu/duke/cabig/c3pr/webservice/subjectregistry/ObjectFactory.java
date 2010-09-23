
package edu.duke.cabig.c3pr.webservice.subjectregistry;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the edu.duke.cabig.c3pr.webservice.subjectregistry package. 
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

    private final static QName _DuplicateStudySubjectExceptionFault_QNAME = new QName("http://enterpriseservices.nci.nih.gov/SubjectRegistryService", "DuplicateStudySubjectExceptionFault");
    private final static QName _NoSuchStudySubjectExceptionFault_QNAME = new QName("http://enterpriseservices.nci.nih.gov/SubjectRegistryService", "NoSuchStudySubjectExceptionFault");
    private final static QName _InvalidStudyProtocolExceptionFault_QNAME = new QName("http://enterpriseservices.nci.nih.gov/SubjectRegistryService", "InvalidStudyProtocolExceptionFault");
    private final static QName _InvalidStudySubjectDataExceptionFault_QNAME = new QName("http://enterpriseservices.nci.nih.gov/SubjectRegistryService", "InvalidStudySubjectDataExceptionFault");
    private final static QName _SubjectRegistryFault_QNAME = new QName("http://enterpriseservices.nci.nih.gov/SubjectRegistryService", "SubjectRegistryFault");
    private final static QName _InsufficientPrivilegesExceptionFault_QNAME = new QName("http://enterpriseservices.nci.nih.gov/SubjectRegistryService", "InsufficientPrivilegesExceptionFault");
    private final static QName _InvalidQueryExceptionFault_QNAME = new QName("http://enterpriseservices.nci.nih.gov/SubjectRegistryService", "InvalidQueryExceptionFault");
    private final static QName _InvalidSiteExceptionFault_QNAME = new QName("http://enterpriseservices.nci.nih.gov/SubjectRegistryService", "InvalidSiteExceptionFault");
    private final static QName _NoSuchPatientExceptionFault_QNAME = new QName("http://enterpriseservices.nci.nih.gov/SubjectRegistryService", "NoSuchPatientExceptionFault");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: edu.duke.cabig.c3pr.webservice.subjectregistry
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SubjectRegistryFault }
     * 
     */
    public SubjectRegistryFault createSubjectRegistryFault() {
        return new SubjectRegistryFault();
    }

    /**
     * Create an instance of {@link InsufficientPrivilegesExceptionFault }
     * 
     */
    public InsufficientPrivilegesExceptionFault createInsufficientPrivilegesExceptionFault() {
        return new InsufficientPrivilegesExceptionFault();
    }

    /**
     * Create an instance of {@link DSETPerformedStudySubjectMilestone }
     * 
     */
    public DSETPerformedStudySubjectMilestone createDSETPerformedStudySubjectMilestone() {
        return new DSETPerformedStudySubjectMilestone();
    }

    /**
     * Create an instance of {@link DSETStudySubjectConsentVersion }
     * 
     */
    public DSETStudySubjectConsentVersion createDSETStudySubjectConsentVersion() {
        return new DSETStudySubjectConsentVersion();
    }

    /**
     * Create an instance of {@link Organization }
     * 
     */
    public Organization createOrganization() {
        return new Organization();
    }

    /**
     * Create an instance of {@link PerformedStudySubjectMilestone }
     * 
     */
    public PerformedStudySubjectMilestone createPerformedStudySubjectMilestone() {
        return new PerformedStudySubjectMilestone();
    }

    /**
     * Create an instance of {@link UpdateSubjectRegistryStatusResponse }
     * 
     */
    public UpdateSubjectRegistryStatusResponse createUpdateSubjectRegistryStatusResponse() {
        return new UpdateSubjectRegistryStatusResponse();
    }

    /**
     * Create an instance of {@link Subject }
     * 
     */
    public Subject createSubject() {
        return new Subject();
    }

    /**
     * Create an instance of {@link QuerySubjectRegistryResponse }
     * 
     */
    public QuerySubjectRegistryResponse createQuerySubjectRegistryResponse() {
        return new QuerySubjectRegistryResponse();
    }

    /**
     * Create an instance of {@link DocumentIdentifier }
     * 
     */
    public DocumentIdentifier createDocumentIdentifier() {
        return new DocumentIdentifier();
    }

    /**
     * Create an instance of {@link StudySubjectProtocolVersionRelationship }
     * 
     */
    public StudySubjectProtocolVersionRelationship createStudySubjectProtocolVersionRelationship() {
        return new StudySubjectProtocolVersionRelationship();
    }

    /**
     * Create an instance of {@link DSETStudySubject }
     * 
     */
    public DSETStudySubject createDSETStudySubject() {
        return new DSETStudySubject();
    }

    /**
     * Create an instance of {@link InitiateSubjectRegistryResponse }
     * 
     */
    public InitiateSubjectRegistryResponse createInitiateSubjectRegistryResponse() {
        return new InitiateSubjectRegistryResponse();
    }

    /**
     * Create an instance of {@link BiologicEntityIdentifier }
     * 
     */
    public BiologicEntityIdentifier createBiologicEntityIdentifier() {
        return new BiologicEntityIdentifier();
    }

    /**
     * Create an instance of {@link StudySubjectConsentVersion }
     * 
     */
    public StudySubjectConsentVersion createStudySubjectConsentVersion() {
        return new StudySubjectConsentVersion();
    }

    /**
     * Create an instance of {@link StudyProtocolVersion }
     * 
     */
    public StudyProtocolVersion createStudyProtocolVersion() {
        return new StudyProtocolVersion();
    }

    /**
     * Create an instance of {@link InvalidStudySubjectDataExceptionFault }
     * 
     */
    public InvalidStudySubjectDataExceptionFault createInvalidStudySubjectDataExceptionFault() {
        return new InvalidStudySubjectDataExceptionFault();
    }

    /**
     * Create an instance of {@link InvalidQueryExceptionFault }
     * 
     */
    public InvalidQueryExceptionFault createInvalidQueryExceptionFault() {
        return new InvalidQueryExceptionFault();
    }

    /**
     * Create an instance of {@link ImportSubjectRegistryRequest }
     * 
     */
    public ImportSubjectRegistryRequest createImportSubjectRegistryRequest() {
        return new ImportSubjectRegistryRequest();
    }

    /**
     * Create an instance of {@link StudySubject }
     * 
     */
    public StudySubject createStudySubject() {
        return new StudySubject();
    }

    /**
     * Create an instance of {@link DSETAdvanceSearchCriterionParameter }
     * 
     */
    public DSETAdvanceSearchCriterionParameter createDSETAdvanceSearchCriterionParameter() {
        return new DSETAdvanceSearchCriterionParameter();
    }

    /**
     * Create an instance of {@link DuplicateStudySubjectExceptionFault }
     * 
     */
    public DuplicateStudySubjectExceptionFault createDuplicateStudySubjectExceptionFault() {
        return new DuplicateStudySubjectExceptionFault();
    }

    /**
     * Create an instance of {@link StudySite }
     * 
     */
    public StudySite createStudySite() {
        return new StudySite();
    }

    /**
     * Create an instance of {@link AdvanceSearchCriterionParameter }
     * 
     */
    public AdvanceSearchCriterionParameter createAdvanceSearchCriterionParameter() {
        return new AdvanceSearchCriterionParameter();
    }

    /**
     * Create an instance of {@link NoSuchPatientExceptionFault }
     * 
     */
    public NoSuchPatientExceptionFault createNoSuchPatientExceptionFault() {
        return new NoSuchPatientExceptionFault();
    }

    /**
     * Create an instance of {@link ImportSubjectRegistryResponse }
     * 
     */
    public ImportSubjectRegistryResponse createImportSubjectRegistryResponse() {
        return new ImportSubjectRegistryResponse();
    }

    /**
     * Create an instance of {@link Consent }
     * 
     */
    public Consent createConsent() {
        return new Consent();
    }

    /**
     * Create an instance of {@link UpdateSubjectRegistryStatusHistoryRequest }
     * 
     */
    public UpdateSubjectRegistryStatusHistoryRequest createUpdateSubjectRegistryStatusHistoryRequest() {
        return new UpdateSubjectRegistryStatusHistoryRequest();
    }

    /**
     * Create an instance of {@link PerformedAdministrativeActivity }
     * 
     */
    public PerformedAdministrativeActivity createPerformedAdministrativeActivity() {
        return new PerformedAdministrativeActivity();
    }

    /**
     * Create an instance of {@link NoSuchStudySubjectExceptionFault }
     * 
     */
    public NoSuchStudySubjectExceptionFault createNoSuchStudySubjectExceptionFault() {
        return new NoSuchStudySubjectExceptionFault();
    }

    /**
     * Create an instance of {@link PerformedActivity }
     * 
     */
    public PerformedActivity createPerformedActivity() {
        return new PerformedActivity();
    }

    /**
     * Create an instance of {@link UpdateSubjectRegistryResponse }
     * 
     */
    public UpdateSubjectRegistryResponse createUpdateSubjectRegistryResponse() {
        return new UpdateSubjectRegistryResponse();
    }

    /**
     * Create an instance of {@link SubjectIdentifier }
     * 
     */
    public SubjectIdentifier createSubjectIdentifier() {
        return new SubjectIdentifier();
    }

    /**
     * Create an instance of {@link Document }
     * 
     */
    public Document createDocument() {
        return new Document();
    }

    /**
     * Create an instance of {@link RetrieveSubjectDemographyHistoryResponse }
     * 
     */
    public RetrieveSubjectDemographyHistoryResponse createRetrieveSubjectDemographyHistoryResponse() {
        return new RetrieveSubjectDemographyHistoryResponse();
    }

    /**
     * Create an instance of {@link OrganizationIdentifier }
     * 
     */
    public OrganizationIdentifier createOrganizationIdentifier() {
        return new OrganizationIdentifier();
    }

    /**
     * Create an instance of {@link QuerySubjectRegistryRequest }
     * 
     */
    public QuerySubjectRegistryRequest createQuerySubjectRegistryRequest() {
        return new QuerySubjectRegistryRequest();
    }

    /**
     * Create an instance of {@link UpdateSubjectRegistryStatusRequest }
     * 
     */
    public UpdateSubjectRegistryStatusRequest createUpdateSubjectRegistryStatusRequest() {
        return new UpdateSubjectRegistryStatusRequest();
    }

    /**
     * Create an instance of {@link Activity }
     * 
     */
    public Activity createActivity() {
        return new Activity();
    }

    /**
     * Create an instance of {@link InvalidStudyProtocolExceptionFault }
     * 
     */
    public InvalidStudyProtocolExceptionFault createInvalidStudyProtocolExceptionFault() {
        return new InvalidStudyProtocolExceptionFault();
    }

    /**
     * Create an instance of {@link DSETPerson }
     * 
     */
    public DSETPerson createDSETPerson() {
        return new DSETPerson();
    }

    /**
     * Create an instance of {@link StudySiteProtocolVersionRelationship }
     * 
     */
    public StudySiteProtocolVersionRelationship createStudySiteProtocolVersionRelationship() {
        return new StudySiteProtocolVersionRelationship();
    }

    /**
     * Create an instance of {@link StudyProtocolDocumentVersion }
     * 
     */
    public StudyProtocolDocumentVersion createStudyProtocolDocumentVersion() {
        return new StudyProtocolDocumentVersion();
    }

    /**
     * Create an instance of {@link InvalidSiteExceptionFault }
     * 
     */
    public InvalidSiteExceptionFault createInvalidSiteExceptionFault() {
        return new InvalidSiteExceptionFault();
    }

    /**
     * Create an instance of {@link Person }
     * 
     */
    public Person createPerson() {
        return new Person();
    }

    /**
     * Create an instance of {@link RetrieveSubjectDemographyHistoryRequest }
     * 
     */
    public RetrieveSubjectDemographyHistoryRequest createRetrieveSubjectDemographyHistoryRequest() {
        return new RetrieveSubjectDemographyHistoryRequest();
    }

    /**
     * Create an instance of {@link InitiateSubjectRegistryRequest }
     * 
     */
    public InitiateSubjectRegistryRequest createInitiateSubjectRegistryRequest() {
        return new InitiateSubjectRegistryRequest();
    }

    /**
     * Create an instance of {@link UpdateSubjectRegistryRequest }
     * 
     */
    public UpdateSubjectRegistryRequest createUpdateSubjectRegistryRequest() {
        return new UpdateSubjectRegistryRequest();
    }

    /**
     * Create an instance of {@link DocumentVersion }
     * 
     */
    public DocumentVersion createDocumentVersion() {
        return new DocumentVersion();
    }

    /**
     * Create an instance of {@link UpdateSubjectRegistryStatusHistoryResponse }
     * 
     */
    public UpdateSubjectRegistryStatusHistoryResponse createUpdateSubjectRegistryStatusHistoryResponse() {
        return new UpdateSubjectRegistryStatusHistoryResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DuplicateStudySubjectExceptionFault }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://enterpriseservices.nci.nih.gov/SubjectRegistryService", name = "DuplicateStudySubjectExceptionFault")
    public JAXBElement<DuplicateStudySubjectExceptionFault> createDuplicateStudySubjectExceptionFault(DuplicateStudySubjectExceptionFault value) {
        return new JAXBElement<DuplicateStudySubjectExceptionFault>(_DuplicateStudySubjectExceptionFault_QNAME, DuplicateStudySubjectExceptionFault.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NoSuchStudySubjectExceptionFault }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://enterpriseservices.nci.nih.gov/SubjectRegistryService", name = "NoSuchStudySubjectExceptionFault")
    public JAXBElement<NoSuchStudySubjectExceptionFault> createNoSuchStudySubjectExceptionFault(NoSuchStudySubjectExceptionFault value) {
        return new JAXBElement<NoSuchStudySubjectExceptionFault>(_NoSuchStudySubjectExceptionFault_QNAME, NoSuchStudySubjectExceptionFault.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InvalidStudyProtocolExceptionFault }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://enterpriseservices.nci.nih.gov/SubjectRegistryService", name = "InvalidStudyProtocolExceptionFault")
    public JAXBElement<InvalidStudyProtocolExceptionFault> createInvalidStudyProtocolExceptionFault(InvalidStudyProtocolExceptionFault value) {
        return new JAXBElement<InvalidStudyProtocolExceptionFault>(_InvalidStudyProtocolExceptionFault_QNAME, InvalidStudyProtocolExceptionFault.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InvalidStudySubjectDataExceptionFault }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://enterpriseservices.nci.nih.gov/SubjectRegistryService", name = "InvalidStudySubjectDataExceptionFault")
    public JAXBElement<InvalidStudySubjectDataExceptionFault> createInvalidStudySubjectDataExceptionFault(InvalidStudySubjectDataExceptionFault value) {
        return new JAXBElement<InvalidStudySubjectDataExceptionFault>(_InvalidStudySubjectDataExceptionFault_QNAME, InvalidStudySubjectDataExceptionFault.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SubjectRegistryFault }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://enterpriseservices.nci.nih.gov/SubjectRegistryService", name = "SubjectRegistryFault")
    public JAXBElement<SubjectRegistryFault> createSubjectRegistryFault(SubjectRegistryFault value) {
        return new JAXBElement<SubjectRegistryFault>(_SubjectRegistryFault_QNAME, SubjectRegistryFault.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InsufficientPrivilegesExceptionFault }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://enterpriseservices.nci.nih.gov/SubjectRegistryService", name = "InsufficientPrivilegesExceptionFault")
    public JAXBElement<InsufficientPrivilegesExceptionFault> createInsufficientPrivilegesExceptionFault(InsufficientPrivilegesExceptionFault value) {
        return new JAXBElement<InsufficientPrivilegesExceptionFault>(_InsufficientPrivilegesExceptionFault_QNAME, InsufficientPrivilegesExceptionFault.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InvalidQueryExceptionFault }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://enterpriseservices.nci.nih.gov/SubjectRegistryService", name = "InvalidQueryExceptionFault")
    public JAXBElement<InvalidQueryExceptionFault> createInvalidQueryExceptionFault(InvalidQueryExceptionFault value) {
        return new JAXBElement<InvalidQueryExceptionFault>(_InvalidQueryExceptionFault_QNAME, InvalidQueryExceptionFault.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InvalidSiteExceptionFault }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://enterpriseservices.nci.nih.gov/SubjectRegistryService", name = "InvalidSiteExceptionFault")
    public JAXBElement<InvalidSiteExceptionFault> createInvalidSiteExceptionFault(InvalidSiteExceptionFault value) {
        return new JAXBElement<InvalidSiteExceptionFault>(_InvalidSiteExceptionFault_QNAME, InvalidSiteExceptionFault.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NoSuchPatientExceptionFault }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://enterpriseservices.nci.nih.gov/SubjectRegistryService", name = "NoSuchPatientExceptionFault")
    public JAXBElement<NoSuchPatientExceptionFault> createNoSuchPatientExceptionFault(NoSuchPatientExceptionFault value) {
        return new JAXBElement<NoSuchPatientExceptionFault>(_NoSuchPatientExceptionFault_QNAME, NoSuchPatientExceptionFault.class, null, value);
    }

}
