/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 *   Distributed under the OSI-approved BSD 3-Clause License.
 *   See https://github.com/NCIP/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Transient;

import com.semanticbits.coppa.domain.annotations.RemoteEntity;
import com.semanticbits.coppa.domain.annotations.RemoteProperty;
import com.semanticbits.coppa.domain.annotations.RemoteUniqueId;

import edu.duke.cabig.c3pr.constants.RemoteSystemStatusCodeEnum;
import edu.duke.cabig.c3pr.infrastructure.RemoteHealthcareSiteResolver;

/**
 * @author Ramakrishna, Vinay Gangoli
 */

@Entity
@DiscriminatorValue("Remote")
@RemoteEntity(entityResolver = RemoteHealthcareSiteResolver.class)
public class RemoteHealthcareSite extends HealthcareSite {
	
	private String externalId;
	
	private RemoteSystemStatusCodeEnum remoteSystemStatusCode;
	
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
	public RemoteSystemStatusCodeEnum getRemoteSystemStatusCode() {
		return remoteSystemStatusCode;
	}

	public void setRemoteSystemStatusCode(
			RemoteSystemStatusCodeEnum remoteSystemStatusCode) {
		this.remoteSystemStatusCode = remoteSystemStatusCode;
	}

}
