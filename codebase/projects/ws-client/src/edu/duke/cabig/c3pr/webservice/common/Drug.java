
package edu.duke.cabig.c3pr.webservice.common;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import edu.duke.cabig.c3pr.webservice.iso21090.II;


/**
 * An article other than food intended for use in the diagnosis, cure, mitigation, treatment, or prevention of disease; or intended to affect the structure or any function of the body. 
 * 				
 * 				For example, aspirin.
 * 
 * <p>Java class for Drug complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Drug">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="kitNumber" type="{uri:iso.org:21090}II"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Drug", propOrder = {
    "kitNumber"
})
public class Drug {

    @XmlElement(required = true)
    protected II kitNumber;

    /**
     * Gets the value of the kitNumber property.
     * 
     * @return
     *     possible object is
     *     {@link II }
     *     
     */
    public II getKitNumber() {
        return kitNumber;
    }

    /**
     * Sets the value of the kitNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link II }
     *     
     */
    public void setKitNumber(II value) {
        this.kitNumber = value;
    }

}
