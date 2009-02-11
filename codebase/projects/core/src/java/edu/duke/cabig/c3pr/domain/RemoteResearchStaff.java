package edu.duke.cabig.c3pr.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

import com.semanticbits.coppa.domain.annotations.RemoteEntity;
import com.semanticbits.coppa.domain.annotations.RemoteUniqueId;

import edu.duke.cabig.c3pr.infrastructure.RemoteResearchStaffResolver;

/**
 * The Class RemoteResearchStaff.
 * @author Vinay Gangoli
 */
@RemoteEntity(entityResolver=RemoteResearchStaffResolver.class)
@Entity
@DiscriminatorValue("Remote")
public class RemoteResearchStaff extends ResearchStaff{
	
	private String uniqueIdentifier;
	
    private String fullName;
    
    private List<StudyPersonnel> studyPersonnels = new ArrayList<StudyPersonnel>();

	 // / BEAN METHODS
    @Transient
    public List<StudyPersonnel> getStudyPersonnels() {
        return studyPersonnels;
    }

    public void setStudyPersonnels(List<StudyPersonnel> studyPersonnels) {
        this.studyPersonnels = studyPersonnels;
    }

    @Transient
    public List<ContactMechanism> getContactMechanisms() {
        return contactMechanisms;
    }

    public void setContactMechanisms(List<ContactMechanism> contactMechanisms) {
        this.contactMechanisms = contactMechanisms;
    }

    public void addStudyPersonnel(StudyPersonnel studyPersonnel) {
        getStudyPersonnels().add(studyPersonnel);
    }
	
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
    
	@RemoteUniqueId
	public String getUniqueIdentifier() {
		return uniqueIdentifier;
	}

	public void setUniqueIdentifier(String uniqueIdentifier) {
		this.uniqueIdentifier = uniqueIdentifier;
	}

}
