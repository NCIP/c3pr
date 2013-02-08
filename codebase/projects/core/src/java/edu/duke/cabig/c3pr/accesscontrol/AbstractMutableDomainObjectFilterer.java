/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.accesscontrol;

import java.util.Iterator;

import org.acegisecurity.AccessDeniedException;

import gov.nih.nci.cabig.ctms.domain.AbstractMutableDomainObject;

public class AbstractMutableDomainObjectFilterer implements Filterer {
	
	private AbstractMutableDomainObject domainObject;
	
	private AbstractMutableDomainObjectFilterer(){}

	public AbstractMutableDomainObjectFilterer(
			AbstractMutableDomainObject domainObject) {
		super();
		this.domainObject = domainObject;
	}


	public Object getFilteredObject() {
		return domainObject;
	}

	public Iterator iterator() {
		return null;
	}

	public void remove(Object object) {
		throw new AccessDeniedException("User does not have access privileges on this study");
	}

}
