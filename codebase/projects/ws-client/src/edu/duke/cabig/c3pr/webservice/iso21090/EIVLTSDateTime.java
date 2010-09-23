
package edu.duke.cabig.c3pr.webservice.iso21090;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EIVL_TS.DateTime complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EIVL_TS.DateTime">
 *   &lt;complexContent>
 *     &lt;extension base="{uri:iso.org:21090}QSET_TS.DateTime">
 *       &lt;sequence>
 *         &lt;element name="offset" type="{uri:iso.org:21090}IVL_PQ" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="event" type="{uri:iso.org:21090}TimingEvent" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EIVL_TS.DateTime", propOrder = {
    "offset"
})
public class EIVLTSDateTime
    extends QSETTSDateTime
{

    protected IVLPQ offset;
    @XmlAttribute
    protected TimingEvent event;

    /**
     * Gets the value of the offset property.
     * 
     * @return
     *     possible object is
     *     {@link IVLPQ }
     *     
     */
    public IVLPQ getOffset() {
        return offset;
    }

    /**
     * Sets the value of the offset property.
     * 
     * @param value
     *     allowed object is
     *     {@link IVLPQ }
     *     
     */
    public void setOffset(IVLPQ value) {
        this.offset = value;
    }

    /**
     * Gets the value of the event property.
     * 
     * @return
     *     possible object is
     *     {@link TimingEvent }
     *     
     */
    public TimingEvent getEvent() {
        return event;
    }

    /**
     * Sets the value of the event property.
     * 
     * @param value
     *     allowed object is
     *     {@link TimingEvent }
     *     
     */
    public void setEvent(TimingEvent value) {
        this.event = value;
    }

}
