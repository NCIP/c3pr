/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.webservice.iso21090;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PostalAddressUse.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="PostalAddressUse">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="H"/>
 *     &lt;enumeration value="HP"/>
 *     &lt;enumeration value="HV"/>
 *     &lt;enumeration value="WP"/>
 *     &lt;enumeration value="DIR"/>
 *     &lt;enumeration value="PUB"/>
 *     &lt;enumeration value="BAD"/>
 *     &lt;enumeration value="TMP"/>
 *     &lt;enumeration value="ABC"/>
 *     &lt;enumeration value="IDE"/>
 *     &lt;enumeration value="SYL"/>
 *     &lt;enumeration value="PHYS"/>
 *     &lt;enumeration value="PST"/>
 *     &lt;enumeration value="SNDX"/>
 *     &lt;enumeration value="PHON"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "PostalAddressUse")
@XmlEnum
public enum PostalAddressUse {

    H,
    HP,
    HV,
    WP,
    DIR,
    PUB,
    BAD,
    TMP,
    ABC,
    IDE,
    SYL,
    PHYS,
    PST,
    SNDX,
    PHON;

    public String value() {
        return name();
    }

    public static PostalAddressUse fromValue(String v) {
        return valueOf(v);
    }

}
