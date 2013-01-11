/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.domain;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name = "Reasons")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "reasons_id_seq") })
public abstract class Reason extends AbstractMutableDeletableDomainObject{

	private String code;
	
	private String description;
	
	private Reason primaryReason;
	
	private Boolean primaryIndicator;
	
	public Reason(String code, String description, Reason primaryReason,
			Boolean primaryIndicator) {
		super();
		this.code = code;
		this.description = description;
		this.primaryReason = primaryReason;
		this.primaryIndicator = primaryIndicator;
	}
	
	public Reason() {
		super();
	}

	@ManyToOne
	@Cascade(value = { CascadeType.LOCK })
	@JoinColumn(name = "parent_id")
	public Reason getPrimaryReason() {
		return primaryReason;
	}

	public void setPrimaryReason(Reason primaryReason) {
		this.primaryReason = primaryReason;
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
	
	@Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        final Reason other = (Reason) obj;
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

	public Boolean getPrimaryIndicator() {
		return primaryIndicator;
	}

	public void setPrimaryIndicator(Boolean primaryIndicator) {
		this.primaryIndicator = primaryIndicator;
	}
}
