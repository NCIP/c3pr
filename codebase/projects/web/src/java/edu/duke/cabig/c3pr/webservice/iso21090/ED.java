
package edu.duke.cabig.c3pr.webservice.iso21090;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ED complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ED">
 *   &lt;complexContent>
 *     &lt;extension base="{uri:iso.org:21090}ANY">
 *       &lt;sequence>
 *         &lt;element name="data" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
 *         &lt;element name="xml" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *         &lt;element name="reference" type="{uri:iso.org:21090}TEL" minOccurs="0"/>
 *         &lt;element name="integrityCheck" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
 *         &lt;element name="thumbnail" type="{uri:iso.org:21090}ED" minOccurs="0"/>
 *         &lt;element name="translation" type="{uri:iso.org:21090}ED" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="value" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="mediaType" type="{http://www.w3.org/2001/XMLSchema}string" default="text/plain" />
 *       &lt;attribute name="charset" type="{uri:iso.org:21090}Code" />
 *       &lt;attribute name="language" type="{uri:iso.org:21090}Code" />
 *       &lt;attribute name="compression" type="{uri:iso.org:21090}Compression" />
 *       &lt;attribute name="integrityCheckAlgorithm" type="{uri:iso.org:21090}IntegrityCheckAlgorithm" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ED", propOrder = {
    "data",
    "xml",
    "reference",
    "integrityCheck",
    "thumbnail",
    "translation"
})
@XmlSeeAlso({
    EDText.class,
    EDNarrative.class,
    EDSignature.class,
    EDImage.class,
    EDDoc.class
})
public class ED
    extends ANY
{

    protected byte[] data;
    protected Object xml;
    protected TEL reference;
    protected byte[] integrityCheck;
    protected ED thumbnail;
    protected List<ED> translation;
    @XmlAttribute
    protected String value;
    @XmlAttribute
    protected String mediaType;
    @XmlAttribute
    protected String charset;
    @XmlAttribute
    protected String language;
    @XmlAttribute
    protected Compression compression;
    @XmlAttribute
    protected IntegrityCheckAlgorithm integrityCheckAlgorithm;
    
    public ED() {		
	}

    public ED(String value) {
		super();
		this.value = value;
	}

	/**
     * Gets the value of the data property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getData() {
        return data;
    }

    /**
     * Sets the value of the data property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setData(byte[] value) {
        this.data = ((byte[]) value);
    }

    /**
     * Gets the value of the xml property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getXml() {
        return xml;
    }

    /**
     * Sets the value of the xml property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setXml(Object value) {
        this.xml = value;
    }

    /**
     * Gets the value of the reference property.
     * 
     * @return
     *     possible object is
     *     {@link TEL }
     *     
     */
    public TEL getReference() {
        return reference;
    }

    /**
     * Sets the value of the reference property.
     * 
     * @param value
     *     allowed object is
     *     {@link TEL }
     *     
     */
    public void setReference(TEL value) {
        this.reference = value;
    }

    /**
     * Gets the value of the integrityCheck property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getIntegrityCheck() {
        return integrityCheck;
    }

    /**
     * Sets the value of the integrityCheck property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setIntegrityCheck(byte[] value) {
        this.integrityCheck = ((byte[]) value);
    }

    /**
     * Gets the value of the thumbnail property.
     * 
     * @return
     *     possible object is
     *     {@link ED }
     *     
     */
    public ED getThumbnail() {
        return thumbnail;
    }

    /**
     * Sets the value of the thumbnail property.
     * 
     * @param value
     *     allowed object is
     *     {@link ED }
     *     
     */
    public void setThumbnail(ED value) {
        this.thumbnail = value;
    }

    /**
     * Gets the value of the translation property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the translation property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTranslation().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ED }
     * 
     * 
     */
    public List<ED> getTranslation() {
        if (translation == null) {
            translation = new ArrayList<ED>();
        }
        return this.translation;
    }

    /**
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Gets the value of the mediaType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMediaType() {
        if (mediaType == null) {
            return "text/plain";
        } else {
            return mediaType;
        }
    }

    /**
     * Sets the value of the mediaType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMediaType(String value) {
        this.mediaType = value;
    }

    /**
     * Gets the value of the charset property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCharset() {
        return charset;
    }

    /**
     * Sets the value of the charset property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCharset(String value) {
        this.charset = value;
    }

    /**
     * Gets the value of the language property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Sets the value of the language property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLanguage(String value) {
        this.language = value;
    }

    /**
     * Gets the value of the compression property.
     * 
     * @return
     *     possible object is
     *     {@link Compression }
     *     
     */
    public Compression getCompression() {
        return compression;
    }

    /**
     * Sets the value of the compression property.
     * 
     * @param value
     *     allowed object is
     *     {@link Compression }
     *     
     */
    public void setCompression(Compression value) {
        this.compression = value;
    }

    /**
     * Gets the value of the integrityCheckAlgorithm property.
     * 
     * @return
     *     possible object is
     *     {@link IntegrityCheckAlgorithm }
     *     
     */
    public IntegrityCheckAlgorithm getIntegrityCheckAlgorithm() {
        return integrityCheckAlgorithm;
    }

    /**
     * Sets the value of the integrityCheckAlgorithm property.
     * 
     * @param value
     *     allowed object is
     *     {@link IntegrityCheckAlgorithm }
     *     
     */
    public void setIntegrityCheckAlgorithm(IntegrityCheckAlgorithm value) {
        this.integrityCheckAlgorithm = value;
    }

}
