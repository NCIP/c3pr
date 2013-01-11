/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.webservice.subjectmanagement.impl;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebService;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import com.semanticbits.querybuilder.AdvancedSearchCriteriaParameter;

import edu.duke.cabig.c3pr.constants.ParticipantStateCode;
import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.repository.ParticipantRepository;
import edu.duke.cabig.c3pr.domain.validator.ParticipantValidator;
import edu.duke.cabig.c3pr.exception.ConversionException;
import edu.duke.cabig.c3pr.web.participant.ParticipantWrapper;
import edu.duke.cabig.c3pr.webservice.common.AdvanceSearchCriterionParameter;
import edu.duke.cabig.c3pr.webservice.common.BiologicEntityIdentifier;
import edu.duke.cabig.c3pr.webservice.common.Subject;
import edu.duke.cabig.c3pr.webservice.converters.JAXBToDomainObjectConverter;
import edu.duke.cabig.c3pr.webservice.iso21090.ST;
import edu.duke.cabig.c3pr.webservice.subjectmanagement.AdvancedQuerySubjectRequest;
import edu.duke.cabig.c3pr.webservice.subjectmanagement.AdvancedQuerySubjectResponse;
import edu.duke.cabig.c3pr.webservice.subjectmanagement.CreateSubjectRequest;
import edu.duke.cabig.c3pr.webservice.subjectmanagement.CreateSubjectResponse;
import edu.duke.cabig.c3pr.webservice.subjectmanagement.DSETSUBJECT;
import edu.duke.cabig.c3pr.webservice.subjectmanagement.InvalidStateTransitionExceptionFault;
import edu.duke.cabig.c3pr.webservice.subjectmanagement.InvalidStateTransitionExceptionFaultMessage;
import edu.duke.cabig.c3pr.webservice.subjectmanagement.InvalidSubjectDataExceptionFault;
import edu.duke.cabig.c3pr.webservice.subjectmanagement.InvalidSubjectDataExceptionFaultMessage;
import edu.duke.cabig.c3pr.webservice.subjectmanagement.NoSuchSubjectExceptionFault;
import edu.duke.cabig.c3pr.webservice.subjectmanagement.NoSuchSubjectExceptionFaultMessage;
import edu.duke.cabig.c3pr.webservice.subjectmanagement.QuerySubjectRequest;
import edu.duke.cabig.c3pr.webservice.subjectmanagement.QuerySubjectResponse;
import edu.duke.cabig.c3pr.webservice.subjectmanagement.SecurityExceptionFaultMessage;
import edu.duke.cabig.c3pr.webservice.subjectmanagement.SubjectAlreadyExistsExceptionFault;
import edu.duke.cabig.c3pr.webservice.subjectmanagement.SubjectAlreadyExistsExceptionFaultMessage;
import edu.duke.cabig.c3pr.webservice.subjectmanagement.SubjectManagement;
import edu.duke.cabig.c3pr.webservice.subjectmanagement.UnableToCreateOrUpdateSubjectExceptionFault;
import edu.duke.cabig.c3pr.webservice.subjectmanagement.UnableToCreateOrUpdateSubjectExceptionFaultMessage;
import edu.duke.cabig.c3pr.webservice.subjectmanagement.UpdateSubjectRequest;
import edu.duke.cabig.c3pr.webservice.subjectmanagement.UpdateSubjectResponse;
import edu.duke.cabig.c3pr.webservice.subjectmanagement.UpdateSubjectStateRequest;
import edu.duke.cabig.c3pr.webservice.subjectmanagement.UpdateSubjectStateResponse;

/**
 * Implementation of the Subject Management web service.
 * 
 * @see https 
 *      ://ncisvn.nci.nih.gov/svn/c3pr/trunk/c3prv2/documentation/design/essn
 *      /SubjectManagement/PSM_SS_Subject_Management.doc
 * @author dkrylov
 * 
 */
