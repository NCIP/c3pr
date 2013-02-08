/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.webservice.subjectregistration.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.jws.WebService;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import com.semanticbits.querybuilder.AdvancedSearchCriteriaParameter;

import edu.duke.cabig.c3pr.dao.ICD9DiseaseSiteDao;
import edu.duke.cabig.c3pr.dao.ParticipantDao;
import edu.duke.cabig.c3pr.dao.StudySubjectDao;
import edu.duke.cabig.c3pr.domain.Consent;
import edu.duke.cabig.c3pr.domain.ConsentQuestion;
import edu.duke.cabig.c3pr.domain.DiseaseHistory;
import edu.duke.cabig.c3pr.domain.Epoch;
import edu.duke.cabig.c3pr.domain.ICD9DiseaseSite;
import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.ScheduledEpoch;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyDisease;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.StudySubjectConsentVersion;
import edu.duke.cabig.c3pr.domain.SubjectConsentQuestionAnswer;
import edu.duke.cabig.c3pr.domain.repository.StudyRepository;
import edu.duke.cabig.c3pr.domain.repository.StudySubjectRepository;
import edu.duke.cabig.c3pr.exception.C3PRCodedRuntimeException;
import edu.duke.cabig.c3pr.exception.C3PRExceptionHelper;
import edu.duke.cabig.c3pr.exception.ConversionException;
import edu.duke.cabig.c3pr.webservice.common.AdvanceSearchCriterionParameter;
import edu.duke.cabig.c3pr.webservice.common.BiologicEntityIdentifier;
import edu.duke.cabig.c3pr.webservice.common.DocumentIdentifier;
import edu.duke.cabig.c3pr.webservice.common.DuplicateStudySubjectExceptionFault;
import edu.duke.cabig.c3pr.webservice.common.InvalidQueryExceptionFault;
import edu.duke.cabig.c3pr.webservice.common.InvalidSiteExceptionFault;
import edu.duke.cabig.c3pr.webservice.common.InvalidStudyProtocolExceptionFault;
import edu.duke.cabig.c3pr.webservice.common.InvalidStudySubjectDataExceptionFault;
import edu.duke.cabig.c3pr.webservice.common.OrganizationIdentifier;
import edu.duke.cabig.c3pr.webservice.common.SubjectIdentifier;
import edu.duke.cabig.c3pr.webservice.iso21090.ANY;
import edu.duke.cabig.c3pr.webservice.iso21090.CD;
import edu.duke.cabig.c3pr.webservice.subjectregistration.ChangeStudySubjectEpochAssignmentRequest;
import edu.duke.cabig.c3pr.webservice.subjectregistration.ChangeStudySubjectEpochAssignmentResponse;
import edu.duke.cabig.c3pr.webservice.subjectregistration.DSETStudySubject;
import edu.duke.cabig.c3pr.webservice.subjectregistration.DiscontinueEnrollmentRequest;
import edu.duke.cabig.c3pr.webservice.subjectregistration.DiscontinueEnrollmentResponse;
import edu.duke.cabig.c3pr.webservice.subjectregistration.DuplicateStudySubjectExceptionFaultMessage;
import edu.duke.cabig.c3pr.webservice.subjectregistration.EnrollSubjectExistingRequest;
import edu.duke.cabig.c3pr.webservice.subjectregistration.EnrollSubjectExistingResponse;
import edu.duke.cabig.c3pr.webservice.subjectregistration.EnrollSubjectNewRequest;
import edu.duke.cabig.c3pr.webservice.subjectregistration.EnrollSubjectNewResponse;
import edu.duke.cabig.c3pr.webservice.subjectregistration.FailSubjectScreeningRequest;
import edu.duke.cabig.c3pr.webservice.subjectregistration.FailSubjectScreeningResponse;
import edu.duke.cabig.c3pr.webservice.subjectregistration.GenerateSummary3ReportRequest;
import edu.duke.cabig.c3pr.webservice.subjectregistration.GenerateSummary3ReportResponse;
import edu.duke.cabig.c3pr.webservice.subjectregistration.ImportRegistrationsRequest;
import edu.duke.cabig.c3pr.webservice.subjectregistration.ImportRegistrationsResponse;
import edu.duke.cabig.c3pr.webservice.subjectregistration.InitiateSubjectEnrollmentRequest;
import edu.duke.cabig.c3pr.webservice.subjectregistration.InitiateSubjectEnrollmentResponse;
import edu.duke.cabig.c3pr.webservice.subjectregistration.InvalidQueryExceptionFaultMessage;
import edu.duke.cabig.c3pr.webservice.subjectregistration.InvalidSiteExceptionFaultMessage;
import edu.duke.cabig.c3pr.webservice.subjectregistration.InvalidStudyProtocolExceptionFaultMessage;
import edu.duke.cabig.c3pr.webservice.subjectregistration.InvalidStudySubjectDataExceptionFaultMessage;
import edu.duke.cabig.c3pr.webservice.subjectregistration.NoSuchPatientExceptionFaultMessage;
import edu.duke.cabig.c3pr.webservice.subjectregistration.NoSuchStudySubjectExceptionFaultMessage;
import edu.duke.cabig.c3pr.webservice.subjectregistration.PerformedDiagnosis;
import edu.duke.cabig.c3pr.webservice.subjectregistration.QuerySubjectRegistrationRequest;
import edu.duke.cabig.c3pr.webservice.subjectregistration.QuerySubjectRegistrationResponse;
import edu.duke.cabig.c3pr.webservice.subjectregistration.ReconsentStudySubjectRequest;
import edu.duke.cabig.c3pr.webservice.subjectregistration.ReconsentStudySubjectResponse;
import edu.duke.cabig.c3pr.webservice.subjectregistration.RegisterButNotEnrollSubjectExistingRequest;
import edu.duke.cabig.c3pr.webservice.subjectregistration.RegisterButNotEnrollSubjectExistingResponse;
import edu.duke.cabig.c3pr.webservice.subjectregistration.RegisterButNotEnrollSubjectNewRequest;
import edu.duke.cabig.c3pr.webservice.subjectregistration.RegisterButNotEnrollSubjectNewResponse;
import edu.duke.cabig.c3pr.webservice.subjectregistration.ReserveSlotExistingRequest;
import edu.duke.cabig.c3pr.webservice.subjectregistration.ReserveSlotExistingResponse;
import edu.duke.cabig.c3pr.webservice.subjectregistration.ReserveSlotNewRequest;
import edu.duke.cabig.c3pr.webservice.subjectregistration.ReserveSlotNewResponse;
import edu.duke.cabig.c3pr.webservice.subjectregistration.RetrieveAccrualDataRequest;
import edu.duke.cabig.c3pr.webservice.subjectregistration.RetrieveAccrualDataResponse;
import edu.duke.cabig.c3pr.webservice.subjectregistration.RetrieveSubjectDemographyHistoryRequest;
import edu.duke.cabig.c3pr.webservice.subjectregistration.RetrieveSubjectDemographyHistoryResponse;
import edu.duke.cabig.c3pr.webservice.subjectregistration.SecurityExceptionFaultMessage;
import edu.duke.cabig.c3pr.webservice.subjectregistration.StudyInvestigator;
import edu.duke.cabig.c3pr.webservice.subjectregistration.StudySubject;
import edu.duke.cabig.c3pr.webservice.subjectregistration.SubjectRegistration;
import edu.duke.cabig.c3pr.webservice.subjectregistration.SubjectRegistrationRejectedExceptionFaultMessage;
import edu.duke.cabig.c3pr.webservice.subjectregistration.TakeSubjectOffStudyRequest;
import edu.duke.cabig.c3pr.webservice.subjectregistration.TakeSubjectOffStudyResponse;
import edu.duke.cabig.c3pr.webservice.subjectregistration.UpdateRegistrationRequest;
import edu.duke.cabig.c3pr.webservice.subjectregistration.UpdateRegistrationResponse;
import edu.duke.cabig.c3pr.webservice.subjectregistration.UpdateStudySubjectConsentRequest;
import edu.duke.cabig.c3pr.webservice.subjectregistration.UpdateStudySubjectConsentResponse;
import edu.duke.cabig.c3pr.webservice.subjectregistration.coverters.SubjectRegistrationJAXBToDomainObjectConverter;

