package edu.duke.cabig.c3pr.domain;

import gov.nih.nci.cabig.ctms.domain.AbstractMutableDomainObject;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;

/**
 * @author Priyatam
 */
 @Entity
 @Table (name = "eligibility_criterias")
 @Inheritance(strategy=InheritanceType.SINGLE_TABLE) 
 @GenericGenerator(name="id-generator", strategy = "native",
     parameters = {
         @Parameter(name="sequence", value="ELIGIBILITY_CRITERIAS_ID_SEQ")
     }
 )
public abstract class EligibilityCriteria extends AbstractMutableDomainObject
{			
	private Boolean notApplicableIndicator;
	
	private int questionNumber;
	
	private String questionText;
	
	private Study study;

    private String name;

    @ManyToOne
	@JoinColumn(name="stu_id", updatable = false, insertable = false) 
	public Study getStudy() {
		return study;
	}

	public void setStudy(Study study) {
		this.study = study;
	}

	public Boolean getNotApplicableIndicator() {
		return notApplicableIndicator;
	}

	public void setNotApplicableIndicator(Boolean notApplicableInidicator) {
		this.notApplicableIndicator = notApplicableInidicator;
	}

	public int getQuestionNumber() {
		return questionNumber;
	}

	public void setQuestionNumber(int questionNumber) {
		this.questionNumber = questionNumber;
	}

	public String getQuestionText() {
		return questionText;
	}

	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}

     @Transient
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}