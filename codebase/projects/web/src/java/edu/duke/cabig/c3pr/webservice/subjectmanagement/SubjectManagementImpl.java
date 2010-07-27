package edu.duke.cabig.c3pr.webservice.subjectmanagement;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.jws.HandlerChain;
import javax.jws.WebService;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.repository.ParticipantRepository;
import edu.duke.cabig.c3pr.domain.validator.ParticipantValidator;
import edu.duke.cabig.c3pr.exception.ConversionException;
import edu.duke.cabig.c3pr.web.participant.ParticipantWrapper;
import edu.duke.cabig.c3pr.webservice.converters.JAXBToDomainObjectConverter;
import edu.duke.cabig.c3pr.webservice.converters.JAXBToDomainObjectConverterImpl;

/**
 * Implementation of the Subject Management web service.
 * 
 * @see https 
 *      ://ncisvn.nci.nih.gov/svn/c3pr/trunk/c3prv2/documentation/design/essn
 *      /SubjectManagement/PSM_SS_Subject_Management.doc
 * @author dkrylov
 * 
 */
@WebService(targetNamespace = "http://enterpriseservices.nci.nih.gov/SubjectManagementService", endpointInterface = "edu.duke.cabig.c3pr.webservice.subjectmanagement.SubjectManagement", portName = "SubjectManagement", serviceName = "SubjectManagementService")
@HandlerChain(file="/ws-handlers.xml")
public class SubjectManagementImpl implements SubjectManagement {

	private static Log log = LogFactory
			.getLog(SubjectManagementImpl.class);

	private JAXBToDomainObjectConverter converter;

	private ParticipantValidator participantValidator;
	
	private ParticipantRepository participantRepository;

	public ParticipantRepository getParticipantRepository() {
		return participantRepository;
	}

	public void setParticipantRepository(ParticipantRepository participantRepository) {
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

	/* (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.webservice.subjectmanagement.SubjectManagement#createSubject(edu.duke.cabig.c3pr.webservice.subjectmanagement.CreateSubjectRequest)
	 */
	public CreateSubjectResponse createSubject(CreateSubjectRequest request)
			throws InsufficientPrivilegesExceptionFaultMessage,
			InvalidSubjectDataExceptionFaultMessage,
			SubjectAlreadyExistsExceptionFaultMessage,
			UnableToCreateOrUpdateSubjectExceptionFaultMessage {
		try {
			Subject subject = request.getSubject();
			Participant participant = converter.convert(subject);
			final ParticipantWrapper wrapper = new ParticipantWrapper(participant);
			participantValidator.validate(wrapper , new ExceptionBasedErrorsImpl(wrapper));
			participantRepository.save(participant);
			CreateSubjectResponse response = new CreateSubjectResponse();
			response.setSubject(subject);
			return response;
		} catch (ConversionException e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			InvalidSubjectDataExceptionFault fault = new InvalidSubjectDataExceptionFault(
					e.getMessage());
			throw new InvalidSubjectDataExceptionFaultMessage(e.getMessage(),
					fault);
		} catch (ParticipantValidationError e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			InvalidSubjectDataExceptionFault fault = new InvalidSubjectDataExceptionFault(
					e.getMessage());
			throw new InvalidSubjectDataExceptionFaultMessage(e.getMessage(),
					fault);
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			UnableToCreateOrUpdateSubjectExceptionFault fault = new UnableToCreateOrUpdateSubjectExceptionFault();
			fault.setMessage(e.getMessage());
			throw new UnableToCreateOrUpdateSubjectExceptionFaultMessage(e
					.getMessage(), fault);
		}

	}

	public QuerySubjectResponse querySubject(QuerySubjectRequest parameters)
			throws InsufficientPrivilegesExceptionFaultMessage,
			InvalidSubjectDataExceptionFaultMessage {
		// TODO Auto-generated method stub
		return null;
	}

	public UpdateSubjectResponse updateSubject(UpdateSubjectRequest parameters)
			throws InsufficientPrivilegesExceptionFaultMessage,
			InvalidSubjectDataExceptionFaultMessage,
			NoSuchSubjectExceptionFaultMessage,
			UnableToCreateOrUpdateSubjectExceptionFaultMessage {
		// TODO Auto-generated method stub
		return null;
	}

	public UpdateSubjectStateResponse updateSubjectState(
			UpdateSubjectStateRequest parameters)
			throws InsufficientPrivilegesExceptionFaultMessage,
			InvalidStateTransitionExceptionFaultMessage,
			NoSuchSubjectExceptionFaultMessage {
		// TODO Auto-generated method stub
		return null;
	}

	private static final class ParticipantValidationError extends
			RuntimeException {

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
