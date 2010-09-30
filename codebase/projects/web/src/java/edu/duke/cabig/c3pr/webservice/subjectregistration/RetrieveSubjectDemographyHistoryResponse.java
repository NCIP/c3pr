
package edu.duke.cabig.c3pr.webservice.subjectregistration;

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
 *         &lt;element name="patients" type="{http://enterpriseservices.nci.nih.gov/SubjectRegistration}DSET_Person"/>
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
    "patients"
})
@XmlRootElement(name = "RetrieveSubjectDemographyHistoryResponse", namespace = "http://enterpriseservices.nci.nih.gov/SubjectRegistrationService")
public class RetrieveSubjectDemographyHistoryResponse {

    @XmlElement(namespace = "", required = true)
    protected DSETPerson patients;

    /**
     * Gets the value of the patients property.
     * 
     * @return
     *     possible object is
     *     {@link DSETPerson }
     *     
     */
    public DSETPerson getPatients() {
        return patients;
    }

    /**
     * Sets the value of the patients property.
     * 
     * @param value
     *     allowed object is
     *     {@link DSETPerson }
     *     
     */
    public void setPatients(DSETPerson value) {
        this.patients = value;
    }

}