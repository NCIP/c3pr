package edu.duke.cabig.c3pr.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.semanticbits.coppa.domain.annotations.RemoteEntity;
import com.semanticbits.coppa.domain.annotations.RemoteUniqueId;

import edu.duke.cabig.c3pr.infrastructure.RemoteHealthcareSiteResolver;

/**
 * @author Ramakrishna
 */

@Entity
@DiscriminatorValue("Remote")
@RemoteEntity(entityResolver = RemoteHealthcareSiteResolver.class)
public class RemoteHealthcareSite extends HealthcareSite {
	private String externalId;
	
	@RemoteUniqueId
	public String getExternalId() {
		return externalId;
	}

	public RemoteHealthcareSite() {
		super();
		this.setVersion(0);
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}
	
	@Override
    public String getNciInstituteCode() {
        return super.getNciInstituteCode();
    }

}