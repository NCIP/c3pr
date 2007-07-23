package edu.duke.cabig.c3pr.domain;

import gov.nih.nci.cabig.ctms.collections.LazyListHelper;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.apache.commons.collections15.functors.InstantiateFactory;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Where;

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
