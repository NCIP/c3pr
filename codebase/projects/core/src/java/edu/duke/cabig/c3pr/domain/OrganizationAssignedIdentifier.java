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
	@Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
    @JoinColumn(name = "hcs_id")
	public HealthcareSite getHealthcareSite() {
		return healthcareSite;
	}

	public void setHealthcareSite(HealthcareSite healthcareSite) {
		this.healthcareSite = healthcareSite;
	}

}
