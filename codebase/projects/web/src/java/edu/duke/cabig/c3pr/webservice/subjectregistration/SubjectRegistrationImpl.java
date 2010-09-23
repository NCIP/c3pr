package edu.duke.cabig.c3pr.webservice.subjectregistration;

import javax.jws.WebService;

@WebService(wsdlLocation="/WEB-INF/wsdl/SubjectRegistration.wsdl", targetNamespace = "http://enterpriseservices.nci.nih.gov/SubjectRegistrationService", endpointInterface = "edu.duke.cabig.c3pr.webservice.subjectregistration.SubjectRegistration", portName = "SubjectRegistration", serviceName = "SubjectRegistrationService")
public class SubjectRegistrationImpl implements SubjectRegistration {

	public ChangeStudySubjectEpochAssignmentResponse changeStudySubjectEpochAssignment(
			ChangeStudySubjectEpochAssignmentRequest parameters)
			throws InsufficientPrivilegesExceptionFaultMessage,
			InvalidStudyProtocolExceptionFaultMessage,
			InvalidStudySubjectDataExceptionFaultMessage,
			NoSuchStudySubjectExceptionFaultMessage,
			SubjectRegistrationRejectedExceptionFaultMessage {
		// TODO Auto-generated method stub
		return null;
	}

	public DiscontinueEnrollmentResponse discontinueSubjectEnrollment(
			DiscontinueEnrollmentRequest parameters)
			throws InsufficientPrivilegesExceptionFaultMessage,
			InvalidStudySubjectDataExceptionFaultMessage,
			NoSuchStudySubjectExceptionFaultMessage {
		// TODO Auto-generated method stub
		return null;
	}

	public FailSubjectScreeningResponse failSubjectScreening(
			FailSubjectScreeningRequest parameters)
			throws InsufficientPrivilegesExceptionFaultMessage,
			InvalidStudySubjectDataExceptionFaultMessage,
			NoSuchStudySubjectExceptionFaultMessage {
		// TODO Auto-generated method stub
		return null;
	}

	public GenerateSummary3ReportResponse generateSummary3Report(
			GenerateSummary3ReportRequest parameters)
			throws InsufficientPrivilegesExceptionFaultMessage,
			InvalidSiteExceptionFaultMessage {
		// TODO Auto-generated method stub
		return null;
	}

	public ImportRegistrationsResponse importRegistrations(
			ImportRegistrationsRequest parameters)
			throws InsufficientPrivilegesExceptionFaultMessage,
			InvalidStudySubjectDataExceptionFaultMessage {
		// TODO Auto-generated method stub
		return null;
	}

	public InitiateSubjectEnrollmentResponse initiateSubjectEnrollment(
			InitiateSubjectEnrollmentRequest parameters)
			throws InsufficientPrivilegesExceptionFaultMessage,
			InvalidSiteExceptionFaultMessage,
			InvalidStudyProtocolExceptionFaultMessage,
			InvalidStudySubjectDataExceptionFaultMessage,
			SubjectAlreadyRegisteredExceptionFaultMessage,
			SubjectRegistrationRejectedExceptionFaultMessage {
		// TODO Auto-generated method stub
		return null;
	}

	public QuerySubjectRegistrationResponse querySubjectRegistration(
			QuerySubjectRegistrationRequest parameters)
			throws InsufficientPrivilegesExceptionFaultMessage,
			InvalidQueryExceptionFaultMessage {
		// TODO Auto-generated method stub
		return null;
	}

	public ReconsentStudySubjectResponse reconsentSubject(
			ReconsentStudySubjectRequest parameters)
			throws InsufficientPrivilegesExceptionFaultMessage,
			InvalidStudyProtocolExceptionFaultMessage,
			InvalidStudySubjectDataExceptionFaultMessage,
			NoSuchStudySubjectExceptionFaultMessage {
		// TODO Auto-generated method stub
		return null;
	}

	public RetrieveAccrualDataResponse retrieveAccuralData(
			RetrieveAccrualDataRequest parameters)
			throws InsufficientPrivilegesExceptionFaultMessage,
			InvalidSiteExceptionFaultMessage,
			InvalidStudyProtocolExceptionFaultMessage {
		// TODO Auto-generated method stub
		return null;
	}

	public RetrieveSubjectDemographyHistoryResponse retrieveSubjectDemographyHistory(
			RetrieveSubjectDemographyHistoryRequest parameters)
			throws InsufficientPrivilegesExceptionFaultMessage,
			NoSuchPatientExceptionFaultMessage {
		// TODO Auto-generated method stub
		return null;
	}

	public TakeSubjectOffStudyResponse takeSubjectOffStudy(
			TakeSubjectOffStudyRequest parameters)
			throws InsufficientPrivilegesExceptionFaultMessage,
			InvalidStudySubjectDataExceptionFaultMessage,
			NoSuchStudySubjectExceptionFaultMessage {
		// TODO Auto-generated method stub
		return null;
	}

	public UpdateRegistrationResponse updateSubjectRegistration(
			UpdateRegistrationRequest parameters)
			throws InsufficientPrivilegesExceptionFaultMessage,
			InvalidStudySubjectDataExceptionFaultMessage,
			NoSuchStudySubjectExceptionFaultMessage {
		// TODO Auto-generated method stub
		return null;
	}

}
