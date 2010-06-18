package edu.duke.cabig.c3pr.domain;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name = "Reasons")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "reasons_id_seq") })
public abstract class Reason extends AbstractMutableDeletableDomainObject{

	private String code;
	
	private String description;
	
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
}