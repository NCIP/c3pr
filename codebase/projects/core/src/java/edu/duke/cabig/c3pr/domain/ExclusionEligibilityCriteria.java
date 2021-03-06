/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * @author Kruttik
 */
@Entity
@DiscriminatorValue(value = "E")
public class ExclusionEligibilityCriteria extends EligibilityCriteria {

    public ExclusionEligibilityCriteria() {
        setQuestionNumber(new Integer(0));
    }

}
