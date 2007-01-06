package edu.duke.cabig.c3pr.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.springframework.dao.DataAccessException;

import edu.duke.cabig.c3pr.domain.Participant;

/**
 * @author kulasekaran
 * @author Ramakrishna
 */
public class ParticipantDao extends AbstractBaseDao<Participant> {
	
	public Class<Participant> domainClass() {
	        return Participant.class;
	 }	
	
	/*
	 * Lists all Participant objects
	 */
	public List<Participant> searchByExample(Participant participant) throws DataAccessException{
			Session session = getHibernateTemplate().getSessionFactory().openSession();		
			Example searchCriteria = Example.create(participant).excludeZeroes();;
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
