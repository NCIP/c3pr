
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
     * Create an instance of {@link QueryConsentResponse }
     * 
     */
    public QueryConsentResponse createQueryConsentResponse() {
        return new QueryConsentResponse();
    }

    /**
     * Create an instance of {@link CreateStudyRequest }
     * 
     */
    public CreateStudyRequest createCreateStudyRequest() {
        return new CreateStudyRequest();
    }

    /**
     * Create an instance of {@link DSETConsent }
     * 
     */
    public DSETConsent createDSETConsent() {
        return new DSETConsent();
    }

    /**
     * Create an instance of {@link UpdateConsentResponse }
     * 
     */
    public UpdateConsentResponse createUpdateConsentResponse() {
        return new UpdateConsentResponse();
    }

    /**
     * Create an instance of {@link UpdateStudyStatusRequest }
     * 
     */
    public UpdateStudyStatusRequest createUpdateStudyStatusRequest() {
        return new UpdateStudyStatusRequest();
    }

    /**
     * Create an instance of {@link UpdateConsentRequest }
     * 
     */
    public UpdateConsentRequest createUpdateConsentRequest() {
        return new UpdateConsentRequest();
    }

    /**
     * Create an instance of {@link StudyUtilityFault }
     * 
     */
    public StudyUtilityFault createStudyUtilityFault() {
        return new StudyUtilityFault();
    }

    /**
     * Create an instance of {@link UpdateStudyStatusResponse }
     * 
     */
    public UpdateStudyStatusResponse createUpdateStudyStatusResponse() {
        return new UpdateStudyStatusResponse();
    }

    /**
     * Create an instance of {@link DSETStudyProtocolVersion }
     * 
     */
    public DSETStudyProtocolVersion createDSETStudyProtocolVersion() {
        return new DSETStudyProtocolVersion();
    }

    /**
     * Create an instance of {@link AdvancedQueryStudyRequest }
     * 
     */
    public AdvancedQueryStudyRequest createAdvancedQueryStudyRequest() {
        return new AdvancedQueryStudyRequest();
    }

    /**
     * Create an instance of {@link AdvancedQueryStudyResponse }
     * 
     */
    public AdvancedQueryStudyResponse createAdvancedQueryStudyResponse() {
        return new AdvancedQueryStudyResponse();
    }

    /**
     * Create an instance of {@link UpdateStudyRequest }
     * 
     */
    public UpdateStudyRequest createUpdateStudyRequest() {
        return new UpdateStudyRequest();
    }

    /**
     * Create an instance of {@link CreateStudyResponse }
     * 
     */
    public CreateStudyResponse createCreateStudyResponse() {
        return new CreateStudyResponse();
    }

    /**
     * Create an instance of {@link UpdateStudyResponse }
     * 
     */
    public UpdateStudyResponse createUpdateStudyResponse() {
        return new UpdateStudyResponse();
    }

    /**
     * Create an instance of {@link QueryConsentRequest }
     * 
     */
    public QueryConsentRequest createQueryConsentRequest() {
        return new QueryConsentRequest();
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
