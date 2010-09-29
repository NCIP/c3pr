
package edu.duke.cabig.c3pr.webservice.studyutility;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import edu.duke.cabig.c3pr.webservice.iso21090.CD;
import edu.duke.cabig.c3pr.webservice.iso21090.II;


/**
 * <p>Java class for StudyIdentifier complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="StudyIdentifier">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="identifier" type="{uri:iso.org:21090}II"/>
 *         &lt;element name="typeCode" type="{uri:iso.org:21090}CD"/>
 *         &lt;element name="assigningOrganization" type="{http://enterpriseservices.nci.nih.gov/StudyUtilitySchema}OrganizationIdentifier"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StudyIdentifier", propOrder = {
    "identifier",
    "typeCode",
    "assigningOrganization"
})
public class StudyIdentifier {

    @XmlElement(required = true)
    protected II identifier;
    @XmlElement(required = true)
    protected CD typeCode;
    @XmlElement(required = true)
    protected OrganizationIdentifier assigningOrganization;

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
     *     {@link OrganizationIdentifier }
     *     
     */
    public OrganizationIdentifier getAssigningOrganization() {
        return assigningOrganization;
    }

    /**
     * Sets the value of the assigningOrganization property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrganizationIdentifier }
     *     
     */
    public void setAssigningOrganization(OrganizationIdentifier value) {
        this.assigningOrganization = value;
    }

}
