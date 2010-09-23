package edu.duke.cabig.c3pr.webservice.subjectregistry.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.jws.WebService;
import javax.xml.ws.Holder;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import com.semanticbits.querybuilder.AdvancedSearchCriteriaParameter;

import edu.duke.cabig.c3pr.constants.RegistrationDataEntryStatus;
import edu.duke.cabig.c3pr.dao.ParticipantDao;
import edu.duke.cabig.c3pr.dao.RegistryStatusDao;
import edu.duke.cabig.c3pr.dao.StudySubjectDao;
import edu.duke.cabig.c3pr.domain.Consent;
import edu.duke.cabig.c3pr.domain.ConsentQuestion;
import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.PermissibleStudySubjectRegistryStatus;
import edu.duke.cabig.c3pr.domain.RegistryStatus;
import edu.duke.cabig.c3pr.domain.RegistryStatusReason;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.StudySubjectConsentVersion;
import edu.duke.cabig.c3pr.domain.StudySubjectDemographics;
import edu.duke.cabig.c3pr.domain.StudySubjectRegistryStatus;
import edu.duke.cabig.c3pr.domain.SubjectConsentQuestionAnswer;
import edu.duke.cabig.c3pr.domain.repository.StudyRepository;
import edu.duke.cabig.c3pr.domain.repository.StudySubjectRepository;
import edu.duke.cabig.c3pr.exception.C3PRCodedRuntimeException;
import edu.duke.cabig.c3pr.exception.C3PRExceptionHelper;
import edu.duke.cabig.c3pr.exception.ConversionException;
import edu.duke.cabig.c3pr.webservice.iso21090.ANY;
import edu.duke.cabig.c3pr.webservice.iso21090.CD;
import edu.duke.cabig.c3pr.webservice.subjectregistry.AdvanceSearchCriterionParameter;
import edu.duke.cabig.c3pr.webservice.subjectregistry.BiologicEntityIdentifier;
import edu.duke.cabig.c3pr.webservice.subjectregistry.DSETPerson;
import edu.duke.cabig.c3pr.webservice.subjectregistry.DSETStudySubject;
import edu.duke.cabig.c3pr.webservice.subjectregistry.DocumentIdentifier;
import edu.duke.cabig.c3pr.webservice.subjectregistry.DuplicateStudySubjectExceptionFault;
import edu.duke.cabig.c3pr.webservice.subjectregistry.DuplicateStudySubjectExceptionFaultMessage;
import edu.duke.cabig.c3pr.webservice.subjectregistry.ImportSubjectRegistryRequest;
import edu.duke.cabig.c3pr.webservice.subjectregistry.InitiateSubjectRegistryRequest;
import edu.duke.cabig.c3pr.webservice.subjectregistry.InitiateSubjectRegistryResponse;
import edu.duke.cabig.c3pr.webservice.subjectregistry.InsufficientPrivilegesExceptionFaultMessage;
import edu.duke.cabig.c3pr.webservice.subjectregistry.InvalidQueryExceptionFault;
import edu.duke.cabig.c3pr.webservice.subjectregistry.InvalidQueryExceptionFaultMessage;
import edu.duke.cabig.c3pr.webservice.subjectregistry.InvalidSiteExceptionFault;
import edu.duke.cabig.c3pr.webservice.subjectregistry.InvalidSiteExceptionFaultMessage;
import edu.duke.cabig.c3pr.webservice.subjectregistry.InvalidStudyProtocolExceptionFault;
import edu.duke.cabig.c3pr.webservice.subjectregistry.InvalidStudyProtocolExceptionFaultMessage;
import edu.duke.cabig.c3pr.webservice.subjectregistry.InvalidStudySubjectDataExceptionFault;
import edu.duke.cabig.c3pr.webservice.subjectregistry.InvalidStudySubjectDataExceptionFaultMessage;
import edu.duke.cabig.c3pr.webservice.subjectregistry.NoSuchPatientExceptionFault;
import edu.duke.cabig.c3pr.webservice.subjectregistry.NoSuchPatientExceptionFaultMessage;
import edu.duke.cabig.c3pr.webservice.subjectregistry.NoSuchStudySubjectExceptionFault;
import edu.duke.cabig.c3pr.webservice.subjectregistry.NoSuchStudySubjectExceptionFaultMessage;
import edu.duke.cabig.c3pr.webservice.subjectregistry.PerformedStudySubjectMilestone;
import edu.duke.cabig.c3pr.webservice.subjectregistry.Person;
import edu.duke.cabig.c3pr.webservice.subjectregistry.QuerySubjectRegistryRequest;
import edu.duke.cabig.c3pr.webservice.subjectregistry.QuerySubjectRegistryResponse;
import edu.duke.cabig.c3pr.webservice.subjectregistry.RetrieveSubjectDemographyHistoryRequest;
import edu.duke.cabig.c3pr.webservice.subjectregistry.RetrieveSubjectDemographyHistoryResponse;
import edu.duke.cabig.c3pr.webservice.subjectregistry.StudySubject;
import edu.duke.cabig.c3pr.webservice.subjectregistry.StudySubjectProtocolVersionRelationship;
import edu.duke.cabig.c3pr.webservice.subjectregistry.SubjectIdentifier;
import edu.duke.cabig.c3pr.webservice.subjectregistry.SubjectRegistry;
import edu.duke.cabig.c3pr.webservice.subjectregistry.UpdateSubjectRegistryRequest;
import edu.duke.cabig.c3pr.webservice.subjectregistry.UpdateSubjectRegistryResponse;
import edu.duke.cabig.c3pr.webservice.subjectregistry.UpdateSubjectRegistryStatusHistoryRequest;
import edu.duke.cabig.c3pr.webservice.subjectregistry.UpdateSubjectRegistryStatusHistoryResponse;
import edu.duke.cabig.c3pr.webservice.subjectregistry.UpdateSubjectRegistryStatusRequest;
import edu.duke.cabig.c3pr.webservice.subjectregistry.UpdateSubjectRegistryStatusResponse;
import edu.duke.cabig.c3pr.webservice.subjectregistry.convertes.SubjectRegistryJAXBToDomainObjectConverter;

