
package edu.duke.cabig.c3pr.webservice.subjectregistration;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PerformedActivity complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PerformedActivity">
 *   &lt;complexContent>
 *     &lt;extension base="{http://enterpriseservices.nci.nih.gov/SubjectRegistration}Activity">
 *       &lt;sequence>
 *         &lt;element name="drug" type="{http://enterpriseservices.nci.nih.gov/SubjectRegistration}Drug" minOccurs="0"/>
 *         &lt;element name="definedActivity" type="{http://enterpriseservices.nci.nih.gov/SubjectRegistration}DefinedActivity" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PerformedActivity", propOrder = {
    "drug",
    "definedActivity"
})
@XmlSeeAlso({
    PerformedAdministrativeActivity.class
})
public class PerformedActivity
    extends Activity
{

    protected Drug drug;
    protected DefinedActivity definedActivity;

    /**
     * Gets the value of the drug property.
     * 
     * @return
     *     possible object is
     *     {@link Drug }
     *     
     */
    public Drug getDrug() {
        return drug;
    }

    /**
     * Sets the value of the drug property.
     * 
     * @param value
     *     allowed object is
     *     {@link Drug }
     *     
     */
    public void setDrug(Drug value) {
        this.drug = value;
    }

    /**
     * Gets the value of the definedActivity property.
     * 
     * @return
     *     possible object is
     *     {@link DefinedActivity }
     *     
     */
    public DefinedActivity getDefinedActivity() {
        return definedActivity;
    }

    /**
     * Sets the value of the definedActivity property.
     * 
     * @param value
     *     allowed object is
     *     {@link DefinedActivity }
     *     
     */
    public void setDefinedActivity(DefinedActivity value) {
        this.definedActivity = value;
    }

}
