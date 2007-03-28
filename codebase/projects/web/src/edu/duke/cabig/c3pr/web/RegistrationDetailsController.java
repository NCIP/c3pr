package edu.duke.cabig.c3pr.web;

import java.text.SimpleDateFormat;
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

import edu.duke.cabig.c3pr.dao.HealthcareSiteDao;
import edu.duke.cabig.c3pr.dao.ParticipantDao;
import edu.duke.cabig.c3pr.dao.StudyParticipantAssignmentDao;
import edu.duke.cabig.c3pr.dao.StudySiteDao;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.StudyParticipantAssignment;
import edu.duke.cabig.c3pr.utils.ConfigurationProperty;
import edu.duke.cabig.c3pr.utils.Lov;
import edu.duke.cabig.c3pr.utils.web.propertyeditors.CustomDaoEditor;
import edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.Flow;
import edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.AbstractTabbedFlowFormController;
import edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.Tab;

/**
 * @author Ramakrishna
 * 
 */

public class RegistrationDetailsController extends RegistrationController {

	private static Log log = LogFactory.getLog(RegistrationDetailsController.class);
	
	public RegistrationDetailsController() {
		super("Registration Management");
	}

	protected void intializeFlows(Flow<StudyParticipantAssignment> flow) {
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
			handleIdentifierAction(registration,
					request.getParameter("_action"), request
							.getParameter("_selected"));
		}

		if (("update").equals(request.getParameter("_update"))) {
			registrationDao.save(registration);
		}
	}
}
