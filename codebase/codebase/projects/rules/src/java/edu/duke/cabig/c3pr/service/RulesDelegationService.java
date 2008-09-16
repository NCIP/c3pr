package edu.duke.cabig.c3pr.service;

import java.util.List;

import edu.duke.cabig.c3pr.constants.NotificationEventTypeEnum;

public interface RulesDelegationService {
	
	
	public void activateRules(NotificationEventTypeEnum event, List<Object> objects);

}
