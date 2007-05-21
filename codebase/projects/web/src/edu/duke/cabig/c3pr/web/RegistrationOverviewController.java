package edu.duke.cabig.c3pr.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import edu.duke.cabig.c3pr.dao.StudyParticipantAssignmentDao;
import edu.duke.cabig.c3pr.domain.StudyParticipantAssignment;

public class RegistrationOverviewController implements Controller {

	protected StudyParticipantAssignmentDao registrationDao;
	private String viewName; 

	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("--------------------In-----------------------------");
		String id=request.getParameter("registrationId");
		if(id==null)
			throw new Exception("request parameter registrationId missing");
		int regId=Integer.parseInt(id);
		StudyParticipantAssignment command=registrationDao.getById(regId);
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("command", command);
		map.put("editable", new Object());
		return new ModelAndView(getViewName(),map );
	}

	public String getViewName() {
		return viewName;
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

	public void setRegistrationDao(StudyParticipantAssignmentDao registrationDao) {
		this.registrationDao = registrationDao;
	}
	
	
	
}
