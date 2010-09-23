
package edu.duke.cabig.c3pr.webservice.subjectregistry;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for StudyProtocolVersion complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="StudyProtocolVersion">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="studyProtocolDocument" type="{http://enterpriseservices.nci.nih.gov/SubjectRegistry}StudyProtocolDocumentVersion"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StudyProtocolVersion", propOrder = {
    "studyProtocolDocument"
})
public class StudyProtocolVersion {

    @XmlElement(required = true)
    protected StudyProtocolDocumentVersion studyProtocolDocument;

    /**
     * Gets the value of the studyProtocolDocument property.
     * 
     * @return
     *     possible object is
     *     {@link StudyProtocolDocumentVersion }
     *     
     */
    public StudyProtocolDocumentVersion getStudyProtocolDocument() {
        return studyProtocolDocument;
    }

    /**
     * Sets the value of the studyProtocolDocument property.
     * 
     * @param value
     *     allowed object is
     *     {@link StudyProtocolDocumentVersion }
     *     
     */
    public void setStudyProtocolDocument(StudyProtocolDocumentVersion value) {
        this.studyProtocolDocument = value;
    }

}
