package edu.duke.cabig.c3pr.dao;

import org.springframework.transaction.annotation.Transactional;

import edu.duke.cabig.c3pr.domain.Epoch;
import edu.duke.cabig.c3pr.domain.StudyDisease;
import edu.duke.cabig.c3pr.domain.StudyVersion;
import gov.nih.nci.cabig.ctms.dao.MutableDomainObjectDao;

public class StudyVersionDao extends GridIdentifiableDao<StudyVersion> implements MutableDomainObjectDao<StudyVersion> {
	
	private EpochDao epochDao;

	public void setEpochDao(EpochDao epochDao) {
		this.epochDao = epochDao;
	}

	@Override
	public Class<StudyVersion> domainClass() {
		return StudyVersion.class ;
	}

	@Transactional(readOnly = false)
	public void save(StudyVersion studyVersion) {
		getHibernateTemplate().saveOrUpdate(studyVersion);
	}

	@Transactional(readOnly = false)
	public void initialize(StudyVersion studyVersion){ 
		getHibernateTemplate().initialize(studyVersion.getEpochsInternal());
		for (Epoch epoch : studyVersion.getEpochsInternal()) {
			if (epoch != null) {
				epochDao.initialize(epoch);
			}
		}
		
		getHibernateTemplate().initialize(studyVersion.getStudyDiseases());
		for(StudyDisease studyDisease : studyVersion.getStudyDiseases()){
			getHibernateTemplate().initialize(studyDisease.getDiseaseTerm().getCategory().getTerms());
			getHibernateTemplate().initialize(studyDisease.getDiseaseTerm().getCategory());
			getHibernateTemplate().initialize(studyDisease.getDiseaseTerm().getCategory().getChildCategories());
		}
	}

}