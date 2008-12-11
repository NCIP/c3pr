package edu.duke.cabig.c3pr.web.registration;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import edu.duke.cabig.c3pr.dao.StudySiteDao;
import edu.duke.cabig.c3pr.domain.Epoch;
import edu.duke.cabig.c3pr.domain.Study;

public class ChangeEpochController extends AbstractController{
	
	private StudySiteDao studySiteDao ;
	
	public StudySiteDao getStudySiteDao() {
		return studySiteDao;
	}

	public void setStudySiteDao(StudySiteDao studySiteDao) {
		this.studySiteDao = studySiteDao;
	}
	
	public ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String studySiteId = request.getParameter("studySite") ; 
		Study companionStudy = studySiteDao.getById(Integer.parseInt(studySiteId)).getStudy();
        List<Epoch> epochs = companionStudy.getEpochs() ;
        
		HashMap map = new HashMap() ;
		map.put("command", epochs);
		map.put("participant", request.getParameter("participant"));
		map.put("studySite", request.getParameter("studySite"));
		map.put("parentRegistrationId", request.getParameter("parentRegistrationId"));
		
		ModelAndView mav = new ModelAndView("registration/createCompanionRegistration", map);
		
		return mav;
	}
}
