package edu.duke.cabig.c3pr.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import edu.duke.cabig.c3pr.utils.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;

import edu.duke.cabig.c3pr.dao.HealthcareSiteDao;
import edu.duke.cabig.c3pr.dao.ParticipantDao;
import edu.duke.cabig.c3pr.domain.Address;
import edu.duke.cabig.c3pr.domain.ContactMechanism;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.HealthcareSiteInvestigator;
import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.Investigator;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.validator.ParticipantValidator;
import edu.duke.cabig.c3pr.utils.ConfigurationProperty;
import edu.duke.cabig.c3pr.utils.Lov;
import edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.AbstractTabbedFlowFormController;
import edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.Flow;
import edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.Tab;

/**
 * @author Kulasekaran, Priyatam
 * 
 */
public class CreateParticipantController extends
		AbstractTabbedFlowFormController<Participant> {

	protected static final Log log = LogFactory
			.getLog(CreateParticipantController.class);

	private ParticipantDao participantDao;

	protected ConfigurationProperty configurationProperty;

	private ParticipantValidator participantValidator;

	private HealthcareSiteDao healthcareSiteDao;

	public CreateParticipantController() {
		setCommandClass(Participant.class);
		Flow<Participant> flow = new Flow<Participant>("Create Subject");
		intializeFlows(flow);
	}

	protected void intializeFlows(Flow<Participant> flow) {
		flow.addTab(new Tab<Participant>("Details", "Details",
				"participant/participant") {
			public Map<String, Object> referenceData() {
				Map<String, List<Lov>> configMap = configurationProperty
						.getMap();

				Map<String, Object> refdata = new HashMap<String, Object>();

				refdata.put("administrativeGenderCode", configMap
						.get("administrativeGenderCode"));
				refdata
						.put("ethnicGroupCode", configMap
								.get("ethnicGroupCode"));
				refdata.put("raceCode", configMap.get("raceCode"));
				refdata.put("source", getHealthcareSites());
				refdata.put("searchTypeRefData", configMap
						.get("participantSearchType"));
				refdata.put("identifiersTypeRefData", configMap
						.get("participantIdentifiersType"));

				return refdata;
			}
		});
		flow.addTab(new Tab<Participant>("Address & Contact Info",
				"Address & ContactInfo", "participant/participant_address") {
			public Map<String, Object> referenceData() {
				Map<String, List<Lov>> configMap = configurationProperty
						.getMap();

				Map<String, Object> refdata = new HashMap<String, Object>();
				refdata.put("contactMechanismType", configMap
						.get("contactMechanismType"));

				return refdata;
			}
		});
		flow.addTab(new Tab<Participant>("Review and Submit ",
				"Review and Submit ", "participant/participant_submit"));

		setFlow(flow);
	}

	@Override
	protected Map<String, Object> referenceData(
			HttpServletRequest httpServletRequest, int page) throws Exception {
		// Currently the static data is a hack, once DB design is approved for
		// an LOV this will be
		// replaced with LOVDao to get the static data from individual tables
		System.out.println("------------Create Reference Data-------------");
		Map<String, Object> refdata = new HashMap<String, Object>();
		Map<String, List<Lov>> configMap = configurationProperty.getMap();

		if (page == 0) {
			if (httpServletRequest.getParameter("studySiteId") != null) {
				if (!httpServletRequest.getParameter("studySiteId").equals("")) {
					refdata.put("studySiteId", httpServletRequest
							.getParameter("studySiteId"));
				}
			}
		}

		if (isSubFlow(httpServletRequest)) {
			processSubFlow(httpServletRequest, refdata);
		}
		return refdata;
	}

	@Override
	protected Object formBackingObject(HttpServletRequest request)
			throws Exception {

		// FIXME: small hack
		if (request.getParameter("url") != null) {
			if (request.getParameter("studySiteId") != null) {
				setPages(new String[] {
						"registration/reg_create_patient_study",
						"registration/reg_create_patient_address_study",
						"registration/reg_create_patient_submit_study" });
			} else {
				setPages(new String[] { "registration/reg_create_patient",
						"registration/reg_create_patient_address",
						"registration/reg_create_patient_submit" });
			}
			request.getSession().setAttribute("url",
					request.getParameter("url"));
			request.getSession().setAttribute("studySiteId",
					request.getParameter("studySiteId"));
		} else {
			setPages(new String[] { "participant/participant",
					"participant/participant_address",
					"participant/participant_submit" });
			request.getSession().removeAttribute("url");
			request.getSession().removeAttribute("studySiteId");
		}

		Participant participant = (Participant) super
				.formBackingObject(request);
		{
			Identifier temp = new Identifier();
			temp.setPrimaryIndicator(false);
			participant.addIdentifier(temp);
		}
		participant = createParticipantWithContacts(participant);
		participant.setAddress(new Address());
		return participant;
	}
	
	private Participant createParticipantWithContacts(Participant participant) {

		ContactMechanism contactMechanismEmail = new ContactMechanism();
		ContactMechanism contactMechanismPhone = new ContactMechanism();
		ContactMechanism contactMechanismFax = new ContactMechanism();
		contactMechanismEmail.setType("Email");
		contactMechanismPhone.setType("Phone");
		contactMechanismFax.setType("Fax");
		participant.addContactMechanism(contactMechanismEmail);
		participant.addContactMechanism(contactMechanismPhone);
		participant.addContactMechanism(contactMechanismFax);
		return participant;
	}

	@Override
	protected void initBinder(HttpServletRequest req,
			ServletRequestDataBinder binder) throws Exception {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("MM/dd/yyyy"), true));
	}

	@Override
	protected void postProcessPage(HttpServletRequest request, Object Command,
			Errors errors, int page) {
		Participant participant = (Participant) Command;

		handleRowAction(participant, page, request.getParameter("_action"),
				request.getParameter("_selected"));

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
		Participant command = (Participant) oCommand;

		Iterator<Identifier> iterator = command.getIdentifiers().iterator();

		while (iterator.hasNext()) {
			Identifier identifier = iterator.next();
			if (identifier.getType().trim().length() == 0
					|| identifier.getValue().trim().length() == 0) {
				iterator.remove();
			}
		}
		
		Iterator<ContactMechanism> cMIterator = command.getContactMechanisms().iterator();
		StringUtils strUtil = new StringUtils();
		while (cMIterator.hasNext()) {
			ContactMechanism contactMechanism = cMIterator.next();
			if (strUtil.isBlank(contactMechanism.getValue()))
				cMIterator.remove();
			}
						
		participantDao.save(command);

		ModelAndView modelAndView = null;
		// FIXME: small hack
		if (isSubFlow(request)) {
			String url = "";
			if (request.getParameter("studySiteId") != null) {
				url = "createRegistration?resumeFlow=true&_page=1&_target3=3";
				url += "&participant=" + Integer.toString(command.getId());
				url += "&studySite=" + request.getParameter("studySiteId");
			} else {
				url = "searchStudy";
				url += "?inRegistration=true&subjectId="
						+ Integer.toString(command.getId());
			}
			response.sendRedirect(url);
			return null;
		}
		response.sendRedirect("confirmCreateParticipant?lastName="
				+ command.getLastName() + "&type=confirm");
		return null;
	}

	private boolean isSubFlow(HttpServletRequest request) {
		if (request.getParameter("inRegistration") != null
				|| request.getParameter("studySiteId") != null)
			return true;
		return false;
	}

	private void processSubFlow(HttpServletRequest request, Map map) {
		map.put("registrationTab", getRegistrationFlow(request).getTab(2));
		map.put("inRegistration", "true");
		map.put("actionReturnType", "CreateParticipant");
	}

	private Flow getRegistrationFlow(HttpServletRequest request) {
		return (Flow) request.getSession().getAttribute("registrationFlow");
	}

	/*
	 * protected void validatePage(Object command, Errors errors, int page,
	 * boolean finish) { Participant participant = (Participant) command; switch
	 * (page) { case 0: { participantValidator
	 * .validateParticipantDetails(participant, errors); //
	 * participantValidator.validateIdentifiers(participant, errors); } break;
	 * case 1: participantValidator .validateParticipantAddress(participant,
	 * errors); break; case 2: // break; } }
	 */

	protected List<HealthcareSite> getHealthcareSites() {
		return healthcareSiteDao.getAll();
	}

	public ConfigurationProperty getConfigurationProperty() {
		return configurationProperty;
	}

	public void setConfigurationProperty(
			ConfigurationProperty configurationProperty) {
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

	public void setParticipantValidator(
			ParticipantValidator participantValidator) {
		this.participantValidator = participantValidator;
	}

	public HealthcareSiteDao getHealthcareSiteDao() {
		return healthcareSiteDao;
	}

	public void setHealthcareSiteDao(HealthcareSiteDao healthcareSiteDao) {
		this.healthcareSiteDao = healthcareSiteDao;
	}

}
