
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
 *         &lt;element name="statuses" type="{http://enterpriseservices.nci.nih.gov/StudyUtilitySchema}DSET_PermissibleStudySubjectRegistryStatus"/>
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
    "statuses"
})
@XmlRootElement(name = "QueryStudyRegistryStatusResponse")
public class QueryStudyRegistryStatusResponse {

    @XmlElement(required = true)
    protected DSETPermissibleStudySubjectRegistryStatus statuses;

    /**
     * Gets the value of the statuses property.
     * 
     * @return
     *     possible object is
     *     {@link DSETPermissibleStudySubjectRegistryStatus }
     *     
     */
    public DSETPermissibleStudySubjectRegistryStatus getStatuses() {
        return statuses;
    }

    /**
     * Sets the value of the statuses property.
     * 
     * @param value
     *     allowed object is
     *     {@link DSETPermissibleStudySubjectRegistryStatus }
     *     
     */
    public void setStatuses(DSETPermissibleStudySubjectRegistryStatus value) {
        this.statuses = value;
    }

}
