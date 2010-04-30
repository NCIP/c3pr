package edu.duke.cabig.c3pr.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import edu.duke.cabig.c3pr.constants.ContactMechanismType;
import edu.duke.cabig.c3pr.dao.HealthcareSiteDao;
import edu.duke.cabig.c3pr.dao.ParticipantDao;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.repository.ParticipantRepository;
import edu.duke.cabig.c3pr.utils.ConfigurationProperty;
import edu.duke.cabig.c3pr.utils.web.ControllerTools;
import edu.duke.cabig.c3pr.utils.web.propertyeditors.CustomDaoEditor;
import edu.duke.cabig.c3pr.utils.web.propertyeditors.EnumByNameEditor;
import edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.AutomaticSaveAjaxableFormController;
import edu.duke.cabig.c3pr.web.participant.ParticipantAddressAndContactInfoTab;
import edu.duke.cabig.c3pr.web.participant.ParticipantDetailsTab;
import gov.nih.nci.cabig.ctms.web.tabs.Flow;

/**
 * @author Ramakrishna
 * 
 */
public class EditParticipantController<C extends Participant> extends
                AutomaticSaveAjaxableFormController<C, Participant, ParticipantDao> {

    private static Log log = LogFactory.getLog(EditParticipantController.class);

    private ParticipantDao participantDao;

    private HealthcareSiteDao healthcareSiteDao;

    protected ConfigurationProperty configurationProperty;
    
    public ParticipantRepository participantRepository ;

    public EditParticipantController() {
        setCommandClass(Participant.class);
        Flow<C> flow = new Flow<C>("Edit Subject");
        layoutTabs(flow);
        setFlow(flow);
        setBindOnNewForm(true);
    }

    protected void layoutTabs(Flow flow) {
        flow.addTab(new ParticipantDetailsTab());
        flow.addTab(new ParticipantAddressAndContactInfoTab());
    }

    @Override
    protected ParticipantDao getDao() {
        return participantDao;
    }

    @Override
    protected Participant getPrimaryDomainObject(C command) {
        return command;
    }

    /**
     * Override this in sub controller if summary is needed
     * 
     * @return
     */
    protected boolean isSummaryEnabled() {
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.web.servlet.mvc.AbstractFormController#formBackingObject(javax.servlet.http.HttpServletRequest)
     */
    @Override
    protected Object formBackingObject(HttpServletRequest request) throws Exception {
        Participant participant = null;

        if (WebUtils.hasSubmitParameter(request, ControllerTools.IDENTIFIER_VALUE_PARAM_NAME)) {
        	Identifier identifier=ControllerTools.getIdentifierInRequest(request);
        	List<Identifier> identifiers=new ArrayList<Identifier>();
        	identifiers.add(identifier);
        	participant=participantRepository.getUniqueParticipant(identifiers);
            participantDao.initialize(participant);
            log.debug(" Participant's ID is:" + participant.getId());
        }else{
        	participant =  new Participant();
        }

        return participant;
    }

    protected void initBinder(HttpServletRequest req, ServletRequestDataBinder binder)
                    throws Exception {
        super.initBinder(req, binder);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat(
                        "MM/dd/yyyy"), true));
        binder.registerCustomEditor(HealthcareSite.class, new CustomDaoEditor(healthcareSiteDao));
        binder.registerCustomEditor(ContactMechanismType.class, new EnumByNameEditor(
                        ContactMechanismType.class));
    }
    
    @Override
    protected Object currentFormObject(HttpServletRequest request,
    		Object oCommand) throws Exception {
    	 Participant participant = null;

         if (WebUtils.hasSubmitParameter(request, ControllerTools.IDENTIFIER_VALUE_PARAM_NAME)) {
         	Identifier identifier=ControllerTools.getIdentifierInRequest(request);
         	List<Identifier> identifiers=new ArrayList<Identifier>();
         	identifiers.add(identifier);
         	participant=participantRepository.getUniqueParticipant(identifiers);
             participantDao.initialize(participant);
             log.debug(" Participant's ID is:" + participant.getId());
         }else{
         	participant =  new Participant();
         }

         return participant;
    }
    
    @Override
	protected ModelAndView showForm(HttpServletRequest request,
			HttpServletResponse response, BindException errors)
			throws Exception {
		// TODO Auto-generated method stub
		return super.showForm(request, response, errors);
	}
  
    
    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request,
    		HttpServletResponse response) throws Exception {
    	if(request.getParameter("goToRegistration")!=null && request.getParameter("goToRegistration").equals("true")){
    		super.handleRequestInternal(request, response);
    		ModelAndView modelAndView = new ModelAndView("redirect:../../registration/createRegistration?fromEditParticipant=true&participantId=" + request.getParameter("participantId"));
    		return modelAndView;
    	}
    	return super.handleRequestInternal(request, response);
    }

    @Override
    protected ModelAndView processFinish(HttpServletRequest request, HttpServletResponse response,
                    Object oCommand, BindException errors) throws Exception {
        Participant participant = (Participant) oCommand;
        participantDao.merge(participant);
        response.sendRedirect("viewParticipant?"+ControllerTools.createParameterString(participant.getOrganizationAssignedIdentifiers().get(0)));
        return null;
    }
    
    public HealthcareSiteDao getHealthcareSiteDao() {
        return healthcareSiteDao;
    }

    public void setHealthcareSiteDao(HealthcareSiteDao healthcareSiteDao) {
        this.healthcareSiteDao = healthcareSiteDao;
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
    
    @Override
    protected C save(C command, Errors errors) {
        command = (C)participantDao.merge((Participant)command);
        return command;
    }

	public ParticipantRepository getParticipantRepository() {
		return participantRepository;
	}

	public void setParticipantRepository(ParticipantRepository participantRepository) {
		this.participantRepository = participantRepository;
	}
}
