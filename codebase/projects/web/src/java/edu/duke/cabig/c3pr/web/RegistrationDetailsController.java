package edu.duke.cabig.c3pr.web;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;

import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.StudyParticipantAssignment;
import edu.duke.cabig.c3pr.utils.Lov;
import edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.SubFlow;
import gov.nih.nci.cabig.ctms.web.tabs.Flow;
import gov.nih.nci.cabig.ctms.web.tabs.Tab;

/**
 * @author Ramakrishna
 * 
 */

public class RegistrationDetailsController extends RegistrationController {

	private static Log log = LogFactory
			.getLog(RegistrationDetailsController.class);

	public RegistrationDetailsController() {
		super("Registration Management");
	}

	protected void intializeFlows(SubFlow<StudyParticipantAssignment> flow) {
		flow.addTab(new Tab<StudyParticipantAssignment>("Details", "Details",
				"registration/reg_details_study_participant") {
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
		flow.addTab(new Tab<StudyParticipantAssignment>("Identifiers",
				"Identifiers", "registration/reg_details_identifiers") {
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
		flow.addTab(new Tab<StudyParticipantAssignment>("Eligibility",
				"Eligibility", "registration/reg_details_eligibility") {
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
		flow.addTab(new Tab<StudyParticipantAssignment>("Stratification",
				"Stratification", "registration/reg_details_stratification") {
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
		flow.addTab(new Tab<StudyParticipantAssignment>("Randomization",
				"Randomization", "registration/reg_details_randomization") {
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
		flow.addTab(new Tab<StudyParticipantAssignment>("Status", "Status",
				"registration/reg_details_status") {
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
		setFlow(flow);
	}

	@Override
	protected Object formBackingObject(HttpServletRequest request)
			throws Exception {
		StudyParticipantAssignment registration = null;
		if ((request.getParameter("registrationId") != null)
				&& (request.getParameter("registrationId") != "")) {
			System.out.println(" Request URl  is:"
					+ request.getRequestURL().toString());
			System.out.println(" RegistrationId is: "
					+ Integer.parseInt(request.getParameter("registrationId")));
			System.out.println(" registration Dao is :"
					+ registrationDao.toString());
			registration = registrationDao.getById(Integer.parseInt(request
					.getParameter("registrationId")), true);
			System.out.println(" Registration ID is:" + registration.getId());
		} else {
			registration = new StudyParticipantAssignment();
			System.out
					.println("------------Command set to new Command------------------");
		}

		if (registration.getIdentifiers().size() == 0) {
			Identifier temp = new Identifier();
			temp.setSource("<enter value>");
			temp.setType("<enter value>");
			temp.setValue("<enter value>");
			temp.setPrimaryIndicator(false);
			registration.addIdentifier(temp);
		}
		return registration;
	}

	@Override
	protected ModelAndView processFinish(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, BindException arg3)
			throws Exception {

		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.servlet.mvc.AbstractFormController#formBackingObject(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected void postProcessPage(HttpServletRequest request, Object oCommand,
			Errors errors, int page) throws Exception {
		StudyParticipantAssignment registration = (StudyParticipantAssignment) oCommand;
		if (page == 1) {
			handleIdentifierAction(registration, request
					.getParameter("_action"), request.getParameter("_selected"));
		}

		if (("update").equals(request.getParameter("_updateaction"))) {

			Iterator<Identifier> iterator = registration.getIdentifiers()
					.iterator();

			while (iterator.hasNext()) {
				Identifier identifier = iterator.next();
				if (identifier.getSource().trim().equals("<enter value>")
						|| identifier.getType().trim().equals("<enter value>")
						|| identifier.getValue().equals("<enter value>")) {
					iterator.remove();
				}
			}

			try {
				log.debug("Updating Registration");
				registrationDao.save(registration);
			} catch (RuntimeException e) {
				log.debug("Unable to update Registration");
				e.printStackTrace();
			}

		}

	}

	@Override
	protected void intializeFlows(Flow<StudyParticipantAssignment> flow) {
		// TODO Auto-generated method stub
		
	}
}
