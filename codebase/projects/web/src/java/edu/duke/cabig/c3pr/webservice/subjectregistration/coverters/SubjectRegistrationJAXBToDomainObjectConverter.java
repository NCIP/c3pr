/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.webservice.subjectregistration.coverters;

import java.util.List;

import edu.duke.cabig.c3pr.domain.DiseaseHistory;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.ScheduledArm;
import edu.duke.cabig.c3pr.domain.SubjectEligibilityAnswer;
import edu.duke.cabig.c3pr.domain.SubjectStratificationAnswer;
import edu.duke.cabig.c3pr.webservice.common.PerformedActivity;
import edu.duke.cabig.c3pr.webservice.common.Subject;
import edu.duke.cabig.c3pr.webservice.subjectregistration.Epoch;
import edu.duke.cabig.c3pr.webservice.subjectregistration.PerformedDiagnosis;
import edu.duke.cabig.c3pr.webservice.subjectregistration.PerformedObservationResult;
import edu.duke.cabig.c3pr.webservice.subjectregistration.ScheduledEpoch;
import edu.duke.cabig.c3pr.webservice.subjectregistration.StudyInvestigator;
import edu.duke.cabig.c3pr.webservice.subjectregistration.StudySubject;
import edu.duke.cabig.c3pr.webservice.subjectregistry.converters.SubjectRegistryJAXBToDomainObjectConverter;

/**
 * Provides operations to convert JAXB objects, such as {@link Subject}, created during a Web service invocation into
 * C3PR domain objects, such as {@link Participant}.
 * @author dkrylov
 *
 */
public interface SubjectRegistrationJAXBToDomainObjectConverter extends SubjectRegistryJAXBToDomainObjectConverter{
	
	/**
	 * Convert to study subject registration.
	 *
	 * @param domainObject the domain object
	 * @return the study subject
	 */
	StudySubject convertToStudySubjectRegistration(edu.duke.cabig.c3pr.domain.StudySubject domainObject);
	
	/**
	 * Convert to scheduled epochs.
	 *
	 * @param scheduledEpochs the scheduled epochs
	 * @return the list
	 */
	List<ScheduledEpoch> convertToScheduledEpochs(List<edu.duke.cabig.c3pr.domain.ScheduledEpoch> scheduledEpochs);
	
	/**
	 * Convert to scheduled epoch.
	 *
	 * @param scheduledEpoch the scheduled epoch
	 * @return the scheduled epoch
	 */
	ScheduledEpoch convertToScheduledEpoch(edu.duke.cabig.c3pr.domain.ScheduledEpoch scheduledEpoch);
	
	/**
	 * Convert to subject eligibility answers.
	 *
	 * @param answers the answers
	 * @return the list
	 */
	List<PerformedObservationResult> convertToSubjectEligibilityAnswers(List<SubjectEligibilityAnswer> answers);
	
	/**
	 * Convert to subject stratification answers.
	 *
	 * @param answers the answers
	 * @return the list
	 */
	List<PerformedObservationResult> convertToSubjectStratificationAnswers(List<SubjectStratificationAnswer> answers);
	
	/**
	 * Convert to disease history.
	 *
	 * @param diseaseHistory the disease history
	 * @return the performed diagnosis
	 */
	PerformedDiagnosis convertToDiseaseHistory(DiseaseHistory diseaseHistory);
	
	/**
	 * Convert to study investigator.
	 *
	 * @param domainObject the domain object
	 * @return the study investigator
	 */
	StudyInvestigator convertToStudyInvestigator(edu.duke.cabig.c3pr.domain.StudySubject domainObject);
	
	/**
	 * Convert to epoch.
	 *
	 * @param epoch the epoch
	 * @return the epoch
	 */
	Epoch convertToEpoch(edu.duke.cabig.c3pr.domain.Epoch epoch);
	
	/**
	 * Convert to scheduled arm.
	 *
	 * @param scheduledArm the scheduled arm
	 * @return the performed activity
	 */
	PerformedActivity convertToScheduledArm(ScheduledArm scheduledArm);
	
	/**
	 * Convert scheduled epochs.
	 *
	 * @param scheduledEpochs the scheduled epochs
	 * @return the list
	 */
	List<edu.duke.cabig.c3pr.domain.ScheduledEpoch> convertScheduledEpochs(List<ScheduledEpoch> scheduledEpochs);
	
	/**
	 * Convert scheduled epoch.
	 *
	 * @param scheduledEpoch the scheduled epoch
	 * @return the edu.duke.cabig.c3pr.domain. scheduled epoch
	 */
	edu.duke.cabig.c3pr.domain.ScheduledEpoch convertScheduledEpoch(ScheduledEpoch scheduledEpoch);
	
	/**
	 * Convert subject eligibility answers.
	 *
	 * @param answers the answers
	 * @return the list
	 */
	List<SubjectEligibilityAnswer> convertSubjectEligibilityAnswers(List<PerformedObservationResult> answers);
	
	/**
	 * Convert subject stratification answers.
	 *
	 * @param answers the answers
	 * @return the list
	 */
	List<SubjectStratificationAnswer> convertSubjectStratificationAnswers(List<PerformedObservationResult> answers);
	
	/**
	 * Convert disease history.
	 *
	 * @param diseaseHistory the disease history
	 * @return the disease history
	 */
	DiseaseHistory convertDiseaseHistory(PerformedDiagnosis diseaseHistory);
	
	/**
	 * Convert study investigator.
	 *
	 * @param studyInvestigator the study investigator
	 * @return the edu.duke.cabig.c3pr.domain. study investigator
	 */
	edu.duke.cabig.c3pr.domain.StudyInvestigator convertStudyInvestigator(StudyInvestigator studyInvestigator);
	
	/**
	 * Convert epoch.
	 *
	 * @param epoch the epoch
	 * @return the edu.duke.cabig.c3pr.domain. epoch
	 */
	edu.duke.cabig.c3pr.domain.Epoch convertEpoch(Epoch epoch);
	
	/**
	 * Convert scheduled arm.
	 *
	 * @param scheduledArm the scheduled arm
	 * @return the scheduled arm
	 */
	ScheduledArm convertScheduledArm(PerformedActivity scheduledArm);
	
}
