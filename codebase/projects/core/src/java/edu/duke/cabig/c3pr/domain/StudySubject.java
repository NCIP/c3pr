package edu.duke.cabig.c3pr.domain;

import edu.duke.cabig.c3pr.utils.DateUtil;
import edu.duke.cabig.c3pr.utils.ProjectedList;
import gov.nih.nci.cabig.ctms.collections.LazyListHelper;
import gov.nih.nci.cabig.ctms.domain.AbstractMutableDomainObject;
import gov.nih.nci.cabig.ctms.domain.DomainObjectTools;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
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
@Table (name = "STUDY_SUBJECTS")
@GenericGenerator(name="id-generator", strategy = "native",
    parameters = {
        @Parameter(name="sequence", value="STUDY_SUBJECTS_ID_SEQ")
    }
)
public class StudySubject extends AbstractMutableDomainObject {
	private LazyListHelper lazyListHelper;
	private List<ScheduledEpoch> scheduledEpochs=new ArrayList<ScheduledEpoch>();
	private String name;
    private StudySite studySite;
    private Participant participant;
    private Date startDate;
    private Date informedConsentSignedDate;
    private String informedConsentVersion;
    private String primaryIdentifier;
    private StudyInvestigator treatingPhysician;
    private String otherTreatingPhysician;
    private String registrationStatus;
    private DiseaseHistory diseaseHistory;
    private List<Identifier> identifiers=new ArrayList<Identifier>();

    
    public StudySubject() {
    	lazyListHelper=new LazyListHelper();
        lazyListHelper.add(ScheduledTreatmentEpoch.class, new InstantiateFactory<ScheduledTreatmentEpoch>(ScheduledTreatmentEpoch.class));
        lazyListHelper.add(ScheduledNonTreatmentEpoch.class, new InstantiateFactory<ScheduledNonTreatmentEpoch>(ScheduledNonTreatmentEpoch.class));
    	this.startDate=new Date();
    	this.primaryIdentifier="SysGen";
        lazyListHelper.add(OrganizationAssignedIdentifier.class, new ParameterizedInstantiateFactory<OrganizationAssignedIdentifier>(OrganizationAssignedIdentifier.class));
        lazyListHelper.add(SystemAssignedIdentifier.class, new ParameterizedInstantiateFactory<SystemAssignedIdentifier>(SystemAssignedIdentifier.class));
        //   mandatory, so that the lazy-projected list is managed properly.
	}
    /// BEAN PROPERTIES
    public StudySubject(boolean forExample) {
    	lazyListHelper=new LazyListHelper();
    	lazyListHelper.add(Identifier.class, new InstantiateFactory<Identifier>(Identifier.class));
        lazyListHelper.add(ScheduledTreatmentEpoch.class, new InstantiateFactory<ScheduledTreatmentEpoch>(ScheduledTreatmentEpoch.class));
        lazyListHelper.add(ScheduledNonTreatmentEpoch.class, new InstantiateFactory<ScheduledNonTreatmentEpoch>(ScheduledNonTreatmentEpoch.class));
    	if(!forExample){
        	this.startDate=new Date();
        	this.primaryIdentifier="SysGen";
    	}
	}
    
    @OneToMany
    @Cascade({CascadeType.ALL,CascadeType.DELETE_ORPHAN})
    @JoinColumn(name = "SPA_ID", nullable=false)
    public List<ScheduledEpoch> getScheduledEpochs() {
		return scheduledEpochs;
	}
    
	private void setScheduledEpochs(List<ScheduledEpoch> scheduledEpochs) {
    	this.scheduledEpochs=scheduledEpochs;
        lazyListHelper.setInternalList(ScheduledTreatmentEpoch.class,new ProjectedList<ScheduledTreatmentEpoch>(this.scheduledEpochs, ScheduledTreatmentEpoch.class));
   	 	lazyListHelper.setInternalList(ScheduledNonTreatmentEpoch.class,new ProjectedList<ScheduledNonTreatmentEpoch>(this.scheduledEpochs, ScheduledNonTreatmentEpoch.class));
	}
	
	@Transient
	public List<ScheduledTreatmentEpoch> getScheduledTreatmentEpochs() {
		return lazyListHelper.getLazyList(ScheduledTreatmentEpoch.class);
	}

	public void setScheduledTreatmentEpochs(List<ScheduledEpoch> scheduledEpochs) {
	}

	@Transient
	public List<ScheduledNonTreatmentEpoch> getScheduledNonTreatmentEpochs() {
		return lazyListHelper.getLazyList(ScheduledNonTreatmentEpoch.class);
	}

