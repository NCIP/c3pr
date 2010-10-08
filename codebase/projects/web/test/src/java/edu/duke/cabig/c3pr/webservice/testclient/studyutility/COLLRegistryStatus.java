
package edu.duke.cabig.c3pr.webservice.testclient.studyutility;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import edu.duke.cabig.c3pr.webservice.testclient.iso21090.ANY;


/**
 * <p>Java class for COLL_RegistryStatus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="COLL_RegistryStatus">
 *   &lt;complexContent>
 *     &lt;extension base="{uri:iso.org:21090}ANY">
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "COLL_RegistryStatus", namespace = "http://enterpriseservices.nci.nih.gov/StudyUtilitySchema")
@XmlSeeAlso({
    DSETRegistryStatus.class
})
public abstract class COLLRegistryStatus
    extends ANY
{


}
