
package edu.duke.cabig.c3pr.webservice.testclient.iso21090;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BAG_TEL.Person complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BAG_TEL.Person">
 *   &lt;complexContent>
 *     &lt;extension base="{uri:iso.org:21090}COLL_TEL.Person">
 *       &lt;sequence>
 *         &lt;element name="item" type="{uri:iso.org:21090}TEL.Person" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BAG_TEL.Person", propOrder = {
    "item"
})
public class BAGTELPerson
    extends COLLTELPerson
{

    protected List<TELPerson> item;

    /**
     * Gets the value of the item property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the item property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getItem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TELPerson }
     * 
     * 
     */
    public List<TELPerson> getItem() {
        if (item == null) {
            item = new ArrayList<TELPerson>();
        }
        return this.item;
    }

}
