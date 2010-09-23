
package edu.duke.cabig.c3pr.webservice.subjectregistry;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * A formalized group of persons or other organizations
 * 				collected together for a common purpose (such as administrative,
 * 				legal, political) and the infrastructure to carry out that purpose.
 * 			
 * 
 * <p>Java class for Organization complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Organization">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="organizationIdentifier" type="{http://enterpriseservices.nci.nih.gov/SubjectRegistry}OrganizationIdentifier" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Organization", propOrder = {
    "organizationIdentifier"
})
public class Organization {

    @XmlElement(required = true)
    protected List<OrganizationIdentifier> organizationIdentifier;

    /**
     * Gets the value of the organizationIdentifier property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the organizationIdentifier property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOrganizationIdentifier().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OrganizationIdentifier }
     * 
     * 
     */
    public List<OrganizationIdentifier> getOrganizationIdentifier() {
        if (organizationIdentifier == null) {
            organizationIdentifier = new ArrayList<OrganizationIdentifier>();
        }
        return this.organizationIdentifier;
    }

}
