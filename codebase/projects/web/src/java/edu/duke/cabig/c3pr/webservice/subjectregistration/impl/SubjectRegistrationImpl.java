package edu.duke.cabig.c3pr.webservice.subjectregistration.impl;

import javax.jws.WebService;

import edu.duke.cabig.c3pr.webservice.subjectregistration.ChangeStudySubjectEpochAssignmentRequest;
import edu.duke.cabig.c3pr.webservice.subjectregistration.ChangeStudySubjectEpochAssignmentResponse;
import edu.duke.cabig.c3pr.webservice.subjectregistration.DiscontinueEnrollmentRequest;
import edu.duke.cabig.c3pr.webservice.subjectregistration.DiscontinueEnrollmentResponse;
import edu.duke.cabig.c3pr.webservice.subjectregistration.FailSubjectScreeningRequest;
import edu.duke.cabig.c3pr.webservice.subjectregistration.FailSubjectScreeningResponse;
import edu.duke.cabig.c3pr.webservice.subjectregistration.GenerateSummary3ReportRequest;
import edu.duke.cabig.c3pr.webservice.subjectregistration.GenerateSummary3ReportResponse;
import edu.duke.cabig.c3pr.webservice.subjectregistration.ImportRegistrationsRequest;
import edu.duke.cabig.c3pr.webservice.subjectregistration.ImportRegistrationsResponse;
import edu.duke.cabig.c3pr.webservice.subjectregistration.InitiateSubjectEnrollmentRequest;
import edu.duke.cabig.c3pr.webservice.subjectregistration.InitiateSubjectEnrollmentResponse;
import edu.duke.cabig.c3pr.webservice.subjectregistration.InsufficientPrivilegesExceptionFaultMessage;
import edu.duke.cabig.c3pr.webservice.subjectregistration.QuerySubjectRegistrationRequest;
import edu.duke.cabig.c3pr.webservice.subjectregistration.QuerySubjectRegistrationResponse;
import edu.duke.cabig.c3pr.webservice.subjectregistration.ReconsentStudySubjectRequest;
import edu.duke.cabig.c3pr.webservice.subjectregistration.ReconsentStudySubjectResponse;
import edu.duke.cabig.c3pr.webservice.subjectregistration.RetrieveAccrualDataRequest;
import edu.duke.cabig.c3pr.webservice.subjectregistration.RetrieveAccrualDataResponse;
import edu.duke.cabig.c3pr.webservice.subjectregistration.RetrieveSubjectDemographyHistoryRequest;
import edu.duke.cabig.c3pr.webservice.subjectregistration.RetrieveSubjectDemographyHistoryResponse;
import edu.duke.cabig.c3pr.webservice.subjectregistration.SubjectAlreadyRegisteredExceptionFaultMessage;
import edu.duke.cabig.c3pr.webservice.subjectregistration.SubjectRegistration;
import edu.duke.cabig.c3pr.webservice.subjectregistration.SubjectRegistrationRejectedExceptionFaultMessage;
import edu.duke.cabig.c3pr.webservice.subjectregistration.TakeSubjectOffStudyRequest;
import edu.duke.cabig.c3pr.webservice.subjectregistration.TakeSubjectOffStudyResponse;
import edu.duke.cabig.c3pr.webservice.subjectregistration.UpdateRegistrationRequest;
import edu.duke.cabig.c3pr.webservice.subjectregistration.UpdateRegistrationResponse;

@WebService(wsdlLocation="/WEB-INF/wsdl/SubjectRegistration.wsdl", targetNamespace = "http://enterpriseservices.nci.nih.gov/SubjectRegistrationService", endpointInterface = "edu.duke.cabig.c3pr.webservice.subjectregistration.SubjectRegistration", portName = "SubjectRegistration", serviceName = "SubjectRegistrationService")
public class SubjectRegistrationImpl implements SubjectRegistration {

	public ChangeStudySubjectEpochAssignmentResponse changeStudySubjectEpochAssignment(
			ChangeStudySubjectEpochAssignmentRequest arg0)
			throws InsufficientPrivilegesExceptionFaultMessage,
			edu.duke.cabig.c3pr.webservice.subjectregistration.InvalidStudyProtocolExceptionFaultMessage,
			edu.duke.cabig.c3pr.webservice.subjectregistration.InvalidStudySubjectDataExceptionFaultMessage,
			edu.duke.cabig.c3pr.webservice.subjectregistration.NoSuchStudySubjectExceptionFaultMessage,
			SubjectRegistrationRejectedExceptionFaultMessage {
		// TODO Auto-generated method stub
		return null;
	}

	public DiscontinueEnrollmentResponse discontinueSubjectEnrollment(
			DiscontinueEnrollmentRequest arg0)
			throws InsufficientPrivilegesExceptionFaultMessage,
			edu.duke.cabig.c3pr.webservice.subjectregistration.InvalidStudySubjectDataExceptionFaultMessage,
			edu.duke.cabig.c3pr.webservice.subjectregistration.NoSuchStudySubjectExceptionFaultMessage {
		// TODO Auto-generated method stub
		return null;
	}

