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
 * <p>Java class for QSD_TS.Birth complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="QSD_TS.Birth">
 *   &lt;complexContent>
 *     &lt;extension base="{uri:iso.org:21090}QSET_TS.Birth">
 *       &lt;sequence>
 *         &lt;element name="first" type="{uri:iso.org:21090}QSET_TS.Birth" minOccurs="0"/>
 *         &lt;element name="second" type="{uri:iso.org:21090}QSET_TS.Birth" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "QSD_TS.Birth", propOrder = {
    "first",
    "second"
})
public class QSDTSBirth
    extends QSETTSBirth
{

    protected QSETTSBirth first;
    protected QSETTSBirth second;

    /**
     * Gets the value of the first property.
     * 
     * @return
     *     possible object is
     *     {@link QSETTSBirth }
     *     
     */
    public QSETTSBirth getFirst() {
        return first;
    }

    /**
     * Sets the value of the first property.
     * 
     * @param value
     *     allowed object is
     *     {@link QSETTSBirth }
     *     
     */
    public void setFirst(QSETTSBirth value) {
        this.first = value;
    }

    /**
     * Gets the value of the second property.
     * 
     * @return
     *     possible object is
     *     {@link QSETTSBirth }
     *     
     */
    public QSETTSBirth getSecond() {
        return second;
    }

    /**
     * Sets the value of the second property.
     * 
     * @param value
     *     allowed object is
     *     {@link QSETTSBirth }
     *     
     */
    public void setSecond(QSETTSBirth value) {
        this.second = value;
    }

}
