package edu.duke.cabig.c3pr.domain;

import edu.duke.cabig.c3pr.utils.StringUtils;
import edu.duke.cabig.c3pr.domain.AbstractMutableDeletableDomainObject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * @author Kulasekaran
 * @version 1.0
 */
@Entity
@Table (name="disease_history")
@GenericGenerator(name="id-generator", strategy = "native",
    parameters = {
        @Parameter(name="sequence", value="disease_history_id_seq")
    }
)
public class DiseaseHistory extends AbstractMutableDeletableDomainObject
{			
	private String otherPrimaryDiseaseCode;
	private String otherPrimaryDiseaseSiteCode;
	private StudyDisease studyDisease;
	private AnatomicSite anatomicSite;


	@OneToOne
	@JoinColumn(name="anatomic_site_id")
	@Cascade(value = { CascadeType.LOCK })
	public AnatomicSite getAnatomicSite() {
		return anatomicSite;
	}

	public void setAnatomicSite(AnatomicSite anatomicSite) {
		this.anatomicSite = anatomicSite;
	}

	@OneToOne
	@JoinColumn(name="study_disease_id")
	@Cascade(value = { CascadeType.ALL })
	public StudyDisease getStudyDisease() {
		return studyDisease;
	}

	public void setStudyDisease(StudyDisease studyDisease) {
		this.studyDisease = studyDisease;
	}

	@Column(name = "other_disease_code")
	public String getOtherPrimaryDiseaseCode() {
		return otherPrimaryDiseaseCode;
	}
	
	public void setOtherPrimaryDiseaseCode(String otherPrimaryDiseaseCode) {
		this.otherPrimaryDiseaseCode = otherPrimaryDiseaseCode;
	}
	
	@Column(name = "other_disease_site_code")
	public String getOtherPrimaryDiseaseSiteCode() {
		return otherPrimaryDiseaseSiteCode;
	}
	
	public void setOtherPrimaryDiseaseSiteCode(String otherPrimaryDiseaseSiteCode) {
		this.otherPrimaryDiseaseSiteCode = otherPrimaryDiseaseSiteCode;
	}
	
	@Transient
	public String getPrimaryDiseaseStr(){
		if(!StringUtils.isBlank(otherPrimaryDiseaseCode))
			return otherPrimaryDiseaseCode;
		try {
			return studyDisease.getDiseaseTerm().getTerm();
		} catch (RuntimeException e) {
		}
		return "";
	}

	@Transient
	public String getPrimaryDiseaseSiteStr(){
		if(!StringUtils.isBlank(otherPrimaryDiseaseSiteCode))
			return otherPrimaryDiseaseSiteCode;
		try {
			return anatomicSite.getName();
		} catch (RuntimeException e) {
		}
		return "";
	}

}

