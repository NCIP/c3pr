package edu.duke.cabig.c3pr.dao.hibernate;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.springframework.dao.DataAccessException;

import edu.duke.cabig.c3pr.dao.AbstractBaseDao;
import edu.duke.cabig.c3pr.dao.ParticipantDao;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.Study;

/**
 * @author kulasekaran,Ramakrishna
 * @version 1.0
 */
public class ParticipantDaoHibernate extends AbstractBaseDao<Participant> implements ParticipantDao {
	
	public Class<Participant> domainClass() {
	        return Participant.class;
	 }
	
	public void saveParticipant(Participant participant) throws Exception {
		 getHibernateTemplate().saveOrUpdate(participant);		
	 }
	 
	    /*
		 * Lists all Participant objects
	     */
	 
	 public List<Participant> searchByExample(Participant participant) throws DataAccessException{
			Session session = getHibernateTemplate().getSessionFactory().openSession();		
			Example searchCriteria = Example.create(participant);
			return session.createCriteria(Participant.class).add(searchCriteria).list();
	  }
		
		/*
		 * Returns all Participant objects
		 * (non-Javadoc)
		 * @see edu.duke.cabig.c3pr.dao.ParticipantDao#getAll()
		 */
	 public List<Participant> getAll() throws DataAccessException{
			 return getHibernateTemplate().find("from Participant");
		 }
}
