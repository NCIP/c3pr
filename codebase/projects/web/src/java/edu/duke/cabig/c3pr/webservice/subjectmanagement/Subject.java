
package edu.duke.cabig.c3pr.webservice.subjectmanagement;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * An entity of interest, either biological or otherwise.
 * 
 *                 For example, a human being who might be of interest because they are
 *                 on a study, a sheep who might have experienced an adverse event, or
 *                 a pacemaker that failed.
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
 *         &lt;element name="performing" type="{http://enterpriseservices.nci.nih.gov}BiologicEntity"/>
 *         &lt;element name="identifying" type="{http://enterpriseservices.nci.nih.gov}SubjectIdentifier" maxOccurs="unbounded" minOccurs="0"/>
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
    "performing",
    "identifying"
})
public class Subject {

    @XmlElement(required = true)
    protected BiologicEntity performing;
    protected List<SubjectIdentifier> identifying;

    /**
     * Gets the value of the performing property.
     * 
     * @return
     *     possible object is
     *     {@link BiologicEntity }
     *     
     */
    public BiologicEntity getPerforming() {
        return performing;
    }

    /**
     * Sets the value of the performing property.
     * 
     * @param value
     *     allowed object is
     *     {@link BiologicEntity }
     *     
     */
    public void setPerforming(BiologicEntity value) {
        this.performing = value;
    }

    /**
     * Gets the value of the identifying property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the identifying property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIdentifying().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SubjectIdentifier }
     * 
     * 
     */
    public List<SubjectIdentifier> getIdentifying() {
        if (identifying == null) {
            identifying = new ArrayList<SubjectIdentifier>();
        }
        return this.identifying;
    }

}
