
package edu.duke.cabig.c3pr.webservice.subjectregistration;

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
 *         &lt;element name="studySubjectIdentifier" type="{http://enterpriseservices.nci.nih.gov/SubjectRegistration}SubjectIdentifier"/>
 *         &lt;element name="studyProtocolVersion" type="{http://enterpriseservices.nci.nih.gov/SubjectRegistration}StudyProtocolVersion"/>
 *         &lt;element name="studySubjectConsentVersions" type="{http://enterpriseservices.nci.nih.gov/SubjectRegistration}DSET_StudySubjectConsentVersion"/>
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
    "studyProtocolVersion",
    "studySubjectConsentVersions"
})
@XmlRootElement(name = "ReconsentStudySubjectRequest", namespace = "http://enterpriseservices.nci.nih.gov/SubjectRegistrationService")
public class ReconsentStudySubjectRequest {

    @XmlElement(namespace = "", required = true)
    protected SubjectIdentifier studySubjectIdentifier;
    @XmlElement(namespace = "", required = true)
    protected StudyProtocolVersion studyProtocolVersion;
    @XmlElement(namespace = "", required = true)
    protected DSETStudySubjectConsentVersion studySubjectConsentVersions;

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
     * Gets the value of the studyProtocolVersion property.
     * 
     * @return
     *     possible object is
     *     {@link StudyProtocolVersion }
     *     
     */
    public StudyProtocolVersion getStudyProtocolVersion() {
        return studyProtocolVersion;
    }

    /**
     * Sets the value of the studyProtocolVersion property.
     * 
     * @param value
     *     allowed object is
     *     {@link StudyProtocolVersion }
     *     
     */
    public void setStudyProtocolVersion(StudyProtocolVersion value) {
        this.studyProtocolVersion = value;
    }

    /**
     * Gets the value of the studySubjectConsentVersions property.
     * 
     * @return
     *     possible object is
     *     {@link DSETStudySubjectConsentVersion }
     *     
     */
    public DSETStudySubjectConsentVersion getStudySubjectConsentVersions() {
        return studySubjectConsentVersions;
    }

    /**
     * Sets the value of the studySubjectConsentVersions property.
     * 
     * @param value
     *     allowed object is
     *     {@link DSETStudySubjectConsentVersion }
     *     
     */
    public void setStudySubjectConsentVersions(DSETStudySubjectConsentVersion value) {
        this.studySubjectConsentVersions = value;
    }

}
