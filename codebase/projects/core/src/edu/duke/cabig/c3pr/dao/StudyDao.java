package edu.duke.cabig.c3pr.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.springframework.dao.DataAccessException;
import edu.duke.cabig.c3pr.dao.AbstractBaseDao;
import edu.duke.cabig.c3pr.dao.StudyDao;
import edu.duke.cabig.c3pr.domain.Arm;
import edu.duke.cabig.c3pr.domain.Epoch;
import edu.duke.cabig.c3pr.domain.Study;


/**
 * Hibernate implementation of StudyDao
 * @author Priyatam
 */
public class StudyDao extends AbstractBaseDao<Study> {
	
	@Override
	public Class<Study> domainClass() {
		return Study.class;
	 }

	/*
	 * Searches based on an example object. Typical usage from your service class: -
	 * If you want to search based on diseaseCode, monitorCode,
	 * <li><code>Study study = new Study();</li></code>
	 * <li>code>study.setDiseaseCode("diseaseCode");</li></code>
	 * <li>code>study.setDMonitorCode("monitorCode");</li></code>
	 * <li>code>studyDao.searchByExample(study)</li></code>
	 * @return list of matching study objects based on your sample study object
	 * (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.dao.StudyDao#searchByExample(edu.duke.cabig.c3pr.domain.Study)
	 */
	public List<Study> searchByExample(Study study) throws DataAccessException{
		Session session = getHibernateTemplate().getSessionFactory().openSession();		
		Example searchCriteria = Example.create(study);
		return session.createCriteria(Study.class).add(searchCriteria).list();
	}
	
	/*
	 * Returns all Study objects
	 * (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.dao.StudyDao#getAll()
	 */
	 public List<Study> getAll() throws DataAccessException{
		 return getHibernateTemplate().find("from Study");
	 }

	/* (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.dao.StudyDao#getArmsForStudy(java.lang.Integer)
	 */
	public List<Arm> getArmsForStudy(Integer studyId) {
	     return getHibernateTemplate().find("select a from Arm a" +
	     		"inner join a.epoch ep where ep.study.id = ?", studyId);	     
	}

	/* (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.dao.StudyDao#getEpochsForStudy(java.lang.Integer)
	 */
	public List<Epoch> getEpochsForStudy(Integer studyId) {
		//TODO
		
		return null;
	}

}
