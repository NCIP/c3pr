
package edu.duke.cabig.c3pr.webservice.subjectregistration;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import edu.duke.cabig.c3pr.webservice.iso21090.ANY;
import edu.duke.cabig.c3pr.webservice.iso21090.CD;


/**
 * <p>Java class for PerformedObservationResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PerformedObservationResult">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="result" type="{uri:iso.org:21090}ANY" minOccurs="0"/>
 *         &lt;element name="targetAnatomicSiteCode" type="{uri:iso.org:21090}CD" minOccurs="0"/>
 *         &lt;element name="targetAnatomicSiteLateralityCode" type="{uri:iso.org:21090}CD" minOccurs="0"/>
 *         &lt;element name="typeCode" type="{uri:iso.org:21090}CD"/>
 *         &lt;element name="startificationCriterion" type="{http://enterpriseservices.nci.nih.gov/SubjectRegistration}DefinedStratificationCriterion" minOccurs="0"/>
 *         &lt;element name="startificationCriterionPermissibleResult" type="{http://enterpriseservices.nci.nih.gov/SubjectRegistration}DefinedStratificationCriterionPermissibleResult" minOccurs="0"/>
 *         &lt;element name="eligibilityCriterion" type="{http://enterpriseservices.nci.nih.gov/SubjectRegistration}DefinedEligibilityCriterion" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PerformedObservationResult", propOrder = {
    "result",
    "targetAnatomicSiteCode",
    "targetAnatomicSiteLateralityCode",
    "typeCode",
    "startificationCriterion",
    "startificationCriterionPermissibleResult",
    "eligibilityCriterion"
})
@XmlSeeAlso({
    PerformedDiagnosis.class
})
public class PerformedObservationResult {

    protected ANY result;
    protected CD targetAnatomicSiteCode;
    protected CD targetAnatomicSiteLateralityCode;
    @XmlElement(required = true)
    protected CD typeCode;
    protected DefinedStratificationCriterion startificationCriterion;
    protected DefinedStratificationCriterionPermissibleResult startificationCriterionPermissibleResult;
    protected DefinedEligibilityCriterion eligibilityCriterion;

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
     * Gets the value of the targetAnatomicSiteCode property.
     * 
     * @return
     *     possible object is
     *     {@link CD }
     *     
     */
    public CD getTargetAnatomicSiteCode() {
        return targetAnatomicSiteCode;
    }

    /**
     * Sets the value of the targetAnatomicSiteCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link CD }
     *     
     */
    public void setTargetAnatomicSiteCode(CD value) {
        this.targetAnatomicSiteCode = value;
    }

    /**
     * Gets the value of the targetAnatomicSiteLateralityCode property.
     * 
     * @return
     *     possible object is
     *     {@link CD }
     *     
     */
    public CD getTargetAnatomicSiteLateralityCode() {
        return targetAnatomicSiteLateralityCode;
    }

    /**
     * Sets the value of the targetAnatomicSiteLateralityCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link CD }
     *     
     */
    public void setTargetAnatomicSiteLateralityCode(CD value) {
        this.targetAnatomicSiteLateralityCode = value;
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
     * Gets the value of the startificationCriterion property.
     * 
     * @return
     *     possible object is
     *     {@link DefinedStratificationCriterion }
     *     
     */
    public DefinedStratificationCriterion getStartificationCriterion() {
        return startificationCriterion;
    }

    /**
     * Sets the value of the startificationCriterion property.
     * 
     * @param value
     *     allowed object is
     *     {@link DefinedStratificationCriterion }
     *     
     */
    public void setStartificationCriterion(DefinedStratificationCriterion value) {
        this.startificationCriterion = value;
    }

    /**
     * Gets the value of the startificationCriterionPermissibleResult property.
     * 
     * @return
     *     possible object is
     *     {@link DefinedStratificationCriterionPermissibleResult }
     *     
     */
    public DefinedStratificationCriterionPermissibleResult getStartificationCriterionPermissibleResult() {
        return startificationCriterionPermissibleResult;
    }

    /**
     * Sets the value of the startificationCriterionPermissibleResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link DefinedStratificationCriterionPermissibleResult }
     *     
     */
    public void setStartificationCriterionPermissibleResult(DefinedStratificationCriterionPermissibleResult value) {
        this.startificationCriterionPermissibleResult = value;
    }

    /**
     * Gets the value of the eligibilityCriterion property.
     * 
     * @return
     *     possible object is
     *     {@link DefinedEligibilityCriterion }
     *     
     */
    public DefinedEligibilityCriterion getEligibilityCriterion() {
        return eligibilityCriterion;
    }

    /**
     * Sets the value of the eligibilityCriterion property.
     * 
     * @param value
     *     allowed object is
     *     {@link DefinedEligibilityCriterion }
     *     
     */
    public void setEligibilityCriterion(DefinedEligibilityCriterion value) {
        this.eligibilityCriterion = value;
    }

}
