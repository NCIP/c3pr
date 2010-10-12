
package edu.duke.cabig.c3pr.webservice.testclient.iso21090;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DSET_ST complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DSET_ST">
 *   &lt;complexContent>
 *     &lt;extension base="{uri:iso.org:21090}COLL_ST">
 *       &lt;sequence>
 *         &lt;element name="item" type="{uri:iso.org:21090}ST" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DSET_ST", propOrder = {
    "item"
})
public class DSETST
    extends COLLST
{

    protected List<ST> item;
    
    public DSETST() {	
	}
    
    

    public DSETST(List<ST> item) {
		super();
		this.item = item;
	}



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
     * {@link ST }
     * 
     * 
     */
    public List<ST> getItem() {
        if (item == null) {
            item = new ArrayList<ST>();
        }
        return this.item;
    }

}
