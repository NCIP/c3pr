package edu.duke.cabig.c3pr.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import edu.duke.cabig.c3pr.constants.ContactMechanismUse;

@Entity
@Table(name = "contact_use_assocns")
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "contact_use_assocns_id_seq") })
public class ContactMechanismUseAssociation extends AbstractMutableDeletableDomainObject {
	private ContactMechanismUse use;
	
	public ContactMechanismUseAssociation() {
		super();
	}

	public ContactMechanismUseAssociation(ContactMechanismUse use) {
		super();
		this.use = use;
	}

	@Enumerated(EnumType.STRING)
	public ContactMechanismUse getUse() {
		return use;
	}

	public void setUse(ContactMechanismUse use) {
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
		if (!(obj instanceof ContactMechanismUseAssociation)) {
			return false;
		}
		ContactMechanismUseAssociation other = (ContactMechanismUseAssociation) obj;
		if (use != other.use) {
			return false;
		}
		return true;
	}
}
