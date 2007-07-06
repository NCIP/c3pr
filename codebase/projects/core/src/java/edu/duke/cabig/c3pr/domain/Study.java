package edu.duke.cabig.c3pr.domain;

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
import org.hibernate.annotations.Where;

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
public class Study extends AbstractMutableDomainObject implements Comparable<Study> {

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
    private ParameterizedBiDirectionalInstantiateFactory<Epoch> epochFactory;

    private List<InclusionEligibilityCriteria> incCriterias = new ArrayList<InclusionEligibilityCriteria>();
    private List<ExclusionEligibilityCriteria> excCriterias = new ArrayList<ExclusionEligibilityCriteria>();
    private List<StudyDisease> studyDiseases = new ArrayList<StudyDisease>();
    private List<StratificationCriterion> stratificationCriteria = new ArrayList<StratificationCriterion>();
   
    // TODO move into Command Object
    private String[] diseaseTermIds;
    private String diseaseCategoryAsText;

    private LazyListHelper lazyListHelper;


    public Study() {
        lazyListHelper = new LazyListHelper();
        lazyListHelper.add(Identifier.class, new InstantiateFactory<Identifier>(Identifier.class));
        lazyListHelper.add(StudySite.class, new BiDirectionalInstantiateFactory<StudySite>(StudySite.class,this));
        epochFactory=new ParameterizedBiDirectionalInstantiateFactory<Epoch>(Epoch.class,this);
        lazyListHelper.add(Epoch.class, epochFactory);

    }

