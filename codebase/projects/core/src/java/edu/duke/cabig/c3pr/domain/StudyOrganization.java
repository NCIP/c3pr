package edu.duke.cabig.c3pr.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Where;

import edu.duke.cabig.c3pr.domain.customfield.CustomField;
import edu.duke.cabig.c3pr.domain.customfield.CustomFieldAuthorable;
import edu.duke.cabig.c3pr.domain.customfield.CustomFieldDefinition;

import gov.nih.nci.cabig.ctms.collections.LazyListHelper;

/**
 * This class encapsulates all types of organizations associated with a Study
 * 
 * @author Ram Chilukuri
 * 
 */
@Entity
@Table(name = "study_organizations")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "STUDY_ORGANIZATIONS_ID_SEQ") })
public abstract class StudyOrganization extends InteroperableAbstractMutableDeletableDomainObject implements CustomFieldAuthorable{

    protected Study study;

    private HealthcareSite healthcareSite;

    private LazyListHelper lazyListHelper;

    // TODO move into Command Object
    private String[] studyInvestigatorIds;
    
    private String[] studyPersonnelIds;
    
    private Boolean hostedMode=true;

    public Boolean getHostedMode() {
        return hostedMode;
    }

    public void setHostedMode(Boolean hostedMode) {
        this.hostedMode = hostedMode;
    }

    public StudyOrganization() {
        lazyListHelper = new LazyListHelper();
        lazyListHelper.add(StudyInvestigator.class,
                        new BiDirectionalInstantiateFactory<StudyInvestigator>(
                                        StudyInvestigator.class, this, "StudyOrganization",
                                        StudyOrganization.class));
        lazyListHelper.add(StudyPersonnel.class,
                        new BiDirectionalInstantiateFactory<StudyPersonnel>(StudyPersonnel.class,
                                        this, "StudyOrganization", StudyOrganization.class));
        lazyListHelper.add(CustomFieldDefinition.class,new ParameterizedBiDirectionalInstantiateFactory<CustomFieldDefinition>(CustomFieldDefinition.class, this));
    }

    public void addStudyPersonnel(StudyPersonnel studyPersonnel) {
        getStudyPersonnel().add(studyPersonnel);
        studyPersonnel.setStudyOrganization(this);
    }

    public void addStudyInvestigator(StudyInvestigator studyInvestigator) {
        getStudyInvestigators().add(studyInvestigator);
        studyInvestigator.setStudyOrganization(this);
    }

    @OneToMany(mappedBy = "studyOrganization")
    @Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
    @Where(clause = "retired_indicator = 'false'")
    public List<StudyInvestigator> getStudyInvestigatorsInternal() {
        return lazyListHelper.getInternalList(StudyInvestigator.class);
    }

    public void setStudyInvestigatorsInternal(List<StudyInvestigator> studyInvestigators) {
        lazyListHelper.setInternalList(StudyInvestigator.class, studyInvestigators);
    }

    @Transient
    public List<StudyInvestigator> getStudyInvestigators() {
        return lazyListHelper.getLazyList(StudyInvestigator.class);
    }

    @Transient
    public List<StudyInvestigator> getActiveStudyInvestigators() {
        List<StudyInvestigator> list = getStudyInvestigators();
        List<StudyInvestigator> retList = new ArrayList<StudyInvestigator>();
        for (StudyInvestigator s : list) {
            if (s.getStatusCode().equalsIgnoreCase("Active")) retList.add(s);
        }
        return retList;
    }

    public void setStudyInvestigators(List<StudyInvestigator> studyInvestigators) {
        lazyListHelper.setInternalList(StudyInvestigator.class, studyInvestigators);
    }

    @OneToMany(mappedBy = "studyOrganization")
    @Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
    @Where(clause = "retired_indicator = 'false'")
    public List<StudyPersonnel> getStudyPersonnelInternal() {
        return lazyListHelper.getInternalList(StudyPersonnel.class);
    }

    public void setStudyPersonnelInternal(List<StudyPersonnel> studyPersonnel) {
        lazyListHelper.setInternalList(StudyPersonnel.class, studyPersonnel);
    }

    @Transient
    public List<StudyPersonnel> getStudyPersonnel() {
        return lazyListHelper.getLazyList(StudyPersonnel.class);
    }

    public void setStudyPersonnel(List<StudyPersonnel> studyPersonnel) {
        lazyListHelper.setInternalList(StudyPersonnel.class, studyPersonnel);
    }

