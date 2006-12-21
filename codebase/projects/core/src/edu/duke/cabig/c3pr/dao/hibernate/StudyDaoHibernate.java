package edu.duke.cabig.c3pr.dao.hibernate;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.springframework.dao.DataAccessException;
import edu.duke.cabig.c3pr.dao.AbstractBaseDao;
import edu.duke.cabig.c3pr.dao.StudyDao;
import edu.duke.cabig.c3pr.domain.Study;


/**
 *  Interface defining methods by which the C3PR Application will access the data store.  All data
 *  pertaiing to the Study data aggregate should be accessed via an implementation of this
 *  interface.
 *
 *  These methods throw org.springframework.dao.DataAccessException which is a Run-time Exception
 *  that should be handled in one of the calling classes.  See the Spring Framework API for a
 *  hierarchy of the DataAccessException.
 * 
 * @author Priyatam
 */
public class StudyDaoHibernate extends AbstractBaseDao<Study> implements StudyDao {
	
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
	 public List<Study> getAll() {
		 return getHibernateTemplate().find("from Study");
	 }

}
