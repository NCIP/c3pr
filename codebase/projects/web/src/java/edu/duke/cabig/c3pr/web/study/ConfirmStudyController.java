package edu.duke.cabig.c3pr.web.study;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.ParameterizableViewController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

public class ConfirmStudyController extends ParameterizableViewController {

    public ConfirmStudyController() {
        setViewName("study/study_confirmation");
    }

    public ModelAndView handleRequestInternal(HttpServletRequest request,
                                              HttpServletResponse response) throws Exception {
        setViewName("study/study_confirmation");
        ModelAndView mav = new ModelAndView("study/study_confirmation");
        return mav;
    }
}
