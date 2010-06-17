
package edu.duke.cabig.c3pr.webservice.subjectmanagement;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DSET_TS.Date.Full complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DSET_TS.Date.Full">
 *   &lt;complexContent>
 *     &lt;extension base="{uri:iso.org:21090}COLL_TS.Date.Full">
 *       &lt;sequence>
 *         &lt;element name="item" type="{uri:iso.org:21090}TS.Date.Full" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DSET_TS.Date.Full", propOrder = {
    "item"
})
public class DSETTSDateFull
    extends COLLTSDateFull
{

    protected List<TSDateFull> item;

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
     * {@link TSDateFull }
     * 
     * 
     */
    public List<TSDateFull> getItem() {
        if (item == null) {
            item = new ArrayList<TSDateFull>();
        }
        return this.item;
    }

}
