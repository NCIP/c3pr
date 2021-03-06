/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.domain.customfield;

import java.util.List;

public interface CustomFieldAuthorable {
	
	/*
	 * Provide Hibernate Annotation   
	 */
	
	public List<CustomFieldDefinition> getCustomFieldDefinitions();
	
}
