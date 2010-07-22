package edu.duke.cabig.c3pr.webservice.subjectmanagement;

import javax.jws.WebService;

import edu.duke.cabig.c3pr.webservice.converters.JAXBToDomainObjectConverter;

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
public class SubjectManagementImpl implements SubjectManagement {

	private JAXBToDomainObjectConverter converter;
	
	public JAXBToDomainObjectConverter getConverter() {
		return converter;
	}

	public void setConverter(JAXBToDomainObjectConverter converter) {
		this.converter = converter;
	}

	public CreateSubjectResponse createSubject(CreateSubjectRequest request)
			throws InsufficientPrivilegesExceptionFaultMessage,
			InvalidSubjectDataExceptionFaultMessage,
			SubjectAlreadyExistsExceptionFaultMessage,
			UnableToCreateOrUpdateSubjectExceptionFaultMessage {
		Subject subject = request.getSubject();
		
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
