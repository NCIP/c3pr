package edu.duke.cabig.c3pr.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigurationProperty {
	
	// Temporary lov values to hold the data pertaining to CADsr
	// TODO remove the lovMap once a CaDsr service is in place
	private Map map  = new HashMap<String, List<Lov>>();
		
	public Map getMap() {
		return map;
	}
	public void setMap(Map map) {
		this.map = map;
	}	
}
