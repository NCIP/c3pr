
package edu.duke.cabig.c3pr.webservice.subjectmanagement;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for UncertaintyType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="UncertaintyType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="U"/>
 *     &lt;enumeration value="N"/>
 *     &lt;enumeration value="LN"/>
 *     &lt;enumeration value="G"/>
 *     &lt;enumeration value="E"/>
 *     &lt;enumeration value="X2"/>
 *     &lt;enumeration value="T"/>
 *     &lt;enumeration value="F"/>
 *     &lt;enumeration value="B"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "UncertaintyType")
@XmlEnum
public enum UncertaintyType {

    U("U"),
    N("N"),
    LN("LN"),
    G("G"),
    E("E"),
    @XmlEnumValue("X2")
    X_2("X2"),
    T("T"),
    F("F"),
    B("B");
    private final String value;

    UncertaintyType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static UncertaintyType fromValue(String v) {
        for (UncertaintyType c: UncertaintyType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
