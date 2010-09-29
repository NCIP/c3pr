
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
 *         &lt;element name="study" type="{http://enterpriseservices.nci.nih.gov/StudyUtilitySchema}Study"/>
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
    "study"
})
@XmlRootElement(name = "CreateStudyResponse", namespace = "http://enterpriseservices.nci.nih.gov/StudyUtilityService")
public class CreateStudyResponse {

    @XmlElement(namespace = "", required = true)
    protected Study study;

    /**
     * Gets the value of the study property.
     * 
     * @return
     *     possible object is
     *     {@link Study }
     *     
     */
    public Study getStudy() {
        return study;
    }

    /**
     * Sets the value of the study property.
     * 
     * @param value
     *     allowed object is
     *     {@link Study }
     *     
     */
    public void setStudy(Study value) {
        this.study = value;
    }

}
