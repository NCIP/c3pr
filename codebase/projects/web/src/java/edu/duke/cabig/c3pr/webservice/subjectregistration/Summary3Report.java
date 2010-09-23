
package edu.duke.cabig.c3pr.webservice.subjectregistration;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import edu.duke.cabig.c3pr.webservice.iso21090.ST;
import edu.duke.cabig.c3pr.webservice.iso21090.TSDateTime;


/**
 * <p>Java class for Summary3Report complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Summary3Report">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="reportingSource" type="{uri:iso.org:21090}ST"/>
 *         &lt;element name="startDate" type="{uri:iso.org:21090}TS.DateTime"/>
 *         &lt;element name="endDate" type="{uri:iso.org:21090}TS.DateTime"/>
 *         &lt;element name="grantNumber" type="{uri:iso.org:21090}ST" minOccurs="0"/>
 *         &lt;element name="diseaseSite" type="{http://enterpriseservices.nci.nih.gov/SubjectRegistration}PerformedDiagnosis" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Summary3Report", propOrder = {
    "reportingSource",
    "startDate",
    "endDate",
    "grantNumber",
    "diseaseSite"
})
public class Summary3Report {

    @XmlElement(required = true)
    protected ST reportingSource;
    @XmlElement(required = true)
    protected TSDateTime startDate;
    @XmlElement(required = true)
    protected TSDateTime endDate;
    protected ST grantNumber;
    @XmlElement(required = true)
    protected List<PerformedDiagnosis> diseaseSite;

    /**
     * Gets the value of the reportingSource property.
     * 
     * @return
     *     possible object is
     *     {@link ST }
     *     
     */
    public ST getReportingSource() {
        return reportingSource;
    }

    /**
     * Sets the value of the reportingSource property.
     * 
     * @param value
     *     allowed object is
     *     {@link ST }
     *     
     */
    public void setReportingSource(ST value) {
        this.reportingSource = value;
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
     * Gets the value of the endDate property.
     * 
     * @return
     *     possible object is
     *     {@link TSDateTime }
     *     
     */
    public TSDateTime getEndDate() {
        return endDate;
    }

    /**
     * Sets the value of the endDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link TSDateTime }
     *     
     */
    public void setEndDate(TSDateTime value) {
        this.endDate = value;
    }

    /**
     * Gets the value of the grantNumber property.
     * 
     * @return
     *     possible object is
     *     {@link ST }
     *     
     */
    public ST getGrantNumber() {
        return grantNumber;
    }

    /**
     * Sets the value of the grantNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link ST }
     *     
     */
    public void setGrantNumber(ST value) {
        this.grantNumber = value;
    }

    /**
     * Gets the value of the diseaseSite property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the diseaseSite property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDiseaseSite().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PerformedDiagnosis }
     * 
     * 
     */
    public List<PerformedDiagnosis> getDiseaseSite() {
        if (diseaseSite == null) {
            diseaseSite = new ArrayList<PerformedDiagnosis>();
        }
        return this.diseaseSite;
    }

}
