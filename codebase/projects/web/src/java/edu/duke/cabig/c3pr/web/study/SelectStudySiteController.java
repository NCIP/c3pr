/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 *   Distributed under the OSI-approved BSD 3-Clause License.
 *   See https://github.com/NCIP/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.web.study;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import edu.duke.cabig.c3pr.dao.StudyDao;
import edu.duke.cabig.c3pr.domain.CompanionStudyAssociation;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.utils.StringUtils;

public class SelectStudySiteController extends AbstractController {

	protected StudyDao studyDao;

	public void setStudyDao(StudyDao studyDao) {
		this.studyDao = studyDao;
	}

	public StudyDao getStudyDao() {
		return studyDao;
	}

	public ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap map = new HashMap();
		String studyId = request.getParameter("studyId");
		String parentAssociationId = request.getParameter("parentAssociationId");
		String parentIndex= request.getParameter("parentIndex");

		Study study = studyDao.getById(Integer.parseInt(studyId));
		studyDao.initialize(study);

		if (!StringUtils.isBlank(studyId)) {
			for (CompanionStudyAssociation parentStudyAssociation : study.getParentStudyAssociations()) {
				if (StringUtils.equals(parentAssociationId,parentStudyAssociation.getId().toString())) {
					map.put("shortTitle", parentStudyAssociation.getParentStudy().getShortTitleText());
					map.put("studyId", parentStudyAssociation.getCompanionStudy().getId().toString());
					map.put("associationId", parentStudyAssociation.getId().toString());
					List<HealthcareSite> tempList = new ArrayList<HealthcareSite>();

					for(StudySite studySite : parentStudyAssociation.getStudySites()){
						tempList.add(studySite.getHealthcareSite());
					}
					List<HealthcareSite> healthcareSiteList = new ArrayList<HealthcareSite>();
					for(StudySite studySite : parentStudyAssociation.getParentStudy().getStudySites()){
						if(!tempList.contains(studySite.getHealthcareSite())){
							healthcareSiteList.add(studySite.getHealthcareSite());
						}
					}
					map.put("healthcareSiteList", healthcareSiteList);
					break;
				}
			}
		}
		map.put("parentIndex", parentIndex);
		ModelAndView mav = new ModelAndView("study/selectStudySites", map);
		return mav;
	}
}
