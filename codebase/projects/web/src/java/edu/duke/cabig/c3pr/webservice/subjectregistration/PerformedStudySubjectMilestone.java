
package edu.duke.cabig.c3pr.webservice.subjectregistration;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import edu.duke.cabig.c3pr.webservice.iso21090.TSDateTime;


/**
 * <p>Java class for PerformedStudySubjectMilestone complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PerformedStudySubjectMilestone">
 *   &lt;complexContent>
 *     &lt;extension base="{http://enterpriseservices.nci.nih.gov/SubjectRegistration}PerformedAdministrativeActivity">
 *       &lt;sequence>
 *         &lt;element name="informedConsentDate" type="{uri:iso.org:21090}TS.DateTime" minOccurs="0"/>
 *         &lt;element name="offStudyDate" type="{uri:iso.org:21090}TS.DateTime" minOccurs="0"/>
 *         &lt;element name="registrationDate" type="{uri:iso.org:21090}TS.DateTime" minOccurs="0"/>
 *         &lt;element name="documentVersion" type="{http://enterpriseservices.nci.nih.gov/SubjectRegistration}DocumentVersion" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PerformedStudySubjectMilestone", propOrder = {
    "informedConsentDate",
    "offStudyDate",
    "registrationDate",
    "documentVersion"
})
@XmlSeeAlso({
    StudySubjectConsentVersion.class
})
public class PerformedStudySubjectMilestone
    extends PerformedAdministrativeActivity
{

    protected TSDateTime informedConsentDate;
    protected TSDateTime offStudyDate;
    protected TSDateTime registrationDate;
    protected DocumentVersion documentVersion;

    /**
     * Gets the value of the informedConsentDate property.
     * 
     * @return
     *     possible object is
     *     {@link TSDateTime }
     *     
     */
    public TSDateTime getInformedConsentDate() {
        return informedConsentDate;
    }

    /**
     * Sets the value of the informedConsentDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link TSDateTime }
     *     
     */
    public void setInformedConsentDate(TSDateTime value) {
        this.informedConsentDate = value;
    }

    /**
     * Gets the value of the offStudyDate property.
     * 
     * @return
     *     possible object is
     *     {@link TSDateTime }
     *     
     */
    public TSDateTime getOffStudyDate() {
        return offStudyDate;
    }

    /**
     * Sets the value of the offStudyDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link TSDateTime }
     *     
     */
    public void setOffStudyDate(TSDateTime value) {
        this.offStudyDate = value;
    }

    /**
     * Gets the value of the registrationDate property.
     * 
     * @return
     *     possible object is
     *     {@link TSDateTime }
     *     
     */
    public TSDateTime getRegistrationDate() {
        return registrationDate;
    }

    /**
     * Sets the value of the registrationDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link TSDateTime }
     *     
     */
    public void setRegistrationDate(TSDateTime value) {
        this.registrationDate = value;
    }

    /**
     * Gets the value of the documentVersion property.
     * 
     * @return
     *     possible object is
     *     {@link DocumentVersion }
     *     
     */
    public DocumentVersion getDocumentVersion() {
        return documentVersion;
    }

    /**
     * Sets the value of the documentVersion property.
     * 
     * @param value
     *     allowed object is
     *     {@link DocumentVersion }
     *     
     */
    public void setDocumentVersion(DocumentVersion value) {
        this.documentVersion = value;
    }

}
