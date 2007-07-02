package edu.duke.cabig.c3pr.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
import edu.duke.cabig.c3pr.domain.ContactMechanism;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.utils.ConfigurationProperty;
import edu.duke.cabig.c3pr.utils.Lov;
import edu.duke.cabig.c3pr.utils.StringUtils;
import edu.duke.cabig.c3pr.utils.web.propertyeditors.CustomDaoEditor;
import edu.duke.cabig.c3pr.web.participant.ParticipantAddressAndContactInfoTab;
import edu.duke.cabig.c3pr.web.participant.ParticipantDetailsTab;
import gov.nih.nci.cabig.ctms.web.tabs.AutomaticSaveFlowFormController;
import gov.nih.nci.cabig.ctms.web.tabs.Flow;

/**
 * @author Ramakrishna
 * 
 */
public class EditParticipantController<C extends Participant> extends
		AutomaticSaveFlowFormController<C, Participant, ParticipantDao> {

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

	@Override
	protected Map<String, Object> referenceData(
			HttpServletRequest httpServletRequest, int page) throws Exception {
		// Currently the static data is a hack, once DB design is approved for
		// an LOV this will be
		// replaced with LOVDao to get the static data from individual tables
		Map<String, Object> refdata = new HashMap<String, Object>();
		Map<String, List<Lov>> configMap = configurationProperty.getMap();
		if (("update")
				.equals((httpServletRequest.getParameter("_updateaction"))))
			switch (page) {
			case 0:
				refdata.put("updateMessageRefData", configMap.get(
						"editParticipantMessages").get(0));
				break;
			case 1:
				refdata.put("updateMessageRefData", configMap.get(
						"editParticipantMessages").get(1));
				break;
			default:
			}

		return refdata;
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
			System.out.println(" Request URl  is:"
					+ request.getRequestURL().toString());
			participant = participantDao.getById(Integer.parseInt(request
					.getParameter("participantId")), true);
			System.out.println(" Participant's ID is:" + participant.getId());
		}

		if (participant.getIdentifiers().size() == 0) {
			Identifier temp = new Identifier();
			temp.setSource("<enter value>");
			temp.setType("<enter value>");
			temp.setValue("<enter value>");
			temp.setPrimaryIndicator(false);
			participant.addIdentifier(temp);
		}
		boolean contactMechanismEmailPresent = false, contactMechanismPhonePresent = false, contactMechanismFaxPresent = false;
		for (ContactMechanism contactMechanism : participant
				.getContactMechanisms()) {
			if (contactMechanism.getType().equals("Email"))
				contactMechanismEmailPresent = true;

			if (contactMechanism.getType().equals("Phone"))
				contactMechanismPhonePresent = true;

			if (contactMechanism.getType().equals("Fax"))
				contactMechanismFaxPresent = true;

		}
		if (!contactMechanismEmailPresent) {
			ContactMechanism contactMechanismEmail = new ContactMechanism();
			contactMechanismEmail.setType("Email");
			participant.getContactMechanisms().add(0, contactMechanismEmail);
		}
		if (!contactMechanismPhonePresent) {
			ContactMechanism contactMechanismPhone = new ContactMechanism();
			contactMechanismPhone.setType("Phone");
			participant.getContactMechanisms().add(1, contactMechanismPhone);
		}
		if (!contactMechanismFaxPresent) {
			ContactMechanism contactMechanismFax = new ContactMechanism();
			contactMechanismFax.setType("Fax");
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
	}

	@Override
	protected void postProcessPage(HttpServletRequest request, Object Command,
			Errors errors, int page) {
		Participant participant = (Participant) Command;

		handleRowAction(participant, page, request.getParameter("_action"),
				request.getParameter("_selected"));

		if ("update".equals(request.getParameter("_action"))) {
			try {
				log.debug(" -- Updating Subject--");
				participantDao.save(participant);
			} catch (RuntimeException e) {
				log.debug("--Error while updating Subject--");
				e.printStackTrace();
			}
		}
	}

	private void handleRowAction(Participant participant, int page,
			String action, String selected) {
		switch (page) {
		case 0:
			if ("addIdentifier".equals(action)) {
				Identifier identifier = new Identifier();
				participant.addIdentifier(identifier);
			} else if ("removeIdentifier".equals(action)) {

				participant.getIdentifiers().remove(Integer.parseInt(selected));

				break;
			}
		case 1:
			if ("addContact".equals(action)) {
				ContactMechanism contactMechanism = new ContactMechanism();
				participant.addContactMechanism(contactMechanism);
			} else if ("removeContact".equals(action)) {
				participant.getContactMechanisms().remove(
						Integer.parseInt(selected));
			}
			break;
		default:
			// do Nothing

		}
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
