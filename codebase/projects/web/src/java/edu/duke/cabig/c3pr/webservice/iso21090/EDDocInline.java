
package edu.duke.cabig.c3pr.webservice.iso21090;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ED.Doc.Inline complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ED.Doc.Inline">
 *   &lt;complexContent>
 *     &lt;restriction base="{uri:iso.org:21090}ED.Doc">
 *       &lt;sequence>
 *         &lt;element name="data" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
 *         &lt;element name="xml" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *         &lt;element name="reference" type="{uri:iso.org:21090}TEL" maxOccurs="0" minOccurs="0"/>
 *         &lt;element name="integrityCheck" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
 *         &lt;element name="thumbnail" type="{uri:iso.org:21090}ED" minOccurs="0"/>
 *         &lt;element name="translation" type="{uri:iso.org:21090}ED" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="mediaType" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ED.Doc.Inline")
public class EDDocInline
    extends EDDoc
{


}
