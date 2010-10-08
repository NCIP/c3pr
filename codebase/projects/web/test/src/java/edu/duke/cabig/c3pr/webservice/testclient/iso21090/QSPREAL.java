
package edu.duke.cabig.c3pr.webservice.testclient.iso21090;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for QSP_REAL complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="QSP_REAL">
 *   &lt;complexContent>
 *     &lt;extension base="{uri:iso.org:21090}QSET_REAL">
 *       &lt;sequence>
 *         &lt;element name="first" type="{uri:iso.org:21090}QSET_REAL" minOccurs="0"/>
 *         &lt;element name="second" type="{uri:iso.org:21090}QSET_REAL" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "QSP_REAL", propOrder = {
    "first",
    "second"
})
public class QSPREAL
    extends QSETREAL
{

    protected QSETREAL first;
    protected QSETREAL second;

    /**
     * Gets the value of the first property.
     * 
     * @return
     *     possible object is
     *     {@link QSETREAL }
     *     
     */
    public QSETREAL getFirst() {
        return first;
    }

    /**
     * Sets the value of the first property.
     * 
     * @param value
     *     allowed object is
     *     {@link QSETREAL }
     *     
     */
    public void setFirst(QSETREAL value) {
        this.first = value;
    }

    /**
     * Gets the value of the second property.
     * 
     * @return
     *     possible object is
     *     {@link QSETREAL }
     *     
     */
    public QSETREAL getSecond() {
        return second;
    }

    /**
     * Sets the value of the second property.
     * 
     * @param value
     *     allowed object is
     *     {@link QSETREAL }
     *     
     */
    public void setSecond(QSETREAL value) {
        this.second = value;
    }

}