    @ManyToOne
    @JoinColumn(name = "hcs_id", nullable = false)
    @Cascade(value = { CascadeType.LOCK })
    public HealthcareSite getHealthcareSite() {
        return healthcareSite;
    }

    public void setHealthcareSite(HealthcareSite site) {
        this.healthcareSite = site;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((healthcareSite == null) ? 0 : healthcareSite.hashCode());
        result = PRIME * result + ((study == null) ? 0 : study.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        final StudyOrganization other = (StudyOrganization) obj;
        if (healthcareSite == null) {
            if (other.healthcareSite != null) return false;
        }
        else if (!healthcareSite.equals(other.healthcareSite)) return false;
        if (study == null) {
            if (other.study != null) return false;
        }
        else if (!study.equals(other.study)) return false;
        return true;
    }

    @ManyToOne
    @JoinColumn(name = "study_id", nullable = false)
    @Cascade( { CascadeType.LOCK })
    public Study getStudy() {
        return study;
    }

    public void setStudy(Study study) {
        this.study = study;
    }

    @Transient
    public String[] getStudyInvestigatorIds() {
        return studyInvestigatorIds;
    }
    
    @Transient
    public String[] getStudyPersonnelIds() {
        return studyPersonnelIds;
    }
    
    public void setStudyPersonnelIds(String[] studyPersonnelIds) {
        this.studyPersonnelIds = studyPersonnelIds;
    }

    public void setStudyInvestigatorIds(String[] studyInvestigatorIds) {
        this.studyInvestigatorIds = studyInvestigatorIds;
    }
    
    @OneToMany(mappedBy="studyOrganization")
    @Cascade(value = { CascadeType.DELETE, CascadeType.DELETE_ORPHAN })
    public List<EndPoint> getEndpoints() {
        return endpoints;
    }
    
    @Transient
    public List<EndPoint> getStudyEndpoints() {
    	List<EndPoint> studyEndpoints=new ArrayList<EndPoint>();
    	for(EndPoint endPoint: endpoints){
    		if(endPoint.getServiceName()==ServiceName.STUDY)
    			studyEndpoints.add(endPoint);
    	}
        return studyEndpoints;
    }
    
    @Transient
    public List<EndPoint> getRegistrationEndpoints() {
    	List<EndPoint> regEndpoints=new ArrayList<EndPoint>();
    	for(EndPoint endPoint: endpoints){
    		if(endPoint.getServiceName()==ServiceName.REGISTRATION)
    			regEndpoints.add(endPoint);
    	}
        return regEndpoints;
    }
    
    @Transient
    public EndPoint getLastAttemptedRegistrationEndpoint(){
    	List<EndPoint> reList=getRegistrationEndpoints();
    	if(reList.size()==0) return null;
    	Collections.sort(reList);
    	return reList.get(0);
    }
    
    @Transient
    public boolean ifStudyInvestigatorExists(HealthcareSiteInvestigator healthcareInvestigator){
    	for (StudyInvestigator studyInvestigator:getStudyInvestigators()){
    		if (studyInvestigator.getHealthcareSiteInvestigator().equals(healthcareInvestigator)){
    			return true;
    		}
    	}
    	return false;
    }
    
    @Transient
    public boolean isSuccessfullSend(APIName apiName){
        for(EndPoint endPoint:getEndpoints()){
            if(endPoint.apiName==apiName && endPoint.getStatus()==WorkFlowStatusType.MESSAGE_SEND_CONFIRMED)
                return true;
        }
        return false;
    }
    
    @Transient
    public boolean getIsCoordinatingCenter(){
        return this.study.isCoOrdinatingCenter(this.getHealthcareSite().getNciInstituteCode());
    }
    
    
    
    
    @OneToMany(mappedBy = "studyOrganization", fetch = FetchType.LAZY)
	@Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
	public List<CustomFieldDefinition> getCustomFieldDefinitionsInternal() {
		return lazyListHelper.getInternalList(CustomFieldDefinition.class);
	}

	@Transient
	public List<CustomFieldDefinition> getCustomFieldDefinitions() {
		return lazyListHelper.getLazyList(CustomFieldDefinition.class);
	}

	public void setCustomFieldDefinitionsInternal(List<CustomFieldDefinition> customFieldDefinitions) {
		lazyListHelper.setInternalList(CustomFieldDefinition.class,customFieldDefinitions);
	}

	public void addCustomFieldDefinition(CustomFieldDefinition customFieldDefinition) {
		this.getCustomFieldDefinitions().add(customFieldDefinition);
		customFieldDefinition.setStudyOrganization(this);
	}
}
