/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.webservice.common;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import edu.duke.cabig.c3pr.webservice.iso21090.CD;
import edu.duke.cabig.c3pr.webservice.iso21090.ST;


/**
 * <p>Java class for DefinedActivity complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DefinedActivity">
 *   &lt;complexContent>
 *     &lt;extension base="{http://enterpriseservices.nci.nih.gov/Common}Activity">
 *       &lt;sequence>
 *         &lt;element name="description" type="{uri:iso.org:21090}ST" minOccurs="0"/>
 *         &lt;element name="nameCode" type="{uri:iso.org:21090}CD" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DefinedActivity", propOrder = {
    "description",
    "nameCode"
})
public class DefinedActivity
    extends Activity
{

    protected ST description;
    protected CD nameCode;

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link ST }
     *     
     */
    public ST getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link ST }
     *     
     */
    public void setDescription(ST value) {
        this.description = value;
    }

    /**
     * Gets the value of the nameCode property.
     * 
     * @return
     *     possible object is
     *     {@link CD }
     *     
     */
    public CD getNameCode() {
        return nameCode;
    }

    /**
     * Sets the value of the nameCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link CD }
     *     
     */
    public void setNameCode(CD value) {
        this.nameCode = value;
    }

}