@WebService(wsdlLocation="/WEB-INF/wsdl/SubjectRegistry.wsdl", targetNamespace = "http://enterpriseservices.nci.nih.gov/SubjectRegistryService", endpointInterface = "edu.duke.cabig.c3pr.webservice.subjectregistry.SubjectRegistry", portName = "SubjectRegistry", serviceName = "SubjectRegistryService")
public class SubjectRegistryImpl implements SubjectRegistry {
	/**
	 * Logger for this class
	 */
	private static final Logger log = Logger
			.getLogger(SubjectRegistryImpl.class);

	private static final int NON_UNIQUE_IDENTIFIER = 910;
	private static final int MISSING_SUBJECT_IDENTIFIER = 902;
	private static final int SUBJECT_NOT_FOUND = 911;
	private static final int MISSING_STUDY_IDENTIFIER = 912;
	private static final int DUPLICATE_STUDYSUBJECT_IDENTIFIER = 913;
	private static final int RE_REGISTRATION = 924;
	private static final int INVALID_CONSENT_NAME = 914;
	private static final int INVALID_CONSENT_QUESTION = 915;
	private static final int INVALID_REASON = 916;
	
	private C3PRExceptionHelper exceptionHelper;
	private SubjectRegistryJAXBToDomainObjectConverter converter;
	private ParticipantDao participantDao;
	private StudyRepository studyRepository;
	private StudySubjectRepository studySubjectRepository;
	private StudySubjectDao studySubjectDao;
	private RegistryStatusDao registryStatusDao;
	
	public void importSubjectRegistry(
			Holder<ImportSubjectRegistryRequest> importSubjectRegistryRequestMessage)
			throws InsufficientPrivilegesExceptionFaultMessage,
			InvalidStudySubjectDataExceptionFaultMessage {
		// TODO Auto-generated method stub

	}

