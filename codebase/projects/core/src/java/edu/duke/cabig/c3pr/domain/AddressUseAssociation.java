package edu.duke.cabig.c3pr.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import edu.duke.cabig.c3pr.constants.AddressUse;

@Entity
@Table(name = "address_use_assocns")
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "address_use_assocns_id_seq") })
public class AddressUseAssociation extends AbstractMutableDeletableDomainObject {
	private AddressUse use;
	
	public AddressUseAssociation() {
		super();
	}

	public AddressUseAssociation(AddressUse use) {
		super();
		this.use = use;
	}

	@Enumerated(EnumType.STRING)
	public AddressUse getUse() {
		return use;
	}

	public void setUse(AddressUse use) {
		this.use = use;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		// setting hash code to a plain constant value as a safety measure related to CPR-2142.
		// In Participant, bags were changed to sets. 
		return 1;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof AddressUseAssociation)) {
			return false;
		}
		AddressUseAssociation other = (AddressUseAssociation) obj;
		if (use != other.use) {
			return false;
		}
		return true;
	}
}
