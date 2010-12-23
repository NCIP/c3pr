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
	
	private String serviceType; 
	
	public Metadata() {
	}
	
	public Metadata(String operationName, String externalId) {
		this.operationName = operationName;
		this.externalIdentifier = externalId;
	}
	
	public Metadata(String operationName, String externalId, String serviceType) {
		this.operationName = operationName;
		this.externalIdentifier = externalId;
		this.serviceType = serviceType;
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

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Metadata [operationName=" + operationName
				+ ", externalIdentifier=" + externalIdentifier
				+ ", serviceType=" + serviceType + "]";
	}

}
