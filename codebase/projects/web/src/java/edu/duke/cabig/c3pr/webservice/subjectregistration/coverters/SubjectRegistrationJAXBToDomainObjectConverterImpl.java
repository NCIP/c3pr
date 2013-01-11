/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.webservice.subjectregistration.coverters;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import edu.duke.cabig.c3pr.constants.EpochType;
import edu.duke.cabig.c3pr.constants.RegistrationWorkFlowStatus;
import edu.duke.cabig.c3pr.constants.ScheduledEpochWorkFlowStatus;
import edu.duke.cabig.c3pr.domain.Address;
import edu.duke.cabig.c3pr.domain.Arm;
import edu.duke.cabig.c3pr.domain.DiseaseHistory;
import edu.duke.cabig.c3pr.domain.DiseaseTerm;
import edu.duke.cabig.c3pr.domain.HealthcareSiteInvestigator;
import edu.duke.cabig.c3pr.domain.ICD9DiseaseSite;
import edu.duke.cabig.c3pr.domain.InclusionEligibilityCriteria;
import edu.duke.cabig.c3pr.domain.Investigator;
import edu.duke.cabig.c3pr.domain.LocalInvestigator;
import edu.duke.cabig.c3pr.domain.OffEpochReason;
import edu.duke.cabig.c3pr.domain.OffFollowupReason;
import edu.duke.cabig.c3pr.domain.OffReservingReason;
import edu.duke.cabig.c3pr.domain.OffScreeningReason;
import edu.duke.cabig.c3pr.domain.OffTreatmentReason;
import edu.duke.cabig.c3pr.domain.ScheduledArm;
import edu.duke.cabig.c3pr.domain.StudyDisease;
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
import edu.duke.cabig.c3pr.webservice.iso21090.CD;
import edu.duke.cabig.c3pr.webservice.iso21090.DSETAD;
import edu.duke.cabig.c3pr.webservice.iso21090.DSETCD;
import edu.duke.cabig.c3pr.webservice.iso21090.DSETENPN;
import edu.duke.cabig.c3pr.webservice.iso21090.ENPN;
import edu.duke.cabig.c3pr.webservice.iso21090.ENXP;
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

	/** The log. */
	private static Log log = LogFactory
			.getLog(SubjectRegistrationJAXBToDomainObjectConverterImpl.class);

	/* (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.webservice.subjectregistration.coverters.SubjectRegistrationJAXBToDomainObjectConverter#convertToStudySubjectRegistration(edu.duke.cabig.c3pr.domain.StudySubject)
	 */
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
	
	/* (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.webservice.subjectregistry.converters.SubjectRegistryJAXBToDomainObjectConverterImpl#getStudySubjectProtocolVersion(edu.duke.cabig.c3pr.domain.StudySubjectStudyVersion)
	 */
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
	
	/* (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.webservice.subjectregistration.coverters.SubjectRegistrationJAXBToDomainObjectConverter#convertToScheduledEpochs(java.util.List)
	 */
	public List<ScheduledEpoch> convertToScheduledEpochs(
			List<edu.duke.cabig.c3pr.domain.ScheduledEpoch> scheduledEpochs) {
		List<ScheduledEpoch> returnList = new ArrayList<ScheduledEpoch>();
		for(edu.duke.cabig.c3pr.domain.ScheduledEpoch scheduledEpoch : scheduledEpochs){
			returnList.add(convertToScheduledEpoch(scheduledEpoch));
		}
		return returnList;
	}
	
	/* (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.webservice.subjectregistration.coverters.SubjectRegistrationJAXBToDomainObjectConverter#convertToScheduledEpoch(edu.duke.cabig.c3pr.domain.ScheduledEpoch)
	 */
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
	
	/* (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.webservice.subjectregistration.coverters.SubjectRegistrationJAXBToDomainObjectConverter#convertToEpoch(edu.duke.cabig.c3pr.domain.Epoch)
	 */
	public Epoch convertToEpoch(edu.duke.cabig.c3pr.domain.Epoch epoch){
		Epoch convertedEpoch = new Epoch();
		convertedEpoch.setDescription(iso.ST(epoch.getDescriptionText()));
		convertedEpoch.setName(iso.ST(epoch.getName()));
		convertedEpoch.setSequenceNumber(iso.INTPositive(epoch.getEpochOrder()));
		convertedEpoch.setTypeCode(iso.CD(epoch.getType().getCode()));
		return convertedEpoch;
	}
	
	/* (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.webservice.subjectregistration.coverters.SubjectRegistrationJAXBToDomainObjectConverter#convertToScheduledArm(edu.duke.cabig.c3pr.domain.ScheduledArm)
	 */
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
	
	/* (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.webservice.subjectregistration.coverters.SubjectRegistrationJAXBToDomainObjectConverter#convertToSubjectEligibilityAnswers(java.util.List)
	 */
	public List<PerformedObservationResult> convertToSubjectEligibilityAnswers(List<SubjectEligibilityAnswer> answers){
		List<PerformedObservationResult> convertedAnswers = new ArrayList<PerformedObservationResult>();
		for(SubjectEligibilityAnswer answer : answers){
			convertedAnswers.add(convertToSubjectEligibilityAnswer(answer));
		}
		return convertedAnswers;
	}
	
	/**
	 * Convert to subject eligibility answer.
	 *
	 * @param answer the answer
	 * @return the performed observation result
	 */
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
	
	/* (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.webservice.subjectregistration.coverters.SubjectRegistrationJAXBToDomainObjectConverter#convertToSubjectStratificationAnswers(java.util.List)
	 */
	public List<PerformedObservationResult> convertToSubjectStratificationAnswers(List<SubjectStratificationAnswer> answers){
		List<PerformedObservationResult> convertedAnswers = new ArrayList<PerformedObservationResult>();
		for(SubjectStratificationAnswer answer : answers){
			convertedAnswers.add(convertToSubjectStratificationAnswer(answer));
		}
		return convertedAnswers;
	}
	
	/**
	 * Convert to subject stratification answer.
	 *
	 * @param answer the answer
	 * @return the performed observation result
	 */
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
	
	/* (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.webservice.subjectregistration.coverters.SubjectRegistrationJAXBToDomainObjectConverter#convertToDiseaseHistory(edu.duke.cabig.c3pr.domain.DiseaseHistory)
	 */
	public PerformedDiagnosis convertToDiseaseHistory(DiseaseHistory diseaseHistory){
		PerformedDiagnosis convertedDiseaseHistory = new PerformedDiagnosis();
		StudyCondition condition = new StudyCondition();
		condition.setConditionCode(iso.CD(diseaseHistory.getPrimaryDiseaseStr()));
		convertedDiseaseHistory.setDisease(condition);
		convertedDiseaseHistory.setTargetAnatomicSiteCode(iso.CD(diseaseHistory.getPrimaryDiseaseSiteStr()));
		return convertedDiseaseHistory;
	}
	
	/* (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.webservice.subjectregistration.coverters.SubjectRegistrationJAXBToDomainObjectConverter#convertToStudyInvestigator(edu.duke.cabig.c3pr.domain.StudySubject)
	 */
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

	/**
	 * Plugs in a dummy StudyDisease with the right term in it. Replace with hibernate attached StudyDisease in impl.
	 *
	 * @param diseaseHistory the disease history
	 * @return the disease history
	 */
	public DiseaseHistory convertDiseaseHistory(PerformedDiagnosis diseaseHistory) {
		DiseaseHistory convertedDiseaseHistory = new DiseaseHistory();

		StudyDisease studyDisease = new StudyDisease();
		DiseaseTerm diseaseTerm = new DiseaseTerm();
		diseaseTerm.setTerm(diseaseHistory.getDisease().getConditionCode().getCode());
		studyDisease.setDiseaseTerm(diseaseTerm);
		convertedDiseaseHistory.setStudyDisease(studyDisease);
		
		//icd9 name
		ICD9DiseaseSite icd9DiseaseSite = new ICD9DiseaseSite();
		icd9DiseaseSite.setName(diseaseHistory.getTargetAnatomicSiteCode().getCode());
		convertedDiseaseHistory.setIcd9DiseaseSite(icd9DiseaseSite);
		
		return convertedDiseaseHistory;
	}

	/* (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.webservice.subjectregistration.coverters.SubjectRegistrationJAXBToDomainObjectConverter#convertEpoch(edu.duke.cabig.c3pr.webservice.subjectregistration.Epoch)
	 */
	public edu.duke.cabig.c3pr.domain.Epoch convertEpoch(Epoch epoch) {
		edu.duke.cabig.c3pr.domain.Epoch convertedEpoch = new edu.duke.cabig.c3pr.domain.Epoch();
		
		convertedEpoch.setDescriptionText(epoch.getDescription().getValue());
		convertedEpoch.setName(epoch.getName().getValue());
		convertedEpoch.setEpochOrder(epoch.getSequenceNumber().getValue());
		convertedEpoch.setType(EpochType.getByCode(epoch.getTypeCode().getCode()));
		
		return convertedEpoch;
	}

	/**
	 * Plugs in a dummy arm with the right arm name.
	 * The dummy arm is expected to be replaced with the Hibernate object by the calling method or Impl.
	 *
	 * @param scheduledArm the scheduled arm
	 * @return the scheduled arm
	 */
	public ScheduledArm convertScheduledArm(PerformedActivity scheduledArm) {
		ScheduledArm convertedArm = new ScheduledArm();
		Arm arm = new Arm();
		if(scheduledArm.getDefinedActivity() != null && scheduledArm.getDefinedActivity().getNameCode() != null &&
				!scheduledArm.getDefinedActivity().getNameCode().getNullFlavor().equals(NullFlavor.NI)){
			arm.setName(scheduledArm.getDefinedActivity().getNameCode().getCode());
			convertedArm.setArm(arm);
		} else if(scheduledArm.getDrug() != null && scheduledArm.getDrug().getKitNumber() != null &&
				!scheduledArm.getDrug().getKitNumber().getNullFlavor().equals(NullFlavor.NI)){
			convertedArm.setKitNumber(scheduledArm.getDrug().getKitNumber().getExtension());
		}
		return convertedArm;
	}

	/**
	 * Converts the ScheduledEpoch stub to the domain object.
	 *
	 * @param scheduledEpoch the scheduled epoch
	 * @return the edu.duke.cabig.c3pr.domain. scheduled epoch
	 */
	public edu.duke.cabig.c3pr.domain.ScheduledEpoch convertScheduledEpoch(ScheduledEpoch scheduledEpoch) {
		edu.duke.cabig.c3pr.domain.ScheduledEpoch covertedScheduledEpoch = new edu.duke.cabig.c3pr.domain.ScheduledEpoch();
		
		covertedScheduledEpoch.setStartDate(convertToDate(scheduledEpoch.getStartDate()));
		covertedScheduledEpoch.setOffEpochDate(convertToDate(scheduledEpoch.getOffEpochDate()));
		covertedScheduledEpoch.setStratumGroupNumber(scheduledEpoch.getStratumGroupNumber().getValue());
		covertedScheduledEpoch.setEpoch(convertEpoch(scheduledEpoch.getEpoch()));
		covertedScheduledEpoch.addScheduledArm(convertScheduledArm(scheduledEpoch.getScheduledArm()));
		
		OffEpochReason offEpochReason = null;
		for(Object object : scheduledEpoch.getOffEpochReason().getItem()){
			CD offEpochReasonCd = (CD)object;
			offEpochReason = new OffEpochReason();
			if(covertedScheduledEpoch.getEpoch().getType().equals(EpochType.FOLLOWUP)){
				OffFollowupReason offFollowupReason = new OffFollowupReason();
				offFollowupReason.setCode(offEpochReasonCd.getCode());
				offEpochReason.setReason(offFollowupReason);
			}
			if(covertedScheduledEpoch.getEpoch().getType().equals(EpochType.RESERVING)){
				OffReservingReason offReservingReason = new OffReservingReason();
				offReservingReason.setCode(offEpochReasonCd.getCode());
				offEpochReason.setReason(offReservingReason);		
			}
			if(covertedScheduledEpoch.getEpoch().getType().equals(EpochType.SCREENING)){
				OffScreeningReason offScreeningReason = new OffScreeningReason();
				offScreeningReason.setCode(offEpochReasonCd.getCode());
				offEpochReason.setReason(offScreeningReason);
			}
			if(covertedScheduledEpoch.getEpoch().getType().equals(EpochType.TREATMENT)){
				OffTreatmentReason offTreatmentReason = new OffTreatmentReason();
				offTreatmentReason.setCode(offEpochReasonCd.getCode());
				offEpochReason.setReason(offTreatmentReason);
			}
			//not creating reason for observation or other
			if(covertedScheduledEpoch.getEpoch().getType().equals(EpochType.OBSERVATION) || 
					covertedScheduledEpoch.getEpoch().getType().equals(EpochType.OTHER)){
				offEpochReason.setDescription(offEpochReasonCd.getCode());
			}
			covertedScheduledEpoch.getOffEpochReasons().add(offEpochReason);
			covertedScheduledEpoch.setScEpochWorkflowStatus(ScheduledEpochWorkFlowStatus.OFF_EPOCH);
		}
		
		covertedScheduledEpoch.getSubjectEligibilityAnswers().addAll(convertSubjectEligibilityAnswers(scheduledEpoch.getSubjectEligibilityAnswer()));
		covertedScheduledEpoch.getSubjectStratificationAnswers().addAll(convertSubjectStratificationAnswers(scheduledEpoch.getSubjectStartificationAnswer()));

		return covertedScheduledEpoch;
	}

	/* (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.webservice.subjectregistration.coverters.SubjectRegistrationJAXBToDomainObjectConverter#convertScheduledEpochs(java.util.List)
	 */
	public List<edu.duke.cabig.c3pr.domain.ScheduledEpoch> convertScheduledEpochs(
			List<ScheduledEpoch> scheduledEpochs) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.webservice.subjectregistration.coverters.SubjectRegistrationJAXBToDomainObjectConverter#convertStudyInvestigator(edu.duke.cabig.c3pr.webservice.subjectregistration.StudyInvestigator)
	 */
	public edu.duke.cabig.c3pr.domain.StudyInvestigator convertStudyInvestigator(StudyInvestigator studyInvestigator) {
		edu.duke.cabig.c3pr.domain.StudyInvestigator convertedStudyInvestigator = new edu.duke.cabig.c3pr.domain.StudyInvestigator();
		LocalInvestigator convertedInvestigator = new LocalInvestigator();
		
		Person person = studyInvestigator.getHealthcareProvider().getPerson();
		convertedInvestigator.setFirstName(getFirstName(person));
		convertedInvestigator.setLastName(getLastName(person));
		convertedInvestigator.setMiddleName(getMiddleName(person));
		Iterator<Address> addIter = getAddresses(person).iterator();
		convertedInvestigator.setAddress(addIter.next());
		
		if(studyInvestigator.getHealthcareProvider().getIdentifier().getNullFlavor() != null &&
				studyInvestigator.getHealthcareProvider().getIdentifier().getNullFlavor().equals(NullFlavor.NI)){
			convertedInvestigator.setAssignedIdentifier(null);
		} else {
			convertedInvestigator.setAssignedIdentifier(studyInvestigator.getHealthcareProvider().getIdentifier().getExtension());
		}
		HealthcareSiteInvestigator healthcareSiteInvestigator = new HealthcareSiteInvestigator();
		healthcareSiteInvestigator.setInvestigator(convertedInvestigator);
		convertedStudyInvestigator.setHealthcareSiteInvestigator(healthcareSiteInvestigator);
		return convertedStudyInvestigator;
	}

	/* (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.webservice.subjectregistration.coverters.SubjectRegistrationJAXBToDomainObjectConverter#convertSubjectEligibilityAnswers(java.util.List)
	 */
	public List<SubjectEligibilityAnswer> convertSubjectEligibilityAnswers(
			List<PerformedObservationResult> answers) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.webservice.subjectregistration.coverters.SubjectRegistrationJAXBToDomainObjectConverter#convertSubjectStratificationAnswers(java.util.List)
	 */
	public List<SubjectStratificationAnswer> convertSubjectStratificationAnswers(
			List<PerformedObservationResult> answers) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Convert.
	 *
	 * @param destination the destination
	 * @param source the source
	 */
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
