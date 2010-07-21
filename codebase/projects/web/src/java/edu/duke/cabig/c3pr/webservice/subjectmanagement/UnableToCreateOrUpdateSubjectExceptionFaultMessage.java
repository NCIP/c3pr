
package edu.duke.cabig.c3pr.webservice.subjectmanagement;

import javax.xml.ws.WebFault;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.3.3-hudson-757-SNAPSHOT
 * Generated source version: 2.1
 * 
 */
@WebFault(name = "SubjectManagementFault", targetNamespace = "http://enterpriseservices.nci.nih.gov/SubjectManagementService")
public class UnableToCreateOrUpdateSubjectExceptionFaultMessage
    extends Exception
{

    /**
     * Java type that goes as soapenv:Fault detail element.
     * 
     */
    private SubjectManagementFault faultInfo;

    /**
     * 
     * @param message
     * @param faultInfo
     */
    public UnableToCreateOrUpdateSubjectExceptionFaultMessage(String message, SubjectManagementFault faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @param message
     * @param faultInfo
     * @param cause
     */
    public UnableToCreateOrUpdateSubjectExceptionFaultMessage(String message, SubjectManagementFault faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @return
     *     returns fault bean: edu.duke.cabig.c3pr.webservice.subjectmanagement.SubjectManagementFault
     */
    public SubjectManagementFault getFaultInfo() {
        return faultInfo;
    }

}
