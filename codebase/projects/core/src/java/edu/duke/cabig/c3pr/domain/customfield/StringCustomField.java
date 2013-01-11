/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.domain.customfield;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "STRING")
public class StringCustomField extends CustomField {
	
	private String value ;

	public void setValue(String value) {
		this.value = value;
	}
	
	@Column(name="string_value")
	public String getValue() {
		return value;
	}

}
