
package edu.duke.cabig.c3pr.webservice.subjectmanagement;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for IVL.Width_TS.DateTime.Full complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IVL.Width_TS.DateTime.Full">
 *   &lt;complexContent>
 *     &lt;restriction base="{uri:iso.org:21090}IVL_TS.DateTime.Full">
 *       &lt;sequence>
 *         &lt;element name="originalText" type="{uri:iso.org:21090}ED.Text" minOccurs="0"/>
 *         &lt;element name="low" type="{uri:iso.org:21090}TS.DateTime.Full" maxOccurs="0" minOccurs="0"/>
 *         &lt;element name="high" type="{uri:iso.org:21090}TS.DateTime.Full" maxOccurs="0" minOccurs="0"/>
 *         &lt;element name="width" type="{uri:iso.org:21090}QTY" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IVL.Width_TS.DateTime.Full")
public class IVLWidthTSDateTimeFull
    extends IVLTSDateTimeFull
{


}