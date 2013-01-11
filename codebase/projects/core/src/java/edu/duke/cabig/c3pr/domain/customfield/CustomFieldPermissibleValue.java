/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.domain.customfield;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.log4j.Logger;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import edu.duke.cabig.c3pr.domain.AbstractMutableDeletableDomainObject;

@Entity
@Table(name = "cust_field_perm_values")
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "CUST_FIELD_PERM_VALUES_ID_SEQ") })
public class CustomFieldPermissibleValue extends AbstractMutableDeletableDomainObject {
	
	private String displayName ;
	private String value ;
	private CustomFieldDefinition customFieldDefinition ;
	
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	public void setCustomFieldDefinition(CustomFieldDefinition customFieldDefinition) {
		this.customFieldDefinition = customFieldDefinition;
	}
	
	@ManyToOne
    @JoinColumn(name = "cust_field_def_id", nullable=false)
    @Cascade( { CascadeType.LOCK, CascadeType.SAVE_UPDATE, CascadeType.MERGE})
	public CustomFieldDefinition getCustomFieldDefinition() {
		return customFieldDefinition;
	}
	
	private Logger log = Logger.getLogger(CustomFieldPermissibleValue.class);
	public CustomFieldPermissibleValue(){
	
	}

}
