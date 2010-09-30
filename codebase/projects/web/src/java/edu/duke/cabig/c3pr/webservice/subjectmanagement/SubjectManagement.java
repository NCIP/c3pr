
package edu.duke.cabig.c3pr.webservice.subjectmanagement;

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
@WebService(name = "SubjectManagement",wsdlLocation="/WEB-INF/wsdl/SubjectManagement.wsdl", targetNamespace = "http://enterpriseservices.nci.nih.gov/SubjectManagementService")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@XmlSeeAlso({
    edu.duke.cabig.c3pr.webservice.iso21090.ObjectFactory.class,
    edu.duke.cabig.c3pr.webservice.subjectmanagement.ObjectFactory.class,
    edu.duke.cabig.c3pr.webservice.common.ObjectFactory.class
})
public interface SubjectManagement {


    /**
     * 
     * @param parameters
     * @return
     *     returns edu.duke.cabig.c3pr.webservice.subjectmanagement.CreateSubjectResponse
     * @throws UnableToCreateOrUpdateSubjectExceptionFaultMessage
     * @throws InvalidSubjectDataExceptionFaultMessage
     * @throws SecurityExceptionFaultMessage
     * @throws SubjectAlreadyExistsExceptionFaultMessage
     */
    @WebMethod
    @WebResult(name = "CreateSubjectResponse", targetNamespace = "http://enterpriseservices.nci.nih.gov/SubjectManagementService", partName = "parameters")
    public CreateSubjectResponse createSubject(
        @WebParam(name = "CreateSubjectRequest", targetNamespace = "http://enterpriseservices.nci.nih.gov/SubjectManagementService", partName = "parameters")
        CreateSubjectRequest parameters)
        throws InvalidSubjectDataExceptionFaultMessage, SecurityExceptionFaultMessage, SubjectAlreadyExistsExceptionFaultMessage, UnableToCreateOrUpdateSubjectExceptionFaultMessage
    ;

    /**
     * 
     * @param parameters
     * @return
     *     returns edu.duke.cabig.c3pr.webservice.subjectmanagement.QuerySubjectResponse
     * @throws InvalidSubjectDataExceptionFaultMessage
     * @throws SecurityExceptionFaultMessage
     */
    @WebMethod
    @WebResult(name = "QuerySubjectResponse", targetNamespace = "http://enterpriseservices.nci.nih.gov/SubjectManagementService", partName = "parameters")
    public QuerySubjectResponse querySubject(
        @WebParam(name = "QuerySubjectRequest", targetNamespace = "http://enterpriseservices.nci.nih.gov/SubjectManagementService", partName = "parameters")
        QuerySubjectRequest parameters)
        throws InvalidSubjectDataExceptionFaultMessage, SecurityExceptionFaultMessage
    ;

    /**
     * 
     * @param parameters
     * @return
     *     returns edu.duke.cabig.c3pr.webservice.subjectmanagement.AdvancedQuerySubjectResponse
     * @throws InvalidSubjectDataExceptionFaultMessage
     * @throws SecurityExceptionFaultMessage
     */
    @WebMethod
    @WebResult(name = "AdvancedQuerySubjectResponse", targetNamespace = "http://enterpriseservices.nci.nih.gov/SubjectManagementService", partName = "parameters")
    public AdvancedQuerySubjectResponse advancedQuerySubject(
        @WebParam(name = "AdvancedQuerySubjectRequest", targetNamespace = "http://enterpriseservices.nci.nih.gov/SubjectManagementService", partName = "parameters")
        AdvancedQuerySubjectRequest parameters)
        throws InvalidSubjectDataExceptionFaultMessage, SecurityExceptionFaultMessage
    ;

    /**
     * 
     * @param parameters
     * @return
     *     returns edu.duke.cabig.c3pr.webservice.subjectmanagement.UpdateSubjectResponse
     * @throws NoSuchSubjectExceptionFaultMessage
     * @throws UnableToCreateOrUpdateSubjectExceptionFaultMessage
     * @throws InvalidSubjectDataExceptionFaultMessage
     * @throws SecurityExceptionFaultMessage
     */
    @WebMethod
    @WebResult(name = "UpdateSubjectResponse", targetNamespace = "http://enterpriseservices.nci.nih.gov/SubjectManagementService", partName = "parameters")
    public UpdateSubjectResponse updateSubject(
        @WebParam(name = "UpdateSubjectRequest", targetNamespace = "http://enterpriseservices.nci.nih.gov/SubjectManagementService", partName = "parameters")
        UpdateSubjectRequest parameters)
        throws InvalidSubjectDataExceptionFaultMessage, NoSuchSubjectExceptionFaultMessage, SecurityExceptionFaultMessage, UnableToCreateOrUpdateSubjectExceptionFaultMessage
    ;

    /**
     * 
     * @param parameters
     * @return
     *     returns edu.duke.cabig.c3pr.webservice.subjectmanagement.UpdateSubjectStateResponse
     * @throws NoSuchSubjectExceptionFaultMessage
     * @throws SecurityExceptionFaultMessage
     * @throws InvalidStateTransitionExceptionFaultMessage
     */
    @WebMethod
    @WebResult(name = "UpdateSubjectStateResponse", targetNamespace = "http://enterpriseservices.nci.nih.gov/SubjectManagementService", partName = "parameters")
    public UpdateSubjectStateResponse updateSubjectState(
        @WebParam(name = "UpdateSubjectStateRequest", targetNamespace = "http://enterpriseservices.nci.nih.gov/SubjectManagementService", partName = "parameters")
        UpdateSubjectStateRequest parameters)
        throws InvalidStateTransitionExceptionFaultMessage, NoSuchSubjectExceptionFaultMessage, SecurityExceptionFaultMessage
    ;

}
