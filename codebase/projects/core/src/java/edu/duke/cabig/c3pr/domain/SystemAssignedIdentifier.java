package edu.duke.cabig.c3pr.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;


@Entity
@DiscriminatorValue("SAI")
public class SystemAssignedIdentifier extends Identifier implements Comparable<SystemAssignedIdentifier>{

	private String systemName;

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}
	
	public int compareTo(SystemAssignedIdentifier o) {
		if (this.equals(o)) return 0;
		else return 1;
	}

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = super.hashCode();
		result = PRIME * result + ((systemName == null) ? 0 : systemName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		final SystemAssignedIdentifier other = (SystemAssignedIdentifier) obj;
		if (systemName == null) {
			if (other.systemName != null)
				return false;
		} else if (!systemName.equalsIgnoreCase(other.systemName))
			return false;
		return true;
	}
	
	

}
