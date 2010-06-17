
package edu.duke.cabig.c3pr.webservice.subjectmanagement;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for QSD_MO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="QSD_MO">
 *   &lt;complexContent>
 *     &lt;extension base="{uri:iso.org:21090}QSET_MO">
 *       &lt;sequence>
 *         &lt;element name="first" type="{uri:iso.org:21090}QSET_MO" minOccurs="0"/>
 *         &lt;element name="second" type="{uri:iso.org:21090}QSET_MO" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "QSD_MO", propOrder = {
    "first",
    "second"
})
public class QSDMO
    extends QSETMO
{

    protected QSETMO first;
    protected QSETMO second;

    /**
     * Gets the value of the first property.
     * 
     * @return
     *     possible object is
     *     {@link QSETMO }
     *     
     */
    public QSETMO getFirst() {
        return first;
    }

    /**
     * Sets the value of the first property.
     * 
     * @param value
     *     allowed object is
     *     {@link QSETMO }
     *     
     */
    public void setFirst(QSETMO value) {
        this.first = value;
    }

    /**
     * Gets the value of the second property.
     * 
     * @return
     *     possible object is
     *     {@link QSETMO }
     *     
     */
    public QSETMO getSecond() {
        return second;
    }

    /**
     * Sets the value of the second property.
     * 
     * @param value
     *     allowed object is
     *     {@link QSETMO }
     *     
     */
    public void setSecond(QSETMO value) {
        this.second = value;
    }

}
