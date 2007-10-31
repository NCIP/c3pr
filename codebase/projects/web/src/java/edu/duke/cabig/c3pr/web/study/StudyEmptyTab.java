package edu.duke.cabig.c3pr.web.study;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;

import edu.duke.cabig.c3pr.domain.CoordinatingCenterStudyStatus;
import edu.duke.cabig.c3pr.domain.SiteStudyStatus;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;
import edu.duke.cabig.c3pr.service.StudyService;

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
		String retValue="";
		
		if (property.startsWith("changedSiteStudyStatus")){
			SiteStudyStatus statusObject = SiteStudyStatus.getByCode(value);
			int studySiteIndex = Integer.parseInt(property.split("_")[1]);
			try {
				studyService.setSiteStudyStatus(command,command.getStudySites().get(studySiteIndex),statusObject);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				retValue="<script>alert('"+e.getMessage()+"')</script>";
				e.printStackTrace();
			}finally{
				retValue += command.getStudySites().get(studySiteIndex).getSiteStudyStatus().getCode();
			}
			
		} else{
			CoordinatingCenterStudyStatus statusObject = CoordinatingCenterStudyStatus.getByCode(value);
		
			try {
				studyService.setStatuses(command, statusObject);
				//adding a callback incase the status change is successful
				//this callback is used to dynamically display/hide the amend study button
				retValue="<script>statusChangeCallback('"+command.getCoordinatingCenterStudyStatus().getCode()+"')</script>";
			}catch (Exception e) {
				retValue="<script>alert('"+e.getMessage()+"')</script>";
				e.printStackTrace();
			} finally{
				retValue+=command.getCoordinatingCenterStudyStatus().getCode();
			}
		}
		map.put(getFreeTextModelName(), retValue);
		return new ModelAndView("", map);
	}

	public StudyService getStudyService() {
		return studyService;
	}

	public void setStudyService(StudyService studyService) {
		this.studyService = studyService;
	}
}
