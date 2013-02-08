/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.webservice.iso21090;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PIVL_TS.Date.Full complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PIVL_TS.Date.Full">
 *   &lt;complexContent>
 *     &lt;extension base="{uri:iso.org:21090}QSET_TS.Date.Full">
 *       &lt;sequence>
 *         &lt;element name="phase" type="{uri:iso.org:21090}IVL_TS.Date.Full" minOccurs="0"/>
 *         &lt;element name="period" type="{uri:iso.org:21090}PQ" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="alignment" type="{uri:iso.org:21090}CalendarCycle" />
 *       &lt;attribute name="institutionSpecified" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PIVL_TS.Date.Full", propOrder = {
    "phase",
    "period"
})
public class PIVLTSDateFull
    extends QSETTSDateFull
{

    protected IVLTSDateFull phase;
    protected PQ period;
    @XmlAttribute
    protected CalendarCycle alignment;
    @XmlAttribute
    protected Boolean institutionSpecified;

    /**
     * Gets the value of the phase property.
     * 
     * @return
     *     possible object is
     *     {@link IVLTSDateFull }
     *     
     */
    public IVLTSDateFull getPhase() {
        return phase;
    }

    /**
     * Sets the value of the phase property.
     * 
     * @param value
     *     allowed object is
     *     {@link IVLTSDateFull }
     *     
     */
    public void setPhase(IVLTSDateFull value) {
        this.phase = value;
    }

    /**
     * Gets the value of the period property.
     * 
     * @return
     *     possible object is
     *     {@link PQ }
     *     
     */
    public PQ getPeriod() {
        return period;
    }

    /**
     * Sets the value of the period property.
     * 
     * @param value
     *     allowed object is
     *     {@link PQ }
     *     
     */
    public void setPeriod(PQ value) {
        this.period = value;
    }

    /**
     * Gets the value of the alignment property.
     * 
     * @return
     *     possible object is
     *     {@link CalendarCycle }
     *     
     */
    public CalendarCycle getAlignment() {
        return alignment;
    }

    /**
     * Sets the value of the alignment property.
     * 
     * @param value
     *     allowed object is
     *     {@link CalendarCycle }
     *     
     */
    public void setAlignment(CalendarCycle value) {
        this.alignment = value;
    }

    /**
     * Gets the value of the institutionSpecified property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isInstitutionSpecified() {
        return institutionSpecified;
    }

    /**
     * Sets the value of the institutionSpecified property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setInstitutionSpecified(Boolean value) {
        this.institutionSpecified = value;
    }

}
