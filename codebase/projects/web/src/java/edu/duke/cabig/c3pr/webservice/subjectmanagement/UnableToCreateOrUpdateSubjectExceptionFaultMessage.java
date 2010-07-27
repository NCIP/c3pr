
package edu.duke.cabig.c3pr.webservice.subjectmanagement;

import javax.xml.ws.WebFault;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.3.3-hudson-757-SNAPSHOT
 * Generated source version: 2.1
 * 
 */
@WebFault(name = "UnableToCreateOrUpdateSubjectExceptionFault", targetNamespace = "http://enterpriseservices.nci.nih.gov/SubjectManagementService")
public class UnableToCreateOrUpdateSubjectExceptionFaultMessage
    extends SubjectManagementException
{

    /**
     * Java type that goes as soapenv:Fault detail element.
     * 
     */
    private UnableToCreateOrUpdateSubjectExceptionFault faultInfo;

    /**
     * 
     * @param message
     * @param faultInfo
     */
    public UnableToCreateOrUpdateSubjectExceptionFaultMessage(String message, UnableToCreateOrUpdateSubjectExceptionFault faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @param message
     * @param faultInfo
     * @param cause
     */
    public UnableToCreateOrUpdateSubjectExceptionFaultMessage(String message, UnableToCreateOrUpdateSubjectExceptionFault faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @return
     *     returns fault bean: edu.duke.cabig.c3pr.webservice.subjectmanagement.UnableToCreateOrUpdateSubjectExceptionFault
     */
    public UnableToCreateOrUpdateSubjectExceptionFault getFaultInfo() {
        return faultInfo;
    }

}