    public void addEpoch(Epoch epoch) {
    	    epoch.setStudy(this);
    	    lazyListHelper.getLazyList(Epoch.class).add(epoch);
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

    public void addInclusionEligibilityCriteria(InclusionEligibilityCriteria inc) {
        incCriterias.add(inc);
        inc.setStudy(this);
    }

    public void removeInclusionEligibilityCriteria(InclusionEligibilityCriteria inc) {
        incCriterias.remove(inc);
    }

    public void addExclusionEligibilityCriteria(ExclusionEligibilityCriteria exc) {
        excCriterias.add(exc);
        exc.setStudy(this);
    }

    public void removeExcclusionEligibilityCriteria(ExclusionEligibilityCriteria exc) {
        excCriterias.remove(exc);
    }


    public void addStudyDisease(StudyDisease studyDisease) {
        studyDisease.setStudy(this);
        studyDiseases.add(studyDisease);
    }

    public void addStratificationCriteria(StratificationCriterion strat) {
        strat.setStudy(this);
        stratificationCriteria.add(strat);
    }

    public void addIdentifier(Identifier identifier) {
        getIdentifiers().add(identifier);
    }

    public void removeIdentifier(Identifier identifier) {
        getIdentifiers().remove(identifier);
    }

    /// BEAN PROPERTIES

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
    public List<Epoch> getEpochsInternal() {
        return lazyListHelper.getInternalList(Epoch.class);
    }

    public void setEpochsInternal(List<Epoch> epochs) {
        lazyListHelper.setInternalList(Epoch.class, epochs);
    }
     
    @Transient
    public List<Epoch> getEpochs() {
    	return lazyListHelper.getLazyList(Epoch.class);
    }

    public void setEpochs(List<Epoch> epochs) {
    	 lazyListHelper.setInternalList(Epoch.class,epochs);
    }
    
    @Transient
	public List<Epoch> getTreatmentEpochsAliased(){
    	epochFactory.setClassToInstantiate(TreatmentEpoch.class);
		return getEpochs();
	}
    
    public void setTreatmentEpochsAliased(List<TreatmentEpoch> treatmentEpochs){
	}
	
	@Transient
	public List<Epoch> getNonTreatmentEpochsAliased(){
    	epochFactory.setClassToInstantiate(NonTreatmentEpoch.class);
		return getEpochs();
	}
	
	public void setNonTreatmentEpochsAliased(List<NonTreatmentEpoch> nonTreatmentEpochs){
	}
    
	@Transient
	public List<TreatmentEpoch> getTreatmentEpochs(){
    	List<TreatmentEpoch> treatmentEpochs=new ArrayList<TreatmentEpoch>();
    	List<Epoch> epochs= getEpochs();
    	for(Epoch epoch: epochs){
    		if (epoch instanceof TreatmentEpoch) {
				treatmentEpochs.add((TreatmentEpoch)epoch);
			}
    	}
    	return treatmentEpochs;
	}
    
    public void setTreatmentEpochs(List<TreatmentEpoch> treatmentEpochs){
	}
	
	@Transient
	public List<NonTreatmentEpoch> getNonTreatmentEpochs(){
    	List<NonTreatmentEpoch> nonTreatmentEpochs=new ArrayList<NonTreatmentEpoch>();
    	List<Epoch> epochs= getEpochs();
    	for(Epoch epoch: epochs){
    		if (epoch instanceof NonTreatmentEpoch) {
    			nonTreatmentEpochs.add((NonTreatmentEpoch)epoch);
			}
    	}
    	return nonTreatmentEpochs;
	}

	public void setNonTreatmentEpochs(List<NonTreatmentEpoch> nonTreatmentEpochs){
	}

    @OneToMany(mappedBy = "study", fetch = FetchType.LAZY)
    @Cascade(value = {CascadeType.ALL, CascadeType.DELETE_ORPHAN})
    public List<StudySite> getStudySitesInternal() {
        return lazyListHelper.getInternalList(StudySite.class);
    }

    public void setStudySitesInternal(List<StudySite> studySites) {
        lazyListHelper.setInternalList(StudySite.class, studySites);
    }

    @Transient
    public List<StudySite> getStudySites() {
        return lazyListHelper.getLazyList(StudySite.class);
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

    @OneToMany
    @Cascade (value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
    @JoinColumn(name = "stu_id", nullable=true)       
    @Where(clause = "DTYPE = 'E'") // it is pretty lame that this is necessary
	public List<ExclusionEligibilityCriteria> getExcCriteriasTemp() {
		return excCriterias;
	}

    public void setExcCriteriasTemp(List<ExclusionEligibilityCriteria> excCriterias) {
        this.excCriterias = excCriterias;
    }

    @OneToMany
    @Cascade (value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
    @JoinColumn(name = "stu_id", nullable=true)
    @Where(clause = "DTYPE = 'I'") // it is pretty lame that this is necessary
	public List<InclusionEligibilityCriteria> getIncCriteriasTemp() {
		return incCriterias;
	}

    public void setIncCriteriasTemp(List<InclusionEligibilityCriteria> incCriterias) {
        this.incCriterias = incCriterias;
    }

    @Transient
    public List<InclusionEligibilityCriteria> getIncCriterias() {
    	try {
			return getTreatmentEpochs().get(0).getInclusionEligibilityCriteria();
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return null;
	}

    public void setIncCriterias(List<InclusionEligibilityCriteria> incCriterias) {
        this.incCriterias = incCriterias;
    }

    @Transient
	public List<ExclusionEligibilityCriteria> getExcCriterias() {
    	try {
			return getTreatmentEpochs().get(0).getExclusionEligibilityCriteria();
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return null;

	}

    public void setExcCriterias(List<ExclusionEligibilityCriteria> excCriterias) {
        this.excCriterias = excCriterias;
    }


    @OneToMany(mappedBy = "study", fetch = FetchType.LAZY)
    @Cascade(value = {CascadeType.ALL, CascadeType.DELETE_ORPHAN})
    public List<StudyDisease> getStudyDiseases() {
        return studyDiseases;
    }

    public void setStudyDiseases(List<StudyDisease> studyDiseases) {
        this.studyDiseases = studyDiseases;
    }

//    @OneToMany(mappedBy = "study", fetch = FetchType.LAZY)
//    @Cascade(value = {CascadeType.ALL, CascadeType.DELETE_ORPHAN})
    @Transient
    public List<StratificationCriterion> getStratificationCriteria() {
    	try {
			return getTreatmentEpochs().get(0).getStratificationCriteria();
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return null;
    }

    public void setDiseaseTermIds(String[] diseaseTermIds) {
        this.diseaseTermIds = diseaseTermIds;
    }

    public void setDiseaseCategoryAsText(String diseaseCategoryAsText) {
        this.diseaseCategoryAsText = diseaseCategoryAsText;
    }
    public void setStratificationCriteria(List<StratificationCriterion> stratificationCriteria) {
        this.stratificationCriteria = stratificationCriteria;
    }

    public void setIdentifiers(List<Identifier> identifiers) {
        lazyListHelper.setInternalList(Identifier.class, identifiers);
    }

    public void setStudySites(List<StudySite> studySites) {
        lazyListHelper.setInternalList(StudySite.class,studySites);
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

}