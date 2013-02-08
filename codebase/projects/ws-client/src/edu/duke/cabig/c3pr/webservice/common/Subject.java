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
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import edu.duke.cabig.c3pr.webservice.iso21090.ST;


/**
 * An entity of interest.
 * 				For example, a human being who might be of interest because they are
 * 				on a study.
 * 			
 * 
 * <p>Java class for Subject complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Subject">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="entity" type="{http://enterpriseservices.nci.nih.gov/Common}BiologicEntity" minOccurs="0"/>
 *         &lt;element name="stateCode" type="{uri:iso.org:21090}ST" minOccurs="0"/>
 *         &lt;element name="subjectIdentifier" type="{http://enterpriseservices.nci.nih.gov/Common}SubjectIdentifier" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Subject", propOrder = {
    "entity",
    "stateCode",
    "subjectIdentifier"
})
@XmlSeeAlso({
    StudySubjectBase.class
})
public class Subject {

    protected BiologicEntity entity;
    protected ST stateCode;
    protected List<SubjectIdentifier> subjectIdentifier;

    /**
     * Gets the value of the entity property.
     * 
     * @return
     *     possible object is
     *     {@link BiologicEntity }
     *     
     */
    public BiologicEntity getEntity() {
        return entity;
    }

    /**
     * Sets the value of the entity property.
     * 
     * @param value
     *     allowed object is
     *     {@link BiologicEntity }
     *     
     */
    public void setEntity(BiologicEntity value) {
        this.entity = value;
    }

    /**
     * Gets the value of the stateCode property.
     * 
     * @return
     *     possible object is
     *     {@link ST }
     *     
     */
    public ST getStateCode() {
        return stateCode;
    }

    /**
     * Sets the value of the stateCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link ST }
     *     
     */
    public void setStateCode(ST value) {
        this.stateCode = value;
    }

    /**
     * Gets the value of the subjectIdentifier property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the subjectIdentifier property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSubjectIdentifier().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SubjectIdentifier }
     * 
     * 
     */
    public List<SubjectIdentifier> getSubjectIdentifier() {
        if (subjectIdentifier == null) {
            subjectIdentifier = new ArrayList<SubjectIdentifier>();
        }
        return this.subjectIdentifier;
    }

}
