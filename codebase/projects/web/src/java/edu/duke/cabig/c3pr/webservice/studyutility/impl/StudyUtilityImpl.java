/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
 ******************************************************************************/
/**
 * 
 */
package edu.duke.cabig.c3pr.webservice.studyutility.impl;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebService;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.semanticbits.querybuilder.AdvancedSearchCriteriaParameter;

import edu.duke.cabig.c3pr.dao.ConsentDao;
import edu.duke.cabig.c3pr.dao.RegistryStatusDao;
import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.RegistryStatus;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.repository.StudyRepository;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;
import edu.duke.cabig.c3pr.exception.C3PRDuplicatePrimaryStudyIdentifierException;
import edu.duke.cabig.c3pr.utils.StringUtils;
import edu.duke.cabig.c3pr.webservice.common.AdvanceSearchCriterionParameter;
import edu.duke.cabig.c3pr.webservice.common.Consent;
import edu.duke.cabig.c3pr.webservice.common.DocumentIdentifier;
import edu.duke.cabig.c3pr.webservice.common.PermissibleStudySubjectRegistryStatus;
import edu.duke.cabig.c3pr.webservice.common.RegistryStatusReason;
import edu.duke.cabig.c3pr.webservice.common.StudyProtocolVersion;
import edu.duke.cabig.c3pr.webservice.converters.JAXBToDomainObjectConverter;
import edu.duke.cabig.c3pr.webservice.iso21090.CD;
import edu.duke.cabig.c3pr.webservice.iso21090.UpdateMode;
import edu.duke.cabig.c3pr.webservice.studyutility.CreateStudyAbstractRequest;
import edu.duke.cabig.c3pr.webservice.studyutility.CreateStudyAbstractResponse;
import edu.duke.cabig.c3pr.webservice.studyutility.DSETConsent;
import edu.duke.cabig.c3pr.webservice.studyutility.DSETPermissibleStudySubjectRegistryStatus;
import edu.duke.cabig.c3pr.webservice.studyutility.DSETRegistryStatus;
import edu.duke.cabig.c3pr.webservice.studyutility.DSETStudyProtocolVersion;
import edu.duke.cabig.c3pr.webservice.studyutility.QueryRegistryStatusRequest;
import edu.duke.cabig.c3pr.webservice.studyutility.QueryRegistryStatusResponse;
import edu.duke.cabig.c3pr.webservice.studyutility.QueryStudyAbstractRequest;
import edu.duke.cabig.c3pr.webservice.studyutility.QueryStudyAbstractResponse;
import edu.duke.cabig.c3pr.webservice.studyutility.QueryStudyConsentRequest;
import edu.duke.cabig.c3pr.webservice.studyutility.QueryStudyConsentResponse;
import edu.duke.cabig.c3pr.webservice.studyutility.QueryStudyRegistryStatusRequest;
import edu.duke.cabig.c3pr.webservice.studyutility.QueryStudyRegistryStatusResponse;
import edu.duke.cabig.c3pr.webservice.studyutility.SecurityExceptionFaultMessage;
import edu.duke.cabig.c3pr.webservice.studyutility.StudyUtility;
import edu.duke.cabig.c3pr.webservice.studyutility.StudyUtilityFault;
import edu.duke.cabig.c3pr.webservice.studyutility.StudyUtilityFaultMessage;
import edu.duke.cabig.c3pr.webservice.studyutility.UpdateStudyAbstractRequest;
import edu.duke.cabig.c3pr.webservice.studyutility.UpdateStudyAbstractResponse;
import edu.duke.cabig.c3pr.webservice.studyutility.UpdateStudyConsentQuestionRequest;
import edu.duke.cabig.c3pr.webservice.studyutility.UpdateStudyConsentQuestionResponse;
import edu.duke.cabig.c3pr.webservice.studyutility.UpdateStudyConsentRequest;
import edu.duke.cabig.c3pr.webservice.studyutility.UpdateStudyConsentResponse;
import edu.duke.cabig.c3pr.webservice.studyutility.UpdateStudyStatusRequest;
import edu.duke.cabig.c3pr.webservice.studyutility.UpdateStudyStatusResponse;

