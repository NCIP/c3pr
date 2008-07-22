package edu.duke.cabig.c3pr.service;

public interface RulesDelegationService {
	
	
	public void activateRules(String event, Object obj, Object oldVal, Object newVal);

}
