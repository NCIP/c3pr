package edu.duke.cabig.c3pr.domain;

import gov.nih.nci.security.util.StringUtilities;
import gov.nih.nci.cabig.ctms.domain.AbstractMutableDomainObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
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
public class DiseaseHistory extends AbstractMutableDomainObject
{			
	private String otherPrimaryDiseaseCode;
	private String otherPrimaryDiseaseSiteCode;
	private StudyDisease studyDisease;
	private AnatomicSite anatomicSite;


	@OneToOne
	@JoinColumn(name="anatomic_site_id")
	@Cascade(value = { CascadeType.ALL })
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
		if(!StringUtilities.isBlank(otherPrimaryDiseaseCode))
			return otherPrimaryDiseaseCode;
		try {
			return studyDisease.getDiseaseTerm().getTerm();
		} catch (RuntimeException e) {
		}
		return "";
	}

	@Transient
	public String getPrimaryDiseaseSiteStr(){
		if(!StringUtilities.isBlank(otherPrimaryDiseaseSiteCode))
			return otherPrimaryDiseaseSiteCode;
		try {
			return anatomicSite.getName();
		} catch (RuntimeException e) {
		}
		return "";
	}

}

