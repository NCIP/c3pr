/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.utils.extensions;

import gov.nih.nci.cabig.ctms.domain.DomainObject;

public class DomainSubClass implements DomainObject{

	private StudySiteSubClass studySiteSubClass;
	public Integer getId() {
		return null;
	}

	public void setId(Integer integer) {
		
	}

	public StudySiteSubClass getStudySiteSubClass() {
		return studySiteSubClass;
	}

	public void setStudySiteSubClass(StudySiteSubClass studySiteSubClass) {
		this.studySiteSubClass = studySiteSubClass;
	}

	
}
