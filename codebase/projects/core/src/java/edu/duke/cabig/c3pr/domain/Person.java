/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.domain;

import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 * This class extends {@link PersonBase} and adds support for person's address. <br>
 * <br> {@link PersonBase} class was introduced during work on CPR-2098. The problem
 * was that we could no longer have address-related fields and other fields,
 * such as first name, in the same base class (used to be just {@link Person}).
 * This became a problem after {@link Participant} was changed to have
 * one-to-many to {@link Address}. The {@link Address} mappings defined in
 * {@link Person} became invalid, and they could not be overridden in
 * {@link Participant} subclass either (Transient annotation does not cancel out
 * persistence mapping defined in a subclass, and OverrideAssociation would not
 * work either). So we had to refactor {@link Person} into {@link Person} and
 * {@link PersonBase} so that {@link Participant} could extend
 * {@link PersonBase} while {@link Investigator}, {@link PersonUser} and
 * others could still use {@link Person}. <br>
 * <br>
 * This will be refactored and corrected once all person-based domain objects
 * are determined to have multiple addresses, I guess.
 * 
 * 
 * @author Priyatam
 * @author dkrylov
 */

@MappedSuperclass
public abstract class Person extends PersonBase {
	private Address address;
	
    @Transient
    public String getLastFirst() {
        StringBuilder name = new StringBuilder();
        boolean hasFirstName = getFirstName() != null;
        if (getLastName() != null) {
            name.append(getLastName());
            if (hasFirstName) {
                name.append(", ");
            }
        }
        if (hasFirstName) {
            name.append(getFirstName());
        }
        return name.toString();
    }

    @Transient
    public String getFullName() {
        StringBuilder name = new StringBuilder();
        boolean hasLastName = getLastName() != null;
        if (getFirstName() != null) {
            name.append(getFirstName());
            if (hasLastName) {
                name.append(' ');
            }
        }
        if (hasLastName) {
            name.append(getLastName());
        }
        return name.toString();
    }
    
	@Transient
	public Address getAddress() {
		if (this.address == null) {
			this.address = new Address();
		}
		return this.address;
	}

	@OneToOne
	@Cascade(value = { CascadeType.ALL })
	@JoinColumn(name = "ADD_ID", nullable = true)
	public Address getAddressInternal() {

		if (this.getAddress().isBlank())
			return null;
		return this.address;

	}

	public void setAddressInternal(Address address) {
		this.address = address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

}
