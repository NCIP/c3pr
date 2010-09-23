
package edu.duke.cabig.c3pr.webservice.subjectregistration;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import edu.duke.cabig.c3pr.webservice.iso21090.BL;


/**
 * <p>Java class for DefinedEligibilityCriterion complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DefinedEligibilityCriterion">
 *   &lt;complexContent>
 *     &lt;extension base="{http://enterpriseservices.nci.nih.gov/SubjectRegistration}DefinedObservation">
 *       &lt;sequence>
 *         &lt;element name="requiredResponse" type="{uri:iso.org:21090}BL"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DefinedEligibilityCriterion", propOrder = {
    "requiredResponse"
})
@XmlSeeAlso({
    DefinedExcusionCriterion.class,
    DefinedIncusionCriterion.class
})
public class DefinedEligibilityCriterion
    extends DefinedObservation
{

    @XmlElement(required = true)
    protected BL requiredResponse;

    /**
     * Gets the value of the requiredResponse property.
     * 
     * @return
     *     possible object is
     *     {@link BL }
     *     
     */
    public BL getRequiredResponse() {
        return requiredResponse;
    }

    /**
     * Sets the value of the requiredResponse property.
     * 
     * @param value
     *     allowed object is
     *     {@link BL }
     *     
     */
    public void setRequiredResponse(BL value) {
        this.requiredResponse = value;
    }

}
