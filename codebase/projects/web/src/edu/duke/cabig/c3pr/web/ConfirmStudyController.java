package edu.duke.cabig.c3pr.web;

import java.util.HashMap;

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
	 HashMap map = new HashMap();
	 map.put("command", request.getAttribute("command"));
	 ModelAndView mav = new ModelAndView("study/study_confirmation",map);	
	 return mav;        
	 }
}
