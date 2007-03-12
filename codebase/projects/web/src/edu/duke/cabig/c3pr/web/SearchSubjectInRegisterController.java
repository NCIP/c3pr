
package edu.duke.cabig.c3pr.web;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.service.ParticipantService;

/**
 * @author Ramakrishna
 *
 */


public class SearchSubjectInRegisterController extends SimpleFormController{
	
private static Log log = LogFactory.getLog(SearchSubjectInRegisterController.class);
	
	private ParticipantService participantService;	

	public SearchSubjectInRegisterController(){
		setCommandClass(SearchCommand.class);
	}	
	
	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object oCommand, BindException errors) throws Exception {
    	SearchCommand searchParticipantCommand = (SearchCommand) oCommand;
    	Participant participant = new Participant();
    	String text = searchParticipantCommand.getSearchText();
    	String type = searchParticipantCommand.getSearchType();
    	
    	log.debug("search string = " +text+"; type = "+type);
    	    	
    	if ("N".equals(type)){
    		participant.setLastName(text);
    	}
    	if ("Identifier".equals(type)) {
    		Identifier identifier = new Identifier();
			identifier.setValue(text);
			//FIXME:
			participant.addIdentifier(identifier);
		}    	
    	    		
    	List<Participant> participants = participantService.search(participant); 
    	
    	Iterator<Participant> participantIter = participants.iterator(); 
          	
    	log.debug("Search results size " +participants.size());
    	Map map =errors.getModel();
    	map.put("participants", participants);
    	map.put("studySiteId", request.getParameter("studySiteId"));
    	map.put("searchTypeRefData", getSearchType());
    	ModelAndView modelAndView= new ModelAndView(getSuccessView(), map);
    	return modelAndView;
    }
	
	 protected Map<String, Object> referenceData(HttpServletRequest httpServletRequest) throws Exception {
 		Map<String, Object> refdata = new HashMap<String, Object>();
 	
 	refdata.put("searchType", getSearchType());
 	refdata.put("studySiteId", httpServletRequest.getParameter("studySiteId"));
     return refdata;
 }
	 
	 private List<LOV> getSearchType(){
			List<LOV> col = new ArrayList<LOV>();
			LOV lov1 = new LOV("N", "Last Name");
			LOV lov2 = new LOV("Identifier", "Identifier");
			
			col.add(lov1);
			col.add(lov2);	    	    	
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
	
	public ParticipantService getParticipantService() {
		return participantService;
	}

	public void setParticipantService(ParticipantService participantService) {
		this.participantService = participantService;
	}

}
