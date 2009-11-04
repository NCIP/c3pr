package edu.duke.cabig.c3pr.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.semanticbits.coppa.domain.annotations.RemoteEntity;
import com.semanticbits.coppa.domain.annotations.RemoteProperty;
import com.semanticbits.coppa.domain.annotations.RemoteUniqueId;

import edu.duke.cabig.c3pr.infrastructure.RemoteResearchStaffResolver;

@RemoteEntity(entityResolver=RemoteResearchStaffResolver.class)
@Entity
@DiscriminatorValue("Remote")
public class RemoteResearchStaff extends ResearchStaff{
	
	private String externalId;
	
	@RemoteUniqueId
	@RemoteProperty
	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}
	
}
