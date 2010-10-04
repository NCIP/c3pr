/**
 * 
 */
package edu.duke.cabig.c3pr.webservice.studyutility;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebService;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.semanticbits.querybuilder.AdvancedSearchCriteriaParameter;

import edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.repository.StudyRepository;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;
import edu.duke.cabig.c3pr.webservice.common.AdvanceSearchCriterionParameter;
import edu.duke.cabig.c3pr.webservice.common.StudyProtocolVersion;
import edu.duke.cabig.c3pr.webservice.converters.JAXBToDomainObjectConverter;
import edu.duke.cabig.c3pr.webservice.subjectmanagement.SubjectManagementImpl;

/**
 * @author dkrylov
 * 
 */
@WebService(endpointInterface = "edu.duke.cabig.c3pr.webservice.studyutility.StudyUtility", wsdlLocation = "/WEB-INF/wsdl/StudyUtility.wsdl", targetNamespace = "http://enterpriseservices.nci.nih.gov/StudyUtilityService", portName = "StudyUtility", serviceName = "StudyUtilityService")
public class StudyUtilityImpl implements StudyUtility {

	private static final String STUDY_IDENTIFIER_REQUIRED = "Study identifier is required.";

	private static final String STUDY_ALREADY_EXISTS = "A study with the given identifier(s) already exists.";

	private static final String STUDY_DOES_NOT_EXIST = "A study with the given identifier(s) does not exist.";

	private static Log log = LogFactory.getLog(SubjectManagementImpl.class);

	private JAXBToDomainObjectConverter converter;

	private StudyRepository studyRepository;

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
		/*DSETStudyProtocolVersion studies = new DSETStudyProtocolVersion();
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
		}*/
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
			/**
			StudyProtocolVersion xmlStudy = request.getStudy();
			List<StudyIdentifier> xmlIds = xmlStudy.getStudyIdentifier();
			if (CollectionUtils.isEmpty(xmlIds)) {
				fail(STUDY_IDENTIFIER_REQUIRED);
			}
			List<OrganizationAssignedIdentifier> ids = converter
					.convert(xmlIds);
			List<edu.duke.cabig.c3pr.domain.Study> existentStudies = studyRepository
					.getByIdentifiers((List) ids);
			if (CollectionUtils.isEmpty(existentStudies)) {
				fail(STUDY_DOES_NOT_EXIST);
			}
			edu.duke.cabig.c3pr.domain.Study study = existentStudies.get(0);
			converter.convert(study, xmlStudy);
			studyRepository.save(study);
			response.setStudy(converter.convert(study));
			**/
		} catch (RuntimeException e) {
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

}
