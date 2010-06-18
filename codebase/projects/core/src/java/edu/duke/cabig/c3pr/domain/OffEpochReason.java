package edu.duke.cabig.c3pr.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name = "off_epoch_reasons")
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "off_epoch_reasons_id_seq") })
public class OffEpochReason extends AbstractMutableDeletableDomainObject{

	private Reason reason;
	
	private String description;

	@ManyToOne
    @JoinColumn(name = "reason_id")
    @Cascade( { CascadeType.LOCK})
	public Reason getReason() {
		return reason;
	}

	public void setReason(Reason reason) {
		this.reason = reason;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public int hashCode() {
		int result =1;
		result = 29*result + (getReason() != null ? getReason().hashCode() : 0);
		return result;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		final OffEpochReason that = (OffEpochReason) o;

		if (getReason() != null ? !getReason().equals(that.getReason())
				: that.getReason() != null)
			return false;
		return true;
	}
}