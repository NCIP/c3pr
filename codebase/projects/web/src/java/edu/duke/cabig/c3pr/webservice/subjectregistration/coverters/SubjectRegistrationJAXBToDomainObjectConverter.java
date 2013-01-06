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
	
	StudySubject convertToStudySubjectRegistration(edu.duke.cabig.c3pr.domain.StudySubject domainObject);
	
	List<ScheduledEpoch> convertToScheduledEpochs(List<edu.duke.cabig.c3pr.domain.ScheduledEpoch> scheduledEpochs);
	
	ScheduledEpoch convertToScheduledEpoch(edu.duke.cabig.c3pr.domain.ScheduledEpoch scheduledEpoch);
	
	List<PerformedObservationResult> convertToSubjectEligibilityAnswers(List<SubjectEligibilityAnswer> answers);
	
	List<PerformedObservationResult> convertToSubjectStratificationAnswers(List<SubjectStratificationAnswer> answers);
	
	PerformedDiagnosis convertToDiseaseHistory(DiseaseHistory diseaseHistory);
	
	StudyInvestigator convertToStudyInvestigator(edu.duke.cabig.c3pr.domain.StudySubject domainObject);
	
	Epoch convertToEpoch(edu.duke.cabig.c3pr.domain.Epoch epoch);
	
	PerformedActivity convertToScheduledArm(ScheduledArm scheduledArm);
	
	List<edu.duke.cabig.c3pr.domain.ScheduledEpoch> convertScheduledEpochs(List<ScheduledEpoch> scheduledEpochs);
	
	edu.duke.cabig.c3pr.domain.ScheduledEpoch convertScheduledEpoch(ScheduledEpoch scheduledEpoch);
	
	List<SubjectEligibilityAnswer> convertSubjectEligibilityAnswers(List<PerformedObservationResult> answers);
	
	List<SubjectStratificationAnswer> convertSubjectStratificationAnswers(List<PerformedObservationResult> answers);
	
	DiseaseHistory convertDiseaseHistory(PerformedDiagnosis diseaseHistory);
	
	edu.duke.cabig.c3pr.domain.StudyInvestigator convertStudyInvestigator(StudyInvestigator studyInvestigator);
	
	edu.duke.cabig.c3pr.domain.Epoch convertEpoch(Epoch epoch);
	
	ScheduledArm convertScheduledArm(PerformedActivity scheduledArm);
	
}
