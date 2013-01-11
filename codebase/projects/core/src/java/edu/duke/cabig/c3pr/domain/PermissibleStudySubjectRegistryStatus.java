/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name = "PERMISSIBLE_REG_STATS")
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "PERMISSIBLE_REG_STATS_id_seq") })
public class PermissibleStudySubjectRegistryStatus extends AbstractMutableDeletableDomainObject{

	private RegistryStatus registryStatus;
	
	private List<RegistryStatusReason> secondaryReasons;

	@ManyToOne
	@Cascade(value = { CascadeType.LOCK })
	@JoinColumn(name = "registry_st_id", nullable = false)
	public RegistryStatus getRegistryStatus() {
		return registryStatus;
	}

	public void setRegistryStatus(RegistryStatus registryStatus) {
		this.registryStatus = registryStatus;
	}

	@OneToMany(orphanRemoval=true)
	@Cascade( { CascadeType.ALL})
	@JoinColumn(name = "per_reg_st_id")
	public List<RegistryStatusReason> getSecondaryReasons() {
		return secondaryReasons;
	}

	public void setSecondaryReasons(List<RegistryStatusReason> secondaryReasons) {
		this.secondaryReasons = secondaryReasons;
	}
	
	@Transient
	public RegistryStatusReason getSecondaryReason(String code){
		for(RegistryStatusReason reason : secondaryReasons){
			if(reason.getCode().equals(code)){
				return reason;
			}
		}
		return null;
	}

}
