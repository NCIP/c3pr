
package edu.duke.cabig.c3pr.webservice.testclient.studyutility;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="registryStatuses" type="{http://enterpriseservices.nci.nih.gov/StudyUtilitySchema}DSET_RegistryStatus"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "registryStatuses"
})
@XmlRootElement(name = "QueryRegistryStatusResponse")
public class QueryRegistryStatusResponse {

    @XmlElement(required = true)
    protected DSETRegistryStatus registryStatuses;

    /**
     * Gets the value of the registryStatuses property.
     * 
     * @return
     *     possible object is
     *     {@link DSETRegistryStatus }
     *     
     */
    public DSETRegistryStatus getRegistryStatuses() {
        return registryStatuses;
    }

    /**
     * Sets the value of the registryStatuses property.
     * 
     * @param value
     *     allowed object is
     *     {@link DSETRegistryStatus }
     *     
     */
    public void setRegistryStatuses(DSETRegistryStatus value) {
        this.registryStatuses = value;
    }

}
