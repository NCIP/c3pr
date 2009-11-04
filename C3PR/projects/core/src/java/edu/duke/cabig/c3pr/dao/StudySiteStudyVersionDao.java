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
