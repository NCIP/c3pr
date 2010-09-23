
package edu.duke.cabig.c3pr.webservice.subjectregistration;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import edu.duke.cabig.c3pr.webservice.iso21090.CD;
import edu.duke.cabig.c3pr.webservice.iso21090.INTPositive;
import edu.duke.cabig.c3pr.webservice.iso21090.TSDateTime;


/**
 * <p>Java class for ScheduledEpoch complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ScheduledEpoch">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="epoch" type="{http://enterpriseservices.nci.nih.gov/SubjectRegistration}Epoch"/>
 *         &lt;element name="offEpochDate" type="{uri:iso.org:21090}TS.DateTime" minOccurs="0"/>
 *         &lt;element name="status" type="{uri:iso.org:21090}CD"/>
 *         &lt;element name="startDate" type="{uri:iso.org:21090}TS.DateTime"/>
 *         &lt;element name="stratumGroupNumber" type="{uri:iso.org:21090}INT.Positive" minOccurs="0"/>
 *         &lt;element name="subjectEligibilityAnswer" type="{http://enterpriseservices.nci.nih.gov/SubjectRegistration}PerformedObservationResult" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="subjectStartificationAnswer" type="{http://enterpriseservices.nci.nih.gov/SubjectRegistration}PerformedObservationResult" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="scheduledArm" type="{http://enterpriseservices.nci.nih.gov/SubjectRegistration}PerformedActivity" minOccurs="0"/>
 *         &lt;element name="offEpochReason" type="{http://enterpriseservices.nci.nih.gov/SubjectRegistration}DefinedObservationResult" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ScheduledEpoch", propOrder = {
    "epoch",
    "offEpochDate",
    "status",
    "startDate",
    "stratumGroupNumber",
    "subjectEligibilityAnswer",
    "subjectStartificationAnswer",
    "scheduledArm",
    "offEpochReason"
})
public class ScheduledEpoch {

    @XmlElement(required = true)
    protected Epoch epoch;
    protected TSDateTime offEpochDate;
    @XmlElement(required = true)
    protected CD status;
    @XmlElement(required = true)
    protected TSDateTime startDate;
    protected INTPositive stratumGroupNumber;
    protected List<PerformedObservationResult> subjectEligibilityAnswer;
    protected List<PerformedObservationResult> subjectStartificationAnswer;
    protected PerformedActivity scheduledArm;
    protected List<DefinedObservationResult> offEpochReason;

    /**
     * Gets the value of the epoch property.
     * 
     * @return
     *     possible object is
     *     {@link Epoch }
     *     
     */
    public Epoch getEpoch() {
        return epoch;
    }

    /**
     * Sets the value of the epoch property.
     * 
     * @param value
     *     allowed object is
     *     {@link Epoch }
     *     
     */
    public void setEpoch(Epoch value) {
        this.epoch = value;
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

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link CD }
     *     
     */
    public CD getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link CD }
     *     
     */
    public void setStatus(CD value) {
        this.status = value;
    }

    /**
     * Gets the value of the startDate property.
     * 
     * @return
     *     possible object is
     *     {@link TSDateTime }
     *     
     */
    public TSDateTime getStartDate() {
        return startDate;
    }

    /**
     * Sets the value of the startDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link TSDateTime }
     *     
     */
    public void setStartDate(TSDateTime value) {
        this.startDate = value;
    }

    /**
     * Gets the value of the stratumGroupNumber property.
     * 
     * @return
     *     possible object is
     *     {@link INTPositive }
     *     
     */
    public INTPositive getStratumGroupNumber() {
        return stratumGroupNumber;
    }

    /**
     * Sets the value of the stratumGroupNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link INTPositive }
     *     
     */
    public void setStratumGroupNumber(INTPositive value) {
        this.stratumGroupNumber = value;
    }

    /**
     * Gets the value of the subjectEligibilityAnswer property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the subjectEligibilityAnswer property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSubjectEligibilityAnswer().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PerformedObservationResult }
     * 
     * 
     */
    public List<PerformedObservationResult> getSubjectEligibilityAnswer() {
        if (subjectEligibilityAnswer == null) {
            subjectEligibilityAnswer = new ArrayList<PerformedObservationResult>();
        }
        return this.subjectEligibilityAnswer;
    }

    /**
     * Gets the value of the subjectStartificationAnswer property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the subjectStartificationAnswer property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSubjectStartificationAnswer().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PerformedObservationResult }
     * 
     * 
     */
    public List<PerformedObservationResult> getSubjectStartificationAnswer() {
        if (subjectStartificationAnswer == null) {
            subjectStartificationAnswer = new ArrayList<PerformedObservationResult>();
        }
        return this.subjectStartificationAnswer;
    }

    /**
     * Gets the value of the scheduledArm property.
     * 
     * @return
     *     possible object is
     *     {@link PerformedActivity }
     *     
     */
    public PerformedActivity getScheduledArm() {
        return scheduledArm;
    }

    /**
     * Sets the value of the scheduledArm property.
     * 
     * @param value
     *     allowed object is
     *     {@link PerformedActivity }
     *     
     */
    public void setScheduledArm(PerformedActivity value) {
        this.scheduledArm = value;
    }

    /**
     * Gets the value of the offEpochReason property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the offEpochReason property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOffEpochReason().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DefinedObservationResult }
     * 
     * 
     */
    public List<DefinedObservationResult> getOffEpochReason() {
        if (offEpochReason == null) {
            offEpochReason = new ArrayList<DefinedObservationResult>();
        }
        return this.offEpochReason;
    }

}
