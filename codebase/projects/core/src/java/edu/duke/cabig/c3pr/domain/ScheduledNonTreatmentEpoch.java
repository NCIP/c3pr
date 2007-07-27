package edu.duke.cabig.c3pr.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
@DiscriminatorValue(value = "NON-TREATMENT")
public class ScheduledNonTreatmentEpoch extends ScheduledEpoch {
	public ScheduledNonTreatmentEpoch() {
		super();
	}
	@Transient
	public NonTreatmentEpoch getTreatmentEpoch(){
		return (NonTreatmentEpoch)getEpoch();
	}

	public void setNonTreatmentEpoch(NonTreatmentEpoch nonTeatmentEpoch){
		setEpoch(nonTeatmentEpoch);
	}
	
}
