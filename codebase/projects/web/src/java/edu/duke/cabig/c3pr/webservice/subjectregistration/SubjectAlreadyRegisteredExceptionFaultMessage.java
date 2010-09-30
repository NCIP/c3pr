
package edu.duke.cabig.c3pr.webservice.subjectregistration;

import javax.xml.ws.WebFault;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.1-b03-
 * Generated source version: 2.1
 * 
 */
@WebFault(name = "SubjectAlreadyRegisteredExceptionFault", targetNamespace = "http://enterpriseservices.nci.nih.gov/SubjectRegistrationService")
public class SubjectAlreadyRegisteredExceptionFaultMessage
    extends Exception
{

    /**
     * Java type that goes as soapenv:Fault detail element.
     * 
     */
    private SubjectAlreadyRegisteredExceptionFault faultInfo;

    /**
     * 
     * @param faultInfo
     * @param message
     */
    public SubjectAlreadyRegisteredExceptionFaultMessage(String message, SubjectAlreadyRegisteredExceptionFault faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @param faultInfo
     * @param message
     * @param cause
     */
    public SubjectAlreadyRegisteredExceptionFaultMessage(String message, SubjectAlreadyRegisteredExceptionFault faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @return
     *     returns fault bean: edu.duke.cabig.c3pr.webservice.subjectregistration.SubjectAlreadyRegisteredExceptionFault
     */
    public SubjectAlreadyRegisteredExceptionFault getFaultInfo() {
        return faultInfo;
    }

}