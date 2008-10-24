package edu.duke.cabig.c3pr.grid.common;

import javax.xml.namespace.QName;


public interface StudyServiceConstants {
	public static final String SERVICE_NS = "http://grid.c3pr.cabig.duke.edu/StudyService";
	public static final QName RESOURCE_KEY = new QName(SERVICE_NS, "StudyServiceKey");
	public static final QName RESOURCE_PROPERTY_SET = new QName(SERVICE_NS, "StudyServiceResourceProperties");

	//Service level metadata (exposed as resouce properties)
	
}
