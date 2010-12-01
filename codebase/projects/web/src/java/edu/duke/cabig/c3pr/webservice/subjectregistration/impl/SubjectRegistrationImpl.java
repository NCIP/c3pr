package edu.duke.cabig.c3pr.webservice.subjectregistration.impl;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebService;

import org.apache.commons.lang.exception.ExceptionUtils;

import com.semanticbits.querybuilder.AdvancedSearchCriteriaParameter;

import edu.duke.cabig.c3pr.dao.StudySubjectDao;
import edu.duke.cabig.c3pr.exception.ConversionException;
import edu.duke.cabig.c3pr.webservice.common.AdvanceSearchCriterionParameter;
import edu.duke.cabig.c3pr.webservice.common.InvalidQueryExceptionFault;
import edu.duke.cabig.c3pr.webservice.subjectregistration.ChangeStudySubjectEpochAssignmentRequest;
import edu.duke.cabig.c3pr.webservice.subjectregistration.ChangeStudySubjectEpochAssignmentResponse;
import edu.duke.cabig.c3pr.webservice.subjectregistration.DSETStudySubject;
import edu.duke.cabig.c3pr.webservice.subjectregistration.DiscontinueEnrollmentRequest;
import edu.duke.cabig.c3pr.webservice.subjectregistration.DiscontinueEnrollmentResponse;
import edu.duke.cabig.c3pr.webservice.subjectregistration.DuplicateStudySubjectExceptionFaultMessage;
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
import edu.duke.cabig.c3pr.webservice.subjectregistration.QuerySubjectRegistrationRequest;
import edu.duke.cabig.c3pr.webservice.subjectregistration.QuerySubjectRegistrationResponse;
import edu.duke.cabig.c3pr.webservice.subjectregistration.ReconsentStudySubjectRequest;
import edu.duke.cabig.c3pr.webservice.subjectregistration.ReconsentStudySubjectResponse;
import edu.duke.cabig.c3pr.webservice.subjectregistration.RetrieveAccrualDataRequest;
import edu.duke.cabig.c3pr.webservice.subjectregistration.RetrieveAccrualDataResponse;
import edu.duke.cabig.c3pr.webservice.subjectregistration.RetrieveSubjectDemographyHistoryRequest;
import edu.duke.cabig.c3pr.webservice.subjectregistration.RetrieveSubjectDemographyHistoryResponse;
import edu.duke.cabig.c3pr.webservice.subjectregistration.SecurityExceptionFaultMessage;
import edu.duke.cabig.c3pr.webservice.subjectregistration.SubjectRegistration;
import edu.duke.cabig.c3pr.webservice.subjectregistration.SubjectRegistrationRejectedExceptionFaultMessage;
import edu.duke.cabig.c3pr.webservice.subjectregistration.TakeSubjectOffStudyRequest;
import edu.duke.cabig.c3pr.webservice.subjectregistration.TakeSubjectOffStudyResponse;
import edu.duke.cabig.c3pr.webservice.subjectregistration.UpdateRegistrationRequest;
import edu.duke.cabig.c3pr.webservice.subjectregistration.UpdateRegistrationResponse;
import edu.duke.cabig.c3pr.webservice.subjectregistration.coverters.SubjectRegistrationJAXBToDomainObjectConverter;

@WebService(wsdlLocation="/WEB-INF/wsdl/SubjectRegistration.wsdl", targetNamespace = "http://enterpriseservices.nci.nih.gov/SubjectRegistrationService", endpointInterface = "edu.duke.cabig.c3pr.webservice.subjectregistration.SubjectRegistration", portName = "SubjectRegistration", serviceName = "SubjectRegistrationService")
public class SubjectRegistrationImpl implements SubjectRegistration {
	/**
	 * Logger for this class
	 */
	private static final Logger log = Logger
			.getLogger(SubjectRegistrationImpl.class);
	private SubjectRegistrationJAXBToDomainObjectConverter converter;
	private StudySubjectDao studySubjectDao;

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

	public InitiateSubjectEnrollmentResponse initiateSubjectEnrollment(
			InitiateSubjectEnrollmentRequest arg0)
			throws DuplicateStudySubjectExceptionFaultMessage,
			InvalidSiteExceptionFaultMessage,
			InvalidStudyProtocolExceptionFaultMessage,
			InvalidStudySubjectDataExceptionFaultMessage,
			SecurityExceptionFaultMessage,
			SubjectRegistrationRejectedExceptionFaultMessage {
		// TODO Auto-generated method stub
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
				studySubjects.getItem().add(converter.convert(result));
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
	
	public void setConverter(
			SubjectRegistrationJAXBToDomainObjectConverter converter) {
		this.converter = converter;
	}

	public void setStudySubjectDao(StudySubjectDao studySubjectDao) {
		this.studySubjectDao = studySubjectDao;
	}
}
