package edu.duke.cabig.c3pr.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;

import edu.duke.cabig.c3pr.domain.Arm;
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
	 */
	public List<Study> searchByExample(Study study, boolean isWildCard) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();		
		Example searchCriteria = Example.create(study).excludeZeroes().ignoreCase();
		if (isWildCard)
		{
			searchCriteria.enableLike(MatchMode.ANYWHERE);
		}
		return session.createCriteria(Study.class).add(searchCriteria).list();
	}
	
	/**
	 * Default Search without a Wildchar
	 * @see edu.duke.cabig.c3pr.dao.searchByExample(Study study, boolean isWildCard)
	 * @param study
	 * @return Search Results
	 */
	public List<Study> searchByExample(Study study) {
		return searchByExample(study, false);
	}
	
	/**
	 * Returns all study objects
	 * @return list of study objects
	 */
	public List<Study> getAll(){
		 return getHibernateTemplate().find("from Study");
	 }

	/**
	 * Get all Arms associated with all of this study's epochs 
	 * @param studyId the study id
	 * @return list of Arm objects given a study id 
	 */
	public List<Arm> getArmsForStudy(Integer studyId) {
		return getHibernateTemplate().find("select a from Study s join s.epochs e join e.arms a " +
		"where s.id = ?", studyId);
	}
}
