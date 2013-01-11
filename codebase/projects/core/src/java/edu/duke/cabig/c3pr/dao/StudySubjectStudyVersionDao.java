/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.dao;

import edu.duke.cabig.c3pr.domain.StudySiteStudyVersion;
import edu.duke.cabig.c3pr.domain.StudySubjectStudyVersion;
import gov.nih.nci.cabig.ctms.dao.MutableDomainObjectDao;

public class StudySubjectStudyVersionDao extends GridIdentifiableDao<StudySubjectStudyVersion> implements MutableDomainObjectDao<StudySubjectStudyVersion> {

	@Override
	public Class<StudySubjectStudyVersion> domainClass() {
		return StudySubjectStudyVersion.class ;
	}

	public void save(StudySubjectStudyVersion studySubjectStudyVersion) {
		super.save(studySubjectStudyVersion);

	}

    public void remove(StudySubjectStudyVersion studySubjectStudyVersion) {
        getHibernateTemplate().delete(studySubjectStudyVersion);
    }

}
