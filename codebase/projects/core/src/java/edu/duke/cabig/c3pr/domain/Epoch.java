package edu.duke.cabig.c3pr.domain;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * @author Ram Chilukuri, Priyatam
 */
@Entity

@Table(name = "epochs",uniqueConstraints = {@UniqueConstraint(columnNames={"stu_id", "name"})})
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "EPOCHS_ID_SEQ") })
public abstract class Epoch extends AbstractMutableDeletableDomainObject implements
		Comparable<Epoch> {

	private String name;

	private String descriptionText;

	private Study study;
	
	private Integer epochOrder;

	/**
	 * Factory method
	 * 
	 * @param epochName
	 * @param armNames
	 * @return
	 */
	public static Epoch create(String epochName, String... armNames) {

		return createTreatmentEpoch(epochName, armNames);
	}

	public static Epoch createTreatmentEpoch(String epochName,
			String... armNames) {
		TreatmentEpoch epoch = new TreatmentEpoch();
		epoch.setName(epochName);
		if (armNames.length == 0) {
			epoch.addNewArm(epochName);
		} else {
			for (String armName : armNames) {
				epoch.addNewArm(armName);
			}
		}
		return epoch;
	}

	public static Epoch createNonTreatmentEpoch(String epochName) {
		Epoch epoch = new NonTreatmentEpoch();
		epoch.setName(epochName);
		return epoch;
	}
	
	@Transient
	public boolean getRequiresArm(){
		if (this instanceof TreatmentEpoch) {
			TreatmentEpoch epoch = (TreatmentEpoch) this;
			return this.getRequiresRandomization()||epoch.getArms().size()>0;
		}
		return false;
	}
	
	@Transient
	public boolean isReserving(){
		if (this instanceof NonTreatmentEpoch) {
			NonTreatmentEpoch epoch = (NonTreatmentEpoch) this;
			return epoch.getReservationIndicator();
		}
		return false;
	}
	@Transient
	public abstract boolean getRequiresRandomization();
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "stu_id", nullable = false)
	public Study getStudy() {
		return study;
	}

	public void setStudy(Study study) {
		this.study = study;
	}

	public void setDescriptionText(String descriptionText) {
		this.descriptionText = descriptionText;
	}

	public String getDescriptionText() {
		return descriptionText;
	}

	public int compareTo(Epoch o) {
		if (this.equals(o)) return 0;
		else return 1;
	}
	
	@Transient
	public abstract boolean isEnrolling();
	
	 @Override public int hashCode() { final int PRIME = 31; int result =
	  super.hashCode(); result = PRIME * result;
	  result = PRIME * result + ((name == null) ? 0 :
	  name.hashCode()); return result; }
	  
	  @Override public boolean equals(Object obj) { if (this == obj) return
	  true; final Epoch other = (Epoch) obj; if (name == null) { if
	  (other.name != null) return false; } else if (!name.equalsIgnoreCase(other.name))
	  return false; return true; }

	public Integer getEpochOrder() {
		return epochOrder;
	}

	public void setEpochOrder(Integer epochOrder) {
		this.epochOrder = epochOrder;
	}
	 

}