@WebService(wsdlLocation = "/WEB-INF/wsdl/SubjectManagement.wsdl", targetNamespace = "http://enterpriseservices.nci.nih.gov/SubjectManagementService", endpointInterface = "edu.duke.cabig.c3pr.webservice.subjectmanagement.SubjectManagement", portName = "SubjectManagement", serviceName = "SubjectManagementService")
public class SubjectManagementImpl implements SubjectManagement {

	private static final String SUBJECT_DOES_NOT_EXIST = "Subject does not exist.";

	private static final String MISSING_EITHER_SUBJECT_IDENTIFIER_OR_NEW_STATE_VALUE = "Missing either subject identifier or new state value.";

	private static final String WRONG_SUBJECT_STATE_VALUE = "Wrong subject state value.";

	private static final String SUBJECT_IS_INACTIVE = "Subject is inactive.";

	private static final String SUBJECT_ALREADY_EXISTS = "Subject already exists.";

	private static Log log = LogFactory.getLog(SubjectManagementImpl.class);

	private JAXBToDomainObjectConverter converter;

	private ParticipantValidator participantValidator;

	private ParticipantRepository participantRepository;

	public ParticipantRepository getParticipantRepository() {
		return participantRepository;
	}

	public void setParticipantRepository(
			ParticipantRepository participantRepository) {
		this.participantRepository = participantRepository;
	}

	public ParticipantValidator getParticipantValidator() {
		return participantValidator;
	}

	public void setParticipantValidator(
			ParticipantValidator participantValidator) {
		this.participantValidator = participantValidator;
	}

	public JAXBToDomainObjectConverter getConverter() {
		return converter;
	}

