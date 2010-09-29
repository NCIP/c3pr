
package edu.duke.cabig.c3pr.webservice.studyutility;

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
 *         &lt;element name="studies" type="{http://enterpriseservices.nci.nih.gov/StudyUtilitySchema}DSET_Study"/>
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
    "studies"
})
@XmlRootElement(name = "AdvancedQueryStudyResponse", namespace = "http://enterpriseservices.nci.nih.gov/StudyUtilityService")
public class AdvancedQueryStudyResponse {

    @XmlElement(namespace = "", required = true)
    protected DSETStudy studies;

    /**
     * Gets the value of the studies property.
     * 
     * @return
     *     possible object is
     *     {@link DSETStudy }
     *     
     */
    public DSETStudy getStudies() {
        return studies;
    }

    /**
     * Sets the value of the studies property.
     * 
     * @param value
     *     allowed object is
     *     {@link DSETStudy }
     *     
     */
    public void setStudies(DSETStudy value) {
        this.studies = value;
    }

}
