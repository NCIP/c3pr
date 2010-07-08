package edu.duke.cabig.c3pr.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
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

import gov.nih.nci.cabig.ctms.collections.LazyListHelper;

@Entity
@Table(name = "consents", uniqueConstraints = { @UniqueConstraint(columnNames = { "stu_version_id", "name" }) })
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "CONSENTS_ID_SEQ") })
public class Consent extends AbstractMutableDeletableDomainObject implements Comparable<Consent>{

	private LazyListHelper lazyListHelper;
	
	private Boolean mandatoryIndicator = false;
	
	public Consent(){
		lazyListHelper = new LazyListHelper();
		lazyListHelper.add(ConsentQuestion.class,new InstantiateFactory<ConsentQuestion>(ConsentQuestion.class));
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

	@OneToMany(fetch=FetchType.EAGER)
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
