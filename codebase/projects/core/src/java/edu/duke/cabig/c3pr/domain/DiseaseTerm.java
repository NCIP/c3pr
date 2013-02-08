/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

// TODO: Auto-generated Javadoc
/**
 * The Class DiseaseTerm.
 * 
 * @author Priyatam
 */
@Entity
@Table(name = "disease_terms")
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "DISEASE_TERMS_ID_SEQ") })
public class DiseaseTerm extends AbstractMutableDeletableDomainObject {
    
    /** The term. */
    private String term;

    // private String select;
    /** The ctep term. */
    private String ctepTerm;

    /** The medra code. */
    private long medraCode;

    /** The category. */
    private DiseaseCategory category;

    // private boolean otherRequired;

    // //// LOGIC

    // //// BEAN PROPERTIES

    /**
     * Sets the medra code.
     * 
     * @param medraCode the new medra code
     */
    public void setMedraCode(long medraCode) {
        this.medraCode = medraCode;
    }

    /**
     * Gets the medra code.
     * 
     * @return the medra code
     */
    public long getMedraCode() {
        return medraCode;
    }

    /**
     * Gets the term.
     * 
     * @return the term
     */
    public String getTerm() {
        return term;
    }

    /**
     * Sets the term.
     * 
     * @param term the new term
     */
    public void setTerm(String term) {
        this.term = term;
    }

    /**
     * Gets the ctep term.
     * 
     * @return the ctep term
     */
    public String getCtepTerm() {
        return ctepTerm;
    }

    /**
     * Sets the ctep term.
     * 
     * @param ctepTerm the new ctep term
     */
    public void setCtepTerm(String ctepTerm) {
        this.ctepTerm = ctepTerm;
    }

    /**
     * Gets the category.
     * 
     * @return the category
     */
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    public DiseaseCategory getCategory() {
        return category;
    }

    /**
     * Sets the category.
     * 
     * @param category the new category
     */
    public void setCategory(DiseaseCategory category) {
        this.category = category;
    }

    /* (non-Javadoc)
     * @see edu.duke.cabig.c3pr.domain.AbstractMutableDeletableDomainObject#equals(java.lang.Object)
     */
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final DiseaseTerm that = (DiseaseTerm) o;

        if (ctepTerm != null ? !ctepTerm.equals(that.ctepTerm) : that.ctepTerm != null) return false;
        return true;
    }

    /* (non-Javadoc)
     * @see edu.duke.cabig.c3pr.domain.AbstractMutableDeletableDomainObject#hashCode()
     */
    public int hashCode() {
    	final int PRIME = 31;
        int result = 1;
        result = PRIME*result + (ctepTerm != null ? ctepTerm.hashCode() : 0);
        return result;
    }

}
