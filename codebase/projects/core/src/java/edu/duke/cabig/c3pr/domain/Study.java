package edu.duke.cabig.c3pr.domain;

import edu.duke.cabig.c3pr.utils.ProjectedList;
import edu.duke.cabig.c3pr.utils.StringUtils;
import gov.nih.nci.cabig.ctms.collections.LazyListHelper;
import gov.nih.nci.cabig.ctms.domain.AbstractMutableDomainObject;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.collections15.functors.InstantiateFactory;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * A systematic evaluation of an observation or an
 * intervention (for example, treatment, drug, device, procedure or system) in one
 * or more subjects. Frequently this is a test of a particular hypothesis about
 * the treatment, drug, device, procedure or system. [CDAM]  A study can be either
 * primary or correlative. A study is considered a primary study if it has one or
 * more correlative studies. A correlative study extends the objectives or
 * observations of a primary study, enrolling the same, or a subset of the same,
 * subjects as the primary study. A Clinical Trial is a Study with type=
 * "intervention" with subjects of type="human".
 *
 * @author Priyatam
 */

@Entity
@Table(name = "STUDIES")
@GenericGenerator(name = "id-generator", strategy = "native",
        parameters = {
        @Parameter(name = "sequence", value = "STUDIES_ID_SEQ")
                }
)
public class Study extends AbstractMutableDomainObject implements Comparable<Study>{
	
	private String blindedIndicator;
    private String multiInstitutionIndicator;
    private String randomizedIndicator;
    private String shortTitleText;
    private String longTitleText;
    private String descriptionText;
    private String precisText;
    private String phaseCode;
    private String status;
    private String type;
    private String primaryIdentifier;
    private Integer targetAccrualNumber;
    private List<Epoch> epochs;
    private RandomizationType randomizationType;
    private List<StudyDisease> studyDiseases = new ArrayList<StudyDisease>();
    private List<StudyOrganization> studyOrganizations;

   
    // TODO move into Command Object
    private String[] diseaseTermIds;
    private String diseaseCategoryAsText;

    private LazyListHelper lazyListHelper;


    public Study() {
        lazyListHelper = new LazyListHelper();
        lazyListHelper.add(Identifier.class, new InstantiateFactory<Identifier>(Identifier.class));
   //     lazyListHelper.add(StudySite.class, new BiDirectionalInstantiateFactory<StudySite>(StudySite.class,this));
        lazyListHelper.add(StudySite.class, new ParameterizedBiDirectionalInstantiateFactory<StudySite>(StudySite.class,this));
		lazyListHelper.add(StudyFundingSponsor.class, new ParameterizedBiDirectionalInstantiateFactory<StudyFundingSponsor>(StudyFundingSponsor.class,this));
		lazyListHelper.add(StudyCoordinatingCenter.class, new ParameterizedBiDirectionalInstantiateFactory<StudyCoordinatingCenter>(StudyCoordinatingCenter.class,this));

    //  lazyListHelper.add(Epoch.class, epochFactory);
        lazyListHelper.add(TreatmentEpoch.class, new ParameterizedBiDirectionalInstantiateFactory<TreatmentEpoch>(TreatmentEpoch.class,this));
        lazyListHelper.add(NonTreatmentEpoch.class, new ParameterizedBiDirectionalInstantiateFactory<NonTreatmentEpoch>(NonTreatmentEpoch.class,this));
   //   mandatory, so that the lazy-projected list is managed properly.
        setStudyOrganizations(new ArrayList<StudyOrganization>());
        setEpochs(new ArrayList<Epoch>());

       }
    @Transient
	public List<Identifier> getLocalIdentifiers() {
		List<Identifier> localIdentifiers = new ArrayList<Identifier>();
		for (Identifier identifier : getIdentifiers()) {
			if ("Protocol Authority Identifier".equals(identifier.getType())
					|| "Coordinating Center Identifier".equals(identifier
							.getType())) {
				// nothing
			} else {
				localIdentifiers.add(identifier);
			}
		}
		return localIdentifiers;
	}
    
    @Transient
    public List<StudySite> getStudySites() {
        return lazyListHelper.getLazyList(StudySite.class);
    }
    
   public void setStudySites(List<StudySite> studySites) {
        // do nothing
    }

    @Transient
	public List<StudyFundingSponsor> getStudyFundingSponsors() {
		return lazyListHelper.getLazyList(StudyFundingSponsor.class);
	}
    
    public void setStudyFundingSponsors(List<StudyFundingSponsor> studyFundingSponsors) {
        // do nothing
    }
	
    @Transient
	public List<StudyCoordinatingCenter> getStudyCoordinatingCenters() {
		return lazyListHelper.getLazyList(StudyCoordinatingCenter.class);
	}
    
