package edu.duke.cabig.c3pr.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

import edu.duke.cabig.c3pr.utils.StringUtils;

@Entity
@DiscriminatorValue(value="NON-TREATMENT")
//@Where(clause="type='NON-TREATMENT'")
public class NonTreatmentEpoch extends Epoch{
	private Integer accrualCeiling;
	private String accrualIndicator;
	private String reservationIndicator;
	private String enrollmentIndicator;
	
	public Integer getAccrualCeiling() {
		return accrualCeiling;
	}
	public void setAccrualCeiling(Integer accrualCeiling) {
		this.accrualCeiling = accrualCeiling;
	}
	public String getAccrualIndicator() {
		return accrualIndicator;
	}
	public void setAccrualIndicator(String accrualIndicator) {
		this.accrualIndicator = accrualIndicator;
	}
	public String getEnrollmentIndicator() {
		return enrollmentIndicator;
	}
	public void setEnrollmentIndicator(String enrollmentIndicator) {
		this.enrollmentIndicator = enrollmentIndicator;
	}
	public String getReservationIndicator() {
		return reservationIndicator;
	}
	public void setReservationIndicator(String reservationIndicator) {
		this.reservationIndicator = reservationIndicator;
	}
	
	@Override
	@Transient
	public boolean isEnrolling() {
		return StringUtils.getBlankIfNull(this.enrollmentIndicator).equalsIgnoreCase("yes");
	}
}
