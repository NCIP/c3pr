/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.web.registration.tabs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;

import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.AjaxableUtils;
import edu.duke.cabig.c3pr.web.registration.RegistrationControllerUtils;
import edu.duke.cabig.c3pr.web.registration.StudySubjectWrapper;

public class CompanionRegistrationTab<C extends StudySubjectWrapper> extends RegistrationTab<C> {
	
	private RegistrationControllerUtils registrationControllerUtils;
	
	public CompanionRegistrationTab() {
		super("Companion Registrations", "Companion Registrations", "registration/reg_companion_reg");
	}
	
	@Override
	public Map<String,Object> referenceData(HttpServletRequest request, StudySubjectWrapper wrapper) {
		Map map = new HashMap();
		map.put("companions", registrationControllerUtils.getCompanionStudySubject(wrapper.getStudySubject().getSystemAssignedIdentifiers().get(0),  wrapper.getStudySubject()));
		return map;
	}

	public void setRegistrationControllerUtils(
			RegistrationControllerUtils registrationControllerUtils) {
		this.registrationControllerUtils = registrationControllerUtils;
	}
	
	public ModelAndView removeChildStudySubject(HttpServletRequest request,Object command, Errors errors) {
		StudySubjectWrapper wrapper = (StudySubjectWrapper) command ;
		String regId = request.getParameter("childStudySubjectId");
		StudySubject childStudySubject = studySubjectDao.getById(Integer.parseInt(regId)) ;
    	StudySubject studySubject=studySubjectRepository.getUniqueStudySubject(wrapper.getStudySubject().getUniqueIdentifier());
    	studySubject.removeChildStudySubject(childStudySubject);
    	studySubjectDao.initialize(studySubject);
    	wrapper.setStudySubject(studySubject);
		return new ModelAndView(AjaxableUtils.getAjaxViewName(request));
	}
	
	public ModelAndView refreshCompanionSection(HttpServletRequest request,Object command, Errors errors) {
		StudySubjectWrapper wrapper = (StudySubjectWrapper) command ;
    	StudySubject studySubject=studySubjectRepository.getUniqueStudySubject(wrapper.getStudySubject().getUniqueIdentifier());
    	studySubjectDao.initialize(studySubject);
    	wrapper.setStudySubject(studySubject);
		return new ModelAndView(AjaxableUtils.getAjaxViewName(request));
	}
}
