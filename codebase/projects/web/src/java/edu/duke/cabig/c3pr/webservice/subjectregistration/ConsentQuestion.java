
package edu.duke.cabig.c3pr.webservice.subjectregistration;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import edu.duke.cabig.c3pr.webservice.iso21090.INTPositive;
import edu.duke.cabig.c3pr.webservice.iso21090.ST;


/**
 * <p>Java class for ConsentQuestion complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ConsentQuestion">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="questionNumber" type="{uri:iso.org:21090}INT.Positive"/>
 *         &lt;element name="text" type="{uri:iso.org:21090}ST"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ConsentQuestion", propOrder = {
    "questionNumber",
    "text"
})
public class ConsentQuestion {

    @XmlElement(required = true)
    protected INTPositive questionNumber;
    @XmlElement(required = true)
    protected ST text;

    /**
     * Gets the value of the questionNumber property.
     * 
     * @return
     *     possible object is
     *     {@link INTPositive }
     *     
     */
    public INTPositive getQuestionNumber() {
        return questionNumber;
    }

    /**
     * Sets the value of the questionNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link INTPositive }
     *     
     */
    public void setQuestionNumber(INTPositive value) {
        this.questionNumber = value;
    }

    /**
     * Gets the value of the text property.
     * 
     * @return
     *     possible object is
     *     {@link ST }
     *     
     */
    public ST getText() {
        return text;
    }

    /**
     * Sets the value of the text property.
     * 
     * @param value
     *     allowed object is
     *     {@link ST }
     *     
     */
    public void setText(ST value) {
        this.text = value;
    }

}
