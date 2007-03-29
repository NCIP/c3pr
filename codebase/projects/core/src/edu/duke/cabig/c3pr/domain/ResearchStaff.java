package edu.duke.cabig.c3pr.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * @author Priyatam
 */
@Entity
@Table (name="research_staffs")
@GenericGenerator(name="id-generator", strategy = "native",
    parameters = {
        @Parameter(name="sequence", value="research_staffs_id_seq")
    }
)
public class ResearchStaff extends AbstractDomainObject {
	
	private String firstName;
    private String lastName;
    private List<StudyPersonnel> studyPersonnels = new ArrayList<StudyPersonnel>();
    
    private String fullName;
    
    /// LOGIC ~
    	   	    
    @Transient
    public String getLastFirst() {
        StringBuilder name = new StringBuilder();
        boolean hasFirstName = getFirstName() != null;
        if (getLastName() != null) {
            name.append(getLastName());
            if (hasFirstName) name.append(", ");
        }
        if (hasFirstName) {
            name.append(getFirstName());
        }
        return name.toString();
    }

    @Transient
    public String getFullName() {
        StringBuilder name = new StringBuilder();
        boolean hasLastName = getLastName() != null;
        if (getFirstName() != null) {
            name.append(getFirstName());
            if (hasLastName) name.append(' ');
        }
        if (hasLastName) {
            name.append(getLastName());
        }
        return name.toString();
    }
       
    public void addStudyPersonnel(StudyPersonnel studyPersonnel) {
        getStudyPersonnels().add(studyPersonnel);        
    }
    
    
    /// BEAN METHODS    
    
    @OneToMany (mappedBy = "researchStaff")    
    @Cascade (value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })	
	public List<StudyPersonnel> getStudyPersonnels() {
		return studyPersonnels;
	}

	public void setStudyPersonnels(List<StudyPersonnel> studyPersonnels) {
		this.studyPersonnels = studyPersonnels;
	}
	public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
			
}