	public void setScheduledNonTreatmentEpochs(List<ScheduledNonTreatmentEpoch> scheduledEpochs) {
	}

	public void addScheduledEpoch(ScheduledEpoch scheduledEpoch){
		getScheduledEpochs().add(scheduledEpoch);
	}
	
	@Transient
	public ScheduledEpoch getScheduledEpoch() {
		return getCurrentScheduledEpoch();
	}
	
	private void setScheduledEpoch(ScheduledEpoch scheduledEpoch) {
	}

	@Transient
	public ScheduledEpoch getCurrentScheduledEpoch(){
		List<ScheduledEpoch> tempList=new ArrayList<ScheduledEpoch>();
		tempList.addAll(getScheduledEpochs());
		Collections.sort(tempList);
		if(tempList.size()==0)
			return null;
		return tempList.get(tempList.size()-1);
	}
	
	public void setCurrentScheduledEpoch(ScheduledEpoch scheduledEpoch){
		
	}
	@Transient
	public boolean getIfTreatmentScheduledEpoch(){
		if (getScheduledEpoch() instanceof ScheduledTreatmentEpoch) {
			return true;
		}
		return false;
	}

	@Transient
    public DiseaseHistory getDiseaseHistory() {
    	if(this.diseaseHistory==null)
    		this.diseaseHistory=new DiseaseHistory();
		return diseaseHistory;
	}

	public void setDiseaseHistory(DiseaseHistory diseaseHistory) {
		this.diseaseHistory = diseaseHistory;
	}
	
	@OneToOne
    @Cascade({CascadeType.ALL,CascadeType.DELETE_ORPHAN})
    @JoinColumn(name = "DISEASE_HISTORY_ID")
    public DiseaseHistory getDiseaseHistoryInternal() {
		return diseaseHistory;
	}
	
	public void setDiseaseHistoryInternal(DiseaseHistory diseaseHistory) {
		this.diseaseHistory=diseaseHistory;
	}
	
	public void setStudySite(StudySite studySite) {
        this.studySite = studySite;
    }

    @ManyToOne
    @JoinColumn(name = "STO_ID", nullable=false)
    @Cascade({CascadeType.LOCK})
    public StudySite getStudySite() {
        return studySite;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

    @ManyToOne
    @JoinColumn(name = "PRT_ID", nullable=false)
    @Cascade({CascadeType.LOCK})
    public Participant getParticipant() {
        return participant;
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

        final StudySubject that = (StudySubject) o;

        if (startDate != null ? !startDate.equals(that.startDate) : that.startDate != null)
            return false;
        if (studySite != null ? !studySite.equals(that.studySite) : that.studySite != null)
            return false;
        // Participant#equals calls this method, so we can't use it here
        if (!DomainObjectTools.equalById(participant, that.participant)) return false;

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
    @JoinColumn(name = "SPA_ID")
	public List<Identifier> getIdentifiers() {
		return identifiers;
	}
	
	private void setIdentifiers(List<Identifier> identifiers) {
    	this.identifiers = identifiers;
		//initialize projected list for OrganizationAssigned and SystemAssignedIdentifier
		lazyListHelper.setInternalList(OrganizationAssignedIdentifier.class, 
				new ProjectedList<OrganizationAssignedIdentifier>(this.identifiers, OrganizationAssignedIdentifier.class));
		lazyListHelper.setInternalList(SystemAssignedIdentifier.class, 
				new ProjectedList<SystemAssignedIdentifier>(this.identifiers, SystemAssignedIdentifier.class));
    }
	

    @Transient
    public List<SystemAssignedIdentifier> getSystemAssignedIdentifiers() {
        return lazyListHelper.getLazyList(SystemAssignedIdentifier.class);
    }
    
    @Transient
    public List<OrganizationAssignedIdentifier> getOrganizationAssignedIdentifiers() {
        return lazyListHelper.getLazyList(OrganizationAssignedIdentifier.class);
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
	public StudyInvestigator getTreatingPhysician() {
		return treatingPhysician;
	}

	public void setTreatingPhysician(StudyInvestigator treatingPhysician) {
		this.treatingPhysician = treatingPhysician;
	}

    @Transient
    public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOtherTreatingPhysician() {
		return otherTreatingPhysician;
	}
	public void setOtherTreatingPhysician(String otherTreatingPhysician) {
		this.otherTreatingPhysician = otherTreatingPhysician;
	}
	
	@Transient
	public String getTreatingPhysicianFullName(){
		if(getTreatingPhysician()!=null)
			return getTreatingPhysician().getHealthcareSiteInvestigator().getInvestigator().getFullName();
		return getOtherTreatingPhysician();
	}
}