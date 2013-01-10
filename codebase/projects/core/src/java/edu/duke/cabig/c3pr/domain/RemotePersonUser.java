/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.semanticbits.coppa.domain.annotations.RemoteEntity;
import com.semanticbits.coppa.domain.annotations.RemoteProperty;
import com.semanticbits.coppa.domain.annotations.RemoteUniqueId;

import edu.duke.cabig.c3pr.constants.PersonUserType;
import edu.duke.cabig.c3pr.infrastructure.RemoteResearchStaffResolver;

@RemoteEntity(entityResolver=RemoteResearchStaffResolver.class)
@Entity
@DiscriminatorValue("Remote")
public class RemotePersonUser extends PersonUser{
	
	private String externalId;
	
	/** Instantiates a new RemotePersonUser.
	 */
	public RemotePersonUser() {
		super();
	}
	
	/** Instantiates a new RemotePersonUser for a given PersonUserType.
	 */
	public RemotePersonUser(PersonUserType personUserType) {
		super();
		setPersonUserType(personUserType);
	}
	
	@RemoteUniqueId
	@RemoteProperty
	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}
	
}
