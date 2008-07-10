package edu.duke.cabig.c3pr.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import edu.duke.cabig.c3pr.dao.HealthcareSiteDao;
import edu.duke.cabig.c3pr.dao.ParticipantDao;
import edu.duke.cabig.c3pr.domain.ContactMechanismType;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.SystemAssignedIdentifier;
import edu.duke.cabig.c3pr.utils.ConfigurationProperty;
import edu.duke.cabig.c3pr.utils.web.propertyeditors.CustomDaoEditor;
import edu.duke.cabig.c3pr.utils.web.propertyeditors.EnumByNameEditor;
import edu.duke.cabig.c3pr.web.participant.ParticipantRegistrationsTab;
import edu.duke.cabig.c3pr.web.participant.ParticipantSummaryTab;
import gov.nih.nci.cabig.ctms.web.tabs.AutomaticSaveFlowFormController;
import gov.nih.nci.cabig.ctms.web.tabs.Flow;
import gov.nih.nci.cabig.ctms.web.tabs.Tab;

/**
 * @author Ramakrishna
 * 
 */
public class ViewParticipantController<C extends Participant> extends
                AutomaticSaveFlowFormController<C, Participant, ParticipantDao> {

    private static Log log = LogFactory.getLog(ViewParticipantController.class);

    private ParticipantDao participantDao;

    private HealthcareSiteDao healthcareSiteDao;
    

    protected ConfigurationProperty configurationProperty;

    public ViewParticipantController() {
        setCommandClass(Participant.class);
        Flow<C> flow = new Flow<C>("View Subject");
        layoutTabs(flow);
        setFlow(flow);
        setBindOnNewForm(true);
    }

    protected void layoutTabs(Flow flow) {
        flow.addTab(new ParticipantSummaryTab());
        flow.addTab(new ParticipantRegistrationsTab());
    }

    @Override
    protected ParticipantDao getDao() {
        return participantDao;
    }

    @Override
    protected Participant getPrimaryDomainObject(C command) {
        return command;
    }

    protected boolean shouldSave(HttpServletRequest request, Participant command,
                    Tab<Participant> tab) {
        return false;

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

        if (request.getParameter("participantId") != null) {
            participant = participantDao.getById(Integer.parseInt(request
                            .getParameter("participantId")), true);
            log.debug(" Participant's ID is:" + participant.getId());
        }else {
        	
        	List<Participant> participants = null;
        	String name = null;
        	if(request.getParameter("name")!=null){
        		name = request.getParameter("name");
        	}
        	
        	String type = null;
        	if (request.getParameter("type")!=null){
        		 type = request.getParameter("type");
        	}
        	
        	String value = null;
        	if(request.getParameter("value")!=null){
        		value = request.getParameter("value");
        	}
        	
        	if(request.getParameter("assignedBy")!=null){
        		String assignedBy = request.getParameter("assignedBy");
        		if(assignedBy.equals("system")){
        			SystemAssignedIdentifier sysIdentifier = new SystemAssignedIdentifier();   
        			sysIdentifier.setSystemName(name);
        			sysIdentifier.setType(type);
        			sysIdentifier.setValue(value);
        			
                	participants = participantDao.searchBySystemAssignedIdentifier(sysIdentifier);
        			
        		}else if(assignedBy.equals("organization")){
        			OrganizationAssignedIdentifier orgIdentifier = new OrganizationAssignedIdentifier(); 
        			HealthcareSite healthcareSite= healthcareSiteDao.getByNciInstituteCode(name);
        			orgIdentifier.setHealthcareSite(healthcareSite);
        			orgIdentifier.setType(type);
        			orgIdentifier.setValue(value);
        			participants = participantDao.searchByOrgIdentifier(orgIdentifier);
        		}
        	}
        	
        	if (participants.size()> 0){
        		participant = participants.get(0);
        		log.debug(" Participant's ID is:" + participant.getId());
        	}
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
    protected Object currentFormObject(HttpServletRequest request, Object sessionFormObject)
                    throws Exception {
        if (sessionFormObject != null) {
            Participant participant = (Participant) sessionFormObject;
            getDao().reassociate((Participant) sessionFormObject);
        }

        return sessionFormObject;
    }

    @Override
    protected Map referenceData(HttpServletRequest request, int page) throws Exception {
        request.setAttribute("flowType", "VIEW_SUBJECT");
        return super.referenceData(request, page);
    }

    @Override
    protected ModelAndView processFinish(HttpServletRequest request, HttpServletResponse response,
                    Object oCommand, BindException errors) throws Exception {
        Participant participant = (Participant) oCommand;
        ModelAndView modelAndView = new ModelAndView(new RedirectView(
                        "editParticipant?participantId=" + participant.getId()));
        return modelAndView;
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

}
