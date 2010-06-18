package edu.duke.cabig.c3pr.web.admin;

import java.util.ArrayList;
import java.util.List;

import edu.duke.cabig.c3pr.constants.C3PRUserGroupType;
import edu.duke.cabig.c3pr.domain.HealthcareSite;

public class HealthcareSiteRolesHolder {
		private HealthcareSite healthcareSite ;
		private List<C3PRUserGroupType> groups = new ArrayList<C3PRUserGroupType>();
		
		public void setGroups(List<C3PRUserGroupType> groups) {
			this.groups = groups;
		}
		public List<C3PRUserGroupType> getGroups() {
			return groups;
		}
		public void setHealthcareSite(HealthcareSite healthcareSite) {
			this.healthcareSite = healthcareSite;
		}
		public HealthcareSite getHealthcareSite() {
			return healthcareSite;
		}
}
