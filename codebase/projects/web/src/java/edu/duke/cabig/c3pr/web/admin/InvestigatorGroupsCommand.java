package edu.duke.cabig.c3pr.web.admin;

import edu.duke.cabig.c3pr.domain.AbstractMutableDeletableDomainObject;
import edu.duke.cabig.c3pr.domain.HealthcareSite;

public class InvestigatorGroupsCommand extends AbstractMutableDeletableDomainObject{
	private HealthcareSite healthcareSite;

	public HealthcareSite getHealthcareSite() {
		return healthcareSite;
	}

	public void setHealthcareSite(HealthcareSite healthcareSite) {
		this.healthcareSite = healthcareSite;
	}

}
