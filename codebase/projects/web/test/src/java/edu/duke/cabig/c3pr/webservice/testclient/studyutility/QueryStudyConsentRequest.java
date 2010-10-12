
package edu.duke.cabig.c3pr.webservice.testclient.studyutility;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import edu.duke.cabig.c3pr.webservice.testclient.common.Consent;
import edu.duke.cabig.c3pr.webservice.testclient.common.DocumentIdentifier;


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
 *         &lt;element name="studyIdentifier" type="{http://enterpriseservices.nci.nih.gov/Common}DocumentIdentifier"/>
 *         &lt;element name="consent" type="{http://enterpriseservices.nci.nih.gov/Common}Consent" minOccurs="0"/>
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
    "studyIdentifier",
    "consent"
})
@XmlRootElement(name = "QueryStudyConsentRequest")
public class QueryStudyConsentRequest {

    @XmlElement(required = true)
    protected DocumentIdentifier studyIdentifier;
    protected Consent consent;

    /**
     * Gets the value of the studyIdentifier property.
     * 
     * @return
     *     possible object is
     *     {@link DocumentIdentifier }
     *     
     */
    public DocumentIdentifier getStudyIdentifier() {
        return studyIdentifier;
    }

    /**
     * Sets the value of the studyIdentifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link DocumentIdentifier }
     *     
     */
    public void setStudyIdentifier(DocumentIdentifier value) {
        this.studyIdentifier = value;
    }

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
