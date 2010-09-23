
package edu.duke.cabig.c3pr.webservice.subjectregistration;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import edu.duke.cabig.c3pr.webservice.iso21090.BL;


/**
 * <p>Java class for SubjectConsentQuestionAnswer complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SubjectConsentQuestionAnswer">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="agreementIndicator" type="{uri:iso.org:21090}BL"/>
 *         &lt;element name="consentQuestion" type="{http://enterpriseservices.nci.nih.gov/SubjectRegistration}ConsentQuestion"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SubjectConsentQuestionAnswer", propOrder = {
    "agreementIndicator",
    "consentQuestion"
})
public class SubjectConsentQuestionAnswer {

    @XmlElement(required = true)
    protected BL agreementIndicator;
    @XmlElement(required = true)
    protected ConsentQuestion consentQuestion;

    /**
     * Gets the value of the agreementIndicator property.
     * 
     * @return
     *     possible object is
     *     {@link BL }
     *     
     */
    public BL getAgreementIndicator() {
        return agreementIndicator;
    }

    /**
     * Sets the value of the agreementIndicator property.
     * 
     * @param value
     *     allowed object is
     *     {@link BL }
     *     
     */
    public void setAgreementIndicator(BL value) {
        this.agreementIndicator = value;
    }

    /**
     * Gets the value of the consentQuestion property.
     * 
     * @return
     *     possible object is
     *     {@link ConsentQuestion }
     *     
     */
    public ConsentQuestion getConsentQuestion() {
        return consentQuestion;
    }

    /**
     * Sets the value of the consentQuestion property.
     * 
     * @param value
     *     allowed object is
     *     {@link ConsentQuestion }
     *     
     */
    public void setConsentQuestion(ConsentQuestion value) {
        this.consentQuestion = value;
    }

}
