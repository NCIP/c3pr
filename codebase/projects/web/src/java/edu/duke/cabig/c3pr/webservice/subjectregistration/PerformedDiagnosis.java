
package edu.duke.cabig.c3pr.webservice.subjectregistration;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PerformedDiagnosis complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PerformedDiagnosis">
 *   &lt;complexContent>
 *     &lt;extension base="{http://enterpriseservices.nci.nih.gov/SubjectRegistration}PerformedObservationResult">
 *       &lt;sequence>
 *         &lt;element name="disease" type="{http://enterpriseservices.nci.nih.gov/SubjectRegistration}StudyCondition"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PerformedDiagnosis", propOrder = {
    "disease"
})
public class PerformedDiagnosis
    extends PerformedObservationResult
{

    @XmlElement(required = true)
    protected StudyCondition disease;

    /**
     * Gets the value of the disease property.
     * 
     * @return
     *     possible object is
     *     {@link StudyCondition }
     *     
     */
    public StudyCondition getDisease() {
        return disease;
    }

    /**
     * Sets the value of the disease property.
     * 
     * @param value
     *     allowed object is
     *     {@link StudyCondition }
     *     
     */
    public void setDisease(StudyCondition value) {
        this.disease = value;
    }

}
