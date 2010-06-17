
package edu.duke.cabig.c3pr.webservice.subjectmanagement;

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
 *         &lt;element name="subjectIdentifier" type="{uri:iso.org:21090}II"/>
 *         &lt;element name="newState" type="{uri:iso.org:21090}ST"/>
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
    "subjectIdentifier",
    "newState"
})
@XmlRootElement(name = "UpdateSubjectStateRequest", namespace = "http://enterpriseservices.nci.nih.gov/SubjectManagementService")
public class UpdateSubjectStateRequest {

    @XmlElement(namespace = "", required = true)
    protected II subjectIdentifier;
    @XmlElement(namespace = "", required = true)
    protected ST newState;

    /**
     * Gets the value of the subjectIdentifier property.
     * 
     * @return
     *     possible object is
     *     {@link II }
     *     
     */
    public II getSubjectIdentifier() {
        return subjectIdentifier;
    }

    /**
     * Sets the value of the subjectIdentifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link II }
     *     
     */
    public void setSubjectIdentifier(II value) {
        this.subjectIdentifier = value;
    }

    /**
     * Gets the value of the newState property.
     * 
     * @return
     *     possible object is
     *     {@link ST }
     *     
     */
    public ST getNewState() {
        return newState;
    }

    /**
     * Sets the value of the newState property.
     * 
     * @param value
     *     allowed object is
     *     {@link ST }
     *     
     */
    public void setNewState(ST value) {
        this.newState = value;
    }

}
