/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

// TODO: Auto-generated Javadoc
/**
 * The Class StudyDisease.
 *
 * @author Priyatam
 */

@Entity
@Table(name = "study_diseases")
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "STUDY_DISEASES_ID_SEQ") })
public class StudyDisease extends AbstractMutableDeletableDomainObject {

    /** The disease term. */
    private DiseaseTerm diseaseTerm;

    /** The lead disease. */
    private Boolean leadDisease;

    /**
     * Gets the disease term.
     *
     * @return the disease term
     */
    @ManyToOne
    @JoinColumn(name = "disease_term_id")
    @Cascade(value = { CascadeType.LOCK })
    public DiseaseTerm getDiseaseTerm() {
        return diseaseTerm;
    }

    /**
     * Sets the disease term.
     *
     * @param diseaseTerm the new disease term
     */
    public void setDiseaseTerm(DiseaseTerm diseaseTerm) {
        this.diseaseTerm = diseaseTerm;
    }

    /**
     * Gets the lead disease.
     *
     * @return the lead disease
     */
    public Boolean getLeadDisease() {
        return leadDisease;
    }

    /**
     * Sets the lead disease.
     *
     * @param leadDisease the new lead disease
     */
    public void setLeadDisease(Boolean leadDisease) {
        this.leadDisease = leadDisease;
    }

	/* (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.domain.AbstractMutableDeletableDomainObject#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((diseaseTerm == null) ? 0 : diseaseTerm.hashCode());

		return result;
	}

	/* (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.domain.AbstractMutableDeletableDomainObject#equals(java.lang.Object)
	 */
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

		return true;
	}

}
