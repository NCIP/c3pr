package edu.duke.cabig.c3pr.web.study;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;

import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.service.StudyService;
import edu.duke.cabig.c3pr.domain.CoordinatingCenterStudyStatus;
import edu.duke.cabig.c3pr.exception.C3PRBaseRuntimeException;

/**
 * Tab that adds no additional refdata or does any processing <p/> Created by
 * IntelliJ IDEA. User: kherm Date: Jun 15, 2007 Time: 3:38:59 PM To change this
 * template use File | Settings | File Templates.
 */
class StudyEmptyTab extends StudyTab {
	protected StudyService studyService;

	public StudyEmptyTab(String longTitle, String shortTitle, String viewName) {
		super(longTitle, shortTitle, viewName);
	}

	@SuppressWarnings("finally")
	@Override
	protected ModelAndView postProcessInPlaceEditing(
			HttpServletRequest request, Study command, String property,
			String value) {
		Map<String, String> map = new HashMap<String, String>();
		CoordinatingCenterStudyStatus statusObject = CoordinatingCenterStudyStatus.getByCode(value);
			
			try {
				studyService.setStatuses(command, statusObject);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
		map.put(getFreeTextModelName(), (command).getCoordinatingCenterStudyStatus().getCode());
		return new ModelAndView("", map);
	}

	public StudyService getStudyService() {
		return studyService;
	}

	public void setStudyService(StudyService studyService) {
		this.studyService = studyService;
	}
}
