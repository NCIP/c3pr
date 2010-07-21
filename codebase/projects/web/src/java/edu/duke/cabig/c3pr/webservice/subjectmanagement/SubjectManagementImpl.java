package edu.duke.cabig.c3pr.webservice.subjectmanagement;

import javax.jws.WebService;

/**
 * @author dkrylov
 * 
 */
@WebService(targetNamespace = "http://enterpriseservices.nci.nih.gov/SubjectManagementService", endpointInterface = "edu.duke.cabig.c3pr.webservice.subjectmanagement.SubjectManagement", portName = "SubjectManagement", serviceName = "SubjectManagementService")
public class SubjectManagementImpl implements SubjectManagement {

	public CreateSubjectResponse createSubject(CreateSubjectRequest parameters)
			throws InsufficientPrivilegesExceptionFaultMessage,
			InvalidSubjectDataExceptionFaultMessage,
			SubjectAlreadyExistsExceptionFaultMessage,
			UnableToCreateOrUpdateSubjectExceptionFaultMessage {
		// TODO Auto-generated method stub
		return null;
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

}
