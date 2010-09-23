
package edu.duke.cabig.c3pr.webservice.subjectregistry;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SubjectRegistryFault complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SubjectRegistryFault">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="message" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SubjectRegistryFault", namespace = "http://enterpriseservices.nci.nih.gov/SubjectRegistryService", propOrder = {
    "message"
})
@XmlSeeAlso({
    InvalidSiteExceptionFault.class,
    InvalidStudyProtocolExceptionFault.class,
    InsufficientPrivilegesExceptionFault.class,
    DuplicateStudySubjectExceptionFault.class,
    InvalidStudySubjectDataExceptionFault.class,
    NoSuchPatientExceptionFault.class,
    NoSuchStudySubjectExceptionFault.class,
    InvalidQueryExceptionFault.class
})
public class SubjectRegistryFault {

    @XmlElement(namespace = "", required = true)
    protected String message;

    /**
     * Gets the value of the message property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the value of the message property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMessage(String value) {
        this.message = value;
    }

}
