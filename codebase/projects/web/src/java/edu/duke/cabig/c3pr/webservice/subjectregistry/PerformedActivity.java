
package edu.duke.cabig.c3pr.webservice.subjectregistry;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import edu.duke.cabig.c3pr.webservice.iso21090.BL;
import edu.duke.cabig.c3pr.webservice.iso21090.CD;
import edu.duke.cabig.c3pr.webservice.iso21090.TSDateTime;


/**
 * <p>Java class for PerformedActivity complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PerformedActivity">
 *   &lt;complexContent>
 *     &lt;extension base="{http://enterpriseservices.nci.nih.gov/SubjectRegistry}Activity">
 *       &lt;sequence>
 *         &lt;element name="statusCode" type="{uri:iso.org:21090}CD" minOccurs="0"/>
 *         &lt;element name="statusDate" type="{uri:iso.org:21090}TS.DateTime" minOccurs="0"/>
 *         &lt;element name="missedIndicator" type="{uri:iso.org:21090}BL" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PerformedActivity", propOrder = {
    "statusCode",
    "statusDate",
    "missedIndicator"
})
@XmlSeeAlso({
    PerformedAdministrativeActivity.class
})
public class PerformedActivity
    extends Activity
{

    protected CD statusCode;
    protected TSDateTime statusDate;
    protected BL missedIndicator;

    /**
     * Gets the value of the statusCode property.
     * 
     * @return
     *     possible object is
     *     {@link CD }
     *     
     */
    public CD getStatusCode() {
        return statusCode;
    }

    /**
     * Sets the value of the statusCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link CD }
     *     
     */
    public void setStatusCode(CD value) {
        this.statusCode = value;
    }

    /**
     * Gets the value of the statusDate property.
     * 
     * @return
     *     possible object is
     *     {@link TSDateTime }
     *     
     */
    public TSDateTime getStatusDate() {
        return statusDate;
    }

    /**
     * Sets the value of the statusDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link TSDateTime }
     *     
     */
    public void setStatusDate(TSDateTime value) {
        this.statusDate = value;
    }

    /**
     * Gets the value of the missedIndicator property.
     * 
     * @return
     *     possible object is
     *     {@link BL }
     *     
     */
    public BL getMissedIndicator() {
        return missedIndicator;
    }

    /**
     * Sets the value of the missedIndicator property.
     * 
     * @param value
     *     allowed object is
     *     {@link BL }
     *     
     */
    public void setMissedIndicator(BL value) {
        this.missedIndicator = value;
    }

}
