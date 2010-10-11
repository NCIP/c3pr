
package edu.duke.cabig.c3pr.webservice.testclient.iso21090;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TS.Date.Full complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TS.Date.Full">
 *   &lt;complexContent>
 *     &lt;restriction base="{uri:iso.org:21090}TS.Date">
 *       &lt;sequence>
 *         &lt;element name="expression" type="{uri:iso.org:21090}ED" minOccurs="0"/>
 *         &lt;element name="originalText" type="{uri:iso.org:21090}ED.Text" minOccurs="0"/>
 *         &lt;element name="uncertainty" type="{uri:iso.org:21090}QTY" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TS.Date.Full")
public class TSDateFull
    extends TSDate
{


}