
package edu.duke.cabig.c3pr.webservice.subjectregistry;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element name="documentIdentifier" type="{http://enterpriseservices.nci.nih.gov/SubjectRegistry}DocumentIdentifier" maxOccurs="unbounded" minOccurs="0"/>
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
    "documentIdentifier"
})
public class Document {

    protected List<DocumentIdentifier> documentIdentifier;

    /**
     * Gets the value of the documentIdentifier property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the documentIdentifier property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDocumentIdentifier().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DocumentIdentifier }
     * 
     * 
     */
    public List<DocumentIdentifier> getDocumentIdentifier() {
        if (documentIdentifier == null) {
            documentIdentifier = new ArrayList<DocumentIdentifier>();
        }
        return this.documentIdentifier;
    }

}
