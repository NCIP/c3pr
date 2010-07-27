
package edu.duke.cabig.c3pr.webservice.iso21090;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AddressPartType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="AddressPartType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="AL"/>
 *     &lt;enumeration value="ADL"/>
 *     &lt;enumeration value="UNID"/>
 *     &lt;enumeration value="UNIT"/>
 *     &lt;enumeration value="DAL"/>
 *     &lt;enumeration value="DINST"/>
 *     &lt;enumeration value="DINSTA"/>
 *     &lt;enumeration value="DINSTQ"/>
 *     &lt;enumeration value="DMOD"/>
 *     &lt;enumeration value="DMODID"/>
 *     &lt;enumeration value="SAL"/>
 *     &lt;enumeration value="BNR"/>
 *     &lt;enumeration value="BNN"/>
 *     &lt;enumeration value="BNS"/>
 *     &lt;enumeration value="STR"/>
 *     &lt;enumeration value="STB"/>
 *     &lt;enumeration value="STTYP"/>
 *     &lt;enumeration value="DIR"/>
 *     &lt;enumeration value="CAR"/>
 *     &lt;enumeration value="CEN"/>
 *     &lt;enumeration value="CNT"/>
 *     &lt;enumeration value="CPA"/>
 *     &lt;enumeration value="CTY"/>
 *     &lt;enumeration value="DEL"/>
 *     &lt;enumeration value="POB"/>
 *     &lt;enumeration value="PRE"/>
 *     &lt;enumeration value="STA"/>
 *     &lt;enumeration value="ZIP"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "AddressPartType")
@XmlEnum
public enum AddressPartType {

    AL,
    ADL,
    UNID,
    UNIT,
    DAL,
    DINST,
    DINSTA,
    DINSTQ,
    DMOD,
    DMODID,
    SAL,
    BNR,
    BNN,
    BNS,
    STR,
    STB,
    STTYP,
    DIR,
    CAR,
    CEN,
    CNT,
    CPA,
    CTY,
    DEL,
    POB,
    PRE,
    STA,
    ZIP;

    public String value() {
        return name();
    }

    public static AddressPartType fromValue(String v) {
        return valueOf(v);
    }

}
