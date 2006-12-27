package edu.duke.cabig.c3pr.domain;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.SortedSet;
import java.util.TreeSet;


/**
 * @author Ram Chilukuri
 */
@Entity
@Table (name = "arms")
@GenericGenerator(name="id-generator", strategy = "native",
    parameters = {
        @Parameter(name="sequence", value="seq_arms_id")
    }
)
public class Arm extends AbstractDomainObject  {
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
}
