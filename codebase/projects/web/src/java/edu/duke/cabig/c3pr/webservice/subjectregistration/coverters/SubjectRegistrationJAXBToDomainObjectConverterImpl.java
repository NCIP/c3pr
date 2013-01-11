/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.webservice.subjectregistration.coverters;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.duke.cabig.c3pr.constants.RegistrationWorkFlowStatus;
import edu.duke.cabig.c3pr.domain.DiseaseHistory;
import edu.duke.cabig.c3pr.domain.InclusionEligibilityCriteria;
import edu.duke.cabig.c3pr.domain.Investigator;
import edu.duke.cabig.c3pr.domain.OffEpochReason;
import edu.duke.cabig.c3pr.domain.ScheduledArm;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.StudySubjectDemographics;
import edu.duke.cabig.c3pr.domain.StudySubjectStudyVersion;
import edu.duke.cabig.c3pr.domain.SubjectEligibilityAnswer;
import edu.duke.cabig.c3pr.domain.SubjectStratificationAnswer;
import edu.duke.cabig.c3pr.webservice.common.DefinedActivity;
import edu.duke.cabig.c3pr.webservice.common.Document;
import edu.duke.cabig.c3pr.webservice.common.Drug;
import edu.duke.cabig.c3pr.webservice.common.Organization;
import edu.duke.cabig.c3pr.webservice.common.PerformedActivity;
import edu.duke.cabig.c3pr.webservice.common.Person;
import edu.duke.cabig.c3pr.webservice.common.StudyProtocolDocumentVersion;
import edu.duke.cabig.c3pr.webservice.common.StudyProtocolVersion;
import edu.duke.cabig.c3pr.webservice.common.StudySiteProtocolVersionRelationship;
import edu.duke.cabig.c3pr.webservice.converters.JAXBToDomainObjectConverter;
import edu.duke.cabig.c3pr.webservice.iso21090.BAGTEL;
import edu.duke.cabig.c3pr.webservice.iso21090.DSETAD;
import edu.duke.cabig.c3pr.webservice.iso21090.DSETCD;
import edu.duke.cabig.c3pr.webservice.iso21090.DSETENPN;
import edu.duke.cabig.c3pr.webservice.iso21090.ENPN;
import edu.duke.cabig.c3pr.webservice.iso21090.EntityNamePartType;
import edu.duke.cabig.c3pr.webservice.iso21090.NullFlavor;
import edu.duke.cabig.c3pr.webservice.subjectregistration.DefinedEligibilityCriterion;
import edu.duke.cabig.c3pr.webservice.subjectregistration.DefinedExclusionCriterion;
import edu.duke.cabig.c3pr.webservice.subjectregistration.DefinedInclusionCriterion;
import edu.duke.cabig.c3pr.webservice.subjectregistration.DefinedStratificationCriterion;
import edu.duke.cabig.c3pr.webservice.subjectregistration.DefinedStratificationCriterionPermissibleResult;
import edu.duke.cabig.c3pr.webservice.subjectregistration.Epoch;
import edu.duke.cabig.c3pr.webservice.subjectregistration.HealthcareProvider;
import edu.duke.cabig.c3pr.webservice.subjectregistration.PerformedDiagnosis;
import edu.duke.cabig.c3pr.webservice.subjectregistration.PerformedObservationResult;
import edu.duke.cabig.c3pr.webservice.subjectregistration.ScheduledEpoch;
import edu.duke.cabig.c3pr.webservice.subjectregistration.StudyCondition;
import edu.duke.cabig.c3pr.webservice.subjectregistration.StudyInvestigator;
import edu.duke.cabig.c3pr.webservice.subjectregistration.StudySubject;
import edu.duke.cabig.c3pr.webservice.subjectregistration.StudySubjectProtocolVersionRelationship;
import edu.duke.cabig.c3pr.webservice.subjectregistry.converters.SubjectRegistryJAXBToDomainObjectConverterImpl;

/**
 * Default implementation of {@link JAXBToDomainObjectConverter}.
 * 
 * @author dkrylov
 * 
 */
