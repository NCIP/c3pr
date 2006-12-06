/**
 * 
 */
package edu.duke.cabig.c3pr.dto;

import java.io.Serializable;

/**
 * 
 * @author Priyatam
 *
 */
public class ProtocolSearchCriteria implements Serializable {

	/**
	 * Class to represent the various criteria that can be specified by the application
	 * to search the data store.
	 */
	private static final long serialVersionUID = -7439318496373555959L;
	
	
	private String protocolId;
	private String piLastName;
	private String piFirstName;
	private String protocolTypeCd;
	private String protocolStatusCd;
	private String branchCd;
	private boolean appendWildCardsToNames;
	/**
	 * a String containing the list of protocols that the query results should be narrowed down to.
	 */
	private String protocolFilter;  //
	
	public ProtocolSearchCriteria(){
		super();
	}
	public ProtocolSearchCriteria(String protocolId, String piLastName, String piFirstName, String protocolTypeCd, String protocolStatusCd, String branchCd, boolean useWildCardsForNames, String protocolFilter) {
		super();
		
		this.protocolId = protocolId;
		this.piLastName = piLastName;
		this.piFirstName = piFirstName;
		this.protocolTypeCd = protocolTypeCd;
		this.protocolStatusCd = protocolStatusCd;
		this.branchCd = branchCd;
		this.appendWildCardsToNames = useWildCardsForNames;
		this.protocolFilter = protocolFilter;
	}
	public String getBranchCd() {
		return branchCd;
	}
	public void setBranchCd(String branchCd) {
		this.branchCd = branchCd;
	}
	public String getPiFirstName() {
		return piFirstName;
	}
	public void setPiFirstName(String piFirstName) {
		this.piFirstName = piFirstName;
	}
	public String getPiLastName() {
		return piLastName;
	}
	public void setPiLastName(String piLastName) {
		this.piLastName = piLastName;
	}
	public String getProtocolId() {
		return protocolId;
	}
	public void setProtocolId(String protocolId) {
		this.protocolId = protocolId;
	}
	public String getProtocolStatusCd() {
		return protocolStatusCd;
	}
	public void setProtocolStatusCd(String protocolStatusCd) {
		this.protocolStatusCd = protocolStatusCd;
	}
	public String getProtocolTypeCd() {
		return protocolTypeCd;
	}
	public void setProtocolTypeCd(String protocolTypeCd) {
		this.protocolTypeCd = protocolTypeCd;
	}
	public boolean isAppendWildCardsToNames() {
		return appendWildCardsToNames;
	}
	public void setAppendWildCardsToNames(boolean useWildCardsForNames) {
		this.appendWildCardsToNames = useWildCardsForNames;
	}
	public String getProtocolFilter() {
		return protocolFilter;
	}
	public void setProtocolFilter(String protocolFilter) {
		this.protocolFilter = protocolFilter;
	}

}
