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
 * <p>Java class for IdentifierReliability.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="IdentifierReliability">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ISS"/>
 *     &lt;enumeration value="VRF"/>
 *     &lt;enumeration value="USE"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "IdentifierReliability")
@XmlEnum
public enum IdentifierReliability {

    ISS,
    VRF,
    USE;

    public String value() {
        return name();
    }

    public static IdentifierReliability fromValue(String v) {
        return valueOf(v);
    }

}
