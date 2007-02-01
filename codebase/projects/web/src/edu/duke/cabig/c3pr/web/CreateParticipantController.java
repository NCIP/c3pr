package edu.duke.cabig.c3pr.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractWizardFormController;

import edu.duke.cabig.c3pr.dao.ParticipantDao;
import edu.duke.cabig.c3pr.domain.Address;
import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.validator.ParticipantValidator;
import edu.duke.cabig.c3pr.utils.ConfigurationProperty;
import edu.duke.cabig.c3pr.utils.Lov;


/**
 * @author Kulasekaran, Priyatam
 * 
 */
public class CreateParticipantController extends AbstractWizardFormController {

	private ParticipantDao participantDao;
	protected ConfigurationProperty configurationProperty;
	private ParticipantValidator participantValidator;

	public CreateParticipantController() {
		setCommandClass(Participant.class);
	}

	@Override
	protected Map<String, Object> referenceData(HttpServletRequest httpServletRequest, int page) throws Exception {
    	// Currently the static data is a hack, once DB design is approved for an LOV this will be
    	// replaced with LOVDao to get the static data from individual tables
    	Map<String, Object> refdata = new HashMap<String, Object>();    	
		Map <String, List<Lov>> configMap = configurationProperty.getMap();
		
    	if(page==0)
    	{
    		refdata.put("administrativeGenderCode", configMap.get("administrativeGenderCode"));
    		refdata.put("ethnicGroupCode", configMap.get("ethnicGroupCode"));
    		refdata.put("raceCode", configMap.get("raceCode"));
    	    refdata.put("source", configMap.get("identifiersSource"));
    		refdata.put("searchType", configMap.get("participantSearchType"));
    		if(httpServletRequest.getParameter("studySiteId")!=null){
    			if(!httpServletRequest.getParameter("studySiteId").equals("")){
    				refdata.put("studySiteId", httpServletRequest.getParameter("studySiteId"));
    			}
    		}
    	}
    	
        return refdata;
    }
	
	@Override
	protected Object formBackingObject(HttpServletRequest request) throws Exception {
		
		//FIXME: small hack
		if(request.getParameter("url")!=null){
			if(request.getParameter("studySiteId")!=null){
				setPages(new String[]{"reg_create_patient_study","reg_create_patient_address_study","reg_create_patient_submit_study"});
			}else{
				setPages(new String[]{"reg_create_patient","reg_create_patient_address","reg_create_patient_submit"});
			}
			request.getSession().setAttribute("url", request.getParameter("url"));
			request.getSession().setAttribute("studySiteId", request.getParameter("studySiteId"));
		}else{
			setPages(new String[]{"participant","participant_address","participant_submit"});
			request.getSession().removeAttribute("url");	
			request.getSession().removeAttribute("studySiteId");
		}
		
		Participant participant = (Participant) super.formBackingObject(request);
		for (int i = 0; i < 5; i++) {
			Identifier temp=new Identifier();
			temp.setPrimaryIndicator(false);
			participant.addIdentifier(temp);
		}
		participant.setAddress(new Address());
		return participant;
	}
	
	@Override
	protected void initBinder(HttpServletRequest req,
			ServletRequestDataBinder binder) throws Exception {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("MM/dd/yyyy"), true));		
	}

	@Override
	protected ModelAndView processFinish(HttpServletRequest request,
			HttpServletResponse response, Object oCommand, BindException errors)
			throws Exception {
		Participant command = (Participant) oCommand;
		
		Iterator<Identifier> iterator = command.getIdentifiers().iterator();

		while(iterator.hasNext())
		{
			Identifier identifier = iterator.next();
			if(identifier.getType().trim().length() == 0 || identifier.getValue().trim().length() == 0)
			{
				iterator.remove();
			}
		}
			
		participantDao.save(command);
		
		ModelAndView modelAndView = null;
		//FIXME: small hack
		String url = null;
		if((url = (String) request.getSession().getAttribute("url")) != null)
		{
			url+="?participantId=" + Integer.toString(command.getId());
			if(request.getSession().getAttribute("studySiteId") != null)
				url+="&studySiteId="+(String)request.getSession().getAttribute("studySiteId");
			response.sendRedirect(url);
			return null;
		}
		response.sendRedirect("/c3pr/SearchAndRegister.do");
		return null;
	}	
	
	protected void validatePage(Object command, Errors errors, int page) {
		Participant participant = (Participant) command;
		switch (page) {
		case 0: {
			participantValidator
					.validateParticipantDetails(participant, errors);
			// participantValidator.validateIdentifiers(participant, errors);
		}
			break;
		case 1:
			participantValidator
					.validateParticipantAddress(participant, errors);
			break;
		case 2:
			//
			break;

		}
	}
	
	public ConfigurationProperty getConfigurationProperty() {
		return configurationProperty;
	}

	public void setConfigurationProperty(ConfigurationProperty configurationProperty) {
		this.configurationProperty = configurationProperty;
	}
	
	public ParticipantDao getParticipantDao() {
		return participantDao;
	}

	public void setParticipantDao(ParticipantDao participantDao) {
		this.participantDao = participantDao;
	}		

	public ParticipantValidator getParticipantValidator() {
		return participantValidator;
	}

	public void setParticipantValidator(ParticipantValidator participantValidator) {
		this.participantValidator = participantValidator;
	}
}
