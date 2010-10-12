
package edu.duke.cabig.c3pr.webservice.studyutility;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.7-hudson-48-
 * Generated source version: 2.1
 * 
 */
@WebService(name = "StudyUtility", targetNamespace = "http://enterpriseservices.nci.nih.gov/StudyUtilityService")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@XmlSeeAlso({
    edu.duke.cabig.c3pr.webservice.studyutility.ObjectFactory.class,
    edu.duke.cabig.c3pr.webservice.common.ObjectFactory.class,
    edu.duke.cabig.c3pr.webservice.iso21090.ObjectFactory.class
})
public interface StudyUtility {


    /**
     * 
     * @param parameters
     * @return
     *     returns edu.duke.cabig.c3pr.webservice.studyutility.CreateStudyAbstractResponse
     * @throws StudyUtilityFaultMessage
     * @throws SecurityExceptionFaultMessage
     */
    @WebMethod
    @WebResult(name = "CreateStudyAbstractResponse", targetNamespace = "http://enterpriseservices.nci.nih.gov/StudyUtilityService", partName = "parameters")
    public CreateStudyAbstractResponse createStudyAbstract(
        @WebParam(name = "CreateStudyAbstractRequest", targetNamespace = "http://enterpriseservices.nci.nih.gov/StudyUtilityService", partName = "parameters")
        CreateStudyAbstractRequest parameters)
        throws SecurityExceptionFaultMessage, StudyUtilityFaultMessage
    ;

    /**
     * 
     * @param parameters
     * @return
     *     returns edu.duke.cabig.c3pr.webservice.studyutility.QueryStudyAbstractResponse
     * @throws StudyUtilityFaultMessage
     * @throws SecurityExceptionFaultMessage
     */
    @WebMethod
    @WebResult(name = "QueryStudyAbstractResponse", targetNamespace = "http://enterpriseservices.nci.nih.gov/StudyUtilityService", partName = "parameters")
    public QueryStudyAbstractResponse queryStudyAbstract(
        @WebParam(name = "QueryStudyAbstractRequest", targetNamespace = "http://enterpriseservices.nci.nih.gov/StudyUtilityService", partName = "parameters")
        QueryStudyAbstractRequest parameters)
        throws SecurityExceptionFaultMessage, StudyUtilityFaultMessage
    ;

    /**
     * 
     * @param parameters
     * @return
     *     returns edu.duke.cabig.c3pr.webservice.studyutility.UpdateStudyAbstractResponse
     * @throws StudyUtilityFaultMessage
     * @throws SecurityExceptionFaultMessage
     */
    @WebMethod
    @WebResult(name = "UpdateStudyAbstractResponse", targetNamespace = "http://enterpriseservices.nci.nih.gov/StudyUtilityService", partName = "parameters")
    public UpdateStudyAbstractResponse updateStudyAbstract(
        @WebParam(name = "UpdateStudyAbstractRequest", targetNamespace = "http://enterpriseservices.nci.nih.gov/StudyUtilityService", partName = "parameters")
        UpdateStudyAbstractRequest parameters)
        throws SecurityExceptionFaultMessage, StudyUtilityFaultMessage
    ;

    /**
     * 
     * @param parameters
     * @return
     *     returns edu.duke.cabig.c3pr.webservice.studyutility.UpdateStudyStatusResponse
     * @throws StudyUtilityFaultMessage
     * @throws SecurityExceptionFaultMessage
     */
    @WebMethod
    @WebResult(name = "UpdateStudyStatusResponse", targetNamespace = "http://enterpriseservices.nci.nih.gov/StudyUtilityService", partName = "parameters")
    public UpdateStudyStatusResponse updateStudyStatus(
        @WebParam(name = "UpdateStudyStatusRequest", targetNamespace = "http://enterpriseservices.nci.nih.gov/StudyUtilityService", partName = "parameters")
        UpdateStudyStatusRequest parameters)
        throws SecurityExceptionFaultMessage, StudyUtilityFaultMessage
    ;

    /**
     * 
     * @param parameters
     * @return
     *     returns edu.duke.cabig.c3pr.webservice.studyutility.UpdateStudyConsentResponse
     * @throws StudyUtilityFaultMessage
     * @throws SecurityExceptionFaultMessage
     */
    @WebMethod
    @WebResult(name = "UpdateStudyConsentResponse", targetNamespace = "http://enterpriseservices.nci.nih.gov/StudyUtilityService", partName = "parameters")
    public UpdateStudyConsentResponse updateStudyConsent(
        @WebParam(name = "UpdateStudyConsentRequest", targetNamespace = "http://enterpriseservices.nci.nih.gov/StudyUtilityService", partName = "parameters")
        UpdateStudyConsentRequest parameters)
        throws SecurityExceptionFaultMessage, StudyUtilityFaultMessage
    ;

    /**
     * 
     * @param parameters
     * @return
     *     returns edu.duke.cabig.c3pr.webservice.studyutility.QueryStudyConsentResponse
     * @throws StudyUtilityFaultMessage
     * @throws SecurityExceptionFaultMessage
     */
    @WebMethod
    @WebResult(name = "QueryStudyConsentResponse", targetNamespace = "http://enterpriseservices.nci.nih.gov/StudyUtilityService", partName = "parameters")
    public QueryStudyConsentResponse queryStudyConsent(
        @WebParam(name = "QueryStudyConsentRequest", targetNamespace = "http://enterpriseservices.nci.nih.gov/StudyUtilityService", partName = "parameters")
        QueryStudyConsentRequest parameters)
        throws SecurityExceptionFaultMessage, StudyUtilityFaultMessage
    ;

    /**
     * 
     * @param parameters
     * @return
     *     returns edu.duke.cabig.c3pr.webservice.studyutility.QueryRegistryStatusResponse
     * @throws StudyUtilityFaultMessage
     * @throws SecurityExceptionFaultMessage
     */
    @WebMethod
    @WebResult(name = "QueryRegistryStatusResponse", targetNamespace = "http://enterpriseservices.nci.nih.gov/StudyUtilityService", partName = "parameters")
    public QueryRegistryStatusResponse queryRegistryStatus(
        @WebParam(name = "QueryRegistryStatusRequest", targetNamespace = "http://enterpriseservices.nci.nih.gov/StudyUtilityService", partName = "parameters")
        QueryRegistryStatusRequest parameters)
        throws SecurityExceptionFaultMessage, StudyUtilityFaultMessage
    ;

    /**
     * 
     * @param parameters
     * @return
     *     returns edu.duke.cabig.c3pr.webservice.studyutility.QueryStudyRegistryStatusResponse
     * @throws StudyUtilityFaultMessage
     * @throws SecurityExceptionFaultMessage
     */
    @WebMethod
    @WebResult(name = "QueryStudyRegistryStatusResponse", targetNamespace = "http://enterpriseservices.nci.nih.gov/StudyUtilityService", partName = "parameters")
    public QueryStudyRegistryStatusResponse queryStudyRegistryStatus(
        @WebParam(name = "QueryStudyRegistryStatusRequest", targetNamespace = "http://enterpriseservices.nci.nih.gov/StudyUtilityService", partName = "parameters")
        QueryStudyRegistryStatusRequest parameters)
        throws SecurityExceptionFaultMessage, StudyUtilityFaultMessage
    ;

}