/**
 * The Class StudyUtilityImpl.
 *
 * @author dkrylov
 */
@WebService(endpointInterface = "edu.duke.cabig.c3pr.webservice.studyutility.StudyUtility", wsdlLocation = "/WEB-INF/wsdl/StudyUtility.wsdl", targetNamespace = "http://enterpriseservices.nci.nih.gov/StudyUtilityService", portName = "StudyUtility", serviceName = "StudyUtilityService")
public class StudyUtilityImpl implements StudyUtility {

	/** The Constant STUDY_IDENTIFIER_REQUIRED. */
	private static final String STUDY_IDENTIFIER_REQUIRED = "Study identifier is required.";

	/** The Constant STUDY_ALREADY_EXISTS. */
	private static final String STUDY_ALREADY_EXISTS = "A study with the given identifier(s) already exists.";

	/** The Constant STUDY_DOES_NOT_EXIST. */
	private static final String STUDY_DOES_NOT_EXIST = "A study with the given identifier(s) does not exist.";

	/** The Constant MORE_THAN_ONE_STUDY. */
	private static final String MORE_THAN_ONE_STUDY = "More than one study with the given identifier found.";
	
	
	/** The Constant CONSENT_DOES_NOT_EXIST. */
	private static final String CONSENT_DOES_NOT_EXIST = "A consent with the given name, version and study identifier does not exist.";

	/** The log. */
	private static Log log = LogFactory.getLog(StudyUtilityImpl.class);

	/** The converter. */
	private JAXBToDomainObjectConverter converter;

	/** The study repository. */
	private StudyRepository studyRepository;

	/** The consent dao. */
	private ConsentDao consentDao;

	/** The registry status dao. */
	private RegistryStatusDao registryStatusDao;

	/**
	 * Instantiates a new study utility impl.
	 */
	public StudyUtilityImpl() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.duke.cabig.c3pr.webservice.studyutility.StudyUtility#advancedQueryStudy
	 * (edu.duke.cabig.c3pr.webservice.studyutility.AdvancedQueryStudyRequest)
	 */
	public QueryStudyAbstractResponse queryStudyAbstract(
			QueryStudyAbstractRequest parameters)
			throws StudyUtilityFaultMessage {

		QueryStudyAbstractResponse response = new QueryStudyAbstractResponse();
		DSETStudyProtocolVersion studies = new DSETStudyProtocolVersion();
		response.setStudies(studies);
		try {
			List<AdvancedSearchCriteriaParameter> advParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
			for (AdvanceSearchCriterionParameter param : parameters
					.getParameters().getItem()) {
				AdvancedSearchCriteriaParameter advParam = converter
						.convert(param);
				advParameters.add(advParam);
			}

			List<edu.duke.cabig.c3pr.domain.Study> results = new ArrayList<edu.duke.cabig.c3pr.domain.Study>(
					studyRepository.search(advParameters));
			for (edu.duke.cabig.c3pr.domain.Study s : results) {
				studies.getItem().add(converter.convert(s));
			}
		} catch (RuntimeException e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			fail(e.getMessage());
		}
		return response;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.duke.cabig.c3pr.webservice.studyutility.StudyUtility#createStudy(
	 * edu.duke.cabig.c3pr.webservice.studyutility.CreateStudyRequest)
	 */
	public CreateStudyAbstractResponse createStudyAbstract(CreateStudyAbstractRequest request)
			throws StudyUtilityFaultMessage {

		CreateStudyAbstractResponse response = new CreateStudyAbstractResponse();
		try {
			StudyProtocolVersion xmlStudy = request.getStudy();
			for(PermissibleStudySubjectRegistryStatus permissibleRegStatus : xmlStudy.getPermissibleStudySubjectRegistryStatus()){
				validatePermissibleStudySubjectRegistryStatus(permissibleRegStatus);
			}
			
			edu.duke.cabig.c3pr.domain.Study study = converter
					.convert(xmlStudy);
			if (CollectionUtils.isEmpty(study.getIdentifiers())) {
				fail(STUDY_IDENTIFIER_REQUIRED);
			}
			
			// Only the primary identifiers across studies have to be unique. This is a requirement for MAYO services. Typically the secondary
			// identifiers of studies attached to a protocol will be common. This is for grouping purposes based on the protocol. 
			
			edu.duke.cabig.c3pr.domain.Study existingStudy = null;
			try {
				existingStudy = studyRepository.getByPrimaryIdentifier(study.getPrimaryIdentifierObject());
			} catch (C3PRDuplicatePrimaryStudyIdentifierException e) {
				fail(MORE_THAN_ONE_STUDY);
			}
			if (existingStudy != null) {
				fail(STUDY_ALREADY_EXISTS);
			}
			studyRepository.save(study);
			response.setStudy(converter.convert(study));
		} catch (RuntimeException e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			fail(e.getMessage());
		} catch (C3PRCodedException e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			fail(e.getMessage());
		}
		return response;

	}

