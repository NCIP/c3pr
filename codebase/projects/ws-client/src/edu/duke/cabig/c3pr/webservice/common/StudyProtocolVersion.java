
package edu.duke.cabig.c3pr.webservice.common;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import edu.duke.cabig.c3pr.webservice.iso21090.ST;


/**
 * <p>Java class for StudyProtocolVersion complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="StudyProtocolVersion">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="studyProtocolDocument" type="{http://enterpriseservices.nci.nih.gov/Common}StudyProtocolDocumentVersion"/>
 *         &lt;element name="targetRegistrationSystem" type="{uri:iso.org:21090}ST" minOccurs="0"/>
 *         &lt;element name="permissibleStudySubjectRegistryStatus" type="{http://enterpriseservices.nci.nih.gov/Common}PermissibleStudySubjectRegistryStatus" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StudyProtocolVersion", propOrder = {
    "studyProtocolDocument",
    "targetRegistrationSystem",
    "permissibleStudySubjectRegistryStatus"
})
public class StudyProtocolVersion {

    @XmlElement(required = true)
    protected StudyProtocolDocumentVersion studyProtocolDocument;
    protected ST targetRegistrationSystem;
    protected List<PermissibleStudySubjectRegistryStatus> permissibleStudySubjectRegistryStatus;

    /**
     * Gets the value of the studyProtocolDocument property.
     * 
     * @return
     *     possible object is
     *     {@link StudyProtocolDocumentVersion }
     *     
     */
    public StudyProtocolDocumentVersion getStudyProtocolDocument() {
        return studyProtocolDocument;
    }

    /**
     * Sets the value of the studyProtocolDocument property.
     * 
     * @param value
     *     allowed object is
     *     {@link StudyProtocolDocumentVersion }
     *     
     */
    public void setStudyProtocolDocument(StudyProtocolDocumentVersion value) {
        this.studyProtocolDocument = value;
    }

    /**
     * Gets the value of the targetRegistrationSystem property.
     * 
     * @return
     *     possible object is
     *     {@link ST }
     *     
     */
    public ST getTargetRegistrationSystem() {
        return targetRegistrationSystem;
    }

    /**
     * Sets the value of the targetRegistrationSystem property.
     * 
     * @param value
     *     allowed object is
     *     {@link ST }
     *     
     */
    public void setTargetRegistrationSystem(ST value) {
        this.targetRegistrationSystem = value;
    }

    /**
     * Gets the value of the permissibleStudySubjectRegistryStatus property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the permissibleStudySubjectRegistryStatus property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPermissibleStudySubjectRegistryStatus().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PermissibleStudySubjectRegistryStatus }
     * 
     * 
     */
    public List<PermissibleStudySubjectRegistryStatus> getPermissibleStudySubjectRegistryStatus() {
        if (permissibleStudySubjectRegistryStatus == null) {
            permissibleStudySubjectRegistryStatus = new ArrayList<PermissibleStudySubjectRegistryStatus>();
        }
        return this.permissibleStudySubjectRegistryStatus;
    }

}
