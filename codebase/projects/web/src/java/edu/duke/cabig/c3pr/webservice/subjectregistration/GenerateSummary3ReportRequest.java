
package edu.duke.cabig.c3pr.webservice.subjectregistration;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import edu.duke.cabig.c3pr.webservice.iso21090.ST;
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
 *         &lt;element name="siteIdentifier" type="{http://enterpriseservices.nci.nih.gov/SubjectRegistration}OrganizationIdentifier"/>
 *         &lt;element name="startDate" type="{uri:iso.org:21090}TS.DateTime"/>
 *         &lt;element name="endDate" type="{uri:iso.org:21090}TS.DateTime" minOccurs="0"/>
 *         &lt;element name="grantNumber" type="{uri:iso.org:21090}ST" minOccurs="0"/>
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
    "siteIdentifier",
    "startDate",
    "endDate",
    "grantNumber"
})
@XmlRootElement(name = "GenerateSummary3ReportRequest", namespace = "http://enterpriseservices.nci.nih.gov/SubjectRegistrationService")
public class GenerateSummary3ReportRequest {

    @XmlElement(namespace = "", required = true)
    protected OrganizationIdentifier siteIdentifier;
    @XmlElement(namespace = "", required = true)
    protected TSDateTime startDate;
    @XmlElement(namespace = "")
    protected TSDateTime endDate;
    @XmlElement(namespace = "")
    protected ST grantNumber;

    /**
     * Gets the value of the siteIdentifier property.
     * 
     * @return
     *     possible object is
     *     {@link OrganizationIdentifier }
     *     
     */
    public OrganizationIdentifier getSiteIdentifier() {
        return siteIdentifier;
    }

    /**
     * Sets the value of the siteIdentifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrganizationIdentifier }
     *     
     */
    public void setSiteIdentifier(OrganizationIdentifier value) {
        this.siteIdentifier = value;
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

}