	public FailSubjectScreeningResponse failSubjectScreening(
			FailSubjectScreeningRequest arg0)
			throws InsufficientPrivilegesExceptionFaultMessage,
			edu.duke.cabig.c3pr.webservice.subjectregistration.InvalidStudySubjectDataExceptionFaultMessage,
			edu.duke.cabig.c3pr.webservice.subjectregistration.NoSuchStudySubjectExceptionFaultMessage {
		// TODO Auto-generated method stub
		return null;
	}

	public GenerateSummary3ReportResponse generateSummary3Report(
			GenerateSummary3ReportRequest arg0)
			throws InsufficientPrivilegesExceptionFaultMessage,
			edu.duke.cabig.c3pr.webservice.subjectregistration.InvalidSiteExceptionFaultMessage {
		// TODO Auto-generated method stub
		return null;
	}

	public ImportRegistrationsResponse importRegistrations(
			ImportRegistrationsRequest arg0)
			throws InsufficientPrivilegesExceptionFaultMessage,
			edu.duke.cabig.c3pr.webservice.subjectregistration.InvalidStudySubjectDataExceptionFaultMessage {
		// TODO Auto-generated method stub
		return null;
	}

	public InitiateSubjectEnrollmentResponse initiateSubjectEnrollment(
			InitiateSubjectEnrollmentRequest arg0)
			throws InsufficientPrivilegesExceptionFaultMessage,
			edu.duke.cabig.c3pr.webservice.subjectregistration.InvalidSiteExceptionFaultMessage,
			edu.duke.cabig.c3pr.webservice.subjectregistration.InvalidStudyProtocolExceptionFaultMessage,
			edu.duke.cabig.c3pr.webservice.subjectregistration.InvalidStudySubjectDataExceptionFaultMessage,
			SubjectAlreadyRegisteredExceptionFaultMessage,
			SubjectRegistrationRejectedExceptionFaultMessage {
		// TODO Auto-generated method stub
		return null;
	}

	public QuerySubjectRegistrationResponse querySubjectRegistration(
			QuerySubjectRegistrationRequest arg0)
			throws InsufficientPrivilegesExceptionFaultMessage,
			edu.duke.cabig.c3pr.webservice.subjectregistration.InvalidQueryExceptionFaultMessage {
		// TODO Auto-generated method stub
		return null;
	}

	public ReconsentStudySubjectResponse reconsentSubject(
			ReconsentStudySubjectRequest arg0)
			throws InsufficientPrivilegesExceptionFaultMessage,
			edu.duke.cabig.c3pr.webservice.subjectregistration.InvalidStudyProtocolExceptionFaultMessage,
			edu.duke.cabig.c3pr.webservice.subjectregistration.InvalidStudySubjectDataExceptionFaultMessage,
			edu.duke.cabig.c3pr.webservice.subjectregistration.NoSuchStudySubjectExceptionFaultMessage {
		// TODO Auto-generated method stub
		return null;
	}

	public RetrieveAccrualDataResponse retrieveAccuralData(
			RetrieveAccrualDataRequest arg0)
			throws InsufficientPrivilegesExceptionFaultMessage,
			edu.duke.cabig.c3pr.webservice.subjectregistration.InvalidSiteExceptionFaultMessage,
			edu.duke.cabig.c3pr.webservice.subjectregistration.InvalidStudyProtocolExceptionFaultMessage {
		// TODO Auto-generated method stub
		return null;
	}

	public RetrieveSubjectDemographyHistoryResponse retrieveSubjectDemographyHistory(
			RetrieveSubjectDemographyHistoryRequest arg0)
			throws InsufficientPrivilegesExceptionFaultMessage,
			edu.duke.cabig.c3pr.webservice.subjectregistration.NoSuchPatientExceptionFaultMessage {
		// TODO Auto-generated method stub
		return null;
	}

	public TakeSubjectOffStudyResponse takeSubjectOffStudy(
			TakeSubjectOffStudyRequest arg0)
			throws InsufficientPrivilegesExceptionFaultMessage,
			edu.duke.cabig.c3pr.webservice.subjectregistration.InvalidStudySubjectDataExceptionFaultMessage,
			edu.duke.cabig.c3pr.webservice.subjectregistration.NoSuchStudySubjectExceptionFaultMessage {
		// TODO Auto-generated method stub
		return null;
	}

	public UpdateRegistrationResponse updateSubjectRegistration(
			UpdateRegistrationRequest arg0)
			throws InsufficientPrivilegesExceptionFaultMessage,
			edu.duke.cabig.c3pr.webservice.subjectregistration.InvalidStudySubjectDataExceptionFaultMessage,
			edu.duke.cabig.c3pr.webservice.subjectregistration.NoSuchStudySubjectExceptionFaultMessage {
		// TODO Auto-generated method stub
		return null;
	}

	

}
