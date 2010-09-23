
package edu.duke.cabig.c3pr.webservice.subjectregistration;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import edu.duke.cabig.c3pr.webservice.iso21090.CD;
import edu.duke.cabig.c3pr.webservice.iso21090.TSDateTime;


/**
 * A physical entity which is the primary unit of operational and/or administrative interest in a study. 
 * 				
 * 				For example, a person who is registered in a study as a recipient of an investigational product or as a control.  May also include individuals who are being screened for studies, or individuals participating in observational or other studies.  Other examples may include a pacemaker, a fuse that can be used in medical devices, a cow, a farm, a pen of pigs, a tissue sample from a tissue bank, etc.
 * 				
 * 				NOTE: StudySubjects within a study are all of the same type.  An entity registered in a study is not part of another entity registered in the same study.
 * 
 * <p>Java class for StudySubject complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="StudySubject">
 *   &lt;complexContent>
 *     &lt;extension base="{http://enterpriseservices.nci.nih.gov/SubjectRegistration}Subject">
 *       &lt;sequence>
 *         &lt;element name="paymentMethodCode" type="{uri:iso.org:21090}CD" minOccurs="0"/>
 *         &lt;element name="statusCode" type="{uri:iso.org:21090}CD"/>
 *         &lt;element name="statusDate" type="{uri:iso.org:21090}TS.DateTime" minOccurs="0"/>
 *         &lt;element name="studySubjectProtocolVersion" type="{http://enterpriseservices.nci.nih.gov/SubjectRegistration}StudySubjectProtocolVersionRelationship"/>
 *         &lt;element name="treatingPhysician" type="{http://enterpriseservices.nci.nih.gov/SubjectRegistration}StudyInvestigator" minOccurs="0"/>
 *         &lt;element name="diseaseHistory" type="{http://enterpriseservices.nci.nih.gov/SubjectRegistration}PerformedDiagnosis" minOccurs="0"/>
 *         &lt;element name="childStudySubject" type="{http://enterpriseservices.nci.nih.gov/SubjectRegistration}StudySubject" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="parentStudySubject" type="{http://enterpriseservices.nci.nih.gov/SubjectRegistration}StudySubject" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StudySubject", propOrder = {
    "paymentMethodCode",
    "statusCode",
    "statusDate",
    "studySubjectProtocolVersion",
    "treatingPhysician",
    "diseaseHistory",
    "childStudySubject",
    "parentStudySubject"
})
public class StudySubject
    extends Subject
{

    protected CD paymentMethodCode;
    @XmlElement(required = true)
    protected CD statusCode;
    protected TSDateTime statusDate;
    @XmlElement(required = true)
    protected StudySubjectProtocolVersionRelationship studySubjectProtocolVersion;
    protected StudyInvestigator treatingPhysician;
    protected PerformedDiagnosis diseaseHistory;
    protected List<StudySubject> childStudySubject;
    protected StudySubject parentStudySubject;

    /**
     * Gets the value of the paymentMethodCode property.
     * 
     * @return
     *     possible object is
     *     {@link CD }
     *     
     */
    public CD getPaymentMethodCode() {
        return paymentMethodCode;
    }

    /**
     * Sets the value of the paymentMethodCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link CD }
     *     
     */
    public void setPaymentMethodCode(CD value) {
        this.paymentMethodCode = value;
    }

    /**
     * Gets the value of the statusCode property.
     * 
     * @return
     *     possible object is
     *     {@link CD }
     *     
     */
    public CD getStatusCode() {
        return statusCode;
    }

    /**
     * Sets the value of the statusCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link CD }
     *     
     */
    public void setStatusCode(CD value) {
        this.statusCode = value;
    }

    /**
     * Gets the value of the statusDate property.
     * 
     * @return
     *     possible object is
     *     {@link TSDateTime }
     *     
     */
    public TSDateTime getStatusDate() {
        return statusDate;
    }

    /**
     * Sets the value of the statusDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link TSDateTime }
     *     
     */
    public void setStatusDate(TSDateTime value) {
        this.statusDate = value;
    }

    /**
     * Gets the value of the studySubjectProtocolVersion property.
     * 
     * @return
     *     possible object is
     *     {@link StudySubjectProtocolVersionRelationship }
     *     
     */
    public StudySubjectProtocolVersionRelationship getStudySubjectProtocolVersion() {
        return studySubjectProtocolVersion;
    }

    /**
     * Sets the value of the studySubjectProtocolVersion property.
     * 
     * @param value
     *     allowed object is
     *     {@link StudySubjectProtocolVersionRelationship }
     *     
     */
    public void setStudySubjectProtocolVersion(StudySubjectProtocolVersionRelationship value) {
        this.studySubjectProtocolVersion = value;
    }

    /**
     * Gets the value of the treatingPhysician property.
     * 
     * @return
     *     possible object is
     *     {@link StudyInvestigator }
     *     
     */
    public StudyInvestigator getTreatingPhysician() {
        return treatingPhysician;
    }

    /**
     * Sets the value of the treatingPhysician property.
     * 
     * @param value
     *     allowed object is
     *     {@link StudyInvestigator }
     *     
     */
    public void setTreatingPhysician(StudyInvestigator value) {
        this.treatingPhysician = value;
    }

    /**
     * Gets the value of the diseaseHistory property.
     * 
     * @return
     *     possible object is
     *     {@link PerformedDiagnosis }
     *     
     */
    public PerformedDiagnosis getDiseaseHistory() {
        return diseaseHistory;
    }

    /**
     * Sets the value of the diseaseHistory property.
     * 
     * @param value
     *     allowed object is
     *     {@link PerformedDiagnosis }
     *     
     */
    public void setDiseaseHistory(PerformedDiagnosis value) {
        this.diseaseHistory = value;
    }

    /**
     * Gets the value of the childStudySubject property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the childStudySubject property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getChildStudySubject().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link StudySubject }
     * 
     * 
     */
    public List<StudySubject> getChildStudySubject() {
        if (childStudySubject == null) {
            childStudySubject = new ArrayList<StudySubject>();
        }
        return this.childStudySubject;
    }

    /**
     * Gets the value of the parentStudySubject property.
     * 
     * @return
     *     possible object is
     *     {@link StudySubject }
     *     
     */
    public StudySubject getParentStudySubject() {
        return parentStudySubject;
    }

    /**
     * Sets the value of the parentStudySubject property.
     * 
     * @param value
     *     allowed object is
     *     {@link StudySubject }
     *     
     */
    public void setParentStudySubject(StudySubject value) {
        this.parentStudySubject = value;
    }

}
