
package edu.duke.cabig.c3pr.webservice.subjectmanagement;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


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
    "entity"
})
public class Subject {

    @XmlElement(required = true)
    protected BiologicEntity entity;

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

}
