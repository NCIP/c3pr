/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.web.study;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.BaseCommandController;

import edu.duke.cabig.c3pr.dao.StudyVersionDao;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyVersion;

public class ViewAmendmentController extends BaseCommandController {
	
	protected StudyVersionDao studyVersionDao;

    public ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        StudyWrapper wrapper = new StudyWrapper();
        StudyVersion studyVersion = studyVersionDao.getById(Integer.parseInt(request.getParameter("studyVersionId")));
        HashMap<String, StudyWrapper> map = new HashMap<String, StudyWrapper>();
        if (studyVersion != null) {
            Study study = studyVersion.getStudy();
            study.setStudyVersion(studyVersion);
        	wrapper.setStudy(study);
            map.put("command", wrapper);
        }
        ModelAndView mav = new ModelAndView("study/study_view_amendment", map);
        return mav;
    }

	public StudyVersionDao getStudyVersionDao() {
		return studyVersionDao;
	}

	public void setStudyVersionDao(StudyVersionDao studyVersionDao) {
		this.studyVersionDao = studyVersionDao;
	}

    
}
