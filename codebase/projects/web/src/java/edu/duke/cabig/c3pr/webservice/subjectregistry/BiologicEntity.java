
package edu.duke.cabig.c3pr.webservice.subjectregistry;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import edu.duke.cabig.c3pr.webservice.iso21090.CD;
import edu.duke.cabig.c3pr.webservice.iso21090.TSDateTime;


/**
 * Any individual living (or previously living) being.
 * 
 * 				For example, animal, human being.
 *             
 * 
 * <p>Java class for BiologicEntity complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BiologicEntity">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="administrativeGenderCode" type="{uri:iso.org:21090}CD"/>
 *         &lt;element name="birthDate" type="{uri:iso.org:21090}TS.DateTime"/>
 *         &lt;element name="biologicEntityIdentifier" type="{http://enterpriseservices.nci.nih.gov/SubjectRegistry}BiologicEntityIdentifier" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BiologicEntity", propOrder = {
    "administrativeGenderCode",
    "birthDate",
    "biologicEntityIdentifier"
})
@XmlSeeAlso({
    Person.class
})
public abstract class BiologicEntity {

    @XmlElement(required = true)
    protected CD administrativeGenderCode;
    @XmlElement(required = true)
    protected TSDateTime birthDate;
    protected List<BiologicEntityIdentifier> biologicEntityIdentifier;

    /**
     * Gets the value of the administrativeGenderCode property.
     * 
     * @return
     *     possible object is
     *     {@link CD }
     *     
     */
    public CD getAdministrativeGenderCode() {
        return administrativeGenderCode;
    }

    /**
     * Sets the value of the administrativeGenderCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link CD }
     *     
     */
    public void setAdministrativeGenderCode(CD value) {
        this.administrativeGenderCode = value;
    }

    /**
     * Gets the value of the birthDate property.
     * 
     * @return
     *     possible object is
     *     {@link TSDateTime }
     *     
     */
    public TSDateTime getBirthDate() {
        return birthDate;
    }

    /**
     * Sets the value of the birthDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link TSDateTime }
     *     
     */
    public void setBirthDate(TSDateTime value) {
        this.birthDate = value;
    }

    /**
     * Gets the value of the biologicEntityIdentifier property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the biologicEntityIdentifier property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBiologicEntityIdentifier().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BiologicEntityIdentifier }
     * 
     * 
     */
    public List<BiologicEntityIdentifier> getBiologicEntityIdentifier() {
        if (biologicEntityIdentifier == null) {
            biologicEntityIdentifier = new ArrayList<BiologicEntityIdentifier>();
        }
        return this.biologicEntityIdentifier;
    }

}
