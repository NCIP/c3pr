
package edu.duke.cabig.c3pr.webservice.testclient.iso21090;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CalendarCycle.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="CalendarCycle">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="CY"/>
 *     &lt;enumeration value="MY"/>
 *     &lt;enumeration value="CM"/>
 *     &lt;enumeration value="CW"/>
 *     &lt;enumeration value="WY"/>
 *     &lt;enumeration value="DM"/>
 *     &lt;enumeration value="CD"/>
 *     &lt;enumeration value="DY"/>
 *     &lt;enumeration value="DW"/>
 *     &lt;enumeration value="HD"/>
 *     &lt;enumeration value="CH"/>
 *     &lt;enumeration value="NH"/>
 *     &lt;enumeration value="CN"/>
 *     &lt;enumeration value="SN"/>
 *     &lt;enumeration value="CS"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "CalendarCycle")
@XmlEnum
public enum CalendarCycle {

    CY,
    MY,
    CM,
    CW,
    WY,
    DM,
    CD,
    DY,
    DW,
    HD,
    CH,
    NH,
    CN,
    SN,
    CS;

    public String value() {
        return name();
    }

    public static CalendarCycle fromValue(String v) {
        return valueOf(v);
    }

}
