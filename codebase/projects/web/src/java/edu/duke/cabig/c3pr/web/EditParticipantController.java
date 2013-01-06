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
import edu.duke.cabig.c3pr.constants.RaceCodeEnum;
import edu.duke.cabig.c3pr.dao.HealthcareSiteDao;
import edu.duke.cabig.c3pr.dao.ParticipantDao;
import edu.duke.cabig.c3pr.dao.RaceCodeAssociationDao;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.RaceCodeAssociation;
import edu.duke.cabig.c3pr.domain.repository.ParticipantRepository;
import edu.duke.cabig.c3pr.utils.ConfigurationProperty;
import edu.duke.cabig.c3pr.utils.web.ControllerTools;
import edu.duke.cabig.c3pr.utils.web.propertyeditors.CustomDaoEditor;
import edu.duke.cabig.c3pr.utils.web.propertyeditors.EnumByNameEditor;
import edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.AutomaticSaveAjaxableFormController;
import edu.duke.cabig.c3pr.web.participant.ParticipantAddressAndContactInfoTab;
import edu.duke.cabig.c3pr.web.participant.ParticipantDetailsTab;
import edu.duke.cabig.c3pr.web.participant.ParticipantWrapper;
import gov.nih.nci.cabig.ctms.web.tabs.Flow;

/**
 * @author Ramakrishna
 * 
 */
public class EditParticipantController<C extends ParticipantWrapper> extends
                AutomaticSaveAjaxableFormController<C, Participant, ParticipantDao> {

    private static Log log = LogFactory.getLog(EditParticipantController.class);

    private ParticipantDao participantDao;

    private HealthcareSiteDao healthcareSiteDao;

    protected ConfigurationProperty configurationProperty;
    
    public ParticipantRepository participantRepository ;
    
    private RaceCodeAssociationDao raceCodeAssociationDao;
    
    public RaceCodeAssociationDao getRaceCodeAssociationDao() {
		return raceCodeAssociationDao;
	}

	public void setRaceCodeAssociationDao(
			RaceCodeAssociationDao raceCodeAssociationDao) {
		this.raceCodeAssociationDao = raceCodeAssociationDao;
	}

    public EditParticipantController() {
        setCommandClass(ParticipantWrapper.class);
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
        return ((ParticipantWrapper)command).getParticipant();
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
    	ParticipantWrapper participantWrapper = new ParticipantWrapper() ;
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
        participantWrapper.setParticipant(participant);
        return participantWrapper;
    }

    protected void initBinder(HttpServletRequest req, ServletRequestDataBinder binder)
                    throws Exception {
        super.initBinder(req, binder);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat(
                        "MM/dd/yyyy"), true));
        binder.registerCustomEditor(HealthcareSite.class, new CustomDaoEditor(healthcareSiteDao));
        binder.registerCustomEditor(ContactMechanismType.class, new EnumByNameEditor(
                        ContactMechanismType.class));
        binder.registerCustomEditor(RaceCodeEnum.class, new EnumByNameEditor(RaceCodeEnum.class));
        binder.registerCustomEditor(RaceCodeAssociation.class, new CustomDaoEditor(raceCodeAssociationDao));
    }
    
    @Override
    protected Object currentFormObject(HttpServletRequest request,
    		Object oCommand) throws Exception {
    		ParticipantWrapper participantWrapper = (ParticipantWrapper)oCommand;
	         if (WebUtils.hasSubmitParameter(request, ControllerTools.IDENTIFIER_VALUE_PARAM_NAME)) {
	         	Identifier identifier=ControllerTools.getIdentifierInRequest(request);
	         	List<Identifier> identifiers=new ArrayList<Identifier>();
	         	identifiers.add(identifier);
	         	Participant participant=participantRepository.getUniqueParticipant(identifiers);
	         	if(!participant.getId().equals(participantWrapper.getParticipant().getId())){
	         		// the participant in command is cached in session and not corresponding to identifier. So returning a participant
	         		// from db based on the identifier
		             participantDao.initialize(participant);
		             log.debug(" Participant's ID is:" + participant.getId());
		             participantWrapper.setParticipant(participant);
		             return participantWrapper;
	         	}
	         }
	         return super.currentFormObject(request, oCommand);
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
    	ParticipantWrapper participantWrapper = (ParticipantWrapper) oCommand;
        Participant participant = participantWrapper.getParticipant();
        participantDao.merge(participant);
        if(request.getParameter("goToRegistration")!=null && request.getParameter("goToRegistration").equals("true")){
    		ModelAndView modelAndView = new ModelAndView("redirect:../../registration/createRegistration?fromEditParticipant=true&participantId=" + request.getParameter("participantId"));
    		return modelAndView;
    	}
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
        ParticipantWrapper participantWrapper = (ParticipantWrapper)command;
        Participant participant = participantRepository.merge(participantWrapper.getParticipant());
        participantDao.initialize(participant);
        participantWrapper.setParticipant(participant);
        return (C)participantWrapper;
    }

	public ParticipantRepository getParticipantRepository() {
		return participantRepository;
	}

	public void setParticipantRepository(ParticipantRepository participantRepository) {
		this.participantRepository = participantRepository;
	}
}
