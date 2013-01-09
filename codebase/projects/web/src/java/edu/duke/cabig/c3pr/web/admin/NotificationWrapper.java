/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.web.admin;

import edu.duke.cabig.c3pr.domain.HealthcareSite;

public class NotificationWrapper {
	
private HealthcareSite healthcareSite;

public HealthcareSite getHealthcareSite() {
	return healthcareSite;
}

public void setHealthcareSite(HealthcareSite healthcareSite) {
	this.healthcareSite = healthcareSite;
}


}