	/**
	 * Fail.
	 *
	 * @param message the message
	 * @throws StudyUtilityFaultMessage the study utility fault message
	 */
	private void fail(String message) throws StudyUtilityFaultMessage {
		final StudyUtilityFault faultInfo = new StudyUtilityFault();
		faultInfo.setMessage(message);
		throw new StudyUtilityFaultMessage(message, faultInfo);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.duke.cabig.c3pr.webservice.studyutility.StudyUtility#updateStudy(
	 * edu.duke.cabig.c3pr.webservice.studyutility.UpdateStudyRequest)
	 */
	public UpdateStudyAbstractResponse updateStudyAbstract(UpdateStudyAbstractRequest request)
			throws StudyUtilityFaultMessage {
		UpdateStudyAbstractResponse response = new UpdateStudyAbstractResponse();
		try {
			StudyProtocolVersion xmlStudy = request.getStudy();
			// validate Permissible Study Subject registry status
			for(PermissibleStudySubjectRegistryStatus permissibleRegStatus : xmlStudy.getPermissibleStudySubjectRegistryStatus()){
				validatePermissibleStudySubjectRegistryStatus(permissibleRegStatus);
			}
			
			edu.duke.cabig.c3pr.domain.Study domainStudy = converter.convert(xmlStudy);
			List<Identifier> idList = domainStudy.getIdentifiers();
			
			if (CollectionUtils.isEmpty(idList)) {
				fail(STUDY_IDENTIFIER_REQUIRED);
			}
			edu.duke.cabig.c3pr.domain.Study existingStudy = null;
			try {
				existingStudy = studyRepository.getByPrimaryIdentifier(domainStudy.getPrimaryIdentifierObject());
			} catch (C3PRDuplicatePrimaryStudyIdentifierException e) {
				fail(MORE_THAN_ONE_STUDY);
			}
			if (existingStudy == null) {
				fail(STUDY_DOES_NOT_EXIST);
			}
			// transfer data except identifiers and consents
			converter.convert(existingStudy, xmlStudy, false);
			// update identifiers: http://jira.semanticbits.com/browse/CPR-2233
			// update identifiers and sites: http://jira.semanticbits.com/browse/CPR-2312
			converter.convert(existingStudy, idList);
			studyRepository.save(existingStudy);
			response.setStudy(converter.convert(existingStudy));
		} catch (RuntimeException e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			fail(e.getMessage());
		} catch (C3PRCodedException e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			fail(e.getMessage());
		}
		return response;

	}

	/**
	 * Gets the converter.
	 *
	 * @return the converter
	 */
	public JAXBToDomainObjectConverter getConverter() {
		return converter;
	}

