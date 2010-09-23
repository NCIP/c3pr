
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
 *         &lt;element name="studySubjects" type="{http://enterpriseservices.nci.nih.gov/SubjectRegistry}DSET_StudySubject"/>
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
    "studySubjects"
})
@XmlRootElement(name = "ImportSubjectRegistryResponse", namespace = "http://enterpriseservices.nci.nih.gov/SubjectRegistryService")
public class ImportSubjectRegistryResponse {

    @XmlElement(namespace = "", required = true)
    protected DSETStudySubject studySubjects;

    /**
     * Gets the value of the studySubjects property.
     * 
     * @return
     *     possible object is
     *     {@link DSETStudySubject }
     *     
     */
    public DSETStudySubject getStudySubjects() {
        return studySubjects;
    }

    /**
     * Sets the value of the studySubjects property.
     * 
     * @param value
     *     allowed object is
     *     {@link DSETStudySubject }
     *     
     */
    public void setStudySubjects(DSETStudySubject value) {
        this.studySubjects = value;
    }

}