@WebService(wsdlLocation="/WEB-INF/wsdl/SubjectRegistration.wsdl", targetNamespace = "http://enterpriseservices.nci.nih.gov/SubjectRegistrationService", endpointInterface = "edu.duke.cabig.c3pr.webservice.subjectregistration.SubjectRegistration", portName = "SubjectRegistration", serviceName = "SubjectRegistrationService")
public class SubjectRegistrationImpl implements SubjectRegistration {
	/**
	 * Logger for this class
	 */
	private static final Logger log = Logger
			.getLogger(SubjectRegistrationImpl.class);
	private static final int NON_UNIQUE_IDENTIFIER = 910;
	private static final int MISSING_SUBJECT_IDENTIFIER = 902;
	private static final int SUBJECT_NOT_FOUND = 911;
	private static final int DUPLICATE_STUDYSUBJECT_IDENTIFIER = 913;
	private static final int RE_REGISTRATION = 924;
	private static final int INVALID_CONSENT_NAME = 914;
	private static final int INVALID_CONSENT_QUESTION = 915;
	private static final int INVALID_REASON = 916;
	private static final int NON_UNIQUE_ANATOMIC_SITE_NAME = 932;
	
	
	private C3PRExceptionHelper exceptionHelper;
	private SubjectRegistrationJAXBToDomainObjectConverter converter;
	private ParticipantDao participantDao;
	private StudyRepository studyRepository;
	private StudySubjectRepository studySubjectRepository;
	private StudySubjectDao studySubjectDao;
	private ICD9DiseaseSiteDao icd9DiseaseSiteDao;

