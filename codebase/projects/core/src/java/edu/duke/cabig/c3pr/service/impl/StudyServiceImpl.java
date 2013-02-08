/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.service.impl;

import edu.duke.cabig.c3pr.service.StudyService;
import edu.duke.cabig.c3pr.tools.Configuration;

public class StudyServiceImpl extends WorkflowServiceImpl implements
		StudyService {

	public String getLocalNCIInstituteCode() {
		return this.configuration.get(Configuration.LOCAL_NCI_INSTITUTE_CODE);
	}

	public boolean isStudyOrganizationLocal(String nciInstituteCode) {
		return getLocalNCIInstituteCode().equalsIgnoreCase(nciInstituteCode);
	}

}
