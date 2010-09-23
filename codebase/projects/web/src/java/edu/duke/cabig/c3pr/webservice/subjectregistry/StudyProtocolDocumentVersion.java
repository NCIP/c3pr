
package edu.duke.cabig.c3pr.webservice.subjectregistry;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import edu.duke.cabig.c3pr.webservice.iso21090.ST;


/**
 * <p>Java class for StudyProtocolDocumentVersion complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="StudyProtocolDocumentVersion">
 *   &lt;complexContent>
 *     &lt;extension base="{http://enterpriseservices.nci.nih.gov/SubjectRegistry}DocumentVersion">
 *       &lt;sequence>
 *         &lt;element name="publicTitle" type="{uri:iso.org:21090}ST" minOccurs="0"/>
 *         &lt;element name="publicDescription" type="{uri:iso.org:21090}ST" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StudyProtocolDocumentVersion", propOrder = {
    "publicTitle",
    "publicDescription"
})
public class StudyProtocolDocumentVersion
    extends DocumentVersion
{

    protected ST publicTitle;
    protected ST publicDescription;

    /**
     * Gets the value of the publicTitle property.
     * 
     * @return
     *     possible object is
     *     {@link ST }
     *     
     */
    public ST getPublicTitle() {
        return publicTitle;
    }

    /**
     * Sets the value of the publicTitle property.
     * 
     * @param value
     *     allowed object is
     *     {@link ST }
     *     
     */
    public void setPublicTitle(ST value) {
        this.publicTitle = value;
    }

    /**
     * Gets the value of the publicDescription property.
     * 
     * @return
     *     possible object is
     *     {@link ST }
     *     
     */
    public ST getPublicDescription() {
        return publicDescription;
    }

    /**
     * Sets the value of the publicDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link ST }
     *     
     */
    public void setPublicDescription(ST value) {
        this.publicDescription = value;
    }

}
