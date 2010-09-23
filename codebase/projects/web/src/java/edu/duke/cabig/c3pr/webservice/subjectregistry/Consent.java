
package edu.duke.cabig.c3pr.webservice.subjectregistry;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import edu.duke.cabig.c3pr.webservice.iso21090.BL;


/**
 * <p>Java class for Consent complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Consent">
 *   &lt;complexContent>
 *     &lt;extension base="{http://enterpriseservices.nci.nih.gov/SubjectRegistry}DocumentVersion">
 *       &lt;sequence>
 *         &lt;element name="mandatoryIndicator" type="{uri:iso.org:21090}BL" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Consent", propOrder = {
    "mandatoryIndicator"
})
public class Consent
    extends DocumentVersion
{

    protected BL mandatoryIndicator;

    /**
     * Gets the value of the mandatoryIndicator property.
     * 
     * @return
     *     possible object is
     *     {@link BL }
     *     
     */
    public BL getMandatoryIndicator() {
        return mandatoryIndicator;
    }

    /**
     * Sets the value of the mandatoryIndicator property.
     * 
     * @param value
     *     allowed object is
     *     {@link BL }
     *     
     */
    public void setMandatoryIndicator(BL value) {
        this.mandatoryIndicator = value;
    }

}
