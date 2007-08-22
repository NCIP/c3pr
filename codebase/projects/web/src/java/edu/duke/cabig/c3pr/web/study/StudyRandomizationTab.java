package edu.duke.cabig.c3pr.web.study;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.Errors;

import edu.duke.cabig.c3pr.domain.Study;

/**
 * Author: gangoliV
 * Date: July 30, 2007
 */

public class StudyRandomizationTab extends StudyTab {
	

	public StudyRandomizationTab() {
        super("Study Book Randomizations", "Randomization", "study/study_randomizations");
     }


     @Override
     public void postProcess(HttpServletRequest req, Study study, Errors errors) {
    	
     }
     
     
/*	    	Map<String, List<Lov>> configMap = configurationProperty.getMap();
	        Map map = new HashMap();
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
				        Object viewData = bookRandomizationAjaxFacade.getTable(map, study, bookRandomizations, epochIndex, req);
				        req.setAttribute("bookRandomizationEntries_"+epochIndex, viewData);
		        	}
		        }
	    	}	        
	    }
*/    	        
	    
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
