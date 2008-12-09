package edu.duke.cabig.c3pr.domain.customfield;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "INT")
public class IntegerCustomField extends CustomField {
	
	private int value ;

	public void setValue(int value) {
		this.value = value;
	}
	
	@Column(name="integer_value")
	public int getValue() {
		return value;
	}

}