	public EnrollSubjectNewResponse enrollStudySubject(EnrollSubjectNewRequest arg0)
		throws DuplicateStudySubjectExceptionFaultMessage,
		InvalidSiteExceptionFaultMessage,
		InvalidStudyProtocolExceptionFaultMessage,
		InvalidStudySubjectDataExceptionFaultMessage,
		SecurityExceptionFaultMessage,
		SubjectRegistrationRejectedExceptionFaultMessage {
	// TODO Auto-generated method stub
		return null;
	}
	
	public EnrollSubjectExistingResponse enrollExistingStudySubject(
		EnrollSubjectExistingRequest arg0)
		throws InvalidStudySubjectDataExceptionFaultMessage,
		NoSuchStudySubjectExceptionFaultMessage,
		SecurityExceptionFaultMessage,
		SubjectRegistrationRejectedExceptionFaultMessage {
	// TODO Auto-generated method stub
		return null;
	}
	
	public RegisterButNotEnrollSubjectNewResponse registerButNotEnrollStudySubject(
		RegisterButNotEnrollSubjectNewRequest arg0)
		throws DuplicateStudySubjectExceptionFaultMessage,
		InvalidSiteExceptionFaultMessage,
		InvalidStudyProtocolExceptionFaultMessage,
		InvalidStudySubjectDataExceptionFaultMessage,
		SecurityExceptionFaultMessage,
		SubjectRegistrationRejectedExceptionFaultMessage {
	// TODO Auto-generated method stub
		return null;
	}
	
	public RegisterButNotEnrollSubjectExistingResponse registerButNotEnrollExisitingStudySubject(
		RegisterButNotEnrollSubjectExistingRequest arg0)
		throws InvalidStudySubjectDataExceptionFaultMessage,
		NoSuchStudySubjectExceptionFaultMessage,
		SecurityExceptionFaultMessage,
		SubjectRegistrationRejectedExceptionFaultMessage {
	// TODO Auto-generated method stub
		return null;
	}
	
	public ReserveSlotNewResponse reserveStudySubjectSlot(ReserveSlotNewRequest arg0)
		throws DuplicateStudySubjectExceptionFaultMessage,
		InvalidSiteExceptionFaultMessage,
		InvalidStudyProtocolExceptionFaultMessage,
		InvalidStudySubjectDataExceptionFaultMessage,
		SecurityExceptionFaultMessage,
		SubjectRegistrationRejectedExceptionFaultMessage {
		// TODO Auto-generated method stub
		return null;
	}
	
