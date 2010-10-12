
package edu.duke.cabig.c3pr.webservice.testclient.subjectmanagement;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.7-hudson-48-
 * Generated source version: 2.1
 * 
 */
@WebServiceClient(name = "SubjectManagementService", targetNamespace = "http://enterpriseservices.nci.nih.gov/SubjectManagementService", wsdlLocation = "file:/C:/temp/subjectmanagement/SubjectManagement.wsdl")
public class SubjectManagementService
    extends Service
{

    private final static URL SUBJECTMANAGEMENTSERVICE_WSDL_LOCATION;
    private final static Logger logger = Logger.getLogger(edu.duke.cabig.c3pr.webservice.testclient.subjectmanagement.SubjectManagementService.class.getName());

    static {
        URL url = null;
        try {
            URL baseUrl;
            baseUrl = edu.duke.cabig.c3pr.webservice.testclient.subjectmanagement.SubjectManagementService.class.getResource(".");
            url = new URL(baseUrl, "file:/C:/temp/subjectmanagement/SubjectManagement.wsdl");
        } catch (MalformedURLException e) {
            logger.warning("Failed to create URL for the wsdl Location: 'file:/C:/temp/subjectmanagement/SubjectManagement.wsdl', retrying as a local file");
            logger.warning(e.getMessage());
        }
        SUBJECTMANAGEMENTSERVICE_WSDL_LOCATION = url;
    }

    public SubjectManagementService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public SubjectManagementService() {
        super(SUBJECTMANAGEMENTSERVICE_WSDL_LOCATION, new QName("http://enterpriseservices.nci.nih.gov/SubjectManagementService", "SubjectManagementService"));
    }

    /**
     * 
     * @return
     *     returns SubjectManagement
     */
    @WebEndpoint(name = "SubjectManagement")
    public SubjectManagement getSubjectManagement() {
        return super.getPort(new QName("http://enterpriseservices.nci.nih.gov/SubjectManagementService", "SubjectManagement"), SubjectManagement.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns SubjectManagement
     */
    @WebEndpoint(name = "SubjectManagement")
    public SubjectManagement getSubjectManagement(WebServiceFeature... features) {
        return super.getPort(new QName("http://enterpriseservices.nci.nih.gov/SubjectManagementService", "SubjectManagement"), SubjectManagement.class, features);
    }

}