    public void setStudyCoordinatingCenters(List<StudyCoordinatingCenter> studyCoordinatingCenters) {
        // do nothing
    }
	
	@OneToMany(mappedBy = "study", fetch = FetchType.LAZY)
	@Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
	public List<StudyOrganization> getStudyOrganizations() {
		return studyOrganizations;
	}
	 public void setStudyOrganizations(List<StudyOrganization> studyOrganizations) {
			this.studyOrganizations = studyOrganizations;
			//initialize projected list for StudySite, StudyFundingSponsor and StudyCoordinatingCenter
			lazyListHelper.setInternalList(StudySite.class, 
					new ProjectedList<StudySite>(this.studyOrganizations, StudySite.class));
			lazyListHelper.setInternalList(StudyFundingSponsor.class, 
					new ProjectedList<StudyFundingSponsor>(this.studyOrganizations, StudyFundingSponsor.class));
			lazyListHelper.setInternalList(StudyCoordinatingCenter.class, 
					new ProjectedList<StudyCoordinatingCenter>(this.studyOrganizations, StudyCoordinatingCenter.class));
		}
	
	public void addStudyOrganization(StudyOrganization so){
		this.getStudyOrganizations().add(so);
		so.setStudy(this);
	}
	
	public void removeStudyOrganization(StudyOrganization so){
		this.getStudyOrganizations().remove(so);
	}

    public void addEpoch(Epoch epoch) {
    	    epoch.setStudy(this);
    	    getEpochs().add(epoch);
    }

    public void removeEpoch(Epoch epoch) {
            lazyListHelper.getLazyList(Epoch.class).remove(epoch);
    }

    public void addStudySite(StudySite studySite) {
        studySite.setStudy(this);
        lazyListHelper.getLazyList(StudySite.class).add(studySite);
    }

    public void removeStudySite(StudySite studySite) {
        lazyListHelper.getLazyList(StudySite.class).remove(studySite);
    }

    public void addStudyDisease(StudyDisease studyDisease) {
        studyDisease.setStudy(this);
        studyDiseases.add(studyDisease);
    }

    public void addIdentifier(Identifier identifier) {
        getIdentifiers().add(identifier);
    }

    public void removeIdentifier(Identifier identifier) {
        getIdentifiers().remove(identifier);
    }

    /// BEAN PROPERTIES
    
//  TODO: this stuff should really, really not be in here. It's
	// web-view/entry specific.
    @Transient
    public String[] getDiseaseTermIds() {
        return diseaseTermIds;
    }

    @Transient
    public String getDiseaseCategoryAsText() {
        return diseaseCategoryAsText;
    }

    @Transient
    public List<Identifier> getIdentifiers() {
        return lazyListHelper.getLazyList(Identifier.class);
    }
      
        
    @OneToMany(mappedBy = "study", fetch = FetchType.LAZY)
    @Cascade(value = {CascadeType.ALL, CascadeType.DELETE_ORPHAN})
    public List<Epoch> getEpochs() {
    	return epochs;
    }

    public void setEpochs(List<Epoch> epochs) {
    	this.epochs=epochs;
        lazyListHelper.setInternalList(TreatmentEpoch.class,new ProjectedList<TreatmentEpoch>(this.epochs, TreatmentEpoch.class));
   	 	lazyListHelper.setInternalList(NonTreatmentEpoch.class,new ProjectedList<NonTreatmentEpoch>(this.epochs, NonTreatmentEpoch.class));
    }
    
    @Transient 
	public List<TreatmentEpoch> getTreatmentEpochs(){
    	 return lazyListHelper.getLazyList(TreatmentEpoch.class);
	}
    
   	public void setTreatmentEpochs(List<TreatmentEpoch> treatmentEpochs){
    	// do nothing
	}
         
    @Transient
	public List<NonTreatmentEpoch> getNonTreatmentEpochs(){
    	 return lazyListHelper.getLazyList(NonTreatmentEpoch.class);
   	}
    
	public void setNonTreatmentEpochs(List<NonTreatmentEpoch> nonTreatmentEpochs){
    	// do nothing
	}

    @OneToMany
    @Cascade({CascadeType.ALL, CascadeType.DELETE_ORPHAN})
    @JoinColumn(name = "STU_ID")
    public List<Identifier> getIdentifiersInternal() {
        return lazyListHelper.getInternalList(Identifier.class);
    }

    public void setIdentifiersInternal(List<Identifier> identifiers) {
        lazyListHelper.setInternalList(Identifier.class, identifiers);
    }

    @OneToMany(mappedBy = "study", fetch = FetchType.LAZY)
    @Cascade(value = {CascadeType.ALL, CascadeType.DELETE_ORPHAN})
    public List<StudyDisease> getStudyDiseases() {
        return studyDiseases;
    }

