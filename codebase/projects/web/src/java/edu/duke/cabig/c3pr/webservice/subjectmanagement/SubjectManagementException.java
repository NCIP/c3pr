package edu.duke.cabig.c3pr.webservice.subjectmanagement;

/**
 * Base class for exceptions related to Subject Management web service.
 * @author dkrylov
 *
 */
public class SubjectManagementException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5912404430120902302L;

	public SubjectManagementException() {
		// TODO Auto-generated constructor stub
	}

	public SubjectManagementException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public SubjectManagementException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public SubjectManagementException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

}
