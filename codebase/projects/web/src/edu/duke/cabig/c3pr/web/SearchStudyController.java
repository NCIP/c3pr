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

import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.service.StudyService;
import edu.duke.cabig.c3pr.utils.Lov;

public class SearchStudyController  extends SimpleFormController{

	private static Log log = LogFactory.getLog(SearchStudyController.class);
	
	private StudyService studyService;
   
    public StudyService getStudyService() {
		return studyService;
	}

	public void setStudyService(StudyService studyService) {
		this.studyService = studyService;
	}

	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object oCommand, BindException errors) throws Exception {
    	SearchStudyCommand searchStudyCommand = (SearchStudyCommand) oCommand;
    	Study study = new Study();
    	String type = searchStudyCommand.getSearchType();
    	String searchtext = searchStudyCommand.getSearchTypeText();
    	
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
    	
    	List<Study> studies = studyService.search(study);   
    	log.debug("Search results size " +studies.size());
    	Map map = errors.getModel();
    	map.put("study", studies);
    	map.put("searchType",getSearchType() );    	
    	ModelAndView modelAndView= new ModelAndView(getSuccessView(), map);
    	return modelAndView;
    }
    
    protected Map<String, Object> referenceData(HttpServletRequest httpServletRequest) throws Exception {
    		Map<String, Object> refdata = new HashMap<String, Object>();
    	
    	refdata.put("searchType", getSearchType());
        return refdata;
    }
    
	private List<Lov> getSearchType(){
		Lov col = new Lov();
		col.addData("s", "Status");
		col.addData("id", "Identifier");
		col.addData("shortTitle", "Short Title");
		col.addData("longTitle", "Long Title");
			
    	return col.getData();
	}	
}
