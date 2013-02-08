/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.web.registration.tabs;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import edu.duke.cabig.c3pr.web.registration.StudySubjectWrapper;

public class OffEpochConfirmationTab extends RegistrationTab<StudySubjectWrapper> {

	public OffEpochConfirmationTab() {
        super("Confirmation", "Confirmation", "registration/reg_off_epoch_confirm");
    }

	@Override
	public Map referenceData(HttpServletRequest request, StudySubjectWrapper command) {
		Map<String, Object> map = new HashMap<String, Object>();
    	String operationType = request.getParameter(OPERATION_TYPE_PARAM_NAME);
    	map.put("operationType", operationType);
    	return map;
	}
	
}
