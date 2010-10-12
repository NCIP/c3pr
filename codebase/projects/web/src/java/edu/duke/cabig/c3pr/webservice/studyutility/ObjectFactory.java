
package edu.duke.cabig.c3pr.webservice.studyutility;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the edu.duke.cabig.c3pr.webservice.studyutility package. 
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

    private final static QName _StudyUtilityFault_QNAME = new QName("http://enterpriseservices.nci.nih.gov/StudyUtilityService", "StudyUtilityFault");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: edu.duke.cabig.c3pr.webservice.studyutility
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link QueryStudyAbstractResponse }
     * 
     */
    public QueryStudyAbstractResponse createQueryStudyAbstractResponse() {
        return new QueryStudyAbstractResponse();
    }

    /**
     * Create an instance of {@link DSETConsent }
     * 
     */
    public DSETConsent createDSETConsent() {
        return new DSETConsent();
    }

    /**
     * Create an instance of {@link UpdateStudyStatusResponse }
     * 
     */
    public UpdateStudyStatusResponse createUpdateStudyStatusResponse() {
        return new UpdateStudyStatusResponse();
    }

    /**
     * Create an instance of {@link QueryStudyConsentResponse }
     * 
     */
    public QueryStudyConsentResponse createQueryStudyConsentResponse() {
        return new QueryStudyConsentResponse();
    }

    /**
     * Create an instance of {@link QueryRegistryStatusRequest }
     * 
     */
    public QueryRegistryStatusRequest createQueryRegistryStatusRequest() {
        return new QueryRegistryStatusRequest();
    }

    /**
     * Create an instance of {@link StudyUtilityFault }
     * 
     */
    public StudyUtilityFault createStudyUtilityFault() {
        return new StudyUtilityFault();
    }

    /**
     * Create an instance of {@link UpdateStudyStatusRequest }
     * 
     */
    public UpdateStudyStatusRequest createUpdateStudyStatusRequest() {
        return new UpdateStudyStatusRequest();
    }

    /**
     * Create an instance of {@link UpdateStudyAbstractResponse }
     * 
     */
    public UpdateStudyAbstractResponse createUpdateStudyAbstractResponse() {
        return new UpdateStudyAbstractResponse();
    }

    /**
     * Create an instance of {@link DSETRegistryStatus }
     * 
     */
    public DSETRegistryStatus createDSETRegistryStatus() {
        return new DSETRegistryStatus();
    }

    /**
     * Create an instance of {@link UpdateStudyAbstractRequest }
     * 
     */
    public UpdateStudyAbstractRequest createUpdateStudyAbstractRequest() {
        return new UpdateStudyAbstractRequest();
    }

    /**
     * Create an instance of {@link QueryStudyRegistryStatusResponse }
     * 
     */
    public QueryStudyRegistryStatusResponse createQueryStudyRegistryStatusResponse() {
        return new QueryStudyRegistryStatusResponse();
    }

    /**
     * Create an instance of {@link DSETStudyProtocolVersion }
     * 
     */
    public DSETStudyProtocolVersion createDSETStudyProtocolVersion() {
        return new DSETStudyProtocolVersion();
    }

    /**
     * Create an instance of {@link CreateStudyAbstractResponse }
     * 
     */
    public CreateStudyAbstractResponse createCreateStudyAbstractResponse() {
        return new CreateStudyAbstractResponse();
    }

    /**
     * Create an instance of {@link QueryStudyConsentRequest }
     * 
     */
    public QueryStudyConsentRequest createQueryStudyConsentRequest() {
        return new QueryStudyConsentRequest();
    }

    /**
     * Create an instance of {@link CreateStudyAbstractRequest }
     * 
     */
    public CreateStudyAbstractRequest createCreateStudyAbstractRequest() {
        return new CreateStudyAbstractRequest();
    }

    /**
     * Create an instance of {@link DSETPermissibleStudySubjectRegistryStatus }
     * 
     */
    public DSETPermissibleStudySubjectRegistryStatus createDSETPermissibleStudySubjectRegistryStatus() {
        return new DSETPermissibleStudySubjectRegistryStatus();
    }

    /**
     * Create an instance of {@link QueryStudyAbstractRequest }
     * 
     */
    public QueryStudyAbstractRequest createQueryStudyAbstractRequest() {
        return new QueryStudyAbstractRequest();
    }

    /**
     * Create an instance of {@link QueryStudyRegistryStatusRequest }
     * 
     */
    public QueryStudyRegistryStatusRequest createQueryStudyRegistryStatusRequest() {
        return new QueryStudyRegistryStatusRequest();
    }

    /**
     * Create an instance of {@link UpdateStudyConsentResponse }
     * 
     */
    public UpdateStudyConsentResponse createUpdateStudyConsentResponse() {
        return new UpdateStudyConsentResponse();
    }

    /**
     * Create an instance of {@link UpdateStudyConsentRequest }
     * 
     */
    public UpdateStudyConsentRequest createUpdateStudyConsentRequest() {
        return new UpdateStudyConsentRequest();
    }

    /**
     * Create an instance of {@link QueryRegistryStatusResponse }
     * 
     */
    public QueryRegistryStatusResponse createQueryRegistryStatusResponse() {
        return new QueryRegistryStatusResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StudyUtilityFault }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://enterpriseservices.nci.nih.gov/StudyUtilityService", name = "StudyUtilityFault")
    public JAXBElement<StudyUtilityFault> createStudyUtilityFault(StudyUtilityFault value) {
        return new JAXBElement<StudyUtilityFault>(_StudyUtilityFault_QNAME, StudyUtilityFault.class, null, value);
    }

}
