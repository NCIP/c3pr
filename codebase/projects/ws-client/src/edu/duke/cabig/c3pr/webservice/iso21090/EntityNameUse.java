
package edu.duke.cabig.c3pr.webservice.iso21090;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EntityNameUse.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="EntityNameUse">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="C"/>
 *     &lt;enumeration value="I"/>
 *     &lt;enumeration value="L"/>
 *     &lt;enumeration value="P"/>
 *     &lt;enumeration value="A"/>
 *     &lt;enumeration value="R"/>
 *     &lt;enumeration value="PHON"/>
 *     &lt;enumeration value="SNDX"/>
 *     &lt;enumeration value="ABC"/>
 *     &lt;enumeration value="SYL"/>
 *     &lt;enumeration value="IDE"/>
 *     &lt;enumeration value="ASGN"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "EntityNameUse")
@XmlEnum
public enum EntityNameUse {

    C,
    I,
    L,
    P,
    A,
    R,
    PHON,
    SNDX,
    ABC,
    SYL,
    IDE,
    ASGN;

    public String value() {
        return name();
    }

    public static EntityNameUse fromValue(String v) {
        return valueOf(v);
    }

}
