package edu.duke.cabig.c3pr.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import org.springframework.web.servlet.mvc.AbstractWizardFormController;
import org.springframework.web.servlet.view.RedirectView;

import edu.duke.cabig.c3pr.dao.HealthcareSiteDao;
import edu.duke.cabig.c3pr.dao.ParticipantDao;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.utils.ConfigurationProperty;
import edu.duke.cabig.c3pr.utils.Lov;
import edu.duke.cabig.c3pr.utils.web.propertyeditors.CustomDaoEditor;
import edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.AbstractTabbedFlowFormController;
import edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.Flow;
import edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.Tab;

/**
 * @author Ramakrishna
 * 
 */
public class EditParticipantController extends
		AbstractTabbedFlowFormController<Participant> {

	private static Log log = LogFactory.getLog(EditParticipantController.class);

	private ParticipantDao participantDao;

	private HealthcareSiteDao healthcareSiteDao;

	protected ConfigurationProperty configurationProperty;

	public EditParticipantController() {
		setCommandClass(Participant.class);
		Flow<Participant> flow = new Flow<Participant>("Edit Participant");
		intializeFlows(flow);
	}

	protected void intializeFlows(Flow<Participant> flow) {
		flow.addTab(new Tab<Participant>("Details", "Details",
				"participant/participant_edit_details") {
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
				refdata.put("source", healthcareSiteDao.getAll());
				refdata.put("searchTypeRefData", configMap
						.get("participantSearchType"));
				refdata.put("identifiersTypeRefData", configMap
						.get("participantIdentifiersType"));
				;

				return refdata;
			}
		});
		flow.addTab(new Tab<Participant>("Identifiers", "Identifiers",
				"participant/participant_edit_identifiers") {
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
				refdata.put("source", healthcareSiteDao.getAll());
				refdata.put("searchTypeRefData", configMap
						.get("participantSearchType"));
				refdata.put("identifiersTypeRefData", configMap
						.get("participantIdentifiersType"));
				;

				return refdata;
			}
		});
		flow.addTab(new Tab<Participant>("Address", "Address",
				"participant/participant_edit_addresses") {
			public Map<String, Object> referenceData() {
				Map<String, List<Lov>> configMap = configurationProperty
						.getMap();

				Map<String, Object> refdata = new HashMap<String, Object>();
				refdata.put("searchTypeRefData", configMap
						.get("participantSearchType"));

				return refdata;
			}
		});
		flow.addTab(new Tab<Participant>("Contact Info", "Contact Info",
				"participant/participant_edit_contactInfo") {
			public Map<String, Object> referenceData() {
				Map<String, List<Lov>> configMap = configurationProperty
						.getMap();

				Map<String, Object> refdata = new HashMap<String, Object>();
				refdata.put("searchTypeRefData", configMap
						.get("participantSearchType"));

				return refdata;
			}
		});
		setFlow(flow);
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
			case 2:
				refdata.put("updateMessageRefData", configMap.get(
						"editParticipantMessages").get(2));
				break;
			case 3:
				refdata.put("updateMessageRefData", configMap.get(
						"editParticipantMessages").get(3));
				break;
			default:
				refdata.put("updateMessageRefData", configMap.get(
						"editParticipantMessages").get(4));

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
		return participant;
	}

	@Override
	protected void initBinder(HttpServletRequest req,
			ServletRequestDataBinder binder) throws Exception {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("MM/dd/yyyy"), true));
		binder.registerCustomEditor(HealthcareSite.class, new CustomDaoEditor(
				healthcareSiteDao));
	}

	@Override
	protected void postProcessPage(HttpServletRequest request, Object oCommand,
			Errors errors, int page) throws Exception {
		Participant participant = (Participant) oCommand;
		if (page == 1) {
			handleIdentifierAction(participant,
					request.getParameter("_action"), request
							.getParameter("_selected"));
		}

		if (("update").equals(request.getParameter("_update"))) {
			participantDao.save(participant);
		}
	}

	@Override
	protected ModelAndView processFinish(HttpServletRequest request,
			HttpServletResponse response, Object oCommand, BindException errors)
			throws Exception {
		ModelAndView modelAndView = new ModelAndView(new RedirectView(
				"searchparticipant.do"));
		return modelAndView;
	}

	private void handleIdentifierAction(Participant participant, String action,
			String selected) {
		if ("addIdentifier".equals(action)) {
			log.debug("Requested Add Identifier");
			Identifier id = new Identifier();
			id.setSource("Duke");
			id.setValue("<enter value>");
			participant.addIdentifier(id);
		} else if ("removeIdentifier".equals(action)) {
			log.debug("Requested Remove Identifier");
			participant.getIdentifiers().remove(Integer.parseInt(selected));
		}
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
