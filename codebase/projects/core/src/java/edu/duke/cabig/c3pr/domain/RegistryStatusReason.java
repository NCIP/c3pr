/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

// TODO: Auto-generated Javadoc
@Entity
@DiscriminatorValue("REGISTRY_STATUS")
public class RegistryStatusReason extends Reason {
	public RegistryStatusReason() {
		super();
	}

	public RegistryStatusReason(String code, String description,
			Reason primaryReason, Boolean primaryIndicator) {
		super(code, description, primaryReason, primaryIndicator);
	}
	
}
