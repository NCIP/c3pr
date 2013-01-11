/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name = "REGISTRY_STATUSES")
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "registry_statuses_id_seq") })
public class RegistryStatus extends AbstractMutableDeletableDomainObject{

	private String code;
	
	private String description;
	
	private List<RegistryStatusReason> primaryReasons;
	
	public RegistryStatus() {
		super();
	}

	public RegistryStatus(String code, String description,
			List<RegistryStatusReason> primaryReasons) {
		super();
		this.code = code;
		this.description = description;
		this.primaryReasons = primaryReasons;
	}

	@OneToMany
	@Cascade( { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
	@JoinColumn(name = "registry_st_id")
	public List<RegistryStatusReason> getPrimaryReasons() {
		return primaryReasons;
	}

	public void setPrimaryReasons(List<RegistryStatusReason> primaryReasons) {
		this.primaryReasons = primaryReasons;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Transient
	public RegistryStatusReason getPrimaryReason(String code){
		for(RegistryStatusReason reason : primaryReasons){
			if(reason.getCode().equals(code)){
				return reason;
			}
		}
		return null;
	}
	
	@Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        final RegistryStatus other = (RegistryStatus) obj;
        if (code == null) {
            if (other.code != null) return false;
        }
        else if (!code.equals(other.code)) return false;
        return true;
    }
	
	@Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((code == null) ? 0 : code.hashCode());
        return result;
    }
}
