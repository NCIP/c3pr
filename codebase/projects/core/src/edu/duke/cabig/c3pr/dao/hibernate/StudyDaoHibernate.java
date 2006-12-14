package edu.duke.cabig.c3pr.dao.hibernate;

import edu.duke.cabig.c3pr.dao.AbstractBaseDao;
import edu.duke.cabig.c3pr.dao.StudyDao;
import edu.duke.cabig.c3pr.domain.Study;

/**
 * @author Priyatam
 *
 *  Interface defining methods by which the C3PR Application will access the data store.  All data
 *  pertaiing to the Study data aggregate should be accessed via an implementation of this
 *  interface.
 *
 *  These methods throw org.springframework.dao.DataAccessException which is a Run-time Exception
 *  that should be handled in one of the calling classes.  See the Spring Framework API for a
 *  hierarchy of the DataAccessException.

 */
public class StudyDaoHibernate extends AbstractBaseDao<Study> implements StudyDao {
	
	public Class<Study> domainClass() {
	        return Study.class;
	 }
	
	 public void saveStudy(Study study) throws Exception {
		 getHibernateTemplate().saveOrUpdate(study);		
	}


}
