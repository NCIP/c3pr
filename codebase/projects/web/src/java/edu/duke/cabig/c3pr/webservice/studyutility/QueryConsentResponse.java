
package edu.duke.cabig.c3pr.webservice.studyutility;

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
 *         &lt;element name="consents" type="{http://enterpriseservices.nci.nih.gov/StudyUtilitySchema}DSET_Consent"/>
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
    "consents"
})
@XmlRootElement(name = "QueryConsentResponse")
public class QueryConsentResponse {

    @XmlElement(required = true)
    protected DSETConsent consents;

    /**
     * Gets the value of the consents property.
     * 
     * @return
     *     possible object is
     *     {@link DSETConsent }
     *     
     */
    public DSETConsent getConsents() {
        return consents;
    }

    /**
     * Sets the value of the consents property.
     * 
     * @param value
     *     allowed object is
     *     {@link DSETConsent }
     *     
     */
    public void setConsents(DSETConsent value) {
        this.consents = value;
    }

}
