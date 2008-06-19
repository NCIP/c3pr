package edu.duke.cabig.c3pr.service.impl;

import edu.duke.cabig.c3pr.service.RulesDelegationService;

public class RulesDelegationServiceImpl implements RulesDelegationService{

	public static final String NEW_STUDY_SAVED_EVENT = "NEW_STUDY_SAVED_EVENT";
	
	public static final String NEW_STUDY_SITE_SAVED_EVENT = "NEW_STUDY_SITE_SAVED_EVENT";
	
	public static final String STUDY_STATUS_CHANGE_EVENT = "STUDY_STATUS_CHANGE_EVENT";
	
	public static final String STUDY_SITE_STATUS_CHANGE_EVENT = "STUDY_SITE_STATUS_CHANGE_EVENT";
	
	public static final String REGISTRATION_EVENT = "REGISTRATION_EVENT";
	
	public void activateRules(String event, Object oldVal, Object newVal){
		
	}
	
}
