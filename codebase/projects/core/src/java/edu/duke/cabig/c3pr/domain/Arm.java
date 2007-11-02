package edu.duke.cabig.c3pr.domain;

import edu.duke.cabig.c3pr.domain.AbstractMutableDeletableDomainObject;

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
public class Arm extends AbstractMutableDeletableDomainObject implements Comparable<Arm> {

    private TreatmentEpoch treatmentEpoch;
    private String name;
    private String descriptionText;
    private Integer targetAccrualNumber;

    /// LOGIC

    @Override
    @Transient
    public void setRetiredIndicatorAsTrue(){
    	super.setRetiredIndicatorAsTrue();  
    }
    
    @Transient
    public String getQualifiedName() {
        StringBuilder sb = new StringBuilder();
        sb.append(treatmentEpoch.getName());
        if (treatmentEpoch.isMultipleArms()) {
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
    @ManyToOne
    @JoinColumn(name="eph_id")
    public TreatmentEpoch getTreatmentEpoch() {
        return treatmentEpoch;
    }

    public void setTreatmentEpoch(TreatmentEpoch epoch) {
        this.treatmentEpoch = epoch;
    }


    public void setTargetAccrualNumber(Integer targetAccrualNumber) {
        this.targetAccrualNumber = targetAccrualNumber;
    }

    public Integer getTargetAccrualNumber() {
        return targetAccrualNumber;
    }

    public int compareTo(Arm o) {
    	if (this.equals(o)) return 0;
		else return 1;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = PRIME * result + ((name == null) ? 0 : name.hashCode());
        result = PRIME * result;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
       if (getClass() != obj.getClass())
            return false;
        final Arm other = (Arm) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equalsIgnoreCase(other.name))
            return false;
        return true;
    }
}