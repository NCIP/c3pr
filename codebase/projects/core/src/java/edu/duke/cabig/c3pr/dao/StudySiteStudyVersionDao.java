/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.dao;

import edu.duke.cabig.c3pr.domain.StudySiteStudyVersion;
import gov.nih.nci.cabig.ctms.dao.MutableDomainObjectDao;

public class StudySiteStudyVersionDao extends GridIdentifiableDao<StudySiteStudyVersion> implements MutableDomainObjectDao<StudySiteStudyVersion> {
	
	@Override
	public Class<StudySiteStudyVersion> domainClass() {
		return StudySiteStudyVersion.class ;
	}

	public void save(StudySiteStudyVersion studySiteStudyVersion) {
		super.save(studySiteStudyVersion);
		
	}

}
