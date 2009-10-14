package edu.duke.cabig.c3pr.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

import com.semanticbits.coppa.domain.annotations.RemoteEntity;
import com.semanticbits.coppa.domain.annotations.RemoteProperty;
import com.semanticbits.coppa.domain.annotations.RemoteUniqueId;

import edu.duke.cabig.c3pr.constants.OrganizationIdentifierTypeEnum;
import edu.duke.cabig.c3pr.infrastructure.RemoteStudyResolver;
import gov.nih.nci.cabig.ccts.domain.OrganizationAssignedIdentifierType;

/**
 * @author Vinay Gangoli
 */

@Entity
@DiscriminatorValue("Remote")
@RemoteEntity(entityResolver = RemoteStudyResolver.class)
public class RemoteStudy extends Study {
	
	private String externalId;

	public RemoteStudy(){
		super();
	}
	
	public RemoteStudy(boolean forSearchByExample){
		super(forSearchByExample);
	}
	
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
    }
	
	
    /*@Transient
     * public String getAssignedIdentifier() {
		for(OrganizationAssignedIdentifier oai: getOrganizationAssignedIdentifiers()){
			if(oai.getType().equals(OrganizationIdentifierTypeEnum.NCI)){
				return oai.getValue();
			}
		}
		return "";
    } */
	
}