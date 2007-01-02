package edu.duke.cabig.c3pr.web;

import java.util.ArrayList;
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

import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.service.StudyService;

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
    	String id  = searchStudyCommand.getStudyid();
    	String type = searchStudyCommand.getSearchType();
    	String searchtext = searchStudyCommand.getSearchTypeText();
    	String shortTitleText = searchStudyCommand.getShortTitleText();
    	
    	log.debug("search string = " +searchtext+"; type = "+type);
    	
    	if (id != null && !("").equals(id))
    		study.setId(new Integer(id));
    	if (shortTitleText != null && !("").equals(shortTitleText))
    		study.setShortTitleText(shortTitleText);    	
    	    	
    	if ("status".equals(type))
    		study.setStatus(searchtext);
    	else if ("T".equals(type))
    		study.setType(searchtext);
    	else if ("P".equals(type))
    		study.setPhaseCode(searchtext);
    	else if ("S".equals(type))
    		study.setStatus(searchtext);
    	else if ("SP".equals(type))
    		study.setSponsorCode(searchtext);
    	else if ("M".equals(type))
    		study.setMonitorCode(searchtext);
    	else if ("D".equals(type))
    		study.setDiseaseCode(searchtext);
    		
    	List<Study> studies = studyService.search(study);   
    	log.debug("Search results size " +studies.size());
    	Map map =errors.getModel();
    	map.put("study", studies);
    	ModelAndView modelAndView= new ModelAndView(getSuccessView(), map);
    	return modelAndView;
    }
    
    protected Map<String, Object> referenceData(HttpServletRequest httpServletRequest) throws Exception {
    		Map<String, Object> refdata = new HashMap<String, Object>();
    	
    	refdata.put("searchType", getSearchType());
        return refdata;
    }
    
	private List<LOV> getSearchType(){
		List<LOV> col = new ArrayList<LOV>();
		LOV lov1 = new LOV("T", "Type");
		LOV lov2 = new LOV("S", "Status");
		LOV lov3 = new LOV("P", "Phase");
		LOV lov4 = new LOV("D", "Disease");
		LOV lov5 = new LOV("M", "Monitor");
		LOV lov6 = new LOV("SP", "Sponsor");
		
		col.add(lov1);
    	col.add(lov2);
    	col.add(lov3);
    	col.add(lov4);
    	col.add(lov5);
    	col.add(lov6);
    	
    	return col;
	}
	
	public class LOV {
		
		private String code;
		private String desc;
		
		LOV(String code, String desc)
		{
			this.code=code;
			this.desc=desc;
			
		}
		
		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}
		
		public String getDesc(){
			return desc;
		}
			
		public void setDesc(String desc){
			this.desc=desc;
		}
	}
}
