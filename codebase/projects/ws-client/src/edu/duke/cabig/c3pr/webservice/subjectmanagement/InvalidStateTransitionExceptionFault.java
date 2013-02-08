/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.webservice.subjectmanagement;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import edu.duke.cabig.c3pr.webservice.common.BaseFault;


/**
 * <p>Java class for InvalidStateTransitionExceptionFault complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InvalidStateTransitionExceptionFault">
 *   &lt;complexContent>
 *     &lt;extension base="{http://enterpriseservices.nci.nih.gov/Common}BaseFault">
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InvalidStateTransitionExceptionFault")
public class InvalidStateTransitionExceptionFault
    extends BaseFault
{


}
