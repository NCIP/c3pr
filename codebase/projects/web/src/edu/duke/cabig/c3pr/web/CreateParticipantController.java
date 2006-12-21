package edu.duke.cabig.c3pr.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.service.ParticipantService;

/**
 * 
 * @author Kulasekaran
 * @version 1.0
 * 
 */
public class CreateParticipantController extends SimpleFormController{

	private ParticipantService participantService;
		
	public CreateParticipantController()
	{
		setCommandClass(Participant.class);		
	}

	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object oCommand, BindException errors) throws Exception {
		Participant participant = (Participant) oCommand;
    	participantService.saveParticipant(participant);   
    	ModelAndView modelAndView= new ModelAndView(getSuccessView());
    	modelAndView.addAllObjects(errors.getModel());
    	return modelAndView;
    }
	
	public ParticipantService getParticipantService() {
		return participantService;
	}

	public void setParticipantService(ParticipantService participantService) {
		this.participantService = participantService;
	}
}
