package edu.duke.cabig.c3pr.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.apache.commons.collections15.functors.InstantiateFactory;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Where;
import org.hibernate.validator.NotNull;

import edu.duke.cabig.c3pr.constants.ConsentingMethod;
import edu.duke.cabig.c3pr.utils.StringUtils;
import gov.nih.nci.cabig.ctms.collections.LazyListHelper;

@Entity
@Table(name = "consents", uniqueConstraints = { @UniqueConstraint(columnNames = { "stu_version_id", "name" }) })
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "CONSENTS_ID_SEQ") })
public class Consent extends AbstractMutableDeletableDomainObject implements Comparable<Consent>{

	private LazyListHelper lazyListHelper;
	
	private Boolean mandatoryIndicator = false;
	
	private List<ConsentingMethod> consentingMethods = new ArrayList<ConsentingMethod>();
	
	private StudyVersion studyVersion;
	
	@ManyToOne
	@JoinColumn(name="stu_version_id", nullable = false)
	public StudyVersion getStudyVersion() {
		return studyVersion;
	}

	public void setStudyVersion(StudyVersion studyVersion) {
		this.studyVersion = studyVersion;
	}

	@Transient
	public List<ConsentingMethod> getConsentingMethods() {
		return consentingMethods;
	}
	
	@Column(name="consenting_methods")
	public String getConsentingMethodsString() {
		String consentingMethodsString = "";
		for(ConsentingMethod consentingMethod : consentingMethods){
			if(consentingMethodsString != ""){
				consentingMethodsString = consentingMethodsString + " : " + consentingMethod.getName();
			}else{
				consentingMethodsString = consentingMethod.getName();
			}
		}
		return consentingMethodsString;
	}
	
	public void setConsentingMethodsString(String consentingMethodsString) {
		consentingMethods = new ArrayList<ConsentingMethod>();
		if (!StringUtils.isBlank(consentingMethodsString)) {
			StringTokenizer tokenizer = new StringTokenizer(consentingMethodsString, " : ");
			while (tokenizer.hasMoreTokens()) {
				ConsentingMethod consentingMethod = (ConsentingMethod) Enum.valueOf(ConsentingMethod.class, tokenizer
						.nextToken());
				consentingMethods.add(consentingMethod);
			};
		}
	}

	public void setConsentingMethods(List<ConsentingMethod> consentingMethods) {
		this.consentingMethods = consentingMethods;
	}

	public Consent(){
		lazyListHelper = new LazyListHelper();
		lazyListHelper.add(ConsentQuestion.class,new InstantiateFactory<ConsentQuestion>(ConsentQuestion.class));
		this.consentingMethods.add(ConsentingMethod.WRITTEN);
	}
	
	public int compareTo(Consent o) {
		if (this.equals(o))
			return 0;
		else
			return 1;
	}

	private String name;
	
	public Boolean getMandatoryIndicator() {
		return mandatoryIndicator;
	}

	@OneToMany
	@JoinColumn(name = "con_id",nullable=false)
	@Cascade(value={CascadeType.ALL,CascadeType.DELETE_ORPHAN})
	@Where(clause = "retired_indicator  = 'false'")
	public List<ConsentQuestion> getQuestionsInternal() {
		return lazyListHelper.getInternalList(ConsentQuestion.class);
	}
	
	@Transient
	public List<ConsentQuestion> getQuestions() {
		return lazyListHelper.getLazyList(ConsentQuestion.class);
	}

	public void setQuestions(List<ConsentQuestion> questions) {
		setQuestionsInternal(questions);
	}
	
	public void setQuestionsInternal(List<ConsentQuestion> questions) {
		lazyListHelper.setInternalList(ConsentQuestion.class, questions);
	}
	
	public void addQuestion(ConsentQuestion question){
		this.getQuestions().add(question);
	}

	public void setMandatoryIndicator(Boolean mandatoryIndicator) {
		this.mandatoryIndicator = mandatoryIndicator;
	}

	private String versionId;
	
	public String getVersionId() {
		return versionId;
	}

	public void setVersionId(String versionId) {
		this.versionId = versionId;
	}


	@NotNull
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

}
