
package edu.duke.cabig.c3pr.webservice.common;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the edu.duke.cabig.c3pr.webservice.common package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: edu.duke.cabig.c3pr.webservice.common
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link OrganizationIdentifier }
     * 
     */
    public OrganizationIdentifier createOrganizationIdentifier() {
        return new OrganizationIdentifier();
    }

    /**
     * Create an instance of {@link AdvanceSearchCriterionParameter }
     * 
     */
    public AdvanceSearchCriterionParameter createAdvanceSearchCriterionParameter() {
        return new AdvanceSearchCriterionParameter();
    }

    /**
     * Create an instance of {@link DSETAdvanceSearchCriterionParameter }
     * 
     */
    public DSETAdvanceSearchCriterionParameter createDSETAdvanceSearchCriterionParameter() {
        return new DSETAdvanceSearchCriterionParameter();
    }

}
