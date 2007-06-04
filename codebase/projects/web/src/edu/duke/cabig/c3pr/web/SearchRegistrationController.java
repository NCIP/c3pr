package edu.duke.cabig.c3pr.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import edu.duke.cabig.c3pr.dao.ParticipantDao;
import edu.duke.cabig.c3pr.dao.StudyDao;
import edu.duke.cabig.c3pr.dao.StudyParticipantAssignmentDao;
import edu.duke.cabig.c3pr.dao.StudySiteDao;
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

	private ParticipantDao participantDao;

	private StudyDao studyDao;

	private StudySiteDao studySiteDao;

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
		List<StudyParticipantAssignment> registrations = new ArrayList<StudyParticipantAssignment>();
		log.debug(" Search string is :" + text);
		if (request.getParameter("select").equals("Subject")) {
			Participant participant = new Participant();
			if (request.getParameter("SubjectOption").equals("N"))
				participant.setLastName(text);
			else {
				Identifier identifier = new Identifier();
				identifier.setValue(text);
				participant.addIdentifier(identifier);
			}
			List<Participant> participants = participantDao
					.searchByExample(participant);
			Set<Participant> participantSet = new TreeSet<Participant>();
	    	participantSet.addAll(participants);
	    	List<Participant> uniqueParticipants = new ArrayList<Participant>();
	    	uniqueParticipants.addAll(participantSet);
			for (Participant partVar : uniqueParticipants) {
				registrations = partVar.getStudyParticipantAssignments();
			}
		} else if (request.getParameter("select").equals("Study")) {
			Study study = new Study();
			if (request.getParameter("StudyOption").equals("shortTitle")) {
				study.setShortTitleText(text);

			} else if (request.getParameter("StudyOption").equals("longTitle")) {
				study.setLongTitleText(text);

			} else if (request.getParameter("StudyOption").equals("status")) {
				study.setStatus(text);
			} else {
				Identifier identifier = new Identifier();
				identifier.setValue(text);
				study.addIdentifier(identifier);
			}

			List<Study> studies = studyDao.searchByExample(study, true);
			Set<Study> studySet = new TreeSet<Study>();
	    	List<Study> uniqueStudies = new ArrayList<Study>();
	    	studySet.addAll(studies);
	    	uniqueStudies.addAll(studySet);
	    	for (Study studyVar : uniqueStudies) {
				for (StudySite studySite : studyVar.getStudySites()) {
					for (StudyParticipantAssignment studyParticipantAssignment : studySite
							.getStudyParticipantAssignments()) {
						registrations.add(studyParticipantAssignment);
					}
				}
			}
		} else if (request.getParameter("select").equals("Id")) {
			Identifier identifier = new Identifier();
			identifier.setValue(text);
			registration.addIdentifier(identifier);
			registrations = registrationDao.searchByExample(registration);
		}

		log.debug("Search registrations result size: " + registrations.size());
		Map<String, List<Lov>> configMap = configurationProperty.getMap();
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

	public ParticipantDao getParticipantDao() {
		return participantDao;
	}

	public void setParticipantDao(ParticipantDao participantDao) {
		this.participantDao = participantDao;
	}

	public StudyDao getStudyDao() {
		return studyDao;
	}

	public void setStudyDao(StudyDao studyDao) {
		this.studyDao = studyDao;
	}

	public StudySiteDao getStudySiteDao() {
		return studySiteDao;
	}

	public void setStudySiteDao(StudySiteDao studySiteDao) {
		this.studySiteDao = studySiteDao;
	}

}