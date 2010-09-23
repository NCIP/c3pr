
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
 *         &lt;element name="offStudyReasons" type="{http://enterpriseservices.nci.nih.gov/SubjectRegistration}DSET_PerformedObservationResult"/>
 *         &lt;element name="offStudyDate" type="{uri:iso.org:21090}TS.DateTime"/>
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
    "offStudyReasons",
    "offStudyDate"
})
@XmlRootElement(name = "TakeSubjectOffStudyRequest", namespace = "http://enterpriseservices.nci.nih.gov/SubjectRegistrationService")
public class TakeSubjectOffStudyRequest {

    @XmlElement(namespace = "", required = true)
    protected SubjectIdentifier studySubjectIdentifier;
    @XmlElement(namespace = "", required = true)
    protected DSETPerformedObservationResult offStudyReasons;
    @XmlElement(namespace = "", required = true)
    protected TSDateTime offStudyDate;

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
     * Gets the value of the offStudyReasons property.
     * 
     * @return
     *     possible object is
     *     {@link DSETPerformedObservationResult }
     *     
     */
    public DSETPerformedObservationResult getOffStudyReasons() {
        return offStudyReasons;
    }

    /**
     * Sets the value of the offStudyReasons property.
     * 
     * @param value
     *     allowed object is
     *     {@link DSETPerformedObservationResult }
     *     
     */
    public void setOffStudyReasons(DSETPerformedObservationResult value) {
        this.offStudyReasons = value;
    }

    /**
     * Gets the value of the offStudyDate property.
     * 
     * @return
     *     possible object is
     *     {@link TSDateTime }
     *     
     */
    public TSDateTime getOffStudyDate() {
        return offStudyDate;
    }

    /**
     * Sets the value of the offStudyDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link TSDateTime }
     *     
     */
    public void setOffStudyDate(TSDateTime value) {
        this.offStudyDate = value;
    }

}
