
package edu.duke.cabig.c3pr.webservice.subjectmanagement;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BAG_TS.DateTime.Full complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BAG_TS.DateTime.Full">
 *   &lt;complexContent>
 *     &lt;extension base="{uri:iso.org:21090}COLL_TS.DateTime.Full">
 *       &lt;sequence>
 *         &lt;element name="item" type="{uri:iso.org:21090}TS.DateTime.Full" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BAG_TS.DateTime.Full", propOrder = {
    "item"
})
public class BAGTSDateTimeFull
    extends COLLTSDateTimeFull
{

    protected List<TSDateTimeFull> item;

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
     * {@link TSDateTimeFull }
     * 
     * 
     */
    public List<TSDateTimeFull> getItem() {
        if (item == null) {
            item = new ArrayList<TSDateTimeFull>();
        }
        return this.item;
    }

}
