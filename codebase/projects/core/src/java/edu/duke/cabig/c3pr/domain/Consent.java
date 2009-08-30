package edu.duke.cabig.c3pr.domain;

import java.util.List;
import java.util.TreeSet;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.validator.NotNull;

import edu.duke.cabig.c3pr.domain.factory.ParameterizedBiDirectionalInstantiateFactory;
import edu.emory.mathcs.backport.java.util.Collections;
import gov.nih.nci.cabig.ctms.collections.LazyListHelper;

@Entity
@Table(name = "consents", uniqueConstraints = { @UniqueConstraint(columnNames = { "stu_version_id", "name" }) })
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "CONSENTS_ID_SEQ") })
public class Consent extends AbstractMutableDeletableDomainObject implements Comparable<Consent>{

	public int compareTo(Consent o) {
		if (this.equals(o))
			return 0;
		else
			return 1;
	}

	private String name;

	@NotNull
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private LazyListHelper lazyListHelper;

	public Consent(){
		lazyListHelper = new LazyListHelper();
		lazyListHelper.add(ConsentVersion.class,new ParameterizedBiDirectionalInstantiateFactory<ConsentVersion>(ConsentVersion.class, this));
	}

	@OneToMany
	@JoinColumn(name = "consent_id", nullable = false)
	@Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
	@OrderBy("effectiveDate")
	public List<ConsentVersion> getConsentVersionsInternal() {
		return lazyListHelper.getInternalList(ConsentVersion.class);
	}

	@Transient
	public List<ConsentVersion> getConsentVersions() {
		return lazyListHelper.getLazyList(ConsentVersion.class);
	}

	public void setConsentVersionsInternal(List<ConsentVersion> consentVersions) {
		lazyListHelper.setInternalList(ConsentVersion.class,consentVersions);
	}

	public void addConsentVersion(ConsentVersion consentVersion) {
		this.getConsentVersions().add(consentVersion);
	}

	public void setConsentVersions(List<ConsentVersion> consentVersions) {
		setConsentVersionsInternal(consentVersions);
	}

	@Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = PRIME * result + ((name == null) ? 0 : name.hashCode());
        result = PRIME * result;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (getClass() != obj.getClass()) return false;
        final Consent other = (Consent) obj;
        if (name == null) {
            if (other.name != null) return false;
        }
        else if (!name.equalsIgnoreCase(other.name)) return false;
        return true;
    }

    @Transient
	public ConsentVersion getLatestConsentVersion(){
    	TreeSet<ConsentVersion> consentVersions = new TreeSet<ConsentVersion>();
    	consentVersions.addAll(getConsentVersions());
//    	int size = consentVersions.size();
		return consentVersions.last();
	}

    @Transient
    public List<ConsentVersion> getConsentVersionListForRegistration(){
    	List<ConsentVersion> versions = getConsentVersions();
    	Collections.reverse(versions);
    	return versions;
    }

}
