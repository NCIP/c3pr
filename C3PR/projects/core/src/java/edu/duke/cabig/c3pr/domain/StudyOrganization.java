package edu.duke.cabig.c3pr.domain;

import java.util.ArrayList;
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

import edu.duke.cabig.c3pr.constants.InvestigatorStatusCodeEnum;
import edu.duke.cabig.c3pr.constants.APIName;
import edu.duke.cabig.c3pr.constants.ServiceName;
import edu.duke.cabig.c3pr.constants.WorkFlowStatusType;
import edu.duke.cabig.c3pr.domain.customfield.CustomFieldAuthorable;
import edu.duke.cabig.c3pr.domain.customfield.CustomFieldDefinition;
import edu.duke.cabig.c3pr.domain.factory.ParameterizedBiDirectionalInstantiateFactory;
import gov.nih.nci.cabig.ctms.collections.LazyListHelper;

/**
 * This class encapsulates all types of organizations associated with a Study.
 * 
 * @author Ram Chilukuri
 */
@Entity
@Table(name = "study_organizations")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "STUDY_ORGANIZATIONS_ID_SEQ") })
public abstract class StudyOrganization extends InteroperableAbstractMutableDeletableDomainObject implements CustomFieldAuthorable{

    /** The study. */
    protected Study study;

    /** The healthcare site. */
    private HealthcareSite healthcareSite;

    /** The lazy list helper. */
    protected LazyListHelper lazyListHelper;

    // TODO move into Command Object
    /** The study investigator ids. */
    private String[] studyInvestigatorIds;
    
    /** The study personnel ids. */
    private String[] studyPersonnelIds;
    
    /** The hosted mode. */
    private Boolean hostedMode=true;

    /**
     * Gets the hosted mode.
     * 
     * @return the hosted mode
     */
    public Boolean getHostedMode() {
        return hostedMode;
    }

    /**
     * Sets the hosted mode.
     * 
     * @param hostedMode the new hosted mode
     */
    public void setHostedMode(Boolean hostedMode) {
        this.hostedMode = hostedMode;
    }

    /**
     * Instantiates a new study organization.
     */
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

    /**
     * Adds the study personnel.
     * 
     * @param studyPersonnel the study personnel
     */
    public void addStudyPersonnel(StudyPersonnel studyPersonnel) {
        getStudyPersonnel().add(studyPersonnel);
        studyPersonnel.setStudyOrganization(this);
    }

    /**
     * Adds the study investigator.
     * 
     * @param studyInvestigator the study investigator
     */
    public void addStudyInvestigator(StudyInvestigator studyInvestigator) {
        getStudyInvestigators().add(studyInvestigator);
        studyInvestigator.setStudyOrganization(this);
    }

    /**
     * Gets the study investigators internal.
     * 
     * @return the study investigators internal
     */
    @OneToMany(mappedBy = "studyOrganization")
    @Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
    @Where(clause = "retired_indicator = 'false'")
    public List<StudyInvestigator> getStudyInvestigatorsInternal() {
        return lazyListHelper.getInternalList(StudyInvestigator.class);
    }

    /**
     * Sets the study investigators internal.
     * 
     * @param studyInvestigators the new study investigators internal
     */
    public void setStudyInvestigatorsInternal(List<StudyInvestigator> studyInvestigators) {
        lazyListHelper.setInternalList(StudyInvestigator.class, studyInvestigators);
    }

    /**
     * Gets the study investigators.
     * 
     * @return the study investigators
     */
    @Transient
    public List<StudyInvestigator> getStudyInvestigators() {
        return lazyListHelper.getLazyList(StudyInvestigator.class);
    }

    /**
     * Gets the active study investigators.
     * 
     * @return the active study investigators
     */
    @Transient
    public List<StudyInvestigator> getActiveStudyInvestigators() {
        List<StudyInvestigator> list = getStudyInvestigators();
        List<StudyInvestigator> retList = new ArrayList<StudyInvestigator>();
        for (StudyInvestigator s : list) {
            if (s.getStatusCode().equals(InvestigatorStatusCodeEnum.AC)) retList.add(s);
        }
        return retList;
    }

