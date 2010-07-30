package edu.duke.cabig.c3pr.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "research_staff")
@GenericGenerator(name = "id-generator", strategy = "sequence", parameters = { @Parameter(name = "sequence", value = "research_staff_id_seq") })
public class BaseResearchStaffDataContainer extends AbstractMutableDeletableDomainObject{

	public String getDtype() {
		return dtype;
	}
	public void setDtype(String dtype) {
		this.dtype = dtype;
	}
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getMaidenName() {
		return maidenName;
	}
	public void setMaidenName(String maidenName) {
		this.maidenName = maidenName;
	}

	private String firstName;
	private String middleName;
	private String lastName;
	private String maidenName;
	private String assignedIdentifier;
	private String dtype;
	private String externalId; 
	private List<BaseOrganizationDataContainer> baseOrganizationDataContainers = new ArrayList<BaseOrganizationDataContainer>();
	private List<BaseContactMechanismDataContainer> contactMechanisms = new ArrayList<BaseContactMechanismDataContainer>();
	
	 /* (non-Javadoc)
     * @see edu.duke.cabig.c3pr.domain.Person#getContactMechanisms()
     */
    @OneToMany
    @Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
    @JoinColumn(name = "RS_ID")
    @OrderBy("id")
    public List<BaseContactMechanismDataContainer> getContactMechanisms() {
        return contactMechanisms;
    }

    /* (non-Javadoc)
     * @see edu.duke.cabig.c3pr.domain.Person#setContactMechanisms(java.util.List)
     */
    public void setContactMechanisms(List<BaseContactMechanismDataContainer> contactMechanisms) {
        this.contactMechanisms = contactMechanisms;
    }
    
    public void addContactMechanism(BaseContactMechanismDataContainer baseContactMechanismDataContainer){
    	this.getContactMechanisms().add(baseContactMechanismDataContainer);
    }
	

	public String getAssignedIdentifier() {
		return assignedIdentifier;
	}
	public void setAssignedIdentifier(String assignedIdentifier) {
		this.assignedIdentifier = assignedIdentifier;
	}
	
	@ManyToMany
	@Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
	@JoinTable(name = "rs_hc_site_assocn", joinColumns = @JoinColumn(name = "rs_id"), inverseJoinColumns = @JoinColumn(name = "hcs_id"))
	public List<BaseOrganizationDataContainer> getBaseOrganizationDataContainers() {
		return baseOrganizationDataContainers;
	}
	public void setBaseOrganizationDataContainers(List<BaseOrganizationDataContainer> baseOrganizationDataContainers) {
		this.baseOrganizationDataContainers = baseOrganizationDataContainers;
	}
	
	public void addBaseOrganizationDataContainer(BaseOrganizationDataContainer baseOrganizationDataContainer){
    	this.getBaseOrganizationDataContainers().add(baseOrganizationDataContainer);
    } 
	public void removeHealthcareSite(BaseOrganizationDataContainer baseOrganizationDataContainer){
    	this.getBaseOrganizationDataContainers().remove(baseOrganizationDataContainer);
    }
	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}
	public String getExternalId() {
		return externalId;
	}
	
}
