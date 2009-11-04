package edu.duke.cabig.c3pr.grid.accrualservice.common;

import javax.xml.namespace.QName;


public interface AccrualServiceConstants {
	public static final String SERVICE_NS = "http://accrualservice.grid.c3pr.cabig.duke.edu/AccrualService";
	public static final QName RESOURCE_KEY = new QName(SERVICE_NS, "AccrualServiceKey");
	public static final QName RESOURCE_PROPERTY_SET = new QName(SERVICE_NS, "AccrualServiceResourceProperties");

	//Service level metadata (exposed as resouce properties)
	
}
