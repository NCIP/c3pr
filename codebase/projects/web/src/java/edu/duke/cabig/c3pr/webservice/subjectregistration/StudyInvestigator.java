
package edu.duke.cabig.c3pr.webservice.subjectregistration;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for StudyInvestigator complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="StudyInvestigator">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="healthcareProvider" type="{http://enterpriseservices.nci.nih.gov/SubjectRegistration}HealthcareProvider"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StudyInvestigator", propOrder = {
    "healthcareProvider"
})
public class StudyInvestigator {

    @XmlElement(required = true)
    protected HealthcareProvider healthcareProvider;

    /**
     * Gets the value of the healthcareProvider property.
     * 
     * @return
     *     possible object is
     *     {@link HealthcareProvider }
     *     
     */
    public HealthcareProvider getHealthcareProvider() {
        return healthcareProvider;
    }

    /**
     * Sets the value of the healthcareProvider property.
     * 
     * @param value
     *     allowed object is
     *     {@link HealthcareProvider }
     *     
     */
    public void setHealthcareProvider(HealthcareProvider value) {
        this.healthcareProvider = value;
    }

}
