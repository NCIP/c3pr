package edu.duke.cabig.c3pr.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.ParameterizableViewController;

public class ConfirmCreateParticipantController extends ParameterizableViewController {

    public ConfirmCreateParticipantController() {
        setViewName("participant/create_participant_confirmation");
    }

    public ModelAndView handleRequestInternal(HttpServletRequest request,
                    HttpServletResponse response) throws Exception {

        setViewName("participant/create_participant_confirmation");
        ModelAndView mav = new ModelAndView("participant/create_participant_confirmation");

        return mav;
    }
}
