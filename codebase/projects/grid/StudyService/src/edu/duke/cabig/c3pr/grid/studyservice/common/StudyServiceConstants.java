/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.grid.studyservice.common;

import javax.xml.namespace.QName;


public interface StudyServiceConstants {
	public static final String SERVICE_NS = "http://studyservice.grid.c3pr.cabig.duke.edu/StudyService";
	public static final QName RESOURCE_KEY = new QName(SERVICE_NS, "StudyServiceKey");
	public static final QName RESOURCE_PROPERTY_SET = new QName(SERVICE_NS, "StudyServiceResourceProperties");

	//Service level metadata (exposed as resouce properties)
	
}
