
package edu.duke.cabig.c3pr.webservice.testclient.common;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import edu.duke.cabig.c3pr.webservice.testclient.iso21090.BL;
import edu.duke.cabig.c3pr.webservice.testclient.iso21090.CD;
import edu.duke.cabig.c3pr.webservice.testclient.iso21090.ST;


/**
 * <p>Java class for Reason complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Reason">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="code" type="{uri:iso.org:21090}CD"/>
 *         &lt;element name="description" type="{uri:iso.org:21090}ST"/>
 *         &lt;element name="primaryIndicator" type="{uri:iso.org:21090}BL"/>
 *         &lt;element name="primaryReason" type="{http://enterpriseservices.nci.nih.gov/Common}Reason" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Reason", propOrder = {
    "code",
    "description",
    "primaryIndicator",
    "primaryReason"
})
@XmlSeeAlso({
    RegistryStatusReason.class
})
public class Reason {

    @XmlElement(required = true)
    protected CD code;
    @XmlElement(required = true)
    protected ST description;
    @XmlElement(required = true)
    protected BL primaryIndicator;
    protected Reason primaryReason;

    /**
     * Gets the value of the code property.
     * 
     * @return
     *     possible object is
     *     {@link CD }
     *     
     */
    public CD getCode() {
        return code;
    }

    /**
     * Sets the value of the code property.
     * 
     * @param value
     *     allowed object is
     *     {@link CD }
     *     
     */
    public void setCode(CD value) {
        this.code = value;
    }

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
     * Gets the value of the primaryIndicator property.
     * 
     * @return
     *     possible object is
     *     {@link BL }
     *     
     */
    public BL getPrimaryIndicator() {
        return primaryIndicator;
    }

    /**
     * Sets the value of the primaryIndicator property.
     * 
     * @param value
     *     allowed object is
     *     {@link BL }
     *     
     */
    public void setPrimaryIndicator(BL value) {
        this.primaryIndicator = value;
    }

    /**
     * Gets the value of the primaryReason property.
     * 
     * @return
     *     possible object is
     *     {@link Reason }
     *     
     */
    public Reason getPrimaryReason() {
        return primaryReason;
    }

    /**
     * Sets the value of the primaryReason property.
     * 
     * @param value
     *     allowed object is
     *     {@link Reason }
     *     
     */
    public void setPrimaryReason(Reason value) {
        this.primaryReason = value;
    }

}
