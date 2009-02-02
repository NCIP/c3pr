package edu.duke.cabig.c3pr.domain;

import gov.nih.nci.cabig.ctms.domain.AbstractMutableDomainObject;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;



@Entity
@Table(name = "consent_history")
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "CONSENT_HISTORY_ID_SEQ") })
public class ConsentHistory extends AbstractMutableDomainObject implements Comparable<ConsentHistory> {

	public int compareTo(ConsentHistory consentHistory) {
		return this.consentSignedDate.compareTo(consentHistory.getConsentSignedDate());
	}
	
	private Integer consentVersion ;
	private Date consentSignedDate ;
	
	public Integer getConsentVersion() {
		return consentVersion;
	}
	public void setConsentVersion(Integer consentVesrion) {
		this.consentVersion = consentVesrion;
	}
	public Date getConsentSignedDate() {
		return consentSignedDate;
	}
	public void setConsentSignedDate(Date consentSignedDate) {
		this.consentSignedDate = consentSignedDate;
	}
	

}
