package edu.duke.cabig.c3pr.web.ajax;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.web.HttpSessionRequiredException;

import edu.duke.cabig.c3pr.dao.ParticipantDao;
import edu.duke.cabig.c3pr.dao.StudyDao;
import edu.duke.cabig.c3pr.dao.StudySubjectDao;
import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.SystemAssignedIdentifier;

public class RegistrationAjaxFacade {
    private StudySubjectDao studySubjectDao;

    private ParticipantDao participantDao;

    private StudyDao studyDao;

    @SuppressWarnings("unchecked")
    private <T> T buildReduced(T src, List<String> properties) {
        T dst = null;
        try {
            // it doesn't seem like this cast should be necessary
            dst = (T) src.getClass().newInstance();
        }
        catch (InstantiationException e) {
            throw new RuntimeException("Failed to instantiate " + src.getClass().getName(), e);
        }
        catch (IllegalAccessException e) {
            throw new RuntimeException("Failed to instantiate " + src.getClass().getName(), e);
        }

        BeanWrapper source = new BeanWrapperImpl(src);
        BeanWrapper destination = new BeanWrapperImpl(dst);
        for (String property : properties) {
            destination.setPropertyValue(property, source.getPropertyValue(property));
        }
        return dst;
    }

    public List<Identifier> matchRegistrationIdentifiers(String text, int criterionSelector) {

        List<StudySubject> registrations = new ArrayList<StudySubject>();
        List<Identifier> reducedIdentifiers = new ArrayList<Identifier>();

        Identifier orgIdentifier = new OrganizationAssignedIdentifier();
        orgIdentifier.setValue(text);
        StudySubject studySubjectObj = new StudySubject(true);
        studySubjectObj.addIdentifier(orgIdentifier);
        registrations = studySubjectDao.searchByExample(studySubjectObj, true);
        List<OrganizationAssignedIdentifier> studySubjectOrgIdentifiers = new ArrayList<OrganizationAssignedIdentifier>();
        Identifier orgIdent = new OrganizationAssignedIdentifier();
        for (StudySubject studySubjectIter : registrations) {
            studySubjectOrgIdentifiers = studySubjectIter.getOrganizationAssignedIdentifiers();
            Iterator<OrganizationAssignedIdentifier> identifierIter = studySubjectOrgIdentifiers
                            .iterator();
            while (identifierIter.hasNext()) {
                orgIdent = identifierIter.next();
                reducedIdentifiers.add(buildReduced(orgIdent, Arrays.asList("id", "value")));
            }
        }

        List<StudySubject> registrationsSys = new ArrayList<StudySubject>();
        Identifier sysIdentifier = new OrganizationAssignedIdentifier();
        sysIdentifier.setValue(text);
        StudySubject studySubject = new StudySubject(true);
        studySubject.addIdentifier(sysIdentifier);
        registrationsSys = studySubjectDao.searchByExample(studySubject, true);
        List<SystemAssignedIdentifier> studySubjectSysIdentifiers = new ArrayList<SystemAssignedIdentifier>();
        Identifier sysIdent = new SystemAssignedIdentifier();
        for (StudySubject studySubjectIter : registrationsSys) {
            studySubjectSysIdentifiers = studySubjectIter.getSystemAssignedIdentifiers();
            Iterator<SystemAssignedIdentifier> identifierIter = studySubjectSysIdentifiers
                            .iterator();
            while (identifierIter.hasNext()) {
                sysIdent = identifierIter.next();
                reducedIdentifiers.add(buildReduced(sysIdent, Arrays.asList("id", "value")));
            }
        }
        return reducedIdentifiers;
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

                    reducedStudies.add(buildReduced(study, Arrays.asList("id", "shortTitleText")));
                    break;

                case 3:
                    reducedStudies.add(buildReduced(study, Arrays.asList("id", "longTitleText")));
                    break;
                case 5:
                    reducedStudies.add(buildReduced(study, Arrays.asList("id", "status")));
                    break;

            }

        }
        return reducedStudies;
    }

    public List<Identifier> matchStudyIdentifiers(String text, int criterionSelector) {

        List<Study> studies = new ArrayList<Study>();
        List<Identifier> reducedIdentifiers = new ArrayList<Identifier>();

        Identifier orgIdentifier = new OrganizationAssignedIdentifier();
        orgIdentifier.setValue(text);
        Study studyObj = new Study(true);
        studyObj.addIdentifier(orgIdentifier);
        studies = studyDao.searchByExample(studyObj, true);
        List<OrganizationAssignedIdentifier> studyOrgIdentifiers = new ArrayList<OrganizationAssignedIdentifier>();
        Identifier orgIdent = new OrganizationAssignedIdentifier();
        for (Study studyIter : studies) {
            studyOrgIdentifiers = studyIter.getOrganizationAssignedIdentifiers();
            Iterator<OrganizationAssignedIdentifier> identifierIter = studyOrgIdentifiers
                            .iterator();
            while (identifierIter.hasNext()) {
                orgIdent = identifierIter.next();
                reducedIdentifiers.add(buildReduced(orgIdent, Arrays.asList("id", "value")));
            }
        }

        List<Study> studiesSys = new ArrayList<Study>();
        Identifier sysIdentifier = new OrganizationAssignedIdentifier();
        sysIdentifier.setValue(text);
        Study study = new Study(true);
        study.addIdentifier(sysIdentifier);
        studiesSys = studyDao.searchByExample(study, true);
        List<SystemAssignedIdentifier> studySysIdentifiers = new ArrayList<SystemAssignedIdentifier>();
        Identifier sysIdent = new SystemAssignedIdentifier();
        for (Study studyIter : studiesSys) {
            studySysIdentifiers = studyIter.getSystemAssignedIdentifiers();
            Iterator<SystemAssignedIdentifier> identifierIter = studySysIdentifiers.iterator();
            while (identifierIter.hasNext()) {
                sysIdent = identifierIter.next();
                reducedIdentifiers.add(buildReduced(sysIdent, Arrays.asList("id", "value")));
            }
        }
        return reducedIdentifiers;
    }

    public List<Identifier> matchParticipantIdentifiers(String text, int criterionSelector) {

        List<Participant> participants = new ArrayList<Participant>();
        List<Identifier> reducedIdentifiers = new ArrayList<Identifier>();

        Identifier orgIdentifier = new OrganizationAssignedIdentifier();
        orgIdentifier.setValue(text);
        Participant participantObj = new Participant();
        participantObj.addIdentifier(orgIdentifier);
        participants = participantDao.searchByExample(participantObj);
        List<OrganizationAssignedIdentifier> participantOrgIdentifiers = new ArrayList<OrganizationAssignedIdentifier>();
        Identifier orgIdent = new OrganizationAssignedIdentifier();
        for (Participant participantIter : participants) {
            participantOrgIdentifiers = participantIter.getOrganizationAssignedIdentifiers();
            Iterator<OrganizationAssignedIdentifier> identifierIter = participantOrgIdentifiers
                            .iterator();
            while (identifierIter.hasNext()) {
                orgIdent = identifierIter.next();
                reducedIdentifiers.add(buildReduced(orgIdent, Arrays.asList("id", "value")));
            }
        }

        List<Participant> participantsSys = new ArrayList<Participant>();
        Identifier sysIdentifier = new OrganizationAssignedIdentifier();
        sysIdentifier.setValue(text);
        Participant participant = new Participant();
        participant.addIdentifier(sysIdentifier);
        participantsSys = participantDao.searchByExample(participant);
        List<SystemAssignedIdentifier> participantSysIdentifiers = new ArrayList<SystemAssignedIdentifier>();
        Identifier sysIdent = new SystemAssignedIdentifier();
        for (Participant participantIter : participantsSys) {
            participantSysIdentifiers = participantIter.getSystemAssignedIdentifiers();
            Iterator<SystemAssignedIdentifier> identifierIter = participantSysIdentifiers
                            .iterator();
            while (identifierIter.hasNext()) {
                sysIdent = identifierIter.next();
                reducedIdentifiers.add(buildReduced(sysIdent, Arrays.asList("id", "value")));
            }
        }
        return reducedIdentifiers;
    }

    public List<Participant> matchParticipants(String text, int criterionSelector) {

        List<Participant> participants = participantDao.getBySubnames(extractSubnames(text),
                        criterionSelector);
        // cut down objects for serialization
        List<Participant> reducedParticipants = new ArrayList<Participant>(participants.size());
        for (Participant participant : participants)

            reducedParticipants.add(buildReduced(participant, Arrays.asList("id", "lastName",
                            "firstName", "middleName")));

        return reducedParticipants;
    }

    private final Object getCommandOnly(HttpServletRequest request) throws Exception {
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
        return "edu.duke.cabig.c3pr.web.registration.RegistrationDetailsController.FORM.command";
    }

    private String getFormSessionAttributeNameAgain() {
        return "edu.duke.cabig.c3pr.web.registration.RegistrationDetailsController.FORM.command";
    }

    private String[] extractSubnames(String text) {
        return text.split("\\s+");
    }

    // //// CONFIGURATION

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

    public StudySubjectDao getStudySubjectDao() {
        return studySubjectDao;
    }

    public void setStudySubjectDao(StudySubjectDao studySubjectDao) {
        this.studySubjectDao = studySubjectDao;
    }

}
