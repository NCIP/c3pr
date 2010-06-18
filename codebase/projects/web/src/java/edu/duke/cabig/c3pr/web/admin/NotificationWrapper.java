package edu.duke.cabig.c3pr.web.admin;

import java.util.ArrayList;
import java.util.List;

import edu.duke.cabig.c3pr.domain.HealthcareSite;

public class NotificationWrapper {
	
	private List<HealthcareSite> healthcareSites = new ArrayList<HealthcareSite>();

	public List<HealthcareSite> getHealthcareSites() {
		return healthcareSites;
	}

	public void setHealthcareSites(List<HealthcareSite> healthcareSites) {
		this.healthcareSites = healthcareSites;
	}
	
}
