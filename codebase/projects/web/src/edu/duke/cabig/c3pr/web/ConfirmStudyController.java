package edu.duke.cabig.c3pr.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.ParameterizableViewController;

public class ConfirmStudyController extends ParameterizableViewController {
	
	public ConfirmStudyController() {
		setViewName("study_confirmation");
	}	

	public ModelAndView handleRequestInternal(HttpServletRequest request,
	        HttpServletResponse response) throws Exception {	 
	 setViewName("study_confirmation");
	 ModelAndView mav = new ModelAndView("study/study_confirmation");	
	 return mav;        
	 }
}