	/**
	 * Sets the converter.
	 *
	 * @param converter the converter to set
	 */
	public void setConverter(JAXBToDomainObjectConverter converter) {
		this.converter = converter;
	}

	/**
	 * Gets the study repository.
	 *
	 * @return the studyRepository
	 */
	public StudyRepository getStudyRepository() {
		return studyRepository;
	}

	/**
	 * Sets the study repository.
	 *
	 * @param studyRepository the studyRepository to set
	 */
	public void setStudyRepository(StudyRepository studyRepository) {
		this.studyRepository = studyRepository;
	}

	/* (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.webservice.studyutility.StudyUtility#updateStudyConsentQuestion(edu.duke.cabig.c3pr.webservice.studyutility.UpdateStudyConsentQuestionRequest)
	 */
	public UpdateStudyConsentQuestionResponse updateStudyConsentQuestion(UpdateStudyConsentQuestionRequest request)
			throws SecurityExceptionFaultMessage, StudyUtilityFaultMessage {
		UpdateStudyConsentQuestionResponse response = new UpdateStudyConsentQuestionResponse();
		try {
			DocumentIdentifier studyId = request.getStudyIdentifier();
			
			// assuming primary identifier of study is passed and querying by it. It has to be unique across studies.
			Study study = getSingleStudy(studyId);
			edu.duke.cabig.c3pr.domain.Consent domainConsent = study.getConsent(request.getConsentName().getValue(), request.getConsentVersion().getValue());
			
			if(domainConsent == null){
				fail(CONSENT_DOES_NOT_EXIST);
			}
			edu.duke.cabig.c3pr.domain.ConsentQuestion domainConsentQuestion = converter.convertConsentQuestion(request.getConsentQuestion());
			edu.duke.cabig.c3pr.domain.ConsentQuestion existingMatchingConsentQuestion =domainConsent.getQuestion(domainConsentQuestion.getCode());
			
			boolean save = true;
			UpdateMode consentQuestionUpdateMode = request.getUpdateMode();
			switch (consentQuestionUpdateMode) {
			case R:
				if(existingMatchingConsentQuestion == null){
					throw new RuntimeException("Cannot replace consent question. No question was found with given code");
				}
				domainConsent.getQuestions().remove(existingMatchingConsentQuestion);
				domainConsent.getQuestions().add(domainConsentQuestion);
				break;
			case A:
				if(existingMatchingConsentQuestion != null){
					throw new RuntimeException("Cannot add consent question. Another question already exists with the same code");
				}
				domainConsent.getQuestions().add(domainConsentQuestion);
				break;
			case D:
				if(existingMatchingConsentQuestion == null){
					throw new RuntimeException("Cannot retire consent question. No question was found with the given code");
				}
				existingMatchingConsentQuestion.setRetiredIndicatorAsTrue();
				break;
			case AU:
				if(existingMatchingConsentQuestion == null){
					domainConsent.getQuestions().add(domainConsentQuestion);
					break;
				}
			case U:
				if(existingMatchingConsentQuestion == null){
					throw new RuntimeException("Cannot update consent question. No question found with the given code");
				}
				
				// The code of consent question being updated should be same as the code of question it is being updated with. So no need for copy code
				existingMatchingConsentQuestion.setText(domainConsentQuestion.getText());
				break;
			default:
				log.debug("no valid values found for updateMode. no action will be taken");
				save = false;
				break;
			}
			if(save){
				studyRepository.save(study);
			}
			response.setConsent(converter.convertConsent(study.getConsent(domainConsent.getName(), domainConsent.getVersionId())));
		} catch (RuntimeException e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			fail(e.getMessage());
		} catch (C3PRCodedException e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			fail(e.getMessage());
		}
		return response;
	}
	
