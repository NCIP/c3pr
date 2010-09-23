
package edu.duke.cabig.c3pr.webservice.subjectregistry;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import edu.duke.cabig.c3pr.webservice.iso21090.BL;
import edu.duke.cabig.c3pr.webservice.iso21090.CD;
import edu.duke.cabig.c3pr.webservice.iso21090.II;
import edu.duke.cabig.c3pr.webservice.iso21090.IVLTSDateTime;


/**
 * The unique identification of a biologic entity in a
 * 				specified context.
 * 				NOTE: This class is a resolution of the
 * 				requirement for noting the type
 * 				of an identifier which is not handled
 * 				by the purely technical HL7 II
 * 				data type. It is the result of
 * 				applying a pattern provided by HL7
 * 				data type expert, Grahame Grieve.
 * 			
 * 
 * <p>Java class for BiologicEntityIdentifier complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BiologicEntityIdentifier">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="effectiveDateRange" type="{uri:iso.org:21090}IVL_TS.DateTime"/>
 *         &lt;element name="identifier" type="{uri:iso.org:21090}II"/>
 *         &lt;element name="primaryIndicator" type="{uri:iso.org:21090}BL"/>
 *         &lt;element name="typeCode" type="{uri:iso.org:21090}CD"/>
 *         &lt;element name="assigningOrganization" type="{http://enterpriseservices.nci.nih.gov/SubjectRegistry}Organization"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BiologicEntityIdentifier", propOrder = {
    "effectiveDateRange",
    "identifier",
    "primaryIndicator",
    "typeCode",
    "assigningOrganization"
})
public class BiologicEntityIdentifier {

    @XmlElement(required = true)
    protected IVLTSDateTime effectiveDateRange;
    @XmlElement(required = true)
    protected II identifier;
    @XmlElement(required = true)
    protected BL primaryIndicator;
    @XmlElement(required = true)
    protected CD typeCode;
    @XmlElement(required = true)
    protected Organization assigningOrganization;

    /**
     * Gets the value of the effectiveDateRange property.
     * 
     * @return
     *     possible object is
     *     {@link IVLTSDateTime }
     *     
     */
    public IVLTSDateTime getEffectiveDateRange() {
        return effectiveDateRange;
    }

    /**
     * Sets the value of the effectiveDateRange property.
     * 
     * @param value
     *     allowed object is
     *     {@link IVLTSDateTime }
     *     
     */
    public void setEffectiveDateRange(IVLTSDateTime value) {
        this.effectiveDateRange = value;
    }

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
