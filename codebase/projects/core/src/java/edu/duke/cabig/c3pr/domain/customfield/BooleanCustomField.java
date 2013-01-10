/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/LICENSE.txt for details.
*******************************************************************************/
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
