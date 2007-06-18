package edu.duke.cabig.c3pr.domain;

import edu.duke.cabig.c3pr.utils.DateUtil;
import gov.nih.nci.cabig.ctms.collections.LazyListHelper;
import gov.nih.nci.cabig.ctms.domain.AbstractMutableDomainObject;

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

import org.apache.commons.collections15.functors.InstantiateFactory;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
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
public class StudyParticipantAssignment extends AbstractMutableDomainObject {
	private LazyListHelper lazyListHelper;
	private String name;
    private StudySite studySite;
    private Participant participant;
    private Date startDate;
    private String studyParticipantIdentifier;
    private String eligibilityWaiverReasonText;
    private Date informedConsentSignedDate;
    private Boolean eligibilityIndicator;
    private String informedConsentVersion;
    private String primaryIdentifier;
    private StudyInvestigator treatingPhysician;
    private String registrationStatus;
    private DiseaseHistory diseaseHistory;
    
    public StudyParticipantAssignment() {
    	lazyListHelper=new LazyListHelper();
    	lazyListHelper.add(SubjectEligibilityAnswer.class, new InstantiateFactory<SubjectEligibilityAnswer>(SubjectEligibilityAnswer.class));
    	lazyListHelper.add(SubjectStratificationAnswer.class, new InstantiateFactory<SubjectStratificationAnswer>(SubjectStratificationAnswer.class));
    	lazyListHelper.add(ScheduledArm.class, new BiDirectionalInstantiateFactory<ScheduledArm>(ScheduledArm.class,this));
    	lazyListHelper.add(Identifier.class, new InstantiateFactory<Identifier>(Identifier.class));
	}
    /// BEAN PROPERTIES
    @OneToOne
    @Cascade({CascadeType.ALL,CascadeType.DELETE_ORPHAN})
    @JoinColumn(name = "DISEASE_HISTORY_ID")
    public DiseaseHistory getDiseaseHistory() {
		return diseaseHistory;
	}

	public void setDiseaseHistory(DiseaseHistory diseaseHistory) {
		this.diseaseHistory = diseaseHistory;
	}

	@OneToMany
    @Cascade({CascadeType.ALL,CascadeType.DELETE_ORPHAN})
    @JoinColumn(name = "SPA_ID")
    public List<SubjectEligibilityAnswer> getSubjectEligibilityAnswersInternal() {
		return lazyListHelper.getInternalList(SubjectEligibilityAnswer.class);
	}
	public void setSubjectEligibilityAnswersInternal(
			List<SubjectEligibilityAnswer> subjectEligibilityAnswers) {
		lazyListHelper.setInternalList(SubjectEligibilityAnswer.class, subjectEligibilityAnswers);
	}
	@Transient
	public List<SubjectEligibilityAnswer> getSubjectEligibilityAnswers() {
		return lazyListHelper.getLazyList(SubjectEligibilityAnswer.class);
	}
	public void addSubjectEligibilityAnswers(SubjectEligibilityAnswer subjectEligibilityAnswer){
		lazyListHelper.getLazyList(SubjectEligibilityAnswer.class).add(subjectEligibilityAnswer);
	}
	public void removeSubjectEligibilityAnswers(SubjectEligibilityAnswer subjectEligibilityAnswer){
		lazyListHelper.getLazyList(SubjectEligibilityAnswer.class).remove(subjectEligibilityAnswer);
	}
	

	@OneToMany
    @Cascade({CascadeType.ALL,CascadeType.DELETE_ORPHAN})
    @JoinColumn(name = "SPA_ID")
    public List<SubjectStratificationAnswer> getSubjectStratificationAnswersInternal() {
		return lazyListHelper.getInternalList(SubjectStratificationAnswer.class);
	}
	public void setSubjectStratificationAnswersInternal(
			List<SubjectStratificationAnswer> subjectStratificationAnswers) {
		lazyListHelper.setInternalList(SubjectStratificationAnswer.class, subjectStratificationAnswers);
	}
	@Transient
	public List<SubjectStratificationAnswer> getSubjectStratificationAnswers() {
		return lazyListHelper.getLazyList(SubjectStratificationAnswer.class);
	}
	public void addSubjectStratificationAnswers(SubjectStratificationAnswer subjectStratificationAnswer){
		lazyListHelper.getLazyList(SubjectStratificationAnswer.class).add(subjectStratificationAnswer);
	}
	
	public void removeSubjectStratificationAnswers(SubjectStratificationAnswer subjectStratificationAnswer){
		lazyListHelper.getLazyList(SubjectStratificationAnswer.class).remove(subjectStratificationAnswer);
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
	public List<ScheduledArm> getScheduledArmsInternal() {
		return lazyListHelper.getInternalList(ScheduledArm.class);
	}

	public void setScheduledArmsInternal(List<ScheduledArm> scheduledArms) {
		lazyListHelper.setInternalList(ScheduledArm.class, scheduledArms);
	}
	@Transient
	public List<ScheduledArm> getScheduledArms() {
		return lazyListHelper.getLazyList(ScheduledArm.class);
	}
	public void addScheduledArm(ScheduledArm scheduledArm){
		lazyListHelper.getLazyList(ScheduledArm.class).add(scheduledArm);
	}

	public void removeScheduledArm(ScheduledArm scheduledArm){
		lazyListHelper.getLazyList(ScheduledArm.class).remove(scheduledArm);
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
        if (!AbstractMutableDomainObject.equalById(participant, that.participant)) return false;

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
	public List<Identifier> getIdentifiersInternal() {
		return lazyListHelper.getInternalList(Identifier.class);
	}
	public void setIdentifiersInternal(List<Identifier> identifiers) {
		lazyListHelper.setInternalList(Identifier.class,identifiers);
	}
	@Transient
	public List<Identifier> getIdentifiers() {
		return lazyListHelper.getLazyList(Identifier.class);
	}
	public void addIdentifier(Identifier identifier){
		lazyListHelper.getLazyList(Identifier.class).add(identifier);
	}

	public void removeIdentifier(Identifier identifier){
		lazyListHelper.getLazyList(Identifier.class).remove(identifier);
	}
	
	@Transient
	public String getPrimaryIdentifier() {		
		for (Identifier identifier : getIdentifiers()) {
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
	public List<SubjectEligibilityAnswer> getInclusionEligibilityAnswers(){
		List<SubjectEligibilityAnswer> inclusionCriteriaAnswers=new ArrayList<SubjectEligibilityAnswer>();
		for(int i=0 ; i<getSubjectEligibilityAnswers().size() ; i++){
			if(getSubjectEligibilityAnswers().get(i).getEligibilityCriteria() instanceof InclusionEligibilityCriteria){
				inclusionCriteriaAnswers.add(getSubjectEligibilityAnswers().get(i));
			}
		}
		return inclusionCriteriaAnswers;
	}
	@Transient
	public List<SubjectEligibilityAnswer> getExclusionEligibilityAnswers(){
		List<SubjectEligibilityAnswer> exclusionCriteriaAnswers=new ArrayList<SubjectEligibilityAnswer>();
		for(int i=0 ; i<getSubjectEligibilityAnswers().size() ; i++){
			if(getSubjectEligibilityAnswers().get(i).getEligibilityCriteria() instanceof ExclusionEligibilityCriteria){
				exclusionCriteriaAnswers.add(getSubjectEligibilityAnswers().get(i));
			}
		}
		return exclusionCriteriaAnswers;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}