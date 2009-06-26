package edu.duke.cabig.c3pr.domain;

import java.util.Iterator;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Transient;

import com.semanticbits.coppa.domain.annotations.RemoteEntity;
import com.semanticbits.coppa.domain.annotations.RemoteProperty;
import com.semanticbits.coppa.domain.annotations.RemoteUniqueId;

import edu.duke.cabig.c3pr.constants.CoppaStatusCodeEnum;
import edu.duke.cabig.c3pr.infrastructure.RemoteHealthcareSiteResolver;

/**
 * @author Ramakrishna, Vinay Gangoli
 */

@Entity
@DiscriminatorValue("Remote")
@RemoteEntity(entityResolver = RemoteHealthcareSiteResolver.class)
public class RemoteHealthcareSite extends HealthcareSite {
	
	private String externalId;
	
	private CoppaStatusCodeEnum coppaStatusCode;
	
	@RemoteUniqueId
	@RemoteProperty
	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}
	
	@Transient
    public String getNciInstituteCode() {
		return externalId;
        //return super.getNciInstituteCode();
    }
	
	@RemoteProperty
	@Enumerated(EnumType.STRING)
	public CoppaStatusCodeEnum getCoppaStatusCode() {
		return coppaStatusCode;
	}

	public void setCoppaStatusCode(CoppaStatusCodeEnum coppaStatusCode) {
		this.coppaStatusCode = coppaStatusCode;
	}

}