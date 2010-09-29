
package edu.duke.cabig.c3pr.webservice.studyutility;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DSET_Study complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DSET_Study">
 *   &lt;complexContent>
 *     &lt;extension base="{http://enterpriseservices.nci.nih.gov/StudyUtilitySchema}COLL_Study">
 *       &lt;sequence>
 *         &lt;element name="item" type="{http://enterpriseservices.nci.nih.gov/StudyUtilitySchema}Study" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DSET_Study", namespace = "http://enterpriseservices.nci.nih.gov/StudyUtilitySchema", propOrder = {
    "item"
})
public class DSETStudy
    extends COLLStudy
{

    protected List<Study> item;

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
     * {@link Study }
     * 
     * 
     */
    public List<Study> getItem() {
        if (item == null) {
            item = new ArrayList<Study>();
        }
        return this.item;
    }

}
