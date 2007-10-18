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
	private Boolean accrualIndicator = false;
	private Boolean reservationIndicator = false;
	private Boolean enrollmentIndicator = false;
	
	public Integer getAccrualCeiling() {
		return accrualCeiling;
	}
	public void setAccrualCeiling(Integer accrualCeiling) {
		this.accrualCeiling = accrualCeiling;
	}
	
	public Boolean getAccrualIndicator() {
		return accrualIndicator;
	}
	public void setAccrualIndicator(Boolean accrualIndicator) {
		this.accrualIndicator = accrualIndicator;
	}
	public Boolean getEnrollmentIndicator() {
		return enrollmentIndicator;
	}
	public void setEnrollmentIndicator(Boolean enrollmentIndicator) {
		this.enrollmentIndicator = enrollmentIndicator;
	}
	public Boolean getReservationIndicator() {
		return reservationIndicator;
	}
	public void setReservationIndicator(Boolean reservationIndicator) {
		this.reservationIndicator = reservationIndicator;
	}
	@Override
	@Transient
	public boolean isEnrolling() {
		return this.enrollmentIndicator;
	}
	
	@Override
	@Transient
	public boolean getRequiresRandomization() {
		return false;
	}
}
