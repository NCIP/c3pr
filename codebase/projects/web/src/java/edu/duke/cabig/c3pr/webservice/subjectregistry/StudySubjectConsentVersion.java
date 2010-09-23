
package edu.duke.cabig.c3pr.webservice.subjectregistry;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import edu.duke.cabig.c3pr.webservice.iso21090.CD;
import edu.duke.cabig.c3pr.webservice.iso21090.ST;
import edu.duke.cabig.c3pr.webservice.iso21090.TSDateTime;


/**
 * <p>Java class for StudySubjectConsentVersion complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="StudySubjectConsentVersion">
 *   &lt;complexContent>
 *     &lt;extension base="{http://enterpriseservices.nci.nih.gov/SubjectRegistry}PerformedStudySubjectMilestone">
 *       &lt;sequence>
 *         &lt;element name="consentDeliveryDate" type="{uri:iso.org:21090}TS.DateTime"/>
 *         &lt;element name="consentingMethod" type="{uri:iso.org:21090}CD"/>
 *         &lt;element name="consentPresenter" type="{uri:iso.org:21090}ST" minOccurs="0"/>
 *         &lt;element name="subjectConsentAnswer" type="{http://enterpriseservices.nci.nih.gov/SubjectRegistry}PerformedStudySubjectMilestone" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StudySubjectConsentVersion", propOrder = {
    "consentDeliveryDate",
    "consentingMethod",
    "consentPresenter",
    "subjectConsentAnswer"
})
public class StudySubjectConsentVersion
    extends PerformedStudySubjectMilestone
{

    @XmlElement(required = true)
    protected TSDateTime consentDeliveryDate;
    @XmlElement(required = true)
    protected CD consentingMethod;
    protected ST consentPresenter;
    protected List<PerformedStudySubjectMilestone> subjectConsentAnswer;

    /**
     * Gets the value of the consentDeliveryDate property.
     * 
     * @return
     *     possible object is
     *     {@link TSDateTime }
     *     
     */
    public TSDateTime getConsentDeliveryDate() {
        return consentDeliveryDate;
    }

    /**
     * Sets the value of the consentDeliveryDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link TSDateTime }
     *     
     */
    public void setConsentDeliveryDate(TSDateTime value) {
        this.consentDeliveryDate = value;
    }

    /**
     * Gets the value of the consentingMethod property.
     * 
     * @return
     *     possible object is
     *     {@link CD }
     *     
     */
    public CD getConsentingMethod() {
        return consentingMethod;
    }

    /**
     * Sets the value of the consentingMethod property.
     * 
     * @param value
     *     allowed object is
     *     {@link CD }
     *     
     */
    public void setConsentingMethod(CD value) {
        this.consentingMethod = value;
    }

    /**
     * Gets the value of the consentPresenter property.
     * 
     * @return
     *     possible object is
     *     {@link ST }
     *     
     */
    public ST getConsentPresenter() {
        return consentPresenter;
    }

    /**
     * Sets the value of the consentPresenter property.
     * 
     * @param value
     *     allowed object is
     *     {@link ST }
     *     
     */
    public void setConsentPresenter(ST value) {
        this.consentPresenter = value;
    }

    /**
     * Gets the value of the subjectConsentAnswer property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the subjectConsentAnswer property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSubjectConsentAnswer().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PerformedStudySubjectMilestone }
     * 
     * 
     */
    public List<PerformedStudySubjectMilestone> getSubjectConsentAnswer() {
        if (subjectConsentAnswer == null) {
            subjectConsentAnswer = new ArrayList<PerformedStudySubjectMilestone>();
        }
        return this.subjectConsentAnswer;
    }

}
