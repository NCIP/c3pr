package edu.duke.cabig.c3pr.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;


/**
 * @author Ram Chilukuri, Priyatam
 */
@Entity
@Table (name = "epochs")
@GenericGenerator(name="id-generator", strategy = "native",
    parameters = {
        @Parameter(name="sequence", value="EPOCHS_ID_SEQ")
    }
)
public class Epoch extends AbstractGridIdentifiableDomainObject implements Comparable<Epoch>{  
  
    private List<Arm> arms = new ArrayList<Arm>();
    private String name;
    private String descriptionText;	
	private Study study;
	
    /**
     * Factory method
     * @param epochName
     * @param armNames
     * @return
     */
    public static Epoch create(String epochName, String... armNames) {
        Epoch epoch = new Epoch();
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

    /// LOGIC
    
    private void addNewArm(String armName) {
        Arm arm = new Arm();
        arm.setName(armName);
        addArm(arm);
    }

    public void addArm(Arm arm) {
        arms.add(arm);
        arm.setEpoch(this);
    }

    @Transient
    public boolean isMultipleArms() {
        return getArms().size() > 1;
    }
    
    /// BEAN PROPERTIES

    @OneToMany(mappedBy="epoch", fetch=FetchType.LAZY)
    @Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN})   
    public List<Arm> getArms() {
        return arms;
    }

    public void setArms(List<Arm> arms) {
        this.arms = arms;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name="stu_id", nullable=false)
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
		
	@Override
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
	}
	
}