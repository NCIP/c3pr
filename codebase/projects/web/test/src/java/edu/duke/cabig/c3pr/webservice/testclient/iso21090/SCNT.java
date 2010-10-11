
package edu.duke.cabig.c3pr.webservice.testclient.iso21090;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SC.NT complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SC.NT">
 *   &lt;complexContent>
 *     &lt;restriction base="{uri:iso.org:21090}SC">
 *       &lt;sequence>
 *         &lt;element name="translation" type="{uri:iso.org:21090}ST" maxOccurs="0" minOccurs="0"/>
 *         &lt;element name="code" type="{uri:iso.org:21090}CD" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SC.NT")
public class SCNT
    extends SC
{


}