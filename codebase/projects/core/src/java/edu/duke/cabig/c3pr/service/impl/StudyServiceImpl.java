package edu.duke.cabig.c3pr.service.impl;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import edu.duke.cabig.c3pr.dao.StudyDao;
import edu.duke.cabig.c3pr.dao.StudySubjectDao;
import edu.duke.cabig.c3pr.domain.BookRandomization;
import edu.duke.cabig.c3pr.domain.CalloutRandomization;
import edu.duke.cabig.c3pr.domain.CoordinatingCenterStudyStatus;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.PhonecallRandomization;
import edu.duke.cabig.c3pr.domain.Randomization;
import edu.duke.cabig.c3pr.domain.RandomizationType;
import edu.duke.cabig.c3pr.domain.SiteStudyStatus;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyAmendment;
import edu.duke.cabig.c3pr.domain.StudyDataEntryStatus;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.TreatmentEpoch;
import edu.duke.cabig.c3pr.exception.C3PRBaseRuntimeException;
import edu.duke.cabig.c3pr.service.StudyService;

/**
 * Services for Study related domain object
 * 
 * @see edu.duke.cabig.c3pr.service.StudyService
 * @author Priyatam
 */
public class StudyServiceImpl implements StudyService {

	public void setSiteStudyStatuses(Study study) throws Exception {
		for (int i = 0; i < study.getStudySites().size(); i++) {
			study.getStudySites().get(i).setSiteStudyStatus(
					evaluateSiteStudyStatus(study.getStudySites().get(i)));
		}

	}

	StudyDao studyDao;

	StudySubjectDao studySubjectDao;

	// TODO hook esb call
	// ProtocolBroadcastService esbCreateProtocol;

	public void setDataEntryStatus(Study study) throws Exception {
		study.setDataEntryStatus(evaluateDataEntryStatus(study));
	}

	public CoordinatingCenterStudyStatus evaluateCoordinatingCenterStudyStatus(
			Study study) throws Exception {
		if (evaluateDataEntryStatus(study) != StudyDataEntryStatus.COMPLETE) {
			return CoordinatingCenterStudyStatus.PENDING;
		}
		if (evaluateAmendmentStatus(study) != StudyDataEntryStatus.COMPLETE) {
			return CoordinatingCenterStudyStatus.AMENDMENT_PENDING;
		}
		if (study.getRandomizedIndicator().equals("true")) {
			if (evaluateRandomizationDataEntryStatus(study) != StudyDataEntryStatus.COMPLETE) {
				return CoordinatingCenterStudyStatus.PENDING;
			}
		}
		return CoordinatingCenterStudyStatus.ACTIVE;
	}

	public StudyDataEntryStatus evaluateStratificationDataEntryStatus(
			Study study) throws Exception {
		if (study.hasStratification()) {
			return StudyDataEntryStatus.COMPLETE;
		}
		return StudyDataEntryStatus.COMPLETE;
	}

	public StudyDataEntryStatus evaluateRandomizationDataEntryStatus(Study study)
			throws Exception {

		if (study.getRandomizationType() == (RandomizationType.BOOK)) {
			for (TreatmentEpoch treatmentEpoch : study.getTreatmentEpochs()) {
				if (treatmentEpoch.hasBookRandomizationEntry()) {
					if (!treatmentEpoch.hasStratumGroups())
						return StudyDataEntryStatus.INCOMPLETE;
				}
			}
			return StudyDataEntryStatus.COMPLETE;
		}

		if (study.getRandomizationType() == (RandomizationType.PHONE_CALL)) {
			for (TreatmentEpoch treatmentEpoch : study.getTreatmentEpochs()) {
				Randomization randomization = treatmentEpoch.getRandomization();
				if (randomization instanceof PhonecallRandomization) {
					if ((((PhonecallRandomization) randomization)
							.getPhoneNumber()) == null) {
						return StudyDataEntryStatus.INCOMPLETE;
					}
				}
			}
		}

		if (study.getRandomizationType() == (RandomizationType.CALL_OUT)) {
			for (TreatmentEpoch treatmentEpoch : study.getTreatmentEpochs()) {
				Randomization randomization = treatmentEpoch.getRandomization();
				if (randomization instanceof PhonecallRandomization) {
					if ((((CalloutRandomization) randomization).getCalloutUrl()) == null) {
						return StudyDataEntryStatus.INCOMPLETE;
					}
				}
			}
		}
		return StudyDataEntryStatus.COMPLETE;
	}

	public StudyDataEntryStatus evaluateEligibilityDataEntryStatus(Study study)
			throws Exception {
		return StudyDataEntryStatus.COMPLETE;
	}

