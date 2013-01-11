/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.utils;

import edu.duke.cabig.c3pr.constants.SortOrder;

public class SortParameter{
	private String contextObjectName;
	
	private String objectName;
	
	private String attributeName;
	
	private SortOrder sortOrder = SortOrder.ASCENDING;
	
	public static final SortParameter studyShortTitle = new SortParameter(null,"edu.duke.cabig.c3pr.domain.StudyVersion","shortTitleText",null);
	public static final SortParameter studyIdentifierValue = new SortParameter("Study","edu.duke.cabig.c3pr.domain.Identifier","value",null);
	public static final SortParameter studyIdentifierType = new SortParameter("Study","edu.duke.cabig.c3pr.domain.Identifier","type",null);
	public static final SortParameter studySubjectIdentifierValue = new SortParameter("StudySubject","edu.duke.cabig.c3pr.domain.Identifier","value",null);
	public static final SortParameter studySubjectIdentifierType = new SortParameter("StudySubject","edu.duke.cabig.c3pr.domain.Identifier","type",null);
	public static final SortParameter subjectIdentifierValue = new SortParameter("Subject","edu.duke.cabig.c3pr.domain.Identifier","value",null);
	public static final SortParameter subjectIdentifierType = new SortParameter("Subject","edu.duke.cabig.c3pr.domain.Identifier","type",null);
	public static final SortParameter subjectLastName = new SortParameter(null,"edu.duke.cabig.c3pr.domain.StudySubjectDemographics","lastName",null);
	
	public String getContextObjectName() {
		return contextObjectName;
	}

	public void setContextObjectName(String contextObjectName) {
		this.contextObjectName = contextObjectName;
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public String getAttributeName() {
		return attributeName;
	}

	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	public SortParameter(String contextObjectName, String objectName, String attributeName, SortOrder sortOrder){
		this.setContextObjectName(contextObjectName);
		this.setObjectName(objectName);
		this.setAttributeName(attributeName);
		if(sortOrder != null)
		this.setSortOrder(sortOrder);
	}

	public SortOrder getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(SortOrder sortOrder) {
		this.sortOrder = sortOrder;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((attributeName == null) ? 0 : attributeName.hashCode());
		result = prime
				* result
				+ ((contextObjectName == null) ? 0 : contextObjectName
						.hashCode());
		result = prime * result
				+ ((objectName == null) ? 0 : objectName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SortParameter other = (SortParameter) obj;
		if (attributeName == null) {
			if (other.attributeName != null)
				return false;
		} else if (!attributeName.equals(other.attributeName))
			return false;
		if (StringUtils.isBlank(contextObjectName)) {
			if (!StringUtils.isBlank(other.contextObjectName))
				return false;
		} else if (!contextObjectName.equals(other.contextObjectName))
			return false;
		if (objectName == null) {
			if (other.objectName != null)
				return false;
		} else if (!objectName.equals(other.objectName))
			return false;
		return true;
	}
	
	

	
}
