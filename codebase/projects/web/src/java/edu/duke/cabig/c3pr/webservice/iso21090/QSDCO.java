
package edu.duke.cabig.c3pr.webservice.iso21090;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for QSD_CO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="QSD_CO">
 *   &lt;complexContent>
 *     &lt;extension base="{uri:iso.org:21090}QSET_CO">
 *       &lt;sequence>
 *         &lt;element name="first" type="{uri:iso.org:21090}QSET_CO" minOccurs="0"/>
 *         &lt;element name="second" type="{uri:iso.org:21090}QSET_CO" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "QSD_CO", propOrder = {
    "first",
    "second"
})
public class QSDCO
    extends QSETCO
{

    protected QSETCO first;
    protected QSETCO second;

    /**
     * Gets the value of the first property.
     * 
     * @return
     *     possible object is
     *     {@link QSETCO }
     *     
     */
    public QSETCO getFirst() {
        return first;
    }

    /**
     * Sets the value of the first property.
     * 
     * @param value
     *     allowed object is
     *     {@link QSETCO }
     *     
     */
    public void setFirst(QSETCO value) {
        this.first = value;
    }

    /**
     * Gets the value of the second property.
     * 
     * @return
     *     possible object is
     *     {@link QSETCO }
     *     
     */
    public QSETCO getSecond() {
        return second;
    }

    /**
     * Sets the value of the second property.
     * 
     * @param value
     *     allowed object is
     *     {@link QSETCO }
     *     
     */
    public void setSecond(QSETCO value) {
        this.second = value;
    }

}
