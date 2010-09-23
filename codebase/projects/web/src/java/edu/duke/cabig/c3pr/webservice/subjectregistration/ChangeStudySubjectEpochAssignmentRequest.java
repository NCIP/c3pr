
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
 *         &lt;element name="studySubject" type="{http://enterpriseservices.nci.nih.gov/SubjectRegistration}StudySubject"/>
 *         &lt;element name="offPreviousEpochReasons" type="{http://enterpriseservices.nci.nih.gov/SubjectRegistration}DSET_PerformedObservationResult"/>
 *         &lt;element name="offPreviousEpochDate" type="{uri:iso.org:21090}TS.DateTime"/>
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
    "studySubject",
    "offPreviousEpochReasons",
    "offPreviousEpochDate"
})
@XmlRootElement(name = "ChangeStudySubjectEpochAssignmentRequest", namespace = "http://enterpriseservices.nci.nih.gov/SubjectRegistrationService")
public class ChangeStudySubjectEpochAssignmentRequest {

    @XmlElement(namespace = "", required = true)
    protected StudySubject studySubject;
    @XmlElement(namespace = "", required = true)
    protected DSETPerformedObservationResult offPreviousEpochReasons;
    @XmlElement(namespace = "", required = true)
    protected TSDateTime offPreviousEpochDate;

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

    /**
     * Gets the value of the offPreviousEpochReasons property.
     * 
     * @return
     *     possible object is
     *     {@link DSETPerformedObservationResult }
     *     
     */
    public DSETPerformedObservationResult getOffPreviousEpochReasons() {
        return offPreviousEpochReasons;
    }

    /**
     * Sets the value of the offPreviousEpochReasons property.
     * 
     * @param value
     *     allowed object is
     *     {@link DSETPerformedObservationResult }
     *     
     */
    public void setOffPreviousEpochReasons(DSETPerformedObservationResult value) {
        this.offPreviousEpochReasons = value;
    }

    /**
     * Gets the value of the offPreviousEpochDate property.
     * 
     * @return
     *     possible object is
     *     {@link TSDateTime }
     *     
     */
    public TSDateTime getOffPreviousEpochDate() {
        return offPreviousEpochDate;
    }

    /**
     * Sets the value of the offPreviousEpochDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link TSDateTime }
     *     
     */
    public void setOffPreviousEpochDate(TSDateTime value) {
        this.offPreviousEpochDate = value;
    }

}
