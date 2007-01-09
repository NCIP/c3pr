package edu.duke.cabig.c3pr.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.springframework.dao.DataAccessException;

import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.Study;

/**
 * @author Priyatam, kulasekaran
 */
public class ParticipantDao extends AbstractBaseDao<Participant> {

	public Class<Participant> domainClass() {
		return Participant.class;
	}

	/**
	 * /* Searches based on an example object. Typical usage from your service
	 * class: - If you want to search based on diseaseCode, monitorCode,
	 * <li><code>Participant participant = new Participant();</li></code>
	 * <li>code>participant.setLastName("last_namee");</li>
	 * </code>
	 * <li>code>participantDao.searchByExample(study)</li>
	 * </code>
	 * 
	 * @return list of matching participant objects based on your sample
	 *         participant object
	 * @param participant
	 * @return
	 */
	public List<Participant> searchByExample(Participant participant, boolean isWildCard){
			Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();		
			Example searchCriteria = Example.create(participant).excludeZeroes();
			if (isWildCard)
			{
				Example example 
		        = Example.create(participant)
		                 .ignoreCase()
		                 .excludeZeroes()
		                 .excludeProperty("doNotUse") 
		                 .enableLike(MatchMode.ANYWHERE);

					return getSession()
			      .createCriteria(Participant.class)
			      .add(example).list();

//				searchCriteria.enableLike(MatchMode.ANYWHERE);
//				session.createCriteria()
			}
			return session.createCriteria(Participant.class).add(searchCriteria).list();
									
	  }

	/**
	 * Default Search without a Wildchar
	 * 
	 * @see edu.duke.cabig.c3pr.dao.searchByExample(Participant participant,
	 *      boolean isWildCard)
	 * @param participant
	 * @return Search results
	 */
	public List<Participant> searchByExample(Participant participant) {
		return searchByExample(participant, true);
	}

	/**
	 * 
	 * @return
	 * @throws DataAccessException
	 */
	public List<Participant> getAll() throws DataAccessException {
		return getHibernateTemplate().find("from Participant");
	}
}
