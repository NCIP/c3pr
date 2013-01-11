/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.grid.registrationservice.common;

import javax.xml.namespace.QName;


public interface RegistrationServiceConstants {
	public static final String SERVICE_NS = "http://registrationservice.grid.c3pr.cabig.duke.edu/RegistrationService";
	public static final QName RESOURCE_KEY = new QName(SERVICE_NS, "RegistrationServiceKey");
	public static final QName RESOURCE_PROPERTY_SET = new QName(SERVICE_NS, "RegistrationServiceResourceProperties");

	//Service level metadata (exposed as resouce properties)
	
}
