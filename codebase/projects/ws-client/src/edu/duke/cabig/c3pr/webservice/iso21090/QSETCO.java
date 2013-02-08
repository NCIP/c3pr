/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.webservice.iso21090;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for QSET_CO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="QSET_CO">
 *   &lt;complexContent>
 *     &lt;extension base="{uri:iso.org:21090}ANY">
 *       &lt;sequence>
 *         &lt;element name="originalText" type="{uri:iso.org:21090}ED.Text" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "QSET_CO", propOrder = {
    "originalText"
})
@XmlSeeAlso({
    QSICO.class,
    QSDCO.class,
    QSUCO.class,
    QSPCO.class,
    QSSCO.class,
    IVLCO.class
})
public abstract class QSETCO
    extends ANY
{

    protected EDText originalText;

    /**
     * Gets the value of the originalText property.
     * 
     * @return
     *     possible object is
     *     {@link EDText }
     *     
     */
    public EDText getOriginalText() {
        return originalText;
    }

    /**
     * Sets the value of the originalText property.
     * 
     * @param value
     *     allowed object is
     *     {@link EDText }
     *     
     */
    public void setOriginalText(EDText value) {
        this.originalText = value;
    }

}
