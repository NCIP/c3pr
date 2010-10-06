/**
 * 
 */
package edu.duke.cabig.c3pr.webservice.studyutility;

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
import edu.duke.cabig.c3pr.webservice.common.PermissibleStudySubjectRegistryStatus;
import edu.duke.cabig.c3pr.webservice.common.StudyProtocolVersion;
import edu.duke.cabig.c3pr.webservice.converters.JAXBToDomainObjectConverter;
import edu.duke.cabig.c3pr.webservice.iso21090.CD;
import edu.duke.cabig.c3pr.webservice.subjectmanagement.SubjectManagementImpl;
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

	private static Log log = LogFactory.getLog(SubjectManagementImpl.class);

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
	public AdvancedQueryStudyResponse advancedQueryStudy(
			AdvancedQueryStudyRequest parameters)
			throws StudyUtilityFaultMessage {

		AdvancedQueryStudyResponse response = new AdvancedQueryStudyResponse();
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
	public CreateStudyResponse createStudy(CreateStudyRequest request)
			throws StudyUtilityFaultMessage {

		CreateStudyResponse response = new CreateStudyResponse();
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
	public UpdateStudyResponse updateStudy(UpdateStudyRequest request)
			throws StudyUtilityFaultMessage {
		UpdateStudyResponse response = new UpdateStudyResponse();
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
			converter.convert(study, xmlStudy, false);
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

	public UpdateConsentResponse updateConsent(UpdateConsentRequest request)
			throws SecurityExceptionFaultMessage, StudyUtilityFaultMessage {
		UpdateConsentResponse response = new UpdateConsentResponse();
		try {
			DocumentIdentifier studyId = request.getStudyIdentifier();
			Consent consent = request.getConsent();

			Study study = getStudyForConsentOperations(studyId);
			edu.duke.cabig.c3pr.domain.Consent domainConsent = converter
					.convertConsent(consent);
			study.getConsents().clear();
			study.addConsent(domainConsent);
			studyRepository.save(study);
			response.setConsent(converter.convertConsent(domainConsent));
		} catch (RuntimeException e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			fail(e.getMessage());
		} catch (C3PRCodedException e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			fail(e.getMessage());
		}
		return response;
	}

	public QueryConsentResponse queryConsent(QueryConsentRequest request)
			throws SecurityExceptionFaultMessage, StudyUtilityFaultMessage {
		QueryConsentResponse response = new QueryConsentResponse();
		DSETConsent consents = new DSETConsent();
		response.setConsents(consents);
		List<edu.duke.cabig.c3pr.domain.Consent> domainConsents = new ArrayList<edu.duke.cabig.c3pr.domain.Consent>();
		try {
			DocumentIdentifier studyId = request.getStudyIdentifier();
			Consent consent = request.getConsent();
			Study study = getStudyForConsentOperations(studyId);

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
	private Study getStudyForConsentOperations(DocumentIdentifier studyId)
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

	public UpdateStudyStatusResponse updateStudyStatus(
			UpdateStudyStatusRequest request)
			throws SecurityExceptionFaultMessage, StudyUtilityFaultMessage {
		UpdateStudyStatusResponse response = new UpdateStudyStatusResponse();
		try {
			DocumentIdentifier studyId = request.getStudyIdentifier();
			PermissibleStudySubjectRegistryStatus status = request.getStatus();

			Study study = getStudyForConsentOperations(studyId);
			edu.duke.cabig.c3pr.domain.PermissibleStudySubjectRegistryStatus domainStatus = converter
					.convert(status);
			study.getPermissibleStudySubjectRegistryStatusesInternal().clear();
			study.getPermissibleStudySubjectRegistryStatusesInternal().add(
					domainStatus);
			studyRepository.save(study);
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

}
