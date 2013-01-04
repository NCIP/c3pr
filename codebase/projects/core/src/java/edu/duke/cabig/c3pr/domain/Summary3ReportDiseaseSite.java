/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 *   Distributed under the OSI-approved BSD 3-Clause License.
 *   See https://github.com/NCIP/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.domain;

import gov.nih.nci.cabig.ctms.domain.AbstractMutableDomainObject;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="summ3_rep_disease_sites")
@GenericGenerator(name = "id-generator", strategy = "assigned")
public class Summary3ReportDiseaseSite extends AbstractMutableDomainObject{
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private String name;
}
