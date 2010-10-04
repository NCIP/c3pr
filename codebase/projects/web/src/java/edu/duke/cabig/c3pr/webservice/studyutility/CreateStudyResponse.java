
package edu.duke.cabig.c3pr.webservice.studyutility;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import edu.duke.cabig.c3pr.webservice.common.StudyProtocolVersion;


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
 *         &lt;element name="study" type="{http://enterpriseservices.nci.nih.gov/Common}StudyProtocolVersion"/>
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
@XmlRootElement(name = "CreateStudyResponse")
public class CreateStudyResponse {

    @XmlElement(required = true)
    protected StudyProtocolVersion study;

    /**
     * Gets the value of the study property.
     * 
     * @return
     *     possible object is
     *     {@link StudyProtocolVersion }
     *     
     */
    public StudyProtocolVersion getStudy() {
        return study;
    }

    /**
     * Sets the value of the study property.
     * 
     * @param value
     *     allowed object is
     *     {@link StudyProtocolVersion }
     *     
     */
    public void setStudy(StudyProtocolVersion value) {
        this.study = value;
    }

}
