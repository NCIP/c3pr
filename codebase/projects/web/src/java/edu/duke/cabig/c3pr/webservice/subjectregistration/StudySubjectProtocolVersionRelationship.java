
package edu.duke.cabig.c3pr.webservice.subjectregistration;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for StudySubjectProtocolVersionRelationship complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="StudySubjectProtocolVersionRelationship">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="studySiteProtocolVersion" type="{http://enterpriseservices.nci.nih.gov/SubjectRegistration}StudySiteProtocolVersionRelationship"/>
 *         &lt;element name="studySubjectConsentVersion" type="{http://enterpriseservices.nci.nih.gov/SubjectRegistration}StudySubjectConsentVersion" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="scheduledEpoch" type="{http://enterpriseservices.nci.nih.gov/SubjectRegistration}ScheduledEpoch" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StudySubjectProtocolVersionRelationship", propOrder = {
    "studySiteProtocolVersion",
    "studySubjectConsentVersion",
    "scheduledEpoch"
})
public class StudySubjectProtocolVersionRelationship {

    @XmlElement(required = true)
    protected StudySiteProtocolVersionRelationship studySiteProtocolVersion;
    protected List<StudySubjectConsentVersion> studySubjectConsentVersion;
    @XmlElement(required = true)
    protected List<ScheduledEpoch> scheduledEpoch;

    /**
     * Gets the value of the studySiteProtocolVersion property.
     * 
     * @return
     *     possible object is
     *     {@link StudySiteProtocolVersionRelationship }
     *     
     */
    public StudySiteProtocolVersionRelationship getStudySiteProtocolVersion() {
        return studySiteProtocolVersion;
    }

    /**
     * Sets the value of the studySiteProtocolVersion property.
     * 
     * @param value
     *     allowed object is
     *     {@link StudySiteProtocolVersionRelationship }
     *     
     */
    public void setStudySiteProtocolVersion(StudySiteProtocolVersionRelationship value) {
        this.studySiteProtocolVersion = value;
    }

    /**
     * Gets the value of the studySubjectConsentVersion property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the studySubjectConsentVersion property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getStudySubjectConsentVersion().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link StudySubjectConsentVersion }
     * 
     * 
     */
    public List<StudySubjectConsentVersion> getStudySubjectConsentVersion() {
        if (studySubjectConsentVersion == null) {
            studySubjectConsentVersion = new ArrayList<StudySubjectConsentVersion>();
        }
        return this.studySubjectConsentVersion;
    }

    /**
     * Gets the value of the scheduledEpoch property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the scheduledEpoch property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getScheduledEpoch().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ScheduledEpoch }
     * 
     * 
     */
    public List<ScheduledEpoch> getScheduledEpoch() {
        if (scheduledEpoch == null) {
            scheduledEpoch = new ArrayList<ScheduledEpoch>();
        }
        return this.scheduledEpoch;
    }

}
