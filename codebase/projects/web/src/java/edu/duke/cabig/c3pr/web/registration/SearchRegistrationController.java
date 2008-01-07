package edu.duke.cabig.c3pr.web.registration;

import java.util.ArrayList;
import java.util.HashMap;
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
import edu.duke.cabig.c3pr.dao.StudySubjectDao;
import edu.duke.cabig.c3pr.dao.StudySiteDao;
import edu.duke.cabig.c3pr.domain.CoordinatingCenterStudyStatus;
import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.SystemAssignedIdentifier;
import edu.duke.cabig.c3pr.utils.ConfigurationProperty;
import edu.duke.cabig.c3pr.utils.Lov;
import edu.duke.cabig.c3pr.web.SearchRegistrationCommand;

/**
 * 
 * @author Ramakrishna
 */
public class SearchRegistrationController extends SimpleFormController {

	private static Log log = LogFactory
			.getLog(SearchRegistrationController.class);

	private StudySubjectDao studySubjectDao;

	private ParticipantDao participantDao;

	private StudyDao studyDao;

	private StudySiteDao studySiteDao;

	private ConfigurationProperty configurationProperty;

	public SearchRegistrationController() {
		setCommandClass(SearchRegistrationCommand.class);
		this.setFormView("registration_search");
		this.setSuccessView("registration_search_results");
	}

	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object oCommand, BindException errors)
			throws Exception {

		SearchRegistrationCommand searchRegistrationCommand = (SearchRegistrationCommand) oCommand;
		StudySubject registration = new StudySubject();
		String text = searchRegistrationCommand.getSearchText();
		String type = searchRegistrationCommand.getSearchType();
		List<StudySubject> registrations = new ArrayList<StudySubject>();
		log.debug(" Search string is :" + text);
		if (request.getParameter("select").equals("Subject")) {
			Participant participant = new Participant();
			if (request.getParameter("subjectOption").equals("N") ||request.getParameter("subjectOption").equals("F") ){
				participant.setLastName(text.split(" ")[0]);
				participant.setFirstName(text.split(" ")[text.split(" ").length-1]);
			} else {
				OrganizationAssignedIdentifier orgIdentifier = new OrganizationAssignedIdentifier();
				orgIdentifier.setValue(text);
				participant.addIdentifier(orgIdentifier);
				SystemAssignedIdentifier sysIdentifier = new SystemAssignedIdentifier();
				sysIdentifier.setValue(text);
				participant.addIdentifier(sysIdentifier);
			} 
			
			List<Participant> participants = participantDao
					.searchByExample(participant);
			Set<Participant> participantSet = new TreeSet<Participant>();
	    	participantSet.addAll(participants);
	    	List<Participant> uniqueParticipants = new ArrayList<Participant>();
	    	uniqueParticipants.addAll(participantSet);
			for (Participant partVar : uniqueParticipants) {
				registrations = partVar.getStudySubjects();
			}
		} else if (request.getParameter("select").equals("Study")) {
			Study study = new Study(true);
			if (request.getParameter("studyOption").equals("shortTitle")) {
				study.setShortTitleText(text);

			} else if (request.getParameter("studyOption").equals("longTitle")) {
				study.setLongTitleText(text);

			} else if (request.getParameter("studyOption").equals("status")) {
				study.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.ACTIVE);
			} else {
				OrganizationAssignedIdentifier orgIdentifier = new OrganizationAssignedIdentifier();
				orgIdentifier.setValue(text);
				study.addIdentifier(orgIdentifier);
				SystemAssignedIdentifier sysIdentifier = new SystemAssignedIdentifier();
				sysIdentifier.setValue(text);
				study.addIdentifier(sysIdentifier);
			}

			List<Study> studies = studyDao.searchByExample(study, true);
			Set<Study> studySet = new TreeSet<Study>();
	    	List<Study> uniqueStudies = new ArrayList<Study>();
	    	studySet.addAll(studies);
	    	uniqueStudies.addAll(studySet);
	    	for (Study studyVar : uniqueStudies) {
				for (StudySite studySite : studyVar.getStudySites()) {
					for (StudySubject studySubject : studySite
							.getStudySubjects()) {
						registrations.add(studySubject);
					}
				}
			}
		} else if (request.getParameter("select").equals("Id")) {
			SystemAssignedIdentifier identifier = new SystemAssignedIdentifier();
			identifier.setValue(text);
			registration.addIdentifier(identifier);
			registrations = studySubjectDao.searchByExample(registration);
		}

		log.debug("Search registrations result size: " + registrations.size());
		Map<String, List<Lov>> configMap = configurationProperty.getMap();
		Map map = errors.getModel();
		map.put("registrations", registrations);
		map.put("searchTypeRefDataPrt", configMap.get("participantSearchType"));
		map.put("searchTypeRefDataStudy", configMap.get("studySearchTypeForRegistration"));
		ModelAndView modelAndView = new ModelAndView(getSuccessView(), map);
		return modelAndView;
	}

	protected Map<String, Object> referenceData(HttpServletRequest request)
			throws Exception {
		Map<String, Object> refdata = new HashMap<String, Object>();
		Map<String, List<Lov>> configMap = configurationProperty.getMap();

		refdata.put("searchTypeRefDataPrt", configMap
				.get("participantSearchType"));
		refdata.put("searchTypeRefDataStudy", configMap.get("studySearchTypeForRegistration"));
		return refdata;
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

	public StudySubjectDao getStudySubjectDao() {
		return studySubjectDao;
	}

	public void setStudySubjectDao(StudySubjectDao studySubjectDao) {
		this.studySubjectDao = studySubjectDao;
	}

}