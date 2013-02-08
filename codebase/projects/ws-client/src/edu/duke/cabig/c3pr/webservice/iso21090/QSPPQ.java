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
 * <p>Java class for QSP_PQ complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="QSP_PQ">
 *   &lt;complexContent>
 *     &lt;extension base="{uri:iso.org:21090}QSET_PQ">
 *       &lt;sequence>
 *         &lt;element name="first" type="{uri:iso.org:21090}QSET_PQ" minOccurs="0"/>
 *         &lt;element name="second" type="{uri:iso.org:21090}QSET_PQ" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "QSP_PQ", propOrder = {
    "first",
    "second"
})
public class QSPPQ
    extends QSETPQ
{

    protected QSETPQ first;
    protected QSETPQ second;

    /**
     * Gets the value of the first property.
     * 
     * @return
     *     possible object is
     *     {@link QSETPQ }
     *     
     */
    public QSETPQ getFirst() {
        return first;
    }

    /**
     * Sets the value of the first property.
     * 
     * @param value
     *     allowed object is
     *     {@link QSETPQ }
     *     
     */
    public void setFirst(QSETPQ value) {
        this.first = value;
    }

    /**
     * Gets the value of the second property.
     * 
     * @return
     *     possible object is
     *     {@link QSETPQ }
     *     
     */
    public QSETPQ getSecond() {
        return second;
    }

    /**
     * Sets the value of the second property.
     * 
     * @param value
     *     allowed object is
     *     {@link QSETPQ }
     *     
     */
    public void setSecond(QSETPQ value) {
        this.second = value;
    }

}
