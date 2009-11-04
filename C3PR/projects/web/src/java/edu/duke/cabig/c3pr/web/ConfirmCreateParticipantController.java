package edu.duke.cabig.c3pr.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.ParameterizableViewController;
import org.springframework.web.util.WebUtils;

import edu.duke.cabig.c3pr.dao.ParticipantDao;
import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.repository.ParticipantRepository;
import edu.duke.cabig.c3pr.utils.web.ControllerTools;

public class ConfirmCreateParticipantController extends ParameterizableViewController {
	
	public ParticipantDao participantDao ;
	
	public ParticipantRepository participantRepository ;
	
    public ConfirmCreateParticipantController() {
        setViewName("participant/create_participant_confirmation");
    }

    public ModelAndView handleRequestInternal(HttpServletRequest request,
                    HttpServletResponse response) throws Exception {
    	Map map = new HashMap();
    	Participant participant = null ;
    	
    	if (WebUtils.hasSubmitParameter(request, ControllerTools.IDENTIFIER_VALUE_PARAM_NAME)) {
        	Identifier identifier=ControllerTools.getIdentifierInRequest(request);
        	List<Identifier> identifiers=new ArrayList<Identifier>();
        	identifiers.add(identifier);
        	participant=participantRepository.getUniqueParticipant(identifiers);
            participantDao.initialize(participant);
        }else{
        	participant =  new Participant();
        }
        map.put("command", participant);
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

	public ParticipantRepository getParticipantRepository() {
		return participantRepository;
	}

	public void setParticipantRepository(ParticipantRepository participantRepository) {
		this.participantRepository = participantRepository;
	}
}