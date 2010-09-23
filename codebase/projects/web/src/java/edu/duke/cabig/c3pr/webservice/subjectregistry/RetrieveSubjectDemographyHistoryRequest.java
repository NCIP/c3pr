
package edu.duke.cabig.c3pr.webservice.subjectregistry;

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
 *         &lt;element name="patientIdentifier" type="{http://enterpriseservices.nci.nih.gov/SubjectRegistry}BiologicEntityIdentifier"/>
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
    "patientIdentifier"
})
@XmlRootElement(name = "RetrieveSubjectDemographyHistoryRequest", namespace = "http://enterpriseservices.nci.nih.gov/SubjectRegistryService")
public class RetrieveSubjectDemographyHistoryRequest {

    @XmlElement(namespace = "", required = true)
    protected BiologicEntityIdentifier patientIdentifier;

    /**
     * Gets the value of the patientIdentifier property.
     * 
     * @return
     *     possible object is
     *     {@link BiologicEntityIdentifier }
     *     
     */
    public BiologicEntityIdentifier getPatientIdentifier() {
        return patientIdentifier;
    }

    /**
     * Sets the value of the patientIdentifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link BiologicEntityIdentifier }
     *     
     */
    public void setPatientIdentifier(BiologicEntityIdentifier value) {
        this.patientIdentifier = value;
    }

}
