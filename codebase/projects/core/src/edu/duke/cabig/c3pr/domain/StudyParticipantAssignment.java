package edu.duke.cabig.c3pr.domain;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import edu.duke.cabig.c3pr.utils.DateUtil;


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
    private String informedConsentVersion;
    private String primaryIdentifier;
    private StudyInvestigator treatingPhysician;
    private String registrationStatus;
    private List<SubjectEligibilityAnswer> subjectEligibilityAnswers=new ArrayList<SubjectEligibilityAnswer>();
    
    /// BEAN PROPERTIES

	@OneToMany
    @Cascade({CascadeType.ALL,CascadeType.DELETE_ORPHAN})
    @JoinColumn(name = "SPA_ID")
    public List<SubjectEligibilityAnswer> getSubjectEligibilityAnswers() {
		return subjectEligibilityAnswers;
	}

	public void setSubjectEligibilityAnswers(
			List<SubjectEligibilityAnswer> subjectEligibilityAnswers) {
		this.subjectEligibilityAnswers = subjectEligibilityAnswers;
	}
	
	public void addSubjectEligibilityAnswers(SubjectEligibilityAnswer subjectEligibilityAnswer){
		subjectEligibilityAnswers.add(subjectEligibilityAnswer);
	}

	public void removeSubjectEligibilityAnswers(SubjectEligibilityAnswer subjectEligibilityAnswer){
		subjectEligibilityAnswers.remove(subjectEligibilityAnswer);
	}
	
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
	
	@Transient
	public String getInformedConsentSignedDateStr() {
		if(informedConsentSignedDate==null){
			return "";
		}else if(informedConsentSignedDate.equals("")){
			return "";
		}
		try {
			return DateUtil.formatDate(informedConsentSignedDate, "MM/dd/yyyy");
		}
		catch(ParseException e){
			//do nothing
		}
		return null;
	}
	
	@Transient
	public String getStartDateStr() {
		if(startDate==null){
			return "";
		}else if(startDate.equals("")){
			return "";
		}
		try {
			return DateUtil.formatDate(startDate, "MM/dd/yyyy");
		}
		catch(ParseException e){
			//do nothing
		}
		return "";
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
	
	@Transient
	public String getPrimaryIdentifier() {		
		for (Identifier identifier : identifiers) {
			if(identifier.getPrimaryIndicator().booleanValue() == true)
			{
				return identifier.getValue();
			}
		}
			
		return primaryIdentifier;		
	}
	@Column(name= "informedConsentVersion" , nullable = true)
	public String getInformedConsentVersion() {
		return informedConsentVersion;
	}

	public void setInformedConsentVersion(String informedConsentVersion) {
		this.informedConsentVersion = informedConsentVersion;
	}

	public String getRegistrationStatus() {
		return registrationStatus;
	}

	public void setRegistrationStatus(String registrationStatus) {
		this.registrationStatus = registrationStatus;
	}

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STI_ID")
    @Cascade (value={CascadeType.ALL})
	public StudyInvestigator getTreatingPhysician() {
		return treatingPhysician;
	}

	public void setTreatingPhysician(StudyInvestigator treatingPhysician) {
		this.treatingPhysician = treatingPhysician;
	}

	@Transient
	public List getInclusionEligibilityAnswers(){
		List inclusionCriteriaAnswers=new ArrayList();
		for(int i=0 ; i<subjectEligibilityAnswers.size() ; i++){
			if(subjectEligibilityAnswers.get(i).getEligibilityCriteria() instanceof InclusionEligibilityCriteria){
				inclusionCriteriaAnswers.add(subjectEligibilityAnswers.get(i));
			}
		}
		return inclusionCriteriaAnswers;
	}
	@Transient
	public List getExclusionEligibilityAnswers(){
		List exclusionCriteriaAnswers=new ArrayList();
		for(int i=0 ; i<subjectEligibilityAnswers.size() ; i++){
			if(subjectEligibilityAnswers.get(i).getEligibilityCriteria() instanceof ExclusionEligibilityCriteria){
				exclusionCriteriaAnswers.add(subjectEligibilityAnswers.get(i));
			}
		}
		return exclusionCriteriaAnswers;
	}
}