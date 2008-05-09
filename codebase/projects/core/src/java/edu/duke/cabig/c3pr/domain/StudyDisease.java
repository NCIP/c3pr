package edu.duke.cabig.c3pr.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * @author Priyatam
 */

@Entity
@Table(name = "study_diseases")
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "STUDY_DISEASES_ID_SEQ") })
public class StudyDisease extends AbstractMutableDeletableDomainObject implements
                Comparable<StudyDisease> {

    private Study study;

    private DiseaseTerm diseaseTerm;

    private Boolean leadDisease;

    /*
     * Constructor -- Initializes participation at create time
     * 
     */
    public StudyDisease() {
        super();
    }

    @ManyToOne
    @JoinColumn(name = "study_id")
    public Study getStudy() {
        return study;
    }

    public void setStudy(Study study) {
        this.study = study;
    }

    @ManyToOne
    @JoinColumn(name = "disease_term_id")
    @Cascade(value = { CascadeType.LOCK })
    public DiseaseTerm getDiseaseTerm() {
        return diseaseTerm;
    }

    public void setDiseaseTerm(DiseaseTerm diseaseTerm) {
        this.diseaseTerm = diseaseTerm;
    }

    public Boolean getLeadDisease() {
        return leadDisease;
    }

    public void setLeadDisease(Boolean leadDisease) {
        this.leadDisease = leadDisease;
    }

    public int compareTo(StudyDisease o) {
        if (this.equals(o)) {
            return 0;
        }
        else return 1;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((diseaseTerm == null) ? 0 : diseaseTerm.hashCode());
		result = prime * result + ((study == null) ? 0 : study.hashCode());
		return result;
	}

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
		if (study == null) {
			if (other.study != null)
				return false;
		} else if (!study.equals(other.study))
			return false;
		return true;
	}

}
