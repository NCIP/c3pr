
package edu.duke.cabig.c3pr.webservice.studyutility;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import edu.duke.cabig.c3pr.webservice.common.DocumentIdentifier;
import edu.duke.cabig.c3pr.webservice.common.PermissibleStudySubjectRegistryStatus;


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
 *         &lt;element name="studyIdentifier" type="{http://enterpriseservices.nci.nih.gov/Common}DocumentIdentifier"/>
 *         &lt;element name="status" type="{http://enterpriseservices.nci.nih.gov/Common}PermissibleStudySubjectRegistryStatus"/>
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
    "studyIdentifier",
    "status"
})
@XmlRootElement(name = "UpdateStudyStatusRequest")
public class UpdateStudyStatusRequest {

    @XmlElement(required = true)
    protected DocumentIdentifier studyIdentifier;
    @XmlElement(required = true)
    protected PermissibleStudySubjectRegistryStatus status;

    /**
     * Gets the value of the studyIdentifier property.
     * 
     * @return
     *     possible object is
     *     {@link DocumentIdentifier }
     *     
     */
    public DocumentIdentifier getStudyIdentifier() {
        return studyIdentifier;
    }

    /**
     * Sets the value of the studyIdentifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link DocumentIdentifier }
     *     
     */
    public void setStudyIdentifier(DocumentIdentifier value) {
        this.studyIdentifier = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link PermissibleStudySubjectRegistryStatus }
     *     
     */
    public PermissibleStudySubjectRegistryStatus getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link PermissibleStudySubjectRegistryStatus }
     *     
     */
    public void setStatus(PermissibleStudySubjectRegistryStatus value) {
        this.status = value;
    }

}
