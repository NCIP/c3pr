package edu.duke.cabig.c3pr.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import edu.duke.cabig.c3pr.dao.StudyDao;
import edu.duke.cabig.c3pr.dao.StudySiteDao;
import edu.duke.cabig.c3pr.domain.Epoch;
import edu.duke.cabig.c3pr.domain.Study;

public class SearchEpochController implements Controller {

    private StudyDao studyDao;

    private StudySiteDao studySiteDao;

    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
                    throws Exception {

        String studySiteId = request.getParameter("studySiteId");
        Integer id = Integer.valueOf(studySiteId);
        Study study = studySiteDao.getById(id).getStudy();
        List<Epoch> epochResults = study.getEpochs();
        epochResults.size();
        Map<String, List<Epoch>> map = new HashMap<String, List<Epoch>>();
        map.put("epochResults", epochResults);

        return new ModelAndView("/registration/epochResultsAsync", map);

    }

    public StudyDao getStudyDao() {
        return studyDao;
    }

    public void setStudyDao(StudyDao studyDao) {
        this.studyDao = studyDao;
    }

    public StudySiteDao getStudySiteDao() {
        return studySiteDao;
    }

    public void setStudySiteDao(StudySiteDao studySiteDao) {
        this.studySiteDao = studySiteDao;
    }

}
