package edu.duke.cabig.c3pr.web.study;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;

import edu.duke.cabig.c3pr.domain.RandomizationType;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.web.ajax.BookRandomizationAjaxFacade;

/**
 * Author: gangoliV
 * Date: July 30, 2007
 */

public class StudyRandomizationTab extends StudyTab {
	
	private BookRandomizationAjaxFacade bookRandomizationAjaxFacade;
	String []bookRandomizationEntries;
	
	public StudyRandomizationTab() {
        super("Randomization", "Randomization", "study/study_randomizations");
        bookRandomizationEntries = new String[10];
     }

	@Override
	public Map<String, Object> referenceData(HttpServletRequest request, Study study) {
		Map<String, Object> refdata = super.referenceData(study);
		String flowType="";
		boolean isAdmin = isAdmin();
		
	   	 if(getFlow().getName().equals("Create Study")){
	   		 flowType = "CREATE_STUDY";
	   	 } else if (getFlow().getName().equals("Edit Study")){
	   		 flowType = "EDIT_STUDY";
	   	 } else if(getFlow().getName().equals("Amend Study")){
	   		 flowType = "AMEND_STUDY";
	   	 }
		if(study.getRandomizedIndicator()&&study.getRandomizationType()==RandomizationType.BOOK){
			Map <String, List>dummyMap = new HashMap<String, List>();
			String []bookRandomizationEntries = new String[study.getTreatmentEpochs().size()];
	        for(int i=0;i<study.getTreatmentEpochs().size(); i++){
	        	bookRandomizationEntries[i]  = bookRandomizationAjaxFacade.getTable(dummyMap, "", i+"", request, flowType);
	        }
	        request.setAttribute("bookRandomizationEntries", bookRandomizationEntries);
		}
		if( (request.getAttribute("amendFlow") != null && request.getAttribute("amendFlow").toString().equals("true")) ||
			    (request.getAttribute("editFlow") != null && request.getAttribute("editFlow").toString().equals("true")) )  
    	{
			if(request.getSession().getAttribute(DISABLE_FORM_RANDOMIZATION) != null && !isAdmin){
				refdata.put("disableForm", request.getSession().getAttribute(DISABLE_FORM_RANDOMIZATION));
			} else {
				refdata.put("disableForm", new Boolean(false));
			}
    	}
		return refdata;
	}
	
	
     @Override
     public void postProcessOnValidation(HttpServletRequest req, Study study, Errors errors) {    	
    	 if(study.getRandomizationType() != null && study.getRandomizationType().equals(RandomizationType.BOOK)){
    		 parseFile(req, study, errors);
    	 }    	 
     }
     
     
     public ModelAndView parseFile(HttpServletRequest request, Object commandObj, Errors error){    	 
    	 //save it to session
    	 String flowType="";
    	 if(getFlow().getName().equals("Create Study")){
    		 request.getSession().setAttribute("edu.duke.cabig.c3pr.web.study.CreateStudyController.FORM.command" ,commandObj);
    		 flowType = "CREATE_STUDY";
    	 } else if (getFlow().getName().equals("Edit Study")){
    		 request.getSession().setAttribute("edu.duke.cabig.c3pr.web.study.EditStudyController.FORM.command" ,commandObj);
    		 flowType = "EDIT_STUDY";
    	 } else if(getFlow().getName().equals("Amend Study")){
    		 request.getSession().setAttribute("edu.duke.cabig.c3pr.web.study.AmendStudyController.FORM.command" ,commandObj);
    		 flowType = "AMEND_STUDY";
    	 }
    	 
    	 Map map=new HashMap();    	 
         try {
        	 String index = request.getParameter("index").toString();
        	 Study study = (Study)(commandObj);
        	 
             Object viewData = bookRandomizationAjaxFacade.getTable(new HashMap<String, List>(), study.getFile(), index, request, flowType);   
             if(StringUtils.isEmpty(viewData.toString())){
            	 map.put(getFreeTextModelName(), "<div><b>Incorrect format. Please try again.</b></div>");
             } else {
            	 bookRandomizationEntries[Integer.parseInt(index)] = viewData.toString();
                 request.setAttribute("bookRandomizationEntries", bookRandomizationEntries);             
                 map.put(getFreeTextModelName(), viewData.toString());
             }                          
         } catch(Exception e ){
        	 log.error(e.getMessage());
         }
         return new ModelAndView("",map);
     }
     

	public BookRandomizationAjaxFacade getBookRandomizationAjaxFacade() {
		return bookRandomizationAjaxFacade;
	}


	public void setBookRandomizationAjaxFacade(
			BookRandomizationAjaxFacade bookRandomizationAjaxFacade) {
		this.bookRandomizationAjaxFacade = bookRandomizationAjaxFacade;
	}
	    
}