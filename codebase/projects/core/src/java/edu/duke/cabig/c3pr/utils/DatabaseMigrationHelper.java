package edu.duke.cabig.c3pr.utils;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import edu.duke.cabig.c3pr.constants.EpochType;
import edu.duke.cabig.c3pr.dao.EpochDao;
import edu.duke.cabig.c3pr.domain.Epoch;
import edu.duke.cabig.c3pr.domain.Study;

public class DatabaseMigrationHelper extends HibernateDaoSupport {
	
	public EpochDao epochDao;

	public void setEpochDao(EpochDao epochDao) {
		this.epochDao = epochDao;
	}

	public boolean isDatabaseMigrationNeeded(){
		return getMigratableStudies().size()>0;
	}
	
	public void updateEpochWithType(int epochId, EpochType epochType){
		Epoch epoch = epochDao.getById(epochId);
		epoch.setType(epochType);
		epochDao.merge(epoch);
	}
	
	public List<Study> getMigratableStudies(){
		return getHibernateTemplate().find("select distinct s from Study s join s.studyVersionsInternal sv join sv.epochsInternal e where e.type is null");
	}
}
