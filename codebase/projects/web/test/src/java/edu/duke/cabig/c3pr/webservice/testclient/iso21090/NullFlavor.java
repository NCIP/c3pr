
package edu.duke.cabig.c3pr.webservice.testclient.iso21090;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for NullFlavor.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="NullFlavor">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="NI"/>
 *     &lt;enumeration value="INV"/>
 *     &lt;enumeration value="OTH"/>
 *     &lt;enumeration value="NINF"/>
 *     &lt;enumeration value="PINF"/>
 *     &lt;enumeration value="UNC"/>
 *     &lt;enumeration value="DER"/>
 *     &lt;enumeration value="UNK"/>
 *     &lt;enumeration value="ASKU"/>
 *     &lt;enumeration value="NAV"/>
 *     &lt;enumeration value="QS"/>
 *     &lt;enumeration value="NASK"/>
 *     &lt;enumeration value="TRC"/>
 *     &lt;enumeration value="MSK"/>
 *     &lt;enumeration value="NA"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "NullFlavor")
@XmlEnum
public enum NullFlavor {

    NI,
    INV,
    OTH,
    NINF,
    PINF,
    UNC,
    DER,
    UNK,
    ASKU,
    NAV,
    QS,
    NASK,
    TRC,
    MSK,
    NA;

    public String value() {
        return name();
    }

    public static NullFlavor fromValue(String v) {
        return valueOf(v);
    }

}
