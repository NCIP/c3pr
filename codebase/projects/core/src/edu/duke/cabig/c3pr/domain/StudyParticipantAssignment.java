package edu.duke.cabig.c3pr.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.FlushMode;
import javax.persistence.FlushModeType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;


/**
 * @author Ram Chilukuri
 */

@Entity
@Table (name = "STUDY_PARTICIPANT_ASSIGNMENTS")
@GenericGenerator(name="id-generator", strategy = "native",
    parameters = {
        @Parameter(name="sequence", value="STUDY_PARTICIPANT_ASSIG_ID_SEQ")
    }
)
public class StudyParticipantAssignment extends AbstractGridIdentifiableDomainObject {
    
    private StudySite studySite;
    private Participant participant;
    private List<ScheduledArm> scheduledArms = new ArrayList<ScheduledArm>();
    private List<Identifier> identifiers = new ArrayList<Identifier>();
    private Date startDate;
    private String studyParticipantIdentifier;
    private String eligibilityWaiverReasonText;
    private Date informedConsentSignedDate;
    private Boolean eligibilityIndicator;

    
    /// BEAN PROPERTIES


	public void setStudySite(StudySite studySite) {
        this.studySite = studySite;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STS_ID")
    @Cascade (value={CascadeType.ALL})
    public StudySite getStudySite() {
        return studySite;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRT_ID")
    public Participant getParticipant() {
        return participant;
    }

    @OneToMany (mappedBy = "studyParticipantAssignment")
    @Cascade (value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })    	    
	public List<ScheduledArm> getScheduledArms() {
		return scheduledArms;
	}

	public void setScheduledArms(List<ScheduledArm> scheduledArms) {
		this.scheduledArms = scheduledArms;
	}

	public void addScheduledArm(ScheduledArm scheduledArm){
		scheduledArms.add(scheduledArm);
	}

	public void removeScheduledArm(ScheduledArm scheduledArm){
		scheduledArms.remove(scheduledArm);
	}

    public void setStudyParticipantIdentifier(String studyParticipantIdentifier) {
        this.studyParticipantIdentifier = studyParticipantIdentifier;
    }

    public String getStudyParticipantIdentifier() {
        return studyParticipantIdentifier;
    }

    public void setEligibilityWaiverReasonText(String eligibilityWaiverReasonText) {
        this.eligibilityWaiverReasonText = eligibilityWaiverReasonText;
    }

    public String getEligibilityWaiverReasonText() {
        return eligibilityWaiverReasonText;
    }

    public Date getInformedConsentSignedDate() {
		return informedConsentSignedDate;
	}

	public void setInformedConsentSignedDate(Date informedConsentSignedDate) {
		this.informedConsentSignedDate = informedConsentSignedDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Override
	public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final StudyParticipantAssignment that = (StudyParticipantAssignment) o;

        if (startDate != null ? !startDate.equals(that.startDate) : that.startDate != null)
            return false;
        if (studySite != null ? !studySite.equals(that.studySite) : that.studySite != null)
            return false;
        // Participant#equals calls this method, so we can't use it here
        if (!AbstractDomainObject.equalById(participant, that.participant)) return false;

        return true;
    }

	@Override
    public int hashCode() {
        int result;
        result = (studySite != null ? studySite.hashCode() : 0);
        result = 29 * result + (participant != null ? participant.hashCode() : 0);
        result = 29 * result + (startDate != null ? startDate.hashCode() : 0);
        return result;
    }

	public Boolean getEligibilityIndicator() {
		return eligibilityIndicator;
	}

	public void setEligibilityIndicator(Boolean eligibilityIndicator) {
		this.eligibilityIndicator = eligibilityIndicator;
	}

	@OneToMany
    @Cascade({CascadeType.ALL,CascadeType.DELETE_ORPHAN})
    @JoinColumn(name = "SPA_ID")
	public List<Identifier> getIdentifiers() {
		return identifiers;
	}

	public void setIdentifiers(List<Identifier> identifiers) {
		this.identifiers = identifiers;
	}
	
	public void addIdentifier(Identifier identifier){
		identifiers.add(identifier);
	}

	public void removeIdentifier(Identifier identifier){
		identifiers.remove(identifier);
	}

}