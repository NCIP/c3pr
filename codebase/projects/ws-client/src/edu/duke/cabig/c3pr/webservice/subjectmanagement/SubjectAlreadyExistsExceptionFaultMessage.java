
package edu.duke.cabig.c3pr.webservice.subjectmanagement;

import javax.xml.ws.WebFault;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.7-hudson-48-
 * Generated source version: 2.1
 * 
 */
@WebFault(name = "SubjectAlreadyExistsExceptionFault", targetNamespace = "http://enterpriseservices.nci.nih.gov/SubjectManagementService")
public class SubjectAlreadyExistsExceptionFaultMessage
    extends Exception
{

    /**
     * Java type that goes as soapenv:Fault detail element.
     * 
     */
    private SubjectAlreadyExistsExceptionFault faultInfo;

    /**
     * 
     * @param message
     * @param faultInfo
     */
    public SubjectAlreadyExistsExceptionFaultMessage(String message, SubjectAlreadyExistsExceptionFault faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @param message
     * @param faultInfo
     * @param cause
     */
    public SubjectAlreadyExistsExceptionFaultMessage(String message, SubjectAlreadyExistsExceptionFault faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @return
     *     returns fault bean: edu.duke.cabig.c3pr.webservice.subjectmanagement.SubjectAlreadyExistsExceptionFault
     */
    public SubjectAlreadyExistsExceptionFault getFaultInfo() {
        return faultInfo;
    }

}
