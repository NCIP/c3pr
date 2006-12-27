package edu.duke.cabig.c3pr.domain;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.IndexColumn;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.Transient;
import javax.persistence.OneToMany;
import javax.persistence.FetchType;
import java.util.List;
import java.util.ArrayList;


/**
 * @author Ram Chilukuri
 */
@Entity
@Table (name = "epochs")
@GenericGenerator(name="id-generator", strategy = "native",
    parameters = {
        @Parameter(name="sequence", value="seq_epochs_id")
    }
)
public class Epoch extends AbstractDomainObject {
    
    private List<Arm> arms = new ArrayList<Arm>();
    private String name;

    ////// FACTORY

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

    private void addNewArm(String armName) {
        Arm arm = new Arm();
        arm.setName(armName);
        addArm(arm);
    }

    ////// LOGIC

    public void addArm(Arm arm) {
        arms.add(arm);
        arm.setEpoch(this);
    }

    @Transient
    public boolean isMultipleArms() {
        return getArms().size() > 1;
    }

    ////// BEAN PROPERTIES

    // This is annotated this way so that the IndexColumn will work with
    // the bidirectional mapping.  See section 2.4.6.2.3 of the hibernate annotations docs.
    @OneToMany
    @JoinColumn(name="epoch_id", nullable=false)
    @IndexColumn(name="list_index")
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
}
