package edu.duke.cabig.c3pr.domain;

import edu.duke.cabig.c3pr.domain.AbstractMutableDeletableDomainObject;

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
@Table (name = "study_diseases")
@GenericGenerator(name="id-generator", strategy = "native",
    parameters = {
        @Parameter(name="sequence", value="STUDY_DISEASES_ID_SEQ")
    }
)
public class StudyDisease extends AbstractMutableDeletableDomainObject implements Comparable<StudyDisease> {
	
	private Study study;
	private DiseaseTerm diseaseTerm;
	private Boolean leadDisease;
	//private String[] diseaseTermId;
	//private String diseaseTermAsString;


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
	
	
	
	/*
	@Transient
	public String[] getDiseaseTermId() {
		return diseaseTermId;
	}

	public void setDiseaseTermId(String[] diseaseTermId) {
		this.diseaseTermId = diseaseTermId;
	}
	
	@Transient
	public String getDiseaseTermAsString() {
		return diseaseTermAsString;
	}

	public void setDiseaseTermAsString(String diseaseTermAsString) {
		this.diseaseTermAsString = diseaseTermAsString;
	}
	*/

	
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		final StudyDisease that = (StudyDisease) o;

		if (study != null ? !study.equals(that.study)
				: that.study != null)
			return false;
		if (diseaseTerm != null ? !diseaseTerm.getId().equals(that.diseaseTerm.getId())
				: that.diseaseTerm != null)
			return false;

		return true;
	}

	public int hashCode() {
		int result;
		result = (diseaseTerm != null ? diseaseTerm.hashCode() : 0);
		result = 29 * result + (study != null ? study.hashCode() : 0);
		return result;
	}

	public int compareTo(StudyDisease o) {
		if (this.equals(o)){return 0;}
			else return 1;
	}

}
