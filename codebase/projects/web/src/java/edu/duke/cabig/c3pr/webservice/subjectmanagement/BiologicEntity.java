
package edu.duke.cabig.c3pr.webservice.subjectmanagement;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * Any individual living (or previously living) being.
 * 
 *                 For example, animal, human being.
 *             
 * 
 * <p>Java class for BiologicEntity complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BiologicEntity">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="administrativeGenderCode" type="{uri:iso.org:21090}CD"/>
 *         &lt;element name="birthDate" type="{uri:iso.org:21090}TS.DateTime"/>
 *         &lt;element name="deathDate" type="{uri:iso.org:21090}TS.DateTime"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BiologicEntity", namespace = "http://enterpriseservices.nci.nih.gov", propOrder = {
    "administrativeGenderCode",
    "birthDate",
    "deathDate"
})
@XmlSeeAlso({
    Person.class
})
public abstract class BiologicEntity {

    @XmlElement(required = true)
    protected CD administrativeGenderCode;
    @XmlElement(required = true)
    protected TSDateTime birthDate;
    @XmlElement(required = true)
    protected TSDateTime deathDate;

    /**
     * Gets the value of the administrativeGenderCode property.
     * 
     * @return
     *     possible object is
     *     {@link CD }
     *     
     */
    public CD getAdministrativeGenderCode() {
        return administrativeGenderCode;
    }

    /**
     * Sets the value of the administrativeGenderCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link CD }
     *     
     */
    public void setAdministrativeGenderCode(CD value) {
        this.administrativeGenderCode = value;
    }

    /**
     * Gets the value of the birthDate property.
     * 
     * @return
     *     possible object is
     *     {@link TSDateTime }
     *     
     */
    public TSDateTime getBirthDate() {
        return birthDate;
    }

    /**
     * Sets the value of the birthDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link TSDateTime }
     *     
     */
    public void setBirthDate(TSDateTime value) {
        this.birthDate = value;
    }

    /**
     * Gets the value of the deathDate property.
     * 
     * @return
     *     possible object is
     *     {@link TSDateTime }
     *     
     */
    public TSDateTime getDeathDate() {
        return deathDate;
    }

    /**
     * Sets the value of the deathDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link TSDateTime }
     *     
     */
    public void setDeathDate(TSDateTime value) {
        this.deathDate = value;
    }

}