	public ReserveSlotExistingResponse reserveExistingStudySubjectSlot(
		ReserveSlotExistingRequest arg0)
		throws InvalidStudySubjectDataExceptionFaultMessage,
		NoSuchStudySubjectExceptionFaultMessage,
		SecurityExceptionFaultMessage,
		SubjectRegistrationRejectedExceptionFaultMessage {
	// TODO Auto-generated method stub
		return null;
	}

	public UpdateStudySubjectConsentResponse updateStudySubjectConsent(
		UpdateStudySubjectConsentRequest arg0)
		throws InvalidStudySubjectDataExceptionFaultMessage,
		NoSuchStudySubjectExceptionFaultMessage,
		SecurityExceptionFaultMessage {
	// TODO Auto-generated method stub
		return null;
	}
	
	public ChangeStudySubjectEpochAssignmentResponse changeStudySubjectEpochAssignment(
			ChangeStudySubjectEpochAssignmentRequest arg0)
			throws InvalidStudyProtocolExceptionFaultMessage,
			InvalidStudySubjectDataExceptionFaultMessage,
			NoSuchStudySubjectExceptionFaultMessage,
			SecurityExceptionFaultMessage,
			SubjectRegistrationRejectedExceptionFaultMessage {
		// TODO Auto-generated method stub
		return null;
	}

	public DiscontinueEnrollmentResponse discontinueSubjectEnrollment(
			DiscontinueEnrollmentRequest arg0)
			throws InvalidStudySubjectDataExceptionFaultMessage,
			NoSuchStudySubjectExceptionFaultMessage,
			SecurityExceptionFaultMessage {
		// TODO Auto-generated method stub
		return null;
	}

	public FailSubjectScreeningResponse failSubjectScreening(
			FailSubjectScreeningRequest arg0)
			throws InvalidStudySubjectDataExceptionFaultMessage,
			NoSuchStudySubjectExceptionFaultMessage,
			SecurityExceptionFaultMessage {
		// TODO Auto-generated method stub
		return null;
	}

	public GenerateSummary3ReportResponse generateSummary3Report(
			GenerateSummary3ReportRequest arg0)
			throws InvalidSiteExceptionFaultMessage,
			SecurityExceptionFaultMessage {
		// TODO Auto-generated method stub
		return null;
	}

	public ImportRegistrationsResponse importRegistrations(
			ImportRegistrationsRequest arg0)
			throws InvalidStudySubjectDataExceptionFaultMessage,
			SecurityExceptionFaultMessage {
		// TODO Auto-generated method stub
		return null;
	}

