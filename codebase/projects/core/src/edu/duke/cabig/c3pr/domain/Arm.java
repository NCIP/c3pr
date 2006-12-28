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
 * @author Ram Chilukuri, priyatam
 */
@Entity
@Table (name = "arms")
@GenericGenerator(name="id-generator", strategy = "native",
    parameters = {
        @Parameter(name="sequence", value="ARMS_ID_SEQ")
    }
)
public class Arm extends AbstractDomainObject implements Comparable<Arm>, Serializable {
    /* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(Arm o) {
		// TODO Auto-generated method stub
		return 0;
	}

	private Epoch epoch;
    private String name;
    private int targetAccrualNumber;
    
    // business methods
    
    @Transient
    public String getQualifiedName() {
        StringBuilder sb = new StringBuilder();
        sb.append(epoch.getName());
        if (epoch.isMultipleArms()) {
            sb.append(": ").append(getName());
        }
        return sb.toString();
    }

    // bean methods

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // This is annotated this way so that the IndexColumn in the parent
    // will work with the bidirectional mapping
    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(insertable=false, updatable=false, nullable=false)
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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = super.hashCode();
		result = PRIME * result + ((epoch == null) ? 0 : epoch.hashCode());
		result = PRIME * result + ((name == null) ? 0 : name.hashCode());
		result = PRIME * result + targetAccrualNumber;
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Arm other = (Arm) obj;
		if (epoch == null) {
			if (other.epoch != null)
				return false;
		} else if (!epoch.equals(other.epoch))
			return false;
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