    /**
     * Sets the study investigators.
     * 
     * @param studyInvestigators the new study investigators
     
    public void setStudyInvestigators(List<StudyInvestigator> studyInvestigators) {
        lazyListHelper.setInternalList(StudyInvestigator.class, studyInvestigators);
    }*/

    /**
     * Gets the study personnel internal.
     * 
     * @return the study personnel internal
     */
    @OneToMany(mappedBy = "studyOrganization")
    @Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
    @Where(clause = "retired_indicator = 'false'")
    public List<StudyPersonnel> getStudyPersonnelInternal() {
        return lazyListHelper.getInternalList(StudyPersonnel.class);
    }

    /**
     * Sets the study personnel internal.
     * 
     * @param studyPersonnel the new study personnel internal
     */
    public void setStudyPersonnelInternal(List<StudyPersonnel> studyPersonnel) {
        lazyListHelper.setInternalList(StudyPersonnel.class, studyPersonnel);
    }

    /**
     * Gets the study personnel.
     * 
     * @return the study personnel
     */
    @Transient
    public List<StudyPersonnel> getStudyPersonnel() {
        return lazyListHelper.getLazyList(StudyPersonnel.class);
    }

    /**
     * Sets the study personnel.
     * 
     * @param studyPersonnel the new study personnel
     
    public void setStudyPersonnel(List<StudyPersonnel> studyPersonnel) {
        lazyListHelper.setInternalList(StudyPersonnel.class, studyPersonnel);
    }*/

    /**
     * Gets the healthcare site.
     * 
     * @return the healthcare site
     */
    @ManyToOne
    @JoinColumn(name = "hcs_id", nullable = false)
    @Cascade(value = { CascadeType.LOCK })
    public HealthcareSite getHealthcareSite() {
        return healthcareSite;
    }

    /**
     * Sets the healthcare site.
     * 
     * @param site the new healthcare site
     */
    public void setHealthcareSite(HealthcareSite site) {
        this.healthcareSite = site;
    }

