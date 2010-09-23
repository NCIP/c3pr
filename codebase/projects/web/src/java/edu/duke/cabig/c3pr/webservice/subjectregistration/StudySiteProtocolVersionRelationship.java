
package edu.duke.cabig.c3pr.webservice.subjectregistration;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import edu.duke.cabig.c3pr.webservice.iso21090.IVLTSDateTime;


/**
 * <p>Java class for StudySiteProtocolVersionRelationship complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="StudySiteProtocolVersionRelationship">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="dateRange" type="{uri:iso.org:21090}IVL_TS.DateTime" minOccurs="0"/>
 *         &lt;element name="studySite" type="{http://enterpriseservices.nci.nih.gov/SubjectRegistration}StudySite"/>
 *         &lt;element name="studyProtocolVersion" type="{http://enterpriseservices.nci.nih.gov/SubjectRegistration}StudyProtocolVersion"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StudySiteProtocolVersionRelationship", propOrder = {
    "dateRange",
    "studySite",
    "studyProtocolVersion"
})
public class StudySiteProtocolVersionRelationship {

    protected IVLTSDateTime dateRange;
    @XmlElement(required = true)
    protected StudySite studySite;
    @XmlElement(required = true)
    protected StudyProtocolVersion studyProtocolVersion;

    /**
     * Gets the value of the dateRange property.
     * 
     * @return
     *     possible object is
     *     {@link IVLTSDateTime }
     *     
     */
    public IVLTSDateTime getDateRange() {
        return dateRange;
    }

    /**
     * Sets the value of the dateRange property.
     * 
     * @param value
     *     allowed object is
     *     {@link IVLTSDateTime }
     *     
     */
    public void setDateRange(IVLTSDateTime value) {
        this.dateRange = value;
    }

    /**
     * Gets the value of the studySite property.
     * 
     * @return
     *     possible object is
     *     {@link StudySite }
     *     
     */
    public StudySite getStudySite() {
        return studySite;
    }

    /**
     * Sets the value of the studySite property.
     * 
     * @param value
     *     allowed object is
     *     {@link StudySite }
     *     
     */
    public void setStudySite(StudySite value) {
        this.studySite = value;
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

}
