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
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import edu.duke.cabig.c3pr.dao.HealthcareSiteDao;
import edu.duke.cabig.c3pr.dao.ParticipantDao;
import edu.duke.cabig.c3pr.domain.ContactMechanism;
import edu.duke.cabig.c3pr.domain.ContactMechanismType;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.RaceCode;
import edu.duke.cabig.c3pr.domain.RegistrationWorkFlowStatus;
import edu.duke.cabig.c3pr.domain.validator.ParticipantValidator;
import edu.duke.cabig.c3pr.service.PersonnelService;
import edu.duke.cabig.c3pr.tools.Configuration;
import edu.duke.cabig.c3pr.utils.ConfigurationProperty;
import edu.duke.cabig.c3pr.utils.web.propertyeditors.CustomDaoEditor;
import edu.duke.cabig.c3pr.utils.web.propertyeditors.EnumByNameEditor;
import edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.AutomaticSaveAjaxableFormController;
import edu.duke.cabig.c3pr.web.participant.ParticipantAddressAndContactInfoTab;
import edu.duke.cabig.c3pr.web.participant.ParticipantDetailsTab;
import edu.duke.cabig.c3pr.web.participant.ParticipantSubmitTab;
import gov.nih.nci.cabig.ctms.web.tabs.Flow;

/**
 * @author Kulasekaran, Priyatam
 * 
 */

public class CreateParticipantController<C extends Participant> extends
                AutomaticSaveAjaxableFormController<C, Participant, ParticipantDao> {

    protected static final Log log = LogFactory.getLog(CreateParticipantController.class);

    private ParticipantDao participantDao;

    protected ConfigurationProperty configurationProperty;

    private ParticipantValidator participantValidator;
    
    private PersonnelService personnelService;

    private HealthcareSiteDao healthcareSiteDao;
    
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

    @Override
    protected Object formBackingObject(HttpServletRequest request) throws Exception {
        Participant participant = (Participant) super.formBackingObject(request);
        participant = createParticipantWithContacts(participant);
        return participant;
    }

    protected void layoutTabs(Flow flow) {
        flow.addTab(new ParticipantDetailsTab());
        flow.addTab(new ParticipantAddressAndContactInfoTab());
        flow.addTab(new ParticipantSubmitTab());
    }

    private Participant createParticipantWithContacts(Participant participant) {

        ContactMechanism contactMechanismEmail = new ContactMechanism();
        ContactMechanism contactMechanismPhone = new ContactMechanism();
        ContactMechanism contactMechanismFax = new ContactMechanism();
        contactMechanismEmail.setType(ContactMechanismType.EMAIL);
        contactMechanismPhone.setType(ContactMechanismType.PHONE);
        contactMechanismFax.setType(ContactMechanismType.Fax);
        participant.addContactMechanism(contactMechanismEmail);
        participant.addContactMechanism(contactMechanismPhone);
        participant.addContactMechanism(contactMechanismFax);
        return participant;
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
        Participant command = (Participant) oCommand;
        addCreatingOrganization(command, request);
        command.setId(participantDao.merge(command).getId());

        ModelAndView modelAndView = null;
        if (request.getParameter("async") != null) {
            response.getWriter().print(
                            command.getFirstName() + " " + command.getLastName() + " ("
                                            + command.getIdentifiers().get(0).getType() + " - "
                                            + command.getIdentifiers().get(0).getValue() + ")"
                                            + "||" + command.getId());
            return null;
        }
        response.sendRedirect("confirmCreateParticipant?lastName=" + command.getLastName()
                        + "&firstName=" + command.getFirstName() + "&middleName="
                        + command.getMiddleName() + "&primaryIdentifier="
                        + command.getPrimaryIdentifier() + "&type=confirm");
        return null;
    }

    
    private void addCreatingOrganization(Participant participant, HttpServletRequest request){
    	HealthcareSite hcs = personnelService.getLoggedInUsersOrganization(request);
    	List<HealthcareSite> hcsList = participant.getHealthcareSites();
    	if(hcs != null){
//    		if(hcs.getParticipants() == null){
//    			List<Participant> pList = new ArrayList<Participant>();
//        		pList.add(participant);
//        		hcs.setParticipants(pList);
//        	} else {
//        		hcs.getParticipants().add(participant);
//        	}
    	} else {
    		hcs = healthcareSiteDao.getByNciInstituteCode(configuration.get(Configuration.LOCAL_NCI_INSTITUTE_CODE));
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

    public ParticipantValidator getParticipantValidator() {
        return participantValidator;
    }

    public void setParticipantValidator(ParticipantValidator participantValidator) {
        this.participantValidator = participantValidator;
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

}
