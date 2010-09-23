
package edu.duke.cabig.c3pr.webservice.subjectregistry;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import edu.duke.cabig.c3pr.webservice.iso21090.CD;


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
 *     &lt;extension base="{http://enterpriseservices.nci.nih.gov/SubjectRegistry}Subject">
 *       &lt;sequence>
 *         &lt;element name="paymentMethodCode" type="{uri:iso.org:21090}CD" minOccurs="0"/>
 *         &lt;element name="statusCode" type="{uri:iso.org:21090}CD" minOccurs="0"/>
 *         &lt;element name="studySubjectProtocolVersion" type="{http://enterpriseservices.nci.nih.gov/SubjectRegistry}StudySubjectProtocolVersionRelationship"/>
 *         &lt;element name="studySubjectStatus" type="{http://enterpriseservices.nci.nih.gov/SubjectRegistry}PerformedStudySubjectMilestone" maxOccurs="unbounded" minOccurs="0"/>
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
    "studySubjectProtocolVersion",
    "studySubjectStatus"
})
public class StudySubject
    extends Subject
{

    protected CD paymentMethodCode;
    protected CD statusCode;
    @XmlElement(required = true)
    protected StudySubjectProtocolVersionRelationship studySubjectProtocolVersion;
    protected List<PerformedStudySubjectMilestone> studySubjectStatus;

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
     * Gets the value of the studySubjectStatus property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the studySubjectStatus property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getStudySubjectStatus().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PerformedStudySubjectMilestone }
     * 
     * 
     */
    public List<PerformedStudySubjectMilestone> getStudySubjectStatus() {
        if (studySubjectStatus == null) {
            studySubjectStatus = new ArrayList<PerformedStudySubjectMilestone>();
        }
        return this.studySubjectStatus;
    }

}