	public InitiateSubjectRegistryResponse initiateSubjectRegistry(
			InitiateSubjectRegistryRequest initiateSubjectRegistryRequestMessage)
			throws DuplicateStudySubjectExceptionFaultMessage,
			InsufficientPrivilegesExceptionFaultMessage,
			InvalidSiteExceptionFaultMessage,
			InvalidStudyProtocolExceptionFaultMessage,
			InvalidStudySubjectDataExceptionFaultMessage {
		StudySubject studySubject = initiateSubjectRegistryRequestMessage.getStudySubject();
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
		Participant participant = getParticipant((Person) studySubject.getEntity());
		
		//getStudySite
		StudySite studySite = getStudySite(studySubject.getStudySubjectProtocolVersion());
		
		//set study, studysite and subject demography
		domainObject.setStudySubjectDemographics(participant.createStudySubjectDemographics());
		domainObject.setStudySite(studySite);
		
		//copy organic attributes, identifiers and consent information
		copyEnrollmentDetails(domainObject , studySubject);
		
		//test re registration
		List<edu.duke.cabig.c3pr.domain.StudySubject> registrations = studySubjectRepository.findRegistrations(domainObject);;
		if (registrations.size() > 0) {
			handleDuplicateStudySubject(exceptionHelper
					.getConversionException(RE_REGISTRATION));
        }
		
		domainObject = studySubjectRepository.save(domainObject);
		
		InitiateSubjectRegistryResponse initiateSubjectRegistryResponse = new InitiateSubjectRegistryResponse();
		initiateSubjectRegistryResponse.setStudySubject(converter.convert(domainObject));
		return initiateSubjectRegistryResponse;
	}

	public QuerySubjectRegistryResponse querySubjectRegistry(
			QuerySubjectRegistryRequest parameters)
			throws InsufficientPrivilegesExceptionFaultMessage,
			InvalidQueryExceptionFaultMessage {
		
		QuerySubjectRegistryResponse response = new QuerySubjectRegistryResponse();
		DSETStudySubject studySubjects = new DSETStudySubject();
		response.setStudySubjects(studySubjects);

		try {
			List<AdvancedSearchCriteriaParameter> advParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
			for (AdvanceSearchCriterionParameter param : parameters.getSearchParameter().getItem()) {
				AdvancedSearchCriteriaParameter advParam = converter
						.convert(param);
				advParameters.add(advParam);
			}

			List<edu.duke.cabig.c3pr.domain.StudySubject> results = new ArrayList<edu.duke.cabig.c3pr.domain.StudySubject>(
					studySubjectDao.search(advParameters));
			for (edu.duke.cabig.c3pr.domain.StudySubject result : results) {
				studySubjects.getItem().add(converter.convert(result));
			}
		} catch (ConversionException e) {
			handleInvalidQueryData(e);
		}
		return response;
	}

	public RetrieveSubjectDemographyHistoryResponse retrieveSubjectDemographyHistory(
			RetrieveSubjectDemographyHistoryRequest retrieveSubjectDemographyHistoryRequestMessage)
			throws InsufficientPrivilegesExceptionFaultMessage,
			NoSuchPatientExceptionFaultMessage {
		RetrieveSubjectDemographyHistoryResponse response = new RetrieveSubjectDemographyHistoryResponse();
		DSETPerson patients = new DSETPerson();
		response.setPatients(patients);

		try {
			List<Participant> results = participantDao.getByIdentifiers(converter.
					convertBiologicIdentifiers(Arrays.asList(new BiologicEntityIdentifier[]{retrieveSubjectDemographyHistoryRequestMessage.getPatientIdentifier()})));
			if(results.size()!=1){
				handleNoSubjectFound(exceptionHelper
						.getConversionException(SUBJECT_NOT_FOUND));
			}
			for (StudySubjectDemographics p : results.get(0).getStudySubjectDemographics()) {
				patients.getItem().add(converter.convertSubjectDemographics(p));
			}
		} catch (ConversionException e) {
			handleNoSubjectFound(e);
		}
		return response;
	}

	public UpdateSubjectRegistryResponse updateSubjectRegistry(
			UpdateSubjectRegistryRequest updateSubjectRegistryRequestMessage)
			throws InsufficientPrivilegesExceptionFaultMessage,
			InvalidStudySubjectDataExceptionFaultMessage,
			NoSuchStudySubjectExceptionFaultMessage {
		//test duplicate study subject
		edu.duke.cabig.c3pr.domain.StudySubject domainObject = null;
		StudySubject studySubject = updateSubjectRegistryRequestMessage.getStudySubject();
		try {
			domainObject = getStudySubject(studySubject.getSubjectIdentifier());
		} catch (C3PRCodedRuntimeException e) {
			handleNoStudySubjectFound(e);
		}
		update(domainObject, studySubject);
		UpdateSubjectRegistryResponse updateSubjectRegistryResponse = new UpdateSubjectRegistryResponse();
		updateSubjectRegistryResponse.setStudySubject(converter.convert(domainObject));
		return updateSubjectRegistryResponse;
	}

