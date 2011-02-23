/**
 * 
 */
package edu.duke.cabig.c3pr.webservice.studyutility.impl;

import java.util.ArrayList;
import java.util.Arrays;
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
import edu.duke.cabig.c3pr.webservice.common.AdvanceSearchCriterionParameter;
import edu.duke.cabig.c3pr.webservice.common.Consent;
import edu.duke.cabig.c3pr.webservice.common.DocumentIdentifier;
import edu.duke.cabig.c3pr.webservice.common.DocumentVersionRelationship;
import edu.duke.cabig.c3pr.webservice.common.PermissibleStudySubjectRegistryStatus;
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
import edu.duke.cabig.c3pr.webservice.studyutility.UpdateStudyConsentRequest;
import edu.duke.cabig.c3pr.webservice.studyutility.UpdateStudyConsentResponse;
import edu.duke.cabig.c3pr.webservice.studyutility.UpdateStudyStatusRequest;
import edu.duke.cabig.c3pr.webservice.studyutility.UpdateStudyStatusResponse;
import edu.duke.cabig.c3pr.webservice.subjectmanagement.impl.SubjectManagementImpl;
import gov.nih.nci.logging.api.util.StringUtils;

/**
 * @author dkrylov
 * 
 */
@WebService(endpointInterface = "edu.duke.cabig.c3pr.webservice.studyutility.StudyUtility", wsdlLocation = "/WEB-INF/wsdl/StudyUtility.wsdl", targetNamespace = "http://enterpriseservices.nci.nih.gov/StudyUtilityService", portName = "StudyUtility", serviceName = "StudyUtilityService")
public class StudyUtilityImpl implements StudyUtility {

	private static final String STUDY_IDENTIFIER_REQUIRED = "Study identifier is required.";

	private static final String STUDY_ALREADY_EXISTS = "A study with the given identifier(s) already exists.";

	private static final String STUDY_DOES_NOT_EXIST = "A study with the given identifier(s) does not exist.";

	private static final String MORE_THAN_ONE_STUDY = "More than one study with the given identifier found.";

	private static Log log = LogFactory.getLog(StudyUtilityImpl.class);

	private JAXBToDomainObjectConverter converter;

	private StudyRepository studyRepository;

	private ConsentDao consentDao;

	private RegistryStatusDao registryStatusDao;

	/**
	 * 
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
			edu.duke.cabig.c3pr.domain.Study study = converter
					.convert(xmlStudy);
			if (CollectionUtils.isEmpty(study.getIdentifiers())) {
				fail(STUDY_IDENTIFIER_REQUIRED);
			}
			List<edu.duke.cabig.c3pr.domain.Study> existentStudies = studyRepository
					.getByIdentifiers(study.getIdentifiers());
			if (CollectionUtils.isNotEmpty(existentStudies)) {
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
			List<Identifier> idList = converter.convert(xmlStudy)
					.getIdentifiers();
			if (CollectionUtils.isEmpty(idList)) {
				fail(STUDY_IDENTIFIER_REQUIRED);
			}
			List<edu.duke.cabig.c3pr.domain.Study> existentStudies = studyRepository
					.getByIdentifiers(idList);
			if (CollectionUtils.isEmpty(existentStudies)) {
				fail(STUDY_DOES_NOT_EXIST);
			}
			edu.duke.cabig.c3pr.domain.Study study = existentStudies.get(0);
			// transfer data except identifiers and consents
			converter.convert(study, xmlStudy, false);
			// update identifiers: http://jira.semanticbits.com/browse/CPR-2233
			// update identifiers and sites: http://jira.semanticbits.com/browse/CPR-2312
			converter.convert(study, idList);
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
	 * @return the converter
	 */
	public JAXBToDomainObjectConverter getConverter() {
		return converter;
	}

	/**
	 * @param converter
	 *            the converter to set
	 */
	public void setConverter(JAXBToDomainObjectConverter converter) {
		this.converter = converter;
	}

	/**
	 * @return the studyRepository
	 */
	public StudyRepository getStudyRepository() {
		return studyRepository;
	}

	/**
	 * @param studyRepository
	 *            the studyRepository to set
	 */
	public void setStudyRepository(StudyRepository studyRepository) {
		this.studyRepository = studyRepository;
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
				if(study.getConsent(domainConsent.getName(), domainConsent.getVersionId())!=null){
					throw new RuntimeException("Cannot add consent. Another consent already exists with the given name and version");
				}
				study.addConsent(domainConsent);
				break;
			case D:
				if(study.getConsent(domainConsent.getName(), domainConsent.getVersionId())==null){
					throw new RuntimeException("Cannot delete consent. No consent found with the given name and version");
				}
				study.getConsent(domainConsent.getName(), domainConsent.getVersionId()).setRetiredIndicatorAsTrue();
				break;
			case AU:
				if(study.getConsent(domainConsent.getName(), domainConsent.getVersionId())==null){
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
	 * @param studyId
	 * @return
	 * @throws StudyUtilityFaultMessage
	 */
	private Study getSingleStudy(DocumentIdentifier studyId)
			throws StudyUtilityFaultMessage {
		Identifier oai = converter.convert(studyId);
		List<Study> studies = studyRepository.getByIdentifiers(Arrays
				.asList(new Identifier[] { oai }));
		if (CollectionUtils.isEmpty(studies)) {
			fail(STUDY_DOES_NOT_EXIST);
		}
		if (studies.size() > 1) {
			fail(MORE_THAN_ONE_STUDY);
		}
		Study study = studies.get(0);
		return study;
	}

	/**
	 * @return the consentDao
	 */
	public ConsentDao getConsentDao() {
		return consentDao;
	}

	/**
	 * @param consentDao
	 *            the consentDao to set
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
			UpdateMode statusUpdateMode = request.getUpdateMode();

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
				matchingRegistryStatus.setSecondaryReasons(domainStatus.getSecondaryReasons());
				break;
			default:
				log.debug("no valid values found for updateMode. no action will be taken");
				save = false;
				break;
			}
			if(save){
				studyRepository.save(study);
			}
			response.setStatus(converter.convert(domainStatus));
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
	 * @return the registryStatusDao
	 */
	public RegistryStatusDao getRegistryStatusDao() {
		return registryStatusDao;
	}

	/**
	 * @param registryStatusDao
	 *            the registryStatusDao to set
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

}
