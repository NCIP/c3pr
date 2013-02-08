/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.webservice.iso21090;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for QSD_INT.NonNeg complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="QSD_INT.NonNeg">
 *   &lt;complexContent>
 *     &lt;extension base="{uri:iso.org:21090}QSET_INT.NonNeg">
 *       &lt;sequence>
 *         &lt;element name="first" type="{uri:iso.org:21090}QSET_INT.NonNeg" minOccurs="0"/>
 *         &lt;element name="second" type="{uri:iso.org:21090}QSET_INT.NonNeg" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "QSD_INT.NonNeg", propOrder = {
    "first",
    "second"
})
public class QSDINTNonNeg
    extends QSETINTNonNeg
{

    protected QSETINTNonNeg first;
    protected QSETINTNonNeg second;

    /**
     * Gets the value of the first property.
     * 
     * @return
     *     possible object is
     *     {@link QSETINTNonNeg }
     *     
     */
    public QSETINTNonNeg getFirst() {
        return first;
    }

    /**
     * Sets the value of the first property.
     * 
     * @param value
     *     allowed object is
     *     {@link QSETINTNonNeg }
     *     
     */
    public void setFirst(QSETINTNonNeg value) {
        this.first = value;
    }

    /**
     * Gets the value of the second property.
     * 
     * @return
     *     possible object is
     *     {@link QSETINTNonNeg }
     *     
     */
    public QSETINTNonNeg getSecond() {
        return second;
    }

    /**
     * Sets the value of the second property.
     * 
     * @param value
     *     allowed object is
     *     {@link QSETINTNonNeg }
     *     
     */
    public void setSecond(QSETINTNonNeg value) {
        this.second = value;
    }

}
