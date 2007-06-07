package edu.duke.cabig.c3pr.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * @author Ram Chilukuri, Priyatam
 */
@Entity
@Table (name = "arms")
@GenericGenerator(name="id-generator", strategy = "native",
    parameters = {
        @Parameter(name="sequence", value="ARMS_ID_SEQ")
    }
)
public class Arm extends AbstractGridIdentifiableDomainObject implements Comparable<Arm> {

	private Epoch epoch;
    private String name;
    private String descriptionText;
    private int targetAccrualNumber;
    
    /// LOGIC
    
    @Transient
    public String getQualifiedName() {
        StringBuilder sb = new StringBuilder();
        sb.append(epoch.getName());
        if (epoch.isMultipleArms()) {
            sb.append(": ").append(getName());
        }
        return sb.toString();
    }

    /// BEAN PROPERTIES

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    

	public String getDescriptionText() {
		return descriptionText;
	}

	public void setDescriptionText(String descriptionText) {
		this.descriptionText = descriptionText;
	}   	

    // This is annotated this way so that the IndexColumn in the parent
    // will work with the bidirectional mapping
    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name="eph_id", nullable=false)   
    public Epoch getEpoch() {
        return epoch;
    }

    public void setEpoch(Epoch epoch) {
        this.epoch = epoch;
    }


    public void setTargetAccrualNumber(int targetAccrualNumber) {
        this.targetAccrualNumber = targetAccrualNumber;
    }

    public int getTargetAccrualNumber() {
        return targetAccrualNumber;
    }
    
	public int compareTo(Arm o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = super.hashCode();
		result = PRIME * result + ((name == null) ? 0 : name.hashCode());
		result = PRIME * result + targetAccrualNumber;
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
		final Arm other = (Arm) obj;		
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (targetAccrualNumber != other.targetAccrualNumber)
			return false;
		return true;
	}
}