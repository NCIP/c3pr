/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.webservice.subjectmanagement;

import javax.xml.ws.WebFault;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.7-hudson-48-
 * Generated source version: 2.1
 * 
 */
@WebFault(name = "NoSuchSubjectExceptionFault", targetNamespace = "http://enterpriseservices.nci.nih.gov/SubjectManagementService")
public class NoSuchSubjectExceptionFaultMessage
    extends Exception
{

    /**
     * Java type that goes as soapenv:Fault detail element.
     * 
     */
    private NoSuchSubjectExceptionFault faultInfo;

    /**
     * 
     * @param message
     * @param faultInfo
     */
    public NoSuchSubjectExceptionFaultMessage(String message, NoSuchSubjectExceptionFault faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @param message
     * @param faultInfo
     * @param cause
     */
    public NoSuchSubjectExceptionFaultMessage(String message, NoSuchSubjectExceptionFault faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @return
     *     returns fault bean: edu.duke.cabig.c3pr.webservice.subjectmanagement.NoSuchSubjectExceptionFault
     */
    public NoSuchSubjectExceptionFault getFaultInfo() {
        return faultInfo;
    }

}
