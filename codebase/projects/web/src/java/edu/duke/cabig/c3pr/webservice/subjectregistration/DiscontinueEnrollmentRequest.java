
package edu.duke.cabig.c3pr.webservice.subjectregistration;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import edu.duke.cabig.c3pr.webservice.iso21090.TSDateTime;


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
 *         &lt;element name="offEpochReasons" type="{http://enterpriseservices.nci.nih.gov/SubjectRegistration}DSET_PerformedObservationResult"/>
 *         &lt;element name="offEpochDate" type="{uri:iso.org:21090}TS.DateTime"/>
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
    "offEpochReasons",
    "offEpochDate"
})
@XmlRootElement(name = "DiscontinueEnrollmentRequest", namespace = "http://enterpriseservices.nci.nih.gov/SubjectRegistrationService")
public class DiscontinueEnrollmentRequest {

    @XmlElement(namespace = "", required = true)
    protected SubjectIdentifier studySubjectIdentifier;
    @XmlElement(namespace = "", required = true)
    protected DSETPerformedObservationResult offEpochReasons;
    @XmlElement(namespace = "", required = true)
    protected TSDateTime offEpochDate;

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
     * Gets the value of the offEpochReasons property.
     * 
     * @return
     *     possible object is
     *     {@link DSETPerformedObservationResult }
     *     
     */
    public DSETPerformedObservationResult getOffEpochReasons() {
        return offEpochReasons;
    }

    /**
     * Sets the value of the offEpochReasons property.
     * 
     * @param value
     *     allowed object is
     *     {@link DSETPerformedObservationResult }
     *     
     */
    public void setOffEpochReasons(DSETPerformedObservationResult value) {
        this.offEpochReasons = value;
    }

    /**
     * Gets the value of the offEpochDate property.
     * 
     * @return
     *     possible object is
     *     {@link TSDateTime }
     *     
     */
    public TSDateTime getOffEpochDate() {
        return offEpochDate;
    }

    /**
     * Sets the value of the offEpochDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link TSDateTime }
     *     
     */
    public void setOffEpochDate(TSDateTime value) {
        this.offEpochDate = value;
    }

}
