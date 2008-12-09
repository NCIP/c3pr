package edu.duke.cabig.c3pr.domain.customfield;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "DATE")
public class DateCustomField extends CustomField {
	
	private Date value ;

	public void setValue(Date value) {
		this.value = value;
	}
	
	@Column(name="date_value")
	public Date getValue() {
		return value;
	}

}
