/*package edu.duke.cabig.c3pr.domain;

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
    public Epoch getNonTreatmentEpoch() {
        return getEpoch();
    }

    public void setNonTreatmentEpoch(Epoch nonTeatmentEpoch) {
        setEpoch(nonTeatmentEpoch);
    }

    @Override
    public ScheduledEpochDataEntryStatus evaluateScheduledEpochDataEntryStatus(Integer stratumGroupNumber) {
        // TODO Auto-generated method stub
        return ScheduledEpochDataEntryStatus.COMPLETE;
    }

}
*/