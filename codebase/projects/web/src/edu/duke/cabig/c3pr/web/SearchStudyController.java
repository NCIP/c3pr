package edu.duke.cabig.c3pr.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import edu.duke.cabig.c3pr.dao.StudyDao;
import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.utils.ConfigurationProperty;
import edu.duke.cabig.c3pr.utils.Lov;
import edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.Flow;

public class SearchStudyController  extends SimpleFormController{

	private static Log log = LogFactory.getLog(SearchStudyController.class);
	private ConfigurationProperty configurationProperty;	
	private StudyDao studyDao;   
	
	@Override
	protected boolean isFormSubmission(HttpServletRequest request) {
		// TODO Auto-generated method stub
		if(request.getParameter("test")!=null)
			return true;
		return super.isFormSubmission(request);
	}

	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object oCommand, BindException errors) throws Exception {
    	SearchCommand searchStudyCommand = (SearchCommand) oCommand;
    	Study study = new Study();
    	String type = searchStudyCommand.getSearchType();
    	String searchtext = searchStudyCommand.getSearchText();
    	
    	log.debug("search string = " +searchtext+"; type = "+type);
    	    	   
    	if ("status".equals(type))
    		study.setStatus(searchtext);
    	else if ("id".equals(type))
    	{
    		Identifier id = new Identifier();
    		id.setValue(searchtext);
    		study.addIdentifier(id);
    	}
    	else if ("shortTitle".equals(type))
    		study.setShortTitleText(searchtext);
    	else if ("longTitle".equals(type))
    		study.setShortTitleText(searchtext);
    	
    	List<Study> studies = studyDao.searchByExample(study, true);
    	log.debug("Search results size " +studies.size());
    	Map <String, List<Lov>> configMap = configurationProperty.getMap();
    	
    	Map map = errors.getModel();
    	map.put("studyResults", studies);
    	map.put("searchTypeRefData",configMap.get("studySearchType"));  
    	if(isSubFlow(request)){
    		processSubFlow(request,map);
    		map.put("actionReturnType", "SearchResults");
    	}
    	ModelAndView modelAndView= new ModelAndView(getSuccessView(), map);
    	return modelAndView;
    }
    
    protected Map<String, Object> referenceData(HttpServletRequest request) throws Exception {
    	Map<String, Object> refdata = new HashMap<String, Object>();
    	Map <String, List<Lov>> configMap = configurationProperty.getMap();    	
    	refdata.put("searchTypeRefData", configMap.get("studySearchType"));
    	if(isSubFlow(request)){
 			processSubFlow(request, refdata);
    	}
        return refdata;
    }

    private boolean isSubFlow(HttpServletRequest request){
    	if(request.getParameter("inRegistration")!=null||request.getParameter("subjectId")!=null)
    		return true;
    	return false;
    }
    private void processSubFlow(HttpServletRequest request, Map map){
    	if(request.getParameter("subjectId")!=null){
    		map.put("subjectId", request.getParameter("subjectId"));
//    		getAlternateDisplayOrder(request).getTab(2).setShowSummary("true");
    	}else{
//    		getRegistrationFlow(request).getTab(1).setShowSummary("false");
    	}
    	map.put("registrationTab", getRegistrationFlow(request).getTab(1));
    	map.put("inRegistration", "true");
    }
    private Flow getRegistrationFlow(HttpServletRequest request){
    	return (Flow)request.getSession().getAttribute("registrationFlow");
    }
	public ConfigurationProperty getConfigurationProperty() {
		return configurationProperty;
	}
	private Flow getAlternateDisplayOrder(HttpServletRequest request){
		return (Flow)request.getSession().getAttribute("registrationAltFlow");
	}
	public void setConfigurationProperty(ConfigurationProperty configurationProperty) {
		this.configurationProperty = configurationProperty;
	}      
	  
	public StudyDao getStudyDao() {
		return studyDao;
	}

	public void setStudyDao(StudyDao studyDao) {
		this.studyDao = studyDao;
	}
    
}
