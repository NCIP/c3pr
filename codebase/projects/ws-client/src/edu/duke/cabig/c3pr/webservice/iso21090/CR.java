/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.webservice.iso21090;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CR complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CR">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="name" type="{uri:iso.org:21090}CD.CV" minOccurs="0"/>
 *         &lt;element name="value" type="{uri:iso.org:21090}CD" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="inverted" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CR", propOrder = {
    "name",
    "value"
})
public class CR {

    protected CDCV name;
    protected CD value;
    @XmlAttribute
    protected Boolean inverted;

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link CDCV }
     *     
     */
    public CDCV getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link CDCV }
     *     
     */
    public void setName(CDCV value) {
        this.name = value;
    }

    /**
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link CD }
     *     
     */
    public CD getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link CD }
     *     
     */
    public void setValue(CD value) {
        this.value = value;
    }

    /**
     * Gets the value of the inverted property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isInverted() {
        if (inverted == null) {
            return false;
        } else {
            return inverted;
        }
    }

    /**
     * Sets the value of the inverted property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setInverted(Boolean value) {
        this.inverted = value;
    }

}
