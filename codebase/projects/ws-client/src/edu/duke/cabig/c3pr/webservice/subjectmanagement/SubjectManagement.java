
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
@WebService(name = "SubjectManagement", targetNamespace = "http://enterpriseservices.nci.nih.gov/SubjectManagementService")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@XmlSeeAlso({
    edu.duke.cabig.c3pr.webservice.iso21090.ObjectFactory.class,
    edu.duke.cabig.c3pr.webservice.subjectmanagement.ObjectFactory.class
})
public interface SubjectManagement {


    /**
     * 
     * @param parameters
     * @return
     *     returns edu.duke.cabig.c3pr.webservice.subjectmanagement.CreateSubjectResponse
     * @throws UnableToCreateOrUpdateSubjectExceptionFaultMessage
     * @throws InsufficientPrivilegesExceptionFaultMessage
     * @throws SubjectAlreadyExistsExceptionFaultMessage
     * @throws InvalidSubjectDataExceptionFaultMessage
     */
    @WebMethod
    @WebResult(name = "CreateSubjectResponse", targetNamespace = "http://enterpriseservices.nci.nih.gov/SubjectManagementService", partName = "parameters")
    public CreateSubjectResponse createSubject(
        @WebParam(name = "CreateSubjectRequest", targetNamespace = "http://enterpriseservices.nci.nih.gov/SubjectManagementService", partName = "parameters")
        CreateSubjectRequest parameters)
        throws InsufficientPrivilegesExceptionFaultMessage, InvalidSubjectDataExceptionFaultMessage, SubjectAlreadyExistsExceptionFaultMessage, UnableToCreateOrUpdateSubjectExceptionFaultMessage
    ;

    /**
     * 
     * @param parameters
     * @return
     *     returns edu.duke.cabig.c3pr.webservice.subjectmanagement.QuerySubjectResponse
     * @throws InsufficientPrivilegesExceptionFaultMessage
     * @throws InvalidSubjectDataExceptionFaultMessage
     */
    @WebMethod
    @WebResult(name = "QuerySubjectResponse", targetNamespace = "http://enterpriseservices.nci.nih.gov/SubjectManagementService", partName = "parameters")
    public QuerySubjectResponse querySubject(
        @WebParam(name = "QuerySubjectRequest", targetNamespace = "http://enterpriseservices.nci.nih.gov/SubjectManagementService", partName = "parameters")
        QuerySubjectRequest parameters)
        throws InsufficientPrivilegesExceptionFaultMessage, InvalidSubjectDataExceptionFaultMessage
    ;

    /**
     * 
     * @param parameters
     * @return
     *     returns edu.duke.cabig.c3pr.webservice.subjectmanagement.AdvancedQuerySubjectResponse
     * @throws InsufficientPrivilegesExceptionFaultMessage
     * @throws InvalidSubjectDataExceptionFaultMessage
     */
    @WebMethod
    @WebResult(name = "AdvancedQuerySubjectResponse", targetNamespace = "http://enterpriseservices.nci.nih.gov/SubjectManagementService", partName = "parameters")
    public AdvancedQuerySubjectResponse advancedQuerySubject(
        @WebParam(name = "AdvancedQuerySubjectRequest", targetNamespace = "http://enterpriseservices.nci.nih.gov/SubjectManagementService", partName = "parameters")
        AdvancedQuerySubjectRequest parameters)
        throws InsufficientPrivilegesExceptionFaultMessage, InvalidSubjectDataExceptionFaultMessage
    ;

    /**
     * 
     * @param parameters
     * @return
     *     returns edu.duke.cabig.c3pr.webservice.subjectmanagement.UpdateSubjectResponse
     * @throws UnableToCreateOrUpdateSubjectExceptionFaultMessage
     * @throws InsufficientPrivilegesExceptionFaultMessage
     * @throws InvalidSubjectDataExceptionFaultMessage
     * @throws NoSuchSubjectExceptionFaultMessage
     */
    @WebMethod
    @WebResult(name = "UpdateSubjectResponse", targetNamespace = "http://enterpriseservices.nci.nih.gov/SubjectManagementService", partName = "parameters")
    public UpdateSubjectResponse updateSubject(
        @WebParam(name = "UpdateSubjectRequest", targetNamespace = "http://enterpriseservices.nci.nih.gov/SubjectManagementService", partName = "parameters")
        UpdateSubjectRequest parameters)
        throws InsufficientPrivilegesExceptionFaultMessage, InvalidSubjectDataExceptionFaultMessage, NoSuchSubjectExceptionFaultMessage, UnableToCreateOrUpdateSubjectExceptionFaultMessage
    ;

    /**
     * 
     * @param parameters
     * @return
     *     returns edu.duke.cabig.c3pr.webservice.subjectmanagement.UpdateSubjectStateResponse
     * @throws InsufficientPrivilegesExceptionFaultMessage
     * @throws InvalidStateTransitionExceptionFaultMessage
     * @throws NoSuchSubjectExceptionFaultMessage
     */
    @WebMethod
    @WebResult(name = "UpdateSubjectStateResponse", targetNamespace = "http://enterpriseservices.nci.nih.gov/SubjectManagementService", partName = "parameters")
    public UpdateSubjectStateResponse updateSubjectState(
        @WebParam(name = "UpdateSubjectStateRequest", targetNamespace = "http://enterpriseservices.nci.nih.gov/SubjectManagementService", partName = "parameters")
        UpdateSubjectStateRequest parameters)
        throws InsufficientPrivilegesExceptionFaultMessage, InvalidStateTransitionExceptionFaultMessage, NoSuchSubjectExceptionFaultMessage
    ;

}
