package edu.duke.cabig.c3pr.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * @author Priyatam
 */
public class Amendment extends AbstractDomainObject implements Comparable<Amendment>, Serializable{

	private int identifier;
	private Date irbApprovalDate;
	private Study study;

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(Amendment o) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * @return the identifier
	 */
	public int getIdentifier() {
		return identifier;
	}

	/**
	 * @param identifier the identifier to set
	 */
	public void setIdentifier(int identifier) {
		this.identifier = identifier;
	}

	/**
	 * @return the irbApprovalDate
	 */
	public java.util.Date getIrbApprovalDate() {
		return irbApprovalDate;
	}

	/**
	 * @param irbApprovalDate the irbApprovalDate to set
	 */
	public void setIrbApprovalDate(java.util.Date irbApprovalDate) {
		this.irbApprovalDate = irbApprovalDate;
	}

	/**
	 * @return the study
	 */
	@ManyToOne
	@JoinColumn(name = "study_id")
	public Study getStudy() {
		return study;
	}

	/**
	 * @param study the study to set
	 */
	public void setStudy(Study study) {
		this.study = study;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = super.hashCode();
		result = PRIME * result + identifier;
		result = PRIME * result + ((irbApprovalDate == null) ? 0 : irbApprovalDate.hashCode());
		result = PRIME * result + ((study == null) ? 0 : study.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Amendment other = (Amendment) obj;
		if (identifier != other.identifier)
			return false;
		if (irbApprovalDate == null) {
			if (other.irbApprovalDate != null)
				return false;
		} else if (!irbApprovalDate.equals(other.irbApprovalDate))
			return false;
		if (study == null) {
			if (other.study != null)
				return false;
		} else if (!study.equals(other.study))
			return false;
		return true;
	}

}