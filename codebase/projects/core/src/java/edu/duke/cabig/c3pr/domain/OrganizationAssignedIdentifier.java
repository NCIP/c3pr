package edu.duke.cabig.c3pr.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;


@Entity
@DiscriminatorValue("OAI")
public class OrganizationAssignedIdentifier extends Identifier{
	
	private HealthcareSite healthcareSite;
	@ManyToOne
	@JoinColumn(name = "hcs_id")
	public HealthcareSite getHealthcareSite() {
		return healthcareSite;
	}

	public void setHealthcareSite(HealthcareSite healthcareSite) {
		this.healthcareSite = healthcareSite;
	}

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((healthcareSite == null) ? 0 : healthcareSite.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final OrganizationAssignedIdentifier other = (OrganizationAssignedIdentifier) obj;
		if (healthcareSite == null) {
			if (other.healthcareSite != null)
				return false;
		} else if (!healthcareSite.equals(other.healthcareSite))
			return false;
		return true;
	}

}
