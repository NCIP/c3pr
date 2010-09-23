
package edu.duke.cabig.c3pr.webservice.subjectregistry;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="studySubject" type="{http://enterpriseservices.nci.nih.gov/SubjectRegistry}StudySubject"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "studySubject"
})
@XmlRootElement(name = "UpdateSubjectRegistryResponse", namespace = "http://enterpriseservices.nci.nih.gov/SubjectRegistryService")
public class UpdateSubjectRegistryResponse {

    @XmlElement(namespace = "", required = true)
    protected StudySubject studySubject;

    /**
     * Gets the value of the studySubject property.
     * 
     * @return
     *     possible object is
     *     {@link StudySubject }
     *     
     */
    public StudySubject getStudySubject() {
        return studySubject;
    }

    /**
     * Sets the value of the studySubject property.
     * 
     * @param value
     *     allowed object is
     *     {@link StudySubject }
     *     
     */
    public void setStudySubject(StudySubject value) {
        this.studySubject = value;
    }

}
