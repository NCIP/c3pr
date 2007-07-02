package edu.duke.cabig.c3pr.web.ajax;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.web.HttpSessionRequiredException;

import edu.duke.cabig.c3pr.dao.ParticipantDao;
import edu.duke.cabig.c3pr.dao.StudyDao;
import edu.duke.cabig.c3pr.dao.StudyParticipantAssignmentDao;
import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyParticipantAssignment;

public class RegistrationAjaxFacade {
	private StudyParticipantAssignmentDao registrationDao;

	private ParticipantDao participantDao;

	private StudyDao studyDao;

	@SuppressWarnings("unchecked")
	private <T> T buildReduced(T src, List<String> properties) {
		T dst = null;
		try {
			// it doesn't seem like this cast should be necessary
			dst = (T) src.getClass().newInstance();
		} catch (InstantiationException e) {
			throw new RuntimeException("Failed to instantiate "
					+ src.getClass().getName(), e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException("Failed to instantiate "
					+ src.getClass().getName(), e);
		}

		BeanWrapper source = new BeanWrapperImpl(src);
		BeanWrapper destination = new BeanWrapperImpl(dst);
		for (String property : properties) {
			destination.setPropertyValue(property, source
					.getPropertyValue(property));
		}
		return dst;
	}

	public List<Identifier> matchRegistrationIdentifiers(
			String text, int criterionSelector) {

		List<StudyParticipantAssignment> registrations = new ArrayList<StudyParticipantAssignment>();
		List<Identifier> identifiers = new ArrayList<Identifier>();

		Identifier identifier = new Identifier();
		identifier.setValue(text);
		StudyParticipantAssignment registrationObj = new StudyParticipantAssignment();
		registrationObj.addIdentifier(identifier);
		registrations = registrationDao.searchByExample(registrationObj);
		List<Identifier> registrationIdentifiers = new ArrayList<Identifier>();
		Identifier ident = new Identifier();
		for (StudyParticipantAssignment registrationIter : registrations) {
			registrationIdentifiers = registrationIter.getIdentifiers();
			Iterator<Identifier> identifierIter = registrationIdentifiers.iterator();
			while (identifierIter.hasNext()) {
				ident = identifierIter.next();
				if (ident.getValue().toUpperCase().contains(text.toUpperCase()))
					identifiers.add(ident);

			}
		}

		return identifiers;
	}

	public List<Study> matchStudies(String text, int criterionSelector) {

		List<Study> studies = new ArrayList<Study>();
		List<Study> reducedStudies = new ArrayList<Study>();

		studies = studyDao.getBySubnames(extractSubnames(text));
		// cut down objects for serialization
		reducedStudies = new ArrayList<Study>(studies.size());

		for (Study study : studies) {

			switch (criterionSelector) {
			case 2:

				reducedStudies.add(buildReduced(study, Arrays.asList("id",
						"shortTitleText")));
				break;

			case 3:
				reducedStudies.add(buildReduced(study, Arrays.asList("id",
						"longTitleText")));
				break;
			case 5:
				reducedStudies.add(buildReduced(study, Arrays.asList("id",
						"status")));
				break;

			}

		}
		return reducedStudies;
	}

	public List<Identifier> matchStudyIdentifiers(String text,
			int criterionSelector) {

		List<Study> studies = new ArrayList<Study>();
		List<Identifier> identifiers = new ArrayList<Identifier>();

		Identifier identifier = new Identifier();
		identifier.setValue(text);
		Study studyObj = new Study();
		studyObj.addIdentifier(identifier);
		studies = studyDao.searchByExample(studyObj);
		List<Identifier> studyIdentifiers = new ArrayList<Identifier>();
		Identifier ident = new Identifier();
		for (Study studyIter : studies) {

			studyIdentifiers = studyIter.getIdentifiers();
			Iterator<Identifier> identifierIter = studyIdentifiers.iterator();
			while (identifierIter.hasNext()) {
				ident = identifierIter.next();
				if (ident.getValue().toUpperCase().contains(text.toUpperCase()))
					identifiers.add(ident);

			}
		}

		return identifiers;
	}

	public List<Identifier> matchParticipantIdentifiers(String text,
			int criterionSelector) {

		List<Participant> participants = new ArrayList<Participant>();
		List<Identifier> identifiers = new ArrayList<Identifier>();

		Identifier identifier = new Identifier();
		identifier.setValue(text);
		Participant participantObj = new Participant();
		participantObj.addIdentifier(identifier);
		participants = participantDao.searchByExample(participantObj);
		List<Identifier> participantIdentifiers = new ArrayList<Identifier>();
		Identifier ident = new Identifier();
		for (Participant participantIter : participants) {
			participantIdentifiers = participantIter.getIdentifiers();
			Iterator<Identifier> identifierIter = participantIdentifiers
					.iterator();
			while (identifierIter.hasNext()) {
				ident = identifierIter.next();
				if (ident.getValue().toUpperCase().contains(text.toUpperCase()))
					identifiers.add(ident);

			}
		}

		return identifiers;
	}

	public List<Participant> matchParticipants(String text,
			int criterionSelector) {

		{

			switch (criterionSelector) {
			case 0:
				List<Participant> participants = participantDao.getBySubnames(
						extractSubnames(text), criterionSelector);
				// cut down objects for serialization
				List<Participant> reducedParticipants = new ArrayList<Participant>(
						participants.size());
				for (Participant participant : participants)

					reducedParticipants.add(buildReduced(participant, Arrays
							.asList("id", "lastName")));

				return reducedParticipants;

			case 1:

				Identifier identifier = new Identifier();
				identifier.setValue(text);
				Participant participant = new Participant();
				participant.addIdentifier(identifier);
				participants = participantDao.searchByExample(participant);
				List<Participant> reducedParts = new ArrayList<Participant>(
						participants.size());
				for (Participant part : participants)
					reducedParts.add(buildReduced(part, Arrays.asList("id",
							"primaryIdentifier")));
				return reducedParts;
			}

		}
		return null;
	}

	private final Object getCommandOnly(HttpServletRequest request)
			throws Exception {
		HttpSession session = request.getSession(false);
		if (session == null) {
			throw new HttpSessionRequiredException(
					"Must have session when trying to bind (in session-form mode)");
		}
		String formAttrName = getFormSessionAttributeName();
		Object sessionFormObject = session.getAttribute(formAttrName);
		if (sessionFormObject == null) {
			formAttrName = getFormSessionAttributeNameAgain();
			sessionFormObject = session.getAttribute(formAttrName);
			return sessionFormObject;
		}

		return sessionFormObject;
	}

	private String getFormSessionAttributeName() {
		return "edu.duke.cabig.c3pr.web.RegistrationDetailsController.FORM.command";
	}

	private String getFormSessionAttributeNameAgain() {
		return "edu.duke.cabig.c3pr.web.RegistrationDetailsController.FORM.command";
	}

	private String[] extractSubnames(String text) {
		return text.split("\\s+");
	}

	// //// CONFIGURATION

	@Required
	public void setRegistrationDao(StudyParticipantAssignmentDao registrationDao) {
		this.registrationDao = registrationDao;
	}

	public StudyParticipantAssignmentDao getRegistrationDao() {
		return registrationDao;
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

}