	/* (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.webservice.studyutility.StudyUtility#updateStudyConsent(edu.duke.cabig.c3pr.webservice.studyutility.UpdateStudyConsentRequest)
	 */
	public UpdateStudyConsentResponse updateStudyConsent(UpdateStudyConsentRequest request)
			throws SecurityExceptionFaultMessage, StudyUtilityFaultMessage {
		UpdateStudyConsentResponse response = new UpdateStudyConsentResponse();
		try {
			DocumentIdentifier studyId = request.getStudyIdentifier();
			Consent consent = request.getConsent();
			
			// assuming primary identifier of study is passed and querying by it. It has to be unique across studies.
			Study study = getSingleStudy(studyId);
			edu.duke.cabig.c3pr.domain.Consent domainConsent = converter
					.convertConsent(consent);
			edu.duke.cabig.c3pr.domain.Consent existingMatchingConsent = study.getConsent(domainConsent.getName(), domainConsent.getVersionId());
			boolean save = true;
			UpdateMode consentUpdateMode = request.getUpdateMode();
			switch (consentUpdateMode) {
			case R:
				if(existingMatchingConsent == null){
					throw new RuntimeException("Cannot replace consent. No consent was found with given name and version Id");
				}
				
				study.getConsents().remove(existingMatchingConsent);
				study.addConsent(domainConsent);
				break;
			case A:
				if(existingMatchingConsent!=null){
					throw new RuntimeException("Cannot add consent. Another consent already exists with the given name and version");
				}
				study.addConsent(domainConsent);
				break;
			case D:
				if(existingMatchingConsent==null){
					throw new RuntimeException("Cannot delete consent. No consent found with the given name and version");
				}
				study.getConsent(domainConsent.getName(), domainConsent.getVersionId()).setRetiredIndicatorAsTrue();
				break;
			case AU:
				if(existingMatchingConsent==null){
					study.addConsent(domainConsent);
					break;
				}
			case U:
				if(existingMatchingConsent == null){
					throw new RuntimeException("Cannot update consent. No consent found with the given name and version");
				}
				existingMatchingConsent.setMandatoryIndicator(domainConsent.getMandatoryIndicator());
				// The name and version id of the consent being updated should be same as the one it is being updated with. So no need for copying  those two attributes
			//	existingMatchingConsent.setName(domainConsent.getName());
			//	existingMatchingConsent.setVersionId(domainConsent.getVersionId());
				existingMatchingConsent.setDescriptionText(domainConsent.getDescriptionText());
				existingMatchingConsent.getQuestions().clear();
				existingMatchingConsent.getQuestions().addAll(domainConsent.getQuestions());
				break;
			default:
				log.debug("no valid values found for updateMode. no action will be taken");
				save = false;
				break;
			}
			if(save){
				studyRepository.save(study);
			}
			response.setConsent(converter.convertConsent(study.getConsent(domainConsent.getName(), domainConsent.getVersionId())));
		} catch (RuntimeException e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			fail(e.getMessage());
		} catch (C3PRCodedException e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			fail(e.getMessage());
		}
		return response;
	}


	/* (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.webservice.studyutility.StudyUtility#queryStudyConsent(edu.duke.cabig.c3pr.webservice.studyutility.QueryStudyConsentRequest)
	 */
	public QueryStudyConsentResponse queryStudyConsent(QueryStudyConsentRequest request)
			throws SecurityExceptionFaultMessage, StudyUtilityFaultMessage {
		QueryStudyConsentResponse response = new QueryStudyConsentResponse();
		DSETConsent consents = new DSETConsent();
		response.setConsents(consents);
		List<edu.duke.cabig.c3pr.domain.Consent> domainConsents = new ArrayList<edu.duke.cabig.c3pr.domain.Consent>();
		try {
			DocumentIdentifier studyId = request.getStudyIdentifier();
			Consent consent = request.getConsent();
			// assuming primary identifier of study is passed and querying by it. It has to be unique across studies.
			Study study = getSingleStudy(studyId);

			if (consent == null) {
				domainConsents.addAll(study.getConsents());
			} else {
				domainConsents.addAll(consentDao.searchByExampleAndStudy(
						converter.convertConsentForSearchByExample(consent),
						study));
			}
			for (edu.duke.cabig.c3pr.domain.Consent c : domainConsents) {
				consents.getItem().add(converter.convertConsent(c));
			}
		} catch (RuntimeException e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			fail(e.getMessage());
		}
		return response;

	}

