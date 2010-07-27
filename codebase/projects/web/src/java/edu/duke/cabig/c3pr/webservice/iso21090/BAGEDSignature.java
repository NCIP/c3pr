
package edu.duke.cabig.c3pr.webservice.iso21090;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BAG_ED.Signature complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BAG_ED.Signature">
 *   &lt;complexContent>
 *     &lt;extension base="{uri:iso.org:21090}COLL_ED.Signature">
 *       &lt;sequence>
 *         &lt;element name="item" type="{uri:iso.org:21090}ED.Signature" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BAG_ED.Signature", propOrder = {
    "item"
})
public class BAGEDSignature
    extends COLLEDSignature
{

    protected List<EDSignature> item;

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
     * {@link EDSignature }
     * 
     * 
     */
    public List<EDSignature> getItem() {
        if (item == null) {
            item = new ArrayList<EDSignature>();
        }
        return this.item;
    }

}
