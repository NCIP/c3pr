package edu.duke.cabig.c3pr.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.semanticbits.coppa.domain.annotations.RemoteProperty;

/**
 * The Class ResearchStaff.
 * 
 * @author Priyatam, Vinay G
 */
@Entity
@Table(name = "research_staff")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "research_staff_id_seq") })
public abstract class ResearchStaff extends User {

    private String nciIdentifier;
    
    private HealthcareSite healthcareSite;
    
    private List<UserBasedRecipient> userBasedRecipient;
    
	public ResearchStaff() {
		super();
	}
	
    @ManyToOne
    @JoinColumn(name = "HCS_ID")
    public HealthcareSite getHealthcareSite() {
        return healthcareSite;
    }

    public void setHealthcareSite(HealthcareSite healthcareSite) {
        this.healthcareSite = healthcareSite;
    }
    

    @OneToMany
    @Cascade(value = { CascadeType.LOCK})
    @JoinColumn(name = "research_staff_id")
	public List<UserBasedRecipient> getUserBasedRecipient() {
		return userBasedRecipient;
	}

	public void setUserBasedRecipient(List<UserBasedRecipient> userBasedRecipient) {
		this.userBasedRecipient = userBasedRecipient;
	}
	
    public int compareTo(Object o) {
        if (this.equals((ResearchStaff) o)) return 0;
        else return 1;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        // int result = super.hashCode();
        int result = 1;
        result = PRIME * result + ((nciIdentifier == null) ? 0 : nciIdentifier.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (getClass() != obj.getClass()) return false;
        final ResearchStaff other = (ResearchStaff) obj;
        if (nciIdentifier == null) {
            if (other.nciIdentifier != null) return false;
        }
        else if (!nciIdentifier.equalsIgnoreCase(other.nciIdentifier)) return false;
        return true;
    }

    @RemoteProperty
    public String getNciIdentifier() {
        return nciIdentifier;
    }

    public void setNciIdentifier(String nciIdentifier) {
        this.nciIdentifier = nciIdentifier;
    }


}