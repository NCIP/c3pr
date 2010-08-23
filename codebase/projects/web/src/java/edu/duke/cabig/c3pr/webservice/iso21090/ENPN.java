
package edu.duke.cabig.c3pr.webservice.iso21090;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EN.PN complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EN.PN">
 *   &lt;complexContent>
 *     &lt;extension base="{uri:iso.org:21090}EN">
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EN.PN")
public class ENPN
    extends EN
{

	public ENPN() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ENPN(ENXP... part) {
		super(part);
		// TODO Auto-generated constructor stub
	}

	public ENPN(List<ENXP> part, List<EntityNameUse> use) {
		super(part, use);
		// TODO Auto-generated constructor stub
	}


}
