/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.domain;

import java.util.ArrayList;
import java.util.List;

import gov.nih.nci.cabig.ctms.domain.AbstractMutableDomainObject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name = "organizations")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "organizations_id_seq") })
public class BaseOrganizationDataContainer extends AbstractMutableDomainObject{
	
	private String name;
	private String descriptionText;
	private String externalId;
	private String dtype;
	@Column(name = "name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescriptionText() {
		return descriptionText;
	}
	public void setDescriptionText(String descriptionText) {
		this.descriptionText = descriptionText;
	}
	
	public String getDtype() {
		return dtype;
	}
	public void setDtype(String dtype) {
		this.dtype = dtype;
	}
	public String getExternalId() {
		return externalId;
	}
	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}
	
    private List<BaseResearchStaffDataContainer> baseResearchStaffDataContainers = new ArrayList<BaseResearchStaffDataContainer>();
    @ManyToMany(mappedBy = "baseOrganizationDataContainers" )
	@Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
    public List<BaseResearchStaffDataContainer> getBaseResearchStaffDataContainers() {
        return baseResearchStaffDataContainers;
    }
    public void setBaseResearchStaffDataContainers(List<BaseResearchStaffDataContainer> baseResearchStaffDataContainers) {
        this.baseResearchStaffDataContainers = baseResearchStaffDataContainers;
    }
    public void addBaseResearchStaffDataContainers(BaseResearchStaffDataContainer rs) {
        getBaseResearchStaffDataContainers().add(rs);
    }
    public void removeBaseResearchStaffDataContainers(BaseResearchStaffDataContainer rs) {
        getBaseResearchStaffDataContainers().remove(rs);
    }
    
}
