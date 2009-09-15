package edu.duke.cabig.c3pr.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.validator.NotNull;

import gov.nih.nci.cabig.ctms.collections.LazyListHelper;

@Entity
@Table(name = "consents", uniqueConstraints = { @UniqueConstraint(columnNames = { "stu_version_id", "name" }) })
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "CONSENTS_ID_SEQ") })
public class Consent extends AbstractMutableDeletableDomainObject implements Comparable<Consent>{

	public int compareTo(Consent o) {
		if (this.equals(o))
			return 0;
		else
			return 1;
	}

	private String name;

	@NotNull
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private LazyListHelper lazyListHelper;

	public Consent(){
		lazyListHelper = new LazyListHelper();
	}

	@Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = PRIME * result + ((name == null) ? 0 : name.hashCode());
        result = PRIME * result;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (getClass() != obj.getClass()) return false;
        final Consent other = (Consent) obj;
        if (name == null) {
            if (other.name != null) return false;
        }
        else if (!name.equalsIgnoreCase(other.name)) return false;
        return true;
    }

}
