package edu.duke.cabig.c3pr.service;

import java.util.List;

public interface RulesDelegationService {
	
	
	public void activateRules(String event, Object obj, Object oldVal, Object newVal);
	
	public void activateRules(String event, List<Object> objects);

}
