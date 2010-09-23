
package edu.duke.cabig.c3pr.webservice.subjectregistry;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import edu.duke.cabig.c3pr.webservice.iso21090.BL;
import edu.duke.cabig.c3pr.webservice.iso21090.CD;
import edu.duke.cabig.c3pr.webservice.iso21090.II;


/**
 * Identifier of an organization.
 * 
 * <p>Java class for DocumentIdentifier complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DocumentIdentifier">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="identifier" type="{uri:iso.org:21090}II"/>
 *         &lt;element name="primaryIndicator" type="{uri:iso.org:21090}BL"/>
 *         &lt;element name="typeCode" type="{uri:iso.org:21090}CD"/>
 *         &lt;element name="assigningOrganization" type="{http://enterpriseservices.nci.nih.gov/SubjectRegistry}Organization" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DocumentIdentifier", propOrder = {
    "identifier",
    "primaryIndicator",
    "typeCode",
    "assigningOrganization"
})
public class DocumentIdentifier {

    @XmlElement(required = true)
    protected II identifier;
    @XmlElement(required = true)
    protected BL primaryIndicator;
    @XmlElement(required = true)
    protected CD typeCode;
    protected Organization assigningOrganization;

    /**
     * Gets the value of the identifier property.
     * 
     * @return
     *     possible object is
     *     {@link II }
     *     
     */
    public II getIdentifier() {
        return identifier;
    }

    /**
     * Sets the value of the identifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link II }
     *     
     */
    public void setIdentifier(II value) {
        this.identifier = value;
    }

    /**
     * Gets the value of the primaryIndicator property.
     * 
     * @return
     *     possible object is
     *     {@link BL }
     *     
     */
    public BL getPrimaryIndicator() {
        return primaryIndicator;
    }

    /**
     * Sets the value of the primaryIndicator property.
     * 
     * @param value
     *     allowed object is
     *     {@link BL }
     *     
     */
    public void setPrimaryIndicator(BL value) {
        this.primaryIndicator = value;
    }

    /**
     * Gets the value of the typeCode property.
     * 
     * @return
     *     possible object is
     *     {@link CD }
     *     
     */
    public CD getTypeCode() {
        return typeCode;
    }

    /**
     * Sets the value of the typeCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link CD }
     *     
     */
    public void setTypeCode(CD value) {
        this.typeCode = value;
    }

    /**
     * Gets the value of the assigningOrganization property.
     * 
     * @return
     *     possible object is
     *     {@link Organization }
     *     
     */
    public Organization getAssigningOrganization() {
        return assigningOrganization;
    }

    /**
     * Sets the value of the assigningOrganization property.
     * 
     * @param value
     *     allowed object is
     *     {@link Organization }
     *     
     */
    public void setAssigningOrganization(Organization value) {
        this.assigningOrganization = value;
    }

}
