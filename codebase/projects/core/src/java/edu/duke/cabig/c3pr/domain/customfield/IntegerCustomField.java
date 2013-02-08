/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/c3pr/LICENSE.txt for details.
*******************************************************************************/
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
