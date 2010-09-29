
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
@WebService(name = "StudyUtility", wsdlLocation="/WEB-INF/wsdl/StudyUtility.wsdl", targetNamespace = "http://enterpriseservices.nci.nih.gov/StudyUtilityService")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@XmlSeeAlso({
    edu.duke.cabig.c3pr.webservice.studyutility.ObjectFactory.class,
    edu.duke.cabig.c3pr.webservice.iso21090.ObjectFactory.class
})
public interface StudyUtility {


    /**
     * 
     * @param parameters
     * @return
     *     returns edu.duke.cabig.c3pr.webservice.studyutility.CreateStudyResponse
     * @throws StudyUtilityFaultMessage
     */
    @WebMethod
    @WebResult(name = "CreateStudyResponse", targetNamespace = "http://enterpriseservices.nci.nih.gov/StudyUtilityService", partName = "parameters")
    public CreateStudyResponse createStudy(
        @WebParam(name = "CreateStudyRequest", targetNamespace = "http://enterpriseservices.nci.nih.gov/StudyUtilityService", partName = "parameters")
        CreateStudyRequest parameters)
        throws StudyUtilityFaultMessage
    ;

    /**
     * 
     * @param parameters
     * @return
     *     returns edu.duke.cabig.c3pr.webservice.studyutility.AdvancedQueryStudyResponse
     * @throws StudyUtilityFaultMessage
     */
    @WebMethod
    @WebResult(name = "AdvancedQueryStudyResponse", targetNamespace = "http://enterpriseservices.nci.nih.gov/StudyUtilityService", partName = "parameters")
    public AdvancedQueryStudyResponse advancedQueryStudy(
        @WebParam(name = "AdvancedQueryStudyRequest", targetNamespace = "http://enterpriseservices.nci.nih.gov/StudyUtilityService", partName = "parameters")
        AdvancedQueryStudyRequest parameters)
        throws StudyUtilityFaultMessage
    ;

    /**
     * 
     * @param parameters
     * @return
     *     returns edu.duke.cabig.c3pr.webservice.studyutility.UpdateStudyResponse
     * @throws StudyUtilityFaultMessage
     */
    @WebMethod
    @WebResult(name = "UpdateStudyResponse", targetNamespace = "http://enterpriseservices.nci.nih.gov/StudyUtilityService", partName = "parameters")
    public UpdateStudyResponse updateStudy(
        @WebParam(name = "UpdateStudyRequest", targetNamespace = "http://enterpriseservices.nci.nih.gov/StudyUtilityService", partName = "parameters")
        UpdateStudyRequest parameters)
        throws StudyUtilityFaultMessage
    ;

}
