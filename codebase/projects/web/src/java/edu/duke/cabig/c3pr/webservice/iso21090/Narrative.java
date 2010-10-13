
package edu.duke.cabig.c3pr.webservice.iso21090;

import java.util.ArrayList;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlMixed;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for Narrative complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Narrative">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice maxOccurs="unbounded" minOccurs="0">
 *         &lt;element ref="{uri:iso.org:21090}content"/>
 *         &lt;element ref="{uri:iso.org:21090}linkHtml"/>
 *         &lt;element ref="{uri:iso.org:21090}sub"/>
 *         &lt;element ref="{uri:iso.org:21090}sup"/>
 *         &lt;element ref="{uri:iso.org:21090}br"/>
 *         &lt;element ref="{uri:iso.org:21090}footnote"/>
 *         &lt;element ref="{uri:iso.org:21090}footnoteRef"/>
 *         &lt;element ref="{uri:iso.org:21090}renderMultiMedia"/>
 *         &lt;element ref="{uri:iso.org:21090}paragraph"/>
 *         &lt;element ref="{uri:iso.org:21090}list"/>
 *         &lt;element ref="{uri:iso.org:21090}table"/>
 *       &lt;/choice>
 *       &lt;attribute name="ID" type="{http://www.w3.org/2001/XMLSchema}ID" />
 *       &lt;attribute name="styleCode" type="{http://www.w3.org/2001/XMLSchema}NMTOKENS" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Narrative", propOrder = {
    "content"
})
public class Narrative {

    @XmlElementRefs({
        @XmlElementRef(name = "linkHtml", namespace = "uri:iso.org:21090", type = LinkHtml.class),
        @XmlElementRef(name = "table", namespace = "uri:iso.org:21090", type = Table.class),
        @XmlElementRef(name = "footnoteRef", namespace = "uri:iso.org:21090", type = FootnoteRef.class),
        @XmlElementRef(name = "content", namespace = "uri:iso.org:21090", type = Content.class),
        @XmlElementRef(name = "sub", namespace = "uri:iso.org:21090", type = JAXBElement.class),
        @XmlElementRef(name = "sup", namespace = "uri:iso.org:21090", type = JAXBElement.class),
        @XmlElementRef(name = "renderMultiMedia", namespace = "uri:iso.org:21090", type = RenderMultiMedia.class),
        @XmlElementRef(name = "br", namespace = "uri:iso.org:21090", type = Br.class),
        @XmlElementRef(name = "list", namespace = "uri:iso.org:21090", type = edu.duke.cabig.c3pr.webservice.iso21090.List.class),
        @XmlElementRef(name = "footnote", namespace = "uri:iso.org:21090", type = Footnote.class),
        @XmlElementRef(name = "paragraph", namespace = "uri:iso.org:21090", type = Paragraph.class)
    })
    @XmlMixed
    protected java.util.List<Object> content;
    @XmlAttribute(name = "ID")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected String id;
    @XmlAttribute
    @XmlSchemaType(name = "NMTOKENS")
    protected java.util.List<String> styleCode;

    /**
     * Gets the value of the content property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the content property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getContent().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link LinkHtml }
     * {@link Content }
     * {@link FootnoteRef }
     * {@link Table }
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link String }
     * {@link RenderMultiMedia }
     * {@link Br }
     * {@link Footnote }
     * {@link edu.duke.cabig.c3pr.webservice.iso21090.List }
     * {@link Paragraph }
     * 
     * 
     */
    public java.util.List<Object> getContent() {
        if (content == null) {
            content = new ArrayList<Object>();
        }
        return this.content;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getID() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setID(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the styleCode property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the styleCode property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getStyleCode().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public java.util.List<String> getStyleCode() {
        if (styleCode == null) {
            styleCode = new ArrayList<String>();
        }
        return this.styleCode;
    }

}
