
package edu.duke.cabig.c3pr.webservice.subjectregistry;

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
 *         &lt;element name="searchParameter" type="{http://enterpriseservices.nci.nih.gov/SubjectRegistry}DSET_AdvanceSearchCriterionParameter"/>
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
    "searchParameter"
})
@XmlRootElement(name = "QuerySubjectRegistryRequest", namespace = "http://enterpriseservices.nci.nih.gov/SubjectRegistryService")
public class QuerySubjectRegistryRequest {

    @XmlElement(namespace = "", required = true)
    protected DSETAdvanceSearchCriterionParameter searchParameter;

    /**
     * Gets the value of the searchParameter property.
     * 
     * @return
     *     possible object is
     *     {@link DSETAdvanceSearchCriterionParameter }
     *     
     */
    public DSETAdvanceSearchCriterionParameter getSearchParameter() {
        return searchParameter;
    }

    /**
     * Sets the value of the searchParameter property.
     * 
     * @param value
     *     allowed object is
     *     {@link DSETAdvanceSearchCriterionParameter }
     *     
     */
    public void setSearchParameter(DSETAdvanceSearchCriterionParameter value) {
        this.searchParameter = value;
    }

}