	public void setConverter(JAXBToDomainObjectConverter converter) {
		this.converter = converter;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeedu.duke.cabig.c3pr.webservice.subjectmanagement.SubjectManagement#
	 * createSubject
	 * (edu.duke.cabig.c3pr.webservice.subjectmanagement.CreateSubjectRequest)
	 */
	public CreateSubjectResponse createSubject(CreateSubjectRequest request)
			throws SecurityExceptionFaultMessage,
			InvalidSubjectDataExceptionFaultMessage,
			SubjectAlreadyExistsExceptionFaultMessage,
			UnableToCreateOrUpdateSubjectExceptionFaultMessage {
		CreateSubjectResponse response = new CreateSubjectResponse();
		try {
			Subject subject = request.getSubject();
			Participant participant = converter.convert(subject, true,false);

			List<Participant> existingList = participantRepository
					.searchByIdentifier(participant.getPrimaryIdentifier());
			if (CollectionUtils.isNotEmpty(existingList)) {
				SubjectAlreadyExistsExceptionFault fault = new SubjectAlreadyExistsExceptionFault();
				fault.setMessage(SUBJECT_ALREADY_EXISTS);
				throw new SubjectAlreadyExistsExceptionFaultMessage(
						SUBJECT_ALREADY_EXISTS, fault);
			}

			final ParticipantWrapper wrapper = new ParticipantWrapper(
					participant);
			// validating only participant's basic details as address is not mandatory
			participantValidator.validateParticipantDetails(wrapper,
					new ExceptionBasedErrorsImpl(wrapper));
			participantRepository.save(participant);
			response.setSubject(converter.convert(participant));
		} catch (ConversionException e) {
			handleInvalidSubjectData(e);
		} catch (ParticipantValidationError e) {
			handleInvalidSubjectData(e);
		} catch (RuntimeException e) {
			handleSubjectBackendProblem(e);
		}
		return response;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeedu.duke.cabig.c3pr.webservice.subjectmanagement.SubjectManagement#
	 * querySubject
	 * (edu.duke.cabig.c3pr.webservice.subjectmanagement.QuerySubjectRequest)
	 */
	public QuerySubjectResponse querySubject(QuerySubjectRequest request)
			throws SecurityExceptionFaultMessage,
			InvalidSubjectDataExceptionFaultMessage {
		QuerySubjectResponse response = new QuerySubjectResponse();
		final Subject subject = request.getSubject();
		if (subject != null && subject.getEntity() != null) {
			try {
				Participant participant = converter.convert(subject, false, true);
				List<Participant> results = new ArrayList<Participant>(
						participantRepository.searchByFullExample(participant));
				/*
				 * org.apache.commons.collections15.CollectionUtils.filter(results
				 * , new Predicate<Participant>() { public boolean
				 * evaluate(Participant p) { return
				 * !SubjectStateCode.INACTIVE.getCode
				 * ().equals(p.getStateCode()); } });
				 */
				DSETSUBJECT dsetsubject = new DSETSUBJECT();
				response.setSubjects(dsetsubject);
				for (Participant p : results) {
					dsetsubject.getItem().add(converter.convert(p));
				}
			} catch (ConversionException e) {
				handleInvalidSubjectData(e);
			}
		} else {
			handleInvalidSubjectData(new RuntimeException(
					"Subject data required for search to be performed."));
		}
		return response;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeedu.duke.cabig.c3pr.webservice.subjectmanagement.SubjectManagement#
	 * advancedQuerySubject
	 * (edu.duke.cabig.c3pr.webservice.subjectmanagement.AdvancedQuerySubjectRequest
	 * )
	 */
	public AdvancedQuerySubjectResponse advancedQuerySubject(
			AdvancedQuerySubjectRequest parameters)
			throws SecurityExceptionFaultMessage,
			InvalidSubjectDataExceptionFaultMessage {

		AdvancedQuerySubjectResponse response = new AdvancedQuerySubjectResponse();
		DSETSUBJECT subjects = new DSETSUBJECT();
		response.setSubjects(subjects);

		try {
			List<AdvancedSearchCriteriaParameter> advParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
			for (AdvanceSearchCriterionParameter param : parameters
					.getParameters().getItem()) {
				AdvancedSearchCriteriaParameter advParam = converter
						.convert(param);
				advParameters.add(advParam);
			}

			List<Participant> results = new ArrayList<Participant>(
					participantRepository.search(advParameters));
			for (Participant p : results) {
				subjects.getItem().add(converter.convert(p));
			}
		} catch (ConversionException e) {
			handleInvalidSubjectData(e);
		}
		return response;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeedu.duke.cabig.c3pr.webservice.subjectmanagement.SubjectManagement#
	 * updateSubject
	 * (edu.duke.cabig.c3pr.webservice.subjectmanagement.UpdateSubjectRequest)
	 */
	public UpdateSubjectResponse updateSubject(UpdateSubjectRequest request)
			throws SecurityExceptionFaultMessage,
			InvalidSubjectDataExceptionFaultMessage,
			NoSuchSubjectExceptionFaultMessage,
			UnableToCreateOrUpdateSubjectExceptionFaultMessage {
		UpdateSubjectResponse response = new UpdateSubjectResponse();
		try {
			Subject subject = request.getSubject();
			Participant participant = converter.convert(subject, true,false);
			List<Participant> existingList = participantRepository
			.searchByIdentifier(participant.getPrimaryIdentifier());
			if (CollectionUtils.isEmpty(existingList)) {
				handleUnexistentSubject();
			}

			participant = existingList.get(0);
			if (!ParticipantStateCode.ACTIVE.equals(participant.getStateCode())) {
				NoSuchSubjectExceptionFault fault = new NoSuchSubjectExceptionFault();
				fault.setMessage(SUBJECT_IS_INACTIVE);
				throw new NoSuchSubjectExceptionFaultMessage(
						SUBJECT_IS_INACTIVE, fault);
			}
			converter.convert(participant, subject,false);
			final ParticipantWrapper wrapper = new ParticipantWrapper(
					participant);
			participantValidator.validateParticipantDetails(wrapper,
					new ExceptionBasedErrorsImpl(wrapper));
			participantRepository.save(participant);
			response.setSubject(converter.convert(participant));

		} catch (ConversionException e) {
			handleInvalidSubjectData(e);
		} catch (ParticipantValidationError e) {
			handleInvalidSubjectData(e);
		} catch (RuntimeException e) {
			handleSubjectBackendProblem(e);
		}
		return response;
	}

	/**
	 * @param e
	 * @throws UnableToCreateOrUpdateSubjectExceptionFaultMessage
	 */
	private void handleSubjectBackendProblem(RuntimeException e)
			throws UnableToCreateOrUpdateSubjectExceptionFaultMessage {
		log.error(ExceptionUtils.getFullStackTrace(e));
		UnableToCreateOrUpdateSubjectExceptionFault fault = new UnableToCreateOrUpdateSubjectExceptionFault();
		fault.setMessage(e.getMessage());
		throw new UnableToCreateOrUpdateSubjectExceptionFaultMessage(e
				.getMessage(), fault);
	}

	/**
	 * @param e
	 * @throws InvalidSubjectDataExceptionFaultMessage
	 */
	private void handleInvalidSubjectData(Exception e)
			throws InvalidSubjectDataExceptionFaultMessage {
		log.error(ExceptionUtils.getFullStackTrace(e));
		InvalidSubjectDataExceptionFault fault = new InvalidSubjectDataExceptionFault();
		fault.setMessage(e.getMessage());
		throw new InvalidSubjectDataExceptionFaultMessage(e.getMessage(), fault);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeedu.duke.cabig.c3pr.webservice.subjectmanagement.SubjectManagement#
	 * updateSubjectState
	 * (edu.duke.cabig.c3pr.webservice.subjectmanagement.UpdateSubjectStateRequest
	 * )
	 */
	public UpdateSubjectStateResponse updateSubjectState(
			UpdateSubjectStateRequest request)
			throws SecurityExceptionFaultMessage,
			InvalidStateTransitionExceptionFaultMessage,
			NoSuchSubjectExceptionFaultMessage {
		UpdateSubjectStateResponse response = new UpdateSubjectStateResponse();
		BiologicEntityIdentifier entityIdentifier = request
				.getBiologicEntityIdentifier();
		ST newState = request.getNewState();
		if (entityIdentifier != null && newState != null) {
			Identifier id = converter
					.convert(entityIdentifier);
			List<Participant> existingList = participantRepository
					.searchByIdentifier(id);
			if (CollectionUtils.isEmpty(existingList)) {
				handleUnexistentSubject();
			}
			Participant participant = existingList.get(0);
			ParticipantStateCode stateCode = ParticipantStateCode
					.getByCode(newState.getValue());
			if (stateCode == null) {
				InvalidStateTransitionExceptionFault fault = new InvalidStateTransitionExceptionFault();
				fault.setMessage(WRONG_SUBJECT_STATE_VALUE);
				throw new InvalidStateTransitionExceptionFaultMessage(
						WRONG_SUBJECT_STATE_VALUE, fault);
			}
			participant.setStateCode(stateCode);
			participantRepository.save(participant);
			response.setSubject(converter.convert(participant));
		} else {
			InvalidStateTransitionExceptionFault fault = new InvalidStateTransitionExceptionFault();
			fault
					.setMessage(MISSING_EITHER_SUBJECT_IDENTIFIER_OR_NEW_STATE_VALUE);
			throw new InvalidStateTransitionExceptionFaultMessage(
					MISSING_EITHER_SUBJECT_IDENTIFIER_OR_NEW_STATE_VALUE, fault);

		}
		return response;
	}

	/**
	 * @throws NoSuchSubjectExceptionFaultMessage
	 */
	private void handleUnexistentSubject()
			throws NoSuchSubjectExceptionFaultMessage {
		NoSuchSubjectExceptionFault fault = new NoSuchSubjectExceptionFault();
		fault.setMessage(SUBJECT_DOES_NOT_EXIST);
		throw new NoSuchSubjectExceptionFaultMessage(SUBJECT_DOES_NOT_EXIST,
				fault);
	}

	public static final class ParticipantValidationError extends RuntimeException {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public ParticipantValidationError() {
			super();
			// TODO Auto-generated constructor stub
		}

		public ParticipantValidationError(String message, Throwable cause) {
			super(message, cause);
			// TODO Auto-generated constructor stub
		}

		public ParticipantValidationError(String message) {
			super(message);
			// TODO Auto-generated constructor stub
		}

		public ParticipantValidationError(Throwable cause) {
			super(cause);
			// TODO Auto-generated constructor stub
		}

	}

	/**
	 * To be able to re-use {@link ParticipantValidator}, we have to provide an
	 * implementation of {@link Errors} because existing ones are not suitable.
	 * 
	 * @author dkrylov
	 * 
	 */
	private static final class ExceptionBasedErrorsImpl implements Errors {

		private ParticipantWrapper participantWrapper;

		public ExceptionBasedErrorsImpl(ParticipantWrapper participantWrapper) {
			super();
			this.participantWrapper = participantWrapper;
		}

		public void addAllErrors(Errors errors) {
			// TODO Auto-generated method stub

		}

		public List getAllErrors() {
			// TODO Auto-generated method stub
			return null;
		}

		public int getErrorCount() {
			// TODO Auto-generated method stub
			return 0;
		}

		public FieldError getFieldError() {
			// TODO Auto-generated method stub
			return null;
		}

		public FieldError getFieldError(String field) {
			// TODO Auto-generated method stub
			return null;
		}

		public int getFieldErrorCount() {
			// TODO Auto-generated method stub
			return 0;
		}

		public int getFieldErrorCount(String field) {
			// TODO Auto-generated method stub
			return 0;
		}

		public List getFieldErrors() {
			// TODO Auto-generated method stub
			return null;
		}

		public List getFieldErrors(String field) {
			// TODO Auto-generated method stub
			return null;
		}

		public Class getFieldType(String field) {
			// TODO Auto-generated method stub
			return null;
		}

		public Object getFieldValue(String field) {
			try {
				return PropertyUtils.getProperty(participantWrapper, field);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		public ObjectError getGlobalError() {
			// TODO Auto-generated method stub
			return null;
		}

		public int getGlobalErrorCount() {
			// TODO Auto-generated method stub
			return 0;
		}

		public List getGlobalErrors() {
			// TODO Auto-generated method stub
			return null;
		}

		public String getNestedPath() {
			// TODO Auto-generated method stub
			return null;
		}

		public String getObjectName() {
			// TODO Auto-generated method stub
			return null;
		}

		public boolean hasErrors() {
			// TODO Auto-generated method stub
			return false;
		}

		public boolean hasFieldErrors() {
			// TODO Auto-generated method stub
			return false;
		}

		public boolean hasFieldErrors(String field) {
			// TODO Auto-generated method stub
			return false;
		}

		public boolean hasGlobalErrors() {
			// TODO Auto-generated method stub
			return false;
		}

		public void popNestedPath() throws IllegalStateException {
			// TODO Auto-generated method stub

		}

		public void pushNestedPath(String subPath) {
			// TODO Auto-generated method stub

		}

		public void reject(String errorCode) {
			throw new ParticipantValidationError(errorCode);
		}

		public void reject(String errorCode, String defaultMessage) {
			throw new ParticipantValidationError(defaultMessage);

		}

		public void reject(String errorCode, Object[] errorArgs,
				String defaultMessage) {
			throw new ParticipantValidationError(defaultMessage);

		}

		public void rejectValue(String field, String errorCode) {
			throw new ParticipantValidationError(errorCode);
		}

		public void rejectValue(String field, String errorCode,
				String defaultMessage) {
			throw new ParticipantValidationError(defaultMessage);

		}

		public void rejectValue(String field, String errorCode,
				Object[] errorArgs, String defaultMessage) {
			throw new ParticipantValidationError(defaultMessage);

		}

		public void setNestedPath(String nestedPath) {
			// TODO Auto-generated method stub

		}

	}

}
