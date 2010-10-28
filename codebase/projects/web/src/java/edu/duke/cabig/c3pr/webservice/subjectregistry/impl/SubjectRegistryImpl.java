package edu.duke.cabig.c3pr.webservice.subjectregistry.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.jws.WebService;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import com.semanticbits.querybuilder.AdvancedSearchCriteriaParameter;
import com.semanticbits.querybuilder.AdvancedSearchHelper;

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
import edu.duke.cabig.c3pr.webservice.common.AdvanceSearchCriterionParameter;
import edu.duke.cabig.c3pr.webservice.common.BiologicEntityIdentifier;
import edu.duke.cabig.c3pr.webservice.common.DSETPerson;
import edu.duke.cabig.c3pr.webservice.common.DocumentIdentifier;
import edu.duke.cabig.c3pr.webservice.common.OrganizationIdentifier;
import edu.duke.cabig.c3pr.webservice.common.Subject;
import edu.duke.cabig.c3pr.webservice.common.SubjectIdentifier;
import edu.duke.cabig.c3pr.webservice.iso21090.ANY;
import edu.duke.cabig.c3pr.webservice.iso21090.CD;
import edu.duke.cabig.c3pr.webservice.subjectregistry.DSETPerformedStudySubjectMilestone;
import edu.duke.cabig.c3pr.webservice.subjectregistry.DSETStudySubject;
import edu.duke.cabig.c3pr.webservice.subjectregistry.DSETStudySubjectConsentVersion;
import edu.duke.cabig.c3pr.webservice.subjectregistry.DuplicateStudySubjectExceptionFault;
import edu.duke.cabig.c3pr.webservice.subjectregistry.DuplicateStudySubjectExceptionFaultMessage;
import edu.duke.cabig.c3pr.webservice.subjectregistry.ImportStudySubjectRegistryRequest;
import edu.duke.cabig.c3pr.webservice.subjectregistry.ImportStudySubjectRegistryResponse;
import edu.duke.cabig.c3pr.webservice.subjectregistry.InitiateStudySubjectRegistryRequest;
import edu.duke.cabig.c3pr.webservice.subjectregistry.InitiateStudySubjectRegistryResponse;
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
import edu.duke.cabig.c3pr.webservice.subjectregistry.QueryConsentsByStudySubjectRequest;
import edu.duke.cabig.c3pr.webservice.subjectregistry.QueryConsentsByStudySubjectResponse;
import edu.duke.cabig.c3pr.webservice.subjectregistry.QueryStudySubjectRegistryByConsentRequest;
import edu.duke.cabig.c3pr.webservice.subjectregistry.QueryStudySubjectRegistryByConsentResponse;
import edu.duke.cabig.c3pr.webservice.subjectregistry.QueryStudySubjectRegistryByStatusRequest;
import edu.duke.cabig.c3pr.webservice.subjectregistry.QueryStudySubjectRegistryByStatusResponse;
import edu.duke.cabig.c3pr.webservice.subjectregistry.QueryStudySubjectRegistryRequest;
import edu.duke.cabig.c3pr.webservice.subjectregistry.QueryStudySubjectRegistryResponse;
import edu.duke.cabig.c3pr.webservice.subjectregistry.QueryStudySubjectRegistryStatusHistoryRequest;
import edu.duke.cabig.c3pr.webservice.subjectregistry.QueryStudySubjectRegistryStatusHistoryResponse;
import edu.duke.cabig.c3pr.webservice.subjectregistry.RetrieveStudySubjectDemographyHistoryRequest;
import edu.duke.cabig.c3pr.webservice.subjectregistry.RetrieveStudySubjectDemographyHistoryResponse;
import edu.duke.cabig.c3pr.webservice.subjectregistry.SecurityExceptionFaultMessage;
import edu.duke.cabig.c3pr.webservice.subjectregistry.StudySubject;
import edu.duke.cabig.c3pr.webservice.subjectregistry.SubjectRegistry;
import edu.duke.cabig.c3pr.webservice.subjectregistry.UpdateStudySubjectConsentRequest;
import edu.duke.cabig.c3pr.webservice.subjectregistry.UpdateStudySubjectConsentResponse;
import edu.duke.cabig.c3pr.webservice.subjectregistry.UpdateStudySubjectRegistryDemographyRequest;
import edu.duke.cabig.c3pr.webservice.subjectregistry.UpdateStudySubjectRegistryDemographyResponse;
import edu.duke.cabig.c3pr.webservice.subjectregistry.UpdateStudySubjectRegistryRequest;
import edu.duke.cabig.c3pr.webservice.subjectregistry.UpdateStudySubjectRegistryResponse;
import edu.duke.cabig.c3pr.webservice.subjectregistry.UpdateStudySubjectRegistryStatusHistoryRequest;
import edu.duke.cabig.c3pr.webservice.subjectregistry.UpdateStudySubjectRegistryStatusHistoryResponse;
import edu.duke.cabig.c3pr.webservice.subjectregistry.UpdateStudySubjectRegistryStatusRequest;
import edu.duke.cabig.c3pr.webservice.subjectregistry.UpdateStudySubjectRegistryStatusResponse;
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
	
	public ImportStudySubjectRegistryResponse importSubjectRegistry(
			ImportStudySubjectRegistryRequest parameters)
			throws InvalidStudySubjectDataExceptionFaultMessage,
			SecurityExceptionFaultMessage {
		ImportStudySubjectRegistryResponse response = new ImportStudySubjectRegistryResponse();
		DSETStudySubject dsetStudySubject = new DSETStudySubject();
		response.setStudySubjects(dsetStudySubject);
		for(StudySubject studySubject : parameters.getStudySubjects().getItem()){
			//test duplicate study subject
			try {
				getStudySubject(studySubject.getSubjectIdentifier());
				log.error(ExceptionUtils.getFullStackTrace(exceptionHelper
						.getConversionException(DUPLICATE_STUDYSUBJECT_IDENTIFIER)));
				continue;
			} catch (C3PRCodedRuntimeException e) {
				if(e.getExceptionCode() != 231){
					log.error(e);
					continue;
				}
			}
			
			edu.duke.cabig.c3pr.domain.StudySubject domainObject = new edu.duke.cabig.c3pr.domain.StudySubject();
			
			//getParticipant
			if(studySubject.getEntity() == null ||
					studySubject.getEntity().getBiologicEntityIdentifier().size() == 0){
				log.error("Invalid study subject record: BiologicEntity is invalid.");
				continue;
			}
			Participant participant = getParticipant(studySubject.getEntity().getBiologicEntityIdentifier().get(0));
			
			//getStudySite
			if(studySubject.getStudySubjectProtocolVersion() == null ||
					studySubject.getStudySubjectProtocolVersion().getStudySiteProtocolVersion() == null ||
					studySubject.getStudySubjectProtocolVersion().getStudySiteProtocolVersion().getStudyProtocolVersion().getStudyProtocolDocument().getDocument().getDocumentIdentifier().size() == 0){
				log.error("Invalid study subject record: Cannot find study information.");
				continue;
			}
			Study study = null;
			try {
				study = getStudy(studySubject.getStudySubjectProtocolVersion().getStudySiteProtocolVersion().
						getStudyProtocolVersion().getStudyProtocolDocument().getDocument().getDocumentIdentifier().get(0));
			} catch (InvalidStudyProtocolExceptionFaultMessage e) {
				log.error(ExceptionUtils.getFullStackTrace(e));
				continue;
			}
			
			//test duplicate study subject
			if(studySubjectDao.searchBySubjectAndStudyIdentifiers(participant.getPrimaryIdentifier(), study.getCoordinatingCenterAssignedIdentifier()).size()>0){
				log.error(ExceptionUtils.getFullStackTrace(exceptionHelper
						.getConversionException(DUPLICATE_STUDYSUBJECT_IDENTIFIER)));
				continue;
			}
			
			//getStudySite
			StudySite studySite = null;
			try {
				studySite = getStudySite(study, studySubject.getStudySubjectProtocolVersion().getStudySiteProtocolVersion().getStudySite().getOrganization().getOrganizationIdentifier().get(0));
			} catch (InvalidSiteExceptionFaultMessage e) {
				log.error(ExceptionUtils.getFullStackTrace(e));
				continue;
			} catch (InvalidStudyProtocolExceptionFaultMessage e) {
				log.error(ExceptionUtils.getFullStackTrace(e));
				continue;
			}
			
			//set study, studysite and subject demography
			domainObject.setStudySubjectDemographics(participant.createStudySubjectDemographics());
			domainObject.setStudySite(studySite);
			
			//copy organic attributes, identifiers
			copyEnrollmentDetails(domainObject , studySubject);
			
			//copy consent information
			if(studySubject.getStudySubjectProtocolVersion() != null &&
					studySubject.getStudySubjectProtocolVersion().getStudySubjectConsentVersion() !=null &&
					studySubject.getStudySubjectProtocolVersion().getStudySubjectConsentVersion().size()>0)
			copyConsentDetails(domainObject, studySubject.getStudySubjectProtocolVersion().getStudySubjectConsentVersion());
			
			//copy registry statuses
			for(PerformedStudySubjectMilestone status : studySubject.getStudySubjectStatus()){
				StudySubjectRegistryStatus studySubjectRegistryStatus = getStudySubjectRegistryStatus(status, domainObject.getStudySite().getStudy());
				domainObject.updateRegistryStatus(studySubjectRegistryStatus.getPermissibleStudySubjectRegistryStatus().getRegistryStatus().getCode(), studySubjectRegistryStatus.getEffectiveDate(), studySubjectRegistryStatus.getReasons());
			}
			
			//test re-registration
			List<edu.duke.cabig.c3pr.domain.StudySubject> registrations = studySubjectRepository.findRegistrations(domainObject);;
			if (registrations.size() > 0) {
				log.error(ExceptionUtils.getFullStackTrace(exceptionHelper
						.getConversionException(RE_REGISTRATION)));
				continue;
	        }
			
			domainObject = studySubjectRepository.save(domainObject);
			dsetStudySubject.getItem().add(converter.convert(domainObject));
		}
		return response;
	}
	
	public InitiateStudySubjectRegistryResponse initiateStudySubject(
			InitiateStudySubjectRegistryRequest parameters)
			throws DuplicateStudySubjectExceptionFaultMessage,
			InvalidSiteExceptionFaultMessage,
			InvalidStudyProtocolExceptionFaultMessage,
			InvalidStudySubjectDataExceptionFaultMessage,
			SecurityExceptionFaultMessage {
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
		
		//copy organic attributes, identifiers
		copyEnrollmentDetails(domainObject , studySubject);
		
		//copy consent information
		if(studySubject.getStudySubjectProtocolVersion() != null &&
				studySubject.getStudySubjectProtocolVersion().getStudySubjectConsentVersion() !=null &&
				studySubject.getStudySubjectProtocolVersion().getStudySubjectConsentVersion().size()>0)
		copyConsentDetails(domainObject, studySubject.getStudySubjectProtocolVersion().getStudySubjectConsentVersion());
		
		//test re-registration
		List<edu.duke.cabig.c3pr.domain.StudySubject> registrations = studySubjectRepository.findRegistrations(domainObject);;
		if (registrations.size() > 0) {
			handleDuplicateStudySubject(exceptionHelper
					.getConversionException(RE_REGISTRATION));
        }
		
		domainObject = studySubjectRepository.save(domainObject);
		
		InitiateStudySubjectRegistryResponse response = new InitiateStudySubjectRegistryResponse();
		response.setStudySubject(converter.convert(domainObject));
		return response;
	}

	public QueryConsentsByStudySubjectResponse queryConsentsByStudySubject(
			QueryConsentsByStudySubjectRequest parameters)
			throws InvalidQueryExceptionFaultMessage,
			SecurityExceptionFaultMessage, NoSuchStudySubjectExceptionFaultMessage {
		edu.duke.cabig.c3pr.domain.StudySubject domainObject = null;
		try {
			domainObject = getStudySubject(Arrays.asList(new SubjectIdentifier[]{parameters.getStudySubjectIdentifier()}));
		} catch (C3PRCodedRuntimeException e) {
			handleNoStudySubjectFound(e);
		}
		QueryConsentsByStudySubjectResponse queryConsentsByStudySubjectResponse = new QueryConsentsByStudySubjectResponse();
		DSETStudySubjectConsentVersion dsetStudySubjectConsentVersion = new DSETStudySubjectConsentVersion();
		queryConsentsByStudySubjectResponse.setStudySubjectConsents(dsetStudySubjectConsentVersion);
		dsetStudySubjectConsentVersion.getItem().addAll(converter.convertToSubjectConsent(domainObject.getAllConsents()));
		if(parameters.getConsent() != null){
			for(int i=0 ; i<dsetStudySubjectConsentVersion.getItem().size() ; i++){
				edu.duke.cabig.c3pr.webservice.common.Consent convertedConsent = (edu.duke.cabig.c3pr.webservice.common.Consent)dsetStudySubjectConsentVersion.getItem().get(i).getConsent();
				if(parameters.getConsent().getOfficialTitle() != null &&
						!StringUtils.isBlank(parameters.getConsent().getOfficialTitle().getValue())){
					if(!convertedConsent.getOfficialTitle().getValue().equals(parameters.getConsent().getOfficialTitle().getValue())){
						dsetStudySubjectConsentVersion.getItem().remove(i--);
						continue;
					}
				}
				if(parameters.getConsent().getVersionNumberText() != null &&
						!StringUtils.isBlank(parameters.getConsent().getVersionNumberText().getValue())){
					if(!convertedConsent.getVersionNumberText().getValue().equals(parameters.getConsent().getVersionNumberText().getValue())){
						dsetStudySubjectConsentVersion.getItem().remove(i--);
					}
				}
			}
		}
		return queryConsentsByStudySubjectResponse;
	}

	public QueryStudySubjectRegistryResponse querySubjectRegistry(
			QueryStudySubjectRegistryRequest parameters)
			throws InvalidQueryExceptionFaultMessage,
			SecurityExceptionFaultMessage {
		QueryStudySubjectRegistryResponse response = new QueryStudySubjectRegistryResponse();
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

	public QueryStudySubjectRegistryByConsentResponse querySubjectRegistryByConsent(
			QueryStudySubjectRegistryByConsentRequest parameters)
			throws InvalidQueryExceptionFaultMessage,
			SecurityExceptionFaultMessage {
		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter = null; 
		if(parameters.getStudyIdentifier() != null &&
				parameters.getStudyIdentifier().getIdentifier() != null &&
				!StringUtils.isBlank(parameters.getStudyIdentifier().getIdentifier().getExtension())){
			advancedSearchCriteriaParameter = AdvancedSearchHelper
			.buildAdvancedSearchCriteriaParameter("edu.duke.cabig.c3pr.domain.Identifier", "Study",
					"value", parameters.getStudyIdentifier().getIdentifier().getExtension(), "=");
			criteriaParameters.add(advancedSearchCriteriaParameter);
		}
		
		boolean consentParamFound = false;
		if(parameters.getConsent() == null){
			InvalidQueryExceptionFault fault = new InvalidQueryExceptionFault();
			String message = "No consent parameter provided";
			fault.setMessage(message);
			throw new InvalidQueryExceptionFaultMessage("message", fault);
		}
		if(parameters.getConsent().getOfficialTitle() != null &&
				!StringUtils.isBlank(parameters.getConsent().getOfficialTitle().getValue())){
			advancedSearchCriteriaParameter = AdvancedSearchHelper
			.buildAdvancedSearchCriteriaParameter("edu.duke.cabig.c3pr.domain.Consent",
					"name", parameters.getConsent().getOfficialTitle().getValue(), "=");
			criteriaParameters.add(advancedSearchCriteriaParameter);
			consentParamFound = true;
		}
		if(parameters.getConsent().getVersionNumberText() != null &&
				!StringUtils.isBlank(parameters.getConsent().getVersionNumberText().getValue())){
			advancedSearchCriteriaParameter = AdvancedSearchHelper
			.buildAdvancedSearchCriteriaParameter("edu.duke.cabig.c3pr.domain.Consent",
					"versionId", parameters.getConsent().getVersionNumberText().getValue(), "=");
			criteriaParameters.add(advancedSearchCriteriaParameter);
			consentParamFound = true;
		}
		if(!consentParamFound){
			InvalidQueryExceptionFault fault = new InvalidQueryExceptionFault();
			String message = "No consent parameter provided";
			fault.setMessage(message);
			throw new InvalidQueryExceptionFaultMessage("message", fault);
		}
		List<edu.duke.cabig.c3pr.domain.StudySubject> results = studySubjectDao.search(criteriaParameters);
		
		QueryStudySubjectRegistryByConsentResponse response = new QueryStudySubjectRegistryByConsentResponse();
		response.setStudySubjects(new DSETStudySubject());
		for (edu.duke.cabig.c3pr.domain.StudySubject result : results) {
			response.getStudySubjects().getItem().add(converter.convert(result));
		}
		return response;
	}

	public QueryStudySubjectRegistryByStatusResponse querySubjectRegistryByRegistryStatus(
			QueryStudySubjectRegistryByStatusRequest parameters)
			throws InvalidQueryExceptionFaultMessage,
			SecurityExceptionFaultMessage {
		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter = null; 
		if(parameters.getStudyIdentifier() != null &&
				parameters.getStudyIdentifier().getIdentifier() != null &&
				!StringUtils.isBlank(parameters.getStudyIdentifier().getIdentifier().getExtension())){
			advancedSearchCriteriaParameter = AdvancedSearchHelper
			.buildAdvancedSearchCriteriaParameter("edu.duke.cabig.c3pr.domain.Identifier", "Study",
					"value", parameters.getStudyIdentifier().getIdentifier().getExtension(), "=");
			criteriaParameters.add(advancedSearchCriteriaParameter);
		}
		
		boolean registryStatusFound = false;
		if(parameters.getRegistryStatus() == null){
			InvalidQueryExceptionFault fault = new InvalidQueryExceptionFault();
			String message = "No registry status parameter provided";
			fault.setMessage(message);
			throw new InvalidQueryExceptionFaultMessage("message", fault);
		}
		if(parameters.getRegistryStatus().getStatusCode() != null &&
				!StringUtils.isBlank(parameters.getRegistryStatus().getStatusCode().getCode())){
			advancedSearchCriteriaParameter = AdvancedSearchHelper
			.buildAdvancedSearchCriteriaParameter("edu.duke.cabig.c3pr.domain.RegistryStatus",
					"code", parameters.getRegistryStatus().getStatusCode().getCode(), "=");
			criteriaParameters.add(advancedSearchCriteriaParameter);
			registryStatusFound = true;
		}
		if(parameters.getRegistryStatus().getReasonCode() != null &&
				parameters.getRegistryStatus().getReasonCode().getItem() != null &&
				parameters.getRegistryStatus().getReasonCode().getItem().size()>0){
			List<String> values = new ArrayList<String>();
			for(CD reason : parameters.getRegistryStatus().getReasonCode().getItem()){
				if(!StringUtils.isBlank(reason.getCode())){
					values.add(reason.getCode());
				}
			}
			if(values.size()>0){
				advancedSearchCriteriaParameter = AdvancedSearchHelper
				.buildAdvancedSearchCriteriaParameter("edu.duke.cabig.c3pr.domain.RegistryStatusReason",
						"code", values, "in");
				criteriaParameters.add(advancedSearchCriteriaParameter);
				registryStatusFound = true;				
			}
		}
		if(parameters.getRegistryStatus().getStatusDate() != null){
			advancedSearchCriteriaParameter = AdvancedSearchHelper
			.buildAdvancedSearchCriteriaParameter("edu.duke.cabig.c3pr.domain.StudySubjectRegistryStatus",
					"effectiveDate", new SimpleDateFormat("MM/dd/yyyy").format(converter.convertToDate(parameters.getRegistryStatus().getStatusDate())), "=");
			criteriaParameters.add(advancedSearchCriteriaParameter);
			registryStatusFound = true;
		}
		if(!registryStatusFound){
			InvalidQueryExceptionFault fault = new InvalidQueryExceptionFault();
			String message = "No registry status parameter provided";
			fault.setMessage(message);
			throw new InvalidQueryExceptionFaultMessage("message", fault);
		}
		List<edu.duke.cabig.c3pr.domain.StudySubject> results = studySubjectDao.search(criteriaParameters);
		
		QueryStudySubjectRegistryByStatusResponse response = new QueryStudySubjectRegistryByStatusResponse();
		response.setStudySubjects(new DSETStudySubject());
		for (edu.duke.cabig.c3pr.domain.StudySubject result : results) {
			response.getStudySubjects().getItem().add(converter.convert(result));
		}
		return response;
	}

	public RetrieveStudySubjectDemographyHistoryResponse retrieveStudySubjectDemographyHistory(
			RetrieveStudySubjectDemographyHistoryRequest parameters)
			throws NoSuchPatientExceptionFaultMessage,
			SecurityExceptionFaultMessage {
		RetrieveStudySubjectDemographyHistoryResponse response = new RetrieveStudySubjectDemographyHistoryResponse();
		DSETPerson patients = new DSETPerson();
		response.setPatients(patients);

		try {
			List<Participant> results = participantDao.getByIdentifiers(converter.
					convertBiologicIdentifiers(Arrays.asList(new BiologicEntityIdentifier[]{parameters.getPatientIdentifier()})));
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

	public UpdateStudySubjectRegistryResponse updateStudySubject(
			UpdateStudySubjectRegistryRequest parameters)
			throws InvalidStudySubjectDataExceptionFaultMessage,
			NoSuchStudySubjectExceptionFaultMessage,
			SecurityExceptionFaultMessage {
		//test invalid study subject
		edu.duke.cabig.c3pr.domain.StudySubject domainObject = null;
		try {
			domainObject = getStudySubject(Arrays.asList(new SubjectIdentifier[]{parameters.getStudySubjectIdentifier()}));
		} catch (C3PRCodedRuntimeException e) {
			handleNoStudySubjectFound(e);
		}
		
		//copy organic attributes like payment method and identifiers
		copyEnrollmentDetails(domainObject, parameters.getStudySubject());
		
		studySubjectDao.save(domainObject);
		
		UpdateStudySubjectRegistryResponse response = new UpdateStudySubjectRegistryResponse();
		response.setStudySubject(converter.convert(domainObject));
		return response;
	}

	public UpdateStudySubjectRegistryDemographyResponse updateStudySubjectDemography(
			UpdateStudySubjectRegistryDemographyRequest parameters)
			throws InvalidStudySubjectDataExceptionFaultMessage,
			NoSuchStudySubjectExceptionFaultMessage,
			SecurityExceptionFaultMessage {
		//test invalid study subject
		edu.duke.cabig.c3pr.domain.StudySubject domainObject = null;
		try {
			domainObject = getStudySubject(Arrays.asList(new SubjectIdentifier[]{parameters.getStudySubjectIdentifier()}));
		} catch (C3PRCodedRuntimeException e) {
			handleNoStudySubjectFound(e);
		}
		
		//copy organic attributes like payment method and identifiers
		Subject subject = new Subject();
		subject.setEntity(parameters.getPerson());
		converter.convertToSubjectDemographics(domainObject.getStudySubjectDemographics(), subject);
		
		studySubjectDao.save(domainObject);
		
		UpdateStudySubjectRegistryDemographyResponse response = new UpdateStudySubjectRegistryDemographyResponse();
		response.setStudySubject(converter.convert(domainObject));
		return response;
	}

	public QueryStudySubjectRegistryStatusHistoryResponse queryStudySubjectRegistryStatusHistory(
			QueryStudySubjectRegistryStatusHistoryRequest parameters)
			throws InvalidQueryExceptionFaultMessage,
			SecurityExceptionFaultMessage, NoSuchStudySubjectExceptionFaultMessage {
		edu.duke.cabig.c3pr.domain.StudySubject domainObject = null;
		try {
			domainObject = getStudySubject(Arrays.asList(new SubjectIdentifier[]{parameters.getStudySubjectIdentifier()}));
		} catch (C3PRCodedRuntimeException e) {
			handleNoStudySubjectFound(e);
		}
		
		DSETPerformedStudySubjectMilestone dsetPerformedStudySubjectMilestone = new DSETPerformedStudySubjectMilestone();
		dsetPerformedStudySubjectMilestone.getItem().addAll(converter.convertToRegistryStatus(domainObject.getStudySubjectRegistryStatusHistory()));
		QueryStudySubjectRegistryStatusHistoryResponse response = new QueryStudySubjectRegistryStatusHistoryResponse();
		response.setStudySubjectRegistryStatusHistory(dsetPerformedStudySubjectMilestone);
		return response;
	}

	public UpdateStudySubjectConsentResponse updateStudySubjectConsent(
			UpdateStudySubjectConsentRequest parameters)
			throws InvalidStudySubjectDataExceptionFaultMessage,
			NoSuchStudySubjectExceptionFaultMessage,
			SecurityExceptionFaultMessage {
		edu.duke.cabig.c3pr.domain.StudySubject domainObject = null;
		try {
			domainObject = getStudySubject(Arrays.asList(new SubjectIdentifier[]{parameters.getStudySubjectIdentifier()}));
		} catch (C3PRCodedRuntimeException e) {
			handleNoStudySubjectFound(e);
		}
		
		copyConsentDetails(domainObject, parameters.getStudySubjectConsentVersions().getItem());
		
		studySubjectDao.save(domainObject);
		
		UpdateStudySubjectConsentResponse response = new UpdateStudySubjectConsentResponse();
		response.setStudySubject(converter.convert(domainObject));
		return response;
	}

	public UpdateStudySubjectRegistryStatusResponse updateStudySubjectRegistryStatus(
			UpdateStudySubjectRegistryStatusRequest parameters)
			throws InvalidStudySubjectDataExceptionFaultMessage,
			NoSuchStudySubjectExceptionFaultMessage,
			SecurityExceptionFaultMessage {
		SubjectIdentifier subjectIdentifier = parameters.getStudySubjectIdentifier();
		//test duplicate study subject
		edu.duke.cabig.c3pr.domain.StudySubject domainObject = null;
		try {
			domainObject = getStudySubject(Arrays.asList(new SubjectIdentifier[]{subjectIdentifier}));
		} catch (C3PRCodedRuntimeException e) {
			handleNoStudySubjectFound(e);
		}
		
		StudySubjectRegistryStatus studySubjectRegistryStatus = getStudySubjectRegistryStatus(parameters.getStudySubjectStatus(), domainObject.getStudySite().getStudy());
		domainObject.updateRegistryStatus(studySubjectRegistryStatus.getPermissibleStudySubjectRegistryStatus().getRegistryStatus().getCode(), studySubjectRegistryStatus.getEffectiveDate(), studySubjectRegistryStatus.getReasons());
		
		studySubjectDao.save(domainObject);
		
		UpdateStudySubjectRegistryStatusResponse response = new UpdateStudySubjectRegistryStatusResponse();
		response.setStudySubject(converter.convert(domainObject));
		return response;
	}

	public UpdateStudySubjectRegistryStatusHistoryResponse updateStudySubjectRegistryStatusHistory(
			UpdateStudySubjectRegistryStatusHistoryRequest parameters)
			throws InvalidStudySubjectDataExceptionFaultMessage,
			NoSuchStudySubjectExceptionFaultMessage,
			SecurityExceptionFaultMessage {
		SubjectIdentifier subjectIdentifier = parameters.getStudySubjectIdentifier();
		//test duplicate study subject
		edu.duke.cabig.c3pr.domain.StudySubject domainObject = null;
		try {
			domainObject = getStudySubject(Arrays.asList(new SubjectIdentifier[]{subjectIdentifier}));
		} catch (C3PRCodedRuntimeException e) {
			handleNoStudySubjectFound(e);
		}
		domainObject.getStudySubjectRegistryStatusHistoryInternal().clear();
		for(PerformedStudySubjectMilestone status : parameters.getStudySubjectRegistryStatusHistory().getItem()){
			StudySubjectRegistryStatus studySubjectRegistryStatus = getStudySubjectRegistryStatus(status, domainObject.getStudySite().getStudy());
			domainObject.updateRegistryStatus(studySubjectRegistryStatus.getPermissibleStudySubjectRegistryStatus().getRegistryStatus().getCode(), studySubjectRegistryStatus.getEffectiveDate(), studySubjectRegistryStatus.getReasons());
		}
		studySubjectDao.save(domainObject);
		
		UpdateStudySubjectRegistryStatusHistoryResponse response = new UpdateStudySubjectRegistryStatusHistoryResponse();
		response.setStudySubject(converter.convert(domainObject));
		return response;
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
			study = studyRepository.getUniqueStudy(converter.convertDocumentIdentifiers(Arrays.asList(new DocumentIdentifier[]{docId})));
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
	}
	
	private void copyConsentDetails(edu.duke.cabig.c3pr.domain.StudySubject destination , List<edu.duke.cabig.c3pr.webservice.subjectregistry.StudySubjectConsentVersion> subjectConsents) throws InvalidStudySubjectDataExceptionFaultMessage{
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
