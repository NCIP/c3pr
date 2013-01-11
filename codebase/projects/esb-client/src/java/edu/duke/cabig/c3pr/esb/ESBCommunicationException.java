/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
 ******************************************************************************/
/**
 * 
 */
package edu.duke.cabig.c3pr.esb;

/**
 * The reason for introducing this specific subclass is to be able to
 * distinguish and indicate <b>recoverable</b> errors when communicating with iHub.
 * 
 * @author Denis G. Krylov
 * 
 */
public class ESBCommunicationException extends BroadcastException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public ESBCommunicationException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param string
	 */
	public ESBCommunicationException(String string) {
		super(string);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param string
	 * @param throwable
	 */
	public ESBCommunicationException(String string, Throwable throwable) {
		super(string, throwable);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param throwable
	 */
	public ESBCommunicationException(Throwable throwable) {
		super(throwable);
		// TODO Auto-generated constructor stub
	}

}
