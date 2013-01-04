/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 *   Distributed under the OSI-approved BSD 3-Clause License.
 *   See https://github.com/NCIP/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.web.security;

import gov.nih.nci.cabig.ctms.web.tabs.Tab;

import org.acegisecurity.Authentication;

public interface TabAuthroizationCheck {
	
	public final String STUDYSITE_SCOPED_AUTHRORIZATION_CHECK="STUDYSITE_SCOPED_AUTHRORIZATION_CHECK";
	public final String COCENTER_SCOPED_AUTHRORIZATION_CHECK="COCENTER_SCOPED_AUTHRORIZATION_CHECK";
	public final String STUDYORGANIZATION_SCOPED_AUTHRORIZATION_CHECK="STUDYORGANIZATION_SCOPED_AUTHRORIZATION_CHECK";
	
	public boolean checkAuthorization(Authentication authentication , String privilege , Tab tab, Object domainObject, String scope);
}