	public UpdateSubjectRegistryStatusResponse updateSubjectRegistryStatus(
			UpdateSubjectRegistryStatusRequest updateSubjectRegistryStatusRequestMessage)
			throws InsufficientPrivilegesExceptionFaultMessage,
			InvalidStudySubjectDataExceptionFaultMessage,
			NoSuchStudySubjectExceptionFaultMessage {
		SubjectIdentifier subjectIdentifier = updateSubjectRegistryStatusRequestMessage.getStudySubjectIdentifier();
		//test duplicate study subject
		edu.duke.cabig.c3pr.domain.StudySubject domainObject = null;
		try {
			domainObject = getStudySubject(Arrays.asList(new SubjectIdentifier[]{subjectIdentifier}));
		} catch (C3PRCodedRuntimeException e) {
			handleNoStudySubjectFound(e);
		}
		
		StudySubjectRegistryStatus studySubjectRegistryStatus = getStudySubjectRegistryStatus(updateSubjectRegistryStatusRequestMessage.getStudySubjectStatus(), domainObject.getStudySite().getStudy());
		domainObject.updateRegistryStatus(studySubjectRegistryStatus.getPermissibleStudySubjectRegistryStatus().getRegistryStatus().getCode(), studySubjectRegistryStatus.getEffectiveDate(), studySubjectRegistryStatus.getReasons());
		
		domainObject = studySubjectRepository.save(domainObject);
		
		UpdateSubjectRegistryStatusResponse updateSubjectRegistryStatusResponse = new UpdateSubjectRegistryStatusResponse();
		updateSubjectRegistryStatusResponse.setStudySubject(converter.convert(domainObject));
		return updateSubjectRegistryStatusResponse;
	}

	public UpdateSubjectRegistryStatusHistoryResponse updateSubjectRegistryStatusHistory(
			UpdateSubjectRegistryStatusHistoryRequest parameters)
			throws InsufficientPrivilegesExceptionFaultMessage,
			InvalidStudySubjectDataExceptionFaultMessage,
			NoSuchStudySubjectExceptionFaultMessage {
		SubjectIdentifier subjectIdentifier = parameters.getStudySubjectIdentifier();
		//test duplicate study subject
		edu.duke.cabig.c3pr.domain.StudySubject domainObject = null;
		try {
			domainObject = getStudySubject(Arrays.asList(new SubjectIdentifier[]{subjectIdentifier}));
		} catch (C3PRCodedRuntimeException e) {
			handleNoStudySubjectFound(e);
		}
		domainObject.getStudySubjectRegistryStatusHistory().clear();
		for(PerformedStudySubjectMilestone status : parameters.getStudySubjectStatusHistory().getItem()){
			StudySubjectRegistryStatus studySubjectRegistryStatus = getStudySubjectRegistryStatus(status, domainObject.getStudySite().getStudy());
			domainObject.updateRegistryStatus(studySubjectRegistryStatus.getPermissibleStudySubjectRegistryStatus().getRegistryStatus().getCode(), studySubjectRegistryStatus.getEffectiveDate(), studySubjectRegistryStatus.getReasons());
		}
		domainObject = studySubjectRepository.save(domainObject);
		
		UpdateSubjectRegistryStatusHistoryResponse updateSubjectRegistryStatusHistoryResponse = new UpdateSubjectRegistryStatusHistoryResponse();
		updateSubjectRegistryStatusHistoryResponse.setStudySubject(converter.convert(domainObject));
		return updateSubjectRegistryStatusHistoryResponse;
	}
	
	public void setSubjectRegistryJAXBToDomainObjectConverter(
			SubjectRegistryJAXBToDomainObjectConverter subjectRegistryJAXBToDomainObjectConverter) {
		this.converter = subjectRegistryJAXBToDomainObjectConverter;
	}

