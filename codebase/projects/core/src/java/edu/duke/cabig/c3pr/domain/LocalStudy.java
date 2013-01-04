/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 *   Distributed under the OSI-approved BSD 3-Clause License.
 *   See https://github.com/NCIP/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * @author Vinay Gangoli 
 */
@Entity
@DiscriminatorValue("Local")
public class LocalStudy extends Study {
	
	public LocalStudy(){
		super();
	}
	
	public LocalStudy(boolean forSearchByExample){
		super(forSearchByExample);
	}
}
