package edu.duke.cabig.c3pr.domain;

import java.text.ParseException;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.validator.NotNull;

import edu.duke.cabig.c3pr.utils.DateUtil;

@Entity
@Table(name = "consent_versions", uniqueConstraints = { @UniqueConstraint(columnNames = { "consent_id", "name" }) })
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "CONSENT_VERSIONS_ID_SEQ") })
public class ConsentVersion extends AbstractMutableDeletableDomainObject implements Comparable<ConsentVersion>{

	public int compareTo(ConsentVersion consentVersion) {
		return this.effectiveDate.compareTo(consentVersion.getEffectiveDate());
	}


	private String name;
	private Date effectiveDate;
	private StudySubjectConsentVersion studySubjectConsentVersion;

	@NotNull
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@NotNull
	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public void setStudySubjectConsentVersion(StudySubjectConsentVersion studySubjectConsentVersion) {
		this.studySubjectConsentVersion = studySubjectConsentVersion;
	}

	@OneToOne(mappedBy = "consentVersion")
	@Cascade( { CascadeType.LOCK})
	public StudySubjectConsentVersion getStudySubjectConsentVersion() {
		return studySubjectConsentVersion;
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
		final ConsentVersion other = (ConsentVersion) obj;
		if (name == null) {
			if (other.name != null) return false;
		}
		else if (!name.equalsIgnoreCase(other.name)) return false;
		return true;
	}

	@Transient
	public String getEffectiveDateStr() {
		if (effectiveDate != null) {
			return DateUtil.formatDate(effectiveDate, "MM/dd/yyyy");
		}
		return "";
	}

}
