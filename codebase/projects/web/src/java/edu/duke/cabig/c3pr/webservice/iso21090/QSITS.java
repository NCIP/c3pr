
package edu.duke.cabig.c3pr.webservice.iso21090;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for QSI_TS complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="QSI_TS">
 *   &lt;complexContent>
 *     &lt;extension base="{uri:iso.org:21090}QSET_TS">
 *       &lt;sequence>
 *         &lt;element name="term" type="{uri:iso.org:21090}QSET_TS" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "QSI_TS", propOrder = {
    "term"
})
@XmlSeeAlso({
    QSETBoundedPIVL.class
})
public class QSITS
    extends QSETTS
{

    protected List<QSETTS> term;

    /**
     * Gets the value of the term property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the term property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTerm().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link QSETTS }
     * 
     * 
     */
    public List<QSETTS> getTerm() {
        if (term == null) {
            term = new ArrayList<QSETTS>();
        }
        return this.term;
    }

}
