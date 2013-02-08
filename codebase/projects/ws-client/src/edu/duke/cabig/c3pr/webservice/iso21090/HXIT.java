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
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for HXIT complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="HXIT">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="validTimeLow" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="validTimeHigh" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="controlActRoot" type="{uri:iso.org:21090}Uid" />
 *       &lt;attribute name="controlActExtension" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HXIT")
@XmlSeeAlso({
    ANY.class
})
public abstract class HXIT {

    @XmlAttribute
    protected String validTimeLow;
    @XmlAttribute
    protected String validTimeHigh;
    @XmlAttribute
    protected String controlActRoot;
    @XmlAttribute
    protected String controlActExtension;

    /**
     * Gets the value of the validTimeLow property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValidTimeLow() {
        return validTimeLow;
    }

    /**
     * Sets the value of the validTimeLow property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValidTimeLow(String value) {
        this.validTimeLow = value;
    }

    /**
     * Gets the value of the validTimeHigh property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValidTimeHigh() {
        return validTimeHigh;
    }

    /**
     * Sets the value of the validTimeHigh property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValidTimeHigh(String value) {
        this.validTimeHigh = value;
    }

    /**
     * Gets the value of the controlActRoot property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getControlActRoot() {
        return controlActRoot;
    }

    /**
     * Sets the value of the controlActRoot property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setControlActRoot(String value) {
        this.controlActRoot = value;
    }

    /**
     * Gets the value of the controlActExtension property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getControlActExtension() {
        return controlActExtension;
    }

    /**
     * Sets the value of the controlActExtension property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setControlActExtension(String value) {
        this.controlActExtension = value;
    }

}
