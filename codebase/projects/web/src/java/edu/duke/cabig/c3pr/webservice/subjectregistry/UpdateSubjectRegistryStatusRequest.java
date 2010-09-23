
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
 *         &lt;element name="studySubjectIdentifier" type="{http://enterpriseservices.nci.nih.gov/SubjectRegistry}SubjectIdentifier"/>
 *         &lt;element name="studySubjectStatus" type="{http://enterpriseservices.nci.nih.gov/SubjectRegistry}PerformedStudySubjectMilestone"/>
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
    "studySubjectIdentifier",
    "studySubjectStatus"
})
@XmlRootElement(name = "UpdateSubjectRegistryStatusRequest", namespace = "http://enterpriseservices.nci.nih.gov/SubjectRegistryService")
public class UpdateSubjectRegistryStatusRequest {

    @XmlElement(namespace = "", required = true)
    protected SubjectIdentifier studySubjectIdentifier;
    @XmlElement(namespace = "", required = true)
    protected PerformedStudySubjectMilestone studySubjectStatus;

    /**
     * Gets the value of the studySubjectIdentifier property.
     * 
     * @return
     *     possible object is
     *     {@link SubjectIdentifier }
     *     
     */
    public SubjectIdentifier getStudySubjectIdentifier() {
        return studySubjectIdentifier;
    }

    /**
     * Sets the value of the studySubjectIdentifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link SubjectIdentifier }
     *     
     */
    public void setStudySubjectIdentifier(SubjectIdentifier value) {
        this.studySubjectIdentifier = value;
    }

    /**
     * Gets the value of the studySubjectStatus property.
     * 
     * @return
     *     possible object is
     *     {@link PerformedStudySubjectMilestone }
     *     
     */
    public PerformedStudySubjectMilestone getStudySubjectStatus() {
        return studySubjectStatus;
    }

    /**
     * Sets the value of the studySubjectStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link PerformedStudySubjectMilestone }
     *     
     */
    public void setStudySubjectStatus(PerformedStudySubjectMilestone value) {
        this.studySubjectStatus = value;
    }

}
