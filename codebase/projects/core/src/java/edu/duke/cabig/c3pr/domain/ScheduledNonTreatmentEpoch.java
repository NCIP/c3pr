package edu.duke.cabig.c3pr.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
@DiscriminatorValue(value = "NON-TREATMENT")
public class ScheduledNonTreatmentEpoch extends ScheduledEpoch {
	public ScheduledNonTreatmentEpoch() {
		super();
		setScEpochDataEntryStatus(ScheduledEpochDataEntryStatus.COMPLETE);
	}
	
	public ScheduledNonTreatmentEpoch(boolean forExample) {
		super(forExample);
	}
	
	@Transient
	public NonTreatmentEpoch getNonTreatmentEpoch(){
		return (NonTreatmentEpoch)getEpoch();
	}

	public void setNonTreatmentEpoch(NonTreatmentEpoch nonTeatmentEpoch){
		setEpoch(nonTeatmentEpoch);
	}
	
}
