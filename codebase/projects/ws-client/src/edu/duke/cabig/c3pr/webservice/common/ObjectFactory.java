
package edu.duke.cabig.c3pr.webservice.common;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the edu.duke.cabig.c3pr.webservice.common package. 
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

    private final static QName _SecurityExceptionFault_QNAME = new QName("http://enterpriseservices.nci.nih.gov/Common", "SecurityExceptionFault");
    private final static QName _InvalidQueryExceptionFault_QNAME = new QName("http://enterpriseservices.nci.nih.gov/Common", "InvalidQueryExceptionFault");
    private final static QName _NoSuchStudySubjectExceptionFault_QNAME = new QName("http://enterpriseservices.nci.nih.gov/Common", "NoSuchStudySubjectExceptionFault");
    private final static QName _InvalidStudyProtocolExceptionFault_QNAME = new QName("http://enterpriseservices.nci.nih.gov/Common", "InvalidStudyProtocolExceptionFault");
    private final static QName _Study_QNAME = new QName("http://enterpriseservices.nci.nih.gov/Common", "study");
    private final static QName _InvalidSiteExceptionFault_QNAME = new QName("http://enterpriseservices.nci.nih.gov/Common", "InvalidSiteExceptionFault");
    private final static QName _NoSuchPatientExceptionFault_QNAME = new QName("http://enterpriseservices.nci.nih.gov/Common", "NoSuchPatientExceptionFault");
    private final static QName _InvalidStudySubjectDataExceptionFault_QNAME = new QName("http://enterpriseservices.nci.nih.gov/Common", "InvalidStudySubjectDataExceptionFault");
    private final static QName _DuplicateStudySubjectExceptionFault_QNAME = new QName("http://enterpriseservices.nci.nih.gov/Common", "DuplicateStudySubjectExceptionFault");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: edu.duke.cabig.c3pr.webservice.common
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link PermissibleStudySubjectRegistryStatus }
     * 
     */
    public PermissibleStudySubjectRegistryStatus createPermissibleStudySubjectRegistryStatus() {
        return new PermissibleStudySubjectRegistryStatus();
    }

    /**
     * Create an instance of {@link BaseFault }
     * 
     */
    public BaseFault createBaseFault() {
        return new BaseFault();
    }

    /**
     * Create an instance of {@link RegistryStatusReason }
     * 
     */
    public RegistryStatusReason createRegistryStatusReason() {
        return new RegistryStatusReason();
    }

    /**
     * Create an instance of {@link DocumentIdentifier }
     * 
     */
    public DocumentIdentifier createDocumentIdentifier() {
        return new DocumentIdentifier();
    }

    /**
     * Create an instance of {@link Consent }
     * 
     */
    public Consent createConsent() {
        return new Consent();
    }

    /**
     * Create an instance of {@link Person }
     * 
     */
    public Person createPerson() {
        return new Person();
    }

    /**
     * Create an instance of {@link StudySite }
     * 
     */
    public StudySite createStudySite() {
        return new StudySite();
    }

    /**
     * Create an instance of {@link StudyProtocolVersion }
     * 
     */
    public StudyProtocolVersion createStudyProtocolVersion() {
        return new StudyProtocolVersion();
    }

    /**
     * Create an instance of {@link AdvanceSearchCriterionParameter }
     * 
     */
    public AdvanceSearchCriterionParameter createAdvanceSearchCriterionParameter() {
        return new AdvanceSearchCriterionParameter();
    }

    /**
     * Create an instance of {@link StudyProtocolDocumentVersion }
     * 
     */
    public StudyProtocolDocumentVersion createStudyProtocolDocumentVersion() {
        return new StudyProtocolDocumentVersion();
    }

    /**
     * Create an instance of {@link DocumentVersionRelationship }
     * 
     */
    public DocumentVersionRelationship createDocumentVersionRelationship() {
        return new DocumentVersionRelationship();
    }

    /**
     * Create an instance of {@link InvalidSiteExceptionFault }
     * 
     */
    public InvalidSiteExceptionFault createInvalidSiteExceptionFault() {
        return new InvalidSiteExceptionFault();
    }

    /**
     * Create an instance of {@link OrganizationIdentifier }
     * 
     */
    public OrganizationIdentifier createOrganizationIdentifier() {
        return new OrganizationIdentifier();
    }

    /**
     * Create an instance of {@link BiologicEntityIdentifier }
     * 
     */
    public BiologicEntityIdentifier createBiologicEntityIdentifier() {
        return new BiologicEntityIdentifier();
    }

    /**
     * Create an instance of {@link Document }
     * 
     */
    public Document createDocument() {
        return new Document();
    }

    /**
     * Create an instance of {@link InvalidStudySubjectDataExceptionFault }
     * 
     */
    public InvalidStudySubjectDataExceptionFault createInvalidStudySubjectDataExceptionFault() {
        return new InvalidStudySubjectDataExceptionFault();
    }

    /**
     * Create an instance of {@link RegistryStatus }
     * 
     */
    public RegistryStatus createRegistryStatus() {
        return new RegistryStatus();
    }

    /**
     * Create an instance of {@link DuplicateStudySubjectExceptionFault }
     * 
     */
    public DuplicateStudySubjectExceptionFault createDuplicateStudySubjectExceptionFault() {
        return new DuplicateStudySubjectExceptionFault();
    }

    /**
     * Create an instance of {@link StudySubjectBase }
     * 
     */
    public StudySubjectBase createStudySubjectBase() {
        return new StudySubjectBase();
    }

    /**
     * Create an instance of {@link DSETStudySubjectConsentVersion }
     * 
     */
    public DSETStudySubjectConsentVersion createDSETStudySubjectConsentVersion() {
        return new DSETStudySubjectConsentVersion();
    }

    /**
     * Create an instance of {@link SecurityExceptionFault }
     * 
     */
    public SecurityExceptionFault createSecurityExceptionFault() {
        return new SecurityExceptionFault();
    }

    /**
     * Create an instance of {@link PerformedStudySubjectMilestone }
     * 
     */
    public PerformedStudySubjectMilestone createPerformedStudySubjectMilestone() {
        return new PerformedStudySubjectMilestone();
    }

    /**
     * Create an instance of {@link NoSuchPatientExceptionFault }
     * 
     */
    public NoSuchPatientExceptionFault createNoSuchPatientExceptionFault() {
        return new NoSuchPatientExceptionFault();
    }

    /**
     * Create an instance of {@link DSETPerformedStudySubjectMilestone }
     * 
     */
    public DSETPerformedStudySubjectMilestone createDSETPerformedStudySubjectMilestone() {
        return new DSETPerformedStudySubjectMilestone();
    }

    /**
     * Create an instance of {@link StudySubjectProtocolVersionRelationship }
     * 
     */
    public StudySubjectProtocolVersionRelationship createStudySubjectProtocolVersionRelationship() {
        return new StudySubjectProtocolVersionRelationship();
    }

    /**
     * Create an instance of {@link Subject }
     * 
     */
    public Subject createSubject() {
        return new Subject();
    }

    /**
     * Create an instance of {@link PerformedActivity }
     * 
     */
    public PerformedActivity createPerformedActivity() {
        return new PerformedActivity();
    }

    /**
     * Create an instance of {@link Organization }
     * 
     */
    public Organization createOrganization() {
        return new Organization();
    }

    /**
     * Create an instance of {@link Drug }
     * 
     */
    public Drug createDrug() {
        return new Drug();
    }

    /**
     * Create an instance of {@link StudySubjectConsentVersion }
     * 
     */
    public StudySubjectConsentVersion createStudySubjectConsentVersion() {
        return new StudySubjectConsentVersion();
    }

    /**
     * Create an instance of {@link StudySiteProtocolVersionRelationship }
     * 
     */
    public StudySiteProtocolVersionRelationship createStudySiteProtocolVersionRelationship() {
        return new StudySiteProtocolVersionRelationship();
    }

    /**
     * Create an instance of {@link DSETAdvanceSearchCriterionParameter }
     * 
     */
    public DSETAdvanceSearchCriterionParameter createDSETAdvanceSearchCriterionParameter() {
        return new DSETAdvanceSearchCriterionParameter();
    }

    /**
     * Create an instance of {@link InvalidQueryExceptionFault }
     * 
     */
    public InvalidQueryExceptionFault createInvalidQueryExceptionFault() {
        return new InvalidQueryExceptionFault();
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
     * Create an instance of {@link NoSuchStudySubjectExceptionFault }
     * 
     */
    public NoSuchStudySubjectExceptionFault createNoSuchStudySubjectExceptionFault() {
        return new NoSuchStudySubjectExceptionFault();
    }

    /**
     * Create an instance of {@link DocumentVersion }
     * 
     */
    public DocumentVersion createDocumentVersion() {
        return new DocumentVersion();
    }

    /**
     * Create an instance of {@link Activity }
     * 
     */
    public Activity createActivity() {
        return new Activity();
    }

    /**
     * Create an instance of {@link PerformedAdministrativeActivity }
     * 
     */
    public PerformedAdministrativeActivity createPerformedAdministrativeActivity() {
        return new PerformedAdministrativeActivity();
    }

    /**
     * Create an instance of {@link SubjectIdentifier }
     * 
     */
    public SubjectIdentifier createSubjectIdentifier() {
        return new SubjectIdentifier();
    }

    /**
     * Create an instance of {@link DefinedActivity }
     * 
     */
    public DefinedActivity createDefinedActivity() {
        return new DefinedActivity();
    }

    /**
     * Create an instance of {@link Reason }
     * 
     */
    public Reason createReason() {
        return new Reason();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SecurityExceptionFault }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://enterpriseservices.nci.nih.gov/Common", name = "SecurityExceptionFault")
    public JAXBElement<SecurityExceptionFault> createSecurityExceptionFault(SecurityExceptionFault value) {
        return new JAXBElement<SecurityExceptionFault>(_SecurityExceptionFault_QNAME, SecurityExceptionFault.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InvalidQueryExceptionFault }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://enterpriseservices.nci.nih.gov/Common", name = "InvalidQueryExceptionFault")
    public JAXBElement<InvalidQueryExceptionFault> createInvalidQueryExceptionFault(InvalidQueryExceptionFault value) {
        return new JAXBElement<InvalidQueryExceptionFault>(_InvalidQueryExceptionFault_QNAME, InvalidQueryExceptionFault.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NoSuchStudySubjectExceptionFault }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://enterpriseservices.nci.nih.gov/Common", name = "NoSuchStudySubjectExceptionFault")
    public JAXBElement<NoSuchStudySubjectExceptionFault> createNoSuchStudySubjectExceptionFault(NoSuchStudySubjectExceptionFault value) {
        return new JAXBElement<NoSuchStudySubjectExceptionFault>(_NoSuchStudySubjectExceptionFault_QNAME, NoSuchStudySubjectExceptionFault.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InvalidStudyProtocolExceptionFault }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://enterpriseservices.nci.nih.gov/Common", name = "InvalidStudyProtocolExceptionFault")
    public JAXBElement<InvalidStudyProtocolExceptionFault> createInvalidStudyProtocolExceptionFault(InvalidStudyProtocolExceptionFault value) {
        return new JAXBElement<InvalidStudyProtocolExceptionFault>(_InvalidStudyProtocolExceptionFault_QNAME, InvalidStudyProtocolExceptionFault.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StudyProtocolVersion }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://enterpriseservices.nci.nih.gov/Common", name = "study")
    public JAXBElement<StudyProtocolVersion> createStudy(StudyProtocolVersion value) {
        return new JAXBElement<StudyProtocolVersion>(_Study_QNAME, StudyProtocolVersion.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InvalidSiteExceptionFault }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://enterpriseservices.nci.nih.gov/Common", name = "InvalidSiteExceptionFault")
    public JAXBElement<InvalidSiteExceptionFault> createInvalidSiteExceptionFault(InvalidSiteExceptionFault value) {
        return new JAXBElement<InvalidSiteExceptionFault>(_InvalidSiteExceptionFault_QNAME, InvalidSiteExceptionFault.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NoSuchPatientExceptionFault }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://enterpriseservices.nci.nih.gov/Common", name = "NoSuchPatientExceptionFault")
    public JAXBElement<NoSuchPatientExceptionFault> createNoSuchPatientExceptionFault(NoSuchPatientExceptionFault value) {
        return new JAXBElement<NoSuchPatientExceptionFault>(_NoSuchPatientExceptionFault_QNAME, NoSuchPatientExceptionFault.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InvalidStudySubjectDataExceptionFault }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://enterpriseservices.nci.nih.gov/Common", name = "InvalidStudySubjectDataExceptionFault")
    public JAXBElement<InvalidStudySubjectDataExceptionFault> createInvalidStudySubjectDataExceptionFault(InvalidStudySubjectDataExceptionFault value) {
        return new JAXBElement<InvalidStudySubjectDataExceptionFault>(_InvalidStudySubjectDataExceptionFault_QNAME, InvalidStudySubjectDataExceptionFault.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DuplicateStudySubjectExceptionFault }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://enterpriseservices.nci.nih.gov/Common", name = "DuplicateStudySubjectExceptionFault")
    public JAXBElement<DuplicateStudySubjectExceptionFault> createDuplicateStudySubjectExceptionFault(DuplicateStudySubjectExceptionFault value) {
        return new JAXBElement<DuplicateStudySubjectExceptionFault>(_DuplicateStudySubjectExceptionFault_QNAME, DuplicateStudySubjectExceptionFault.class, null, value);
    }

}
