/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.domain;

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

import edu.duke.cabig.c3pr.utils.StringUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class DiseaseHistory.
 * 
 * @author Kulasekaran
 * @version 1.0
 */
@Entity
@Table(name = "disease_history")
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "disease_history_id_seq") })
public class DiseaseHistory extends AbstractMutableDeletableDomainObject {
    
    /** The other primary disease code. */
    private String otherPrimaryDiseaseCode;

    /** The other primary disease site code. */
    private String otherPrimaryDiseaseSiteCode;

    /** The study disease. */
    private StudyDisease studyDisease;
    
    private ICD9DiseaseSite icd9DiseaseSite;

    @OneToOne
    @JoinColumn(name = "icd9_disease_site_id")
    @Cascade(value = { CascadeType.LOCK })
    public ICD9DiseaseSite getIcd9DiseaseSite() {
		return icd9DiseaseSite;
	}

	public void setIcd9DiseaseSite(ICD9DiseaseSite icd9DiseaseSite) {
		this.icd9DiseaseSite = icd9DiseaseSite;
	}


    /**
     * Gets the study disease.
     * 
     * @return the study disease
     */
    @OneToOne
    @JoinColumn(name = "study_disease_id")
    //@Cascade(value = { CascadeType.ALL })
    public StudyDisease getStudyDisease() {
        return studyDisease;
    }

    /**
     * Sets the study disease.
     * 
     * @param studyDisease the new study disease
     */
    public void setStudyDisease(StudyDisease studyDisease) {
        this.studyDisease = studyDisease;
    }

    /**
     * Gets the other primary disease code.
     * 
     * @return the other primary disease code
     */
    @Column(name = "other_disease_code")
    public String getOtherPrimaryDiseaseCode() {
        return otherPrimaryDiseaseCode;
    }

    /**
     * Sets the other primary disease code.
     * 
     * @param otherPrimaryDiseaseCode the new other primary disease code
     */
    public void setOtherPrimaryDiseaseCode(String otherPrimaryDiseaseCode) {
        this.otherPrimaryDiseaseCode = otherPrimaryDiseaseCode;
    }

    /**
     * Gets the other primary disease site code.
     * 
     * @return the other primary disease site code
     */
    @Column(name = "other_disease_site_code")
    public String getOtherPrimaryDiseaseSiteCode() {
        return otherPrimaryDiseaseSiteCode;
    }

    /**
     * Sets the other primary disease site code.
     * 
     * @param otherPrimaryDiseaseSiteCode the new other primary disease site code
     */
    public void setOtherPrimaryDiseaseSiteCode(String otherPrimaryDiseaseSiteCode) {
        this.otherPrimaryDiseaseSiteCode = otherPrimaryDiseaseSiteCode;
    }

    /**
     * Gets the primary disease str.
     * 
     * @return the primary disease str
     */
    @Transient
    public String getPrimaryDiseaseStr() {
        if (!StringUtils.isBlank(otherPrimaryDiseaseCode)) return otherPrimaryDiseaseCode;
        try {
            return studyDisease.getDiseaseTerm().getTerm();
        }
        catch (RuntimeException e) {
        }
        return "";
    }

    /**
     * Gets the primary disease site str.
     * 
     * @return the primary disease site str
     */
    @Transient
    public String getPrimaryDiseaseSiteStr() {
        if (!StringUtils.isBlank(otherPrimaryDiseaseSiteCode)) return otherPrimaryDiseaseSiteCode;
        try {
            return icd9DiseaseSite.getName();
        }
        catch (RuntimeException e) {
        }
        return "";
    }

}
