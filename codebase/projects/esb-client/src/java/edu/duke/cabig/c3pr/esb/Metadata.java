package edu.duke.cabig.c3pr.esb;

/**
 * This class a  local representation of the caXchange Metadata class.
 * Its instantiated by the client and then passed to the broadcast method to build the caxchange metadata object
 * 
 * @author Vinay
 */
public class Metadata {

	private String operationName;
	
	private String externalIdentifier;
	
	public Metadata(String operationName, String externalId) {
		this.operationName = operationName;
		this.externalIdentifier = externalId;
	}

	public String getExternalIdentifier() {
		return externalIdentifier;
	}

	public void setExternalIdentifier(String externalIdentifier) {
		this.externalIdentifier = externalIdentifier;
	}

	public String getOperationName() {
		return operationName;
	}

	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}

}
