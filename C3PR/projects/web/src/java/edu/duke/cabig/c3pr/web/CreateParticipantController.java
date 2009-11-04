package edu.duke.cabig.c3pr.web;

import java.text.SimpleDateFormat;
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
import edu.duke.cabig.c3pr.constants.RaceCode;
import edu.duke.cabig.c3pr.dao.HealthcareSiteDao;
import edu.duke.cabig.c3pr.dao.ParticipantDao;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.validator.ParticipantValidator;
import edu.duke.cabig.c3pr.service.PersonnelService;
import edu.duke.cabig.c3pr.tools.Configuration;
import edu.duke.cabig.c3pr.utils.ConfigurationProperty;
import edu.duke.cabig.c3pr.utils.IdentifierGenerator;
import edu.duke.cabig.c3pr.utils.web.ControllerTools;
import edu.duke.cabig.c3pr.utils.web.propertyeditors.CustomDaoEditor;
import edu.duke.cabig.c3pr.utils.web.propertyeditors.EnumByNameEditor;
import edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.AutomaticSaveAjaxableFormController;
import edu.duke.cabig.c3pr.web.participant.ParticipantAddressAndContactInfoTab;
import edu.duke.cabig.c3pr.web.participant.ParticipantDetailsTab;
import gov.nih.nci.cabig.ctms.web.tabs.Flow;
import gov.nih.nci.cabig.ctms.web.tabs.Tab;

/**
 * @author Kulasekaran, Priyatam
 * 
 */

public class CreateParticipantController<C extends Participant> extends
                AutomaticSaveAjaxableFormController<C, Participant, ParticipantDao> {

    protected static final Log log = LogFactory.getLog(CreateParticipantController.class);

    private ParticipantDao participantDao;

    protected ConfigurationProperty configurationProperty;

    private PersonnelService personnelService;

    private HealthcareSiteDao healthcareSiteDao;
    
    private ParticipantValidator participantValidator;
    
    public IdentifierGenerator identifierGenerator ;
    
    public ParticipantValidator getParticipantValidator() {
		return participantValidator;
	}

	public void setParticipantValidator(ParticipantValidator participantValidator) {
		this.participantValidator = participantValidator;
	}

	private Configuration configuration;

    public CreateParticipantController() {
        setCommandClass(Participant.class);
        Flow<C> flow = new Flow<C>("Create Subject");
        layoutTabs(flow);
        setFlow(flow);
        setBindOnNewForm(true);
    }

    @Override
    protected ParticipantDao getDao() {
        return participantDao;
    }

    @Override
    protected Participant getPrimaryDomainObject(C command) {
        return command;
    }
    
    @Override
    protected boolean isFormSubmission(HttpServletRequest request) {
        if (WebUtils.hasSubmitParameter(request, "async")) {
            try {
                request.getSession(false).setAttribute(getFormSessionAttributeName(),
                                formBackingObject(request));
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return super.isFormSubmission(request);
    }

    protected void layoutTabs(Flow flow) {
        flow.addTab(new ParticipantDetailsTab());
        flow.addTab(new ParticipantAddressAndContactInfoTab());
    }

    @Override
    protected void initBinder(HttpServletRequest req, ServletRequestDataBinder binder)
                    throws Exception {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat(
                        "MM/dd/yyyy"), true));
        binder.registerCustomEditor(HealthcareSite.class, new CustomDaoEditor(healthcareSiteDao));
        binder.registerCustomEditor(ContactMechanismType.class, new EnumByNameEditor(
                        ContactMechanismType.class));
        binder.registerCustomEditor(RaceCode.class, new EnumByNameEditor(
                RaceCode.class));

    }

    @Override
    protected void onBind(HttpServletRequest request, Object command, BindException errors)
                    throws Exception {
        super.onBind(request, command, errors);
    }
    
    

    @Override
    protected ModelAndView processFinish(HttpServletRequest request, HttpServletResponse response,
                    Object oCommand, BindException errors) throws Exception {
    	
        Participant participant = (Participant) oCommand;
        
        if (request.getParameter("async") != null) {
        	 OrganizationAssignedIdentifier mrn = participant.getMRN();
	        if ((mrn != null) && (mrn.getHealthcareSite() != null)) {
	            List<OrganizationAssignedIdentifier> participantsWithMRN = participantDao
	                            .getSubjectIdentifiersWithMRN(mrn.getValue(), mrn.getHealthcareSite());
	            if (participantsWithMRN.size() > 0) {
	             response.sendError(499,"Participant with mrn " + mrn.getValue() + " already exists for the organization " + mrn.getHealthcareSite().getName());
	            }
	        }
        }
        addCreatingOrganization(participant, request);
        participant.setId(participantDao.merge(participant).getId());
        
        ModelAndView modelAndView = null;
        if (request.getParameter("async") != null) {
            response.getWriter().print(
            		participant.getFirstName() + " " + participant.getLastName() + " ("
                                            + participant.getIdentifiers().get(0).getTypeInternal() + " - "
                                            + participant.getIdentifiers().get(0).getValue() + ")"
                                            + "||" + participant.getId());
            return null;
        }
        response.sendRedirect("confirmCreateParticipant?"+ControllerTools.createParameterString(participant.getOrganizationAssignedIdentifiers().get(0)));
        return null;
    }

    
    @Override
	protected boolean suppressValidation(HttpServletRequest request,
			Object command, BindException errors) {
		return (request.getParameter("async") != null);
	}

	private void addCreatingOrganization(Participant participant, HttpServletRequest request){
    	HealthcareSite hcs = personnelService.getLoggedInUsersOrganization(request);
    	List<HealthcareSite> hcsList = participant.getHealthcareSites();
    	if(hcs == null){
    		hcs = healthcareSiteDao.getByPrimaryIdentifier(configuration.get(Configuration.LOCAL_NCI_INSTITUTE_CODE));
    	}
    	hcsList.add(hcs);
    	
    }
    
    //throwing a stack overflow...hence commented out
    protected List<HealthcareSite> getHealthcareSites() {
        return null; //healthcareSiteDao.getAll();
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

    public HealthcareSiteDao getHealthcareSiteDao() {
        return healthcareSiteDao;
    }

    public void setHealthcareSiteDao(HealthcareSiteDao healthcareSiteDao) {
        this.healthcareSiteDao = healthcareSiteDao;
    }

	public PersonnelService getPersonnelService() {
		return personnelService;
	}

	public void setPersonnelService(PersonnelService personnelService) {
		this.personnelService = personnelService;
	}

	public Configuration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}
	
    @Override
    protected boolean shouldPersist(HttpServletRequest request, C command, Tab<C> tab) {
        if (WebUtils.hasSubmitParameter(request, "dontSave")) {
        	return false;
        }
        return true;
    }
    
    @Override
    protected boolean isNextPageSavable(HttpServletRequest request, C command, Tab<C> tab) {
    	return true;
    }
    
    @Override
    protected C save(C command, Errors errors) {
    	Participant participant = (Participant)command;
        command = (C)participantDao.merge(participant);
        participantDao.initialize(command);
        return command;
    }

	public IdentifierGenerator getIdentifierGenerator() {
		return identifierGenerator;
	}

	public void setIdentifierGenerator(IdentifierGenerator identifierGenerator) {
		this.identifierGenerator = identifierGenerator;
	}
}