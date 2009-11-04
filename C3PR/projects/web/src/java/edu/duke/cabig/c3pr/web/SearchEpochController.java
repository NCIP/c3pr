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
import edu.duke.cabig.c3pr.dao.StudySiteStudyVersionDao;
import edu.duke.cabig.c3pr.domain.Epoch;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudySiteStudyVersion;

public class SearchEpochController implements Controller {

    private StudyDao studyDao;

    private StudySiteDao studySiteDao;
    
    private StudySiteStudyVersionDao studySiteStudyVersionDao;
    
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
                    throws Exception {

    	String studySiteStudyVersionId = request.getParameter("studySiteStudyVersionId");
        Integer id = Integer.valueOf(studySiteStudyVersionId);
        StudySiteStudyVersion studySiteStudyVersion = studySiteStudyVersionDao.getById(id);
        List<Epoch> epochResults = studySiteStudyVersion.getStudyVersion().getEpochs();
        
        epochResults.size();
        Map<String, List<Epoch>> map = new HashMap<String, List<Epoch>>();
        map.put("epochResults", epochResults);

        return new ModelAndView("/registration/epochResultsAsync", map);

    }
	
    public void setStudySiteStudyVersionDao(
			StudySiteStudyVersionDao studySiteStudyVersionDao) {
		this.studySiteStudyVersionDao = studySiteStudyVersionDao;
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
