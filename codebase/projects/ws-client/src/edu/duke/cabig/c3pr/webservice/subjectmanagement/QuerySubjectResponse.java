/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.webservice.subjectmanagement;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="subjects" type="{http://enterpriseservices.nci.nih.gov}DSET_SUBJECT"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "subjects"
})
@XmlRootElement(name = "QuerySubjectResponse")
public class QuerySubjectResponse {

    @XmlElement(required = true)
    protected DSETSUBJECT subjects;

    /**
     * Gets the value of the subjects property.
     * 
     * @return
     *     possible object is
     *     {@link DSETSUBJECT }
     *     
     */
    public DSETSUBJECT getSubjects() {
        return subjects;
    }

    /**
     * Sets the value of the subjects property.
     * 
     * @param value
     *     allowed object is
     *     {@link DSETSUBJECT }
     *     
     */
    public void setSubjects(DSETSUBJECT value) {
        this.subjects = value;
    }

}
