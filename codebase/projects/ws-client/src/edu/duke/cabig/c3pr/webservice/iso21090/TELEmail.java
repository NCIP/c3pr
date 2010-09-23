
package edu.duke.cabig.c3pr.webservice.iso21090;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TEL.Email complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TEL.Email">
 *   &lt;complexContent>
 *     &lt;restriction base="{uri:iso.org:21090}TEL.Person">
 *       &lt;sequence>
 *         &lt;element name="useablePeriod" type="{uri:iso.org:21090}QSET_TS" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TEL.Email")
public class TELEmail
    extends TELPerson
{


}
