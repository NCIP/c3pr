
package edu.duke.cabig.c3pr.webservice.testclient.subjectmanagement;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the edu.duke.cabig.c3pr.webservice.testclient.subjectmanagement package. 
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

    private final static QName _NoSuchSubjectExceptionFault_QNAME = new QName("http://enterpriseservices.nci.nih.gov/SubjectManagementService", "NoSuchSubjectExceptionFault");
    private final static QName _InvalidStateTransitionExceptionFault_QNAME = new QName("http://enterpriseservices.nci.nih.gov/SubjectManagementService", "InvalidStateTransitionExceptionFault");
    private final static QName _SubjectAlreadyExistsExceptionFault_QNAME = new QName("http://enterpriseservices.nci.nih.gov/SubjectManagementService", "SubjectAlreadyExistsExceptionFault");
    private final static QName _SubjectManagementFault_QNAME = new QName("http://enterpriseservices.nci.nih.gov/SubjectManagementService", "SubjectManagementFault");
    private final static QName _UnableToCreateOrUpdateSubjectExceptionFault_QNAME = new QName("http://enterpriseservices.nci.nih.gov/SubjectManagementService", "UnableToCreateOrUpdateSubjectExceptionFault");
    private final static QName _InvalidSubjectDataExceptionFault_QNAME = new QName("http://enterpriseservices.nci.nih.gov/SubjectManagementService", "InvalidSubjectDataExceptionFault");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: edu.duke.cabig.c3pr.webservice.testclient.subjectmanagement
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CreateSubjectRequest }
     * 
     */
    public CreateSubjectRequest createCreateSubjectRequest() {
        return new CreateSubjectRequest();
    }

    /**
     * Create an instance of {@link AdvancedQuerySubjectResponse }
     * 
     */
    public AdvancedQuerySubjectResponse createAdvancedQuerySubjectResponse() {
        return new AdvancedQuerySubjectResponse();
    }

    /**
     * Create an instance of {@link Person }
     * 
     */
    public Person createPerson() {
        return new Person();
    }

    /**
     * Create an instance of {@link InvalidStateTransitionExceptionFault }
     * 
     */
    public InvalidStateTransitionExceptionFault createInvalidStateTransitionExceptionFault() {
        return new InvalidStateTransitionExceptionFault();
    }

    /**
     * Create an instance of {@link SubjectAlreadyExistsExceptionFault }
     * 
     */
    public SubjectAlreadyExistsExceptionFault createSubjectAlreadyExistsExceptionFault() {
        return new SubjectAlreadyExistsExceptionFault();
    }

    /**
     * Create an instance of {@link NoSuchSubjectExceptionFault }
     * 
     */
    public NoSuchSubjectExceptionFault createNoSuchSubjectExceptionFault() {
        return new NoSuchSubjectExceptionFault();
    }

    /**
     * Create an instance of {@link QuerySubjectRequest }
     * 
     */
    public QuerySubjectRequest createQuerySubjectRequest() {
        return new QuerySubjectRequest();
    }

    /**
     * Create an instance of {@link BiologicEntityIdentifier }
     * 
     */
    public BiologicEntityIdentifier createBiologicEntityIdentifier() {
        return new BiologicEntityIdentifier();
    }

    /**
     * Create an instance of {@link UpdateSubjectResponse }
     * 
     */
    public UpdateSubjectResponse createUpdateSubjectResponse() {
        return new UpdateSubjectResponse();
    }

    /**
     * Create an instance of {@link DSETSUBJECT }
     * 
     */
    public DSETSUBJECT createDSETSUBJECT() {
        return new DSETSUBJECT();
    }

    /**
     * Create an instance of {@link AdvancedQuerySubjectRequest }
     * 
     */
    public AdvancedQuerySubjectRequest createAdvancedQuerySubjectRequest() {
        return new AdvancedQuerySubjectRequest();
    }

    /**
     * Create an instance of {@link Subject }
     * 
     */
    public Subject createSubject() {
        return new Subject();
    }

    /**
     * Create an instance of {@link CreateSubjectResponse }
     * 
     */
    public CreateSubjectResponse createCreateSubjectResponse() {
        return new CreateSubjectResponse();
    }

    /**
     * Create an instance of {@link UpdateSubjectStateRequest }
     * 
     */
    public UpdateSubjectStateRequest createUpdateSubjectStateRequest() {
        return new UpdateSubjectStateRequest();
    }

    /**
     * Create an instance of {@link QuerySubjectResponse }
     * 
     */
    public QuerySubjectResponse createQuerySubjectResponse() {
        return new QuerySubjectResponse();
    }

    /**
     * Create an instance of {@link UpdateSubjectRequest }
     * 
     */
    public UpdateSubjectRequest createUpdateSubjectRequest() {
        return new UpdateSubjectRequest();
    }

    /**
     * Create an instance of {@link UnableToCreateOrUpdateSubjectExceptionFault }
     * 
     */
    public UnableToCreateOrUpdateSubjectExceptionFault createUnableToCreateOrUpdateSubjectExceptionFault() {
        return new UnableToCreateOrUpdateSubjectExceptionFault();
    }

    /**
     * Create an instance of {@link InvalidSubjectDataExceptionFault }
     * 
     */
    public InvalidSubjectDataExceptionFault createInvalidSubjectDataExceptionFault() {
        return new InvalidSubjectDataExceptionFault();
    }

    /**
     * Create an instance of {@link SubjectManagementFault }
     * 
     */
    public SubjectManagementFault createSubjectManagementFault() {
        return new SubjectManagementFault();
    }

    /**
     * Create an instance of {@link UpdateSubjectStateResponse }
     * 
     */
    public UpdateSubjectStateResponse createUpdateSubjectStateResponse() {
        return new UpdateSubjectStateResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NoSuchSubjectExceptionFault }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://enterpriseservices.nci.nih.gov/SubjectManagementService", name = "NoSuchSubjectExceptionFault")
    public JAXBElement<NoSuchSubjectExceptionFault> createNoSuchSubjectExceptionFault(NoSuchSubjectExceptionFault value) {
        return new JAXBElement<NoSuchSubjectExceptionFault>(_NoSuchSubjectExceptionFault_QNAME, NoSuchSubjectExceptionFault.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InvalidStateTransitionExceptionFault }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://enterpriseservices.nci.nih.gov/SubjectManagementService", name = "InvalidStateTransitionExceptionFault")
    public JAXBElement<InvalidStateTransitionExceptionFault> createInvalidStateTransitionExceptionFault(InvalidStateTransitionExceptionFault value) {
        return new JAXBElement<InvalidStateTransitionExceptionFault>(_InvalidStateTransitionExceptionFault_QNAME, InvalidStateTransitionExceptionFault.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SubjectAlreadyExistsExceptionFault }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://enterpriseservices.nci.nih.gov/SubjectManagementService", name = "SubjectAlreadyExistsExceptionFault")
    public JAXBElement<SubjectAlreadyExistsExceptionFault> createSubjectAlreadyExistsExceptionFault(SubjectAlreadyExistsExceptionFault value) {
        return new JAXBElement<SubjectAlreadyExistsExceptionFault>(_SubjectAlreadyExistsExceptionFault_QNAME, SubjectAlreadyExistsExceptionFault.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SubjectManagementFault }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://enterpriseservices.nci.nih.gov/SubjectManagementService", name = "SubjectManagementFault")
    public JAXBElement<SubjectManagementFault> createSubjectManagementFault(SubjectManagementFault value) {
        return new JAXBElement<SubjectManagementFault>(_SubjectManagementFault_QNAME, SubjectManagementFault.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UnableToCreateOrUpdateSubjectExceptionFault }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://enterpriseservices.nci.nih.gov/SubjectManagementService", name = "UnableToCreateOrUpdateSubjectExceptionFault")
    public JAXBElement<UnableToCreateOrUpdateSubjectExceptionFault> createUnableToCreateOrUpdateSubjectExceptionFault(UnableToCreateOrUpdateSubjectExceptionFault value) {
        return new JAXBElement<UnableToCreateOrUpdateSubjectExceptionFault>(_UnableToCreateOrUpdateSubjectExceptionFault_QNAME, UnableToCreateOrUpdateSubjectExceptionFault.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InvalidSubjectDataExceptionFault }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://enterpriseservices.nci.nih.gov/SubjectManagementService", name = "InvalidSubjectDataExceptionFault")
    public JAXBElement<InvalidSubjectDataExceptionFault> createInvalidSubjectDataExceptionFault(InvalidSubjectDataExceptionFault value) {
        return new JAXBElement<InvalidSubjectDataExceptionFault>(_InvalidSubjectDataExceptionFault_QNAME, InvalidSubjectDataExceptionFault.class, null, value);
    }

}
