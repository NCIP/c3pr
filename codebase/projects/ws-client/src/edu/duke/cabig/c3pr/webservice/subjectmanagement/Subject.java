
package edu.duke.cabig.c3pr.webservice.subjectmanagement;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import edu.duke.cabig.c3pr.webservice.iso21090.ST;


/**
 * An entity of interest, either biological or otherwise.
 * 
 * 				For example, a human being who might be of interest because they are
 * 				on a study, a sheep who might have experienced an adverse event, or
 * 				a pacemaker that failed.
 *             
 * 
 * <p>Java class for Subject complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Subject">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="entity" type="{http://enterpriseservices.nci.nih.gov}BiologicEntity"/>
 *         &lt;element name="stateCode" type="{uri:iso.org:21090}ST" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Subject", namespace = "http://enterpriseservices.nci.nih.gov", propOrder = {
    "entity",
    "stateCode"
})
public class Subject {

    @XmlElement(required = true)
    protected BiologicEntity entity;
    protected ST stateCode;

    /**
     * Gets the value of the entity property.
     * 
     * @return
     *     possible object is
     *     {@link BiologicEntity }
     *     
     */
    public BiologicEntity getEntity() {
        return entity;
    }

    /**
     * Sets the value of the entity property.
     * 
     * @param value
     *     allowed object is
     *     {@link BiologicEntity }
     *     
     */
    public void setEntity(BiologicEntity value) {
        this.entity = value;
    }

    /**
     * Gets the value of the stateCode property.
     * 
     * @return
     *     possible object is
     *     {@link ST }
     *     
     */
    public ST getStateCode() {
        return stateCode;
    }

    /**
     * Sets the value of the stateCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link ST }
     *     
     */
    public void setStateCode(ST value) {
        this.stateCode = value;
    }

}
