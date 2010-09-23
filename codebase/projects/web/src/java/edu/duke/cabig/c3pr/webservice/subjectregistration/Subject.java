
package edu.duke.cabig.c3pr.webservice.subjectregistration;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element name="entity" type="{http://enterpriseservices.nci.nih.gov/SubjectRegistration}BiologicEntity"/>
 *         &lt;element name="subjectIdentifier" type="{http://www.w3.org/2001/XMLSchema}anyType" maxOccurs="unbounded"/>
 *         &lt;element name="enrollmentMilestones" type="{http://enterpriseservices.nci.nih.gov/SubjectRegistration}PerformedAdministrativeActivity" minOccurs="0"/>
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
    "subjectIdentifier",
    "enrollmentMilestones"
})
@XmlSeeAlso({
    StudySubject.class
})
public class Subject {

    @XmlElement(required = true)
    protected BiologicEntity entity;
    @XmlElement(required = true)
    protected List<Object> subjectIdentifier;
    protected PerformedAdministrativeActivity enrollmentMilestones;

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
     * {@link Object }
     * 
     * 
     */
    public List<Object> getSubjectIdentifier() {
        if (subjectIdentifier == null) {
            subjectIdentifier = new ArrayList<Object>();
        }
        return this.subjectIdentifier;
    }

    /**
     * Gets the value of the enrollmentMilestones property.
     * 
     * @return
     *     possible object is
     *     {@link PerformedAdministrativeActivity }
     *     
     */
    public PerformedAdministrativeActivity getEnrollmentMilestones() {
        return enrollmentMilestones;
    }

    /**
     * Sets the value of the enrollmentMilestones property.
     * 
     * @param value
     *     allowed object is
     *     {@link PerformedAdministrativeActivity }
     *     
     */
    public void setEnrollmentMilestones(PerformedAdministrativeActivity value) {
        this.enrollmentMilestones = value;
    }

}
