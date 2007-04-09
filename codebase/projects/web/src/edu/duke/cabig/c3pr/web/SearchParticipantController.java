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

import edu.duke.cabig.c3pr.dao.ParticipantDao;
import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.utils.ConfigurationProperty;
import edu.duke.cabig.c3pr.utils.Lov;
import edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.Flow;

/**
 * 
 * @author Ramakrishna, Priyatam
 */
public class SearchParticipantController extends SimpleFormController{
	
private static Log log = LogFactory.getLog(SearchParticipantController.class);
	
	private ParticipantDao participantDao;
	private ConfigurationProperty configurationProperty;	

	public SearchParticipantController(){
		setCommandClass(SearchCommand.class);
		this.setFormView("particpant_search");
		this.setSuccessView("participant_search_results");
	}
	
	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object oCommand, BindException errors) throws Exception {
    	SearchCommand searchParticipantCommand = (SearchCommand) oCommand;
    	Participant participant = new Participant();
    	String text = searchParticipantCommand.getSearchText();
    	String type = searchParticipantCommand.getSearchType();
    	Map <String, List<Lov>> configMap = configurationProperty.getMap();		
    	
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
    	    		
    	List<Participant> participants = participantDao.searchByExample(participant);     	
    	Iterator<Participant> participantIter = participants.iterator();
    	log.debug("Search results size " +participants.size());
    	Map map =errors.getModel();
    	map.put("participants", participants);
    	map.put("searchTypeRefData",configMap.get("participantSearchType"));
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
		
 		refdata.put("searchTypeRefData", configMap.get("participantSearchType"));
 		if(isSubFlow(request)){
 			processSubFlow(request, refdata);
    	}
 		return refdata;
	 }	
    private boolean isSubFlow(HttpServletRequest request){
    	if(request.getParameter("inRegistration")!=null||request.getParameter("studySiteId")!=null)
    		return true;
    	return false;
    }
	
    private void processSubFlow(HttpServletRequest request, Map map){
    	if(request.getParameter("studySiteId")!=null){
    		map.put("studySiteId", request.getParameter("studySiteId"));
//    		getRegistrationFlow(request).getTab(2).setShowSummary("true");
    	}else{
    		List dispOrder=new ArrayList();
			dispOrder.add("SearchSubjectStudy");
			dispOrder.add("Select Subject");
			dispOrder.add("Select Study");
			dispOrder.add("Enrollment Details");
			dispOrder.add("Check Eligibility");
			dispOrder.add("Stratify");
			dispOrder.add("Review & Submit");
			setAlternateDisplayOrder(request,getRegistrationFlow(request).createAlternateFlow(dispOrder));
//			getAlternateDisplayOrder(request).getTab(1).setShowSummary("false");
    	}
    	map.put("registrationTab", getRegistrationFlow(request).getTab(2));
    	map.put("inRegistration", "true");
    }
    private Flow getRegistrationFlow(HttpServletRequest request){
    	return (Flow)request.getSession().getAttribute("registrationFlow");
    }
	private void setAlternateDisplayOrder(HttpServletRequest request, Flow flow){
		request.getSession().setAttribute("registrationAltFlow", flow);
	}
	private Flow getAlternateDisplayOrder(HttpServletRequest request){
		return (Flow)request.getSession().getAttribute("registrationAltFlow");
	}
	 public ParticipantDao getParticipantDao() {
		return participantDao;
	}

	public void setParticipantDao(ParticipantDao participantDao) {
		this.participantDao = participantDao;
	}
	
	public ConfigurationProperty getConfigurationProperty() {
		return configurationProperty;
	}

	public void setConfigurationProperty(ConfigurationProperty configurationProperty) {
		this.configurationProperty = configurationProperty;
	}

}