    public void setStudyDiseases(List<StudyDisease> studyDiseases) {
        this.studyDiseases = studyDiseases;
    }

    public void setDiseaseTermIds(String[] diseaseTermIds) {
        this.diseaseTermIds = diseaseTermIds;
    }

    public void setDiseaseCategoryAsText(String diseaseCategoryAsText) {
        this.diseaseCategoryAsText = diseaseCategoryAsText;
    }
   
    public void setIdentifiers(List<Identifier> identifiers) {
        lazyListHelper.setInternalList(Identifier.class, identifiers);
    }

    public String getDescriptionText() {
        return descriptionText;
    }

    public void setDescriptionText(String descriptionText) {
        this.descriptionText = descriptionText;
    }

    public String getLongTitleText() {
        return longTitleText;
    }

    public void setLongTitleText(String longTitleText) {
        this.longTitleText = longTitleText;
    }

    public String getPhaseCode() {
        return phaseCode;
    }

    public void setPhaseCode(String phaseCode) {
        this.phaseCode = phaseCode;
    }

    public String getPrecisText() {
        return precisText;
    }

    public void setPrecisText(String precisText) {
        this.precisText = precisText;
    }

    public String getShortTitleText() {
        return shortTitleText;
    }

    public void setShortTitleText(String shortTitleText) {
        this.shortTitleText = shortTitleText;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getTargetAccrualNumber() {
        return targetAccrualNumber;
    }

    public void setTargetAccrualNumber(Integer targetAccrualNumber) {
        this.targetAccrualNumber = targetAccrualNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int compareTo(Study o) {
        if (this.equals((Study) o)) return 0;

        return 1;
    }

    /*@Override
        public int hashCode() {
            final int PRIME = 31;
            int result = super.hashCode();
            result = PRIME * result + ((epochs == null) ? 0 : epochs.hashCode());
            result = PRIME * result + ((identifiers == null) ? 0 : identifiers.hashCode());
            result = PRIME * result + ((longTitleText == null) ? 0 : longTitleText.hashCode());
            result = PRIME * result + ((phaseCode == null) ? 0 : phaseCode.hashCode());
            result = PRIME * result + ((status == null) ? 0 : status.hashCode());
            result = PRIME * result + ((studySites == null) ? 0 : studySites.hashCode());
            result = PRIME * result + ((type == null) ? 0 : type.hashCode());
            return result;
        }
    */
    /*@Override
     public boolean equals(Object obj) {
         if (this == obj)
             return true;
         if (!super.equals(obj))
             return false;
         if (getClass() != obj.getClass())
             return false;
         final Study other = (Study) obj;
         if (epochs == null) {
             if (other.epochs != null)
                 return false;
         } else if (!epochs.equals(other.epochs))
             return false;
         if (identifiers == null) {
             if (other.identifiers != null)
                 return false;
         } else if (!identifiers.equals(other.identifiers))
             return false;
         if (longTitleText == null) {
             if (other.longTitleText != null)
                 return false;
         } else if (!longTitleText.equals(other.longTitleText))
             return false;
         if (phaseCode == null) {
             if (other.phaseCode != null)
                 return false;
         } else if (!phaseCode.equals(other.phaseCode))
             return false;
         if (status == null) {
             if (other.status != null)
                 return false;
         } else if (!status.equals(other.status))
             return false;
         if (studySites == null) {
             if (other.studySites != null)
                 return false;
         } else if (!studySites.equals(other.studySites))
             return false;
         if (type == null) {
             if (other.type != null)
                 return false;
         } else if (!type.equals(other.type))
             return false;
         return true;
     }*/

    @Transient
    public String getTrimmedShortTitleText() {
        return StringUtils.getTrimmedText(shortTitleText, 40);
    }

    @Transient
    public String getPrimaryIdentifier() {
        for (Identifier identifier : getIdentifiers()) {
            if (identifier.getPrimaryIndicator().booleanValue() == true) {
                return identifier.getValue();
            }
        }

        return primaryIdentifier;
    }

    public String getBlindedIndicator() {
        return blindedIndicator;
    }

    public void setBlindedIndicator(String blindedIndicator) {
        this.blindedIndicator = blindedIndicator;
    }

    public String getMultiInstitutionIndicator() {
        return multiInstitutionIndicator;
    }

    public void setMultiInstitutionIndicator(String multiInstitutionIndicator) {
        this.multiInstitutionIndicator = multiInstitutionIndicator;
    }

    public String getRandomizedIndicator() {
        return randomizedIndicator;
    }

    public void setRandomizedIndicator(String randomizedIndicator) {
        this.randomizedIndicator = randomizedIndicator;
    }
    
	public RandomizationType getRandomizationType() {
		return randomizationType;
	}

	public void setRandomizationType(RandomizationType randomizationType) {
		this.randomizationType = randomizationType;
	}

}