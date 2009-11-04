package edu.duke.cabig.c3pr.domain.customfield;

import java.util.List;

public interface CustomFieldAuthorable {
	
	/*
	 * Provide Hibernate Annotation   
	 */
	
	public List<CustomFieldDefinition> getCustomFieldDefinitions();
	
}
