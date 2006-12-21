package edu.duke.cabig.c3pr.domain;

import java.io.Serializable;

public class Amendment extends AbstractDomainObject implements Comparable<Amendment>, Serializable {

	public int compareTo(Amendment o) {
		// TODO Auto-generated method stub
		return 0;
	}
	private int identifier;
	private String irbApprovalDate;
	public int getIdentifier() {
		return identifier;
	}
	public void setIdentifier(int identifier) {
		this.identifier = identifier;
	}
	public String getIrbApprovalDate() {
		return irbApprovalDate;
	}
	public void setIrbApprovalDate(String irbApprovalDate) {
		this.irbApprovalDate = irbApprovalDate;
	}
	public int compareTo(EligibilityCriteria o) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return super.equals(obj);
	}
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
	
}
