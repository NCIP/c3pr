
package edu.duke.cabig.c3pr.webservice.subjectregistration;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
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
 *     &lt;extension base="{http://enterpriseservices.nci.nih.gov/SubjectRegistration}DocumentVersion">
 *       &lt;sequence>
 *         &lt;element name="mandatoryIndicator" type="{uri:iso.org:21090}BL" minOccurs="0"/>
 *         &lt;element name="studyProtocolDocumentVersion" type="{http://enterpriseservices.nci.nih.gov/SubjectRegistration}StudyProtocolDocumentVersion"/>
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
    "mandatoryIndicator",
    "studyProtocolDocumentVersion"
})
public class Consent
    extends DocumentVersion
{

    protected BL mandatoryIndicator;
    @XmlElement(required = true)
    protected StudyProtocolDocumentVersion studyProtocolDocumentVersion;

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

    /**
     * Gets the value of the studyProtocolDocumentVersion property.
     * 
     * @return
     *     possible object is
     *     {@link StudyProtocolDocumentVersion }
     *     
     */
    public StudyProtocolDocumentVersion getStudyProtocolDocumentVersion() {
        return studyProtocolDocumentVersion;
    }

    /**
     * Sets the value of the studyProtocolDocumentVersion property.
     * 
     * @param value
     *     allowed object is
     *     {@link StudyProtocolDocumentVersion }
     *     
     */
    public void setStudyProtocolDocumentVersion(StudyProtocolDocumentVersion value) {
        this.studyProtocolDocumentVersion = value;
    }

}
