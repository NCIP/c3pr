
package edu.duke.cabig.c3pr.webservice.subjectmanagement;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for IdentifierScope.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="IdentifierScope">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="BUSN"/>
 *     &lt;enumeration value="OBJ"/>
 *     &lt;enumeration value="VER"/>
 *     &lt;enumeration value="VW"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "IdentifierScope")
@XmlEnum
public enum IdentifierScope {

    BUSN,
    OBJ,
    VER,
    VW;

    public String value() {
        return name();
    }

    public static IdentifierScope fromValue(String v) {
        return valueOf(v);
    }

}
