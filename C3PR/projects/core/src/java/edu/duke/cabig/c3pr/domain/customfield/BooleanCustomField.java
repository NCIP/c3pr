package edu.duke.cabig.c3pr.domain.customfield;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "BOOLEAN")
public class BooleanCustomField extends CustomField {
	
	private boolean value ;

	public void setValue(boolean value) {
		this.value = value;
	}
	
	@Column(name="boolean_value")
	public boolean getValue() {
		return value;
	}

}
