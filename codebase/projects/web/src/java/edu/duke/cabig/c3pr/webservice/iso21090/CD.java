
package edu.duke.cabig.c3pr.webservice.iso21090;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for CD complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CD">
 *   &lt;complexContent>
 *     &lt;extension base="{uri:iso.org:21090}ANY">
 *       &lt;sequence>
 *         &lt;element name="displayName" type="{uri:iso.org:21090}ST" minOccurs="0"/>
 *         &lt;element name="originalText" type="{uri:iso.org:21090}ED.Text" minOccurs="0"/>
 *         &lt;element name="qualifier" type="{uri:iso.org:21090}CR" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="group" type="{uri:iso.org:21090}CDGroup" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="translation" type="{uri:iso.org:21090}CD" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="source" type="{uri:iso.org:21090}XReference" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="code" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="codeSystem" type="{uri:iso.org:21090}Uid" />
 *       &lt;attribute name="codeSystemName" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="codeSystemVersion" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="valueSet" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="valueSetVersion" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}ID" />
 *       &lt;attribute name="codingRationale" type="{uri:iso.org:21090}set_CodingRationale" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CD", propOrder = {
    "displayName",
    "originalText",
    "qualifier",
    "group",
    "translation",
    "source"
})
@XmlSeeAlso({
    CDCE.class,
    PQR.class
})
public class CD
    extends ANY
{

    protected ST displayName;
    protected EDText originalText;
    protected List<CR> qualifier;
    protected List<CDGroup> group;
    protected List<CD> translation;
    protected XReference source;
    @XmlAttribute
    protected String code;
    @XmlAttribute
    protected String codeSystem;
    @XmlAttribute
    protected String codeSystemName;
    @XmlAttribute
    protected String codeSystemVersion;
    @XmlAttribute
    protected String valueSet;
    @XmlAttribute
    protected String valueSetVersion;
    @XmlAttribute
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected String id;
    @XmlAttribute
    protected List<CodingRationale> codingRationale;

    /**
     * Gets the value of the displayName property.
     * 
     * @return
     *     possible object is
     *     {@link ST }
     *     
     */
    public ST getDisplayName() {
        return displayName;
    }

    /**
     * Sets the value of the displayName property.
     * 
     * @param value
     *     allowed object is
     *     {@link ST }
     *     
     */
    public void setDisplayName(ST value) {
        this.displayName = value;
    }

    /**
     * Gets the value of the originalText property.
     * 
     * @return
     *     possible object is
     *     {@link EDText }
     *     
     */
    public EDText getOriginalText() {
        return originalText;
    }

    /**
     * Sets the value of the originalText property.
     * 
     * @param value
     *     allowed object is
     *     {@link EDText }
     *     
     */
    public void setOriginalText(EDText value) {
        this.originalText = value;
    }

    /**
     * Gets the value of the qualifier property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the qualifier property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getQualifier().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CR }
     * 
     * 
     */
    public List<CR> getQualifier() {
        if (qualifier == null) {
            qualifier = new ArrayList<CR>();
        }
        return this.qualifier;
    }

    /**
     * Gets the value of the group property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the group property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getGroup().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CDGroup }
     * 
     * 
     */
    public List<CDGroup> getGroup() {
        if (group == null) {
            group = new ArrayList<CDGroup>();
        }
        return this.group;
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
     * {@link CD }
     * 
     * 
     */
    public List<CD> getTranslation() {
        if (translation == null) {
            translation = new ArrayList<CD>();
        }
        return this.translation;
    }

    /**
     * Gets the value of the source property.
     * 
     * @return
     *     possible object is
     *     {@link XReference }
     *     
     */
    public XReference getSource() {
        return source;
    }

    /**
     * Sets the value of the source property.
     * 
     * @param value
     *     allowed object is
     *     {@link XReference }
     *     
     */
    public void setSource(XReference value) {
        this.source = value;
    }

    /**
     * Gets the value of the code property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the value of the code property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCode(String value) {
        this.code = value;
    }

    /**
     * Gets the value of the codeSystem property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodeSystem() {
        return codeSystem;
    }

    /**
     * Sets the value of the codeSystem property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodeSystem(String value) {
        this.codeSystem = value;
    }

    /**
     * Gets the value of the codeSystemName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodeSystemName() {
        return codeSystemName;
    }

    /**
     * Sets the value of the codeSystemName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodeSystemName(String value) {
        this.codeSystemName = value;
    }

    /**
     * Gets the value of the codeSystemVersion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodeSystemVersion() {
        return codeSystemVersion;
    }

    /**
     * Sets the value of the codeSystemVersion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodeSystemVersion(String value) {
        this.codeSystemVersion = value;
    }

    /**
     * Gets the value of the valueSet property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValueSet() {
        return valueSet;
    }

    /**
     * Sets the value of the valueSet property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValueSet(String value) {
        this.valueSet = value;
    }

    /**
     * Gets the value of the valueSetVersion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValueSetVersion() {
        return valueSetVersion;
    }

    /**
     * Sets the value of the valueSetVersion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValueSetVersion(String value) {
        this.valueSetVersion = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
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
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the codingRationale property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the codingRationale property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCodingRationale().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CodingRationale }
     * 
     * 
     */
    public List<CodingRationale> getCodingRationale() {
        if (codingRationale == null) {
            codingRationale = new ArrayList<CodingRationale>();
        }
        return this.codingRationale;
    }

}
