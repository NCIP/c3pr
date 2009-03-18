package edu.duke.cabig.c3pr.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.ParameterizableViewController;

import edu.duke.cabig.c3pr.dao.ParticipantDao;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.utils.StringUtils;

public class ConfirmCreateParticipantController extends ParameterizableViewController {
	
	public ParticipantDao participantDao ;
	
    public ConfirmCreateParticipantController() {
        setViewName("participant/create_participant_confirmation");
    }

    public ModelAndView handleRequestInternal(HttpServletRequest request,
                    HttpServletResponse response) throws Exception {
    	Map map = new HashMap();
    	Participant command = null ;
    	String participantId = request.getParameter("participantId");
    	if(!StringUtils.isBlank(participantId)){
    		command = participantDao.getById(Integer.parseInt(participantId));
    	}
        map.put("command", command);
    	setViewName("participant/create_participant_confirmation");
        ModelAndView mav = new ModelAndView("participant/create_participant_confirmation", map);

        return mav;
    }

	public ParticipantDao getParticipantDao() {
		return participantDao;
	}

	public void setParticipantDao(ParticipantDao participantDao) {
		this.participantDao = participantDao;
	}
}
