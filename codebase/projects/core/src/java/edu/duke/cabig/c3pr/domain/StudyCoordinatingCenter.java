/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * The Class StudyCoordinatingCenter.
 * 
 * @author Ram Chilukuri
 */
@Entity
@DiscriminatorValue(value = "SCC")
public class StudyCoordinatingCenter extends StudyOrganization {

    /**
     * Instantiates a new study coordinating center.
     */
    public StudyCoordinatingCenter() {
        super();
        this.setHostedMode(true);
    }
}
