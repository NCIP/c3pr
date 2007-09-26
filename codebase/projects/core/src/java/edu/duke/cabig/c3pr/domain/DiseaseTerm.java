package edu.duke.cabig.c3pr.domain;

import edu.duke.cabig.c3pr.domain.AbstractMutableDeletableDomainObject;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * @author Priyatam
 */
@Entity
@Table (name = "disease_terms")
@GenericGenerator(name="id-generator", strategy = "native",
    parameters = {
        @Parameter(name="sequence", value="DISEASE_TERMS_ID_SEQ")
    }
)
public class DiseaseTerm extends AbstractMutableDeletableDomainObject {
    private String term;
    //private String select;
    private String ctepTerm;
    private long medraCode;
    private DiseaseCategory category;
    //private boolean otherRequired;

    ////// LOGIC

    ////// BEAN PROPERTIES

    public void setMedraCode(long medraCode) {
		this.medraCode = medraCode;
	}

    public long getMedraCode() {
        return medraCode;
    }


	public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getCtepTerm() {
        return ctepTerm;
    }

    public void setCtepTerm(String ctepTerm) {
        this.ctepTerm = ctepTerm;
    }

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name="category_id", nullable=false)   
    public DiseaseCategory getCategory() {
        return category;
    }

    public void setCategory(DiseaseCategory category) {
        this.category = category;
    }
    
    public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		final DiseaseTerm that = (DiseaseTerm) o;

		if (ctepTerm != null ? !ctepTerm.equals(that.ctepTerm)
				: that.ctepTerm != null)
			return false;
		return true;
	}

	public int hashCode() {
		int result;
		result = (ctepTerm != null ? ctepTerm.hashCode() : 0);
		return result;
	}
    
}
