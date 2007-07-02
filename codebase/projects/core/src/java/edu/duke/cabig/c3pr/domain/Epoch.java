package edu.duke.cabig.c3pr.domain;

import gov.nih.nci.cabig.ctms.domain.AbstractMutableDomainObject;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * @author Ram Chilukuri, Priyatam
 */
@Entity
@Table(name = "epochs")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "EPOCHS_ID_SEQ") })
public class Epoch extends AbstractMutableDomainObject implements
		Comparable<Epoch> {

	private String name;

	private String descriptionText;

	private Study study;

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
		// TODO Auto-generated method stub
		return 0;
	}

	/*@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = super.hashCode();
		result = PRIME * result + ((arms == null) ? 0 : arms.hashCode());
		result = PRIME * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Epoch other = (Epoch) obj;
		if (arms == null) {
			if (other.arms != null)
				return false;
		} else if (!arms.equals(other.arms))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}*/

}