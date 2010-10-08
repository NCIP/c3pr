
package edu.duke.cabig.c3pr.webservice.testclient.iso21090;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for QSP_INT.Positive complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="QSP_INT.Positive">
 *   &lt;complexContent>
 *     &lt;extension base="{uri:iso.org:21090}QSET_INT.Positive">
 *       &lt;sequence>
 *         &lt;element name="first" type="{uri:iso.org:21090}QSET_INT.Positive" minOccurs="0"/>
 *         &lt;element name="second" type="{uri:iso.org:21090}QSET_INT.Positive" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "QSP_INT.Positive", propOrder = {
    "first",
    "second"
})
public class QSPINTPositive
    extends QSETINTPositive
{

    protected QSETINTPositive first;
    protected QSETINTPositive second;

    /**
     * Gets the value of the first property.
     * 
     * @return
     *     possible object is
     *     {@link QSETINTPositive }
     *     
     */
    public QSETINTPositive getFirst() {
        return first;
    }

    /**
     * Sets the value of the first property.
     * 
     * @param value
     *     allowed object is
     *     {@link QSETINTPositive }
     *     
     */
    public void setFirst(QSETINTPositive value) {
        this.first = value;
    }

    /**
     * Gets the value of the second property.
     * 
     * @return
     *     possible object is
     *     {@link QSETINTPositive }
     *     
     */
    public QSETINTPositive getSecond() {
        return second;
    }

    /**
     * Sets the value of the second property.
     * 
     * @param value
     *     allowed object is
     *     {@link QSETINTPositive }
     *     
     */
    public void setSecond(QSETINTPositive value) {
        this.second = value;
    }

}
