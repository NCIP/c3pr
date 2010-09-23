
package edu.duke.cabig.c3pr.webservice.subjectregistration;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import edu.duke.cabig.c3pr.webservice.iso21090.CD;


/**
 * A collection (physical or logical) of data with the following characteristics: 
 * 				1) Stewardship, 2) Potential for authentication, 3) Wholeness, 4) Human readability, 5) Persistence, 6) Global vs local context (the person that signs it is the author of all sections unless otherwise noted).
 * 				
 * 				For example, regulatory processes require the submission of documents from the Applicant to the Regulatory Authority. These documents are varied in focus and are often defined by the field of study or by the regulatory application requirements of the region or Regulatory Authority (e.g., Integrated Summary of Safety, Pharmacokinetics Written Summary). 
 * 				
 * 				For example, Adverse Event Report, Expedited Adverse Event Report, Institutional Review Board (IRB) Report, X-Ray Report, Lab Summary Report, Autopsy Report, etc.
 * 
 * <p>Java class for Document complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Document">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="typeCode" type="{uri:iso.org:21090}CD"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Document", propOrder = {
    "typeCode"
})
public class Document {

    @XmlElement(required = true)
    protected CD typeCode;

    /**
     * Gets the value of the typeCode property.
     * 
     * @return
     *     possible object is
     *     {@link CD }
     *     
     */
    public CD getTypeCode() {
        return typeCode;
    }

    /**
     * Sets the value of the typeCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link CD }
     *     
     */
    public void setTypeCode(CD value) {
        this.typeCode = value;
    }

}