public class SubjectRegistrationJAXBToDomainObjectConverterImpl extends SubjectRegistryJAXBToDomainObjectConverterImpl implements
		SubjectRegistrationJAXBToDomainObjectConverter  {

	private static Log log = LogFactory
			.getLog(SubjectRegistrationJAXBToDomainObjectConverterImpl.class);

	public StudySubject convertToStudySubjectRegistration(
			edu.duke.cabig.c3pr.domain.StudySubject domainObject) {
		StudySubject studySubject = new StudySubject();
		//set person
		Person person = convertSubjectDemographics(domainObject.getStudySubjectDemographics());
		
		//copy enrollment
		studySubject.setEntity(person);
		studySubject.setPaymentMethodCode(iso.CD(domainObject.getPaymentMethod()));
		studySubject.setStatusCode(iso.CD(domainObject.getRegWorkflowStatus().getCode()));
		
		//copy identifiers
		studySubject.getSubjectIdentifier().addAll(convertToSubjectIdentifier(domainObject.getIdentifiers()));
		
		//set studySubjectProtocolVersion
		studySubject.setStudySubjectProtocolVersion(getStudySubjectProtocolVersion(domainObject.getStudySubjectStudyVersion()));
		
		//set Disease History
		studySubject.setDiseaseHistory(convertToDiseaseHistory(domainObject.getDiseaseHistory()));
		
		//set Treating Physician
		if(domainObject.getTreatingPhysician()!=null || !StringUtils.isBlank(domainObject.getOtherTreatingPhysician())){
			studySubject.setTreatingPhysician(convertToStudyInvestigator(domainObject));
		}
		
		//set childstudysubjects
		for(edu.duke.cabig.c3pr.domain.StudySubject childDomainObject : domainObject.getChildStudySubjects()){
			studySubject.getChildStudySubject().add(convertToStudySubjectRegistration(childDomainObject));
		}
		
		return studySubject;
	}
	
	@Override
	protected StudySubjectProtocolVersionRelationship getStudySubjectProtocolVersion(StudySubjectStudyVersion studySubjectStudyVersion){
		StudySubjectProtocolVersionRelationship studySubjectProtocolVersion = new StudySubjectProtocolVersionRelationship();
		StudySite studySite = studySubjectStudyVersion.getStudySiteStudyVersion().getStudySite();
		studySubjectProtocolVersion.setStudySiteProtocolVersion(new StudySiteProtocolVersionRelationship());
		
		//setup study
		studySubjectProtocolVersion.getStudySiteProtocolVersion().setStudyProtocolVersion(new StudyProtocolVersion());
		studySubjectProtocolVersion.getStudySiteProtocolVersion().getStudyProtocolVersion().setStudyProtocolDocument(new StudyProtocolDocumentVersion());
		studySubjectProtocolVersion.getStudySiteProtocolVersion().getStudyProtocolVersion().getStudyProtocolDocument().setPublicTitle(iso.ST(studySite.getStudy().getLongTitleText()));
		studySubjectProtocolVersion.getStudySiteProtocolVersion().getStudyProtocolVersion().getStudyProtocolDocument().setOfficialTitle(iso.ST(studySite.getStudy().getShortTitleText()));
		studySubjectProtocolVersion.getStudySiteProtocolVersion().getStudyProtocolVersion().getStudyProtocolDocument().setPublicDescription(iso.ST(studySite.getStudy().getDescriptionText()));
		studySubjectProtocolVersion.getStudySiteProtocolVersion().getStudyProtocolVersion().getStudyProtocolDocument().setDocument(new Document());
		studySubjectProtocolVersion.getStudySiteProtocolVersion().getStudyProtocolVersion().getStudyProtocolDocument().getDocument().getDocumentIdentifier().addAll(convertToDocumentIdentifier(studySite.getStudy().getIdentifiers()));
		
		//setup studysite
		studySubjectProtocolVersion.getStudySiteProtocolVersion().setStudySite(new edu.duke.cabig.c3pr.webservice.common.StudySite());
		studySubjectProtocolVersion.getStudySiteProtocolVersion().getStudySite().setOrganization(new Organization());
		studySubjectProtocolVersion.getStudySiteProtocolVersion().getStudySite().getOrganization().getOrganizationIdentifier().addAll(convertToOrganizationIdentifier(studySite.getHealthcareSite().getIdentifiersAssignedToOrganization()));
		
		studySubjectProtocolVersion.getStudySubjectConsentVersion().addAll(convertToSubjectConsent(studySubjectStudyVersion.getStudySubjectConsentVersions()));
		studySubjectProtocolVersion.getScheduledEpoch().addAll(convertToScheduledEpochs(studySubjectStudyVersion.getScheduledEpochs()));
		return studySubjectProtocolVersion;
	}
	
	public List<ScheduledEpoch> convertToScheduledEpochs(
			List<edu.duke.cabig.c3pr.domain.ScheduledEpoch> scheduledEpochs) {
		List<ScheduledEpoch> returnList = new ArrayList<ScheduledEpoch>();
		for(edu.duke.cabig.c3pr.domain.ScheduledEpoch scheduledEpoch : scheduledEpochs){
			returnList.add(convertToScheduledEpoch(scheduledEpoch));
		}
		return returnList;
	}
	
	public ScheduledEpoch convertToScheduledEpoch(
			edu.duke.cabig.c3pr.domain.ScheduledEpoch scheduledEpoch) {
		ScheduledEpoch covertedScheduledEpoch = new ScheduledEpoch();
		covertedScheduledEpoch.setStartDate(convertToTsDateTime(scheduledEpoch.getStartDate()));
		covertedScheduledEpoch.setOffEpochDate(convertToTsDateTime(scheduledEpoch.getOffEpochDate()));
		covertedScheduledEpoch.setStatus(iso.CD(scheduledEpoch.getScEpochWorkflowStatus().getCode()));
		covertedScheduledEpoch.setStratumGroupNumber(iso.INTPositive(scheduledEpoch.getStratumGroupNumber()));
		covertedScheduledEpoch.setEpoch(convertToEpoch(scheduledEpoch.getEpoch()));
		if(scheduledEpoch.getScheduledArm() != null){
			covertedScheduledEpoch.setScheduledArm(convertToScheduledArm(scheduledEpoch.getScheduledArm()));
		}
		if(!CollectionUtils.isEmpty(scheduledEpoch.getOffEpochReasons())){
			covertedScheduledEpoch.setOffEpochReason(new DSETCD());
			for(OffEpochReason offEpochReason : scheduledEpoch.getOffEpochReasons()){
				covertedScheduledEpoch.getOffEpochReason().getItem().add(iso.CD(offEpochReason.getReason().getCode()));
			}
		}
		covertedScheduledEpoch.getSubjectEligibilityAnswer().addAll(convertToSubjectEligibilityAnswers(scheduledEpoch.getSubjectEligibilityAnswers()));
		covertedScheduledEpoch.getSubjectStartificationAnswer().addAll(convertToSubjectStratificationAnswers(scheduledEpoch.getSubjectStratificationAnswers()));
		return covertedScheduledEpoch;
	}
	
	public Epoch convertToEpoch(edu.duke.cabig.c3pr.domain.Epoch epoch){
		Epoch convertedEpoch = new Epoch();
		convertedEpoch.setDescription(iso.ST(epoch.getDescriptionText()));
		convertedEpoch.setName(iso.ST(epoch.getName()));
		convertedEpoch.setSequenceNumber(iso.INTPositive(epoch.getEpochOrder()));
		convertedEpoch.setTypeCode(iso.CD(epoch.getType().getCode()));
		return convertedEpoch;
	}
	
	public PerformedActivity convertToScheduledArm(ScheduledArm scheduledArm){
		PerformedActivity convertedArm = new PerformedActivity();
		if(scheduledArm.getArm() !=null){
			DefinedActivity arm = new DefinedActivity();
			arm.setNameCode(iso.CD(scheduledArm.getArm().getName()));
			convertedArm.setDefinedActivity(arm);
		}else if(!StringUtils.isBlank(scheduledArm.getKitNumber())){
			Drug drug = new Drug();
			drug.setKitNumber(iso.II(scheduledArm.getKitNumber()));
			convertedArm.setDrug(drug);
		}
		return convertedArm;
	}
	
	public List<PerformedObservationResult> convertToSubjectEligibilityAnswers(List<SubjectEligibilityAnswer> answers){
		List<PerformedObservationResult> convertedAnswers = new ArrayList<PerformedObservationResult>();
		for(SubjectEligibilityAnswer answer : answers){
			convertedAnswers.add(convertToSubjectEligibilityAnswer(answer));
		}
		return convertedAnswers;
	}
	
	public PerformedObservationResult convertToSubjectEligibilityAnswer(SubjectEligibilityAnswer answer){
		PerformedObservationResult convertedAnswer = new PerformedObservationResult();
		DefinedEligibilityCriterion eligibility = null;
		if(answer.getEligibilityCriteria() instanceof InclusionEligibilityCriteria){
			eligibility = new DefinedInclusionCriterion();
		}else{
			eligibility = new DefinedExclusionCriterion();
		}
		eligibility.setNameCode(iso.CD(answer.getEligibilityCriteria().getQuestionText()));
		eligibility.setDescription(iso.ST(answer.getEligibilityCriteria().getQuestionText()));
		if(answer.getEligibilityCriteria().getNotApplicableIndicator() == null){
			eligibility.setRequiredResponse(iso.BL(NullFlavor.NI));
		}else{
			eligibility.setRequiredResponse(iso.BL(answer.getEligibilityCriteria().getNotApplicableIndicator()));
		}
		convertedAnswer.setEligibilityCriterion(eligibility);
		convertedAnswer.setResult(iso.ST(answer.getAnswerText()));
		return convertedAnswer;
	}
	
	public List<PerformedObservationResult> convertToSubjectStratificationAnswers(List<SubjectStratificationAnswer> answers){
		List<PerformedObservationResult> convertedAnswers = new ArrayList<PerformedObservationResult>();
		for(SubjectStratificationAnswer answer : answers){
			convertedAnswers.add(convertToSubjectStratificationAnswer(answer));
		}
		return convertedAnswers;
	}
	
	public PerformedObservationResult convertToSubjectStratificationAnswer(SubjectStratificationAnswer answer){
		if(answer.getStratificationCriterionAnswer() == null){
			return null;
		}
		PerformedObservationResult convertedAnswer = new PerformedObservationResult();
		DefinedStratificationCriterion startification = new DefinedStratificationCriterion();
		startification.setNameCode(iso.CD(answer.getStratificationCriterion().getQuestionText()));
		startification.setDescription(iso.ST(answer.getStratificationCriterion().getQuestionText()));
		convertedAnswer.setStartificationCriterion(startification);
		DefinedStratificationCriterionPermissibleResult result = new DefinedStratificationCriterionPermissibleResult();
		result.setResult(iso.ST(answer.getStratificationCriterionAnswer().getPermissibleAnswer()));
		convertedAnswer.setStartificationCriterionPermissibleResult(result);
		return convertedAnswer;
	}
	
	public PerformedDiagnosis convertToDiseaseHistory(DiseaseHistory diseaseHistory){
		PerformedDiagnosis convertedDiseaseHistory = new PerformedDiagnosis();
		StudyCondition condition = new StudyCondition();
		condition.setConditionCode(iso.CD(diseaseHistory.getPrimaryDiseaseStr()));
		convertedDiseaseHistory.setDisease(condition);
		convertedDiseaseHistory.setTargetAnatomicSiteCode(iso.CD(diseaseHistory.getPrimaryDiseaseSiteStr()));
		return convertedDiseaseHistory;
	}
	
	public StudyInvestigator convertToStudyInvestigator(edu.duke.cabig.c3pr.domain.StudySubject domainObject){
		StudyInvestigator studyInvestigator = new StudyInvestigator();
		Person investigator = new Person();
		studyInvestigator.setHealthcareProvider(new HealthcareProvider());
		studyInvestigator.getHealthcareProvider().setPerson(investigator);
		if(domainObject.getTreatingPhysician()!=null){
			Investigator inv = domainObject.getTreatingPhysician().getHealthcareSiteInvestigator().getInvestigator();
			DSETENPN dsetenpn = new DSETENPN();
			ENPN enpn = new ENPN();
			enpn.getPart().add(iso.ENXP(inv.getFirstName(), EntityNamePartType.valueOf(GIV)));
			enpn.getPart().add(iso.ENXP(inv.getLastName(), EntityNamePartType.valueOf(FAM)));
			dsetenpn.getItem().add(enpn);
			investigator.setName(dsetenpn);
			BAGTEL addr = new BAGTEL();
			addr.getItem().add(iso.TEL(MAILTO + SEMICOLON + inv.getEmail(), null));
			investigator.setTelecomAddress(addr);
			investigator.setAdministrativeGenderCode(iso.CD(NullFlavor.NI));
			investigator.setBirthDate(iso.TSDateTime(NullFlavor.NI));
			investigator.setEthnicGroupCode(new DSETCD());
			investigator.setMaritalStatusCode(iso.CD(NullFlavor.NI));
			investigator.setPostalAddress(new DSETAD());
			investigator.setRaceCode(new DSETCD());
			studyInvestigator.getHealthcareProvider().setIdentifier(iso.II(inv.getAssignedIdentifier()));
		}else if(!StringUtils.isBlank(domainObject.getOtherTreatingPhysician())){
			DSETENPN dsetenpn = new DSETENPN();
			ENPN enpn = new ENPN();
			enpn.getPart().add(iso.ENXP(domainObject.getOtherTreatingPhysician(), EntityNamePartType.valueOf(GIV)));
			dsetenpn.getItem().add(enpn);
			investigator.setName(dsetenpn);
			investigator.setAdministrativeGenderCode(iso.CD(NullFlavor.NI));
			investigator.setBirthDate(iso.TSDateTime(NullFlavor.NI));
			investigator.setEthnicGroupCode(new DSETCD());
			investigator.setMaritalStatusCode(iso.CD(NullFlavor.NI));
			investigator.setPostalAddress(new DSETAD());
			investigator.setRaceCode(new DSETCD());
			investigator.setTelecomAddress(new BAGTEL());
			studyInvestigator.getHealthcareProvider().setIdentifier(iso.II(NullFlavor.NA));
		}else{
			return null;
		}
		return studyInvestigator;
	}

	public DiseaseHistory convertDiseaseHistory(
			PerformedDiagnosis diseaseHistory) {
		// TODO Auto-generated method stub
		return null;
	}

	public edu.duke.cabig.c3pr.domain.Epoch convertEpoch(Epoch epoch) {
		// TODO Auto-generated method stub
		return null;
	}

	public ScheduledArm convertScheduledArm(PerformedActivity scheduledArm) {
		// TODO Auto-generated method stub
		return null;
	}

	public edu.duke.cabig.c3pr.domain.ScheduledEpoch convertScheduledEpoch(
			ScheduledEpoch scheduledEpoch) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<edu.duke.cabig.c3pr.domain.ScheduledEpoch> convertScheduledEpochs(
			List<ScheduledEpoch> scheduledEpochs) {
		// TODO Auto-generated method stub
		return null;
	}

	public edu.duke.cabig.c3pr.domain.StudyInvestigator convertStudyInvestigator(StudyInvestigator studyInvestigator) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<SubjectEligibilityAnswer> convertSubjectEligibilityAnswers(
			List<PerformedObservationResult> answers) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<SubjectStratificationAnswer> convertSubjectStratificationAnswers(
			List<PerformedObservationResult> answers) {
		// TODO Auto-generated method stub
		return null;
	}

	public void convert(edu.duke.cabig.c3pr.domain.StudySubject destination,
			StudySubject source) {
		//set person
		if(destination.getStudySubjectDemographics() == null){
			destination.setStudySubjectDemographics(new StudySubjectDemographics());
		}
		convertToSubjectDemographics(destination.getStudySubjectDemographics(), source);
		
		//copy enrollment
		destination.setPaymentMethod(source.getPaymentMethodCode()!=null?source.getPaymentMethodCode().getCode():null);
		destination.setRegWorkflowStatus(source.getStateCode()!=null?RegistrationWorkFlowStatus.valueOf(source.getStateCode().getValue()):null);
		
		//copy identifiers
		destination.getIdentifiers().clear();
		destination.getIdentifiers().addAll(convertSubjectIdentifiers(source.getSubjectIdentifier()));
		
//		//set studySubjectProtocolVersion
//		studySubject.setStudySubjectProtocolVersion(getStudySubjectProtocolVersion(domainObject.getStudySubjectStudyVersion()));
//		
//		//set Disease History
//		studySubject.setDiseaseHistory(convertToDiseaseHistory(domainObject.getDiseaseHistory()));
//		
//		//set Treating Physician
//		if(domainObject.getTreatingPhysician()!=null || !StringUtils.isBlank(domainObject.getOtherTreatingPhysician())){
//			studySubject.setTreatingPhysician(convertToStudyInvestigator(domainObject));
//		}
//		
//		//set childstudysubjects
//		for(edu.duke.cabig.c3pr.domain.StudySubject childDomainObject : domainObject.getChildStudySubjects()){
//			studySubject.getChildStudySubject().add(convertTo(childDomainObject));
//		}
//		
		
	}

	
}
