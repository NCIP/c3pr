/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.web.study;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.BaseCommandController;

import edu.duke.cabig.c3pr.dao.StudyDao;
import edu.duke.cabig.c3pr.domain.Study;

public class ConfirmStudyController extends BaseCommandController {

    protected StudyDao studyDao;

    public ConfirmStudyController() {
    }


    public ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {


        StudyWrapper wrapper = new StudyWrapper();
        Study study = studyDao.getById(Integer.parseInt(request.getParameter("studyId")));
        HashMap<String, StudyWrapper> map = new HashMap<String, StudyWrapper>();
        if (study != null) {
            wrapper.setStudy(study);
            map.put("command", wrapper);
        }
        ModelAndView mav = new ModelAndView("study/study_confirmation", map);
        return mav;
    }


    public StudyDao getStudyDao() {
        return studyDao;
    }


    public void setStudyDao(StudyDao studyDao) {
        this.studyDao = studyDao;
    }

}
