
package edu.duke.cabig.c3pr.webservice.subjectregistry;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import edu.duke.cabig.c3pr.webservice.iso21090.BAGTEL;
import edu.duke.cabig.c3pr.webservice.iso21090.CD;
import edu.duke.cabig.c3pr.webservice.iso21090.DSETAD;
import edu.duke.cabig.c3pr.webservice.iso21090.DSETCD;
import edu.duke.cabig.c3pr.webservice.iso21090.DSETENPN;


/**
 * A human being.
 * 
 * <p>Java class for Person complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Person">
 *   &lt;complexContent>
 *     &lt;extension base="{http://enterpriseservices.nci.nih.gov/SubjectRegistry}BiologicEntity">
 *       &lt;sequence>
 *         &lt;element name="ethnicGroupCode" type="{uri:iso.org:21090}DSET_CD"/>
 *         &lt;element name="maritalStatusCode" type="{uri:iso.org:21090}CD"/>
 *         &lt;element name="name" type="{uri:iso.org:21090}DSET_EN.PN"/>
 *         &lt;element name="postalAddress" type="{uri:iso.org:21090}DSET_AD"/>
 *         &lt;element name="raceCode" type="{uri:iso.org:21090}DSET_CD"/>
 *         &lt;element name="telecomAddress" type="{uri:iso.org:21090}BAG_TEL"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Person", propOrder = {
    "ethnicGroupCode",
    "maritalStatusCode",
    "name",
    "postalAddress",
    "raceCode",
    "telecomAddress"
})
public class Person
    extends BiologicEntity
{

    @XmlElement(required = true)
    protected DSETCD ethnicGroupCode;
    @XmlElement(required = true)
    protected CD maritalStatusCode;
    @XmlElement(required = true)
    protected DSETENPN name;
    @XmlElement(required = true)
    protected DSETAD postalAddress;
    @XmlElement(required = true)
    protected DSETCD raceCode;
    @XmlElement(required = true)
    protected BAGTEL telecomAddress;

    /**
     * Gets the value of the ethnicGroupCode property.
     * 
     * @return
     *     possible object is
     *     {@link DSETCD }
     *     
     */
    public DSETCD getEthnicGroupCode() {
        return ethnicGroupCode;
    }

    /**
     * Sets the value of the ethnicGroupCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link DSETCD }
     *     
     */
    public void setEthnicGroupCode(DSETCD value) {
        this.ethnicGroupCode = value;
    }

    /**
     * Gets the value of the maritalStatusCode property.
     * 
     * @return
     *     possible object is
     *     {@link CD }
     *     
     */
    public CD getMaritalStatusCode() {
        return maritalStatusCode;
    }

    /**
     * Sets the value of the maritalStatusCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link CD }
     *     
     */
    public void setMaritalStatusCode(CD value) {
        this.maritalStatusCode = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link DSETENPN }
     *     
     */
    public DSETENPN getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link DSETENPN }
     *     
     */
    public void setName(DSETENPN value) {
        this.name = value;
    }

    /**
     * Gets the value of the postalAddress property.
     * 
     * @return
     *     possible object is
     *     {@link DSETAD }
     *     
     */
    public DSETAD getPostalAddress() {
        return postalAddress;
    }

    /**
     * Sets the value of the postalAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link DSETAD }
     *     
     */
    public void setPostalAddress(DSETAD value) {
        this.postalAddress = value;
    }

    /**
     * Gets the value of the raceCode property.
     * 
     * @return
     *     possible object is
     *     {@link DSETCD }
     *     
     */
    public DSETCD getRaceCode() {
        return raceCode;
    }

    /**
     * Sets the value of the raceCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link DSETCD }
     *     
     */
    public void setRaceCode(DSETCD value) {
        this.raceCode = value;
    }

    /**
     * Gets the value of the telecomAddress property.
     * 
     * @return
     *     possible object is
     *     {@link BAGTEL }
     *     
     */
    public BAGTEL getTelecomAddress() {
        return telecomAddress;
    }

    /**
     * Sets the value of the telecomAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link BAGTEL }
     *     
     */
    public void setTelecomAddress(BAGTEL value) {
        this.telecomAddress = value;
    }

}
