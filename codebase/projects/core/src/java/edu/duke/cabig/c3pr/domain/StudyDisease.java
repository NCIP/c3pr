package edu.duke.cabig.c3pr.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

// TODO: Auto-generated Javadoc
/**
 * The Class StudyDisease.
 * 
 * @author Priyatam
 */

@Entity
@Table(name = "study_diseases")
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "STUDY_DISEASES_ID_SEQ") })
public class StudyDisease extends AbstractMutableDeletableDomainObject implements
                Comparable<StudyDisease> {

    /** The study. */
    private StudyVersion studyVersion;

    /** The disease term. */
    private DiseaseTerm diseaseTerm;

    /** The lead disease. */
    private Boolean leadDisease;

    /*
     * Constructor -- Initializes participation at create time
     * 
     */
    /**
     * Instantiates a new study disease.
     */
    public StudyDisease() {
        super();
    }

    /**
     * Gets the study.
     * 
     * @return the study
     */
    @ManyToOne
    @JoinColumn(name = "stu_version_id")
    public StudyVersion getStudyVersion() {
        return studyVersion;
    }

    /**
     * Sets the study.
     * 
     * @param study the new study
     */
    public void setStudyVersion(StudyVersion studyVersion) {
        this.studyVersion = studyVersion;
    }

    /**
     * Gets the disease term.
     * 
     * @return the disease term
     */
    @ManyToOne
    @JoinColumn(name = "disease_term_id")
    @Cascade(value = { CascadeType.LOCK })
    public DiseaseTerm getDiseaseTerm() {
        return diseaseTerm;
    }

    /**
     * Sets the disease term.
     * 
     * @param diseaseTerm the new disease term
     */
    public void setDiseaseTerm(DiseaseTerm diseaseTerm) {
        this.diseaseTerm = diseaseTerm;
    }

    /**
     * Gets the lead disease.
     * 
     * @return the lead disease
     */
    public Boolean getLeadDisease() {
        return leadDisease;
    }

    /**
     * Sets the lead disease.
     * 
     * @param leadDisease the new lead disease
     */
    public void setLeadDisease(Boolean leadDisease) {
        this.leadDisease = leadDisease;
    }

    /* (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(StudyDisease o) {
        if (this.equals(o)) {
            return 0;
        }
        else return 1;
    }

	/* (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.domain.AbstractMutableDeletableDomainObject#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((diseaseTerm == null) ? 0 : diseaseTerm.hashCode());
		result = prime * result + ((studyVersion == null) ? 0 : studyVersion.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.domain.AbstractMutableDeletableDomainObject#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final StudyDisease other = (StudyDisease) obj;
		if (diseaseTerm == null) {
			if (other.diseaseTerm != null)
				return false;
		} else if (!diseaseTerm.equals(other.diseaseTerm))
			return false;
		if (studyVersion == null) {
			if (other.studyVersion != null)
				return false;
		} else if (!studyVersion.equals(other.studyVersion))
			return false;
		return true;
	}

}
