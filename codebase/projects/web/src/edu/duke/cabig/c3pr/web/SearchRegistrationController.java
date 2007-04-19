package edu.duke.cabig.c3pr.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import edu.duke.cabig.c3pr.dao.ParticipantDao;
import edu.duke.cabig.c3pr.dao.StudyParticipantAssignmentDao;
import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyParticipantAssignment;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.utils.ConfigurationProperty;
import edu.duke.cabig.c3pr.utils.Lov;
import edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.Flow;

/**
 * 
 * @author Ramakrishna
 */
public class SearchRegistrationController extends SimpleFormController {

	private static Log log = LogFactory
			.getLog(SearchRegistrationController.class);

	private StudyParticipantAssignmentDao registrationDao;

	private ConfigurationProperty configurationProperty;

	public SearchRegistrationController() {
		setCommandClass(SearchCommand.class);
		this.setFormView("registration_search");
		this.setSuccessView("registration_search_results");
	}

	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object oCommand, BindException errors)
			throws Exception {

		SearchCommand searchRegistrationCommand = (SearchCommand) oCommand;
		StudyParticipantAssignment registration = new StudyParticipantAssignment();
		String text = searchRegistrationCommand.getSearchText();
		String type = searchRegistrationCommand.getSearchType();
		Map<String, List<Lov>> configMap = configurationProperty.getMap();
		log.debug("search string = " + text + "; type = " + type);

		List<StudyParticipantAssignment> registrations = registrationDao
				.searchByExample(registration, true);
		log.debug("Search results size " + registrations.size());
		Map map = errors.getModel();
		map.put("registrations", registrations);
		map.put("searchTypeRefDataPrt", configMap.get("participantSearchType"));
		map.put("searchTypeRefDataStudy", configMap.get("studySearchType"));
		ModelAndView modelAndView = new ModelAndView(getSuccessView(), map);
		return modelAndView;
	}

	protected Map<String, Object> referenceData(HttpServletRequest request)
			throws Exception {
		Map<String, Object> refdata = new HashMap<String, Object>();
		Map<String, List<Lov>> configMap = configurationProperty.getMap();

		refdata.put("searchTypeRefDataPrt", configMap
				.get("participantSearchType"));
		refdata.put("searchTypeRefDataStudy", configMap.get("studySearchType"));
		return refdata;
	}

	public ConfigurationProperty getConfigurationProperty() {
		return configurationProperty;
	}

	public void setConfigurationProperty(
			ConfigurationProperty configurationProperty) {
		this.configurationProperty = configurationProperty;
	}

	public StudyParticipantAssignmentDao getRegistrationDao() {
		return registrationDao;
	}

	public void setRegistrationDao(StudyParticipantAssignmentDao registrationDao) {
		this.registrationDao = registrationDao;
	}

}