	public InitiateSubjectEnrollmentResponse initiateStudySubjectEnrollment(
			InitiateSubjectEnrollmentRequest parameters)
			throws DuplicateStudySubjectExceptionFaultMessage,
			InvalidSiteExceptionFaultMessage,
			InvalidStudyProtocolExceptionFaultMessage,
			InvalidStudySubjectDataExceptionFaultMessage,
			SecurityExceptionFaultMessage,
			SubjectRegistrationRejectedExceptionFaultMessage {
		StudySubject studySubject = parameters.getStudySubject();
		//test duplicate study subject
		try {
			getStudySubject(studySubject.getSubjectIdentifier());
			handleDuplicateStudySubject(exceptionHelper
					.getConversionException(DUPLICATE_STUDYSUBJECT_IDENTIFIER));
		} catch (C3PRCodedRuntimeException e) {
			if(e.getExceptionCode() != 231)
				handleDuplicateStudySubject(e);
		}
		
		edu.duke.cabig.c3pr.domain.StudySubject domainObject = new edu.duke.cabig.c3pr.domain.StudySubject();
		
		//getParticipant
		Participant participant = getParticipant(parameters.getSubjectIdentifier());
		
		//getStudySite
		Study study = getStudy(parameters.getStudyIdentifier());
		
		//getStudySite
		StudySite studySite = getStudySite(study, parameters.getSiteIdentifier());
		
		//set study, studysite and subject demography
		domainObject.setStudySubjectDemographics(participant.createStudySubjectDemographics());
		domainObject.setStudySite(studySite);

		//test re-registration
		List<edu.duke.cabig.c3pr.domain.StudySubject> registrations = studySubjectRepository.findRegistrations(domainObject);;
		if (registrations.size() > 0) {
			handleDuplicateStudySubject(exceptionHelper
					.getConversionException(RE_REGISTRATION));
        }
		
		//getScheduledEpoch
		
		
		//copy enrollment information like identifiers, paymentMethod, disease, treatingPhysician
		copyEnrollmentDetails(domainObject , studySubject);
		
		//copy consent information
		if(studySubject.getStudySubjectProtocolVersion() != null &&
				studySubject.getStudySubjectProtocolVersion().getStudySubjectConsentVersion() !=null &&
				studySubject.getStudySubjectProtocolVersion().getStudySubjectConsentVersion().size()>0){
			copyConsentDetails(domainObject, studySubject.getStudySubjectProtocolVersion().getStudySubjectConsentVersion());
		}
		
		domainObject = studySubjectRepository.save(domainObject);
		
//		InitiateStudySubjectRegistryResponse response = new InitiateStudySubjectRegistryResponse();
//		response.setStudySubject(converter.convert(domainObject));
//		return response;
		return null;
	}

	public QuerySubjectRegistrationResponse querySubjectRegistration(
			QuerySubjectRegistrationRequest arg0)
			throws InvalidQueryExceptionFaultMessage,
			SecurityExceptionFaultMessage {
		QuerySubjectRegistrationResponse response = new QuerySubjectRegistrationResponse();
		DSETStudySubject studySubjects = new DSETStudySubject();
		response.setStudySubjects(studySubjects);

		try {
			List<AdvancedSearchCriteriaParameter> advParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
			for (AdvanceSearchCriterionParameter param : arg0.getParameters().getItem()) {
				AdvancedSearchCriteriaParameter advParam = converter
						.convert(param);
				advParameters.add(advParam);
			}

			List<edu.duke.cabig.c3pr.domain.StudySubject> results = new ArrayList<edu.duke.cabig.c3pr.domain.StudySubject>(
					studySubjectDao.search(advParameters));
			for (edu.duke.cabig.c3pr.domain.StudySubject result : results) {
				studySubjects.getItem().add(converter.convertToStudySubjectRegistration(result));
			}
		} catch (ConversionException e) {
			handleInvalidQueryData(e);
		}
		return response;
	}

	public ReconsentStudySubjectResponse reconsentSubject(
			ReconsentStudySubjectRequest arg0)
			throws InvalidStudyProtocolExceptionFaultMessage,
			InvalidStudySubjectDataExceptionFaultMessage,
			NoSuchStudySubjectExceptionFaultMessage,
			SecurityExceptionFaultMessage {
		// TODO Auto-generated method stub
		return null;
	}

	public RetrieveAccrualDataResponse retrieveAccuralData(
			RetrieveAccrualDataRequest arg0)
			throws InvalidSiteExceptionFaultMessage,
			InvalidStudyProtocolExceptionFaultMessage,
			SecurityExceptionFaultMessage {
		// TODO Auto-generated method stub
		return null;
	}

	public RetrieveSubjectDemographyHistoryResponse retrieveSubjectDemographyHistory(
			RetrieveSubjectDemographyHistoryRequest arg0)
			throws NoSuchPatientExceptionFaultMessage,
			SecurityExceptionFaultMessage {
		// TODO Auto-generated method stub
		return null;
	}

	public TakeSubjectOffStudyResponse takeSubjectOffStudy(
			TakeSubjectOffStudyRequest arg0)
			throws InvalidStudySubjectDataExceptionFaultMessage,
			NoSuchStudySubjectExceptionFaultMessage,
			SecurityExceptionFaultMessage {
		// TODO Auto-generated method stub
		return null;
	}

