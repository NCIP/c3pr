
package edu.duke.cabig.c3pr.webservice.studyutility;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import edu.duke.cabig.c3pr.webservice.common.Consent;


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
 *         &lt;element name="consent" type="{http://enterpriseservices.nci.nih.gov/Common}Consent"/>
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
    "consent"
})
@XmlRootElement(name = "UpdateStudyConsentResponse")
public class UpdateStudyConsentResponse {

    @XmlElement(required = true)
    protected Consent consent;

    /**
     * Gets the value of the consent property.
     * 
     * @return
     *     possible object is
     *     {@link Consent }
     *     
     */
    public Consent getConsent() {
        return consent;
    }

    /**
     * Sets the value of the consent property.
     * 
     * @param value
     *     allowed object is
     *     {@link Consent }
     *     
     */
    public void setConsent(Consent value) {
        this.consent = value;
    }

}
