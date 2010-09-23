
package edu.duke.cabig.c3pr.webservice.subjectregistration;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import edu.duke.cabig.c3pr.webservice.iso21090.ST;
import edu.duke.cabig.c3pr.webservice.iso21090.TSDateTime;


/**
 * <p>Java class for DocumentVersion complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DocumentVersion">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="officialTitle" type="{uri:iso.org:21090}ST" minOccurs="0"/>
 *         &lt;element name="versionDate" type="{uri:iso.org:21090}TS.DateTime" minOccurs="0"/>
 *         &lt;element name="versionNumberText" type="{uri:iso.org:21090}ST" minOccurs="0"/>
 *         &lt;element name="document" type="{http://enterpriseservices.nci.nih.gov/SubjectRegistration}Document"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DocumentVersion", propOrder = {
    "officialTitle",
    "versionDate",
    "versionNumberText",
    "document"
})
@XmlSeeAlso({
    StudyProtocolDocumentVersion.class,
    Consent.class
})
public class DocumentVersion {

    protected ST officialTitle;
    protected TSDateTime versionDate;
    protected ST versionNumberText;
    @XmlElement(required = true)
    protected Document document;

    /**
     * Gets the value of the officialTitle property.
     * 
     * @return
     *     possible object is
     *     {@link ST }
     *     
     */
    public ST getOfficialTitle() {
        return officialTitle;
    }

    /**
     * Sets the value of the officialTitle property.
     * 
     * @param value
     *     allowed object is
     *     {@link ST }
     *     
     */
    public void setOfficialTitle(ST value) {
        this.officialTitle = value;
    }

    /**
     * Gets the value of the versionDate property.
     * 
     * @return
     *     possible object is
     *     {@link TSDateTime }
     *     
     */
    public TSDateTime getVersionDate() {
        return versionDate;
    }

    /**
     * Sets the value of the versionDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link TSDateTime }
     *     
     */
    public void setVersionDate(TSDateTime value) {
        this.versionDate = value;
    }

    /**
     * Gets the value of the versionNumberText property.
     * 
     * @return
     *     possible object is
     *     {@link ST }
     *     
     */
    public ST getVersionNumberText() {
        return versionNumberText;
    }

    /**
     * Sets the value of the versionNumberText property.
     * 
     * @param value
     *     allowed object is
     *     {@link ST }
     *     
     */
    public void setVersionNumberText(ST value) {
        this.versionNumberText = value;
    }

    /**
     * Gets the value of the document property.
     * 
     * @return
     *     possible object is
     *     {@link Document }
     *     
     */
    public Document getDocument() {
        return document;
    }

    /**
     * Sets the value of the document property.
     * 
     * @param value
     *     allowed object is
     *     {@link Document }
     *     
     */
    public void setDocument(Document value) {
        this.document = value;
    }

}