	public UpdateRegistrationResponse updateSubjectRegistration(
			UpdateRegistrationRequest arg0)
			throws InvalidStudySubjectDataExceptionFaultMessage,
			NoSuchStudySubjectExceptionFaultMessage,
			SecurityExceptionFaultMessage {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param e
	 * @throws handleInvalidQueryData
	 */
	private void handleInvalidQueryData(Exception e)
			throws InvalidQueryExceptionFaultMessage {
		log.error(ExceptionUtils.getFullStackTrace(e));
		InvalidQueryExceptionFault fault = new InvalidQueryExceptionFault();
		fault.setMessage(e.getMessage());
		throw new InvalidQueryExceptionFaultMessage(e.getMessage(), fault);
	}
	
	/**
	 * @param e
	 * @throws handleDuplicateStudySubject
	 */
	private void handleDuplicateStudySubject(Exception e)
			throws DuplicateStudySubjectExceptionFaultMessage {
		log.error(ExceptionUtils.getFullStackTrace(e));
		DuplicateStudySubjectExceptionFault fault = new DuplicateStudySubjectExceptionFault();
		fault.setMessage(e.getMessage());
		throw new DuplicateStudySubjectExceptionFaultMessage(e.getMessage(), fault);
	}
	
	/**
	 * @param e
	 * @throws InvalidStudySubjectDataExceptionFaultMessage
	 */
	private void handleInvalidStudySubjectData(Exception e)
			throws InvalidStudySubjectDataExceptionFaultMessage {
		log.error(ExceptionUtils.getFullStackTrace(e));
		InvalidStudySubjectDataExceptionFault fault = new InvalidStudySubjectDataExceptionFault();
		fault.setMessage(e.getMessage());
		throw new InvalidStudySubjectDataExceptionFaultMessage(e.getMessage(), fault);
	}
	
	/**
	 * @param e
	 * @throws handleInvalidStudyData
	 */
	private void handleInvalidStudyData(Exception e)
			throws InvalidStudyProtocolExceptionFaultMessage {
		log.error(ExceptionUtils.getFullStackTrace(e));
		InvalidStudyProtocolExceptionFault fault = new InvalidStudyProtocolExceptionFault();
		fault.setMessage(e.getMessage());
		throw new InvalidStudyProtocolExceptionFaultMessage(e.getMessage(), fault);
	}
	
	/**
	 * @param e
	 * @throws handleInvalidSiteData
	 */
	private void handleInvalidSiteData(Exception e)
			throws InvalidSiteExceptionFaultMessage {
		log.error(ExceptionUtils.getFullStackTrace(e));
		InvalidSiteExceptionFault fault = new InvalidSiteExceptionFault();
		fault.setMessage(e.getMessage());
		throw new InvalidSiteExceptionFaultMessage(e.getMessage(), fault);
	}
	
	private edu.duke.cabig.c3pr.domain.StudySubject getStudySubject(List<SubjectIdentifier> identifiers){
		edu.duke.cabig.c3pr.domain.StudySubject domainObject = new edu.duke.cabig.c3pr.domain.StudySubject();
		domainObject.getIdentifiers().addAll(converter.convertSubjectIdentifiers(identifiers));
		return studySubjectRepository.getUniqueStudySubject(domainObject.getPrimaryIdentifierObject());
	}
	
	private Participant getParticipant(BiologicEntityIdentifier bioId) throws InvalidStudySubjectDataExceptionFaultMessage{
		List<BiologicEntityIdentifier> identifiers = new ArrayList<BiologicEntityIdentifier>();
		identifiers.add(bioId);
		if (CollectionUtils.isEmpty(identifiers)) {
			handleInvalidStudySubjectData(exceptionHelper
					.getConversionException(MISSING_SUBJECT_IDENTIFIER));
		}
		List<Participant> participants = participantDao.getByIdentifiers(converter.convertBiologicIdentifiers(identifiers));
		if(participants.size() > 1){
			handleInvalidStudySubjectData(exceptionHelper
					.getConversionException(NON_UNIQUE_IDENTIFIER));
		}else if(participants.size() == 0){
			handleInvalidStudySubjectData(exceptionHelper
					.getConversionException(SUBJECT_NOT_FOUND));
		}
		return participants.get(0);
	}
	
	private Study getStudy(DocumentIdentifier docId) throws InvalidStudyProtocolExceptionFaultMessage{
		Study study = null;
		try {
			study = studyRepository.getUniqueStudy(converter.convert(Arrays.asList(new DocumentIdentifier[]{docId})));
		} catch (C3PRCodedRuntimeException e) {
			handleInvalidStudyData(e);
		}
		return study;
	}
	
	private StudySite getStudySite(Study study, OrganizationIdentifier orgId)throws InvalidSiteExceptionFaultMessage, InvalidStudyProtocolExceptionFaultMessage{
		StudySite studySite = null;
		try {
			String siteId = converter.convertHealthcareSitePrimaryIdentifier(orgId);
			studySite = study.getStudySite(siteId);
			if(studySite == null){
				throw exceptionHelper.getRuntimeException(212, new Object[]{siteId});
			}
		} catch (C3PRCodedRuntimeException e) {
			handleInvalidSiteData(e);
		}
		return studySite;
	}

	private void copyEnrollmentDetails(edu.duke.cabig.c3pr.domain.StudySubject destination , StudySubject source) throws InvalidStudySubjectDataExceptionFaultMessage{
		//copy organic attributes
		CD paymentMethod = source.getPaymentMethodCode();
		destination.setPaymentMethod(!isNull(paymentMethod) ? paymentMethod.getCode() : null);
		
		//copy identifiers
		List<Identifier> identifiers = converter.convertSubjectIdentifiers(source.getSubjectIdentifier());
		destination.getIdentifiers().clear();
		destination.getIdentifiers().addAll(identifiers);
		
		//set Disease History
		updateDiseaseHistory(destination, source.getDiseaseHistory());
		
		//set Treating/Enrolling physician
		updateTreatingPhysician(destination, source.getTreatingPhysician());
	}
	
	private void copyConsentDetails(edu.duke.cabig.c3pr.domain.StudySubject destination , List<edu.duke.cabig.c3pr.webservice.common.StudySubjectConsentVersion> subjectConsents) throws InvalidStudySubjectDataExceptionFaultMessage{
		List<StudySubjectConsentVersion> studySubjectConsents = converter.convertSubjectConsent(subjectConsents);
		destination.getStudySubjectStudyVersion().getStudySubjectConsentVersions().clear();
		Study study = destination.getStudySite().getStudy();
		for(StudySubjectConsentVersion studySubjectConsentVersion : studySubjectConsents){
			Consent consent = study.getConsent(studySubjectConsentVersion.getConsent().getName(), studySubjectConsentVersion.getConsent().getVersionId());
			if(consent == null){
				handleInvalidStudySubjectData(exceptionHelper
						.getConversionException(INVALID_CONSENT_NAME, new Object[]{studySubjectConsentVersion.getConsent().getName()}));
				
			}
			studySubjectConsentVersion.setConsent(consent);
			for(SubjectConsentQuestionAnswer subjectAnswer : studySubjectConsentVersion.getSubjectConsentAnswers()){
				ConsentQuestion consentQuestion = consent.getQuestion(subjectAnswer.getConsentQuestion().getCode());
				if(consentQuestion == null){
					handleInvalidStudySubjectData(exceptionHelper
							.getConversionException(INVALID_CONSENT_QUESTION, new Object[]{subjectAnswer.getConsentQuestion().getCode()}));
				}
				subjectAnswer.setConsentQuestion(consentQuestion);
			}
			destination.getStudySubjectStudyVersion().getStudySubjectConsentVersions().add(studySubjectConsentVersion);
		}
	}
	
	private void updateDiseaseHistory(edu.duke.cabig.c3pr.domain.StudySubject destination, PerformedDiagnosis diseaseHistory) throws InvalidStudySubjectDataExceptionFaultMessage{
		DiseaseHistory convertedDiseaseHistory = converter.convertDiseaseHistory(diseaseHistory);
		if(!StringUtils.isBlank(convertedDiseaseHistory.getOtherPrimaryDiseaseCode())){
			for(StudyDisease studyDisease: destination.getStudySite().getStudy().getStudyDiseases()){
				if(studyDisease.getDiseaseTerm().getTerm().equals(convertedDiseaseHistory.getOtherPrimaryDiseaseCode())){
					convertedDiseaseHistory.setStudyDisease(studyDisease);
					convertedDiseaseHistory.setOtherPrimaryDiseaseCode(null);
					break;
				}
			}
		}
		if(!StringUtils.isBlank(convertedDiseaseHistory.getOtherPrimaryDiseaseSiteCode())){
			List<ICD9DiseaseSite> list = icd9DiseaseSiteDao.getByName(convertedDiseaseHistory.getOtherPrimaryDiseaseSiteCode());
			if(list.size()>1){
				handleInvalidStudySubjectData(exceptionHelper
						.getConversionException(NON_UNIQUE_ANATOMIC_SITE_NAME));
			}else if(list.size()==1){
				convertedDiseaseHistory.setIcd9DiseaseSite(list.get(0));
			}
		}
		destination.setDiseaseHistory(convertedDiseaseHistory);
	}
	
	private void updateTreatingPhysician(edu.duke.cabig.c3pr.domain.StudySubject destination, StudyInvestigator studyInvestigator){
		edu.duke.cabig.c3pr.domain.StudyInvestigator convertedStudyInvestigator = converter.convertStudyInvestigator(studyInvestigator);
		if(convertedStudyInvestigator == null){
			return;
		}
		if(StringUtils.isBlank(convertedStudyInvestigator.getHealthcareSiteInvestigator().getInvestigator().getAssignedIdentifier())){
			destination.setOtherTreatingPhysician(convertedStudyInvestigator.getHealthcareSiteInvestigator().getInvestigator().getFullName());
		}else{
			for(edu.duke.cabig.c3pr.domain.StudyInvestigator stInv : destination.getStudySite().getStudyInvestigators()){
				if(stInv.getHealthcareSiteInvestigator().getInvestigator().getAssignedIdentifier().equals(
						convertedStudyInvestigator.getHealthcareSiteInvestigator().getInvestigator().getAssignedIdentifier())){
					destination.setTreatingPhysician(stInv);
					return;
				}
			}
		}
	}
	
	private ScheduledEpoch getScheduledEpoch(Study study, edu.duke.cabig.c3pr.webservice.subjectregistration.ScheduledEpoch scheduledEpoch){
		ScheduledEpoch convertedScheduledEpoch = new ScheduledEpoch();
		Epoch convertedEpoch = converter.convertEpoch(scheduledEpoch.getEpoch());
		boolean notFound = true;
		for(Epoch epoch : study.getEpochs()){
			if(epoch.getName().equalsIgnoreCase(convertedEpoch.getName())){
				convertedEpoch = epoch;
				notFound = false;
				break;
			}
		}
		if(notFound){
			
		}
		return null;
	}
	
	private boolean isNull(ANY cd) {
		return cd == null || cd.getNullFlavor() != null;
	}
	
	public void setParticipantDao(ParticipantDao participantDao) {
		this.participantDao = participantDao;
	}

	public void setStudyRepository(StudyRepository studyRepository) {
		this.studyRepository = studyRepository;
	}

	public void setStudySubjectRepository(
			StudySubjectRepository studySubjectRepository) {
		this.studySubjectRepository = studySubjectRepository;
	}

	public void setExceptionHelper(C3PRExceptionHelper exceptionHelper) {
		this.exceptionHelper = exceptionHelper;
	}
	
	public void setConverter(
			SubjectRegistrationJAXBToDomainObjectConverter converter) {
		this.converter = converter;
	}

	public void setStudySubjectDao(StudySubjectDao studySubjectDao) {
		this.studySubjectDao = studySubjectDao;
	}

	public void setIcd9DiseaseSiteDao(ICD9DiseaseSiteDao icd9DiseaseSiteDao) {
		this.icd9DiseaseSiteDao = icd9DiseaseSiteDao;
	}
	
}
