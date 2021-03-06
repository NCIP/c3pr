/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/c3pr/LICENSE.txt for details.
*******************************************************************************/
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
    edu.duke.cabig.c3pr.webservice.common.ObjectFactory.class,
    edu.duke.cabig.c3pr.webservice.subjectmanagement.ObjectFactory.class
})
public interface SubjectManagement {


    /**
     * 
     * @param parameters
     * @return
     *     returns edu.duke.cabig.c3pr.webservice.subjectmanagement.CreateSubjectResponse
     * @throws UnableToCreateOrUpdateSubjectExceptionFaultMessage
     * @throws SecurityExceptionFaultMessage
     * @throws InvalidSubjectDataExceptionFaultMessage
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
     * @throws SecurityExceptionFaultMessage
     * @throws InvalidSubjectDataExceptionFaultMessage
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
     * @throws SecurityExceptionFaultMessage
     * @throws InvalidSubjectDataExceptionFaultMessage
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
     * @throws UnableToCreateOrUpdateSubjectExceptionFaultMessage
     * @throws SecurityExceptionFaultMessage
     * @throws InvalidSubjectDataExceptionFaultMessage
     * @throws NoSuchSubjectExceptionFaultMessage
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
     * @throws InvalidStateTransitionExceptionFaultMessage
     * @throws SecurityExceptionFaultMessage
     * @throws NoSuchSubjectExceptionFaultMessage
     */
    @WebMethod
    @WebResult(name = "UpdateSubjectStateResponse", targetNamespace = "http://enterpriseservices.nci.nih.gov/SubjectManagementService", partName = "parameters")
    public UpdateSubjectStateResponse updateSubjectState(
        @WebParam(name = "UpdateSubjectStateRequest", targetNamespace = "http://enterpriseservices.nci.nih.gov/SubjectManagementService", partName = "parameters")
        UpdateSubjectStateRequest parameters)
        throws InvalidStateTransitionExceptionFaultMessage, NoSuchSubjectExceptionFaultMessage, SecurityExceptionFaultMessage
    ;

}