	/**
	 * Gets the single study.
	 *
	 * @param studyId the study id
	 * @return the single study
	 * @throws StudyUtilityFaultMessage the study utility fault message
	 */
	
	// If primary identifier is not passed the query will return null. It should return a duplicate
	// study found fault when more than 1 result is found
	private Study getSingleStudy(DocumentIdentifier studyId)
			throws StudyUtilityFaultMessage {
		Identifier id = converter.convert(studyId);
		Study study = null;
		try {
			study = studyRepository.getByPrimaryIdentifier(id);
		} catch (C3PRDuplicatePrimaryStudyIdentifierException e) {
			fail(MORE_THAN_ONE_STUDY);
		}
		
		if (study == null) {
			fail(STUDY_DOES_NOT_EXIST);
		
		}
		return study;
	}

	/**
	 * Gets the consent dao.
	 *
	 * @return the consentDao
	 */
	public ConsentDao getConsentDao() {
		return consentDao;
	}

	/**
	 * Sets the consent dao.
	 *
	 * @param consentDao the consentDao to set
	 */
	public void setConsentDao(ConsentDao consentDao) {
		this.consentDao = consentDao;
	}

	/* (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.webservice.studyutility.StudyUtility#updateStudyStatus(edu.duke.cabig.c3pr.webservice.studyutility.UpdateStudyStatusRequest)
	 */
	public UpdateStudyStatusResponse updateStudyStatus(
			UpdateStudyStatusRequest request)
			throws SecurityExceptionFaultMessage, StudyUtilityFaultMessage {
		UpdateStudyStatusResponse response = new UpdateStudyStatusResponse();
		try {
			DocumentIdentifier studyId = request.getStudyIdentifier();
			PermissibleStudySubjectRegistryStatus status = request.getStatus();
			// validation checks for reasons
			validatePermissibleStudySubjectRegistryStatus(status);
			
			UpdateMode statusUpdateMode = request.getUpdateMode();

			// assuming primary identifier of study is passed and querying by it. It has to be unique across studies.
			Study study = getSingleStudy(studyId);
			edu.duke.cabig.c3pr.domain.PermissibleStudySubjectRegistryStatus domainStatus = converter
					.convert(status);
			// find the existing registry status in study
			edu.duke.cabig.c3pr.domain.PermissibleStudySubjectRegistryStatus matchingRegistryStatus = 
				study.getPermissibleStudySubjectRegistryStatus(domainStatus.getRegistryStatus().getCode());
			
			boolean save = true;
			switch (statusUpdateMode) {
			case R:
				if(matchingRegistryStatus == null){
					throw new RuntimeException("Cannot replace study registry status. No status was found with given code");
				}
				
				study.getPermissibleStudySubjectRegistryStatusesInternal().remove(matchingRegistryStatus);
				study.getPermissibleStudySubjectRegistryStatusesInternal().add(domainStatus);
				break;
			case A:
				if(matchingRegistryStatus != null){
					throw new RuntimeException("Cannot add study registry status. This status already exists");
				}
				study.getPermissibleStudySubjectRegistryStatusesInternal().add(domainStatus);
				break;
			case D:
				if(matchingRegistryStatus == null){
					throw new RuntimeException("Cannot delete study registry status. No status was found with the given code");
				}
				matchingRegistryStatus.setRetiredIndicatorAsTrue();
				break;
			case AU:
				if(matchingRegistryStatus == null){
					study.getPermissibleStudySubjectRegistryStatusesInternal().add(domainStatus);
					break;
				}
			case U:
				if(matchingRegistryStatus == null){
					throw new RuntimeException("Cannot update status. No status with given code is found");
				}
				matchingRegistryStatus.setRegistryStatus(domainStatus.getRegistryStatus());
				
				List<edu.duke.cabig.c3pr.domain.RegistryStatusReason> associatedSecondaryReasons = getListOfStudySubjectAssociatedSecondaryReasons(matchingRegistryStatus.getSecondaryReasons());
				//remove the unassociated reasons from db
				removeUnassociatedSecondaryReasons(matchingRegistryStatus.getSecondaryReasons(), associatedSecondaryReasons);
				//remove the associated reasons from request
				removeAssociatedSecondaryReasons(domainStatus.getSecondaryReasons(), associatedSecondaryReasons);
				//add the unassociated reasons from request to list of associated reasons from db
				matchingRegistryStatus.getSecondaryReasons().addAll(domainStatus.getSecondaryReasons());
				break;
			default:
				log.debug("no valid values found for updateMode. no action will be taken");
				save = false;
				break;
			}
			if(save){
				studyRepository.save(study);
			}
			response.setStatus(converter.convert(study.getPermissibleStudySubjectRegistryStatus(domainStatus.getRegistryStatus().getCode())));
		} catch (RuntimeException e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			fail(e.getMessage());
		} catch (C3PRCodedException e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			fail(e.getMessage());
		}
		return response;
	}


