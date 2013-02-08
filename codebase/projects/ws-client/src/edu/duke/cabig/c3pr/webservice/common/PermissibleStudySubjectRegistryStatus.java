/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.webservice.common;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PermissibleStudySubjectRegistryStatus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PermissibleStudySubjectRegistryStatus">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="registryStatus" type="{http://enterpriseservices.nci.nih.gov/Common}RegistryStatus"/>
 *         &lt;element name="secondaryReason" type="{http://enterpriseservices.nci.nih.gov/Common}RegistryStatusReason" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PermissibleStudySubjectRegistryStatus", propOrder = {
    "registryStatus",
    "secondaryReason"
})
public class PermissibleStudySubjectRegistryStatus {

    @XmlElement(required = true)
    protected RegistryStatus registryStatus;
    protected List<RegistryStatusReason> secondaryReason;

    /**
     * Gets the value of the registryStatus property.
     * 
     * @return
     *     possible object is
     *     {@link RegistryStatus }
     *     
     */
    public RegistryStatus getRegistryStatus() {
        return registryStatus;
    }

    /**
     * Sets the value of the registryStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link RegistryStatus }
     *     
     */
    public void setRegistryStatus(RegistryStatus value) {
        this.registryStatus = value;
    }

    /**
     * Gets the value of the secondaryReason property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the secondaryReason property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSecondaryReason().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RegistryStatusReason }
     * 
     * 
     */
    public List<RegistryStatusReason> getSecondaryReason() {
        if (secondaryReason == null) {
            secondaryReason = new ArrayList<RegistryStatusReason>();
        }
        return this.secondaryReason;
    }

}
