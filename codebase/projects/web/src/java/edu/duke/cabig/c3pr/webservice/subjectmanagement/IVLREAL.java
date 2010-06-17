
package edu.duke.cabig.c3pr.webservice.subjectmanagement;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for IVL_REAL complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IVL_REAL">
 *   &lt;complexContent>
 *     &lt;extension base="{uri:iso.org:21090}QSET_REAL">
 *       &lt;sequence>
 *         &lt;element name="low" type="{uri:iso.org:21090}REAL" minOccurs="0"/>
 *         &lt;element name="high" type="{uri:iso.org:21090}REAL" minOccurs="0"/>
 *         &lt;element name="width" type="{uri:iso.org:21090}QTY" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="lowClosed" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="highClosed" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IVL_REAL", propOrder = {
    "low",
    "high",
    "width"
})
@XmlSeeAlso({
    IVLWidthREAL.class,
    IVLHighREAL.class,
    IVLLowREAL.class
})
public class IVLREAL
    extends QSETREAL
{

    protected REAL low;
    protected REAL high;
    protected QTY width;
    @XmlAttribute
    protected Boolean lowClosed;
    @XmlAttribute
    protected Boolean highClosed;

    /**
     * Gets the value of the low property.
     * 
     * @return
     *     possible object is
     *     {@link REAL }
     *     
     */
    public REAL getLow() {
        return low;
    }

    /**
     * Sets the value of the low property.
     * 
     * @param value
     *     allowed object is
     *     {@link REAL }
     *     
     */
    public void setLow(REAL value) {
        this.low = value;
    }

    /**
     * Gets the value of the high property.
     * 
     * @return
     *     possible object is
     *     {@link REAL }
     *     
     */
    public REAL getHigh() {
        return high;
    }

    /**
     * Sets the value of the high property.
     * 
     * @param value
     *     allowed object is
     *     {@link REAL }
     *     
     */
    public void setHigh(REAL value) {
        this.high = value;
    }

    /**
     * Gets the value of the width property.
     * 
     * @return
     *     possible object is
     *     {@link QTY }
     *     
     */
    public QTY getWidth() {
        return width;
    }

    /**
     * Sets the value of the width property.
     * 
     * @param value
     *     allowed object is
     *     {@link QTY }
     *     
     */
    public void setWidth(QTY value) {
        this.width = value;
    }

    /**
     * Gets the value of the lowClosed property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isLowClosed() {
        return lowClosed;
    }

    /**
     * Sets the value of the lowClosed property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setLowClosed(Boolean value) {
        this.lowClosed = value;
    }

    /**
     * Gets the value of the highClosed property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isHighClosed() {
        return highClosed;
    }

    /**
     * Sets the value of the highClosed property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setHighClosed(Boolean value) {
        this.highClosed = value;
    }

}
