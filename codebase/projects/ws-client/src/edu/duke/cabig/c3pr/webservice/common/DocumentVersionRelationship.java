/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.webservice.common;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import edu.duke.cabig.c3pr.webservice.iso21090.CD;


/**
 * <p>Java class for DocumentVersionRelationship complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DocumentVersionRelationship">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="typeCode" type="{uri:iso.org:21090}CD"/>
 *         &lt;element name="source" type="{http://enterpriseservices.nci.nih.gov/Common}DocumentVersion" minOccurs="0"/>
 *         &lt;element name="target" type="{http://enterpriseservices.nci.nih.gov/Common}DocumentVersion"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DocumentVersionRelationship", propOrder = {
    "typeCode",
    "source",
    "target"
})
public class DocumentVersionRelationship {

    @XmlElement(required = true)
    protected CD typeCode;
    protected DocumentVersion source;
    @XmlElement(required = true)
    protected DocumentVersion target;

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

    /**
     * Gets the value of the source property.
     * 
     * @return
     *     possible object is
     *     {@link DocumentVersion }
     *     
     */
    public DocumentVersion getSource() {
        return source;
    }

    /**
     * Sets the value of the source property.
     * 
     * @param value
     *     allowed object is
     *     {@link DocumentVersion }
     *     
     */
    public void setSource(DocumentVersion value) {
        this.source = value;
    }

    /**
     * Gets the value of the target property.
     * 
     * @return
     *     possible object is
     *     {@link DocumentVersion }
     *     
     */
    public DocumentVersion getTarget() {
        return target;
    }

    /**
     * Sets the value of the target property.
     * 
     * @param value
     *     allowed object is
     *     {@link DocumentVersion }
     *     
     */
    public void setTarget(DocumentVersion value) {
        this.target = value;
    }

}
