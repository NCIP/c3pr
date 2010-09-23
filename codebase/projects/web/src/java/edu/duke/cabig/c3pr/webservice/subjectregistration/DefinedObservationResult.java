
package edu.duke.cabig.c3pr.webservice.subjectregistration;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import edu.duke.cabig.c3pr.webservice.iso21090.ANY;
import edu.duke.cabig.c3pr.webservice.iso21090.CD;


/**
 * <p>Java class for DefinedObservationResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DefinedObservationResult">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="result" type="{uri:iso.org:21090}ANY"/>
 *         &lt;element name="typeCode" type="{uri:iso.org:21090}CD"/>
 *         &lt;element name="definedObservation" type="{http://enterpriseservices.nci.nih.gov/SubjectRegistration}DefinedObservation" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DefinedObservationResult", propOrder = {
    "result",
    "typeCode",
    "definedObservation"
})
@XmlSeeAlso({
    DefinedStratificationCriterionPermissibleResult.class
})
public class DefinedObservationResult {

    @XmlElement(required = true)
    protected ANY result;
    @XmlElement(required = true)
    protected CD typeCode;
    protected DefinedObservation definedObservation;

    /**
     * Gets the value of the result property.
     * 
     * @return
     *     possible object is
     *     {@link ANY }
     *     
     */
    public ANY getResult() {
        return result;
    }

    /**
     * Sets the value of the result property.
     * 
     * @param value
     *     allowed object is
     *     {@link ANY }
     *     
     */
    public void setResult(ANY value) {
        this.result = value;
    }

    /**
     * Gets the value of the typeCode property.
     * 
     * @return
     *     possible object is
     *     {@link CD }
     *     
     */
    public CD getTypeCode() {
        return typeCode;
    }

    /**
     * Sets the value of the typeCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link CD }
     *     
     */
    public void setTypeCode(CD value) {
        this.typeCode = value;
    }

    /**
     * Gets the value of the definedObservation property.
     * 
     * @return
     *     possible object is
     *     {@link DefinedObservation }
     *     
     */
    public DefinedObservation getDefinedObservation() {
        return definedObservation;
    }

    /**
     * Sets the value of the definedObservation property.
     * 
     * @param value
     *     allowed object is
     *     {@link DefinedObservation }
     *     
     */
    public void setDefinedObservation(DefinedObservation value) {
        this.definedObservation = value;
    }

}
