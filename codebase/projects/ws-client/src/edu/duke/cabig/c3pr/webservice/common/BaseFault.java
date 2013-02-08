/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.webservice.common;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import edu.duke.cabig.c3pr.webservice.subjectmanagement.InvalidStateTransitionExceptionFault;
import edu.duke.cabig.c3pr.webservice.subjectmanagement.InvalidSubjectDataExceptionFault;
import edu.duke.cabig.c3pr.webservice.subjectmanagement.NoSuchSubjectExceptionFault;
import edu.duke.cabig.c3pr.webservice.subjectmanagement.SubjectAlreadyExistsExceptionFault;
import edu.duke.cabig.c3pr.webservice.subjectmanagement.UnableToCreateOrUpdateSubjectExceptionFault;


/**
 * <p>Java class for BaseFault complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BaseFault">
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
@XmlType(name = "BaseFault", propOrder = {
    "message"
})
@XmlSeeAlso({
    NoSuchSubjectExceptionFault.class,
    UnableToCreateOrUpdateSubjectExceptionFault.class,
    SubjectAlreadyExistsExceptionFault.class,
    InvalidStateTransitionExceptionFault.class,
    InvalidSubjectDataExceptionFault.class,
    NoSuchPatientExceptionFault.class,
    NoSuchStudySubjectExceptionFault.class,
    InvalidStudySubjectDataExceptionFault.class,
    DuplicateStudySubjectExceptionFault.class,
    InvalidStudyProtocolExceptionFault.class,
    SecurityExceptionFault.class,
    InvalidSiteExceptionFault.class,
    InvalidQueryExceptionFault.class
})
public class BaseFault {

    @XmlElement(required = true)
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
