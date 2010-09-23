
package edu.duke.cabig.c3pr.webservice.subjectregistry;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for InvalidQueryExceptionFault complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InvalidQueryExceptionFault">
 *   &lt;complexContent>
 *     &lt;extension base="{http://enterpriseservices.nci.nih.gov/SubjectRegistryService}SubjectRegistryFault">
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InvalidQueryExceptionFault", namespace = "http://enterpriseservices.nci.nih.gov/SubjectRegistryService")
public class InvalidQueryExceptionFault
    extends SubjectRegistryFault
{


}
