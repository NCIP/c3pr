
package edu.duke.cabig.c3pr.webservice.subjectregistration;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for NoSuchPatientExceptionFault complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="NoSuchPatientExceptionFault">
 *   &lt;complexContent>
 *     &lt;extension base="{http://enterpriseservices.nci.nih.gov/SubjectRegistrationService}SubjectRegistrationFault">
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NoSuchPatientExceptionFault", namespace = "http://enterpriseservices.nci.nih.gov/SubjectRegistrationService")
public class NoSuchPatientExceptionFault
    extends SubjectRegistrationFault
{


}
