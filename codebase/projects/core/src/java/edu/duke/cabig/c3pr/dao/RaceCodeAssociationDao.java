/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.dao;

import edu.duke.cabig.c3pr.domain.RaceCodeAssociation;
import gov.nih.nci.cabig.ctms.dao.MutableDomainObjectDao;

public class RaceCodeAssociationDao extends GridIdentifiableDao<RaceCodeAssociation> implements MutableDomainObjectDao<RaceCodeAssociation> {

	public Class<RaceCodeAssociation> domainClass() {
		return RaceCodeAssociation.class;
	}

	public void save(RaceCodeAssociation raceCodeAssociation) {
		super.save(raceCodeAssociation);
		
	}

	
}
