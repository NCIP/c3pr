
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
 *         &lt;element name="summary3Report" type="{http://enterpriseservices.nci.nih.gov/SubjectRegistration}Summary3Report"/>
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
    "summary3Report"
})
@XmlRootElement(name = "GenerateSummary3ReportResponse", namespace = "http://enterpriseservices.nci.nih.gov/SubjectRegistrationService")
public class GenerateSummary3ReportResponse {

    @XmlElement(namespace = "", required = true)
    protected Summary3Report summary3Report;

    /**
     * Gets the value of the summary3Report property.
     * 
     * @return
     *     possible object is
     *     {@link Summary3Report }
     *     
     */
    public Summary3Report getSummary3Report() {
        return summary3Report;
    }

    /**
     * Sets the value of the summary3Report property.
     * 
     * @param value
     *     allowed object is
     *     {@link Summary3Report }
     *     
     */
    public void setSummary3Report(Summary3Report value) {
        this.summary3Report = value;
    }

}
