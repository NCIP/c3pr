/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.service;

import java.util.List;

import edu.duke.cabig.c3pr.constants.NotificationEventTypeEnum;

public interface RulesDelegationService {
	
	
	public void activateRules(NotificationEventTypeEnum event, List<Object> objects);

}
