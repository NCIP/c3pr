
package edu.duke.cabig.c3pr.webservice.subjectregistration;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import edu.duke.cabig.c3pr.webservice.iso21090.CD;
import edu.duke.cabig.c3pr.webservice.iso21090.INTPositive;
import edu.duke.cabig.c3pr.webservice.iso21090.ST;


/**
 * <p>Java class for Epoch complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Epoch">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="description" type="{uri:iso.org:21090}ST"/>
 *         &lt;element name="name" type="{uri:iso.org:21090}ST"/>
 *         &lt;element name="sequenceNumber" type="{uri:iso.org:21090}INT.Positive"/>
 *         &lt;element name="typeCode" type="{uri:iso.org:21090}CD"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Epoch", propOrder = {
    "description",
    "name",
    "sequenceNumber",
    "typeCode"
})
public class Epoch {

    @XmlElement(required = true)
    protected ST description;
    @XmlElement(required = true)
    protected ST name;
    @XmlElement(required = true)
    protected INTPositive sequenceNumber;
    @XmlElement(required = true)
    protected CD typeCode;

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link ST }
     *     
     */
    public ST getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link ST }
     *     
     */
    public void setDescription(ST value) {
        this.description = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link ST }
     *     
     */
    public ST getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link ST }
     *     
     */
    public void setName(ST value) {
        this.name = value;
    }

    /**
     * Gets the value of the sequenceNumber property.
     * 
     * @return
     *     possible object is
     *     {@link INTPositive }
     *     
     */
    public INTPositive getSequenceNumber() {
        return sequenceNumber;
    }

    /**
     * Sets the value of the sequenceNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link INTPositive }
     *     
     */
    public void setSequenceNumber(INTPositive value) {
        this.sequenceNumber = value;
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

}
