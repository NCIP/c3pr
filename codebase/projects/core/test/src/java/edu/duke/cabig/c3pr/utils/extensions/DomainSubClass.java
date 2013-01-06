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
