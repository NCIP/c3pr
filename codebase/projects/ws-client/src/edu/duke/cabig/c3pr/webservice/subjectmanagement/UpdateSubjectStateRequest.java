
package edu.duke.cabig.c3pr.webservice.subjectmanagement;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import edu.duke.cabig.c3pr.webservice.common.BiologicEntityIdentifier;
import edu.duke.cabig.c3pr.webservice.iso21090.ST;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="biologicEntityIdentifier" type="{http://enterpriseservices.nci.nih.gov/Common}BiologicEntityIdentifier"/>
 *         &lt;element name="newState" type="{uri:iso.org:21090}ST"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "biologicEntityIdentifier",
    "newState"
})
@XmlRootElement(name = "UpdateSubjectStateRequest")
public class UpdateSubjectStateRequest {

    @XmlElement(required = true)
    protected BiologicEntityIdentifier biologicEntityIdentifier;
    @XmlElement(required = true)
    protected ST newState;

    /**
     * Gets the value of the biologicEntityIdentifier property.
     * 
     * @return
     *     possible object is
     *     {@link BiologicEntityIdentifier }
     *     
     */
    public BiologicEntityIdentifier getBiologicEntityIdentifier() {
        return biologicEntityIdentifier;
    }

    /**
     * Sets the value of the biologicEntityIdentifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link BiologicEntityIdentifier }
     *     
     */
    public void setBiologicEntityIdentifier(BiologicEntityIdentifier value) {
        this.biologicEntityIdentifier = value;
    }

    /**
     * Gets the value of the newState property.
     * 
     * @return
     *     possible object is
     *     {@link ST }
     *     
     */
    public ST getNewState() {
        return newState;
    }

    /**
     * Sets the value of the newState property.
     * 
     * @param value
     *     allowed object is
     *     {@link ST }
     *     
     */
    public void setNewState(ST value) {
        this.newState = value;
    }

}
