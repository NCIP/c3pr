package edu.duke.cabig.c3pr.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Hashtable;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import edu.duke.cabig.c3pr.dao.HealthcareSiteDao;
import edu.duke.cabig.c3pr.dao.ParticipantDao;
import edu.duke.cabig.c3pr.dao.StudyDao;
import edu.duke.cabig.c3pr.domain.*;
import edu.duke.cabig.c3pr.utils.ConfigurationProperty;
import edu.duke.cabig.c3pr.utils.Lov;
import edu.duke.cabig.c3pr.utils.StringUtils;
import edu.duke.cabig.c3pr.utils.web.propertyeditors.CustomDaoEditor;
import edu.duke.cabig.c3pr.web.participant.ParticipantDetailsTab;
import edu.duke.cabig.c3pr.web.participant.ParticipantAddressAndContactInfoTab;
import edu.duke.cabig.c3pr.web.participant.ParticipantSummaryTab;
import edu.duke.cabig.c3pr.utils.web.propertyeditors.ObjectGraphBasedEditor;
import edu.duke.cabig.c3pr.utils.web.propertyeditors.EnumByNameEditor;
import edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.AutomaticSaveAjaxableFormController;

import edu.duke.cabig.c3pr.web.beans.DefaultObjectPropertyReader;
import gov.nih.nci.cabig.ctms.web.tabs.AbstractTabbedFlowFormController;
import gov.nih.nci.cabig.ctms.web.tabs.AutomaticSaveFlowFormController;
import gov.nih.nci.cabig.ctms.web.tabs.Flow;
import gov.nih.nci.cabig.ctms.web.tabs.Tab;

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
		flow.addTab(new ParticipantSummaryTab());
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
	protected Object formBackingObject(HttpServletRequest request)
			throws Exception {
		Participant participant = null;

		if (request.getParameter("participantId") != null) {
			participant = participantDao.getById(Integer.parseInt(request
					.getParameter("participantId")), true);
			log.debug(" Participant's ID is:" + participant.getId());
		}

		boolean contactMechanismEmailPresent = false, contactMechanismPhonePresent = false, contactMechanismFaxPresent = false;
		for (ContactMechanism contactMechanism : participant
				.getContactMechanisms()) {
			if (contactMechanism.getType().equals(ContactMechanismType.EMAIL))
				contactMechanismEmailPresent = true;

			if (contactMechanism.getType().equals(ContactMechanismType.PHONE))
				contactMechanismPhonePresent = true;

			if (contactMechanism.getType().equals(ContactMechanismType.Fax))
				contactMechanismFaxPresent = true;

		}
		if (!contactMechanismEmailPresent) {
			ContactMechanism contactMechanismEmail = new ContactMechanism();
			contactMechanismEmail.setType(ContactMechanismType.EMAIL);
			participant.getContactMechanisms().add(0, contactMechanismEmail);
		}
		if (!contactMechanismPhonePresent) {
			ContactMechanism contactMechanismPhone = new ContactMechanism();
			contactMechanismPhone.setType(ContactMechanismType.PHONE);
			participant.getContactMechanisms().add(1, contactMechanismPhone);
		}
		if (!contactMechanismFaxPresent) {
			ContactMechanism contactMechanismFax = new ContactMechanism();
			contactMechanismFax.setType(ContactMechanismType.Fax);
			participant.getContactMechanisms().add(2, contactMechanismFax);
		}

		return participant;
	}

	protected void initBinder(HttpServletRequest req,
			ServletRequestDataBinder binder) throws Exception {
		super.initBinder(req, binder);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("MM/dd/yyyy"), true));
		binder.registerCustomEditor(HealthcareSite.class, new CustomDaoEditor(
				healthcareSiteDao));
		binder.registerCustomEditor(ContactMechanismType.class,
                new EnumByNameEditor(ContactMechanismType.class));
	}
	

    @Override
    protected Object currentFormObject(HttpServletRequest request, Object sessionFormObject) throws Exception {
        if (sessionFormObject != null) {
        	Participant participant = (Participant) sessionFormObject;
            getDao().reassociate((Participant) sessionFormObject);
            getDao().refresh((Participant) sessionFormObject);
        }

        return sessionFormObject;
    }

	@Override
	protected ModelAndView processFinish(HttpServletRequest request,
			HttpServletResponse response, Object oCommand, BindException errors)
			throws Exception {
		Participant participant = (Participant) oCommand;
		Iterator<ContactMechanism> cMIterator = participant
				.getContactMechanisms().iterator();
		StringUtils strUtil = new StringUtils();
		while (cMIterator.hasNext()) {
			ContactMechanism contactMechanism = cMIterator.next();
			if (strUtil.isBlank(contactMechanism.getValue()))
				cMIterator.remove();
		}

		participantDao.save(participant);
		ModelAndView modelAndView = new ModelAndView(new RedirectView(
				"searchparticipant.do"));
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

	public void setConfigurationProperty(
			ConfigurationProperty configurationProperty) {
		this.configurationProperty = configurationProperty;
	}
}
