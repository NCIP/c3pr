package edu.duke.cabig.c3pr.web.study;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
        super("Study Book Randomizations", "Randomization", "study/study_randomizations");
        bookRandomizationEntries = new String[10];
     }

	@Override
	public Map<String, Object> referenceData(HttpServletRequest request, Study study) {
		// TODO Auto-generated method stub
		if(study.getRandomizedIndicator().equalsIgnoreCase("true")&&study.getRandomizationType()==RandomizationType.BOOK){
			Map <String, List>dummyMap = new HashMap<String, List>();
			String []bookRandomizationEntries = new String[study.getTreatmentEpochs().size()];
	        for(int i=0;i<study.getTreatmentEpochs().size(); i++){
	        	bookRandomizationEntries[i]  = bookRandomizationAjaxFacade.getTable(dummyMap, "", i+"", request);
	        }
	        request.setAttribute("bookRandomizationEntries", bookRandomizationEntries);
		}
		Map<String, Object> refdata = super.referenceData(study);
		if( (request.getAttribute("amendFlow") != null && request.getAttribute("amendFlow").toString().equals("true")) ||
			    (request.getAttribute("editFlow") != null && request.getAttribute("editFlow").toString().equals("true")) )  
    	{
			if(request.getSession().getAttribute(DISABLE_FORM_RANDOMIZATION) != null){
				refdata.put("disableForm", request.getSession().getAttribute(DISABLE_FORM_RANDOMIZATION));
			} else {
				refdata.put("disableForm", new Boolean(false));
			}
    	}
		return refdata;
	}
	
	
     @Override
     public void postProcess(HttpServletRequest req, Study study, Errors errors) {    	
    	 if(study.getRandomizationType() != null && study.getRandomizationType().equals(RandomizationType.BOOK)){
    		 parseFile(req, study, errors);
    	 }    	 
     }
     
     
     public ModelAndView parseFile(HttpServletRequest request, Object commandObj, Errors error){    	 
    	 //save it to session
    	 if(getFlow().getName().equals("Create Study")){
    		 request.getSession().setAttribute("edu.duke.cabig.c3pr.web.study.CreateStudyController.FORM.command" ,commandObj);
    	 } else if (getFlow().getName().equals("Edit Study")){
    		 request.getSession().setAttribute("edu.duke.cabig.c3pr.web.study.EditStudyController.FORM.command" ,commandObj);
    	 }    	 
    	 
    	 Map map=new HashMap();    	 
         try {
        	 String index = request.getParameter("index").toString();
        	 Study study = (Study)(commandObj);
        	 
             Object viewData = bookRandomizationAjaxFacade.getTable(new HashMap<String, List>(), study.getFile(), index, request);             
             bookRandomizationEntries[Integer.parseInt(index)] = viewData.toString();
             request.setAttribute("bookRandomizationEntries", bookRandomizationEntries);             
             map.put(getFreeTextModelName(), viewData.toString());             
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


  	        
	    
/*  		if(study.getRandomizationType().equals(RandomizationType.CALL_OUT) ||
	    			study.getRandomizationType().equals(RandomizationType.PHONE_CALL)){
	    		return;
	    	}
	    	
	    	if(study.getRandomizationType().equals(RandomizationType.BOOK)){
		    	ArrayList <String>reqParamArr = new ArrayList<String>();
		        Enumeration e = req.getParameterNames();
		        String temp;
		        while(e.hasMoreElements()){
		        	temp = e.nextElement().toString(); 
		        	if(temp.startsWith("bookRandomizations")){
		        		reqParamArr.add(temp);
		        	}
		        }
		        String epochIndex, bookRandomizations;
		        int selectedEpoch;
		        for(String param : reqParamArr){
		        	bookRandomizations = StringUtils.getBlankIfNull(req.getParameter(param));
		        	if(!StringUtils.isEmpty(bookRandomizations)){
				        epochIndex = param.substring(param.indexOf("-")+1);
				        selectedEpoch = StringUtils.getBlankIfNull(epochIndex).equals("")?-1:Integer.parseInt(epochIndex);
				        TreatmentEpoch tEpoch = study.getTreatmentEpochs().get(selectedEpoch);		        
				        if(study.getRandomizationType().getName().equals("BOOK")){
				        	parseBookRandomization(bookRandomizations, tEpoch);
				        }
		        	}
		        }
	    	}
	    }*/	    
}