	/**
	 * Removes the associated secondary reasons.
	 *
	 * @param domainSecondaryReasons the domain secondary reasons
	 * @param associatedSecondaryReasons the associated secondary reasons
	 */
	private void removeAssociatedSecondaryReasons(
			List<edu.duke.cabig.c3pr.domain.RegistryStatusReason> domainSecondaryReasons,
			List<edu.duke.cabig.c3pr.domain.RegistryStatusReason> associatedSecondaryReasons) {
		
		for(edu.duke.cabig.c3pr.domain.RegistryStatusReason rsReason: associatedSecondaryReasons){
			domainSecondaryReasons.remove(rsReason);
		}		
	}

	/**
	 * Removes the unassociated secondary reasons.
	 *
	 * @param matchingSecondaryReasons the matching secondary reasons
	 * @param associatedSecondaryReasons the associated secondary reasons
	 */
	private void removeUnassociatedSecondaryReasons(
			List<edu.duke.cabig.c3pr.domain.RegistryStatusReason> matchingSecondaryReasons,
			List<edu.duke.cabig.c3pr.domain.RegistryStatusReason> associatedSecondaryReasons) {

		for(edu.duke.cabig.c3pr.domain.RegistryStatusReason rsReason: new ArrayList<edu.duke.cabig.c3pr.domain.RegistryStatusReason>(matchingSecondaryReasons)){
			if(!associatedSecondaryReasons.contains(rsReason)){
				matchingSecondaryReasons.remove(rsReason);
			}
		}
	}
	

	/**
	 * Gets the list of the secondary reasons associated to any existing study subject.
	 *
	 * @param matchingSecondaryReasons the matching secondary reasons
	 * @return the list of study subject associated secondary reasons
	 */
	private List<edu.duke.cabig.c3pr.domain.RegistryStatusReason> getListOfStudySubjectAssociatedSecondaryReasons(List<edu.duke.cabig.c3pr.domain.RegistryStatusReason> matchingSecondaryReasons) {
		List<edu.duke.cabig.c3pr.domain.RegistryStatusReason> associatedSecondaryReasons = new ArrayList<edu.duke.cabig.c3pr.domain.RegistryStatusReason>();
		boolean isAssociated = false;
		for(edu.duke.cabig.c3pr.domain.RegistryStatusReason matchingSecondaryReason:matchingSecondaryReasons){
			isAssociated = studyRepository.isSecondaryReasonAssociatedToExistingStudySubjects(matchingSecondaryReason);
			if(isAssociated){
				associatedSecondaryReasons.add(matchingSecondaryReason);
			}
		}
		return associatedSecondaryReasons;
	}