    /* (non-Javadoc)
     * @see edu.duke.cabig.c3pr.domain.AbstractMutableDeletableDomainObject#hashCode()
     */
    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((healthcareSite == null) ? 0 : healthcareSite.hashCode());
        result = PRIME * result + ((study == null) ? 0 : study.hashCode());
        return result;
    }

    /* (non-Javadoc)
     * @see edu.duke.cabig.c3pr.domain.AbstractMutableDeletableDomainObject#equals(java.lang.Object)
     */
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

    /**
     * Gets the study.
     * 
     * @return the study
     */
    @ManyToOne
    @JoinColumn(name = "study_id", nullable = false)
    @Cascade( { CascadeType.LOCK })
    public Study getStudyInternal() {
        return study;
    }

    /**
     * Sets the study.
     * 
     * @param study the new study
     */
    public void setStudyInternal(Study study) {
        this.study = study;
    }
    
    @Transient
    public Study getStudy(){
    	return getStudyInternal();
    }
    
    public void setStudy(Study study){
    	this.study = study;
    }

    /**
     * Gets the study investigator ids.
     * 
     * @return the study investigator ids
     */
    @Transient
    public String[] getStudyInvestigatorIds() {
        return studyInvestigatorIds;
    }
    
    /**
     * Gets the study personnel ids.
     * 
     * @return the study personnel ids
     */
    @Transient
    public String[] getStudyPersonnelIds() {
        return studyPersonnelIds;
    }
    
    /**
     * Sets the study personnel ids.
     * 
     * @param studyPersonnelIds the new study personnel ids
     */
    public void setStudyPersonnelIds(String[] studyPersonnelIds) {
        this.studyPersonnelIds = studyPersonnelIds;
    }

    /**
     * Sets the study investigator ids.
     * 
     * @param studyInvestigatorIds the new study investigator ids
     */
    public void setStudyInvestigatorIds(String[] studyInvestigatorIds) {
        this.studyInvestigatorIds = studyInvestigatorIds;
    }
    
    /* (non-Javadoc)
     * @see edu.duke.cabig.c3pr.domain.InteroperableAbstractMutableDeletableDomainObject#getEndpoints()
     */
    @OneToMany(mappedBy="studyOrganization")
    @Cascade(value = { CascadeType.DELETE, CascadeType.DELETE_ORPHAN })
    public List<EndPoint> getEndpoints() {
        return endpoints;
    }
    
    /**
     * Gets the study endpoints.
     * 
     * @return the study endpoints
     */
    @Transient
    public List<EndPoint> getStudyEndpoints() {
    	List<EndPoint> studyEndpoints=new ArrayList<EndPoint>();
    	for(EndPoint endPoint: endpoints){
    		if(endPoint.getServiceName()==ServiceName.STUDY)
    			studyEndpoints.add(endPoint);
    	}
        return studyEndpoints;
    }
    
    /**
     * Gets the registration endpoints.
     * 
     * @return the registration endpoints
     */
    @Transient
    public List<EndPoint> getRegistrationEndpoints() {
    	List<EndPoint> regEndpoints=new ArrayList<EndPoint>();
    	for(EndPoint endPoint: endpoints){
    		if(endPoint.getServiceName()==ServiceName.REGISTRATION)
    			regEndpoints.add(endPoint);
    	}
        return regEndpoints;
    }
    
    /**
     * Gets the last attempted registration endpoint.
     * 
     * @return the last attempted registration endpoint
     */
    @Transient
    public EndPoint getLastAttemptedRegistrationEndpoint(){
    	List<EndPoint> reList=getRegistrationEndpoints();
    	if(reList.size()==0) return null;
    	Collections.sort(reList);
    	return reList.get(0);
    }
    
    /**
     * If study investigator exists.
     * 
     * @param healthcareInvestigator the healthcare investigator
     * 
     * @return true, if successful
     */
    @Transient
    public boolean ifStudyInvestigatorExists(HealthcareSiteInvestigator healthcareInvestigator){
    	for (StudyInvestigator studyInvestigator:getStudyInvestigators()){
    		if (studyInvestigator.getHealthcareSiteInvestigator().equals(healthcareInvestigator)){
    			return true;
    		}
    	}
    	return false;
    }
    
    /**
     * Checks if is successfull send.
     * 
     * @param apiName the api name
     * 
     * @return true, if is successfull send
     */
    @Transient
    public boolean isSuccessfullSend(APIName apiName){
        for(EndPoint endPoint:getEndpoints()){
            if(endPoint.apiName==apiName && endPoint.getStatus()==WorkFlowStatusType.MESSAGE_SEND_CONFIRMED)
                return true;
        }
        return false;
    }
    
    /**
     * Gets the checks if is coordinating center.
     * 
     * @return the checks if is coordinating center
     */
    @Transient
    public boolean getIsCoordinatingCenter(){
        return this.study.isCoOrdinatingCenter(this.getHealthcareSite().getPrimaryIdentifier());
    }
    
    
    /**
     * Gets the custom field definitions internal.
     * 
     * @return the custom field definitions internal
     */
    @OneToMany(mappedBy = "studyOrganization", fetch = FetchType.LAZY)
	@Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
	public List<CustomFieldDefinition> getCustomFieldDefinitionsInternal() {
		return lazyListHelper.getInternalList(CustomFieldDefinition.class);
	}

	/* (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.domain.customfield.CustomFieldAuthorable#getCustomFieldDefinitions()
	 */
	@Transient
	public List<CustomFieldDefinition> getCustomFieldDefinitions() {
		return lazyListHelper.getLazyList(CustomFieldDefinition.class);
	}

	/**
	 * Sets the custom field definitions internal.
	 * 
	 * @param customFieldDefinitions the new custom field definitions internal
	 */
	public void setCustomFieldDefinitionsInternal(List<CustomFieldDefinition> customFieldDefinitions) {
		lazyListHelper.setInternalList(CustomFieldDefinition.class,customFieldDefinitions);
	}

	/**
	 * Adds the custom field definition.
	 * 
	 * @param customFieldDefinition the custom field definition
	 */
	public void addCustomFieldDefinition(CustomFieldDefinition customFieldDefinition) {
		this.getCustomFieldDefinitions().add(customFieldDefinition);
		customFieldDefinition.setStudyOrganization(this);
	}
}