	public void setParticipantDao(ParticipantDao participantDao) {
		this.participantDao = participantDao;
	}

	public void setExceptionHelper(C3PRExceptionHelper exceptionHelper) {
		this.exceptionHelper = exceptionHelper;
	}
	
	private Participant getParticipant(Person person) throws InvalidStudySubjectDataExceptionFaultMessage{
		List<BiologicEntityIdentifier> identifiers = person
		.getBiologicEntityIdentifier();
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
	
	private Study getStudy(StudySubjectProtocolVersionRelationship studySubjectProtocol)throws InvalidStudyProtocolExceptionFaultMessage{
		if(studySubjectProtocol == null ||
				studySubjectProtocol.getStudySiteProtocolVersion() == null ||
				studySubjectProtocol.getStudySiteProtocolVersion().getStudyProtocolVersion() == null ||
				studySubjectProtocol.getStudySiteProtocolVersion().getStudyProtocolVersion().getStudyProtocolDocument() == null ||
				studySubjectProtocol.getStudySiteProtocolVersion().getStudyProtocolVersion().getStudyProtocolDocument().getDocument() == null ||
				studySubjectProtocol.getStudySiteProtocolVersion().getStudyProtocolVersion().getStudyProtocolDocument().getDocument().getDocumentIdentifier() == null ||
				studySubjectProtocol.getStudySiteProtocolVersion().getStudyProtocolVersion().getStudyProtocolDocument().getDocument().getDocumentIdentifier().size() ==0){
			handleInvalidStudyData(exceptionHelper
					.getConversionException(MISSING_STUDY_IDENTIFIER));
		}
		List<DocumentIdentifier> documentIdentifiers = studySubjectProtocol.getStudySiteProtocolVersion().getStudyProtocolVersion().getStudyProtocolDocument().getDocument().getDocumentIdentifier();
		Study study = null;
		try {
			study = studyRepository.getUniqueStudy(converter.convertDocumentIdentifiers(documentIdentifiers));
		} catch (C3PRCodedRuntimeException e) {
			handleInvalidStudyData(e);
		}
		return study;
	}
	
	private StudySite getStudySite(StudySubjectProtocolVersionRelationship studySubjectProtocol)throws InvalidSiteExceptionFaultMessage, InvalidStudyProtocolExceptionFaultMessage{
		Study study = getStudy(studySubjectProtocol);
		if(studySubjectProtocol.getStudySiteProtocolVersion().getStudySite() == null ||
				studySubjectProtocol.getStudySiteProtocolVersion().getStudySite().getOrganization() == null){
			handleInvalidSiteData(exceptionHelper
					.getConversionException(MISSING_STUDY_IDENTIFIER));
		}
		StudySite studySite = null;
		try {
			studySite = study.getStudySite(converter.convertHealthcareSitePrimaryIdentifier(studySubjectProtocol.getStudySiteProtocolVersion().getStudySite().getOrganization()));
		} catch (C3PRCodedRuntimeException e) {
			handleInvalidSiteData(e);
		}
		return studySite;
	}
	
	private edu.duke.cabig.c3pr.domain.StudySubject getStudySubject(List<SubjectIdentifier> identifiers){
		edu.duke.cabig.c3pr.domain.StudySubject domainObject = new edu.duke.cabig.c3pr.domain.StudySubject();
		domainObject.getIdentifiers().addAll(converter.convertSubjectIdentifiers(identifiers));
		return studySubjectRepository.getUniqueStudySubject(domainObject.getPrimaryIdentifierObject());
	}
	
	private void copyEnrollmentDetails(edu.duke.cabig.c3pr.domain.StudySubject destination , StudySubject source) throws InvalidStudySubjectDataExceptionFaultMessage{
		//copy organic attributes
		CD paymentMethod = source.getPaymentMethodCode();
		destination.setPaymentMethod(!isNull(paymentMethod) ? paymentMethod.getCode() : null);
		CD status = source.getStatusCode();
		destination.setRegDataEntryStatus(!isNull(status) ? RegistrationDataEntryStatus.getByCode(status.getCode()) : null);
		
		//copy identifiers
		List<Identifier> identifiers = converter.convertSubjectIdentifiers(source.getSubjectIdentifier());
		destination.getIdentifiers().clear();
		destination.getIdentifiers().addAll(identifiers);
		
		//copy consent
		List<StudySubjectConsentVersion> studySubjectConsents = converter.convertSubjectConsent(source.getStudySubjectProtocolVersion());
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
	
	private void update(edu.duke.cabig.c3pr.domain.StudySubject destination , StudySubject source) throws InvalidStudySubjectDataExceptionFaultMessage{
		copyEnrollmentDetails(destination, source);
		
		//copy Subject Demographics
		converter.convertToSubjectDemographics(destination.getStudySubjectDemographics(), source);
	}
	
	private StudySubjectRegistryStatus getStudySubjectRegistryStatus(PerformedStudySubjectMilestone status, Study study) throws InvalidStudySubjectDataExceptionFaultMessage{
		StudySubjectRegistryStatus studySubjectRegistryStatus = converter.convertRegistryStatus(status);
		if(CollectionUtils.isNotEmpty(studySubjectRegistryStatus.getReasons())){
			String statusCode = studySubjectRegistryStatus.getPermissibleStudySubjectRegistryStatus().getRegistryStatus().getCode();
			RegistryStatus registryStatus = registryStatusDao.getRegistryStatusByCode(statusCode);
			PermissibleStudySubjectRegistryStatus permissibleStudySubjectRegistryStatus = study.getPermissibleStudySubjectRegistryStatus(statusCode);
			List<RegistryStatusReason> possibleReasons = new ArrayList<RegistryStatusReason>();
			possibleReasons.addAll(registryStatus.getPrimaryReasons());
			possibleReasons.addAll(permissibleStudySubjectRegistryStatus.getSecondaryReasons());
			List<RegistryStatusReason> actualReasons = new ArrayList<RegistryStatusReason>();
			actualReasons.addAll(studySubjectRegistryStatus.getReasons());
			studySubjectRegistryStatus.getReasons().clear();
			for(RegistryStatusReason actualReason : actualReasons){
				boolean found = false;
				for(RegistryStatusReason possibleReason : possibleReasons){
					if(actualReason.getCode().equalsIgnoreCase(possibleReason.getCode())){
						studySubjectRegistryStatus.getReasons().add(possibleReason);
						found = true;
						break;
					}
				}
				if(!found){
					handleInvalidStudySubjectData(exceptionHelper
							.getConversionException(INVALID_REASON, new Object[]{actualReason.getCode()}));
				}
			}
		}
		return studySubjectRegistryStatus;
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
	 * @throws handleNoStudySubjectFound
	 */
	private void handleNoStudySubjectFound(Exception e)
			throws NoSuchStudySubjectExceptionFaultMessage {
		log.error(ExceptionUtils.getFullStackTrace(e));
		NoSuchStudySubjectExceptionFault fault = new NoSuchStudySubjectExceptionFault();
		fault.setMessage(e.getMessage());
		throw new NoSuchStudySubjectExceptionFaultMessage(e.getMessage(), fault);
	}
	
	/**
	 * @param e
	 * @throws handleNoStudySubjectFound
	 */
	private void handleNoSubjectFound(Exception e)
			throws NoSuchPatientExceptionFaultMessage {
		log.error(ExceptionUtils.getFullStackTrace(e));
		NoSuchPatientExceptionFault fault = new NoSuchPatientExceptionFault();
		fault.setMessage(e.getMessage());
		throw new NoSuchPatientExceptionFaultMessage(e.getMessage(), fault);
	}
	
	private boolean isNull(ANY cd) {
		return cd == null || cd.getNullFlavor() != null;
	}

	public void setStudyRepository(StudyRepository studyRepository) {
		this.studyRepository = studyRepository;
	}

	public void setStudySubjectRepository(
			StudySubjectRepository studySubjectRepository) {
		this.studySubjectRepository = studySubjectRepository;
	}

	public void setStudySubjectDao(StudySubjectDao studySubjectDao) {
		this.studySubjectDao = studySubjectDao;
	}

	public void setRegistryStatusDao(RegistryStatusDao registryStatusDao) {
		this.registryStatusDao = registryStatusDao;
	}
}