	/* (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.webservice.studyutility.StudyUtility#queryRegistryStatus(edu.duke.cabig.c3pr.webservice.studyutility.QueryRegistryStatusRequest)
	 */
	public QueryRegistryStatusResponse queryRegistryStatus(
			QueryRegistryStatusRequest request)
			throws SecurityExceptionFaultMessage, StudyUtilityFaultMessage {
		QueryRegistryStatusResponse response = new QueryRegistryStatusResponse();
		response.setRegistryStatuses(new DSETRegistryStatus());
		List<RegistryStatus> list = new ArrayList<RegistryStatus>();
		CD cd = request.getStatusCode();
		if (cd == null || cd.getNullFlavor() != null
				|| StringUtils.isBlank(cd.getCode())) {
			list.addAll(registryStatusDao.getAll());
		} else {
			list.add(registryStatusDao.getRegistryStatusByCode(cd.getCode()));
		}
		for (RegistryStatus registryStatus : list) {
			response.getRegistryStatuses().getItem()
					.add(converter.convert(registryStatus));
		}
		return response;
	}

	/**
	 * Gets the registry status dao.
	 *
	 * @return the registryStatusDao
	 */
	public RegistryStatusDao getRegistryStatusDao() {
		return registryStatusDao;
	}

	/**
	 * Sets the registry status dao.
	 *
	 * @param registryStatusDao the registryStatusDao to set
	 */
	public void setRegistryStatusDao(RegistryStatusDao registryStatusDao) {
		this.registryStatusDao = registryStatusDao;
	}

	/* (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.webservice.studyutility.StudyUtility#queryStudyRegistryStatus(edu.duke.cabig.c3pr.webservice.studyutility.QueryStudyRegistryStatusRequest)
	 */
	public QueryStudyRegistryStatusResponse queryStudyRegistryStatus(
			QueryStudyRegistryStatusRequest request)
			throws SecurityExceptionFaultMessage, StudyUtilityFaultMessage {
		QueryStudyRegistryStatusResponse response = new QueryStudyRegistryStatusResponse();
		response.setStatuses(new DSETPermissibleStudySubjectRegistryStatus());
		try {
			DocumentIdentifier studyId = request.getStudyIdentifier();
			// assuming primary identifier of study is passed and querying by it. It has to be unique across studies.
			Study study = getSingleStudy(studyId);
			for (edu.duke.cabig.c3pr.domain.PermissibleStudySubjectRegistryStatus status : study
					.getPermissibleStudySubjectRegistryStatuses()) {
				response.getStatuses().getItem().add(converter.convert(status));
			}
		} catch (RuntimeException e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			fail(e.getMessage());
		}
		return response;
	}
	
	/**
	 * Validate permissible study subject registry status.
	 *
	 * @param status the status
	 */
	private void validatePermissibleStudySubjectRegistryStatus(PermissibleStudySubjectRegistryStatus status){
		// validation checks for reasons
		for(RegistryStatusReason secondaryReason:status.getSecondaryReason()){
			if(secondaryReason.getPrimaryIndicator().isValue()){
				throw new RuntimeException("Secondary registry reason in update study status request cannot have primary indicator as true");
			}
			if(secondaryReason.getPrimaryReason() == null){
				throw new RuntimeException("Secondary registry reason in update study status request does not have a primary reason");
			}
			boolean primaryRegistryReasonSent = false;
			for(RegistryStatusReason primaryRegistryReason :status.getRegistryStatus().getPrimaryReason()){
				if(secondaryReason.getPrimaryReason().getCode().getCode().equals(primaryRegistryReason.getCode().getCode())){
					primaryRegistryReasonSent = true;
					break;
				}
			}
			if(!primaryRegistryReasonSent){
				throw new RuntimeException("Unable to find a matching primary registry reason for the secondary registry reason in update study status request");
			}
		}	
	}

}