	public StudyDataEntryStatus evaluateAmendmentStatus(Study study)
			throws Exception {

		if (study.getStudyAmendments().size() > 0) {
			StudyAmendment latestAmendment = study.getStudyAmendments().get(
					study.getStudyAmendments().size() - 1);

			if ((latestAmendment.getAmendmentDate() == null)
					|| (latestAmendment.getVersion() == null)) {
				return StudyDataEntryStatus.INCOMPLETE;
			}
			if ((latestAmendment.getAmendmentDate() != null)
					&& (latestAmendment.getAmendmentDate().after(new Date()))) {
				return StudyDataEntryStatus.INCOMPLETE;
			}
			if ((latestAmendment.getConsentChangedIndicator())
					|| (latestAmendment.getDiseasesChangedIndicator())
					|| (latestAmendment.getEligibilityChangedIndicator())
					|| (latestAmendment.getEaChangedIndicator())
					|| (latestAmendment.getStratChangedIndicator())
					|| (latestAmendment.getPiChangedIndicator())
							|| (latestAmendment
									.getRandomizationChangedIndicator())) {
				return StudyDataEntryStatus.COMPLETE;
			} else {
				return StudyDataEntryStatus.INCOMPLETE;
			}

		}
		return StudyDataEntryStatus.COMPLETE;
	}

	public StudyDataEntryStatus evaluateDataEntryStatus(Study study)
			throws Exception {

		if ((study.getStudySites().size() == 0) || (!study.hasEnrollingEpoch()))
			return StudyDataEntryStatus.INCOMPLETE;

		if (study.hasElligibility()) {
			if (evaluateEligibilityDataEntryStatus(study) != StudyDataEntryStatus.COMPLETE)
				return StudyDataEntryStatus.INCOMPLETE;
		}
		if (study.hasStratification()) {
			if (evaluateStratificationDataEntryStatus(study) != StudyDataEntryStatus.COMPLETE)
				return StudyDataEntryStatus.INCOMPLETE;
		}

		return StudyDataEntryStatus.COMPLETE;
	}

	public SiteStudyStatus evaluateSiteStudyStatus(StudySite studySite) {

		if (studySite.getStudy().getCoordinatingCenterStudyStatus() == CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL) {
			return SiteStudyStatus.CLOSED_TO_ACCRUAL;
		}
		if (studySite.getStudy().getCoordinatingCenterStudyStatus() == CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL_AND_TREATMENT) {
			return SiteStudyStatus.CLOSED_TO_ACCRUAL_AND_TREATMENT;
		}

		if (studySite.getStudy().getCoordinatingCenterStudyStatus() == CoordinatingCenterStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL) {
			return SiteStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL;
		}
		if (studySite.getStudy().getCoordinatingCenterStudyStatus() == CoordinatingCenterStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_TREATMENT) {
			return SiteStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_TREATMENT;
		}

		if ((studySite.getStudy().getCoordinatingCenterStudyStatus() == CoordinatingCenterStudyStatus.AMENDMENT_PENDING)
				&& (studySite.getSiteStudyStatus() == SiteStudyStatus.ACTIVE)) {
			return SiteStudyStatus.AMENDMENT_PENDING;
		}
		if (studySite.getStudy().getCoordinatingCenterStudyStatus() == CoordinatingCenterStudyStatus.ACTIVE) {
			Date currentDate = new Date();
			if (((studySite.getIrbApprovalDate()) == null)
					|| (studySite.getIrbApprovalDate().after(currentDate))) {
				return SiteStudyStatus.PENDING;
			}
			if ((studySite.getStartDate() == null)||(studySite.getStartDate().after(currentDate))) {
					return SiteStudyStatus.PENDING;
			}
			return SiteStudyStatus.ACTIVE;
		}

		return SiteStudyStatus.PENDING;
	}

	/**
	 * Search using a sample populate Study object
	 * 
	 * @param study
	 *            the study object
	 * @return List of Study objects based on the sample study object
	 * @throws Exception
	 *             runtime exception object
	 */
	public List<Study> search(Study study) throws Exception {
		return studyDao.searchByExample(study, true);
	}

	/**
	 * Saves a study object
	 * 
	 * @param study
	 *            the study object
	 * @throws Exception
	 *             runtime exception object
	 */
	public void save(Study study) throws Exception {
		// TODO call esb to broadcast protocol, POC
		studyDao.save(study);
	}

	public StudyDao getStudyDao() {
		return studyDao;
	}

	public void setStudyDao(StudyDao studyDao) {
		this.studyDao = studyDao;
	}

	public StudySubjectDao getStudyParticipantDao() {
		return studySubjectDao;
	}

	public void setStudyParticipantDao(StudySubjectDao studyParticipantDao) {
		this.studySubjectDao = studyParticipantDao;
	}

	/**
	 * Assigns a Participant to a Study at a particular Site. The Study and Site
	 * must already exist and be associated.
	 * 
	 * @param study
	 * @param participant
	 * @param site
	 * @return StudySubject for the Participant
	 */
	public StudySubject assignParticipant(Study study, Participant participant,
			HealthcareSite site, Date enrollmentDate) {
		// new assignment
		StudySubject assignment = new StudySubject();

		// study shld exist
		Study assignedStudy = studyDao.getByGridId(study.getGridId());

		assignment.setParticipant(participant);
		assignment.setStartDate(enrollmentDate);
		studySubjectDao.save(assignment);

		return assignment;
	}

