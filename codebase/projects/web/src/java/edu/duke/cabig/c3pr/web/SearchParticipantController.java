package edu.duke.cabig.c3pr.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.util.WebUtils;

import edu.duke.cabig.c3pr.dao.ParticipantDao;
import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.utils.ConfigurationProperty;
import edu.duke.cabig.c3pr.utils.Lov;

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
    	Set<Participant> participantSet = new TreeSet<Participant>();
    	participantSet.addAll(participants);
    	List<Participant> uniqueParticipants = new ArrayList<Participant>();
    	uniqueParticipants.addAll(participantSet);
    	Iterator<Participant> participantIter = participants.iterator();
    	log.debug("Search results size " +uniqueParticipants.size());
    	Map map =errors.getModel();
    	map.put("participants", uniqueParticipants);
    	map.put("searchTypeRefData",configMap.get("participantSearchType"));
    	if(WebUtils.hasSubmitParameter(request, "async")){
        	return new ModelAndView("/registration/subjectResultsAsync",map);
        }
    	ModelAndView modelAndView= new ModelAndView(getSuccessView(), map);
    	return modelAndView;
    }
	
	 protected Map<String, Object> referenceData(HttpServletRequest request) throws Exception {
 		Map<String, Object> refdata = new HashMap<String, Object>();
 		Map <String, List<Lov>> configMap = configurationProperty.getMap();
		
 		refdata.put("searchTypeRefData", configMap.get("participantSearchType"));
 		return refdata;
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