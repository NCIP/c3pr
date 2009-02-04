package edu.duke.cabig.c3pr.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.semanticbits.coppa.domain.annotations.RemoteEntity;
import com.semanticbits.coppa.domain.annotations.RemoteUniqueId;
import com.semanticbits.coppa.infrastructure.service.RemoteResolver;

@RemoteEntity(entityResolver=RemoteResearchStaffResolver.class)
@Entity
@DiscriminatorValue("Remote")
public class RemoteResearchStaff extends ResearchStaff{
	
	private String uniqueIdentifier;
	
	@RemoteUniqueId
	public String getUniqueIdentifier() {
		return uniqueIdentifier;
	}

	public void setUniqueIdentifier(String uniqueIdentifier) {
		this.uniqueIdentifier = uniqueIdentifier;
	}

}
