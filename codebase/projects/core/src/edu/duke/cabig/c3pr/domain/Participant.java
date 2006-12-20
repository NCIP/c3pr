package edu.duke.cabig.c3pr.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * @author Kulasekaran
 * @version 1.0 
 * 
 */
@Entity 
@Table (name = "PARTICIPANT")
@GenericGenerator (name="id-generator", strategy = "native",
		parameters = {
			@Parameter(name="sequence", value="C3PR_GENERAL_SEQ")
		}
)
public class Participant extends Person implements Serializable
{
	@Column(name = "CONFIDENTIALITY_INDICATOR", nullable = false)
	private boolean confidentialityIndicator;
	
	@Column(name = "INITIALS", length=1, nullable = false)
	private char initials;
	
	@Column(name = "PAYMENT_METHOD_CODE", length=1, nullable = false)
	private char paymentMethodCode;
	
	@Column(name = "ZIP_CODE", length=10, nullable = false)
	private String zipCode;
	
  	public boolean isConfidentialityIndicator() {
		return confidentialityIndicator;
	}

	public void setConfidentialityIndicator(boolean confidentialityIndicator) {
		this.confidentialityIndicator = confidentialityIndicator;
	}

	public char getInitials() {
		return initials;
	}

	public void setInitials(char initials) {
		this.initials = initials;
	}

	public char getPaymentMethodCode() {
		return paymentMethodCode;
	}

	public void setPaymentMethodCode(char paymentMethodCode) {
		this.paymentMethodCode = paymentMethodCode;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	/**
     * Simple constructor of Participant instances.
     */
    public Participant()
    {
    }
    
	    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L; 
}

