package edu.duke.cabig.c3pr.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.validator.NotNull;

import edu.duke.cabig.c3pr.domain.factory.ParameterizedBiDirectionalInstantiateFactory;
import gov.nih.nci.cabig.ctms.collections.LazyListHelper;

// TODO: Auto-generated Javadoc
/**
 * The Class Consent.
 */
@Entity
@Table(name = "consents")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "CONSENTS_ID_SEQ") })
public class Consent extends AbstractMutableDeletableDomainObject {
	
	/** The study. */
	private Study study;
	
	/**
	 * Gets the study.
	 * 
	 * @return the study
	 */
	@ManyToOne
    @JoinColumn(name = "study_id", nullable = false)
	public Study getStudy() {
		return study;
	}

	/**
	 * Sets the study.
	 * 
	 * @param study the new study
	 */
	public void setStudy(Study study) {
		this.study = study;
	}

	/** The consent name. */
	private String name;
	
	/**
	 * Gets the name.
	 * 
	 * @return the name
	 */
	@NotNull
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 * 
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/** The lazy list helper. */
	private LazyListHelper lazyListHelper;
	
	/**
	 * Instantiates a new consent.
	 */
	public Consent(){
		lazyListHelper = new LazyListHelper();
		lazyListHelper.add(ConsentVersion.class,new ParameterizedBiDirectionalInstantiateFactory<ConsentVersion>(ConsentVersion.class, this));
	}
	
	/**
	 * Gets the consent versions internal.
	 * 
	 * @return the consent versions internal
	 */
	@OneToMany(mappedBy = "consent", fetch = FetchType.LAZY)
	@Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
	@OrderBy ("date")
	public List<ConsentVersion> getConsentVersionsInternal() {
		return lazyListHelper.getInternalList(ConsentVersion.class);
	}

	/**
	 * Gets the consent versions.
	 * 
	 * @return the consent versions
	 */
	@Transient
	public List<ConsentVersion> getConsentVersions() {
		return lazyListHelper.getLazyList(ConsentVersion.class);
	}

	/**
	 * Gets the consent versions internal.
	 * 
	 * @param consentVersions the consent versions
	 * 
	 * @return the consent versions internal
	 */
	public void setConsentVersionsInternal(List<ConsentVersion> consentVersions) {
		lazyListHelper.setInternalList(ConsentVersion.class,consentVersions);
	}

	/**
	 * Adds the consent version.
	 * 
	 * @param consentVersion the consent version
	 */
	public void addConsentVersion(ConsentVersion consentVersion) {
		this.getConsentVersions().add(consentVersion);
		consentVersion.setConsent(this);
	}
	
	
	/**
	 * Sets the consent versions.
	 * 
	 * @param consentVersions the new consent versions
	 */
	public void setConsentVersions(List<ConsentVersion> consentVersions) {
		setConsentVersionsInternal(consentVersions);
	}

	
}
