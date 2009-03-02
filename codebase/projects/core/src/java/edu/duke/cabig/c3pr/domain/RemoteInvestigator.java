package edu.duke.cabig.c3pr.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.semanticbits.coppa.domain.annotations.RemoteEntity;
import com.semanticbits.coppa.domain.annotations.RemoteUniqueId;

import edu.duke.cabig.c3pr.infrastructure.RemoteInvestigatorResolver;

@RemoteEntity(entityResolver=RemoteInvestigatorResolver.class)
@Entity
@DiscriminatorValue("Remote")
public class RemoteInvestigator extends Investigator{
	
	private String uniqueIdentifier;
	
	@RemoteUniqueId
	public String getUniqueIdentifier() {
		return uniqueIdentifier;
	}

	public void setUniqueIdentifier(String uniqueIdentifier) {
		this.uniqueIdentifier = uniqueIdentifier;
	}

}