	public Study setStatuses(Study study) throws Exception {

		study.setDataEntryStatus(evaluateDataEntryStatus(study));

		// For a new study, the coordingating center status should be set to
		// Pending.
		if (study.getId() == null) {
			study
					.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.PENDING);
		} else {
			study
					.setCoordinatingCenterStudyStatus(evaluateCoordinatingCenterStudyStatus(study));
		}
		for (int i = 0; i < study.getStudySites().size(); i++) {
			study.getStudySites().get(i).setSiteStudyStatus(
					evaluateSiteStudyStatus(study.getStudySites().get(i)));
		}

		return study;
	}

	public Study setStatuses(Study study,
			CoordinatingCenterStudyStatus targetStatus) throws Exception {

		study.setDataEntryStatus(evaluateDataEntryStatus(study));
		CoordinatingCenterStudyStatus oldStatus = study
				.getCoordinatingCenterStudyStatus();

		// For a new study, the coordingating center status should be set to
		// Pending.
		if (study.getId() == null) {
			study
					.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.PENDING);
		} else {
			if (statusSettable(study, targetStatus)) {
				study.setCoordinatingCenterStudyStatus(targetStatus);
			} else {
				study.setCoordinatingCenterStudyStatus(oldStatus);
			}

		}

		return study;
	}

	public boolean statusSettable(Study study,
			CoordinatingCenterStudyStatus newCoordinatingCenterStatus) {

		try {
			// For a new study, the coordingating center status should not be
			// settable.
			if (study.getId() == null) {
				return false;
			}

			CoordinatingCenterStudyStatus evaluatedStatus = evaluateCoordinatingCenterStudyStatus(study);
			CoordinatingCenterStudyStatus currentStatus = study
					.getCoordinatingCenterStudyStatus();
			if (newCoordinatingCenterStatus == CoordinatingCenterStudyStatus.ACTIVE) {

				if (((evaluatedStatus) == (CoordinatingCenterStudyStatus.PENDING))
						|| ((evaluatedStatus) == (CoordinatingCenterStudyStatus.AMENDMENT_PENDING))
						|| ((currentStatus) == (CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL))
						|| ((currentStatus) == (CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL_AND_TREATMENT))) {
					return false;
				} else
					return true;
			}

			if ((newCoordinatingCenterStatus == CoordinatingCenterStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL)
					|| (newCoordinatingCenterStatus == CoordinatingCenterStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_TREATMENT)) {
				if (((currentStatus) == (CoordinatingCenterStudyStatus.PENDING))
						|| ((currentStatus) == (CoordinatingCenterStudyStatus.AMENDMENT_PENDING))
						|| ((currentStatus) == (CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL))
						|| ((currentStatus) == (CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL_AND_TREATMENT))) {
					return false;
				} else
					return true;
			}

			if ((newCoordinatingCenterStatus == CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL)
					|| (newCoordinatingCenterStatus == CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL_AND_TREATMENT)) {
				if (((currentStatus) == (CoordinatingCenterStudyStatus.PENDING))
						|| ((currentStatus) == (CoordinatingCenterStudyStatus.AMENDMENT_PENDING))
						|| ((currentStatus) == (CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL_AND_TREATMENT))) {
					return false;
				} else
					return true;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return true;
	}

	public Study setSiteStudyStatus(Study study, StudySite studySite,
			SiteStudyStatus status) throws Exception {
		SiteStudyStatus currentSiteStatus = studySite.getSiteStudyStatus();
		if ((status == SiteStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL)
				|| (status == SiteStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_TREATMENT)) {
			if (((currentSiteStatus) == (SiteStudyStatus.ACTIVE))
					|| ((currentSiteStatus) == (SiteStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL))) {
				studySite.setSiteStudyStatus(status);
			}
		} else if ((status == SiteStudyStatus.CLOSED_TO_ACCRUAL)
				|| (status == SiteStudyStatus.CLOSED_TO_ACCRUAL_AND_TREATMENT)) {
			if (((currentSiteStatus) == (SiteStudyStatus.PENDING))
					|| ((currentSiteStatus) == (SiteStudyStatus.AMENDMENT_PENDING))
					|| ((currentSiteStatus) == (SiteStudyStatus.CLOSED_TO_ACCRUAL_AND_TREATMENT))) {
				return study;
			} else
				studySite.setSiteStudyStatus(status);
		} else if (status == evaluateSiteStudyStatus(studySite)) {
			studySite.setSiteStudyStatus(status);
		}
		return study;
	}

	public Study merge(Study study) {
		return studyDao.merge(study);
	}

	public StudySubjectDao getStudySubjectDao() {
		return studySubjectDao;
	}

	public void setStudySubjectDao(StudySubjectDao studySubjectDao) {
		this.studySubjectDao = studySubjectDao;
	}
}
