
package edu.duke.cabig.c3pr.webservice.subjectregistry;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import edu.duke.cabig.c3pr.webservice.iso21090.CD;
import edu.duke.cabig.c3pr.webservice.iso21090.DSETST;
import edu.duke.cabig.c3pr.webservice.iso21090.ST;


/**
 * <p>Java class for AdvanceSearchCriterionParameter complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AdvanceSearchCriterionParameter">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="objectName" type="{uri:iso.org:21090}ST"/>
 *         &lt;element name="attributeName" type="{uri:iso.org:21090}ST"/>
 *         &lt;element name="values" type="{uri:iso.org:21090}DSET_ST"/>
 *         &lt;element name="predicate" type="{uri:iso.org:21090}CD"/>
 *         &lt;element name="objectContextName" type="{uri:iso.org:21090}ST" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AdvanceSearchCriterionParameter", propOrder = {
    "objectName",
    "attributeName",
    "values",
    "predicate",
    "objectContextName"
})
public class AdvanceSearchCriterionParameter {

    @XmlElement(required = true)
    protected ST objectName;
    @XmlElement(required = true)
    protected ST attributeName;
    @XmlElement(required = true)
    protected DSETST values;
    @XmlElement(required = true)
    protected CD predicate;
    protected ST objectContextName;

    /**
     * Gets the value of the objectName property.
     * 
     * @return
     *     possible object is
     *     {@link ST }
     *     
     */
    public ST getObjectName() {
        return objectName;
    }

    /**
     * Sets the value of the objectName property.
     * 
     * @param value
     *     allowed object is
     *     {@link ST }
     *     
     */
    public void setObjectName(ST value) {
        this.objectName = value;
    }

    /**
     * Gets the value of the attributeName property.
     * 
     * @return
     *     possible object is
     *     {@link ST }
     *     
     */
    public ST getAttributeName() {
        return attributeName;
    }

    /**
     * Sets the value of the attributeName property.
     * 
     * @param value
     *     allowed object is
     *     {@link ST }
     *     
     */
    public void setAttributeName(ST value) {
        this.attributeName = value;
    }

    /**
     * Gets the value of the values property.
     * 
     * @return
     *     possible object is
     *     {@link DSETST }
     *     
     */
    public DSETST getValues() {
        return values;
    }

    /**
     * Sets the value of the values property.
     * 
     * @param value
     *     allowed object is
     *     {@link DSETST }
     *     
     */
    public void setValues(DSETST value) {
        this.values = value;
    }

    /**
     * Gets the value of the predicate property.
     * 
     * @return
     *     possible object is
     *     {@link CD }
     *     
     */
    public CD getPredicate() {
        return predicate;
    }

    /**
     * Sets the value of the predicate property.
     * 
     * @param value
     *     allowed object is
     *     {@link CD }
     *     
     */
    public void setPredicate(CD value) {
        this.predicate = value;
    }

    /**
     * Gets the value of the objectContextName property.
     * 
     * @return
     *     possible object is
     *     {@link ST }
     *     
     */
    public ST getObjectContextName() {
        return objectContextName;
    }

    /**
     * Sets the value of the objectContextName property.
     * 
     * @param value
     *     allowed object is
     *     {@link ST }
     *     
     */
    public void setObjectContextName(ST value) {
        this.objectContextName = value;
    }

}
